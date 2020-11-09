/**
 * Author : czy
 * Date : 2019年11月11日 下午3:08:54
 * Title : com.riozenc.billing.webapp.dao.PublishDAO.java
 *
**/
package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.fms.billing.common.webapp.domain.ArrearageDomain;
import org.fms.billing.common.webapp.domain.ChargeDomain;
import org.fms.billing.common.webapp.domain.MeterMoneyDomain;
import org.fms.billing.common.webapp.domain.NoteInfoDomain;
import org.fms.billing.common.webapp.domain.PreChargeDomain;
import org.fms.billing.common.webapp.domain.WriteFilesDomain;
import org.fms.billing.common.webapp.entity.PulishEntity;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;

@TransactionDAO
public class PublishDAO extends AbstractTransactionDAOSupport {

	private String writeFilesNamespace = WriteFilesDAO.class.getName();
	private String meterMoneyNamespace = MeterMoneyDAO.class.getName();
	private String arrearageNamespace = ArrearageDAO.class.getName();
	private String preChargeNamesapce=PreChargeDAO.class.getName();
	private String chargeNamesapce=ChargeDAO.class.getName();
	private String noteInfosapce=NoteInfoDAO.class.getName();

	public int deleteWriteFilesByParam(List<WriteFilesDomain> writeFilesDomains) {
		return getPersistanceManager(ExecutorType.BATCH).deleteList(writeFilesNamespace + ".deleteByParam",
				writeFilesDomains);
	}
	public int deleteWriteFilesByMeterIdsMonAndSn(PulishEntity pulishEntity) {
		return getPersistanceManager().delete(writeFilesNamespace +".deleteWriteFilesByMeterIdsMonAndSn",pulishEntity);
	}

	public int insertWriteFilesList(List<WriteFilesDomain> writeFilesDomains) {
		return getPersistanceManager(ExecutorType.BATCH).insertList(writeFilesNamespace + ".insert", writeFilesDomains);
	}

	public int deleteMeterMoneyByParam(List<MeterMoneyDomain> meterMoneyDomains) {
		return getPersistanceManager(ExecutorType.BATCH).deleteList(meterMoneyNamespace + ".deleteByParam",
				meterMoneyDomains);
	}
	public int deleteMeterMoneyByMeterIdsMonAndSn(PulishEntity pulishEntity) {
		return getPersistanceManager().delete(meterMoneyNamespace +
				".deleteMeterMoneyByMeterIdsMonAndSn",pulishEntity);
	}
	public int insertMeterMoneyList(List<MeterMoneyDomain> meterMoneyDomains) {
		return getPersistanceManager(ExecutorType.BATCH).insertList(meterMoneyNamespace + ".insert", meterMoneyDomains);
	}

	public int insertMeterMoneyForeach(List<MeterMoneyDomain> meterMoneyDomains) {
		return getPersistanceManager().insert(meterMoneyNamespace +
				".insertForeach",meterMoneyDomains);
	}

	public int deleteArrearageByParam(List<ArrearageDomain> arrearageDomains) {
		return getPersistanceManager(ExecutorType.BATCH).deleteList(arrearageNamespace + ".deleteByParam",
				arrearageDomains);
	}

	public int insertArrearageList(List<ArrearageDomain> arrearageDomains) {
		return getPersistanceManager(ExecutorType.BATCH).insertList(arrearageNamespace + ".insert", arrearageDomains);
	}
	public int deleteArrearageByMeterIdsMonAndSn(PulishEntity pulishEntity) {
		return getPersistanceManager().delete(arrearageNamespace +
				".deleteArrearageByMeterIdsMonAndSn",pulishEntity);
	}

	public int updateArrearageByParam(List<ArrearageDomain> arrearageDomains) {
		return getPersistanceManager(ExecutorType.BATCH).updateList(arrearageNamespace + ".update",arrearageDomains);
	}

	public int updateMoneyItemId() {
		return getPersistanceManager().update(arrearageNamespace +
				".updateMoneyItemId", new Object());
	}

	public int insertPreChargeList(List<PreChargeDomain> preChargeDomains) {
		return getPersistanceManager(ExecutorType.BATCH).insertList(preChargeNamesapce +".insert", preChargeDomains);
	}
	public int deleteChargeByParam(List<ChargeDomain> chargeDomains) {
		return getPersistanceManager(ExecutorType.BATCH).insertList(chargeNamesapce+".delete", chargeDomains);
	}

	public int insertChargeList(List<ChargeDomain> chargeDomains) {
		return getPersistanceManager(ExecutorType.BATCH).insertList(chargeNamesapce+".insert", chargeDomains);
	}

	public int updateChargeItemId() {
		return getPersistanceManager().update(chargeNamesapce +
				".updateChargeItemId", new Object());
	}

	public int updateNoteInfoChargeId() {
		return getPersistanceManager().update(noteInfosapce +
				".updateNoteInfoChargeId", new Object());
	}

	public int deleteNoteInfoChargeIds(NoteInfoDomain noteInfoDomain) {
		return getPersistanceManager().delete(noteInfosapce +	".deleteByChargeIds",noteInfoDomain);
	}

}
