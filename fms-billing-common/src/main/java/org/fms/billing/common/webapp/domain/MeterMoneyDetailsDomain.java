package org.fms.billing.common.webapp.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.fms.billing.common.webapp.domain.mongo.MeterMoneyMongoDomain;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MeterMoneyDetailsDomain {
    private String settlementNo;// 结算户编号 SETTLEMENT_NO varchar(16) 16 FALSE FALSE FALSE
    private String settlementName;// 结算人 SETTLEMENT_NAME varchar(64) 64 FALSE FALSE FALSE
    private String address;
    private Integer mon;// 电费年月 MON int FALSE FALSE FALSE
    private Byte sn;// 当月次数 SN smallint FALSE FALSE FALSE
    private BigDecimal checkingEnergy; // 考核电量
    private BigDecimal totalPower;//计费电量
    private BigDecimal amount;// 总电费 AMOUNT decimal(14,4) 14 4 FALSE FALSE FALSE
    private BigDecimal refundMoney;//退补电费
    private BigDecimal totalMoney;//应收电费
    private BigDecimal powerRateMoney;// 力调电费 POWER_RATE_MONEY decimal(14,4) 14 4 FALSE FALSE FALSE
    private BigDecimal basicMoney;// 基本电费 BASIC_MONEY decimal(14,4) 14 4 FALSE FALSE FALSE
    private BigDecimal volumeCharge;// 电度电费 VOLUME_CHARGE decimal(14,4) 14 4 FALSE FALSE FALSE
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
     * 价格调节基金
     */
    private BigDecimal addMoney9;
    private String accountantName; // 核算员姓名
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date accountingDate; // 核算日期
    private String publisherName; // 发行人姓名
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishingDate; // 发行日期

    List<MeterMoneyMongoDomain> meterMoneyMongoDomains;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<MeterMoneyMongoDomain> getMeterMoneyMongoDomains() {
        return meterMoneyMongoDomains;
    }

    public void setMeterMoneyMongoDomains(List<MeterMoneyMongoDomain> meterMoneyMongoDomains) {
        this.meterMoneyMongoDomains = meterMoneyMongoDomains;
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

    public BigDecimal getCheckingEnergy() {
        return checkingEnergy;
    }

    public void setCheckingEnergy(BigDecimal checkingEnergy) {
        this.checkingEnergy = checkingEnergy;
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

    public BigDecimal getAddMoney9() {
        return addMoney9;
    }

    public void setAddMoney9(BigDecimal addMoney9) {
        this.addMoney9 = addMoney9;
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
}
