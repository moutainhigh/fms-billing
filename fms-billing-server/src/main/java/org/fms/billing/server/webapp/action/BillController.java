package org.fms.billing.server.webapp.action;

import java.util.List;

import org.fms.billing.common.webapp.domain.BillDomain;
import org.fms.billing.common.webapp.domain.MeterMoneyDetailsDomain;
import org.fms.billing.common.webapp.service.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("bill")
public class BillController {
	@Autowired
	@Qualifier("billServiceImpl")
	private IBillService iBillService;

	@RequestMapping("select")
	@ResponseBody
	public List<BillDomain> fingByWhere(@ModelAttribute BillDomain billDomain) {
		return iBillService.findByWhere(billDomain);
	}

	@RequestMapping("insert")
	@ResponseBody
	public int insert(@ModelAttribute BillDomain billDomain) {
		return iBillService.insert(billDomain);
	}

	@RequestMapping("update")
	@ResponseBody
	public int update(@ModelAttribute BillDomain billDomain) {
		return iBillService.update(billDomain);
	}

	@RequestMapping("delete")
	@ResponseBody
	public int delete(@ModelAttribute BillDomain billDomain) {
		billDomain.setStatus(0);
		return iBillService.update(billDomain);
	}

	@ResponseBody
	@RequestMapping(params = "method=getMeterMoneyBySettlement")
	public List<MeterMoneyDetailsDomain> getMeterMoneyBySettlement(@RequestBody(required = false) String json) {
		JSONObject jsonObject=JSONObject.parseObject(json);
		String settlementNo=jsonObject.getString("settlementNo");
		String mon=jsonObject.getString("mon");

		List<MeterMoneyDetailsDomain> list =
				iBillService.getMeterMoneyBySettlement(settlementNo,mon);

		return list;
	}

}
