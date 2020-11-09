/**
 * Author : czy
 * Date : 2019年7月8日 下午4:12:47
 * Title : com.riozenc.billing.webapp.dao.MeterDAO.java
 **/
package org.fms.billing.server.webapp.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.WriteFilesDomain;
import org.fms.billing.common.webapp.domain.mongo.MeterMongoDomain;
import org.fms.billing.common.webapp.entity.MeterMoneyVerificationEntity;
import org.fms.billing.server.web.config.MongoCollectionConfig;
import org.springframework.stereotype.Repository;

import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.WriteModel;
import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.mongo.dao.MongoDAOSupport;
import com.riozenc.titanTool.mybatis.pagination.Page;

@Repository
public class MeterDAO implements MongoDAOSupport {

    public List<MeterDomain> findMeterByWhere(MeterDomain meterDomain) {

        List<MeterDomain> meterDomains = findManyByPage(
                getCollectionName(meterDomain.getMon().toString(), MongoCollectionConfig.ELECTRIC_METER.name()),
                new MongoFindFilter() {
                    @Override
                    public Bson filter() {
                        Document document =
                                Document.parse(GsonUtils.getIgnorReadGson().toJson(meterDomain));
                        document.remove("mon");
                        return document;
                    }

                    @Override
                    public Bson getSort() {
                        return new Document("id", up());
                    }

                    @Override
                    public Page getPage() {
                        return meterDomain;
                    }
                }, MeterDomain.class);

        return meterDomains;
    }

    public List<MeterDomain> getMeters(MeterDomain meterDomain, List<Long> meterIds) {

        List<MeterDomain> meterDomains = findManyByPage(
                getCollectionName(meterDomain.getMon().toString(), MongoCollectionConfig.ELECTRIC_METER.name()),
                new MongoFindFilter() {
                    @Override
                    public Bson filter() {
                        Document document =
                                Document.parse(GsonUtils.getIgnorReadGson().toJson(meterDomain));
                        Filters.or(new Document("status", 3), new Document("status", 2), new Document("status", -2));
                        Filters.or(new Document("userNo", meterDomain.getUserNo()));
                        Filters.or(new Document("writeSectNo", meterDomain.getWriteSectNo()));
                        return document;
                    }

                    @Override
                    public Bson getSort() {
                        return new Document("meterId", up());
                    }

                    @Override
                    public Page getPage() {
                        return meterDomain;
                    }
                }, MeterDomain.class);

        return meterDomains;
    }

    public List<MeterDomain> getEffectiveComputeMeter(Integer mon, List<WriteFilesDomain> writeFilesDomains,
                                                      Integer... status) {

        List<Long> meterIds = writeFilesDomains.parallelStream()
                .filter(w -> w.getWriteFlag() != null && w.getWriteFlag() == 1).map(w -> w.getMeterId())
                .collect(Collectors.toList());

        List<MeterDomain> meterDomains = findMany(
                getCollection(mon.toString(), MongoCollectionConfig.ELECTRIC_METER.name()), new MongoFindFilter() {

                    @Override
                    public Bson filter() {

                        return Filters.and(Filters.in("id", meterIds), Filters.in("status", status));
                    }

                }, MeterDomain.class);

        return meterDomains;
    }

    public List<MeterDomain> getMeterByWriteSectionIds(Integer mon,
                                                       Integer[] writeSectionIds,
                                                       Integer... status) {
        List<MeterDomain> meterDomains = findMany(
                getCollection(mon.toString(), MongoCollectionConfig.ELECTRIC_METER.name()), new MongoFindFilter() {

                    @Override
                    public Bson filter() {

                        return Filters.and(Filters.in("writeSectionId", writeSectionIds), Filters.in("status", status));
                    }

                }, MeterDomain.class);

        return meterDomains;
    }


    public List<MeterDomain> getMeterByBusinessPlaceCode(Integer mon,
                                                       Long businessPlaceCode,
                                                       Integer... status) {
        List<MeterDomain> meterDomains = findMany(

                getCollection(mon.toString(), MongoCollectionConfig.ELECTRIC_METER.name()), new MongoFindFilter() {

                    @Override
                    public Bson filter() {

                        return Filters.and(Filters.in("businessPlaceCode", businessPlaceCode), Filters.in("status", status));
                    }

                }, MeterDomain.class);

        return meterDomains;
    }

    /**
     * 从mongodb查电费计算的数据 界面展示作为电费核查 1.分页 2.匹配规则：匹配电费计算阶段
     *
     * @param meterDomain
     * @return meterMoneyVerificationEntities
     */
    public List<MeterMoneyVerificationEntity> findMeterMoneyByMongodb(MeterDomain meterDomain) {
        List<Document> documents = aggregate(
                getCollectionName(meterDomain.getMon().toString(), MongoCollectionConfig.ELECTRIC_METER.name()),
                new MongoAggregateLookupFilter() {

                    @Override
                    public List<? extends Bson> getPipeline() {
                        List<Bson> pipeLine = new LinkedList<>();
                        pipeLine.add(this.getMatch());
                        pipeLine.add(this.getLookup());
                        // 一对一
                        pipeLine.add(new Document("$unwind", "$meterMoney"));
                        return pipeLine;
                    }

                    @Override
                    public Bson setMatch() {
                        // 算费记录不为空
                        /*
                         * String json="{meter_money: {$ne:[]}}"; return BsonDocument.parse(json);
                         */
                        String json = "{status: 2}";
                        return BsonDocument.parse(json);
                    }

                    @Override
                    public Bson setLookup() {
                        String json = "{" + "'from': '"
                                + getCollectionName(meterDomain.getMon().toString(),
                                MongoCollectionConfig.ELECTRIC_METER_MONEY.name())
                                + "'," + "'let': {'id': '$id','sn': '$sn'}," + "'pipeline': [" + "{'$match': "
                                + "{'$expr': " + "{'$and': [" + "{'$eq': ['$meterId','$$id']},"
                                + "{'$eq': ['$sn','$$sn']}" + "]" + "}" + "}" + "}" + "]," + "'as': 'meterMoney'" + "}";
                        return BsonDocument.parse(json);
                    }
                });
        // 查出的数据转成json
        String meterMoneyVerificationJson = GsonUtils.toJson(documents);
        // 转成相应的数据格式
        List<MeterMoneyVerificationEntity> meterMoneyVerificationEntities = GsonUtils
                .readValueToList(meterMoneyVerificationJson, MeterMoneyVerificationEntity.class);
        return meterMoneyVerificationEntities;
    }

    /**
     * 用于report调用
     *
     * @param meterDomain
     * @return
     */
    public List<MeterDomain> findMeterDomain(MeterDomain meterDomain) {

        List<MeterDomain> result = findMany(
                getCollectionName(meterDomain.getMon().toString(), MongoCollectionConfig.ELECTRIC_METER.name()),
                new MongoFindFilter() {

                    @Override
                    public Bson filter() {
                        Document document = new Document();
                        if (meterDomain.getIds() != null) {
                            document.put("id", Document.parse("{$in:" + meterDomain.getIds() + "}"));
                        } else if (meterDomain.getId() != null) {
                            document.put("id", meterDomain.getId());
                        }
                        if (meterDomain.getWritorIds() != null) {
                            document.put("writorId", Document.parse("{$in:" + meterDomain.getWritorIds() + "}"));
                        } else if (meterDomain.getWritorId() != null) {
                            document.put("writorId", meterDomain.getWritorId());
                        }
                        if (meterDomain.getWriteSectionIds() != null) {
                            document.put("writeSectionId",
                                    Document.parse("{$in:" + meterDomain.getWriteSectionIds() + "}"));
                        } else if (meterDomain.getWriteSectionId() != null) {
                            document.put("writeSectionId", meterDomain.getWriteSectionId());
                        }
                        if (meterDomain.getBusinessPlaceCodes() != null) {
                            document.put("businessPlaceCode",
                                    Document.parse("{$in:" + meterDomain.getBusinessPlaceCodes() + "}"));
                        } else if (meterDomain.getBusinessPlaceCode() != null) {
                            document.put("businessPlaceCode", meterDomain.getBusinessPlaceCode());
                        }
                        if (meterDomain.getUserIds() != null) {
                            document.put("userId", Document.parse("{$in:" + meterDomain.getUserIds() + "}"));
                        } else if (meterDomain.getUserId() != null) {
                            document.put("userId", meterDomain.getUserId());
                        }
                        if (meterDomain.getSn() != null) {
                            document.put("sn",
                                    meterDomain.getSn());
                        }
                        if (meterDomain.getElecTypeCode() != null) {
                            document.put("elecTypeCode",
                                    meterDomain.getElecTypeCode());
                        }
                        if (meterDomain.getWriteSectNo() != null) {
                            document.put("writeSectNo",
                                    meterDomain.getWriteSectNo());
                        }
                        return document;
                    }
                }, MeterDomain.class);
        return result;
    }

    // 数据确认 更新计量点状态 -2->2
    public long mongoDataValidaUpdate(List<MeterDomain> meterDomains) {

        List<WriteModel<Document>> writeModels = updateMany(
                toDocuments(meterDomains, new ToDocumentCallBack<MeterDomain>() {

                    @Override
                    public MeterDomain call(MeterDomain t) {
                        return t;
                    }

                }), new MongoUpdateFilter() {

                    @Override
                    public Bson filter(Document param) {
                        // TODO Auto-generated method stub
                        return Filters.eq("id", param.get("id"));
                    }
                }, false);

        BulkWriteResult meterWriteResult = getCollection(meterDomains.get(0).getMon().toString(),
                MongoCollectionConfig.ELECTRIC_METER.name()).bulkWrite(writeModels);

        return meterWriteResult.getModifiedCount();
    }

    public long mongoUpdateStatus(List<MeterMongoDomain> meterMongoDomains) {

        List<WriteModel<Document>> writeModels = updateMany(
                toDocuments(meterMongoDomains, new ToDocumentCallBack<MeterMongoDomain>() {

                    @Override
                    public MeterMongoDomain call(MeterMongoDomain t) {
                        // TODO Auto-generated method stub
                        t.setStatus((byte) 2);
                        return t;
                    }

                }), new MongoUpdateFilter() {

                    @Override
                    public Bson filter(Document param) {
                        // TODO Auto-generated method stub
                        return Filters.and(Filters.eq("id", param.get("id")),
                                Filters.eq("sn", param.get("sn"))
                        );
                    }
                }, false);

        BulkWriteResult meterWriteResult = getCollection(meterMongoDomains.get(0).getMon(),
                MongoCollectionConfig.ELECTRIC_METER.name()).bulkWrite(writeModels);

        return meterWriteResult.getModifiedCount();
    }
}
