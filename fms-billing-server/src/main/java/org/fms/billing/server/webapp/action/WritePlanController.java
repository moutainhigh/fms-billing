package org.fms.billing.server.webapp.action;

import java.util.List;

import org.fms.billing.common.webapp.domain.WritePlanDomain;
import org.fms.billing.common.webapp.service.IWritePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("WritePlan")
public class WritePlanController {
	@Autowired
	@Qualifier("writePlanServiceImpl")
	private IWritePlanService iWritePlanService;
	@RequestMapping("select")
	@ResponseBody
	public List<WritePlanDomain> fingByWhere(@ModelAttribute WritePlanDomain writePlanDomain){
		return iWritePlanService.findByWhere(writePlanDomain);
	}
	@RequestMapping("insert")
	@ResponseBody
	public int insert(@ModelAttribute WritePlanDomain writePlanDomain) {
		return iWritePlanService.insert(writePlanDomain);
	}
	@RequestMapping("update")
	@ResponseBody
	public int update(@ModelAttribute WritePlanDomain writePlanDomain) {
		return iWritePlanService.update(writePlanDomain);
	}
	@RequestMapping("delete")
	@ResponseBody
	public int delete(@ModelAttribute WritePlanDomain writePlanDomain) {
		writePlanDomain.setStatus(0);
		return iWritePlanService.update(writePlanDomain);
	}
}
