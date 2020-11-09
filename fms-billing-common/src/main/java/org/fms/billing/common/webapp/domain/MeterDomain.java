/**
 * Author : czy
 * Date : 2019年7月8日 下午4:10:35
 * Title : com.riozenc.billing.webapp.domain.MeterDomain.java
 *
**/
package org.fms.billing.common.webapp.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.fms.billing.common.webapp.domain.mongo.MeterMoneyMongoDomain;
import org.fms.billing.common.webapp.entity.ManagerParamEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.annotation.TablePrimaryKey;
@JsonIgnoreProperties(ignoreUnknown = true)
public class MeterDomain extends ManagerParamEntity {
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
	private Date createDate; // 创建时间
	private String remark; // 备注
	private Integer status; // 状态
	private Long tgId; // 所属台区 TG_ID bigint
	private Long lineId; // 所属线路 LINE_ID bigint
	private Long subsId; // 所属厂站 SUBS_ID bigint
	private Long userId; // 所属用户 USER_ID bigint
	private Long transformerId; // 所属变压器 TRANSFORMER_ID bigint
	private Long settlementId; // 所属结算户 SETTLEMENT_ID bigint
	private Long writeSectionId; // 所属抄表段 WRITE_SECTION_ID bigint
	private Byte keepPowerFlag; // 保电标识 KEEP_POWER_FLAG smallint
	private Byte demolitionFlag; // 扒房标识 DEMOLITION_FLAG smallint
	private Date demolitionDate; // 扒房标识日期 DEMOLITION_DATE datetime
	private Byte billFlag; // 发票打印标识 BILL_FLAG smallint
	private Byte overdueFineFlag; // 滞纳金标识 OVERDUE_FINE_FLAG smallint
	private Byte tieredPriceFlag; // 阶梯电价标识 TIERED_PRICE_FLAG smallint
	private Byte writeMethod; // 抄表方式 WRITE_METHOD

	private Integer countTimes = 1;// COUNT_TIMES 算费次数
	private BigDecimal chargingCapacity;// CHARGING_CAPACITY 计费容量
	private Long basicPrice;// 基本电价
	/*--------------------------以下为非数据库字段-----------------------------------------*/
	private String transformerNo; // 变压器编号，用于接收业扩返回的信息，不存库
	private String writeSectNo;//
	private String userNo; // 用户户号
	private String userName;// 用户名称
	private String writeSectId; // 抄表区段
	private String writeSectName;//
	private Long businessPlaceCode;// 营业区域ID
	private Long writorId;// 抄表员ID
	private Integer ladderNum;
	private Integer mon;
	private MeterMoneyMongoDomain meterMoneyMoneyDomain;
	private BigDecimal totalPower;// 有功总电量 TOTAL_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
	private BigDecimal amount;// 总电费 TOTAL_MONEY decimal(14,4) 14 4 FALSE FALSE FALSE
	private List ids;
	private List writeSectionIds;
	private List businessPlaceCodes;
	private List userIds;
	private List writorIds;
	private Integer sn;

	private Integer isCharge;
	private String bankNo;// 银行卡号 BANK_NO varchar(32) 32 FALSE FALSE FALSE

	private Long meterOrder;// 计量点序号
	private String meterAssetsNo; // 电表资产号
	private String writeSn;
	private String madeNo;
	private String address;
	private Long customerId;
	private List<Long> meterIds;
	private List<Integer> statuss;


	public Integer getLadderNum() {
		return ladderNum;
	}

	public void setLadderNum(Integer ladderNum) {
		this.ladderNum = ladderNum;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public Integer getIsCharge() {
		return isCharge;
	}

	public void setIsCharge(Integer isCharge) {
		this.isCharge = isCharge;
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setCountTimes(Integer countTimes) {
		this.countTimes = countTimes;
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


	public BigDecimal getChargingCapacity() {
		return chargingCapacity;
	}

	public void setChargingCapacity(BigDecimal chargingCapacity) {
		this.chargingCapacity = chargingCapacity;
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

	public String getWriteSectId() {
		return writeSectId;
	}

	public void setWriteSectId(String writeSectId) {
		this.writeSectId = writeSectId;
	}

	public Long getBusinessPlaceCode() {
		return businessPlaceCode;
	}

	public void setBusinessPlaceCode(Long businessPlaceCode) {
		this.businessPlaceCode = businessPlaceCode;
	}

	public Long getWritorId() {
		return writorId;
	}

	public void setWritorId(Long writorId) {
		this.writorId = writorId;
	}

	public Integer getMon() {
		return mon;
	}

	public void setMon(Integer mon) {
		this.mon = mon;
	}

	public MeterMoneyMongoDomain getMeterMoneyMoneyDomain() {
		return meterMoneyMoneyDomain;
	}

	public void setMeterMoneyMoneyDomain(MeterMoneyMongoDomain meterMoneyMoneyDomain) {
		this.meterMoneyMoneyDomain = meterMoneyMoneyDomain;
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

	public Long getBasicPrice() {
		return basicPrice;
	}

	public void setBasicPrice(Long basicPrice) {
		this.basicPrice = basicPrice;
	}

	public List getIds() {
		return ids;
	}

	public void setIds(List ids) {
		this.ids = ids;
	}

	public List getWriteSectionIds() {
		return writeSectionIds;
	}

	public void setWriteSectionIds(List writeSectionIds) {
		this.writeSectionIds = writeSectionIds;
	}

	public List getBusinessPlaceCodes() {
		return businessPlaceCodes;
	}

	public void setBusinessPlaceCodes(List businessPlaceCodes) {
		this.businessPlaceCodes = businessPlaceCodes;
	}

	public List getUserIds() {
		return userIds;
	}

	public void setUserIds(List userIds) {
		this.userIds = userIds;
	}

	public List getWritorIds() {
		return writorIds;
	}

	public void setWritorIds(List writorIds) {
		this.writorIds = writorIds;
	}

	public Integer getCountTimes() {
		return countTimes;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public Long getMeterOrder() {
		return meterOrder;
	}

	public void setMeterOrder(Long meterOrder) {
		this.meterOrder = meterOrder;
	}

	public String getMeterAssetsNo() {
		return meterAssetsNo;
	}

	public void setMeterAssetsNo(String meterAssetsNo) {
		this.meterAssetsNo = meterAssetsNo;
	}

	public String getWriteSn() {
		return writeSn;
	}

	public void setWriteSn(String writeSn) {
		this.writeSn = writeSn;
	}

	public String getMadeNo() {
		return madeNo;
	}

	public void setMadeNo(String madeNo) {
		this.madeNo = madeNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public List<Long> getMeterIds() {
		return meterIds;
	}

	public void setMeterIds(List<Long> meterIds) {
		this.meterIds = meterIds;
	}

	public List<Integer> getStatuss() {
		return statuss;
	}

	public void setStatuss(List<Integer> statuss) {
		this.statuss = statuss;
	}
}
