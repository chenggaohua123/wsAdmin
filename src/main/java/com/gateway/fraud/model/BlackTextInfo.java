package com.gateway.fraud.model;

import java.sql.Timestamp;

public class BlackTextInfo {
	private String blackId;
	private String blackText;
	private String blackType;
	private Timestamp createDate;
	private String createBy;
	private String merNo;
	
	
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public String getBlackId() {
		return blackId;
	}
	public void setBlackId(String blackId) {
		this.blackId = blackId;
	}
	public String getBlackText() {
		return blackText;
	}
	public void setBlackText(String blackText) {
		this.blackText = blackText;
	}
	public String getBlackType() {
		return blackType;
	}
	public void setBlackType(String blackType) {
		this.blackType = blackType;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	
}
