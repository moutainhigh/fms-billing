package org.fms.billing.common.webapp.service;

import java.io.IOException;
import java.util.List;

import org.fms.billing.common.webapp.domain.ArrearageDetailDomain;
import org.fms.billing.common.webapp.domain.ArrearageDomain;
import org.fms.billing.common.webapp.domain.beakInterface.CustomerDomain;
import org.fms.billing.common.webapp.entity.BankCollectionEntity;
import org.fms.billing.common.webapp.entity.ElectricityTariffRankEntity;
import org.fms.billing.common.webapp.entity.MeterPageEntity;
import org.fms.billing.common.webapp.entity.PulishEntity;

import com.riozenc.titanTool.spring.webapp.service.BaseService;

public interface IArrearageService extends BaseService<ArrearageDomain> {
	public List<ArrearageDomain> findByWhere(ArrearageDomain arrearageDomain);

	public ArrearageDomain findByKey(ArrearageDomain arrearageDomain);

	public List<ArrearageDomain> findByListKey(String ids);

	public int insert(ArrearageDomain arrearageDomain);

	public int update(ArrearageDomain arrearageDomain);

	public List<ArrearageDetailDomain> findArrearageDetail(MeterPageEntity meterPageEntity);

	public List<ArrearageDetailDomain> findArrearageDetailByWhere(ArrearageDomain arrearageDomain);

	public int updateList(List<ArrearageDomain> arrearageDomains);

	public List<ArrearageDomain> findArrearageByMeterIds(String meterIds);

	public int updateLockByIds(List<Long> ids);

	public int removeLockByIds(List<Long> ids);

	public List<ArrearageDomain> findArrearageBySettleIds(String settleIds);


	public List<ArrearageDomain> findBySettleIdMonAndSn(ArrearageDomain arrearageDomain);

	List<ArrearageDomain> findArrearageQuerySumByWhere(ArrearageDomain arrearage);

	List<ArrearageDomain> arrearageDetailQuery(ArrearageDomain arrearage);


	List<ArrearageDomain> arrearageAccumulate(ArrearageDomain arrearage) throws IOException;

	List<ArrearageDomain> findByMeterIdsMonAndSn(PulishEntity pulishEntity);

	List<BankCollectionEntity> findArrearageGroupbySettleId(ArrearageDomain arrearageDomain);

	List<ArrearageDomain> findArrearageByCustomer(CustomerDomain customer);

	public List<ArrearageDomain> findArrearageGroupBySettleAndMon(ArrearageDomain arrearageDomain);

	List<ArrearageDomain> findByIsSettleMonAndIsSend(ArrearageDomain arrearageDomain);

	int updateSendBySettlementIds(List<Long> settlementIds);

	int updateSendBySettlementIdsAndMon(ArrearageDomain arrearageDomain);

	List<ArrearageDomain> reminderNotice(ArrearageDomain arrearage) throws IOException;

	// 电量电费排行
	public List<ArrearageDomain> electricityTariffRankQuery(ElectricityTariffRankEntity electricityTariffRankEntity);
}
