/**
 * Author : czy
 * Date : 2019年7月8日 下午4:09:40
 * Title : com.riozenc.billing.webapp.service.IMeterService.java
 *
**/
package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.WriteFilesDomain;
import org.fms.billing.common.webapp.entity.MeterMoneyVerificationEntity;

public interface IMeterService {

	public List<MeterDomain> findMeterByWhere(MeterDomain meterDomain);

	public List<MeterDomain> getEffectiveComputeMeter(Integer mon, List<WriteFilesDomain> writeFilesDomains,Integer... status);

	public List<MeterMoneyVerificationEntity> findMeterMoneyByMongodb(MeterDomain meterDomain);

	public List<MeterDomain> findMeterDomain(MeterDomain meterDomain);

	public List<MeterDomain> getMeterByWriteSectionIds(Integer mon,
													   Integer[] writeSectionIds,
													   Integer... status);
}
