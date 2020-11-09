package org.fms.billing.server.webapp.action;

import java.util.List;

import org.fms.billing.common.webapp.domain.PreChargeDomain;
import org.fms.billing.common.webapp.domain.beakInterface.CustomerDomain;
import org.fms.billing.common.webapp.service.IPreChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.spring.web.http.HttpResultPagination;

@Controller
@RequestMapping("preCharge")
public class PreChargeController {
	@Autowired
	@Qualifier("preChargeServiceImpl")
	private IPreChargeService iPreChargeService;
	
	@RequestMapping("select")
	@ResponseBody
	public List<PreChargeDomain> fingByWhere(@RequestBody String preChargeDomainJson){
		PreChargeDomain preChargeDomain=
				JSONObject.parseObject(preChargeDomainJson,
						PreChargeDomain.class);
		return iPreChargeService.findByWhere(preChargeDomain);
	}

	@RequestMapping("findPreChargeByCustomer")
	@ResponseBody
	public HttpResultPagination findPreChargeByCustomer(@RequestBody String customerDomain) {
		CustomerDomain customer = GsonUtils.readValue(customerDomain, CustomerDomain.class);
		PreChargeDomain preChargeDomain = new PreChargeDomain();
		List<PreChargeDomain> preChargeByCustomer = iPreChargeService.findPreChargeByCustomer(customer);
		return new HttpResultPagination<>(preChargeDomain, preChargeByCustomer);
	}

	@RequestMapping("insert")
	@ResponseBody
	public int insert(@ModelAttribute PreChargeDomain preChargeDomain) {
		return iPreChargeService.insert(preChargeDomain);
	}
	@RequestMapping("update")
	@ResponseBody
	public int update(@ModelAttribute PreChargeDomain preChargeDomain) {
		return iPreChargeService.update(preChargeDomain);
	}
	@RequestMapping("delete")
	@ResponseBody
	public int delete(@ModelAttribute PreChargeDomain preChargeDomain) {
		preChargeDomain.setStatus(0);
		return iPreChargeService.update(preChargeDomain);
	}

	@RequestMapping("findPreChargeBySettleIds")
	@ResponseBody
	public List<PreChargeDomain> findPreChargeBySettleIds(@RequestBody String settlementIdJson) {
		List<Long> settlementIds = JSONObject.parseArray(settlementIdJson,
				Long.class);
		return iPreChargeService.findPreChargeBySettleIds(settlementIds);
	}
}
