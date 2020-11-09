package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.bson.conversions.Bson;
import org.fms.billing.common.webapp.domain.BillDomain;
import org.fms.billing.common.webapp.domain.mongo.MeterMoneyMongoDomain;
import org.fms.billing.common.webapp.domain.mongo.MeterMongoDomain;
import org.fms.billing.server.web.config.MongoCollectionConfig;

import com.mongodb.client.model.Filters;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.mongo.dao.MongoDAOSupport;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;

@TransactionDAO
public class BillDAO extends AbstractTransactionDAOSupport implements MongoDAOSupport {

	public List<BillDomain> findByWhere(BillDomain billDomain) {
		return getPersistanceManager().find(getNamespace() + ".findByWhere", billDomain);
	}

	public int insert(BillDomain billDomain) {
		return getPersistanceManager().insert(getNamespace() + ".insert", billDomain);
	};

	public int update(BillDomain billDomain) {
		return getPersistanceManager().update(getNamespace() + ".update", billDomain);
	}

	public List<MeterMoneyMongoDomain> getMeterMoneyByMeterIds(List<Long> meterIds, String mon) {

		List<MeterMoneyMongoDomain> meterMoneyMongoDomains = findMany(
				getCollectionName(mon, MongoCollectionConfig.ELECTRIC_METER_MONEY.name()), new MongoFindFilter() {

					@Override
					public Bson filter() {
						return Filters.in("meterId", meterIds);
					}
				}, MeterMoneyMongoDomain.class);

		return meterMoneyMongoDomains;
	}

	public List<MeterMongoDomain> getMeterByMeterIds(List<Long> meterIds, String mon) {

		List<MeterMongoDomain> meterMongoDomains = findMany(
				getCollectionName(mon, MongoCollectionConfig.ELECTRIC_METER.name()), new MongoFindFilter() {

					@Override
					public Bson filter() {
						return Filters.in("id", meterIds);
					}
				}, MeterMongoDomain.class);

		return meterMongoDomains;
	}
}
