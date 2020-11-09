package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.mongo.MeterMeterAssetsRelDomain;

public interface IMeterMeterAssetsRelService {
    public List<MeterMeterAssetsRelDomain> mongoFind(MeterMeterAssetsRelDomain rel);
}
