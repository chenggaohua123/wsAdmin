package com.gateway.settlemgr.model;

import java.math.BigDecimal;

public class MerchantAccount {
	private String id;
	private String merNo;
	private String terNo;
	private String currency;
	private BigDecimal totalAmount;
	private BigDecimal cashAmount;
	private BigDecimal bondAmount;
	private BigDecimal bondCashAmount;
	private BigDecimal frozenAmount;
	private BigDecimal bondFrozenAmount;
	private int status;
	private int accountType;
	/**商户渠道 在字典管理中设置*/
	private int merchantChannel;
	
	public int getMerchantChannel() {
		return merchantChannel;
	}
	public void setMerchantChannel(int merchantChannel) {
		this.merchantChannel = merchantChannel;
	}
	public BigDecimal getBondFrozenAmount() {
		return bondFrozenAmount;
	}
	public void setBondFrozenAmount(BigDecimal bondFrozenAmount) {
		this.bondFrozenAmount = bondFrozenAmount;
	}
	public int getAccountType() {
		return accountType;
	}
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getCashAmount() {
		return cashAmount;
	}
	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}
	public BigDecimal getBondAmount() {
		return bondAmount;
	}
	public void setBondAmount(BigDecimal bondAmount) {
		this.bondAmount = bondAmount;
	}
	public BigDecimal getBondCashAmount() {
		return bondCashAmount;
	}
	public void setBondCashAmount(BigDecimal bondCashAmount) {
		this.bondCashAmount = bondCashAmount;
	}
	public BigDecimal getFrozenAmount() {
		return frozenAmount;
	}
	public void setFrozenAmount(BigDecimal frozenAmount) {
		this.frozenAmount = frozenAmount;
	}
	
	
	
}
