package org.fms.billing.server.webapp.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.fms.billing.common.webapp.domain.mongo.TransformerMeterRelDomain;
import org.fms.billing.server.web.config.MongoCollectionConfig;
import org.springframework.stereotype.Repository;

import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.mongo.dao.MongoDAOSupport;

@Repository
public class TransformerMeterRelDAO implements MongoDAOSupport {
    public List<TransformerMeterRelDomain> getTransMeterRel(TransformerMeterRelDomain transformerMeterRel) {
        List<TransformerMeterRelDomain> list = findMany(
                getCollection(transformerMeterRel.getMon().toString(), MongoCollectionConfig.TRANSFORMER_METER_REL.name()),
                new MongoFindFilter() {
                    @Override
                    public Bson filter() {
                        Document document = new Document();
                        if (transformerMeterRel.getMeterIds() != null) {
                            document.put("meterId", Document
                                    .parse("{$in:" + transformerMeterRel.getMeterIds() + "}"));
                        } else if (transformerMeterRel.getMeterId() != null) {
                            document.put("meterId", transformerMeterRel.getMeterId());
                        }
                        if (transformerMeterRel.getTransformerIds() != null) {
                            document.put("transformerId", Document
                                    .parse("{$in:" + transformerMeterRel.getTransformerIds() + "}"));
                        } else if (transformerMeterRel.getTransformerId() != null) {
                            document.put("transformerId", transformerMeterRel.getTransformerId());
                        }
                        return document;
                    }
                }, TransformerMeterRelDomain.class);
        return list;
    }

    public String findNumGroupByTransformId(String mon,
                                            List<Long> transformerAssetsIds) {
        List<Document> result = aggregate(
                getCollectionName(mon,
                        MongoCollectionConfig.TRANSFORMER_METER_REL.name()),
                new MongoAggregateLookupFilter() {

                    @Override
                    public List<? extends Bson> getPipeline() {
                        List<Bson> pipeLine = new LinkedList<>();
                        pipeLine.add(getMatch());
                        pipeLine.add(new Document("$group",Document.parse("{_id : '$transformerId', sumNum : {$sum : 1}}")));
                        return pipeLine;
                    }

                    @Override
                    public Bson setLookup() {
                        return null;
                    }

                    @Override
                    public Bson setMatch() {
                        // 算费记录不为空
                        /*
                         * String json="{meter_money: {$ne:[]}}"; return BsonDocument.parse(json);
                         */
                        if(transformerAssetsIds!=null && transformerAssetsIds.size()>0){
                            String json =
                                    "{transformerId: {$in:["+ transformerAssetsIds.stream().map(String::valueOf).collect(Collectors.joining(","))+"]}}";
                            return BsonDocument.parse(json);
                        }else{
                            Document document = new Document();
                            return document;
                        }
                    }


                });
        String json = GsonUtils.toJson(result);
        return json;
    }
}
