package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.fms.billing.common.webapp.domain.WriteFilesDomain;
import org.fms.billing.server.web.config.MongoCollectionConfig;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.mongo.dao.MongoDAOSupport;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;
@TransactionDAO
public class DBFRelaDAO extends AbstractTransactionDAOSupport implements MongoDAOSupport{
	public List<WriteFilesDomain> dbfFilter(WriteFilesDomain writeFilesDomain) {
		System.out.println(writeFilesDomain.getMon().toString());
		List<WriteFilesDomain> writeFile = findMany(getCollection(writeFilesDomain.getMon().toString(), MongoCollectionConfig.WRITE_FILES.name()), new MongoFindFilter() {
			@Override
			public Bson filter() {
				Document document = Document.parse(GsonUtils.getIgnorReadGson().toJson(writeFilesDomain));
				document.remove("mon");
				return document;
			}
		}, WriteFilesDomain.class);
		System.out.println(GsonUtils.toJson(writeFile));
		return writeFile;

	}
	

}
