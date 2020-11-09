package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.fms.billing.common.webapp.domain.mongo.TransformerMongoDomain;
import org.fms.billing.server.web.config.MongoCollectionConfig;
import org.springframework.stereotype.Repository;

import com.riozenc.titanTool.mongo.dao.MongoDAOSupport;

@Repository
public class TransformerDAO implements MongoDAOSupport {
    public List<TransformerMongoDomain> getTransformer(TransformerMongoDomain transformer) {
        List<TransformerMongoDomain> list = findMany(
                getCollection(transformer.getMon().toString(), MongoCollectionConfig.TRANSFORMER_INFO.name()),
                new MongoFindFilter() {
                    @Override
                    public Bson filter() {
                        Document document = new Document();
                        if (transformer.getIds() != null) {
                            document.put("id", Document
                                    .parse("{$in:" + transformer.getIds() + "}"));
                        } else if (transformer.getId() != null) {
                            document.put("id", transformer.getId());
                        }
                        if (transformer.getBusinessPlaceCodes() != null) {
                            document.put("businessPlaceCode", Document
                                    .parse("{$in:" + transformer.getBusinessPlaceCodes() + "}"));
                        } else if (transformer.getBusinessPlaceCode() != null) {
                            document.put("businessPlaceCode", transformer.getBusinessPlaceCode());
                        }
                        return document;
                    }
                }, TransformerMongoDomain.class);
        return list;
    }
}
