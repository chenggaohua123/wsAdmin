package com.gateway.settlemgr.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class GwSettleTransInfo {
	private Integer id;
	private String tradeNo;
	private String relNo;
	private BigDecimal transAmount;
	private String merNo;
	private String terNo;
	private String cardType;
	private String agentNo;
	private String parentAgentNo;
	private Timestamp transDate;
	private String settleBatchNo;
	private String settleDate;
	private String batchNo;
	private String posNo;
	private int currencyId;
	private BigDecimal merForAmount;
	private BigDecimal agentForAmount;
	private BigDecimal parentAgentForAmount;
	private String transType;
	private String respCode;
	private String currencyName;
	
	private String bankName;
	
	
	
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getRelNo() {
		return relNo;
	}
	public void setRelNo(String relNo) {
		this.relNo = relNo;
	}
	public BigDecimal getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
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
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	public String getParentAgentNo() {
		return parentAgentNo;
	}
	public void setParentAgentNo(String parentAgentNo) {
		this.parentAgentNo = parentAgentNo;
	}
	public Timestamp getTransDate() {
		return transDate;
	}
	public void setTransDate(Timestamp transDate) {
		this.transDate = transDate;
	}
	public String getSettleBatchNo() {
		return settleBatchNo;
	}
	public void setSettleBatchNo(String settleBatchNo) {
		this.settleBatchNo = settleBatchNo;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getPosNo() {
		return posNo;
	}
	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}
	public int getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(int currencyId) {
		this.currencyId = currencyId;
	}
	public BigDecimal getMerForAmount() {
		return merForAmount;
	}
	public void setMerForAmount(BigDecimal merForAmount) {
		this.merForAmount = merForAmount;
	}
	public BigDecimal getAgentForAmount() {
		return agentForAmount;
	}
	public void setAgentForAmount(BigDecimal agentForAmount) {
		this.agentForAmount = agentForAmount;
	}
	public BigDecimal getParentAgentForAmount() {
		return parentAgentForAmount;
	}
	public void setParentAgentForAmount(BigDecimal parentAgentForAmount) {
		this.parentAgentForAmount = parentAgentForAmount;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	
}
