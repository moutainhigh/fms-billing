/**
 * Author : czy
 * Date : 2019年6月20日 上午11:03:51
 * Title : com.riozenc.billing.webapp.service.impl.DeptMonServiceImpl.java
 *
**/
package org.fms.billing.server.webapp.service;

import java.util.ArrayList;
import java.util.List;

import org.fms.billing.common.util.MonUtils;
import org.fms.billing.common.webapp.domain.DeptMonDomain;
import org.fms.billing.common.webapp.service.IDeptMonService;
import org.fms.billing.server.webapp.dao.DeptMonDAO;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;

@TransactionService
public class DeptMonServiceImpl implements IDeptMonService {

	@TransactionDAO
	private DeptMonDAO deptMonDAO;

	@Override
	public int insert(DeptMonDomain t) {
		// TODO Auto-generated method stub
		return deptMonDAO.insert(t);
	}

	@Override
	public int delete(DeptMonDomain t) {
		// TODO Auto-generated method stub
		return deptMonDAO.delete(t);
	}

	@Override
	public int update(DeptMonDomain t) {
		// TODO Auto-generated method stub
		return deptMonDAO.update(t);
	}

	@Override
	public DeptMonDomain findByKey(DeptMonDomain t) {
		// TODO Auto-generated method stub
		return deptMonDAO.findByKey(t);
	}

	@Override
	public List<DeptMonDomain> findByWhere(DeptMonDomain t) {
		// TODO Auto-generated method stub
		return deptMonDAO.findByWhere(t);
	}

	public List<DeptMonDomain> getDeptMonByDeptIds(List<Integer> deptIds) {
		return deptMonDAO.getDeptMonByDeptIds(deptIds);
	}

	@Override
	public List<DeptMonDomain> nextMon(String date, List<Integer> deptIds) {
		// TODO Auto-generated method stub

		List<DeptMonDomain> deptMonDomains = deptMonDAO.getDeptMonByDeptIds(deptIds);
		List<Integer> rList = new ArrayList<>();

		deptMonDomains.stream().forEach(d -> {
			if (Integer.valueOf(d.getMon()) < Integer.valueOf(date)) {
				d.setMon(MonUtils.getNextMon(d.getMon()));//翻月
				deptMonDAO.update(d);
			} else {
				d.setRemark("已经处于当月状态，无法重复初始化。");
			}
			rList.add(d.getDeptId());
		});

		deptIds.removeAll(rList);

		for (Integer d : deptIds) {
			DeptMonDomain deptMonDomain = new DeptMonDomain();
			deptMonDomain.setDeptId(d);
			deptMonDomain.setMon(date);
			deptMonDomain.setStatus((byte)1);
			deptMonDomains.add(deptMonDomain);
			deptMonDAO.insert(deptMonDomain);
		}

//		deptIds.forEach(d -> {
//			DeptMonDomain deptMonDomain = new DeptMonDomain();
//			deptMonDomain.setDeptId(d);
//			deptMonDomain.setMon(date);
//			deptMonDomains.add(deptMonDomain);
//			deptMonDAO.insert(deptMonDomain);
//		});
		return deptMonDomains;
	}

}
