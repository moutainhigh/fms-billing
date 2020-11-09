/**
 * Author : chizf
 * Date : 2020年7月2日 下午7:09:28
 * Title : com.riozenc.billing.webapp.domain.MeterPenaltyDomain.java
 *
**/
package org.fms.billing.common.webapp.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;


public class MeterPenaltyDomain implements MybatisEntity {
	@TablePrimaryKey
	private Long id;//
	private Long meterId;//
	private Long arrearageId;//
	private BigDecimal arrears;//
	private BigDecimal penaltyMoney;//
	private Date createDate;//
	private String remark;//
	private Byte status;//

	private Byte isSettle; // IS_SETTLE
	private BigDecimal oweMoney;// OWE_MONEY

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMeterId() {
		return meterId;
	}

	public void setMeterId(Long meterId) {
		this.meterId = meterId;
	}

	public Long getArrearageId() {
		return arrearageId;
	}

	public void setArrearageId(Long arrearageId) {
		this.arrearageId = arrearageId;
	}

	public BigDecimal getArrears() {
		return arrears;
	}

	public void setArrears(BigDecimal arrears) {
		this.arrears = arrears;
	}

	public BigDecimal getPenaltyMoney() {
		return penaltyMoney;
	}

	public void setPenaltyMoney(BigDecimal penaltyMoney) {
		this.penaltyMoney = penaltyMoney;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Byte getIsSettle() {
		return isSettle;
	}

	public void setIsSettle(Byte isSettle) {
		this.isSettle = isSettle;
	}

	public BigDecimal getOweMoney() {
		return oweMoney;
	}

	public void setOweMoney(BigDecimal oweMoney) {
		this.oweMoney = oweMoney;
	}
}
