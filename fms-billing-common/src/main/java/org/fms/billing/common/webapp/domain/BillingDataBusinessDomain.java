package org.fms.billing.common.webapp.domain;

import java.util.List;

public class BillingDataBusinessDomain {
    private Integer mon;
    private List<MeterDomain> meterDomains;

    public Integer getMon() {
        return mon;
    }

    public void setMon(Integer mon) {
        this.mon = mon;
    }

    public List<MeterDomain> getMeterDomains() {
        return meterDomains;
    }

    public void setMeterDomains(List<MeterDomain> meterDomains) {
        this.meterDomains = meterDomains;
    }
}
