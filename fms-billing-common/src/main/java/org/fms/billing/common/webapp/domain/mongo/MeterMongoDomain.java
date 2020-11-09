/**
 * Author : czy
 * Date : 2019年12月3日 下午6:45:50
 * Title : com.riozenc.billing.webapp.domain.mongo.MeterDomain.java
 *
**/
package org.fms.billing.common.webapp.domain.mongo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MeterMongoDomain {

	private Long id;
	private String mon;
	private Byte sn;
	private Long userId;
	private String userNo;
	private String userName;
	private String setAddress;
	private String meterNo;
	private String meterName;
	private Long writeSectionId;
	private String writeSectNo;
	private String writeSectName;
	private Byte meterType;
	private Integer voltLevelType;
	private Byte tsType;
	private Byte msType;
	private Byte baseMoneyFlag;
	private Long businessPlaceCode;
	private Byte cosType;
	private Byte countTimes;
	private Integer elecTypeCode;
	private Byte lineLostType;
	private Byte transLostType;
	private Long priceType;
	private BigDecimal needIndex;
	private Long writorId;
	private Date createDate;
	private Byte status;

	private MeterMoneyMongoDomain meterMoneyDomain;

	private BigDecimal totalPower;
	private BigDecimal amount;
	//是否算费了
	private Integer isCal;

	private String computeLog;

	private List<Long> ids;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	public Byte getSn() {
		return sn;
	}

	public void setSn(Byte sn) {
		this.sn = sn;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSetAddress() {
		return setAddress;
	}

	public void setSetAddress(String setAddress) {
		this.setAddress = setAddress;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public String getMeterName() {
		return meterName;
	}

	public void setMeterName(String meterName) {
		this.meterName = meterName;
	}

	public Long getWriteSectionId() {
		return writeSectionId;
	}

	public void setWriteSectionId(Long writeSectionId) {
		this.writeSectionId = writeSectionId;
	}

	public String getWriteSectNo() {
		return writeSectNo;
	}

	public void setWriteSectNo(String writeSectNo) {
		this.writeSectNo = writeSectNo;
	}

	public String getWriteSectName() {
		return writeSectName;
	}

	public void setWriteSectName(String writeSectName) {
		this.writeSectName = writeSectName;
	}

	public Byte getMeterType() {
		return meterType;
	}

	public void setMeterType(Byte meterType) {
		this.meterType = meterType;
	}

	public Integer getVoltLevelType() {
		return voltLevelType;
	}

	public void setVoltLevelType(Integer voltLevelType) {
		this.voltLevelType = voltLevelType;
	}

	public Byte getTsType() {
		return tsType;
	}

	public void setTsType(Byte tsType) {
		this.tsType = tsType;
	}

	public Byte getMsType() {
		return msType;
	}

	public void setMsType(Byte msType) {
		this.msType = msType;
	}

	public Byte getBaseMoneyFlag() {
		return baseMoneyFlag;
	}

	public void setBaseMoneyFlag(Byte baseMoneyFlag) {
		this.baseMoneyFlag = baseMoneyFlag;
	}

	public Long getBusinessPlaceCode() {
		return businessPlaceCode;
	}

	public void setBusinessPlaceCode(Long businessPlaceCode) {
		this.businessPlaceCode = businessPlaceCode;
	}

	public Byte getCosType() {
		return cosType;
	}

	public void setCosType(Byte cosType) {
		this.cosType = cosType;
	}

	public Byte getCountTimes() {
		return countTimes;
	}

	public void setCountTimes(Byte countTimes) {
		this.countTimes = countTimes;
	}

	public Integer getElecTypeCode() {
		return elecTypeCode;
	}

	public void setElecTypeCode(Integer elecTypeCode) {
		this.elecTypeCode = elecTypeCode;
	}

	public Byte getLineLostType() {
		return lineLostType;
	}

	public void setLineLostType(Byte lineLostType) {
		this.lineLostType = lineLostType;
	}

	public Byte getTransLostType() {
		return transLostType;
	}

	public void setTransLostType(Byte transLostType) {
		this.transLostType = transLostType;
	}

	public Long getPriceType() {
		return priceType;
	}

	public void setPriceType(Long priceType) {
		this.priceType = priceType;
	}

	public BigDecimal getNeedIndex() {
		return needIndex;
	}

	public void setNeedIndex(BigDecimal needIndex) {
		this.needIndex = needIndex;
	}

	public Long getWritorId() {
		return writorId;
	}

	public void setWritorId(Long writorId) {
		this.writorId = writorId;
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

	public MeterMoneyMongoDomain getMeterMoneyDomain() {
		return meterMoneyDomain;
	}

	public void setMeterMoneyDomain(MeterMoneyMongoDomain meterMoneyDomain) {
		this.meterMoneyDomain = meterMoneyDomain;
	}

	public BigDecimal getTotalPower() {
		return totalPower;
	}

	public void setTotalPower(BigDecimal totalPower) {
		this.totalPower = totalPower;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getIsCal() {
		return isCal;
	}

	public void setIsCal(Integer isCal) {
		this.isCal = isCal;
	}

	public String getComputeLog() {
		return computeLog;
	}

	public void setComputeLog(String computeLog) {
		this.computeLog = computeLog;
	}
}
