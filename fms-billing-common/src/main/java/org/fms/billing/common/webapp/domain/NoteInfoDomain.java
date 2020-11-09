package org.fms.billing.common.webapp.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.mybatis.MybatisEntity;

//发票实体
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoteInfoDomain implements MybatisEntity{
    private Long id;
    private Long chargeInfoId;
    private String notePrintNo;
    private Long printMan;
    private Date printDate;
    private Integer mon;
    private String address;
    private Byte connectBank;
    private String bankNo;
    private BigDecimal factMoney;
    private BigDecimal thisBalance;
    private BigDecimal lastBalance;
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
    private BigDecimal punishMoney;
    private BigDecimal powerRateMoney;
    private BigDecimal basicMoney;
    private BigDecimal arrears;
    private String settlementNo;
    private String settlementName;
    private Long settlementId;
    private String meterItem;
    private Long priceTypeId;
    private String priceName;
    private String noteFlowNo;
    private BigDecimal factPre;
    private Long meterId;
    private Integer sn;
    private Integer isPrint;
    private String  yfphm;
    private String  yfpdm;
    private String printManName;
    private String bankName;
    private String collectorId;
    private String notePath;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date payDate;
    private Long meterMoneyId;
    private Long ysTypeCode;
    private List<Long> ids;
    private Integer invoiceType;
    private List<Long> chargeInfoIds;
    private BigDecimal factTotal;
    private String cuscc;
    private String openindBank;
    private String settlementPhone;
    private Long fChargeMode;

    public Long getfChargeMode() {
        return fChargeMode;
    }

    public void setfChargeMode(Long fChargeMode) {
        this.fChargeMode = fChargeMode;
    }

    public String getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(String collectorId) {
        this.collectorId = collectorId;
    }

    public String getSettlementPhone() {
        return settlementPhone;
    }

    public void setSettlementPhone(String settlementPhone) {
        this.settlementPhone = settlementPhone;
    }

    public String getOpenindBank() {
        return openindBank;
    }

    public void setOpenindBank(String openindBank) {
        this.openindBank = openindBank;
    }

    public String getCuscc() {
        return cuscc;
    }

    public void setCuscc(String cuscc) {
        this.cuscc = cuscc;
    }

    public BigDecimal getFactTotal() {
        return factTotal;
    }

    public void setFactTotal(BigDecimal factTotal) {
        this.factTotal = factTotal;
    }

    public List<Long> getChargeInfoIds() {
        return chargeInfoIds;
    }

    public void setChargeInfoIds(List<Long> chargeInfoIds) {
        this.chargeInfoIds = chargeInfoIds;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Long getYsTypeCode() {
        return ysTypeCode;
    }

    public void setYsTypeCode(Long ysTypeCode) {
        this.ysTypeCode = ysTypeCode;
    }

    public Long getMeterMoneyId() {
        return meterMoneyId;
    }

    public void setMeterMoneyId(Long meterMoneyId) {
        this.meterMoneyId = meterMoneyId;
    }

    public BigDecimal getLadder1Money() {
        if(ladder1Money==null){
            return BigDecimal.ZERO;
        }else{
            return ladder1Money.setScale(2,RoundingMode.HALF_UP);
        }
    }

    public void setLadder1Money(BigDecimal ladder1Money) {
        if(ladder1Money==null){
            this.ladder1Money=BigDecimal.ZERO;
        }else{
            this.ladder1Money = ladder1Money.setScale(2,RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getLadder2Money() {
        if(ladder2Money==null){
            return BigDecimal.ZERO;
        }else{
            return ladder2Money.setScale(2,RoundingMode.HALF_UP);
        }
    }

    public void setLadder2Money(BigDecimal ladder2Money) {
        if(ladder2Money==null){
            this.ladder2Money=BigDecimal.ZERO;
        }else{
            this.ladder2Money = ladder2Money.setScale(2,RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getLadder3Money() {
        if(ladder3Money==null){
            return BigDecimal.ZERO;
        }else{
            return ladder3Money.setScale(2,RoundingMode.HALF_UP);
        }
    }

    public void setLadder3Money(BigDecimal ladder3Money) {
        if(ladder3Money==null){
            this.ladder3Money=BigDecimal.ZERO;
        }else{
            this.ladder3Money = ladder3Money.setScale(2,RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getLadder4Money() {
        if(ladder4Money==null){
            return BigDecimal.ZERO;
        }else{
            return ladder4Money.setScale(2,RoundingMode.HALF_UP);
        }
    }

    public void setLadder4Money(BigDecimal ladder4Money) {
        if(ladder4Money==null){
            this.ladder4Money=BigDecimal.ZERO;
        }else{
            this.ladder4Money = ladder4Money.setScale(2,RoundingMode.HALF_UP);
        }
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getNotePath() {
        return notePath;
    }

    public void setNotePath(String notePath) {
        this.notePath = notePath;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getPrintManName() {
        return printManName;
    }

    public void setPrintManName(String printManName) {
        this.printManName = printManName;
    }

    public Integer getIsPrint() {
		return isPrint;
	}

	public void setIsPrint(Integer isPrint) {
		this.isPrint = isPrint;
	}

	public String getYfphm() {
		return yfphm;
	}

	public void setYfphm(String yfphm) {
		this.yfphm = yfphm;
	}

	public String getYfpdm() {
		return yfpdm;
	}

	public void setYfpdm(String yfpdm) {
		this.yfpdm = yfpdm;
	}

	public Long getMeterId() {
        return meterId;
    }

    public void setMeterId(Long meterId) {
        this.meterId = meterId;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public BigDecimal getFactPre() {
        if(factPre==null){
            return BigDecimal.ZERO;
        }else{
            return factPre;
        }
    }

    public void setFactPre(BigDecimal factPre) {
        this.factPre = factPre;
    }

    public String getNoteFlowNo() {
        return noteFlowNo;
    }

    public void setNoteFlowNo(String noteFlowNo) {
        this.noteFlowNo = noteFlowNo;
    }

    public Long getPriceTypeId() {
        return priceTypeId;
    }

    public void setPriceTypeId(Long priceTypeId) {
        this.priceTypeId = priceTypeId;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public String getMeterItem() {
        return meterItem;
    }

    public void setMeterItem(String meterItem) {
        this.meterItem = meterItem;
    }

    public Long getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(Long settlementId) {
        this.settlementId = settlementId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChargeInfoId() {
        return chargeInfoId;
    }

    public void setChargeInfoId(Long chargeInfoId) {
        this.chargeInfoId = chargeInfoId;
    }

    public String getNotePrintNo() {
        return notePrintNo;
    }

    public void setNotePrintNo(String notePrintNo) {
        this.notePrintNo = notePrintNo;
    }

    public Long getPrintMan() {
        return printMan;
    }

    public void setPrintMan(Long printMan) {
        this.printMan = printMan;
    }

    public Date getPrintDate() {
        return printDate;
    }

    public void setPrintDate(Date printDate) {
        this.printDate = printDate;
    }

    public Integer getMon() {
        return mon;
    }

    public void setMon(Integer mon) {
        this.mon = mon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Byte getConnectBank() {
        return connectBank;
    }

    public void setConnectBank(Byte connectBank) {
        this.connectBank = connectBank;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }


    public BigDecimal getThisBalance() {
        return thisBalance;
    }

    public void setThisBalance(BigDecimal thisBalance) {
        this.thisBalance = thisBalance;
    }

    public BigDecimal getLastBalance() {
        if(lastBalance==null){
            return  BigDecimal.ZERO;
        }else{
            return lastBalance.setScale(2,RoundingMode.HALF_UP);
        }
    }

    public void setLastBalance(BigDecimal lastBalance) {
        this.lastBalance = lastBalance;
    }

    public BigDecimal getLadder1Limit() {
        if(ladder1Limit==null){
            return BigDecimal.ZERO;
        }else{
            return ladder1Limit.setScale(0, RoundingMode.HALF_UP);
        }
    }

    public void setLadder1Limit(BigDecimal ladder1Limit) {
        if(ladder1Limit==null){
            this.ladder1Limit=BigDecimal.ZERO;
        }else{
            this.ladder1Limit = ladder1Limit.setScale(0,RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getLadder1Power() {
        if(ladder1Power==null){
            return BigDecimal.ZERO;
        }else{
            return ladder1Power.setScale(0, RoundingMode.HALF_UP);
        }
    }

    public void setLadder1Power(BigDecimal ladder1Power) {
        if(ladder1Power==null){
            this.ladder1Power=BigDecimal.ZERO;
        }else{
            this.ladder1Power = ladder1Power.setScale(0,RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getLadder2Limit() {
        if(ladder2Limit==null){
            return BigDecimal.ZERO;
        }else{
            return ladder2Limit.setScale(0, RoundingMode.HALF_UP);
        }
    }

    public void setLadder2Limit(BigDecimal ladder2Limit) {
        if(ladder2Limit==null){
            this.ladder2Limit=BigDecimal.ZERO;
        }else{
            this.ladder2Limit = ladder2Limit.setScale(0,RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getLadder2Power() {
        if(ladder2Power==null){
            return BigDecimal.ZERO;
        }else{
            return ladder2Power.setScale(0, RoundingMode.HALF_UP);
        }
    }

    public void setLadder2Power(BigDecimal ladder2Power) {
        if(ladder2Power==null){
            this.ladder2Power=BigDecimal.ZERO;
        }else{
            this.ladder2Power = ladder2Power.setScale(0,RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getLadder3Limit() {
        if(ladder3Limit==null){
            return BigDecimal.ZERO;
        }else{
            return ladder3Limit.setScale(0, RoundingMode.HALF_UP);
        }
    }

    public void setLadder3Limit(BigDecimal ladder3Limit) {
        if(ladder3Limit==null){
            this.ladder3Limit=BigDecimal.ZERO;
        }else{
            this.ladder3Limit = ladder3Limit.setScale(0,RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getLadder3Power() {
        if(ladder3Power==null){
            return BigDecimal.ZERO;
        }else{
            return ladder3Power.setScale(0, RoundingMode.HALF_UP);
        }
    }

    public void setLadder3Power(BigDecimal ladder3Power) {
        if(ladder3Power==null){
            this.ladder3Power=BigDecimal.ZERO;
        }else{
            this.ladder3Power = ladder3Power.setScale(0,RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getLadder4Limit() {
        if(ladder4Limit==null){
            return BigDecimal.ZERO;
        }else{
            return ladder4Limit.setScale(0, RoundingMode.HALF_UP);
        }
    }

    public void setLadder4Limit(BigDecimal ladder4Limit) {
        if(ladder4Limit==null){
            this.ladder4Limit=BigDecimal.ZERO;
        }else{
            this.ladder4Limit = ladder4Limit.setScale(0,RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getLadder4Power() {
        if(ladder4Power==null){
            return BigDecimal.ZERO;
        }else{
            return ladder4Power.setScale(0, RoundingMode.HALF_UP);
        }
    }

    public void setLadder4Power(BigDecimal ladder4Power) {
        if(ladder4Power==null){
            this.ladder4Power=BigDecimal.ZERO;
        }else{
            this.ladder4Power = ladder4Power.setScale(0,RoundingMode.HALF_UP);
        }
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

    public BigDecimal getFactMoney() {
        if(factMoney==null){
            return BigDecimal.ZERO;
        }else{
            return factMoney;
        }
    }

    public void setFactMoney(BigDecimal factMoney) {
        if(factMoney==null){
            this.factMoney=BigDecimal.ZERO;
        }else{
            this.factMoney = factMoney.setScale(2,RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getPunishMoney() {
        if(punishMoney==null){
            return BigDecimal.ZERO;
        }else{
            return punishMoney.setScale(2,RoundingMode.HALF_UP);
        }
    }

    public void setPunishMoney(BigDecimal punishMoney) {
        if(punishMoney==null){
            this.punishMoney=BigDecimal.ZERO;
        }else{
            this.punishMoney = punishMoney.setScale(2,RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getPowerRateMoney() {
        if(powerRateMoney==null){
            return BigDecimal.ZERO;
        }else{
            return powerRateMoney.setScale(2,RoundingMode.HALF_UP);
        }
    }

    public void setPowerRateMoney(BigDecimal powerRateMoney) {
        if(powerRateMoney==null){
            this.powerRateMoney=BigDecimal.ZERO;
        }else{
            this.powerRateMoney = powerRateMoney.setScale(2,RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getBasicMoney() {
        if(basicMoney==null){
            return BigDecimal.ZERO;
        }else{
            return basicMoney.setScale(2,RoundingMode.HALF_UP);
        }
    }

    public void setBasicMoney(BigDecimal basicMoney) {
        if(basicMoney==null){
            this.basicMoney=BigDecimal.ZERO;
        }else{
            this.basicMoney = basicMoney.setScale(2,RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getArrears() {
        if(arrears==null){
            return BigDecimal.ZERO;
        }else{
            return arrears.setScale(2,RoundingMode.HALF_UP);
        }
    }

    public void setArrears(BigDecimal arrears) {
        if(arrears==null){
            this.arrears=BigDecimal.ZERO;
        }else{
            this.arrears = arrears.setScale(2,RoundingMode.HALF_UP);
        }
    }
}
