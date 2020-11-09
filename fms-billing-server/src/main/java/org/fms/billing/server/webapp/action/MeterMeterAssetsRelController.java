package org.fms.billing.server.webapp.action;

import java.util.List;

import org.fms.billing.common.webapp.domain.mongo.MeterMeterAssetsRelDomain;
import org.fms.billing.common.webapp.service.IMeterMeterAssetsRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("meterMeterAssetsRel")
public class MeterMeterAssetsRelController {

    @Autowired
    @Qualifier("meterMeterAssetsRelServiceIpml")
    private IMeterMeterAssetsRelService meterMeterAssetsRelService;


    @RequestMapping("getRel")
    @ResponseBody
    public List<MeterMeterAssetsRelDomain> getMeterMeterAssetsRel(@RequestBody MeterMeterAssetsRelDomain rel) {

        List<MeterMeterAssetsRelDomain> list = meterMeterAssetsRelService.mongoFind(rel);

        return list;
    }
}
