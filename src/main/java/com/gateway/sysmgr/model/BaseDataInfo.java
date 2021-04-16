package com.gateway.sysmgr.model;

import java.sql.Timestamp;

public class BaseDataInfo {
	private int id;
	private String tableName;
	private String columnKeyName;
	private String columnVauleName;
	private String columnKey;
	private String columnvalue;
	private String remark;
	private String createBy;
	private Timestamp createDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnKeyName() {
		return columnKeyName;
	}
	public void setColumnKeyName(String columnKeyName) {
		this.columnKeyName = columnKeyName;
	}
	public String getColumnVauleName() {
		return columnVauleName;
	}
	public void setColumnVauleName(String columnVauleName) {
		this.columnVauleName = columnVauleName;
	}
	public String getColumnKey() {
		return columnKey;
	}
	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}
	public String getColumnvalue() {
		return columnvalue;
	}
	public void setColumnvalue(String columnvalue) {
		this.columnvalue = columnvalue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	
}
