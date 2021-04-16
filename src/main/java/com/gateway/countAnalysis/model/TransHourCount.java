package com.gateway.countAnalysis.model;

public class TransHourCount {
	private String time;
	private String finishCount="0";
	private String currency="CNY";
	private String totalAmount="0.00";
	private String successCount="0";
	private String successAmount="0.00";
	private String dupCount="0";
	private String failCount="0";
	private String riskCount="0";
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getFinishCount() {
		return finishCount;
	}
	public void setFinishCount(String finishCount) {
		this.finishCount = finishCount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(String successCount) {
		this.successCount = successCount;
	}
	public String getSuccessAmount() {
		return successAmount;
	}
	public void setSuccessAmount(String successAmount) {
		this.successAmount = successAmount;
	}
	public String getDupCount() {
		return dupCount;
	}
	public void setDupCount(String dupCount) {
		this.dupCount = dupCount;
	}
	public String getFailCount() {
		return failCount;
	}
	public void setFailCount(String failCount) {
		this.failCount = failCount;
	}
	public String getRiskCount() {
		return riskCount;
	}
	public void setRiskCount(String riskCount) {
		this.riskCount = riskCount;
	}
	
	
	
}
