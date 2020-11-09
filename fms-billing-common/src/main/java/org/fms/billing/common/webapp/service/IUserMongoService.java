/**
 * Author : czy
 * Date : 2019年7月8日 下午4:09:40
 * Title : com.riozenc.billing.webapp.service.IMeterService.java
 *
**/
package org.fms.billing.common.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.domain.mongo.UserMongoDomain;

public interface IUserMongoService {

	public List<UserMongoDomain> findUserMongoByWhere(UserMongoDomain userMongoDomain);

}
