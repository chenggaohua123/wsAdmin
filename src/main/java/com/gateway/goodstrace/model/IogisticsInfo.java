package com.gateway.goodstrace.model;

import java.sql.Timestamp;

public class IogisticsInfo {
	private String id;
	private String iogistics;
	private String name;
	private String iogisticsUrl;
	private String createby;
	private Timestamp createDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIogistics() {
		return iogistics;
	}
	public void setIogistics(String iogistics) {
		this.iogistics = iogistics;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIogisticsUrl() {
		return iogisticsUrl;
	}
	public void setIogisticsUrl(String iogisticsUrl) {
		this.iogisticsUrl = iogisticsUrl;
	}
	public String getCreateby() {
		return createby;
	}
	public void setCreateby(String createby) {
		this.createby = createby;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	
	
	
}
