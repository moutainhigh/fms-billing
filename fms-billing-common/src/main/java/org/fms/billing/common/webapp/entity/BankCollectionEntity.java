package org.fms.billing.common.webapp.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.mybatis.MybatisEntity;

//银行托收参数实体
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankCollectionEntity extends ManagerParamEntity implements MybatisEntity {
    private Long settlementId;
    private String settlementNo;
    private String settlementName;
    private Integer mon;
    private Integer sn;
    private String bankNo;
    private String opendingBank;
    private BigDecimal oweMoney;
    private BigDecimal punishMoney;
    private BigDecimal collectTotalMoney;
    private Long writeSectId;
    private String writeSectNo;
    private Long businessPlaceCode;
    private String ids;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Long getBusinessPlaceCode() {
        return businessPlaceCode;
    }

    public void setBusinessPlaceCode(Long businessPlaceCode) {
        this.businessPlaceCode = businessPlaceCode;
    }

    public Long getWriteSectId() {
        return writeSectId;
    }

    public void setWriteSectId(Long writeSectId) {
        this.writeSectId = writeSectId;
    }

    public Long getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(Long settlementId) {
        this.settlementId = settlementId;
    }

    public BigDecimal getCollectTotalMoney() {
        if(collectTotalMoney==null){
            return BigDecimal.ZERO;
        }else{
            return collectTotalMoney.setScale(2, RoundingMode.HALF_UP);
        }
    }

    public void setCollectTotalMoney(BigDecimal collectTotalMoney) {
        this.collectTotalMoney = collectTotalMoney;
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


    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getOpendingBank() {
        return opendingBank;
    }

    public void setOpendingBank(String opendingBank) {
        this.opendingBank = opendingBank;
    }

    public BigDecimal getOweMoney() {
        if(oweMoney==null){
            return BigDecimal.ZERO;
        }else{
            return oweMoney.setScale(2, RoundingMode.HALF_UP);
        }
    }

    public void setOweMoney(BigDecimal oweMoney) {
        this.oweMoney = oweMoney;
    }

    public BigDecimal getPunishMoney() {
        if(punishMoney==null){
            return BigDecimal.ZERO;
        }else{
            return punishMoney.setScale(2, RoundingMode.HALF_UP);
        }
    }

    public void setPunishMoney(BigDecimal punishMoney) {
        this.punishMoney = punishMoney;
    }

    public String getWriteSectNo() {
        return writeSectNo;
    }

    public void setWriteSectNo(String writeSectNo) {
        this.writeSectNo = writeSectNo;
    }

}
