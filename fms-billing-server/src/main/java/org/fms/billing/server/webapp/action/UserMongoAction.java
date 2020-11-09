/**
 * Author : czy
 * Date : 2019年7月8日 下午4:09:04
 * Title : com.riozenc.billing.webapp.controller.MeterAction.java
 *
**/
package org.fms.billing.server.webapp.action;

import java.util.List;

import org.fms.billing.common.webapp.domain.mongo.UserMongoDomain;
import org.fms.billing.common.webapp.service.IUserMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.titanTool.properties.Global;
import com.riozenc.titanTool.spring.web.http.HttpResultPagination;

@ControllerAdvice
@RequestMapping("userMongo")
public class UserMongoAction {

	@Autowired
	@Qualifier("userMongoServiceImpl")
	private IUserMongoService userMongoService;

		@RequestMapping(params = "method=findUserMongoByWhere")
	@ResponseBody
	public Object findMeterByWhere(@RequestBody UserMongoDomain userMongoDomain) {
			int mon = Integer.valueOf(Global.getConfig("mon"));
			if (Integer.valueOf(userMongoDomain.getMon()) < mon) {
				userMongoDomain.setMon(String.valueOf(mon));
			}
		List<UserMongoDomain> list =
				userMongoService.findUserMongoByWhere(userMongoDomain);

		return new HttpResultPagination<UserMongoDomain>(userMongoDomain, list);
	}


	// 用于report调用
	@RequestMapping("findUserMongoByWhere")
	@ResponseBody
	public List<UserMongoDomain> findUserMongoByWhere(@RequestBody UserMongoDomain userMongoDomain) {

		List<UserMongoDomain> list =
				userMongoService.findUserMongoByWhere(userMongoDomain);

		return list;
	}
}
