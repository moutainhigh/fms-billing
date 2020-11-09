/**
 *    Auth:riozenc
 *    Date:2019年3月8日 下午3:22:58
 *    Title:com.riozenc.cim.webapp.domain.CustomerDomain.java
 **/
package org.fms.billing.common.webapp.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.mybatis.MybatisEntity;

/**
 * 变压器 TRANSFORMER_INFO
 *
 * @author riozenc
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransformerLineRelEntity extends ManagerParamEntity implements MybatisEntity {

	private Long id;// ID ID bigint TRUE FALSE TRUE
	private Long lineId;// 线路ID ID bigint TRUE FALSE TRUE
	private Long transformerId;
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date createDate; // 出厂日期
	private String remark; // 备注
	private Byte status; // 状态
	private Long operator;
	/*---------------------------------------------------------------------*/
	private String lineName; // 线路名称
	private String lineCode;
	private Long meterId;
	private List<Long> transformIds;
	private List<Long> lineIds;

	public List<Long> getLineIds() {
		return lineIds;
	}

	public void setLineIds(List<Long> lineIds) {
		this.lineIds = lineIds;
	}

	public List<Long> getTransformIds() {
		return transformIds;
	}

	public void setTransformIds(List<Long> transformIds) {
		this.transformIds = transformIds;
	}

	public Long getMeterId() {
		return meterId;
	}

	public void setMeterId(Long meterId) {
		this.meterId = meterId;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLineId() {
		return lineId;
	}
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}
	public Long getTransformerId() {
		return transformerId;
	}
	public void setTransformerId(Long transformerId) {
		this.transformerId = transformerId;
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
	public Long getOperator() {
		return operator;
	}
	public void setOperator(Long operator) {
		this.operator = operator;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getLineCode() {
		return lineCode;
	}
	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}



}
