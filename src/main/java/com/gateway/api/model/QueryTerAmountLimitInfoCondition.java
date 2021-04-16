package com.gateway.api.model;

import java.util.ArrayList;
import java.util.List;

public class QueryTerAmountLimitInfoCondition extends BaseInfo{
	private String merNo;
	private String terNo;
	
	private List<TerAmountLimitInfo> list = new ArrayList<TerAmountLimitInfo>();
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
	public List<TerAmountLimitInfo> getList() {
		return list;
	}
	public void setList(List<TerAmountLimitInfo> list) {
		this.list = list;
	}
	
	
}
