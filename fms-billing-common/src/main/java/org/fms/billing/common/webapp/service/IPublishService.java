package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.ArrearageDomain;
import org.fms.billing.common.webapp.entity.PulishEntity;

import com.riozenc.titanTool.spring.web.http.HttpResult;

public interface IPublishService {
	public HttpResult publishCallback(PulishEntity pulishEntity,String managerId) throws Exception;

	public HttpResult updateMeterMoneyId() throws Exception;

	public PulishEntity processArrearage(PulishEntity pulishEntity,Long managerId) throws Exception;

	public HttpResult backPublishCallback(PulishEntity pulishEntity,
									   String managerId) throws Exception;

	public PulishEntity processMoneyRecall(List<ArrearageDomain> arrearageDomains) throws Exception;

	public PulishEntity offCharge(PulishEntity pulishEntity,
								  Long managerId) throws Exception;
}
