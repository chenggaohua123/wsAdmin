package com.gateway.complaint.model;

import java.sql.Timestamp;


/** 调查单 */
public class ComplaintType{
	private Integer id;
	private String cKey;
	private String cValue;
	/** 是否有效 */
	private int enabled;
	/** 创建人 */
	private String createdBy;
	/** 创建时间 */
	private Timestamp createdDate;
	/** 修改人 */
	private String lastUpdateBy;
	/** 修改时间 */
	private Timestamp lastUpdateDate;
	/** 0:调查单，1，拒付单，2投诉单 */
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getcKey() {
		return cKey;
	}
	public void setcKey(String cKey) {
		this.cKey = cKey;
	}
	public String getcValue() {
		return cValue;
	}
	public void setcValue(String cValue) {
		this.cValue = cValue;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public Timestamp getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Timestamp lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
}
