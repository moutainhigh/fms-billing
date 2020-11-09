package org.fms.billing.server.webapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.fms.billing.common.webapp.domain.PreChargeDomain;
import org.fms.billing.common.webapp.domain.beakInterface.CustomerDomain;
import org.fms.billing.common.webapp.domain.beakInterface.SettlementDomain;
import org.fms.billing.common.webapp.service.IPreChargeService;
import org.fms.billing.server.web.config.MongoCollectionConfig;
import org.fms.billing.server.webapp.dao.PreChargeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.WriteModel;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;
import com.riozenc.titanTool.mongo.dao.MongoDAOSupport;
import com.riozenc.titanTool.spring.web.client.TitanTemplate;
import com.riozenc.titanTool.spring.web.http.HttpResult;

@TransactionService
public class PreChargeServiceImpl implements IPreChargeService,MongoDAOSupport{
	@TransactionDAO
	private PreChargeDAO preChargeDAO;

	@Autowired
	private TitanTemplate titanTemplate;

	@Override
	public List<PreChargeDomain> findByWhere(PreChargeDomain preChargeDomain) {
		return preChargeDAO.findByWhere(preChargeDomain);
	}

	@Override
	public List<PreChargeDomain> findPreChargeByCustomer(CustomerDomain customerDomain) {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("customerId", customerDomain.getId());
			List<SettlementDomain> settlementDomains = titanTemplate.postJson("CIM-SERVER",
					"cimServer/settlement?method=getSettlements", new HttpHeaders(), params,
					new TypeReference<List<SettlementDomain>>() {
					});
			List<Long> settlementIds = new ArrayList<>();
			for (SettlementDomain settlementDomain : settlementDomains) {
				settlementIds.add(settlementDomain.getId());
			}
			if (settlementIds.isEmpty()) {
				return new ArrayList<>(0);
			}
			return preChargeDAO.findAllPreChargeBySettleIds(settlementIds);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<>(0);
		}

	}

	@Override
	public int insert(PreChargeDomain preChargeDomain) {
		return preChargeDAO.insert(preChargeDomain);
	}

	@Override
	public int update(PreChargeDomain preChargeDomain) {
		return preChargeDAO.update(preChargeDomain);
	}
	@Override
	public List<PreChargeDomain> findPreChargeBySettleIds(List<Long> settlementIds){
		return preChargeDAO.findPreChargeBySettleIds(settlementIds);
	}


	//将余额同步本月
	@Override
	public HttpResult synCurrentPreCharge(List<PreChargeDomain>  preChargeDomains) {
		HttpResult httpResult=new HttpResult();

		List<WriteModel<Document>> updateResult =
				updateMany(toDocuments(preChargeDomains, new MongoDAOSupport.ToDocumentCallBack<PreChargeDomain>() {
							@Override
							public PreChargeDomain call(PreChargeDomain t) {
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
					MongoCollectionConfig.PRE_CHARGE.name()).bulkWrite(updateResult);
			httpResult.setStatusCode(HttpResult.SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			httpResult.setStatusCode(HttpResult.ERROR);
			httpResult.setMessage("同步余额出错");
		}

		return httpResult;
	}

}
