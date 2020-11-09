package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.PreChargeLogDomain;

public interface IPreChargeLogService {
	public List<PreChargeLogDomain> findByWhere(PreChargeLogDomain preChargeLogDomain);
	public int insert(PreChargeLogDomain preChargeLogDomain);
	public int update(PreChargeLogDomain preChargeLogDomain);
}
