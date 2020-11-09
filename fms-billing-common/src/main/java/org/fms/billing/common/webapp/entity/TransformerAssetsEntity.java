/**
 *    Auth:riozenc
 *    Date:2019年3月8日 下午3:22:58
 *    Title:com.riozenc.cim.webapp.domain.CustomerDomain.java
 **/
package org.fms.billing.common.webapp.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.mybatis.MybatisEntity;

/**
 * 变压器资产 TRANSFORMER_ASSETS_INFO
 *
 * @author riozenc
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransformerAssetsEntity extends ManagerParamEntity implements MybatisEntity {

	private Long id;// ID ID bigint TRUE FALSE TRUE
	private String transformerAssetsNo; // 变压器资产编号
	private Integer voltageRatio; // 电压比
	private Integer currentRatio; // 电流比
	private Long transformerModelType; // 型号
	private Integer factoryType; // 制造厂家
	private String madeNo; // 出厂编号
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date madeDate; // 出厂日期
	private Byte ratedVoltage; // 额定电压					----
	private BigDecimal ratedCurrent; // 额定电流
	private BigDecimal shortVoltage; // 短路电压
	private Byte connectType; // 连接组别
	private Byte numPhaseType; // 相数
	private BigDecimal emptyLose; // 空载损耗
	private BigDecimal loadLose; // 负载损耗
	private BigDecimal emptyCurrent; // 空载电流
	private BigDecimal shortLose; // 短路损耗
	private BigDecimal rt; // 阻抗电压
	private String oilNo; // 油号
	private Byte voltAttach; // 调压接头数
	private BigDecimal hvLimit; // 高压熔丝限额(A)
	private BigDecimal theroem; // 额定温升
	private BigDecimal firstVolt; // 一次侧电压
	private BigDecimal secondVolt; // 二次侧电压
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	//@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date lastDetDate; // 上次检定日期
	private Integer detPeriod; // 检定周期(月)
	private Integer lifeSpan; // 使用寿命(月)
	private Long deptId; // 所属单位
	private String manId; // 持有人
	private Long rightAttach; //产权归属
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createDate; // 创建日期
	private String remark; // 备注
	private Byte status; // 状态
	private String batchNo; // BATCH_NO 批次
	private BigDecimal firstCurrent; // 1次电流
	private BigDecimal secondCurrent; // 2次电流
	private String companyAssetsNo; // COMPANY_ASSETS_NO  局资产
	private String propertyNo; // PROPERTY_NO  财产号
	private BigDecimal ratedCapacity; // 额定容量						------
	private List<Long> ids;
	
	

	public Integer getLifeSpan() {
		return lifeSpan;
	}

	public void setLifeSpan(Integer lifeSpan) {
		this.lifeSpan = lifeSpan;
	}

	public Integer getDetPeriod() {
		return detPeriod;
	}

	public void setDetPeriod(Integer detPeriod) {
		this.detPeriod = detPeriod;
	}

	public Date getLastDetDate() {
		return lastDetDate;
	}

	public void setLastDetDate(Date lastDetDate) {
		this.lastDetDate = lastDetDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTransformerAssetsNo() {
		return transformerAssetsNo;
	}

	public void setTransformerAssetsNo(String transformerAssetsNo) {
		this.transformerAssetsNo = transformerAssetsNo;
	}

	public Integer getVoltageRatio() {
		return voltageRatio;
	}

	public void setVoltageRatio(Integer voltageRatio) {
		this.voltageRatio = voltageRatio;
	}

	public Integer getCurrentRatio() {
		return currentRatio;
	}

	public void setCurrentRatio(Integer currentRatio) {
		this.currentRatio = currentRatio;
	}

	public Long getTransformerModelType() {
		return transformerModelType;
	}

	public void setTransformerModelType(Long transformerModelType) {
		this.transformerModelType = transformerModelType;
	}

	public Integer getFactoryType() {
		return factoryType;
	}

	public void setFactoryType(Integer factoryType) {
		this.factoryType = factoryType;
	}

	public String getMadeNo() {
		return madeNo;
	}

	public void setMadeNo(String madeNo) {
		this.madeNo = madeNo;
	}

	public Date getMadeDate() {
		return madeDate;
	}

	public void setMadeDate(Date madeDate) {
		this.madeDate = madeDate;
	}

	public Byte getRatedVoltage() {
		return ratedVoltage;
	}

	public void setRatedVoltage(Byte ratedVoltage) {
		this.ratedVoltage = ratedVoltage;
	}

	public BigDecimal getRatedCurrent() {
		return ratedCurrent;
	}

	public void setRatedCurrent(BigDecimal ratedCurrent) {
		this.ratedCurrent = ratedCurrent;
	}

	public BigDecimal getShortVoltage() {
		return shortVoltage;
	}

	public void setShortVoltage(BigDecimal shortVoltage) {
		this.shortVoltage = shortVoltage;
	}

	public Byte getConnectType() {
		return connectType;
	}

	public void setConnectType(Byte connectType) {
		this.connectType = connectType;
	}

	public Byte getNumPhaseType() {
		return numPhaseType;
	}

	public void setNumPhaseType(Byte numPhaseType) {
		this.numPhaseType = numPhaseType;
	}

	public BigDecimal getEmptyLose() {
		return emptyLose;
	}

	public void setEmptyLose(BigDecimal emptyLose) {
		this.emptyLose = emptyLose;
	}

	public BigDecimal getEmptyCurrent() {
		return emptyCurrent;
	}

	public void setEmptyCurrent(BigDecimal emptyCurrent) {
		this.emptyCurrent = emptyCurrent;
	}

	public BigDecimal getLoadLose() {
		return loadLose;
	}

	public void setLoadLose(BigDecimal loadLose) {
		this.loadLose = loadLose;
	}

	public BigDecimal getShortLose() {
		return shortLose;
	}

	public void setShortLose(BigDecimal shortLose) {
		this.shortLose = shortLose;
	}

	public BigDecimal getRt() {
		return rt;
	}

	public void setRt(BigDecimal rt) {
		this.rt = rt;
	}

	public String getOilNo() {
		return oilNo;
	}

	public void setOilNo(String oilNo) {
		this.oilNo = oilNo;
	}

	public Byte getVoltAttach() {
		return voltAttach;
	}

	public void setVoltAttach(Byte voltAttach) {
		this.voltAttach = voltAttach;
	}

	public BigDecimal getHvLimit() {
		return hvLimit;
	}

	public void setHvLimit(BigDecimal hvLimit) {
		this.hvLimit = hvLimit;
	}

	public BigDecimal getTheroem() {
		return theroem;
	}

	public void setTheroem(BigDecimal theroem) {
		this.theroem = theroem;
	}

	public BigDecimal getFirstVolt() {
		return firstVolt;
	}

	public void setFirstVolt(BigDecimal firstVolt) {
		this.firstVolt = firstVolt;
	}

	public BigDecimal getSecondVolt() {
		return secondVolt;
	}

	public void setSecondVolt(BigDecimal secondVolt) {
		this.secondVolt = secondVolt;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getManId() {
		return manId;
	}

	public void setManId(String manId) {
		this.manId = manId;
	}

	public Long getRightAttach() {
		return rightAttach;
	}

	public void setRightAttach(Long rightAttach) {
		this.rightAttach = rightAttach;
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

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public BigDecimal getFirstCurrent() {
		return firstCurrent;
	}

	public void setFirstCurrent(BigDecimal firstCurrent) {
		this.firstCurrent = firstCurrent;
	}

	public BigDecimal getSecondCurrent() {
		return secondCurrent;
	}

	public void setSecondCurrent(BigDecimal secondCurrent) {
		this.secondCurrent = secondCurrent;
	}

	public String getCompanyAssetsNo() {
		return companyAssetsNo;
	}

	public void setCompanyAssetsNo(String companyAssetsNo) {
		this.companyAssetsNo = companyAssetsNo;
	}

	public String getPropertyNo() {
		return propertyNo;
	}

	public void setPropertyNo(String propertyNo) {
		this.propertyNo = propertyNo;
	}

	public BigDecimal getRatedCapacity() {
		return ratedCapacity;
	}

	public void setRatedCapacity(BigDecimal ratedCapacity) {
		this.ratedCapacity = ratedCapacity;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
}
