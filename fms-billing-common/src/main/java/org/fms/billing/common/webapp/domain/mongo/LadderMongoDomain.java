/**
 * Author : chizf
 * Date : 2020年4月26日 下午7:13:30
 * Title : com.riozenc.billing.webapp.domain.mongo.LadderMongoDomain.java
 *
**/
package org.fms.billing.common.webapp.domain.mongo;

import java.math.BigDecimal;

public class LadderMongoDomain {
	private Integer ladderSn; // 阶梯
	private BigDecimal ladderValue; // 阶梯值
	private BigDecimal price;// 电价
	private BigDecimal chargePower;// 量
	private BigDecimal amount;// 费

	public Integer getLadderSn() {
		return ladderSn;
	}

	public void setLadderSn(Integer ladderSn) {
		this.ladderSn = ladderSn;
	}

	public BigDecimal getLadderValue() {
		return ladderValue;
	}

	public void setLadderValue(BigDecimal ladderValue) {
		this.ladderValue = ladderValue;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getChargePower() {
		return chargePower;
	}

	public void setChargePower(BigDecimal chargePower) {
		this.chargePower = chargePower;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
