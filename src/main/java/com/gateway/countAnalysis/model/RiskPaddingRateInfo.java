package com.gateway.countAnalysis.model;

public class RiskPaddingRateInfo {
	private String time;
	private String type;
	private String riskToSuccessCount;
	private String riskToSuccessToDisCount;
	private String riskToDisRate;
	private String tradeNo;
	
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRiskToSuccessCount() {
		return riskToSuccessCount;
	}
	public void setRiskToSuccessCount(String riskToSuccessCount) {
		this.riskToSuccessCount = riskToSuccessCount;
	}
	public String getRiskToSuccessToDisCount() {
		return riskToSuccessToDisCount;
	}
	public void setRiskToSuccessToDisCount(String riskToSuccessToDisCount) {
		this.riskToSuccessToDisCount = riskToSuccessToDisCount;
	}
	public String getRiskToDisRate() {
		return riskToDisRate;
	}
	public void setRiskToDisRate(String riskToDisRate) {
		this.riskToDisRate = riskToDisRate;
	}
	
	
}
