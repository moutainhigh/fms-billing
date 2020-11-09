package org.fms.billing.server.webapp.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.MeterMoneyDomain;
import org.fms.billing.common.webapp.entity.PulishEntity;
import org.fms.billing.server.web.config.MongoCollectionConfig;

import com.mongodb.client.model.Filters;
import com.riozenc.titanTool.annotation.PaginationSupport;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.mongo.dao.MongoDAOSupport;
import com.riozenc.titanTool.mybatis.pagination.Page;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;

@TransactionDAO
public class MeterMoneyDAO extends AbstractTransactionDAOSupport implements MongoDAOSupport {
	public List<MeterMoneyDomain> findByWhere(MeterMoneyDomain meterMoneyDomain) {
		return getPersistanceManager().find(getNamespace() + ".findByWhere", meterMoneyDomain);
	}

	public List<MeterMoneyDomain> meterMoneyDetailQuery(MeterMoneyDomain meterMoneyDomain) {
		return getPersistanceManager().find(getNamespace() + ".meterMoneyDetailQuery", meterMoneyDomain);
	}

	@PaginationSupport
	public List<MeterMoneyDomain> findByMeterIds(String meterIds) {
		return getPersistanceManager().find(getNamespace() + ".findByMeterIds", meterIds);
	}

	@PaginationSupport
	public List<MeterMoneyDomain> findByMeterIdList(List<Long> meterIds) {
		return getPersistanceManager().find(getNamespace() + ".findByMeterIdList", meterIds);
	}

	@PaginationSupport
	public List<MeterMoneyDomain> selectByMeterMoney(MeterMoneyDomain meterMoneyDomain) {
		return getPersistanceManager().find(getNamespace() + ".selectByMeterMoney", meterMoneyDomain);
	}

	public List<MeterMoneyDomain> findMeterMoneyByMeterIdsAndMon(MeterMoneyDomain meterMoneyDomain) {
		return getPersistanceManager().find(getNamespace() + ".findMeterMoneyByMeterIdsAndMon", meterMoneyDomain);
	}
	//居民阶梯电量报表
	public List<MeterMoneyDomain> ladderPowerQuery(MeterMoneyDomain meterMoneyDomain) {
		return getPersistanceManager().find(getNamespace() + ".ladderPowerQuery", meterMoneyDomain);
	}

	public MeterMoneyDomain findByKey(MeterMoneyDomain meterMoneyDomain) {
		return getPersistanceManager().load(getNamespace() + ".findByKey", meterMoneyDomain);
	}

	public int insert(MeterMoneyDomain meterMoneyDomain) {
		return getPersistanceManager().insert(getNamespace() + ".insert", meterMoneyDomain);
	}

	public int update(MeterMoneyDomain meterMoneyDomain) {
		return getPersistanceManager().update(getNamespace() + ".update", meterMoneyDomain);
	}

	public int deleteListByParam(List<MeterMoneyDomain> meterMoneyDomains) {
		return getPersistanceManager(ExecutorType.BATCH).deleteList(getNamespace() + ".deleteByParam",
				meterMoneyDomains);
	}

	public int insertList(List<MeterMoneyDomain> meterMoneyDomains) {
		return getPersistanceManager(ExecutorType.BATCH).insertList(getNamespace() + ".insert", meterMoneyDomains);
	}

	public List<MeterMoneyDomain> findMeterMoneyByIds(List<Long> ids){
		return getPersistanceManager().find(getNamespace() + ".findMeterMoneyByIds", ids);
	}
	public int deleteMeterMoneyByMeterIdsMonAndSn(PulishEntity pulishEntity) {
		return getPersistanceManager().delete("deleteMeterMoneyByMeterIdsMonAndSn",pulishEntity);
	}
	public List<Document> getMeterMoneyList(String mon, Iterable<Long> meterIdList, Page page) {

		List<Document> documents = aggregate(getCollectionName(mon, MongoCollectionConfig.ELECTRIC_METER.name()),
				new MongoAggregateLookupFilter() {

					@Override
					public List<? extends Bson> getPipeline() {
						List<Bson> pipeLine = new LinkedList<>();
						pipeLine.add(this.getMatch());
						pipeLine.add(this.getLookup());
//						pipeLine.add(new Document("$unwind", new Document().append("path", "$meterMoneyDomain")
//								.append("preserveNullAndEmptyArrays", true)));

						pipeLine.add(new Document("$unwind",
								Document.parse("{path:'$meterMoneyDomain',preserveNullAndEmptyArrays:true}")));
						
						/*pipeLine.add(new Document("$match",Document.parse("{meterMoneyDomain:{'$ne':[]}}")));*/
						return pipeLine;
					}

					@Override
					public Bson setMatch() {
						return Document.parse("{$expr:{ $in: [ '$id'," + meterIdList + " ] }}");
					}

					@Override
					public Bson setLookup() {

						Document document = new Document();
						document.put("from", getCollectionName(mon, MongoCollectionConfig.ELECTRIC_METER_MONEY.name()));
						document.put("localField", "id");
						document.put("foreignField", "meterId");
						document.put("as", "meterMoneyDomain");

						return document;
					}
				});

		return documents;
	}
//  获取电费合计--取出所有数据
//	public List<MeterMoneyDomain> getMeterMoneySum(MeterDomain queryParam, Iterable<Long> meterIdList) {
//
//		MeterMoneyDomain d = new MeterMoneyDomain();
//		List<MeterMoneyDomain> meterMoneyDomains = findMany(getCollectionName(queryParam.getMon().toString(), MongoCollectionConfig.ELECTRIC_METER_MONEY.name()),
//				new MongoFindFilter() {
//
//					@Override
//					public Bson filter() {
//						List<Bson> filters = new LinkedList<>();
//						filters.add(Filters.in("meterId", meterIdList));
//						filters.add(Filters.eq("mon", queryParam.getMon()));
//						return Filters.and(filters);
//					}
//				},MeterMoneyDomain.class);
//
//		return meterMoneyDomains;
//	}

	public String getMeterMoneySum(MeterDomain queryParam, Iterable<Long> meterIdList) {

		List<Document> documents = aggregate(getCollectionName(queryParam.getMon().toString(), MongoCollectionConfig.ELECTRIC_METER_MONEY.name()),
				new MongoAggregateGroupFilter() {
					@Override
					public Bson setGroup() {
						// TODO Auto-generated method stub
						Document group = new Document();
						group.put("_id", null);
						group.put("amount", new Document("$sum", "$amount"));
						group.put("totalPower", new Document("$sum", "$totalPower"));
						group.put("basicMoney", new Document("$sum", "$basicMoney"));
						group.put("powerRateMoney", new Document("$sum", "$powerRateMoney"));
						group.put("surcharges", new Document("$sum", "$surcharges"));
						group.put("calCapacity", new Document("$sum", "$calCapacity"));
						group.put("volumeCharge", new Document("$sum", "$volumeCharge"));
						return group;
					}

					@Override
					public Bson setMatch() {
						// TODO Auto-generated method stub
						Document match = new Document();
						match.put("meterId", new Document("$in", meterIdList));
						match.put("mon", new Document("$eq", queryParam.getMon()));
						return match;
					}
				});

		return GsonUtils.toJson(documents);
	}


	public List<MeterMoneyDomain> mongoFind(MeterMoneyDomain queryParam) {

		List<MeterMoneyDomain> domains = findManyByPage(
				getCollectionName(queryParam.getMon().toString(), MongoCollectionConfig.ELECTRIC_METER_MONEY.name()),
				new MongoFindFilter() {
					@Override
					public Bson filter() {
						List<Bson> filters = new ArrayList<Bson>();
						if (queryParam.getMeterIds() != null) {
							filters.add(Filters.in("meterId", queryParam.getMeterIds()));
						}
						if (queryParam.getMeterId() != null) {
							filters.add(Filters.eq("meterId", queryParam.getMeterId()));
						}
						if (queryParam.getMon() != null) {
							filters.add(Filters.eq("mon", queryParam.getMon()));
						}
						if (queryParam.getSn() != null) {
							filters.add(Filters.eq("sn", queryParam.getSn()));
						}
						if (queryParam.getWriteSectIds() != null) {
							filters.add(Filters.in("writeSectId", queryParam.getWriteSectIds()));
						} else if (queryParam.getWriteSectId() != null) {
							filters.add(Filters.eq("writeSectId", queryParam.getWriteSectId()));
						}
						// TODO Auto-generated method stub
						return Filters.and(filters);
					}

					@Override
					public Page getPage() {
						// TODO Auto-generated method stub
						return queryParam;
					}
					@Override
					public Bson getSort() {
						return new Document("_id", 1);
					}
				}, MeterMoneyDomain.class);

		return domains;
	}

	public List<MeterMoneyDomain> selectMeterMoney(MeterMoneyDomain meterMoneyDomain) {
		return findManyByPage(
				getCollectionName(meterMoneyDomain.getMon().toString(),
						MongoCollectionConfig.ELECTRIC_METER_MONEY.name()),
				new MongoFindFilter() {
					@Override
					public Bson filter() {
						Document document = new Document();
//						if (meterMoneyDomain.getBusinessPlaceCode() != null) {
//							document.put("businessPlaceCode", meterMoneyDomain.getBusinessPlaceCode());
//						}
//						if (meterMoneyDomain.getWriteSectId() != null) {
//							document.put("writeSectId", meterMoneyDomain.getWriteSectId());
//						}
//						if (meterMoneyDomain.getMon() != null) {
//							document.put("mon", meterMoneyDomain.getMon());
//						}
						return document;
					}
					@Override
					public Page getPage() {
						// TODO Auto-generated method stub
						return meterMoneyDomain;
					}
				},
				MeterMoneyDomain.class);
	}

	public int dataValidation(String date,List<Long> meterIds) {
		return 1;
		/*getCollection(date,
				MongoCollectionConfig.ELECTRIC_METER_MONEY.name());*/
	}
}
