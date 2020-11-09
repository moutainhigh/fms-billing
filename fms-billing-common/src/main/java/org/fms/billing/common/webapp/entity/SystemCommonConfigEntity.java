/**
 *    Auth:riozenc
 *    Date:2019年3月8日 下午3:22:58
 *    Title:com.riozenc.cim.webapp.domain.CustomerDomain.java
 **/
package org.fms.billing.common.webapp.entity;

/**
 * 系统中用到的下拉	SYSTEM_COMMON_CONFIG
 * 
 * @author 
 *
 */
public class SystemCommonConfigEntity {

	private Long id;// ID ID bigint TRUE FALSE TRUE
	private String type;
	private Long paramKey; //名称
	private String paramValue; 
	private Byte status;
	private String paramName;
	private String paramOrder;
	private String remark1;
	private String remark2;
	private String remark3;
	private String remark4;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getParamKey() {
		return paramKey;
	}

	public void setParamKey(Long paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamOrder() {
		return paramOrder;
	}

	public void setParamOrder(String paramOrder) {
		this.paramOrder = paramOrder;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public String getRemark4() {
		return remark4;
	}

	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}
}
