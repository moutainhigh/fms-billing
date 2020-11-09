/**
 * Author : chizf
 * Date : 2020年10月28日 下午3:17:34
 * Title : org.fms.billing.common.webapp.service.IComputeService.java
 *
**/
package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.MeterDomain;

import com.riozenc.titanTool.spring.web.http.HttpResult;

public interface IComputeService {

	public HttpResult<?> compute(String mon, String sn, List<MeterDomain> meterDomains);
}
