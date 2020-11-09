package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.BadArrearageDomain;
import org.fms.billing.common.webapp.entity.BadArrearageShowEntity;

public interface IBadArrearageService {
	public List<BadArrearageDomain> findByWhere(BadArrearageDomain badArrearageDomain);
	public int insert(BadArrearageDomain badArrearageDomain);
	public int update(BadArrearageDomain badArrearageDomain);
	public int insertList(List<BadArrearageDomain> badArrearageDomains);
	List<BadArrearageShowEntity> findArrearageAndBadArrearage(BadArrearageShowEntity badArrearageShowEntity);
}
