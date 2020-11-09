/**
 * Author : czy
 * Date : 2019年12月5日 上午8:59:22
 * Title : com.riozenc.billing.webapp.domain.mongo.TransformerCalculateMongoDomain.java
 *
**/
package org.fms.billing.common.webapp.domain.mongo;

import java.math.BigDecimal;
import java.util.List;

public class TransformerCalculateMongoDomain {

	private Long key;
	private Byte day;
	private BigDecimal capacity;
	private BigDecimal activeEnergy;
	private BigDecimal reactiveEnergy;
	private BigDecimal activeTransformerLoss;
	private BigDecimal reactiveTransformerLoss;
	private Byte transformerLossType;
	private Byte transformerModelType;
	private boolean isCalculated;

	private List<TransformerMongoDomain> transformerDomains;

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public Byte getDay() {
		return day;
	}

	public void setDay(Byte day) {
		this.day = day;
	}

	public BigDecimal getCapacity() {
		return capacity;
	}

	public void setCapacity(BigDecimal capacity) {
		this.capacity = capacity;
	}

	public BigDecimal getActiveEnergy() {
		return activeEnergy;
	}

	public void setActiveEnergy(BigDecimal activeEnergy) {
		this.activeEnergy = activeEnergy;
	}

	public BigDecimal getReactiveEnergy() {
		return reactiveEnergy;
	}

	public void setReactiveEnergy(BigDecimal reactiveEnergy) {
		this.reactiveEnergy = reactiveEnergy;
	}

	public BigDecimal getActiveTransformerLoss() {
		return activeTransformerLoss;
	}

	public void setActiveTransformerLoss(BigDecimal activeTransformerLoss) {
		this.activeTransformerLoss = activeTransformerLoss;
	}

	public BigDecimal getReactiveTransformerLoss() {
		return reactiveTransformerLoss;
	}

	public void setReactiveTransformerLoss(BigDecimal reactiveTransformerLoss) {
		this.reactiveTransformerLoss = reactiveTransformerLoss;
	}

	public Byte getTransformerLossType() {
		return transformerLossType;
	}

	public void setTransformerLossType(Byte transformerLossType) {
		this.transformerLossType = transformerLossType;
	}

	public Byte getTransformerModelType() {
		return transformerModelType;
	}

	public void setTransformerModelType(Byte transformerModelType) {
		this.transformerModelType = transformerModelType;
	}

	public boolean isCalculated() {
		return isCalculated;
	}

	public void setCalculated(boolean isCalculated) {
		this.isCalculated = isCalculated;
	}

	public List<TransformerMongoDomain> getTransformerDomains() {
		return transformerDomains;
	}

	public void setTransformerDomains(List<TransformerMongoDomain> transformerDomains) {
		this.transformerDomains = transformerDomains;
	}

}
