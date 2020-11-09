package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.fms.billing.common.webapp.domain.BackChargeDomain;
import org.fms.billing.common.webapp.entity.MeterPageEntity;

import com.riozenc.titanTool.annotation.PaginationSupport;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;

@TransactionDAO
public class  BackChargeDAO extends AbstractTransactionDAOSupport{
	public List<BackChargeDomain> findByWhere(BackChargeDomain backChargeDomain){
		return getPersistanceManager().find(getNamespace() + ".findByWhere", backChargeDomain);
	}
	public int insert(BackChargeDomain backChargeDomain) {
		return getPersistanceManager().insert(getNamespace() + ".insert", backChargeDomain);
	};
	public int update(BackChargeDomain backChargeDomain) {
		return getPersistanceManager().update(getNamespace() + ".update", backChargeDomain);
	}
	public int delete(BackChargeDomain backChargeDomain) {
		return getPersistanceManager().update(getNamespace() + ".delete",backChargeDomain);
	}
	@PaginationSupport
	public List<BackChargeDomain> getBackChargeByMeterIds(MeterPageEntity meterPageEntity){
		return getPersistanceManager().find(getNamespace() + ".getBackChargeByMeterIds", meterPageEntity);
	}
	@PaginationSupport
	public List<BackChargeDomain> getBackCharge(MeterPageEntity meterPageEntity){
		return getPersistanceManager().find(getNamespace() + ".getBackCharge", meterPageEntity);
	}

	public List<BackChargeDomain> findByChargeInfoIds(List<Long> ids){
		return getPersistanceManager().find(getNamespace() + ".findByChargeInfoIds", ids);
	}

	public List<BackChargeDomain> findBackChargeInfoByIds(List<String> ids){
		return getPersistanceManager().find(getNamespace() + ".findBackChargeInfoByIds", ids);
	}
	@PaginationSupport
	public List<BackChargeDomain> getFinishBackCharge(MeterPageEntity meterPageEntity){
		return getPersistanceManager().find(getNamespace() + ".getFinishBackCharge", meterPageEntity);
	}

}
