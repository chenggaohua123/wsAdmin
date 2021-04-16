package com.gateway.settlemgr.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class AgentSettleInfo {
	private Integer id;
	private String merNo;
	private String terNo;
	private String agentNo;
	private String parentAgentNo;
	private int transCount;
	private BigDecimal transAmount;
	private BigDecimal transForAmaount;
	private BigDecimal diversityAgentForAmount;
	private BigDecimal diversitySplitAgentForAmount;
	private BigDecimal diversityParentAgentForAmount;
	private BigDecimal diversitySplitParentAgentForAmount;
	private BigDecimal settleAmount;
	private String settleBatchNo;
	private Timestamp settleDate;
	private BigDecimal agentSplitRate;
	private BigDecimal parentAgentRate;
	private String accountAddress;
	private String accountName;
	private String accountNo;
	private Timestamp createDate;
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getAccountAddress() {
		return accountAddress;
	}
	public void setAccountAddress(String accountAddress) {
		this.accountAddress = accountAddress;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public BigDecimal getAgentSplitRate() {
		return agentSplitRate;
	}
	public void setAgentSplitRate(BigDecimal agentSplitRate) {
		this.agentSplitRate = agentSplitRate;
	}
	public BigDecimal getParentAgentRate() {
		return parentAgentRate;
	}
	public void setParentAgentRate(BigDecimal parentAgentRate) {
		this.parentAgentRate = parentAgentRate;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public int getTransCount() {
		return transCount;
	}
	public void setTransCount(int transCount) {
		this.transCount = transCount;
	}
	public BigDecimal getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}
	public BigDecimal getTransForAmaount() {
		return transForAmaount;
	}
	public void setTransForAmaount(BigDecimal transForAmaount) {
		this.transForAmaount = transForAmaount;
	}
	public BigDecimal getDiversityAgentForAmount() {
		return diversityAgentForAmount;
	}
	public void setDiversityAgentForAmount(BigDecimal diversityAgentForAmount) {
		this.diversityAgentForAmount = diversityAgentForAmount;
	}
	public BigDecimal getDiversitySplitAgentForAmount() {
		return diversitySplitAgentForAmount;
	}
	public void setDiversitySplitAgentForAmount(
			BigDecimal diversitySplitAgentForAmount) {
		this.diversitySplitAgentForAmount = diversitySplitAgentForAmount;
	}
	public BigDecimal getDiversityParentAgentForAmount() {
		return diversityParentAgentForAmount;
	}
	public void setDiversityParentAgentForAmount(
			BigDecimal diversityParentAgentForAmount) {
		this.diversityParentAgentForAmount = diversityParentAgentForAmount;
	}
	public BigDecimal getDiversitySplitParentAgentForAmount() {
		return diversitySplitParentAgentForAmount;
	}
	public void setDiversitySplitParentAgentForAmount(
			BigDecimal diversitySplitParentAgentForAmount) {
		this.diversitySplitParentAgentForAmount = diversitySplitParentAgentForAmount;
	}
	public BigDecimal getSettleAmount() {
		return settleAmount;
	}
	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}
	public String getSettleBatchNo() {
		return settleBatchNo;
	}
	public void setSettleBatchNo(String settleBatchNo) {
		this.settleBatchNo = settleBatchNo;
	}
	public Timestamp getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(Timestamp settleDate) {
		this.settleDate = settleDate;
	}
	
}
