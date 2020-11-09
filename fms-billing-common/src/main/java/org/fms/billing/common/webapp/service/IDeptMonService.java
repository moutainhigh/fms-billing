/**
 * Author : czy
 * Date : 2019年6月20日 上午10:59:51
 * Title : com.riozenc.billing.webapp.service.IDeptMonService.java
 *
**/
package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.DeptMonDomain;

import com.riozenc.titanTool.spring.webapp.service.BaseService;

public interface IDeptMonService extends BaseService<DeptMonDomain> {
	public List<DeptMonDomain> getDeptMonByDeptIds(List<Integer> deptIds);

	public List<DeptMonDomain> nextMon(String date, List<Integer> deptIds);
}
