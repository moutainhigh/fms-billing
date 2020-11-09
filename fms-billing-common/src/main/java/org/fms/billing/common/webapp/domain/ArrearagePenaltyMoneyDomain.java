/**
 * Author : chizf
 * Date : 2020年6月5日 下午7:17:49
 * Title : com.riozenc.billing.webapp.domain.ArrearagePenaltyMoneyDomain.java
 *
**/
package org.fms.billing.common.webapp.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;

/**
 * 欠费违约数据生成记录
 * 
 * @author czy
 *
 */
public class ArrearagePenaltyMoneyDomain implements MybatisEntity {

	@TablePrimaryKey
	private Long id;// ID ID bigint TRUE FALSE TRUE
	private Long arrearageId;// 欠费记录ID ARREARAGE_ID bigint FALSE FALSE FALSE
	private BigDecimal oweMoney;// 当前欠费 OWE_MONEY decimal(14,4) 14 4 FALSE FALSE FALSE
	private BigDecimal penaltyMoney;// 违约金 PENALTY_MONEY decimal(14,4) 14 4 FALSE FALSE FALSE
	private Date createDate;// 创建时间 CREATE_DATE datetime FALSE FALSE FALSE
	private String remark;// 备注 REMARK varchar(256) 256 FALSE FALSE FALSE
	private Byte status;// 状态 STATUS smallint FALSE FALSE FALSE

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getArrearageId() {
		return arrearageId;
	}

	public void setArrearageId(Long arrearageId) {
		this.arrearageId = arrearageId;
	}

	public BigDecimal getOweMoney() {
		return oweMoney;
	}

	public void setOweMoney(BigDecimal oweMoney) {
		this.oweMoney = oweMoney;
	}

	public BigDecimal getPenaltyMoney() {
		return penaltyMoney;
	}

	public void setPenaltyMoney(BigDecimal penaltyMoney) {
		this.penaltyMoney = penaltyMoney;
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

}
