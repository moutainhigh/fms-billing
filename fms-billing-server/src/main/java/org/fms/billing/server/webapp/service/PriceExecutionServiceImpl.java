/**
 * Author : czy
 * Date : 2019年6月24日 上午9:01:43
 * Title : com.riozenc.billing.webapp.service.impl.PriceExecutionServiceImpl.java
 **/
package org.fms.billing.server.webapp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.fms.billing.common.webapp.domain.PriceExecutionDomain;
import org.fms.billing.common.webapp.service.IPriceExecutionService;
import org.fms.billing.server.web.config.MongoCollectionConfig;
import org.fms.billing.server.webapp.dao.PriceExecutionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.WriteModel;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;
import com.riozenc.titanTool.mongo.dao.MongoDAOSupport;
import com.riozenc.titanTool.spring.web.client.TitanTemplate;
import com.riozenc.titanTool.spring.web.http.HttpResult;

@TransactionService
public class PriceExecutionServiceImpl implements IPriceExecutionService, MongoDAOSupport {

    @Autowired
    private TitanTemplate titanTemplate;

    @TransactionDAO
    private PriceExecutionDAO priceExecutionDAO;

    @Override
    public int insert(PriceExecutionDomain t) {
        // TODO Auto-generated method stub
        return priceExecutionDAO.insert(t);
    }

    @Override
    public int delete(PriceExecutionDomain t) {
        // TODO Auto-generated method stub
        return priceExecutionDAO.delete(t);
    }

    @Override
    public int update(PriceExecutionDomain t) {
        // TODO Auto-generated method stub
        return priceExecutionDAO.update(t);
    }

    @Override
    public PriceExecutionDomain findByKey(PriceExecutionDomain t) {
        // TODO Auto-generated method stub
        return priceExecutionDAO.findByKey(t);
    }

    @Override
    public List<PriceExecutionDomain> findByWhere(PriceExecutionDomain t) {
        // TODO Auto-generated method stub
        return priceExecutionDAO.findByWhere(t);
    }

    @Override
    public List<PriceExecutionDomain> findByNoPage(PriceExecutionDomain t) {
        return priceExecutionDAO.findByNoPage(t);
    }

    @Override
    public Map<Long, List<PriceExecutionDomain>> findMongoPriceExecution(String mon) {
        return priceExecutionDAO.findMongoPriceExecution(mon);
    }

    //将电价同步本月
    @Override
    public HttpResult synCurrentPrice() {
        HttpResult httpResult = new HttpResult();

        //获得全部电价
        List<PriceExecutionDomain> updatePriceLists = findByNoPage(null);

        List<WriteModel<Document>> updateResult =
                updateMany(toDocuments(updatePriceLists, new MongoDAOSupport.ToDocumentCallBack<PriceExecutionDomain>() {
                            @Override
                            public PriceExecutionDomain call(PriceExecutionDomain t) {
                                // TODO Auto-generated method stub
                                t.setCreateDate(new Date());
                                return t;
                            }
                        }),
                        new MongoDAOSupport.MongoUpdateFilter() {
                            @Override
                            public Bson filter(Document document) {
                                return Filters.eq("id", document.get("id"));
                            }
                        }, true);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //取当前月份
        try {
            String currentMon = titanTemplate.postJson("TITAN-CONFIG", "titan-config/sysCommConfig/getCurrentMon",
                    httpHeaders, null, String.class);

            BulkWriteResult bulkWriteResult = getCollection(currentMon,
                    MongoCollectionConfig.PRICE_EXECUTION.name()).bulkWrite(updateResult);
            httpResult.setStatusCode(HttpResult.SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            httpResult.setStatusCode(HttpResult.ERROR);
            httpResult.setMessage("同步电价出错");
        }

        return httpResult;
    }
    //电价值 分时电价取平段 阶梯电价取第一阶梯
    public List<PriceExecutionDomain> findTimeLadderPrice(){
        return priceExecutionDAO.findTimeLadderPrice();
    }


}
