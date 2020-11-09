package org.fms.billing.common.webapp.domain;

import java.math.BigDecimal;

import org.fms.billing.common.webapp.entity.ManagerParamEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;

//余额
@JsonIgnoreProperties(ignoreUnknown = true)
public class PreChargeDomain extends ManagerParamEntity implements MybatisEntity {
	@TablePrimaryKey
	private Long id;
	// 结算户
	private Long settlementId;
	// 余额
	private BigDecimal balance;
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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
