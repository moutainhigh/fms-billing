package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.BackChargeDomain;
import org.fms.billing.common.webapp.entity.MeterPageEntity;

import com.riozenc.titanTool.spring.web.http.HttpResult;

public interface IBackChargeService {
	public List<BackChargeDomain> findByWhere(BackChargeDomain backChargeDomain);

	public int insert(BackChargeDomain backChargeDomain);

	public int update(BackChargeDomain backChargeDomain);

	public List<BackChargeDomain> getBackChargeByMeterIds(MeterPageEntity meterPageEntity);

	public List<BackChargeDomain> getBackCharge(MeterPageEntity meterPageEntity);

	public List<BackChargeDomain> findByChargeInfoIds(List<Long> ids);

	public List<BackChargeDomain> findBackChargeInfoByIds(List<String> ids);

	public HttpResult backCharge( String json) throws Exception;

	public List<BackChargeDomain> getFinishBackCharge(MeterPageEntity meterPageEntity);
	public int delete(BackChargeDomain backChargeDomain) ;
}