package org.fms.billing.server.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.ArrearageBackDomain;
import org.fms.billing.common.webapp.service.IArrearageBackService;
import org.fms.billing.server.webapp.dao.ArrearageBackDAO;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;

@TransactionService
public class ArrearageBackServiceImpl implements IArrearageBackService {
    @TransactionDAO
    private ArrearageBackDAO arrearageBackDAO;

    @Override
    public List<ArrearageBackDomain> findByWhere(ArrearageBackDomain arrearageBackDomain) {
        return arrearageBackDAO.findByWhere(arrearageBackDomain);
    }


    @Override
    public int insert(ArrearageBackDomain arrearageBackDomain) {
        return arrearageBackDAO.insert(arrearageBackDomain);
    }

    @Override
    public int update(ArrearageBackDomain arrearageBackDomain) {
        return arrearageBackDAO.update(arrearageBackDomain);
    }


}
