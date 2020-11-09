package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.fms.billing.common.webapp.domain.MeterPenaltyLogDomain;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;

@TransactionDAO
public class MeterPenaltyLogDAO extends AbstractTransactionDAOSupport{
	public List<MeterPenaltyLogDomain> findByWhere(MeterPenaltyLogDomain meterPenaltyLogDomain){
		return getPersistanceManager().find(getNamespace() + ".findByWhere", meterPenaltyLogDomain);
	}
	public int insert(MeterPenaltyLogDomain meterPenaltyLogDomain) {
		return getPersistanceManager().insert(getNamespace() + ".insert", meterPenaltyLogDomain);
	};
	public int update(MeterPenaltyLogDomain meterPenaltyLogDomain) {
		return getPersistanceManager().update(getNamespace() + ".update", meterPenaltyLogDomain);
	}
}
