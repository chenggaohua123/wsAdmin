package com.gateway.suspicious.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SuspiciousOrderListInfo {
	private String merNo;
	private String terNo;
	private String orderNo;
	private String tradeNo;
	private Timestamp transDate;
	private String merBusCurrency;
	private BigDecimal merTransAmount;
	private String isDishonor;
	private String isRefund;
	private String tradeList;
	private String ruleIdList;
	private String ruleNameList;
	private String ruleId;
	private String ruleName;
	private String susType;
	private String syspId;
	private String susOrderId;
	private String createDate;
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
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	public String getTradeList() {
		return tradeList;
	}
	public void setTradeList(String tradeList) {
		this.tradeList = tradeList;
	}
	public String getRuleIdList() {
		return ruleIdList;
	}
	public void setRuleIdList(String ruleIdList) {
		this.ruleIdList = ruleIdList;
	}
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getSyspId() {
		return syspId;
	}
	public void setSyspId(String syspId) {
		this.syspId = syspId;
	}
	public String getSusOrderId() {
		return susOrderId;
	}
	public void setSusOrderId(String susOrderId) {
		this.susOrderId = susOrderId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getRuleNameList() {
		return ruleNameList;
	}
	public void setRuleNameList(String ruleNameList) {
		this.ruleNameList = ruleNameList;
	}
	public String getSusType() {
		return susType;
	}
	public void setSusType(String susType) {
		this.susType = susType;
	}
	public String getMerBusCurrency() {
		return merBusCurrency;
	}
	public void setMerBusCurrency(String merBusCurrency) {
		this.merBusCurrency = merBusCurrency;
	}
}
