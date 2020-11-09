package org.fms.billing.server.webapp.action;

import java.util.Date;
import java.util.List;

import org.fms.billing.common.webapp.domain.PriceItemDomain;
import org.fms.billing.common.webapp.service.IPriceItemService;
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
@RequestMapping("PriceItem")
public class PriceItemController {
    @Autowired
    @Qualifier("priceItemServiceImpl")
    private IPriceItemService iPriceItemService;

    @RequestMapping("select")
    @ResponseBody
    public HttpResultPagination findByWhere(@RequestBody String priceItemDomainJson) {
        PriceItemDomain priceItemDomain = GsonUtils.readValue(priceItemDomainJson,
                PriceItemDomain.class);
        List<PriceItemDomain> priceItemDomains =
                iPriceItemService.findByWhere(priceItemDomain);
        return new HttpResultPagination(priceItemDomain, priceItemDomains);
    }

    @RequestMapping("priceItemDorp")
    @ResponseBody
    public List<PriceItemDomain> priceItemDorp(@RequestBody String priceItemDomainJson) {
        PriceItemDomain priceItemDomain = GsonUtils.readValue(priceItemDomainJson,
                PriceItemDomain.class);
        List<PriceItemDomain> priceItemDomains =
                iPriceItemService.findByWhere(priceItemDomain);
        return priceItemDomains;
    }


    @RequestMapping("insert")
    @ResponseBody
    public HttpResult insert(@RequestBody String priceItemDomainJson) {
        PriceItemDomain priceItemDomain = GsonUtils.readValue(priceItemDomainJson,
                PriceItemDomain.class);
        HttpResult httpResult = new HttpResult();
        List<PriceItemDomain> priceItemDomains = iPriceItemService.findByWhere(priceItemDomain);
        if (priceItemDomains != null && priceItemDomains.size() > 0) {
        	httpResult.setStatusCode(HttpResult.ERROR);
        	httpResult.setMessage("添加失败,已存在相同的电价条目");
            return httpResult;
        }
        priceItemDomain.setCreateDate(new Date());
        priceItemDomain.setOperator(priceItemDomain.getManagerId());
        int num = iPriceItemService.insert(priceItemDomain);
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
    public HttpResult update(@RequestBody String priceItemDomainJson) {
        PriceItemDomain priceItemDomain = GsonUtils.readValue(priceItemDomainJson,
                PriceItemDomain.class);
        HttpResult httpResult = new HttpResult();
        PriceItemDomain priceItem = new PriceItemDomain();
        priceItem.setPriceItemName(priceItemDomain.getPriceItemName());
        priceItem.setPriceItemType(priceItemDomain.getPriceItemType());
        priceItem.setStatus(priceItemDomain.getStatus());
        List<PriceItemDomain> priceItemDomains = iPriceItemService.findByWhere(priceItem);
        if (priceItemDomains != null && priceItemDomains.size() > 0) {
        	httpResult.setStatusCode(HttpResult.ERROR);
        	httpResult.setMessage("更新失败,已存在相同的电价条目");
            return httpResult;
        }
        priceItemDomain.setCreateDate(new Date());
        priceItemDomain.setOperator(priceItemDomain.getManagerId());
        int num = iPriceItemService.update(priceItemDomain);
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
    public HttpResult delete(@RequestBody String priceItemDomainJson) {
    	HttpResult httpResult = new HttpResult();
        PriceItemDomain priceItemDomain = GsonUtils.readValue(priceItemDomainJson,
                PriceItemDomain.class);
        priceItemDomain.setCreateDate(new Date());
        priceItemDomain.setStatus(0);
        priceItemDomain.setOperator(priceItemDomain.getManagerId());
        int num=iPriceItemService.update(priceItemDomain);
        if (num >= 1) {
        	httpResult.setStatusCode(HttpResult.SUCCESS);

        } else {
        	httpResult.setStatusCode(HttpResult.ERROR);
            httpResult.setMessage("删除失败");
        }
        return httpResult;
    }
}
