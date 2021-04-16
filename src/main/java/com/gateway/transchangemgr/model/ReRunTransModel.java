package com.gateway.transchangemgr.model;

public class ReRunTransModel {
	private int id;
	private String tradeNo;
	private String amount;
	private String currency;
	private String currencyId;
	private String respCode;
	private String respMsg;
	private String oldCurrencyId;
	private String isOrNotSubmitToBank;
	private String isRisk;
	
	
	
	
	
	public String getIsRisk() {
		return isRisk;
	}
	public void setIsRisk(String isRisk) {
		this.isRisk = isRisk;
	}
	public String getIsOrNotSubmitToBank() {
		return isOrNotSubmitToBank;
	}
	public void setIsOrNotSubmitToBank(String isOrNotSubmitToBank) {
		this.isOrNotSubmitToBank = isOrNotSubmitToBank;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOldCurrencyId() {
		return oldCurrencyId;
	}
	public void setOldCurrencyId(String oldCurrencyId) {
		this.oldCurrencyId = oldCurrencyId;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	
}
