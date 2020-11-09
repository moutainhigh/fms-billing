/**
 * Author : czy
 * Date : 2019年12月6日 下午1:49:30
 * Title : com.riozenc.billing.webapp.domain.ReceivableFallbackDomain.java
 *
**/
package org.fms.billing.common.webapp.domain;

import java.util.List;

public class ReceivableFallbackDomain {
	private String mon;
	private List<Long> meterIds;
	private String backText;

	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	public List<Long> getMeterIds() {
		return meterIds;
	}

	public void setMeterIds(List<Long> meterIds) {
		this.meterIds = meterIds;
	}

	public String getBackText() {
		return backText;
	}

	public void setBackText(String backText) {
		this.backText = backText;
	}

}
