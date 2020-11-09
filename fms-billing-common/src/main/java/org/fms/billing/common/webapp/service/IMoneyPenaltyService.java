/**
 * Author : chizf
 * Date : 2020年6月5日 下午3:36:33
 * Title : com.riozenc.billing.webapp.service.IMoneyPenaltyService.java
 *
**/
package org.fms.billing.common.webapp.service;

import org.fms.billing.common.webapp.domain.MeterMoneyPenaltyDataDomain;
import org.fms.billing.common.webapp.domain.WriteSectDomain;

import com.riozenc.titanTool.spring.webapp.service.BaseService;

public interface IMoneyPenaltyService extends BaseService<MeterMoneyPenaltyDataDomain> {

	public void generatePenaltyData(WriteSectDomain writeSectionDomain);
	
	public int generatePenaltyMoney();

}
