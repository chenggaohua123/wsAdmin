package com.gateway.suspicious.model;

import java.util.List;

public class SuspiciousRuleOrderInfo {
	private int count;
	private String susType;
	private String susOrderId;
	private String createDate;
	private String merNo;
	private String terNo;
	private String ruleIds;
	private String tradeNo;
	private List<SuspicioustTransInfo> list;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getSusType() {
		return susType;
	}
	public void setSusType(String susType) {
		this.susType = susType;
	}
	public String getSusOrderId() {
		return susOrderId;
	}
	public void setSusOrderId(String susOrderId) {
		this.susOrderId = susOrderId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public List<SuspicioustTransInfo> getList() {
		return list;
	}
	public void setList(List<SuspicioustTransInfo> list) {
		this.list = list;
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
	public String getRuleIds() {
		return ruleIds;
	}
	public void setRuleIds(String ruleIds) {
		this.ruleIds = ruleIds;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
}
