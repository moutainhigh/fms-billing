package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.fms.billing.common.webapp.domain.PreChargeLogDomain;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;

@TransactionDAO
public class PreChargeLogDAO extends AbstractTransactionDAOSupport{
	public List<PreChargeLogDomain> findByWhere(PreChargeLogDomain preChargeLogDomain){
		return getPersistanceManager().find(getNamespace() + ".findByWhere", preChargeLogDomain);
	}
	public int insert(PreChargeLogDomain preChargeLogDomain) {
		return getPersistanceManager().insert(getNamespace() + ".insert", preChargeLogDomain);
	};
	public int update(PreChargeLogDomain preChargeLogDomain) {
		return getPersistanceManager().update(getNamespace() + ".update", preChargeLogDomain);
	}
}
