package org.fms.billing.common.webapp.entity;

import com.riozenc.titanTool.mybatis.MybatisEntity;

//调整抄表序号实体
public class MeterWriteSnEntity extends ManagerParamEntity implements MybatisEntity {
    private Long id;
    private Long rowNum;
    private Long writeSectId;
    private String writeSectNo;
    private String writeSectName;
    private String userNo; //户号;
    private String userName;//户名
    private String address; //用户地址,
    private String meterNo;// 计量点号,
    private String meterName;//计量点名称,
    private String setAddress;//计量点地址,
    private String meterType; //计量点类别,
    private Long functionCode; //功能代码,
    private Long factorNum; //倍率,
    private Long writeSn; //抄表序号,
    private Long tsFlag;//分时标志,
    private Long powerDirection;//功率方向,
    private Long phaseSeq;//相位,
    private Long meterAssetsId; //表计id,
    private Long meterId;//计量点id,
    private String meterAssetsNo; //资产号;
    private String madeNo;//表号;
    private Integer meterSn;

    public Integer getMeterSn() {
        return meterSn;
    }

    public void setMeterSn(Integer meterSn) {
        this.meterSn = meterSn;
    }

    public Long getWriteSectId() {
        return writeSectId;
    }

    public void setWriteSectId(Long writeSectId) {
        this.writeSectId = writeSectId;
    }

    public Long getRowNum() {
        return rowNum;
    }

    public void setRowNum(Long rowNum) {
        this.rowNum = rowNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getSetAddress() {
        return setAddress;
    }

    public void setSetAddress(String setAddress) {
        this.setAddress = setAddress;
    }

    public String getMeterType() {
        return meterType;
    }

    public void setMeterType(String meterType) {
        this.meterType = meterType;
    }

    public Long getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(Long functionCode) {
        this.functionCode = functionCode;
    }

    public Long getFactorNum() {
        return factorNum;
    }

    public void setFactorNum(Long factorNum) {
        this.factorNum = factorNum;
    }

    public Long getWriteSn() {
        return writeSn;
    }

    public void setWriteSn(Long writeSn) {
        this.writeSn = writeSn;
    }

    public Long getTsFlag() {
        return tsFlag;
    }

    public void setTsFlag(Long tsFlag) {
        this.tsFlag = tsFlag;
    }

    public Long getPowerDirection() {
        return powerDirection;
    }

    public void setPowerDirection(Long powerDirection) {
        this.powerDirection = powerDirection;
    }

    public Long getPhaseSeq() {
        return phaseSeq;
    }

    public void setPhaseSeq(Long phaseSeq) {
        this.phaseSeq = phaseSeq;
    }

    public Long getMeterAssetsId() {
        return meterAssetsId;
    }

    public void setMeterAssetsId(Long meterAssetsId) {
        this.meterAssetsId = meterAssetsId;
    }

    public Long getMeterId() {
        return meterId;
    }

    public void setMeterId(Long meterId) {
        this.meterId = meterId;
    }

    public String getMeterAssetsNo() {
        return meterAssetsNo;
    }

    public void setMeterAssetsNo(String meterAssetsNo) {
        this.meterAssetsNo = meterAssetsNo;
    }

    public String getMadeNo() {
        return madeNo;
    }

    public void setMadeNo(String madeNo) {
        this.madeNo = madeNo;
    }

    public boolean equals(Object obj) {
        if (obj instanceof MeterWriteSnEntity) {
            MeterWriteSnEntity u = (MeterWriteSnEntity) obj;
            return this.id.equals(u.id);
        }
        return super.equals(obj);
    }
}