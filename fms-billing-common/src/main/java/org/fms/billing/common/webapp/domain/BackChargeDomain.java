package org.fms.billing.common.webapp.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.fms.billing.common.webapp.entity.ManagerParamEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;

//抹账记录
@JsonIgnoreProperties(ignoreUnknown = true)
public class BackChargeDomain extends ManagerParamEntity implements MybatisEntity{
	@TablePrimaryKey
	private Long id;
	private Integer mon;
	private Integer sn;
	//应收凭证号
	private String arrearageNo;
	//计量点ID
	private Long meterId;
	//抵扣余额
	private Double deductionBalance;
	//应缴电费（欠费）
	private BigDecimal arrears;
	//实收电费
	private BigDecimal factMoney;
	//实收违约金
	private BigDecimal factPunish;
	//预收电费
	private BigDecimal factPre;
	//收费日期
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date payDate;
	//进帐日期
	private Date inDate;
	//对账标记
	private Integer balanceFlag;
	//缴费方式
	private Integer fChargeMode;
	//缴费帐号
	private Long settlementId;
	//结清标志
	private Integer paidFlag;
	//结算户号
	private String relaUserNo;
	//工作单号
	private String appNo;
	private Long operator;
	private String remark;
	private String flowNo;
	private Integer status;

	//结账标志
	private Integer jzFlag;
	//抹账人
	private Long erasePerson;
	// 抹账原因
	private String eraseReason;

	private Long chargeInfoId;

	private String meterNo;

	private String settlementNo;

	private BigDecimal factTotal;

	private Integer eraseOption;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date dealDate;

	private String operatorName;

	private String erasePersonName;

	private String fChargeModeName;

	private String settlementName;

	public String getSettlementName() {
		return settlementName;
	}

	public void setSettlementName(String settlementName) {
		this.settlementName = settlementName;
	}

	public String getfChargeModeName() {
		return fChargeModeName;
	}

	public void setfChargeModeName(String fChargeModeName) {
		this.fChargeModeName = fChargeModeName;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getErasePersonName() {
		return erasePersonName;
	}

	public void setErasePersonName(String erasePersonName) {
		this.erasePersonName = erasePersonName;
	}

	public Date getDealDate() {
		return dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMon() {
		return mon;
	}

	public void setMon(Integer mon) {
		this.mon = mon;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public String getArrearageNo() {
		return arrearageNo;
	}

	public void setArrearageNo(String arrearageNo) {
		this.arrearageNo = arrearageNo;
	}

	public Long getMeterId() {
		return meterId;
	}

	public void setMeterId(Long meterId) {
		this.meterId = meterId;
	}

	public Double getDeductionBalance() {
		return deductionBalance;
	}

	public void setDeductionBalance(Double deductionBalance) {
		this.deductionBalance = deductionBalance;
	}


	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	public Integer getBalanceFlag() {
		return balanceFlag;
	}

	public void setBalanceFlag(Integer balanceFlag) {
		this.balanceFlag = balanceFlag;
	}

	public Integer getfChargeMode() {
		return fChargeMode;
	}

	public void setfChargeMode(Integer fChargeMode) {
		this.fChargeMode = fChargeMode;
	}

	public Long getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(Long settlementId) {
		this.settlementId = settlementId;
	}

	public Integer getPaidFlag() {
		return paidFlag;
	}

	public void setPaidFlag(Integer paidFlag) {
		this.paidFlag = paidFlag;
	}

	public String getRelaUserNo() {
		return relaUserNo;
	}

	public void setRelaUserNo(String relaUserNo) {
		this.relaUserNo = relaUserNo;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getErasePerson() {
		return erasePerson;
	}

	public void setErasePerson(Long erasePerson) {
		this.erasePerson = erasePerson;
	}

	public String getEraseReason() {
		return eraseReason;
	}

	public void setEraseReason(String eraseReason) {
		this.eraseReason = eraseReason;
	}

	public Long getChargeInfoId() {
		return chargeInfoId;
	}

	public void setChargeInfoId(Long chargeInfoId) {
		this.chargeInfoId = chargeInfoId;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public String getSettlementNo() {
		return settlementNo;
	}

	public void setSettlementNo(String settlementNo) {
		this.settlementNo = settlementNo;
	}

	public BigDecimal getArrears() {
		return arrears;
	}

	public void setArrears(BigDecimal arrears) {
		this.arrears = arrears;
	}

	public BigDecimal getFactMoney() {
		return factMoney;
	}

	public void setFactMoney(BigDecimal factMoney) {
		this.factMoney = factMoney;
	}

	public BigDecimal getFactPunish() {
		return factPunish;
	}

	public void setFactPunish(BigDecimal factPunish) {
		this.factPunish = factPunish;
	}

	public BigDecimal getFactPre() {
		return factPre;
	}

	public void setFactPre(BigDecimal factPre) {
		this.factPre = factPre;
	}

	public BigDecimal getFactTotal() {
		return factTotal;
	}

	public void setFactTotal(BigDecimal factTotal) {
		this.factTotal = factTotal;
	}

	public Integer getEraseOption() {
		return eraseOption;
	}

	public void setEraseOption(Integer eraseOption) {
		this.eraseOption = eraseOption;
	}

	public Integer getJzFlag() {
		return jzFlag;
	}

	public void setJzFlag(Integer jzFlag) {
		this.jzFlag = jzFlag;
	}
}
