package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.PriceLadderRelaDomain;

import com.riozenc.titanTool.spring.web.http.HttpResult;

public interface IPriceLadderRelaService {
	public List<PriceLadderRelaDomain> findByWhere(PriceLadderRelaDomain priceLadderRelaDomain);
	public int insert(PriceLadderRelaDomain priceLadderRelaDomain);
	public int update(PriceLadderRelaDomain priceLadderRelaDomain);
	public HttpResult synCurrentPrice();
	public List<PriceLadderRelaDomain> findMongoPriceLadderRela(String mon);

}
