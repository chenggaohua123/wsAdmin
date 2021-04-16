package com.gateway.faffmgr.model;


public class TransCheckInfo {
	private String id;
	private String tradeNo;
	private String bankSettleAmount;
	private String bankSettleCurrency;
	private String bankFee;
	private String settleDate;
	private String settleType;
	private String createBy;
	private String createDate;
	
	
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
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
	public String getBankFee() {
		return bankFee;
	}
	public void setBankFee(String bankFee) {
		this.bankFee = bankFee;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public String getSettleType() {
		return settleType;
	}
	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}
	
	
	
}
