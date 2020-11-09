package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.mongo.TransformerMeterRelDomain;

public interface ITransformerMeterRelService {
    List<TransformerMeterRelDomain> getTransMeterRel(TransformerMeterRelDomain transformerMeterRel);
}
