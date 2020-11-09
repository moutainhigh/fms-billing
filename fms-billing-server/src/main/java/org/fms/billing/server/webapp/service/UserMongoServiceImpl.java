/**
 * Author : czy
 * Date : 2019年7月8日 下午4:12:26
 * Title : com.riozenc.billing.webapp.service.impl.MeterServiceImpl.java
 *
**/
package org.fms.billing.server.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.mongo.UserMongoDomain;
import org.fms.billing.common.webapp.service.IUserMongoService;
import org.fms.billing.server.webapp.dao.UserMongoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMongoServiceImpl implements IUserMongoService {

	@Autowired
	private UserMongoDAO userMongoDAO;

	@Override
	public List<UserMongoDomain> findUserMongoByWhere(UserMongoDomain userMongoDomain) {
		// TODO Auto-generated method stub
		return userMongoDAO.findUserMongoByWhere(userMongoDomain);
	}
}
