package org.fms.billing.server.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.mongo.TransformerMongoDomain;
import org.fms.billing.common.webapp.service.ITransformerService;
import org.fms.billing.server.webapp.dao.TransformerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransformerServiceImpl implements ITransformerService {
    @Autowired
    private TransformerDAO transformerDAO;

    @Override
    public List<TransformerMongoDomain> getTransformer(TransformerMongoDomain transformer) {
        return transformerDAO.getTransformer(transformer);
    }
}
