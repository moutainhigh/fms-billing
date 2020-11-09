package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.fms.billing.common.webapp.domain.PriceItemDomain;

import com.riozenc.titanTool.annotation.PaginationSupport;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;

@TransactionDAO
public class PriceItemDAO extends AbstractTransactionDAOSupport{
	@PaginationSupport
	public List<PriceItemDomain> findByWhere(PriceItemDomain priceItemDomain){
		return getPersistanceManager().find(getNamespace() + ".findByWhere", priceItemDomain);
	}
	public int insert(PriceItemDomain priceItemDomain) {
		return getPersistanceManager().insert(getNamespace() + ".insert", priceItemDomain);
	};
	public int update(PriceItemDomain priceItemDomain) {
		return getPersistanceManager().update(getNamespace() + ".update", priceItemDomain);
	}

	public List<PriceItemDomain> priceItemDorp(PriceItemDomain priceItemDomain){
		return getPersistanceManager().find(getNamespace() + ".priceItemDorp", priceItemDomain);
	}

}
