package org.fms.billing.server.webapp.action;

import java.util.Date;
import java.util.List;

import org.fms.billing.common.webapp.domain.PriceTypeDomain;
import org.fms.billing.common.webapp.service.IPriceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.spring.web.http.HttpResult;
import com.riozenc.titanTool.spring.web.http.HttpResultPagination;

@Controller
@RequestMapping("PriceType")
public class PriceTypeController {
    @Autowired
    @Qualifier("priceTypeServiceImpl")
    private IPriceTypeService iPriceTypeService;

    //电价下拉值
    @RequestMapping("select")
    @ResponseBody
    public List<PriceTypeDomain> fingByWhere(@RequestBody(required = false) String priceTypeDomainJson) {
        PriceTypeDomain priceTypeDomain = GsonUtils.readValue(priceTypeDomainJson,
                PriceTypeDomain.class);
        List<PriceTypeDomain> priceTypeDomains =iPriceTypeService.priceDrop(priceTypeDomain);
        return priceTypeDomains;
    }

    //电价下拉值
    @RequestMapping("priceAllDrop")
    @ResponseBody
    public List<PriceTypeDomain> priceAllDrop(@RequestBody(required = false) String priceTypeDomainJson) {
        PriceTypeDomain priceTypeDomain = GsonUtils.readValue(priceTypeDomainJson,
                PriceTypeDomain.class);
        List<PriceTypeDomain> priceTypeDomains =
                iPriceTypeService.priceAllDrop(priceTypeDomain);
        return priceTypeDomains;
    }

    @RequestMapping("selectData")
    @ResponseBody
    public HttpResultPagination selectData(@RequestBody(required = false) String priceTypeDomainJson) {
        PriceTypeDomain priceTypeDomain = GsonUtils.readValue(priceTypeDomainJson,
                PriceTypeDomain.class);
        List<PriceTypeDomain> priceTypeDomains = iPriceTypeService.findByWhere(priceTypeDomain);
        return new HttpResultPagination(priceTypeDomain, priceTypeDomains);
    }

    @RequestMapping("insert")
    @ResponseBody
    public HttpResult insert(@RequestBody(required = false) String priceTypeDomainJson) {
        PriceTypeDomain priceTypeDomain = GsonUtils.readValue(priceTypeDomainJson,
                PriceTypeDomain.class);
        priceTypeDomain.setId(null);
        HttpResult httpResult = new HttpResult();
        //校验
        PriceTypeDomain priceName=new PriceTypeDomain();
        priceName.setPriceName(priceTypeDomain.getPriceName());
        priceName.setPriceClass(priceTypeDomain.getPriceClass());
        priceName.setVoltLevelType(priceTypeDomain.getVoltLevelType());
        priceName.setElecType(priceTypeDomain.getElecType());
        List<PriceTypeDomain> priceNames=iPriceTypeService.findByWhere(priceName);
        if(priceNames!=null && priceNames.size()>0){
        	httpResult.setStatusCode(HttpResult.ERROR);
        	httpResult.setMessage("添加失败,已存在相同的电价");
            return httpResult;
        }
        //插入
        priceTypeDomain.setCreateDate(new Date());
        priceTypeDomain.setOperator(priceTypeDomain.getManagerId());
        int num = iPriceTypeService.insert(priceTypeDomain);
        if (num >= 1) {
        	httpResult.setStatusCode(HttpResult.SUCCESS);

        } else {
        	httpResult.setStatusCode(HttpResult.ERROR);
        	httpResult.setMessage("添加失败");
        }
        return httpResult;
    }

    @RequestMapping("update")
    @ResponseBody
    public HttpResult update(@RequestBody(required = false) String priceTypeDomainJson) {
        PriceTypeDomain priceTypeDomain = GsonUtils.readValue(priceTypeDomainJson,
                PriceTypeDomain.class);
        priceTypeDomain.setCreateDate(new Date());
        priceTypeDomain.setOperator(priceTypeDomain.getManagerId());
        int num = iPriceTypeService.update(priceTypeDomain);

        HttpResult httpResult = new HttpResult();
        if (num >= 1) {
        	httpResult.setStatusCode(HttpResult.SUCCESS);

        } else {
        	httpResult.setStatusCode(HttpResult.ERROR);
        	httpResult.setMessage("更新失败");
        }
        return httpResult;
    }

    @RequestMapping("delete")
    @ResponseBody
    public HttpResult delete(@RequestBody(required = false) String priceTypeDomainJson) {
        PriceTypeDomain priceTypeDomain = GsonUtils.readValue(priceTypeDomainJson,
                PriceTypeDomain.class);
        priceTypeDomain.setCreateDate(new Date());
        priceTypeDomain.setStatus(0);
        priceTypeDomain.setOperator(priceTypeDomain.getManagerId());
        int num = iPriceTypeService.update(priceTypeDomain);

        HttpResult httpResult = new HttpResult();
        if (num >= 1) {
        	httpResult.setStatusCode(HttpResult.SUCCESS);

        } else {
        	httpResult.setStatusCode(HttpResult.ERROR);
        	httpResult.setMessage("删除失败");
        }
        return httpResult;
    }
}
