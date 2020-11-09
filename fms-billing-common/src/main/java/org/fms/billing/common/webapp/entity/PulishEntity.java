package org.fms.billing.common.webapp.entity;

import java.util.List;

import org.fms.billing.common.webapp.domain.AppMoneyRecall;
import org.fms.billing.common.webapp.domain.ArrearageDomain;
import org.fms.billing.common.webapp.domain.ChargeDomain;
import org.fms.billing.common.webapp.domain.MeterMoneyDomain;
import org.fms.billing.common.webapp.domain.PreChargeDomain;
import org.fms.billing.common.webapp.domain.WriteFilesDomain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.mybatis.MybatisEntity;
//发行接收实体
@JsonIgnoreProperties(ignoreUnknown = true)
public class PulishEntity implements MybatisEntity {
    private List<ArrearageDomain> arrearageDomains;
    private List<WriteFilesDomain> writeFilesDomains;
    private List<MeterMoneyDomain> meterMoneyDomains;
    private List<PreChargeDomain> preChargeDomains;
    private List<ChargeDomain> chargeDomains;
    private List<AppMoneyRecall> appMoneyRecalls;
    private List<Long> meterIds;
    private Long managerId;
    private Integer mon;
    private Integer sn;
    private Integer fChargeMode;
    private List<PreChargeDomain> lastPreChargeDomains;

    public List<PreChargeDomain> getLastPreChargeDomains() {
        return lastPreChargeDomains;
    }

    public void setLastPreChargeDomains(List<PreChargeDomain> lastPreChargeDomains) {
        this.lastPreChargeDomains = lastPreChargeDomains;
    }

    public Integer getfChargeMode() {
        return fChargeMode;
    }

    public void setfChargeMode(Integer fChargeMode) {
        this.fChargeMode = fChargeMode;
    }

    public List<AppMoneyRecall> getAppMoneyRecalls() {
        return appMoneyRecalls;
    }

    public void setAppMoneyRecalls(List<AppMoneyRecall> appMoneyRecalls) {
        this.appMoneyRecalls = appMoneyRecalls;
    }

    public List<ChargeDomain> getChargeDomains() {
        return chargeDomains;
    }

    public void setChargeDomains(List<ChargeDomain> chargeDomains) {
        this.chargeDomains = chargeDomains;
    }

    public List<PreChargeDomain> getPreChargeDomains() {
        return preChargeDomains;
    }

    public void setPreChargeDomains(List<PreChargeDomain> preChargeDomains) {
        this.preChargeDomains = preChargeDomains;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public List<Long> getMeterIds() {
        return meterIds;
    }

    public void setMeterIds(List<Long> meterIds) {
        this.meterIds = meterIds;
    }

    public Integer getMon() {
        return mon;
    }

    public void setMon(Integer mon) {
        this.mon = mon;
    }

    public List<ArrearageDomain> getArrearageDomains() {
        return arrearageDomains;
    }

    public void setArrearageDomains(List<ArrearageDomain> arrearageDomains) {
        this.arrearageDomains = arrearageDomains;
    }

    public List<WriteFilesDomain> getWriteFilesDomains() {
        return writeFilesDomains;
    }

    public void setWriteFilesDomains(List<WriteFilesDomain> writeFilesDomains) {
        this.writeFilesDomains = writeFilesDomains;
    }

    public List<MeterMoneyDomain> getMeterMoneyDomains() {
        return meterMoneyDomains;
    }

    public void setMeterMoneyDomains(List<MeterMoneyDomain> meterMoneyDomains) {
        this.meterMoneyDomains = meterMoneyDomains;
    }
}
