package com.gateway.faffmgr.model;

import java.sql.Timestamp;


public class TransCheckForQuery {
	private String merNo;
	private String terNo;
	private Timestamp transDate;
	private String tradeNo;
	private String bankName;
	private String currencyName;
	private String isChecked;
	private String access;
	private String respCode;
	private String bankTransAmount;
	private String bankCurrency;
	private String bankSettleCurrency;
	private String bondSettleAmount;
	private String disSettleAmount;
	private String salesSettleAmount;
	private String salesSettleFee;
	private String refundSettleAmount;
	private String transRefund;
	private String transDishonor;
	private String isComp;
	private String isSp;
	private Timestamp settleDate;
	private String refundAmount;
	private String disAmount;
	private Timestamp exceptionDate;
	
	
	
	
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
	public Timestamp getExceptionDate() {
		return exceptionDate;
	}
	public void setExceptionDate(Timestamp exceptionDate) {
		this.exceptionDate = exceptionDate;
	}
	public String getBankSettleCurrency() {
		return bankSettleCurrency;
	}
	public void setBankSettleCurrency(String bankSettleCurrency) {
		this.bankSettleCurrency = bankSettleCurrency;
	}
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
	public String getTerNo() {
		return terNo;
	}
	public void setTerNo(String terNo) {
		this.terNo = terNo;
	}
	public Timestamp getTransDate() {
		return transDate;
	}
	public void setTransDate(Timestamp transDate) {
		this.transDate = transDate;
	}
	public Timestamp getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(Timestamp settleDate) {
		this.settleDate = settleDate;
	}
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
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getBankTransAmount() {
		return bankTransAmount;
	}
	public void setBankTransAmount(String bankTransAmount) {
		this.bankTransAmount = bankTransAmount;
	}
	public String getBankCurrency() {
		return bankCurrency;
	}
	public void setBankCurrency(String bankCurrency) {
		this.bankCurrency = bankCurrency;
	}
	public String getBondSettleAmount() {
		return bondSettleAmount;
	}
	public void setBondSettleAmount(String bondSettleAmount) {
		this.bondSettleAmount = bondSettleAmount;
	}
	public String getDisSettleAmount() {
		return disSettleAmount;
	}
	public void setDisSettleAmount(String disSettleAmount) {
		this.disSettleAmount = disSettleAmount;
	}
	public String getSalesSettleAmount() {
		return salesSettleAmount;
	}
	public void setSalesSettleAmount(String salesSettleAmount) {
		this.salesSettleAmount = salesSettleAmount;
	}
	public String getSalesSettleFee() {
		return salesSettleFee;
	}
	public void setSalesSettleFee(String salesSettleFee) {
		this.salesSettleFee = salesSettleFee;
	}
	public String getRefundSettleAmount() {
		return refundSettleAmount;
	}
	public void setRefundSettleAmount(String refundSettleAmount) {
		this.refundSettleAmount = refundSettleAmount;
	}
	public String getTransRefund() {
		return transRefund;
	}
	public void setTransRefund(String transRefund) {
		this.transRefund = transRefund;
	}
	public String getTransDishonor() {
		return transDishonor;
	}
	public void setTransDishonor(String transDishonor) {
		this.transDishonor = transDishonor;
	}
	public String getIsComp() {
		return isComp;
	}
	public void setIsComp(String isComp) {
		this.isComp = isComp;
	}
	public String getIsSp() {
		return isSp;
	}
	public void setIsSp(String isSp) {
		this.isSp = isSp;
	}
	
}
