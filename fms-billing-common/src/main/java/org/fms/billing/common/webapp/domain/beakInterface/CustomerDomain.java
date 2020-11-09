/**
 *    Auth:riozenc
 *    Date:2019年3月8日 下午3:22:58
 *    Title:com.riozenc.cim.webapp.domain.CustomerDomain.java
 **/
package org.fms.billing.common.webapp.domain.beakInterface;

import java.util.Date;
import java.util.List;

import org.fms.billing.common.webapp.entity.ManagerParamEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;

/**
 * 客户 CUSTOMER_INFO
 * 
 * @author riozenc
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDomain extends ManagerParamEntity implements MybatisEntity {

	@TablePrimaryKey
	private Long id;// ID ID bigint TRUE FALSE TRUE
	private String customerNo;// 客户编号 CUSTOMER_NO VARCHAR(16) 16 FALSE FALSE FALSE
	private String customerName;// 客户名称 CUSTOMER_NAME VARCHAR(64) 64 FALSE FALSE FALSE
	private String customerNameSpell;// 名称拼音 CUSTOMER_NAME_SPELL VARCHAR(8) 8 FALSE FALSE FALSE
	private String address;// 地址 ADDRESS VARCHAR(128) 128 FALSE FALSE FALSE
	private String addressSpell;// 地址拼音 ADDRESS_SPELL VARCHAR(16) 16 FALSE FALSE FALSE
	private Long deptId;// 所属区域
	private Byte cardType;// 证件类型 CARD_TYPE smallint FALSE FALSE FALSE
	private String cardNo;// 证件号 CARD_NO VARCHAR(32) 32 FALSE FALSE FALSE
	private String linkMan;// 联系人 LINK_MAN VARCHAR(64) 64 FALSE FALSE FALSE
	private String contactInformation;// 联系方式 CONTACT_INFORMATION VARCHAR(64) 64 FALSE FALSE FALSE
	private Date createDate;// 立户日期 CREATE_DATE datetime FALSE FALSE FALSE
	private Date cancelDate;// 销户日期 CANCEL_DATE datetime FALSE FALSE FALSE
	private String remark;// 备注 REMARK VARCHAR(256) 256 FALSE FALSE FALSE
	private Byte status;// 客户状态 STATUS smallint FALSE FALSE FALSE
	private	Byte insideFlag;	//局内外 INSIDE_FLAG
	private String spareLinkMan;				//	备用联系人	SPARE_LINK_MAN	VARCHAR(64)
	private String spareContactInformation;				//	备用联系方式	SPARE_CONTACT_INFORMATION	VARCHAR(64)
	private Long businessPlaceCode;				//	营业区域	BUSINESS_PLACE_CODE	bigint		
	private	Byte weChatFlag;			//	是否绑定微信	WE_CHAT_FLAG	smallint
	private Integer startMon;
	private Integer endMon;
	private List<Long> meterIds;
	private List<Long> customerIds;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerNameSpell() {
		return customerNameSpell;
	}

	public void setCustomerNameSpell(String customerNameSpell) {
		this.customerNameSpell = customerNameSpell;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressSpell() {
		return addressSpell;
	}

	public void setAddressSpell(String addressSpell) {
		this.addressSpell = addressSpell;
	}

	public Byte getCardType() {
		return cardType;
	}

	public void setCardType(Byte cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getContactInformation() {
		return contactInformation;
	}

	public void setContactInformation(String contactInformation) {
		this.contactInformation = contactInformation;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
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

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getSpareLinkMan() {
		return spareLinkMan;
	}

	public void setSpareLinkMan(String spareLinkMan) {
		this.spareLinkMan = spareLinkMan;
	}

	public String getSpareContactInformation() {
		return spareContactInformation;
	}

	public void setSpareContactInformation(String spareContactInformation) {
		this.spareContactInformation = spareContactInformation;
	}

	public Long getBusinessPlaceCode() {
		return businessPlaceCode;
	}

	public void setBusinessPlaceCode(Long businessPlaceCode) {
		this.businessPlaceCode = businessPlaceCode;
	}

	public Byte getWeChatFlag() {
		return weChatFlag;
	}

	public void setWeChatFlag(Byte weChatFlag) {
		this.weChatFlag = weChatFlag;
	}

	public Byte getInsideFlag() {
		return insideFlag;
	}

	public void setInsideFlag(Byte insideFlag) {
		this.insideFlag = insideFlag;
	}

	public Integer getStartMon() {
		return startMon;
	}

	public void setStartMon(Integer startMon) {
		this.startMon = startMon;
	}

	public Integer getEndMon() {
		return endMon;
	}

	public void setEndMon(Integer endMon) {
		this.endMon = endMon;
	}

	public List<Long> getMeterIds() {
		return meterIds;
	}

	public void setMeterIds(List<Long> meterIds) {
		this.meterIds = meterIds;
	}

	public List<Long> getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(List<Long> customerIds) {
		this.customerIds = customerIds;
	}
}
