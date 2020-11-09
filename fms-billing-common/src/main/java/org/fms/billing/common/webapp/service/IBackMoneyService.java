package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.BackMoneyDomain;

public interface IBackMoneyService {
	public List<BackMoneyDomain> findByWhere(BackMoneyDomain backMoneyDomain);
	public int insert(BackMoneyDomain backMoneyDomain);
	public int update(BackMoneyDomain backMoneyDomain);
}
