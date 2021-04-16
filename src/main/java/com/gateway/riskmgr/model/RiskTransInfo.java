package com.gateway.riskmgr.model;

import java.sql.Timestamp;

public class RiskTransInfo {
	private String merNo;
	private String terNo;
	private String tradeNo;
	private String orderNo;
	private String website;
	private String ruleId;
	private Timestamp doDate;
	private Timestamp doEndDate;
	private String doReason;
	private String doStatus;
	private String respCode;
	private String respMsg;
	private String reStatus;
	
	
	
	
	
	
	
	public String getReStatus() {
		return reStatus;
	}
	public void setReStatus(String reStatus) {
		this.reStatus = reStatus;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public Timestamp getDoEndDate() {
		return doEndDate;
	}
	public void setDoEndDate(Timestamp doEndDate) {
		this.doEndDate = doEndDate;
	}
	public String getTerNo() {
		return terNo;
	}
	public void setTerNo(String terNo) {
		this.terNo = terNo;
	}
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public Timestamp getDoDate() {
		return doDate;
	}
	public void setDoDate(Timestamp doDate) {
		this.doDate = doDate;
	}
	public String getDoReason() {
		return doReason;
	}
	public void setDoReason(String doReason) {
		this.doReason = doReason;
	}
	public String getDoStatus() {
		return doStatus;
	}
	public void setDoStatus(String doStatus) {
		this.doStatus = doStatus;
	}
	
	
}
