package com.gateway.brandProduct.model;
/**
 * 
 * 产品和品牌实体类
 * */
public class BrandProductInfo {
	private String id;
	//产品和品牌名称
	private String bpname;
	//产品和品牌值
	private String bpValue;
	//类型
	private String type;
	private String createBy;
	private String createdate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBpname() {
		return bpname;
	}
	public void setBpname(String bpname) {
		this.bpname = bpname;
	}
	public String getBpValue() {
		return bpValue;
	}
	public void setBpValue(String bpValue) {
		this.bpValue = bpValue;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	
	
}
