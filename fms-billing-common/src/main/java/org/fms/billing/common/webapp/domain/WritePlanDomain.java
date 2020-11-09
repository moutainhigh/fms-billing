package org.fms.billing.common.webapp.domain;

import java.util.Date;

import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;

//抄表计划
public class WritePlanDomain implements MybatisEntity {
	@TablePrimaryKey
	private Long id;
	// 抄表区段
	private String writeSectNo;
	// 抄表区段名称
	private String writeSectName;
	// 电费年月
	private String mon;
	// 当月次数
	private Double monSn;
	// 抄表员编号
	private Long writorId;
	// 计算人
	private Long calculatorId;
	// 抄表日期
	private Date writeDate;
	// 计算日期
	private Date computeDate;
	// 生成应收日期
	private Date confirmDate;
	// 抄表周期
	private Integer writeSectType;
	// 抄表方式
	private Integer writeType;
	// 区段用户类型
	private Integer sectUserType;
	// 应抄表总数
	private Integer sTNumber;
	// 实抄表总数
	private Integer fTNumber;
	// 应抄有功表数
	private Integer sYNumber;
	// 实抄有功表数
	private Integer fYNumber;
	// 估抄有功表数
	private Integer eYNumber;
	// 应抄无功表数
	private Integer sWNumber;
	// 实抄无功表数
	private Integer fWNumber;
	// 估抄无功表数
	private Integer eWNumber;
	// 划零有功表数
	private Integer zYNumber;
	// 划零无功表数
	private Integer zWNumber;
	// 异常表数
	private Integer abnormalNumber;
	// 初始化表数
	private Integer iNumber;
	// 已抄未算表数
	private Integer rNumber;
	// 已算表数
	private Integer cNumber;
	// 已发行表数
	private Integer aNumber;
	// 备注
	private String remark;
	// 抄表区段状态
	private Integer status;

	// 查询相关

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

	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	public Double getMonSn() {
		return monSn;
	}

	public void setMonSn(Double monSn) {
		this.monSn = monSn;
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

	public Date getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}

	public Date getComputeDate() {
		return computeDate;
	}

	public void setComputeDate(Date computeDate) {
		this.computeDate = computeDate;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public Integer getWriteSectType() {
		return writeSectType;
	}

	public void setWriteSectType(Integer writeSectType) {
		this.writeSectType = writeSectType;
	}

	public Integer getWriteType() {
		return writeType;
	}

	public void setWriteType(Integer writeType) {
		this.writeType = writeType;
	}

	public Integer getSectUserType() {
		return sectUserType;
	}

	public void setSectUserType(Integer sectUserType) {
		this.sectUserType = sectUserType;
	}

	public Integer getsTNumber() {
		return sTNumber;
	}

	public void setsTNumber(Integer sTNumber) {
		this.sTNumber = sTNumber;
	}

	public Integer getfTNumber() {
		return fTNumber;
	}

	public void setfTNumber(Integer fTNumber) {
		this.fTNumber = fTNumber;
	}

	public Integer getsYNumber() {
		return sYNumber;
	}

	public void setsYNumber(Integer sYNumber) {
		this.sYNumber = sYNumber;
	}

	public Integer getfYNumber() {
		return fYNumber;
	}

	public void setfYNumber(Integer fYNumber) {
		this.fYNumber = fYNumber;
	}

	public Integer geteYNumber() {
		return eYNumber;
	}

	public void seteYNumber(Integer eYNumber) {
		this.eYNumber = eYNumber;
	}

	public Integer getsWNumber() {
		return sWNumber;
	}

	public void setsWNumber(Integer sWNumber) {
		this.sWNumber = sWNumber;
	}

	public Integer getfWNumber() {
		return fWNumber;
	}

	public void setfWNumber(Integer fWNumber) {
		this.fWNumber = fWNumber;
	}

	public Integer geteWNumber() {
		return eWNumber;
	}

	public void seteWNumber(Integer eWNumber) {
		this.eWNumber = eWNumber;
	}

	public Integer getzYNumber() {
		return zYNumber;
	}

	public void setzYNumber(Integer zYNumber) {
		this.zYNumber = zYNumber;
	}

	public Integer getzWNumber() {
		return zWNumber;
	}

	public void setzWNumber(Integer zWNumber) {
		this.zWNumber = zWNumber;
	}

	public Integer getAbnormalNumber() {
		return abnormalNumber;
	}

	public void setAbnormalNumber(Integer abnormalNumber) {
		this.abnormalNumber = abnormalNumber;
	}

	public Integer getiNumber() {
		return iNumber;
	}

	public void setiNumber(Integer iNumber) {
		this.iNumber = iNumber;
	}

	public Integer getrNumber() {
		return rNumber;
	}

	public void setrNumber(Integer rNumber) {
		this.rNumber = rNumber;
	}

	public Integer getcNumber() {
		return cNumber;
	}

	public void setcNumber(Integer cNumber) {
		this.cNumber = cNumber;
	}

	public Integer getaNumber() {
		return aNumber;
	}

	public void setaNumber(Integer aNumber) {
		this.aNumber = aNumber;
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
