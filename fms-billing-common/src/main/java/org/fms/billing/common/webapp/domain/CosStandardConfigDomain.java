package org.fms.billing.common.webapp.domain;

import java.util.Date;

import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;

//电费力率调整标准
public class CosStandardConfigDomain implements MybatisEntity{
	@TablePrimaryKey
	private Long id;
	//力调标准
	private Integer cosType;
	//功率因数
	private Double cos;
	//调整系数
	private Double cosStd;
	private Date createDate;
	private String operator;
	private Integer status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getCosType() {
		return cosType;
	}
	public void setCosType(Integer cosType) {
		this.cosType = cosType;
	}
	public Double getCos() {
		return cos;
	}
	public void setCos(Double cos) {
		this.cos = cos;
	}
	public Double getCosStd() {
		return cosStd;
	}
	public void setCosStd(Double cosStd) {
		this.cosStd = cosStd;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
