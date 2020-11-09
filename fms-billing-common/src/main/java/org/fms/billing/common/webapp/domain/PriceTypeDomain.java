package org.fms.billing.common.webapp.domain;

import java.util.Date;
import java.util.List;

import org.fms.billing.common.webapp.entity.ManagerParamEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;

//电价类型表
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceTypeDomain extends ManagerParamEntity implements MybatisEntity {
    @TablePrimaryKey
    private Long id;
    //电价名称
    private String priceName;
    //电压等级
    private Integer voltLevelType;
    //用电类别代码
    private Integer elecType;
    //执行日期
    private Date validDate;
    //对外标志
    private Integer isPublic;
    //电价分类
    private Integer priceClass;
    private Date createDate;
    private String operator;
    private String remark;
    private Integer status;

    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public Integer getVoltLevelType() {
        return voltLevelType;
    }

    public void setVoltLevelType(Integer voltLevelType) {
        this.voltLevelType = voltLevelType;
    }

    public Integer getElecType() {
        return elecType;
    }

    public void setElecType(Integer elecType) {
        this.elecType = elecType;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Integer getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Integer isPublic) {
        this.isPublic = isPublic;
    }

    public Integer getPriceClass() {
        return priceClass;
    }

    public void setPriceClass(Integer priceClass) {
        this.priceClass = priceClass;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
