/**
 * Author : chizf
 * Date : 2020年6月4日 下午8:28:08
 * Title : com.riozenc.billing.webapp.controller.MoneyPenaltyAction.java
 *
**/
package org.fms.billing.server.webapp.action;

import org.fms.billing.common.webapp.domain.WriteSectDomain;
import org.fms.billing.common.webapp.service.IMoneyPenaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("moneyPenalty")
public class MoneyPenaltyAction {

	@Autowired
	@Qualifier("moneyPenaltyServiceImpl")
	private IMoneyPenaltyService moneyPenaltyService;

	/**
	 * 
	 * 生成违约数据
	 * 
	 * @param writeSectionDomain传入执行的电费月份参数mon
	 */
	public void generatePenaltyData(WriteSectDomain writeSectionDomain) {
		moneyPenaltyService.generatePenaltyData(writeSectionDomain);
	}

	/**
	 * 产生违约金
	 */
	public void generatePenaltyMoney() {
		moneyPenaltyService.generatePenaltyMoney();
	}

}
