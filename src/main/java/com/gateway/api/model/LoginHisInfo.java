package com.gateway.api.model;

public class LoginHisInfo {
	private String lastLoginTime = "";
	private String lastLoginIP="";
	private String lastLoginAddress="";
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastLoginIP() {
		return lastLoginIP;
	}
	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}
	public String getLastLoginAddress() {
		return lastLoginAddress;
	}
	public void setLastLoginAddress(String lastLoginAddress) {
		this.lastLoginAddress = lastLoginAddress;
	}

	
	
	
}
