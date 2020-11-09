package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.BillDomain;
import org.fms.billing.common.webapp.domain.MeterMoneyDetailsDomain;

public interface IBillService {
	public List<BillDomain> findByWhere(BillDomain billDomain);

	public int insert(BillDomain billDomain);

	public int update(BillDomain billDomain);

	public List<MeterMoneyDetailsDomain> getMeterMoneyBySettlement(String settlementNo, String mon);
}
