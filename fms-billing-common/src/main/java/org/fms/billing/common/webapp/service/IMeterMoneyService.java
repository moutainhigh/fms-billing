package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.MeterMoneyDomain;
import org.fms.billing.common.webapp.domain.beakInterface.SettlementDomain;
import org.fms.billing.common.webapp.domain.mongo.MeterMoneySumMongoDomain;
import org.fms.billing.common.webapp.domain.mongo.MeterMongoDomain;

public interface IMeterMoneyService {
	public List<MeterMoneyDomain> findByWhere(MeterMoneyDomain meterMoneyDomain);

	public List<MeterMoneyDomain> meterMoneyDetailQuery(MeterMoneyDomain meterMoneyDomain);

	public int insert(MeterMoneyDomain meterMoneyDomain);

	public int update(MeterMoneyDomain meterMoneyDomain);

	public List<MeterMoneyDomain> mongoFind(MeterMoneyDomain meterMoneyDomain);

	public int updateList(List<MeterMoneyDomain> meterMoneyDomains);

	public MeterMoneyDomain findByKey(MeterMoneyDomain meterMoneyDomain);
	
	public long dataValidation(List<MeterDomain> meterDomains);

	public List<MeterMongoDomain> getMeterMoneyByWhere(MeterDomain meterDomain);

	public List<MeterMongoDomain> getMeterMoney(MeterDomain meterDomain);

	List<MeterMoneyDomain> findHistoricalMeterMoney(MeterMoneyDomain meterMoneyDomain);

	public List<MeterMoneySumMongoDomain> getMeterMoneySum(MeterDomain meterDomain);

	public List<MeterMoneyDomain> findByMeterIds(String meterIds);

	public List<MeterMoneyDomain> findMeterMoneyByIds(List<Long> ids);

	List<MeterMoneyDomain> findMeterMoneyBySettlement(SettlementDomain settlementDomain);

	public List<MeterMoneyDomain> ladderPowerQuery(MeterMoneyDomain meterMoneyDomain);
}
