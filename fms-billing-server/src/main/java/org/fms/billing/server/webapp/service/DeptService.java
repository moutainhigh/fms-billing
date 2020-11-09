package org.fms.billing.server.webapp.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.fms.billing.common.webapp.domain.DeptDomain;
import org.fms.billing.common.webapp.entity.MapEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.riozenc.titanTool.annotation.TransactionService;
import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.common.json.utils.JSONUtil;
import com.riozenc.titanTool.spring.web.client.TitanTemplate;
import com.riozenc.titanTool.spring.web.http.HttpResult;

@TransactionService
public class DeptService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TitanTemplate titanTemplate;


    public List<DeptDomain> getDeptList(Long businessPlaceCode) {
        List<DeptDomain> businessPlaceCodes = new ArrayList<>();
        try {

            Map<String, Object> params = new HashMap<>();
            params.put("id", businessPlaceCode);
            HttpResult<List<DeptDomain>> httpResult = titanTemplate.postJson("AUTH-CENTER", "auth/dept/tree", null, params,
                    new TypeReference<HttpResult<List<DeptDomain>>>() {
                    });
            List<DeptDomain> deptList = httpResult.getResultData();
            businessPlaceCodes = getList(deptList, businessPlaceCodes);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return businessPlaceCodes;
    }

    public DeptDomain getDept(Long businessPlaceCode) {
        DeptDomain dept = null;
        try {
            String resultJson = restTemplate.getForObject("http://AUTH-CENTER/auth/dept/getDeptById/" + businessPlaceCode,
                    String.class);
            HttpResult<DeptDomain> result = JSONUtil.readValue(resultJson,
                    new TypeReference<HttpResult<DeptDomain>>() {
                    });
            dept = result.getResultData();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dept;
    }

    public List<DeptDomain> getList(List<DeptDomain> list, List<DeptDomain> businessPlaceCodes) {
        businessPlaceCodes.addAll(list);
        List<List<DeptDomain>> tree = list.stream().filter(i -> i.getChildren().size() > 0).map(DeptDomain::getChildren).collect(Collectors.toList());
        if (tree.size() > 0) {
            List<DeptDomain> child = new ArrayList<>();
            tree.forEach(child::addAll);
            return getList(child, businessPlaceCodes);
        } else {
            return businessPlaceCodes;
        }
    }

    public List<MapEntity> findIdMapByDomain(DeptDomain deptDomain) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpResult<List<MapEntity>> result = new HttpResult<>();
        try {
            result = titanTemplate.post("AUTH-CENTER",
                    "auth/dept/findIdMapByDomain", httpHeaders,
                    GsonUtils.toJson(deptDomain),
                    new TypeReference<HttpResult<List<MapEntity>>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.getResultData();
    }

    public List<MapEntity> findDeptIdMapByDomain(DeptDomain deptDomain) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpResult<List<MapEntity>> result = new HttpResult<>();
        try {
            result = titanTemplate.post("AUTH-CENTER",
                    "auth/dept/findDeptIdMapByDomain", httpHeaders,
                    GsonUtils.toJson(deptDomain),
                    new TypeReference<HttpResult<List<MapEntity>>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<MapEntity> list=  result.getResultData();

        return list;
    }


}
