/**
 * Author : czy
 * Date : 2019年7月4日 下午3:36:21
 * Title : com.riozenc.billing.webapp.controller.ComputeAction.java
 *
**/
package org.fms.billing.server.webapp.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.MeterMoneyDomain;
import org.fms.billing.common.webapp.domain.WriteFilesDomain;
import org.fms.billing.common.webapp.service.IMeterMoneyService;
import org.fms.billing.common.webapp.service.IMeterService;
import org.fms.billing.common.webapp.service.IWriteFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.spring.web.client.TitanTemplate;
import com.riozenc.titanTool.spring.web.client.TitanTemplate.TitanCallback;
import com.riozenc.titanTool.spring.web.http.HttpResult;

@ControllerAdvice
@RequestMapping("compute")
public class ComputeAction {

	@Autowired
	@Qualifier("writeFilesServiceImpl")
	private IWriteFilesService writeFilesService;

	@Autowired
	@Qualifier("meterMoneyServiceImpl")
	private IMeterMoneyService iMeterMoneyService;

	@Autowired
	@Qualifier("meterServiceImpl")
	private IMeterService meterService;

	@Autowired
	private TitanTemplate titanTemplate;

	@RequestMapping(params = "method=computeByMeter")
	@ResponseBody
	public Object computeByMeter(@RequestBody String json) {
		/*
		 * WriteFilesDomain writeFilesDomain = GsonUtils.readValue(json,
		 * WriteFilesDomain.class); List<WriteFilesDomain> writeFilesDomains =
		 * writeFilesService.mongoFind(writeFilesDomain);
		 */

		/*
		 * List<MeterDomain> meterDomains =
		 * meterService.getEffectiveComputeMeter(writeFilesDomain.getMon(),
		 * writeFilesDomains);
		 */
		JSONObject jsonObject = JSONObject.parseObject(json);
		WriteFilesDomain writeFilesDomain = GsonUtils.readValue(json, WriteFilesDomain.class);
		List<MeterDomain> meterDomains = GsonUtils.readValueToList(jsonObject.get("meters").toString(),
				MeterDomain.class);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Object> params = new HashMap<>();
		params.put("date", writeFilesDomain.getMon());
		// params.put("writeFilesDomains", writeFilesDomains);
		params.put("meterDomains", meterDomains);
		params.put("sn", writeFilesDomain.getSn());
		params.put("callback", null);
		try {
			TitanCallback<HttpResult> callback = titanTemplate.postCallBack("CFS",
					"cfs/electricMonthlyBill?method=compute", httpHeaders, params, HttpResult.class);

			HttpResult httpResult = callback.call();
			return httpResult;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new HttpResult(HttpResult.ERROR, "计算出错啦.");
	}

	@RequestMapping(params = "method=computeByWriteSect")
	@ResponseBody
//	public Object computeByWriteSect(@RequestBody WriteFilesDomain writeFilesDomain) {
	public Object computeByWriteSect(@RequestBody String json) {
		WriteFilesDomain writeFilesDomain = GsonUtils.readValue(json, WriteFilesDomain.class);
		List<MeterDomain> meterDomains = meterService.getMeterByWriteSectionIds(writeFilesDomain.getMon(),
				writeFilesDomain.getWriteSectionIds(), 0,-2,1,2);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Object> params = new HashMap<>();
		params.put("date", writeFilesDomain.getMon());
		// params.put("writeFilesDomains", writeFilesDomains);
		params.put("meterDomains", meterDomains);
		params.put("sn", writeFilesDomain.getSn());
		params.put("callback", "billingServer/publish?method=computeCallback");
		try {
			TitanCallback<HttpResult> callback = titanTemplate.postCallBack("CFS",
					"cfs/electricMonthlyBill?method=compute", httpHeaders, params, HttpResult.class);

			HttpResult httpResult = callback.call();
			return httpResult;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new HttpResult(HttpResult.ERROR, "计算出错啦.");
	}

	@RequestMapping(params = "method=computeCallback")
	@ResponseBody
	public HttpResult computeCallback(@RequestBody String meterMoneyBody) {
		List<MeterMoneyDomain> meterMoneyDomains = GsonUtils.readValueToList(meterMoneyBody, MeterMoneyDomain.class);
		// 要插入的条数
		// int callBackNum=meterMoneyDomains.size();
		// 实际插入的条数
		// int returnNum=
		iMeterMoneyService.updateList(meterMoneyDomains);
		return new HttpResult(HttpResult.SUCCESS, "成功");
	}
}
