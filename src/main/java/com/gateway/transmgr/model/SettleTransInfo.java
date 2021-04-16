package com.gateway.transmgr.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.gateway.api.model.PicInfo;

public class SettleTransInfo {
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
	
	/*private BigDecimal agentForAmount;
	
	private BigDecimal parentAgentForAmount;*/
	
	private String transType;
	
	private String respCode;
	
	private String cardNo;
	
	private String bankRefNo;
	
	private String bankPosNo;
	
	private String bankBatchNo;
	
	private String autoCode;
	
	private String bankTransDate;
	
	private String bankTransTime;

	private String currencyName;
	
	private String bankName;
	private String acquiringBank;
	private String cardName;
	private double total;
	
	private PicInfo picInfo;
	
	
	private Timestamp transDateStart;
	private Timestamp transDateEnd;
	
	private Timestamp settleDateStart;
	private Timestamp settleDateEnd;
	
	/** 新增字段 */
	//商户交易币种
	private String merBusCurrency;
	//商户交易金额
	private BigDecimal merTransAmount;
	//银行交易币种
	private String bankCurrency;
	//银行交易金额
	private BigDecimal bankTransAmount;
	//商户结算币种
	private String merSettleCurrency;
	//商户结算金额
	private BigDecimal merSettleAmount;
	
	public String getMerBusCurrency() {
		return merBusCurrency;
	}
	public void setMerBusCurrency(String merBusCurrency) {
		this.merBusCurrency = merBusCurrency;
	}
	public BigDecimal getMerTransAmount() {
		return merTransAmount;
	}
	public void setMerTransAmount(BigDecimal merTransAmount) {
		this.merTransAmount = merTransAmount;
	}
	public String getBankCurrency() {
		return bankCurrency;
	}
	public void setBankCurrency(String bankCurrency) {
		this.bankCurrency = bankCurrency;
	}
	public BigDecimal getBankTransAmount() {
		return bankTransAmount;
	}
	public void setBankTransAmount(BigDecimal bankTransAmount) {
		this.bankTransAmount = bankTransAmount;
	}
	public String getMerSettleCurrency() {
		return merSettleCurrency;
	}
	public void setMerSettleCurrency(String merSettleCurrency) {
		this.merSettleCurrency = merSettleCurrency;
	}
	public BigDecimal getMerSettleAmount() {
		return merSettleAmount;
	}
	public void setMerSettleAmount(BigDecimal merSettleAmount) {
		this.merSettleAmount = merSettleAmount;
	}
	public Timestamp getTransDateStart() {
		return transDateStart;
	}
	public void setTransDateStart(Timestamp transDateStart) {
		this.transDateStart = transDateStart;
	}
	public Timestamp getTransDateEnd() {
		return transDateEnd;
	}
	public void setTransDateEnd(Timestamp transDateEnd) {
		this.transDateEnd = transDateEnd;
	}
	public Timestamp getSettleDateStart() {
		return settleDateStart;
	}
	public void setSettleDateStart(Timestamp settleDateStart) {
		this.settleDateStart = settleDateStart;
	}
	public Timestamp getSettleDateEnd() {
		return settleDateEnd;
	}
	public void setSettleDateEnd(Timestamp settleDateEnd) {
		this.settleDateEnd = settleDateEnd;
	}
	public String getAcquiringBank() {
		return acquiringBank;
	}
	public void setAcquiringBank(String acquiringBank) {
		this.acquiringBank = acquiringBank;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public PicInfo getPicInfo() {
		return picInfo;
	}
	public void setPicInfo(PicInfo picInfo) {
		this.picInfo = picInfo;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	/*public BigDecimal getAgentForAmount() {
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
	}*/

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

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBankRefNo() {
		return bankRefNo;
	}

	public void setBankRefNo(String bankRefNo) {
		this.bankRefNo = bankRefNo;
	}

	public String getBankPosNo() {
		return bankPosNo;
	}

	public void setBankPosNo(String bankPosNo) {
		this.bankPosNo = bankPosNo;
	}

	public String getBankBatchNo() {
		return bankBatchNo;
	}

	public void setBankBatchNo(String bankBatchNo) {
		this.bankBatchNo = bankBatchNo;
	}

	public String getAutoCode() {
		return autoCode;
	}

	public void setAutoCode(String autoCode) {
		this.autoCode = autoCode;
	}

	public String getBankTransDate() {
		return bankTransDate;
	}

	public void setBankTransDate(String bankTransDate) {
		this.bankTransDate = bankTransDate;
	}

	public String getBankTransTime() {
		return bankTransTime;
	}

	public void setBankTransTime(String bankTransTime) {
		this.bankTransTime = bankTransTime;
	}

}
