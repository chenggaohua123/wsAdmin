package com.gateway.fraud.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class RuleProfileInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String proFileId;
	private String proFileName;
	private String status;
	private String createBy;
	private Timestamp createDate;
	
	private List<RulesInfo> ruleList;
	
	public List<RulesInfo> getRuleList() {
		return ruleList;
	}
	public void setRuleList(List<RulesInfo> ruleList) {
		this.ruleList = ruleList;
	}
	public String getProFileId() {
		return proFileId;
	}
	public void setProFileId(String proFileId) {
		this.proFileId = proFileId;
	}
	public String getProFileName() {
		return proFileName;
	}
	public void setProFileName(String proFileName) {
		this.proFileName = proFileName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
