package org.fms.billing.server.webapp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.fms.billing.common.util.FormatterUtil;
import org.fms.billing.common.webapp.domain.BackChargeDomain;
import org.fms.billing.common.webapp.domain.DeptDomain;
import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.NoteInfoDomain;
import org.fms.billing.common.webapp.domain.beakInterface.SettlementDomain;
import org.fms.billing.common.webapp.entity.MapEntity;
import org.fms.billing.common.webapp.entity.MeterPageEntity;
import org.fms.billing.common.webapp.entity.SettlementEntity;
import org.fms.billing.common.webapp.entity.UserInfoEntity;
import org.fms.billing.common.webapp.service.IBackChargeService;
import org.fms.billing.common.webapp.service.IChargeService;
import org.fms.billing.common.webapp.service.INoteInfoService;
import org.fms.billing.server.webapp.service.DeptService;
import org.fms.billing.server.webapp.service.SysCommonConfigService;
import org.fms.billing.server.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.properties.Global;
import com.riozenc.titanTool.spring.web.client.TitanTemplate;
import com.riozenc.titanTool.spring.web.http.HttpResult;
import com.riozenc.titanTool.spring.web.http.HttpResultPagination;

@Controller
@RequestMapping("backCharge")
public class BackChargeController {
    @Autowired
    @Qualifier("backChargeServiceImpl")
    private IBackChargeService iBackChargeService;

    @Autowired
    @Qualifier("chargeServiceImpl")
    private IChargeService iChargeService;

    @Autowired
    @Qualifier("noteInfoServiceImpl")
    private INoteInfoService iNoteInfoService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SysCommonConfigService sysCommonConfigService;

    @Autowired
    private UserService userService;

    @Autowired
    private TitanTemplate titanTemplate;

    @Autowired
    private DeptService deptService;

    @RequestMapping("select")
    @ResponseBody
    public List<BackChargeDomain> fingByWhere(@ModelAttribute BackChargeDomain backChargeDomain) {
        return iBackChargeService.findByWhere(backChargeDomain);
    }

    @RequestMapping("insert")
    @ResponseBody
    public int insert(@ModelAttribute BackChargeDomain backChargeDomain) {
        return iBackChargeService.insert(backChargeDomain);
    }

    @RequestMapping("update")
    @ResponseBody
    public int update(@ModelAttribute BackChargeDomain backChargeDomain) {
        return iBackChargeService.update(backChargeDomain);
    }

    @RequestMapping("delete")
    @ResponseBody
    public HttpResult delete(@RequestBody String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        List<Long> ids = JSONObject.parseArray(jsonObject.getString("ids"),
                Long.class);
        ids.forEach(t -> {
            BackChargeDomain backChargeDomain = new BackChargeDomain();
            backChargeDomain.setId(t);
            iBackChargeService.delete(backChargeDomain);
        });
        return new HttpResult(HttpResult.SUCCESS, "删除抹账申请成功");
    }


    //根据结算户获取收费记录
    @RequestMapping("getBackChargeBySettle")
    @ResponseBody
    public HttpResult getBackChargeBySettle(@RequestBody String settleJson) {
        MeterPageEntity meterPageEntity =
                JSONObject.parseObject(settleJson, MeterPageEntity.class);
        //根据结算户获取计量点信息
        BackChargeDomain backChargeDomain = JSONObject.parseObject(settleJson,
                BackChargeDomain.class);
        JSONObject settleJsonobject =
                JSONObject.parseObject(settleJson);
        String settlementNo = settleJsonobject.getString("no");
        String name = settleJsonobject.getString("name");
        String businessPlaceCode  = settleJsonobject.getString("businessPlaceCode");
        String bankNo = settleJsonobject.getString("bankNo");
        List<Long> deptIds=null;
        if(businessPlaceCode!=null && !"".equals(businessPlaceCode)){
        DeptDomain dept =
                deptService.getDept(Long.valueOf(businessPlaceCode));
        List<DeptDomain> deptDomains = deptService.getDeptList(Long.valueOf(businessPlaceCode));
        deptDomains.add(dept);

        deptIds=
                deptDomains.stream().map(DeptDomain::getId).distinct().collect(Collectors.toList());
        }

        JSONObject postData = new JSONObject();

        if ((settlementNo != null && !"".equals(settlementNo))||(bankNo != null && !"".equals(bankNo))) {

            //结算户信息
            Map<String, Object> params = new HashMap<>();
            params.put("settlementNo", settlementNo);
            params.put("settlementName", name);
            params.put("businessPlaceCodes", deptIds);
            params.put("bankNo", bankNo);
            params.put("pageSize", -1);
            HttpResultPagination<SettlementDomain> settlementResultDomains =
                    new HttpResultPagination<>();
            try {
                settlementResultDomains = titanTemplate.postJson("CIM-SERVER",
                        "cimServer/settlement?method=findClearSettlementByWhere", new HttpHeaders(), params,
                        new TypeReference<HttpResultPagination<SettlementDomain>>() {
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }

            List<SettlementDomain> settlementDomains =
                    settlementResultDomains.getList();

           //结算户下没有计量点
            if ((settlementDomains==null||settlementDomains.size()<1)) {
                return new HttpResult(HttpResult.ERROR, "结算户输入错误");
            }

            String settlementIdsString =
                    settlementDomains.stream().filter(t->t.getId()!=null).map(SettlementDomain::getId).map(String::valueOf).collect(Collectors.joining(","));
            meterPageEntity.setSettlementId(settlementIdsString);

        }else{
            meterPageEntity.setEraseOption(1);
        }
        meterPageEntity.setJzFlag(settleJsonobject.getInteger("jzFlag"));
        meterPageEntity.setOperator(settleJsonobject.getLong("managerId"));

        //上个月26号之前的帐不允许抹账
        List<BackChargeDomain> backChargeDomains =
                iBackChargeService.getBackChargeByMeterIds(meterPageEntity);

        //循环赋值 结算户号和计量点号
        backChargeDomains.forEach(t -> {
            postData.clear();
            postData.put("id", t.getSettlementId());
            String settlementJson =
                    restTemplate.postForObject(Global.getConfig(
                            "getSettlementById"), postData, String.class);
            SettlementEntity settlementEntity =
                    JSONObject.parseObject(settlementJson, SettlementEntity.class);
            t.setSettlementNo(settlementEntity.getSettlementNo());
            t.setSettlementName(settlementEntity.getSettlementName());
            if(t.getMeterId()!=null){
                postData.clear();
                postData.put("id", t.getMeterId());
                String meterJsons =
                        restTemplate.postForObject(Global.getConfig("getMeterByWhere"), postData, String.class);
                List<MeterDomain> appMeterInfosByCim =
                        JSONObject.parseArray(JSONObject.parseObject(meterJsons).get("list").toString(), MeterDomain.class);
                t.setMeterNo(appMeterInfosByCim.get(0).getMeterNo());
            }

        });
        return new HttpResult(HttpResult.SUCCESS, "查询成功", new HttpResultPagination(backChargeDomain, backChargeDomains));
    }


    //申请抹账
    @RequestMapping("applyBackChargeBySettle")
    @ResponseBody
    public HttpResult applyBackChargeBySettle(@RequestBody String json) {
        //获取前台传来的收费记录的id
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONObject postData = new JSONObject();
        List<Long> ids = JSONObject.parseArray(jsonObject.getString("ids"), Long.class);

        List<BackChargeDomain> backChargeDomains =
                iChargeService.findBackChargeByIds(ids);

        //有发票记录不能申请抹账
        List<NoteInfoDomain> noteInfoDomains =
                iNoteInfoService.findInvoiceNoteInfoByChargeIds(ids);

        if (noteInfoDomains.size() > 0) {
            return new HttpResult(HttpResult.ERROR, "请先作废电子发票再进行抹账");
        }

        backChargeDomains.forEach(t -> {
            //根据收费id判断 抹账表是否存在记录 存在就更新不存在就插入
            BackChargeDomain backChargeDomain = new BackChargeDomain();
            backChargeDomain.setChargeInfoId(t.getId());
            List<BackChargeDomain> testBackChargeDomains =
                    iBackChargeService.findByWhere(backChargeDomain);
            if (testBackChargeDomains == null || testBackChargeDomains.size() < 1) {
                t.setChargeInfoId(t.getId());
                t.setEraseReason(jsonObject.getString("eraseReason"));
                t.setId(null);
                //赋值结算户号
                postData.clear();
                postData.put("id", t.getSettlementId());
                String settlementJson =
                        restTemplate.postForObject(Global.getConfig(
                                "getSettlementById"), postData, String.class);
                SettlementEntity settlementEntity =
                        JSONObject.parseObject(settlementJson,SettlementEntity.class);
                t.setSettlementNo(settlementEntity.getSettlementNo());
                t.setSettlementName(settlementEntity.getSettlementName());
                //赋值计量点号
                postData.clear();
                postData.put("id", t.getMeterId());
                String meterJsons =
                        restTemplate.postForObject(Global.getConfig("getMeterByWhere"), postData, String.class);
                List<MeterDomain> appMeterInfosByCim =
                        JSONObject.parseArray(JSONObject.parseObject(meterJsons).get("list").toString(), MeterDomain.class);
                t.setMeterNo(appMeterInfosByCim.get(0).getMeterNo());
                iBackChargeService.insert(t);
            } else {
                testBackChargeDomains.get(0).setEraseReason(jsonObject.getString("eraseReason"));
                iBackChargeService.update(testBackChargeDomains.get(0));
            }
        });

        return new HttpResult(HttpResult.SUCCESS, "申请成功", null);
    }


    //申请审批保存
    @RequestMapping("saveBackCharge")
    @ResponseBody
    public HttpResult saveBackCharge(@RequestBody String paramJson) {
        JSONObject paramJsonObject = JSONObject.parseObject(paramJson);
        JSONObject jsonObject = paramJsonObject.getJSONObject("data");
        List<String> ids = JSONObject.parseArray(jsonObject.getString("ids"), String.class);
        List<BackChargeDomain> backChargeDomains = iBackChargeService.findBackChargeInfoByIds(ids);
        UserInfoEntity userInfoEntity =
                GsonUtils.readValue(paramJsonObject.get("userInfo").toString(), UserInfoEntity.class);

        backChargeDomains.forEach(t -> {
            t.setEraseOption(jsonObject.getInteger("eraseOption"));
            t.setErasePerson((long)userInfoEntity.getId());
            iBackChargeService.update(t);
        });
        return new HttpResult(HttpResult.SUCCESS, "保存成功", null);
    }


    //申请审批查询
    @RequestMapping("getBackCharge")
    @ResponseBody
    public HttpResult getBackCharge(@RequestBody String json) {

        MeterPageEntity meterPageEntity =
                JSONObject.parseObject(json, MeterPageEntity.class);

        BackChargeDomain backChargeDomain = JSONObject.parseObject(json,
                BackChargeDomain.class);
        //根据结算户获取计量点信息
        JSONObject settleJsonobject = JSONObject.parseObject(json);
        String settlementNo = settleJsonobject.getString("no");
        Long managerId = settleJsonobject.getLong("managerId");
        String name = settleJsonobject.getString("name");
        String businessPlaceCode  = settleJsonobject.getString("businessPlaceCode");
        String bankNo = settleJsonobject.getString("bankNo");

        List<Long> deptIds=null;
        if(businessPlaceCode!=null && !"".equals(businessPlaceCode)){
            DeptDomain dept =
                    deptService.getDept(Long.valueOf(businessPlaceCode));
            List<DeptDomain> deptDomains = deptService.getDeptList(Long.valueOf(businessPlaceCode));
            deptDomains.add(dept);

            deptIds=
                    deptDomains.stream().map(DeptDomain::getId).distinct().collect(Collectors.toList());
        }

        //结算户信息
        Map<String, Object> params = new HashMap<>();
        params.put("settlementNo", settlementNo);
        params.put("settlementName", name);
        params.put("businessPlaceCodes", deptIds);
        params.put("bankNo", bankNo);
        params.put("pageSize", -1);
        HttpResultPagination<SettlementDomain> settlementResultDomains =
                new HttpResultPagination<>();
        try {
            settlementResultDomains = titanTemplate.postJson("CIM-SERVER",
                    "cimServer/settlement?method=findClearSettlementByWhere", new HttpHeaders(), params,
                    new TypeReference<HttpResultPagination<SettlementDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<SettlementDomain> settlementDomains =
                settlementResultDomains.getList();

        //结算户下没有计量点
        if ((settlementDomains==null||settlementDomains.size()<1)) {
            return new HttpResult(HttpResult.ERROR, "结算户输入错误");
        }

        String settlementIdsString =
                settlementDomains.stream().filter(t->t.getId()!=null).map(SettlementDomain::getId).map(String::valueOf).collect(Collectors.joining(","));
        meterPageEntity.setSettlementId(settlementIdsString);

        List<BackChargeDomain> backChargeDomains=
                iBackChargeService.getBackCharge(meterPageEntity);

        //收费员自己收费不能自己审批
        if(backChargeDomains!=null && backChargeDomains.size()>0){
            backChargeDomains=
                    backChargeDomains.stream().filter(t->t.getOperator()!=Long.valueOf(managerId)).collect(Collectors.toList());
        }
        return new HttpResult(HttpResult.SUCCESS, "查询成功",
                new HttpResultPagination(backChargeDomain, backChargeDomains));
    }


    //申请审批查询
    @RequestMapping("getFinishBackCharge")
    @ResponseBody
    public HttpResult getFinishBackCharge(@RequestBody String json) {

        MeterPageEntity meterPageEntity =
                JSONObject.parseObject(json, MeterPageEntity.class);
        //根据结算户获取计量点信息
        JSONObject settleJsonobject = JSONObject.parseObject(json);

        String settlementNo = settleJsonobject.getString("no");
        String name = settleJsonobject.getString("name");
        String businessPlaceCode  = settleJsonobject.getString("businessPlaceCode");
        String bankNo = settleJsonobject.getString("bankNo");

        List<Long> deptIds=null;
        if(businessPlaceCode!=null && !"".equals(businessPlaceCode)){
            DeptDomain dept =
                    deptService.getDept(Long.valueOf(businessPlaceCode));
            List<DeptDomain> deptDomains = deptService.getDeptList(Long.valueOf(businessPlaceCode));
            deptDomains.add(dept);

            deptIds=
                    deptDomains.stream().map(DeptDomain::getId).distinct().collect(Collectors.toList());
        }

        JSONObject postData = new JSONObject();
        postData.put("settlementNo", settlementNo);
        postData.put("settlementName", name);
        postData.put("businessPlaceCodes", deptIds);
        postData.put("bankNo", bankNo);

        String meterIdsJsonString = restTemplate.postForObject(Global.getConfig(
                "getMeterIdsBySettlementInfo"), postData, String.class);

        List<String> meterIds = JSONObject.parseArray(meterIdsJsonString,String.class);
        if(meterIds!=null && meterIds.size()>0){
            meterPageEntity.setMeterId(String.join(",", meterIds));
        }
        //meterPageEntity.setErasePerson(settleJsonobject.getInteger("managerId"));

        List<BackChargeDomain> backChargeDomains=
                iBackChargeService.getFinishBackCharge(meterPageEntity);

        Map<Long, String> fChargeModeMap =
                sysCommonConfigService.getsystemCommonConfigMap("F_CHARGE_MODE");
        UserInfoEntity userDomain = new UserInfoEntity();
        List<MapEntity> listmap = userService.findMapByDomain(userDomain);
        Map<Long, String> userMap = FormatterUtil.ListMapEntityToMap(listmap);

        if(backChargeDomains!=null && backChargeDomains.size()>0){
            backChargeDomains.forEach(t->{
                t.setfChargeModeName(fChargeModeMap.get(Long.valueOf(t.getfChargeMode())));
                t.setOperatorName(userMap.get(t.getOperator()));
                t.setErasePersonName(userMap.get(t.getErasePerson()));
            });
        }
        return new HttpResult(HttpResult.SUCCESS, "查询成功",
                new HttpResultPagination(meterPageEntity,backChargeDomains));
    }


    //抹账
    @RequestMapping("backCharge")
    @ResponseBody
    public HttpResult backCharge(@RequestBody String json) throws Exception {
        return iBackChargeService.backCharge(json);
    }
}
