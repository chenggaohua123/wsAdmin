package com.gateway.merchantmgr.model;

import java.sql.Timestamp;

public class AgentInfo {
	private Integer id;
	private String agentNo;
	private String agentName;
	private String parentAgentNo;
	private int enabled;
	private String phoneNo;
	private String accountName;
	private String accountState;
	private String accountCity;
	private String accountContryCode;
	private String accountNo;
	private String accountAddress;
	private Timestamp regDate;
	private Timestamp activationDate;
	private Timestamp expDate;
	private String createBy;
	private Timestamp uDate;
	private String ucreateBy;
	
	private String sysSource;

	
	
	public String getSysSource() {
		return sysSource;
	}

	public void setSysSource(String sysSource) {
		this.sysSource = sysSource;
	}

	public Timestamp getuDate() {
		return uDate;
	}

	public void setuDate(Timestamp uDate) {
		this.uDate = uDate;
	}

	public String getUcreateBy() {
		return ucreateBy;
	}

	public void setUcreateBy(String ucreateBy) {
		this.ucreateBy = ucreateBy;
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getParentAgentNo() {
		return parentAgentNo;
	}

	public void setParentAgentNo(String parentAgentNo) {
		this.parentAgentNo = parentAgentNo;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountState() {
		return accountState;
	}

	public void setAccountState(String accountState) {
		this.accountState = accountState;
	}

	public String getAccountCity() {
		return accountCity;
	}

	public void setAccountCity(String accountCity) {
		this.accountCity = accountCity;
	}

	public String getAccountContryCode() {
		return accountContryCode;
	}

	public void setAccountContryCode(String accountContryCode) {
		this.accountContryCode = accountContryCode;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountAddress() {
		return accountAddress;
	}

	public void setAccountAddress(String accountAddress) {
		this.accountAddress = accountAddress;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public Timestamp getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Timestamp activationDate) {
		this.activationDate = activationDate;
	}

	public Timestamp getExpDate() {
		return expDate;
	}

	public void setExpDate(Timestamp expDate) {
		this.expDate = expDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

}
