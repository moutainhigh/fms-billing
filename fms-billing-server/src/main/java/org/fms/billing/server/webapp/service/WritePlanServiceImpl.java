package org.fms.billing.server.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.WritePlanDomain;
import org.fms.billing.common.webapp.service.IWritePlanService;
import org.fms.billing.server.webapp.dao.WritePlanDAO;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;

@TransactionService
public class WritePlanServiceImpl implements IWritePlanService{
	@TransactionDAO
	private WritePlanDAO writePlanDAO;

	@Override
	public List<WritePlanDomain> findByWhere(WritePlanDomain writePlanDomain) {
		return writePlanDAO.findByWhere(writePlanDomain);
	}

	@Override
	public int insert(WritePlanDomain writePlanDomain) {
		return writePlanDAO.insert(writePlanDomain);
	}

	@Override
	public int update(WritePlanDomain writePlanDomain) {
		return writePlanDAO.update(writePlanDomain);
	}
}
