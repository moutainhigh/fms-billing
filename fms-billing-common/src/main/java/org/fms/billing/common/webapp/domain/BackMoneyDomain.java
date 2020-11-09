package org.fms.billing.common.webapp.domain;

import java.util.Date;

import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;


//退补信息
public class BackMoneyDomain implements MybatisEntity{
	@TablePrimaryKey
	private Long id;
	//计量点ID
	private Long meterId;
	//工作单号
	private String appNo;
	//退费人
	private String backPerson;
	//退费日期
	private Date backDate;
	//退费金额
	private Double backMoney;
	//退费类型
	private Integer backType;
	//退费方式
	private Integer fChargeMode;
	//退费原因
	private String applyReason;
	//资金来源
	private Integer moneyTo;
	//客户经办人
	private String manName;
	//经办电话
	private String manPhone;
	//经办证件号
	private String manCard;
	//申请人
	private String applyPerson;
	//申请日期
	private Date applyDate;
	//审批人
	private String passPerson;
	//审批日期
	private Date passDate;
	//审批标志
	private Integer dealFlag;
	private String remark;
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
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	
	public String getBackPerson() {
		return backPerson;
	}
	public void setBackPerson(String backPerson) {
		this.backPerson = backPerson;
	}
	public Date getBackDate() {
		return backDate;
	}
	public void setBackDate(Date backDate) {
		this.backDate = backDate;
	}
	public Double getBackMoney() {
		return backMoney;
	}
	public void setBackMoney(Double backMoney) {
		this.backMoney = backMoney;
	}
	public Integer getBackType() {
		return backType;
	}
	public void setBackType(Integer backType) {
		this.backType = backType;
	}
	public Integer getfChargeMode() {
		return fChargeMode;
	}
	public void setfChargeMode(Integer fChargeMode) {
		this.fChargeMode = fChargeMode;
	}
	public String getApplyReason() {
		return applyReason;
	}
	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}
	public Integer getMoneyTo() {
		return moneyTo;
	}
	public void setMoneyTo(Integer moneyTo) {
		this.moneyTo = moneyTo;
	}
	public String getManName() {
		return manName;
	}
	public void setManName(String manName) {
		this.manName = manName;
	}
	public String getManPhone() {
		return manPhone;
	}
	public void setManPhone(String manPhone) {
		this.manPhone = manPhone;
	}
	public String getManCard() {
		return manCard;
	}
	public void setManCard(String manCard) {
		this.manCard = manCard;
	}
	public String getApplyPerson() {
		return applyPerson;
	}
	public void setApplyPerson(String applyPerson) {
		this.applyPerson = applyPerson;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getPassPerson() {
		return passPerson;
	}
	public void setPassPerson(String passPerson) {
		this.passPerson = passPerson;
	}
	public Date getPassDate() {
		return passDate;
	}
	public void setPassDate(Date passDate) {
		this.passDate = passDate;
	}
	public Integer getDealFlag() {
		return dealFlag;
	}
	public void setDealFlag(Integer dealFlag) {
		this.dealFlag = dealFlag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
