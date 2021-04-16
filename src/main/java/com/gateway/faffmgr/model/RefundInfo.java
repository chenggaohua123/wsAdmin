package com.gateway.faffmgr.model;

import java.sql.Timestamp;

public class RefundInfo {
	private String tradeNo;
	private String merNo;
	private Timestamp transDate;
	private String bankTransCurrency;
	private String bankTransAmount;
	private String refundCurrency;
	private String refundAmount;
	private Timestamp refundDate;
	private String operateBy;
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public String getBankTransCurrency() {
		return bankTransCurrency;
	}
	public void setBankTransCurrency(String bankTransCurrency) {
		this.bankTransCurrency = bankTransCurrency;
	}
	public String getBankTransAmount() {
		return bankTransAmount;
	}
	public void setBankTransAmount(String bankTransAmount) {
		this.bankTransAmount = bankTransAmount;
	}
	public String getRefundCurrency() {
		return refundCurrency;
	}
	public void setRefundCurrency(String refundCurrency) {
		this.refundCurrency = refundCurrency;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getOperateBy() {
		return operateBy;
	}
	public void setOperateBy(String operateBy) {
		this.operateBy = operateBy;
	}
	public Timestamp getTransDate() {
		return transDate;
	}
	public void setTransDate(Timestamp transDate) {
		this.transDate = transDate;
	}
	public Timestamp getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(Timestamp refundDate) {
		this.refundDate = refundDate;
	}
	
	
}	
