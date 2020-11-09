package org.fms.billing.server.webapp.action;

import java.util.List;

import org.fms.billing.common.webapp.domain.BackMoneyDomain;
import org.fms.billing.common.webapp.service.IBackMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("BackMoney")
public class BackMoneyController {
	@Autowired
	@Qualifier("backMoneyServiceImpl")
	private IBackMoneyService iBackMoneyService;
	@RequestMapping("select")
	@ResponseBody
	public List<BackMoneyDomain> fingByWhere(@ModelAttribute BackMoneyDomain backMoneyDomain){
		return iBackMoneyService.findByWhere(backMoneyDomain);
	}
	@RequestMapping("insert")
	@ResponseBody
	public int insert(@ModelAttribute BackMoneyDomain backMoneyDomain) {
		return iBackMoneyService.insert(backMoneyDomain);
	}
	@RequestMapping("update")
	@ResponseBody
	public int update(@ModelAttribute BackMoneyDomain backMoneyDomain) {
		return iBackMoneyService.update(backMoneyDomain);
	}
	@RequestMapping("delete")
	@ResponseBody
	public int delete(@ModelAttribute BackMoneyDomain backMoneyDomain) {
		backMoneyDomain.setStatus(0);
		return iBackMoneyService.update(backMoneyDomain);
	}
}
