package com.gateway.settlemgr.model;

import java.math.BigDecimal;

public class PoolSettleInfo {
	private String merNo;
	private String terNo;
	private String dateStr;
	private String currency;
	private BigDecimal settleAmount;
	private BigDecimal feeAmount;
	private BigDecimal singleAmount;
	private BigDecimal bondAmount;
	private BigDecimal refundAmount;
	private BigDecimal disAmount;
	private BigDecimal thawAmount;
	private BigDecimal froznAmount;
	private BigDecimal cashAmount;
	private BigDecimal otherAmount;
	private BigDecimal totalAmount;
	
	
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getSettleAmount() {
		return settleAmount;
	}
	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}
	public BigDecimal getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}
	public BigDecimal getSingleAmount() {
		return singleAmount;
	}
	public void setSingleAmount(BigDecimal singleAmount) {
		this.singleAmount = singleAmount;
	}
	public BigDecimal getBondAmount() {
		return bondAmount;
	}
	public void setBondAmount(BigDecimal bondAmount) {
		this.bondAmount = bondAmount;
	}
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
	public BigDecimal getDisAmount() {
		return disAmount;
	}
	public void setDisAmount(BigDecimal disAmount) {
		this.disAmount = disAmount;
	}
	public BigDecimal getThawAmount() {
		return thawAmount;
	}
	public void setThawAmount(BigDecimal thawAmount) {
		this.thawAmount = thawAmount;
	}
	public BigDecimal getFroznAmount() {
		return froznAmount;
	}
	public void setFroznAmount(BigDecimal froznAmount) {
		this.froznAmount = froznAmount;
	}
	public BigDecimal getCashAmount() {
		return cashAmount;
	}
	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}
	public BigDecimal getOtherAmount() {
		return otherAmount;
	}
	public void setOtherAmount(BigDecimal otherAmount) {
		this.otherAmount = otherAmount;
	}
	
}
