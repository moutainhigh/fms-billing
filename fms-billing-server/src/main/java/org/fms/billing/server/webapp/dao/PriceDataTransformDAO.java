package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.fms.billing.common.webapp.domain.PriceDataTransformDomain;

import com.riozenc.titanTool.annotation.PaginationSupport;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;

@TransactionDAO
public class PriceDataTransformDAO extends AbstractTransactionDAOSupport {
    @PaginationSupport
    public List<PriceDataTransformDomain> findPriceDataTransform(PriceDataTransformDomain priceDataTransformDomain) {
        return getPersistanceManager().find(getNamespace() +
                ".findPriceDataTransform", priceDataTransformDomain);
    }

}
