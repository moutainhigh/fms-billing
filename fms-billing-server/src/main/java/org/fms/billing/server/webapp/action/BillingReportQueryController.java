package org.fms.billing.server.webapp.action;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.fms.billing.common.util.FormatterUtil;
import org.fms.billing.common.webapp.domain.DeptDomain;
import org.fms.billing.common.webapp.domain.WriteSectDomain;
import org.fms.billing.common.webapp.domain.mongo.UserMongoDomain;
import org.fms.billing.common.webapp.entity.LineEntity;
import org.fms.billing.common.webapp.entity.MapEntity;
import org.fms.billing.common.webapp.entity.SettlementNumEdntity;
import org.fms.billing.common.webapp.entity.SubsEntity;
import org.fms.billing.common.webapp.entity.SubsLineRelaEntity;
import org.fms.billing.common.webapp.entity.TransformerAssetsEntity;
import org.fms.billing.common.webapp.entity.TransformerEntity;
import org.fms.billing.common.webapp.entity.TransformerLineRelEntity;
import org.fms.billing.common.webapp.entity.UserInfoEntity;
import org.fms.billing.server.webapp.dao.TransformerMeterRelDAO;
import org.fms.billing.server.webapp.dao.UserMongoDAO;
import org.fms.billing.server.webapp.dao.WriteSectDAO;
import org.fms.billing.server.webapp.service.CimService;
import org.fms.billing.server.webapp.service.DeptService;
import org.fms.billing.server.webapp.service.SysCommonConfigService;
import org.fms.billing.server.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.riozenc.titanTool.mybatis.pagination.Page;
import com.riozenc.titanTool.spring.web.http.HttpResult;
import com.riozenc.titanTool.spring.web.http.HttpResultPagination;

@Controller
@RequestMapping("billingReportQuery")
public class BillingReportQueryController {
    @Autowired
    private CimService cimService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private UserMongoDAO userMongoDAO;

    @Autowired
    private WriteSectDAO writeSectDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private TransformerMeterRelDAO transformerMeterRelDAO;

    @Autowired
    private SysCommonConfigService sysCommonConfigService;

    //用户数量查询（按变压器）
    @RequestMapping("qureySettleNumByTransform")
    @ResponseBody
    public HttpResult qureySettleNumByTransform(@RequestBody String paramJson) {
        JSONObject paramJSONObject = JSONObject.parseObject(paramJson);
        Integer mon = paramJSONObject.getInteger("mon");
        Long businessPlaceCode = paramJSONObject.getLong("businessPlaceCode");

        DeptDomain dept =
                deptService.getDept(businessPlaceCode);
        List<DeptDomain> deptDomains = deptService.getDeptList(businessPlaceCode);
        deptDomains.add(dept);

        Map<Long, DeptDomain> deptDomainMap =
                deptDomains.stream().collect(Collectors.toMap(DeptDomain::getId, a -> a, (k1, k2) -> k1));

        List<Long> deptIds =
                deptDomains.stream().map(DeptDomain::getId).distinct().collect(Collectors.toList());

        //获取变电站信息
        SubsEntity paramSubsEntity = new SubsEntity();
        paramSubsEntity.setBusinessPlaceCodes(deptIds);
        paramSubsEntity.setPageSize(-1);
        List<SubsEntity> subsEntities = cimService.getSubs(paramSubsEntity);

        if (subsEntities == null || subsEntities.size() < 1) {
            return new HttpResult(HttpResult.ERROR, "所选按营业区域没有变电站");
        }

        Map<Long, SubsEntity> subsEntityMap =
                subsEntities.stream().collect(Collectors.toMap(SubsEntity::getId,
                        a -> a, (k1, k2) -> k1));

        //变电站id
        List<Long> subsIds =
                subsEntities.stream().map(SubsEntity::getId).distinct().collect(Collectors.toList());

        //变电站线路关系
        SubsLineRelaEntity paramSubsLineRela = new SubsLineRelaEntity();
        paramSubsLineRela.setPageSize(-1);
        paramSubsLineRela.setBeginSubsIds(subsIds);
        List<SubsLineRelaEntity> subsLineRelaEntities =
                cimService.getsubsLineRela(paramSubsLineRela);
        if (subsLineRelaEntities == null || subsLineRelaEntities.size() < 1) {
            return new HttpResult(HttpResult.ERROR, "所选按营业区域的变电站没有所属线路");
        }
        //线路id
        List<Long> lineIds =
                subsLineRelaEntities.stream().filter(t -> t.getLineId() != null).map(SubsLineRelaEntity::getLineId).distinct().collect(Collectors.toList());

        Map<Long, SubsLineRelaEntity> subsLineRelaEntityMap =
                subsLineRelaEntities.stream().collect(Collectors.toMap(SubsLineRelaEntity::getLineId, a -> a, (k1, k2) -> k1));


        //获取线路
        LineEntity paramLineDomain = new LineEntity();
        paramLineDomain.setLineIds(lineIds);
        List<LineEntity> lineDomains =
                cimService.findLineByLineIds(paramLineDomain);
        Map<Long, LineEntity> lineEntityMap = lineDomains.stream().collect(Collectors.toMap(LineEntity::getId,
                a -> a, (k1, k2) -> k1));

        //获取线路变压器关系
        TransformerLineRelEntity paramTransformerLineRel =
                new TransformerLineRelEntity();
        paramTransformerLineRel.setPageSize(-1);
        paramTransformerLineRel.setLineIds(lineIds);
        List<TransformerLineRelEntity> transformerLineRelEntities =
                cimService.findLineTransRelByLineIds(paramTransformerLineRel);
        if (transformerLineRelEntities == null || transformerLineRelEntities.size() < 1) {
            return new HttpResult(HttpResult.ERROR, "所选线路底下没有变压器信息");
        }
        //变压器id集合
        List<Long> transformerIds =
                transformerLineRelEntities.stream().filter(t -> t.getTransformerId() != null).map(TransformerLineRelEntity::getTransformerId).distinct().collect(Collectors.toList());

        Map<Long, TransformerLineRelEntity> transformerLineRelEntityMap =
                transformerLineRelEntities.stream().collect(Collectors.toMap(TransformerLineRelEntity::getTransformerId, a -> a, (k1, k2) -> k1));

        TransformerEntity paramTransformerEntity = new TransformerEntity();
        paramTransformerEntity.setTransformerIds(transformerIds);
        paramTransformerEntity.setPageSize(paramJSONObject.getInteger("pageSize"));
        paramTransformerEntity.setPageCurrent(paramJSONObject.getInteger("pageCurrent"));
        HttpResultPagination<TransformerEntity> transformerEntityPage=
                cimService.getAvaTransformerByWhere(paramTransformerEntity);
        List<TransformerEntity> transformerEntities =transformerEntityPage.getList();
        if (transformerEntities == null || transformerEntities.size() < 1) {
            return new HttpResult(HttpResult.ERROR, "所选线路底下没有变压器档案");
        }
        List<Long> transformerIdsOfPage =
                transformerEntities.stream().filter(t -> t.getId() != null).map(TransformerEntity::getId).distinct().collect(Collectors.toList());


        //变压器map
        Map<Long, TransformerEntity> transformerEntityMap =
                transformerEntities.stream().collect(Collectors.toMap(TransformerEntity::getId, a -> a, (k1, k2) -> k1));

        Map<Long, TransformerAssetsEntity> transformerAssetsEntityMap =null;
        if(transformerEntities!=null &&transformerEntities.size()>0){
            List<Long> transformerAssetsIds=transformerEntities.stream().filter(t->t.getTransformerAssetsId()!=null).map(TransformerEntity::getTransformerAssetsId).collect(Collectors.toList());
            TransformerAssetsEntity transformerAssetsEntity=
                    new TransformerAssetsEntity();

            transformerAssetsEntity.setIds(transformerAssetsIds);
            transformerAssetsEntity.setPageSize(-1);

            List<TransformerAssetsEntity> transformerAssetsEntities =
                    cimService.getTransformerAssetsByWhere(transformerAssetsEntity);

            if(transformerAssetsEntities!=null){
                transformerAssetsEntityMap=
                        transformerAssetsEntities.stream().collect(Collectors.toMap(TransformerAssetsEntity::getId, a -> a, (k1, k2) -> k1));
            }
        }

        //变压器计量点关系
        String findNumGroupByTransformIdJson =null;
        if(paramJSONObject.getInteger("pageSize").intValue()==-1){
            findNumGroupByTransformIdJson=
                    transformerMeterRelDAO.findNumGroupByTransformId(mon.toString(),null);
        }else{
            findNumGroupByTransformIdJson=
                    transformerMeterRelDAO.findNumGroupByTransformId(mon.toString(),transformerIdsOfPage);
        }
        List<JSONObject> findNumGroupByTransformIdEntities =
                JSONObject.parseArray(findNumGroupByTransformIdJson,  JSONObject.class);

        if (findNumGroupByTransformIdEntities == null || findNumGroupByTransformIdEntities.size() < 1) {
            return new HttpResult(HttpResult.ERROR, "无所选月份计量点变压器关系档案");
        }


        //返回值赋值
        List<SettlementNumEdntity> settlementNumEdntities = new ArrayList<>();

        for (JSONObject t : findNumGroupByTransformIdEntities) {
            SettlementNumEdntity settlementNumEdntity =
                    new SettlementNumEdntity();
            settlementNumEdntity.setTransformerId(t.getLong("_id"));
            settlementNumEdntity.setMeterNum(t.getLong("sumNum"));
            settlementNumEdntities.add(settlementNumEdntity);
        };

        //加入无计量点的变压器记录
        List<Long> settlementNumTransformerIds=
                settlementNumEdntities.stream().map(SettlementNumEdntity::getTransformerId).distinct().collect(Collectors.toList());

        for (Long t:transformerIdsOfPage){
            if(!settlementNumTransformerIds.contains(t)){
                SettlementNumEdntity settlementNumEdntity =
                        new SettlementNumEdntity();
                settlementNumEdntity.setTransformerId(t);
                settlementNumEdntity.setMeterNum(0);
                settlementNumEdntities.add(settlementNumEdntity);
            }
        }

        if (settlementNumEdntities == null || settlementNumEdntities.size() < 1) {
            return new HttpResult(HttpResult.ERROR, "变压器在" + mon + "月底下没有计量点");
        }

        Map<Long, String> isPubTransMap =
                sysCommonConfigService.getsystemCommonConfigMap("IS_PUB_TRANS");


        Map<Long, String> transformerModelMap =
                sysCommonConfigService.getsystemCommonConfigMap("TRANSFORMER_MODEL_TYPE");

        for (SettlementNumEdntity settlementNumEdntity : settlementNumEdntities) {
            Long key = settlementNumEdntity.getTransformerId();

            TransformerEntity transformerEntity = transformerEntityMap.get(key);

            if(transformerEntity==null){
                continue;
            }


            settlementNumEdntity.setCapacity(transformerEntity.getCapacity());

            if(transformerAssetsEntityMap!=null && transformerAssetsEntityMap.get(transformerEntity.getTransformerAssetsId())!=null){

                settlementNumEdntity.setTransformerModelTypeName(transformerModelMap.get(transformerAssetsEntityMap.get(transformerEntity.getTransformerAssetsId()).getTransformerModelType()));
            }
            settlementNumEdntity.setIsPubTypeName(transformerEntity.getIsPubType()==null
                    ?"":isPubTransMap.get(Long.valueOf(transformerEntity.getIsPubType())));

            settlementNumEdntity.setMon(mon);
            settlementNumEdntity.setTransformerNo(transformerEntity.getTransformerNo());
            settlementNumEdntity.setTransformerName(transformerEntity.getDeskName());

            TransformerLineRelEntity transformerLineRelEntity = transformerLineRelEntityMap.get(key);

            settlementNumEdntity.setLineId(transformerLineRelEntity.getLineId());
            settlementNumEdntity.setLineName(lineEntityMap.get(settlementNumEdntity.getLineId()).getLineName());

            SubsLineRelaEntity subsLineRelaEntity =
                    subsLineRelaEntityMap.get(settlementNumEdntity.getLineId());

            settlementNumEdntity.setSubsId(subsLineRelaEntity.getBeginSubsId());

            SubsEntity subsEntity =
                    subsEntityMap.get(settlementNumEdntity.getSubsId());

            settlementNumEdntity.setSubsName(subsEntity.getSubsName());
            settlementNumEdntity.setSubsNo(subsEntity.getSubsNo());

            settlementNumEdntity.setBusinessPlaceCode(subsEntity.getBusinessPlaceCode());
            DeptDomain deptDomain =
                    deptDomainMap.get(subsEntity.getBusinessPlaceCode());
            if (deptDomain != null) {
                settlementNumEdntity.setBusinessPlaceCodeName(deptDomain.getDeptName());
            }

        }
        settlementNumEdntities =
                settlementNumEdntities.stream().sorted(Comparator.comparing(SettlementNumEdntity::getBusinessPlaceCode, Comparator.nullsFirst(Long::compareTo))
                        .thenComparing(SettlementNumEdntity::getSubsNo,
                                Comparator.nullsFirst(String::compareTo))
                        .thenComparing(SettlementNumEdntity::getLineName,
                                Comparator.nullsFirst(String::compareTo))
                        .thenComparing(SettlementNumEdntity::getTransformerNo,
                                Comparator.nullsFirst(String::compareTo))).collect(Collectors.toList());
        //构造分页参数
        Page page=new Page();
        page.setPageCurrent(transformerEntityPage.getPageCurrent());
        page.setPageSize(paramJSONObject.getInteger("pageSize"));
        page.setTotalRow(transformerEntityPage.getTotalRow());
        return new HttpResult(HttpResult.SUCCESS,"查询成功",new HttpResultPagination(page,settlementNumEdntities)) ;

    }

    //用户数量查询（按抄表区段）
    @RequestMapping("qureyUserNumByWriteSectId")
    @ResponseBody
    public HttpResultPagination qureyUserNumByTransform(@RequestBody String paramJson) {
        JSONObject paramJSONObject = JSONObject.parseObject(paramJson);
        Integer mon = paramJSONObject.getInteger("mon");
        Long businessPlaceCode = paramJSONObject.getLong("businessPlaceCode");
        Integer pageSize = paramJSONObject.getInteger("pageSize");
        Integer pageCurrent = paramJSONObject.getInteger("pageCurrent");

        DeptDomain dept =
                deptService.getDept(businessPlaceCode);
        List<DeptDomain> deptDomains = deptService.getDeptList(businessPlaceCode);
        deptDomains.add(dept);

        List<Long> businessPlaceCodes=
                deptDomains.stream().map(DeptDomain::getId).collect(Collectors.toList());
        UserMongoDomain userMongoDomain=new UserMongoDomain();
        userMongoDomain.setMon(mon.toString());
        userMongoDomain.setBusinessPlaceCodes(businessPlaceCodes);

        String findNumGroupByWriteSectdJson=
                userMongoDAO.findNumGroupByWriteSectId(userMongoDomain);

        List<JSONObject> findNumGroupByWriteSectdEntities =
                JSONObject.parseArray(findNumGroupByWriteSectdJson,  JSONObject.class);

        if (findNumGroupByWriteSectdEntities == null || findNumGroupByWriteSectdEntities.size() < 1) {
            return new HttpResultPagination();
        }

        DeptDomain paramDeptDomain=new DeptDomain();
        List<MapEntity> depts =
                deptService.findIdMapByDomain(paramDeptDomain);
        Map<Long, String> deptIdMap = FormatterUtil.ListMapEntityToMap(depts);

        //返回值赋值
        List<SettlementNumEdntity> settlementNumEdntities = new ArrayList<>();

        for (JSONObject t : findNumGroupByWriteSectdEntities) {
            SettlementNumEdntity settlementNumEdntity =
                    new SettlementNumEdntity();
            settlementNumEdntity.setWriteSectId(t.getLong("_id"));
            settlementNumEdntity.setUserNum(t.getLong("sumNum"));
            settlementNumEdntities.add(settlementNumEdntity);
        }

        //获取抄表区段信息
        List<Long> writeSectIds=
                settlementNumEdntities.stream().map(SettlementNumEdntity::getWriteSectId).distinct().collect(Collectors.toList());
        WriteSectDomain paramSectDomain=new WriteSectDomain();
        paramSectDomain.setMon(mon.toString());
        paramSectDomain.setPageSize(pageSize);
        paramSectDomain.setPageCurrent(pageCurrent);
        paramSectDomain.setWriteSectionIds(writeSectIds);
        List<WriteSectDomain> writeSectDomains=
                writeSectDAO.getWriteSect(paramSectDomain);
        Map<Long,WriteSectDomain> writeSectDomainMap=
                writeSectDomains.stream().collect(Collectors.toMap(WriteSectDomain::getId, a -> a, (k1, k2) -> k1));
        List<Long> pageWriteSect=
                writeSectDomains.stream().map(WriteSectDomain::getId).distinct().collect(Collectors.toList());
        //系统用户
        UserInfoEntity userDomain = new UserInfoEntity();
        List<MapEntity> listmap = userService.findMapByDomain(userDomain);
        Map<Long, String> userMap = FormatterUtil.ListMapEntityToMap(listmap);

        settlementNumEdntities=
                settlementNumEdntities.stream().filter(t->pageWriteSect.contains(t.getWriteSectId())).collect(Collectors.toList());

        for(SettlementNumEdntity settlementNumEdntity:settlementNumEdntities){
            WriteSectDomain writeSectDomain=
                    writeSectDomainMap.get(settlementNumEdntity.getWriteSectId());
            settlementNumEdntity.setBusinessPlaceCode(writeSectDomain.getBusinessPlaceCode());
            String deptDomainName=
                    deptIdMap.get(settlementNumEdntity.getBusinessPlaceCode());
            if(deptDomainName!=null){
                settlementNumEdntity.setBusinessPlaceCodeName(deptDomainName);
            }
            settlementNumEdntity.setWriteSectNo(writeSectDomain.getWriteSectNo());
            settlementNumEdntity.setWriteSectName(writeSectDomain.getWriteSectName());
            settlementNumEdntity.setWritorId(writeSectDomain.getWritorId());
            settlementNumEdntity.setWritorName(userMap.get(settlementNumEdntity.getWritorId()));
            settlementNumEdntity.setMon(mon);

        }
        settlementNumEdntities =
                settlementNumEdntities.stream().sorted(Comparator.comparing(SettlementNumEdntity::getBusinessPlaceCode, Comparator.nullsFirst(Long::compareTo))
                        .thenComparing(SettlementNumEdntity::getWritorId,
                                Comparator.nullsFirst(Long::compareTo))
                        .thenComparing(SettlementNumEdntity::getWriteSectNo,
                                Comparator.nullsFirst(String::compareTo))).collect(Collectors.toList());


        return new HttpResultPagination(paramSectDomain,settlementNumEdntities);



    }
}
