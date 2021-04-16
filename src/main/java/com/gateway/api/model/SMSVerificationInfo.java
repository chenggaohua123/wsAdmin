package com.gateway.api.model;

import java.sql.Timestamp;

public class SMSVerificationInfo extends BaseInfo{
	private String centext;
	private String code;
	private String key;
	private Timestamp expDate;
	
	public String getCentext() {
		return centext;
	}
	public void setCentext(String centext) {
		this.centext = centext;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Timestamp getExpDate() {
		return expDate;
	}
	public void setExpDate(Timestamp expDate) {
		this.expDate = expDate;
	}
	
}
