package com.gateway.sysmgr.model;

public class TransInfo {
	private String tradeNo;
	private String transDate;
	private String merNo;
	private String terNo;
	private String merBusCurrency;
	private String merTransAmount;
	private String orderNo;
	private String ipAddress;
	private String email;
	private String respCode;
	private String respMsg;
	private String bankRespMsg;
	private String currencyName;
	private String bankName;
	
	
	
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public String getBankRespMsg() {
		return bankRespMsg;
	}
	public void setBankRespMsg(String bankRespMsg) {
		this.bankRespMsg = bankRespMsg;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
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
	public String getMerBusCurrency() {
		return merBusCurrency;
	}
	public void setMerBusCurrency(String merBusCurrency) {
		this.merBusCurrency = merBusCurrency;
	}
	public String getMerTransAmount() {
		return merTransAmount;
	}
	public void setMerTransAmount(String merTransAmount) {
		this.merTransAmount = merTransAmount;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
}
