package org.fms.billing.common.webapp.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//结算户电费明细实体
@JsonIgnoreProperties(ignoreUnknown = true)
public class SettlementDetailEntity {
    private String settlementNo; //结算户号
    private String settlementName;//结算户名
    private String businessPlaceCodeName; //营业区域
    private String address; //地址
    private Integer mon; //月
    private Integer sn;
    private Integer meterSn; //表序号
    private BigDecimal startNum; //起码
    private BigDecimal endNum;// 止码
    private String priceName;
    private Integer factorNum;//倍率
    private BigDecimal addPower;//增减电量
    private BigDecimal activeTransformerLossPower;//变损
    private BigDecimal totalPower;//总电量
    private BigDecimal powerRateMoney;//力率电费
    private BigDecimal basicMoney;//基本电费
    private BigDecimal volumeCharge;//电度电费
    private BigDecimal refundMoney;//退补电费
    private BigDecimal totalMoney;//总电费
    private Long meterId;
    private BigDecimal activeWritePower;
    private BigDecimal surcharges;
    private String meterNo; //计量点号
    private BigDecimal addMoney8;//价调基金
    private BigDecimal shouldMoney;//应收电费
    private String writeSectionNo;//抄表区段
    private String bankNo;

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

    public String getWriteSectionNo() {
        return writeSectionNo;
    }

    public void setWriteSectionNo(String writeSectionNo) {
        this.writeSectionNo = writeSectionNo;
    }

    public BigDecimal getShouldMoney() {
        return shouldMoney;
    }

    public void setShouldMoney(BigDecimal shouldMoney) {
        this.shouldMoney = shouldMoney;
    }

    public BigDecimal getAddMoney8() {
        return addMoney8;
    }

    public void setAddMoney8(BigDecimal addMoney8) {
        this.addMoney8 = addMoney8;
    }


    public BigDecimal getSurcharges() {
        return surcharges;
    }

    public void setSurcharges(BigDecimal surcharges) {
        this.surcharges = surcharges;
    }

    public BigDecimal getActiveWritePower() {
        return activeWritePower;
    }

    public void setActiveWritePower(BigDecimal activeWritePower) {
        this.activeWritePower = activeWritePower;
    }

    public Long getMeterId() {
        return meterId;
    }

    public void setMeterId(Long meterId) {
        this.meterId = meterId;
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

    public String getBusinessPlaceCodeName() {
        return businessPlaceCodeName;
    }

    public void setBusinessPlaceCodeName(String businessPlaceCodeName) {
        this.businessPlaceCodeName = businessPlaceCodeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Integer getMeterSn() {
        return meterSn;
    }

    public void setMeterSn(Integer meterSn) {
        this.meterSn = meterSn;
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

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public Integer getFactorNum() {
        return factorNum;
    }

    public void setFactorNum(Integer factorNum) {
        this.factorNum = factorNum;
    }

    public BigDecimal getAddPower() {
        return addPower;
    }

    public void setAddPower(BigDecimal addPower) {
        this.addPower = addPower;
    }

    public BigDecimal getActiveTransformerLossPower() {
        return activeTransformerLossPower;
    }

    public void setActiveTransformerLossPower(BigDecimal activeTransformerLossPower) {
        this.activeTransformerLossPower = activeTransformerLossPower;
    }

    public BigDecimal getTotalPower() {
        return totalPower;
    }

    public void setTotalPower(BigDecimal totalPower) {
        this.totalPower = totalPower;
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

    public BigDecimal getVolumeCharge() {
        return volumeCharge;
    }

    public void setVolumeCharge(BigDecimal volumeCharge) {
        this.volumeCharge = volumeCharge;
    }

    public BigDecimal getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(BigDecimal refundMoney) {
        this.refundMoney = refundMoney;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
}
