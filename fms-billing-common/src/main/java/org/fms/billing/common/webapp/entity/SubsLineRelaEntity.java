/**
 *    Auth:riozenc
 *    Date:2019年3月8日 下午3:22:58
 *    Title:com.riozenc.cim.webapp.domain.CustomerDomain.java
 **/
package org.fms.billing.common.webapp.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.mybatis.MybatisEntity;

/**
 * 变电站线路关系	SUBS_LINE_REL
 * 
 * @author riozenc
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubsLineRelaEntity extends ManagerParamEntity implements MybatisEntity {

	private Long beginSubsId;// 首端变电站ID ID bigint TRUE FALSE TRUE
	private Long endSubsId;// 末端变电站ID ID bigint TRUE FALSE TRUE
	private Long lineId; //线路ID
	private Date createDate;// 创建日期 CREATE_DATE datetime FALSE FALSE FALSE
	private Long operator; //操作人
	private Byte status; //运行状态
	private List<Long> beginSubsIds;

	private String lineCode;
	private String lineName;

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

	public List<Long> getBeginSubsIds() {
		return beginSubsIds;
	}

	public void setBeginSubsIds(List<Long> beginSubsIds) {
		this.beginSubsIds = beginSubsIds;
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
	public Long getLineId() {
		return lineId;
	}
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getOperator() {
		return operator;
	}
	public void setOperator(Long operator) {
		this.operator = operator;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}



}
