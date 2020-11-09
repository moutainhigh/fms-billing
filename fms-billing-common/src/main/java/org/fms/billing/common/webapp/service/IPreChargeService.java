package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.PreChargeDomain;
import org.fms.billing.common.webapp.domain.beakInterface.CustomerDomain;

import com.riozenc.titanTool.spring.web.http.HttpResult;

public interface IPreChargeService {
	public List<PreChargeDomain> findByWhere(PreChargeDomain preChargeDomain);
	public List<PreChargeDomain> findPreChargeByCustomer(CustomerDomain customerDomain);
	public int insert(PreChargeDomain preChargeDomain);
	public int update(PreChargeDomain preChargeDomain);
	public List<PreChargeDomain> findPreChargeBySettleIds(List<Long> settlementIds);
	public HttpResult synCurrentPreCharge(List<PreChargeDomain>  preChargeDomains);}
