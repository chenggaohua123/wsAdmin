package com.gateway.merchantmgr.model;

import java.sql.Timestamp;

public class MerchantRelCurrencyInfo {
	private int id;
	private String merNo;
	private String terNo;
	private String cardType;
	private int currencyId;
	private int currencyId2;
	private int currencyId3;
	private String currencyName;
	private String currencyName2;
	private String currencyName3;
	private int currencyDayAmountType;//日限额备用通道0不切换 1切换
	private String currencyDayAmountIds;//通道日限额备用通道id
	private String currencyDayAmountNames;//通道日限额备用通道名称
	private String createBy;
	private Timestamp createDate;
	private String bankName;
	private String bankName2;
	private String bankName3;
	private int enabled;
	private Timestamp updateDate;
	private String updateBy;
	private String batchNo;
	private String merchantName;
	private String acquirer;
	private String acquirerDiy;
	private int autoChange;


	public int getCurrencyDayAmountType() {
		return currencyDayAmountType;
	}

	public void setCurrencyDayAmountType(int currencyDayAmountType) {
		this.currencyDayAmountType = currencyDayAmountType;
	}

	public String getCurrencyDayAmountNames() {
		return currencyDayAmountNames;
	}

	public void setCurrencyDayAmountNames(String currencyDayAmountNames) {
		this.currencyDayAmountNames = currencyDayAmountNames;
	}

	public String getCurrencyDayAmountIds() {
		return currencyDayAmountIds;
	}

	public void setCurrencyDayAmountIds(String currencyDayAmountIds) {
		this.currencyDayAmountIds = currencyDayAmountIds;
	}

	public String getAcquirerDiy() {
		return acquirerDiy;
	}

	public void setAcquirerDiy(String acquirerDiy) {
		this.acquirerDiy = acquirerDiy;
	}

	public int getAutoChange() {
		return autoChange;
	}

	public void setAutoChange(int autoChange) {
		this.autoChange = autoChange;
	}

	public String getAcquirer() {
		return acquirer;
	}

	public void setAcquirer(String acquirer) {
		this.acquirer = acquirer;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getBankName2() {
		return bankName2;
	}

	public void setBankName2(String bankName2) {
		this.bankName2 = bankName2;
	}

	public String getBankName3() {
		return bankName3;
	}

	public void setBankName3(String bankName3) {
		this.bankName3 = bankName3;
	}

	public String getCurrencyName2() {
		return currencyName2;
	}

	public void setCurrencyName2(String currencyName2) {
		this.currencyName2 = currencyName2;
	}

	public String getCurrencyName3() {
		return currencyName3;
	}

	public void setCurrencyName3(String currencyName3) {
		this.currencyName3 = currencyName3;
	}

	public int getCurrencyId2() {
		return currencyId2;
	}

	public void setCurrencyId2(int currencyId2) {
		this.currencyId2 = currencyId2;
	}

	public int getCurrencyId3() {
		return currencyId3;
	}

	public void setCurrencyId3(int currencyId3) {
		this.currencyId3 = currencyId3;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public int getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(int currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
	
}
