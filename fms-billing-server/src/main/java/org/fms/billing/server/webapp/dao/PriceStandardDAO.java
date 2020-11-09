package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.fms.billing.common.webapp.domain.PriceStandardDomain;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;

@TransactionDAO
public class PriceStandardDAO extends AbstractTransactionDAOSupport{
	public List<PriceStandardDomain> findByWhere(PriceStandardDomain priceStandardDomain){
		return getPersistanceManager().find(getNamespace() + ".findByWhere", priceStandardDomain);
	}
	public int insert(PriceStandardDomain priceStandardDomain) {
		return getPersistanceManager().insert(getNamespace() + ".insert", priceStandardDomain);
	};
	public int update(PriceStandardDomain priceStandardDomain) {
		return getPersistanceManager().update(getNamespace() + ".update", priceStandardDomain);
	}

}
