package org.fms.billing.server.webapp.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.fms.billing.common.webapp.domain.WriteSectDomain;
import org.fms.billing.server.web.config.MongoCollectionConfig;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.model.Filters;
import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.mongo.dao.MongoDAOSupport;
import com.riozenc.titanTool.mybatis.pagination.Page;

@Repository
public class WriteSectDAO implements MongoDAOSupport {

    public List<WriteSectDomain> getWriteSect(WriteSectDomain writeSectDomain) {

        List<WriteSectDomain> domains = findManyByPage(
                getCollectionName(writeSectDomain.getMon(), MongoCollectionConfig.WRITE_SECT.name()),
                new MongoFindFilter() {

                    @Override
                    public Bson filter() {
                        // TODO Auto-generated method stub
//						return Document.parse(GsonUtils.toJsonIgnoreNull(writeSectDomain));

                        List<Bson> filters = new ArrayList<Bson>();
                        if (writeSectDomain.getWriteSectNo() != null) {
                            filters.add(Filters.eq("writeSectNo", writeSectDomain.getWriteSectNo()));
                        }
                        if (writeSectDomain.getWriteSectionIds() != null) {
                            filters.add(Filters.in("id",
                                    writeSectDomain.getWriteSectionIds()));
                        } else if (writeSectDomain.getId() != null) {
                            filters.add(Filters.eq("id", writeSectDomain.getId()));
                        }
                        if (writeSectDomain.getWritorId() != null) {
                            filters.add(Filters.eq("writorId", writeSectDomain.getWritorId()));
                        }
                        if (writeSectDomain.getBusinessPlaceCode() != null) {
                            filters.add(Filters.eq("businessPlaceCode", writeSectDomain.getBusinessPlaceCode()));
                        }
                        if (writeSectDomain.getWriteSectName() != null) {
                            filters.add(Filters.regex("writeSectName", Pattern.compile(
                                    "^.*" + writeSectDomain.getWriteSectName() + ".*$", Pattern.CASE_INSENSITIVE)));
                        }
                        filters.add(Filters.eq("status", 1));
                        return Filters.and(filters);
                    }

                    @Override
                    public Bson getSort() {
                        // TODO Auto-generated method stub
                        return new Document("id", 1);
                    }

                    @Override
                    public Page getPage() {
                        // TODO Auto-generated method stub
                        return writeSectDomain;
                    }
                }, WriteSectDomain.class);

        return domains;
    }

    /**
     * 已初始化数量
     *
     * @param writeSectDomain
     * @return
     */
    public String getMeterInitSituation(WriteSectDomain writeSectDomain) {

        List<Document> documents = aggregate(
                getCollectionName(writeSectDomain.getMon(), MongoCollectionConfig.ELECTRIC_METER.name()),
                new MongoAggregateGroupFilter() {
                    @Override
                    public Bson setGroup() {
                        // TODO Auto-generated method stub
                        Document group = new Document();
                        group.put("_id", "$writeSectionId");
                        group.put("count", new Document("$sum", 1));
                        return group;
                    }

                    @Override
                    public Bson setMatch() {
                        Document match = new Document();
                        return match;
                    }

                });

        Map<Integer, Integer> result = new HashMap<>();

        documents.forEach(d -> {

            if(d.getInteger("_id")!=null){
                result.put(d.getInteger("_id"), d.getInteger("count"));
            }
            //	result.put("count", d.getInteger("count"));

        });

        return GsonUtils.toJson(result);


//		long count = getCount(getCollectionName(writeSectDomain.getMon(), MongoCollectionConfig.ELECTRIC_METER.name()),
//				new MongoFindFilter() {
//
//					@Override
//					public Bson filter() {
//						// TODO Auto-generated method stub
//						return Filters.eq("writeSectionId", writeSectDomain.getId());
//					}
//				});
//
//		return count;
    }

    /**
     * 已抄表数量
     *
     * @param writeSectDomain
     * @return
     */
    public List<WriteSectDomain> getMeterReadingSituation(WriteSectDomain writeSectDomain) {

        List<Document> documents = aggregate(
                getCollectionName(writeSectDomain.getMon(), MongoCollectionConfig.WRITE_FILES.name()),
                new MongoAggregateGroupFilter() {
                    @Override
                    public Bson setGroup() {
                        // TODO Auto-generated method stub
                        Document group = new Document();
                        group.put("_id",
                                Document.parse("{'WRITE_FLAG' : '$writeFlag', 'WRITE_SECTION_ID':'$writeSectionId'}"));
                        group.put("count", new Document("$sum", 1));
                        return group;
                    }

                    @Override
                    public Bson setMatch() {
                        // TODO Auto-generated method stub
                        Document match = new Document();
                        if (writeSectDomain.getWriteSectionIds() != null && writeSectDomain.getWriteSectionIds().size() > 0) {
                            match.put("writeSectionId", new Document("$in",
                                    writeSectDomain.getWriteSectionIds()));
                        }
                        return match;
                    }
                });


		/*ap<String, Integer> result = new HashMap<>();
		result.put("complete", 0);
		result.put("uncomplete", 0);*/

        List<WriteSectDomain> writeSectDomains = new ArrayList<>();

        documents.forEach(d -> {
            WriteSectDomain returnWriteSectDomain = new WriteSectDomain();
            JSONObject jsonObject =
                    JSONObject.parseObject(JSONObject.toJSONString(d.get("_id")),
                            JSONObject.class);

            returnWriteSectDomain.setId(jsonObject.getLong("WRITE_SECTION_ID"));

            Integer count = d.getInteger("count");
            if (count==null) {
                count = 0;
            }
            if (jsonObject.getInteger("WRITE_FLAG")!=null&&jsonObject.getInteger("WRITE_FLAG") == 1) {
                returnWriteSectDomain.setUncomplete(BigDecimal.ZERO);
                returnWriteSectDomain.setComplete(new BigDecimal(count));
            } else {
                returnWriteSectDomain.setUncomplete(new BigDecimal(count));
                returnWriteSectDomain.setComplete(BigDecimal.ZERO);
            }
            writeSectDomains.add(returnWriteSectDomain);
        });



        return writeSectDomains;
    }


    // 已算数量
    public List<WriteSectDomain> getMeterCalOrPublicSituation(WriteSectDomain writeSectDomain,int status) {

        List<Document> documents = aggregate(
                getCollectionName(writeSectDomain.getMon(), MongoCollectionConfig.ELECTRIC_METER.name()),
                new MongoAggregateGroupFilter() {
                    @Override
                    public Bson setGroup() {
                        Document group = new Document();
                        group.put("_id",
                                Document.parse("{'STATUS' : '$status','WRITE_SECTION_ID':'$writeSectionId'}"));
                        group.put("count", new Document("$sum", 1));
                        return group;
                    }

                    @Override
                    public Bson setMatch() {
                        // TODO Auto-generated method stub
                        Document match = new Document();
                        if (writeSectDomain.getWriteSectionIds() != null && writeSectDomain.getWriteSectionIds().size() > 0) {
                            match.put("writeSectionId", new Document("$in",
                                    writeSectDomain.getWriteSectionIds()));
                        }
                        return match;
                    }
                });

        List<WriteSectDomain> writeSectDomains = new ArrayList<>();

        documents.forEach(d -> {
            WriteSectDomain returnWriteSectDomain = new WriteSectDomain();
            JSONObject jsonObject =
                    JSONObject.parseObject(JSONObject.toJSONString(d.get("_id")),
                            JSONObject.class);

            returnWriteSectDomain.setId(jsonObject.getLong("WRITE_SECTION_ID"));

            Integer count = d.getInteger("count");
            if (count==null) {
                count = 0;
            }
            if (jsonObject.getInteger("STATUS")!=null&&jsonObject.getInteger("STATUS")>=status) {
                returnWriteSectDomain.setCalcComplete(new BigDecimal(count));
                returnWriteSectDomain.setCalcUncomplete(BigDecimal.ZERO);
            } else {
                returnWriteSectDomain.setCalcComplete(BigDecimal.ZERO);
                returnWriteSectDomain.setCalcUncomplete(new BigDecimal(count));
            }
            writeSectDomains.add(returnWriteSectDomain);
        });

        return writeSectDomains;
    }

    /**
     * 用于report调用
     *
     * @param writeSectDomain
     * @return
     */
    public List<WriteSectDomain> getWriteSectDomain(WriteSectDomain writeSectDomain) {
        List<WriteSectDomain> result = findMany(
                getCollectionName(writeSectDomain.getMon().toString(), MongoCollectionConfig.WRITE_SECT.name()),
                new MongoFindFilter() {

                    @Override
                    public Bson filter() {
                        Document document = new Document();
                        if (writeSectDomain.getMon() != null) {
                            document.put("mon", writeSectDomain.getMon());
                        }
                        if (writeSectDomain.getWriteSectionIds() != null) {
                            document.put("id", Document
                                    .parse("{$in:" + writeSectDomain.getWriteSectionIds() + "}"));
                        } else if (writeSectDomain.getId() != null) {
                            document.put("id", writeSectDomain.getId());
                        }
                        if (writeSectDomain.getBusinessPlaceCodes() != null) {
                            document.put("businessPlaceCode", Document
                                    .parse("{$in:" + writeSectDomain.getBusinessPlaceCodes() + "}"));
                        } else if (writeSectDomain.getBusinessPlaceCode() != null) {
                            document.put("businessPlaceCode", writeSectDomain.getBusinessPlaceCode());
                        }
                        if (writeSectDomain.getWritorId() != null) {
                            document.put("writorId", writeSectDomain.getWritorId());
                        }
                        return document;
                    }
                }, WriteSectDomain.class);
        return result;
    }


    public List<WriteSectDomain> getWriteSectSortByWritorId(WriteSectDomain writeSectDomain) {

        List<WriteSectDomain> domains = findManyByPage(
                getCollectionName(writeSectDomain.getMon(), MongoCollectionConfig.WRITE_SECT.name()),
                new MongoFindFilter() {

                    @Override
                    public Bson filter() {
                        // TODO Auto-generated method stub
//						return Document.parse(GsonUtils.toJsonIgnoreNull(writeSectDomain));

                        List<Bson> filters = new ArrayList<Bson>();
                        if (writeSectDomain.getWriteSectNo() != null) {
                            filters.add(Filters.eq("writeSectNo", writeSectDomain.getWriteSectNo()));
                        }

                        if (writeSectDomain.getId() != null) {
                            filters.add(Filters.eq("id", writeSectDomain.getId()));
                        }
                        if (writeSectDomain.getWritorId() != null) {
                            filters.add(Filters.eq("writorId", writeSectDomain.getWritorId()));
                        }
                        if (writeSectDomain.getBusinessPlaceCode() != null) {
                            filters.add(Filters.eq("businessPlaceCode", writeSectDomain.getBusinessPlaceCode()));
                        }
                        if (writeSectDomain.getWriteSectName() != null) {
                            filters.add(Filters.regex("writeSectName", Pattern.compile(
                                    "^.*" + writeSectDomain.getWriteSectName() + ".*$", Pattern.CASE_INSENSITIVE)));
                        }
                        filters.add(Filters.eq("status", 1));
                        return Filters.and(filters);
                    }

                    @Override
                    public Bson getSort() {
                        // TODO Auto-generated method stub
                        return new Document("writorId", 1).append("id",1);
                    }

                    @Override
                    public Page getPage() {
                        // TODO Auto-generated method stub
                        return writeSectDomain;
                    }
                }, WriteSectDomain.class);

        return domains;
    }


}
