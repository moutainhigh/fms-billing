package org.fms.billing.server.webapp.action;

import java.util.List;

import org.fms.billing.common.webapp.domain.WriteRunningDomain;
import org.fms.billing.common.webapp.service.IWriteRunningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("WriteRunning")
public class WriteRunningController {
	@Autowired
	@Qualifier("writeRunningServiceImpl")
	private IWriteRunningService iWriteRunningService;

	@RequestMapping("select")
	@ResponseBody
	public List<WriteRunningDomain> fingByWhere(@ModelAttribute WriteRunningDomain writeRunningDomain) {
		return iWriteRunningService.findByWhere(writeRunningDomain);
	}

	@RequestMapping("insert")
	@ResponseBody
	public int insert(@ModelAttribute WriteRunningDomain writeRunningDomain) {
		return iWriteRunningService.insert(writeRunningDomain);
	}

	@RequestMapping("update")
	@ResponseBody
	public int update(@RequestBody WriteRunningDomain writeRunningDomain) {
		return iWriteRunningService.update(writeRunningDomain);
	}

}
