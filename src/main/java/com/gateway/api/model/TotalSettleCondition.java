package com.gateway.api.model;

import java.util.ArrayList;
import java.util.List;

public class TotalSettleCondition extends BaseInfo{
	private String totalBy;
	private String merNo;
	private String terNo;
	private String batchNo;
	private String settleDateStart;
	private String settleDateEnd;
	private int pageNum = 1;
	private int pageSize = 20;
	private int total = 0;
	private List<SettleInfo> settleList = new ArrayList<SettleInfo>();
	
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getTotalBy() {
		return totalBy;
	}
	public void setTotalBy(String totalBy) {
		this.totalBy = totalBy;
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
	public String getSettleDateStart() {
		return settleDateStart;
	}
	public void setSettleDateStart(String settleDateStart) {
		this.settleDateStart = settleDateStart;
	}
	public String getSettleDateEnd() {
		return settleDateEnd;
	}
	public void setSettleDateEnd(String settleDateEnd) {
		this.settleDateEnd = settleDateEnd;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<SettleInfo> getSettleList() {
		return settleList;
	}
	public void setSettleList(List<SettleInfo> settleList) {
		this.settleList = settleList;
	}
	
}
