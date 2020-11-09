package org.fms.billing.server.webapp.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.fms.billing.common.webapp.entity.SystemCommonConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.riozenc.titanTool.annotation.TransactionService;
import com.riozenc.titanTool.properties.Global;

@TransactionService
public class SysCommonConfigService {

    @Autowired
    private RestTemplate restTemplate;


    public Map<Long, String> getsystemCommonConfigMap(String type){
        SystemCommonConfigEntity systemCommonConfigEntity = new SystemCommonConfigEntity();

        systemCommonConfigEntity.setType(type);

        String returnSystemCommonConfig =
                restTemplate.postForObject(Global.getConfig("findSystemCommonConfigByType"), systemCommonConfigEntity,
                        String.class);

        List<SystemCommonConfigEntity> systemCommonConfigEntities =
                JSONObject.parseArray(returnSystemCommonConfig, SystemCommonConfigEntity.class);

        Map<Long, String> chargeModeConfigMap =
                systemCommonConfigEntities.stream().filter(t->t.getParamKey()!=null).collect(Collectors.toMap(SystemCommonConfigEntity::getParamKey, a -> a.getParamValue(), (k1, k2) -> k1));

        return chargeModeConfigMap;

    }
}
