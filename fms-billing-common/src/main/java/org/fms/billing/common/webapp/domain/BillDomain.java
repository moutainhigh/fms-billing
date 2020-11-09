package org.fms.billing.common.webapp.domain;

import java.util.Date;

import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;



//账单信息
public class BillDomain implements MybatisEntity{
	@TablePrimaryKey
	private Long id;
	//收费记录ID
	private Long chargeId;
	//电费年月
	private Integer mon;
	//应收类型
	private Integer ysTypeCode;
	//票据类型
	private Integer noteTypeCode;
	//生成方式
	private Integer createFlag;
	//客户编号
	private String userNo;
	//客户名称
	private String userName;
	private String address;
	//通讯地址
	private String mailAddress;
	private String tel;
	//抄表日期
	private Date writeDate;
	//计费电量
	private Double totalPower;
	//应收电费
	private Double totalMoney;
	//违约金
	private Double punishMoney;
	//合计金额
	private Double sumMoney;
	//大写合计金额
	private String upperMoney;
	//收费方式
	private Integer chargeMode;
	//客户类型
	private String userType;
	//增值税户标志
	private Integer addTaxflag;
	//结清标志
	private Integer paidFlag;
	//收费日期
	private Date payDate;
	//收费员
	private String collectorId;
	//票据号
	private String noteNo;
	//开票日期
	private Date printDate;
	//开票员
	private String printMan;
	private Date createDate;
	private String remark;
	private Integer status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getChargeId() {
		return chargeId;
	}
	public void setChargeId(Long chargeId) {
		this.chargeId = chargeId;
	}
	public Integer getMon() {
		return mon;
	}
	public void setMon(Integer mon) {
		this.mon = mon;
	}
	public Integer getYsTypeCode() {
		return ysTypeCode;
	}
	public void setYsTypeCode(Integer ysTypeCode) {
		this.ysTypeCode = ysTypeCode;
	}
	public Integer getNoteTypeCode() {
		return noteTypeCode;
	}
	public void setNoteTypeCode(Integer noteTypeCode) {
		this.noteTypeCode = noteTypeCode;
	}
	public Integer getCreateFlag() {
		return createFlag;
	}
	public void setCreateFlag(Integer createFlag) {
		this.createFlag = createFlag;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Date getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	public Double getTotalPower() {
		return totalPower;
	}
	public void setTotalPower(Double totalPower) {
		this.totalPower = totalPower;
	}
	public Double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
	public Double getPunishMoney() {
		return punishMoney;
	}
	public void setPunishMoney(Double punishMoney) {
		this.punishMoney = punishMoney;
	}
	public Double getSumMoney() {
		return sumMoney;
	}
	public void setSumMoney(Double sumMoney) {
		this.sumMoney = sumMoney;
	}
	public String getUpperMoney() {
		return upperMoney;
	}
	public void setUpperMoney(String upperMoney) {
		this.upperMoney = upperMoney;
	}
	public Integer getChargeMode() {
		return chargeMode;
	}
	public void setChargeMode(Integer chargeMode) {
		this.chargeMode = chargeMode;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public Integer getAddTaxflag() {
		return addTaxflag;
	}
	public void setAddTaxflag(Integer addTaxflag) {
		this.addTaxflag = addTaxflag;
	}
	public Integer getPaidFlag() {
		return paidFlag;
	}
	public void setPaidFlag(Integer paidFlag) {
		this.paidFlag = paidFlag;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public String getCollectorId() {
		return collectorId;
	}
	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}
	public String getNoteNo() {
		return noteNo;
	}
	public void setNoteNo(String noteNo) {
		this.noteNo = noteNo;
	}
	public Date getPrintDate() {
		return printDate;
	}
	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}
	public String getPrintMan() {
		return printMan;
	}
	public void setPrintMan(String printMan) {
		this.printMan = printMan;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
