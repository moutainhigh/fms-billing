package org.fms.billing.server.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.PriceItemDomain;
import org.fms.billing.common.webapp.service.IPriceItemService;
import org.fms.billing.server.webapp.dao.PriceItemDAO;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;

@TransactionService
public class PriceItemServiceImpl implements IPriceItemService {
	@TransactionDAO
	private PriceItemDAO priceItemDAO;

	@Override
	public List<PriceItemDomain> findByWhere(PriceItemDomain priceItemDomain) {
		return priceItemDAO.findByWhere(priceItemDomain);
	}

	@Override
	public List<PriceItemDomain> priceItemDorp(PriceItemDomain priceItemDomain) {
		return priceItemDAO.priceItemDorp(priceItemDomain);
	}

	@Override
	public int insert(PriceItemDomain priceItemDomain) {
	return priceItemDAO.insert(priceItemDomain);
	}

	@Override
	public int update(PriceItemDomain priceItemDomain) {
		return priceItemDAO.update(priceItemDomain);
	}
}
