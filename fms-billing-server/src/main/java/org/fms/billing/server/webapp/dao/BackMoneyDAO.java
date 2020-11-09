package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.fms.billing.common.webapp.domain.BackMoneyDomain;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;

@TransactionDAO
public class BackMoneyDAO extends AbstractTransactionDAOSupport{
	public List<BackMoneyDomain> findByWhere(BackMoneyDomain backMoneyDomain){
		return getPersistanceManager().find(getNamespace() + ".findByWhere", backMoneyDomain);
	}
	public int insert(BackMoneyDomain backMoneyDomain) {
		return getPersistanceManager().insert(getNamespace() + ".insert", backMoneyDomain);
	};
	public int update(BackMoneyDomain backMoneyDomain) {
		return getPersistanceManager().update(getNamespace() + ".update", backMoneyDomain);
	}
}
