package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.WriteRunningDomain;

public interface IWriteRunningService {
	public List<WriteRunningDomain> findByWhere(WriteRunningDomain writeRunningDomain);
	public int insert(WriteRunningDomain writeRunningDomain);
	public int update(WriteRunningDomain writeRunningDomain);
}
