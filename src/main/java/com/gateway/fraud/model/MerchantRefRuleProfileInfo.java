package com.gateway.fraud.model;

import java.sql.Timestamp;

public class MerchantRefRuleProfileInfo {
	
	private String id;
	private String merNo;
	private String terNo;
	private String createBy;
	private Timestamp createDate;
	private String status;
	private String proFileName;
	private String proFileId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProFileName() {
		return proFileName;
	}

	public void setProFileName(String proFileName) {
		this.proFileName = proFileName;
	}

	public String getProFileId() {
		return proFileId;
	}

	public void setProFileId(String proFileId) {
		this.proFileId = proFileId;
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
	

}
