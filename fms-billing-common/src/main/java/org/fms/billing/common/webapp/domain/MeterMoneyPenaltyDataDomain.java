/**
 * Author : chizf
 * Date : 2020年6月4日 下午8:50:55
 * Title : com.riozenc.billing.webapp.domain.MeterMoneyPenaltyDataDomain.java
 *
**/
package org.fms.billing.common.webapp.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.riozenc.titanTool.mybatis.MybatisEntity;

public class MeterMoneyPenaltyDataDomain implements MybatisEntity {

	private Long id;// ID ID bigint TRUE FALSE TRUE
	private Long writeSectId;// 抄表区段ID
	private Long arrearageId;// 欠费记录ID ARREARAGE_ID bigint FALSE FALSE FALSE
	private BigDecimal oweMoney;// 欠费金额 OWE_MONEY decimal(14,4) 14 4 FALSE FALSE FALSE
	private Date createDate;// 创建时间 CREATE_DATE datetime FALSE FALSE FALSE
	private Date updateDate;// 更新时间 UPDATE_DATE datetime FALSE FALSE FALSE
	private String remark;// 备注 REMARK varchar(256) 256 FALSE FALSE FALSE
	private Byte status;// 状态 STATUS smallint FALSE FALSE FALSE

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWriteSectId() {
		return writeSectId;
	}

	public void setWriteSectId(Long writeSectId) {
		this.writeSectId = writeSectId;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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
