/**
 * Author : czy
 * Date : 2019年6月19日 下午3:40:05
 * Title : com.riozenc.billing.webapp.controller.DeptMonAction.java
 *
**/
package org.fms.billing.server.webapp.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.security.sasl.AuthenticationException;

import org.fms.billing.common.webapp.domain.DeptMonDomain;
import org.fms.billing.common.webapp.service.IDeptMonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.riozenc.titanTool.common.date.DateUtil;
import com.riozenc.titanTool.common.json.utils.JSONUtil;
import com.riozenc.titanTool.spring.web.http.HttpResult;

/**
 * 系统初始化功能 营业区域电费月份
 * 
 * @author czy
 *
 */
@ControllerAdvice
@RequestMapping("deptMon")
public class DeptMonAction {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	@Qualifier("deptMonServiceImpl")
	private IDeptMonService deptMonService;

//	@ResponseBody
//	@RequestMapping(params = "method=nextMon")
//	public Object nextMon(@RequestBody String json) throws Exception {
//		String systemDate = DateUtil.getDate("yyyyMM");
//
//		Map map = JSONUtil.readValue(json, Map.class);
//		List<Integer> deptIds = (List) map.get("deptIds");
//
//		return deptMonService.nextMon(systemDate, deptIds);
//	}
	@ResponseBody
	 @RequestMapping(params = "method=nextMon")
	 public HttpResult<?> nextMon(@RequestBody String json) throws Exception {
	  String systemDate = DateUtil.getDate("yyyyMM");

	  Map map = JSONUtil.readValue(json, Map.class);
	  List<Integer> deptIds = (List) map.get("deptIds");

	  HttpResult<List<DeptMonDomain>> httpResult = new HttpResult<>();

	  try {
	   httpResult.setStatusCode(HttpResult.SUCCESS);
	   httpResult.setResultData(deptMonService.nextMon(systemDate, deptIds));
	  } catch (Exception e) {
	   e.printStackTrace();
	   httpResult.setStatusCode(HttpResult.ERROR);
	   httpResult.setMessage(e.getCause().getMessage());
	  }

	  return httpResult;
	 }

	@ResponseBody
	@RequestMapping(params = "method=getDeptCurrentMonById")
	public Object getDeptCurrentMonById(@RequestBody String json)
			throws JsonParseException, JsonMappingException, IOException {
		Map map = JSONUtil.readValue(json, Map.class);
		List<Integer> ids = (List<Integer>) map.get("ids");
		if (ids == null || ids.size() == 0) {
			throw new NullPointerException("组织机构ID不能为空.");
		}

		List<DeptMonDomain> deptMonDomains = deptMonService.getDeptMonByDeptIds(ids);
		return deptMonDomains;
	}

	/**
	 * 获取营业区域当前月份
	 * 
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */

	@ResponseBody
	@RequestMapping(params = "method=getDeptCurrentMon")
	public Object getDeptCurrentMon(@RequestHeader("Authorization") String token)
			throws JsonParseException, JsonMappingException, IOException {

		if (token == null) {
			throw new AuthenticationException("身份无效.");
		}

//		Map map = JSONUtil.readValue(json, Map.class);

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Authorization", token);
		HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);

//		ResponseEntity<String> responseEntity = restTemplate.exchange(
//				"http://AUTH-DATA/auth-data/dept/user/" + map.get("managerId"), HttpMethod.GET, requestEntity,
//				String.class);

		ResponseEntity<String> responseEntity = restTemplate.exchange("http://AUTH-CENTER/auth/dept/auth/tree",
				HttpMethod.GET, requestEntity, String.class);

		HttpResult<List<DeptMonDomain>> httpResult = JSONUtil.readValue(responseEntity.getBody(),
				new TypeReference<HttpResult<List<DeptMonDomain>>>() {
				});

		// 实际获取的是dept对象
		List<DeptMonDomain> resultList = httpResult.getResultData();

		// 营业区域电费月份对象
		List<DeptMonDomain> deptMonDomains = deptMonService.findByWhere(new DeptMonDomain());

		deptMonDomains.forEach(d -> {
			assignmentDeptMon(resultList, d);
		});

		return new HttpResult(HttpResult.SUCCESS, resultList);
	}

	private void assignmentDeptMon(List<DeptMonDomain> deptMonDomains, DeptMonDomain d) {
		deptMonDomains.forEach(r -> {

			if (r.getId().equals(d.getDeptId())) {
				r.setMon(d.getMon());
			}

			if (r.getChildren() != null || !r.getChildren().isEmpty()) {
				assignmentDeptMon(r.getChildren(), d);
			}

		});
	}
}
