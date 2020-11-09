package org.fms.billing.server.webapp.action;

import java.util.List;

import org.fms.billing.common.webapp.domain.PriceDataTransformDomain;
import org.fms.billing.common.webapp.service.IPriceDataTransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.spring.web.http.HttpResultPagination;

/**
 * czy
 */
@Controller
@RequestMapping("priceDataTransform")
public class PriceDataTransformController {
    @Autowired
    @Qualifier("priceDataTransformImpl")
    private IPriceDataTransformService iPriceDataTransformService;

    @RequestMapping("findPriceDataTransform")
    @ResponseBody
    public HttpResultPagination findPriceDataTransform(@RequestBody(required = false) String priceDataTransformJson) {

        PriceDataTransformDomain priceDataTransformDomain = GsonUtils.readValue(priceDataTransformJson, PriceDataTransformDomain.class);

        List<PriceDataTransformDomain> priceDataTransformDomains =
                iPriceDataTransformService.findPriceDataTransform(priceDataTransformDomain);

        return new HttpResultPagination(priceDataTransformDomain,priceDataTransformDomains);
    }


}
