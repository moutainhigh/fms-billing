package org.fms.billing.common.webapp.entity;

import java.math.BigDecimal;

import com.riozenc.titanTool.mybatis.pagination.Page;

//计算户数量统计
public class SettlementNumEdntity extends Page {
    private Integer mon; //月份
    private String subsName;//变电站名称
    private String subsNo;//变电站名称
    private Long subsId;
    private Long businessPlaceCode;
    private String businessPlaceCodeName; //营业区域名称
    private Long lineId;
    private String lineName;// 线路名称
    private Long transformerId;
    private String transformerNo;
    private String transformerName;// 变压器名称
    private long meterNum;//结算户数量
    private String isPubTypeName;
    private BigDecimal capacity;
    private String transformerModelTypeName;

    private long userNum;
    private Long writeSectId;
    private String writeSectNo;
    private String writeSectName;//抄表区段名
    private Long writorId;
    private String  writorName;//抄表员姓名
    private Long administrativeDivisions;//行政区域
    private String administrativeDivisionsName;//行政区域

    public String getAdministrativeDivisionsName() {
        return administrativeDivisionsName;
    }

    public void setAdministrativeDivisionsName(String administrativeDivisionsName) {
        this.administrativeDivisionsName = administrativeDivisionsName;
    }

    public Long getAdministrativeDivisions() {
        return administrativeDivisions;
    }

    public void setAdministrativeDivisions(Long administrativeDivisions) {
        this.administrativeDivisions = administrativeDivisions;
    }

    public Long getWritorId() {
        return writorId;
    }

    public void setWritorId(Long writorId) {
        this.writorId = writorId;
    }

    public Long getWriteSectId() {
        return writeSectId;
    }

    public void setWriteSectId(Long writeSectId) {
        this.writeSectId = writeSectId;
    }

    public long getUserNum() {
        return userNum;
    }

    public void setUserNum(long userNum) {
        this.userNum = userNum;
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


    public String getWritorName() {
        return writorName;
    }

    public void setWritorName(String writorName) {
        this.writorName = writorName;
    }

    public Long getTransformerId() {
        return transformerId;
    }

    public void setTransformerId(Long transformerId) {
        this.transformerId = transformerId;
    }

    public long getMeterNum() {
        return meterNum;
    }

    public void setMeterNum(long meterNum) {
        this.meterNum = meterNum;
    }

    public String getSubsNo() {
        return subsNo;
    }

    public void setSubsNo(String subsNo) {
        this.subsNo = subsNo;
    }


    public Integer getMon() {
        return mon;
    }

    public void setMon(Integer mon) {
        this.mon = mon;
    }

    public String getSubsName() {
        return subsName;
    }

    public void setSubsName(String subsName) {
        this.subsName = subsName;
    }

    public Long getSubsId() {
        return subsId;
    }

    public void setSubsId(Long subsId) {
        this.subsId = subsId;
    }

    public Long getBusinessPlaceCode() {
        return businessPlaceCode;
    }

    public void setBusinessPlaceCode(Long businessPlaceCode) {
        this.businessPlaceCode = businessPlaceCode;
    }

    public String getBusinessPlaceCodeName() {
        return businessPlaceCodeName;
    }

    public void setBusinessPlaceCodeName(String businessPlaceCodeName) {
        this.businessPlaceCodeName = businessPlaceCodeName;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getTransformerNo() {
        return transformerNo;
    }

    public void setTransformerNo(String transformerNo) {
        this.transformerNo = transformerNo;
    }

    public String getTransformerName() {
        return transformerName;
    }

    public void setTransformerName(String transformerName) {
        this.transformerName = transformerName;
    }

    public String getIsPubTypeName() {
        return isPubTypeName;
    }

    public void setIsPubTypeName(String isPubTypeName) {
        this.isPubTypeName = isPubTypeName;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public String getTransformerModelTypeName() {
        return transformerModelTypeName;
    }

    public void setTransformerModelTypeName(String transformerModelTypeName) {
        this.transformerModelTypeName = transformerModelTypeName;
    }
}
