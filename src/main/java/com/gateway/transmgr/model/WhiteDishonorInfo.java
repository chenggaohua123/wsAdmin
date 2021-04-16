package com.gateway.transmgr.model;

import java.sql.Timestamp;

public class WhiteDishonorInfo {

	private int id;
	private String merNo;
	private String terNo;
	private String tradeNo;
	private Timestamp disCreatedDate;
	private Timestamp fakeCreatedDate;
	private String whiteIds;
	private String blackTexts;
	private String respCode;
	private String respMsg;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public Timestamp getDisCreatedDate() {
		return disCreatedDate;
	}
	public void setDisCreatedDate(Timestamp disCreatedDate) {
		this.disCreatedDate = disCreatedDate;
	}
	public Timestamp getFakeCreatedDate() {
		return fakeCreatedDate;
	}
	public void setFakeCreatedDate(Timestamp fakeCreatedDate) {
		this.fakeCreatedDate = fakeCreatedDate;
	}
	public String getWhiteIds() {
		return whiteIds;
	}
	public void setWhiteIds(String whiteIds) {
		this.whiteIds = whiteIds;
	}
	public String getBlackTexts() {
		return blackTexts;
	}
	public void setBlackTexts(String blackTexts) {
		this.blackTexts = blackTexts;
	}
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
	
}
