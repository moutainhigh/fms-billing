package org.fms.billing.server.webapp.action;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.fms.billing.common.util.MonUtils;
import org.fms.billing.common.webapp.domain.ArrearageDomain;
import org.fms.billing.common.webapp.domain.beakInterface.BankInterfaceDomain;
import org.fms.billing.common.webapp.domain.beakInterface.BatchChargeDomain;
import org.fms.billing.common.webapp.domain.beakInterface.SettlementDomain;
import org.fms.billing.common.webapp.service.IArrearageService;
import org.fms.billing.common.webapp.service.IChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.riozenc.titanTool.properties.Global;
import com.riozenc.titanTool.spring.web.client.TitanTemplate;
import com.riozenc.titanTool.spring.web.http.HttpResult;

@Controller
@RequestMapping("bankInterfaceController")
public class BankInterfaceController {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("arrearageServiceImpl")
    private IArrearageService arrearageService;

    @Autowired
    @Qualifier("chargeServiceImpl")
    private IChargeService iChargeService;

    @Autowired
    private TitanTemplate titanTemplate;

    @RequestMapping("getBankArrearage")
    @ResponseBody
    public HttpResult getBankArrearage(@RequestBody(required = false) String bankInterfaceJson) {
        List<BankInterfaceDomain> bankInterfaceDomains = new ArrayList<>();
        try {
            //从cim获取银行代扣的计算户信息
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("chargeModeType",3);
            String settlementJson = restTemplate.postForObject
                    (Global.getConfig("findBankSettlement"), jsonObject, String.class);

            //将结算户信息转换成集合
            List<SettlementDomain> settlementDomainList =
                    JSONObject.parseArray(settlementJson, SettlementDomain.class);

            if (settlementDomainList == null || settlementDomainList.size() < 1) {
                return new HttpResult<>(HttpResult.ERROR, "无银行代扣结算户欠费信息");
            }

            //将结算户集合->map 方便后面取数据
            Map<Long, List<SettlementDomain>> settlementMap =
                    settlementDomainList.stream().collect(Collectors.groupingBy(m -> m.getId()));

            // 取出相关的欠费计算户id
            List<Long> settlementIds =
                    settlementDomainList.stream().map(t -> t.getId()).distinct().collect(toList());

            // 处理超过1000个id
            List<ArrearageDomain> arrearageDomains = new ArrayList<>();
            int len = settlementIds.size();
            for (int m = 0; m < len / 1999 + 1; m++) {// 遍历次数

                List<Long> tl = settlementIds.subList(m * 1999,
                        (m + 1) * 1999 > len ? len : (m + 1) * 1999);

                String settleIdString =
                        tl.stream().map(String::valueOf).collect(Collectors.joining(","));

                arrearageDomains.addAll(arrearageService.findArrearageBySettleIds(settleIdString));

            }

            if (arrearageDomains == null || arrearageDomains.size() < 1) {
                return new HttpResult<>(HttpResult.ERROR, "无此类结算户的欠费数据");
            }
            //当前月份 只能发送比当前月份少的欠费到银行接口
            Integer currMon = Integer.valueOf(MonUtils.getMon());

            //结算户号分组 《
            Map<Long, List<ArrearageDomain>> longArrearageMap =
                    arrearageDomains.stream().filter(t -> t.getMon()<currMon).filter(t -> t != null)
                            .collect(Collectors.groupingBy(m -> m.getSettlementId()));


            List<Long> ids =
                    arrearageDomains.stream().filter(t -> t.getMon()<currMon).filter(t -> t != null)
                            .map(ArrearageDomain::getId).distinct().collect(toList());

            //欠费锁定
            arrearageService.updateLockByIds(ids);

            for (Long t : longArrearageMap.keySet()) {

                //取出结算户的算有欠费记录
                List<ArrearageDomain> settleArrearages =
                        longArrearageMap.get(t);


                if (settleArrearages == null || settleArrearages.size() < 1) {
                    continue;
                }

                //按月份和算费次数分组
                Map<String, List<ArrearageDomain>> settleArrearageMap =
                        settleArrearages.stream().collect(Collectors.groupingBy(m -> m.getMon() + "_" + m.getSn()));

                for (String monSn : settleArrearageMap.keySet()) {

                    if (settleArrearageMap.get(monSn).get(0) != null) {

                        BankInterfaceDomain bankInterfaceDomain = new BankInterfaceDomain();

                        List<Long> arrearageIds =
                                settleArrearageMap.get(monSn).stream().map(ArrearageDomain::getId).collect(toList());

                        BigDecimal oweMoney =
                                settleArrearageMap.get(monSn).stream().map(ArrearageDomain::getOweMoney).reduce(BigDecimal::add).get();

                        BigDecimal punishMoney =
                                settleArrearageMap.get(monSn).stream().map(ArrearageDomain::getPunishMoney).reduce(BigDecimal::add).get();

                        bankInterfaceDomain.setCusCode(t.toString());
                        bankInterfaceDomain.setPayFee(oweMoney.doubleValue());
                        bankInterfaceDomain.setPunishMoney(punishMoney.doubleValue());
                        bankInterfaceDomain.setFeeDate(settleArrearageMap.get(monSn).get(0).getMon());
                        bankInterfaceDomain.setMonSn(settleArrearageMap.get(monSn).get(0).getSn());
                        bankInterfaceDomain.setBankCode(settlementMap.get(t).get(0).getConnectBank());
                        bankInterfaceDomain.setCusAccount(settlementMap.get(t).get(0).getBankNo());
                        bankInterfaceDomain.setCusName(settlementMap.get(t).get(0).getAccountName());
                        bankInterfaceDomain.setIds(arrearageIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
                        bankInterfaceDomains.add(bankInterfaceDomain);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResult<>(HttpResult.ERROR, "失败", e.toString());
        }
        return new HttpResult<>(HttpResult.SUCCESS, "成功", bankInterfaceDomains);
    }

    /**
     * 银行代扣
     *
     * @param settlementIds
     * @return
     * @throws Exception 
     */
    @RequestMapping("bankCharge")
    @ResponseBody
    public HttpResult bankCharge(@RequestBody(required = false) String settlementIds) throws Exception {

        List<BatchChargeDomain> bankChargeDomains =
                JSONObject.parseArray(settlementIds, BatchChargeDomain.class);

        if (bankChargeDomains == null || bankChargeDomains.size() < 1) {
            return new HttpResult(HttpResult.ERROR, "没有要银行代扣的结算户");
        }

        List<String> arrearageChargeIds =
                bankChargeDomains.stream().filter(t -> t.getFlag() == 1).map(BatchChargeDomain::getIds).collect(toList());

        if (arrearageChargeIds != null || arrearageChargeIds.size() > 0) {


            List<Long> arrearageChargeList = new ArrayList<>();

            arrearageChargeIds.forEach(t -> {
                arrearageChargeList.addAll(Arrays.asList(t.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList()));
            });

            iChargeService.batchCharge(arrearageChargeList, "3",
                    bankChargeDomains.get(0).getOperator());
        }

        //解除未缴费锁定标志
        List<String> arrearageNoChargeIds =
                bankChargeDomains.stream().filter(t -> t.getFlag() != 1).map(BatchChargeDomain::getIds).collect(toList());

        if (arrearageNoChargeIds != null && arrearageNoChargeIds.size() > 0) {

            List<Long> arrearageNoChargeList = new ArrayList<>();

            arrearageNoChargeIds.forEach(t -> {
                arrearageNoChargeList.addAll(Arrays.asList(t.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList()));
            });

            arrearageService.removeLockByIds(arrearageNoChargeList);
        }

        return new HttpResult(HttpResult.SUCCESS, "银行代扣成功");
    }
}
