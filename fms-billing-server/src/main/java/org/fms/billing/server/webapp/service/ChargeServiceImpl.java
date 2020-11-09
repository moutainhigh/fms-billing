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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fms.billing.common.webapp.domain.ArrearageDomain;
import org.fms.billing.common.webapp.domain.BackChargeDomain;
import org.fms.billing.common.webapp.domain.ChargeDomain;
import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.NoteInfoDomain;
import org.fms.billing.common.webapp.domain.PreChargeDomain;
import org.fms.billing.common.webapp.domain.SettlementMeterRelDomain;
import org.fms.billing.common.webapp.domain.beakInterface.CustomerDomain;
import org.fms.billing.common.webapp.domain.beakInterface.SettlementDomain;
import org.fms.billing.common.webapp.entity.ChargeInfoDetailEntity;
import org.fms.billing.common.webapp.entity.ChargeInfoEntity;
import org.fms.billing.common.webapp.entity.MeterPageEntity;
import org.fms.billing.common.webapp.entity.SettlementEntity;
import org.fms.billing.common.webapp.service.IChargeService;
import org.fms.billing.common.webapp.service.IMeterService;
import org.fms.billing.common.webapp.service.INoteInfoService;
import org.fms.billing.server.webapp.dao.ArrearageDAO;
import org.fms.billing.server.webapp.dao.ChargeDAO;
import org.fms.billing.server.webapp.dao.NoteInfoDAO;
import org.fms.billing.server.webapp.dao.PreChargeDAO;
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
public class ChargeServiceImpl implements IChargeService {
    private Log logger = LogFactory.getLog(ChargeServiceImpl.class);

    @TransactionDAO
    private ChargeDAO chargeDAO;

    @TransactionDAO
    private ArrearageDAO arrearageDAO;

    @TransactionDAO
    private NoteInfoDAO noteInfoDAO;

    @Autowired
    private TitanTemplate titanTemplate;

    @TransactionDAO
    private PreChargeDAO preChargeDAO;

    @Autowired
    @Qualifier("noteInfoServiceImpl")
    private INoteInfoService iNoteInfoService;

    @Autowired
    @Qualifier("meterServiceImpl")
    private IMeterService meterService;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CimService cimService;

    @Override
    public ChargeDomain findBykey(ChargeDomain chargeDomain) {
        return chargeDAO.findByKey(chargeDomain);
    }

    @Override
    public List<ChargeDomain> findByWhere(ChargeDomain chargeDomain) {
        return chargeDAO.findByWhere(chargeDomain);
    }

    @Override
    public int insert(ChargeDomain chargeDomain) {
        return chargeDAO.insert(chargeDomain);
    }

    @Override
    public int update(ChargeDomain chargeDomain) {
        return chargeDAO.update(chargeDomain);
    }

    @Override
    public int updateList(List<ChargeDomain> chargeDomains) {
        return chargeDAO.updateList(chargeDomains);
    }

    @Override
    public List<ChargeDomain> findByMeterIds(MeterPageEntity meterPageEntity) {
        return chargeDAO.findByMeterIds(meterPageEntity);
    }

    @Override
    public List<ChargeDomain> findChargeByFlowNos(List<String> flowNos) {
        return chargeDAO.findChargeByFlowNos(flowNos);
    }

    @Override
    public List<ChargeDomain> findBackPulishByMeterIds(Map map) {
        return chargeDAO.findBackPulishByMeterIds(map);
    }
    @Override
    public List<ChargeDomain> findChargeByMeterMoneyIds(List<Long> meterMoneyIds) {
        return chargeDAO.findChargeByMeterMoneyIds(meterMoneyIds);
    }

    /**
     * 收费 先结清违约金再结清电费
     *
     * @param arrearageIds 欠费记录的id
     * @param fChargeMode  缴费方式
     * @param settleMentNo 结算户号
     * @param spareMoeny   实际缴纳金额
     * @param managerId    操作人
     * @param flowNo       收费流水号
     * @return
     */
    @Override
    @Transactional
    public HttpResult charge(List<String> arrearageIds, String fChargeMode,
                             String settleMentNo, BigDecimal spareMoeny,
                             Long managerId, String flowNo) throws Exception {
            JSONObject postData = new JSONObject();

            // 根据欠费表 生成收费记录
            String ids = String.join(",", arrearageIds);
            logger.info("收费参数===="+new Date()+"|"+ids+"|"+fChargeMode+"|"+settleMentNo+"|"+spareMoeny+"|");
            List<ArrearageDomain> arrearageDomains = arrearageDAO.findByListKey(ids);

            arrearageDomains=
                    arrearageDomains.stream().filter(t->t.getIsSettle()==0).collect(toList());

            if(arrearageDomains==null || arrearageDomains.size()<1){
                return new HttpResult(HttpResult.SUCCESS,"所选记录不欠费");
            }


            arrearageDomains =
                    arrearageDomains.stream().sorted(Comparator.comparing(ArrearageDomain::getSettlementId)
                            .thenComparing(ArrearageDomain::getMon).thenComparing(ArrearageDomain::getSn)).collect(Collectors.toList());

            //获取结算户信息
            List<Long> settleIds =
                    arrearageDomains.stream().map(ArrearageDomain::getSettlementId).distinct().collect(toList());

            Map<Long,Long> settleFlowMap=new HashMap<>();
            for (int i=0;i<settleIds.size();i++){
                settleFlowMap.put(settleIds.get(i),Long.valueOf(flowNo)+i);
            }

            Map<Long, SettlementDomain> settlementMap = new HashMap<>();
            Map<String, Object> params = new HashMap<>();
            params.put("settleIds", settleIds);
            try {
                HttpResult<List<SettlementDomain>> settleResult =
                        titanTemplate.postJson("CIM-SERVER",
                                "cimServer/cim_bill?method=findSettlementByIds", new HttpHeaders(), params,
                                new TypeReference<HttpResult<List<SettlementDomain>>>() {
                                });

                settlementMap = settleResult.getResultData().stream().collect(Collectors.toMap(SettlementDomain::getId, a -> a, (k1, k2) -> k1));
            } catch (Exception e) {
                return new HttpResult(HttpResult.ERROR, "缺少结算户信息");
            }

            if (settlementMap == null) {
                return new HttpResult(HttpResult.ERROR, "该结算户号不存在");
            }

            List<ChargeDomain> chargeDomains = new ArrayList<>();

            // 生成收费记录
            Date date = new Date();
            ArrearageDomain arrearageDomain = new ArrearageDomain();

            BigDecimal dealMoney = BigDecimal.ZERO;

            for (int i = 0; i < arrearageDomains.size(); i++) {
                //模拟违约金 取欠费千分之一
                ArrearageDomain t = arrearageDomains.get(i);
                /*if (t.getOweMoney().compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal punishMoney =
                            t.getOweMoney().divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
                    //t.setPunishMoney(punishMoney);
                    t.setPunishMoney(BigDecimal.ZERO);
                }
                arrearageDAO.update(t);*/


                BigDecimal factMoney = BigDecimal.ZERO;
                BigDecimal factPunish = BigDecimal.ZERO;
                //如果剩余的钱<=0 收费结束
                if (spareMoeny.compareTo(BigDecimal.ZERO) <= 0) {
                    break;
                }


                //每次的预收=实际缴纳的钱-欠费
                ChargeDomain chargeDomain = new ChargeDomain();

                dealMoney = spareMoeny.subtract(t.getPunishMoney());
                spareMoeny =
                        spareMoeny.subtract(t.getOweMoney()).subtract(t.getPunishMoney());
                //缴纳的钱够违约金+欠费  先交违约金再交欠费
                if (spareMoeny.compareTo(BigDecimal.ZERO) >= 0) {
                    arrearageDomain.setId(new Long(t.getId()));
                    arrearageDomain.setOweMoney(BigDecimal.ZERO);
                    arrearageDomain.setPunishMoney(BigDecimal.ZERO);
                    arrearageDomain.setIsSettle(1);
                    factMoney = t.getOweMoney();
                    factPunish = t.getPunishMoney();
                    // 结清标识 暂填 1 结清
                    chargeDomain.setPaidFlag(1);
                } else if (dealMoney.compareTo(BigDecimal.ZERO) >= 0) {
                    //缴纳的钱够欠费 不够违约金
                    arrearageDomain.setId(new Long(t.getId()));
                    arrearageDomain.setOweMoney(t.getOweMoney().subtract(dealMoney));
                    arrearageDomain.setPunishMoney(BigDecimal.ZERO);
                    arrearageDomain.setIsSettle(0);
                    chargeDomain.setPaidFlag(0);
                    factMoney = dealMoney;
                    factPunish = t.getPunishMoney();
                } else {
                    //缴纳的钱不够欠费
                    arrearageDomain.setId(new Long(t.getId()));
                    arrearageDomain.setPunishMoney(dealMoney.negate());
                    arrearageDomain.setIsSettle(0);
                    chargeDomain.setPaidFlag(0);
                    factMoney = BigDecimal.ZERO;
                    factPunish = dealMoney.add(t.getPunishMoney());
                }
                arrearageDAO.update(arrearageDomain);

                chargeDomain.setMeterId(t.getMeterId());
                chargeDomain.setChargeMode(settlementMap.get(t.getSettlementId()).getChargeModeType());
                chargeDomain.setArrearageNo(t.getArrearageNo());
                chargeDomain.setMeterMoneyId(t.getMeterMoneyId());
                chargeDomain.setMon(t.getMon());
                chargeDomain.setWriteSectId(t.getWriteSectId());
                chargeDomain.setSn(t.getSn());
                chargeDomain.setJzFlag(0);
                chargeDomain.setYsTypeCode(1);
                chargeDomain.setBusinessPlaceCode(settlementMap.get(t.getSettlementId()).getBusinessPlaceCode());
                // 抵扣余额 暂填
                chargeDomain.setDeductionBalance(BigDecimal.ZERO);
                chargeDomain.setArrears(t.getReceivable());
                // 收费余额记录 关联户的余额 更新到所有收费记录里
                //最后一次收费
                if (i == (arrearageDomains.size() - 1)) {
                    //如果是最后一次收费 余额小于0 预收就是0 大于0 预收为spareMoeny
                    chargeDomain.setFactPre(spareMoeny.compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ZERO : spareMoeny);
                } else {
                    chargeDomain.setFactPre(BigDecimal.ZERO);
                }
                chargeDomain.setfChargeMode(new Integer(fChargeMode));
                chargeDomain.setPayDate(date);
                // 进账日期 暂填
                chargeDomain.setInDate(date);
                //实收电费
                chargeDomain.setFactMoney(factMoney);
                //实缴比违约金多 实收违约金为应收违约金 否则 为实缴金额
                chargeDomain.setFactPunish(factPunish);
                chargeDomain.setRelaUserNo(settlementMap.get(t.getSettlementId()).getSettlementNo());
                chargeDomain.setSettlementId(t.getSettlementId());
                chargeDomain.setOperator(managerId);
                //本次余额上次余额
                // 对账标志 暂填
                chargeDomain.setBalanceFlag(0);
                // 状态标识 暂填 1
                chargeDomain.setStatus(1);
                chargeDomain.setFlowNo(settleFlowMap.get(t.getSettlementId()).toString());
                // 实收总额
                BigDecimal factTotal = chargeDomain.getFactMoney()
                        .add(chargeDomain.getFactPre()).add(chargeDomain.getFactPunish());
                chargeDomain.setFactTotal(factTotal);
                chargeDomain.setShouldMoney(t.getShouldMoney());
                chargeDAO.insert(chargeDomain);
                chargeDomains.add(chargeDomain);

            }

            // 生成发票信息 并赋值上次余额
            HttpResult httpResult = iNoteInfoService.createNoteInfo(chargeDomains);
            if (httpResult.getStatusCode() != HttpResult.SUCCESS) {
                return httpResult;
            }

            Map<Long,Integer> chargeDomainMap=
                    chargeDomains.stream().collect(Collectors.toMap(ChargeDomain::getId, t -> t.getfChargeMode(), (k1, k2) -> k1));

            // 取余额信息 多户将余额放在最后一个欠费的户上
            Long settlenmentId =
                    arrearageDomains.get(arrearageDomains.size() - 1).getSettlementId();

            PreChargeDomain preChargeDomain = new PreChargeDomain();
            preChargeDomain.setSettlementId(settlenmentId);
            List<PreChargeDomain> preChargeDomains = preChargeDAO.findByWhere(preChargeDomain);
            //第一次赋值上次余额
            List<NoteInfoDomain> noteInfoDomains = (List<NoteInfoDomain>) httpResult.getResultData();
            if(preChargeDomains==null || preChargeDomains.size()<1){
                noteInfoDomains.get(0).setLastBalance(BigDecimal.ZERO);
            }else{
                noteInfoDomains.get(0).setLastBalance(preChargeDomains.get(0).getBalance());
            }

            preChargeDomain.setBalance(spareMoeny.compareTo(BigDecimal.ZERO) > 0 ? spareMoeny : BigDecimal.ZERO);
            if (null == preChargeDomains || preChargeDomains.size() < 1) {
                preChargeDomain.setStatus(1);
                preChargeDAO.insert(preChargeDomain);
            } else {
                preChargeDomain.setId(preChargeDomains.get(0).getId());
                preChargeDAO.update(preChargeDomain);
            }



            // 赋值本次余额  出去最后一次 其余的本一月都为0
            PreChargeDomain thisPreCharge = new PreChargeDomain();
            thisPreCharge.setSettlementId(settlenmentId);
            List<PreChargeDomain> thisPreCharges = preChargeDAO.findByWhere(thisPreCharge);
            noteInfoDomains.get(noteInfoDomains.size() - 1).setThisBalance(thisPreCharges.get(0).getBalance());

            noteInfoDAO.insertList(noteInfoDomains);
            noteInfoDomains.forEach(t -> {
                t.setFactTotal(t.getFactMoney().add(t.getFactPre()).add(t.getPunishMoney()));
                t.setfChargeMode(Long.valueOf(chargeDomainMap.get(t.getChargeInfoId())));
            });

            return new HttpResult(HttpResult.SUCCESS, "缴费成功", noteInfoDomains);

    }

    @Override
    // //按扣减顺序 将余额放到第一个计量点上 并且算费次数是1
    @Transactional
    public HttpResult preCharge(String fChargeMode, String settleMentNo,
                                BigDecimal spareMoeny,
                                Long managerId, String flowNo) throws Exception {

        JSONObject postData = new JSONObject();
        //根据结算户no 获取结算户id
        postData.clear();
        postData.put("settlementNo", settleMentNo);
        String settlementJson = restTemplate.postForObject(Global.getConfig("getSettlementByNo"), postData, String.class);
        List<SettlementEntity> settementArray =
                JSONObject.parseArray(JSONObject.parseObject(settlementJson).getString("list"), SettlementEntity.class);
        if (settementArray == null || settementArray.size() != 1) {
            return new HttpResult(HttpResult.ERROR, "该结算户号不存在或者该结算户号存在重复记录");
        }

        Long settlenmentId = settementArray.get(0).getId();
        Byte chargeMode = settementArray.get(0).getChargeModeType();

        //预收获取结算户下 所有计量点
        postData.clear();
        postData.put("settleMentId", settlenmentId);
        String settleRelString = restTemplate.postForObject(Global.getConfig(
                "getMeterIdsBySettleId"), postData, String.class);


        HttpResult settleHttpResult = JSONObject.parseObject(settleRelString,
                HttpResult.class);
        if (settleHttpResult.getStatusCode() == HttpResult.ERROR) {
            return settleHttpResult;
        }

        List<SettlementMeterRelDomain> meterRelDomainList =
                JSONObject.parseArray(settleHttpResult.getResultData().toString(),
                        SettlementMeterRelDomain.class);

        //结算户下没有计量点
        if (null == meterRelDomainList || meterRelDomainList.size() < 1) {
            return new HttpResult(HttpResult.ERROR, "结算户输入错误,该结算户下没有计量点");
        }
        //按扣减顺序 将余额放到第一个计量点上
        List<SettlementMeterRelDomain> sortSettleRel =
                meterRelDomainList.stream().sorted(Comparator.comparing(SettlementMeterRelDomain::getDeductionOrder, Comparator.nullsFirst(Byte::compareTo))).collect(Collectors.toList());


        // 获取电费月份
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String currentMon = titanTemplate.postJson("TITAN-CONFIG", "titan-config/sysCommConfig/getCurrentMon",
                httpHeaders, null, String.class);
        String meterId = sortSettleRel.get(0).getMeterId().toString();
        List<String> meterIds=new ArrayList<>();
        meterIds.add(meterId);
        Map<String, Object> meterMaps=new HashMap<>();
        meterMaps.put("meterIds", meterIds);
        List<MeterDomain> meterList = cimService.getMeterFindByWhere(meterMaps);
        Map<Long, MeterDomain> meterMap = meterList.stream().collect(Collectors.toMap(MeterDomain::getId, k -> k));

        // 生成收费记录
        Date date = new Date();
        List<ChargeDomain> chargeDomains = new ArrayList<>();
        ChargeDomain chargeDomain = new ChargeDomain();
        chargeDomain.setChargeMode(chargeMode);
        chargeDomain.setMon(new Integer(currentMon));
        chargeDomain.setSn(1);
        chargeDomain.setMeterId(new Long(meterId));
        chargeDomain.setArrearageNo("YS");
        chargeDomain.setJzFlag(0);
        chargeDomain.setBusinessPlaceCode(settementArray.get(0).getBusinessPlaceCode());
        MeterDomain meterDomain=meterMap.get(Long.valueOf(meterId));
        if(meterDomain!=null && meterDomain.getWriteSectionId()!=null){
            chargeDomain.setWriteSectId(Long.valueOf(meterDomain.getWriteSectionId()));
        }
        // 抵扣余额 暂填
        chargeDomain.setDeductionBalance(BigDecimal.ZERO);
        chargeDomain.setArrears(BigDecimal.ZERO);
        chargeDomain.setFactMoney(BigDecimal.ZERO);
        // 收费余额记录 关联户的余额 更新到所有收费记录里
        chargeDomain.setFactPre(spareMoeny);
        chargeDomain.setfChargeMode(new Integer(fChargeMode));
        chargeDomain.setPayDate(date);
        // 进账日期 暂填
        chargeDomain.setInDate(date);
        chargeDomain.setFactPunish(BigDecimal.ZERO);
        chargeDomain.setRelaUserNo(settleMentNo);
        chargeDomain.setSettlementId(settlenmentId);
        chargeDomain.setOperator(managerId);
        // 对账标志 暂填
        chargeDomain.setBalanceFlag(0);
        chargeDomain.setFactTotal(spareMoeny);
        // 结清标识 暂填 1 结清
        chargeDomain.setPaidFlag(1);
        // 状态标识 暂填 1
        chargeDomain.setStatus(1);
        chargeDomain.setYsTypeCode(2);
        if(spareMoeny.compareTo(BigDecimal.ZERO)<0  ){
            chargeDomain.setYsTypeCode(3);
            chargeDomain.setArrearageNo("TYS");
        }
        chargeDomain.setFlowNo(flowNo);
        chargeDomain.setShouldMoney(BigDecimal.ZERO);
        chargeDAO.insert(chargeDomain);

        chargeDomains.add(chargeDomain);
        // 创建发票信息
        HttpResult httpResult = iNoteInfoService.createNoteInfo(chargeDomains);
        if (httpResult.getStatusCode() != HttpResult.SUCCESS) {
            return httpResult;
        }
        Map<Long,Integer> chargeDomainMap=
                chargeDomains.stream().collect(Collectors.toMap(ChargeDomain::getId, a -> a.getfChargeMode(), (k1, k2) -> k1));

        // 更新余额
        PreChargeDomain preChargeDomain = new PreChargeDomain();
        preChargeDomain.setSettlementId(settlenmentId);
        List<PreChargeDomain> preChargeDomains = preChargeDAO.findByWhere(preChargeDomain);
        preChargeDomain.setBalance(spareMoeny);
        if (null == preChargeDomains || preChargeDomains.size() < 1) {
            preChargeDomain.setStatus(1);
            preChargeDAO.insert(preChargeDomain);
        } else {
            preChargeDomain.setId(preChargeDomains.get(0).getId());
            preChargeDomain.setBalance(spareMoeny.add(preChargeDomains.get(0).getBalance()));
            preChargeDAO.update(preChargeDomain);
        }

        List<NoteInfoDomain> noteInfoDomains = (List<NoteInfoDomain>) httpResult.getResultData();

        // 赋值本次余额  出去最后一次 其余的本一月都为0
        PreChargeDomain thisPreCharge = new PreChargeDomain();
        thisPreCharge.setSettlementId(settlenmentId);
        List<PreChargeDomain> thisPreCharges = preChargeDAO.findByWhere(thisPreCharge);
        noteInfoDomains.get(noteInfoDomains.size() - 1).setThisBalance(thisPreCharges.get(0).getBalance());
        noteInfoDomains.get(0).setLastBalance(preChargeDomains.get(0).getBalance());

        noteInfoDAO.insertList(noteInfoDomains);
        noteInfoDomains.forEach(t -> {
            t.setfChargeMode(Long.valueOf(chargeDomainMap.get(t.getChargeInfoId())));
        });
        return new HttpResult(HttpResult.SUCCESS, "缴费成功", noteInfoDomains);
    }




    /**
     * 冲预收
     *
     * @param arrearageIds 欠费记录的id
     * @param fChargeMode  缴费方式
     * @param settleMentNo 结算户号
     * @param spareMoeny   实际缴纳金额
     * @param managerId    操作人
     * @param flowNo       收费流水号
     * @return
     */
    @Override
    @Transactional
    public HttpResult chargeRecall(List<String> arrearageIds,
                                   String fChargeMode,
                                   String settleMentNo, BigDecimal spareMoeny,
                                   Long managerId, String flowNo) throws Exception {
            JSONObject postData = new JSONObject();
            //根据结算户no 获取结算户id
            postData.clear();
            postData.put("settlementNo", settleMentNo);
            String settlementJson = restTemplate.postForObject(Global.getConfig("getSettlementByNo"), postData, String.class);
            List<SettlementEntity> settementArray =
                    JSONObject.parseArray(JSONObject.parseObject(settlementJson).getString("list"), SettlementEntity.class);
            if (settementArray == null || settementArray.size() != 1) {
                return new HttpResult(HttpResult.ERROR, "该结算户号不存在或者该结算户号存在重复记录");
            }

            Long settlenmentId = settementArray.get(0).getId();
            Byte chargeMode = settementArray.get(0).getChargeModeType();


            // 根据欠费表 生成收费记录
            String ids = String.join(",", arrearageIds);
            List<ArrearageDomain> arrearageDomains = arrearageDAO.findByListKey(ids);
            List<ChargeDomain> chargeDomains = new ArrayList<>();

            // 生成收费记录
            Date date = new Date();
            ArrearageDomain arrearageDomain = new ArrearageDomain();

            BigDecimal dealMoney = BigDecimal.ZERO;

            for (int i = 0; i < arrearageDomains.size(); i++) {
                ArrearageDomain t = arrearageDomains.get(i);

                //每次的预收=实际缴纳的钱-欠费
                ChargeDomain chargeDomain = new ChargeDomain();

                //缴纳的钱够违约金+欠费  先交违约金再交欠费
                arrearageDomain.setId(new Long(t.getId()));
                arrearageDomain.setOweMoney(BigDecimal.ZERO);
                arrearageDomain.setPunishMoney(BigDecimal.ZERO);
                arrearageDomain.setIsSettle(1);
                // 结清标识 暂填 1 结清
                chargeDomain.setPaidFlag(1);
                arrearageDAO.update(arrearageDomain);

                chargeDomain.setMeterId(t.getMeterId());
                chargeDomain.setChargeMode(chargeMode);
                chargeDomain.setArrearageNo(t.getArrearageNo());
                chargeDomain.setMeterMoneyId(t.getMeterMoneyId());
                chargeDomain.setMon(t.getMon());
                chargeDomain.setSn(t.getSn());
                chargeDomain.setWriteSectId(t.getWriteSectId());
                chargeDomain.setJzFlag(0);
                chargeDomain.setBusinessPlaceCode(settementArray.get(0).getBusinessPlaceCode());
                // 抵扣余额 暂填
                chargeDomain.setDeductionBalance(BigDecimal.ZERO);
                chargeDomain.setArrears(t.getReceivable());
                chargeDomain.setFactPre(BigDecimal.ZERO);
                chargeDomain.setfChargeMode(new Integer(fChargeMode));
                chargeDomain.setPayDate(date);
                // 进账日期 暂填
                chargeDomain.setInDate(date);
                //实收电费
                chargeDomain.setFactMoney(t.getOweMoney());
                //实缴比违约金多 实收违约金为应收违约金 否则 为实缴金额
                chargeDomain.setFactPunish(BigDecimal.ZERO);
                chargeDomain.setRelaUserNo(settleMentNo);
                chargeDomain.setSettlementId(settlenmentId);
                chargeDomain.setOperator(managerId);
                //本次余额上次余额
                // 对账标志 暂填
                chargeDomain.setBalanceFlag(0);
                // 状态标识 暂填 1
                chargeDomain.setStatus(1);
                chargeDomain.setYsTypeCode(1);
                chargeDomain.setFlowNo(flowNo);
                // 实收总额
                BigDecimal factTotal = chargeDomain.getFactMoney()
                        .add(chargeDomain.getFactPre()).add(chargeDomain.getFactPunish());
                chargeDomain.setFactTotal(factTotal);
                chargeDomain.setShouldMoney(t.getShouldMoney());
                chargeDAO.insert(chargeDomain);
                chargeDomains.add(chargeDomain);
            }

            // 生成发票信息 并赋值上次余额
            HttpResult httpResult = iNoteInfoService.createNoteInfo(chargeDomains);
            if (httpResult.getStatusCode() != HttpResult.SUCCESS) {
                return httpResult;
            }
            Map<Long,Integer> chargeDomainMap=
                    chargeDomains.stream().collect(Collectors.toMap(ChargeDomain::getId, a -> a.getfChargeMode(), (k1, k2) -> k1));

            // 取余额信息
            PreChargeDomain preChargeDomain = new PreChargeDomain();
            preChargeDomain.setSettlementId(settlenmentId);
            List<PreChargeDomain> preChargeDomains = preChargeDAO.findByWhere(preChargeDomain);
            if (null == preChargeDomains || preChargeDomains.size() < 1) {
                preChargeDomain.setBalance(BigDecimal.ZERO);
                preChargeDomain.setStatus(1);
                preChargeDAO.insert(preChargeDomain);
            }

            List<NoteInfoDomain> noteInfoDomains = (List<NoteInfoDomain>) httpResult.getResultData();

            // 赋值本次余额  出去最后一次 其余的本一月都为0
            PreChargeDomain thisPreCharge = new PreChargeDomain();
            thisPreCharge.setSettlementId(settlenmentId);
            List<PreChargeDomain> thisPreCharges = preChargeDAO.findByWhere(thisPreCharge);
            noteInfoDomains.get(noteInfoDomains.size() - 1).setThisBalance(thisPreCharges.get(0).getBalance());
            noteInfoDomains.get(0).setLastBalance(preChargeDomains.get(0).getBalance());

            noteInfoDAO.insertList(noteInfoDomains);
            noteInfoDomains.forEach(t -> {
                t.setfChargeMode(Long.valueOf(chargeDomainMap.get(t.getChargeInfoId())));
            });
            return new HttpResult(HttpResult.SUCCESS, "缴费成功", noteInfoDomains);
    }

    /**
     * 银行代扣
     *
     * @param fChargeMode 缴费方式
     * @param managerId   操作人
     * @return
     */
    @Override
    public HttpResult batchCharge(List<Long> arrearageIds, String fChargeMode,
                                 Long managerId) throws Exception {

        if (arrearageIds == null || arrearageIds.size() < 1) {
            return new HttpResult(HttpResult.ERROR, "没有相关欠费记录");
        }

        List<ArrearageDomain> arrearageDomains = new ArrayList<>();

        int len = arrearageIds.size();

        for (int m = 0; m < len / 1999 + 1; m++) {// 遍历次数

            List<Long> tl = arrearageIds.subList(m * 1999,
                    (m + 1) * 1999 > len ? len : (m + 1) * 1999);

            String idsString =
                    tl.stream().map(String::valueOf).collect(Collectors.joining(","));

            arrearageDomains.addAll(arrearageDAO.findByListKey(idsString));
        }

        //处理欠费数据
        if (arrearageDomains == null || arrearageDomains.size() < 1) {
            return new HttpResult(HttpResult.ERROR, "无欠费数据");
        }

        List<Long> settleIds =
                arrearageDomains.stream().map(ArrearageDomain::getSettlementId).collect(toList());

        Map<Long, SettlementDomain> settlementMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("settleIds", settleIds);
        try {
            HttpResult<List<SettlementDomain>> settleResult =
                    titanTemplate.postJson("CIM-SERVER",
                            "cimServer/cim_bill?method=findSettlementByIds", new HttpHeaders(), params,
                            new TypeReference<HttpResult<List<SettlementDomain>>>() {
                            });

            settlementMap = settleResult.getResultData().stream().collect(Collectors.toMap(SettlementDomain::getId, a -> a, (k1, k2) -> k1));
        } catch (Exception e) {
            return new HttpResult(HttpResult.ERROR, "缺少结算户信息");
        }

        //实收流水号
        params.clear();
        params.put("strategy", null);
        params.put("size", len);
        String flowNoJson = restTemplate.postForObject(Global.getConfig(
                "getChargeFlowNoBySize"), params, String.class);

        Long flowNo = JSONObject.parseObject(flowNoJson, Long.class);

        List<ChargeDomain> chargeDomains = new ArrayList<>();

        for (int i = 0; i < arrearageDomains.size(); i++) {
            ArrearageDomain t = arrearageDomains.get(i);
            ChargeDomain chargeDomain = new ChargeDomain();
            chargeDomain.setPaidFlag(1);
            chargeDomain.setMeterId(t.getMeterId());
            chargeDomain.setChargeMode(settlementMap.get(t.getSettlementId()).getChargeModeType());
            chargeDomain.setArrearageNo(t.getArrearageNo());
            chargeDomain.setMeterMoneyId(t.getMeterMoneyId());
            chargeDomain.setMon(t.getMon());
            chargeDomain.setSn(t.getSn());
            chargeDomain.setJzFlag(0);
            chargeDomain.setYsTypeCode(1);
            chargeDomain.setWriteSectId(t.getWriteSectId());
            chargeDomain.setBusinessPlaceCode(settlementMap.get(t.getSettlementId()).getBusinessPlaceCode());
            // 抵扣余额 暂填
            chargeDomain.setDeductionBalance(BigDecimal.ZERO);
            chargeDomain.setArrears(t.getReceivable());
            // 收费余额记录 关联户的余额 更新到所有收费记录里
            chargeDomain.setFactPre(BigDecimal.ZERO);
            chargeDomain.setfChargeMode(new Integer(fChargeMode));
            chargeDomain.setPayDate(new Date());
            // 进账日期 暂填
            chargeDomain.setInDate(new Date());
            //实收电费
            chargeDomain.setFactMoney(t.getOweMoney());
            //实缴比违约金多 实收违约金为应收违约金 否则 为实缴金额
            chargeDomain.setFactPunish(t.getPunishMoney());
            chargeDomain.setRelaUserNo(settlementMap.get(t.getSettlementId()).getSettlementNo());
            chargeDomain.setSettlementId(t.getSettlementId());
            chargeDomain.setOperator(managerId);
            //本次余额上次余额
            // 对账标志 暂填
            chargeDomain.setBalanceFlag(0);
            // 状态标识 暂填 1
            chargeDomain.setStatus(1);
            chargeDomain.setFlowNo(String.valueOf(flowNo + i));
            // 实收总额
            BigDecimal factTotal = chargeDomain.getFactMoney()
                    .add(chargeDomain.getFactPre()).add(chargeDomain.getFactPunish());
            chargeDomain.setFactTotal(factTotal);
            chargeDomain.setShouldMoney(t.getShouldMoney());
            chargeDomains.add(chargeDomain);
        }
        ;
        chargeDAO.insertList(chargeDomains);
        //生成发票信息
        HttpResult httpResult =iNoteInfoService.createNoteInfo(chargeDomains);
        if (httpResult.getStatusCode() != HttpResult.SUCCESS) {
            return httpResult;
        }

        List<NoteInfoDomain> noteInfoDomains=
                (List<NoteInfoDomain>)httpResult.getResultData();

        noteInfoDomains.forEach(t->{
            t.setLastBalance(BigDecimal.ZERO);
            t.setThisBalance(BigDecimal.ZERO);
        });
        //插入银行代扣发票
        noteInfoDAO.insertList(noteInfoDomains);
        //更新欠费信息
        arrearageDomains.forEach(t -> {
            t.setPunishMoney(BigDecimal.ZERO);
            t.setOweMoney(BigDecimal.ZERO);
            t.setIsSettle(1);
            t.setIsLock((short) 0);
        });

        arrearageDAO.updateList(arrearageDomains);

        return new HttpResult(HttpResult.SUCCESS, "缴费成功",chargeDomains);
    }

    @Override
    public List<ChargeDomain> findChargeByIds(List<Long> ids) {
        return chargeDAO.findChargeByIds(ids);
    }

    @Override
    public List<BackChargeDomain> findBackChargeByIds(List<Long> ids) {
        return chargeDAO.findBackChargeByIds(ids);
    }

    @Override
    public int delete(ChargeDomain chargeDomain) {
        return chargeDAO.delete(chargeDomain);
    }

    @Override
    public int insertList(List<ChargeDomain> chargeDomains) {
        return chargeDAO.insertList(chargeDomains);
    }

    @Override
    public int insertListBulk(List<ChargeDomain> chargeDomains) {
        return chargeDAO.insertListBulk(chargeDomains);
    }

    @Override
    public List<ChargeInfoDetailEntity> findChargeInfoDetails(ChargeInfoEntity chargeInfoEntity) {
        return chargeDAO.findChargeInfoDetails(chargeInfoEntity);
    }

    @Override
    public List<ChargeInfoDetailEntity> findChargeInfoDetailsByCustomer(CustomerDomain customerDomain) {
        Map<String, Object> params = new HashMap<>();
        MeterDomain meterDomain = new MeterDomain();
        meterDomain.setCustomerId(customerDomain.getId());
        params.put("meter", meterDomain);
        List<MeterDomain> meterDomains = new ArrayList<>();
        try {
            meterDomains = titanTemplate.postJson("CIM-SERVER",
                    "cimServer/meter?method=queryMetersByCustomer", new HttpHeaders(), params,
                    new TypeReference<List<MeterDomain>>() {
                    });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<SettlementDomain> settlementDomains = new ArrayList<>();
        Map<String, Object> params2 = new HashMap<>();
        SettlementDomain settlementDomain = new SettlementDomain();
        settlementDomain.setCustomerId(customerDomain.getId());
        params2.put("settlement", settlementDomain);
        try {
            settlementDomains = titanTemplate.postJson("CIM-SERVER",
                    "cimServer/settlement?method=querySettlements", new HttpHeaders(), params2,
                    new TypeReference<List<SettlementDomain>>() {
                    });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (meterDomains.isEmpty()) {
            return new ArrayList<>(0);
        }
        if (settlementDomains.isEmpty()) {
            return new ArrayList<>(0);
        }
        List<Long> meterIds = meterDomains.stream().filter(m -> m.getId() != null)
                .map(MeterDomain::getId).collect(toList());
        Map<Long, MeterDomain> meterDomainMap = meterDomains.stream()
                .collect(Collectors.toMap(MeterDomain::getId, a -> a, (k1, k2) -> k2));
        Map<Long, SettlementDomain> settlementDomainMap = settlementDomains.stream()
                .collect(Collectors.toMap(SettlementDomain::getId, a -> a, (k1, k2) -> k1));
        customerDomain.setMeterIds(meterIds);
        List<ChargeInfoDetailEntity> chargeInfoDetailsByCustomer = chargeDAO
                .findChargeInfoDetailsByCustomer(customerDomain);
        chargeInfoDetailsByCustomer.forEach(c -> {
            if (c.getFactMoney() != null && c.getFactPre() != null) {
                c.setTotalAmount(c.getFactMoney().add(c.getFactPre()));
            }
        });
        chargeInfoDetailsByCustomer.forEach(c -> {
            if (c.getSettlementId() != null) {
                c.setSettlementNo(settlementDomainMap.get(c.getSettlementId()).getSettlementNo());
                c.setSettlementName(settlementDomainMap.get(c.getSettlementId()).getSettlementName());
            }
        });
        chargeInfoDetailsByCustomer.forEach(c -> {
            c.setMeterName(meterDomainMap.get(c.getMeterId()).getMeterName());
        });
        return chargeInfoDetailsByCustomer;
    }

    //预收余额查询报表
    @Override
    public List<ChargeDomain> findMaxIdBySettlementIds(ChargeInfoEntity chargeInfoEntity){
        return chargeDAO.findMaxIdBySettlementIds(chargeInfoEntity);
    }
    //按天或者支付方式分组 收费员月结
    @Override
    public List<ChargeInfoDetailEntity> findChargeInfoDetailsGroupByDay(ChargeInfoEntity chargeInfoEntity) {
        return chargeDAO.findChargeInfoDetailsGroupByDay(chargeInfoEntity);
    };

    //跨区收费
    @Override
    public List<ChargeInfoDetailEntity> findCrossChargeInfoDetails(ChargeInfoEntity chargeInfoEntity) {
        return chargeDAO.findCrossChargeInfoDetails(chargeInfoEntity);
    }
}
