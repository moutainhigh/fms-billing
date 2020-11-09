package org.fms.billing.common.webapp.domain;

import java.util.Date;

import com.riozenc.titanTool.annotation.TablePrimaryKey;
import com.riozenc.titanTool.mybatis.MybatisEntity;


//电价标准表
public class PriceStandardDomain implements MybatisEntity{
	@TablePrimaryKey
	private Long id;
	//电价类型ID
	private Long priceTypeId;
	//电价项目ID
	private Long priceItemId;
	//时段
	private Integer timeSeg;
	//电价
	private Double price;
	private Date createDate;
	private String operator;
	private Integer status;
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
	
	public Integer getTimeSeg() {
		return timeSeg;
	}
	public void setTimeSeg(Integer timeSeg) {
		this.timeSeg = timeSeg;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
