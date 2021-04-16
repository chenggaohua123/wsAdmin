package com.gateway.sellmgr.model;

public class SellRefSellsInfo {
	private int id;
	private String sellMgr;
	private String sellMgrRealName;
	private String[] sells;
	private String sellsRealName;
	private String createDate;
	private String createBy;
	private String lastModifyBy;
	private String lastModifyDate;
	private String sell;
	
	
	
	public String getSell() {
		return sell;
	}
	public void setSell(String sell) {
		this.sell = sell;
	}
	public String getSellMgrRealName() {
		return sellMgrRealName;
	}
	public void setSellMgrRealName(String sellMgrRealName) {
		this.sellMgrRealName = sellMgrRealName;
	}
	public String getSellsRealName() {
		return sellsRealName;
	}
	public void setSellsRealName(String sellsRealName) {
		this.sellsRealName = sellsRealName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getLastModifyBy() {
		return lastModifyBy;
	}
	public void setLastModifyBy(String lastModifyBy) {
		this.lastModifyBy = lastModifyBy;
	}
	public String getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(String lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSellMgr() {
		return sellMgr;
	}
	public void setSellMgr(String sellMgr) {
		this.sellMgr = sellMgr;
	}
	public String[] getSells() {
		return sells;
	}
	public void setSells(String[] sells) {
		this.sells = sells;
	}
	
	
}
