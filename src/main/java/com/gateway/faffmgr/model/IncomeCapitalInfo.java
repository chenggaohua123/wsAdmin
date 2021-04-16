package com.gateway.faffmgr.model;

import java.math.BigDecimal;

public class IncomeCapitalInfo {
	private BigDecimal incomeTotal;
	private String currency;
	private int type;
	public BigDecimal getIncomeTotal() {
		return incomeTotal;
	}
	public void setIncomeTotal(BigDecimal incomeTotal) {
		this.incomeTotal = incomeTotal;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
