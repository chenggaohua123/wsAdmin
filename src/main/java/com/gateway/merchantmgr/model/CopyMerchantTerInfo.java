package com.gateway.merchantmgr.model;

public class CopyMerchantTerInfo {
	private int oldId;//源Id
	private String merNo;//源商户号
	private String terNo;//源终端
	private String newMerNo;//新商户号
	private String newTerNo;//新终端
	private int id;//新ID
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
	public String getNewMerNo() {
		return newMerNo;
	}
	public void setNewMerNo(String newMerNo) {
		this.newMerNo = newMerNo;
	}
	public String getNewTerNo() {
		return newTerNo;
	}
	public void setNewTerNo(String newTerNo) {
		this.newTerNo = newTerNo;
	}
	public int getOldId() {
		return oldId;
	}
	public void setOldId(int oldId) {
		this.oldId = oldId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
