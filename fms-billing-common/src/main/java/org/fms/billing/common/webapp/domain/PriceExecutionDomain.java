/**
 * Author : czy
 * Date : 2019年6月24日 上午8:50:14
 * Title : com.riozenc.billing.webapp.domain.PriceExecutionDomain.java
 *
**/
package org.fms.billing.common.webapp.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.fms.billing.common.webapp.entity.ManagerParamEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceExecutionDomain extends ManagerParamEntity implements MybatisEntity {
	@TablePrimaryKey
	private Long id;// ID ID bigint TRUE FALSE TRUE
	private Long priceTypeId;// 电价类型ID PRICE_TYPE_ID bigint FALSE FALSE FALSE
	private Long priceItemId;// 电价项目ID PRICE_ITEM_ID bigint FALSE FALSE FALSE
	private Long priceVersionId;// 版本号 VERSION_ID bigint FALSE FALSE FALSE
	private Byte timeSeg;// 时段 TIME_SEG smallint FALSE FALSE FALSE
	private BigDecimal price;// 电价 PRICE decimal(9,7) 9 7 FALSE FALSE FALSE
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createDate;// 创建日期 CREATE_DATE datetime FALSE FALSE FALSE
	private String operator;// 操作人 OPERATOR varchar(32) 32 FALSE FALSE FALSE
	private List<Long> ids;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPriceTypeId() {
		return priceTypeId;
	}

	public void setPriceTypeId(Long priceTypeId) {
		this.priceTypeId = priceTypeId;
	}

	public Long getPriceItemId() {
		return priceItemId;
	}

	public void setPriceItemId(Long priceItemId) {
		this.priceItemId = priceItemId;
	}

	public Long getPriceVersionId() {
		return priceVersionId;
	}

	public void setPriceVersionId(Long priceVersionId) {
		this.priceVersionId = priceVersionId;
	}

	public Byte getTimeSeg() {
		return timeSeg;
	}

	public void setTimeSeg(Byte timeSeg) {
		this.timeSeg = timeSeg;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}


}
