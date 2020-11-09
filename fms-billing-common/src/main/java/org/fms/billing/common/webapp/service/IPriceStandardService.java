package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.PriceStandardDomain;

public interface IPriceStandardService {
	public List<PriceStandardDomain> findByWhere(PriceStandardDomain priceStandardDomain);
	public int insert(PriceStandardDomain priceStandardDomain);
	public int update(PriceStandardDomain priceStandardDomain);
}
