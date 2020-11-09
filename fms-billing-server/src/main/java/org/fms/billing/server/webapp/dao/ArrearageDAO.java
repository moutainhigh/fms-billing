package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.fms.billing.common.webapp.domain.ArrearageDetailDomain;
import org.fms.billing.common.webapp.domain.ArrearageDomain;
import org.fms.billing.common.webapp.entity.BankCollectionEntity;
import org.fms.billing.common.webapp.entity.ElectricityTariffRankEntity;
import org.fms.billing.common.webapp.entity.MeterPageEntity;
import org.fms.billing.common.webapp.entity.PulishEntity;

import com.riozenc.titanTool.annotation.PaginationSupport;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;
import com.riozenc.titanTool.spring.webapp.dao.BaseDAO;

@TransactionDAO
	public class ArrearageDAO extends AbstractTransactionDAOSupport implements BaseDAO<ArrearageDomain> {

	@PaginationSupport
	public List<ArrearageDomain> findByWhere(ArrearageDomain arrearageDomain) {
		return getPersistanceManager().find(getNamespace() + ".findByWhere", arrearageDomain);
	}

	@PaginationSupport
	public List<ArrearageDomain> findDetailByWhere(ArrearageDomain arrearageDomain) {
		return getPersistanceManager().find(getNamespace() + ".findDetailByWhere", arrearageDomain);
	}

	public int insert(ArrearageDomain arrearageDomain) {
		return getPersistanceManager().insert(getNamespace() + ".insert", arrearageDomain);
	}

	@Override
	public int delete(ArrearageDomain arrearageDomain) {
		return getPersistanceManager().delete(getNamespace() + ".delete",
				arrearageDomain);

	}

	public int update(ArrearageDomain arrearageDomain) {
		return getPersistanceManager().update(getNamespace() + ".update", arrearageDomain);
	}

	
	public List<ArrearageDetailDomain> findArrearageDetail(MeterPageEntity meterPageEntity) {
		return getPersistanceManager().find(getNamespace() + ".findArrearageDetail", meterPageEntity);
	}

	public List<ArrearageDetailDomain> findArrearageDetailByWhere(ArrearageDomain arrearageDomain) {
		return getPersistanceManager().find(getNamespace() + ".findArrearageDetailByWhere", arrearageDomain);
	}

	public ArrearageDomain findByKey(ArrearageDomain arrearageDomain) {
		return getPersistanceManager().load(getNamespace() + ".findByKey", arrearageDomain);
	}

	public List<ArrearageDomain> findByListKey(String ids) {
		return getPersistanceManager().find(getNamespace() + ".findByListKey", ids);
	}

	public int deleteListByParam(List<ArrearageDomain> arrearageDomains) {
		return getPersistanceManager(ExecutorType.BATCH).deleteList(getNamespace() + ".deleteByParam",
				arrearageDomains);
	}

	public int insertList(List<ArrearageDomain> arrearageDomains) {
		return getPersistanceManager(ExecutorType.BATCH).insertList(getNamespace() + ".insert", arrearageDomains);
	}

	public List<ArrearageDomain> findArrearageByMeterIds(String meterIds) {
		return getPersistanceManager().find(getNamespace() + ".findArrearageByMeterIds", meterIds);
	}

	public List<ArrearageDomain> findAllByMeterIds(List<Long> meterIds) {
		return getPersistanceManager().find(getNamespace() + ".findAllByMeterIds", meterIds);
	}

	public List<ArrearageDomain> findArrearageBySettleIds(String settleIds) {
		return getPersistanceManager().find(getNamespace() + ".findArrearageBySettleIds", settleIds);
	}

	public int updateLockByIds(List<Long> ids) {
		return getPersistanceManager().update(getNamespace() +
				".updateLockByIds", ids);
	}

	public int removeLockByIds(List<Long> ids) {
		return getPersistanceManager().update(getNamespace() +
				".removeLockByIds", ids);
	}

    public List<ArrearageDomain> findBySettleIdMonAndSn(ArrearageDomain arrearageDomain) {
        return getPersistanceManager().find(getNamespace() + ".findBySettleIdMonAndSn", arrearageDomain);
    }

	public int updateList(List<ArrearageDomain> arrearageDomains) {
		return getPersistanceManager(ExecutorType.BATCH).updateList(getNamespace() + ".update",
				arrearageDomains);
	}
	@PaginationSupport
	public List<ArrearageDomain> arrearageAccumulate(ArrearageDomain arrearage) {
		return getPersistanceManager().find(getNamespace() + ".arrearageAccumulate", arrearage);
	}
    public List<ArrearageDomain> findByMeterIdsMonAndSn(PulishEntity pulishEntity) {
        return getPersistanceManager().find(getNamespace() + ".findByMeterIdsMonAndSn", pulishEntity);
    }
	public int deleteArrearageByMeterIdsMonAndSn(PulishEntity pulishEntity) {
		return getPersistanceManager().delete(getNamespace() +
						".deleteArrearageByMeterIdsMonAndSn",pulishEntity);
	}
	//欠费汇总报表专用
	public List<ArrearageDomain> findArrearageQuerySumByWhere(ArrearageDomain arrearageDomain) {
		return getPersistanceManager().find(getNamespace() + ".findArrearageQuerySumByWhere", arrearageDomain);
	}

	//按结算户汇总
	@PaginationSupport
	public List<BankCollectionEntity> findArrearageGroupbySettleId(ArrearageDomain arrearageDomain) {
		return getPersistanceManager().find(getNamespace() + ".findArrearageGroupbySettleId", arrearageDomain);
	}
	//客户明细 按结算户 月份 sn 分组
	@PaginationSupport
	public List<ArrearageDomain> findArrearageGroupBySettleAndMon(ArrearageDomain arrearageDomain) {
		return getPersistanceManager().find(getNamespace() + ".findArrearageGroupBySettleAndMon", arrearageDomain);
	}

	// 短信服务 按照是否付费 当前月份 是否发送
	public List<ArrearageDomain> findByIsSettleMonAndIsSend(ArrearageDomain arrearageDomain) {
		return getPersistanceManager().find(getNamespace() + ".findByIsSettleMonAndIsSend", arrearageDomain);
	}

	public int updateSendBySettlementIds(List<Long> ids) {
		return getPersistanceManager().update(getNamespace() +
				".updateSendBySettlementIds", ids);
	}

	public int updateSendBySettlementIdsAndMon(ArrearageDomain arrearageDomain) {
		return getPersistanceManager().update(getNamespace() +
				".updateSendBySettlementIdsAndMon", arrearageDomain);
	}
	// 电量电费排行
	public List<ArrearageDomain> electricityTariffRankQuery(ElectricityTariffRankEntity electricityTariffRankEntity) {
		return getPersistanceManager().find(getNamespace() + ".electricityTariffRankQuery", electricityTariffRankEntity);
	}
}
