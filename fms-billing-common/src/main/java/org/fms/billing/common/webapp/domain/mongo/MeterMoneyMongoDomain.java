/**
 * Author : czy
 * Date : 2019年12月3日 下午6:56:32
 * Title : com.riozenc.billing.webapp.domain.mongo.MeterMoneyDomain.java
 *
**/
package org.fms.billing.common.webapp.domain.mongo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.fms.billing.common.webapp.domain.MeterDomain;

public class MeterMoneyMongoDomain {
	private Long meterId;// 计量点ID METER_ID bigint FALSE FALSE FALSE
	private String meterNo;
	private Integer mon;
	private Byte sn;
	private BigDecimal activeWritePower;
	private BigDecimal activeWritePower1;
	private BigDecimal activeWritePower2;
	private BigDecimal activeWritePower3;
	private BigDecimal activeWritePower4;

	private BigDecimal reactiveWritePower;
	private BigDecimal reverseReactiveWritePower;

	private BigDecimal activeDeductionPower = BigDecimal.ZERO;

	private BigDecimal powerRateMoney;
	private BigDecimal basicMoney;

	private BigDecimal calCapacity;

	private BigDecimal chgPower;// 有功换表电量 CHG_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
	private BigDecimal qChgPower;// 无功换表电量 Q_CHG_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
	private BigDecimal addPower;// 有功增减电量 ADD_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
	private BigDecimal qAddPower;// 无功增减电量 Q_ADD_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
	private BigDecimal activeLineLossPower = BigDecimal.ZERO;// 有功线损电量 LINE_LOSS_POWER decimal(12,2) 12 2 FALSE FALSE
																// FALSE
	private BigDecimal reactiveLineLossPower;// 无功线损电量 Q_LINE_LOSS_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
	private BigDecimal activeTransformerLossPower;// 有功变损电量 TRANS_LOSS_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
	private BigDecimal reactiveTransformerLossPower;// 无功变损电量 Q_TRANS_LOSS_POWER decimal(12,2) 12 2 FALSE FALSE FALSE

	private BigDecimal surcharges;

	private BigDecimal totalPower;// 有功总电量 TOTAL_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
	private BigDecimal qTotalPower;// 无功总电量 Q_TOTAL_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
	private BigDecimal volumeCharge;
	private BigDecimal amount;

	private Long writeSectId;
	private String computeLog;
	private Date createDate;

	private Byte msType;//

	private Long priceTypeId;

	/**
	 * 目录电价
	 */
	private BigDecimal addMoney1;

	/**
	 * 国家水利工程建设基金
	 */
	private BigDecimal addMoney2;

	/**
	 * 城市公用事业附加费
	 */
	private BigDecimal addMoney3;

	/**
	 * 大中型水库移民后期扶持资金
	 */
	private BigDecimal addMoney4;

	/**
	 * 地方水库移民后期扶持资金
	 */
	private BigDecimal addMoney5;

	/**
	 * 可再生能源电价附加
	 */
	private BigDecimal addMoney6;

	/**
	 * 农网还贷资金
	 */
	private BigDecimal addMoney7;

	/**
	 * 农村电网维护费
	 */
	private BigDecimal addMoney8;

	/**
	 * 价格调节基金
	 */
	private BigDecimal addMoney9;

	private BigDecimal addMoney10;

    private List<WriteFilesMongoDomain> meterDataInfo;// 抄表信息
    private TransformerCalculateMongoDomain transformerCalculateInfo;
	private Map<String, BigDecimal> surchargeDetail;
    private List<LadderMongoDomain> ladderDataModels;// 阶梯数据

	List<MeterDomain> meterDomains;

	public List<MeterDomain> getMeterDomains() {
		return meterDomains;
	}

	public void setMeterDomains(List<MeterDomain> meterDomains) {
		this.meterDomains = meterDomains;
	}

	public Long getPriceTypeId() {
		return priceTypeId;
	}

	public void setPriceTypeId(Long priceTypeId) {
		this.priceTypeId = priceTypeId;
	}

	public BigDecimal getAddMoney1() {
		return addMoney1;
	}

	public void setAddMoney1(BigDecimal addMoney1) {
		this.addMoney1 = addMoney1;
	}

	public BigDecimal getAddMoney2() {
		return addMoney2;
	}

	public void setAddMoney2(BigDecimal addMoney2) {
		this.addMoney2 = addMoney2;
	}

	public BigDecimal getAddMoney3() {
		return addMoney3;
	}

	public void setAddMoney3(BigDecimal addMoney3) {
		this.addMoney3 = addMoney3;
	}

	public BigDecimal getAddMoney4() {
		return addMoney4;
	}

	public void setAddMoney4(BigDecimal addMoney4) {
		this.addMoney4 = addMoney4;
	}

	public BigDecimal getAddMoney5() {
		return addMoney5;
	}

	public void setAddMoney5(BigDecimal addMoney5) {
		this.addMoney5 = addMoney5;
	}

	public BigDecimal getAddMoney6() {
		return addMoney6;
	}

	public void setAddMoney6(BigDecimal addMoney6) {
		this.addMoney6 = addMoney6;
	}

	public BigDecimal getAddMoney7() {
		return addMoney7;
	}

	public void setAddMoney7(BigDecimal addMoney7) {
		this.addMoney7 = addMoney7;
	}

	public BigDecimal getAddMoney8() {
		return addMoney8;
	}

	public void setAddMoney8(BigDecimal addMoney8) {
		this.addMoney8 = addMoney8;
	}

	public BigDecimal getAddMoney9() {
		return addMoney9;
	}

	public void setAddMoney9(BigDecimal addMoney9) {
		this.addMoney9 = addMoney9;
	}

	public BigDecimal getAddMoney10() {
		return addMoney10;
	}

	public void setAddMoney10(BigDecimal addMoney10) {
		this.addMoney10 = addMoney10;
	}

	public Long getMeterId() {
		return meterId;
	}

	public void setMeterId(Long meterId) {
		this.meterId = meterId;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public Integer getMon() {
		return mon;
	}

	public void setMon(Integer mon) {
		this.mon = mon;
	}

	public Byte getSn() {
		return sn;
	}

	public void setSn(Byte sn) {
		this.sn = sn;
	}

	public BigDecimal getActiveWritePower() {
		return activeWritePower;
	}

	public void setActiveWritePower(BigDecimal activeWritePower) {
		this.activeWritePower = activeWritePower;
	}

	public BigDecimal getActiveWritePower1() {
		return activeWritePower1;
	}

	public void setActiveWritePower1(BigDecimal activeWritePower1) {
		this.activeWritePower1 = activeWritePower1;
	}

	public BigDecimal getActiveWritePower2() {
		return activeWritePower2;
	}

	public void setActiveWritePower2(BigDecimal activeWritePower2) {
		this.activeWritePower2 = activeWritePower2;
	}

	public BigDecimal getActiveWritePower3() {
		return activeWritePower3;
	}

	public void setActiveWritePower3(BigDecimal activeWritePower3) {
		this.activeWritePower3 = activeWritePower3;
	}

	public BigDecimal getActiveWritePower4() {
		return activeWritePower4;
	}

	public void setActiveWritePower4(BigDecimal activeWritePower4) {
		this.activeWritePower4 = activeWritePower4;
	}

	public BigDecimal getReactiveWritePower() {
		return reactiveWritePower;
	}

	public void setReactiveWritePower(BigDecimal reactiveWritePower) {
		this.reactiveWritePower = reactiveWritePower;
	}

	public BigDecimal getReverseReactiveWritePower() {
		return reverseReactiveWritePower;
	}

	public void setReverseReactiveWritePower(BigDecimal reverseReactiveWritePower) {
		this.reverseReactiveWritePower = reverseReactiveWritePower;
	}

	public BigDecimal getChgPower() {
		return chgPower;
	}

	public void setChgPower(BigDecimal chgPower) {
		this.chgPower = chgPower;
	}

	public BigDecimal getActiveDeductionPower() {
		return activeDeductionPower;
	}

	public void setActiveDeductionPower(BigDecimal activeDeductionPower) {
		this.activeDeductionPower = activeDeductionPower;
	}

	public BigDecimal getAddPower() {
		return addPower;
	}

	public void setAddPower(BigDecimal addPower) {
		this.addPower = addPower;
	}

	public BigDecimal getPowerRateMoney() {
		return powerRateMoney;
	}

	public void setPowerRateMoney(BigDecimal powerRateMoney) {
		this.powerRateMoney = powerRateMoney;
	}

	public BigDecimal getBasicMoney() {
		return basicMoney;
	}

	public void setBasicMoney(BigDecimal basicMoney) {
		this.basicMoney = basicMoney;
	}

	public BigDecimal getCalCapacity() {
		return calCapacity;
	}

	public void setCalCapacity(BigDecimal calCapacity) {
		this.calCapacity = calCapacity;
	}

	public BigDecimal getqTotalPower() {
		return qTotalPower;
	}

	public void setqTotalPower(BigDecimal qTotalPower) {
		this.qTotalPower = qTotalPower;
	}

	public BigDecimal getSurcharges() {
		return surcharges;
	}

	public void setSurcharges(BigDecimal surcharges) {
		this.surcharges = surcharges;
	}

	public Map<String, BigDecimal> getSurchargeDetail() {
		return surchargeDetail;
	}

	public void setSurchargeDetail(Map<String, BigDecimal> surchargeDetail) {
		this.surchargeDetail = surchargeDetail;
	}

	public BigDecimal getTotalPower() {
		return totalPower;
	}

	public void setTotalPower(BigDecimal totalPower) {
		this.totalPower = totalPower;
	}

	public BigDecimal getVolumeCharge() {
		return volumeCharge;
	}

	public void setVolumeCharge(BigDecimal volumeCharge) {
		this.volumeCharge = volumeCharge;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getWriteSectId() {
		return writeSectId;
	}

	public void setWriteSectId(Long writeSectId) {
		this.writeSectId = writeSectId;
	}

	public String getComputeLog() {
		return computeLog;
	}

	public void setComputeLog(String computeLog) {
		this.computeLog = computeLog;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<WriteFilesMongoDomain> getMeterDataInfo() {
		return meterDataInfo;
	}

	public void setMeterDataInfo(List<WriteFilesMongoDomain> meterDataInfo) {
		this.meterDataInfo = meterDataInfo;
	}

	public BigDecimal getqChgPower() {
		return qChgPower;
	}

	public void setqChgPower(BigDecimal qChgPower) {
		this.qChgPower = qChgPower;
	}

	public BigDecimal getqAddPower() {
		return qAddPower;
	}

	public void setqAddPower(BigDecimal qAddPower) {
		this.qAddPower = qAddPower;
	}

	public BigDecimal getActiveTransformerLossPower() {
		return activeTransformerLossPower;
	}

	public void setActiveTransformerLossPower(BigDecimal activeTransformerLossPower) {
		this.activeTransformerLossPower = activeTransformerLossPower;
	}

	public BigDecimal getReactiveTransformerLossPower() {
		return reactiveTransformerLossPower;
	}

	public void setReactiveTransformerLossPower(BigDecimal reactiveTransformerLossPower) {
		this.reactiveTransformerLossPower = reactiveTransformerLossPower;
	}

	public TransformerCalculateMongoDomain getTransformerCalculateInfo() {
		return transformerCalculateInfo;
	}

	public void setTransformerCalculateInfo(TransformerCalculateMongoDomain transformerCalculateInfo) {
		this.transformerCalculateInfo = transformerCalculateInfo;
	}

	public Byte getMsType() {
		return msType;
	}

	public void setMsType(Byte msType) {
		this.msType = msType;
	}

	public List<LadderMongoDomain> getLadderDataModels() {
		return ladderDataModels;
	}

	public void setLadderDataModels(List<LadderMongoDomain> ladderDataModels) {
		this.ladderDataModels = ladderDataModels;
	}

	public BigDecimal getActiveLineLossPower() {
		return activeLineLossPower;
	}

	public void setActiveLineLossPower(BigDecimal activeLineLossPower) {
		this.activeLineLossPower = activeLineLossPower;
	}

	public BigDecimal getReactiveLineLossPower() {
		return reactiveLineLossPower;
	}

	public void setReactiveLineLossPower(BigDecimal reactiveLineLossPower) {
		this.reactiveLineLossPower = reactiveLineLossPower;
	}

}
