package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.PriceTypeDomain;

public interface IPriceTypeService {
	public List<PriceTypeDomain> findByWhere(PriceTypeDomain priceTypeDomain);

	public List<PriceTypeDomain> priceDrop(PriceTypeDomain priceTypeDomain);

	public int insert(PriceTypeDomain priceTypeDomain);

	public int update(PriceTypeDomain priceTypeDomain);

	public List<PriceTypeDomain> priceAllDrop(PriceTypeDomain priceTypeDomain);

	public PriceTypeDomain findByKey(PriceTypeDomain priceTypeDomain);

	public List<PriceTypeDomain> findByListKey(List<Long> ids);
}
