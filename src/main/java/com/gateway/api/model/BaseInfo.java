package com.gateway.api.model;


public class BaseInfo {
	private String realName = "";
	private String userName ="";
	private String phoneNo ="";
	private String passWord ="";
	private String respCode="";
	private String respDesc="";
	private String shaString = "";
	
	public String getShaString() {
		return shaString;
	}
	public void setShaString(String shaString) {
		this.shaString = shaString;
	}
	public String getRealName() {
		
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	
	
}
