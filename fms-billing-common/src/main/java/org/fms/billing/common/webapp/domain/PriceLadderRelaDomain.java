package org.fms.billing.common.webapp.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;
import com.riozenc.titanTool.mybatis.pagination.Page;

public class PriceLadderRelaDomain extends Page implements MybatisEntity{
    @TablePrimaryKey
    private Long id; //id
    private Long priceExecutionId; //电价id
    private String priceName; //电价名称
    private Integer ladderSn; //阶梯
    private Integer ladderValue; //阶梯值
    private BigDecimal price;//电价
    private Date createDate;//创建日期
    private Integer status;// 状态标识

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPriceExecutionId() {
        return priceExecutionId;
    }

    public void setPriceExecutionId(Long priceExecutionId) {
        this.priceExecutionId = priceExecutionId;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public Integer getLadderSn() {
        return ladderSn;
    }

    public void setLadderSn(Integer ladderSn) {
        this.ladderSn = ladderSn;
    }

    public Integer getLadderValue() {
        return ladderValue;
    }

    public void setLadderValue(Integer ladderValue) {
        this.ladderValue = ladderValue;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
