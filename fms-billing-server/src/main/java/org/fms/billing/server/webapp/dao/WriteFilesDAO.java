package org.fms.billing.server.webapp.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.fms.billing.common.util.FormatterUtil;
import org.fms.billing.common.util.MonUtils;
import org.fms.billing.common.webapp.domain.WriteFilesDomain;
import org.fms.billing.common.webapp.entity.CbReadingDownEntity;
import org.fms.billing.common.webapp.entity.PulishEntity;
import org.fms.billing.server.web.config.MongoCollectionConfig;

import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.WriteModel;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.common.date.DateUtil;
import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.mongo.dao.MongoDAOSupport;
import com.riozenc.titanTool.mybatis.pagination.Page;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;

@TransactionDAO
public class WriteFilesDAO extends AbstractTransactionDAOSupport implements MongoDAOSupport {

	public List<WriteFilesDomain> findByWhere(WriteFilesDomain writeFilesDomain) {
		return getPersistanceManager().find(getNamespace() + ".findByWhere", writeFilesDomain);
	}

	public List<WriteFilesDomain> findByMeterIdsAndMon(WriteFilesDomain writeFilesDomain) {
		return getPersistanceManager().find(getNamespace() + ".findByMeterIdsAndMon", writeFilesDomain);
	}

	public List<WriteFilesDomain> findByMeterIdsMonPowerDirectionFunctionCodeAndTimeSeg(WriteFilesDomain writeFilesDomain) {
		return getPersistanceManager().find(getNamespace() + ".findByMeterIdsAndMon", writeFilesDomain);
	}

	public int deleteByMeterIdsMonAndSn(PulishEntity pulishEntity) {
		return getPersistanceManager().delete(getNamespace() + ".deleteByMeterIdsMonAndSn", pulishEntity);
	}

	public List<WriteFilesDomain> mongoFind(WriteFilesDomain queryParam) {

		List<WriteFilesDomain> writeFilesDomains = findManyByPage(
				getCollectionName(queryParam.getMon().toString(), MongoCollectionConfig.WRITE_FILES.name()),
				new MongoFindFilter() {
					@Override
					public Bson filter() {
						if (queryParam.getWriteFlag() != null && queryParam.getWriteFlag() == 0) {
							queryParam.setWriteFlag(null);
							return Filters.and(Document.parse(GsonUtils.getIgnorReadGson().toJson(queryParam)),
									Filters.ne("writeFlag", 1));
						} else {
							return Document.parse(GsonUtils.getIgnorReadGson().toJson(queryParam));
						}
					}

					@Override
					public Bson getSort() {
						return new Document("writeSn", 1).append("meterId", 1).append("functionCode", 1)
								.append("timeSeg", 1).append("phaseSeq", 1);
					}

					@Override
					public Page getPage() {
						// TODO Auto-generated method stub
						return queryParam;
					}

				}, WriteFilesDomain.class);

		return writeFilesDomains;
	}

	// 抄表区段

	public List<WriteFilesDomain> mongoFindByWrite(WriteFilesDomain queryParam) {

		Integer[] writeSectionIds = queryParam.getWriteSectionIds();
		queryParam.setWriteSectionIds(null);
		List<WriteFilesDomain> writeFilesDomains = findManyByPage(
				getCollectionName(queryParam.getMon().toString(), MongoCollectionConfig.WRITE_FILES.name()),
				new MongoFindFilter() {
					@Override
					public Bson filter() {
						if (queryParam.getWriteFlag() != null && queryParam.getWriteFlag() == 0) {
							Document document = Document.parse(GsonUtils.getIgnorReadGson().toJson(queryParam));
							document.remove("writeFlag");
							return Filters.and(document, Filters.ne("writeFlag", 1),
									(null == writeSectionIds ? new Document()
											: Filters.in("writeSectionId", writeSectionIds)));
						} else {
							return Filters.and(Document.parse(GsonUtils.getIgnorReadGson().toJson(queryParam)),
									(null == writeSectionIds ? new Document()
											: Filters.in("writeSectionId", writeSectionIds)));
						}
					}

					@Override
					public Bson getSort() {
						// TODO Auto-generated method stub
						return new Document("writeSectionId",1).append("writeSn",1).append("meterId", 1).append("functionCode", 1).append("timeSeg", 1).append("phaseSeq", 1);
					}

					@Override
					public Page getPage() {
						// TODO Auto-generated method stub
						return queryParam;
					}

				}, WriteFilesDomain.class);

		return writeFilesDomains;
	}

	// 抄表器下装

	public List<WriteFilesDomain> mongoFindForReadingDown(WriteFilesDomain queryParam) {

		Integer[] writeSectionIds = queryParam.getWriteSectionIds();
		queryParam.setWriteSectionIds(null);
		List<WriteFilesDomain> writeFilesDomains = findManyByPage(
				getCollectionName(queryParam.getMon().toString(), MongoCollectionConfig.WRITE_FILES.name()),
				new MongoFindFilter() {
					@Override
					public Bson filter() {
						List<Bson> filters = new ArrayList<Bson>();
						if (queryParam.getMon() != null) {
							filters.add(Filters.eq("mon", queryParam.getMon()));
						}
						if (queryParam.getSn() != null) {
							filters.add(Filters.eq("sn", queryParam.getSn()));
						}
						if (writeSectionIds != null) {
							filters.add(Filters.in("writeSectionId", writeSectionIds));
						}
						// TODO Auto-generated method stub
						return Filters.and(filters);
					}

					@Override
					public Page getPage() {
						// TODO Auto-generated method stub
						return queryParam;
					}

				}, WriteFilesDomain.class);

		return writeFilesDomains;
	}

	// 抄表器上装
	public long mongoUpdateByCBQ(List<CbReadingDownEntity> cbReadingDownEntities) {

		List<WriteModel<Document>> writeModels = updateMany(
				toDocuments(cbReadingDownEntities, new ToDocumentCallBack<CbReadingDownEntity>() {

					@Override
					public CbReadingDownEntity call(CbReadingDownEntity t) {
						// TODO Auto-generated method stub
						CbReadingDownEntity cbReadingDownEntity = new CbReadingDownEntity();
						cbReadingDownEntity.setEndNum(t.getEndNum());
						cbReadingDownEntity.setInputMan(t.getInputMan());
						cbReadingDownEntity.setWriteFlag(1);
						cbReadingDownEntity.setDiffNum(FormatterUtil.calcDiff((t.getStartNum()==null? BigDecimal.ZERO:t.getStartNum()),t.getEndNum()));
						cbReadingDownEntity.set_id(t.get_id());
						cbReadingDownEntity.setWriteDate(t.getWriteDate());
						return cbReadingDownEntity;
					}

				}), new MongoUpdateFilter() {

					@Override
					public Bson filter(Document param) {
						// TODO Auto-generated method stub
						return Filters.and(Filters.eq("_id", param.get("_id")));
					}
				}, false);

		// System.out.println("writeModels========="+writeModels);
		BulkWriteResult meterWriteResult = getCollection(cbReadingDownEntities.get(0).getMon().toString(),
				MongoCollectionConfig.WRITE_FILES.name()).bulkWrite(writeModels);

		cbReadingDownEntities.forEach(t->{
			String[] strArray = t.get_id().split("#");
			t.setMeterId(Long.valueOf(strArray[0]));
		});

		List<WriteModel<Document>> meterModels = updateMany(
				toDocuments(cbReadingDownEntities, new ToDocumentCallBack<CbReadingDownEntity>() {

					@Override
					public CbReadingDownEntity call(CbReadingDownEntity t) {
						// TODO Auto-generated method stub
						CbReadingDownEntity cbReadingDownEntity = new CbReadingDownEntity();
						cbReadingDownEntity.set_id(t.getMeterId()+"#"+t.getSn());
						cbReadingDownEntity.setStatus(1);
						return cbReadingDownEntity;
					}

				}), new MongoUpdateFilter() {

					@Override
					public Bson filter(Document param) {
						// TODO Auto-generated method stub
						return Filters.and(Filters.eq("_id", param.get("_id")));
					}
				}, false);
		BulkWriteResult meterResult = getCollection(cbReadingDownEntities.get(0).getMon().toString(),
				MongoCollectionConfig.ELECTRIC_METER.name()).bulkWrite(meterModels);

		return meterWriteResult.getModifiedCount();
	}

	public long mongoUpdate(List<WriteFilesDomain> writeFilesDomains) {

		List<Document> meterDocuments = writeFilesDomains.stream().map(w -> {
			return new Document("id", w.getMeterId()).append("sn", w.getSn()).append("status", 1);
		}).distinct().collect(Collectors.toList());

		List<WriteModel<Document>> writeModels = updateMany(
				toDocuments(writeFilesDomains, new ToDocumentCallBack<WriteFilesDomain>() {

					@Override
					public WriteFilesDomain call(WriteFilesDomain t) {
						t.setWriteFlag((byte) 1);
						t.setWriteDate(DateUtil.getDateFromUTC());
						return t;
					}

				}), new MongoUpdateFilter() {

					@Override
					public Bson filter(Document param) {
						return Filters.and(Filters.eq("meterId", param.get("meterId")),
								Filters.eq("timeSeg", param.get("timeSeg")), Filters.eq("sn", param.get("sn")),
								Filters.eq("powerDirection", param.get("powerDirection")),
								Filters.eq("functionCode", param.get("functionCode")),
								Filters.eq("phaseSeq", param.get("phaseSeq")));
					}
				}, false);

		List<WriteModel<Document>> writeModels2 = updateMany(meterDocuments, new MongoUpdateFilter() {

			@Override
			public Bson filter(Document param) {
				return Filters.and(Filters.eq("id", param.get("id")), Filters.eq("sn", param.get("sn")));
			}
		}, false);

		BulkWriteResult meterWriteResult = getCollection(writeFilesDomains.get(0).getMon().toString(),
				MongoCollectionConfig.WRITE_FILES.name()).bulkWrite(writeModels);

		BulkWriteResult meterWriteResult2 = getCollection(writeFilesDomains.get(0).getMon().toString(),
				MongoCollectionConfig.ELECTRIC_METER.name()).bulkWrite(writeModels2);

		return meterWriteResult.getModifiedCount();
	}

	public List<WriteFilesDomain> getWriteFiles(WriteFilesDomain writeFilesDomain) {
		List<WriteFilesDomain> domains = findMany(
				getCollection(writeFilesDomain.getMon().toString(), MongoCollectionConfig.WRITE_FILES.name()),
				new MongoFindFilter() {

					@Override
					public Bson filter() {
						List<Bson> filters = new ArrayList<Bson>();
						if (writeFilesDomain.getMeterId() != null) {
							filters.add(Filters.eq("meterId", writeFilesDomain.getMeterId()));
						}
						if (writeFilesDomain.getMeterAssetsId() != null) {
							filters.add(Filters.eq("meterAssetsId", writeFilesDomain.getMeterAssetsId()));
						}
						if (writeFilesDomain.getFunctionCode() != null) {
							filters.add(Filters.eq("functionCode", writeFilesDomain.getFunctionCode()));
						}
						// TODO Auto-generated method stub
						return Filters.and(filters);
					}
				}, WriteFilesDomain.class);

		return domains;
	}

	/**
	 * 用于report调用
	 * 
	 * @param writeFilesDomain
	 * @return
	 */
	public List<WriteFilesDomain> getWriteFilesDomain2(WriteFilesDomain writeFilesDomain) {
		List<WriteFilesDomain> result = findManyByPage(
				getCollectionName(writeFilesDomain.getMon().toString(), MongoCollectionConfig.WRITE_FILES.name()),
				new MongoFindFilter() {

					@Override
					public Bson filter() {
						Document document = new Document();
						if (writeFilesDomain.getMon() != null) {
							document.put("mon", writeFilesDomain.getMon());
						}
						if (writeFilesDomain.getDiffNum() != null) {
							document.put("diffNum", writeFilesDomain.getDiffNum());
						}
						if (writeFilesDomain.getWriteFlag() != null) {
							document.put("writeFlag", writeFilesDomain.getWriteFlag());
						}
						if (writeFilesDomain.getWriteSectionIds() != null) {
							document.put("writeSectionId", Document
									.parse("{$in:" + Arrays.asList(writeFilesDomain.getWriteSectionIds()) + "}"));
						} else if (writeFilesDomain.getWriteSectionId() != null) {
							document.put("writeSectionId", writeFilesDomain.getWriteSectionId());
						}
						if (writeFilesDomain.getBusinessPlaceCodes() != null) {
							document.put("businessPlaceCode", Document
									.parse("{$in:" + Arrays.asList(writeFilesDomain.getBusinessPlaceCodes()) + "}"));
						} else if (writeFilesDomain.getBusinessPlaceCode() != null) {
							document.put("businessPlaceCode", writeFilesDomain.getBusinessPlaceCode());
						}
						if (writeFilesDomain.getMeterId() != null) {
							document.put("meterId", writeFilesDomain.getMeterId());
						}
						if (writeFilesDomain.getPhaseSeq() != null) {
							document.put("phaseSeq", writeFilesDomain.getPhaseSeq());
						}
						if (writeFilesDomain.getPowerDirection() != null) {
							document.put("powerDirection", writeFilesDomain.getPowerDirection());
						}
						if (writeFilesDomain.getTimeSeg() != null) {
							document.put("timeSeg", writeFilesDomain.getTimeSeg());
						}
						if (writeFilesDomain.getEndNum() != null) {
							document.put("endNum", writeFilesDomain.getEndNum());
						}
						if (writeFilesDomain.getUserNo() != null) {
							document.put("userNo", writeFilesDomain.getUserNo());
						}
						if (writeFilesDomain.getMeterIds() != null) {
							document.put("meterId", Document
									.parse("{$in:" + writeFilesDomain.getMeterIds() + "}"));
						}
						return document;
					}

					@Override
					public Page getPage() {
						// TODO Auto-generated method stub
						return writeFilesDomain;
					}
				}, WriteFilesDomain.class);
		return result;
	}

	/**
	 * 未抄表户查询
	 * 
	 * @param writeFilesDomain
	 * @return
	 */
	public List<WriteFilesDomain> selectWriteFilesList(WriteFilesDomain writeFilesDomain) {
		List<WriteFilesDomain> writeFilesDomains = findManyByPage(
				getCollectionName(writeFilesDomain.getMon().toString(), MongoCollectionConfig.WRITE_FILES.name()),
				new MongoFindFilter() {
					@Override
					public Bson filter() {
						Document document = new Document();
						Integer[] writeSectionIds = writeFilesDomain.getWriteSectionIds();
						if (writeFilesDomain.getMon() != null) {
							document.put("mon", writeFilesDomain.getMon());
						}
						if (writeFilesDomain.getWriteFlag() != null) {
							document.put("writeFlag", writeFilesDomain.getWriteFlag());
						}
						if (writeFilesDomain.getMeterId() != null) {
							document.put("meterId", writeFilesDomain.getMeterId());
						}
						if (writeFilesDomain.getBusinessPlaceCode() != null) {
							document.put("businessPlaceCode", writeFilesDomain.getBusinessPlaceCode());
						}
						if (writeFilesDomain.getWriteSectionId() != null) {
							document.put("writeSectionId", writeFilesDomain.getWriteSectionId());
						}
						if (writeSectionIds != null && writeSectionIds.length != 0) {
							document.put("writeSectionId",
									Document.parse("{$in:" + Arrays.toString(writeSectionIds) + "}"));
						}
						if (writeFilesDomain.getUnWriteFlag() != null) {
							document.put("writeFlag",
									Document.parse("{$ne:" + writeFilesDomain.getUnWriteFlag() + "}"));
						}
						return document;
					}

					@Override
					public Bson getSort() {
						// TODO Auto-generated method stub
						return new Document("writeSectionId", 1).append("userId", 1).append("meterNo", 1)
								.append("functionCode", 1);
					}

					@Override
					public Page getPage() {
						// TODO Auto-generated method stub
						return writeFilesDomain;
					}
				}, WriteFilesDomain.class);

		return writeFilesDomains;
	}

	/**
	 * 过时方法 关联WRITE_FILES和METER_METER_ASSETS_REL，速度慢
	 * 
	 * @param writeFiles
	 * @return
	 */
	public List<WriteFilesDomain> getWriteFilesList(WriteFilesDomain writeFiles) {
		List<Document> list = aggregate(
				getCollectionName(writeFiles.getMon().toString(), MongoCollectionConfig.WRITE_FILES.name()),
				new MongoAggregateLookupFilter() {

					@Override
					public Bson setMatch() {
						Document document = new Document();
						if (writeFiles.getMon() != null) {
							document.put("mon", writeFiles.getMon());
						}
						if (writeFiles.getDiffNum() != null) {
							document.put("diffNum", writeFiles.getDiffNum());
						}
						if (writeFiles.getWriteFlag() != null) {
							document.put("writeFlag", writeFiles.getWriteFlag());
						}
						if (writeFiles.getWriteSectionIds() != null) {
							document.put("writeSectionId",
									Document.parse("{$in:" + Arrays.asList(writeFiles.getWriteSectionIds()) + "}"));
						} else if (writeFiles.getWriteSectionId() != null) {
							document.put("writeSectionId", writeFiles.getWriteSectionId());
						}
						if (writeFiles.getBusinessPlaceCodes() != null) {
							document.put("businessPlaceCode",
									Document.parse("{$in:" + Arrays.asList(writeFiles.getBusinessPlaceCodes()) + "}"));
						} else if (writeFiles.getBusinessPlaceCode() != null) {
							document.put("businessPlaceCode", writeFiles.getBusinessPlaceCode());
						}
						if (writeFiles.getMeterId() != null) {
							document.put("meterId", writeFiles.getMeterId());
						}
						if (writeFiles.getPhaseSeq() != null) {
							document.put("phaseSeq", writeFiles.getPhaseSeq());
						}
						if (writeFiles.getPowerDirection() != null) {
							document.put("powerDirection", writeFiles.getPowerDirection());
						}
						if (writeFiles.getTimeSeg() != null) {
							document.put("timeSeg", writeFiles.getTimeSeg());
						}
						return document;
					}

					@Override
					public Bson setLookup() {
						String json = "{" + "'from': '"
								+ getCollectionName(MonUtils.getMon(writeFiles.getMon().toString()),
										MongoCollectionConfig.METER_METER_ASSETS_REL.name())
								+ "'," + "'let': {'meterId': '$meterId','meterAssetsId': '$meterAssetsId'},"
								+ "'pipeline': [" + "{'$match': " + "{'$expr': " + "{'$and': ["
								+ "{'$eq': ['$meterId','$$meterId']}," + "{'$eq': ['$meterAssetsId','$$meterAssetsId']}"
								+ "]" + "}" + "}" + "}" + "]," + "'as': 'rel'" + "}";
						return BsonDocument.parse(json);
					}
				});
		String json = GsonUtils.toJson(list);
		return GsonUtils.readValueToList(json, WriteFilesDomain.class);
	}

	/**
	 * 过时方法
	 * 
	 * @param writeFilesDomain
	 * @return
	 */
	@Deprecated
	public String getWriteFilesDomain(WriteFilesDomain writeFilesDomain) {
		List<Document> result = aggregate(
				getCollectionName(writeFilesDomain.getMon().toString(), MongoCollectionConfig.WRITE_FILES.name()),
				new MongoAggregateLookupFilter() {

					@Override
					public List<? extends Bson> getPipeline() {
						List<Bson> pipeLine = new LinkedList<>();
						pipeLine.add(getMatch());
						pipeLine.add(getLookup());
						pipeLine.add(getLookupWriteSect());
						pipeLine.add(getLookupElectricMeter());
						pipeLine.add(getMatchMeter());
						return pipeLine;
					}

					@Override
					public Bson setLookup() {
						String json = "{" + "'from': '"
								+ getCollectionName(MonUtils.getLastMon(writeFilesDomain.getMon().toString()),
										MongoCollectionConfig.WRITE_FILES.name())
								+ "',"
								+ "'let': {'functionCode': '$functionCode','meterId': '$meterId','phaseSeq': '$phaseSeq','powerDirection': '$powerDirection','timeSeg': '$timeSeg'},"
								+ "'pipeline': [" + "{'$match': " + "{'$expr': " + "{'$and': ["
								+ "{'$eq': ['$functionCode','$$functionCode']}," + "{'$eq': ['$meterId','$$meterId']},"
								+ "{'$eq': ['$phaseSeq','$$phaseSeq']},"
								+ "{'$eq': ['$powerDirection','$$powerDirection']},"
								+ "{'$eq': ['$timeSeg','$$timeSeg']}" + "]" + "}" + "}" + "}" + "],"
								+ "'as': 'lastWriteFiles'" + "}";
						return BsonDocument.parse(json);
					}

					public Bson setLookupWriteSect() {
						Document document = new Document();
						document.put("from", getCollectionName(writeFilesDomain.getMon().toString(),
								MongoCollectionConfig.WRITE_SECT.name()));
						document.put("localField", "writeSectionId");
						document.put("foreignField", "id");
						document.put("as", "writeSect");
						return document;
					}

					public Bson getLookupWriteSect() {
						return new Document("$lookup", this.setLookupWriteSect());
					}

					public Bson setLookupElectricMeter() {
						Document document = new Document();
						document.put("from", getCollectionName(writeFilesDomain.getMon().toString(),
								MongoCollectionConfig.ELECTRIC_METER.name()));
						document.put("localField", "meterId");
						document.put("foreignField", "id");
						document.put("as", "electricMeter");
						return document;
					}

					public Bson getLookupElectricMeter() {
						return new Document("$lookup", this.setLookupElectricMeter());
					}

					@Override
					public Bson setMatch() {
						Document document = new Document();
						if (writeFilesDomain.getMon() != null) {
							document.put("mon", writeFilesDomain.getMon());
						}
						if (writeFilesDomain.getDiffNum() != null) {
							document.put("diffNum", writeFilesDomain.getDiffNum());
						}
						if (writeFilesDomain.getWriteFlag() != null) {
							document.put("writeFlag", writeFilesDomain.getWriteFlag());
						}
						if (writeFilesDomain.getWriteSectionId() != null) {
							document.put("writeSectionId", writeFilesDomain.getWriteSectionId());
						}
						if (writeFilesDomain.getBusinessPlaceCodes() != null) {
							document.put("businessPlaceCode", Document
									.parse("{$in:" + Arrays.asList(writeFilesDomain.getBusinessPlaceCodes()) + "}"));
						} else if (writeFilesDomain.getBusinessPlaceCode() != null) {
							document.put("businessPlaceCode", writeFilesDomain.getBusinessPlaceCode());
						}
						return document;
					}

					public Bson setMatchMeter() {
						Document document = new Document();
						if (writeFilesDomain.getWritorId() != null) {
							document.put("electricMeter.writorId", writeFilesDomain.getWritorId());
						}
						return document;
					}

					public Bson getMatchMeter() {
						return new Document("$match", this.setMatchMeter());
					}

				});
		String json = GsonUtils.toJson(result);
		return json;
	}

}
