package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.CosStandardConfigDomain;

public interface ICosStandardConfigService {
	public List<CosStandardConfigDomain> findByWhere(CosStandardConfigDomain cosStandardConfigDomain);
	public int insert(CosStandardConfigDomain cosStandardConfigDomain);
	public int update(CosStandardConfigDomain cosStandardConfigDomain);
}
