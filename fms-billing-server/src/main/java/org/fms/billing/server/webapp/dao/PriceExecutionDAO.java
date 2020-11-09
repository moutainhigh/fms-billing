/**
 * Author : czy
 * Date : 2019年6月24日 上午9:03:56
 * Title : com.riozenc.billing.webapp.dao.PriceExecutionDAO.java
 **/
package org.fms.billing.server.webapp.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.fms.billing.common.webapp.domain.PriceExecutionDomain;
import org.fms.billing.server.web.config.MongoCollectionConfig;

import com.riozenc.titanTool.annotation.PaginationSupport;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.mongo.dao.MongoDAOSupport;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;
import com.riozenc.titanTool.spring.webapp.dao.BaseDAO;

@TransactionDAO
public class PriceExecutionDAO extends AbstractTransactionDAOSupport implements MongoDAOSupport,BaseDAO<PriceExecutionDomain> {


    @Override
    public int insert(PriceExecutionDomain t) {
        // TODO Auto-generated method stub
        return getPersistanceManager().insert(getNamespace() + ".insert", t);
    }

    @Override
    public int delete(PriceExecutionDomain t) {
        // TODO Auto-generated method stub
        return getPersistanceManager().delete(getNamespace() + ".delete", t);
    }

    @Override
    public int update(PriceExecutionDomain t) {
        // TODO Auto-generated method stub
        return getPersistanceManager().update(getNamespace() + ".update", t);
    }

    @Override
    public PriceExecutionDomain findByKey(PriceExecutionDomain t) {
        // TODO Auto-generated method stub
        return getPersistanceManager().load(getNamespace() + ".findByKey", t);
    }

    @Override
    @PaginationSupport
    public List<PriceExecutionDomain> findByWhere(PriceExecutionDomain t) {
        // TODO Auto-generated method stub
        return getPersistanceManager().find(getNamespace() + ".findByWhere", t);
    }

    public List<PriceExecutionDomain> findByNoPage(PriceExecutionDomain t) {
        // TODO Auto-generated method stub
        return getPersistanceManager().find(getNamespace() + ".findByWhere", t);
    }

    public Map<Long, List<PriceExecutionDomain>> findMongoPriceExecution(String mon){
        List<PriceExecutionDomain> priceList = findMany(
                getCollectionName(mon, MongoCollectionConfig.PRICE_EXECUTION.name()),
                new MongoDAOSupport.MongoFindFilter() {
                    @Override
                    public Bson filter() {
                        return new Document();
                    }
                }, PriceExecutionDomain.class);
        Map<Long, List<PriceExecutionDomain>> priceMap=priceList.stream()
                .collect(Collectors.groupingBy(PriceExecutionDomain::getPriceTypeId));
        return priceMap;
    }


    public List<PriceExecutionDomain> findTimeLadderPrice(){
        return getPersistanceManager().find(getNamespace() +
                ".findTimeLadderPrice", null);

    }
}
