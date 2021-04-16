package com.gateway.settlemgr.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class MerchantSettleInfo {
	private Integer id;
	
	private String merNo;
	
	private String terNo;
	
	private String agentNo;
	
	private String parentAgentNo;
	
	private Integer transCount;
	
	private BigDecimal forAmount;
	
	private BigDecimal agentForAmount;
	
	private BigDecimal parentAgentForAmount;
	
	private Timestamp settleDate;
	
	private String batchNo;
	
	private BigDecimal settleAmount;
	
	private BigDecimal transAmount;
	private String accountName;
	private String accountNo;
	private String accountAddress;
	private String merchantName;
	
	private Timestamp settleDateEnd;
	
	private Timestamp settleDateStart;
	
	
	
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Timestamp getSettleDateEnd() {
		return settleDateEnd;
	}
	public void setSettleDateEnd(Timestamp settleDateEnd) {
		this.settleDateEnd = settleDateEnd;
	}
	public Timestamp getSettleDateStart() {
		return settleDateStart;
	}
	public void setSettleDateStart(Timestamp settleDateStart) {
		this.settleDateStart = settleDateStart;
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
	public String getAccountAddress() {
		return accountAddress;
	}
	public void setAccountAddress(String accountAddress) {
		this.accountAddress = accountAddress;
	}
	public BigDecimal getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getTransCount() {
		return transCount;
	}
	public void setTransCount(Integer transCount) {
		this.transCount = transCount;
	}
	public BigDecimal getForAmount() {
		return forAmount;
	}
	public void setForAmount(BigDecimal forAmount) {
		this.forAmount = forAmount;
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
	public Timestamp getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(Timestamp settleDate) {
		this.settleDate = settleDate;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public BigDecimal getSettleAmount() {
		return settleAmount;
	}
	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}
}
