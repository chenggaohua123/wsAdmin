package com.gateway.emailmgr.model;

import java.sql.Timestamp;

public class EmailSendInfo {

	private int id;
	private String tradeNo;
	private String ids;
	private String merNo;
	private String terNo;
	private String sendTypeId;
	private int status;
	private Timestamp sendDate;
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
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Timestamp getSendDate() {
		return sendDate;
	}
	public void setSendDate(Timestamp sendDate) {
		this.sendDate = sendDate;
	}
	
}
