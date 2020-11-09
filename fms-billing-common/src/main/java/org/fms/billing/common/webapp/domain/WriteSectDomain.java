package org.fms.billing.common.webapp.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.fms.billing.common.webapp.entity.ManagerParamEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;

//抄表区段
@JsonIgnoreProperties(ignoreUnknown = true)
public class WriteSectDomain extends ManagerParamEntity implements MybatisEntity {
	@TablePrimaryKey
	private Long id;
	// 抄表区段
	private String writeSectNo;
	// 抄表区段名称
	private String writeSectName;
	// 抄表员ID
	private Long writorId;
	// 计算人ID
	private Long calculatorId;
	// 营业区域
	private Long businessPlaceCode;
	// 抄表班组
	private String deptCode;
	// 抄表方式
	private Integer writeType;
	// 应抄日期
	private Integer shouldWriteDays;
	// 标准抄表天数
	private Integer standardWriteDays;
	// 应计算日期
	private Integer shouldCalDays;
	// 区段用户类型
	private Long sectUserType;
	// 应算违约金天数
	private Integer punishDays;
	// 创建时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	private String remark;
	private Integer status;

	private String mon;// 电费月份

	// mongo
	private Integer startMon;
	private Integer endMon;

	private Integer initedNum;  //0  已初始化数量
	private Integer readedNum;	//1	已抄表数量
	private Integer calcedNum;	//2	已计算数量
	private Integer calceExceptiondNum;//-2 算费异常数量
	private Integer issuedNum;//3	已发行数量
	private Integer unknownNum;//	未知状态
	private Integer findStatus;

	public Integer getFindStatus() {
		return findStatus;
	}

	public void setFindStatus(Integer findStatus) {
		this.findStatus = findStatus;
	}

	public Integer getInitedNum() {
		return initedNum;
	}

	public void setInitedNum(Integer initedNum) {
		this.initedNum = initedNum;
	}

	public Integer getReadedNum() {
		return readedNum;
	}

	public void setReadedNum(Integer readedNum) {
		this.readedNum = readedNum;
	}

	public Integer getCalcedNum() {
		return calcedNum;
	}

	public void setCalcedNum(Integer calcedNum) {
		this.calcedNum = calcedNum;
	}

	public Integer getCalceExceptiondNum() {
		return calceExceptiondNum;
	}

	public void setCalceExceptiondNum(Integer calceExceptiondNum) {
		this.calceExceptiondNum = calceExceptiondNum;
	}

	public Integer getIssuedNum() {
		return issuedNum;
	}

	public void setIssuedNum(Integer issuedNum) {
		this.issuedNum = issuedNum;
	}

	private BigDecimal complete;// 已经完成抄表的数量
	private BigDecimal uncomplete;// 未完成抄表的数量
	
	private BigDecimal calcComplete;// 已经完成抄表的数量
	private BigDecimal calcUncomplete;// 未完成抄表的数量
	

	private List writeSectionIds;

	private List businessPlaceCodes;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getWritorId() {
		return writorId;
	}

	public void setWritorId(Long writorId) {
		this.writorId = writorId;
	}

	public Long getCalculatorId() {
		return calculatorId;
	}

	public void setCalculatorId(Long calculatorId) {
		this.calculatorId = calculatorId;
	}

	public Long getBusinessPlaceCode() {
		return businessPlaceCode;
	}

	public void setBusinessPlaceCode(Long businessPlaceCode) {
		this.businessPlaceCode = businessPlaceCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public Integer getWriteType() {
		return writeType;
	}

	public void setWriteType(Integer writeType) {
		this.writeType = writeType;
	}

	public Integer getShouldWriteDays() {
		return shouldWriteDays;
	}

	public void setShouldWriteDays(Integer shouldWriteDays) {
		this.shouldWriteDays = shouldWriteDays;
	}

	public Integer getStandardWriteDays() {
		return standardWriteDays;
	}

	public void setStandardWriteDays(Integer standardWriteDays) {
		this.standardWriteDays = standardWriteDays;
	}

	public Integer getShouldCalDays() {
		return shouldCalDays;
	}

	public void setShouldCalDays(Integer shouldCalDays) {
		this.shouldCalDays = shouldCalDays;
	}

	public Long getSectUserType() {
		return sectUserType;
	}

	public void setSectUserType(Long sectUserType) {
		this.sectUserType = sectUserType;
	}

	public Integer getPunishDays() {
		return punishDays;
	}

	public void setPunishDays(Integer punishDays) {
		this.punishDays = punishDays;
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

	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	public BigDecimal getComplete() {
		return complete;
	}

	public void setComplete(BigDecimal complete) {
		this.complete = complete;
	}

	public List getWriteSectionIds() {
		return writeSectionIds;
	}

	public void setWriteSectionIds(List writeSectionIds) {
		this.writeSectionIds = writeSectionIds;
	}

	public List getBusinessPlaceCodes() {
		return businessPlaceCodes;
	}

	public void setBusinessPlaceCodes(List businessPlaceCodes) {
		this.businessPlaceCodes = businessPlaceCodes;
	}

	public BigDecimal getUncomplete() {
		return uncomplete;
	}

	public void setUncomplete(BigDecimal uncomplete) {
		this.uncomplete = uncomplete;
	}

	public BigDecimal getCalcComplete() {
		return calcComplete;
	}

	public void setCalcComplete(BigDecimal calcComplete) {
		this.calcComplete = calcComplete;
	}

	public BigDecimal getCalcUncomplete() {
		return calcUncomplete;
	}

	public void setCalcUncomplete(BigDecimal calcUncomplete) {
		this.calcUncomplete = calcUncomplete;
	}

	public Integer getUnknownNum() {
		return unknownNum;
	}

	public void setUnknownNum(Integer unknownNum) {
		this.unknownNum = unknownNum;
	}
}
