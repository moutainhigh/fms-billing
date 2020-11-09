package org.fms.billing.server.webapp.action;

import java.util.List;

import org.fms.billing.common.webapp.domain.mongo.TransformerMongoDomain;
import org.fms.billing.common.webapp.service.ITransformerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("transformer")
public class TransformerController {

    @Autowired
    @Qualifier("transformerServiceImpl")
    private ITransformerService transformerService;

    @ResponseBody
    @RequestMapping("getTransformer")
    public List<TransformerMongoDomain> getTransformer(@RequestBody TransformerMongoDomain transformer) {
        List<TransformerMongoDomain> list = transformerService.getTransformer(transformer);
        return list;
    }
}
