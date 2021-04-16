package com.gateway.faffmgr.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ReceiveIncomeInfo {
	private int id;
	private String bankName;
	private Integer type;
	private String incomeDesc;
	private BigDecimal amount;
	private String currency;
	private String incomeDate;
	private String remark;
	private Timestamp createDate;
	private String createBy;
	private String lastModifyBy;
	private Timestamp lastmodifyDate;
	private String currencyId;
	private String currencyName;
	
	
	
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getIncomeDesc() {
		return incomeDesc;
	}
	public void setIncomeDesc(String incomeDesc) {
		this.incomeDesc = incomeDesc;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getIncomeDate() {
		return incomeDate;
	}
	public void setIncomeDate(String incomeDate) {
		this.incomeDate = incomeDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getLastModifyBy() {
		return lastModifyBy;
	}
	public void setLastModifyBy(String lastModifyBy) {
		this.lastModifyBy = lastModifyBy;
	}
	public Timestamp getLastmodifyDate() {
		return lastmodifyDate;
	}
	public void setLastmodifyDate(Timestamp lastmodifyDate) {
		this.lastmodifyDate = lastmodifyDate;
	}
}
