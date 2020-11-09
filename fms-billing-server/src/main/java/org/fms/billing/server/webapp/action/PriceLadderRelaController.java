package org.fms.billing.server.webapp.action;

import java.util.Date;
import java.util.List;

import org.fms.billing.common.webapp.domain.PriceLadderRelaDomain;
import org.fms.billing.common.webapp.service.IPriceLadderRelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.titanTool.spring.web.http.HttpResult;
import com.riozenc.titanTool.spring.web.http.HttpResultPagination;

@Controller
@RequestMapping("priceLadderRela")
public class PriceLadderRelaController {
	@Autowired
	@Qualifier("priceLadderRelaImpl")
	private IPriceLadderRelaService iPriceLadderRelaService;

	// 查询阶梯
	@RequestMapping("get")
	@ResponseBody
	public Object get(@RequestBody PriceLadderRelaDomain priceLadderRelaDomain) {
		List<PriceLadderRelaDomain> priceLadderRelaDomains = iPriceLadderRelaService.findByWhere(priceLadderRelaDomain);
		return new HttpResultPagination<PriceLadderRelaDomain>(priceLadderRelaDomain, priceLadderRelaDomains);
	}

	// 插入阶梯
	@RequestMapping("insert")
	@ResponseBody
	public HttpResult insert(@RequestBody PriceLadderRelaDomain priceLadderRelaDomain) {
		// 检查是否含有重复值
		PriceLadderRelaDomain testPriceLadderRela = new PriceLadderRelaDomain();
		testPriceLadderRela.setPriceExecutionId(priceLadderRelaDomain.getPriceExecutionId());
		testPriceLadderRela.setLadderSn(priceLadderRelaDomain.getLadderSn());
		List<PriceLadderRelaDomain> priceLadderRelaDomains = iPriceLadderRelaService.findByWhere(testPriceLadderRela);
		if (null == priceLadderRelaDomains || priceLadderRelaDomains.size() > 0) {
			return new HttpResult(HttpResult.ERROR, "新增阶梯电价失败:含有电价与阶梯相同的记录");
		}
		priceLadderRelaDomain.setCreateDate(new Date());
		int num = iPriceLadderRelaService.insert(priceLadderRelaDomain);
		if (num > 0) {

			return new HttpResult(HttpResult.SUCCESS, iPriceLadderRelaService.findByWhere(priceLadderRelaDomain));
		}
		return new HttpResult(HttpResult.ERROR, "新增阶梯电价失败");

	}

	// 更新阶梯
	@RequestMapping("update")
	@ResponseBody
	public HttpResult update(@RequestBody PriceLadderRelaDomain priceLadderRelaDomain) {
		// 检查是否含有重复值
		priceLadderRelaDomain.setCreateDate(new Date());
		int num = iPriceLadderRelaService.update(priceLadderRelaDomain);
		if (num > 0) {

			return new HttpResult(HttpResult.SUCCESS, iPriceLadderRelaService.findByWhere(priceLadderRelaDomain));
		}
		return new HttpResult(HttpResult.ERROR, "更新阶梯电价失败");

	}

	// 同步阶梯电价到mongodb
	@RequestMapping("synCurrentPrice")
	@ResponseBody
	public HttpResult synCurrentPrice(@RequestBody(required = false) String priceExecutionJson) {
		return iPriceLadderRelaService.synCurrentPrice();
	}

	@RequestMapping("findMongoPriceLadderRela")
	@ResponseBody
	public List<PriceLadderRelaDomain> findMongoPriceLadderRela(@RequestBody String mon) {
		return iPriceLadderRelaService.findMongoPriceLadderRela(mon);
	}
}
