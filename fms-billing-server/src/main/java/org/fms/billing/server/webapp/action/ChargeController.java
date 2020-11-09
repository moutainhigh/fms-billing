package org.fms.billing.server.webapp.action;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.fms.billing.common.webapp.domain.ArrearageDetailDomain;
import org.fms.billing.common.webapp.domain.ArrearageDomain;
import org.fms.billing.common.webapp.domain.ChargeDomain;
import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.beakInterface.CustomerDomain;
import org.fms.billing.common.webapp.domain.beakInterface.SettlementDomain;
import org.fms.billing.common.webapp.entity.ChargeInfoDetailEntity;
import org.fms.billing.common.webapp.entity.ChargeInfoEntity;
import org.fms.billing.common.webapp.entity.PulishEntity;
import org.fms.billing.common.webapp.entity.SettlementEntity;
import org.fms.billing.common.webapp.service.IArrearageService;
import org.fms.billing.common.webapp.service.IChargeService;
import org.fms.billing.common.webapp.service.IPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping("charge")
public class ChargeController {
    @Autowired
    @Qualifier("chargeServiceImpl")
    private IChargeService iChargeService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("publishServiceImpl")
    private IPublishService iPublishService;
    @Autowired
    @Qualifier("arrearageServiceImpl")
    private IArrearageService iArrearageService;

    @Autowired
    private TitanTemplate titanTemplate;


    @RequestMapping("findChargeByIds")
    @ResponseBody
    public List<ChargeDomain> findChargeByIds(@RequestBody String chargeIdsJson) {

        List<Long> chargeIds = JSONObject.parseArray(chargeIdsJson,
                Long.class);

        List<ChargeDomain> chargeDomains =
                iChargeService.findChargeByIds(chargeIds);
        return chargeDomains;
    }

    @RequestMapping("select")
    @ResponseBody
    public HttpResult<List<ChargeDomain>> fingByWhere(@RequestBody String chargeJson) {
        ChargeDomain chargeDomain = JSONObject.parseObject(chargeJson, ChargeDomain.class);

        HttpResult<List<ChargeDomain>> httpResult = new HttpResult<List<ChargeDomain>>(HttpResult.SUCCESS,
                iChargeService.findByWhere(chargeDomain));
        return httpResult;
    }

    @RequestMapping("findByKey")
    @ResponseBody
    public ChargeDomain findByKey(@RequestBody String chargeJson) {
        ChargeDomain receviceChargeDomain = JSONObject.parseObject(chargeJson,
                ChargeDomain.class);

        ChargeDomain chargeDomain = iChargeService.findBykey(receviceChargeDomain);
        return chargeDomain;
    }

    @RequestMapping("insert")
    @ResponseBody
    public int insert(@ModelAttribute ChargeDomain chargeDomain) {
        return iChargeService.insert(chargeDomain);
    }

    @RequestMapping("update")
    @ResponseBody
    public int update(@ModelAttribute ChargeDomain chargeDomain) {
        return iChargeService.update(chargeDomain);
    }

    @RequestMapping("delete")
    @ResponseBody
    public int delete(@ModelAttribute ChargeDomain chargeDomain) {
        chargeDomain.setStatus(0);
        return iChargeService.update(chargeDomain);
    }

    // 缴费=更新欠费记录+插入收费记录
    @RequestMapping("charge")
    @ResponseBody
    @Transactional
    public HttpResult charge(@RequestBody String chargeJson) throws Exception {

        JSONObject countDataNObject = JSONObject.parseObject(chargeJson);
        // 余额
        BigDecimal balance = new BigDecimal(
                null == countDataNObject.getString("lastBalance") ? "0" : countDataNObject.getString("lastBalance"));

        String countData = countDataNObject.getString("countData");
        JSONObject chargeNObject = JSONObject.parseObject(countData);

        // 欠费记录id
        List<ArrearageDetailDomain> arrearageDetailDomains = JSONObject
                .parseArray(chargeNObject.getString("arrearageDetails"), ArrearageDetailDomain.class);

        List<ArrearageDetailDomain> totalArrearageDetails = JSONObject
                .parseArray(chargeNObject.getString("totalArrearageDetails"), ArrearageDetailDomain.class);

        if (totalArrearageDetails != null && totalArrearageDetails.size() > 0) {
            if (arrearageDetailDomains == null || arrearageDetailDomains.size() < 1) {
                return new HttpResult(HttpResult.ERROR, "缴费失败，必须选择欠费记录");
            }

            long size = arrearageDetailDomains.size();
            long distinctIds =
                    arrearageDetailDomains.stream().map(ArrearageDetailDomain::getId).distinct().count();

            if (size != distinctIds) {
                return new HttpResult(HttpResult.ERROR,
                        "存在相同的欠费记录，请注意结算户号是否重复录入");
            }

            if (arrearageDetailDomains.size() != totalArrearageDetails.size()) {

                Map<Long, List<ArrearageDetailDomain>> arrearageDetailDomainsMap =
                        arrearageDetailDomains.stream().collect(Collectors.groupingBy(ArrearageDetailDomain::getSettlementId));

                List<Long> arrearageDetailIds =
                        arrearageDetailDomains.stream().map(ArrearageDetailDomain::getId).collect(toList());

                //差集记录
                List<ArrearageDetailDomain> reduceArrearageDetails =
                        totalArrearageDetails.stream().filter(item -> !arrearageDetailIds.contains(item.getId())).collect(toList());

                Map<Long, List<ArrearageDetailDomain>> reduceArrearageDetailMap =
                        reduceArrearageDetails.stream().collect(Collectors.groupingBy(ArrearageDetailDomain::getSettlementId));

                for (Long key : reduceArrearageDetailMap.keySet()) {

                    //选中的月份
                    List<ArrearageDetailDomain> arrearageDetailDomainsBySett = arrearageDetailDomainsMap.get(key);

                    if (arrearageDetailDomainsBySett == null || arrearageDetailDomainsBySett.size() < 1) {
                        continue;
                    }

                    List<Long> mon =
                            arrearageDetailDomainsBySett.stream().map(ArrearageDetailDomain::getMon).map(Long::valueOf).distinct().collect(toList());

                    List<ArrearageDetailDomain> reduceArrearageDetailsBySett = reduceArrearageDetailMap.get(key);

                    //差集月份
                    List<Long> reduceMon =
                            reduceArrearageDetailsBySett.stream().map(ArrearageDetailDomain::getMon).map(Long::valueOf).distinct().collect(toList());


                    //用差集 和所选月份比较 若未选中有和所选月份相同的记录 则提示
                    List<Long> notSelectSameMons =
                            mon.stream().filter(item -> reduceMon.contains(item)).collect(toList());

                    if (notSelectSameMons != null && notSelectSameMons.size() > 0) {
                        return new HttpResult(HttpResult.ERROR,
                                "缴费失败，id为" + key + "的结算户相同月份的欠费必须一起交");
                    }
                    //未选中的最小月份
                    long minReduceMon =
                            reduceMon.stream().mapToLong(Long::valueOf).min().getAsLong();

                    long maxSelectSameMons =
                            mon.stream().mapToLong(Long::valueOf).max().getAsLong();

                    if (minReduceMon < maxSelectSameMons) {
                        return new HttpResult(HttpResult.ERROR,
                                "缴费失败，id为" + key + "的结算户必须从最小月份开始缴费");
                    }
                }

            }

        }

        //获取欠费流水号
        long size = 1;
        if (arrearageDetailDomains != null && arrearageDetailDomains.size() > 0) {
            size =
                    arrearageDetailDomains.stream().map(ArrearageDetailDomain::getSettlementId).distinct().count();
        }
        Map<String, Object> flowParam = new HashMap<>();
        flowParam.clear();
        flowParam.put("strategy", null);
        flowParam.put("size", size);

        //实收流水号
        String flowNoJson = restTemplate.postForObject(Global.getConfig("getChargeFlowNoBySize"), flowParam, String.class);
        // 收费流水号
        String flowNo = JSONObject.parseObject(flowNoJson, String.class);

        if (arrearageDetailDomains != null && arrearageDetailDomains.size() > 0) {
            //锁定缴费失败
            List<ArrearageDetailDomain> lockArrearageDetails =
                    arrearageDetailDomains.stream().filter(t -> t.getIsLock() != null).filter(t -> t.getIsLock() == 1).collect(Collectors.toList());

            if (lockArrearageDetails != null && lockArrearageDetails.size() > 0) {
                return new HttpResult(HttpResult.ERROR, "缴费失败，银行代扣用户正在处理，请勿缴费");
            }
        }
        // 缴费方式
        String fChargeMode = chargeNObject.getString("fChargeMode");
        // 结算户号
        String settleMentNo = chargeNObject.getString("settleMentNo");
        // 应收金额
        BigDecimal shouldMoney = new BigDecimal(chargeNObject.getString("shouldMoney"));
        // 实收金额
        BigDecimal factMoney = new BigDecimal(chargeNObject.getString("factMoney"));
        // 找零
        BigDecimal factChange = new BigDecimal(chargeNObject.getString("factChange"));

        // 实际缴纳的钱 缴费-找零
        BigDecimal spareMoeny = factMoney.subtract(factChange);

        if (spareMoeny.compareTo(BigDecimal.ZERO) == 0) {
            return new HttpResult(HttpResult.ERROR, "缴纳金额与找零金额相同，若预收请选择不取整");
        }

        // 操作人
        Long managerId = countDataNObject.getLong("managerId");

        if (shouldMoney.compareTo(BigDecimal.ZERO) < 0 && shouldMoney.compareTo(spareMoeny) != 0) {
            return new HttpResult(HttpResult.ERROR, "欠费为负数时,退费金额要等于欠费金额");
        }

        // 找零不能为负值
        if (balance.compareTo(BigDecimal.ZERO) > 0 && shouldMoney.compareTo(BigDecimal.ZERO) > 0) {
            return new HttpResult(HttpResult.ERROR, "用户账目存在问题，既有余额又欠费, 请冲预收！");
        }

        // 找零不能为负值
        if (factChange.compareTo(BigDecimal.ZERO) == -1) {
            return new HttpResult(HttpResult.ERROR, "用户找零不能为负值！");
        }

        // 不予许部分缴费 实际缴纳的钱+余额<应收
        if ((spareMoeny.subtract(shouldMoney)).compareTo(BigDecimal.ZERO) == -1 && shouldMoney.compareTo(BigDecimal.ZERO) == 1) {
            return new HttpResult(HttpResult.ERROR, "用户缴纳的金额小于欠费,不允许部分缴费");
        }

        // 预收标识 应收金额=0 并且 实际缴纳的钱>0
        boolean preFlag = (0 == (shouldMoney.compareTo(BigDecimal.ZERO)) && 1 == (spareMoeny.compareTo(BigDecimal.ZERO)));
        //退预收
        boolean preRefundFlag =
                (0 == (shouldMoney.compareTo(BigDecimal.ZERO)) && -1 == (spareMoeny.compareTo(BigDecimal.ZERO)));


        // 欠费id集合
        List<String> arrearageIds = new ArrayList<>();
        if (preFlag != true && arrearageDetailDomains != null) {
            // 遍历取出相关计量点
            arrearageIds =
                    arrearageDetailDomains.stream().map(ArrearageDetailDomain::getId).map(String::valueOf).distinct().collect(toList());
        }

        if (shouldMoney.compareTo(BigDecimal.ZERO) < 0) {
            return iChargeService.chargeRecall(arrearageIds, fChargeMode, settleMentNo, spareMoeny, managerId, flowNo);
        } else if (preFlag == true || preRefundFlag == true) {
            return iChargeService.preCharge(fChargeMode, settleMentNo, spareMoeny, managerId, flowNo);
        } else {
            return iChargeService.charge(arrearageIds, fChargeMode, settleMentNo, spareMoeny, managerId, flowNo);
        }
    }

    // 冲预收
    @RequestMapping("offCharge")
    @ResponseBody
    public HttpResult offCharge(@RequestBody String chargeJson) {

        JSONObject countDataNObject = JSONObject.parseObject(chargeJson);

        String countData = countDataNObject.getString("countData");

        JSONObject chargeNObject = JSONObject.parseObject(countData);

        // 欠费记录id
        List<ArrearageDetailDomain> arrearageDetailDomains = JSONObject
                .parseArray(chargeNObject.getString("arrearageDetails"), ArrearageDetailDomain.class);

        if (arrearageDetailDomains == null || arrearageDetailDomains.size() < 1) {
            return new HttpResult(HttpResult.ERROR, "无欠费记录无法冲预收");
        }

        // 结算户号
        String settleMentNo = chargeNObject.getString("settleMentNo");

        String name = chargeNObject.getString("name");
        String bankNo = chargeNObject.getString("bankNo");

        // 余额
        BigDecimal balance = new BigDecimal(
                null == countDataNObject.getString("lastBalance") ? "0" : countDataNObject.getString("lastBalance"));

        Long managerId = countDataNObject.getLong("managerId");

        if (balance.compareTo(BigDecimal.ZERO) <= 0) {
            return new HttpResult(HttpResult.ERROR, "用户不满足冲预收条件，余额必须大于0！");
        }
        //结算户信息
        Map<String, Object> params = new HashMap<>();
        params.put("settlementNo", settleMentNo);
        params.put("settlementName", name);
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

        List<ArrearageDomain> arrearageDomains = iArrearageService.findArrearageBySettleIds(String.join(",", settlementIdsString));
        // 冲预收
        List<ArrearageDomain> dealArrearageDomains = null;
        PulishEntity pulishEntity = new PulishEntity();
        pulishEntity.setArrearageDomains(arrearageDomains);
        pulishEntity.setfChargeMode(5);
        try {
            dealArrearageDomains =
                    iPublishService.offCharge(pulishEntity, managerId).getArrearageDomains();
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResult(HttpResult.SUCCESS, "冲预收失败,错误" + e.toString());
        }

        iArrearageService.updateList(dealArrearageDomains);

        return new HttpResult(HttpResult.SUCCESS, "冲预收成功,请重新查询");

    }

    // 收费明细查询 及报表
    @RequestMapping("findChargeInfoDetails")
    @ResponseBody
    public HttpResultPagination<ChargeInfoDetailEntity> findChargeInfoDetails(@RequestBody String chargeInfoJson) throws IOException {

        ChargeInfoEntity chargeInfoEntity = JSONObject.parseObject(chargeInfoJson, ChargeInfoEntity.class);

        List<ChargeInfoDetailEntity> chargeInfoDetailEntities = iChargeService.findChargeInfoDetails(chargeInfoEntity);

        List<Long> meterIds = chargeInfoDetailEntities.stream().map(ChargeInfoDetailEntity::getMeterId)
                .collect(Collectors.toList()).stream().distinct().collect(toList());

        if (meterIds == null || meterIds.size() < 1) {
            new HttpResultPagination<>();
        }

        HttpResult httpResult = restTemplate.postForObject(Global.getConfig("getMeterByMeterIdsWithoutStatus"), meterIds,
                HttpResult.class);

        List<MeterDomain> meterDomains = JSONObject.parseArray(JSONArray.toJSONString(httpResult.getResultData()),
                MeterDomain.class);

        Map<Long, MeterDomain> meterDomainMap = meterDomains.stream()
                .collect(Collectors.toMap(MeterDomain::getId, a -> a, (k1, k2) -> k1));

        chargeInfoDetailEntities.stream().filter(t -> meterDomainMap.get(t.getMeterId()) != null).forEach(t -> {
            t.setMeterNo(meterDomainMap.get(t.getMeterId()).getMeterNo());
            t.setMeterName(meterDomainMap.get(t.getMeterId()).getMeterName());
        });

        List<Long> settlementIds = chargeInfoDetailEntities.stream().filter(i -> i.getSettlementId() != null)
                .map(ChargeInfoDetailEntity::getSettlementId).collect(Collectors.toList());

        String settlementJson = restTemplate.postForObject(Global.getConfig("findSettlementByIds"),
                settlementIds, String.class);

        List<SettlementEntity> settementList = JSONUtil.readValue(settlementJson,
                new TypeReference<HttpResult<List<SettlementEntity>>>() {
                }).getResultData();

        Map<Long, SettlementEntity> settementMap = settementList.stream().collect(Collectors.toMap(SettlementEntity::getId, k -> k));

        //赋值结算户信息
        chargeInfoDetailEntities.stream().filter(t -> meterDomainMap.get(t.getSettlementId()) == null).forEach(t -> {
            t.setSettlementName(settementMap.get(t.getSettlementId()).getSettlementName());
            t.setSettlementNo(settementMap.get(t.getSettlementId()).getSettlementNo());
        });

        return new HttpResultPagination<ChargeInfoDetailEntity>(chargeInfoEntity, chargeInfoDetailEntities);
    }

    @RequestMapping("findChargeInfoDetailsByCustomer")
    @ResponseBody
    public JSONObject findChargeInfoDetailsByCustomer(@RequestBody String customerDomain) {
        CustomerDomain customer = GsonUtils.readValue(customerDomain, CustomerDomain.class);
        ChargeInfoDetailEntity chargeInfoDetailEntity = new ChargeInfoDetailEntity();
        chargeInfoDetailEntity.setSettlementId(customer.getId());
        List<ChargeInfoDetailEntity> chargeInfoDetailsByCustomer = iChargeService
                .findChargeInfoDetailsByCustomer(customer);
        JSONObject jsonObject = new JSONObject();
        BigDecimal sumFactMoney = BigDecimal.ZERO;
        BigDecimal sumFactPunish = BigDecimal.ZERO;
        BigDecimal sumFacrPre = BigDecimal.ZERO;
        BigDecimal sumFactTotal = BigDecimal.ZERO;

        if (chargeInfoDetailsByCustomer != null && chargeInfoDetailsByCustomer.size() > 0) {
            sumFactMoney =
                    chargeInfoDetailsByCustomer.stream().filter(t -> t.getFactMoney() != null)
                            .map(ChargeInfoDetailEntity::getFactMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
            sumFactPunish =
                    chargeInfoDetailsByCustomer.stream().filter(t -> t.getFactPunish() != null)
                            .map(ChargeInfoDetailEntity::getFactPunish).reduce(BigDecimal.ZERO, BigDecimal::add);
            sumFacrPre =
                    chargeInfoDetailsByCustomer.stream().filter(t -> t.getFactPre() != null)
                            .map(ChargeInfoDetailEntity::getFactPre).reduce(BigDecimal.ZERO, BigDecimal::add);
            sumFactTotal =
                    chargeInfoDetailsByCustomer.stream().filter(t -> t.getFactTotal() != null)
                            .map(ChargeInfoDetailEntity::getFactTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        jsonObject.put("data", new HttpResultPagination<>(customer, chargeInfoDetailsByCustomer));
        jsonObject.put("sumFactMoney", sumFactMoney);
        jsonObject.put("sumFactPunish", sumFactPunish);
        jsonObject.put("sumFactPre", sumFacrPre);
        jsonObject.put("sumFactTotal", sumFactTotal);

        return jsonObject;
    }

    @RequestMapping("findMaxIdBySettlementIds")
    @ResponseBody
    public List<ChargeDomain> findMaxIdBySettlementIds(@RequestBody String chargeInfoEntityJson) {
        ChargeInfoEntity chargeInfoEntity =
                JSONObject.parseObject(chargeInfoEntityJson,
                        ChargeInfoEntity.class);
        return iChargeService.findMaxIdBySettlementIds(chargeInfoEntity);
    }

    //按天或者支付方式分组 收费员月结
    @RequestMapping("findChargeInfoDetailsGroupByDay")
    @ResponseBody
    public List<ChargeInfoDetailEntity> findChargeInfoDetailsGroupByDay(@RequestBody String chargeInfoEntityJson) {
        ChargeInfoEntity chargeInfoEntity =
                JSONObject.parseObject(chargeInfoEntityJson,
                        ChargeInfoEntity.class);
        return iChargeService.findChargeInfoDetailsGroupByDay(chargeInfoEntity);
    }

    ;

    //跨区收费报表
    @RequestMapping("findCrossChargeInfoDetails")
    @ResponseBody
    public List<ChargeInfoDetailEntity> findCrossChargeInfoDetails(@RequestBody String chargeInfoEntityJson) {
        ChargeInfoEntity chargeInfoEntity =
                JSONObject.parseObject(chargeInfoEntityJson,
                        ChargeInfoEntity.class);
        return iChargeService.findCrossChargeInfoDetails(chargeInfoEntity);
    }

    ;
}
