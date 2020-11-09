package org.fms.billing.server.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.PriceStandardDomain;
import org.fms.billing.common.webapp.service.IPriceStandardService;
import org.fms.billing.server.webapp.dao.PriceStandardDAO;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;

@TransactionService
public class PriceStandardServiceImpl implements IPriceStandardService{
@TransactionDAO
private PriceStandardDAO priceStandardDAO;

@Override
public List<PriceStandardDomain> findByWhere(PriceStandardDomain priceStandardDomain) {
	return priceStandardDAO.findByWhere(priceStandardDomain);
}

@Override
public int insert(PriceStandardDomain priceStandardDomain) {
	return priceStandardDAO.insert(priceStandardDomain);
}

@Override
public int update(PriceStandardDomain priceStandardDomain) {
	return priceStandardDAO.update(priceStandardDomain);
}
}
