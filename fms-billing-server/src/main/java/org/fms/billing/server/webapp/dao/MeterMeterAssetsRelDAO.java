package org.fms.billing.server.webapp.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;
import org.fms.billing.common.webapp.domain.mongo.MeterMeterAssetsRelDomain;
import org.fms.billing.server.web.config.MongoCollectionConfig;
import org.springframework.stereotype.Repository;

import com.mongodb.client.model.Filters;
import com.riozenc.titanTool.mongo.dao.MongoDAOSupport;

@Repository
public class MeterMeterAssetsRelDAO implements MongoDAOSupport {

    public List<MeterMeterAssetsRelDomain> mongoFind(MeterMeterAssetsRelDomain rel) {
        List<MeterMeterAssetsRelDomain> list = findMany(
                getCollectionName(rel.getMon().toString(), MongoCollectionConfig.METER_METER_ASSETS_REL.name()),
                new MongoFindFilter() {
                    @Override
                    public Bson filter() {
                        List<Bson> filters = new ArrayList<Bson>();
                        if (rel.getMeterIds() != null) {
                            filters.add(Filters.in("meterId", rel.getMeterIds()));
                        } else if (rel.getMeterId() != null) {
                            filters.add(Filters.eq("meterId", rel.getMeterId()));
                        }
                        if (rel.getMeterAssetsId() != null) {
                            filters.add(Filters.eq("meterAssetsId", rel.getMeterAssetsId()));
                        }
                        if (rel.getMeterAssetsIds() != null && rel.getMeterAssetsIds().size()>0) {
                            filters.add(Filters.in("meterAssetsId", rel.getMeterAssetsIds()));
                        }
                        filters.add(Filters.eq("status", 1));
                        return Filters.and(filters);
                    }
                }, MeterMeterAssetsRelDomain.class);
        return list;
    }

}
