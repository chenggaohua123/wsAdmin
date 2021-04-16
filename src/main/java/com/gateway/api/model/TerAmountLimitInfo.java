package com.gateway.api.model;

import java.math.BigDecimal;

public class TerAmountLimitInfo {
	private String merNo;
	private String terNo;
	private BigDecimal cSingleTransAmountLimit;
	private BigDecimal cDayTransAmountLimit;
	private BigDecimal cMonthTransAmountLimit;
	private BigDecimal dSingleTransAmountLimit;
	private BigDecimal dDayTransAmountLimit;
	private BigDecimal dMonthTransAmountLimit;
	
	private BigDecimal hasUseCDayTransAmountLimit;
	private BigDecimal hasUseCMonthTransAmountLimit;
	private BigDecimal hasUseDDayTransAmountLimit;
	private BigDecimal hasUseDMonthTransAmountLimit;
	private int transCount;
	private String transDate;
	private String startTransDate;
	private String endTransDate;
	
	public int getTransCount() {
		return transCount;
	}
	public void setTransCount(int transCount) {
		this.transCount = transCount;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getStartTransDate() {
		return startTransDate;
	}
	public void setStartTransDate(String startTransDate) {
		this.startTransDate = startTransDate;
	}
	public String getEndTransDate() {
		return endTransDate;
	}
	public void setEndTransDate(String endTransDate) {
		this.endTransDate = endTransDate;
	}
	public BigDecimal getHasUseCDayTransAmountLimit() {
		return hasUseCDayTransAmountLimit;
	}
	public void setHasUseCDayTransAmountLimit(BigDecimal hasUseCDayTransAmountLimit) {
		this.hasUseCDayTransAmountLimit = hasUseCDayTransAmountLimit;
	}
	public BigDecimal getHasUseCMonthTransAmountLimit() {
		return hasUseCMonthTransAmountLimit;
	}
	public void setHasUseCMonthTransAmountLimit(
			BigDecimal hasUseCMonthTransAmountLimit) {
		this.hasUseCMonthTransAmountLimit = hasUseCMonthTransAmountLimit;
	}
	public BigDecimal getHasUseDDayTransAmountLimit() {
		return hasUseDDayTransAmountLimit;
	}
	public void setHasUseDDayTransAmountLimit(BigDecimal hasUseDDayTransAmountLimit) {
		this.hasUseDDayTransAmountLimit = hasUseDDayTransAmountLimit;
	}
	public BigDecimal getHasUseDMonthTransAmountLimit() {
		return hasUseDMonthTransAmountLimit;
	}
	public void setHasUseDMonthTransAmountLimit(
			BigDecimal hasUseDMonthTransAmountLimit) {
		this.hasUseDMonthTransAmountLimit = hasUseDMonthTransAmountLimit;
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
	public BigDecimal getcSingleTransAmountLimit() {
		return cSingleTransAmountLimit;
	}
	public void setcSingleTransAmountLimit(BigDecimal cSingleTransAmountLimit) {
		this.cSingleTransAmountLimit = cSingleTransAmountLimit;
	}
	public BigDecimal getcDayTransAmountLimit() {
		return cDayTransAmountLimit;
	}
	public void setcDayTransAmountLimit(BigDecimal cDayTransAmountLimit) {
		this.cDayTransAmountLimit = cDayTransAmountLimit;
	}
	public BigDecimal getcMonthTransAmountLimit() {
		return cMonthTransAmountLimit;
	}
	public void setcMonthTransAmountLimit(BigDecimal cMonthTransAmountLimit) {
		this.cMonthTransAmountLimit = cMonthTransAmountLimit;
	}
	public BigDecimal getdSingleTransAmountLimit() {
		return dSingleTransAmountLimit;
	}
	public void setdSingleTransAmountLimit(BigDecimal dSingleTransAmountLimit) {
		this.dSingleTransAmountLimit = dSingleTransAmountLimit;
	}
	public BigDecimal getdDayTransAmountLimit() {
		return dDayTransAmountLimit;
	}
	public void setdDayTransAmountLimit(BigDecimal dDayTransAmountLimit) {
		this.dDayTransAmountLimit = dDayTransAmountLimit;
	}
	public BigDecimal getdMonthTransAmountLimit() {
		return dMonthTransAmountLimit;
	}
	public void setdMonthTransAmountLimit(BigDecimal dMonthTransAmountLimit) {
		this.dMonthTransAmountLimit = dMonthTransAmountLimit;
	}
	
	
}
