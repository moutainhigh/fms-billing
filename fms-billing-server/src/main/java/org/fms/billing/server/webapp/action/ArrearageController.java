package org.fms.billing.server.webapp.action;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.fms.billing.common.util.CustomCollectors;
import org.fms.billing.common.webapp.domain.ArrearageDetailDomain;
import org.fms.billing.common.webapp.domain.ArrearageDomain;
import org.fms.billing.common.webapp.domain.ChargeDomain;
import org.fms.billing.common.webapp.domain.DeptDomain;
import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.PreChargeDomain;
import org.fms.billing.common.webapp.domain.WriteSectDomain;
import org.fms.billing.common.webapp.domain.beakInterface.CustomerDomain;
import org.fms.billing.common.webapp.domain.beakInterface.SettlementDomain;
import org.fms.billing.common.webapp.entity.BankCollectionEntity;
import org.fms.billing.common.webapp.entity.BankEntity;
import org.fms.billing.common.webapp.entity.ElectricityTariffRankEntity;
import org.fms.billing.common.webapp.entity.MeterPageEntity;
import org.fms.billing.common.webapp.entity.SettlementEntity;
import org.fms.billing.common.webapp.service.IArrearageService;
import org.fms.billing.common.webapp.service.IChargeService;
import org.fms.billing.common.webapp.service.IPreChargeService;
import org.fms.billing.common.webapp.service.WriteSectService;
import org.fms.billing.server.webapp.service.DeptService;
import org.fms.billing.server.webapp.service.SysCommonConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.common.json.utils.JSONUtil;
import com.riozenc.titanTool.properties.Global;
import com.riozenc.titanTool.spring.web.client.TitanTemplate;
import com.riozenc.titanTool.spring.web.http.HttpResult;
import com.riozenc.titanTool.spring.web.http.HttpResultPagination;

@Controller
@RequestMapping("arrearage")
public class ArrearageController {
    @Autowired
    @Qualifier("arrearageServiceImpl")
    private IArrearageService iArrearageService;

    @Autowired
    @Qualifier("preChargeServiceImpl")
    private IPreChargeService iPreChargeService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TitanTemplate titanTemplate;

    @Autowired
    private DeptService deptService;

    @Autowired
    private SysCommonConfigService sysCommonConfigService;

    @Autowired
    @Qualifier("writeSectServiceImpl")
    private WriteSectService writeSectService;

    @Autowired
    @Qualifier("chargeServiceImpl")
    private IChargeService iChargeService;

    @RequestMapping("select")
    @ResponseBody
    public List<ArrearageDomain> fingByWhere(@ModelAttribute ArrearageDomain arrearageDomain) {
        return iArrearageService.findByWhere(arrearageDomain);
    }

    @RequestMapping("insert")
    @ResponseBody
    public int insert(@ModelAttribute ArrearageDomain arrearageDomain) {
        return iArrearageService.insert(arrearageDomain);
    }

    @RequestMapping("update")
    @ResponseBody
    public int update(@ModelAttribute ArrearageDomain arrearageDomain) {
        return iArrearageService.update(arrearageDomain);
    }

    @RequestMapping("delete")
    @ResponseBody
    public int delete(@ModelAttribute ArrearageDomain arrearageDomain) {
        arrearageDomain.setStatus(0);
        return iArrearageService.update(arrearageDomain);
    }

    // 根据条件筛选满足的计量点
    @RequestMapping("generateArrearage")
    @ResponseBody
    public int generateArrearage(@RequestBody String arrearageDomain) {
        Map<String, Object> arrearageMap = GsonUtils.readValue(arrearageDomain, Map.class);
        String mon = arrearageMap.get("mon").toString();
        String type = arrearageMap.get("type").toString();
        String value = arrearageMap.get("value").toString();
        switch (type) {
            case "1":

                break;
            case "2":

                break;
            case "3":

                break;
            default:
                break;
        }
        /*
         * meterIds.stream().forEach(t->{ String
         * flowNo=restTemplate.postForObject(Global.getConfig("getArrearageNo"), null,
         * String.class); });
         */
        return 1;
    }

    /**
     * 缴费 逻辑:先根据页面条件 选出欠费的计量点
     *
     * @param arrearageDomainJson
     * @return
     */
    @RequestMapping("findArrearageDetail")
    @ResponseBody
    public HttpResult findArrearageDetail(@RequestBody String arrearageDomainJson) throws IOException {
        // 获取分页参数
        MeterPageEntity meterPageEntity = JSONObject.parseObject(arrearageDomainJson, MeterPageEntity.class);
        //List<String> meterIds = new ArrayList<>();
        JSONObject businessJson = JSONObject.parseObject(arrearageDomainJson);
        JSONObject postData = new JSONObject();
        String chargeObject = businessJson.getString("chargeObject");
        String no = businessJson.getString("no");
        String name = businessJson.getString("name");
        String businessPlaceCode = businessJson.getString("businessPlaceCode");
        String bankNo = businessJson.getString("bankNo");
        if ("".equals(chargeObject) || null == chargeObject) {
            return new HttpResult(HttpResult.ERROR, "请选择条件");
        }
        if (("".equals(no) || null == no) && ("".equals(name) || null == name)) {
            if (("".equals(bankNo) || null == bankNo) || ("".equals(businessPlaceCode) || null == businessPlaceCode)) {
                return new HttpResult(HttpResult.ERROR, "结算户户号不能为空，托收用户收费时," +
                        "营业区域和银行账号都不能为空");
            }
        }
        List<Long> deptIds=null;
        if(businessPlaceCode!=null && !"".equals(businessPlaceCode)){
            DeptDomain dept =
                    deptService.getDept(Long.valueOf(businessPlaceCode));
            List<DeptDomain> deptDomains = deptService.getDeptList(Long.valueOf(businessPlaceCode));
            deptDomains.add(dept);

            deptIds=
                    deptDomains.stream().map(DeptDomain::getId).distinct().collect(Collectors.toList());
        }
        postData.put("settlementNo", no);
        postData.put("settlementName", name);
        postData.put("businessPlaceCodes", deptIds);
        postData.put("bankNo", bankNo);

        String meterIdsJsonString = restTemplate.postForObject(Global.getConfig(
                "getMeterIdsBySettlementInfo"), postData, String.class);

        List<Long> meterIds = JSONObject.parseArray(meterIdsJsonString, Long.class);

        /* List<MeterDomain> meterJsonList = JSONObject.parseArray(meterString, MeterDomain.class);*/

        // 结算户下没有计量点
        if (null == meterIds || meterIds.size() < 1) {
            return new HttpResult(HttpResult.ERROR, "结算户输入错误,该结算户下没有计量点");
        }

        /*meterIds = meterJsonList.stream().map(t -> {
            return t.getId().toString();
        }).collect(Collectors.toList());

        meterIds=meterIds.stream().distinct().collect(toList());*/

        String meterIdsString =
                meterIds.stream().map(String::valueOf).collect(Collectors.joining(","));
        meterPageEntity.setMeterId(meterIdsString);

        //结算户信息
        Map<String, Object> params = new HashMap<>();
        params.put("settlementNo", no);
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

        Map<Long, SettlementDomain> settlementDomainMap =
                settlementDomains.stream().collect(Collectors.toMap(SettlementDomain::getId, a -> a, (k1, k2) -> k1));

        if (bankNo != null) {
            if (settlementDomains == null || settlementDomains.size() < 1) {
                return new HttpResult(HttpResult.ERROR, "该结算户号不存在或者该结算户号存在重复记录");
            }
        } else {

            if (settlementDomains == null || settlementDomains.size() != 1) {
                return new HttpResult(HttpResult.ERROR, "该结算户号不存在或者该结算户号存在重复记录");
            }
        }

        String settlementIdsString =
                settlementDomains.stream().filter(t->t.getId()!=null).map(SettlementDomain::getId).map(String::valueOf).collect(Collectors.joining(","));
        meterPageEntity.setSettlementId(settlementIdsString);


        // 获得所有欠费的计量点信息
        List<ArrearageDetailDomain> arrearageDetailDomains = iArrearageService.findArrearageDetail(meterPageEntity);

        postData.clear();
        postData.put("meterIds", meterIds);
        String meterDomainListString = restTemplate.postForObject(Global.getConfig("getMeterAndUserByIds"), postData, String.class);
        List<MeterDomain> meterJsonList =
                JSONObject.parseArray(meterDomainListString, MeterDomain.class);
        // 欠费计量点信息完善 户号户名
        Map<Long, MeterDomain> meterDomainMap = meterJsonList.stream()
                .collect(Collectors.toMap(MeterDomain::getId, a -> a, (k1, k2) -> k1));




        List<ArrearageDetailDomain> returnArrearageDetails = arrearageDetailDomains.stream().map(t -> {
            // 循环加入各个计量点的违约金 暂定违约金都是是欠费的千分之一
			/*if (t.getOweMoney().compareTo(BigDecimal.ZERO) > 0) {
				BigDecimal punishMoney = t.getOweMoney().divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
				//t.setPunishMoney(punishMoney);
				t.setPunishMoney(BigDecimal.ZERO);
			}*/
            Integer invoiceType = 0;
            if (settlementDomainMap.get(t.getSettlementId()).getInvoiceType() != null && settlementDomainMap.get(t.getSettlementId()).getInvoiceType() == 2) {
                invoiceType = 1;
            }
            t.setIsAddTax(invoiceType);
            if(meterDomainMap.get(t.getMeterId())!=null){
                t.setUserNo(meterDomainMap.get(t.getMeterId()).getUserNo());
                t.setUserName(meterDomainMap.get(t.getMeterId()).getUserName());
                t.setMeterName(meterDomainMap.get(t.getMeterId()).getMeterName());
            }else if(settlementDomainMap.get(t.getSettlementId())!=null){
                t.setUserNo(settlementDomainMap.get(t.getSettlementId()).getSettlementNo());
                t.setUserName(settlementDomainMap.get(t.getSettlementId()).getSettlementName());
                t.setMeterName("");
            }
            // 更新欠费表违约金
            return t;
        }).collect(Collectors.toList());


        List<Long> settlementIds =
                settlementDomains.stream().map(SettlementDomain::getId).distinct().collect(toList());

        if (returnArrearageDetails != null && returnArrearageDetails.size() > 0) {
            settlementIds =
                    returnArrearageDetails.stream().map(ArrearageDetailDomain::getSettlementId).distinct().collect(toList());
        }

        //Long settlenmentId = settlementDomains.get(0).getId();
        List<PreChargeDomain> preChargeDomains = iPreChargeService.findPreChargeBySettleIds(settlementIds);
        BigDecimal balance = BigDecimal.ZERO;
        if (null != preChargeDomains && preChargeDomains.size() > 0) {
            balance =
                    preChargeDomains.stream().filter(t -> t.getBalance() != null)
                            .map(PreChargeDomain::getBalance).reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        // 构造返回map 赋值queryFlag 前台区分是无欠费
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("arrearageDetails", returnArrearageDetails);
        returnMap.put("queryFlag", true);
        returnMap.put("lastBalance", balance);
        returnMap.put("settement", settlementDomains.get(0));
        return new HttpResult(HttpResult.SUCCESS, null, returnMap);
    }

    @RequestMapping("findArrearageDetailByWhere")
    @ResponseBody
    public List<ArrearageDetailDomain> findArrearageDetailByWhere(@RequestBody ArrearageDomain arrearageDomain) {
        return iArrearageService.findArrearageDetailByWhere(arrearageDomain);
    }

    @RequestMapping("findArrearageByMeterIds")
    @ResponseBody
    public List<ArrearageDomain> findArrearageByMeterIds(@RequestBody String arrearageDomainJson) {
        List<Long> meterIds = JSONObject.parseArray(arrearageDomainJson,
                Long.class);
        String arrearageDomainString = meterIds.stream().map(String::valueOf).collect(Collectors.joining(","));
        return iArrearageService.findArrearageByMeterIds(arrearageDomainString);
    }

    @RequestMapping("findBySettleIdMonAndSn")
    @ResponseBody
    public List<ArrearageDomain> findBySettleIdMonAndSn(@RequestBody String arrearageDomainJson) {
        ArrearageDomain arrearageDomain = JSONObject.parseObject(arrearageDomainJson,
                ArrearageDomain.class);
        return iArrearageService.findBySettleIdMonAndSn(arrearageDomain);
    }


    // 客户欠费明细查询
    @ResponseBody
    @RequestMapping(value = "/arrearageDetailQuery")
    public Object arrearageDetailQuery(@RequestBody ArrearageDomain paramarrearage) throws Exception {

        JSONObject jsonObject = new JSONObject();

        if (paramarrearage.getBusinessPlaceCode() != null) {

            jsonObject.put("id", paramarrearage.getBusinessPlaceCode());

            HttpResult<List<DeptDomain>> paramDeptHttpResult =
                    titanTemplate.postJson("AUTH-CENTER", "auth/dept/tree", null, jsonObject,
                            new TypeReference<HttpResult<List<DeptDomain>>>() {
                            });

            List<DeptDomain> paramDeptList =
                    paramDeptHttpResult.getResultData();

            if (paramDeptList.size() > 0) {
                paramarrearage.setBusinessPlaceCodes(paramDeptList.stream().map(DeptDomain::getId).collect(Collectors.toList()));
                paramarrearage.getBusinessPlaceCodes().add(paramarrearage.getBusinessPlaceCode());
            }
        }
        //writesectionId转成writesectid 查询条件 -》基础数据
        paramarrearage.setIsSettle(0);
        List<ArrearageDomain> arrearageDomainList = iArrearageService.findArrearageGroupBySettleAndMon(paramarrearage);

        paramarrearage.setPageSize(-1);
        List<ArrearageDomain> totalArrearageDomainList =
                iArrearageService.findArrearageGroupBySettleAndMon(paramarrearage);

        BigDecimal totalPunishMoney =
                totalArrearageDomainList.stream().map(ArrearageDomain::getPunishMoney).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalOweMoney = totalArrearageDomainList.stream().map(ArrearageDomain::getOweMoney).reduce(BigDecimal.ZERO, BigDecimal::add);

        if (arrearageDomainList == null || arrearageDomainList.size() < 1) {
            new HttpResultPagination<ArrearageDomain>(paramarrearage, new ArrayList<>());
        }

        //不为null 且不重复的结算户id
        List<Long> settlementIds =
                arrearageDomainList.stream().filter(t -> t.getSettlementId() != null)
                        .map(t -> t.getSettlementId()).distinct().collect(toList());

        //根据结算户id 查询结算户信息
        jsonObject.clear();
        jsonObject.put("settleIds", settlementIds);
        HttpResult<List<SettlementEntity>> settleResult =
                titanTemplate.postJson("CIM-SERVER",
                        "cimServer/cim_bill?method=findSettlementByIds", new HttpHeaders(), jsonObject,
                        new TypeReference<HttpResult<List<SettlementEntity>>>() {
                        });

        List<SettlementEntity> settlementDomains = settleResult.getResultData();


        //处理成key（结算户id） value（结算户信息）显示
        Map<Long, SettlementEntity> settlementDomainMap =
                settlementDomains.stream()
                        .collect(Collectors.toMap(SettlementEntity::getId, a -> a, (k1, k2) -> k2));

        //获取抄表区段集合
        List<Long> writeSectionIds = arrearageDomainList.stream().filter(t -> t.getWriteSectId() != null)
                .map(t -> t.getWriteSectId()).distinct().collect(toList());

        WriteSectDomain writeSectDomain = new WriteSectDomain();
        writeSectDomain.setPageSize(-1);
        writeSectDomain.setWriteSectionIds(writeSectionIds);
        String writeSectJson = restTemplate.postForObject(Global.getConfig("getWriteSectFindByWhere"),
                writeSectDomain, String.class);

        //抄表区段map
        Map<Long, WriteSectDomain> writeSectDomainMap = JSONUtil.readValue(writeSectJson,
                new TypeReference<List<WriteSectDomain>>() {
                }).stream().collect(Collectors.toMap(WriteSectDomain::getId, k -> k));

        //营业区域下拉
        DeptDomain deptDomain = new DeptDomain();

        jsonObject.clear();
        HttpResult<List<DeptDomain>> paramDeptHttpResult =
                titanTemplate.postJson("AUTH-CENTER", "auth/dept/tree", null, jsonObject,
                        new TypeReference<HttpResult<List<DeptDomain>>>() {
                        });

        List<DeptDomain> deptList =
                paramDeptHttpResult.getResultData();
        Map<Long, DeptDomain> deptIdMap =
                deptList.stream().collect(Collectors.toMap(DeptDomain::getId, a -> a, (k1, k2) -> k1));

        //获取下拉值
        Map<Long, String> chargeModeCommonConfigMap =
                sysCommonConfigService.getsystemCommonConfigMap("CHARGE_MODE");

        Map<Long, String> elecTypeConfigMap =
                sysCommonConfigService.getsystemCommonConfigMap("ELEC_TYPE");

        //批量获取计量点信息
        List<Long> meterIds =
                arrearageDomainList.stream().map(ArrearageDomain::getMeterId).distinct().collect(toList());

        HttpResult httpResult = restTemplate.postForObject(Global.getConfig("getMeterByMeterIdsWithoutStatus"), meterIds,
                HttpResult.class);

        List<MeterDomain> meterDomains = JSONObject.parseArray(JSONArray.toJSONString(httpResult.getResultData()),
                MeterDomain.class);

        Map<Long, MeterDomain> meterDomainMap = meterDomains.stream()
                .collect(Collectors.toMap(MeterDomain::getId, a -> a, (k1, k2) -> k1));


        for (ArrearageDomain arrearageDomain : arrearageDomainList) {
            SettlementEntity settlementEntity =
                    settlementDomainMap.get(arrearageDomain.getSettlementId());
            if (settlementEntity == null) {
                continue;
            }
            MeterDomain meterDomain =
                    meterDomainMap.get(arrearageDomain.getMeterId());
            arrearageDomain.setSettlementNo(settlementEntity.getSettlementNo());
            arrearageDomain.setSettlementName(settlementEntity.getSettlementName());
            arrearageDomain.setSetAddress(settlementEntity.getAddress());
            arrearageDomain.setWriteSectName(writeSectDomainMap.get(arrearageDomain.getWriteSectId()).getWriteSectName());
            arrearageDomain.setBusinessPlaceName(deptIdMap.get(arrearageDomain.getBusinessPlaceCode()).getDeptName());
            arrearageDomain.setChargeModeName(chargeModeCommonConfigMap.get(Long.valueOf(settlementEntity.getChargeModeType())));
            arrearageDomain.setMeterNo(meterDomain.getMeterNo());
            arrearageDomain.setElecTypeCode(meterDomain.getElecTypeCode());
            arrearageDomain.setElecTypeName(elecTypeConfigMap.get(Long.valueOf(meterDomain.getElecTypeCode())));
//            arrearageDomain.setPunishMoney(paramArrearageDomain.getPunishMoney());
        }
        arrearageDomainList =
                arrearageDomainList.stream().sorted(Comparator.comparing(ArrearageDomain::getWritorId, Comparator.nullsFirst(Long::compareTo))
                        .thenComparing(ArrearageDomain::getWriteSectId, Comparator.nullsFirst(Long::compareTo))
                        .thenComparing(ArrearageDomain::getSettlementId,
                                Comparator.nullsFirst(Long::compareTo))).collect(Collectors.toList());

        jsonObject.clear();
        jsonObject.put("totalPunishMoney", totalPunishMoney);
        jsonObject.put("totalOweMoney", totalOweMoney);
        jsonObject.put("data",
                new HttpResultPagination<ArrearageDomain>(paramarrearage, arrearageDomainList));
        return new HttpResult(HttpResult.SUCCESS, "查询成功", jsonObject);
    }

    // 客户欠费累计查询
    @ResponseBody
    @RequestMapping(value = "/ArrearageAccumulate")
    public HttpResultPagination arrearageAccumulate(@RequestBody ArrearageDomain arrearage) {
        List<ArrearageDomain> list = null;
        try {
            list = iArrearageService.arrearageAccumulate(arrearage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HttpResultPagination<>(arrearage, list);
    }

    // 用于report调用--通知单
    @ResponseBody
    @RequestMapping(value = "/ArrearageQuery")
    public Object arrearageQuery(@RequestBody ArrearageDomain arrearage) {
        List<ArrearageDomain> list = iArrearageService.arrearageDetailQuery(arrearage);
        return list;
    }

    // 用于report调用--欠费汇总
    @ResponseBody
    @RequestMapping(value = "/ArrearageQuerySum")
    public Object arrearageQuerySum(@RequestBody ArrearageDomain arrearage) {
        List<ArrearageDomain> list = iArrearageService.findArrearageQuerySumByWhere(arrearage);
        return list;
    }

    //托收单打印调用方法
    @ResponseBody
    @RequestMapping(value = "/findArrearageBySettleIds")
    public Object findArrearageBySettleIds(@RequestBody String settlementIdsJson) {
        List<Long> settlementIds = JSONObject.parseArray(settlementIdsJson, Long.class);
        List<ArrearageDomain> list =
                iArrearageService.findArrearageBySettleIds(settlementIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
        return list;
    }


    //结算户汇总欠费 report打印托收单
    @ResponseBody
    @RequestMapping(value = "/findArrearageGroupbySettleId")
    public List<BankCollectionEntity> findArrearageGroupbySettleId(@RequestBody String arrearageJson) {
        ArrearageDomain arrearageDomain = JSONObject.parseObject(arrearageJson,
                ArrearageDomain.class);
        List<BankCollectionEntity> list =
                iArrearageService.findArrearageGroupbySettleId(arrearageDomain);
        return list;
    }

    //打印托收单查询
    @ResponseBody
    @RequestMapping(value = "/findBankCollectArrearage")
    public HttpResult<?> findBankCollectArrearage(@RequestBody String settlementIdsJson) throws IOException {

        ArrearageDomain paramArrearageDomain = new ArrearageDomain();
        ArrearageDomain paramArrearageNoPage = new ArrearageDomain();
        //分页实体
        paramArrearageDomain = JSONObject.parseObject(settlementIdsJson,
                ArrearageDomain.class);
        //页面合计实体
        paramArrearageNoPage = JSONObject.parseObject(settlementIdsJson,
                ArrearageDomain.class);

        List<BankCollectionEntity> bankCollectionEntities = new ArrayList<>();

        List<BankCollectionEntity> bankCollectionEntitiesNoPage = new ArrayList<>();

        //托收结算户
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("chargeModeType", 5);
        if (paramArrearageDomain.getBusinessPlaceCode() != null) {
            jsonObject.put("businessPlaceCode",
                    paramArrearageDomain.getBusinessPlaceCode());
        }
        if (paramArrearageDomain.getBankNo() != null && !"".equals(paramArrearageDomain.getBankNo().trim())) {
            jsonObject.put("bankNo",
                    paramArrearageDomain.getBankNo());
        }
        //符合要求的结算户记录
        String settlementJson = restTemplate.postForObject
                (Global.getConfig("findBankSettlement"), jsonObject, String.class);

        List<SettlementEntity> settlementDomainList =
                JSONObject.parseArray(settlementJson, SettlementEntity.class);

        List<Long> settlementIds =
                settlementDomainList.stream().map(SettlementEntity::getId).distinct().collect(Collectors.toList());

        if (settlementDomainList == null || settlementDomainList.size() < 1) {
            jsonObject.clear();
            jsonObject.put("data",
                    new HttpResultPagination(paramArrearageDomain, new ArrayList()));
            return new HttpResult<>(HttpResult.SUCCESS, "无银行托收结算户信息", jsonObject);
        }

        ChargeDomain chargeInfoDomain = new ChargeDomain();
        chargeInfoDomain.setfChargeMode(4);
        chargeInfoDomain.setMon(paramArrearageDomain.getMon());
        chargeInfoDomain.setSettlementIds(settlementIds);
        List<ChargeDomain> lastBalanceChargeDomains =
                iChargeService.findByWhere(chargeInfoDomain);

        Map<Long, BigDecimal> lastBalanceChargeInfoMap =
                lastBalanceChargeDomains.stream().filter(t -> t.getFactMoney() != null)
                        .collect(Collectors.groupingBy(ChargeDomain::getSettlementId, CustomCollectors.summingBigDecimal(ChargeDomain::getFactMoney)));



        Map<Long, SettlementEntity> settementMap = settlementDomainList.stream().collect(Collectors.toMap(SettlementEntity::getId, k -> k));

        //已打时 不用限制欠费
        /*if(paramArrearageDomain.getIsPrint()!=null && paramArrearageDomain.getIsPrint()==1){
            paramArrearageDomain.setIsSettle(null);
            paramArrearageNoPage.setIsSettle(null);
        }*/
        paramArrearageDomain.setBusinessPlaceCode(null);
        paramArrearageDomain.setSettlementIds(settlementIds);
        bankCollectionEntities = iArrearageService.findArrearageGroupbySettleId(paramArrearageDomain);

        //paramArrearageNoPage.setIsSettle(0);
        paramArrearageNoPage.setBusinessPlaceCode(null);
        paramArrearageNoPage.setSettlementIds(settlementIds);
        paramArrearageNoPage.setPageSize(-1);
        bankCollectionEntitiesNoPage = iArrearageService.findArrearageGroupbySettleId(paramArrearageNoPage);


        if (bankCollectionEntities == null || bankCollectionEntities.size() < 1) {
            jsonObject.clear();
            jsonObject.put("data",
                    new HttpResultPagination(paramArrearageDomain, new ArrayList()));
            return new HttpResult<>(HttpResult.SUCCESS, "无该条件的欠费记录", jsonObject);
        }

        List<Long> writeSectIds =
                bankCollectionEntities.stream().map(BankCollectionEntity::getWriteSectId).collect(Collectors.toList());

        //抄表区段
        WriteSectDomain ws = new WriteSectDomain();
        int mon = Integer.valueOf(Global.getConfig("mon"));
        if (paramArrearageDomain.getMon() > mon) {
            mon = paramArrearageDomain.getMon();
        }
        ws.setMon(String.valueOf(mon));
        ws.setWriteSectionIds(writeSectIds);
        List<WriteSectDomain> writeSectDomains = writeSectService.getWriteSectDomain(ws);
        Map<Long, WriteSectDomain> writeSectDomainMap =
                writeSectDomains.stream().collect(Collectors.toMap(WriteSectDomain::getId, a -> a, (k1, k2) -> k1));

        //开户银行
        BankEntity bankEntity = new BankEntity();
        bankEntity.setPageSize(-1);
        String bankString = restTemplate.postForObject(Global.getConfig(
                "getBank"), JSONObject.toJSONString(bankEntity), String.class);
        List<BankEntity> bankArray =
                JSONObject.parseArray(JSONObject.parseObject(bankString).getString("list"), BankEntity.class);
        Map<Long, BankEntity> bankEntityMap =
                bankArray.stream().collect(Collectors.toMap(BankEntity::getId, a -> a, (k1, k2) -> k1));


        bankCollectionEntities.forEach(t -> {
            BigDecimal oweMoney=t.getOweMoney();
            if(lastBalanceChargeInfoMap.get(t.getSettlementId())!=null){
                oweMoney=
                        oweMoney.subtract(lastBalanceChargeInfoMap.get(t.getSettlementId()));
            }

            t.setOweMoney(oweMoney);
            t.setSettlementNo(settementMap.get(t.getSettlementId()).getSettlementNo());
            t.setSettlementName(settementMap.get(t.getSettlementId()).getSettlementName());
            t.setBankNo(settementMap.get(t.getSettlementId()).getBankNo());
            Long opendingBank =
                    settementMap.get(t.getSettlementId()).getOpendingBank();
            t.setOpendingBank(bankEntityMap.get(opendingBank).getBankName());
            t.setCollectTotalMoney(t.getPunishMoney().add(t.getOweMoney()));
            t.setWriteSectNo(writeSectDomainMap.get(t.getWriteSectId()).getWriteSectNo());
            t.setIds(t.getIds());
        });

        BigDecimal oweMoneySum =
                bankCollectionEntitiesNoPage.stream().map(BankCollectionEntity::getOweMoney).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal owePunishMoneySum =
                bankCollectionEntitiesNoPage.stream().map(BankCollectionEntity::getPunishMoney).reduce(BigDecimal.ZERO, BigDecimal::add);

        jsonObject.clear();
        jsonObject.put("totalMoney",
                oweMoneySum.add(owePunishMoneySum).setScale(2, RoundingMode.HALF_UP));
        jsonObject.put("data", new HttpResultPagination(paramArrearageDomain, bankCollectionEntities));
        return new HttpResult<>(HttpResult.SUCCESS, "查询成功", jsonObject);
    }

    @ResponseBody
    @RequestMapping(value = "/findArrearageByCustomer")
    // 历史欠费记录查询
    public JSONObject findArrearageByCustomer(@RequestBody String customer) {
        CustomerDomain customerDomain = GsonUtils.readValue(customer, CustomerDomain.class);
        List<ArrearageDomain> list = iArrearageService.findArrearageByCustomer(customerDomain);


        JSONObject jsonObject = new JSONObject();
        BigDecimal sumOweMoney = BigDecimal.ZERO;
        if (list != null && list.size() > 0) {
            sumOweMoney =
                    list.stream().filter(t -> t.getOweMoney() != null)
                            .map(ArrearageDomain::getOweMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        jsonObject.put("data", new HttpResultPagination<>(customerDomain, list));
        jsonObject.put("sumOweMoney", sumOweMoney);
        return jsonObject;

    }

    // 短信服务查询使用
    @ResponseBody
    @PostMapping(value = "/findByIsSettleMonAndIsSend")
    public List<ArrearageDomain> findByIsSettleMonAndIsSend(@RequestBody String arrearageDomain) {
        Map hashMap = GsonUtils.readValue(arrearageDomain, HashMap.class);
        ArrearageDomain arrearage = GsonUtils.readValue(GsonUtils.toJson(hashMap.get("arrearage")),
                ArrearageDomain.class);
        return iArrearageService.findByIsSettleMonAndIsSend(arrearage);
    }

    // 呆坏账
    @ResponseBody
    @RequestMapping(value = "/badArrearageDetailQuery")
    public Object badArrearageDetailQuery(@RequestBody ArrearageDomain paramarrearage) throws Exception {

        JSONObject jsonObject = new JSONObject();

        paramarrearage.setIsSettle(null);

        if (paramarrearage.getBusinessPlaceCode() != null) {

            jsonObject.put("id", paramarrearage.getBusinessPlaceCode());

            HttpResult<List<DeptDomain>> paramDeptHttpResult =
                    titanTemplate.postJson("AUTH-CENTER", "auth/dept/tree", null, jsonObject,
                            new TypeReference<HttpResult<List<DeptDomain>>>() {
                            });

            List<DeptDomain> paramDeptList =
                    paramDeptHttpResult.getResultData();

            if (paramDeptList.size() > 0) {
                paramarrearage.setBusinessPlaceCodes(paramDeptList.stream().map(DeptDomain::getId).collect(Collectors.toList()));
                paramarrearage.getBusinessPlaceCodes().add(paramarrearage.getBusinessPlaceCode());
            }
        }
        //结算户
        if (paramarrearage.getSettlementNo() != null) {
            {
                jsonObject.clear();
                jsonObject.put("settlementNo", paramarrearage.getSettlementNo());
                String settlementJson = restTemplate.postForObject(Global.getConfig("getSettlementByNo"), jsonObject, String.class);
                List<SettlementEntity> settementArray = JSONObject
                        .parseArray(JSONObject.parseObject(settlementJson).getString("list"), SettlementEntity.class);
                if (settementArray == null || settementArray.size() < 1) {
                    return new HttpResultPagination<ArrearageDomain>(paramarrearage, new ArrayList<>());
                }
                List<Long> settlementIds =
                        settementArray.stream().map(SettlementEntity::getId).distinct().collect(toList());
                paramarrearage.setSettlementIds(settlementIds);
            }

        }
        //writesectionId转成writesectid 查询条件 -》基础数据
        List<ArrearageDomain> arrearageDomainList =
                iArrearageService.findByWhere(paramarrearage);

        if (arrearageDomainList == null || arrearageDomainList.size() < 1) {
            new HttpResultPagination<ArrearageDomain>(paramarrearage, new ArrayList<>());
        }

        //不为null 且不重复的结算户id
        List<Long> settlementIds =
                arrearageDomainList.stream().filter(t -> t.getSettlementId() != null)
                        .map(t -> t.getSettlementId()).distinct().collect(toList());

        //根据结算户id 查询结算户信息
        jsonObject.clear();
        jsonObject.put("settleIds", settlementIds);
        HttpResult<List<SettlementEntity>> settleResult =
                titanTemplate.postJson("CIM-SERVER",
                        "cimServer/cim_bill?method=findSettlementByIds", new HttpHeaders(), jsonObject,
                        new TypeReference<HttpResult<List<SettlementEntity>>>() {
                        });

        List<SettlementEntity> settlementDomains = settleResult.getResultData();


        //处理成key（结算户id） value（结算户信息）显示
        Map<Long, SettlementEntity> settlementDomainMap =
                settlementDomains.stream()
                        .collect(Collectors.toMap(SettlementEntity::getId, a -> a, (k1, k2) -> k2));

        //获取抄表区段集合
        List<Long> writeSectionIds = arrearageDomainList.stream().filter(t -> t.getWriteSectId() != null)
                .map(t -> t.getWriteSectId()).distinct().collect(toList());

        WriteSectDomain writeSectDomain = new WriteSectDomain();
        writeSectDomain.setPageSize(-1);
        writeSectDomain.setWriteSectionIds(writeSectionIds);
        String writeSectJson = restTemplate.postForObject(Global.getConfig("getWriteSectFindByWhere"),
                writeSectDomain, String.class);

        //抄表区段map
        Map<Long, WriteSectDomain> writeSectDomainMap = JSONUtil.readValue(writeSectJson,
                new TypeReference<List<WriteSectDomain>>() {
                }).stream().collect(Collectors.toMap(WriteSectDomain::getId, k -> k));

        //营业区域下拉
        DeptDomain deptDomain = new DeptDomain();

        jsonObject.clear();
        HttpResult<List<DeptDomain>> paramDeptHttpResult =
                titanTemplate.postJson("AUTH-CENTER", "auth/dept/tree", null, jsonObject,
                        new TypeReference<HttpResult<List<DeptDomain>>>() {
                        });

        List<DeptDomain> deptList =
                paramDeptHttpResult.getResultData();
        Map<Long, DeptDomain> deptIdMap =
                deptList.stream().collect(Collectors.toMap(DeptDomain::getId, a -> a, (k1, k2) -> k1));

        //获取下拉值
        Map<Long, String> chargeModeCommonConfigMap =
                sysCommonConfigService.getsystemCommonConfigMap("CHARGE_MODE");

        Map<Long, String> elecTypeConfigMap =
                sysCommonConfigService.getsystemCommonConfigMap("ELEC_TYPE");

        //批量获取计量点信息
        List<Long> meterIds =
                arrearageDomainList.stream().map(ArrearageDomain::getMeterId).distinct().collect(toList());

        if(meterIds==null || meterIds.size()<1){
            return new HttpResultPagination<>(paramarrearage,new ArrayList<>());
        }

        HttpResult httpResult = restTemplate.postForObject(Global.getConfig("getMeterByMeterIdsWithoutStatus"), meterIds,
                HttpResult.class);

        List<MeterDomain> meterDomains = JSONObject.parseArray(JSONArray.toJSONString(httpResult.getResultData()),
                MeterDomain.class);

        Map<Long, MeterDomain> meterDomainMap = meterDomains.stream()
                .collect(Collectors.toMap(MeterDomain::getId, a -> a, (k1, k2) -> k1));


        for (ArrearageDomain arrearageDomain : arrearageDomainList) {
            SettlementEntity settlementEntity =
                    settlementDomainMap.get(arrearageDomain.getSettlementId());
            MeterDomain meterDomain =
                    meterDomainMap.get(arrearageDomain.getMeterId());
            arrearageDomain.setSettlementNo(settlementEntity.getSettlementNo());
            arrearageDomain.setSettlementName(settlementEntity.getSettlementName());
            arrearageDomain.setSetAddress(settlementEntity.getAddress());
            arrearageDomain.setWriteSectName(writeSectDomainMap.get(arrearageDomain.getWriteSectId()).getWriteSectName());
            arrearageDomain.setBusinessPlaceName(deptIdMap.get(arrearageDomain.getBusinessPlaceCode()).getDeptName());
            arrearageDomain.setChargeModeName(chargeModeCommonConfigMap.get(Long.valueOf(settlementEntity.getChargeModeType())));
            arrearageDomain.setMeterNo(meterDomain.getMeterNo());
            arrearageDomain.setElecTypeCode(meterDomain.getElecTypeCode());
            arrearageDomain.setElecTypeName(elecTypeConfigMap.get(Long.valueOf(meterDomain.getElecTypeCode())));
//            arrearageDomain.setPunishMoney(paramArrearageDomain.getPunishMoney());
        }
        return new HttpResultPagination<ArrearageDomain>(paramarrearage, arrearageDomainList);
    }

    //催费通知单
    @ResponseBody
    @RequestMapping(value = "/reminderNotice")
    public HttpResultPagination reminderNotice(@RequestBody ArrearageDomain arrearage) {
        List<ArrearageDomain> list = null;
        try {
            list = iArrearageService.reminderNotice(arrearage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HttpResultPagination<>(arrearage, list);
    }


    // 短信服务修改使用
    @ResponseBody
    @PostMapping(value = "/updateSendBySettlementIdsAndMon")
    public int updateSendBySettlementIdsAndMon(@RequestBody String arrearageDomain) {
        Map hashMap = GsonUtils.readValue(arrearageDomain, HashMap.class);
        ArrearageDomain arrearage = GsonUtils.readValue(GsonUtils.toJson(hashMap.get("arrearage")),
                ArrearageDomain.class);
        return iArrearageService.updateSendBySettlementIdsAndMon(arrearage);
    }

    //电量电费排行
    @ResponseBody
    @PostMapping(value = "/electricityTariffRankQuery")
    public List<ArrearageDomain> electricityTariffRankQuery(@RequestBody String json) {
        ElectricityTariffRankEntity electricityTariffRankEntity = GsonUtils.readValue(json,  ElectricityTariffRankEntity.class);
        return iArrearageService.electricityTariffRankQuery(electricityTariffRankEntity);
    }
}
