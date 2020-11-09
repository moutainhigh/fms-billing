/**
 * Author : czy
 * Date : 2019年7月8日 下午4:10:35
 * Title : com.riozenc.billing.webapp.domain.MeterDomain.java
 *
**/
package org.fms.billing.common.webapp.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.riozenc.titanTool.annotation.TablePrimaryKey;

public class MeterUserRelationEntity extends ManagerParamEntity{
	@TablePrimaryKey
	private Long id;// ID ID bigint TRUE FALSE TRUE
	private String meterNo; // 计量点号
	private String meterName; // 计量点名称
	private Long meterAssetsId; // 电表资产ID
	private String setAddress; // 安装地点
	private Long ctAssetsId; // CT资产号
	private Long ptAssetsId; // PT资产号
	private String ctValue; // CT变比
	private String ptValue; // PT变比
	private Long priceType; // 电价
	private Integer needIndex; // 需量定值
	private Byte voltLevelType; // 计量点电压
	private Byte meterType; // 计量点类别
	private Byte meterClassType; // 计量点分类
	private Byte msType; // 计量方式
	private Integer elecTypeCode; // 用电类别
	private Byte baseMoneyFlag; // 基本电费计算方法
	private Byte cosType; // 力率标准
	private Long tradeType; // 行业用电分类
	private Byte tsType; // 分时计费标准
	private Byte transLostType; // 变损分摊方式
	private BigDecimal transLostNum; // 变损率or变损值
	private BigDecimal qTransLostNum; // 无功变损率or值
	private Byte lineLostType; // 线损计算方法
	private BigDecimal lineLostNum; // 线损率or线损值
	private BigDecimal qLineLostNum; // 无功线损率or值
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate; // 创建时间
	private String remark; // 备注
	private Byte status; // 状态
	private Long tgId; // 所属台区 TG_ID bigint
	private Long lineId; // 所属线路 LINE_ID bigint
	private Long subsId; // 所属厂站 SUBS_ID bigint
	private Long userId; // 所属用户 USER_ID bigint
	private Long transformerId; // 所属变压器 TRANSFORMER_ID bigint
	private Long settlementId; // 所属结算户 SETTLEMENT_ID bigint
	private Long writeSectionId; // 所属抄表段 WRITE_SECTION_ID bigint
	private Byte keepPowerFlag; // 保电标识 KEEP_POWER_FLAG smallint
	private Byte demolitionFlag; // 扒房标识 DEMOLITION_FLAG smallint
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date demolitionDate; // 扒房标识日期 DEMOLITION_DATE datetime
	private Byte billFlag; // 发票打印标识 BILL_FLAG smallint
	private Byte overdueFineFlag; // 滞纳金标识 OVERDUE_FINE_FLAG smallint
	private Byte tieredPriceFlag; // 阶梯电价标识 TIERED_PRICE_FLAG smallint
	private Byte writeMethod; // 抄表方式 WRITE_METHOD
	private Byte rateFlag; // 时段 RATE_FLAG
	/*--------------------------以下为非数据库字段-----------------------------------------*/
	private String transformerNo; // 变压器编号，用于接收业扩返回的信息，不存库

	private String userNo; // 用户户号
	private String userName;// 用户名称

	private String tgNo; // 用户户号
	private String tgName;// 用户名称

	private String writeSectNo; // 用户户号
	private String writeSectName;// 用户名称

	private Integer mon;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getMeterAssetsId() {
		return meterAssetsId;
	}

	public void setMeterAssetsId(Long meterAssetsId) {
		this.meterAssetsId = meterAssetsId;
	}

	public String getSetAddress() {
		return setAddress;
	}

	public void setSetAddress(String setAddress) {
		this.setAddress = setAddress;
	}

	public Long getCtAssetsId() {
		return ctAssetsId;
	}

	public void setCtAssetsId(Long ctAssetsId) {
		this.ctAssetsId = ctAssetsId;
	}

	public Long getPtAssetsId() {
		return ptAssetsId;
	}

	public void setPtAssetsId(Long ptAssetsId) {
		this.ptAssetsId = ptAssetsId;
	}

	public String getCtValue() {
		return ctValue;
	}

	public void setCtValue(String ctValue) {
		this.ctValue = ctValue;
	}

	public String getPtValue() {
		return ptValue;
	}

	public void setPtValue(String ptValue) {
		this.ptValue = ptValue;
	}

	public Long getPriceType() {
		return priceType;
	}

	public void setPriceType(Long priceType) {
		this.priceType = priceType;
	}

	public void setTradeType(Long tradeType) {
		this.tradeType = tradeType;
	}

	public Integer getNeedIndex() {
		return needIndex;
	}

	public void setNeedIndex(Integer needIndex) {
		this.needIndex = needIndex;
	}

	public Byte getVoltLevelType() {
		return voltLevelType;
	}

	public void setVoltLevelType(Byte voltLevelType) {
		this.voltLevelType = voltLevelType;
	}

	public Byte getMeterType() {
		return meterType;
	}

	public void setMeterType(Byte meterType) {
		this.meterType = meterType;
	}

	public Byte getMeterClassType() {
		return meterClassType;
	}

	public void setMeterClassType(Byte meterClassType) {
		this.meterClassType = meterClassType;
	}

	public Byte getMsType() {
		return msType;
	}

	public void setMsType(Byte msType) {
		this.msType = msType;
	}

	public Integer getElecTypeCode() {
		return elecTypeCode;
	}

	public void setElecTypeCode(Integer elecTypeCode) {
		this.elecTypeCode = elecTypeCode;
	}

	public Byte getBaseMoneyFlag() {
		return baseMoneyFlag;
	}

	public void setBaseMoneyFlag(Byte baseMoneyFlag) {
		this.baseMoneyFlag = baseMoneyFlag;
	}

	public Byte getCosType() {
		return cosType;
	}

	public void setCosType(Byte cosType) {
		this.cosType = cosType;
	}

	public Long getTradeType() {
		return tradeType;
	}

	public Byte getTsType() {
		return tsType;
	}

	public void setTsType(Byte tsType) {
		this.tsType = tsType;
	}

	public Byte getTransLostType() {
		return transLostType;
	}

	public void setTransLostType(Byte transLostType) {
		this.transLostType = transLostType;
	}

	public BigDecimal getTransLostNum() {
		return transLostNum;
	}

	public void setTransLostNum(BigDecimal transLostNum) {
		this.transLostNum = transLostNum;
	}

	public BigDecimal getqTransLostNum() {
		return qTransLostNum;
	}

	public void setqTransLostNum(BigDecimal qTransLostNum) {
		this.qTransLostNum = qTransLostNum;
	}

	public Byte getLineLostType() {
		return lineLostType;
	}

	public void setLineLostType(Byte lineLostType) {
		this.lineLostType = lineLostType;
	}

	public BigDecimal getLineLostNum() {
		return lineLostNum;
	}

	public void setLineLostNum(BigDecimal lineLostNum) {
		this.lineLostNum = lineLostNum;
	}

	public BigDecimal getqLineLostNum() {
		return qLineLostNum;
	}

	public void setqLineLostNum(BigDecimal qLineLostNum) {
		this.qLineLostNum = qLineLostNum;
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

	public Long getTgId() {
		return tgId;
	}

	public void setTgId(Long tgId) {
		this.tgId = tgId;
	}

	public Long getLineId() {
		return lineId;
	}

	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

	public Long getSubsId() {
		return subsId;
	}

	public void setSubsId(Long subsId) {
		this.subsId = subsId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTransformerId() {
		return transformerId;
	}

	public void setTransformerId(Long transformerId) {
		this.transformerId = transformerId;
	}

	public Long getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(Long settlementId) {
		this.settlementId = settlementId;
	}

	public Long getWriteSectionId() {
		return writeSectionId;
	}

	public void setWriteSectionId(Long writeSectionId) {
		this.writeSectionId = writeSectionId;
	}

	public Byte getKeepPowerFlag() {
		return keepPowerFlag;
	}

	public void setKeepPowerFlag(Byte keepPowerFlag) {
		this.keepPowerFlag = keepPowerFlag;
	}

	public Byte getDemolitionFlag() {
		return demolitionFlag;
	}

	public void setDemolitionFlag(Byte demolitionFlag) {
		this.demolitionFlag = demolitionFlag;
	}

	public Date getDemolitionDate() {
		return demolitionDate;
	}

	public void setDemolitionDate(Date demolitionDate) {
		this.demolitionDate = demolitionDate;
	}

	public Byte getBillFlag() {
		return billFlag;
	}

	public void setBillFlag(Byte billFlag) {
		this.billFlag = billFlag;
	}

	public Byte getOverdueFineFlag() {
		return overdueFineFlag;
	}

	public void setOverdueFineFlag(Byte overdueFineFlag) {
		this.overdueFineFlag = overdueFineFlag;
	}

	public Byte getTieredPriceFlag() {
		return tieredPriceFlag;
	}

	public void setTieredPriceFlag(Byte tieredPriceFlag) {
		this.tieredPriceFlag = tieredPriceFlag;
	}

	public Byte getWriteMethod() {
		return writeMethod;
	}

	public void setWriteMethod(Byte writeMethod) {
		this.writeMethod = writeMethod;
	}

	public Byte getRateFlag() {
		return rateFlag;
	}

	public void setRateFlag(Byte rateFlag) {
		this.rateFlag = rateFlag;
	}

	public String getTransformerNo() {
		return transformerNo;
	}

	public void setTransformerNo(String transformerNo) {
		this.transformerNo = transformerNo;
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

	public String getTgNo() {
		return tgNo;
	}

	public void setTgNo(String tgNo) {
		this.tgNo = tgNo;
	}

	public String getTgName() {
		return tgName;
	}

	public void setTgName(String tgName) {
		this.tgName = tgName;
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

	public Integer getMon() {
		return mon;
	}

	public void setMon(Integer mon) {
		this.mon = mon;
	}

}
