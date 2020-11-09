package org.fms.billing.server.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.MeterPenaltyLogDomain;
import org.fms.billing.common.webapp.service.IMeterPenaltyLogService;
import org.fms.billing.server.webapp.dao.MeterPenaltyLogDAO;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;
@TransactionService
public class MeterPenaltyLogServiceImpl implements IMeterPenaltyLogService{
@TransactionDAO 
private MeterPenaltyLogDAO meterPenaltyLogDAO;
	@Override
	public List<MeterPenaltyLogDomain> findByWhere(MeterPenaltyLogDomain meterPenaltyLogDomain) {
		return meterPenaltyLogDAO.findByWhere(meterPenaltyLogDomain);
	}

	@Override
	public int insert(MeterPenaltyLogDomain meterPenaltyLogDomain) {
		return meterPenaltyLogDAO.insert(meterPenaltyLogDomain);
	}

	@Override
	public int update(MeterPenaltyLogDomain meterPenaltyLogDomain) {
		return meterPenaltyLogDAO.update(meterPenaltyLogDomain);
	}

}
