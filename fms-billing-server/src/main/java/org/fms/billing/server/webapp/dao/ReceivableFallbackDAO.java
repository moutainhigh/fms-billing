/**
 * Author : czy
 * Date : 2019年12月5日 下午8:31:48
 * Title : com.riozenc.billing.webapp.dao.ReceivableFallbackDAO.java
 *
**/
package org.fms.billing.server.webapp.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.session.ExecutorType;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.fms.billing.common.webapp.domain.ArrearageDomain;
import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.server.web.config.MongoCollectionConfig;

import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.WriteModel;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.mongo.dao.MongoDAOSupport;
import com.riozenc.titanTool.mybatis.pagination.Page;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;

@TransactionDAO
public class ReceivableFallbackDAO extends AbstractTransactionDAOSupport implements MongoDAOSupport {

	public List<ArrearageDomain> findReceivable(ArrearageDomain arrearageDomain) {
		Map<Long,MeterDomain> meterDomainMap = findMany(
				getCollectionName(arrearageDomain.getMon().toString(), MongoCollectionConfig.ELECTRIC_METER.name()),
				new MongoFindFilter() {
					@Override
					public Bson filter() {
						Document params = new Document();
						if (arrearageDomain.getMeterNo() != null) {
							params.append("meterNo", arrearageDomain.getMeterNo());
						}
						if (arrearageDomain.getWriteSectNo() != null) {
							params.append("writeSectNo", arrearageDomain.getWriteSectNo());
						}
						return params;
					}
				}, MeterDomain.class).stream().collect(Collectors.toMap(MeterDomain::getId, v->v));

		List<ArrearageDomain> arrearageDomains = findManyByPage(
				getCollectionName(arrearageDomain.getMon().toString(), MongoCollectionConfig.ARREARAGE_INFO.name()),
				new MongoFindFilter() {
					@Override
					public Bson filter() {
						return Filters.in("meterId",meterDomainMap.keySet());
					}
					@Override
					public Page getPage() {
						return arrearageDomain;
					}
				}, ArrearageDomain.class);
		
		arrearageDomains.forEach(a->{
			a.setMeterNo(meterDomainMap.get(a.getMeterId()).getMeterNo());
			a.setWriteSectNo(meterDomainMap.get(a.getMeterId()).getWriteSectNo());
			a.setMeterName(meterDomainMap.get(a.getMeterId()).getMeterName());
			a.setWriteSectName(meterDomainMap.get(a.getMeterId()).getWriteSectName());
		});
		
		
		
		return arrearageDomains;

	}

	/**
	 * 回退
	 */
	public int fallback(String mon, List<ArrearageDomain> arrearageDomains) {
		// 修改meter状态
		// ARREARAGE_INFO 数据

		long count = deleteMany(getCollectionName(mon, MongoCollectionConfig.ARREARAGE_INFO.name()),
				new MongoDeleteFilter() {
					@Override
					public Bson filter() {
						return Filters.in("meterId", arrearageDomains.stream().map(ArrearageDomain::getMeterId)
								.collect(Collectors.toList()));
					}
				});

		List<WriteModel<Document>> writeModels = updateMany2(arrearageDomains,
				new MongoUpdateFilter2<ArrearageDomain>() {
					@Override
					public Bson filter(ArrearageDomain t) {
						return Filters.eq("id", t.getMeterId());
					}

					@Override
					public Document setUpdate(ArrearageDomain t) {
						return new Document().append("status", 1);
					}
				}, false);

		if (writeModels.size() != 0) {
			BulkWriteResult writeResult = getCollection(mon, MongoCollectionConfig.ELECTRIC_METER.name())
					.bulkWrite(writeModels);
			if (count == arrearageDomains.size()) {
				// 删除mysql arrearage 数据
				getPersistanceManager(ExecutorType.BATCH).deleteList(ArrearageDAO.class.getName() + ".deleteByParam", arrearageDomains);
				return arrearageDomains.size();
			}
		} else {
			return 0;
		}

		return 0;
	}

}
