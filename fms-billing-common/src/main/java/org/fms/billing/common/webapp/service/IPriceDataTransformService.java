package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.PriceDataTransformDomain;

public interface IPriceDataTransformService {
	public List<PriceDataTransformDomain> findPriceDataTransform(PriceDataTransformDomain priceDataTransformDomain);
}
