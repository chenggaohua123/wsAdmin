package com.gateway.transmgr.model;

import java.math.BigDecimal;

public class TransCountModel {
	/** 币种 */
	private String currency;
	/** 总金额 */
	private BigDecimal countMoney;
	/** 成功总金额 */
	private BigDecimal countSuccessMoney;
	/** 统计币种字段名 */
	private String countCurrencyStr;
	/** 统计金额字段名 */
	private String countMoneyStr;
	/** 组装SQL */
	private String sql;
	/** 组装SQL条件 */
	private String where;
	
	
	
	public BigDecimal getCountSuccessMoney() {
		return countSuccessMoney;
	}
	public void setCountSuccessMoney(BigDecimal countSuccessMoney) {
		this.countSuccessMoney = countSuccessMoney;
	}
	public String getWhere(){
		return where;
	}
	public void setWhere(String where){
		this.where = where;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getCountMoney() {
		return countMoney;
	}
	public void setCountMoney(BigDecimal countMoney) {
		this.countMoney = countMoney;
	}
	public String getCountCurrencyStr() {
		return countCurrencyStr;
	}
	public void setCountCurrencyStr(String countCurrencyStr) {
		this.countCurrencyStr = countCurrencyStr;
	}
	public String getCountMoneyStr() {
		return countMoneyStr;
	}
	public void setCountMoneyStr(String countMoneyStr) {
		this.countMoneyStr = countMoneyStr;
	}
}

