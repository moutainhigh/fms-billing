package org.fms.billing.common.webapp.domain;

import java.util.Date;

import org.fms.billing.common.webapp.entity.ManagerParamEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;

//呆坏账记录
@JsonIgnoreProperties(ignoreUnknown = true)
public class BadArrearageDomain extends ManagerParamEntity implements MybatisEntity {
	@TablePrimaryKey
	private Long id;
	// 应收凭证号
	private Long arrearageId;
	private Integer oldIsSettle;
	private Integer newIsSettle;
	private Integer operator;
	private String remark;
	private Date createDate;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

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

	public Integer getOldIsSettle() {
		return oldIsSettle;
	}

	public void setOldIsSettle(Integer oldIsSettle) {
		this.oldIsSettle = oldIsSettle;
	}

	public Integer getNewIsSettle() {
		return newIsSettle;
	}

	public void setNewIsSettle(Integer newIsSettle) {
		this.newIsSettle = newIsSettle;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
