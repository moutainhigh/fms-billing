package org.fms.billing.server.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.mongo.MeterMeterAssetsRelDomain;
import org.fms.billing.common.webapp.service.IMeterMeterAssetsRelService;
import org.fms.billing.server.webapp.dao.MeterMeterAssetsRelDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeterMeterAssetsRelServiceIpml implements IMeterMeterAssetsRelService {

    @Autowired
    private MeterMeterAssetsRelDAO meterMeterAssetsRelDAO;

    @Override
    public List<MeterMeterAssetsRelDomain> mongoFind(MeterMeterAssetsRelDomain rel) {
        return meterMeterAssetsRelDAO.mongoFind(rel);
    }


}
