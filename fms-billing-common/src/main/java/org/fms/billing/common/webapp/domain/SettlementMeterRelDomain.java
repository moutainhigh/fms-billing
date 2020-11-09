/**
 * Author : czy
 * Date : 2019年12月30日 下午7:11:33
 * Title : com.riozenc.billing.webapp.domain.SettlementMeterRelDomain.java
 *
**/
package org.fms.billing.common.webapp.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SettlementMeterRelDomain {
	private Long id;// ID ID bigint TRUE FALSE TRUE
	private Long settlementId; // 结算户ID SETTLEMENT_ID bigint
	private Long meterId; // 计量点ID METER_ID bigint
	private String meterName; // 计量点名称
	private String meterNo; // 计量点编号
	private Byte deductionOrder; // 扣减顺序 DEDUCTION_ORDER int
	private Date createDate;// 创建时间 CREATE_DATE datetime FALSE FALSE FALSE
	private String remark;// 备注 REMARK varchar(256) 256 FALSE FALSE FALSE
	private Byte status;// 状态 STATUS smallint FALSE FALSE FALSE
	private Byte mpType; // 计量点类型 1电表 2水表 3燃气表 4住户
	private Long customerId;
	private BigDecimal balance;// 锁定余额

	@Transient
	private BigDecimal writeSn;

	public BigDecimal getWriteSn() {
		return writeSn;
	}

	public void setWriteSn(BigDecimal writeSn) {
		this.writeSn = writeSn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(Long settlementId) {
		this.settlementId = settlementId;
	}

	public Long getMeterId() {
		return meterId;
	}

	public void setMeterId(Long meterId) {
		this.meterId = meterId;
	}

	public String getMeterName() {
		return meterName;
	}

	public void setMeterName(String meterName) {
		this.meterName = meterName;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public Byte getDeductionOrder() {
		return deductionOrder;
	}

	public void setDeductionOrder(Byte deductionOrder) {
		this.deductionOrder = deductionOrder;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getMpType() {
		return mpType;
	}

	public void setMpType(Byte mpType) {
		this.mpType = mpType;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
