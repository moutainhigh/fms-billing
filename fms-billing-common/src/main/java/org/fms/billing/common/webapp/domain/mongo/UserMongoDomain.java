/**
 *    Auth:riozenc
 *    Date:2019年3月12日 下午5:40:37
 *    Title:com.riozenc.cim.webapp.domain.UserMongoDomain.java
 **/
package org.fms.billing.common.webapp.domain.mongo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.fms.billing.common.webapp.entity.ManagerParamEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.mybatis.MybatisEntity;

/**
 * 用电户 USER_INFO
 *
 * @author riozenc
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMongoDomain extends ManagerParamEntity implements MybatisEntity{

	private Long id;			// ID ID bigint
	private Long customerId;	// 对应 CUSTOMER_INFO中的ID 客户ID
	private String userNo;		// 客户编号 USER_NO varchar(16)
	private String userName;	// 客户名称 USER_NAME varchar(64)
	private String address;		// 用电地址 ADDRESS varchar(64)
	private Long businessPlaceCode;// 营业区域 BUSINESS_PLACE_CODE varchar(8)
	private Long writeSectId;	// 抄表区段 WRITE_SECT_ID varchar(8)
	private BigDecimal allCapacity;// 合同容量 ALL_CAPACITY decimal(10,2)
	private Byte loadType;// 负荷类别 LOAD_TYPE smallint FALSE FALSE FALSE
	private Byte isHighType;// 高可靠性标志 IS_HIGH_TYPE smallint FALSE FALSE FALSE
	private Byte tempType;// 临时用电标志 TEMP_TYPE smallint FALSE FALSE FALSE
	private Byte creditRankType;// 信誉等级 CREDIT_RANK_TYPE smallint FALSE FALSE FALSE
	private Byte writeType;// 抄表周期 WRITE_TYPE smallint FALSE FALSE FALSE
	private Byte userType;// 分类标识 USER_TYPE smallint FALSE FALSE FALSE
	private Integer elecType;// 用电类别 ELEC_TYPE_TYPE smallint FALSE FALSE FALSE
	private Byte voltLevelType;// 电压等级 VOLT_LEVEL_TYPE smallint FALSE FALSE FALSE
	private Long tgId;// 所属台区
	private Long guid; // 原系统用户ID
	private String meterBoxNumber; // 表箱号 METER_BOX_NUMBER

	private Date createDate;// 创建日期 CREATE_DATE datetime FALSE FALSE FALSE
	private String remark;// 备注 REMARK varchar(256) 256 FALSE FALSE FALSE
	private Byte status;// 客户状态 STATUS smallint

	private String writeSectNo; // 抄表段号
	private String writeSectName;// 抄表段名称
	private Byte sectUserType;// 区段用户类型 SECT_USER_TYPE smallint FALSE FALSE FALSE
	private String customerNo;
	private String customerName;
	private String tgName; // 台区名称
	private String mon;

	private List ids;

	private List<Long> writeSectIds;

	private List<Long> businessPlaceCodes;

	public List<Long> getBusinessPlaceCodes() {
		return businessPlaceCodes;
	}

	public void setBusinessPlaceCodes(List<Long> businessPlaceCodes) {
		this.businessPlaceCodes = businessPlaceCodes;
	}

	public List<Long> getWriteSectIds() {
		return writeSectIds;
	}

	public void setWriteSectIds(List<Long> writeSectIds) {
		this.writeSectIds = writeSectIds;
	}

	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	public String getTgName() {
		return tgName;
	}

	public void setTgName(String tgName) {
		this.tgName = tgName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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

	public Long getBusinessPlaceCode() {
		return businessPlaceCode;
	}

	public void setBusinessPlaceCode(Long businessPlaceCode) {
		this.businessPlaceCode = businessPlaceCode;
	}

	public Long getWriteSectId() {
		return writeSectId;
	}

	public void setWriteSectId(Long writeSectId) {
		this.writeSectId = writeSectId;
	}

	public BigDecimal getAllCapacity() {
		return allCapacity;
	}

	public void setAllCapacity(BigDecimal allCapacity) {
		this.allCapacity = allCapacity;
	}

	public Integer getElecType() {
		return elecType;
	}

	public void setElecType(Integer elecType) {
		this.elecType = elecType;
	}

	public Byte getVoltLevelType() {
		return voltLevelType;
	}

	public void setVoltLevelType(Byte voltLevelType) {
		this.voltLevelType = voltLevelType;
	}

	public Byte getLoadType() {
		return loadType;
	}

	public void setLoadType(Byte loadType) {
		this.loadType = loadType;
	}

	public Byte getIsHighType() {
		return isHighType;
	}

	public void setIsHighType(Byte isHighType) {
		this.isHighType = isHighType;
	}

	public Byte getTempType() {
		return tempType;
	}

	public void setTempType(Byte tempType) {
		this.tempType = tempType;
	}

	public Byte getCreditRankType() {
		return creditRankType;
	}

	public void setCreditRankType(Byte creditRankType) {
		this.creditRankType = creditRankType;
	}

	public Byte getWriteType() {
		return writeType;
	}

	public void setWriteType(Byte writeType) {
		this.writeType = writeType;
	}

	public Byte getUserType() {
		return userType;
	}

	public void setUserType(Byte userType) {
		this.userType = userType;
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

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getMeterBoxNumber() {
		return meterBoxNumber;
	}

	public void setMeterBoxNumber(String meterBoxNumber) {
		this.meterBoxNumber = meterBoxNumber;
	}

	public Long getGuid() {
		return guid;
	}

	public void setGuid(Long guid) {
		this.guid = guid;
	}

	public Long getTgId() {
		return tgId;
	}

	public void setTgId(Long tgId) {
		this.tgId = tgId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getWriteSectNo() {
		return writeSectNo;
	}

	public void setWriteSectNo(String writeSectNo) {
		this.writeSectNo = writeSectNo;
	}

	public String getWriteSectName() {
		return writeSectName;
	}

	public void setWriteSectName(String writeSectName) {
		this.writeSectName = writeSectName;
	}

	public Byte getSectUserType() {
		return sectUserType;
	}

	public void setSectUserType(Byte sectUserType) {
		this.sectUserType = sectUserType;
	}

	public List getIds() {
		return ids;
	}

	public void setIds(List ids) {
		this.ids = ids;
	}
}
