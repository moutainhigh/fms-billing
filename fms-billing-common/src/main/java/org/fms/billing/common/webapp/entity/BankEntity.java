package org.fms.billing.common.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BankEntity extends ManagerParamEntity implements MybatisEntity{
	
	@TablePrimaryKey
	private Long id;
	private String bankCode;				//	   BANK_CODE 银行代码
	private String bankName;			//	   BANK_NAME  银行名称
	private String bankAddress;			//	   BANK_ADDRESS  办公地址
	private String inTouch;			//	   IN_TOUCH  联系人
	private String tel;			//	   TEL   联系电话
	private String bankHeadOffice;		//	     总行代号
	private Byte isBankHead;		//	    是否总行
	private Long businessPlaceCode;			//	   AREA_CODE 营业区域
	private Byte isYdlw;				//	   IS_YDLW  是否银电联网
	private String remark;				//	   REMARK               varchar(256),
	private	Byte status;		//	   STATUS               smallint,
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAddress() {
		return bankAddress;
	}
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	public String getInTouch() {
		return inTouch;
	}
	public void setInTouch(String inTouch) {
		this.inTouch = inTouch;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getBankHeadOffice() {
		return bankHeadOffice;
	}
	public void setBankHeadOffice(String bankHeadOffice) {
		this.bankHeadOffice = bankHeadOffice;
	}
	public Byte getIsBankHead() {
		return isBankHead;
	}
	public void setIsBankHead(Byte isBankHead) {
		this.isBankHead = isBankHead;
	}
	public Long getBusinessPlaceCode() {
		return businessPlaceCode;
	}
	public void setBusinessPlaceCode(Long businessPlaceCode) {
		this.businessPlaceCode = businessPlaceCode;
	}
	public Byte getIsYdlw() {
		return isYdlw;
	}
	public void setIsYdlw(Byte isYdlw) {
		this.isYdlw = isYdlw;
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
