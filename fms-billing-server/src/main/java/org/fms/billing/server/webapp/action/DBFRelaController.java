package org.fms.billing.server.webapp.action;

import java.util.ArrayList;
import java.util.List;

import org.fms.billing.common.webapp.domain.WriteFilesDomain;
import org.fms.billing.common.webapp.service.IDBFRelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("DBFRela")
public class DBFRelaController {
	@Autowired
	@Qualifier("DBFRelaServiceImpl")
	private IDBFRelaService iDBFilesService;
	@RequestMapping("getResult")
	@ResponseBody
	public List<WriteFilesDomain> getResult(@RequestBody List<WriteFilesDomain> list){
		/*
		 * List<WriteFilesDomain> testDomains =new ArrayList<WriteFilesDomain>();
		 * WriteFilesDomain write1=new WriteFilesDomain();
		 * write1.setMeterNo("20500066971111"); write1.setMon(202003); WriteFilesDomain
		 * write2=new WriteFilesDomain(); write2.setMeterNo("20500070481");
		 * write2.setMon(202003); testDomains.add(write1); testDomains.add(write2);
		 */
		List<WriteFilesDomain> simplifyData =new ArrayList<WriteFilesDomain>();
		for(WriteFilesDomain data:list) {
			WriteFilesDomain writeFilesDomain=new WriteFilesDomain();
			writeFilesDomain.setUserNo(data.getUserNo());
			writeFilesDomain.setMeterNo(data.getMeterNo());
			writeFilesDomain.setMon(data.getMon());
			writeFilesDomain.setSn(data.getSn());
			simplifyData.add(writeFilesDomain);
		}
		List<WriteFilesDomain> result=iDBFilesService.writeFilter(simplifyData);
		return result;

	}

}
