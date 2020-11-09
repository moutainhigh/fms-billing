package org.fms.billing.common.webapp.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.mybatis.MybatisEntity;
import com.riozenc.titanTool.mybatis.pagination.Page;
@JsonIgnoreProperties(ignoreUnknown = true)
public class MeterPageEntity extends Page implements MybatisEntity{
    private String meterId;
    private String settlementId;
    private Long operator;
    private Integer jzFlag;
    private Integer eraseOption;
    private Integer erasePerson;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endDate;

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

    public Integer getErasePerson() {
        return erasePerson;
    }

    public void setErasePerson(Integer erasePerson) {
        this.erasePerson = erasePerson;
    }

    public Integer getEraseOption() {
        return eraseOption;
    }

    public void setEraseOption(Integer eraseOption) {
        this.eraseOption = eraseOption;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public Integer getJzFlag() {
        return jzFlag;
    }

    public void setJzFlag(Integer jzFlag) {
        this.jzFlag = jzFlag;
    }

    public String getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(String settlementId) {
        this.settlementId = settlementId;
    }
}
