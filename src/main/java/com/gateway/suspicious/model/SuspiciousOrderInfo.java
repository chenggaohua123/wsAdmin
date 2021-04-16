package com.gateway.suspicious.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SuspiciousOrderInfo {
	private String merNo;
	private String terNo;
	private String tradeNo;
	private Timestamp transDate;
	private String merBusCurrency;
	private BigDecimal merTransAmount;
	private String isDishonor;
	private String isRefund;
	private String ruleIds;
	private String ruleNames;
	private int syspId;
	private int susOrderId;
	private int susType;
	private Timestamp createDate;
	private String status;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public Timestamp getTransDate() {
		return transDate;
	}
	public void setTransDate(Timestamp transDate) {
		this.transDate = transDate;
	}
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
	public String getIsDishonor() {
		return isDishonor;
	}
	public void setIsDishonor(String isDishonor) {
		this.isDishonor = isDishonor;
	}
	public String getIsRefund() {
		return isRefund;
	}
	public void setIsRefund(String isRefund) {
		this.isRefund = isRefund;
	}
	public String getRuleIds() {
		return ruleIds;
	}
	public void setRuleIds(String ruleIds) {
		this.ruleIds = ruleIds;
	}
	public String getRuleNames() {
		return ruleNames;
	}
	public void setRuleNames(String ruleNames) {
		this.ruleNames = ruleNames;
	}
	public int getSyspId() {
		return syspId;
	}
	public void setSyspId(int syspId) {
		this.syspId = syspId;
	}
	public int getSusOrderId() {
		return susOrderId;
	}
	public void setSusOrderId(int susOrderId) {
		this.susOrderId = susOrderId;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public int getSusType() {
		return susType;
	}
	public void setSusType(int susType) {
		this.susType = susType;
	}
}
