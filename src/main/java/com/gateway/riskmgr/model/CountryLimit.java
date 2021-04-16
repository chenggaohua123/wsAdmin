package com.gateway.riskmgr.model;

import java.sql.Timestamp;

public class CountryLimit {
	private String merNo;
	private String countryNameEN;
	private String countryNameCN;
	private String countryNameSimple;
	private Timestamp createDate;
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public String getCountryNameEN() {
		return countryNameEN;
	}
	public void setCountryNameEN(String countryNameEN) {
		this.countryNameEN = countryNameEN;
	}
	public String getCountryNameCN() {
		return countryNameCN;
	}
	public void setCountryNameCN(String countryNameCN) {
		this.countryNameCN = countryNameCN;
	}
	public String getCountryNameSimple() {
		return countryNameSimple;
	}
	public void setCountryNameSimple(String countryNameSimple) {
		this.countryNameSimple = countryNameSimple;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	
}
