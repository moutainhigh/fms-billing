package org.fms.billing.server.webapp.action;

import java.util.List;

import org.fms.billing.common.webapp.domain.mongo.TransformerMeterRelDomain;
import org.fms.billing.common.webapp.service.ITransformerMeterRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("transMeterRel")
public class TransformerMeterRelController {

    @Autowired
    @Qualifier("transformerMeterRelServiceImpl")
    private ITransformerMeterRelService transformerMeterRelService;

    @ResponseBody
    @RequestMapping("getTransMeterRel")
    public List<TransformerMeterRelDomain> getTransMeterRel(@RequestBody TransformerMeterRelDomain transformerMeterRel) {
        List<TransformerMeterRelDomain> list = transformerMeterRelService.getTransMeterRel(transformerMeterRel);
        return list;
    }
}
