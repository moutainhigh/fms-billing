package org.fms.billing.server.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.PriceDataTransformDomain;
import org.fms.billing.common.webapp.service.IPriceDataTransformService;
import org.fms.billing.server.webapp.dao.PriceDataTransformDAO;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;

@TransactionService
public class PriceDataTransformImpl implements IPriceDataTransformService {
    @TransactionDAO
    private PriceDataTransformDAO priceDataTransformDAO;

    @Override
    public List<PriceDataTransformDomain> findPriceDataTransform(PriceDataTransformDomain priceDataTransformDomain) {
        return priceDataTransformDAO.findPriceDataTransform(priceDataTransformDomain);
    }
}
