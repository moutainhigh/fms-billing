package org.fms.billing.server.webapp.service;

import java.util.List;

import org.fms.billing.common.webapp.entity.MapEntity;
import org.fms.billing.common.webapp.entity.UserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.riozenc.titanTool.annotation.TransactionService;
import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.spring.web.client.TitanTemplate;
import com.riozenc.titanTool.spring.web.http.HttpResult;

@TransactionService
public class UserService {


    @Autowired
    private TitanTemplate titanTemplate;

    public List<MapEntity> findMapByDomain(UserInfoEntity userInfoEntity) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpResult<List<MapEntity>> result = new HttpResult<>();
        try {
            result = titanTemplate.post("AUTH-CENTER",
                    "auth/user/findMapByDomain", httpHeaders,
                    GsonUtils.toJson(userInfoEntity),
                    new TypeReference<HttpResult<List<MapEntity>>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<MapEntity> list = result.getResultData();

        return list;
    }
}
