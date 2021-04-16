package com.gateway.warnmgr.model;

import java.sql.Timestamp;


public class MerchantWarnInfo {
	private String merNo;
	private String bankCurrency;
	private int dateCount;
	private int transDateCount;
	private Timestamp lastTransDate;
	private int successCount;
	private double successAmount;
	private int type;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public String getBankCurrency() {
		return bankCurrency;
	}
	public void setBankCurrency(String bankCurrency) {
		this.bankCurrency = bankCurrency;
	}
	public int getDateCount() {
		return dateCount;
	}
	public void setDateCount(int dateCount) {
		this.dateCount = dateCount;
	}
	public int getTransDateCount() {
		return transDateCount;
	}
	public void setTransDateCount(int transDateCount) {
		this.transDateCount = transDateCount;
	}
	public Timestamp getLastTransDate() {
		return lastTransDate;
	}
	public void setLastTransDate(Timestamp lastTransDate) {
		this.lastTransDate = lastTransDate;
	}
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	public double getSuccessAmount() {
		return successAmount;
	}
	public void setSuccessAmount(double successAmount) {
		this.successAmount = successAmount;
	}
	
}
