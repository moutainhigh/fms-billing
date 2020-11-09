package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.fms.billing.common.webapp.domain.ArrearageBackDomain;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;

@TransactionDAO
public class ArrearageBackDAO extends AbstractTransactionDAOSupport {
	public List<ArrearageBackDomain> findByWhere(ArrearageBackDomain arrearageBackDomain) {
		return getPersistanceManager().find(getNamespace() + ".findByWhere", arrearageBackDomain);
	}

	public int insert(ArrearageBackDomain arrearageBackDomain) {
		return getPersistanceManager().insert(getNamespace() + ".insert", arrearageBackDomain);
	}

	;

	public int update(ArrearageBackDomain arrearageBackDomain) {
		return getPersistanceManager().update(getNamespace() + ".update", arrearageBackDomain);
	}


}
