package com.gateway.report.riskreport.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class GwRiskTransinfo {
	private Integer id;
	private String tradeNo;
	private String riskType;
	private int status;
	private Timestamp createDate;
	private String[] ids;
	private String relNo;
	private String merNo;
	private String terNo;
	private String transType;
	private BigDecimal transAmount;
	private String respCode;
	private Timestamp transDate;
	private String currencyName;
	private String acquiringBank;
	private String bankName;
	private String cardName;
	private String cardType;
	private String settleDate;
	
	private Timestamp transDateEnd;
	private Timestamp transDateStart;
	
	
	
	public Timestamp getTransDateEnd() {
		return transDateEnd;
	}
	public void setTransDateEnd(Timestamp transDateEnd) {
		this.transDateEnd = transDateEnd;
	}
	public Timestamp getTransDateStart() {
		return transDateStart;
	}
	public void setTransDateStart(Timestamp transDateStart) {
		this.transDateStart = transDateStart;
	}
	public String getRelNo() {
		return relNo;
	}
	public void setRelNo(String relNo) {
		this.relNo = relNo;
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
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public BigDecimal getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public Timestamp getTransDate() {
		return transDate;
	}
	public void setTransDate(Timestamp transDate) {
		this.transDate = transDate;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getAcquiringBank() {
		return acquiringBank;
	}
	public void setAcquiringBank(String acquiringBank) {
		this.acquiringBank = acquiringBank;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public String[] getIds() {
		return ids;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
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
	public String getRiskType() {
		return riskType;
	}
	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
}
