package com.gateway.api.model;

import java.math.BigDecimal;

public class BusInfo {
	private String IDCardNo ="";
	private String comName = "";
	private String comAdress ="";
	private String busTimeStart="";
	private String busTimeEnd="";
	private String email = "";
	private String merchantType="";
	private String industry="";
	private String province="";
	private String city="";
	private BigDecimal maxTransAmount=new BigDecimal("0");
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getIDCardNo() {
		return IDCardNo;
	}
	public void setIDCardNo(String iDCardNo) {
		IDCardNo = iDCardNo;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
	public String getComAdress() {
		return comAdress;
	}
	public void setComAdress(String comAdress) {
		this.comAdress = comAdress;
	}
	public String getBusTimeStart() {
		return busTimeStart;
	}
	public void setBusTimeStart(String busTimeStart) {
		this.busTimeStart = busTimeStart;
	}
	public String getBusTimeEnd() {
		return busTimeEnd;
	}
	public void setBusTimeEnd(String busTimeEnd) {
		this.busTimeEnd = busTimeEnd;
	}

	public BigDecimal getMaxTransAmount() {
		return maxTransAmount;
	}
	public void setMaxTransAmount(BigDecimal maxTransAmount) {
		this.maxTransAmount = maxTransAmount;
	}
	
	
}
