package org.fms.billing.common.webapp.domain;

import java.util.Date;

import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;

public class WriteRunningDomain  implements MybatisEntity{
	@TablePrimaryKey
	private Long id;
	//客户账号
	private String userNo;
	//表序号
	private Integer meterSn;
	//对象标识
	private String idFragment;
	//计量点编号
	private Integer groupNo;
	//计费点编号
	private Integer feeNo;
	//功能代码
	private Character functionCode;
	//功率方向
	private Character powerDirection;
	//资产编号
	private String assetsNo;
	//定量定比值
	private Integer constPower;
	//抄表序号
	private String writeSn;
	//分时表标志
	private Character isTsMeter;
	//综合倍率
	private Integer factorNum;
	//计划标志
	private Character planFlag;
	//安装日期
	private Date chgDate;
	//箱柜号
	private String meterBox;
	//相位
	private Character phase;
	//备注
	private String remark;
	//修改日期
	private Date modifyDate;
	//原抄表号
	private String writeOrder;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public Integer getMeterSn() {
		return meterSn;
	}
	public void setMeterSn(Integer meterSn) {
		this.meterSn = meterSn;
	}
	public String getIdFragment() {
		return idFragment;
	}
	public void setIdFragment(String idFragment) {
		this.idFragment = idFragment;
	}
	
	public Integer getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(Integer groupNo) {
		this.groupNo = groupNo;
	}
	public Integer getFeeNo() {
		return feeNo;
	}
	public void setFeeNo(Integer feeNo) {
		this.feeNo = feeNo;
	}
	public Character getFunctionCode() {
		return functionCode;
	}
	public void setFunctionCode(Character functionCode) {
		this.functionCode = functionCode;
	}
	public Character getPowerDirection() {
		return powerDirection;
	}
	public void setPowerDirection(Character powerDirection) {
		this.powerDirection = powerDirection;
	}
	public String getAssetsNo() {
		return assetsNo;
	}
	public void setAssetsNo(String assetsNo) {
		this.assetsNo = assetsNo;
	}
	public Integer getConstPower() {
		return constPower;
	}
	public void setConstPower(Integer constPower) {
		this.constPower = constPower;
	}
	public String getWriteSn() {
		return writeSn;
	}
	public void setWriteSn(String writeSn) {
		this.writeSn = writeSn;
	}
	public Character getIsTsMeter() {
		return isTsMeter;
	}
	public void setIsTsMeter(Character isTsMeter) {
		this.isTsMeter = isTsMeter;
	}
	public Integer getFactorNum() {
		return factorNum;
	}
	public void setFactorNum(Integer factorNum) {
		this.factorNum = factorNum;
	}
	public Character getPlanFlag() {
		return planFlag;
	}
	public void setPlanFlag(Character planFlag) {
		this.planFlag = planFlag;
	}
	public Date getChgDate() {
		return chgDate;
	}
	public void setChgDate(Date chgDate) {
		this.chgDate = chgDate;
	}
	public String getMeterBox() {
		return meterBox;
	}
	public void setMeterBox(String meterBox) {
		this.meterBox = meterBox;
	}
	public Character getPhase() {
		return phase;
	}
	public void setPhase(Character phase) {
		this.phase = phase;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getWriteOrder() {
		return writeOrder;
	}
	public void setWriteOrder(String writeOrder) {
		this.writeOrder = writeOrder;
	}
}
