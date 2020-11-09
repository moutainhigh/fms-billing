package org.fms.billing.common.webapp.domain;

import java.util.Date;

import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;

//余额记录表
public class PreChargeLogDomain implements MybatisEntity{
	@TablePrimaryKey
	private Long id;
	//结算户
	private Long settlementId;
	//当前值
	private Double currentBalance;
	//变动值
	private Double changeBalance;
	//最新值
	private Double lastBalance;
	private Date createDate;
	private Integer status;
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
	public Double getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}
	public Double getChangeBalance() {
		return changeBalance;
	}
	public void setChangeBalance(Double changeBalance) {
		this.changeBalance = changeBalance;
	}
	public Double getLastBalance() {
		return lastBalance;
	}
	public void setLastBalance(Double lastBalance) {
		this.lastBalance = lastBalance;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
