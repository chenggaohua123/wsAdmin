package com.gateway.api.model;

import java.util.ArrayList;
import java.util.List;

public class TotalTransInfoCondtion extends BaseInfo{
	private String totalBy;
	private String merNo;
	private String terNo;
	private String batchNo;
	private String transDateStart;
	private String transDateEnd;
	private int pageNum = 1;
	private int pageSize = 20;
	private int total = 0;
	private List<TotalTransInfo> transList = new ArrayList<TotalTransInfo>();

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

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getTransDateStart() {
		return transDateStart;
	}

	public void setTransDateStart(String transDateStart) {
		this.transDateStart = transDateStart;
	}

	public String getTransDateEnd() {
		return transDateEnd;
	}

	public void setTransDateEnd(String transDateEnd) {
		this.transDateEnd = transDateEnd;
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

	public List<TotalTransInfo> getTransList() {
		return transList;
	}

	public void setTransList(List<TotalTransInfo> transList) {
		this.transList = transList;
	}
	
}
