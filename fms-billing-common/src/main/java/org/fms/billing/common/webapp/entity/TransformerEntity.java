package org.fms.billing.common.webapp.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.mybatis.MybatisEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransformerEntity extends ManagerParamEntity implements MybatisEntity{
    private Long id;// ID ID bigint TRUE FALSE TRUE
    private Long transformerAssetsId; // 变压器资产ID
    private String transformerNo; // 变压器编号
    private String deskName; // 变压器名称
    private BigDecimal capacity; // 变压器容量
    private String transformerGroupNo; // 变压器组号
    private Byte isPubType; // 公用变标志
    private Byte transformerLossType; // 变损计算方法
//    private Long businessPlaceCode; // 供电所
    private Date runDate; // 投运日期
    private Integer standTime; // 标准时间
    private Byte transformerModelType;// 变压器型号
    private Byte voltType; // 供电电压
    private String chargeMan; // 责任人
    private Integer produceTeam; // 生产班次
    private Byte setFormat; // 安装形式
    private String areaNo; // 片区号
    private String supplyRoom; // 配电房编号
    private String villageNo; // 村别
    private String transChange; // 变台改造情况
    private String inputNodeCode; // 接入线路对应结束节点编号
    private BigDecimal capcitorCapacity; // 补偿电容器总容量
    private Integer cosStdCode; // 考核功率因数
    private Date createDate; // 创建日期
    private String remark; // 备注
    private Byte status; // 状态
    private Long operator;// 操作人ID ID bigint TRUE FALSE TRUE



    private List ids;
    private List businessPlaceCodes;
    private List meterIds;


    private List<Long> transformerIds;

    public List<Long> getTransformerIds() {
        return transformerIds;
    }

    public void setTransformerIds(List<Long> transformerIds) {
        this.transformerIds = transformerIds;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTransformerAssetsId() {
        return transformerAssetsId;
    }

    public void setTransformerAssetsId(Long transformerAssetsId) {
        this.transformerAssetsId = transformerAssetsId;
    }

    public String getTransformerNo() {
        return transformerNo;
    }

    public void setTransformerNo(String transformerNo) {
        this.transformerNo = transformerNo;
    }

    public String getDeskName() {
        return deskName;
    }

    public void setDeskName(String deskName) {
        this.deskName = deskName;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public String getTransformerGroupNo() {
        return transformerGroupNo;
    }

    public void setTransformerGroupNo(String transformerGroupNo) {
        this.transformerGroupNo = transformerGroupNo;
    }

    public Byte getIsPubType() {
        return isPubType;
    }

    public void setIsPubType(Byte isPubType) {
        this.isPubType = isPubType;
    }

    public Byte getTransformerLossType() {
        return transformerLossType;
    }

    public void setTransformerLossType(Byte transformerLossType) {
        this.transformerLossType = transformerLossType;
    }


    public Date getRunDate() {
        return runDate;
    }

    public void setRunDate(Date runDate) {
        this.runDate = runDate;
    }

    public Integer getStandTime() {
        return standTime;
    }

    public void setStandTime(Integer standTime) {
        this.standTime = standTime;
    }

    public Byte getTransformerModelType() {
        return transformerModelType;
    }

    public void setTransformerModelType(Byte transformerModelType) {
        this.transformerModelType = transformerModelType;
    }

    public Byte getVoltType() {
        return voltType;
    }

    public void setVoltType(Byte voltType) {
        this.voltType = voltType;
    }

    public String getChargeMan() {
        return chargeMan;
    }

    public void setChargeMan(String chargeMan) {
        this.chargeMan = chargeMan;
    }

    public Integer getProduceTeam() {
        return produceTeam;
    }

    public void setProduceTeam(Integer produceTeam) {
        this.produceTeam = produceTeam;
    }

    public Byte getSetFormat() {
        return setFormat;
    }

    public void setSetFormat(Byte setFormat) {
        this.setFormat = setFormat;
    }

    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }

    public String getSupplyRoom() {
        return supplyRoom;
    }

    public void setSupplyRoom(String supplyRoom) {
        this.supplyRoom = supplyRoom;
    }

    public String getVillageNo() {
        return villageNo;
    }

    public void setVillageNo(String villageNo) {
        this.villageNo = villageNo;
    }

    public String getTransChange() {
        return transChange;
    }

    public void setTransChange(String transChange) {
        this.transChange = transChange;
    }

    public String getInputNodeCode() {
        return inputNodeCode;
    }

    public void setInputNodeCode(String inputNodeCode) {
        this.inputNodeCode = inputNodeCode;
    }

    public BigDecimal getCapcitorCapacity() {
        return capcitorCapacity;
    }

    public void setCapcitorCapacity(BigDecimal capcitorCapacity) {
        this.capcitorCapacity = capcitorCapacity;
    }

    public Integer getCosStdCode() {
        return cosStdCode;
    }

    public void setCosStdCode(Integer cosStdCode) {
        this.cosStdCode = cosStdCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }





    public List getIds() {
        return ids;
    }

    public void setIds(List ids) {
        this.ids = ids;
    }

    public List getBusinessPlaceCodes() {
        return businessPlaceCodes;
    }

    public void setBusinessPlaceCodes(List businessPlaceCodes) {
        this.businessPlaceCodes = businessPlaceCodes;
    }



    public List getMeterIds() {
        return meterIds;
    }

    public void setMeterIds(List meterIds) {
        this.meterIds = meterIds;
    }
}
