package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.fms.billing.common.webapp.domain.PreChargeDomain;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;

@TransactionDAO
public class PreChargeDAO extends AbstractTransactionDAOSupport {
	public List<PreChargeDomain> findByWhere(PreChargeDomain preChargeDomain) {
		return getPersistanceManager().find(getNamespace() + ".findByWhere", preChargeDomain);
	}

	public int insert(PreChargeDomain preChargeDomain) {
		return getPersistanceManager().insert(getNamespace() + ".insert", preChargeDomain);
	};

	public int update(PreChargeDomain preChargeDomain) {
		return getPersistanceManager().update(getNamespace() + ".update", preChargeDomain);
	}

	public List<PreChargeDomain> findPreChargeBySettleIds(List<Long> settlementIds) {
		return getPersistanceManager().find(getNamespace() + ".findPreChargeBySettleIds", settlementIds);
	}

	public List<PreChargeDomain> findAllPreChargeBySettleIds(List<Long> settlementIds) {
		return getPersistanceManager().find(getNamespace() + ".findAllPreChargeBySettleIds", settlementIds);
	}

	public int insertList(List<PreChargeDomain> preChargeDomain) {
		return getPersistanceManager(ExecutorType.BATCH).insertList(getNamespace() + ".insert",preChargeDomain);
	};

	public int updateList(List<PreChargeDomain> preChargeDomain) {
		return getPersistanceManager(ExecutorType.BATCH).updateList(getNamespace() +
				".update",preChargeDomain);
	};

}
