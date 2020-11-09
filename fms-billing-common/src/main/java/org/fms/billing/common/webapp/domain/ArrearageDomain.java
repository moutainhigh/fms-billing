package org.fms.billing.common.webapp.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.fms.billing.common.webapp.entity.ManagerParamEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;

//欠费单
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArrearageDomain extends ManagerParamEntity implements MybatisEntity {
	@TablePrimaryKey
	private Long id;
	// 应收凭证号
	private String arrearageNo;
	// 计量点ID
	private Long meterId;
	// 收费截止日期
	private Date endDate;
	private BigDecimal totalPower;
	// 应收电费
	private BigDecimal receivable;

	private BigDecimal punishMoney;
	private Date createDate;
	private String operator;
	private Integer status;
	// 电费年月
	private Integer mon;
	// 电费年月
	private Integer sn;
	// 明细id
	private Long meterMoneyId;
	// 是否结清
	private Integer isSettle;
	// 欠费
	private BigDecimal oweMoney;

	private Long writeSectId;// 抄表区段ID
	private Long writorId;// 抄表员ID
	private Long businessPlaceCode;// 营业区域ID

	private String meterNo;
	private String meterName;
	private String writeSectNo; //
	private String writeSectName; //

	private Long settlementId;

	private Short isLock;

	// 欠费明细报表查询 电费年月-开始年月
	private Integer startMon;

	// 欠费明细报表查询 电费年月-结束年月
	private Integer endMon;
	private Integer arrearsNum;//欠费期数

	private Long userId;
	private String userNo;
	private String userName;
	private Byte userType;//
	private String deptName;
	private String setAddress;
	private Integer elecTypeCode; // 用电类别
	private String bankNo;// 银行卡号
	private String settlementPhone;// 结算人电话
	private String settlementName;// 结算人名称
	private String settlementNo;// 结算人名称
	private Byte connectBank;// 联网银行
	private Byte chargeModeType;// 收费方式
	private int count; // 欠费期数
	private BigDecimal cos;// 功率因数 COS decimal(5,2) 5 2 FALSE FALSE FALSE
	private BigDecimal lastBalance;
	private BigDecimal calCapacity;//计费容量
	private BigDecimal surcharges;// 附加费/价调基金 SURCHARGES decimal(14,4) 14 4 FALSE FALSE FALSE
	private String writerName;
	private String operatorName;
	private BigDecimal factorNum;
    private BigDecimal activeWritePower;// 有功抄见电量 ACTIVE_WRITE_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal qTotalPower;
    private BigDecimal startNum;
    private BigDecimal endNum;
    private BigDecimal addPower;
    private BigDecimal chgPower;
    private BigDecimal activeLineLossPower;// 有功线损电量 ACTIVE_LINE_LOSS_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal reactiveLineLossPower;// 无功线损电量 REACTIVE_LINE_LOSS_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal activeTransformerLossPower;// 有功变损电量 ACTIVE_TRANSFORMER_LOSS_POWER decimal(12,2) 12 2 FALSE FALSE
	private BigDecimal powerRateMoney;// 力调电费 POWER_RATE_MONEY decimal(14,4) 14 4 FALSE FALSE FALSE
	private BigDecimal refundMoney;
	private Long priceType;
	private BigDecimal deductionBalance;
	private BigDecimal addMoney8;
	private BigDecimal activeDeductionPower;
	//cim meterInfo

	private String cosStdCodeName;//利率标准名称
	private String cosFlagName;//利率标准数值名称

	//noteInfo
	private String priceName;
    private Long priceTypeId;
	private BigDecimal price;

	//meterMoney
	private BigDecimal ladder1Limit;
	private BigDecimal ladder1Power;
	private BigDecimal ladder1Money;
	private BigDecimal ladder2Limit;
	private BigDecimal ladder2Power;
	private BigDecimal ladder2Money;
	private BigDecimal ladder3Limit;
	private BigDecimal ladder3Power;
	private BigDecimal ladder3Money;
	private BigDecimal ladder4Limit;
	private BigDecimal ladder4Power;
	private BigDecimal ladder4Money;
	private String writeDate;
	private String lastWriteDate;
	private String lasrWriteDate;

	private List<Integer> mons;
	private List<Long> businessPlaceCodes;
	private List<Long> writeSectIds;
	private List<Long> writorIds;
	private List<Long> meterIds;
	private List<Long> settlementIds;
	private Long chargeId;
	private String tg;
	private String name;
	private Integer functionCode;
	private BigDecimal volumeCharge;
	private BigDecimal cosRate;
	//托收凭证总额

	private Long customerId;// ID ID bigint TRUE FALSE TRUE
	private String customerNo;// 客户编号 CUSTOMER_NO VARCHAR(16) 16 FALSE FALSE FALSE
	private String customerName;// 客户名称 CUSTOMER_NAME VARCHAR(64) 64 FALSE FALSE FALSE
	private String chargeModeTypeName;
	private String connectBankName;
	private String elecTypeName;
	private String userTypeName;
	private BigDecimal amount;
	private Integer isPrint;
	private String businessPlaceName;
	private String chargeModeName;
	private BigDecimal electricityChargeReceived;
	private BigDecimal basicMoney;
	private BigDecimal shouldMoney;
	private boolean isSend;
	private BigDecimal diffNum;// 度差
	private List<Long> ids; // ArrearageId
	private BigDecimal writeSn;
	private Long groupKey;
	private String groupBy;

	public String getLastWriteDate() {
		return lastWriteDate;
	}

	public void setLastWriteDate(String lastWriteDate) {
		this.lastWriteDate = lastWriteDate;
	}

	public BigDecimal getWriteSn() {
		return writeSn;
	}

	public void setWriteSn(BigDecimal writeSn) {
		this.writeSn = writeSn;
	}

	public BigDecimal getActiveDeductionPower() {
		return activeDeductionPower;
	}

	public void setActiveDeductionPower(BigDecimal activeDeductionPower) {
		this.activeDeductionPower = activeDeductionPower;
	}

	public BigDecimal getShouldMoney() {
		return shouldMoney;
	}

	public void setShouldMoney(BigDecimal shouldMoney) {
		this.shouldMoney = shouldMoney;
	}

	public BigDecimal getBasicMoney() {
		return basicMoney;
	}

	public void setBasicMoney(BigDecimal basicMoney) {
		this.basicMoney = basicMoney;
	}

	public String getChargeModeName() {
		return chargeModeName;
	}

	public void setChargeModeName(String chargeModeName) {
		this.chargeModeName = chargeModeName;
	}

	public String getBusinessPlaceName() {
		return businessPlaceName;
	}

	public void setBusinessPlaceName(String businessPlaceName) {
		this.businessPlaceName = businessPlaceName;
	}

	public Integer getIsPrint() {
		return isPrint;
	}

	public void setIsPrint(Integer isPrint) {
		this.isPrint = isPrint;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public String getElecTypeName() {
		return elecTypeName;
	}

	public void setElecTypeName(String elecTypeName) {
		this.elecTypeName = elecTypeName;
	}

	public String getConnectBankName() {
		return connectBankName;
	}

	public void setConnectBankName(String connectBankName) {
		this.connectBankName = connectBankName;
	}

	public String getChargeModeTypeName() {
		return chargeModeTypeName;
	}

	public void setChargeModeTypeName(String chargeModeTypeName) {
		this.chargeModeTypeName = chargeModeTypeName;
	}

	public BigDecimal getDeductionBalance() {
		if(deductionBalance==null){
			return BigDecimal.ZERO;
		}else{
			return deductionBalance.setScale(2, RoundingMode.HALF_UP);
		}
	}

	public void setDeductionBalance(BigDecimal deductionBalance) {
		this.deductionBalance = deductionBalance;
	}

	public BigDecimal getRefundMoney() {
		return refundMoney;
	}

	public void setRefundMoney(BigDecimal refundMoney) {
		this.refundMoney = refundMoney;
	}

	public BigDecimal getCosRate() {
		return cosRate;
	}

	public void setCosRate(BigDecimal cosRate) {
		this.cosRate = cosRate;
	}

	public BigDecimal getVolumeCharge() {
		return volumeCharge;
	}

	public void setVolumeCharge(BigDecimal volumeCharge) {
		this.volumeCharge = volumeCharge;
	}

	public Integer getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(Integer functionCode) {
		this.functionCode = functionCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTg() {
		return tg;
	}

	public void setTg(String tg) {
		this.tg = tg;
	}

	public String getSettlementNo() {
		return settlementNo;
	}

	public void setSettlementNo(String settlementNo) {
		this.settlementNo = settlementNo;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	public String getLasrWriteDate() {
		return lasrWriteDate;
	}

	public void setLasrWriteDate(String lasrWriteDate) {
		this.lasrWriteDate = lasrWriteDate;
	}

	public Long getChargeId() {
		return chargeId;
	}

	public void setChargeId(Long chargeId) {
		this.chargeId = chargeId;
	}

	//CHARGE_INFO


    public List<Long> getSettlementIds() {
        return settlementIds;
    }

    public void setSettlementIds(List<Long> settlementIds) {
        this.settlementIds = settlementIds;
    }

    public Long getPriceType() {
		return priceType;
	}

	public void setPriceType(Long priceType) {
		this.priceType = priceType;
	}

	public BigDecimal getLadder1Limit() {
		return ladder1Limit;
	}

	public void setLadder1Limit(BigDecimal ladder1Limit) {
		this.ladder1Limit = ladder1Limit;
	}

	public BigDecimal getLadder1Power() {
		return ladder1Power;
	}

	public void setLadder1Power(BigDecimal ladder1Power) {
		this.ladder1Power = ladder1Power;
	}

	public BigDecimal getLadder1Money() {
		return ladder1Money;
	}

	public void setLadder1Money(BigDecimal ladder1Money) {
		this.ladder1Money = ladder1Money;
	}

	public BigDecimal getLadder2Limit() {
		return ladder2Limit;
	}

	public void setLadder2Limit(BigDecimal ladder2Limit) {
		this.ladder2Limit = ladder2Limit;
	}

	public BigDecimal getLadder2Power() {
		return ladder2Power;
	}

	public void setLadder2Power(BigDecimal ladder2Power) {
		this.ladder2Power = ladder2Power;
	}

	public BigDecimal getLadder2Money() {
		return ladder2Money;
	}

	public void setLadder2Money(BigDecimal ladder2Money) {
		this.ladder2Money = ladder2Money;
	}

	public BigDecimal getLadder3Limit() {
		return ladder3Limit;
	}

	public void setLadder3Limit(BigDecimal ladder3Limit) {
		this.ladder3Limit = ladder3Limit;
	}

	public BigDecimal getLadder3Power() {
		return ladder3Power;
	}

	public void setLadder3Power(BigDecimal ladder3Power) {
		this.ladder3Power = ladder3Power;
	}

	public BigDecimal getLadder3Money() {
		return ladder3Money;
	}

	public void setLadder3Money(BigDecimal ladder3Money) {
		this.ladder3Money = ladder3Money;
	}

	public BigDecimal getLadder4Limit() {
		return ladder4Limit;
	}

	public void setLadder4Limit(BigDecimal ladder4Limit) {
		this.ladder4Limit = ladder4Limit;
	}

	public BigDecimal getLadder4Power() {
		return ladder4Power;
	}

	public void setLadder4Power(BigDecimal ladder4Power) {
		this.ladder4Power = ladder4Power;
	}

	public BigDecimal getLadder4Money() {
		return ladder4Money;
	}

	public void setLadder4Money(BigDecimal ladder4Money) {
		this.ladder4Money = ladder4Money;
	}

	public String getPriceName() {
		return priceName;
	}

	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}

	public String getCosStdCodeName() {
		return cosStdCodeName;
	}

	public void setCosStdCodeName(String cosStdCodeName) {
		this.cosStdCodeName = cosStdCodeName;
	}

	public String getCosFlagName() {
		return cosFlagName;
	}

	public void setCosFlagName(String cosFlagName) {
		this.cosFlagName = cosFlagName;
	}

	public BigDecimal getPowerRateMoney() {
		return powerRateMoney;
	}

	public void setPowerRateMoney(BigDecimal powerRateMoney) {
		this.powerRateMoney = powerRateMoney;
	}

	public Integer getArrearsNum() {
		return arrearsNum;
	}

	public void setArrearsNum(Integer arrearsNum) {
		this.arrearsNum = arrearsNum;
	}

	public BigDecimal getAddPower() {
        return addPower;
    }

    public void setAddPower(BigDecimal addPower) {
        this.addPower = addPower;
    }

    public BigDecimal getChgPower() {
        return chgPower;
    }

    public void setChgPower(BigDecimal chgPower) {
        this.chgPower = chgPower;
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

    public BigDecimal getActiveTransformerLossPower() {
        return activeTransformerLossPower;
    }

    public void setActiveTransformerLossPower(BigDecimal activeTransformerLossPower) {
        this.activeTransformerLossPower = activeTransformerLossPower;
    }

    public BigDecimal getqTotalPower() {
        return qTotalPower;
    }

    public void setqTotalPower(BigDecimal qTotalPower) {
        this.qTotalPower = qTotalPower;
    }

    public BigDecimal getStartNum() {
        return startNum;
    }

    public void setStartNum(BigDecimal startNum) {
        this.startNum = startNum;
    }

    public BigDecimal getEndNum() {
        return endNum;
    }

    public void setEndNum(BigDecimal endNum) {
        this.endNum = endNum;
    }

    public BigDecimal getFactorNum() {
        return factorNum;
    }

    public void setFactorNum(BigDecimal factorNum) {
        this.factorNum = factorNum;
    }

    public BigDecimal getActiveWritePower() {
        return activeWritePower;
    }

    public void setActiveWritePower(BigDecimal activeWritePower) {
        this.activeWritePower = activeWritePower;
    }

    public BigDecimal getCalCapacity() {
		return calCapacity;
	}

	public void setCalCapacity(BigDecimal calCapacity) {
		this.calCapacity = calCapacity;
	}

	public BigDecimal getSurcharges() {
		return surcharges;
	}

	public void setSurcharges(BigDecimal surcharges) {
		this.surcharges = surcharges;
	}

	public String getWriterName() {
		return writerName;
	}

	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Long getPriceTypeId() {
		return priceTypeId;
	}

	public void setPriceTypeId(Long priceTypeId) {
		this.priceTypeId = priceTypeId;
	}

	public String getSettlementName() {
		return settlementName;
	}

	public void setSettlementName(String settlementName) {
		this.settlementName = settlementName;
	}

	public BigDecimal getCos() {
		return cos;
	}

	public void setCos(BigDecimal cos) {
		this.cos = cos;
	}

	public BigDecimal getLastBalance() {
		return lastBalance;
	}

	public void setLastBalance(BigDecimal lastBalance) {
		this.lastBalance = lastBalance;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(Long settlementId) {
		this.settlementId = settlementId;
	}

	public Short getIsLock() {
        return isLock;
    }

    public void setIsLock(Short isLock) {
        this.isLock = isLock;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Long getMeterMoneyId() {
		return meterMoneyId;
	}

	public void setMeterMoneyId(Long meterMoneyId) {
		this.meterMoneyId = meterMoneyId;
	}

	public Integer getIsSettle() {
		return isSettle;
	}

	public void setIsSettle(Integer isSettle) {
		this.isSettle = isSettle;
	}

	public BigDecimal getReceivable() {
			return receivable;
	}

	public void setReceivable(BigDecimal receivable) {
		this.receivable = receivable;
	}

	public BigDecimal getPunishMoney() {
    	if(punishMoney==null){
    		return BigDecimal.ZERO;
		}else{

			return punishMoney;
		}
	}

	public void setPunishMoney(BigDecimal punishMoney) {
		this.punishMoney = punishMoney;
	}

	public BigDecimal getOweMoney() {
		return oweMoney;
	}

	public void setOweMoney(BigDecimal oweMoney) {
		this.oweMoney = oweMoney;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public String getWriteSectNo() {
		return writeSectNo;
	}

	public void setWriteSectNo(String writeSectNo) {
		this.writeSectNo = writeSectNo;
	}

	public String getMeterName() {
		return meterName;
	}

	public void setMeterName(String meterName) {
		this.meterName = meterName;
	}

	public String getWriteSectName() {
		return writeSectName;
	}

	public void setWriteSectName(String writeSectName) {
		this.writeSectName = writeSectName;
	}

	public Long getWriteSectId() {
		return writeSectId;
	}

	public void setWriteSectId(Long writeSectId) {
		this.writeSectId = writeSectId;
	}

	public Long getWritorId() {
		return writorId;
	}

	public void setWritorId(Long writorId) {
		this.writorId = writorId;
	}

	public Long getBusinessPlaceCode() {
		return businessPlaceCode;
	}

	public void setBusinessPlaceCode(Long businessPlaceCode) {
		this.businessPlaceCode = businessPlaceCode;
	}

	public BigDecimal getTotalPower() {
		return totalPower;
	}

	public void setTotalPower(BigDecimal totalPower) {
		this.totalPower = totalPower;
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

	public List<Integer> getMons() {
		return mons;
	}

	public void setMons(List<Integer> mons) {
		this.mons = mons;
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

	public Byte getUserType() {
		return userType;
	}

	public void setUserType(Byte userType) {
		this.userType = userType;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getSetAddress() {
		return setAddress;
	}

	public void setSetAddress(String setAddress) {
		this.setAddress = setAddress;
	}

	public Integer getElecTypeCode() {
		return elecTypeCode;
	}

	public void setElecTypeCode(Integer elecTypeCode) {
		this.elecTypeCode = elecTypeCode;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getSettlementPhone() {
		return settlementPhone;
	}

	public void setSettlementPhone(String settlementPhone) {
		this.settlementPhone = settlementPhone;
	}

	public Byte getConnectBank() {
		return connectBank;
	}

	public void setConnectBank(Byte connectBank) {
		this.connectBank = connectBank;
	}

	public Byte getChargeModeType() {
		return chargeModeType;
	}

	public void setChargeModeType(Byte chargeModeType) {
		this.chargeModeType = chargeModeType;
	}

	public List<Long> getBusinessPlaceCodes() {
		return businessPlaceCodes;
	}

	public void setBusinessPlaceCodes(List<Long> businessPlaceCodes) {
		this.businessPlaceCodes = businessPlaceCodes;
	}

	public List<Long> getWriteSectIds() {
		return writeSectIds;
	}

	public void setWriteSectIds(List<Long> writeSectIds) {
		this.writeSectIds = writeSectIds;
	}

	public List<Long> getWritorIds() {
		return writorIds;
	}

	public void setWritorIds(List<Long> writorIds) {
		this.writorIds = writorIds;
	}

	public List<Long> getMeterIds() {
		return meterIds;
	}

	public void setMeterIds(List<Long> meterIds) {
		this.meterIds = meterIds;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getElectricityChargeReceived() {
		return electricityChargeReceived;
	}

	public void setElectricityChargeReceived(BigDecimal electricityChargeReceived) {
		this.electricityChargeReceived = electricityChargeReceived;
	}

	public BigDecimal getAddMoney8() {
		return addMoney8;
	}

	public void setAddMoney8(BigDecimal addMoney8) {
		this.addMoney8 = addMoney8;
	}

	public boolean getIsSend() {
		return isSend;
	}

	public void setIsSend(boolean isSend) {
		this.isSend = isSend;
	}

	public BigDecimal getDiffNum() {
		return diffNum;
	}

	public void setDiffNum(BigDecimal diffNum) {
		this.diffNum = diffNum;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Long getGroupKey() {
		return groupKey;
	}

	public void setGroupKey(Long groupKey) {
		this.groupKey = groupKey;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}
}
