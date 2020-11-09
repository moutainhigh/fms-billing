package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.ArrearageBackDomain;

public interface IArrearageBackService {
    public List<ArrearageBackDomain> findByWhere(ArrearageBackDomain arrearageBackDomain);

    public int insert(ArrearageBackDomain arrearageBackDomain);

    public int update(ArrearageBackDomain arrearageBackDomain);

}
