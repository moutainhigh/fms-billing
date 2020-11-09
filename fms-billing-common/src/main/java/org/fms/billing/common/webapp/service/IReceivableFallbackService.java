/**
 * Author : czy
 * Date : 2019年12月5日 下午8:21:16
 * Title : com.riozenc.billing.webapp.service.IReceivableFallbackService.java
 *
**/
package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.ArrearageDomain;

public interface IReceivableFallbackService  {
	public List<ArrearageDomain> findReceivable(ArrearageDomain arrearageDomain);
	
	public int fallback(String mon, List<ArrearageDomain> arrearageDomains);
}
