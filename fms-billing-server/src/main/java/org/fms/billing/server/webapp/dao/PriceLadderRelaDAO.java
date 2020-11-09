package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.fms.billing.common.webapp.domain.PriceLadderRelaDomain;
import org.fms.billing.server.web.config.MongoCollectionConfig;

import com.riozenc.titanTool.annotation.PaginationSupport;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.mongo.dao.MongoDAOSupport;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;
import com.riozenc.titanTool.spring.webapp.dao.BaseDAO;

@TransactionDAO
public class PriceLadderRelaDAO extends AbstractTransactionDAOSupport implements MongoDAOSupport,BaseDAO<PriceLadderRelaDomain> {
	@PaginationSupport
	@Override
	public List<PriceLadderRelaDomain> findByWhere(PriceLadderRelaDomain priceLadderRelaDomain){
		return getPersistanceManager().find(getNamespace() + ".findByWhere", priceLadderRelaDomain);
	}
	@Override
	public int insert(PriceLadderRelaDomain priceLadderRelaDomain) {
		return getPersistanceManager().insert(getNamespace() + ".insert", priceLadderRelaDomain);
	};
	@Override
	public int update(PriceLadderRelaDomain priceLadderRelaDomain) {
		return getPersistanceManager().update(getNamespace() + ".update", priceLadderRelaDomain);
	}
	@Override
	public int delete(PriceLadderRelaDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().delete(getNamespace() + ".delete", t);
	}
	@Override
	public PriceLadderRelaDomain findByKey(PriceLadderRelaDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().load(getNamespace() + ".findByKey", t);
	}
	public List<PriceLadderRelaDomain> findMongoPriceLadderRela(String mon){
		List<PriceLadderRelaDomain> priceLadderRelaDomains = findMany(
				getCollectionName(mon, MongoCollectionConfig.PRICE_LADDER_RELA.name()),
				new MongoDAOSupport.MongoFindFilter() {
					@Override
					public Bson filter() {
						return new Document();
					}
				}, PriceLadderRelaDomain.class);
		return priceLadderRelaDomains;
	}

}
