package org.fms.billing.server.webapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fms.billing.common.webapp.domain.MeterDomain;
import org.fms.billing.common.webapp.domain.SettlementMeterRelDomain;
import org.fms.billing.common.webapp.domain.beakInterface.SettlementDomain;
import org.fms.billing.common.webapp.domain.mongo.MeterMeterAssetsRelDomain;
import org.fms.billing.common.webapp.entity.BankEntity;
import org.fms.billing.common.webapp.entity.LineEntity;
import org.fms.billing.common.webapp.entity.SubsEntity;
import org.fms.billing.common.webapp.entity.SubsLineRelaEntity;
import org.fms.billing.common.webapp.entity.TransformerAssetsEntity;
import org.fms.billing.common.webapp.entity.TransformerEntity;
import org.fms.billing.common.webapp.entity.TransformerLineRelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.riozenc.titanTool.annotation.TransactionService;
import com.riozenc.titanTool.common.json.utils.GsonUtils;
import com.riozenc.titanTool.properties.Global;
import com.riozenc.titanTool.spring.web.client.TitanTemplate;
import com.riozenc.titanTool.spring.web.http.HttpResult;
import com.riozenc.titanTool.spring.web.http.HttpResultPagination;

@TransactionService
public class CimService {
    @Autowired
    private TitanTemplate titanTemplate;

    @Autowired
    private RestTemplate restTemplate;


    public List<SettlementDomain> findBankSettlement(SettlementDomain settlementDomain) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        List<SettlementDomain> list = new ArrayList<>();
        try {
            list = titanTemplate.post("CIM-SERVER",
                    "cimServer/cim_bill?method=findBankSettlement", httpHeaders,
                    GsonUtils.toJson(settlementDomain),
                    new TypeReference<List<SettlementDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取结算户信息
    public List<SettlementDomain> findSettlementByIds(List<Long> settlementIds) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("settleIds", settlementIds);
        HttpResult httpResult = new HttpResult();
        try {
            httpResult = titanTemplate.post("CIM-SERVER",
                    "cimServer/cim_bill?method=findSettlementByIds", httpHeaders,
                    GsonUtils.toJson(jsonObject),
                    new TypeReference<HttpResult>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<SettlementDomain> list =
                JSONObject.parseArray(JSONObject.toJSONString(httpResult.getResultData()),
                        SettlementDomain.class);
        return list;
    }

    //获取开户银行下拉
    public List<BankEntity> getBank(BankEntity bankEntity) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<BankEntity> list = new ArrayList<>();
        try {
            list = titanTemplate.post("CIM-SERVER",
                    "cimServer/bank?method=getBankList", httpHeaders,
                    GsonUtils.toJson(bankEntity),
                    new TypeReference<List<BankEntity>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }



    //根据抄表区段获取结算户id集合
    public List<Long> getSettlementIdsByWriteSectionId(List<Long> writeSectionIds) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<Long> list = new ArrayList<>();
        try {
            list = titanTemplate.post("CIM-SERVER",
                    "cimServer/settlementMeterRel?method=getSettlementIdsByWriteSectionId",
                    httpHeaders,
                    GsonUtils.toJson(writeSectionIds),
                    new TypeReference<List<Long>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    //结算户findbywhere
    public List<SettlementDomain> getSettlement(SettlementDomain settlementDomain) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpResultPagination<SettlementDomain> httpResultPagination = null;
        try {
            httpResultPagination = titanTemplate.post("CIM-SERVER",
                    "cimServer/settlement?method=getSettlement",
                    httpHeaders,
                    GsonUtils.toJson(settlementDomain),
                    new TypeReference<HttpResultPagination<SettlementDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }


        return httpResultPagination.getList();
    }

    //获取计量点集合
    public List<MeterDomain> getMeterFindByWhere(Map<String, Object> meterMaps) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MeterDomain> list = new ArrayList<>();
        try {
            list = titanTemplate.post("CIM-SERVER",
                    "cimServer/meter?method=findMeterByMeterIds",
                    httpHeaders,
                    GsonUtils.toJson(meterMaps),
                    new TypeReference<List<MeterDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //根据结算户获取计量点集合
    public List<SettlementMeterRelDomain> getMeterIdsBySettlements(List<SettlementDomain> settlements) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("settlement",settlements);
        String settlementMeterJson =restTemplate.postForObject
                (Global.getConfig("getMeterIdsBySettlements"), jsonObject, String.class);
        List<SettlementMeterRelDomain> settlementMeterRelDomains=
                JSONObject.parseArray(settlementMeterJson,SettlementMeterRelDomain.class);
        return settlementMeterRelDomains;

    }


    //根据计量点获取计量点结算户关系
    public List<SettlementMeterRelDomain> getSettlementMeterRelByMeterIds(List<Long> meterIds) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<SettlementMeterRelDomain> list = new ArrayList<>();
        try {
            list = titanTemplate.post("CIM-SERVER",
                    "cimServer/settlement?method=getSettlementMeterRelByMeterIds",
                    httpHeaders,
                    GsonUtils.toJson(meterIds),
                    new TypeReference<List<SettlementMeterRelDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    //根据结算户信息查找结算户id
    public List<Long> findSettlementIdByWhere(SettlementDomain settlementDomain) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<Long> list = new ArrayList<>();
        try {
            list = titanTemplate.post("CIM-SERVER",
                    "cimServer/settlement?method=findSettlementIdByWhere",
                    httpHeaders,
                    GsonUtils.toJson(settlementDomain),
                    new TypeReference<List<Long>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    //获取变电站信息
    public List<SubsEntity> getSubs(SubsEntity subsEntity) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpResultPagination<SubsEntity> httpResultPagination = null;
        try {
            httpResultPagination = titanTemplate.post("CIM-SERVER",
                    "cimServer/subs?method=getSubs",
                    httpHeaders,
                    GsonUtils.toJson(subsEntity),
                    new TypeReference<HttpResultPagination<SubsEntity>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }


        return httpResultPagination.getList();
    }

    //获取变电站线路信息
    public List<SubsLineRelaEntity> getsubsLineRela(SubsLineRelaEntity subsLineRelaEntity) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpResultPagination<SubsLineRelaEntity> httpResultPagination = null;
        try {
            httpResultPagination = titanTemplate.post("CIM-SERVER",
                        "cimServer/subsLineRela?method=getsubsLineRela",
                    httpHeaders,
                    GsonUtils.toJson(subsLineRelaEntity),
                    new TypeReference<HttpResultPagination<SubsLineRelaEntity>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }


        return httpResultPagination.getList();
    }


    //查线路
    public List<LineEntity> findLineByLineIds(LineEntity lineDomain) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<LineEntity> list = new ArrayList<>();
        try {
            list = titanTemplate.post("CIM-SERVER",
                    "cimServer/line?method=findByLineIds",
                    httpHeaders,
                    GsonUtils.toJson(lineDomain),
                    new TypeReference<List<LineEntity>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    //根据变压器ids 获取变压器线路关系
    public List<TransformerLineRelEntity> findLineTransRelByLineIds(TransformerLineRelEntity transformerLineRelDomain) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<TransformerLineRelEntity> list = new ArrayList<>();
        try {
            list = titanTemplate.post("CIM-SERVER",
                    "cimServer/transformer?method=findRelByLineIds",
                    httpHeaders,
                    GsonUtils.toJson(transformerLineRelDomain),
                    new TypeReference<List<TransformerLineRelEntity>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    //结算户findbywhere
    public HttpResultPagination<TransformerEntity> getAvaTransformerByWhere(TransformerEntity transformerDomain) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpResultPagination<TransformerEntity> httpResultPagination = null;
        try {
            httpResultPagination = titanTemplate.post("CIM-SERVER",
                    "cimServer/transformer?method=getAvaTransformerByWhere",
                    httpHeaders,
                    GsonUtils.toJson(transformerDomain),
                    new TypeReference<HttpResultPagination<TransformerEntity>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }


        return httpResultPagination;
    }

    //获取结算户信息
    public List<SettlementDomain> getSettlementbyMeterIds(List<Long> meterIds) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpResult httpResult = new HttpResult();
        try {
            httpResult = titanTemplate.post("CIM-SERVER",
                    "cimServer/settlement?method=getSettlementbyMeterIds", httpHeaders,
                    GsonUtils.toJson(meterIds),
                    new TypeReference<HttpResult>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<SettlementDomain> list =
                JSONObject.parseArray(JSONObject.toJSONString(httpResult.getResultData()),
                        SettlementDomain.class);
        return list;
    }

    //结算户findbywhere
    public List<SettlementDomain> findClearSettlementByWhere(SettlementDomain settlementDomain) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpResultPagination<SettlementDomain> httpResultPagination = null;
        try {
            httpResultPagination = titanTemplate.post("CIM-SERVER",
                    "cimServer/settlement?method=findClearSettlementByWhere",
                    httpHeaders,
                    GsonUtils.toJson(settlementDomain),
                    new TypeReference<HttpResultPagination<SettlementDomain>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }


        return httpResultPagination.getList();
    }

    public List<MeterMeterAssetsRelDomain> getMeterAssetsByMeterIds(List<Long> meterIdList) {
        Map<String, Object> params2 = new HashMap<>();
        params2.put("meterIds", meterIdList);
        List<MeterMeterAssetsRelDomain> meterMeterAssetsRelDomains = new ArrayList<>();
        try {
            meterMeterAssetsRelDomains = titanTemplate.postJsonToList("CIM-SERVER",
                    "cimServer/meterMeterAssets?method=getMeterAssetsByMeterIds", null, params2,
                    MeterMeterAssetsRelDomain.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return meterMeterAssetsRelDomains;
    }

    //查询变压器资产
    public List<TransformerAssetsEntity> getTransformerAssetsByWhere(TransformerAssetsEntity transformerAssetsEntity) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpResultPagination<TransformerAssetsEntity> httpResultPagination = null;
        try {
            httpResultPagination = titanTemplate.post("CIM-SERVER",
                    "cimServer/transformerAssets?method=getTransformerAssetsByWhere",
                    httpHeaders,
                    GsonUtils.toJson(transformerAssetsEntity),
                    new TypeReference<HttpResultPagination<TransformerAssetsEntity>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }


        return httpResultPagination.getList();
    }
}
