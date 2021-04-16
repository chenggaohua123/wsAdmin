package com.gateway.api.model;

public class UserForOther {
	private String erroMsg;
	private String erroCode;
	private String userName;
	private String password;
	private int systemId;
	private String merNos;
	public String getErroMsg() {
		return erroMsg;
	}
	public void setErroMsg(String erroMsg) {
		this.erroMsg = erroMsg;
	}
	public String getErroCode() {
		return erroCode;
	}
	public void setErroCode(String erroCode) {
		this.erroCode = erroCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getSystemId() {
		return systemId;
	}
	public void setSystemId(int systemId) {
		this.systemId = systemId;
	}
	public String getMerNos() {
		return merNos;
	}
	public void setMerNos(String merNos) {
		this.merNos = merNos;
	}
	
	
}
