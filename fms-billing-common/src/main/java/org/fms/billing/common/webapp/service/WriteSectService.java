package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.WriteSectDomain;

public interface WriteSectService {

	public List<WriteSectDomain> getWriteSect(WriteSectDomain writeSectionDomain);

	public List<WriteSectDomain> getMeterReadingSituation(WriteSectDomain writeSectionDomain);

	public String getMeterInitSituation(WriteSectDomain writeSectionDomain);

	public List<WriteSectDomain> getMeterCalOrPublicSituation(WriteSectDomain writeSectionDomain,int status);

	List<WriteSectDomain> getWriteSectDomain(WriteSectDomain writeSectDomain);

	List<WriteSectDomain> getWriteSectAndNum(WriteSectDomain writeSectionDomain);

	List<WriteSectDomain> getWriteSectSortByWritorId(WriteSectDomain writeSectionDomain);
}
