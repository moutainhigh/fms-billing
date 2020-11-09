package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.mongo.TransformerMongoDomain;

public interface ITransformerService {
    public List<TransformerMongoDomain> getTransformer(TransformerMongoDomain transformer);
}
