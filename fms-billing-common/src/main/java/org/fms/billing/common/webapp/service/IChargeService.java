package org.fms.billing.common.webapp.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.fms.billing.common.webapp.domain.BackChargeDomain;
import org.fms.billing.common.webapp.domain.ChargeDomain;
import org.fms.billing.common.webapp.domain.beakInterface.CustomerDomain;
import org.fms.billing.common.webapp.entity.ChargeInfoDetailEntity;
import org.fms.billing.common.webapp.entity.ChargeInfoEntity;
import org.fms.billing.common.webapp.entity.MeterPageEntity;

import com.riozenc.titanTool.spring.web.http.HttpResult;

public interface IChargeService {
    public List<ChargeDomain> findByWhere(ChargeDomain chargeDomain);

    public int insert(ChargeDomain chargeDomain);

    public ChargeDomain findBykey(ChargeDomain chargeDomain);

    public int update(ChargeDomain chargeDomain);

    public int updateList(List<ChargeDomain> chargeDomains);

    public HttpResult batchCharge(List<Long> arrearageIds, String fChargeMode,
                                 Long managerId) throws Exception;

    public HttpResult charge(List<String> arrearageIds, String fChargeMode,
                             String settleMentNo,
                             BigDecimal preMoney, Long managerId,
                             String flowNo) throws Exception;
    public HttpResult preCharge(String fChargeMode,
                             String settleMentNo,
                             BigDecimal preMoney, Long managerId,
                             String flowNo) throws Exception;

    public HttpResult chargeRecall(List<String> arrearageIds,
                                   String fChargeMode,
                             String settleMentNo,
                             BigDecimal preMoney, Long managerId,
                             String flowNo) throws Exception;

    public List<ChargeDomain> findByMeterIds(MeterPageEntity meterPageEntity);

    public List<BackChargeDomain> findBackChargeByIds(List<Long> ids);

    public List<ChargeDomain> findChargeByIds(List<Long> ids);

    public int delete(ChargeDomain chargeDomain);

    public int insertList(List<ChargeDomain> chargeDomains);
    public int insertListBulk(List<ChargeDomain> chargeDomains);
    public List<ChargeDomain> findChargeByFlowNos(List<String> flowNos);

    public List<ChargeDomain> findBackPulishByMeterIds(Map map) ;
    public List<ChargeInfoDetailEntity> findChargeInfoDetails(ChargeInfoEntity chargeInfoEntity);

    public List<ChargeDomain> findChargeByMeterMoneyIds(List<Long> meterMoneyIds);

    public List<ChargeInfoDetailEntity> findChargeInfoDetailsByCustomer(CustomerDomain customerDomain);

    public List<ChargeDomain> findMaxIdBySettlementIds(ChargeInfoEntity chargeInfoEntity);

    public List<ChargeInfoDetailEntity> findChargeInfoDetailsGroupByDay(ChargeInfoEntity chargeInfoEntity);

    public List<ChargeInfoDetailEntity> findCrossChargeInfoDetails(ChargeInfoEntity chargeInfoEntity);

}
