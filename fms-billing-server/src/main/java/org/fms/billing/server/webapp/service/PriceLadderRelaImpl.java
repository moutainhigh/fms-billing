/**
 * Author : czy
 * Date : 2019年6月24日 上午9:01:43
 * Title : com.riozenc.billing.webapp.service.impl.PriceExecutionServiceImpl.java
 **/
package org.fms.billing.server.webapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.fms.billing.common.webapp.domain.PriceLadderRelaDomain;
import org.fms.billing.common.webapp.service.IPriceLadderRelaService;
import org.fms.billing.server.web.config.MongoCollectionConfig;
import org.fms.billing.server.webapp.dao.PriceLadderRelaDAO;
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
public class PriceLadderRelaImpl implements IPriceLadderRelaService, MongoDAOSupport {

	@Autowired
	private TitanTemplate titanTemplate;

	@TransactionDAO
	private PriceLadderRelaDAO priceLadderRelaDAO;

	@Override
	public int insert(PriceLadderRelaDomain t) {
		// TODO Auto-generated method stub
		return priceLadderRelaDAO.insert(t);
	}

	@Override
	public int update(PriceLadderRelaDomain t) {
		// TODO Auto-generated method stub
		return priceLadderRelaDAO.update(t);
	}

	@Override
	public List<PriceLadderRelaDomain> findByWhere(PriceLadderRelaDomain t) {
		// TODO Auto-generated method stub
		return priceLadderRelaDAO.findByWhere(t);
	}

	// 将电价同步本月
	@Override
	public HttpResult synCurrentPrice() {
		// 获得全部电价
		PriceLadderRelaDomain priceLadderRelaDomain = new PriceLadderRelaDomain();
		priceLadderRelaDomain.setPageSize(-1);
		List<PriceLadderRelaDomain> updatePriceLadderRela = findByWhere(priceLadderRelaDomain);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		// 取当前月份
		try {
			String currentMon = titanTemplate.postJson("TITAN-CONFIG", "titan-config/sysCommConfig/getCurrentMon",
					httpHeaders, null, String.class);

			List<WriteModel<Document>> updateResult = updateMany(
					toDocuments(updatePriceLadderRela, new ToDocumentCallBack<PriceLadderRelaDomain>() {
						@Override
						public PriceLadderRelaDomain call(PriceLadderRelaDomain t) {
							// TODO Auto-generated method stub
							t.setCreateDate(new Date());
							return t;
						}
					}), new MongoUpdateFilter() {
						@Override
						public Bson filter(Document document) {
							List<Bson> filters = new ArrayList<Bson>();
							filters.add(Filters.eq("id", document.get("id")));
							return Filters.and(filters);
						}
					}, true);

			BulkWriteResult bulkWriteResult = getCollection(currentMon, MongoCollectionConfig.PRICE_LADDER_RELA.name())
					.bulkWrite(updateResult);
		} catch (Exception e) {
			return new HttpResult(HttpResult.ERROR, "同步阶梯电价出错");
		}
		return new HttpResult(HttpResult.SUCCESS, "同步阶梯电价成功");
	}
	@Override
	public List<PriceLadderRelaDomain> findMongoPriceLadderRela(String mon) {
		return priceLadderRelaDAO.findMongoPriceLadderRela(mon);
	}



}
