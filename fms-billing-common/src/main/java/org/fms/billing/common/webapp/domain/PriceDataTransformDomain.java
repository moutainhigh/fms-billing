package org.fms.billing.common.webapp.domain;

import java.math.BigDecimal;

import org.fms.billing.common.webapp.entity.ManagerParamEntity;

import com.riozenc.titanTool.mybatis.MybatisEntity;

//电价中间处理实体
public class PriceDataTransformDomain  extends ManagerParamEntity implements MybatisEntity{
    private String priceTypeId;
    private String elecType;
    private String voltLevelType;
    private String priceName;
    private String timeSeg;
    private BigDecimal contentPrice;
    private BigDecimal addPrice1;
    private BigDecimal addPrice2;
    private BigDecimal addPrice3;
    private BigDecimal addPrice4;
    private BigDecimal addPrice5;
    private BigDecimal addPrice6;
    private BigDecimal addPrice7;
    private BigDecimal addPrice8;
    private BigDecimal price;
    private String isPublic;
    private Byte status;
    private String priceClass;

    public String getPriceTypeId() {
        return priceTypeId;
    }

    public void setPriceTypeId(String priceTypeId) {
        this.priceTypeId = priceTypeId;
    }

    public String getElecType() {
        return elecType;
    }

    public void setElecType(String elecType) {
        this.elecType = elecType;
    }

    public String getVoltLevelType() {
        return voltLevelType;
    }

    public void setVoltLevelType(String voltLevelType) {
        this.voltLevelType = voltLevelType;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public String getTimeSeg() {
        return timeSeg;
    }

    public void setTimeSeg(String timeSeg) {
        this.timeSeg = timeSeg;
    }

    public BigDecimal getContentPrice() {
        return contentPrice;
    }

    public void setContentPrice(BigDecimal contentPrice) {
        this.contentPrice = contentPrice;
    }

    public BigDecimal getAddPrice1() {
        return addPrice1;
    }

    public void setAddPrice1(BigDecimal addPrice1) {
        this.addPrice1 = addPrice1;
    }

    public BigDecimal getAddPrice2() {
        return addPrice2;
    }

    public void setAddPrice2(BigDecimal addPrice2) {
        this.addPrice2 = addPrice2;
    }

    public BigDecimal getAddPrice3() {
        return addPrice3;
    }

    public void setAddPrice3(BigDecimal addPrice3) {
        this.addPrice3 = addPrice3;
    }

    public BigDecimal getAddPrice4() {
        return addPrice4;
    }

    public void setAddPrice4(BigDecimal addPrice4) {
        this.addPrice4 = addPrice4;
    }

    public BigDecimal getAddPrice5() {
        return addPrice5;
    }

    public void setAddPrice5(BigDecimal addPrice5) {
        this.addPrice5 = addPrice5;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public String getPriceClass() {
        return priceClass;
    }

    public void setPriceClass(String priceClass) {
        this.priceClass = priceClass;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public BigDecimal getAddPrice6() {
        return addPrice6;
    }

    public void setAddPrice6(BigDecimal addPrice6) {
        this.addPrice6 = addPrice6;
    }

    public BigDecimal getAddPrice7() {
        return addPrice7;
    }

    public void setAddPrice7(BigDecimal addPrice7) {
        this.addPrice7 = addPrice7;
    }

    public BigDecimal getAddPrice8() {
        return addPrice8;
    }

    public void setAddPrice8(BigDecimal addPrice8) {
        this.addPrice8 = addPrice8;
    }
}
