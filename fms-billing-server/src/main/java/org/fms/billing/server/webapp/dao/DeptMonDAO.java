/**
 * Author : czy
 * Date : 2019年6月20日 上午11:01:46
 * Title : com.riozenc.billing.webapp.dao.DeptMonDAO.java
 *
**/
package org.fms.billing.server.webapp.dao;

import java.util.List;

import org.fms.billing.common.webapp.domain.DeptMonDomain;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.spring.webapp.dao.AbstractTransactionDAOSupport;
import com.riozenc.titanTool.spring.webapp.dao.BaseDAO;

@TransactionDAO
public class DeptMonDAO extends AbstractTransactionDAOSupport implements BaseDAO<DeptMonDomain> {

	@Override
	public int insert(DeptMonDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().insert(getNamespace() + ".insert", t);
	}

	@Override
	public int delete(DeptMonDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().delete(getNamespace() + ".delete", t);
	}

	@Override
	public int update(DeptMonDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().update(getNamespace() + ".update", t);
	}

	@Override
	public DeptMonDomain findByKey(DeptMonDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().load(getNamespace() + ".findByKey", t);
	}

	@Override
	public List<DeptMonDomain> findByWhere(DeptMonDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().find(getNamespace() + ".findByWhere", t);
	}

	public List<DeptMonDomain> getDeptMonByDeptIds(List<Integer> deptIds) {
		return getPersistanceManager().find(getNamespace() + ".getDeptMonByDeptIds", deptIds);
	}

}
