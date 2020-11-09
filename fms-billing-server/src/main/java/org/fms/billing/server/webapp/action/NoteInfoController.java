/**
 * Author : czy
 * Date : 2019年7月4日 下午3:36:21
 * Title : com.riozenc.billing.webapp.controller.ComputeAction.java
 **/
package org.fms.billing.server.webapp.action;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fms.billing.common.util.FormatterUtil;
import org.fms.billing.common.webapp.domain.ChargeDomain;
import org.fms.billing.common.webapp.domain.MeterMoneyDomain;
import org.fms.billing.common.webapp.domain.NoteInfoDomain;
import org.fms.billing.common.webapp.domain.PriceLadderRelaDomain;
import org.fms.billing.common.webapp.domain.beakInterface.BatchChargeDomain;
import org.fms.billing.common.webapp.entity.BankEntity;
import org.fms.billing.common.webapp.entity.ChargeInfoDetailEntity;
import org.fms.billing.common.webapp.entity.ChargeInfoEntity;
import org.fms.billing.common.webapp.entity.InvoiceEntity;
import org.fms.billing.common.webapp.entity.MapEntity;
import org.fms.billing.common.webapp.entity.SettlementEntity;
import org.fms.billing.common.webapp.entity.UserInfoEntity;
import org.fms.billing.common.webapp.service.IChargeService;
import org.fms.billing.common.webapp.service.IMeterMoneyService;
import org.fms.billing.common.webapp.service.INoteInfoService;
import org.fms.billing.common.webapp.service.IPriceLadderRelaService;
import org.fms.billing.server.webapp.service.ChargeServiceImpl;
import org.fms.billing.server.webapp.service.CimService;
import org.fms.billing.server.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.riozenc.titanTool.common.json.utils.JSONUtil;
import com.riozenc.titanTool.properties.Global;
import com.riozenc.titanTool.spring.web.http.HttpResult;
import com.riozenc.titanTool.spring.web.http.HttpResultPagination;

//发票模块
@ControllerAdvice
@RequestMapping("invoice")
public class NoteInfoController {
    private Log logger = LogFactory.getLog(ChargeServiceImpl.class);

    @Autowired
    @Qualifier("noteInfoServiceImpl")
    private INoteInfoService iNoteInfoService;

    @Autowired
    @Qualifier("chargeServiceImpl")
    private IChargeService iChargeService;

    @Autowired
    @Qualifier("meterMoneyServiceImpl")
    private IMeterMoneyService meterMoneyService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("priceLadderRelaImpl")
    private IPriceLadderRelaService iPriceLadderRelaService;

    @Autowired
    private CimService cimService;

    @Autowired
    private UserService userService;


    //打印发票界面的打印结算单
    @RequestMapping(params = "method=printNoteInfoByJSD")
    @ResponseBody
    public HttpResult printNoteInfoByJSD(@RequestBody String noteInfoJson) {
        //返回ids格式[{'ids':{1,2,3}},{'ids:{4,5,6}'}]
        JSONObject noteJsonObject = JSONObject.parseObject(noteInfoJson);
        List<ChargeInfoDetailEntity> receiveNoteInfoDomains =
                (JSONObject.parseArray(noteJsonObject.getString("data"), ChargeInfoDetailEntity.class));

        List<String> receiveIdsList =
                receiveNoteInfoDomains.stream().map(ChargeInfoDetailEntity::getIds).collect(Collectors.toList());

        List<Long> iddsList = new ArrayList<>();

        receiveIdsList.forEach(t -> {
            iddsList.addAll(Arrays.asList(t.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList()));
        });


        List<NoteInfoDomain> noteInfoDomains = new ArrayList<>();


        for (Long id : iddsList) {
            NoteInfoDomain noteInfoDomain = new NoteInfoDomain();
            noteInfoDomain.setId(id);
            noteInfoDomains.add(noteInfoDomain);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userInfo", noteJsonObject.getString("userInfo"));
        jsonObject.put("data", JSONObject.toJSONString(iNoteInfoService.findNoteInfoByIds(noteInfoDomains)));

        return restTemplate.postForObject(Global.getConfig("invoiceStatement"), jsonObject, HttpResult.class);
    }


    //打印发票界面的打印电子发票
    @RequestMapping(params = "method=printNoteInfoByFP")
    @ResponseBody
    public HttpResult printNoteInfoByFP(@RequestBody String noteInfoJson) throws IOException {
        //返回ids格式[{'ids':{1,2,3}},{'ids:{4,5,6}'}]
        JSONObject noteJsonObject = JSONObject.parseObject(noteInfoJson);

        Integer kplx = noteJsonObject.getInteger("kplx");

        List<ChargeInfoDetailEntity> receiveNoteInfoDomains =
                (JSONObject.parseArray(noteJsonObject.getString("data"), ChargeInfoDetailEntity.class));

        if (receiveNoteInfoDomains == null || receiveNoteInfoDomains.size() < 1) {
            return new HttpResult(HttpResult.ERROR, "无要打印的发票数据,请选中后打印");
        }

        Integer isPrint = receiveNoteInfoDomains.get(0).getIsPrint();

        //无法作废未打印的电子发票
        if (kplx == 1 && isPrint != 1) {
            return new HttpResult(HttpResult.ERROR, "电子发票未打印,无法作废");
        }

        List<String> receiveIdsList =
                receiveNoteInfoDomains.stream().map(ChargeInfoDetailEntity::getIds).collect(Collectors.toList());

        List<Long> iddsList = new ArrayList<>();

        receiveIdsList.forEach(t -> {
            iddsList.addAll(Arrays.asList(t.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList()));
        });


        List<NoteInfoDomain> noteInfoDomains = new ArrayList<>();


        for (Long id : iddsList) {
            NoteInfoDomain noteInfoDomain = new NoteInfoDomain();
            noteInfoDomain.setId(id);
            noteInfoDomains.add(noteInfoDomain);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userInfo", noteJsonObject.getString("userInfo"));
        jsonObject.put("kplx", kplx);
        jsonObject.put("data", JSONObject.toJSONString(iNoteInfoService.findNoteInfoByIds(noteInfoDomains)));
        return printNoteInfo(jsonObject.toJSONString());
    }


    //打印发票
    @RequestMapping(params = "method=printNoteInfo")
    @ResponseBody
    public HttpResult printNoteInfo(@RequestBody String noteInfoJson) throws IOException {

        JSONObject jsonObject = JSONObject.parseObject(noteInfoJson);

        UserInfoEntity userInfoEntity =
                JSONObject.parseObject(jsonObject.getString("userInfo"),
                        UserInfoEntity.class);
        //发票记录
        List<NoteInfoDomain> noteInfoDomains =
                (JSONObject.parseArray(jsonObject.getString("data"), NoteInfoDomain.class));

        List<NoteInfoDomain> noteInfoByPreCharges =
                noteInfoDomains.stream().filter(t -> t.getYsTypeCode() != 2).collect(Collectors.toList());

        if (noteInfoByPreCharges == null || noteInfoByPreCharges.size() < 1) {
            return new HttpResult(HttpResult.ERROR, "预收电费不打电子发票");
        }


        Map<String, List<NoteInfoDomain>> noteGroupMap =
                noteInfoDomains.stream().collect(Collectors.groupingBy(m -> m.getMon() + "_" + m.getSettlementId() + "_" + m.getSn()));


        //获取结算户信息
        List<Long> settlementIds = noteInfoDomains.stream().filter(i -> i.getSettlementId() != null)
                .map(NoteInfoDomain::getSettlementId).distinct().collect(Collectors.toList());

        String settlementJson = restTemplate.postForObject(Global.getConfig("findSettlementByIds"),
                settlementIds, String.class);


        List<SettlementEntity> settlementEntities = JSONUtil
                .readValue(settlementJson, new TypeReference<HttpResult<List<SettlementEntity>>>() {
                }).getResultData();

        settlementEntities.forEach(t -> {
            if (t.getInvoiceType() == null) {
                t.setInvoiceType((byte) 1);
            }
        });

        // 过滤掉增值税信息
        settlementEntities =
                settlementEntities.stream().filter(t -> t.getInvoiceType() != 2).collect(toList());

        if (settlementEntities == null || settlementEntities.size() < 1) {
            return new HttpResult(HttpResult.ERROR, "增值税开票用户不能打印电子发票！");
        }

        Map<Long, SettlementEntity> settementMap = settlementEntities.stream().collect(Collectors.toMap(SettlementEntity::getId, k -> k));

        //获取开户行
        //开户银行
        BankEntity bankEntity = new BankEntity();
        bankEntity.setPageSize(-1);
        List<BankEntity> bankArray =
                cimService.getBank(bankEntity);
        Map<Long, BankEntity> bankEntityMap =
                bankArray.stream().collect(Collectors.toMap(BankEntity::getId, a -> a, (k1, k2) -> k1));

        List<NoteInfoDomain> invoiceNoteInfos = new ArrayList<>();

        //将发票记录按月份+户号合成一张发票
        for (String key : noteGroupMap.keySet()) {
            List<NoteInfoDomain> noteGroupMapValue = noteGroupMap.get(key);

            List<Long> chargeInfoIds =
                    noteGroupMapValue.stream().map(NoteInfoDomain::getChargeInfoId).collect(Collectors.toList());

            //Long类型转String
            List<ChargeDomain> chargeDomains =
                    iChargeService.findChargeByIds(chargeInfoIds);

            List<Long> meterMoneyIds =
                    chargeDomains.stream().map(ChargeDomain::getMeterMoneyId).collect(Collectors.toList());

            List<Long> monSettlementIds =
                    noteInfoDomains.stream().map(NoteInfoDomain::getSettlementId).distinct().collect(Collectors.toList());

            ChargeDomain chargeInfoDomain = new ChargeDomain();
            chargeInfoDomain.setfChargeMode(4);
            chargeInfoDomain.setMon(Integer.valueOf(noteGroupMapValue.get(0).getMon().toString()));
            chargeInfoDomain.setSettlementIds(monSettlementIds);
            List<ChargeDomain> lastBalanceDomains =
                    iChargeService.findByWhere(chargeInfoDomain);

            List<MeterMoneyDomain> meterMoneyDomains =
                    meterMoneyService.findMeterMoneyByIds(meterMoneyIds);

            List<PriceLadderRelaDomain> monpriceLadderRelaDomains =
                    iPriceLadderRelaService.findMongoPriceLadderRela(noteGroupMapValue.get(0).getMon().toString());

            Map<String, PriceLadderRelaDomain> priceLadderRelaMapByPriceAndSn =
                    monpriceLadderRelaDomains.stream().collect(Collectors.toMap(o -> o.getPriceExecutionId() + "_" + o.getLadderSn(), a -> a, (k1, k2) -> k1));


            String meterItem =
                    noteGroupMapValue.stream().map(NoteInfoDomain::getMeterItem).collect(Collectors.joining(";"));
            NoteInfoDomain noteInfoDomain = noteGroupMapValue.get(0);

            BankEntity noteInoBank =
                    bankEntityMap.get(settementMap.get(noteInfoDomain.getSettlementId()).getOpendingBank());

            Long operatorId =
                    chargeDomains.stream().map(ChargeDomain::getOperator).max(Long::compareTo).get();

            UserInfoEntity paramUserInfo = new UserInfoEntity();
            paramUserInfo.setId(operatorId.intValue());
            List<MapEntity> listmap = userService.findMapByDomain(paramUserInfo);
            if (listmap == null || listmap.size() < 1) {
                noteInfoDomain.setCollectorId("");
            } else {
                Map<Long, String> userMap = FormatterUtil.ListMapEntityToMap(listmap);
                noteInfoDomain.setCollectorId(userMap.get(operatorId));
            }

            noteInfoDomain.setIds(noteGroupMapValue.stream().map(NoteInfoDomain::getId).collect(Collectors.toList()));
            noteInfoDomain.setPrintManName(userInfoEntity.getUserName());
            noteInfoDomain.setAddress(settementMap.get(noteInfoDomain.getSettlementId()).getAddress());
            noteInfoDomain.setSettlementPhone(settementMap.get(noteInfoDomain.getSettlementId()).getSettlementPhone());
            noteInfoDomain.setOpenindBank(noteInoBank == null ? "" : noteInoBank.getBankName());
            noteInfoDomain.setCuscc(settementMap.get(noteInfoDomain.getSettlementId()).getCuscc());
            noteInfoDomain.setFactMoney(noteGroupMapValue.stream().filter(t -> t.getFactMoney() != null).map(NoteInfoDomain::getFactMoney).reduce(BigDecimal.ZERO, BigDecimal::add));
            noteInfoDomain.setThisBalance(noteGroupMapValue.stream().filter(t -> t.getThisBalance() != null).map(NoteInfoDomain::getThisBalance).reduce(BigDecimal.ZERO, BigDecimal::add));
            noteInfoDomain.setLastBalance(lastBalanceDomains.stream().filter(t -> t.getDeductionBalance() != null).map(ChargeDomain::getDeductionBalance).reduce(BigDecimal.ZERO, BigDecimal::add));
            noteInfoDomain.setLadder1Power(meterMoneyDomains.stream().filter(t -> t.getLadder1Power() != null).map(MeterMoneyDomain::getLadder1Power).reduce(BigDecimal.ZERO, BigDecimal::add));
            noteInfoDomain.setLadder2Power(meterMoneyDomains.stream().filter(t -> t.getLadder2Power() != null).map(MeterMoneyDomain::getLadder2Power).reduce(BigDecimal.ZERO, BigDecimal::add));
            noteInfoDomain.setLadder3Power(meterMoneyDomains.stream().filter(t -> t.getLadder3Power() != null).map(MeterMoneyDomain::getLadder3Power).reduce(BigDecimal.ZERO, BigDecimal::add));
            noteInfoDomain.setLadder4Power(meterMoneyDomains.stream().filter(t -> t.getLadder4Power() != null).map(MeterMoneyDomain::getLadder4Power).reduce(BigDecimal.ZERO, BigDecimal::add));
            noteInfoDomain.setLadder1Money(meterMoneyDomains.stream().filter(t -> t.getLadder1Money() != null).map(MeterMoneyDomain::getLadder1Money).reduce(BigDecimal.ZERO, BigDecimal::add));
            noteInfoDomain.setLadder2Money(meterMoneyDomains.stream().filter(t -> t.getLadder2Money() != null).map(MeterMoneyDomain::getLadder2Money).reduce(BigDecimal.ZERO, BigDecimal::add));
            noteInfoDomain.setLadder3Money(meterMoneyDomains.stream().filter(t -> t.getLadder3Money() != null).map(MeterMoneyDomain::getLadder3Money).reduce(BigDecimal.ZERO, BigDecimal::add));
            noteInfoDomain.setLadder4Money(meterMoneyDomains.stream().filter(t -> t.getLadder4Money() != null).map(MeterMoneyDomain::getLadder4Money).reduce(BigDecimal.ZERO, BigDecimal::add));

            PriceLadderRelaDomain priceLadderRela1 = priceLadderRelaMapByPriceAndSn.get(noteInfoDomain.getPriceTypeId() + "_" + "1");
            PriceLadderRelaDomain priceLadderRela2 = priceLadderRelaMapByPriceAndSn.get(noteInfoDomain.getPriceTypeId() + "_" + "2");
            PriceLadderRelaDomain priceLadderRela3 = priceLadderRelaMapByPriceAndSn.get(noteInfoDomain.getPriceTypeId() + "_" + "3");
            PriceLadderRelaDomain priceLadderRela4 = priceLadderRelaMapByPriceAndSn.get(noteInfoDomain.getPriceTypeId() + "_" + "4");

            noteInfoDomain.setLadder1Limit(priceLadderRela1 == null ? BigDecimal.ZERO : new BigDecimal(priceLadderRela1.getLadderValue()));
            noteInfoDomain.setLadder2Limit(priceLadderRela2 == null ? BigDecimal.ZERO : new BigDecimal(priceLadderRela2.getLadderValue()));
            noteInfoDomain.setLadder3Limit(priceLadderRela3 == null ? BigDecimal.ZERO : new BigDecimal(priceLadderRela3.getLadderValue()));
            noteInfoDomain.setLadder4Limit(priceLadderRela4 == null ? BigDecimal.ZERO : new BigDecimal(priceLadderRela4.getLadderValue()));

            noteInfoDomain.setBasicMoney(noteGroupMapValue.stream().filter(t -> t.getBasicMoney() != null).map(NoteInfoDomain::getBasicMoney).reduce(BigDecimal.ZERO, BigDecimal::add));
            noteInfoDomain.setPowerRateMoney(noteGroupMapValue.stream().filter(t -> t.getPowerRateMoney() != null).map(NoteInfoDomain::getPowerRateMoney).reduce(BigDecimal.ZERO, BigDecimal::add));

            BigDecimal arrears =
                    noteGroupMapValue.stream().filter(t -> t.getFactMoney() != null).map(NoteInfoDomain::getArrears).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal punishMoney =
                    noteGroupMapValue.stream().filter(t -> t.getPunishMoney() != null).map(NoteInfoDomain::getPunishMoney).reduce(BigDecimal.ZERO, BigDecimal::add);

            if (arrears == null) {
                arrears = BigDecimal.ZERO;
            }

            if (punishMoney == null) {
                punishMoney = BigDecimal.ZERO;
            }
            noteInfoDomain.setFactMoney(noteInfoDomain.getFactMoney().add(punishMoney));
            noteInfoDomain.setPunishMoney(punishMoney);
            noteInfoDomain.setArrears(arrears.add(punishMoney));
            noteInfoDomain.setFactPre(noteGroupMapValue.stream().filter(t -> t.getFactPre() != null).map(NoteInfoDomain::getFactPre).reduce(BigDecimal.ZERO, BigDecimal::add));
            noteInfoDomain.setMeterItem(meterItem);
            noteInfoDomain.setPrintMan((long) userInfoEntity.getId());
            invoiceNoteInfos.add(noteInfoDomain);
        }

        invoiceNoteInfos =
                invoiceNoteInfos.stream().sorted(Comparator.comparing(NoteInfoDomain::getMon)
                        .thenComparing(NoteInfoDomain::getSn)).collect(Collectors.toList());
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setKplx(jsonObject.getInteger("kplx"));
        invoiceEntity.setNoteInfoDomains(invoiceNoteInfos);

        return restTemplate.postForObject(Global.getConfig("download"),
                invoiceEntity, HttpResult.class);

    }

    @RequestMapping("updateList")
    @ResponseBody
    public HttpResult updateList(@RequestBody(required = false) String noteJson) {
        NoteInfoDomain noteInfoDomain =
                JSONObject.parseObject(noteJson, NoteInfoDomain.class);
        List<NoteInfoDomain> noteInfoDomains = new ArrayList<>();

        if (noteInfoDomain.getIsPrint() != null && 0 == (noteInfoDomain.getIsPrint())) {

            //作废电子发票 查出电子发票记录 更改noteflowno
            for (Long id : noteInfoDomain.getIds()) {
                NoteInfoDomain paramNnoteInfoDomain = new NoteInfoDomain();
                paramNnoteInfoDomain.setId(id);
                noteInfoDomains.add(paramNnoteInfoDomain);
            }

            List<NoteInfoDomain> paramNoteInfoDomains =
                    iNoteInfoService.findNoteInfoByIds(noteInfoDomains);
            if (paramNoteInfoDomains == null || paramNoteInfoDomains.size() < 1) {
                return new HttpResult(HttpResult.SUCCESS, "更新失败：不存在的发票记录");
            }

            Map<String, List<NoteInfoDomain>> noteFlowMap =
                    paramNoteInfoDomains.stream().collect(Collectors.groupingBy(NoteInfoDomain::getNoteFlowNo));

            int size = noteFlowMap.keySet().size();

            Map<String, Object> flowParam = new HashMap<>();
            flowParam.clear();
            flowParam.put("strategy", null);
            flowParam.put("size", size);

            //实收流水号
            String flowNoJson = restTemplate.postForObject(Global.getConfig(
                    "getChargeFlowNoBySize"), flowParam, String.class);

            Long flowNo = JSONObject.parseObject(flowNoJson, Long.class);
            logger.info("流水号===="+flowNo);

            List<NoteInfoDomain> dealNoteInfoDomains = new ArrayList<>();
            List<ChargeDomain> dealChargeDomains = new ArrayList<>();

            for (String key : noteFlowMap.keySet()) {
                List<NoteInfoDomain> interNoteInfoDomains = noteFlowMap.get(key);
                Long interFlowNo = flowNo++;
                interNoteInfoDomains.forEach(t -> {
                    NoteInfoDomain paramNoteInfoDoamin = new NoteInfoDomain();
                    paramNoteInfoDoamin.setId(t.getId());
                    paramNoteInfoDoamin.setIsPrint(noteInfoDomain.getIsPrint());
                    paramNoteInfoDoamin.setYfpdm(noteInfoDomain.getYfpdm());
                    paramNoteInfoDoamin.setYfphm(noteInfoDomain.getYfphm());
                    paramNoteInfoDoamin.setPrintMan(noteInfoDomain.getPrintMan());
                    paramNoteInfoDoamin.setPrintManName(noteInfoDomain.getPrintManName());
                    paramNoteInfoDoamin.setPrintDate(noteInfoDomain.getPrintDate());
                    paramNoteInfoDoamin.setNotePath(noteInfoDomain.getNotePath());
                    paramNoteInfoDoamin.setNotePrintNo(noteInfoDomain.getNotePrintNo());
                    paramNoteInfoDoamin.setNoteFlowNo(String.format("%10d", interFlowNo.intValue()).replace(" ", "0"));
                    dealNoteInfoDomains.add(paramNoteInfoDoamin);

                    ChargeDomain paramChargeDomain = new ChargeDomain();
                    paramChargeDomain.setId(t.getChargeInfoId());
                    paramChargeDomain.setFlowNo(interFlowNo.toString());
                    dealChargeDomains.add(paramChargeDomain);
                });
                iNoteInfoService.updateList(dealNoteInfoDomains);
                iChargeService.updateList(dealChargeDomains);
            }

        } else {
            noteInfoDomains.add(noteInfoDomain);
            iNoteInfoService.updateByIds(noteInfoDomains);
        }
        return new HttpResult(HttpResult.SUCCESS, "更新成功");
    }

    // 发票 明细
    @RequestMapping("findNoteInfoDetails")
    @ResponseBody
    public HttpResultPagination findChargeInfoDetails(@RequestBody String chargeInfoJson) {

        ChargeInfoEntity chargeInfoEntity = JSONObject.parseObject(chargeInfoJson, ChargeInfoEntity.class);

        if ("".equals(chargeInfoEntity.getStartMon())) {
            chargeInfoEntity.setStartMon(null);
        }
        List<ChargeInfoDetailEntity> chargeInfoDetailEntities =
                iNoteInfoService.findNoteInfoDetails(chargeInfoEntity);

        return new HttpResultPagination<ChargeInfoDetailEntity>(chargeInfoEntity, chargeInfoDetailEntities);
    }


    //根据收费id 获取发票信息 --预收余额查询调用
    @RequestMapping("findNoteInfoByWhere")
    @ResponseBody
    public List<NoteInfoDomain> findNoteInfoByWhere(@RequestBody String noteInfoDomainJson) {

        NoteInfoDomain noteInfoDomain = JSONObject.parseObject(noteInfoDomainJson, NoteInfoDomain.class);


        List<NoteInfoDomain> chargeInfoDetailEntities =
                iNoteInfoService.findByWhere(noteInfoDomain);

        return chargeInfoDetailEntities;
    }


    /**
     * 托收打印电子发票
     *
     * @param batchChargeAndPrintNoteJson
     * @return
     */
    @RequestMapping("batchChargeAndPrintNoteInfo")
    @ResponseBody
    public HttpResult batchChargeAndPrintNoteInfo(@RequestBody(required = false) String batchChargeAndPrintNoteJson) throws IOException {

        JSONObject jsonObject = JSONObject.parseObject(batchChargeAndPrintNoteJson);
        UserInfoEntity userInfoEntity =
                JSONObject.parseObject(jsonObject.getString("userInfo"),
                        UserInfoEntity.class);

        List<BatchChargeDomain> bankChargeDomains =
                JSONObject.parseArray(jsonObject.getString("data"),
                        BatchChargeDomain.class);

        if (bankChargeDomains == null || bankChargeDomains.size() < 1) {
            return new HttpResult(HttpResult.ERROR, "没有要银行托收的结算户");
        }

        List<String> arrearageChargeIds =
                bankChargeDomains.stream().map(BatchChargeDomain::getIds).collect(toList());

        if (arrearageChargeIds != null || arrearageChargeIds.size() > 0) {


            List<Long> arrearageChargeList = new ArrayList<>();

            arrearageChargeIds.forEach(t -> {
                arrearageChargeList.addAll(Arrays.asList(t.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList()));
            });

            HttpResult httpResult = null;
            try {
                httpResult = iChargeService.batchCharge(arrearageChargeList, "1",
                        userInfoEntity.getId().longValue());
            } catch (Exception e) {
                e.printStackTrace();
                return new HttpResult(HttpResult.ERROR, "打印电子发票失败");
            }

            List<ChargeDomain> chargeDomains = (List<ChargeDomain>) httpResult.getResultData();

            List<Long> chargeInfoIds =
                    chargeDomains.stream().map(ChargeDomain::getId).distinct().collect(toList());

            NoteInfoDomain noteInfoDomain = new NoteInfoDomain();
            noteInfoDomain.setChargeInfoIds(chargeInfoIds);

            List<NoteInfoDomain> chargeInfoDetailEntities =
                    iNoteInfoService.findByWhere(noteInfoDomain);

            JSONObject paramJsonObject = new JSONObject();
            paramJsonObject.put("userInfo", jsonObject.getString("userInfo"));
            paramJsonObject.put("kplx", 0);
            paramJsonObject.put("data", JSONObject.toJSONString(chargeInfoDetailEntities));
            return printNoteInfo(paramJsonObject.toJSONString());
        }

        return new HttpResult(HttpResult.ERROR, "打印电子发票失败");
    }


}
