package org.fms.billing.common.webapp.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.mybatis.MybatisEntity;

//电量电费排行参数
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElectricityTariffRankEntity implements MybatisEntity {
    private Long businessPlaceCode;
    private Integer startMon;
    private Integer endMon;
    private Integer queryType;
    private Long limitSettlementNum;
    private String queryTypeString;
    private List<Long> businessPlaceCodes;

    public Long getBusinessPlaceCode() {
        return businessPlaceCode;
    }

    public void setBusinessPlaceCode(Long businessPlaceCode) {
        this.businessPlaceCode = businessPlaceCode;
    }

    public Integer getStartMon() {
        return startMon;
    }

    public void setStartMon(Integer startMon) {
        this.startMon = startMon;
    }

    public Integer getEndMon() {
        return endMon;
    }

    public void setEndMon(Integer endMon) {
        this.endMon = endMon;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }

    public Long getLimitSettlementNum() {
        return limitSettlementNum;
    }

    public void setLimitSettlementNum(Long limitSettlementNum) {
        this.limitSettlementNum = limitSettlementNum;
    }

    public String getQueryTypeString() {
        return queryTypeString;
    }

    public void setQueryTypeString(String queryTypeString) {
        this.queryTypeString = queryTypeString;
    }

    public List<Long> getBusinessPlaceCodes() {
        return businessPlaceCodes;
    }

    public void setBusinessPlaceCodes(List<Long> businessPlaceCodes) {
        this.businessPlaceCodes = businessPlaceCodes;
    }
}
