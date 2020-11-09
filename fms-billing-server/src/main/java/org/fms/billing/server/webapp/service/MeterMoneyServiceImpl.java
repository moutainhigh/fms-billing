package org.fms.billing.server.webapp.service;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.Document;
import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.MeterMoneyDomain;
import org.fms.billing.common.webapp.domain.SettlementMeterRelDomain;
import org.fms.billing.common.webapp.domain.WriteSectDomain;
import org.fms.billing.common.webapp.domain.beakInterface.CustomerDomain;
import org.fms.billing.common.webapp.domain.beakInterface.SettlementDomain;
import org.fms.billing.common.webapp.domain.beakInterface.UserDomain;
import org.fms.billing.common.webapp.domain.mongo.MeterMoneySumMongoDomain;
import org.fms.billing.common.webapp.domain.mongo.MeterMongoDomain;
import org.fms.billing.common.webapp.service.IMeterMoneyService;
import org.fms.billing.server.webapp.dao.ArrearageDAO;
import org.fms.billing.server.webapp.dao.MeterDAO;
import org.fms.billing.server.webapp.dao.MeterMoneyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;
import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.spring.web.client.TitanTemplate;

@TransactionService
public class MeterMoneyServiceImpl implements IMeterMoneyService {

    @TransactionDAO
    private MeterMoneyDAO meterMoneyDAO;
    @TransactionDAO
    private ArrearageDAO arrearageDAO;
    @Autowired
    private MeterDAO meterDAO;
    @Autowired
    private CimService cimService;

    @Autowired
    private TitanTemplate titanTemplate;

    @Override
    public List<MeterMoneyDomain> findByWhere(MeterMoneyDomain meterMoneyDomain) {
        return meterMoneyDAO.findByWhere(meterMoneyDomain);
    }

    @Override
    public List<MeterMoneyDomain> meterMoneyDetailQuery(MeterMoneyDomain meterMoneyDomain) {
        return meterMoneyDAO.meterMoneyDetailQuery(meterMoneyDomain);
    }

    @Override
    public int insert(MeterMoneyDomain meterMoneyDomain) {
        return meterMoneyDAO.insert(meterMoneyDomain);
    }

    @Override
    public int update(MeterMoneyDomain meterMoneyDomain) {
        return meterMoneyDAO.update(meterMoneyDomain);
    }

    // @Override
//    public List<MeterMoneyDomain> mongoFind(Map<String, Object> queryParam) {
//        return meterMoneyDAO.mongoFind(queryParam);
//    }
    @Override
    public List<MeterMoneyDomain> mongoFind(MeterMoneyDomain meterMoneyDomain) {
        return meterMoneyDAO.mongoFind(meterMoneyDomain);
    }

    @Override
    public List<MeterMoneyDomain> findMeterMoneyByIds(List<Long> ids) {
        return meterMoneyDAO.findMeterMoneyByIds(ids);
    }

    @Override
    public List<MeterMoneyDomain> findMeterMoneyBySettlement(SettlementDomain settlementDomain) {
        Map<String, Object> params = new HashMap<>();
        params.put("businessPlaceCode", settlementDomain.getBusinessPlaceCode());
        params.put("invoiceType", settlementDomain.getInvoiceType());
        List<SettlementDomain> settlementDomains = new ArrayList<>();
        final String serverName = "CIM-SERVER";
        try {
            settlementDomains = titanTemplate.postJson(serverName,
                    "cimServer/settlement?method=getSettlementsByBusinessPlaceCodeAndInvoiceType",
                    new HttpHeaders(), params, new TypeReference<List<SettlementDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (settlementDomains.isEmpty()) {
            return new ArrayList<>(0);
        }
        Map<Long, SettlementDomain> settlementDomainMap = settlementDomains.stream()
                .collect(Collectors.toMap(SettlementDomain::getId, v -> v, (v1, v2) -> v1));
        List<Long> customerIds = settlementDomains.stream().filter(s -> s.getCustomerId() != null)
                .map(SettlementDomain::getCustomerId).distinct().collect(toList());
        CustomerDomain customerDomain = new CustomerDomain();
        customerDomain.setCustomerIds(customerIds);
        Map<String, Object> params2 = new HashMap<>();
        params2.put("customer", customerDomain);
        List<UserDomain> userDomains = new ArrayList<>();
        try {
            userDomains = titanTemplate.postJson(serverName,
                    "cimServer/user?method=queryUsersByCustomer", new HttpHeaders(), params2,
                    new TypeReference<List<UserDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userDomains.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> userIds = userDomains.stream()
                .filter(u -> u.getId() != null).map(UserDomain::getId).distinct().collect(toList());
        Map<String, Object> params3 = new HashMap<>();
        params3.put("userIds", userIds);
        List<MeterDomain> meterDomains = new ArrayList<>();
        try {
            meterDomains = titanTemplate.postJson(serverName,
                    "cimServer/meter?method=findMeterByUserIds", new HttpHeaders(), params3,
                    new TypeReference<List<MeterDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (meterDomains.isEmpty()) {
            return new ArrayList<>();
        }
        Map<Long, MeterDomain> meterDomainMap = meterDomains.stream()
                .collect(Collectors.toMap(MeterDomain::getId, v -> v, (v1, v2) -> v1));
        List<Long> meterIds = meterDomains.stream().filter(s -> s.getId() != null)
                .map(MeterDomain::getId).distinct().collect(toList());
        MeterMoneyDomain meterMoneyDomain1 = new MeterMoneyDomain();
        meterMoneyDomain1.setMeterIds(meterIds);
        meterMoneyDomain1.setMon(settlementDomain.getMon());
        List<MeterMoneyDomain> meterMoneyDomains = meterMoneyDAO.findMeterMoneyByMeterIdsAndMon(meterMoneyDomain1);
        meterMoneyDomains.forEach(m -> m.setSettlementId(meterDomainMap.get(m.getMeterId()).getSettlementId()));
        meterMoneyDomains.forEach(m -> {
            m.setAddress(settlementDomainMap.get(m.getSettlementId()).getAddress());
            m.setSettlementName(settlementDomainMap.get(m.getSettlementId()).getSettlementName());
            m.setSettlementNo(settlementDomainMap.get(m.getSettlementId()).getSettlementNo());
        });
        Map<String, List<MeterMoneyDomain>> meterDomainGroup = meterMoneyDomains.stream().
                collect(Collectors.groupingBy(m -> m.getSn() + "_" + m.getSettlementId()));
        List<MeterMoneyDomain> meterMoneyDomainList = new ArrayList<>();
        for (String snSettlementId :
                meterDomainGroup.keySet()) {
            List<MeterMoneyDomain> meterMoneyValues = meterDomainGroup.get(snSettlementId);
            if (meterDomainGroup.get(snSettlementId).get(0) != null) {
                MeterMoneyDomain meterMoneyDomain = new MeterMoneyDomain();
                BigDecimal totalPower = meterMoneyValues.stream().filter(m -> m.getTotalPower() != null)
                        .map(MeterMoneyDomain::getTotalPower)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal amount = meterMoneyValues.stream().filter(m -> m.getAmount() != null)
                        .map(MeterMoneyDomain::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal addMoney8 = meterMoneyValues.stream().filter(m -> m.getAddMoney8() != null)
                        .map(MeterMoneyDomain::getAddMoney8)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal electricityReceivable = amount.subtract(addMoney8);
                meterMoneyDomain.setTotalPower(totalPower);
                meterMoneyDomain.setAmount(amount);
                meterMoneyDomain.setAddMoney8(addMoney8);
                meterMoneyDomain.setElectricityReceivable(electricityReceivable);
                meterMoneyDomain.setSettlementNo(meterDomainGroup.get(snSettlementId).get(0).getSettlementNo());
                meterMoneyDomain.setSettlementName(meterDomainGroup.get(snSettlementId).get(0).getSettlementName());
                meterMoneyDomain.setAddress(meterDomainGroup.get(snSettlementId).get(0).getAddress());
                meterMoneyDomain.setMon(meterDomainGroup.get(snSettlementId).get(0).getMon());
                meterMoneyDomain.setSn(meterDomainGroup.get(snSettlementId).get(0).getSn());
                meterMoneyDomain.setWriteSectId(meterDomainGroup.get(snSettlementId).get(0).getWriteSectId());
                meterMoneyDomain.setBusinessPlaceCode(meterDomainGroup.get(snSettlementId).get(0).getBusinessPlaceCode());
                meterMoneyDomainList.add(meterMoneyDomain);
            }
        }
        List<Long> writeSectionIds = meterMoneyDomains.stream()
                .filter(m -> m.getWriteSectId() != null).map(MeterMoneyDomain::getWriteSectId)
                .distinct().collect(toList());
        HashMap<String, Object> params4 = new HashMap<>();
        WriteSectDomain writeSectDomain = new WriteSectDomain();
        writeSectDomain.setWriteSectionIds(writeSectionIds);
        params4.put("writeSect", writeSectDomain);
        List<WriteSectDomain> writeSectDomains = new ArrayList<>();
        try {
            writeSectDomains = titanTemplate.postJson(serverName,
                    "cimServer/writeSect?method=queryByWhere", new HttpHeaders(), params4,
                    new TypeReference<List<WriteSectDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<Long, WriteSectDomain> writeSectDomainMap = writeSectDomains.stream()
                .collect(Collectors.toMap(WriteSectDomain::getId, v -> v, (v1, v2) -> v1));
        meterMoneyDomainList.forEach(m -> {
            m.setWriteSectNo(writeSectDomainMap.get(m.getWriteSectId()).getWriteSectNo());
            m.setWriteSectName(writeSectDomainMap.get(m.getWriteSectId()).getWriteSectName());
        });
        return meterMoneyDomainList;
    }

    @Override
    public int updateList(List<MeterMoneyDomain> meterMoneyDomains) {
        meterMoneyDAO.deleteListByParam(meterMoneyDomains);
        return meterMoneyDAO.insertList(meterMoneyDomains);
    }

    @Override
    public List<MeterMongoDomain> getMeterMoneyByWhere(MeterDomain meterDomain) {
        Map<Long, MeterDomain> meterMap = meterDAO.findMeterByWhere(meterDomain).stream()
                .collect(Collectors.toMap(MeterDomain::getId, v -> v));
        List<Document> documents = meterMoneyDAO.getMeterMoneyList(meterDomain.getMon().toString(), meterMap.keySet(),
                meterDomain);
        List<MeterMongoDomain> meterMongoDomains = GsonUtils.readValueToList(GsonUtils.toJson(documents),
                MeterMongoDomain.class);
        meterMongoDomains.forEach(m -> {
            if (m.getMeterMoneyDomain() != null) {
                m.setMon(m.getMeterMoneyDomain().getMon().toString());
                m.setTotalPower(m.getMeterMoneyDomain().getTotalPower());
                m.setAmount(m.getMeterMoneyDomain().getAmount());
                m.getMeterMoneyDomain().setMeterNo(m.getMeterNo());
                m.getMeterMoneyDomain().getMeterDataInfo().forEach(mm -> {
                    mm.setMeterNo(m.getMeterNo());
                });

                m.setMsType(m.getMeterMoneyDomain().getMsType());
                m.setIsCal(1);
                m.setComputeLog(m.getMeterMoneyDomain().getComputeLog());
            } else {
                m.setIsCal(0);
            }

        });
        return meterMongoDomains;
    }

    @Override
    public List<MeterMongoDomain> getMeterMoney(MeterDomain meterDomain) {
        Map<String, Object> params = new HashMap<>();
        params.put("bankNo", meterDomain.getBankNo());
        List<SettlementDomain> settlementDomains = new ArrayList<>();
        try {
            settlementDomains = titanTemplate.postJson("CIM-SERVER",
                    "cimServer/settlement?method=getSettlements", new HttpHeaders(), params,
                    new TypeReference<List<SettlementDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> params2 = new HashMap<>();
        params2.put("settlement", settlementDomains);
        List<SettlementMeterRelDomain> settlementMeterRelDomains = new ArrayList<>();
        try {
            settlementMeterRelDomains = titanTemplate.postJson("CIM-SERVER",
                    "cimServer/settlementMeterRel?method=getMeterIdsBySettlements", new HttpHeaders(), params2,
                    new TypeReference<List<SettlementMeterRelDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Long> meterIds = new ArrayList<>();
        if (!settlementDomains.isEmpty()) {
            for (SettlementMeterRelDomain settlementMeterRelDomain :
                    settlementMeterRelDomains) {
                meterIds.add(settlementMeterRelDomain.getMeterId());
            }
        }
        Map<Long, MeterDomain> meterMap = meterDAO.getMeters(meterDomain, meterIds).stream()
                .collect(Collectors.toMap(MeterDomain::getId, v -> v));
        List<MeterMongoDomain> meterMongoDomains = new ArrayList<>();
        if (!(meterMap.isEmpty())) {
            List<Document> documents = meterMoneyDAO.getMeterMoneyList(meterDomain.getMon().toString(), meterMap.keySet(),
                    meterDomain);
            meterMongoDomains = GsonUtils.readValueToList(GsonUtils.toJson(documents),
                    MeterMongoDomain.class);
            meterMongoDomains.forEach(m -> {
                if (m.getMeterMoneyDomain() != null) {
                    m.setMon(m.getMeterMoneyDomain().getMon().toString());
                    m.setTotalPower(m.getMeterMoneyDomain().getTotalPower());
                    m.setAmount(m.getMeterMoneyDomain().getAmount());
                    m.getMeterMoneyDomain().setMeterNo(m.getMeterNo());
                    m.getMeterMoneyDomain().getMeterDataInfo().forEach(mm -> {
                        mm.setMeterNo(m.getMeterNo());
                    });

                    m.setMsType(m.getMeterMoneyDomain().getMsType());
                    m.setIsCal(1);
                    m.setComputeLog(m.getMeterMoneyDomain().getComputeLog());
                } else {
                    m.setIsCal(0);
                }

            });
            return meterMongoDomains;
        }
        return meterMongoDomains;
    }

    @Override
    public List<MeterMoneyDomain> findHistoricalMeterMoney(MeterMoneyDomain meterMoneyDomain) {
        CustomerDomain customerDomain = new CustomerDomain();
        customerDomain.setId(meterMoneyDomain.getCustomerId());
        Map<String, Object> params = new HashMap<>();
        params.put("customer", customerDomain);
        List<UserDomain> userDomains = new ArrayList<>();
        try {
            userDomains = titanTemplate.postJson("CIM-SERVER",
                    "cimServer/user?method=queryUsersByCustomer", new HttpHeaders(), params,
                    new TypeReference<List<UserDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userDomains.isEmpty()) {
            return new ArrayList<>(0);
        }
        Map<String, Object> params2 = new HashMap<>();
        MeterDomain meterDomain = new MeterDomain();
        meterDomain.setCustomerId(meterMoneyDomain.getCustomerId());
        params2.put("meter", meterDomain);
        List<MeterDomain> meterDomains = new ArrayList<>();
        try {
            meterDomains = titanTemplate.postJson("CIM-SERVER",
                    "cimServer/meter?method=queryMetersByCustomer", new HttpHeaders(), params2,
                    new TypeReference<List<MeterDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (meterDomains.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> meterIds = meterDomains.stream().filter(m -> m.getId() != null)
                .map(MeterDomain::getId)
                .distinct().collect(toList());
        meterMoneyDomain.setMeterIds(meterIds);
        List<MeterMoneyDomain> meterMoneyDomains = meterMoneyDAO.selectByMeterMoney(meterMoneyDomain);

        Map<Long, SettlementDomain> meterSettlementRelMap = null;

        List<SettlementDomain> settlementDomains =
                cimService.getSettlementbyMeterIds(meterIds);

        if (settlementDomains != null && settlementDomains.size() > 0) {

            Map<Long, SettlementDomain> settlementDomainMap =
                    settlementDomains.stream().collect(Collectors.toMap(SettlementDomain::getId, a -> a, (k1, k2) -> k1));

            //获取结算户与计量点关系
            List<SettlementMeterRelDomain> settlementMeterRelDomains =
                    cimService.getSettlementMeterRelByMeterIds(meterIds);

            //获取计量点和结算户map
            meterSettlementRelMap =
                    settlementMeterRelDomains.stream().filter(t -> t.getMeterId() != null).filter(t -> t.getSettlementId() != null)
                            .collect(Collectors.toMap(SettlementMeterRelDomain::getMeterId, a -> settlementDomainMap.get(a.getSettlementId()), (k1, k2) -> k1));
        }


        Map<Long, MeterDomain> meterDomainMap = meterDomains.stream()
                .collect(Collectors.toMap(MeterDomain::getId, a -> a, (k1, k2) -> k1));
        meterMoneyDomains.forEach(m -> {
            if (m.getMeterId() != null) {
                m.setMeterNo(meterDomainMap.get(m.getMeterId()).getMeterNo());
                m.setMeterName(meterDomainMap.get(m.getMeterId()).getMeterName());
            }
        });
        Map<Long, UserDomain> userDomainMap = userDomains.stream()
                .collect(Collectors.toMap(UserDomain::getId, a -> a, (k1, k2) -> k1));
        meterMoneyDomains.forEach(m -> {
            if (m.getMeterId().equals(meterDomainMap.get(m.getMeterId()).getId()) && meterDomainMap.get(m.getMeterId()) != null) {
                m.setUserNo(userDomainMap.get(meterDomainMap.get(m.getMeterId()).getUserId()).getUserNo());
                m.setUserName(userDomainMap.get(meterDomainMap.get(m.getMeterId()).getUserId()).getUserName());
            }
        });
        List<Long> writeSectIds = userDomains.stream().filter(u -> u.getWriteSectId() != null)
                .map(UserDomain::getWriteSectId).distinct().collect(toList());
        WriteSectDomain writeSectDomain = new WriteSectDomain();
        writeSectDomain.setWriteSectionIds(writeSectIds);
        Map<String, Object> params3 = new HashMap<>();
        params3.put("writeSect", writeSectDomain);
        List<WriteSectDomain> writeSectDomains = new ArrayList<>();
        try {
            writeSectDomains = titanTemplate.postJson("CIM-SERVER",
                    "cimServer/writeSect?method=queryByWhere", new HttpHeaders(), params3,
                    new TypeReference<List<WriteSectDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (writeSectDomains.isEmpty()) {
            return new ArrayList<>(0);
        }
        Map<Long, WriteSectDomain> writeSectDomainMap = writeSectDomains.stream()
                .collect(Collectors.toMap(WriteSectDomain::getId, a -> a, (k1, k2) -> k1));
        meterMoneyDomains.forEach(m -> {
            if (writeSectDomainMap.get(m.getWriteSectId()) != null) {
                m.setWriteSectNo(writeSectDomainMap.get(m.getWriteSectId()).getWriteSectNo());
                m.setWriteSectName(writeSectDomainMap.get(m.getWriteSectId()).getWriteSectName());
            }
        });
        List<MeterMoneyDomain> newMeterMoneyDomainList = new ArrayList<>();
        Map<Integer, List<MeterMoneyDomain>> meterMoneyDomainMap = meterMoneyDomains.stream()
                .collect(Collectors.groupingBy(MeterMoneyDomain::getMon));
        for (Map.Entry<Integer, List<MeterMoneyDomain>> meterMoneyDomainKey : meterMoneyDomainMap.entrySet()) {
            Integer key = meterMoneyDomainKey.getKey();
            List<MeterMoneyDomain> meterMoneyDomainList = meterMoneyDomainKey.getValue();
            MeterMoneyDomain meterMoney = new MeterMoneyDomain();
            meterMoney.setMeterId(meterMoneyDomainList.get(0).getMeterId());
            Long userId = meterMoneyDomainList.get(0).getUserId();
            meterMoney.setUserId(userId);
            String userName = meterMoneyDomainList.get(0).getUserName();
            meterMoney.setUserName(userName);
            String userNo = meterMoneyDomainList.get(0).getUserNo();
            meterMoney.setUserNo(userNo);
            Long customerId = meterMoneyDomainList.get(0).getCustomerId();
            meterMoney.setCustomerId(customerId);
            Integer mon = meterMoneyDomainList.get(0).getMon();
            meterMoney.setMon(mon);
            Byte sn = meterMoneyDomainList.get(0).getSn();
            meterMoney.setSn(sn);
            //发行人
            meterMoney.setPunishMan(meterMoneyDomainList.get(0).getPunishMan());
//            checkingEnergy
            BigDecimal checkingEnergy =
                    meterMoneyDomainList.stream().filter(t -> t.getTotalPower() != null).filter(t -> Long.valueOf(101).equals(t.getPriceTypeId())).map(MeterMoneyDomain::getTotalPower)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
            meterMoney.setCheckingEnergy(checkingEnergy);
            // totalPower
            BigDecimal totalPower =
                    meterMoneyDomainList.stream().filter(t -> t.getTotalPower() != null).filter(t -> !Long.valueOf(101).equals(t.getPriceTypeId())).map(MeterMoneyDomain::getTotalPower)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
            meterMoney.setTotalPower(totalPower);
            BigDecimal amount = meterMoneyDomainList.stream().filter(t -> t.getAmount() != null).map(MeterMoneyDomain::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            meterMoney.setAmount(amount);
            // refundMoney 退补电费
            BigDecimal refundMoney = meterMoneyDomainList.stream().filter(t -> t.getRefundMoney() != null).map(MeterMoneyDomain::getRefundMoney)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            meterMoney.setRefundMoney(refundMoney.negate());
            // 应收电费 electricityReceivable
            meterMoney.setTotalMoney(meterMoney.getAmount().add(meterMoney.getRefundMoney().negate()));
            // powerRateMoney力率电费
            BigDecimal powerRateMoney = meterMoneyDomainList.stream().filter(t -> t.getPowerRateMoney() != null).map(MeterMoneyDomain::getPowerRateMoney)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            meterMoney.setPowerRateMoney(powerRateMoney);
            // basicMoney;// 基本电费
            BigDecimal basicMoney = meterMoneyDomainList.stream().filter(t -> t.getBasicMoney() != null).map(MeterMoneyDomain::getBasicMoney)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            meterMoney.setBasicMoney(basicMoney);
            // volumeCharge;// 电度电费
            BigDecimal volumeCharge = meterMoneyDomainList.stream().filter(t -> t.getVolumeCharge() != null).map(MeterMoneyDomain::getVolumeCharge)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            meterMoney.setVolumeCharge(volumeCharge);
            //
            BigDecimal addMoney1 =
                    meterMoneyDomainList.stream().filter(t -> t.getAddMoney1() != null).map(MeterMoneyDomain::getAddMoney1).reduce(BigDecimal.ZERO, BigDecimal::add);
            meterMoney.setAddMoney1(addMoney1);
            BigDecimal addMoney2 = meterMoneyDomainList.stream().filter(t -> t.getAddMoney2() != null).map(MeterMoneyDomain::getAddMoney2)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            meterMoney.setAddMoney2(addMoney2);
            BigDecimal addMoney3 = meterMoneyDomainList.stream().filter(t -> t.getAddMoney3() != null).map(MeterMoneyDomain::getAddMoney3)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            meterMoney.setAddMoney3(addMoney3);
            BigDecimal addMoney4 = meterMoneyDomainList.stream().filter(t -> t.getAddMoney4() != null).map(MeterMoneyDomain::getAddMoney4)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            meterMoney.setAddMoney4(addMoney4);
            BigDecimal addMoney5 = meterMoneyDomainList.stream().filter(t -> t.getAddMoney5() != null).map(MeterMoneyDomain::getAddMoney5)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            meterMoney.setAddMoney5(addMoney5);
            BigDecimal addMoney6 = meterMoneyDomainList.stream().filter(t -> t.getAddMoney6() != null).map(MeterMoneyDomain::getAddMoney6)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            meterMoney.setAddMoney6(addMoney6);
            BigDecimal addMoney7 =
                    meterMoneyDomainList.stream().filter(t -> t.getAddMoney7() != null).map(MeterMoneyDomain::getAddMoney7)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
            meterMoney.setAddMoney7(addMoney7);
            BigDecimal addMoney8 =
                    meterMoneyDomainList.stream().filter(t -> t.getAddMoney8() != null).map(MeterMoneyDomain::getAddMoney8)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
            meterMoney.setAddMoney8(addMoney8);
            //Byte status = meterMoneyDomainList.get(0).getStatus();
            //meterMoney.setStatus(status);
            if (meterSettlementRelMap != null && meterSettlementRelMap.get(meterMoney.getMeterId()) != null) {
                meterMoney.setSettlementNo(meterSettlementRelMap.get(meterMoney.getMeterId()).getSettlementNo());
            }
            newMeterMoneyDomainList.add(meterMoney);
        }
        newMeterMoneyDomainList =
                newMeterMoneyDomainList.stream().sorted(Comparator.comparing(MeterMoneyDomain::getMon).reversed()).collect(Collectors.toList());
        return newMeterMoneyDomainList;
    }

    // 电费明细--合计--JAVA中处理所有数据
//    @Override
//    public MeterMoneySumMongoDomain getMeterMoneySum(MeterDomain meterDomain) {
//
//        Map<Long, MeterDomain> meterMap = meterDAO.findMeterByWhere(meterDomain).stream().collect(Collectors.toMap(MeterDomain::getId, v -> v));
//
//        List<MeterMoneyDomain> meterMoneyDomains = meterMoneyDAO.getMeterMoneySum(meterDomain, meterMap.keySet());
//
//        MeterMoneySumMongoDomain meterMoneySumMongoDomain = new MeterMoneySumMongoDomain();
//
//        meterMoneySumMongoDomain.setAmount(meterMoneyDomains.parallelStream().map(MeterMoneyDomain::getAmount).reduce(BigDecimal.ZERO,BigDecimal::add));
//        meterMoneySumMongoDomain.setTotalPower(meterMoneyDomains.parallelStream().map(MeterMoneyDomain::getTotalPower).reduce(BigDecimal.ZERO,BigDecimal::add));
//        meterMoneySumMongoDomain.setBasicMoney(meterMoneyDomains.parallelStream().map(MeterMoneyDomain::getBasicMoney).reduce(BigDecimal.ZERO,BigDecimal::add));
//        meterMoneySumMongoDomain.setPowerRateMoney(meterMoneyDomains.parallelStream().map(MeterMoneyDomain::getPowerRateMoney).reduce(BigDecimal.ZERO,BigDecimal::add));
//        meterMoneySumMongoDomain.setSurcharges(meterMoneyDomains.parallelStream().map(MeterMoneyDomain::getSurcharges).reduce(BigDecimal.ZERO,BigDecimal::add));
//        meterMoneySumMongoDomain.setCalCapacity(meterMoneyDomains.parallelStream().map(MeterMoneyDomain::getCalCapacity).reduce(BigDecimal.ZERO,BigDecimal::add));
//        meterMoneySumMongoDomain.setVolumeCharge(meterMoneyDomains.parallelStream().map(MeterMoneyDomain::getVolumeCharge).reduce(BigDecimal.ZERO,BigDecimal::add));
//
//        return meterMoneySumMongoDomain;
//    }
    @Override
    public List<MeterMoneySumMongoDomain> getMeterMoneySum(MeterDomain meterDomain) {

        Map<Long, MeterDomain> meterMap = meterDAO.findMeterByWhere(meterDomain).stream()
                .collect(Collectors.toMap(MeterDomain::getId, v -> v));

        String documents = meterMoneyDAO.getMeterMoneySum(meterDomain, meterMap.keySet());
        List<MeterMoneySumMongoDomain> meterMoneySumMongoDomains = GsonUtils.readValueToList(documents,
                MeterMoneySumMongoDomain.class);

        return meterMoneySumMongoDomains;
    }

    @Override
    public MeterMoneyDomain findByKey(MeterMoneyDomain meterMoneyDomain) {
        return meterMoneyDAO.findByKey(meterMoneyDomain);
    }

    @Override
    public long dataValidation(List<MeterDomain> meterDomains) {
        return meterDAO.mongoDataValidaUpdate(meterDomains);
    }

    @Override
    public List<MeterMoneyDomain> findByMeterIds(String meterIds) {
        return meterMoneyDAO.findByMeterIds(meterIds);
    }

    @Override
    public List<MeterMoneyDomain> ladderPowerQuery(MeterMoneyDomain meterMoneyDomain) {
        return meterMoneyDAO.ladderPowerQuery(meterMoneyDomain);
    }

}
