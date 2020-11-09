package org.fms.billing.server.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.BackMoneyDomain;
import org.fms.billing.common.webapp.service.IBackMoneyService;
import org.fms.billing.server.webapp.dao.BackMoneyDAO;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;

@TransactionService
public class BackMoneyServiceImpl implements IBackMoneyService {
	@TransactionDAO
	private BackMoneyDAO backMoneyDAO;

	@Override
	public List<BackMoneyDomain> findByWhere(BackMoneyDomain backMoneyDomain) {
		return backMoneyDAO.findByWhere(backMoneyDomain);
	}

	@Override
	public int insert(BackMoneyDomain backMoneyDomain) {
		return backMoneyDAO.insert(backMoneyDomain);
	}

	@Override
	public int update(BackMoneyDomain backMoneyDomain) {
		return backMoneyDAO.update(backMoneyDomain);
	}
}
