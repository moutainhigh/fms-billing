package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.WritePlanDomain;

public interface IWritePlanService {
	public List<WritePlanDomain> findByWhere(WritePlanDomain writePlanDomain);
	public int insert(WritePlanDomain writePlanDomain);
	public int update(WritePlanDomain writePlanDomain);
}
