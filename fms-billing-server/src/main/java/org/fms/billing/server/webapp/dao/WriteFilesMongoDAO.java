/**
 * Author : czy
 * Date : 2019年7月8日 下午4:12:47
 * Title : com.riozenc.billing.webapp.dao.MeterDAO.java
 **/
package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.fms.billing.common.webapp.domain.WriteFilesDomain;
import org.fms.billing.common.webapp.domain.mongo.WriteSectMongoDomain;
import org.fms.billing.server.web.config.MongoCollectionConfig;
import org.springframework.stereotype.Repository;

import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.mongo.dao.MongoDAOSupport;
import com.riozenc.titanTool.mybatis.pagination.Page;

@Repository
public class WriteFilesMongoDAO implements MongoDAOSupport {

	public List<WriteSectMongoDomain> findWriteFilesMongoByWhere(WriteSectMongoDomain writeFilesMongoDomain) {

		List<WriteSectMongoDomain> writeFilesMongoDomains = findManyByPage(
				getCollectionName(writeFilesMongoDomain.getMon().toString(), MongoCollectionConfig.WRITE_FILES.name()),
				new MongoFindFilter() {
					@Override
					public Bson filter() {
						List ids=writeFilesMongoDomain.getIds();
						writeFilesMongoDomain.setIds(null);
						Document document = Document.parse(GsonUtils.getIgnorReadGson().toJson(writeFilesMongoDomain));
						//测试时使用 去掉状态 发行之后可以看电费明细
                        document.remove("mon");
						if (writeFilesMongoDomain.getIds() != null) {
							document.put("id", Document
									.parse("{$in:" + ids + "}"));
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
						return writeFilesMongoDomain;
					}
				}, WriteSectMongoDomain.class);

		return writeFilesMongoDomains;
	}

	public List<WriteFilesDomain> findWriteFilesByWhere(WriteFilesDomain writeFilesDomain) {
		List<WriteFilesDomain> writeFilesDomains = findManyByPage(
				getCollectionName(writeFilesDomain.getMon().toString(), MongoCollectionConfig.WRITE_FILES.name()),
				new MongoFindFilter() {
					@Override
					public Bson filter() {
						Document document = Document.parse(GsonUtils.getIgnorReadGson().toJson(writeFilesDomain));
						document.put("userNo", writeFilesDomain.getUserNo());
						return document;
					}
					@Override
					public Bson getSort() {
						// TODO Auto-generated method stub
						return new Document("_id", up());
					}
					@Override
					public Page getPage() {
						// TODO Auto-generated method stub
						return writeFilesDomain;
					}
				}, WriteFilesDomain.class);
		return writeFilesDomains;

	}



}
