package org.fms.billing.server.webapp.action;

import java.util.List;

import org.fms.billing.common.webapp.domain.PriceStandardDomain;
import org.fms.billing.common.webapp.service.IPriceStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("PriceStandard")
public class PriceStandardController {
	@Autowired
	@Qualifier("priceStandardServiceImpl")
	private IPriceStandardService iPriceStandardService;
	@RequestMapping("select")
	@ResponseBody
	public List<PriceStandardDomain> fingByWhere(@ModelAttribute PriceStandardDomain priceStandardDomain){
		return iPriceStandardService.findByWhere(priceStandardDomain);
	}
	@RequestMapping("insert")
	@ResponseBody
	public int insert(@ModelAttribute PriceStandardDomain priceStandardDomain) {
		return iPriceStandardService.insert(priceStandardDomain);
	}
	@RequestMapping("update")
	@ResponseBody
	public int update(@ModelAttribute PriceStandardDomain priceStandardDomain) {
		return iPriceStandardService.update(priceStandardDomain);
	}
	@RequestMapping("delete")
	@ResponseBody
	public int delete(@ModelAttribute PriceStandardDomain priceStandardDomain) {
		priceStandardDomain.setStatus(0);
		return iPriceStandardService.update(priceStandardDomain);
	}
}
