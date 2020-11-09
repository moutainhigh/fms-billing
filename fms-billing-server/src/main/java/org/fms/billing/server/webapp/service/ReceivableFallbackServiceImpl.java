/**
 * Author : czy
 * Date : 2019年12月5日 下午8:21:59
 * Title : com.riozenc.billing.webapp.service.impl.ReceivableFallbackServiceImpl.java
 *
**/
package org.fms.billing.server.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.ArrearageDomain;
import org.fms.billing.common.webapp.service.IReceivableFallbackService;
import org.fms.billing.server.webapp.dao.MeterDAO;
import org.fms.billing.server.webapp.dao.ReceivableFallbackDAO;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;

@TransactionService
public class ReceivableFallbackServiceImpl implements IReceivableFallbackService {

	@TransactionDAO
	private ReceivableFallbackDAO receivableFallbackDAO;
	
	private MeterDAO meterDAO;

	public List<ArrearageDomain> findReceivable(ArrearageDomain arrearageDomain) {
		List<ArrearageDomain> arrearageDomains = receivableFallbackDAO.findReceivable(arrearageDomain);		
		return arrearageDomains;
	}

	public int fallback(String mon, List<ArrearageDomain> arrearageDomains) {
		int count = receivableFallbackDAO.fallback(mon, arrearageDomains);
		return count;
	}

}
