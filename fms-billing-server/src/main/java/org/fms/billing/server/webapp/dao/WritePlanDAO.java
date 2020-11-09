package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.fms.billing.common.webapp.domain.WritePlanDomain;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;

@TransactionDAO
public class WritePlanDAO extends AbstractTransactionDAOSupport{
	public List<WritePlanDomain> findByWhere(WritePlanDomain writePlanDomain){
		return getPersistanceManager().find(getNamespace() + ".findByWhere", writePlanDomain);
	}
	public int insert(WritePlanDomain writePlanDomain) {
		return getPersistanceManager().insert(getNamespace() + ".insert", writePlanDomain);
	};
	public int update(WritePlanDomain writePlanDomain) {
		return getPersistanceManager().update(getNamespace() + ".update", writePlanDomain);
	}
}
