package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.PriceItemDomain;

public interface IPriceItemService {
	public List<PriceItemDomain> findByWhere(PriceItemDomain priceItemDomain);
	public int insert(PriceItemDomain priceItemDomain);
	public int update(PriceItemDomain priceItemDomain);
	public List<PriceItemDomain> priceItemDorp(PriceItemDomain priceItemDomain);
}
