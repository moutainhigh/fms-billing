/**
 * Author : czy
 * Date : 2019年6月24日 上午9:00:43
 * Title : com.riozenc.billing.webapp.service.IPriceExecutionService.java
 *
**/
package org.fms.billing.common.webapp.service;

import java.util.List;
import java.util.Map;

import org.fms.billing.common.webapp.domain.PriceExecutionDomain;

import com.riozenc.titanTool.spring.web.http.HttpResult;
import com.riozenc.titanTool.spring.webapp.service.BaseService;

public interface IPriceExecutionService extends BaseService<PriceExecutionDomain> {
    public List<PriceExecutionDomain> findByNoPage(PriceExecutionDomain t);
    public HttpResult synCurrentPrice();
    public Map<Long, List<PriceExecutionDomain>> findMongoPriceExecution(String mon);
    public List<PriceExecutionDomain> findTimeLadderPrice();
}
