package org.fms.billing.common.webapp.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.fms.billing.common.webapp.domain.mongo.LadderMongoDomain;
import org.fms.billing.common.webapp.domain.mongo.TransformerCalculateMongoDomain;
import org.fms.billing.common.webapp.domain.mongo.WriteFilesMongoDomain;
import org.fms.billing.common.webapp.entity.ManagerParamEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;

//计量点电费计算信息
@JsonIgnoreProperties(ignoreUnknown = true)
public class MeterMoneyDomain extends ManagerParamEntity implements MybatisEntity {

    @TablePrimaryKey
    private Long id;// ID ID bigint TRUE FALSE TRUE
    private Long meterId;// 计量点ID METER_ID bigint FALSE FALSE FALSE
    private Integer mon;// 电费年月 MON int FALSE FALSE FALSE
    private Byte sn;// 当月次数 SN smallint FALSE FALSE FALSE
    private BigDecimal activeWritePower;// 有功抄见电量 ACTIVE_WRITE_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal reactiveWritePower;// 正向无功抄见电量 REACTIVE_WRITE_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal reverseActiveWritePower;// 反向有功抄见电力 REVERSE_ACTIVE_WRITE_POWER decimal(12,2) 12 2 FALSE FALSE
    // FALSE
    private BigDecimal reverseReactiveWritePower;// 反向无功抄见电量 REVERSE_REACTIVE_WRITE_POWER decimal(12,2) 12 2 FALSE FALSE
    // FALSE
    private BigDecimal activeWritePower1;// 峰有功抄见电量 ACTIVE_WRITE_POWER_1 decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal activeWritePower2;// 平有功抄见电量 ACTIVE_WRITE_POWER_2 decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal activeWritePower3;// 谷有功抄见电量 ACTIVE_WRITE_POWER_3 decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal activeWritePower4;// 尖有功抄见电量 ACTIVE_WRITE_POWER_4 decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal reactiveWritePower1;// 峰无功抄见电量 REACTIVE_WRITE_POWER_1 decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal reactiveWritePower2;// 平无功抄见电量 REACTIVE_WRITE_POWER_2 decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal reactiveWritePower3;// 谷无功抄见电量 REACTIVE_WRITE_POWER_3 decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal reactiveWritePower4;// 尖无功抄见电量 REACTIVE_WRITE_POWER_4 decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal chgPower;// 有功换表电量 CHG_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal qChgPower;// 无功换表电量 Q_CHG_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal addPower;// 有功增减电量 ADD_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal qAddPower;// 无功增减电量 Q_ADD_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal activeDeductionPower;// 有功扣表电量 ACTIVE_DEDUCTION_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal reactiveDeductionPower;// 无功扣表电量 REACTIVE_DEDUCTION_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal activeLineLossPower;// 有功线损电量 ACTIVE_LINE_LOSS_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal reactiveLineLossPower;// 无功线损电量 REACTIVE_LINE_LOSS_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal activeTransformerLossPower;// 有功变损电量 ACTIVE_TRANSFORMER_LOSS_POWER decimal(12,2) 12 2 FALSE FALSE
    // FALSE
    private BigDecimal reactiveTransformerLossPower;// 无功变损电量 REACTIVE_TRANSFORMER_LOSS_POWER decimal(12,2) 12 2 FALSE
    // FALSE FALSE
    private BigDecimal totalPower;// 有功总电量 TOTAL_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal totalAmount;
    private BigDecimal qTotalPower;// 无功总电量 Q_TOTAL_POWER decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal cos;// 功率因数 COS decimal(5,2) 5 2 FALSE FALSE FALSE
    private BigDecimal cosRate;
    private BigDecimal calCapacity;// 计费容量(最大需量) CAL_CAPACITY decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal shareCapacity;// 分摊容量 SHARE_CAPACITY decimal(12,2) 12 2 FALSE FALSE FALSE
    private BigDecimal volumeCharge;// 电度电费 VOLUME_CHARGE decimal(14,4) 14 4 FALSE FALSE FALSE
    private BigDecimal basicMoney;// 基本电费 BASIC_MONEY decimal(14,4) 14 4 FALSE FALSE FALSE
    private BigDecimal powerRateMoney;// 力调电费 POWER_RATE_MONEY decimal(14,4) 14 4 FALSE FALSE FALSE
    private BigDecimal surcharges;// 附加费 SURCHARGES decimal(14,4) 14 4 FALSE FALSE FALSE
    private BigDecimal amount;// 总电费 AMOUNT decimal(14,4) 14 4 FALSE FALSE FALSE
    private Date createDate;// 创建时间 CREATE_DATE datetime FALSE FALSE FALSE
    private String remark;// 备注 REMARK varchar(256) 256 FALSE FALSE FALSE
    private Byte status;// 状态 STATUS smallint FALSE FALSE FALSE
    private Long priceTypeId;
    private Map<String, BigDecimal> surchargeDetail;// 附加费

    private Long writeSectId; // 抄表区段
    private List<Long> writeSectIds;
    private List meterIds;

    private List<LadderMongoDomain> ladderDataModels;// 阶梯数据

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
    private BigDecimal ladderTotalPower;
    private Integer startMon;
    private Integer endMon;
    private BigDecimal totalMoney;
    private Long punishMan;

    public Map<String, BigDecimal> getSurchargeDetail() {
        return surchargeDetail;
    }


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

    private BigDecimal refundMoney;

    private Long settlementId;
    private String settlementNo;
    private String settlementName;
    private String address;
    /**
     * 应收电费
     */
    private BigDecimal electricityReceivable;
    private String businessPlaceCode;
    private Long customerId;
    private String bankNo;
    private String meterNo; // 计量点号
    private String meterName; // 计量点名称
    private String userNo; // 用户户号
    private String userName;// 用户名称
    private String writeSectName;
    private String writeSectNo;
    private Byte meterType; // 计量点类别
    private String accountantName; // 核算员姓名
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date accountingDate; // 核算日期
    private String publisherName; // 发行人姓名
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishingDate; // 发行日期
    private BigDecimal checkingEnergy; // 考核电量
    private Long userId;
    private List<Long> priceTypeIds;
    private TransformerCalculateMongoDomain transformerCalculateInfo;
    private List<WriteFilesMongoDomain> meterDataInfo;
    private BigDecimal transformerGroupNo;

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
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

    public BigDecimal getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(BigDecimal refundMoney) {
        this.refundMoney = refundMoney;
    }

    public BigDecimal getLadderTotalPower() {
        return ladderTotalPower;
    }

    public void setLadderTotalPower(BigDecimal ladderTotalPower) {
        this.ladderTotalPower = ladderTotalPower;
    }

    public void setSurchargeDetail(Map<String, BigDecimal> surchargeDetail) {
        this.surchargeDetail = surchargeDetail;
    }

    public Long getPriceTypeId() {
        return priceTypeId;
    }

    public void setPriceTypeId(Long priceTypeId) {
        this.priceTypeId = priceTypeId;
    }

    public List<LadderMongoDomain> getLadderDataModels() {
        return ladderDataModels;
    }

    public void setLadderDataModels(List<LadderMongoDomain> ladderDataModels) {
        this.ladderDataModels = ladderDataModels;
    }

    public BigDecimal getLadder1Limit() {
        if (ladder1Limit == null) {
            return BigDecimal.ZERO;
        } else {
            return ladder1Limit.setScale(0, RoundingMode.HALF_UP);
        }
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
        if (ladder2Limit == null) {
            return BigDecimal.ZERO;
        } else {
            return ladder2Limit.setScale(0, RoundingMode.HALF_UP);
        }
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
        if (ladder3Limit == null) {
            return BigDecimal.ZERO;
        } else {
            return ladder3Limit.setScale(0, RoundingMode.HALF_UP);
        }
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
        if (ladder4Limit == null) {
            return BigDecimal.ZERO;
        } else {
            return ladder4Limit.setScale(0, RoundingMode.HALF_UP);
        }
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMeterId() {
        return meterId;
    }

    public void setMeterId(Long meterId) {
        this.meterId = meterId;
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

    public BigDecimal getReactiveWritePower() {
        return reactiveWritePower;
    }

    public void setReactiveWritePower(BigDecimal reactiveWritePower) {
        this.reactiveWritePower = reactiveWritePower;
    }

    public BigDecimal getReverseActiveWritePower() {
        return reverseActiveWritePower;
    }

    public void setReverseActiveWritePower(BigDecimal reverseActiveWritePower) {
        this.reverseActiveWritePower = reverseActiveWritePower;
    }

    public BigDecimal getReverseReactiveWritePower() {
        return reverseReactiveWritePower;
    }

    public void setReverseReactiveWritePower(BigDecimal reverseReactiveWritePower) {
        this.reverseReactiveWritePower = reverseReactiveWritePower;
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

    public BigDecimal getReactiveWritePower1() {
        return reactiveWritePower1;
    }

    public void setReactiveWritePower1(BigDecimal reactiveWritePower1) {
        this.reactiveWritePower1 = reactiveWritePower1;
    }

    public BigDecimal getReactiveWritePower2() {
        return reactiveWritePower2;
    }

    public void setReactiveWritePower2(BigDecimal reactiveWritePower2) {
        this.reactiveWritePower2 = reactiveWritePower2;
    }

    public BigDecimal getReactiveWritePower3() {
        return reactiveWritePower3;
    }

    public void setReactiveWritePower3(BigDecimal reactiveWritePower3) {
        this.reactiveWritePower3 = reactiveWritePower3;
    }

    public BigDecimal getReactiveWritePower4() {
        return reactiveWritePower4;
    }

    public void setReactiveWritePower4(BigDecimal reactiveWritePower4) {
        this.reactiveWritePower4 = reactiveWritePower4;
    }

    public BigDecimal getChgPower() {
        return chgPower;
    }

    public void setChgPower(BigDecimal chgPower) {
        this.chgPower = chgPower;
    }

    public BigDecimal getqChgPower() {
        return qChgPower;
    }

    public void setqChgPower(BigDecimal qChgPower) {
        this.qChgPower = qChgPower;
    }

    public BigDecimal getAddPower() {
        return addPower;
    }

    public void setAddPower(BigDecimal addPower) {
        this.addPower = addPower;
    }

    public BigDecimal getqAddPower() {
        return qAddPower;
    }

    public void setqAddPower(BigDecimal qAddPower) {
        this.qAddPower = qAddPower;
    }

    public BigDecimal getActiveDeductionPower() {
        return activeDeductionPower;
    }

    public void setActiveDeductionPower(BigDecimal activeDeductionPower) {
        this.activeDeductionPower = activeDeductionPower;
    }

    public BigDecimal getReactiveDeductionPower() {
        return reactiveDeductionPower;
    }

    public void setReactiveDeductionPower(BigDecimal reactiveDeductionPower) {
        this.reactiveDeductionPower = reactiveDeductionPower;
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

    public BigDecimal getReactiveTransformerLossPower() {
        return reactiveTransformerLossPower;
    }

    public void setReactiveTransformerLossPower(BigDecimal reactiveTransformerLossPower) {
        this.reactiveTransformerLossPower = reactiveTransformerLossPower;
    }

    public BigDecimal getTotalPower() {
        return totalPower;
    }

    public void setTotalPower(BigDecimal totalPower) {
        this.totalPower = totalPower;
    }

    public BigDecimal getqTotalPower() {
        return qTotalPower;
    }

    public void setqTotalPower(BigDecimal qTotalPower) {
        this.qTotalPower = qTotalPower;
    }

    public BigDecimal getCos() {
        return cos;
    }

    public void setCos(BigDecimal cos) {
        this.cos = cos;
    }

    public BigDecimal getCalCapacity() {
        return calCapacity;
    }

    public void setCalCapacity(BigDecimal calCapacity) {
        this.calCapacity = calCapacity;
    }

    public BigDecimal getShareCapacity() {
        return shareCapacity;
    }

    public void setShareCapacity(BigDecimal shareCapacity) {
        this.shareCapacity = shareCapacity;
    }

    public BigDecimal getVolumeCharge() {
        return volumeCharge;
    }

    public void setVolumeCharge(BigDecimal volumeCharge) {
        this.volumeCharge = volumeCharge;
    }

    public BigDecimal getBasicMoney() {
        return basicMoney;
    }

    public void setBasicMoney(BigDecimal basicMoney) {
        this.basicMoney = basicMoney;
    }

    public BigDecimal getPowerRateMoney() {
        return powerRateMoney;
    }

    public void setPowerRateMoney(BigDecimal powerRateMoney) {
        this.powerRateMoney = powerRateMoney;
    }

    public BigDecimal getSurcharges() {
        return surcharges;
    }

    public void setSurcharges(BigDecimal surcharges) {
        this.surcharges = surcharges;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public Long getWriteSectId() {
        return writeSectId;
    }

    public void setWriteSectId(Long writeSectId) {
        this.writeSectId = writeSectId;
    }

    public List<Long> getWriteSectIds() {
        return writeSectIds;
    }

    public void setWriteSectIds(List<Long> writeSectIds) {
        this.writeSectIds = writeSectIds;
    }

    public List getMeterIds() {
        return meterIds;
    }

    public void setMeterIds(List meterIds) {
        this.meterIds = meterIds;
    }

    public Long getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(Long settlementId) {
        this.settlementId = settlementId;
    }

    public String getSettlementNo() {
        return settlementNo;
    }

    public void setSettlementNo(String settlementNo) {
        this.settlementNo = settlementNo;
    }

    public String getSettlementName() {
        return settlementName;
    }

    public void setSettlementName(String settlementName) {
        this.settlementName = settlementName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getElectricityReceivable() {
        return electricityReceivable;
    }

    public void setElectricityReceivable(BigDecimal electricityReceivable) {
        this.electricityReceivable = electricityReceivable;
    }

    public String getBusinessPlaceCode() {
        return businessPlaceCode;
    }

    public void setBusinessPlaceCode(String businessPlaceCode) {
        this.businessPlaceCode = businessPlaceCode;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
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

    public String getWriteSectName() {
        return writeSectName;
    }

    public void setWriteSectName(String writeSectName) {
        this.writeSectName = writeSectName;
    }

    public String getWriteSectNo() {
        return writeSectNo;
    }

    public void setWriteSectNo(String writeSectNo) {
        this.writeSectNo = writeSectNo;
    }

    public Byte getMeterType() {
        return meterType;
    }

    public void setMeterType(Byte meterType) {
        this.meterType = meterType;
    }

    public String getAccountantName() {
        return accountantName;
    }

    public void setAccountantName(String accountantName) {
        this.accountantName = accountantName;
    }

    public Date getAccountingDate() {
        return accountingDate;
    }

    public void setAccountingDate(Date accountingDate) {
        this.accountingDate = accountingDate;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public Date getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(Date publishingDate) {
        this.publishingDate = publishingDate;
    }

    public BigDecimal getCheckingEnergy() {
        return checkingEnergy;
    }

    public void setCheckingEnergy(BigDecimal checkingEnergy) {
        this.checkingEnergy = checkingEnergy;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getPriceTypeIds() {
        return priceTypeIds;
    }

    public void setPriceTypeIds(List<Long> priceTypeIds) {
        this.priceTypeIds = priceTypeIds;
    }

    public TransformerCalculateMongoDomain getTransformerCalculateInfo() {
        return transformerCalculateInfo;
    }

    public void setTransformerCalculateInfo(TransformerCalculateMongoDomain transformerCalculateInfo) {
        this.transformerCalculateInfo = transformerCalculateInfo;
    }

    public List<WriteFilesMongoDomain> getMeterDataInfo() {
        return meterDataInfo;
    }

    public void setMeterDataInfo(List<WriteFilesMongoDomain> meterDataInfo) {
        this.meterDataInfo = meterDataInfo;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getCosRate() {
        return cosRate;
    }

    public void setCosRate(BigDecimal cosRate) {
        this.cosRate = cosRate;
    }

    public BigDecimal getTransformerGroupNo() {
        return transformerGroupNo;
    }

    public void setTransformerGroupNo(BigDecimal transformerGroupNo) {
        this.transformerGroupNo = transformerGroupNo;
    }

    public Long getPunishMan() {
        return punishMan;
    }

    public void setPunishMan(Long punishMan) {
        this.punishMan = punishMan;
    }
}
