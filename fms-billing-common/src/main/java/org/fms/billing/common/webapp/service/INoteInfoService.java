package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.ChargeDomain;
import org.fms.billing.common.webapp.domain.NoteInfoDomain;
import org.fms.billing.common.webapp.entity.ChargeInfoDetailEntity;
import org.fms.billing.common.webapp.entity.ChargeInfoEntity;

import com.riozenc.titanTool.spring.web.http.HttpResult;

public interface INoteInfoService {
	public List<NoteInfoDomain> findByWhere(NoteInfoDomain noteInfoDomain);
	public int insert(NoteInfoDomain noteInfoDomain);
	public int update(NoteInfoDomain noteInfoDomain);
	public int updateList(List<NoteInfoDomain> noteInfoDomains);
	public int updateByIds(List<NoteInfoDomain> noteInfoDomains);
	public List<NoteInfoDomain> findNoteInfoByIds(List<NoteInfoDomain> noteInfoDomains);
	public HttpResult createNoteInfo(List<ChargeDomain> chargeDomains)throws Exception;
	public List<NoteInfoDomain> findInvoiceNoteInfoByChargeIds(List<Long> chargeInfoIds);
	public List<ChargeInfoDetailEntity> findNoteInfoDetails(ChargeInfoEntity chargeInfoEntity);
	public int insertList(List<NoteInfoDomain> noteInfoDomains) ;
}
