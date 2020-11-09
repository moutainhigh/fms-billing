/**
 *    Auth:riozenc
 *    Date:2019年3月8日 下午3:22:58
 *    Title:com.riozenc.cim.webapp.domain.CustomerDomain.java
 **/
package org.fms.billing.common.webapp.domain;

import java.util.Date;

import org.fms.billing.common.webapp.entity.ManagerParamEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;

/**
 * 电表资产 METER_ASSETS_INFO
 *
 * @author riozenc
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MeterAssetsDomain extends ManagerParamEntity implements MybatisEntity {

	@TablePrimaryKey
	private Long id;// ID ID bigint TRUE FALSE TRUE
	private String meterAssetsNo; // 资产编号
	private String madeNo; // 出厂编号
	private Byte funcKindCode; // 功能分类
	private Byte powerKindCode; // 功率性质
	private Byte fixedAssetsFlag; // 是否固定资产
	private Long facCode; // 制造厂家
	private Byte modelCode; // 型号
	private Byte ratedVoltCode; // 额定电压
	private Byte ratedCurntCode; // 标定电流
	private Byte accuLevelCode; // 准确度等级
	private Byte phaseLine; // 相线
	private Byte isTsMeter; // 分时表标志
	private Byte structCode; // 结构
	private Byte meterConst; // 电表常数
	private Byte constUnit; // 常数单位
	private Long factor; // 表计倍率
	private Byte madeStdard; // 制造标准
	private Byte axeStructCode; // 轴承结构
	private String meteryardType; // 计度器类型
	private Byte noReturnFlag; // 有无止逆器
	private Byte isCard; // 是否卡表
	private Byte connectMode; // 接通方式
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date madeDate; // 出厂日期
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date bayDate; // 立帐日期
	private Integer lifeSpan; // 使用寿命(月)
	private String manId; // 持有人
	private Byte stateFlag; // 状态标志
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date statusChgDate; // 状态改变时间
	private String statusChgReason; // 状态改变原因
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date lastDetDate; // 上次检定日期
	private Integer detPeriod; // 检定周期(月)
	private Long rightAttach; // 产权归属
	private String simNo; // SIM卡号
	private Byte numDigit; // 表码位数
	private String barCode; // 条形码
	private Long deptId; // 营业区域
	private Date createDate; // 创建时间
	private String remark; // 备注
	private Byte status; // 状态
	private String batchNo; // BATCH_NO 批次

	private Integer assetsNum; // 批量生成资产时的个数

	public Integer getAssetsNum() {
		return assetsNum;
	}

	public void setAssetsNum(Integer assetsNum) {
		this.assetsNum = assetsNum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMadeNo() {
		return madeNo;
	}

	public void setMadeNo(String madeNo) {
		this.madeNo = madeNo;
	}

	public Byte getFuncKindCode() {
		return funcKindCode;
	}

	public void setFuncKindCode(Byte funcKindCode) {
		this.funcKindCode = funcKindCode;
	}

	public Byte getPowerKindCode() {
		return powerKindCode;
	}

	public void setPowerKindCode(Byte powerKindCode) {
		this.powerKindCode = powerKindCode;
	}

	public Byte getFixedAssetsFlag() {
		return fixedAssetsFlag;
	}

	public void setFixedAssetsFlag(Byte fixedAssetsFlag) {
		this.fixedAssetsFlag = fixedAssetsFlag;
	}

	public Long getFacCode() {
		return facCode;
	}

	public void setFacCode(Long facCode) {
		this.facCode = facCode;
	}

	public Byte getModelCode() {
		return modelCode;
	}

	public void setModelCode(Byte modelCode) {
		this.modelCode = modelCode;
	}

	public Byte getRatedVoltCode() {
		return ratedVoltCode;
	}

	public void setRatedVoltCode(Byte ratedVoltCode) {
		this.ratedVoltCode = ratedVoltCode;
	}

	public Byte getRatedCurntCode() {
		return ratedCurntCode;
	}

	public void setRatedCurntCode(Byte ratedCurntCode) {
		this.ratedCurntCode = ratedCurntCode;
	}

	public Byte getAccuLevelCode() {
		return accuLevelCode;
	}

	public void setAccuLevelCode(Byte accuLevelCode) {
		this.accuLevelCode = accuLevelCode;
	}

	public Byte getPhaseLine() {
		return phaseLine;
	}

	public void setPhaseLine(Byte phaseLine) {
		this.phaseLine = phaseLine;
	}

	public Byte getIsTsMeter() {
		return isTsMeter;
	}

	public void setIsTsMeter(Byte isTsMeter) {
		this.isTsMeter = isTsMeter;
	}

	public Byte getStructCode() {
		return structCode;
	}

	public void setStructCode(Byte structCode) {
		this.structCode = structCode;
	}

	public Byte getMeterConst() {
		return meterConst;
	}

	public void setMeterConst(Byte meterConst) {
		this.meterConst = meterConst;
	}

	public Byte getConstUnit() {
		return constUnit;
	}

	public void setConstUnit(Byte constUnit) {
		this.constUnit = constUnit;
	}

	public Long getFactor() {
		return factor;
	}

	public void setFactor(Long factor) {
		this.factor = factor;
	}

	public Byte getMadeStdard() {
		return madeStdard;
	}

	public void setMadeStdard(Byte madeStdard) {
		this.madeStdard = madeStdard;
	}

	public Byte getAxeStructCode() {
		return axeStructCode;
	}

	public void setAxeStructCode(Byte axeStructCode) {
		this.axeStructCode = axeStructCode;
	}

	public String getMeteryardType() {
		return meteryardType;
	}

	public void setMeteryardType(String meteryardType) {
		this.meteryardType = meteryardType;
	}

	public Byte getNoReturnFlag() {
		return noReturnFlag;
	}

	public void setNoReturnFlag(Byte noReturnFlag) {
		this.noReturnFlag = noReturnFlag;
	}

	public Byte getIsCard() {
		return isCard;
	}

	public void setIsCard(Byte isCard) {
		this.isCard = isCard;
	}

	public Byte getConnectMode() {
		return connectMode;
	}

	public void setConnectMode(Byte connectMode) {
		this.connectMode = connectMode;
	}

	public Date getMadeDate() {
		return madeDate;
	}

	public void setMadeDate(Date madeDate) {
		this.madeDate = madeDate;
	}

	public Date getBayDate() {
		return bayDate;
	}

	public void setBayDate(Date bayDate) {
		this.bayDate = bayDate;
	}

	public Integer getLifeSpan() {
		return lifeSpan;
	}

	public void setLifeSpan(Integer lifeSpan) {
		this.lifeSpan = lifeSpan;
	}

	public String getManId() {
		return manId;
	}

	public void setManId(String manId) {
		this.manId = manId;
	}

	public Byte getStateFlag() {
		return stateFlag;
	}

	public void setStateFlag(Byte stateFlag) {
		this.stateFlag = stateFlag;
	}

	public Date getStatusChgDate() {
		return statusChgDate;
	}

	public void setStatusChgDate(Date statusChgDate) {
		this.statusChgDate = statusChgDate;
	}

	public String getStatusChgReason() {
		return statusChgReason;
	}

	public void setStatusChgReason(String statusChgReason) {
		this.statusChgReason = statusChgReason;
	}

	public Date getLastDetDate() {
		return lastDetDate;
	}

	public void setLastDetDate(Date lastDetDate) {
		this.lastDetDate = lastDetDate;
	}

	public Integer getDetPeriod() {
		return detPeriod;
	}

	public void setDetPeriod(Integer detPeriod) {
		this.detPeriod = detPeriod;
	}

	public Long getRightAttach() {
		return rightAttach;
	}

	public void setRightAttach(Long rightAttach) {
		this.rightAttach = rightAttach;
	}

	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public Byte getNumDigit() {
		return numDigit;
	}

	public void setNumDigit(Byte numDigit) {
		this.numDigit = numDigit;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
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

	public String getMeterAssetsNo() {
		return meterAssetsNo;
	}

	public void setMeterAssetsNo(String meterAssetsNo) {
		this.meterAssetsNo = meterAssetsNo;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

}
