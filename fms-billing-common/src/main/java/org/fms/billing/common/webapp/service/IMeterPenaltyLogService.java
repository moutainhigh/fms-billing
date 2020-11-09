package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.MeterPenaltyLogDomain;

public interface IMeterPenaltyLogService {
	public List<MeterPenaltyLogDomain> findByWhere(MeterPenaltyLogDomain meterPenaltyLogDomain);
	public int insert(MeterPenaltyLogDomain meterPenaltyLogDomain);
	public int update(MeterPenaltyLogDomain meterPenaltyLogDomain);
}
