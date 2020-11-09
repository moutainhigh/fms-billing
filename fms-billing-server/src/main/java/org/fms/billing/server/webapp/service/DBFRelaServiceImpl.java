package org.fms.billing.server.webapp.service;

import java.util.ArrayList;
import java.util.List;

import org.fms.billing.common.webapp.domain.WriteFilesDomain;
import org.fms.billing.common.webapp.service.IDBFRelaService;
import org.fms.billing.server.webapp.dao.DBFRelaDAO;

import com.riozenc.titanTool.annotation.TransactionDAO;
import com.riozenc.titanTool.annotation.TransactionService;
@TransactionService
public class DBFRelaServiceImpl implements IDBFRelaService{
	@TransactionDAO
	private DBFRelaDAO dao;

	@Override
	public List<WriteFilesDomain> writeFilter(List<WriteFilesDomain> originalData) {
		// TODO Auto-generated method stub
		List<WriteFilesDomain> list =new ArrayList<WriteFilesDomain>();
		for(WriteFilesDomain data:originalData) {
			List<WriteFilesDomain> daoReturn=dao.dbfFilter(data);
			if (daoReturn.size()>0&&daoReturn.get(0).getWriteFlag()==1) {
				list.add(daoReturn.get(0));
			}
		}
		System.out.println("list-----------------------"+list.size());
		return list;
	}
	

}
