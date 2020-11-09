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

/**
 * 线路	LINE_INFO
 *
 * @author riozenc
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineEntity {

	private Long id;// ID ID bigint TRUE FALSE TRUE
	private String lineCode; //线路编号
	private String lineName; //线路名称
	private Byte voltType; //电压等级
	private Byte lineType; //线路类型
	private Date runDate; //投运日期
	private String switchNo; //开关号
	private String lineModel; //线路型号
	private BigDecimal lineLenght; //线路长度
	private Byte ratingCurnt; //额定电流
	private Byte ratingVol; //额定电压
	private Date createDate; //创建时间
	private String remark; //备注
	private Byte status; //状态
    private Long businessPlaceCode;  //BUSINESS_PLACE_CODE

	private Long beginSubsId;
	private String beginSubsName; // 厂站名称
	private Long endSubsId;
	private String endSubsName; // 厂站名称
	private String subsName; // 厂站名称
	private List<Long> lineIds;

	public List<Long> getLineIds() {
		return lineIds;
	}

	public void setLineIds(List<Long> lineIds) {
		this.lineIds = lineIds;
	}

	public String getSubsName() {
		return subsName;
	}

	public void setSubsName(String subsName) {
		this.subsName = subsName;
	}

	public Long getBeginSubsId() {
		return beginSubsId;
	}
	public void setBeginSubsId(Long beginSubsId) {
		this.beginSubsId = beginSubsId;
	}
	public Long getEndSubsId() {
		return endSubsId;
	}
	public void setEndSubsId(Long endSubsId) {
		this.endSubsId = endSubsId;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLineCode() {
		return lineCode;
	}
	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public Byte getVoltType() {
		return voltType;
	}
	public void setVoltType(Byte voltType) {
		this.voltType = voltType;
	}
	public Byte getLineType() {
		return lineType;
	}
	public void setLineType(Byte lineType) {
		this.lineType = lineType;
	}
	public Date getRunDate() {
		return runDate;
	}
	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}
	public String getSwitchNo() {
		return switchNo;
	}
	public void setSwitchNo(String switchNo) {
		this.switchNo = switchNo;
	}
	public String getLineModel() {
		return lineModel;
	}
	public void setLineModel(String lineModel) {
		this.lineModel = lineModel;
	}
	public BigDecimal getLineLenght() {
		return lineLenght;
	}
	public void setLineLenght(BigDecimal lineLenght) {
		this.lineLenght = lineLenght;
	}
	public Byte getRatingCurnt() {
		return ratingCurnt;
	}
	public void setRatingCurnt(Byte ratingCurnt) {
		this.ratingCurnt = ratingCurnt;
	}
	public Byte getRatingVol() {
		return ratingVol;
	}
	public void setRatingVol(Byte ratingVol) {
		this.ratingVol = ratingVol;
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

	public String getBeginSubsName() {
		return beginSubsName;
	}

	public void setBeginSubsName(String beginSubsName) {
		this.beginSubsName = beginSubsName;
	}

	public String getEndSubsName() {
		return endSubsName;
	}

	public void setEndSubsName(String endSubsName) {
		this.endSubsName = endSubsName;
	}

	public Long getBusinessPlaceCode() {
		return businessPlaceCode;
	}

	public void setBusinessPlaceCode(Long businessPlaceCode) {
		this.businessPlaceCode = businessPlaceCode;
	}






}
