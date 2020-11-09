/**
 * Author : czy
 * Date : 2019年12月3日 下午6:45:50
 * Title : com.riozenc.billing.webapp.domain.mongo.MeterDomain.java
 *
 **/
package org.fms.billing.common.webapp.domain.mongo;

import java.math.BigDecimal;

public class MeterMoneySumMongoDomain {

    private BigDecimal powerRateMoney; //力调电费
    private BigDecimal basicMoney; //基本电费
    private BigDecimal calCapacity; //计费容量
    private BigDecimal totalPower;// 有功总电量
    private BigDecimal volumeCharge; //电度电费
    private BigDecimal amount; //总电费
    private BigDecimal surcharges; //附加费

    public BigDecimal getPowerRateMoney() {
        return powerRateMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setPowerRateMoney(BigDecimal powerRateMoney) {
        this.powerRateMoney = powerRateMoney;
    }

    public BigDecimal getBasicMoney() {
        return basicMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setBasicMoney(BigDecimal basicMoney) {
        this.basicMoney = basicMoney;
    }

    public BigDecimal getCalCapacity() {
        return calCapacity.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setCalCapacity(BigDecimal calCapacity) {
        this.calCapacity = calCapacity;
    }

    public BigDecimal getTotalPower() {
        return totalPower.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setTotalPower(BigDecimal totalPower) {
        this.totalPower = totalPower;
    }

    public BigDecimal getVolumeCharge() {
        return volumeCharge.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setVolumeCharge(BigDecimal volumeCharge) {
        this.volumeCharge = volumeCharge;
    }

    public BigDecimal getAmount() {
        return amount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getSurcharges() {
        return surcharges.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setSurcharges(BigDecimal surcharges) {
        this.surcharges = surcharges;
    }
}
