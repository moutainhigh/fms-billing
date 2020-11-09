package org.fms.billing.common.webapp.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.fms.billing.common.webapp.entity.ManagerParamEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.mybatis.MybatisEntity;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArrearageDetailDomain extends ManagerParamEntity implements MybatisEntity {
    private Long id;

    //应收凭证号
    private String arrearageNo;
    private String userNo;
    private String userName;
    //计量点ID
    private Long meterId;
    private String meterName;
    //收费截止日期
    private LocalDate endDate;
    //应收电费
    private Double receivable;
    private BigDecimal oweMoney;
    private BigDecimal punishMoney;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    private String operator;
    private Integer status;
    // 电费年月
    private String mon;
    // 电费年月
    private Integer sn;
    //明细id
    private Long meterMoneyId;
    //是否结清
    private Integer isSettle;
    //正向有功扣表电量
    private Double activeDeductionPower;
    //正向有功线损电量
    private Double activeLineLossPower;
    //正向有功变损电量
    private Double activeTransformerLossPower;
    //正向有功抄见电量
    private Double activeWritePower;
    //正向峰
    private Double activeWritePower_1;
    //正向平
    private Double activeWritePower_2;
    //正向谷
    private Double activeWritePower_3;
    //正向尖
    private Double activeWritePower_4;
    //增减
    private Double addPower;
    //总电费
    private BigDecimal amount;
    //基本电费
    private BigDecimal basicMoney;
    //容量
    private Double calCapacity;
    //换表电量
    private Double chgPower;
    //力调系数
    private Double cos;
    //力调电费
    private BigDecimal powerRateMoney;
    //无功增减
    private Double qAddPower;
    //无功换表
    private Double qChgPower;
    //无功总
    private Double qTotalPower;
    //无功扣表
    private Double reactiveDeductionPower;
    //无功线损
    private Double reactiveLineLossPower;
    //无功变损
    private Double reactiveTransformerLoss;
    //无功抄见
    private Double reactiveWritePower;
    //无功峰
    private Double reactiveWritePower_1;
    //无功平
    private Double reactiveWritePower_2;
    //无功谷
    private Double reactiveWritePower_3;
    //无功尖
    private Double reactiveWritePower_4;
    //反向无功抄见
    private Double  reverseReactiveWritePower;
    //分摊容量
    private Double shareCapacity;
    //附加费
    private BigDecimal surcharges;
    //总电量
    private Double totalPower;
    //电度电费
    private BigDecimal volumeCharge;
    private Integer rowSn;
    private Long writeSectId; // 抄表区段

    private Integer priceTypeId;

    private BigDecimal ycys;
    //收费界面起止码展示 可能多个
    private String startNumString;

    private String endNumString;

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

    private Integer isLock;

    private BigDecimal refundMoney;

    private Long settlementId;

    private Integer isAddTax;

    public Integer getIsAddTax() {
        return isAddTax;
    }

    public void setIsAddTax(Integer isAddTax) {
        this.isAddTax = isAddTax;
    }

    public Long getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(Long settlementId) {
        this.settlementId = settlementId;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public Integer getPriceTypeId() {
        return priceTypeId;
    }

    public void setPriceTypeId(Integer priceTypeId) {
        this.priceTypeId = priceTypeId;
    }

    public Integer getRowSn() {
        return rowSn;
    }

    public void setRowSn(Integer rowSn) {
        this.rowSn = rowSn;
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

    public String getMeterName() {
        return meterName;
    }

    public void setMeterName(String meterName) {
        this.meterName = meterName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getReceivable() {
        return receivable;
    }

    public void setReceivable(Double receivable) {
        this.receivable = receivable;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
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

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
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

    public Double getActiveDeductionPower() {
        return activeDeductionPower;
    }

    public void setActiveDeductionPower(Double activeDeductionPower) {
        this.activeDeductionPower = activeDeductionPower;
    }

    public Double getActiveLineLossPower() {
        return activeLineLossPower;
    }

    public void setActiveLineLossPower(Double activeLineLossPower) {
        this.activeLineLossPower = activeLineLossPower;
    }

    public Double getActiveTransformerLossPower() {
        return activeTransformerLossPower;
    }

    public void setActiveTransformerLossPower(Double activeTransformerLossPower) {
        this.activeTransformerLossPower = activeTransformerLossPower;
    }

    public Double getActiveWritePower() {
        return activeWritePower;
    }

    public void setActiveWritePower(Double activeWritePower) {
        this.activeWritePower = activeWritePower;
    }

    public Double getActiveWritePower_1() {
        return activeWritePower_1;
    }

    public void setActiveWritePower_1(Double activeWritePower_1) {
        this.activeWritePower_1 = activeWritePower_1;
    }

    public Double getActiveWritePower_2() {
        return activeWritePower_2;
    }

    public void setActiveWritePower_2(Double activeWritePower_2) {
        this.activeWritePower_2 = activeWritePower_2;
    }

    public Double getActiveWritePower_3() {
        return activeWritePower_3;
    }

    public void setActiveWritePower_3(Double activeWritePower_3) {
        this.activeWritePower_3 = activeWritePower_3;
    }

    public Double getActiveWritePower_4() {
        return activeWritePower_4;
    }

    public void setActiveWritePower_4(Double activeWritePower_4) {
        this.activeWritePower_4 = activeWritePower_4;
    }

    public Double getAddPower() {
        return addPower;
    }

    public void setAddPower(Double addPower) {
        this.addPower = addPower;
    }


    public Double getCalCapacity() {
        return calCapacity;
    }

    public void setCalCapacity(Double calCapacity) {
        this.calCapacity = calCapacity;
    }

    public Double getChgPower() {
        return chgPower;
    }

    public void setChgPower(Double chgPower) {
        this.chgPower = chgPower;
    }

    public Double getCos() {
        return cos;
    }

    public void setCos(Double cos) {
        this.cos = cos;
    }


    public Double getqAddPower() {
        return qAddPower;
    }

    public void setqAddPower(Double qAddPower) {
        this.qAddPower = qAddPower;
    }

    public Double getqChgPower() {
        return qChgPower;
    }

    public void setqChgPower(Double qChgPower) {
        this.qChgPower = qChgPower;
    }

    public Double getqTotalPower() {
        return qTotalPower;
    }

    public void setqTotalPower(Double qTotalPower) {
        this.qTotalPower = qTotalPower;
    }

    public Double getReactiveDeductionPower() {
        return reactiveDeductionPower;
    }

    public void setReactiveDeductionPower(Double reactiveDeductionPower) {
        this.reactiveDeductionPower = reactiveDeductionPower;
    }

    public Double getReactiveLineLossPower() {
        return reactiveLineLossPower;
    }

    public void setReactiveLineLossPower(Double reactiveLineLossPower) {
        this.reactiveLineLossPower = reactiveLineLossPower;
    }

    public Double getReactiveTransformerLoss() {
        return reactiveTransformerLoss;
    }

    public void setReactiveTransformerLoss(Double reactiveTransformerLoss) {
        this.reactiveTransformerLoss = reactiveTransformerLoss;
    }

    public Double getReactiveWritePower() {
        return reactiveWritePower;
    }

    public void setReactiveWritePower(Double reactiveWritePower) {
        this.reactiveWritePower = reactiveWritePower;
    }

    public Double getReactiveWritePower_1() {
        return reactiveWritePower_1;
    }

    public void setReactiveWritePower_1(Double reactiveWritePower_1) {
        this.reactiveWritePower_1 = reactiveWritePower_1;
    }

    public Double getReactiveWritePower_2() {
        return reactiveWritePower_2;
    }

    public void setReactiveWritePower_2(Double reactiveWritePower_2) {
        this.reactiveWritePower_2 = reactiveWritePower_2;
    }

    public Double getReactiveWritePower_3() {
        return reactiveWritePower_3;
    }

    public void setReactiveWritePower_3(Double reactiveWritePower_3) {
        this.reactiveWritePower_3 = reactiveWritePower_3;
    }

    public Double getReactiveWritePower_4() {
        return reactiveWritePower_4;
    }

    public void setReactiveWritePower_4(Double reactiveWritePower_4) {
        this.reactiveWritePower_4 = reactiveWritePower_4;
    }

    public Double getReverseReactiveWritePower() {
        return reverseReactiveWritePower;
    }

    public void setReverseReactiveWritePower(Double reverseReactiveWritePower) {
        this.reverseReactiveWritePower = reverseReactiveWritePower;
    }

    public Double getShareCapacity() {
        return shareCapacity;
    }

    public void setShareCapacity(Double shareCapacity) {
        this.shareCapacity = shareCapacity;
    }


    public Double getTotalPower() {
        return totalPower;
    }

    public void setTotalPower(Double totalPower) {
        this.totalPower = totalPower;
    }

    public BigDecimal getOweMoney() {
        return oweMoney;
    }

    public void setOweMoney(BigDecimal oweMoney) {
        this.oweMoney = oweMoney;
    }

    public BigDecimal getPunishMoney() {
        return punishMoney;
    }

    public void setPunishMoney(BigDecimal punishMoney) {
        this.punishMoney = punishMoney;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public BigDecimal getVolumeCharge() {
        return volumeCharge;
    }

    public void setVolumeCharge(BigDecimal volumeCharge) {
        this.volumeCharge = volumeCharge;
    }

    public Long getWriteSectId() {
        return writeSectId;
    }

    public void setWriteSectId(Long writeSectId) {
        this.writeSectId = writeSectId;
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

    public BigDecimal getYcys() {
        return ycys;
    }

    public void setYcys(BigDecimal ycys) {
        this.ycys = ycys;
    }

    public String getStartNumString() {
        return startNumString;
    }

    public void setStartNumString(String startNumString) {
        this.startNumString = startNumString;
    }

    public String getEndNumString() {
        return endNumString;
    }

    public void setEndNumString(String endNumString) {
        this.endNumString = endNumString;
    }

    public BigDecimal getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(BigDecimal refundMoney) {
        this.refundMoney = refundMoney;
    }
}
