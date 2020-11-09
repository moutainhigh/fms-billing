package org.fms.billing.server.webapp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.fms.billing.common.webapp.domain.BackChargeDomain;
import org.fms.billing.common.webapp.domain.ChargeDomain;
import org.fms.billing.common.webapp.domain.beakInterface.CustomerDomain;
import org.fms.billing.common.webapp.entity.ChargeInfoDetailEntity;
import org.fms.billing.common.webapp.entity.ChargeInfoEntity;
import org.fms.billing.common.webapp.entity.MeterPageEntity;

import com.riozenc.titanTool.annotation.PaginationSupport;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;

@TransactionDAO
public class ChargeDAO extends AbstractTransactionDAOSupport{
	public List<ChargeDomain> findByWhere(ChargeDomain chargeDomain){
		return getPersistanceManager().find(getNamespace() + ".findByWhere", chargeDomain);
	}

	public ChargeDomain findByKey(ChargeDomain chargeDomain){
		return getPersistanceManager().load(getNamespace() + ".findByKey",chargeDomain);
	}
	public int insert(ChargeDomain chargeDomain) {
		return getPersistanceManager().insert(getNamespace() + ".insert", chargeDomain);
	};
	public int update(ChargeDomain chargeDomain) {
		return getPersistanceManager().update(getNamespace() + ".update", chargeDomain);
	}

	public int updateList(List<ChargeDomain> chargeDomains) {
		return getPersistanceManager(ExecutorType.BATCH).updateList(getNamespace() + ".update",
				chargeDomains);
	}

	public List<ChargeDomain> findByMeterIds(MeterPageEntity meterPageEntity){
		return getPersistanceManager().find(getNamespace() + ".findByMeterIds", meterPageEntity);
	}

	public List<BackChargeDomain> findBackChargeByIds(List<Long> ids){
		return getPersistanceManager().find(getNamespace() + ".findBackChargeByIds", ids);
	}

	public List<ChargeDomain> findChargeByIds(List<Long> ids){
		return getPersistanceManager().find(getNamespace() + ".findChargeByIds", ids);
	}

	public List<ChargeDomain> findChargeByFlowNos(List<String> flowNos){
		return getPersistanceManager().find(getNamespace() + ".findChargeByFlowNos", flowNos);
	}

	public int delete(ChargeDomain chargeDomain){
		return getPersistanceManager().delete(getNamespace() + ".delete",	chargeDomain);
	}

	public int insertList(List<ChargeDomain> chargeDomains) {
		return getPersistanceManager().insertList(getNamespace() + ".insert",
				chargeDomains);
	};

	public int insertListBulk(List<ChargeDomain> chargeDomains) {
		return getPersistanceManager(ExecutorType.BATCH).insertList(getNamespace() + ".insert",
				chargeDomains);
	};

	public List<ChargeDomain> findProcessArrearageByMeterIds(Map map) {
		return getPersistanceManager().find(getNamespace() + ".findProcessArrearageByMeterIds",map);
	};

	public List<ChargeDomain> findBackPulishByMeterIds(Map map) {
		return getPersistanceManager().find(getNamespace() + ".findBackPulishByMeterIds",map);
	};
	@PaginationSupport
	public List<ChargeInfoDetailEntity> findChargeInfoDetails(ChargeInfoEntity chargeInfoEntity) {
		return getPersistanceManager().find(getNamespace() + ".findChargeInfoDetails",chargeInfoEntity);
	};

	public List<ChargeDomain> findChargeByMeterMoneyIds(List<Long> meterMoneyIds){
		return getPersistanceManager().find(getNamespace() + ".findChargeByMeterMoneyIds", meterMoneyIds);
	}

	public List<ChargeInfoDetailEntity> findChargeInfoDetailsByCustomer(CustomerDomain customerDomain) {
		return getPersistanceManager().find(getNamespace()+".findChargeInfoDetailsByCustomer", customerDomain);
	}

	public List<ChargeDomain> findMaxIdBySettlementIds(ChargeInfoEntity chargeInfoEntity){
		return getPersistanceManager().find(getNamespace() + ".findMaxIdBySettlementIds", chargeInfoEntity);
	}

	public List<ChargeInfoDetailEntity> findChargeInfoDetailsGroupByDay(ChargeInfoEntity chargeInfoEntity) {
		return getPersistanceManager().find(getNamespace() + ".findChargeInfoDetailsGroupByDay",chargeInfoEntity);
	};

	public List<ChargeInfoDetailEntity> findCrossChargeInfoDetails(ChargeInfoEntity chargeInfoEntity) {
		return getPersistanceManager().find(getNamespace() + ".findCrossChargeInfoDetails",chargeInfoEntity);
	};


}
