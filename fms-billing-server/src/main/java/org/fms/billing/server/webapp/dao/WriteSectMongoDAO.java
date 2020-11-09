/**
 * Author : czy
 * Date : 2019年7月8日 下午4:12:47
 * Title : com.riozenc.billing.webapp.dao.MeterDAO.java
 **/
package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.fms.billing.common.webapp.domain.mongo.WriteSectMongoDomain;
import org.fms.billing.server.web.config.MongoCollectionConfig;
import org.springframework.stereotype.Repository;

import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.mongo.dao.MongoDAOSupport;
import com.riozenc.titanTool.mybatis.pagination.Page;

@Repository
public class WriteSectMongoDAO implements MongoDAOSupport {

	public List<WriteSectMongoDomain> findWriteSectMongoByWhere(WriteSectMongoDomain writeSectMongoDomain) {

		List<WriteSectMongoDomain> writeSectMongoDomains = findManyByPage(
				getCollectionName(writeSectMongoDomain.getMon().toString(), MongoCollectionConfig.WRITE_SECT.name()),
				new MongoFindFilter() {
					@Override
					public Bson filter() {
						List ids=writeSectMongoDomain.getIds();
						List writorIds=writeSectMongoDomain.getWritorIds();
						List businessPlaceCodes=writeSectMongoDomain.getBusinessPlaceCodes();
						writeSectMongoDomain.setIds(null);
						writeSectMongoDomain.setBusinessPlaceCodes(null);
						writeSectMongoDomain.setWritorIds(null);
						Document document = Document.parse(GsonUtils.getIgnorReadGson().toJson(writeSectMongoDomain));
						//测试时使用 去掉状态 发行之后可以看电费明细
                        document.remove("mon");
						if (ids != null) {
							document.put("id", Document
									.parse("{$in:" + ids + "}"));
						}
						if (writorIds != null) {
							document.put("writorId", Document
									.parse("{$in:" + writorIds + "}"));
						}
						if (businessPlaceCodes != null) {
							document.put("businessPlaceCode", Document
									.parse("{$in:" + businessPlaceCodes + "}"));
						}
						System.out.println("document========="+document);
						return document;
					}

					@Override
					public Bson getSort() {
						// TODO Auto-generated method stub
						return new Document("id", up());
					}

					@Override
					public Page getPage() {
						// TODO Auto-generated method stub
						return writeSectMongoDomain;
					}
				}, WriteSectMongoDomain.class);

		return writeSectMongoDomains;
	}


}
