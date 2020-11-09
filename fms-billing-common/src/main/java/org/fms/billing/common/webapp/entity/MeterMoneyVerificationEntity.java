package org.fms.billing.common.webapp.entity;

import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.MeterMoneyDomain;

//电费核查实体
public class MeterMoneyVerificationEntity extends MeterDomain {
    private MeterMoneyDomain meterMoney;

    public MeterMoneyDomain getMeterMoney() {
        return meterMoney;
    }

    public void setMeterMoney(MeterMoneyDomain meterMoney) {
        this.meterMoney = meterMoney;
    }
}
