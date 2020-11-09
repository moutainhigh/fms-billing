package org.fms.billing.common.webapp.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;


//收费记录
public class ChargeDomain implements MybatisEntity{
	@TablePrimaryKey
	private Long id;
	private Integer mon;
	private Integer sn;
	private Integer ysTypeCode;
	//应收凭证号
	private String arrearageNo;
	//计量点ID
	private Long meterId;
	//抵扣余额
	private BigDecimal deductionBalance;
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

	private BigDecimal factTotal;

	private Long meterMoneyId;

	private Byte chargeMode;

	private Long businessPlaceCode;

	private List<Long> settlementIds;

	private Integer startMon;

	private Integer endMon;

	private BigDecimal shouldMoney;

	private List<Long> meterIds;

	private Long writeSectId;

	public Long getWriteSectId() {
		return writeSectId;
	}

	public void setWriteSectId(Long writeSectId) {
		this.writeSectId = writeSectId;
	}

	public List<Long> getMeterIds() {
		return meterIds;
	}

	public void setMeterIds(List<Long> meterIds) {
		this.meterIds = meterIds;
	}

	public BigDecimal getShouldMoney() {
		return shouldMoney;
	}

	public void setShouldMoney(BigDecimal shouldMoney) {
		this.shouldMoney = shouldMoney;
	}

	public List<Long> getSettlementIds() {
		return settlementIds;
	}

	public void setSettlementIds(List<Long> settlementIds) {
		this.settlementIds = settlementIds;
	}

	public Integer getYsTypeCode() {
		return ysTypeCode;
	}

	public void setYsTypeCode(Integer ysTypeCode) {
		this.ysTypeCode = ysTypeCode;
	}

	public Long getBusinessPlaceCode() {
		return businessPlaceCode;
	}

	public void setBusinessPlaceCode(Long businessPlaceCode) {
		this.businessPlaceCode = businessPlaceCode;
	}

	public Byte getChargeMode() {
		return chargeMode;
	}

	public void setChargeMode(Byte chargeMode) {
		this.chargeMode = chargeMode;
	}

	public Long getMeterMoneyId() {
		return meterMoneyId;
	}

	public void setMeterMoneyId(Long meterMoneyId) {
		this.meterMoneyId = meterMoneyId;
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

	public BigDecimal getDeductionBalance() {
		return deductionBalance;
	}

	public void setDeductionBalance(BigDecimal deductionBalance) {
		this.deductionBalance = deductionBalance;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public Integer getJzFlag() {
		return jzFlag;
	}

	public void setJzFlag(Integer jzFlag) {
		this.jzFlag = jzFlag;
	}

	public Integer getStartMon() {
		return startMon;
	}

	public void setStartMon(Integer startMon) {
		this.startMon = startMon;
	}

	public Integer getEndMon() {
		return endMon;
	}

	public void setEndMon(Integer endMon) {
		this.endMon = endMon;
	}
}
