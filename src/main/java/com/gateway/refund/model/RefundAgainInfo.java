package com.gateway.refund.model;

import java.math.BigDecimal;

public class RefundAgainInfo {
	private int id;
	private String tradeNo;
	private String tradeNewNo;
	private String currency;
	private String refundAmount;
	private String transAmount;
	private BigDecimal bankTransAmount;
	private BigDecimal bankRefundTransAmount;
	
	
	
	public BigDecimal getBankTransAmount() {
		return bankTransAmount;
	}
	public void setBankTransAmount(BigDecimal bankTransAmount) {
		this.bankTransAmount = bankTransAmount;
	}
	public BigDecimal getBankRefundTransAmount() {
		return bankRefundTransAmount;
	}
	public void setBankRefundTransAmount(BigDecimal bankRefundTransAmount) {
		this.bankRefundTransAmount = bankRefundTransAmount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getTradeNewNo() {
		return tradeNewNo;
	}
	public void setTradeNewNo(String tradeNewNo) {
		this.tradeNewNo = tradeNewNo;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}
	
	
}
