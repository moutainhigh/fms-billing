package org.fms.billing.server.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.WriteRunningDomain;
import org.fms.billing.common.webapp.service.IWriteRunningService;
import org.fms.billing.server.webapp.dao.WriteRunningDAO;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;
@TransactionService
public class WriteRunningServiceImpl implements IWriteRunningService{
	@TransactionDAO
	private WriteRunningDAO writeRunningDAO;
	@Override
	public List<WriteRunningDomain> findByWhere(WriteRunningDomain writeRunningDomain) {
		return writeRunningDAO.findByWhere(writeRunningDomain);
	}

	@Override
	public int insert(WriteRunningDomain writeRunningDomain) {
		return writeRunningDAO.insert(writeRunningDomain);
	}

	@Override
	public int update(WriteRunningDomain writeRunningDomain) {
		return writeRunningDAO.update(writeRunningDomain);
	}

}
