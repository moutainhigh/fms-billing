package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.fms.billing.common.webapp.domain.CosStandardConfigDomain;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;

@TransactionDAO
public class CosStandardConfigDAO extends AbstractTransactionDAOSupport{
	public List<CosStandardConfigDomain> findByWhere(CosStandardConfigDomain coStandardConfigDomain){
		return getPersistanceManager().find(getNamespace() + ".findByWhere", coStandardConfigDomain);
	}
	public int insert(CosStandardConfigDomain coStandardConfigDomain) {
		return getPersistanceManager().insert(getNamespace() + ".insert", coStandardConfigDomain);
	};
	public int update(CosStandardConfigDomain coStandardConfigDomain) {
		return getPersistanceManager().update(getNamespace() + ".update", coStandardConfigDomain);
	}
}
