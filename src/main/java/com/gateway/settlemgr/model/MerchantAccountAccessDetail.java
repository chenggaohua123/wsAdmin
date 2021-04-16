package com.gateway.settlemgr.model;

public class MerchantAccountAccessDetail {
	private String id;
	private String tradeNo;
	private String accessId;
	private String batchNo;
	private String createDate;
	private String busAmount;
	private String busCurrency;
	private String settleAmount;
	private String settleCurrency;
	private String respCode;
	private String transDate;
	private String merForAmount;
	private String bondAmount;
	private String bondBatchNo;
	
	
	
	
	public String getBondBatchNo() {
		return bondBatchNo;
	}
	public void setBondBatchNo(String bondBatchNo) {
		this.bondBatchNo = bondBatchNo;
	}
	public String getMerForAmount() {
		return merForAmount;
	}
	public void setMerForAmount(String merForAmount) {
		this.merForAmount = merForAmount;
	}
	public String getBondAmount() {
		return bondAmount;
	}
	public void setBondAmount(String bondAmount) {
		this.bondAmount = bondAmount;
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
	public String getAccessId() {
		return accessId;
	}
	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getBusAmount() {
		return busAmount;
	}
	public void setBusAmount(String busAmount) {
		this.busAmount = busAmount;
	}
	public String getBusCurrency() {
		return busCurrency;
	}
	public void setBusCurrency(String busCurrency) {
		this.busCurrency = busCurrency;
	}
	public String getSettleAmount() {
		return settleAmount;
	}
	public void setSettleAmount(String settleAmount) {
		this.settleAmount = settleAmount;
	}
	public String getSettleCurrency() {
		return settleCurrency;
	}
	public void setSettleCurrency(String settleCurrency) {
		this.settleCurrency = settleCurrency;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	
}
