package org.fms.billing.server.webapp.service;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.fms.billing.common.util.CustomCollectors;
import org.fms.billing.common.webapp.domain.AppBulkRefund;
import org.fms.billing.common.webapp.domain.AppMoneyRecall;
import org.fms.billing.common.webapp.domain.ArrearageDomain;
import org.fms.billing.common.webapp.domain.ChargeDomain;
import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.MeterMoneyDomain;
import org.fms.billing.common.webapp.domain.NoteInfoDomain;
import org.fms.billing.common.webapp.domain.PreChargeDomain;
import org.fms.billing.common.webapp.domain.SettlementMeterRelDomain;
import org.fms.billing.common.webapp.domain.beakInterface.SettlementDomain;
import org.fms.billing.common.webapp.domain.mongo.LadderMongoDomain;
import org.fms.billing.common.webapp.domain.mongo.MeterMongoDomain;
import org.fms.billing.common.webapp.entity.PulishEntity;
import org.fms.billing.common.webapp.service.INoteInfoService;
import org.fms.billing.common.webapp.service.IPublishService;
import org.fms.billing.server.webapp.dao.ArrearageDAO;
import org.fms.billing.server.webapp.dao.ChargeDAO;
import org.fms.billing.server.webapp.dao.MeterDAO;
import org.fms.billing.server.webapp.dao.NoteInfoDAO;
import org.fms.billing.server.webapp.dao.PreChargeDAO;
import org.fms.billing.server.webapp.dao.PublishDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;
import com.riozenc.titanTool.properties.Global;
import com.riozenc.titanTool.spring.web.client.TitanTemplate;
import com.riozenc.titanTool.spring.web.http.HttpResult;

@TransactionService
public class PublishServiceImpl implements IPublishService {

    @TransactionDAO
    private PublishDAO publishDAO;
    @TransactionDAO
    private PreChargeDAO preChargeDAO;

    @Autowired
    private CimService cimService;

    @Autowired
    @Qualifier("noteInfoServiceImpl")
    private INoteInfoService iNoteInfoService;


    @Autowired
    private TitanTemplate titanTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ChargeDAO chargeDAO;

    @Autowired
    private NoteInfoDAO noteInfoDAO;

    @Autowired
    private ArrearageDAO arrearageDAO;

    @Autowired
    private MeterDAO meterDAO;

    @Override
    @Transactional
    public HttpResult<?> publishCallback(PulishEntity pulishEntity, String managerId) throws Exception {


        // 处理欠费和余额
        if (pulishEntity.getArrearageDomains().size() == pulishEntity.getMeterMoneyDomains().size()) {

            // 更新抄表
            System.out.println("插入抄表记录================" + new Date());
            publishDAO.deleteWriteFilesByParam(pulishEntity.getWriteFilesDomains());
            publishDAO.insertWriteFilesList(pulishEntity.getWriteFilesDomains());
            // 更新电费

            List<MeterMoneyDomain> meterMoneyDomains =
                    pulishEntity.getMeterMoneyDomains();

            List<MeterMoneyDomain> negativeMeterMoneys =
                    meterMoneyDomains.stream().filter(t -> t.getAmount().compareTo(BigDecimal.ZERO) < 0).collect(toList());

            if (negativeMeterMoneys != null && negativeMeterMoneys.size() > 0) {
                List<Long> meterIds =
                        negativeMeterMoneys.stream().map(MeterMoneyDomain::getMeterId).distinct().collect(toList());
                MeterDomain m = new MeterDomain();
                m.setMon(negativeMeterMoneys.get(0).getMon());
                m.setIds(meterIds);
                List<MeterDomain> meterList = meterDAO.findMeterDomain(m);
                String returnJson =
                        meterList.stream().filter(t -> t.getUserNo() != null).map(MeterDomain::getUserNo).collect(Collectors.joining(","));
                return new HttpResult<>(HttpResult.ERROR,
                        "发行失败：存在负电费情况，户号为" + returnJson);
            }

            for (MeterMoneyDomain t : meterMoneyDomains) {
                List<LadderMongoDomain> ladderMongoDomains = t.getLadderDataModels();
                //赋值阶梯数据
                if (ladderMongoDomains != null && ladderMongoDomains.size() > 0) {
                    Map<Integer, LadderMongoDomain> ladderMongoDomainMap =
                            ladderMongoDomains.stream().collect(Collectors.toMap(LadderMongoDomain::getLadderSn, a -> a, (k1, k2) -> k1));
                    t.setLadder1Limit(ladderMongoDomainMap.get(1) == null ?
                            BigDecimal.ZERO :
                            ladderMongoDomainMap.get(1).getLadderValue());
                    t.setLadder2Limit(ladderMongoDomainMap.get(2) == null ?
                            BigDecimal.ZERO :
                            ladderMongoDomainMap.get(2).getLadderValue());
                    t.setLadder3Limit(ladderMongoDomainMap.get(3) == null ?
                            BigDecimal.ZERO :
                            ladderMongoDomainMap.get(3).getLadderValue());
                    t.setLadder4Limit(ladderMongoDomainMap.get(4) == null ?
                            BigDecimal.ZERO :
                            ladderMongoDomainMap.get(4).getLadderValue());

                    t.setLadder1Power(ladderMongoDomainMap.get(1) == null ?
                            BigDecimal.ZERO :
                            ladderMongoDomainMap.get(1).getChargePower());
                    t.setLadder2Power(ladderMongoDomainMap.get(2) == null ?
                            BigDecimal.ZERO :
                            ladderMongoDomainMap.get(2).getChargePower());
                    t.setLadder3Power(ladderMongoDomainMap.get(3) == null ?
                            BigDecimal.ZERO :
                            ladderMongoDomainMap.get(3).getChargePower());
                    t.setLadder4Power(ladderMongoDomainMap.get(4) == null ?
                            BigDecimal.ZERO :
                            ladderMongoDomainMap.get(4).getChargePower());

                    t.setLadder1Money(ladderMongoDomainMap.get(1) == null ?
                            BigDecimal.ZERO :
                            ladderMongoDomainMap.get(1).getAmount());
                    t.setLadder2Money(ladderMongoDomainMap.get(2) == null ?
                            BigDecimal.ZERO :
                            ladderMongoDomainMap.get(2).getAmount());
                    t.setLadder3Money(ladderMongoDomainMap.get(3) == null ?
                            BigDecimal.ZERO :
                            ladderMongoDomainMap.get(3).getAmount());
                    t.setLadder4Money(ladderMongoDomainMap.get(4) == null ?
                            BigDecimal.ZERO :
                            ladderMongoDomainMap.get(4).getAmount());
                }

                t.setAddMoney1(BigDecimal.ZERO);
                t.setAddMoney2(BigDecimal.ZERO);
                t.setAddMoney3(BigDecimal.ZERO);
                t.setAddMoney4(BigDecimal.ZERO);
                t.setAddMoney5(BigDecimal.ZERO);
                t.setAddMoney6(BigDecimal.ZERO);
                t.setAddMoney7(BigDecimal.ZERO);
                t.setAddMoney8(BigDecimal.ZERO);
                t.setAddMoney9(BigDecimal.ZERO);
                t.setAddMoney10(BigDecimal.ZERO);
                t.setPunishMan(managerId == null ? null : Long.valueOf(managerId));
                for (int i = 0; i <= 4; i++) {
                    BigDecimal addMoney1 =
                            t.getSurchargeDetail().get(t.getPriceTypeId() + "#" + "2" + "#" + i);
                    if (addMoney1 != null) {
                        t.setAddMoney1(t.getAddMoney1().add(addMoney1));
                    }
                    BigDecimal addMoney2 =
                            t.getSurchargeDetail().get(t.getPriceTypeId() + "#" + "3" + "#" + i);
                    if (addMoney2 != null) {
                        t.setAddMoney2(t.getAddMoney2().add(addMoney2));
                    }
                    BigDecimal addMoney3 =
                            t.getSurchargeDetail().get(t.getPriceTypeId() + "#" + "4" + "#" + i);
                    if (addMoney3 != null) {
                        t.setAddMoney3(t.getAddMoney3().add(addMoney3));
                    }
                    BigDecimal addMoney4 =
                            t.getSurchargeDetail().get(t.getPriceTypeId() + "#" + "5" + "#" + i);
                    if (addMoney4 != null) {
                        t.setAddMoney4(t.getAddMoney4().add(addMoney4));
                    }
                    BigDecimal addMoney5 =
                            t.getSurchargeDetail().get(t.getPriceTypeId() + "#" + "6" + "#" + i);
                    if (addMoney5 != null) {
                        t.setAddMoney5(t.getAddMoney5().add(addMoney5));
                    }
                    BigDecimal addMoney6 =
                            t.getSurchargeDetail().get(t.getPriceTypeId() + "#" + "7" + "#" + i);
                    if (addMoney6 != null) {
                        t.setAddMoney6(t.getAddMoney6().add(addMoney6));
                    }
                    BigDecimal addMoney7 =
                            t.getSurchargeDetail().get(t.getPriceTypeId() + "#" + "8" + "#" + i);
                    if (addMoney7 != null) {
                        t.setAddMoney7(t.getAddMoney7().add(addMoney7));
                    }
                    BigDecimal addMoney8 =
                            t.getSurchargeDetail().get(t.getPriceTypeId() + "#" + "9" + "#" + i);
                    if (addMoney8 != null) {
                        t.setAddMoney8(t.getAddMoney8().add(addMoney8));
                    }
                    BigDecimal addMoney9 =
                            t.getSurchargeDetail().get(t.getPriceTypeId() + "#" + "10" + "#" + i);
                    if (addMoney9 != null) {
                        t.setAddMoney9(t.getAddMoney9().add(addMoney9));
                    }
                    BigDecimal addMoney10 =
                            t.getSurchargeDetail().get(t.getPriceTypeId() + "#" + "11" + "#" + i);
                    if (addMoney10 != null) {
                        t.setAddMoney10(t.getAddMoney10().add(addMoney10));
                    }
                }
            }
            ;
            System.out.println("插入电费记录================" + new Date());
            publishDAO.deleteMeterMoneyByParam(pulishEntity.getMeterMoneyDomains());
            publishDAO.insertMeterMoneyList(pulishEntity.getMeterMoneyDomains());

            //获取欠费流水号
            Map<String, Object> params = new HashMap<>();
            params.clear();
            params.put("strategy", null);
            params.put("size", pulishEntity.getArrearageDomains().size());

            String arrearageJson = restTemplate.postForObject(Global.getConfig(
                    "getArrearageNoBySize"), params, String.class);


            Long arrearageNo = JSONObject.parseObject(arrearageJson, Long.class);

            //赋值违约金 欠费号 操作人 欠费
            for (int i = 0; i < pulishEntity.getArrearageDomains().size(); i++) {
                pulishEntity.getArrearageDomains().get(i).setPunishMoney(BigDecimal.ZERO);
                pulishEntity.getArrearageDomains().get(i).setArrearageNo(String.valueOf(arrearageNo + i));
                pulishEntity.getArrearageDomains().get(i).setOperator(managerId);
                pulishEntity.getArrearageDomains().get(i).setIsLock((short) 0);
                pulishEntity.getArrearageDomains().get(i).setOweMoney(pulishEntity.getArrearageDomains().get(i).getReceivable());
            }
            //退费冲抵
            PulishEntity returnPulishEntity =
                    processMoneyRecall(pulishEntity.getArrearageDomains());

            pulishEntity.setPreChargeDomains(returnPulishEntity.getPreChargeDomains());
            pulishEntity.setChargeDomains(returnPulishEntity.getChargeDomains());

            //余额冲抵后处理的欠费记录
            System.out.println("余额冲抵开始================" + new Date());
            PulishEntity returnPulish = processArrearage(pulishEntity, (long) 9999);


            // 更新欠费
            System.out.println("插入欠费记录================" + new Date());
            publishDAO.deleteArrearageByParam(pulishEntity.getArrearageDomains());

            //赋值应收
            pulishEntity.getArrearageDomains().stream().filter(t -> t.getShouldMoney() == null).forEach(t -> {
                t.setShouldMoney(t.getReceivable());
            });
            publishDAO.insertArrearageList(pulishEntity.getArrearageDomains());
            System.out.println("欠费记录插入完成================" + new Date());
            //退费更新
            if (returnPulishEntity.getAppMoneyRecalls() != null && returnPulishEntity.getAppMoneyRecalls().size() > 0) {
                JSONObject postData = new JSONObject();
                //单户退费
                List<AppMoneyRecall> appMoneyRecalls =
                        returnPulishEntity.getAppMoneyRecalls().stream().filter(t -> t.getRecallType() == 1).collect(toList());
                if (appMoneyRecalls != null && appMoneyRecalls.size() > 0) {
                    postData.clear();
                    postData.put("appMoneyRecallList", appMoneyRecalls);
                    restTemplate.postForObject(Global.getConfig(
                            "updateBillingMoneyRecall"), postData, String.class);
                }

                List<AppMoneyRecall> appBulkMoneyRecalls =
                        returnPulishEntity.getAppMoneyRecalls().stream().filter(t -> t.getRecallType() == 2).collect(toList());
                if (appBulkMoneyRecalls != null && appBulkMoneyRecalls.size() > 0) {
                    //批量退费
                    postData.clear();
                    postData.put("appMoneyBulkRecallList",
                            returnPulishEntity.getAppMoneyRecalls().stream().filter(t -> t.getRecallType() == 2));
                    restTemplate.postForObject(Global.getConfig(
                            "updateBillingBulkRefundRecall"), postData, String.class);
                }
            }
            System.out.println("退费处理完成================" + new Date());
            // 更新arrearage的metermoneyId
            //测试 前面事务不提交可能出现java.sql.BatchUpdateException问题
            //publishDAO.updateMoneyItemId(currentMon);
            // publishDAO.updateChargeItemId(currentMon);

            return new HttpResult<>(HttpResult.SUCCESS, "发行成功",
                    returnPulish.getLastPreChargeDomains());
        } else {
            return new HttpResult<>(HttpResult.ERROR, "发行失败:计算程序处理的欠费和电费条数不相等");
        }

    }

    /**
     * 应收回退
     *
     * @param pulishEntity
     * @param managerId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public HttpResult<?> backPublishCallback(PulishEntity pulishEntity,
                                             String managerId) throws Exception {
        List<ArrearageDomain> arrearageDomains =
                arrearageDAO.findByMeterIdsMonAndSn(pulishEntity);
        // 处理欠费和余额
        if (arrearageDomains != null && arrearageDomains.size() > 0) {
            // 删除抄表
            publishDAO.deleteWriteFilesByMeterIdsMonAndSn(pulishEntity);
            // 删除电费
            publishDAO.deleteMeterMoneyByMeterIdsMonAndSn(pulishEntity);

            // 删除欠费
            publishDAO.deleteArrearageByMeterIdsMonAndSn(pulishEntity);

            List<Long> meterIds = pulishEntity.getMeterIds();

            Map<String, Object> map = new HashMap<>();
            map.put("meterIds", meterIds);
            map.put("sn", pulishEntity.getSn());
            map.put("mon", pulishEntity.getMon());
            List<ChargeDomain> chargeDomains =
                    chargeDAO.findProcessArrearageByMeterIds(map);
            if (chargeDomains != null && chargeDomains.size() > 0) {

                List<Long> chargeInfoIds =
                        chargeDomains.stream().map(ChargeDomain::getId).collect(toList());

                Map<Long, List<ChargeDomain>> chargeBySettleIdMap =
                        chargeDomains.stream().collect(Collectors.groupingBy(ChargeDomain::getSettlementId));

                List<Long> settleIds =
                        new ArrayList<>(chargeBySettleIdMap.keySet());

                List<PreChargeDomain> preChargeDomains =
                        preChargeDAO.findAllPreChargeBySettleIds(settleIds);

                List<PreChargeDomain> updatePreCharges = new ArrayList<>();

                Map<Long, PreChargeDomain> preChargeDomainMap =
                        preChargeDomains.stream().collect(Collectors.toMap(PreChargeDomain::getSettlementId, a -> a, (k1, k2) -> k1));

                BigDecimal yecdFactMoney = BigDecimal.ZERO;
                BigDecimal zycFactMoney = BigDecimal.ZERO;
                for (Long key : chargeBySettleIdMap.keySet()) {
                    //余额冲抵
                    yecdFactMoney =
                            chargeBySettleIdMap.get(key).stream().filter(t -> t.getfChargeMode() == 4)
                                    .map(ChargeDomain::getFactMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
                    //退费
                    zycFactMoney =
                            chargeBySettleIdMap.get(key).stream().filter(t -> t.getfChargeMode() == 6)
                                    .map(ChargeDomain::getFactMoney).reduce(BigDecimal.ZERO, BigDecimal::add);

                    preChargeDomainMap.get(key).setBalance(preChargeDomainMap.get(key).getBalance().add(yecdFactMoney).subtract(zycFactMoney));
                    updatePreCharges.add(preChargeDomainMap.get(key));
                }


                preChargeDAO.updateList(updatePreCharges);
                NoteInfoDomain noteInfoDomain = new NoteInfoDomain();
                noteInfoDomain.setChargeInfoIds(chargeInfoIds);
                publishDAO.deleteNoteInfoChargeIds(noteInfoDomain);
                publishDAO.deleteChargeByParam(chargeDomains);

                if (zycFactMoney.compareTo(BigDecimal.ZERO) != 0) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("settlementIds",
                            settleIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
                    jsonObject.put("monSn", pulishEntity.getSn());
                    jsonObject.put("mon", pulishEntity.getMon());
                    //单户退费
                    restTemplate.postForObject(Global.getConfig(
                            "billingFindFinishAppMoneyRecall"), jsonObject, String.class);
                    //批量退费
                    restTemplate.postForObject(Global.getConfig(
                            "billingFindAppBulkRefundRecall"), jsonObject, String.class);
                }
            }

            //更新mongo计量点状态
            List<MeterMongoDomain> meterMongoDomains = new ArrayList<>();
            arrearageDomains.forEach(t -> {
                MeterMongoDomain meterMongoDomain = new MeterMongoDomain();
                meterMongoDomain.setId(t.getMeterId());
                meterMongoDomain.setMon(t.getMon().toString());
                meterMongoDomain.setSn(t.getSn().byteValue());
                meterMongoDomains.add(meterMongoDomain);
            });
            if (meterMongoDomains != null && meterMongoDomains.size() > 0) {
                meterDAO.mongoUpdateStatus(meterMongoDomains);
            }

            return new HttpResult<>(HttpResult.SUCCESS, "回退成功");
        } else {
            return new HttpResult<>(HttpResult.ERROR, "回退失败：无要回退的欠费信息");
        }

    }

    // 针对余额足够的结算户，直接结清电费 生成发票
    @Override
    @Transactional
    public PulishEntity processArrearage(PulishEntity pulishEntity,
                                         Long managerId) throws Exception {

        Integer fChargeMode = pulishEntity.getfChargeMode() == null ? 4 : pulishEntity.getfChargeMode();
        // 根据欠费获取meterId 过滤掉负电费
        List<ArrearageDomain> arrearageDomains = pulishEntity.getArrearageDomains();

        List<Long> meterIds =
                arrearageDomains.stream().filter(t -> t.getMeterId() != null).filter(t -> t.getOweMoney().compareTo(BigDecimal.ZERO) >= 0).map(ArrearageDomain::getMeterId).distinct().collect(toList());


        PulishEntity returnPulishEntity = new PulishEntity();
        if (meterIds == null || meterIds.size() < 1) {
            returnPulishEntity.setArrearageDomains(arrearageDomains);
            return returnPulishEntity;
        }

        // 欠费
        Map<Long, List<ArrearageDomain>> arrearageMap =
                arrearageDomains.stream()
                        .filter(t -> t.getOweMoney().compareTo(BigDecimal.ZERO) >= 0)
                        .collect(Collectors.groupingBy(ArrearageDomain::getMeterId));

        Map<String, List<Long>> params = new HashMap<>();
        params.put("meterIds", meterIds);
        // 根据计量点获取结算户与计量点关系
        HttpResult<List<SettlementMeterRelDomain>> httpResult = titanTemplate.postJson("CIM-SERVER",
                "cimServer/cim_bill?method=getSettlementByBillingMeterIds", new HttpHeaders(), params,
                new TypeReference<HttpResult<List<SettlementMeterRelDomain>>>() {
                });

        // 根据结算户分组

        List<SettlementMeterRelDomain> settlementMeterRelDomains = httpResult.getResultData();
        Map<Long, List<SettlementMeterRelDomain>> settlementMeterRelMap = settlementMeterRelDomains.stream()
                .collect(Collectors.groupingBy(SettlementMeterRelDomain::getSettlementId));


        List<Long> allSettlementIds =
                settlementMeterRelDomains.stream().map(SettlementMeterRelDomain::getSettlementId).distinct().collect(toList());
        // 获取结算户的余额信息
        List<PreChargeDomain> allPreChargeDomains =
                preChargeDAO.findAllPreChargeBySettleIds(allSettlementIds);

        returnPulishEntity.setLastPreChargeDomains(allPreChargeDomains);

        List<PreChargeDomain> preChargeDomains = new ArrayList<>();

        if (pulishEntity.getPreChargeDomains() != null) {
            //取差集
            List<Long> recallSettIds =
                    pulishEntity.getPreChargeDomains().stream().map(e -> e.getSettlementId()).collect(Collectors.toList());
            List<PreChargeDomain> distinctPreChargeDomains =
                    allPreChargeDomains.stream().filter(item ->
                            !recallSettIds.contains(item.getSettlementId()))
                            .collect(Collectors.toList());

            preChargeDomains.addAll(pulishEntity.getPreChargeDomains());
            preChargeDomains.addAll(distinctPreChargeDomains);
        } else {
            preChargeDomains = allPreChargeDomains;
        }

        //首次发行不存在余额记录的用户
        List<Long> setterIds =
                preChargeDomains.stream().map(PreChargeDomain::getSettlementId).distinct().collect(toList());
        List<PreChargeDomain> insertPreChargeDomains = new ArrayList<>();
        for (Long settlementId : settlementMeterRelMap.keySet()) {
            if (!setterIds.contains(settlementId)) {
                PreChargeDomain preChargeDomain = new PreChargeDomain();
                preChargeDomain.setSettlementId(settlementId);
                preChargeDomain.setBalance(BigDecimal.ZERO);
                preChargeDomain.setStatus(1);
                insertPreChargeDomains.add(preChargeDomain);
            }
        }

        //过滤余额大于0的
        preChargeDomains =
                preChargeDomains.stream().filter(t -> t.getBalance().compareTo(BigDecimal.ZERO) == 1).collect(toList());


        //结算户和余额差集 查看哪些户没有余额记录 插入
        Map<Long, PreChargeDomain> preChargeDomainMap =
                preChargeDomains.stream()
                        .collect(Collectors.toMap(PreChargeDomain::getSettlementId, a -> a, (k1, k2) -> k2));

        List<Long> settleIds =
                settlementMeterRelDomains.stream().map(t -> t.getSettlementId()).distinct().collect(toList());


        params.clear();
        params.put("settleIds", settleIds);
        HttpResult<List<SettlementDomain>> settleResult =
                titanTemplate.postJson("CIM-SERVER",
                        "cimServer/cim_bill?method=findSettlementByIds", new HttpHeaders(), params,
                        new TypeReference<HttpResult<List<SettlementDomain>>>() {
                        });

        //退费转预存记录
        List<ChargeDomain> refundChargeDomains =
                pulishEntity.getChargeDomains() == null ? new ArrayList<>() : pulishEntity.getChargeDomains();

        Map<Long, BigDecimal> refundChargeMap =
                refundChargeDomains.stream().filter(t -> t.getfChargeMode() == 6)
                        .collect(Collectors.groupingBy(ChargeDomain::getMeterId, CustomCollectors.summingBigDecimal(ChargeDomain::getFactTotal)));


        // 根据结算户分组
        Map<Long, SettlementDomain> settlementMap = settleResult.getResultData().stream()
                .collect(Collectors.toMap(SettlementDomain::getId, a -> a, (k1, k2) -> k1));

        List<ArrearageDomain> arrearageDomainList = new ArrayList<>();

        List<PreChargeDomain> preChargeDomainsList = new ArrayList<>();

        List<ChargeDomain> chargeDomains = new ArrayList<>();

        settlementMeterRelMap.forEach((k, v) -> {
            List<ArrearageDomain> arrearageList = new ArrayList<>();

            List<SettlementMeterRelDomain> temp = v;
            //按扣减顺序
            List<SettlementMeterRelDomain> sorttemp = temp.stream().sorted
                    (Comparator.comparing(SettlementMeterRelDomain::getDeductionOrder, Comparator.nullsFirst(Byte::compareTo))).collect(toList());


            BigDecimal thisBalance = BigDecimal.ZERO;

            PreChargeDomain preChargeDomain = null;

            if (preChargeDomainMap.get(k) != null) {
                thisBalance = preChargeDomainMap.get(k).getBalance();
                preChargeDomain = preChargeDomainMap.get(k);
            }

            for (SettlementMeterRelDomain settlementMeterRelDomain : sorttemp) {

                arrearageList =
                        arrearageMap.get(settlementMeterRelDomain.getMeterId());

                for (int i = 0; i < arrearageList.size(); i++) {
                    ArrearageDomain arrearageDomain = arrearageList.get(i);

                    if (arrearageDomain.getOweMoney() == null) {
                        arrearageDomain.setOweMoney(arrearageDomain.getReceivable());
                    }

                    arrearageDomain.setSettlementId(settlementMeterRelDomain.getSettlementId());

                    if (arrearageDomain.getOweMoney() == null || arrearageDomain.getOweMoney().compareTo(BigDecimal.ZERO) == 0) {
                        arrearageDomain.setOweMoney(BigDecimal.ZERO);
                        arrearageDomain.setIsSettle(1);
                    }
                    //若余额大于欠费 剩余余额=余额-欠费 欠费=0 已结清
                    //若余额小于欠费 剩余余额=0 新欠费=就欠费-余额
                    //若余额=欠费 剩余余额=0 欠费=0 已结清
                    if (thisBalance.compareTo(BigDecimal.ZERO) <= 0) {
                        arrearageDomainList.add(arrearageDomain);
                        continue;
                    }

                    if (thisBalance.subtract(arrearageDomain.getOweMoney()).compareTo(BigDecimal.ZERO) > 0) {
                        arrearageDomain.setIsSettle(1);
                        thisBalance = thisBalance.subtract(arrearageDomain.getOweMoney());
                        arrearageDomain.setOweMoney(BigDecimal.ZERO);
                    } else if (thisBalance.subtract(arrearageDomain.getOweMoney()).compareTo(BigDecimal.ZERO) < 0) {
                        arrearageDomain.setOweMoney(arrearageDomain.getOweMoney().subtract(thisBalance));
                        arrearageDomain.setIsSettle(0);
                        thisBalance = BigDecimal.ZERO;
                    } else {
                        arrearageDomain.setOweMoney(BigDecimal.ZERO);
                        arrearageDomain.setIsSettle(1);
                        thisBalance = BigDecimal.ZERO;
                    }

                    if (refundChargeMap != null && refundChargeMap.isEmpty() != true) {
                        BigDecimal refundCharge =
                                refundChargeMap.get(arrearageDomain.getMeterId());
                        if (refundCharge == null) {
                            refundCharge = BigDecimal.ZERO;
                        }
                        arrearageDomain.setShouldMoney(arrearageDomain.getReceivable().subtract(refundCharge));
                    } else {
                        arrearageDomain.setShouldMoney(arrearageDomain.getReceivable());
                    }
                    arrearageDomainList.add(arrearageDomain);


                    //生成收费记录
                    ChargeDomain chargeDomain = new ChargeDomain();
                    chargeDomain.setMeterId(arrearageDomain.getMeterId());
                    chargeDomain.setArrearageNo(arrearageDomain.getArrearageNo());
                    chargeDomain.setMeterMoneyId(arrearageDomain.getMeterMoneyId());
                    chargeDomain.setMon(arrearageDomain.getMon());
                    chargeDomain.setSn(arrearageDomain.getSn());
                    chargeDomain.setWriteSectId(arrearageDomain.getWriteSectId());
                    chargeDomain.setJzFlag(0);
                    // 抵扣余额 暂填
                    chargeDomain.setDeductionBalance(arrearageDomain.getReceivable().subtract(arrearageDomain.getOweMoney()));
                    chargeDomain.setArrears(arrearageDomain.getReceivable());
                    chargeDomain.setFactMoney(arrearageDomain.getReceivable().subtract(arrearageDomain.getOweMoney()));
                    // 收费余额记录 关联户的余额 更新到所有收费记录里
                    chargeDomain.setFactPre(chargeDomain.getFactMoney().negate());
                    // 赋值交费方式
                    chargeDomain.setfChargeMode(fChargeMode);
                    // System.out.println("发行结算户"+settlementMeterRelDomain
                    // .getSettlementId());
                    // 赋值收费方式
                    Byte chargeMode =
                            settlementMap.get(settlementMeterRelDomain.getSettlementId()).getChargeModeType();
                    chargeDomain.setChargeMode(chargeMode);
                    chargeDomain.setBusinessPlaceCode(settlementMap.get(settlementMeterRelDomain.getSettlementId()).getBusinessPlaceCode());
                    chargeDomain.setPayDate(new Date());
                    // 进账日期 暂填
                    chargeDomain.setInDate(new Date());
                    chargeDomain.setFactPunish(BigDecimal.ZERO);
                    chargeDomain.setRelaUserNo(settlementMeterRelDomain.getSettlementId().toString());
                    chargeDomain.setSettlementId(settlementMeterRelDomain.getSettlementId());
                    chargeDomain.setOperator(managerId);
                    // 对账标志 暂填
                    chargeDomain.setBalanceFlag(0);
                    // 结清标识 暂填 1 结清
                    chargeDomain.setPaidFlag(1);
                    // 状态标识 暂填 1
                    chargeDomain.setStatus(1);
                    chargeDomain.setYsTypeCode(1);
                    chargeDomain.setShouldMoney(arrearageDomain.getShouldMoney());
                    // 实收总额
                    chargeDomain.setFactTotal(BigDecimal.ZERO);
                    if (chargeDomain.getFactMoney().compareTo(BigDecimal.ZERO) != 0) {
                        chargeDomains.add(chargeDomain);
                    }
                }
            }
            //生成余额

            if (preChargeDomain != null) {
                preChargeDomain.setBalance(thisBalance);
                preChargeDomainsList.add(preChargeDomain);
            }
        });

        //获取欠费流水号
        Map<String, Object> flowParam = new HashMap<>();
        flowParam.clear();
        flowParam.put("strategy", null);
        flowParam.put("size", chargeDomains.size());

        //实收流水号
        String flowNoJson = restTemplate.postForObject(Global.getConfig(
                "getChargeFlowNoBySize"), flowParam, String.class);

        Long flowNo = JSONObject.parseObject(flowNoJson, Long.class);


        if (chargeDomains != null && chargeDomains.size() > 0) {

            for (int i = 0; i < chargeDomains.size(); i++) {
                chargeDomains.get(i).setFlowNo(String.valueOf(flowNo + i));
            }

            //更新数据
            if (pulishEntity.getChargeDomains() != null && pulishEntity.getChargeDomains().size() > 0) {
                chargeDomains.addAll(pulishEntity.getChargeDomains());
            }

            publishDAO.insertChargeList(chargeDomains);
            //生成发票
            //iNoteInfoService.createNoteInfo(chargeDomains);
        }
        if (preChargeDomainsList != null && preChargeDomainsList.size() > 0) {
            preChargeDAO.updateList(preChargeDomainsList);
        }
        //插入不存在的余额户
        if (insertPreChargeDomains != null && insertPreChargeDomains.size() > 0) {
            preChargeDAO.insertList(insertPreChargeDomains);
        }
        returnPulishEntity.setArrearageDomains(arrearageDomainList);
        return returnPulishEntity;
    }

    @Transactional
    public HttpResult updateMeterMoneyId() {
        publishDAO.updateChargeItemId();
        publishDAO.updateMoneyItemId();
        publishDAO.updateNoteInfoChargeId();
        return new HttpResult(HttpResult.SUCCESS, "发行成功");
    }


    //处理冲票
    @Override
    @Transactional
    public PulishEntity processMoneyRecall(List<ArrearageDomain> arrearageDomains) throws Exception {

        Short monSn = 1;
        if (arrearageDomains.size() > 0) {
            monSn = arrearageDomains.get(0).getSn().shortValue();
        }
        //单户退费
        String appMoneyRecallJson = restTemplate.postForObject(Global.getConfig(
                "billingFindAppMoneyRecall"), null, String.class);

        HttpResult appMoneyRecallObject =
                JSONObject.parseObject(appMoneyRecallJson, HttpResult.class);

        //多户退费
        String appBulkRefundJson = restTemplate.postForObject(Global.getConfig(
                "billingFindAppBulkRefund"), null, String.class);

        HttpResult appBulkRefundObject =
                JSONObject.parseObject(appBulkRefundJson, HttpResult.class);

        if (appMoneyRecallObject.getResultData() == null && appBulkRefundObject.getResultData() == null) {
            return new PulishEntity();
        }

        // 获取电费月份
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Integer currentMon = titanTemplate.postJson("TITAN-CONFIG", "titan-config/sysCommConfig/getCurrentMon",
                httpHeaders, null, Integer.class);

        //取得本月的退费记录
        List<AppMoneyRecall> appMoneyRecalls =
                JSONObject.parseArray(appMoneyRecallObject.getResultData().toString(),
                        AppMoneyRecall.class);


        List<AppBulkRefund> appBulkRefunds =
                JSONObject.parseArray(appBulkRefundObject.getResultData().toString(),
                        AppBulkRefund.class);

        //欠费的计量点
        List<Long> arrearageMeterIds =
                arrearageDomains.stream().map(ArrearageDomain::getMeterId).collect(toList());


        String resultSettlements = restTemplate.postForObject
                (Global.getConfig("getSettlementByMeterIds"), arrearageMeterIds, String.class);

        List<Long> settlementIds = JSONObject.parseArray(resultSettlements, Long.class);

        List<SettlementDomain> settlementDomains = new ArrayList<>();
        settlementIds.forEach(t -> {
            SettlementDomain settlementDomain = new SettlementDomain();
            settlementDomain.setId(t);
            settlementDomains.add(settlementDomain);
        });
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("settlement", settlementDomains);
        String settlementMeterJson = restTemplate.postForObject
                (Global.getConfig("getMeterIdsBySettlements"), jsonObject, String.class);
        List<SettlementMeterRelDomain> settlementMeterRelDomains =
                JSONObject.parseArray(settlementMeterJson, SettlementMeterRelDomain.class);
        Map<Long, Long> meterSettlementRelMap =
                settlementMeterRelDomains.stream().collect(Collectors.toMap(SettlementMeterRelDomain::getSettlementId, a -> a.getMeterId(), (k1, k2) -> k1));


        //本次要处理的退费
        appMoneyRecalls =
                appMoneyRecalls.stream().filter(t -> arrearageMeterIds.contains(t.getMeterId())).collect(toList());

        appMoneyRecalls.forEach(t -> {
            t.setRecallType(1);
        });


        //退费记录中加入批量退费
        if (appBulkRefunds != null && appBulkRefunds.size() > 0) {

            for (int i = 0; i < appBulkRefunds.size(); i++) {
                AppMoneyRecall paramAppMoneyRecall = new AppMoneyRecall();
                paramAppMoneyRecall.setId(appBulkRefunds.get(i).getId());
                paramAppMoneyRecall.setSettlementId(appBulkRefunds.get(i).getSettlementId());
                paramAppMoneyRecall.setTotalMoney(appBulkRefunds.get(i).getRefundMoney());
                paramAppMoneyRecall.setRecallType(2);
                paramAppMoneyRecall.setMeterId(meterSettlementRelMap.get(appBulkRefunds.get(i).getSettlementId()));
                appMoneyRecalls.add(paramAppMoneyRecall);
            }
            ;
        }

        appMoneyRecalls =
                appMoneyRecalls.stream().filter(t -> settlementIds.contains(t.getSettlementId())).collect(toList());

        if (appMoneyRecalls == null || appMoneyRecalls.size() < 1) {
            return new PulishEntity();
        }

        //分组求和 key结算户 value总欠费
        Map<Long, BigDecimal> settleMoney =
                appMoneyRecalls.stream().collect(Collectors.groupingBy(AppMoneyRecall::getSettlementId, CustomCollectors.summingBigDecimal(AppMoneyRecall::getTotalMoney)));

        List<Long> settleIds =
                settleMoney.keySet().stream().map(t -> t.longValue()).collect(Collectors.toList());

        Map<String, List<Long>> params = new HashMap<>();
        params.clear();
        params.put("settleIds", settleIds);
        HttpResult<List<SettlementDomain>> settleResult =
                titanTemplate.postJson("CIM-SERVER",
                        "cimServer/cim_bill?method=findSettlementByIds", new HttpHeaders(), params,
                        new TypeReference<HttpResult<List<SettlementDomain>>>() {
                        });

        // 根据结算户分组
        Map<Long, SettlementDomain> settlementMap = settleResult.getResultData().stream()
                .collect(Collectors.toMap(SettlementDomain::getId, a -> a, (k1, k2) -> k1));

        //获取计量点信息
        List<Long> meterIds =
                appMoneyRecalls.stream().map(AppMoneyRecall::getMeterId).distinct().collect(toList());
        Map<String, Object> meterMaps = new HashMap<>();
        meterMaps.put("meterIds", meterIds);
        List<MeterDomain> meterList = cimService.getMeterFindByWhere(meterMaps);
        Map<Long, MeterDomain> meterMap = meterList.stream().collect(Collectors.toMap(MeterDomain::getId, k -> k));


        List<ChargeDomain> chargeDomains = new ArrayList<>();
        List<AppMoneyRecall> appMoneyRecallList = new ArrayList<>();

        for (AppMoneyRecall appMoneyRecall : appMoneyRecalls) {
            //生成收费记录
            ChargeDomain chargeDomain = new ChargeDomain();
            chargeDomain.setMeterId(appMoneyRecall.getMeterId());
            chargeDomain.setArrearageNo("TFCD(退费转预存)");
            chargeDomain.setMon(currentMon);
            chargeDomain.setSn((int) monSn);
            chargeDomain.setJzFlag(0);
            // 抵扣余额 暂填
            chargeDomain.setDeductionBalance(BigDecimal.ZERO);
            chargeDomain.setArrears(BigDecimal.ZERO);
            chargeDomain.setFactMoney(appMoneyRecall.getTotalMoney());
            // 收费余额记录 关联户的余额 更新到所有收费记录里
            chargeDomain.setFactPre(BigDecimal.ZERO);
            MeterDomain meterDomain = meterMap.get(appMoneyRecall.getMeterId());
            if (meterDomain != null && meterDomain.getWriteSectionId() != null) {
                chargeDomain.setWriteSectId(Long.valueOf(meterDomain.getWriteSectionId()));
            }
            // 赋值交费方式
            chargeDomain.setfChargeMode(6);
            // 赋值收费方式
            Byte chargeMode =
                    settlementMap.get(appMoneyRecall.getSettlementId()).getChargeModeType();
            chargeDomain.setChargeMode(chargeMode);
            chargeDomain.setBusinessPlaceCode(settlementMap.get(appMoneyRecall.getSettlementId()).getBusinessPlaceCode());
            chargeDomain.setPayDate(new Date());
            // 进账日期 暂填
            chargeDomain.setInDate(new Date());
            chargeDomain.setAppNo(appMoneyRecall.getAppId());
            chargeDomain.setFactPunish(BigDecimal.ZERO);
            chargeDomain.setRelaUserNo(settlementMap.get(appMoneyRecall.getSettlementId()).getSettlementNo().toString());
            chargeDomain.setSettlementId(appMoneyRecall.getSettlementId());
            chargeDomain.setOperator((long) 9999);
            // 对账标志 暂填
            chargeDomain.setBalanceFlag(0);
            // 结清标识 暂填 1 结清
            chargeDomain.setPaidFlag(1);
            // 状态标识 暂填 1
            chargeDomain.setStatus(1);
            chargeDomain.setYsTypeCode(4);
            // 实收总额
            chargeDomain.setFactTotal(chargeDomain.getFactMoney());
            chargeDomain.setShouldMoney(BigDecimal.ZERO);
            chargeDomains.add(chargeDomain);
            appMoneyRecall.setMon(currentMon);
            appMoneyRecall.setMonSn(monSn);
            appMoneyRecallList.add(appMoneyRecall);
        }
        List<Long> appMoneyRecallSettlementIds =
                appMoneyRecalls.stream().map(AppMoneyRecall::getSettlementId).distinct().collect(toList());
        List<PreChargeDomain> preChargeDomains = preChargeDAO
                .findAllPreChargeBySettleIds(appMoneyRecallSettlementIds);

        Map<Long, PreChargeDomain> preChargeDomainMap =
                preChargeDomains.stream()
                        .collect(Collectors.toMap(PreChargeDomain::getSettlementId, a -> a, (k1, k2) -> k1));

        //根据结算户更新余额
        List<PreChargeDomain> updatePreCharges = new ArrayList<>();

        for (Long key : settleMoney.keySet()) {
            PreChargeDomain preChargeDomain = new PreChargeDomain();
            preChargeDomain = preChargeDomainMap.get(key);
            preChargeDomain.setBalance(preChargeDomain.getBalance().add(settleMoney.get(key)));
            updatePreCharges.add(preChargeDomain);
        }
        //获取欠费流水号
        Map<String, Object> flowParam = new HashMap<>();
        flowParam.clear();
        flowParam.put("strategy", null);
        flowParam.put("size", chargeDomains.size());

        //实收流水号
        String flowNoJson = restTemplate.postForObject(Global.getConfig(
                "getChargeFlowNoBySize"), flowParam, String.class);

        Long flowNo = JSONObject.parseObject(flowNoJson, Long.class);


        for (int i = 0; i < chargeDomains.size(); i++) {
            chargeDomains.get(i).setFlowNo(String.valueOf(flowNo + i));
        }

        //chargeDAO.insertList(chargeDomains);
        //preChargeDAO.updateList(updatePreCharges);
        //iNoteInfoService.createNoteInfo(chargeDomains);

        PulishEntity pulishEntity = new PulishEntity();
        pulishEntity.setPreChargeDomains(updatePreCharges);
        pulishEntity.setChargeDomains(chargeDomains);
        pulishEntity.setAppMoneyRecalls(appMoneyRecallList);
        return pulishEntity;
    }

    // 冲预收
    @Override
    @Transactional
    public PulishEntity offCharge(PulishEntity pulishEntity,
                                  Long managerId) throws Exception {

        Integer fChargeMode = pulishEntity.getfChargeMode() == null ? 4 : pulishEntity.getfChargeMode();
        // 根据欠费获取meterId 过滤掉负电费
        List<ArrearageDomain> arrearageDomains = pulishEntity.getArrearageDomains();

        // 欠费
        Map<Long, List<ArrearageDomain>> arrearageMap =
                arrearageDomains.stream()
                        .filter(t -> t.getOweMoney().compareTo(BigDecimal.ZERO) >= 0)
                        .collect(Collectors.groupingBy(ArrearageDomain::getSettlementId));


        List<Long> allSettlementIds =
                arrearageDomains.stream().map(ArrearageDomain::getSettlementId).distinct().collect(toList());
        // 获取结算户的余额信息
        List<PreChargeDomain> allPreChargeDomains =
                preChargeDAO.findAllPreChargeBySettleIds(allSettlementIds);

        Map<Long, PreChargeDomain> preChargeDomainMap =
                allPreChargeDomains.stream().collect(Collectors.toMap(PreChargeDomain::getSettlementId, a -> a, (k1, k2) -> k1));

        JSONObject params = new JSONObject();
        params.clear();
        params.put("settleIds", allSettlementIds);
        HttpResult<List<SettlementDomain>> settleResult =
                titanTemplate.postJson("CIM-SERVER",
                        "cimServer/cim_bill?method=findSettlementByIds", new HttpHeaders(), params,
                        new TypeReference<HttpResult<List<SettlementDomain>>>() {
                        });

        // 根据结算户分组
        Map<Long, SettlementDomain> settlementMap = settleResult.getResultData().stream()
                .collect(Collectors.toMap(SettlementDomain::getId, a -> a, (k1, k2) -> k1));

        List<ArrearageDomain> arrearageDomainList=new ArrayList<>();
        List<ChargeDomain> chargeDomains=new ArrayList<>();
        List<PreChargeDomain> preChargeDomainsList=new ArrayList<>();
        PulishEntity returnPulishEntity=new PulishEntity();

        arrearageMap.forEach((k, v) -> {

            List<ArrearageDomain> temp = v;

            BigDecimal thisBalance = BigDecimal.ZERO;

            PreChargeDomain preChargeDomain = null;

            if (preChargeDomainMap.get(k) != null) {
                thisBalance = preChargeDomainMap.get(k).getBalance();
                preChargeDomain = preChargeDomainMap.get(k);
            }


            for (int i = 0; i < temp.size(); i++) {
                ArrearageDomain arrearageDomain = temp.get(i);

                if (arrearageDomain.getOweMoney() == null) {
                    arrearageDomain.setOweMoney(arrearageDomain.getReceivable());
                }

                arrearageDomain.setSettlementId(k);

                if (arrearageDomain.getOweMoney() == null || arrearageDomain.getOweMoney().compareTo(BigDecimal.ZERO) == 0) {
                    arrearageDomain.setOweMoney(BigDecimal.ZERO);
                    arrearageDomain.setIsSettle(1);
                }
                //若余额大于欠费 剩余余额=余额-欠费 欠费=0 已结清
                //若余额小于欠费 剩余余额=0 新欠费=就欠费-余额
                //若余额=欠费 剩余余额=0 欠费=0 已结清
                if (thisBalance.compareTo(BigDecimal.ZERO) <= 0) {
                    arrearageDomainList.add(arrearageDomain);
                    continue;
                }

                if (thisBalance.subtract(arrearageDomain.getOweMoney()).compareTo(BigDecimal.ZERO) > 0) {
                    arrearageDomain.setIsSettle(1);
                    thisBalance = thisBalance.subtract(arrearageDomain.getOweMoney());
                    arrearageDomain.setOweMoney(BigDecimal.ZERO);
                } else if (thisBalance.subtract(arrearageDomain.getOweMoney()).compareTo(BigDecimal.ZERO) < 0) {
                    arrearageDomain.setOweMoney(arrearageDomain.getOweMoney().subtract(thisBalance));
                    arrearageDomain.setIsSettle(0);
                    thisBalance = BigDecimal.ZERO;
                } else {
                    arrearageDomain.setOweMoney(BigDecimal.ZERO);
                    arrearageDomain.setIsSettle(1);
                    thisBalance = BigDecimal.ZERO;
                }

                arrearageDomainList.add(arrearageDomain);


                //生成收费记录
                ChargeDomain chargeDomain = new ChargeDomain();
                chargeDomain.setMeterId(arrearageDomain.getMeterId());
                chargeDomain.setArrearageNo(arrearageDomain.getArrearageNo());
                chargeDomain.setMeterMoneyId(arrearageDomain.getMeterMoneyId());
                chargeDomain.setMon(arrearageDomain.getMon());
                chargeDomain.setSn(arrearageDomain.getSn());
                chargeDomain.setWriteSectId(arrearageDomain.getWriteSectId());
                chargeDomain.setJzFlag(0);
                // 抵扣余额 暂填
                chargeDomain.setDeductionBalance(arrearageDomain.getReceivable().subtract(arrearageDomain.getOweMoney()));
                chargeDomain.setArrears(arrearageDomain.getReceivable());
                chargeDomain.setFactMoney(arrearageDomain.getReceivable().subtract(arrearageDomain.getOweMoney()));
                // 收费余额记录 关联户的余额 更新到所有收费记录里
                chargeDomain.setFactPre(chargeDomain.getFactMoney().negate());
                // 赋值交费方式
                chargeDomain.setfChargeMode(fChargeMode);
                // System.out.println("发行结算户"+settlementMeterRelDomain
                // .getSettlementId());
                // 赋值收费方式
                Byte chargeMode =
                        settlementMap.get(k).getChargeModeType();
                chargeDomain.setChargeMode(chargeMode);
                chargeDomain.setBusinessPlaceCode(settlementMap.get(k).getBusinessPlaceCode());
                chargeDomain.setPayDate(new Date());
                // 进账日期 暂填
                chargeDomain.setInDate(new Date());
                chargeDomain.setFactPunish(BigDecimal.ZERO);
                chargeDomain.setRelaUserNo(settlementMap.get(k).getSettlementNo());
                chargeDomain.setSettlementId(k);
                chargeDomain.setOperator(managerId);
                // 对账标志 暂填
                chargeDomain.setBalanceFlag(0);
                // 结清标识 暂填 1 结清
                chargeDomain.setPaidFlag(1);
                // 状态标识 暂填 1
                chargeDomain.setStatus(1);
                chargeDomain.setYsTypeCode(1);
                chargeDomain.setShouldMoney(arrearageDomain.getShouldMoney());
                // 实收总额
                chargeDomain.setFactTotal(BigDecimal.ZERO);
                if (chargeDomain.getFactMoney().compareTo(BigDecimal.ZERO) != 0) {
                    chargeDomains.add(chargeDomain);
                }
            }
            //生成余额

            if (preChargeDomain != null) {
                preChargeDomain.setBalance(thisBalance);
                preChargeDomainsList.add(preChargeDomain);
            }
        });

        //获取欠费流水号
        Map<String, Object> flowParam = new HashMap<>();
        flowParam.clear();
        flowParam.put("strategy", null);
        flowParam.put("size", chargeDomains.size());

        //实收流水号
        String flowNoJson = restTemplate.postForObject(Global.getConfig(
                "getChargeFlowNoBySize"), flowParam, String.class);

        Long flowNo = JSONObject.parseObject(flowNoJson, Long.class);


        if (chargeDomains != null && chargeDomains.size() > 0) {

            for (int i = 0; i < chargeDomains.size(); i++) {
                chargeDomains.get(i).setFlowNo(String.valueOf(flowNo + i));
            }

            //更新数据
            if (pulishEntity.getChargeDomains() != null && pulishEntity.getChargeDomains().size() > 0) {
                chargeDomains.addAll(pulishEntity.getChargeDomains());
            }

            publishDAO.insertChargeList(chargeDomains);
            //生成发票
            //iNoteInfoService.createNoteInfo(chargeDomains);
        }
        if (preChargeDomainsList != null && preChargeDomainsList.size() > 0) {
            preChargeDAO.updateList(preChargeDomainsList);
        }
        //插入不存在的余额户
        returnPulishEntity.setArrearageDomains(arrearageDomainList);
        return returnPulishEntity;
    }

}
