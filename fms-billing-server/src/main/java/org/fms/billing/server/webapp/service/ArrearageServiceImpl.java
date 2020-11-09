package org.fms.billing.server.webapp.service;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.fms.billing.common.util.CustomCollectors;
import org.fms.billing.common.util.FormatterUtil;
import org.fms.billing.common.webapp.domain.ArrearageDetailDomain;
import org.fms.billing.common.webapp.domain.ArrearageDomain;
import org.fms.billing.common.webapp.domain.DeptDomain;
import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.PriceExecutionDomain;
import org.fms.billing.common.webapp.domain.SettlementMeterRelDomain;
import org.fms.billing.common.webapp.domain.WriteFilesDomain;
import org.fms.billing.common.webapp.domain.WriteSectDomain;
import org.fms.billing.common.webapp.domain.beakInterface.CustomerDomain;
import org.fms.billing.common.webapp.domain.beakInterface.SettlementDomain;
import org.fms.billing.common.webapp.domain.mongo.MeterMeterAssetsRelDomain;
import org.fms.billing.common.webapp.domain.mongo.UserMongoDomain;
import org.fms.billing.common.webapp.domain.mongo.WriteSectMongoDomain;
import org.fms.billing.common.webapp.entity.BankCollectionEntity;
import org.fms.billing.common.webapp.entity.ElectricityTariffRankEntity;
import org.fms.billing.common.webapp.entity.MapEntity;
import org.fms.billing.common.webapp.entity.MeterPageEntity;
import org.fms.billing.common.webapp.entity.PulishEntity;
import org.fms.billing.common.webapp.entity.SettlementEntity;
import org.fms.billing.common.webapp.entity.SystemCommonConfigEntity;
import org.fms.billing.common.webapp.service.IArrearageService;
import org.fms.billing.server.webapp.dao.ArrearageDAO;
import org.fms.billing.server.webapp.dao.MeterDAO;
import org.fms.billing.server.webapp.dao.MeterMoneyDAO;
import org.fms.billing.server.webapp.dao.PriceExecutionDAO;
import org.fms.billing.server.webapp.dao.UserMongoDAO;
import org.fms.billing.server.webapp.dao.WriteFilesDAO;
import org.fms.billing.server.webapp.dao.WriteSectMongoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;
import com.riozenc.titanTool.common.json.utils.JSONUtil;
import com.riozenc.titanTool.properties.Global;
import com.riozenc.titanTool.spring.web.client.TitanTemplate;
import com.riozenc.titanTool.spring.web.http.HttpResult;

@TransactionService
public class ArrearageServiceImpl implements IArrearageService {
    @TransactionDAO
    private ArrearageDAO arrearageDAO;
    @TransactionDAO
    private MeterMoneyDAO meterMoneyDAO;

    @Autowired
    private MeterDAO meterDAO;

    @Autowired
    private UserMongoDAO userMongoDAO;

    @Autowired
    private WriteSectMongoDAO writeSectMongoDAO;

    @Autowired
    private TitanTemplate titanTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PriceExecutionDAO priceExecutionDAO;

    @Autowired
    private SysCommonConfigService sysCommonConfigService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private CimService cimService;

    @Resource
    private WriteFilesDAO writeFilesDAO;

    @Override
    public List<ArrearageDomain> findByWhere(ArrearageDomain arrearageDomain) {
        return arrearageDAO.findByWhere(arrearageDomain);
    }

    @Override
    public ArrearageDomain findByKey(ArrearageDomain arrearageDomain) {
        return arrearageDAO.findByKey(arrearageDomain);
    }

    @Override
    public List<ArrearageDomain> findByListKey(String ids) {
        return arrearageDAO.findByListKey(ids);
    }

    @Override
    public int insert(ArrearageDomain arrearageDomain) {
        return arrearageDAO.insert(arrearageDomain);
    }

    @Override
    public int delete(ArrearageDomain arrearageDomain) {
        return arrearageDAO.delete(arrearageDomain);
    }

    @Override
    public int update(ArrearageDomain arrearageDomain) {
        return arrearageDAO.update(arrearageDomain);
    }

    @Override
    public List<ArrearageDetailDomain> findArrearageDetail(MeterPageEntity meterPageEntity) {
        return arrearageDAO.findArrearageDetail(meterPageEntity);
    }

    @Override
    public List<ArrearageDetailDomain> findArrearageDetailByWhere(ArrearageDomain arrearageDomain) {
        return arrearageDAO.findArrearageDetailByWhere(arrearageDomain);
    }

    // 根据计量点生成欠费记录
    public List<ArrearageDetailDomain> generateArrearageByMeterIds(String meterId, String arrearageNo, String mon) {
        ArrearageDomain arrearageDomain = new ArrearageDomain();
        arrearageDomain.setArrearageNo(arrearageNo);
        // meterMoneyDAO
        return null;
    }

    @Override
    public int updateList(List<ArrearageDomain> arrearageDomains) {
        arrearageDAO.deleteListByParam(arrearageDomains);
        return arrearageDAO.insertList(arrearageDomains);
    }

    @Override
    public List<ArrearageDomain> findArrearageByMeterIds(String meterIds) {
        return arrearageDAO.findArrearageByMeterIds(meterIds);
    }

    @Override
    public int updateLockByIds(List<Long> ids) {
        return arrearageDAO.updateLockByIds(ids);
    }

    @Override
    public int removeLockByIds(List<Long> ids) {
        return arrearageDAO.removeLockByIds(ids);
    }

    @Override
    public List<ArrearageDomain> findBySettleIdMonAndSn(ArrearageDomain arrearageDomain) {
        return arrearageDAO.findBySettleIdMonAndSn(arrearageDomain);
    }

    @Override
    public List<ArrearageDomain> findArrearageBySettleIds(String settleIds) {
        return arrearageDAO.findArrearageBySettleIds(settleIds);
    }

    @Override
    public List<BankCollectionEntity> findArrearageGroupbySettleId(ArrearageDomain arrearageDomain) {
        return arrearageDAO.findArrearageGroupbySettleId(arrearageDomain);
    }

    @Override
    public List<ArrearageDomain> findArrearageByCustomer(CustomerDomain customer) {
        Map<String, Object> params = new HashMap<>();
        MeterDomain meterDomain = new MeterDomain();
        meterDomain.setCustomerId(customer.getId());
        meterDomain.setPageSize(-1);
        params.put("meter", meterDomain);
        List<MeterDomain> meterDomains = new ArrayList<>();
        try {
            meterDomains = titanTemplate.postJson("CIM-SERVER",
                    "cimServer/meter?method=queryMetersByCustomer", new HttpHeaders(), params,
                    new TypeReference<List<MeterDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (meterDomains.isEmpty()) {
            return new ArrayList<>();
        }
        Map<Long, MeterDomain> meterDomainMap = meterDomains.stream()
                .collect(Collectors.toMap(MeterDomain::getId, a -> a, (k1, k2) -> k1));
        // 取计量点id
        List<Long> meterIds = meterDomains.stream().filter(m -> m.getId() != null)
                .map(MeterDomain::getId).distinct().collect(toList());
        //去欠费记录
        ArrearageDomain arrearageDomain = new ArrearageDomain();
        arrearageDomain.setMeterIds(meterIds);
        arrearageDomain.setStartMon(customer.getStartMon());
        arrearageDomain.setEndMon(customer.getEndMon());
        arrearageDomain.setIsSettle(0);
        List<ArrearageDomain> arrearageDomainList = arrearageDAO.findByWhere(arrearageDomain);
        Map<String, Object> params2 = new HashMap<>();
        SettlementDomain settlementDomain = new SettlementDomain();
        settlementDomain.setCustomerId(customer.getId());
        params2.put("settlement", settlementDomain);
        List<SettlementDomain> settlementDomains = new ArrayList<>();
        try {
            settlementDomains = titanTemplate.postJson("CIM-SERVER",
                    "cimServer/settlement?method=querySettlements", new HttpHeaders(), params2,
                    new TypeReference<List<SettlementDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (settlementDomains.isEmpty()) {
            return new ArrayList<>();
        }
        Map<Long, ArrearageDomain> arrearageDomainMap = arrearageDomainList.stream()
                .collect(Collectors.toMap(ArrearageDomain::getId, a -> a, (k1, k2) -> k1));
        //遍历欠费 补充结算户属性
        Map<Long, SettlementDomain> settlementDomainMap = settlementDomains.stream()
                .collect(Collectors.toMap(SettlementDomain::getId, k -> k));
        arrearageDomainList.forEach(s -> {
            s.setCustomerId(settlementDomainMap.get(s.getSettlementId()).getCustomerId());
            s.setSettlementNo(settlementDomainMap.get(s.getSettlementId()).getSettlementNo());
            s.setSettlementName(settlementDomainMap.get(s.getSettlementId()).getSettlementName());
        });
        arrearageDomainList.forEach(a -> a.setMeterName(meterDomainMap.get(a.getMeterId()).getMeterName()));
        List<Long> writeSectIds = arrearageDomainList.stream().filter(a -> a.getWriteSectId() != null)
                .map(ArrearageDomain::getWriteSectId).distinct().collect(toList());
        arrearageDomainList.stream().filter(a -> a.getOweMoney() != null).forEach(a -> {
            if (a.getOweMoney() != null) {
                a.setElectricityChargeReceived(a.getReceivable() == null ? BigDecimal.ZERO : a.getReceivable()
                        .subtract(a.getOweMoney()));
            }

        });
        arrearageDomainList.forEach(a -> {
            if (arrearageDomainMap.get(a.getId()) != null) {
                a.setElectricityChargeReceived(arrearageDomainMap.get(a.getId()).getReceivable()
                        .subtract(arrearageDomainMap.get(a.getId()).getOweMoney()));
            }
        });
        List<WriteSectDomain> writeSectDomains = new ArrayList<>();
        Map<String, Object> params3 = new HashMap<>();
        WriteSectDomain writeSectDomain = new WriteSectDomain();
        writeSectDomain.setWriteSectionIds(writeSectIds);
        params3.put("writeSect", writeSectDomain);
        try {
            writeSectDomains = titanTemplate.postJson("CIM-SERVER",
                    "cimServer/writeSect?method=queryByWhere", new HttpHeaders(), params3,
                    new TypeReference<List<WriteSectDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        //遍历欠费 补充抄表区段属性
        Map<Long, WriteSectDomain> writeSectDomainMap = writeSectDomains.stream()
                .collect(Collectors.toMap(WriteSectDomain::getId, k -> k));
        if (!writeSectDomains.isEmpty()) {
            arrearageDomainList.forEach(w -> {
                if (writeSectDomainMap.get(w.getWriteSectId()) != null) {
                    w.setWriteSectNo(w.getWriteSectNo());
                    w.setWriteSectName(w.getWriteSectName());
                }
            });
        }

        return arrearageDomainList;
    }

//    @Override
//    public List<ArrearageDomain> findAccumulatedArrearage(ArrearageDomain arrearageDomain) {
//        MeterMoneyDomain meterMoneyDomain = new MeterMoneyDomain();
////        meterMoneyDomain.setBusinessPlaceCode(arrearageDomain.getBusinessPlaceCode().toString());
////        meterMoneyDomain.setWriteSectId(arrearageDomain.getWriteSectId());
//        meterMoneyDomain.setMon(arrearageDomain.getEndMon());
//        List<MeterMoneyDomain> meterMoneyDomains = meterMoneyDAO.selectMeterMoney(meterMoneyDomain);
//        if (meterMoneyDomains.isEmpty()) {
//            return new ArrayList<>(0);
//        }
//        List<Long> meterIds = new ArrayList<>();
//        for (MeterMoneyDomain meterMoney :
//                meterMoneyDomains) {
//            meterIds.add(meterMoney.getMeterId());
//        }
//        Map<String, Object> params = new HashMap<>();
//        params.put("meterIds", meterIds);
//        List<Long> settlementIds = new ArrayList<>();
//        try {
//            settlementIds = titanTemplate.postJson("CIM-SERVER",
//                    "cimServer/settlementMeterRel?method=querySettlementIdsByMeterIds",
//                    new HttpHeaders(), params, new TypeReference<List<Long>>() {
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Map<String, Object> params2 = new HashMap<>();
//        params2.put("settlementIds", settlementIds);
//        List<SettlementDomain> settlementDomains = new ArrayList<>();
//        try {
//            settlementDomains = titanTemplate.postJson("CIM-SERVER",
//                    "cimServer/settlement?method=querySettlementsByIds",
//                    new HttpHeaders(), params2, new TypeReference<List<SettlementDomain>>() {
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        List<ArrearageDomain> arrearageDomains = arrearageDAO.findArrearageQuerySumByWhere(arrearageDomain);
//        for (ArrearageDomain arrearage :
//                arrearageDomains) {
//            for (MeterMoneyDomain meterMoney :
//                    meterMoneyDomains) {
//                if (meterMoney.getMeterId().equals(arrearage.getMeterId())) {
//                    arrearage.setSettlementName(meterMoney.getSettlementName());
//                    arrearage.setSettlementNo(meterMoney.getSettlementNo());
//                }
//            }
//        }
//        return arrearageDomains;
//    }


    @Override
    public List<ArrearageDomain> arrearageDetailQuery(ArrearageDomain arrearageDomain) {
        List<ArrearageDomain> arrearageDomains = null;
        arrearageDomain.setPageSize(-1);
        if (arrearageDomain.getBusinessPlaceCode() == null) {
            arrearageDomains = arrearageDAO.findDetailByWhere(arrearageDomain);
        } else {
            arrearageDomains = getArrearageByBusinessPlaceCode(arrearageDomain, arrearageDomain.getBusinessPlaceCode());
        }

        Map<Integer, List<ArrearageDomain>> arrearageGroupMonMap =
                arrearageDomains.stream()
                .collect(Collectors.groupingBy(ArrearageDomain::getMon));

        arrearageGroupMonMap.forEach((mon, list) -> {

            try {
                // CIM下拉
                Map<String, Object> commonParams = new HashMap<>();

                //获取下拉
                SystemCommonConfigEntity systemCommonConfigEntity = new SystemCommonConfigEntity();

                systemCommonConfigEntity.setType("COS_STD_CODE");

                String returnSystemCommonConfig =
                        restTemplate.postForObject(Global.getConfig("findSystemCommonConfigByType"), systemCommonConfigEntity,
                                String.class);

                List<SystemCommonConfigEntity> systemCommonConfigEntities =
                        JSONObject.parseArray(returnSystemCommonConfig, SystemCommonConfigEntity.class);

                Map<Long, String> cosStdCommonConfigMap =
                        systemCommonConfigEntities.stream().collect(Collectors.toMap(SystemCommonConfigEntity::getParamKey, a -> a.getParamValue(), (k1, k2) -> k1));


                systemCommonConfigEntity.setType("COS_FLAG");

                returnSystemCommonConfig =
                        restTemplate.postForObject(Global.getConfig("findSystemCommonConfigByType"), systemCommonConfigEntity,
                                String.class);

                systemCommonConfigEntities =
                        JSONObject.parseArray(returnSystemCommonConfig, SystemCommonConfigEntity.class);

                Map<Long, String> cosFlagCommonConfigMap =
                        systemCommonConfigEntities.stream().collect(Collectors.toMap(SystemCommonConfigEntity::getParamKey, a -> a.getParamValue(), (k1, k2) -> k1));


                // 结算户
                List<Long> settlementIds = list.stream().filter(i -> i.getSettlementId() != null)
                        .map(ArrearageDomain::getSettlementId).distinct().collect(Collectors.toList());
                String settlementJson = restTemplate.postForObject(Global.getConfig("findSettlementByIds"),
                        settlementIds, String.class);

                List<SettlementEntity> settementList = JSONUtil
                        .readValue(settlementJson, new TypeReference<HttpResult<List<SettlementEntity>>>() {
                        }).getResultData();

                Map<Long, SettlementEntity> settementMap = settementList.stream().collect(Collectors.toMap(SettlementEntity::getId, k -> k));

                // 计量点
                List<Long> meterIds = list.stream().filter(a -> a.getMeterId() != null).map(ArrearageDomain::getMeterId)
                        .distinct().collect(Collectors.toList());
                MeterDomain meter = new MeterDomain();
                meter.setIds(meterIds);
                meter.setMon(mon);
                meter.setPageSize(-1);
                List<MeterDomain> meterDomains = meterDAO.findMeterDomain(meter);
                Map<Long, MeterDomain> meterMap = meterDomains.stream()
                        .collect(Collectors.toMap(MeterDomain::getId, k -> k));

                //电价
                Map<Long, List<PriceExecutionDomain>> priceMap = priceExecutionDAO.findMongoPriceExecution(mon.toString());

                // 用户
                List<Long> writeIds =
                        meterMap.values().stream().filter(i -> i.getWriteSectionId() != null).map(MeterDomain::getWriteSectionId).distinct().collect(Collectors.toList());
                UserMongoDomain userMongoDomain = new UserMongoDomain();
                userMongoDomain.setWriteSectIds(writeIds);
                userMongoDomain.setMon(mon.toString());
                userMongoDomain.setPageSize(-1);
                List<UserMongoDomain> userMongoDomainList =
                        userMongoDAO.findUserMongoByWhere(userMongoDomain);

                Map<Long, UserMongoDomain> userMap = userMongoDomainList.stream()
                        .collect(Collectors.toMap(UserMongoDomain::getId, k -> k));

                //抄表段
                List<Long> writeSectIds = meterMap.values().stream().filter(i -> i.getWriteSectionId() != null).map(MeterDomain::getWriteSectionId).distinct().collect(Collectors.toList());
                WriteSectMongoDomain writeSectMongoDomain = new WriteSectMongoDomain();
                writeSectMongoDomain.setIds(writeSectIds);
                writeSectMongoDomain.setMon(mon);
                writeSectMongoDomain.setPageSize(-1);
                List<WriteSectMongoDomain> writeSectMongoDomainList =
                        writeSectMongoDAO.findWriteSectMongoByWhere(writeSectMongoDomain);

                Map<Long, WriteSectMongoDomain> writeSectMap = writeSectMongoDomainList.stream()
                        .collect(Collectors.toMap(WriteSectMongoDomain::getId, k -> k));

                // 营业区域
                Map<String, Object> params = new HashMap<>();
                params.put("status", 1);

                HttpResult<List<DeptDomain>> httpResult = titanTemplate.postJson("AUTH-CENTER", "auth/dept/tree", null, params,
                        new TypeReference<HttpResult<List<DeptDomain>>>() {
                        });
                List<DeptDomain> deptList = httpResult.getResultData();

                Map<Long, DeptDomain> deptMap = deptList.stream().collect(Collectors.toMap(DeptDomain::getId, k -> k));

                list.stream().forEach(a -> {

                    if (settementMap.get(a.getSettlementId()) != null) {
                        // 银行卡号
                        a.setBankNo(settementMap.get(a.getSettlementId()).getBankNo());
                        // 结算人电话
                        a.setSettlementPhone(settementMap.get(a.getSettlementId()).getSettlementPhone());
                        // 联网银行
                        a.setConnectBank(settementMap.get(a.getSettlementId()).getConnectBank());
                        // 缴费渠道
                        a.setChargeModeType(settementMap.get(a.getSettlementId()).getChargeModeType());
                        a.setSettlementName(settementMap.get(a.getSettlementId()).getSettlementName());
                        a.setSettlementNo(settementMap.get(a.getSettlementId()).getSettlementNo());
                    }

                    if (meterMap.get(a.getMeterId()) != null) {
                        a.setUserId(meterMap.get(a.getMeterId()).getUserId());
                        // 客户编号
                        a.setUserNo(meterMap.get(a.getMeterId()).getUserNo());
                        // 客户名称
                        a.setUserName(meterMap.get(a.getMeterId()).getUserName());

                        // 抄表区段
                        a.setWriteSectNo(meterMap.get(a.getMeterId()).getWriteSectNo());
                        a.setWriteSectName(writeSectMap.get(a.getWriteSectId()).getWriteSectName());
                        a.setTg(writeSectMap.get(a.getWriteSectId()).getWriteSectName());

                        // 计量点编号
                        a.setMeterNo(meterMap.get(a.getMeterId()).getMeterNo());
                        a.setMeterName(meterMap.get(a.getMeterId()).getMeterName());
                        a.setCalCapacity(meterMap.get(a.getMeterId()).getChargingCapacity());
                        // 用电类别
                        a.setElecTypeCode(meterMap.get(a.getMeterId()).getElecTypeCode());
                        if (a.getWritorId() == null) {
                            a.setWritorId(meterMap.get(a.getMeterId()).getWritorId());
                        }
                        a.setPriceType(meterMap.get(a.getMeterId()).getPriceType());
                        //力率标准
                        if(meterMap.get(a.getMeterId()).getCosType()!=null){
                            a.setCosStdCodeName(cosStdCommonConfigMap.get(new Long(meterMap.get(a.getMeterId()).getCosType())));
                            a.setCosFlagName(cosFlagCommonConfigMap.get(new Long(meterMap.get(a.getMeterId()).getCosType())));
                        }else{
                            a.setCosStdCodeName("");
                            a.setCosFlagName("");
                        }
                        if (a.getPriceType() != null) {
                            a.setPrice(priceMap.get(a.getPriceType()).stream().filter(t -> t.getPrice() != null).map(PriceExecutionDomain::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
                        }

                        if (a.getFunctionCode() != null && a.getFunctionCode() == 2) {
                            a.setPrice(BigDecimal.ZERO);
                        }
                    }

                    if (userMap.get(a.getUserId()) != null) {
                        // 用户类型
                        // 用电地址
                        a.setSetAddress(userMap.get(a.getUserId()).getAddress());
                        a.setUserType(userMap.get(a.getUserId()).getUserType());
                    }


                    if (deptMap.get(a.getBusinessPlaceCode()) != null) {
                        a.setDeptName(deptMap.get(a.getBusinessPlaceCode()).getTitle());
                    }

                });

            } catch (JsonParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JsonMappingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });

        return arrearageDomains;
    }

    //欠费汇总专用方法
    @Override
    public List<ArrearageDomain> findArrearageQuerySumByWhere(ArrearageDomain arrearageDomain) {
        List<ArrearageDomain> arrearageDomains = null;
        if (arrearageDomain.getBusinessPlaceCode() == null) {
            arrearageDomains = arrearageDAO.findArrearageQuerySumByWhere(arrearageDomain);
        } else {
            arrearageDomains = getArrearageByBusinessPlaceCode(arrearageDomain, arrearageDomain.getBusinessPlaceCode());
        }

        /*Map<Integer, List<ArrearageDomain>> arrearageGroupMonMap = arrearageDomains.parallelStream()
                .collect(Collectors.groupingBy(ArrearageDomain::getMon));*/

        /*arrearageGroupMonMap.forEach((mon, list) -> {
            try {
                //抄表段
                List<Long> writeSectIds =
                        list.stream().filter(i -> i.getWriteSectId() != null).map(ArrearageDomain::getWriteSectId).distinct().collect(Collectors.toList());
                WriteSectMongoDomain writeSectMongoDomain = new WriteSectMongoDomain();
                writeSectMongoDomain.setIds(writeSectIds);
                writeSectMongoDomain.setMon(mon);
                writeSectMongoDomain.setPageSize(-1);
                List<WriteSectMongoDomain> writeSectMongoDomainList =
                        writeSectMongoDAO.findWriteSectMongoByWhere(writeSectMongoDomain);

                Map<Long, WriteSectMongoDomain> writeSectMap = writeSectMongoDomainList.stream()
                        .collect(Collectors.toMap(WriteSectMongoDomain::getId, k -> k));

                list.parallelStream().forEach(a -> {

                    a.setWriteSectNo(writeSectMap.get(a.getWriteSectId()).getWriteSectNo());
                    a.setWriteSectName(writeSectMap.get(a.getWriteSectId()).getWriteSectName());

                });

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });*/

        return arrearageDomains;
    }

    private List<ArrearageDomain> getArrearageByBusinessPlaceCode(ArrearageDomain arrearageDomain,
                                                                  Long businessPlaceCode) {

        Map<String, Object> params = new HashMap<>();
        params.put("id", businessPlaceCode);
        try {
            HttpResult<List<DeptDomain>> httpResult = titanTemplate.postJson("AUTH-CENTER", "auth/dept/tree", null, params,
                    new TypeReference<HttpResult<List<DeptDomain>>>() {
                    });
            List<DeptDomain> deptList = httpResult.getResultData();
            List<DeptDomain> businessPlaceCodes = new ArrayList<>();
            if (deptList.size() > 0) {

                businessPlaceCodes = getDeptList(deptList, businessPlaceCodes);
                arrearageDomain.setBusinessPlaceCodes(businessPlaceCodes.stream().map(DeptDomain::getId).collect(Collectors.toList()));
                arrearageDomain.getBusinessPlaceCodes().add(businessPlaceCode);
            }
            List<ArrearageDomain> list = arrearageDAO.findArrearageQuerySumByWhere(arrearageDomain);

            String resultJson = restTemplate.getForObject("http://AUTH-CENTER/auth/dept/getDeptById/" + businessPlaceCode,
                    String.class);
            HttpResult<DeptDomain> httpResult2 = JSONUtil.readValue(resultJson,
                    new TypeReference<HttpResult<DeptDomain>>() {
                    });
            businessPlaceCodes.add(httpResult2.getResultData());
            Map<Long, DeptDomain> deptMap = businessPlaceCodes.stream().collect(Collectors.toMap(DeptDomain::getId, k -> k));

            list.forEach(a -> {
                if (deptMap.get(a.getBusinessPlaceCode()) != null) {
                    a.setDeptName(deptMap.get(a.getBusinessPlaceCode()).getDeptName());
                }
            });

            return list;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

    public List<DeptDomain> getDeptList(List<DeptDomain> list, List<DeptDomain> businessPlaceCodes) {
        businessPlaceCodes.addAll(list);
        List<List<DeptDomain>> tree = list.stream().filter(i -> i.getChildren().size() > 0).map(DeptDomain::getChildren).collect(Collectors.toList());
        if (tree.size() > 0) {
            List<DeptDomain> child = new ArrayList<>();
            tree.forEach(child::addAll);
            return getDeptList(child, businessPlaceCodes);
        } else {
            return businessPlaceCodes;
        }
    }

    public List<ArrearageDomain> arrearageAccumulate(ArrearageDomain arrearage) throws IOException {
        List<ArrearageDomain> arrearageDomains = null;
        if (arrearage.getBusinessPlaceCode() == null) {
            arrearageDomains = arrearageDAO.arrearageAccumulate(arrearage);

        } else {
            arrearageDomains = arrearageAccumulateByBusinessPlaceCode(arrearage, arrearage.getBusinessPlaceCode());
        }

        if (arrearageDomains == null || arrearageDomains.size() < 1) {
            return new ArrayList<>();
        }

        //档案计量点信息
        List<Long> meterIds =
                arrearageDomains.stream().map(ArrearageDomain::getMeterId).distinct().collect(Collectors.toList());

        HttpResult paramHttpResult =
                restTemplate.postForObject(Global.getConfig("getMeterByMeterIdsWithoutStatus"), meterIds,
                        HttpResult.class);

        List<MeterDomain> meterDomains = JSONObject.parseArray(JSONArray.toJSONString(paramHttpResult.getResultData()),
                MeterDomain.class);

        Map<Long, MeterDomain> meterMap = meterDomains.stream()
                .collect(Collectors.toMap(MeterDomain::getId, a -> a, (k1, k2) -> k1));

        //档案用户信息
        List<Long> userIds =
                meterDomains.stream().map(MeterDomain::getUserId).distinct().collect(toList());

        String userJson =
                restTemplate.postForObject(Global.getConfig("getUserByIds"), userIds,
                        String.class);

        List<UserMongoDomain> userMongoDomainList =
                JSONObject.parseArray(userJson, UserMongoDomain.class);

        Map<Long, UserMongoDomain> userMap = userMongoDomainList.stream()
                .collect(Collectors.toMap(UserMongoDomain::getId, k -> k));

        Map<Integer, List<ArrearageDomain>> listMap = arrearageDomains.parallelStream()
                .collect(Collectors.groupingBy(ArrearageDomain::getMon));

        //结算户
        List<Long> settlementIds = arrearageDomains.stream().filter(i -> i.getSettlementId() != null)
                .map(ArrearageDomain::getSettlementId).distinct().collect(toList());
        String settlementJson = restTemplate.postForObject(Global.getConfig("findSettlementByIds"),
                settlementIds, String.class);

        Map<Long, SettlementEntity> settementMap = JSONUtil
                .readValue(settlementJson, new TypeReference<HttpResult<List<SettlementEntity>>>() {
                }).getResultData().stream().collect(Collectors.toMap(SettlementEntity::getId, k -> k));

        //抄表区段
        List<Long> writeSectIds =
                arrearageDomains.stream().filter(i -> i.getWriteSectId() != null)
                        .map(ArrearageDomain::getWriteSectId).distinct().collect(Collectors.toList());

        WriteSectDomain writeSectDomain = new WriteSectDomain();
        writeSectDomain.setWriteSectionIds(writeSectIds);
        String writeSectJson = restTemplate.postForObject(Global.getConfig("getWriteSectFindByWhere"),
                writeSectDomain, String.class);

        Map<Long, WriteSectDomain> writeSectDomainMap = JSONUtil
                .readValue(writeSectJson,
                        new TypeReference<List<WriteSectDomain>>() {
                        }).stream().collect(Collectors.toMap(WriteSectDomain::getId, k -> k));

        Map<Long, String> chargeModeConfigMap =
                sysCommonConfigService.getsystemCommonConfigMap("CHARGE_MODE");

        Map<Long, String> connectBankConfigMap =
                sysCommonConfigService.getsystemCommonConfigMap("CONNECT_BANK");

        Map<Long, String> elecTypeConfigMap =
                sysCommonConfigService.getsystemCommonConfigMap("ELEC_TYPE");

        Map<Long, String> userTypeConfigMap =
                sysCommonConfigService.getsystemCommonConfigMap("CUSTOMER_TYPE");


        // 营业区域
        Map<String, Object> params = new HashMap<>();
        params.put("status", 1);

        HttpResult<List<DeptDomain>> httpResult = null;
        try {
            httpResult = titanTemplate.postJson("AUTH-CENTER", "auth/dept/tree", null, params,
                    new TypeReference<HttpResult<List<DeptDomain>>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<DeptDomain> deptList = httpResult.getResultData();

        Map<Long, DeptDomain> deptMap = deptList.stream().collect(Collectors.toMap(DeptDomain::getId, k -> k));


        listMap.forEach((t, list) -> {

            list.parallelStream().forEach(a -> {

                if (settementMap.get(a.getSettlementId()) != null) {
                    // 客户编号
                    a.setUserNo(settementMap.get(a.getSettlementId()).getSettlementNo());
                    // 客户名称
                    a.setUserName(settementMap.get(a.getSettlementId()).getSettlementName());
                    // 银行卡号
                    a.setBankNo(settementMap.get(a.getSettlementId()).getBankNo());
                    // 结算人电话
                    a.setSettlementPhone(settementMap.get(a.getSettlementId()).getSettlementPhone());
                    // 联网银行
                    a.setConnectBank(settementMap.get(a.getSettlementId()).getConnectBank());
                    if (a.getConnectBank() != null) {
                        a.setConnectBankName(connectBankConfigMap.get(new Long(a.getConnectBank())));
                    }

                    // 缴费渠道
                    a.setChargeModeType(settementMap.get(a.getSettlementId()).getChargeModeType());
                    if (a.getChargeModeType() != null) {
                        a.setChargeModeTypeName(chargeModeConfigMap.get(new Long(settementMap.get(a.getSettlementId()).getChargeModeType())));
                    }

                    a.setSetAddress(settementMap.get(a.getSettlementId()).getAddress());

                }

                if (meterMap.get(a.getMeterId()) != null) {
                    a.setUserId(meterMap.get(a.getMeterId()).getUserId());


                    // 抄表区段
                    if (a.getWriteSectId() != null) {
                        a.setWriteSectNo(writeSectDomainMap.get(a.getWriteSectId()).getWriteSectNo());
                        a.setWriteSectName(writeSectDomainMap.get(a.getWriteSectId()).getWriteSectName());
                    }

                    // 计量点编号
                    a.setMeterNo(meterMap.get(a.getMeterId()).getMeterNo());

                    // 用电类别
                    a.setElecTypeCode(meterMap.get(a.getMeterId()).getElecTypeCode());
                    if (a.getElecTypeCode() != null) {
                        a.setElecTypeName(elecTypeConfigMap.get(new Long(a.getElecTypeCode())));
                    }
                    if (a.getWritorId() == null) {
                        a.setWritorId(meterMap.get(a.getMeterId()).getWritorId());
                    }
                }

                if (userMap.get(a.getUserId()) != null) {
                    // 用户类型
                    a.setUserType(userMap.get(a.getUserId()).getUserType());
                    if (a.getUserType() != null) {
                        a.setUserTypeName(userTypeConfigMap.get(new Long(a.getUserType())));
                    }
                }

                if (deptMap.get(a.getBusinessPlaceCode()) != null) {
                    a.setDeptName(deptMap.get(a.getBusinessPlaceCode()).getDeptName());
                }

            });

        });
        arrearageDomains =
                arrearageDomains.stream().sorted(Comparator.comparing(ArrearageDomain::getWritorId, Comparator.nullsFirst(Long::compareTo))
                        .thenComparing(ArrearageDomain::getWriteSectId, Comparator.nullsFirst(Long::compareTo))
                        .thenComparing(ArrearageDomain::getSetAddress, Comparator.nullsFirst(String::compareTo))
                        .thenComparing(ArrearageDomain::getSetAddress, Comparator.nullsFirst(String::compareTo))
                        .thenComparing(ArrearageDomain::getSettlementId,
                                Comparator.nullsFirst(Long::compareTo))).collect(Collectors.toList());

        return arrearageDomains;
    }

    public List<ArrearageDomain> arrearageAccumulateByBusinessPlaceCode(ArrearageDomain arrearage, Long businessPlaceCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", businessPlaceCode);
        try {
            HttpResult<List<DeptDomain>> httpResult = titanTemplate.postJson("AUTH-CENTER", "auth/dept/tree", null, params,
                    new TypeReference<HttpResult<List<DeptDomain>>>() {
                    });
            List<DeptDomain> deptList = httpResult.getResultData();
            List<DeptDomain> businessPlaceCodes = new ArrayList<>();
            if (deptList.size() > 0) {

                businessPlaceCodes = getDeptList(deptList, businessPlaceCodes);
                arrearage.setBusinessPlaceCodes(businessPlaceCodes.stream().map(DeptDomain::getId).collect(Collectors.toList()));
//				arrearageDomain
//						.setBusinessPlaceCodes(deptList.stream().filter(d -> d.getParentId() == businessPlaceCode)
//								.map(DeptDomain::getId).collect(Collectors.toList()));
                arrearage.getBusinessPlaceCodes().add(businessPlaceCode);
            }
            List<ArrearageDomain> list = arrearageDAO.arrearageAccumulate(arrearage);

            String resultJson = restTemplate.getForObject("http://AUTH-CENTER/auth/dept/getDeptById/" + businessPlaceCode,
                    String.class);
            HttpResult<DeptDomain> httpResult2 = JSONUtil.readValue(resultJson,
                    new TypeReference<HttpResult<DeptDomain>>() {
                    });
            businessPlaceCodes.add(httpResult2.getResultData());
            Map<Long, DeptDomain> deptMap = businessPlaceCodes.stream().collect(Collectors.toMap(DeptDomain::getId, k -> k));

            list.forEach(a -> {
                if (deptMap.get(a.getBusinessPlaceCode()) != null) {
                    a.setDeptName(deptMap.get(a.getBusinessPlaceCode()).getDeptName());
                }
            });

            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<ArrearageDomain> findByMeterIdsMonAndSn(PulishEntity pulishEntity) {
        return arrearageDAO.findByMeterIdsMonAndSn(pulishEntity);
    }


    @Override
    public List<ArrearageDomain> findArrearageGroupBySettleAndMon(ArrearageDomain arrearageDomain) {
        return arrearageDAO.findArrearageGroupBySettleAndMon(arrearageDomain);
    }

    //催费通知单
    @Override
    public List<ArrearageDomain> reminderNotice(ArrearageDomain arrearage) throws IOException {
        List<ArrearageDomain> arrearageDomains = null;
        if (arrearage.getBusinessPlaceCode() == null) {
            arrearageDomains = arrearageDAO.arrearageAccumulate(arrearage);

        } else {
            arrearageDomains = arrearageAccumulateByBusinessPlaceCode(arrearage, arrearage.getBusinessPlaceCode());
        }

        if (arrearageDomains == null || arrearageDomains.size() < 1) {
            return new ArrayList<>();
        }

        //结算户
        List<Long> settlementIds = arrearageDomains.stream().filter(i -> i.getSettlementId() != null)
                .map(ArrearageDomain::getSettlementId).distinct().collect(toList());

        String settlementJson = restTemplate.postForObject(Global.getConfig("findSettlementByIds"),
                settlementIds, String.class);

        List<SettlementDomain> settlementEntities=JSONUtil
                .readValue(settlementJson,
                        new TypeReference<HttpResult<List<SettlementDomain>>>() {
                }).getResultData();

        if(arrearage.getChargeModeType()!=null){
            settlementEntities=
                    settlementEntities.stream().filter(t->t.getChargeModeType()==arrearage.getChargeModeType()).collect(toList());
        }


        if(settlementEntities==null || settlementEntities.size()<1){
            return new ArrayList<>(0);
        }
        Map<Long, SettlementDomain> settementMap = settlementEntities.stream().collect(Collectors.toMap(SettlementDomain::getId, k -> k));

        arrearageDomains=
                arrearageDomains.stream().filter(t->settementMap.keySet().contains(t.getSettlementId())).collect(toList());


        //结算户与计量点关系
        List<SettlementMeterRelDomain> settlementMeterRelDomains =
                cimService.getMeterIdsBySettlements(settlementEntities);

        //获取抄表序号
        List<Long> meterIds=
                settlementMeterRelDomains.stream().map(SettlementMeterRelDomain::getMeterId).distinct().collect(toList());

        List<MeterMeterAssetsRelDomain> meterMeterAssetsRelDomains= cimService.getMeterAssetsByMeterIds(meterIds);

        meterMeterAssetsRelDomains.forEach(t->{
            if(t.getWriteSn()==null){
                t.setWriteSn(new BigDecimal("9999"));
            }
        });

        Map<Long,BigDecimal> meterMeterAssetsMap=
                meterMeterAssetsRelDomains.stream().collect(Collectors.groupingBy(MeterMeterAssetsRelDomain::getMeterId, CustomCollectors.minBy(MeterMeterAssetsRelDomain::getWriteSn)));

        settlementMeterRelDomains.forEach(t->{
            t.setWriteSn(meterMeterAssetsMap.get(t.getMeterId()));
        });

        settlementMeterRelDomains.forEach(t->{
            if(t.getWriteSn()==null){
                t.setWriteSn(new BigDecimal("9999"));
            }
        });

        Map<Long,BigDecimal> settlementWriteSnMap=
                settlementMeterRelDomains.stream().collect(Collectors.groupingBy(SettlementMeterRelDomain::getSettlementId, CustomCollectors.minBy(SettlementMeterRelDomain::getWriteSn)));

        //抄表区段
        List<Long> writeSectIds =
                arrearageDomains.stream().filter(i -> i.getWriteSectId() != null)
                        .map(ArrearageDomain::getWriteSectId).distinct().collect(Collectors.toList());

        WriteSectDomain writeSectDomain = new WriteSectDomain();
        writeSectDomain.setWriteSectionIds(writeSectIds);
        String writeSectJson = restTemplate.postForObject(Global.getConfig("getWriteSectFindByWhere"),
                writeSectDomain, String.class);

        Map<Long, WriteSectDomain> writeSectDomainMap = JSONUtil
                .readValue(writeSectJson,
                        new TypeReference<List<WriteSectDomain>>() {
                        }).stream().collect(Collectors.toMap(WriteSectDomain::getId, k -> k));


        DeptDomain paramDeptDomain=new DeptDomain();
        List<MapEntity> depts =
                deptService.findIdMapByDomain(paramDeptDomain);
        Map<Long, String> deptMap = FormatterUtil.ListMapEntityToMap(depts);


        for (ArrearageDomain arrearageDomain:arrearageDomains){
            arrearageDomain.setWriteSn(settlementWriteSnMap.get(arrearageDomain.getSettlementId()));
            // 抄表区段
            if (arrearageDomain.getWriteSectId() != null) {
                arrearageDomain.setWriteSectNo(writeSectDomainMap.get(arrearageDomain.getWriteSectId()).getWriteSectNo());
                arrearageDomain.setWriteSectName(writeSectDomainMap.get(arrearageDomain.getWriteSectId()).getWriteSectName());
            }

            arrearageDomain.setDeptName(deptMap.get(arrearageDomain.getBusinessPlaceCode()));

            if (settementMap.get(arrearageDomain.getSettlementId()) != null) {
                // 客户编号
                arrearageDomain.setSettlementNo(settementMap.get(arrearageDomain.getSettlementId()).getSettlementNo());
                // 客户名称
                arrearageDomain.setSettlementName(settementMap.get(arrearageDomain.getSettlementId()).getSettlementName());

                arrearageDomain.setSetAddress(settementMap.get(arrearageDomain.getSettlementId()).getAddress());
            }
        }

        arrearageDomains=
                arrearageDomains.stream().sorted(Comparator.comparing(ArrearageDomain::getWritorId, Comparator.nullsFirst(Long::compareTo))
                        .thenComparing(ArrearageDomain::getWriteSectId, Comparator.nullsFirst(Long::compareTo))
                        .thenComparing(ArrearageDomain::getWriteSn,Comparator.nullsFirst(BigDecimal::compareTo))
                        .thenComparing(ArrearageDomain::getSetAddress,  Comparator.nullsFirst(String::compareTo))
                        .thenComparing(ArrearageDomain::getSettlementId,
                                Comparator.nullsFirst(Long::compareTo))).collect(Collectors.toList());

        return arrearageDomains;
    }


    @Override
    public List<ArrearageDomain> findByIsSettleMonAndIsSend(ArrearageDomain arrearageDomain) {
        List<ArrearageDomain> arrearageDomains = arrearageDAO.findByIsSettleMonAndIsSend(arrearageDomain);
        List<Long> meterIds = arrearageDomains.stream().map(ArrearageDomain::getMeterId).distinct().collect(toList());
        WriteFilesDomain writeFilesDomain = new WriteFilesDomain();
        writeFilesDomain.setMeterIds(meterIds);
        writeFilesDomain.setMon(arrearageDomain.getMon());
        List<WriteFilesDomain> writeFilesDomains = writeFilesDAO
                .findByMeterIdsMonPowerDirectionFunctionCodeAndTimeSeg(writeFilesDomain);
        Map<Long, WriteFilesDomain> writeFilesDomainMap = writeFilesDomains.stream()
                .collect(Collectors.toMap(WriteFilesDomain::getMeterId, v -> v, (v1, v2) -> v2));
        arrearageDomains.forEach(a -> {
            if (writeFilesDomainMap.get(a.getMeterId()) != null) {
                a.setEndNum(writeFilesDomainMap.get(a.getMeterId()).getEndNum());
                a.setDiffNum(writeFilesDomainMap.get(a.getMeterId()).getDiffNum());
            }
        });
        Map<String, Object> params = new HashMap<>();
        SettlementDomain settlementDomain = new SettlementDomain();
        settlementDomain.setMeterIds(meterIds);
        params.put("settlement", settlementDomain);
//        params.put("body", meterIds);
        List<SettlementDomain> settlementDomains = new ArrayList<>();
        if (meterIds.size() == 0) {
            return new ArrayList<>();
        }
        try {
            settlementDomains = titanTemplate.postJson("CIM-SERVER",
                    "cimServer/settlement?method=findSettlementsByMeterIds", new HttpHeaders(), params,
                    new TypeReference<List<SettlementDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }



        Map<Long, SettlementDomain> settlementDomainMap = settlementDomains.stream().collect(Collectors.
                toMap(SettlementDomain::getId, v -> v, (v1, v2) -> v2));
        arrearageDomains.forEach(a -> {
            if (settlementDomainMap.get(a.getSettlementId()) != null) {
                a.setSettlementName(settlementDomainMap.get(a.getSettlementId()).getSettlementName());
                a.setSettlementNo(settlementDomainMap.get(a.getSettlementId()).getSettlementNo());
                a.setSettlementPhone(settlementDomainMap.get(a.getSettlementId()).getSettlementPhone());
            }
        });
        return arrearageDomains;
    }

    @Override
    public int updateSendBySettlementIds(List<Long> settlementIds) {
        return arrearageDAO.updateSendBySettlementIds(settlementIds);
    }

    @Override
    public int updateSendBySettlementIdsAndMon(ArrearageDomain arrearageDomain) {
        return arrearageDAO.updateSendBySettlementIdsAndMon(arrearageDomain);
    }

    // 电量电费排行
    @Override
    public List<ArrearageDomain> electricityTariffRankQuery(ElectricityTariffRankEntity electricityTariffRankEntity) {
        return arrearageDAO.electricityTariffRankQuery(electricityTariffRankEntity);
    }
}
