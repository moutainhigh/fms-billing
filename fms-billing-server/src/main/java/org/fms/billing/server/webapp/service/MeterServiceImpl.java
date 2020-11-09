/**
 * Author : czy
 * Date : 2019年7月8日 下午4:12:26
 * Title : com.riozenc.billing.webapp.service.impl.MeterServiceImpl.java
 *
**/
package org.fms.billing.server.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.WriteFilesDomain;
import org.fms.billing.common.webapp.entity.MeterMoneyVerificationEntity;
import org.fms.billing.common.webapp.service.IMeterService;
import org.fms.billing.server.webapp.dao.MeterDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeterServiceImpl implements IMeterService {

	@Autowired
	private MeterDAO meterDAO;

	@Override
	public List<MeterDomain> findMeterByWhere(MeterDomain meterDomain) {
		return meterDAO.findMeterByWhere(meterDomain);
	}

	@Override
	public List<MeterDomain> getEffectiveComputeMeter(Integer mon, List<WriteFilesDomain> writeFilesDomains,Integer... status) {
		return meterDAO.getEffectiveComputeMeter(mon, writeFilesDomains,status);
	}
	@Override
	public List<MeterMoneyVerificationEntity> findMeterMoneyByMongodb(MeterDomain meterDomain){
		return meterDAO.findMeterMoneyByMongodb(meterDomain);
	}

	@Override
	public List<MeterDomain> findMeterDomain(MeterDomain meterDomain) {
		return meterDAO.findMeterDomain(meterDomain);
	}
	@Override
	public List<MeterDomain> getMeterByWriteSectionIds(Integer mon,
													   Integer[] writeSectionIds,
													   Integer... status){
		return meterDAO.getMeterByWriteSectionIds(mon,writeSectionIds,status);
	}


}
