package org.fms.billing.common.webapp.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.mybatis.MybatisEntity;

// 电费明细查询前后台交互实体
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChargeInfoEntity extends ManagerParamEntity implements MybatisEntity {

    private String settlementNo;
    private String operator;
    private String dept;
    private String writeSect;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startPayDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endPayDate;
    private String fChargeMode;
    private String startMon;
    private String endMon;
    private Integer isPrint;
    private Integer invoiceType;
    private List<Long> settlementIds;
    private String groupBy;
    private List<Long> businessPlaceCodes;
    private Integer mon;

    public Integer getMon() {
        return mon;
    }

    public void setMon(Integer mon) {
        this.mon = mon;
    }

    public List<Long> getBusinessPlaceCodes() {
        return businessPlaceCodes;
    }

    public void setBusinessPlaceCodes(List<Long> businessPlaceCodes) {
        this.businessPlaceCodes = businessPlaceCodes;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public List<Long> getSettlementIds() {
        return settlementIds;
    }

    public void setSettlementIds(List<Long> settlementIds) {
        this.settlementIds = settlementIds;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Integer getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(Integer isPrint) {
        this.isPrint = isPrint;
    }

    public String getSettlementNo() {
        return settlementNo;
    }

    public void setSettlementNo(String settlementNo) {
        this.settlementNo = settlementNo;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getWriteSect() {
        return writeSect;
    }

    public void setWriteSect(String writeSect) {
        this.writeSect = writeSect;
    }

    public Date getStartPayDate() {
        return startPayDate;
    }

    public void setStartPayDate(Date startPayDate) {
        this.startPayDate = startPayDate;
    }

    public Date getEndPayDate() {
        return endPayDate;
    }

    public void setEndPayDate(Date endPayDate) {
        this.endPayDate = endPayDate;
    }

    public String getfChargeMode() {
        return fChargeMode;
    }

    public void setfChargeMode(String fChargeMode) {
        this.fChargeMode = fChargeMode;
    }

    public String getStartMon() {
        return startMon;
    }

    public void setStartMon(String startMon) {
        this.startMon = startMon;
    }

    public String getEndMon() {
        return endMon;
    }

    public void setEndMon(String endMon) {
        this.endMon = endMon;
    }
}
