package com.gateway.transmgr.model;

import java.sql.Timestamp;

public class TransCallbackInfo {

	private int id;
	private String tradeNo;
	private String sendEmail;
	private String emailModel;
	private Timestamp uploadDate;
	private String uploadBy;
	private Timestamp sendTime;
	private String sendStatus;
	private String merNo;
	private String terNo;
	private String orderNo;
	private String email;
	private Timestamp transDate;
	private String payWebSite;
	private String transRefund;
	private String transDishonor;
	private String transFrozen;
	private String status;//运单状态
	private String operationStatus;
	private String gatherTypes;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getSendEmail() {
		return sendEmail;
	}
	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}
	public String getEmailModel() {
		return emailModel;
	}
	public void setEmailModel(String emailModel) {
		this.emailModel = emailModel;
	}
	public Timestamp getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Timestamp uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getUploadBy() {
		return uploadBy;
	}
	public void setUploadBy(String uploadBy) {
		this.uploadBy = uploadBy;
	}
	public Timestamp getSendTime() {
		return sendTime;
	}
	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}
	public String getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Timestamp getTransDate() {
		return transDate;
	}
	public void setTransDate(Timestamp transDate) {
		this.transDate = transDate;
	}
	public String getPayWebSite() {
		return payWebSite;
	}
	public void setPayWebSite(String payWebSite) {
		this.payWebSite = payWebSite;
	}
	public String getTransRefund() {
		return transRefund;
	}
	public void setTransRefund(String transRefund) {
		this.transRefund = transRefund;
	}
	public String getTransDishonor() {
		return transDishonor;
	}
	public void setTransDishonor(String transDishonor) {
		this.transDishonor = transDishonor;
	}
	public String getTransFrozen() {
		return transFrozen;
	}
	public void setTransFrozen(String transFrozen) {
		this.transFrozen = transFrozen;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOperationStatus() {
		return operationStatus;
	}
	public void setOperationStatus(String operationStatus) {
		this.operationStatus = operationStatus;
	}
	public String getGatherTypes() {
		return gatherTypes;
	}
	public void setGatherTypes(String gatherTypes) {
		this.gatherTypes = gatherTypes;
	}
	
}
