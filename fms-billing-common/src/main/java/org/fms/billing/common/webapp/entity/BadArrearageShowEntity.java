package org.fms.billing.common.webapp.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.mybatis.MybatisEntity;
@JsonIgnoreProperties(ignoreUnknown = true)
public class BadArrearageShowEntity extends ManagerParamEntity implements MybatisEntity {
    // 计量点ID
    private Long meterId;
    private BigDecimal totalPower;

    private BigDecimal punishMoney;
    private Long oldIsSettle;
    private Long newIsSettle;
    private String oldIsSettleName;
    private String newIsSettleName;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date endDate;
    private String operator;
    // 电费年月
    private Integer mon;
    // 电费年月
    private Integer sn;
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
    // 欠费明细报表查询 电费年月-开始年月
    private Integer startMon;

    // 欠费明细报表查询 电费年月-结束年月
    private Integer endMon;

    private Long userId;
    private String userNo;
    private String userName;
    private Byte userType;//
    private String deptName;
    private String setAddress;
    private Integer elecTypeCode; // 用电类别
    private String settlementPhone;// 结算人电话
    private String settlementName;// 结算人名称
    private String settlementNo;// 结算人名称
    private Byte chargeModeType;// 收费方式
    private String operatorName;

    private List<Long> businessPlaceCodes;
    private List<Long> writeSectIds;
    private List<Long> writorIds;

    private String chargeModeTypeName;
    private String elecTypeName;
    private String userTypeName;
    private String businessPlaceName;
    private String chargeModeName;
    private List<Long> settlementIds;

    public String getOldIsSettleName() {
        return oldIsSettleName;
    }

    public void setOldIsSettleName(String oldIsSettleName) {
        this.oldIsSettleName = oldIsSettleName;
    }

    public String getNewIsSettleName() {
        return newIsSettleName;
    }

    public void setNewIsSettleName(String newIsSettleName) {
        this.newIsSettleName = newIsSettleName;
    }

    public List<Long> getSettlementIds() {
        return settlementIds;
    }

    public void setSettlementIds(List<Long> settlementIds) {
        this.settlementIds = settlementIds;
    }

    public Long getMeterId() {
        return meterId;
    }

    public void setMeterId(Long meterId) {
        this.meterId = meterId;
    }

    public BigDecimal getTotalPower() {
        return totalPower;
    }

    public void setTotalPower(BigDecimal totalPower) {
        this.totalPower = totalPower;
    }

    public BigDecimal getPunishMoney() {
        return punishMoney;
    }

    public void setPunishMoney(BigDecimal punishMoney) {
        this.punishMoney = punishMoney;
    }

    public Long getOldIsSettle() {
        return oldIsSettle;
    }

    public void setOldIsSettle(Long oldIsSettle) {
        this.oldIsSettle = oldIsSettle;
    }

    public Long getNewIsSettle() {
        return newIsSettle;
    }

    public void setNewIsSettle(Long newIsSettle) {
        this.newIsSettle = newIsSettle;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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


    public BigDecimal getOweMoney() {
        return oweMoney;
    }

    public void setOweMoney(BigDecimal oweMoney) {
        this.oweMoney = oweMoney;
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

    public Long getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(Long settlementId) {
        this.settlementId = settlementId;
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

    public String getSettlementPhone() {
        return settlementPhone;
    }

    public void setSettlementPhone(String settlementPhone) {
        this.settlementPhone = settlementPhone;
    }

    public String getSettlementName() {
        return settlementName;
    }

    public void setSettlementName(String settlementName) {
        this.settlementName = settlementName;
    }

    public String getSettlementNo() {
        return settlementNo;
    }

    public void setSettlementNo(String settlementNo) {
        this.settlementNo = settlementNo;
    }

    public Byte getChargeModeType() {
        return chargeModeType;
    }

    public void setChargeModeType(Byte chargeModeType) {
        this.chargeModeType = chargeModeType;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
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

    public String getChargeModeTypeName() {
        return chargeModeTypeName;
    }

    public void setChargeModeTypeName(String chargeModeTypeName) {
        this.chargeModeTypeName = chargeModeTypeName;
    }

    public String getElecTypeName() {
        return elecTypeName;
    }

    public void setElecTypeName(String elecTypeName) {
        this.elecTypeName = elecTypeName;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getBusinessPlaceName() {
        return businessPlaceName;
    }

    public void setBusinessPlaceName(String businessPlaceName) {
        this.businessPlaceName = businessPlaceName;
    }

    public String getChargeModeName() {
        return chargeModeName;
    }

    public void setChargeModeName(String chargeModeName) {
        this.chargeModeName = chargeModeName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
