package com.gateway.transmgr.model;

public class TransRecordInfo {
	private String id;
	private String tradeNo;
	private String merNo;
	private String terNo;
	private String orderNo;
	private String amount;
	private String currencyCode;
	private String currencyId;
	private String language;
	private String returnURL;
	private String transPortProtocol;
	private String clientIP;
	private String merURL;
	private String merNotifyURL;
	private int merNotifyStatus;//0未通知 1通知成功 2通知失败
	private int merNotifyCount;//通知次数
	private String submitURL;
	private String enterTime;
	private String description;
	private String respCode;
	private String respMsg;
	
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
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
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getReturnURL() {
		return returnURL;
	}
	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}
	public String getTransPortProtocol() {
		return transPortProtocol;
	}
	public void setTransPortProtocol(String transPortProtocol) {
		this.transPortProtocol = transPortProtocol;
	}
	public String getClientIP() {
		return clientIP;
	}
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
	public String getMerURL() {
		return merURL;
	}
	public void setMerURL(String merURL) {
		this.merURL = merURL;
	}
	public String getSubmitURL() {
		return submitURL;
	}
	public void setSubmitURL(String submitURL) {
		this.submitURL = submitURL;
	}
	public String getEnterTime() {
		return enterTime;
	}
	public void setEnterTime(String enterTime) {
		this.enterTime = enterTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMerNotifyURL() {
		return merNotifyURL;
	}
	public void setMerNotifyURL(String merNotifyURL) {
		this.merNotifyURL = merNotifyURL;
	}
	public int getMerNotifyStatus() {
		return merNotifyStatus;
	}
	public void setMerNotifyStatus(int merNotifyStatus) {
		this.merNotifyStatus = merNotifyStatus;
	}
	public int getMerNotifyCount() {
		return merNotifyCount;
	}
	public void setMerNotifyCount(int merNotifyCount) {
		this.merNotifyCount = merNotifyCount;
	}
	
}
