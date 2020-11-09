package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.fms.billing.common.webapp.domain.PriceTypeDomain;

import com.riozenc.titanTool.annotation.PaginationSupport;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;

@TransactionDAO
public class PriceTypeDAO extends AbstractTransactionDAOSupport{

	public List<PriceTypeDomain> priceDrop(PriceTypeDomain priceTypeDomain){
		return getPersistanceManager().find(getNamespace() + ".priceDrop",priceTypeDomain);
	}

	@PaginationSupport
	public List<PriceTypeDomain> findByWhere(PriceTypeDomain priceTypeDomain){
		return getPersistanceManager().find(getNamespace() + ".findByWhere", priceTypeDomain);
	}

	public int insert(PriceTypeDomain priceTypeDomain) {
		return getPersistanceManager().insert(getNamespace() + ".insert", priceTypeDomain);
	};
	public int update(PriceTypeDomain priceTypeDomain) {
		return getPersistanceManager().update(getNamespace() + ".update", priceTypeDomain);
	}

	public List<PriceTypeDomain> priceAllDrop(PriceTypeDomain priceTypeDomain){
		return getPersistanceManager().find(getNamespace() + ".priceAllDrop",priceTypeDomain);
	}

	public PriceTypeDomain findByKey(PriceTypeDomain priceTypeDomain) {
		return getPersistanceManager().load(getNamespace() + ".findByKey",priceTypeDomain);
	}

	public List<PriceTypeDomain> findByListKey(List<Long> ids) {
		return getPersistanceManager().find(getNamespace() + ".findByListKey",ids);
	}
}
