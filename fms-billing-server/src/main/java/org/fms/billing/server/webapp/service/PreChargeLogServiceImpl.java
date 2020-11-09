package org.fms.billing.server.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.PreChargeLogDomain;
import org.fms.billing.common.webapp.service.IPreChargeLogService;
import org.fms.billing.server.webapp.dao.PreChargeLogDAO;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;

@TransactionService
public class PreChargeLogServiceImpl implements IPreChargeLogService{
@TransactionDAO
private PreChargeLogDAO preChargeLogDAO;
	@Override
	public List<PreChargeLogDomain> findByWhere(PreChargeLogDomain preChargeLogDomain) {
		return preChargeLogDAO.findByWhere(preChargeLogDomain);
	}

	@Override
	public int insert(PreChargeLogDomain preChargeLogDomain) {
		return preChargeLogDAO.insert(preChargeLogDomain);
	}

	@Override
	public int update(PreChargeLogDomain preChargeLogDomain) {
		return preChargeLogDAO.update(preChargeLogDomain);
	}

}
