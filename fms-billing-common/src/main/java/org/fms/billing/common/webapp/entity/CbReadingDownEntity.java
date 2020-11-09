package org.fms.billing.common.webapp.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//抄表器上装
@JsonIgnoreProperties(ignoreUnknown = true)
public class CbReadingDownEntity {
    private Byte functionCode;
    //户号
    private String userNo;
    //抄表号
    private Long  writeSn;
    // 电价
    private BigDecimal price;
    //表号
    private String meterAssetsNo;
    // 户名
    private String userName;
    //地址
    private String address;
    // 倍率
    private BigDecimal factorNum;
    //时段
    private Byte timeSeg;
    //上月表码
    private BigDecimal startNum;
    //上月电量
    private BigDecimal writePower;
    //mongo抄表记录id
    private String _id;

    private Long userId;

    private Long meterAssetsId;

    private Long priceTypeId;

    private Long meterId;

    private Integer mon;

    private Integer sn;

    private Long writeSectionId;

    private BigDecimal endNum;

    private String writeSectionNo;

    private Integer writeFlag;

    private Integer status;

    private Integer meterSn;

    private BigDecimal diffNum;

    private String writeTime;

    private Date writeDate;

    private Long inputMan;

    public String getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(String writeTime) {
        this.writeTime = writeTime;
    }

    public Date getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
    }

    public BigDecimal getDiffNum() {
        return diffNum;
    }

    public void setDiffNum(BigDecimal diffNum) {
        this.diffNum = diffNum;
    }

    public Integer getMeterSn() {
        return meterSn;
    }

    public void setMeterSn(Integer meterSn) {
        this.meterSn = meterSn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWriteFlag() {
        return writeFlag;
    }

    public void setWriteFlag(Integer writeFlag) {
        this.writeFlag = writeFlag;
    }

    public String getWriteSectionNo() {
        return writeSectionNo;
    }

    public void setWriteSectionNo(String writeSectionNo) {
        this.writeSectionNo = writeSectionNo;
    }

    public BigDecimal getEndNum() {
        return endNum;
    }

    public void setEndNum(BigDecimal endNum) {
        this.endNum = endNum;
    }

    public Long getWriteSn() {
        return writeSn;
    }

    public void setWriteSn(Long writeSn) {
        this.writeSn = writeSn;
    }

    public Long getWriteSectionId() {
        return writeSectionId;
    }

    public void setWriteSectionId(Long writeSectionId) {
        this.writeSectionId = writeSectionId;
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

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public Byte getTimeSeg() {
        return timeSeg;
    }

    public void setTimeSeg(Byte timeSeg) {
        this.timeSeg = timeSeg;
    }

    public Byte getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(Byte functionCode) {
        this.functionCode = functionCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMeterAssetsId() {
        return meterAssetsId;
    }

    public void setMeterAssetsId(Long meterAssetsId) {
        this.meterAssetsId = meterAssetsId;
    }

    public Long getPriceTypeId() {
        return priceTypeId;
    }

    public void setPriceTypeId(Long priceTypeId) {
        this.priceTypeId = priceTypeId;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getMeterAssetsNo() {
        return meterAssetsNo;
    }

    public void setMeterAssetsNo(String meterAssetsNo) {
        this.meterAssetsNo = meterAssetsNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getFactorNum() {
        return factorNum;
    }

    public void setFactorNum(BigDecimal factorNum) {
        this.factorNum = factorNum;
    }


    public BigDecimal getStartNum() {
        return startNum;
    }

    public void setStartNum(BigDecimal startNum) {
        this.startNum = startNum;
    }

    public BigDecimal getWritePower() {
        return writePower;
    }

    public void setWritePower(BigDecimal writePower) {
        this.writePower = writePower;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Long getInputMan() {
        return inputMan;
    }

    public void setInputMan(Long inputMan) {
        this.inputMan = inputMan;
    }
}
