package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.fms.billing.common.webapp.domain.NoteInfoDomain;
import org.fms.billing.common.webapp.entity.ChargeInfoDetailEntity;
import org.fms.billing.common.webapp.entity.ChargeInfoEntity;

import com.riozenc.titanTool.annotation.PaginationSupport;
import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;

@TransactionDAO
public class NoteInfoDAO extends AbstractTransactionDAOSupport{


	public List<NoteInfoDomain> findByWhere(NoteInfoDomain noteInfoDomain){
		return getPersistanceManager().find(getNamespace() + ".findByWhere", noteInfoDomain);
	}
	public int insert(NoteInfoDomain noteInfoDomain) {
		return getPersistanceManager().insert(getNamespace() + ".insert", noteInfoDomain);
	};

	public int insertList(List<NoteInfoDomain> noteInfoDomains) {
		return getPersistanceManager(ExecutorType.BATCH).insertList(getNamespace() + ".insert",
				noteInfoDomains);
	};
	public int update(NoteInfoDomain noteInfoDomain) {
		return getPersistanceManager().update(getNamespace() + ".update", noteInfoDomain);
	}

	public int delete(NoteInfoDomain noteInfoDomain) {
		return getPersistanceManager().delete(getNamespace() + ".delete",
				noteInfoDomain);
	}

	public int deleteByChargeIds(NoteInfoDomain noteInfoDomain) {
		return getPersistanceManager().delete(getNamespace() + ".deleteByChargeIds",
				noteInfoDomain);
	}
	public int updateList(List<NoteInfoDomain> noteInfoDomains) {
		return getPersistanceManager().updateList(getNamespace() + ".update",	noteInfoDomains);
	}

	public int updateByIds(List<NoteInfoDomain> noteInfoDomains) {
		return getPersistanceManager().updateList(getNamespace() + ".updateByIds",	noteInfoDomains);
	}
	public List<NoteInfoDomain> findNoteInfoByIds(List<NoteInfoDomain> noteInfoDomains){
		return getPersistanceManager().find(getNamespace()+".findNoteInfoByIds", noteInfoDomains);
	}

	public List<NoteInfoDomain> findInvoiceNoteInfoByChargeIds(List<Long> chargeInfoIds){
		return getPersistanceManager().find(getNamespace()+".findInvoiceNoteInfoByChargeIds", chargeInfoIds);
	}

	@PaginationSupport
	public List<ChargeInfoDetailEntity> findNoteInfoDetails(ChargeInfoEntity chargeInfoEntity) {
		return getPersistanceManager().find(getNamespace() +
				".findNoteInfoDetails",chargeInfoEntity);
	};




}
