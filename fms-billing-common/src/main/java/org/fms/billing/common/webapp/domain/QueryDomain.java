package org.fms.billing.common.webapp.domain;

public class QueryDomain {
	private String printType;
	private String selectData;
	private String customerNo;
	private String writeDate;
	public String getPrintType() {
		return printType;
	}
	public void setPrintType(String printType) {
		this.printType = printType;
	}
	public String getSelectData() {
		return selectData;
	}
	public void setSelectData(String selectData) {
		this.selectData = selectData;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
}
