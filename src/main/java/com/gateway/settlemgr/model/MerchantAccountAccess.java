package com.gateway.settlemgr.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class MerchantAccountAccess {
	private String id;
	private String merNo;
	private String terNo;
	private String accountId;
	private int accountType;
	private String currency;
	private BigDecimal amount;
	private Timestamp createDate;
	private String remark;
	private int cashType;
	/*** 提现状态:0待审核 1审核通过 2审核不通过 3复核通过 4复核驳回 5已出款 */
	private String status;
	private String createBy;
	private String checkBy;
	private Timestamp checkDate;
	private String checkRemark;
	private String reCheckBy;
	private Timestamp reCheckDate;
	private String reCheckRemark;
	private String moneyBy;
	private Timestamp moneyDate;
	private String moneyDateStr;
	private String moneyRemark;
	private BigDecimal operateAmount;
	private BigDecimal operatedAmount;
	
	private String accountNo;
	private String accountAddress;
	private String bankNo;
	private String accountState;
	private String accountCity;
	private String accountName;
	private String accountContryCode;
	private String deductionType;
	
	private String merchantName;
	
	/**商户渠道 在字典管理中设置*/
	private int merchantChannel;
	
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public int getMerchantChannel() {
		return merchantChannel;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMoneyDateStr() {
		return moneyDateStr;
	}
	public void setMoneyDateStr(String moneyDateStr) {
		this.moneyDateStr = moneyDateStr;
	}
	public String getDeductionType() {
		return deductionType;
	}
	public void setDeductionType(String deductionType) {
		this.deductionType = deductionType;
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
	public String getAccountState() {
		return accountState;
	}
	public void setAccountState(String accountState) {
		this.accountState = accountState;
	}
	public String getAccountCity() {
		return accountCity;
	}
	public void setAccountCity(String accountCity) {
		this.accountCity = accountCity;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountContryCode() {
		return accountContryCode;
	}
	public void setAccountContryCode(String accountContryCode) {
		this.accountContryCode = accountContryCode;
	}
	public String getReCheckRemark() {
		return reCheckRemark;
	}
	public void setReCheckRemark(String reCheckRemark) {
		this.reCheckRemark = reCheckRemark;
	}
	public BigDecimal getOperateAmount() {
		return operateAmount;
	}
	public void setOperateAmount(BigDecimal operateAmount) {
		this.operateAmount = operateAmount;
	}
	public BigDecimal getOperatedAmount() {
		return operatedAmount;
	}
	public void setOperatedAmount(BigDecimal operatedAmount) {
		this.operatedAmount = operatedAmount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public int getAccountType() {
		return accountType;
	}
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getCashType() {
		return cashType;
	}
	public void setCashType(int cashType) {
		this.cashType = cashType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCheckBy() {
		return checkBy;
	}
	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}
	public String getCheckRemark() {
		return checkRemark;
	}
	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
	public String getReCheckBy() {
		return reCheckBy;
	}
	public void setReCheckBy(String reCheckBy) {
		this.reCheckBy = reCheckBy;
	}
	public String getMoneyBy() {
		return moneyBy;
	}
	public void setMoneyBy(String moneyBy) {
		this.moneyBy = moneyBy;
	}
	public String getMoneyRemark() {
		return moneyRemark;
	}
	public void setMoneyRemark(String moneyRemark) {
		this.moneyRemark = moneyRemark;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public Timestamp getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Timestamp checkDate) {
		this.checkDate = checkDate;
	}
	public Timestamp getReCheckDate() {
		return reCheckDate;
	}
	public void setReCheckDate(Timestamp reCheckDate) {
		this.reCheckDate = reCheckDate;
	}
	public Timestamp getMoneyDate() {
		return moneyDate;
	}
	public void setMoneyDate(Timestamp moneyDate) {
		this.moneyDate = moneyDate;
	}
	
	
	

}
