package com.gateway.exchangRate.model;

public class CreateCheckBankSourceRateInfo {
	private String bankRateType;
	private String sourceCurrency;
	private String targetCurrency;
	private String merRateType;
	private String groupName;
	private String addRate;
	private String createBy;
	
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getBankRateType() {
		return bankRateType;
	}
	public void setBankRateType(String bankRateType) {
		this.bankRateType = bankRateType;
	}
	public String getSourceCurrency() {
		return sourceCurrency;
	}
	public void setSourceCurrency(String sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}
	public String getTargetCurrency() {
		return targetCurrency;
	}
	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
	}
	public String getMerRateType() {
		return merRateType;
	}
	public void setMerRateType(String merRateType) {
		this.merRateType = merRateType;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getAddRate() {
		return addRate;
	}
	public void setAddRate(String addRate) {
		this.addRate = addRate;
	}
	
	
	
}
