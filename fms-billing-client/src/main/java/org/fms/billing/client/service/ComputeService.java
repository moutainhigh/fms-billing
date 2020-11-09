/**
 * Author : chizf
 * Date : 2020年10月28日 下午3:16:12
 * Title : org.fms.billing.client.service.ComputeService.java
 *
**/
package org.fms.billing.client.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.service.IComputeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.riozenc.titanTool.spring.web.client.TitanTemplate;
import com.riozenc.titanTool.spring.web.client.TitanTemplate.TitanCallback;
import com.riozenc.titanTool.spring.web.http.HttpResult;

public class ComputeService implements IComputeService {
	@Autowired
	private TitanTemplate titanTemplate;

	@Override
	public HttpResult<?> compute(String mon, String sn, List<MeterDomain> meterDomains) {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Object> params = new HashMap<>();
		params.put("date", mon);
		// params.put("writeFilesDomains", writeFilesDomains);
		params.put("meterDomains", meterDomains);
		params.put("sn", sn);
		params.put("callback", null);

		TitanCallback<HttpResult> callback;
		try {
			callback = titanTemplate.postCallBack("CFS", "cfs/electricMonthlyBill?method=compute", httpHeaders, params,
					HttpResult.class);

			HttpResult<?> httpResult = callback.call();
			return httpResult;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new HttpResult<>(HttpResult.ERROR,"失败","");

	}

}
