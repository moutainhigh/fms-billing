package org.fms.billing.server.webapp.action;

import java.util.List;

import org.fms.billing.common.webapp.domain.CosStandardConfigDomain;
import org.fms.billing.common.webapp.service.ICosStandardConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("CosStandardConfig")
public class CosStandardConfigController {
	@Autowired
	@Qualifier("cosStandardConfigServiceImpl")
	private ICosStandardConfigService iCosStandardConfigService;

	@RequestMapping("select")
	@ResponseBody
	public List<CosStandardConfigDomain> fingByWhere(@ModelAttribute CosStandardConfigDomain cosStandardConfigDomain) {
		return iCosStandardConfigService.findByWhere(cosStandardConfigDomain);
	}

	@RequestMapping("insert")
	@ResponseBody
	public int insert(@ModelAttribute CosStandardConfigDomain cosStandardConfigDomain) {
		return iCosStandardConfigService.insert(cosStandardConfigDomain);
	}

	@RequestMapping("update")
	@ResponseBody
	public int update(@ModelAttribute CosStandardConfigDomain cosStandardConfigDomain) {
		return iCosStandardConfigService.update(cosStandardConfigDomain);
	}

	@RequestMapping("delete")
	@ResponseBody
	public int delete(@ModelAttribute CosStandardConfigDomain cosStandardConfigDomain) {
		cosStandardConfigDomain.setStatus(0);
		return iCosStandardConfigService.update(cosStandardConfigDomain);
	}
}
