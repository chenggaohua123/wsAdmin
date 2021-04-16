package com.gateway.merchantmgr.model;

public class MerchantCurrencySpecialInfo {
	private int id;
	private int merchantCurrencyId;
	private String settleCurrency;
	//单笔
	private int singleType;
	private double singleAmount;
	//日
	private int dayCountType;
	private int dayCount;
	private int dayAmountType;
	private double dayAmount;
	//月
	private int monthCountType;
	private int monthCount;
	private int monthAmountType;
	private double monthAmount;
	
	private String createDate;
	private String createBy;
	private String lastModifyDate;
	private String lastModifyBy;
	
	/** * 根据通道交易限额切换通道 0不切换 1切换*/
	private int currencyDayAmountType;
	/**  * 根据通道交易限额切换通道备选通道id，英文逗号隔开，例如19,20,21  */
	private String currencyDayAmountIds;
	/**  * 根据通道交易限额切换通道备选通道名称，英文逗号隔开，例如33B,10C,2A  */
	private String CurrencyDayAmountNames;
	public String getSettleCurrency() {
		return settleCurrency;
	}
	public void setSettleCurrency(String settleCurrency) {
		this.settleCurrency = settleCurrency;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(String lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public String getLastModifyBy() {
		return lastModifyBy;
	}
	public void setLastModifyBy(String lastModifyBy) {
		this.lastModifyBy = lastModifyBy;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMerchantCurrencyId() {
		return merchantCurrencyId;
	}
	public void setMerchantCurrencyId(int merchantCurrencyId) {
		this.merchantCurrencyId = merchantCurrencyId;
	}
	public int getSingleType() {
		return singleType;
	}
	public void setSingleType(int singleType) {
		this.singleType = singleType;
	}
	public double getSingleAmount() {
		return singleAmount;
	}
	public void setSingleAmount(double singleAmount) {
		this.singleAmount = singleAmount;
	}
	public int getDayCountType() {
		return dayCountType;
	}
	public void setDayCountType(int dayCountType) {
		this.dayCountType = dayCountType;
	}
	public int getDayCount() {
		return dayCount;
	}
	public void setDayCount(int dayCount) {
		this.dayCount = dayCount;
	}
	public int getDayAmountType() {
		return dayAmountType;
	}
	public void setDayAmountType(int dayAmountType) {
		this.dayAmountType = dayAmountType;
	}
	public int getMonthCountType() {
		return monthCountType;
	}
	public void setMonthCountType(int monthCountType) {
		this.monthCountType = monthCountType;
	}
	public int getMonthCount() {
		return monthCount;
	}
	public void setMonthCount(int monthCount) {
		this.monthCount = monthCount;
	}
	public int getMonthAmountType() {
		return monthAmountType;
	}
	public void setMonthAmountType(int monthAmountType) {
		this.monthAmountType = monthAmountType;
	}
	public double getDayAmount() {
		return dayAmount;
	}
	public void setDayAmount(double dayAmount) {
		this.dayAmount = dayAmount;
	}
	public double getMonthAmount() {
		return monthAmount;
	}
	public void setMonthAmount(double monthAmount) {
		this.monthAmount = monthAmount;
	}
	public int getCurrencyDayAmountType() {
		return currencyDayAmountType;
	}
	public void setCurrencyDayAmountType(int currencyDayAmountType) {
		this.currencyDayAmountType = currencyDayAmountType;
	}
	public String getCurrencyDayAmountIds() {
		return currencyDayAmountIds;
	}
	public void setCurrencyDayAmountIds(String currencyDayAmountIds) {
		this.currencyDayAmountIds = currencyDayAmountIds;
	}
	public String getCurrencyDayAmountNames() {
		return CurrencyDayAmountNames;
	}
	public void setCurrencyDayAmountNames(String currencyDayAmountNames) {
		CurrencyDayAmountNames = currencyDayAmountNames;
	}
	
	
}
