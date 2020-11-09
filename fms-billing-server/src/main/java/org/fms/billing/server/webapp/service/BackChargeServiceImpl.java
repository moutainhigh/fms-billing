package org.fms.billing.server.webapp.service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.fms.billing.common.webapp.domain.ArrearageDomain;
import org.fms.billing.common.webapp.domain.BackChargeDomain;
import org.fms.billing.common.webapp.domain.ChargeDomain;
import org.fms.billing.common.webapp.domain.NoteInfoDomain;
import org.fms.billing.common.webapp.domain.PreChargeDomain;
import org.fms.billing.common.webapp.entity.MeterPageEntity;
import org.fms.billing.common.webapp.service.IBackChargeService;
import org.fms.billing.server.webapp.dao.ArrearageDAO;
import org.fms.billing.server.webapp.dao.BackChargeDAO;
import org.fms.billing.server.webapp.dao.ChargeDAO;
import org.fms.billing.server.webapp.dao.NoteInfoDAO;
import org.fms.billing.server.webapp.dao.PreChargeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONObject;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;
import com.riozenc.titanTool.spring.web.client.TitanTemplate;
import com.riozenc.titanTool.spring.web.http.HttpResult;

@TransactionService
public class BackChargeServiceImpl implements IBackChargeService {
    @TransactionDAO
    private BackChargeDAO backChargeDAO;

    @TransactionDAO
    private ChargeDAO chargeDAO;

    @TransactionDAO
    private PreChargeDAO preChargeDAO;

    @TransactionDAO
    private ArrearageDAO arrearageDAO;

    @TransactionDAO
    private NoteInfoDAO noteInfoDAO;

    @Autowired
    private TitanTemplate titanTemplate;

    @Override
    public List<BackChargeDomain> findByWhere(BackChargeDomain backChargeDomain) {
        return backChargeDAO.findByWhere(backChargeDomain);
    }

    @Override
    public int insert(BackChargeDomain backChargeDomain) {
        return backChargeDAO.insert(backChargeDomain);
    }

    @Override
    public int update(BackChargeDomain backChargeDomain) {
        return backChargeDAO.update(backChargeDomain);
    }

    @Override
    public int delete(BackChargeDomain backChargeDomain) {
        return backChargeDAO.delete(backChargeDomain);
    }

    @Override
    public List<BackChargeDomain> getBackChargeByMeterIds(MeterPageEntity meterPageEntity) {
        return backChargeDAO.getBackChargeByMeterIds(meterPageEntity);
    }

    @Override
    public List<BackChargeDomain> getBackCharge(MeterPageEntity meterPageEntity) {
        return backChargeDAO.getBackCharge(meterPageEntity);
    }

    @Override
    public List<BackChargeDomain> getFinishBackCharge(MeterPageEntity meterPageEntity) {
        return backChargeDAO.getFinishBackCharge(meterPageEntity);
    }

    @Override
    public List<BackChargeDomain> findByChargeInfoIds(List<Long> ids) {
        return backChargeDAO.findByChargeInfoIds(ids);
    }

    @Override
    public List<BackChargeDomain> findBackChargeInfoByIds(List<String> ids) {
        return backChargeDAO.findBackChargeInfoByIds(ids);
    }

    // 抹账
    public HttpResult backCharge(@RequestBody String json) throws Exception {
        // 勾选的id
        JSONObject jsonObject = JSONObject.parseObject(json);
        List<Long> ids = JSONObject.parseArray(jsonObject.getString("ids"),
                Long.class);

        List<BackChargeDomain> backChargeDomains = backChargeDAO.findByChargeInfoIds(ids);

        if (backChargeDomains == null || backChargeDomains.size() < 1) {
            return new HttpResult(HttpResult.ERROR, "请先申请再抹账！", null);
        }

        backChargeDomains.forEach(t -> {
            //t.setErasePerson(jsonObject.getLong("managerId"));
            t.setDealDate(new Date());
            backChargeDAO.update(t);
        });

        // 过滤不同意或未审批的记录
        int num =
                backChargeDomains.stream().filter(t -> t.getEraseOption() == null || !Objects.equals(t.getEraseOption(), 1))
                        .collect(Collectors.toList()).size();
        if (num > 0) {
            return new HttpResult(HttpResult.ERROR, "所选记录存在未发起审批或者未审批通过", null);
        }
        //按收费先后降序
        List<ChargeDomain> chargeDomains =
                chargeDAO.findChargeByIds(ids).stream()
                        .sorted(Comparator.comparing(ChargeDomain::getId).reversed()).collect(Collectors.toList());

        //判断一次收费必须一起抹账 不能抹一部分
        List<String> flowNos =
                chargeDomains.stream().map(ChargeDomain::getFlowNo).distinct().collect(Collectors.toList());

        int sameFlowNoCharges = chargeDAO.findChargeByFlowNos(flowNos).size();

        /*暂定
        if(sameFlowNoCharges!=chargeDomains.size()){
            return new HttpResult(HttpResult.ERROR, "同一次收费必须一起抹账");
        }*/


        for (ChargeDomain t : chargeDomains) {
            // 余额处理
            PreChargeDomain preChargeDomain = new PreChargeDomain();
            preChargeDomain.setSettlementId(t.getSettlementId());
            List<PreChargeDomain> preChargeDomains = preChargeDAO.findByWhere(preChargeDomain);
            if (preChargeDomains.size() != 1) {
                return new HttpResult(HttpResult.ERROR, "异常:余额表存在多个结算户Id" + "或者无结算户记录", null);
            }

            BigDecimal balance = preChargeDomains.get(0).getBalance();
            BigDecimal delBalance = balance.subtract(t.getFactPre());
            // 余额小于等于0 则为0
            if (delBalance.compareTo(BigDecimal.ZERO) != 1) {
                preChargeDomains.get(0).setBalance(BigDecimal.ZERO);
            } else {
                preChargeDomains.get(0).setBalance(delBalance);
            }
            preChargeDAO.update(preChargeDomains.get(0));

            // 处理欠费
            ArrearageDomain arrearageDomain = new ArrearageDomain();
            arrearageDomain.setArrearageNo(t.getArrearageNo());
            //arrearageDomain.setIsSettle(1);
            List<ArrearageDomain> arrearageDomains = arrearageDAO.findByWhere(arrearageDomain);

            if (t.getYsTypeCode() != null && t.getYsTypeCode() != 2) {
                if (arrearageDomains.size() > 0) {
                    arrearageDomains.forEach(a -> {
                        a.setIsSettle(0);
                        a.setOweMoney(a.getOweMoney().add(t.getFactMoney()));
                        a.setPunishMoney(a.getPunishMoney().add(t.getFactPunish()));
                        arrearageDAO.update(a);
                    });
                }
            }

            // 处理欠费
            NoteInfoDomain noteInfoDomain = new NoteInfoDomain();
            noteInfoDomain.setChargeInfoId(t.getId());
            List<NoteInfoDomain> noteInfoDomains = noteInfoDAO.findByWhere(noteInfoDomain);
            //删除发票记录
            noteInfoDomains.forEach(n -> noteInfoDAO.delete(n));

            // 删除收费记录
            chargeDAO.delete(t);

        }

        //删除发票记录
        List<Long> chargeInfoIds =
                chargeDomains.stream().map(ChargeDomain::getId).collect(Collectors.toList());
        NoteInfoDomain noteInfoDomain = new NoteInfoDomain();
        noteInfoDomain.setChargeInfoIds(chargeInfoIds);
        noteInfoDAO.deleteByChargeIds(noteInfoDomain);

        return new HttpResult(HttpResult.SUCCESS, "抹账成功", null);
    }
}
