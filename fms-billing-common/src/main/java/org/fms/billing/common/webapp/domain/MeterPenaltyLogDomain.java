package org.fms.billing.common.webapp.domain;

import java.util.Date;

import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;


//违约金记录表
public class MeterPenaltyLogDomain implements MybatisEntity{
	@TablePrimaryKey
	private Long id;
	//计量点ID
	private Long meterId;
	//欠费记录ID
	private Long arrearageId;
	//当日欠费
	private Double arrears;
	//当日违约金
	private Double penaltyMoney;
	private Date createDate;
	private Integer status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMeterId() {
		return meterId;
	}
	public void setMeterId(Long meterId) {
		this.meterId = meterId;
	}
	public Long getArrearageId() {
		return arrearageId;
	}
	public void setArrearageId(Long arrearageId) {
		this.arrearageId = arrearageId;
	}
	public Double getArrears() {
		return arrears;
	}
	public void setArrears(Double arrears) {
		this.arrears = arrears;
	}
	public Double getPenaltyMoney() {
		return penaltyMoney;
	}
	public void setPenaltyMoney(Double penaltyMoney) {
		this.penaltyMoney = penaltyMoney;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

}
