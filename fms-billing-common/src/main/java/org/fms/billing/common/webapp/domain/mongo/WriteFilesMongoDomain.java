/**
 * Author : czy
 * Date : 2019年12月3日 下午7:01:32
 * Title : com.riozenc.billing.webapp.domain.mongo.WriteFilesDomain.java
 *
**/
package org.fms.billing.common.webapp.domain.mongo;

import java.math.BigDecimal;

public class WriteFilesMongoDomain {
	private String meterNo;
	private Byte timeSeg;
	private Byte functionCode;
	private Byte powerDirection;
	private Byte phaseSeq;
	private BigDecimal startNum;
	private BigDecimal endNum;
	private BigDecimal diffNum;
	private BigDecimal readPower;
	private BigDecimal addPower;
	private BigDecimal changePower;
	private BigDecimal deductionPower;
	private BigDecimal protocolPower;
	private BigDecimal compensatingPower;
	private BigDecimal lineLossPower;
	private BigDecimal transformerLossPower;
	private BigDecimal chargePower;
	private BigDecimal charge;
	private BigDecimal factorNum;
	private BigDecimal price;

	private Long meterOrder;// 表序号
	private BigDecimal writeSn;

	public Long getMeterOrder() {
		return meterOrder;
	}

	public void setMeterOrder(Long meterOrder) {
		this.meterOrder = meterOrder;
	}

	public BigDecimal getWriteSn() {
		return writeSn;
	}

	public void setWriteSn(BigDecimal writeSn) {
		this.writeSn = writeSn;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public Byte getTimeSeg() {
		return timeSeg;
	}

	public void setTimeSeg(Byte timeSeg) {
		this.timeSeg = timeSeg;
	}

	public Byte getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(Byte functionCode) {
		this.functionCode = functionCode;
	}

	public Byte getPowerDirection() {
		return powerDirection;
	}

	public void setPowerDirection(Byte powerDirection) {
		this.powerDirection = powerDirection;
	}

	public Byte getPhaseSeq() {
		return phaseSeq;
	}

	public void setPhaseSeq(Byte phaseSeq) {
		this.phaseSeq = phaseSeq;
	}

	public BigDecimal getStartNum() {
		return startNum;
	}

	public void setStartNum(BigDecimal startNum) {
		this.startNum = startNum;
	}

	public BigDecimal getEndNum() {
		return endNum;
	}

	public void setEndNum(BigDecimal endNum) {
		this.endNum = endNum;
	}

	public BigDecimal getDiffNum() {
		return diffNum;
	}

	public void setDiffNum(BigDecimal diffNum) {
		this.diffNum = diffNum;
	}

	public BigDecimal getReadPower() {
		return readPower;
	}

	public void setReadPower(BigDecimal readPower) {
		this.readPower = readPower;
	}

	public BigDecimal getAddPower() {
		return addPower;
	}

	public void setAddPower(BigDecimal addPower) {
		this.addPower = addPower;
	}

	public BigDecimal getChangePower() {
		return changePower;
	}

	public void setChangePower(BigDecimal changePower) {
		this.changePower = changePower;
	}

	public BigDecimal getDeductionPower() {
		return deductionPower;
	}

	public void setDeductionPower(BigDecimal deductionPower) {
		this.deductionPower = deductionPower;
	}

	public BigDecimal getProtocolPower() {
		return protocolPower;
	}

	public void setProtocolPower(BigDecimal protocolPower) {
		this.protocolPower = protocolPower;
	}

	public BigDecimal getCompensatingPower() {
		return compensatingPower;
	}

	public void setCompensatingPower(BigDecimal compensatingPower) {
		this.compensatingPower = compensatingPower;
	}

	public BigDecimal getLineLossPower() {
		return lineLossPower;
	}

	public void setLineLossPower(BigDecimal lineLossPower) {
		this.lineLossPower = lineLossPower;
	}

	public BigDecimal getTransformerLossPower() {
		return transformerLossPower;
	}

	public void setTransformerLossPower(BigDecimal transformerLossPower) {
		this.transformerLossPower = transformerLossPower;
	}

	public BigDecimal getChargePower() {
		return chargePower;
	}

	public void setChargePower(BigDecimal chargePower) {
		this.chargePower = chargePower;
	}

	public BigDecimal getCharge() {
		return charge;
	}

	public void setCharge(BigDecimal charge) {
		this.charge = charge;
	}

	public BigDecimal getFactorNum() {
		return factorNum;
	}

	public void setFactorNum(BigDecimal factorNum) {
		this.factorNum = factorNum;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
