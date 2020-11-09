package org.fms.billing.common.webapp.domain.mongo;

import java.util.Date;
import java.util.List;

public class TransformerMeterRelDomain {
    private Long id;// ID ID bigint TRUE FALSE TRUE
    private Long transformerId;// 变压器ID TRANSFORMER_ID bigint FALSE FALSE FALSE
    private Long meterId;// 计量点ID METER_ID bigint FALSE FALSE FALSE
    private Byte msType;// 计量方式 MS_TYPE smallint(2) 2 FALSE FALSE FALSE
    private Date createDate;// 创建时间 CREATE_DATE datetime FALSE FALSE FALSE
    private String remark;// 备注 REMARK varchar(256) 256 FALSE FALSE FALSE
    private Byte status;// 状态 STATUS smallint FALSE FALSE FALSE

    private Integer mon;// 电费年月
    private List meterIds;
    private List transformerIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTransformerId() {
        return transformerId;
    }

    public void setTransformerId(Long transformerId) {
        this.transformerId = transformerId;
    }

    public Long getMeterId() {
        return meterId;
    }

    public void setMeterId(Long meterId) {
        this.meterId = meterId;
    }

    public Byte getMsType() {
        return msType;
    }

    public void setMsType(Byte msType) {
        this.msType = msType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getMon() {
        return mon;
    }

    public void setMon(Integer mon) {
        this.mon = mon;
    }

    public List getMeterIds() {
        return meterIds;
    }

    public void setMeterIds(List meterIds) {
        this.meterIds = meterIds;
    }

    public List getTransformerIds() {
        return transformerIds;
    }

    public void setTransformerIds(List transformerIds) {
        this.transformerIds = transformerIds;
    }
}
