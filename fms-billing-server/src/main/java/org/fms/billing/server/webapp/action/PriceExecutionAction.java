/**
 * Author : czy
 * Date : 2019年6月24日 上午8:49:52
 * Title : com.riozenc.billing.webapp.controller.PriceExecutionAction.java
 *
**/
package org.fms.billing.server.webapp.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.fms.billing.common.webapp.domain.PriceExecutionDomain;
import org.fms.billing.common.webapp.service.IPriceExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.spring.web.client.TitanTemplate;
import com.riozenc.titanTool.spring.web.http.HttpResult;
import com.riozenc.titanTool.spring.web.http.HttpResultPagination;

/**
 * 执行电价
 * 
 * @author czy
 *
 **/

@ControllerAdvice
@RequestMapping("priceExecution")
public class PriceExecutionAction {
	@Autowired
	private TitanTemplate titanTemplate;

	@Autowired
	@Qualifier("priceExecutionServiceImpl")
	private IPriceExecutionService priceExecutionService;

	/**
	 * 获取执行电价标准信息(远程调用 不分页)
	 * 
	 * @return
	 */

	@PostMapping(params = "method=getPriceExecutionInfo")
	@ResponseBody
	public Object getPriceExecutionInfo(@RequestBody PriceExecutionDomain priceExecutionDomain) {
//		PriceExecutionDomain priceExecutionDomain = new PriceExecutionDomain();
		return priceExecutionService.findByNoPage(priceExecutionDomain);
	}

	@RequestMapping("select")
	@ResponseBody
	public HttpResultPagination fingByWhere(@RequestBody(required = false) String priceExecutionJson) {
		PriceExecutionDomain preChargeDomain = GsonUtils.readValue(priceExecutionJson, PriceExecutionDomain.class);
		List<PriceExecutionDomain> priceExecutionActions = priceExecutionService.findByWhere(preChargeDomain);
		return new HttpResultPagination(preChargeDomain, priceExecutionActions);
	}

	@RequestMapping("insert")
	@ResponseBody
	public HttpResult insert(@RequestBody(required = false) String priceExecutionJson) {
		PriceExecutionDomain priceExecutionDomain = GsonUtils.readValue(priceExecutionJson, PriceExecutionDomain.class);

		// 校验相同电价是否存在
		PriceExecutionDomain priceExecutionTest = new PriceExecutionDomain();
		priceExecutionTest.setPriceItemId(priceExecutionDomain.getPriceItemId());
		priceExecutionTest.setPriceTypeId(priceExecutionDomain.getPriceTypeId());
		priceExecutionTest.setTimeSeg(priceExecutionDomain.getTimeSeg());
		List<PriceExecutionDomain> priceExecutionDomains = priceExecutionService.findByWhere(priceExecutionTest);
		if (priceExecutionDomains != null && priceExecutionDomains.size() > 0) {

			return new  HttpResult(HttpResult.ERROR,"添加失败,已存在相同的电价");
		}
		priceExecutionDomain.setCreateDate(new Date());
		priceExecutionDomain.setPriceVersionId(new Long(1));
		priceExecutionDomain.setOperator(priceExecutionDomain.getManagerId());
		try {
			int num = priceExecutionService.insert(priceExecutionDomain);
			return new  HttpResult(HttpResult.SUCCESS,"添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new  HttpResult(HttpResult.ERROR,"添加失败");
		}
	}

	@RequestMapping("update")
	@ResponseBody
	public HttpResult update(@RequestBody(required = false) String priceExecutionJson) {
		PriceExecutionDomain priceExecutionDomain = GsonUtils.readValue(priceExecutionJson, PriceExecutionDomain.class);

		// 校验相同电价是否存在
		PriceExecutionDomain priceExecutionTest = new PriceExecutionDomain();
		priceExecutionTest.setPriceItemId(priceExecutionDomain.getPriceItemId());
		priceExecutionTest.setPriceTypeId(priceExecutionDomain.getPriceTypeId());
		priceExecutionTest.setTimeSeg(priceExecutionDomain.getTimeSeg());
		priceExecutionTest.setPrice(priceExecutionDomain.getPrice());
		List<PriceExecutionDomain> priceExecutionDomains = priceExecutionService.findByWhere(priceExecutionTest);
		if (priceExecutionDomains != null && priceExecutionDomains.size() > 0) {
			return new  HttpResult(HttpResult.ERROR,"更新失败,已存在相同的电价");
		}

		priceExecutionDomain.setOperator(priceExecutionDomain.getManagerId());
		priceExecutionDomain.setCreateDate(new Date());
		int num = priceExecutionService.update(priceExecutionDomain);
		if (num >= 1) {
			return new  HttpResult(HttpResult.SUCCESS,"更新成功");
		} else {
			return new  HttpResult(HttpResult.ERROR,"更新失败");
		}
	}

	@RequestMapping("delete")
	@ResponseBody
	public HttpResult delete(@RequestBody(required = false) String priceExecutionJson) {
		PriceExecutionDomain priceExecutionDomain = GsonUtils.readValue(priceExecutionJson, PriceExecutionDomain.class);
		int num = priceExecutionService.delete(priceExecutionDomain);
		if (num >= 1) {
			return new  HttpResult(HttpResult.SUCCESS,"删除成功");
		} else {
			return new  HttpResult(HttpResult.ERROR,"删除失败");
		}
	}

	@RequestMapping("synCurrentPrice")
	@ResponseBody
	public HttpResult synCurrentPrice(@RequestBody(required = false) String priceExecutionJson) {
		return priceExecutionService.synCurrentPrice();
	}

	@RequestMapping("findMongoPriceExecution")
	@ResponseBody
	public Map<Long, List<PriceExecutionDomain>> findMongoPriceExecution(@RequestBody String mon) {
		return priceExecutionService.findMongoPriceExecution(mon);
	}

    @RequestMapping("findTimeLadderPrice")
    @ResponseBody
    public List<PriceExecutionDomain> findTimeLadderPrice(){
	    return priceExecutionService.findTimeLadderPrice();
    }
}
