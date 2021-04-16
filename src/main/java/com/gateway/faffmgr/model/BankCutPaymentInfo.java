package com.gateway.faffmgr.model;

import java.sql.Timestamp;

public class BankCutPaymentInfo {
	private String id;
	private String merNo;
	private String tradeNo;
	private String bankCurrency;
	private String bankTransAmount;
	private String refundAmount;
	private Timestamp refundDate;
	private String disAmount;
	private Timestamp disDate;
	private String bankSettleAmount;
	private String bankSettleCurrency;
	private Timestamp settleDate;
	private String createBy;
	private Timestamp createDate;
	private Timestamp transDate;
	
	
	public Timestamp getTransDate() {
		return transDate;
	}
	public void setTransDate(Timestamp transDate) {
		this.transDate = transDate;
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
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getBankCurrency() {
		return bankCurrency;
	}
	public void setBankCurrency(String bankCurrency) {
		this.bankCurrency = bankCurrency;
	}
	public String getBankTransAmount() {
		return bankTransAmount;
	}
	public void setBankTransAmount(String bankTransAmount) {
		this.bankTransAmount = bankTransAmount;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getDisAmount() {
		return disAmount;
	}
	public void setDisAmount(String disAmount) {
		this.disAmount = disAmount;
	}
	public String getBankSettleAmount() {
		return bankSettleAmount;
	}
	public void setBankSettleAmount(String bankSettleAmount) {
		this.bankSettleAmount = bankSettleAmount;
	}
	public String getBankSettleCurrency() {
		return bankSettleCurrency;
	}
	public void setBankSettleCurrency(String bankSettleCurrency) {
		this.bankSettleCurrency = bankSettleCurrency;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(Timestamp refundDate) {
		this.refundDate = refundDate;
	}
	public Timestamp getDisDate() {
		return disDate;
	}
	public void setDisDate(Timestamp disDate) {
		this.disDate = disDate;
	}
	public Timestamp getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(Timestamp settleDate) {
		this.settleDate = settleDate;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	
	
}	
