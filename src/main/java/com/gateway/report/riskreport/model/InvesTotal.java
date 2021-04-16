package com.gateway.report.riskreport.model;

import java.sql.Timestamp;



public class InvesTotal {
	private String merNo;
	private String terNo;
	private String invesType;
	private String type;
	private int invesCount;
	private int totalCount;
	private String invesRate;
	
	private Timestamp transDateStart;
	private Timestamp transDateEnd;
	
	
	public Timestamp getTransDateStart() {
		return transDateStart;
	}
	public void setTransDateStart(Timestamp transDateStart) {
		this.transDateStart = transDateStart;
	}
	public Timestamp getTransDateEnd() {
		return transDateEnd;
	}
	public void setTransDateEnd(Timestamp transDateEnd) {
		this.transDateEnd = transDateEnd;
	}
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public String getTerNo() {
		return terNo;
	}
	public void setTerNo(String terNo) {
		this.terNo = terNo;
	}
	public String getInvesType() {
		return invesType;
	}
	public void setInvesType(String invesType) {
		this.invesType = invesType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getInvesCount() {
		return invesCount;
	}
	public void setInvesCount(int invesCount) {
		this.invesCount = invesCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public String getInvesRate() {
		return invesRate;
	}
	public void setInvesRate(String invesRate) {
		this.invesRate = invesRate;
	} 
	
}
