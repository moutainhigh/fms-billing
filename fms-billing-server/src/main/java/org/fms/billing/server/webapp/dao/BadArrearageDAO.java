package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.fms.billing.common.webapp.domain.BadArrearageDomain;
import org.fms.billing.common.webapp.entity.BadArrearageShowEntity;

import com.riozenc.titanTool.annotation.PaginationSupport;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;

@TransactionDAO
public class BadArrearageDAO extends AbstractTransactionDAOSupport {
    @PaginationSupport
    public List<BadArrearageDomain> findByWhere(BadArrearageDomain badArrearageDomain) {
        return getPersistanceManager().find(getNamespace() + ".findByWhere", badArrearageDomain);
    }

    public int insert(BadArrearageDomain badArrearageDomain) {
        return getPersistanceManager().insert(getNamespace() + ".insert", badArrearageDomain);
    }

    public int update(BadArrearageDomain badArrearageDomain) {
        return getPersistanceManager().update(getNamespace() + ".update", badArrearageDomain);
    }

    public int insertList(List<BadArrearageDomain> badArrearageDomains) {
        return getPersistanceManager(ExecutorType.BATCH).insertList(getNamespace() +
                        ".insert",
                badArrearageDomains);
    }

    @PaginationSupport
    public List<BadArrearageShowEntity> findArrearageAndBadArrearage(BadArrearageShowEntity badArrearageShowEntity) {
        return getPersistanceManager().find(getNamespace() +
                        ".findArrearageAndBadArrearage", badArrearageShowEntity);
    }

}
