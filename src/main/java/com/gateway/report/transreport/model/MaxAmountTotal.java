package com.gateway.report.transreport.model;

public class MaxAmountTotal {
	
	private String currencyName;
	private String maxAmountRisk;
	private double transAmount;
	private double totalAmount;
	private String transDate;
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	public String getMaxAmountRisk() {
		return maxAmountRisk;
	}
	public void setMaxAmountRisk(String maxAmountRisk) {
		this.maxAmountRisk = maxAmountRisk;
	}
	public double getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(double transAmount) {
		this.transAmount = transAmount;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	
}
