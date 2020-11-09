package org.fms.billing.server.webapp.action;

import java.util.List;

import org.fms.billing.common.webapp.domain.MeterPenaltyLogDomain;
import org.fms.billing.common.webapp.service.IMeterPenaltyLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("MeterPenaltyLog")
public class MeterPenaltyLogController {
	@Autowired
	@Qualifier("meterPenaltyLogServiceImpl")
	private IMeterPenaltyLogService iMeterPenaltyLogService;
	
	@RequestMapping("select")
	@ResponseBody
	public List<MeterPenaltyLogDomain> fingByWhere(@ModelAttribute MeterPenaltyLogDomain meterPenaltyLogDomain){
		return iMeterPenaltyLogService.findByWhere(meterPenaltyLogDomain);
	}
	@RequestMapping("insert")
	@ResponseBody
	public int insert(@ModelAttribute MeterPenaltyLogDomain meterPenaltyLogDomain) {
		return iMeterPenaltyLogService.insert(meterPenaltyLogDomain);
	}
	@RequestMapping("update")
	@ResponseBody
	public int update(@ModelAttribute MeterPenaltyLogDomain meterPenaltyLogDomain) {
		return iMeterPenaltyLogService.update(meterPenaltyLogDomain);
	}
	@RequestMapping("delete")
	@ResponseBody
	public int delete(@ModelAttribute MeterPenaltyLogDomain meterPenaltyLogDomain) {
		meterPenaltyLogDomain.setStatus(0);
		return iMeterPenaltyLogService.update(meterPenaltyLogDomain);
	}
}
