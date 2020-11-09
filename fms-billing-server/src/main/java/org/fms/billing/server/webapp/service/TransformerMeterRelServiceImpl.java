package org.fms.billing.server.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.mongo.TransformerMeterRelDomain;
import org.fms.billing.common.webapp.service.ITransformerMeterRelService;
import org.fms.billing.server.webapp.dao.TransformerMeterRelDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransformerMeterRelServiceImpl implements ITransformerMeterRelService {

    @Autowired
    private TransformerMeterRelDAO transformerMeterRelDAO;

    @Override
    public List<TransformerMeterRelDomain> getTransMeterRel(TransformerMeterRelDomain transformerMeterRel) {
        return transformerMeterRelDAO.getTransMeterRel(transformerMeterRel);
    }
}
