package org.fms.billing.server.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.BadArrearageDomain;
import org.fms.billing.common.webapp.entity.BadArrearageShowEntity;
import org.fms.billing.common.webapp.service.IBadArrearageService;
import org.fms.billing.server.webapp.dao.BadArrearageDAO;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;

@TransactionService
public class BadArrearageImpl implements IBadArrearageService {
    @TransactionDAO
    private BadArrearageDAO badArrearageDAO;

    @Override
    public List<BadArrearageDomain> findByWhere(BadArrearageDomain badArrearageDomain) {
        return badArrearageDAO.findByWhere(badArrearageDomain);
    }

    @Override
    public int insert(BadArrearageDomain badArrearageDomain) {
        return badArrearageDAO.insert(badArrearageDomain);
    }

    @Override
    public int update(BadArrearageDomain badArrearageDomain) {
        return badArrearageDAO.update(badArrearageDomain);
    }

    @Override
    public int insertList(List<BadArrearageDomain> badArrearageDomains) {
        return badArrearageDAO.insertList(badArrearageDomains);
    }
    @Override
    public List<BadArrearageShowEntity> findArrearageAndBadArrearage(BadArrearageShowEntity badArrearageShowEntity){
        return badArrearageDAO.findArrearageAndBadArrearage(badArrearageShowEntity);
    }

}
