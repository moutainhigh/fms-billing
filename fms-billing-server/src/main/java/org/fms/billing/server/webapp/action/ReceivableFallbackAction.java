/**
 * Author : czy
 * Date : 2019年12月5日 下午5:46:45
 * Title : com.riozenc.billing.webapp.controller.ReceivableFallbackAction.java
 *
**/
package org.fms.billing.server.webapp.action;

import java.util.List;
import java.util.stream.Collectors;

import org.fms.billing.common.webapp.domain.ArrearageDomain;
import org.fms.billing.common.webapp.domain.ReceivableFallbackDomain;
import org.fms.billing.common.webapp.service.IArrearageService;
import org.fms.billing.common.webapp.service.IReceivableFallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.titanTool.spring.web.http.HttpResult;
import com.riozenc.titanTool.spring.web.http.HttpResultPagination;

/**
 * 应收账款回退
 * 
 * @author czy
 *
 */

@RequestMapping("receivableFallback")
@ControllerAdvice
public class ReceivableFallbackAction {

	@Autowired
	@Qualifier("arrearageServiceImpl")
	private IArrearageService arrearageService;
	@Autowired
	@Qualifier("receivableFallbackServiceImpl")
	private IReceivableFallbackService receivableFallbackService;

	/**
	 * 回退数据查询
	 * @param arrearageDomain
	 * @return
	 */
	
	@RequestMapping(params = "method=findReceivable")
	@ResponseBody
	public HttpResultPagination<ArrearageDomain> findReceivable(@RequestBody ArrearageDomain arrearageDomain) {
		List<ArrearageDomain> arrearageDomains = receivableFallbackService.findReceivable(arrearageDomain);
		return new HttpResultPagination<ArrearageDomain>(arrearageDomain, arrearageDomains);
	}

	/**
	 * 回退
	 */
	
	@RequestMapping(params = "method=fallback")
	@ResponseBody
	public HttpResult fallback(@RequestBody ReceivableFallbackDomain receivableFallbackDomain) {
		


		int count = receivableFallbackService.fallback(receivableFallbackDomain.getMon(), receivableFallbackDomain.getMeterIds().stream().map(meterId->{
			ArrearageDomain arrearageDomain = new ArrearageDomain();
			arrearageDomain.setMeterId(meterId);
			return arrearageDomain;
		}).collect(Collectors.toList()));

		// 删除mongo库数据

		// 删除mysql库的数据
		
		return new HttpResult(HttpResult.SUCCESS,"处理"+count+"条.");
	}

}
