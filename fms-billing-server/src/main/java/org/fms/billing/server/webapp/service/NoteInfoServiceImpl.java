package org.fms.billing.server.webapp.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.fms.billing.common.webapp.domain.ChargeDomain;
import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.MeterMoneyDomain;
import org.fms.billing.common.webapp.domain.NoteInfoDomain;
import org.fms.billing.common.webapp.domain.PreChargeDomain;
import org.fms.billing.common.webapp.domain.PriceExecutionDomain;
import org.fms.billing.common.webapp.domain.PriceTypeDomain;
import org.fms.billing.common.webapp.domain.WriteFilesDomain;
import org.fms.billing.common.webapp.entity.ChargeInfoDetailEntity;
import org.fms.billing.common.webapp.entity.ChargeInfoEntity;
import org.fms.billing.common.webapp.entity.SettlementEntity;
import org.fms.billing.common.webapp.entity.SystemCommonConfigEntity;
import org.fms.billing.common.webapp.service.INoteInfoService;
import org.fms.billing.server.webapp.dao.MeterMoneyDAO;
import org.fms.billing.server.webapp.dao.NoteInfoDAO;
import org.fms.billing.server.webapp.dao.PreChargeDAO;
import org.fms.billing.server.webapp.dao.PriceExecutionDAO;
import org.fms.billing.server.webapp.dao.PriceTypeDAO;
import org.fms.billing.server.webapp.dao.WriteFilesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;
import com.riozenc.titanTool.properties.Global;
import com.riozenc.titanTool.spring.web.client.TitanTemplate;
import com.riozenc.titanTool.spring.web.http.HttpResult;

@TransactionService
public class NoteInfoServiceImpl implements INoteInfoService {

    @Autowired
    private RestTemplate restTemplate;

    @TransactionDAO
    private MeterMoneyDAO meterMoneyDAO;

    @TransactionDAO
    private WriteFilesDAO writeFilesDAO;

    @TransactionDAO
    private PriceTypeDAO priceTypeDAO;


    @TransactionDAO
    private PreChargeDAO preChargeDAO;

    @TransactionDAO
    private NoteInfoDAO noteInfoDAO;

    @TransactionDAO
    private PriceExecutionDAO priceExecutionDAO;

    @Autowired
    private TitanTemplate titanTemplate;

    @Override
    public List<NoteInfoDomain> findByWhere(NoteInfoDomain noteInfoDomain) {
        return noteInfoDAO.findByWhere(noteInfoDomain);
    }

    @Override
    public int insert(NoteInfoDomain noteInfoDomain) {
        return noteInfoDAO.insert(noteInfoDomain);
    }

    @Override
    public int update(NoteInfoDomain noteInfoDomain) {
        return noteInfoDAO.update(noteInfoDomain);
    }

    @Override
    public int updateByIds(List<NoteInfoDomain> noteInfoDomains) {
        return noteInfoDAO.updateByIds(noteInfoDomains);
    }

    @Override
    public List<NoteInfoDomain> findNoteInfoByIds(List<NoteInfoDomain> noteInfoDomains) {
        return noteInfoDAO.findNoteInfoByIds(noteInfoDomains);
    }

    @Override
    public List<NoteInfoDomain> findInvoiceNoteInfoByChargeIds(List<Long> chargeInfoIds) {
        return noteInfoDAO.findInvoiceNoteInfoByChargeIds(chargeInfoIds);
    }


    // 收费时生成发票信息
    @Override
    @Transactional
    public HttpResult createNoteInfo(List<ChargeDomain> chargeDomains) throws Exception {
        JSONObject postData = new JSONObject();
        List<NoteInfoDomain> noteInfoDomains = new ArrayList<>();

        List<Integer> monList =
                chargeDomains.stream().map(ChargeDomain::getMon).distinct().collect(Collectors.toList());


        Map<Integer, Map<Long, List<PriceExecutionDomain>>> monsPriceMap = new HashMap<>();

        monList.forEach(t -> {
            //处理导入月份
            int mon = Integer.valueOf(Global.getConfig("mon"));
            if (t < mon) {
                t = mon;
            }
            Map<Long, List<PriceExecutionDomain>> priceMap =
                    priceExecutionDAO.findMongoPriceExecution(t.toString());
            monsPriceMap.put(t, priceMap);
        });

        //获取银行代码
        SystemCommonConfigEntity systemCommonConfigEntity = new SystemCommonConfigEntity();

        systemCommonConfigEntity.setType("CONNECT_BANK");
        // 根据结算户获取计量点id
        String returnSystemCommonConfig =
                restTemplate.postForObject(Global.getConfig("findSystemCommonConfigByType"), systemCommonConfigEntity,
                        String.class);

        List<SystemCommonConfigEntity> systemCommonConfigEntities =
                JSONObject.parseArray(returnSystemCommonConfig, SystemCommonConfigEntity.class);

        Map<Long, String> systemCommonConfigEntityMap =
                systemCommonConfigEntities.stream().collect(Collectors.toMap(SystemCommonConfigEntity::getParamKey, a -> a.getParamValue(), (k1, k2) -> k1));
        //批量获取余额信息
        List<Long> settlerIds =
                chargeDomains.stream().filter(t -> t.getSettlementId() != null).map(ChargeDomain::getSettlementId).collect(Collectors.toList());

        List<PreChargeDomain> preChargeDomains =
                preChargeDAO.findPreChargeBySettleIds(settlerIds);

        Map<Long, PreChargeDomain> preChargeDomainMap =
                preChargeDomains.stream().collect(Collectors.toMap(PreChargeDomain::getSettlementId, a -> a, (k1, k2) -> k1));

        List<Long> meterIds =
                chargeDomains.stream().filter(t->t.getMeterId()!=null).map(ChargeDomain::getMeterId).distinct().collect(Collectors.toList());

        ///批量获取抄表信息
        Map<Long, MeterDomain> meterDomainMap = null;
        Map<Long, PriceTypeDomain> priceTypeDomainMap = null;
        Map<Long, MeterMoneyDomain> meterMoneyDomainMap=null;
        Map<String, List<WriteFilesDomain>> writeFilesDomainMap=null;
        if (meterIds != null && meterIds.size() > 0) {

            WriteFilesDomain writeFilesDomain = new WriteFilesDomain();
            writeFilesDomain.setMons(monList);
            writeFilesDomain.setMeterIds(meterIds);
            writeFilesDomain.setTimeSeg((byte) 0);
            List<WriteFilesDomain> returnWriteFilesDomains =
                    writeFilesDAO.findByMeterIdsAndMon(writeFilesDomain);

            writeFilesDomainMap =
                    returnWriteFilesDomains.stream().collect(Collectors.groupingBy(o -> o.getMon().toString() + "_" + o.getMeterId().toString() + "_" + o.getSn().toString()));

            //批量获取计量点信息
            HttpResult httpResult = restTemplate.postForObject(Global.getConfig("getMeterByMeterIdsWithoutStatus"), meterIds,
                    HttpResult.class);

            List<MeterDomain> meterDomains = JSONObject.parseArray(JSONArray.toJSONString(httpResult.getResultData()),
                    MeterDomain.class);

            meterDomainMap = meterDomains.stream()
                    .collect(Collectors.toMap(MeterDomain::getId, a -> a, (k1, k2) -> k1));

            //批量获取电价类型信息
            List<Long> priceTypeIds =
                    meterDomains.stream().filter(t -> t.getPriceType() != null).map(MeterDomain::getPriceType).distinct().collect(Collectors.toList());

            List<PriceTypeDomain> priceTypeDomainList =
                    priceTypeDAO.findByListKey(priceTypeIds);

            priceTypeDomainMap =
                    priceTypeDomainList.stream().collect(Collectors.toMap(PriceTypeDomain::getId, a -> a, (k1, k2) -> k1));

            //批量获取电费信息
            List<Long> meterMoneyIds =
                    chargeDomains.stream().filter(t -> t.getMeterMoneyId() != null).map(ChargeDomain::getMeterMoneyId).distinct().collect(Collectors.toList());

            List<MeterMoneyDomain> meterMoneyDomains = new ArrayList<>();

            int len = meterMoneyIds.size();

            //无电费记录 预收之类
            if (len != 0) {
                for (int m = 0; m < len / 1999 + 1; m++) {// 遍历次数

                    List<Long> tl = meterMoneyIds.subList(m * 1999,
                            (m + 1) * 1999 > len ? len : (m + 1) * 1999);

                    meterMoneyDomains.addAll(meterMoneyDAO.findMeterMoneyByIds(tl));
                }

                meterMoneyDomainMap =
                        meterMoneyDomains.stream().collect(Collectors.toMap(MeterMoneyDomain::getId, a -> a, (k1, k2) -> k1));
            }
        }

        //获取结算户信息
        postData.clear();
        postData.put("settleIds", settlerIds);
        HttpResult<List<SettlementEntity>> settleResult =
                titanTemplate.postJson("CIM-SERVER",
                        "cimServer/cim_bill?method=findSettlementByIds", new HttpHeaders(), postData,
                        new TypeReference<HttpResult<List<SettlementEntity>>>() {
                        });

        // 根据结算户分组
        Map<Long, SettlementEntity> settlementMap = settleResult.getResultData().stream()
                .collect(Collectors.toMap(SettlementEntity::getId, a -> a, (k1, k2) -> k1));
        System.out.println("开始构造发票实体");
        // 构造发票实体
        for (ChargeDomain t : chargeDomains) {
            int hisMon = Integer.valueOf(Global.getConfig("mon"));
            if (hisMon <= t.getMon()) {
                hisMon = t.getMon();
            }
            BigDecimal factMoney = t.getFactMoney();
            // 赋值收费信息
            NoteInfoDomain noteInfoDomain = new NoteInfoDomain();
            noteInfoDomain.setChargeInfoId(t.getId());
            noteInfoDomain.setMon(t.getMon());
            noteInfoDomain.setMeterMoneyId(t.getMeterMoneyId());
            noteInfoDomain.setMeterId(t.getMeterId());
            noteInfoDomain.setSn(t.getSn());
            noteInfoDomain.setYsTypeCode((long) t.getYsTypeCode());
            noteInfoDomain.setFactMoney(factMoney);
            // 阶梯暂赋值0
            noteInfoDomain.setArrears(t.getShouldMoney());
            noteInfoDomain.setFactPre(t.getFactPre());
            noteInfoDomain.setSettlementId(t.getSettlementId());
            noteInfoDomain.setPunishMoney(t.getFactPunish());
            noteInfoDomain.setThisBalance(BigDecimal.ZERO);
            noteInfoDomain.setLastBalance(BigDecimal.ZERO);
            noteInfoDomain.setPayDate(t.getPayDate());
            // 采用收费记录的id补全
            noteInfoDomain.setNoteFlowNo(String.format("%10d", new Integer(t.getFlowNo())).replace(" ", "0"));

            SettlementEntity settlementEntity = settlementMap.get(t.getSettlementId());
            noteInfoDomain.setConnectBank(settlementEntity.getConnectBank());
            noteInfoDomain.setBankNo(settlementEntity.getBankNo());
            noteInfoDomain.setSettlementNo(settlementEntity.getSettlementNo());
            noteInfoDomain.setSettlementName(settlementEntity.getSettlementName());
            noteInfoDomain.setIsPrint(0);
            noteInfoDomain.setBankName(systemCommonConfigEntityMap.get(noteInfoDomain.getConnectBank()));

            PriceTypeDomain returnPriceType=new PriceTypeDomain();
            PriceExecutionDomain priceExecutionDomain= new PriceExecutionDomain();

            if (meterDomainMap != null && t.getMeterId() != null) {
                MeterDomain appMeterInfosByCim = meterDomainMap.get(t.getMeterId());
                Long priceTypeId = appMeterInfosByCim.getPriceType();
                // 明细 参数格式如:2017居民生活用水(阶梯)_起码_止码_倍率_加减/变损_电量_电价_金额;
                 returnPriceType=
                        priceTypeDomainMap.get(priceTypeId);

                noteInfoDomain.setPriceTypeId(returnPriceType.getId());
                noteInfoDomain.setPriceName(returnPriceType.getPriceName());

                //获取当月电价
                priceExecutionDomain =
                        monsPriceMap.get(hisMon).get(returnPriceType.getId())
                                .stream().filter(m -> m.getTimeSeg() == 0).collect(Collectors.toList()).get(0);
            }

        StringBuffer meterItem = new StringBuffer();
        //部分余额冲抵不生成发票
        if (t.getfChargeMode() == 4 && t.getDeductionBalance().compareTo(t.getArrears()) != 0) {
            meterItem = null;
        } else if (t.getMeterMoneyId() != null && !"".equals(t.getMeterMoneyId())) {
            MeterMoneyDomain returnMeterMoney =
                    meterMoneyDomainMap.get(t.getMeterMoneyId());
            noteInfoDomain.setBasicMoney(returnMeterMoney.getBasicMoney());
            noteInfoDomain.setPowerRateMoney(returnMeterMoney.getPowerRateMoney());
            String key =
                    returnMeterMoney.getMon().toString()
                            + "_" + returnMeterMoney.getMeterId().toString()
                            + "_" + returnMeterMoney.getSn().toString();

            List<WriteFilesDomain> writeFilesDomains = writeFilesDomainMap.get(key);

            BigDecimal transAndAddPower = BigDecimal.ZERO;
            if (returnMeterMoney.getActiveTransformerLossPower() != null) {
                transAndAddPower =
                        transAndAddPower.add(returnMeterMoney.getActiveTransformerLossPower());
            }
            if (returnMeterMoney.getAddPower() != null) {
                transAndAddPower =
                        transAndAddPower.add(returnMeterMoney.getAddPower());
            }

            if (writeFilesDomains != null && writeFilesDomains.size() >= 1) {
                WriteFilesDomain returnWriteFile = writeFilesDomains.get(0);

                // 表中无倍率 默认1
                BigDecimal factorNum = new BigDecimal("1");
                if (returnWriteFile.getFactorNum() != null) {
                    factorNum = returnWriteFile.getFactorNum();
                }

                meterItem = (
                        new StringBuffer(returnPriceType.getPriceName()).append("_")
                                .append(returnWriteFile.getStartNum()).append("_")
                                .append(returnWriteFile.getEndNum()).append("_")
                                .append(factorNum).append("_")
                                .append(transAndAddPower).append("_")
                                .append(returnMeterMoney.getTotalPower()).append("_")
                                .append(priceExecutionDomain.getPrice()).append("_")
                                .append(t.getShouldMoney().subtract(returnMeterMoney.getBasicMoney() == null ? BigDecimal.ZERO : returnMeterMoney.getBasicMoney())));

            } else {
                meterItem = (
                        new StringBuffer(returnPriceType.getPriceName()).append("_")
                                .append("0").append("_")
                                .append("0").append("_")
                                .append("1").append("_")
                                .append(transAndAddPower).append("_")
                                .append(returnMeterMoney.getTotalPower()).append("_")
                                .append(priceExecutionDomain.getPrice()).append("_")
                                .append(t.getShouldMoney().subtract(returnMeterMoney.getBasicMoney() == null ? BigDecimal.ZERO : returnMeterMoney.getBasicMoney())));
            }
        } else if (t.getYsTypeCode() == 2) {
            meterItem =
                    new StringBuffer("预收电费").append("_")
                            .append("0").append("_")
                            .append("0").append("_")
                            .append("1").append("_")
                            .append("0").append("_")
                            .append("0").append("_")
                            .append("0").append("_")
                            .append(t.getFactPre());
        } else if (t.getYsTypeCode() == 3) {
            meterItem =
                    new StringBuffer("预存退款").append("_")
                            .append("0").append("_")
                            .append("0").append("_")
                            .append("1").append("_")
                            .append("0").append("_")
                            .append("0").append("_")
                            .append("0").append("_")
                            .append(t.getFactPre());
        } else if (t.getfChargeMode() == 6) {
            meterItem = new StringBuffer("退费冲预收").append("_")
                    .append("0").append("_")
                    .append("0").append("_")
                    .append("1").append("_")
                    .append("0").append("_")
                    .append("0").append("_")
                    .append("0").append("_")
                    .append(t.getFactTotal());
        } else {
            meterItem = new StringBuffer("历史电费").append("_")
                    .append("0").append("_")
                    .append("0").append("_")
                    .append("1").append("_")
                    .append("0").append("_")
                    .append("0").append("_")
                    .append("0").append("_")
                    .append(t.getFactTotal());
        }
        noteInfoDomain.setMeterItem(meterItem == null ? null : meterItem.toString());

        // 赋值余额信息
        /*PreChargeDomain preChargeDomain = preChargeDomainMap.get(t.getSettlementId());*/


          /*  if (preChargeDomain == null) {
                noteInfoDomain.setLastBalance(BigDecimal.ZERO);
            } else {
                noteInfoDomain.setLastBalance(preChargeDomain.getBalance());
            }*/
        noteInfoDomains.add(noteInfoDomain);
    }
        return new

    HttpResult(HttpResult.SUCCESS, "生成发票记录成功",noteInfoDomains);
}

    @Override
    public int updateList(List<NoteInfoDomain> noteInfoDomains) {
        // TODO Auto-generated method stub
        return noteInfoDAO.updateList(noteInfoDomains);
    }

    @Override
    public List<ChargeInfoDetailEntity> findNoteInfoDetails(ChargeInfoEntity chargeInfoEntity) {
        return noteInfoDAO.findNoteInfoDetails(chargeInfoEntity);
    }

    @Override
    public int insertList(List<NoteInfoDomain> noteInfoDomains) {
        return noteInfoDAO.insertList(noteInfoDomains);
    }

;
}
