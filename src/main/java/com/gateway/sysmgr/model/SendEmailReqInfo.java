package com.gateway.sysmgr.model;

public class SendEmailReqInfo {
	private String id;
	private String tradeNo;
	private String merNo;
	private String terNo;
	private String sendTypeId;
	private String status;
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
	public String getSendTypeId() {
		return sendTypeId;
	}
	public void setSendTypeId(String sendTypeId) {
		this.sendTypeId = sendTypeId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
