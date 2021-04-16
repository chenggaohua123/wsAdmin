package com.gateway.faffmgr.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class RiskCapitalPoolInfo {
	private String moneyDate;
	private String currency;
	private BigDecimal incomeAmount;
	private BigDecimal outAmount;
	private BigDecimal totalAmount;
	
	//for export
	private int type; //0 收入 1 支出
	private int cashType;//收入支出名目
	private Timestamp incomeDate;
	private BigDecimal amount;
	
	
	
	
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
	public int getCashType() {
		return cashType;
	}
	public void setCashType(int cashType) {
		this.cashType = cashType;
	}
	public Timestamp getIncomeDate() {
		return incomeDate;
	}
	public void setIncomeDate(Timestamp incomeDate) {
		this.incomeDate = incomeDate;
	}
	public String getMoneyDate() {
		return moneyDate;
	}
	public void setMoneyDate(String moneyDate) {
		this.moneyDate = moneyDate;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getIncomeAmount() {
		return incomeAmount;
	}
	public void setIncomeAmount(BigDecimal incomeAmount) {
		this.incomeAmount = incomeAmount;
	}
	public BigDecimal getOutAmount() {
		return outAmount;
	}
	public void setOutAmount(BigDecimal outAmount) {
		this.outAmount = outAmount;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
	
	
}
