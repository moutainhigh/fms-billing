package org.fms.billing.server.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.CosStandardConfigDomain;
import org.fms.billing.common.webapp.service.ICosStandardConfigService;
import org.fms.billing.server.webapp.dao.CosStandardConfigDAO;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;

@TransactionService
public class CosStandardConfigServiceImpl implements ICosStandardConfigService {
	@TransactionDAO
	private CosStandardConfigDAO cosStandardConfigDAO;

	@Override
	public List<CosStandardConfigDomain> findByWhere(CosStandardConfigDomain cosStandardConfigDomain) {
		return cosStandardConfigDAO.findByWhere(cosStandardConfigDomain);
	}

	@Override
	public int insert(CosStandardConfigDomain cosStandardConfigDomain) {
		return cosStandardConfigDAO.insert(cosStandardConfigDomain);
	}

	@Override
	public int update(CosStandardConfigDomain cosStandardConfigDomain) {
		// TODO Auto-generated method stub
		return 0;
	}
}
