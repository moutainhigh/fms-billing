package org.fms.billing.server.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.PriceTypeDomain;
import org.fms.billing.common.webapp.service.IPriceTypeService;
import org.fms.billing.server.webapp.dao.PriceTypeDAO;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;

@TransactionService
public class PriceTypeServiceImpl implements IPriceTypeService {
    @TransactionDAO
    private PriceTypeDAO priceTypeDAO;

    @Override
    public List<PriceTypeDomain> findByWhere(PriceTypeDomain priceTypeDomain) {
        return priceTypeDAO.findByWhere(priceTypeDomain);
    }

    @Override
    public List<PriceTypeDomain> priceDrop(PriceTypeDomain priceTypeDomain) {
        return priceTypeDAO.priceDrop(priceTypeDomain);
    }

    @Override
    public List<PriceTypeDomain> priceAllDrop(PriceTypeDomain priceTypeDomain) {
        return priceTypeDAO.priceAllDrop(priceTypeDomain);
    }

    @Override
    public int insert(PriceTypeDomain priceTypeDomain) {
        return priceTypeDAO.insert(priceTypeDomain);
    }

    @Override
    public int update(PriceTypeDomain priceTypeDomain) {
        return priceTypeDAO.update(priceTypeDomain);
    }
    @Override
    public PriceTypeDomain findByKey(PriceTypeDomain priceTypeDomain){
        return priceTypeDAO.findByKey(priceTypeDomain);
    }
    @Override
    public List<PriceTypeDomain> findByListKey(List<Long> ids) {
        return priceTypeDAO.findByListKey(ids);
    }
}
