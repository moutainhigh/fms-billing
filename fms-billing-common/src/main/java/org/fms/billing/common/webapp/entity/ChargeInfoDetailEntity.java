package org.fms.billing.common.webapp.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.mybatis.MybatisEntity;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChargeInfoDetailEntity extends ManagerParamEntity implements MybatisEntity {
    private Long id;
    private Integer mon;
    private Integer sn;
    //应收凭证号
    private String arrearageNo;
    //计量点ID
    private Long meterId;
    //抵扣余额
    private BigDecimal deductionBalance;
    //应缴电费（欠费）
    private BigDecimal arrears;
    //实收电费
    private BigDecimal factMoney;
    //实收违约金
    private BigDecimal factPunish;
    //预收电费
    private BigDecimal factPre;
    //收费日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date payDate;
    //进帐日期
    private Date inDate;
    //对账标记
    private Integer balanceFlag;
    //缴费方式
    private Integer fChargeMode;
    //缴费帐号
    private Long settlementId;
    //结清标志
    private Integer paidFlag;
    //结算户号
    private String relaUserNo;
    //工作单号
    private String appNo;
    private Long operator;
    private String remark;
    private String flowNo;
    private Integer status;
    //结账标志
    private Integer jzFlag;

    private BigDecimal factTotal;

    private Long meterMoneyId;

    private Integer chargeMode;

    private Integer ysTypeCode;


    private Long businessPlaceCode;

    private Long writeSectId;

    private String meterNo;

    private String meterName;

    private String settlementNo;


    private String settlementName;

    private String priceName;

    private String yfphm;

    private String ids;


    private String notePath;

    private Integer isPrint;

    private BigDecimal totalAmount;

    private String columnName;

    private String sumFlow;

    private String sumSettlement;

    private BigDecimal factAdvance;

    private BigDecimal volumeCharge;//电度电费
    private BigDecimal powerRateMoney;//力调
    private BigDecimal basicMoney;//基本电费
    private BigDecimal addMoney1;//国家水利工程建设基金
    private BigDecimal addMoney2;//城市公用事业附加费
    private BigDecimal addMoney3;//大中型水库移民后期扶持资金
    private BigDecimal addMoney4;//地方水库移民后期扶持资金
    private BigDecimal addMoney5;//可再生能源电价附加
    private BigDecimal addMoney6;//农网还贷资金
    private BigDecimal addMoney7;//农村电网维护费
    private BigDecimal addMoney8;//价格调节基金
    private BigDecimal addMoney9;

    public BigDecimal getVolumeCharge() {
        return volumeCharge;
    }

    public void setVolumeCharge(BigDecimal volumeCharge) {
        this.volumeCharge = volumeCharge;
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

    public BigDecimal getFactAdvance() {
        return factAdvance;
    }

    public void setFactAdvance(BigDecimal factAdvance) {
        this.factAdvance = factAdvance;
    }

    public String getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(String sumFlow) {
        this.sumFlow = sumFlow;
    }

    public String getSumSettlement() {
        return sumSettlement;
    }

    public void setSumSettlement(String sumSettlement) {
        this.sumSettlement = sumSettlement;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Integer getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(Integer isPrint) {
        this.isPrint = isPrint;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getNotePath() {
        return notePath;
    }

    public void setNotePath(String notePath) {
        this.notePath = notePath;
    }

    public Long getWriteSectId() {
        return writeSectId;
    }

    public void setWriteSectId(Long writeSectId) {
        this.writeSectId = writeSectId;
    }

    public String getYfphm() {
        return yfphm;
    }

    public void setYfphm(String yfphm) {
        this.yfphm = yfphm;
    }

    public Integer getYsTypeCode() {
        return ysTypeCode;
    }

    public void setYsTypeCode(Integer ysTypeCode) {
        this.ysTypeCode = ysTypeCode;
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

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public Long getBusinessPlaceCode() {
        return businessPlaceCode;
    }

    public void setBusinessPlaceCode(Long businessPlaceCode) {
        this.businessPlaceCode = businessPlaceCode;
    }

    public Integer getChargeMode() {
        return chargeMode;
    }

    public void setChargeMode(Integer chargeMode) {
        this.chargeMode = chargeMode;
    }

    public Long getMeterMoneyId() {
        return meterMoneyId;
    }

    public void setMeterMoneyId(Long meterMoneyId) {
        this.meterMoneyId = meterMoneyId;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public Date getPayDate() {
        return payDate;
    }
    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }
    public Date getInDate() {
        return inDate;
    }
    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }
    public Integer getBalanceFlag() {
        return balanceFlag;
    }
    public void setBalanceFlag(Integer balanceFlag) {
        this.balanceFlag = balanceFlag;
    }

    public BigDecimal getDeductionBalance() {
        return deductionBalance;
    }

    public void setDeductionBalance(BigDecimal deductionBalance) {
        this.deductionBalance = deductionBalance;
    }

    public BigDecimal getArrears() {
        return arrears;
    }

    public void setArrears(BigDecimal arrears) {
        this.arrears = arrears;
    }

    public BigDecimal getFactMoney() {
        return factMoney;
    }

    public void setFactMoney(BigDecimal factMoney) {
        this.factMoney = factMoney;
    }

    public BigDecimal getFactPunish() {
        return factPunish;
    }

    public void setFactPunish(BigDecimal factPunish) {
        this.factPunish = factPunish;
    }

    public BigDecimal getFactPre() {
        return factPre;
    }

    public void setFactPre(BigDecimal factPre) {
        this.factPre = factPre;
    }

    public BigDecimal getFactTotal() {
        return factTotal;
    }

    public void setFactTotal(BigDecimal factTotal) {
        this.factTotal = factTotal;
    }

    public Integer getfChargeMode() {
        return fChargeMode;
    }

    public void setfChargeMode(Integer fChargeMode) {
        this.fChargeMode = fChargeMode;
    }

    public Long getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(Long settlementId) {
        this.settlementId = settlementId;
    }

    public Integer getPaidFlag() {
        return paidFlag;
    }
    public void setPaidFlag(Integer paidFlag) {
        this.paidFlag = paidFlag;
    }
    public String getRelaUserNo() {
        return relaUserNo;
    }
    public void setRelaUserNo(String relaUserNo) {
        this.relaUserNo = relaUserNo;
    }
    public String getAppNo() {
        return appNo;
    }
    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
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

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public Integer getJzFlag() {
        return jzFlag;
    }

    public void setJzFlag(Integer jzFlag) {
        this.jzFlag = jzFlag;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
