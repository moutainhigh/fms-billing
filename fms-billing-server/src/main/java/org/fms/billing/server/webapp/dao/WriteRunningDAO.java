package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.fms.billing.common.webapp.domain.WriteRunningDomain;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;
@TransactionDAO
public class WriteRunningDAO extends AbstractTransactionDAOSupport{
	public List<WriteRunningDomain> findByWhere(WriteRunningDomain writeRunningDomain){
		return getPersistanceManager().find(getNamespace() + ".findByWhere", writeRunningDomain);
	}
	public int insert(WriteRunningDomain writeRunningDomain) {
		return getPersistanceManager().insert(getNamespace() + ".insert", writeRunningDomain);
	};
	public int update(WriteRunningDomain writeRunningDomain) {
		return getPersistanceManager().update(getNamespace() + ".update", writeRunningDomain);
	}
}
