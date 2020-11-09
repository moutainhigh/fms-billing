package org.fms.billing.server.webapp.action;

import java.util.List;

import org.fms.billing.common.webapp.domain.PreChargeLogDomain;
import org.fms.billing.common.webapp.service.IPreChargeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("PreChargeLog")
public class PreChargeLogController {
	@Autowired
	@Qualifier("preChargeLogServiceImpl")
	private IPreChargeLogService iPreChargeLogService;
	@RequestMapping("select")
	@ResponseBody
	public List<PreChargeLogDomain> fingByWhere(@ModelAttribute PreChargeLogDomain preChargeLogDomain){
		return iPreChargeLogService.findByWhere(preChargeLogDomain);
	}
	@RequestMapping("insert")
	@ResponseBody
	public int insert(@ModelAttribute PreChargeLogDomain preChargeLogDomain) {
		return iPreChargeLogService.insert(preChargeLogDomain);
	}
	@RequestMapping("update")
	@ResponseBody
	public int update(@ModelAttribute PreChargeLogDomain preChargeLogDomain) {
		return iPreChargeLogService.update(preChargeLogDomain);
	}
	@RequestMapping("delete")
	@ResponseBody
	public int delete(@ModelAttribute PreChargeLogDomain preChargeLogDomain) {
		preChargeLogDomain.setStatus(0);
		return iPreChargeLogService.update(preChargeLogDomain);
	}
}
