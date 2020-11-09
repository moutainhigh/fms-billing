/**
 * Author : chizf
 * Date : 2020年6月5日 下午8:39:43
 * Title : com.riozenc.billing.webapp.dao.ArrearagePenaltyMoneyDAO.java
 *
**/
package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.fms.billing.common.webapp.domain.ArrearagePenaltyMoneyDomain;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;
import com.riozenc.titanTool.spring.webapp.dao.BaseDAO;

@TransactionDAO
public class ArrearagePenaltyMoneyDAO extends AbstractTransactionDAOSupport
		implements BaseDAO<ArrearagePenaltyMoneyDomain> {

	@Override
	public int insert(ArrearagePenaltyMoneyDomain t) {
		return getPersistanceManager().insert(getNamespace()+".insert", t);
	}

	@Override
	public int delete(ArrearagePenaltyMoneyDomain t) {
		return getPersistanceManager().delete(getNamespace()+".delete", t);
	}

	@Override
	public int update(ArrearagePenaltyMoneyDomain t) {
		return getPersistanceManager().update(getNamespace()+".update", t);
	}

	@Override
	public ArrearagePenaltyMoneyDomain findByKey(ArrearagePenaltyMoneyDomain t) {
		return getPersistanceManager().load(getNamespace()+".findByKey", t);
	}

	@Override
	public List<ArrearagePenaltyMoneyDomain> findByWhere(ArrearagePenaltyMoneyDomain t) {
		return getPersistanceManager().find(getNamespace()+".findByWhere", t);
	}
	public int insertList(List<ArrearagePenaltyMoneyDomain> list) {
		return getPersistanceManager().insertList(getNamespace()+".insert", list);
	}

}
