package com.gateway.ratemgr.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class RateInfo {
	private int id;
	private String merNo;
	private String terNo;
	private BigDecimal merRate;
	private Timestamp createDate;
	private Timestamp lastUpdateDate;
	private String createBy;
	private String lastUpdateBy;
	private int status;
	private int rateId;
	private String merchantName;
	/** 保证金费率 */
	private BigDecimal bondRate;
	private BigDecimal singleFee;
	private String cardType;
	/** 银行名 */
	private String bankName;
	private String rateCurrency;//费率币种
	private String currencyId;//通道id
	private String currencyName;
	/** 银行ID */
	private String bankId;
	private String countrys;
	private String isEUR;
	
	
	
	
	
	public String getIsEUR() {
		return isEUR;
	}
	public void setIsEUR(String isEUR) {
		this.isEUR = isEUR;
	}
	public String getCountrys() {
		return countrys;
	}
	public void setCountrys(String countrys) {
		this.countrys = countrys;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public BigDecimal getBondRate() {
		return bondRate;
	}
	public void setBondRate(BigDecimal bondRate) {
		this.bondRate = bondRate;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public int getRateId() {
		return rateId;
	}
	public void setRateId(int rateId) {
		this.rateId = rateId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public BigDecimal getMerRate() {
		return merRate;
	}
	public void setMerRate(BigDecimal merRate) {
		this.merRate = merRate;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public Timestamp getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Timestamp lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public BigDecimal getSingleFee() {
		return singleFee;
	}
	public void setSingleFee(BigDecimal singleFee) {
		this.singleFee = singleFee;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	public String getRateCurrency() {
		return rateCurrency;
	}
	public void setRateCurrency(String rateCurrency) {
		this.rateCurrency = rateCurrency;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	
	
	
}
