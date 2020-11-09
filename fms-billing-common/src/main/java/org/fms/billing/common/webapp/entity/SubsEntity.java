/**
 *    Auth:riozenc
 *    Date:2019年3月8日 下午3:22:58
 *    Title:com.riozenc.cim.webapp.domain.CustomerDomain.java
 **/
package org.fms.billing.common.webapp.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.mybatis.MybatisEntity;

/**
 * 变电站	SUBS_INFO
 * 
 * @author riozenc
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubsEntity extends ManagerParamEntity implements MybatisEntity {

	private Long id;// ID ID bigint TRUE FALSE TRUE
	private String subsNo; //变电站编码
	private String subsName; //名称
	private String shortName; //拼音简写
	private Byte voltType; //电压等级
	private String address; //变电站地址
	private Byte subsType; //厂站类型
	private Byte subsProp; //电厂性质
	private Byte mtNum; //主变台数
	private BigDecimal mtCap; //主变容量
	private Byte status; //运行状态
	private Date createDate;
    private Long businessPlaceCode;  //BUSINESS_PLACE_CODE
	private List<Long> businessPlaceCodes;

	public List<Long> getBusinessPlaceCodes() {
		return businessPlaceCodes;
	}

	public void setBusinessPlaceCodes(List<Long> businessPlaceCodes) {
		this.businessPlaceCodes = businessPlaceCodes;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSubsNo() {
		return subsNo;
	}
	public void setSubsNo(String subsNo) {
		this.subsNo = subsNo;
	}
	public String getSubsName() {
		return subsName;
	}
	public void setSubsName(String subsName) {
		this.subsName = subsName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public Byte getVoltType() {
		return voltType;
	}
	public void setVoltType(Byte voltType) {
		this.voltType = voltType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Byte getSubsType() {
		return subsType;
	}
	public void setSubsType(Byte subsType) {
		this.subsType = subsType;
	}
	public Byte getSubsProp() {
		return subsProp;
	}
	public void setSubsProp(Byte subsProp) {
		this.subsProp = subsProp;
	}
	public Byte getMtNum() {
		return mtNum;
	}
	public void setMtNum(Byte mtNum) {
		this.mtNum = mtNum;
	}
	public BigDecimal getMtCap() {
		return mtCap;
	}
	public void setMtCap(BigDecimal mtCap) {
		this.mtCap = mtCap;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getBusinessPlaceCode() {
		return businessPlaceCode;
	}
	public void setBusinessPlaceCode(Long businessPlaceCode) {
		this.businessPlaceCode = businessPlaceCode;
	}





}
