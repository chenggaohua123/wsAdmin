package com.gateway.bankmgr.model;

import java.sql.Timestamp;
import java.util.Map;

public class CurrencyInfo {
	private int id;
	private String currencyName;
	private String bankName;
	private int bankId;
	private String mcc;
	private String merchantNo;
	private String terNo;
	private int enabled;
	private Timestamp createDate;
	private String createBy;
	private Map<String, String> configList;
	private BankInfo bankInfo;
	
	private String ucreateBy;
	private Timestamp updateDate;
	private int currencyId;
	
	private String keyType;
	private String keyValue;//密钥
	private int keyLength;
	private String keyAlias;
	private	String className;
	private String checkValue;//校验值
		
	private double currencyRate;
	private double topAmount;
	private String acquirer;
	
	private String currencyBelongs;
	private String remark;
	
	
	
	
	public String getCurrencyBelongs() {
		return currencyBelongs;
	}
	public void setCurrencyBelongs(String currencyBelongs) {
		this.currencyBelongs = currencyBelongs;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAcquirer() {
		return acquirer;
	}
	public void setAcquirer(String acquirer) {
		this.acquirer = acquirer;
	}
	public double getCurrencyRate() {
		return currencyRate;
	}
	public void setCurrencyRate(double currencyRate) {
		this.currencyRate = currencyRate;
	}
	public double getTopAmount() {
		return topAmount;
	}
	public void setTopAmount(double topAmount) {
		this.topAmount = topAmount;
	}
	public int getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(int currencyId) {
		this.currencyId = currencyId;
	}
	public String getUcreateBy() {
		return ucreateBy;
	}
	public void setUcreateBy(String ucreateBy) {
		this.ucreateBy = ucreateBy;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public int getBankId() {
		return bankId;
	}
	public void setBankId(int bankId) {
		this.bankId = bankId;
	}
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getTerNo() {
		return terNo;
	}
	public void setTerNo(String terNo) {
		this.terNo = terNo;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Map<String, String> getConfigList() {
		return configList;
	}
	public void setConfigList(Map<String, String> configList) {
		this.configList = configList;
	}
	public BankInfo getBankInfo() {
		return bankInfo;
	}
	public void setBankInfo(BankInfo bankInfo) {
		this.bankInfo = bankInfo;
	}
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	public String getCheckValue() {
		return checkValue;
	}
	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}
	public String getKeyAlias() {
		return keyAlias;
	}
	public void setKeyAlias(String keyAlias) {
		this.keyAlias = keyAlias;
	}
	public String getKeyType() {
		return keyType;
	}
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getKeyLength() {
		return keyLength;
	}
	public void setKeyLength(int keyLength) {
		this.keyLength = keyLength;
	}
	
	
}
