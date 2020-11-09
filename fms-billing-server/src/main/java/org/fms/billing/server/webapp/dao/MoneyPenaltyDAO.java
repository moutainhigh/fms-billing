/**
 * Author : chizf
 * Date : 2020年6月5日 下午3:40:20
 * Title : com.riozenc.billing.webapp.dao.MoneyPenaltyDAO.java
 *
**/
package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.fms.billing.common.webapp.domain.MeterMoneyPenaltyDataDomain;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.mongo.dao.MongoDAOSupport;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;
import com.riozenc.titanTool.spring.webapp.dao.BaseDAO;

@TransactionDAO
public class MoneyPenaltyDAO extends AbstractTransactionDAOSupport
		implements BaseDAO<MeterMoneyPenaltyDataDomain>, MongoDAOSupport {

	@Override
	public int insert(MeterMoneyPenaltyDataDomain t) {
		return getPersistanceManager().insert(getNamespace() + ".insert", t);
	}

	@Override
	public int delete(MeterMoneyPenaltyDataDomain t) {
		return getPersistanceManager().delete(getNamespace() + ".delete", t);
	}

	@Override
	public int update(MeterMoneyPenaltyDataDomain t) {
		return getPersistanceManager().update(getNamespace() + ".update", t);
	}

	@Override
	public MeterMoneyPenaltyDataDomain findByKey(MeterMoneyPenaltyDataDomain t) {
		return getPersistanceManager().load(getNamespace() + ".findByKey", t);
	}

	@Override
	public List<MeterMoneyPenaltyDataDomain> findByWhere(MeterMoneyPenaltyDataDomain t) {
		return getPersistanceManager().find(getNamespace() + ".findByWhere", t);
	}

	public int insertList(List<MeterMoneyPenaltyDataDomain> list) {
		return getPersistanceManager().insertList(getNamespace() + ".insert", list);
	}
}
