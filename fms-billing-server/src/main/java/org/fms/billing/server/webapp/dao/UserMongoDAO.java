/**
 * Author : czy
 * Date : 2019年7月8日 下午4:12:47
 * Title : com.riozenc.billing.webapp.dao.MeterDAO.java
 **/
package org.fms.billing.server.webapp.dao;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.fms.billing.common.webapp.domain.mongo.UserMongoDomain;
import org.fms.billing.server.web.config.MongoCollectionConfig;
import org.springframework.stereotype.Repository;

import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.mongo.dao.MongoDAOSupport;
import com.riozenc.titanTool.mybatis.pagination.Page;

@Repository
public class UserMongoDAO implements MongoDAOSupport {

	public List<UserMongoDomain> findUserMongoByWhere(UserMongoDomain userMongoDomain) {

		List<UserMongoDomain> userMongoDomains = findManyByPage(
				getCollectionName(userMongoDomain.getMon().toString(), MongoCollectionConfig.USER_INFO.name()),
				new MongoFindFilter() {
					@Override
					public Bson filter() {
						List ids=userMongoDomain.getIds();
						userMongoDomain.setIds(null);
						List writeSectIds=userMongoDomain.getWriteSectIds();
						userMongoDomain.setWriteSectIds(null);
						Document document = Document.parse(GsonUtils.getIgnorReadGson().toJson(userMongoDomain));
						//测试时使用 去掉状态 发行之后可以看电费明细
                        document.remove("mon");
						if (ids != null && ids.size()>0) {
							document.put("id", Document
									.parse("{$in:" + ids + "}"));
						}
						if (writeSectIds != null && writeSectIds.size()>0) {
							document.put("writeSectId", Document
									.parse("{$in:" + writeSectIds + "}"));
						}
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
						return userMongoDomain;
					}
				}, UserMongoDomain.class);

		return userMongoDomains;
	}


	public String findNumGroupByWriteSectId(UserMongoDomain userMongoDomain) {
		List<Document> result = aggregate(
				getCollectionName(userMongoDomain.getMon(),
						MongoCollectionConfig.USER_INFO.name()),
				new MongoAggregateLookupFilter() {

					@Override
					public List<? extends Bson> getPipeline() {
						List<Bson> pipeLine = new LinkedList<>();
						pipeLine.add(this.getMatch());
						pipeLine.add(new Document("$group",Document.parse(
								"{_id : '$writeSectId', sumNum : {$sum : 1}}")));
						return pipeLine;
					}

					@Override
					public Bson setLookup() {
						return null;
					}

					@Override
					public Bson setMatch() {
						return Document.parse("{$expr:{ $in: [ " +
								"'$businessPlaceCode'," + userMongoDomain.getBusinessPlaceCodes() + " ] }}");
					}


				});
		String json = GsonUtils.toJson(result);
		return json;
	}
}
