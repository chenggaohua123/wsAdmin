package com.gateway.merchantmgr.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class GwTernoLmitInfo {
	private Integer id;
	private String merNo;
	private String terNo;
	/** 单笔限额 */
	private BigDecimal singleTransAmountLimit;
	/** 日限额 */
	private BigDecimal dayTransAmountLimit;
	/** 日交易额 */
	private BigDecimal dayAmount;
	/** 日使用比例 */
	private String dayRate;
	/** 月限额 */
	private BigDecimal monthTransAmountLimit;
	/** 月交易额 */
	private BigDecimal monthAmount;
	/** 月使用比例 */
	private String monthRate;
	private String settleCurrency;
	private Timestamp updateDate;
	/** 修改人 */
	private String upby;
	private Timestamp createDate;
	private String createby;
	private String status;
	
	private Timestamp createDateStart;
	private Timestamp createDateEnd;
	private int transCount;
	private String transDate;
	private String startTransDate;
	private String endTransDate;
	private String merchantName;
	private String type;
	private String cardType;
	private String amountOrCount;
	
	private int monthCountLimit;
	private String monthCount;
	private String monthCountRate;
	
	
	
	
	
	
	public int getMonthCountLimit() {
		return monthCountLimit;
	}
	public void setMonthCountLimit(int monthCountLimit) {
		this.monthCountLimit = monthCountLimit;
	}
	public String getMonthCount() {
		return monthCount;
	}
	public void setMonthCount(String monthCount) {
		this.monthCount = monthCount;
	}
	public String getMonthCountRate() {
		return monthCountRate;
	}
	public void setMonthCountRate(String monthCountRate) {
		this.monthCountRate = monthCountRate;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getAmountOrCount() {
		return amountOrCount;
	}
	public void setAmountOrCount(String amountOrCount) {
		this.amountOrCount = amountOrCount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSettleCurrency() {
		return settleCurrency;
	}
	public void setSettleCurrency(String settleCurrency) {
		this.settleCurrency = settleCurrency;
	}
	public BigDecimal getDayAmount() {
		return dayAmount;
	}
	public void setDayAmount(BigDecimal dayAmount) {
		this.dayAmount = dayAmount;
	}
	public String getDayRate() {
		return dayRate;
	}
	public void setDayRate(String dayRate) {
		this.dayRate = dayRate;
	}
	public BigDecimal getMonthAmount() {
		return monthAmount;
	}
	public void setMonthAmount(BigDecimal monthAmount) {
		this.monthAmount = monthAmount;
	}
	public String getMonthRate() {
		return monthRate;
	}
	public void setMonthRate(String monthRate) {
		this.monthRate = monthRate;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getStartTransDate() {
		return startTransDate;
	}
	public void setStartTransDate(String startTransDate) {
		this.startTransDate = startTransDate;
	}
	public String getEndTransDate() {
		return endTransDate;
	}
	public void setEndTransDate(String endTransDate) {
		this.endTransDate = endTransDate;
	}
	public int getTransCount() {
		return transCount;
	}
	public void setTransCount(int transCount) {
		this.transCount = transCount;
	}
	public Timestamp getCreateDateStart() {
		return createDateStart;
	}
	public void setCreateDateStart(Timestamp createDateStart) {
		this.createDateStart = createDateStart;
	}
	public Timestamp getCreateDateEnd() {
		return createDateEnd;
	}
	public void setCreateDateEnd(Timestamp createDateEnd) {
		this.createDateEnd = createDateEnd;
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
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpby() {
		return upby;
	}
	public void setUpby(String upby) {
		this.upby = upby;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getCreateby() {
		return createby;
	}
	public void setCreateby(String createby) {
		this.createby = createby;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getSingleTransAmountLimit() {
		return singleTransAmountLimit;
	}
	public void setSingleTransAmountLimit(BigDecimal singleTransAmountLimit) {
		this.singleTransAmountLimit = singleTransAmountLimit;
	}
	public BigDecimal getDayTransAmountLimit() {
		return dayTransAmountLimit;
	}
	public void setDayTransAmountLimit(BigDecimal dayTransAmountLimit) {
		this.dayTransAmountLimit = dayTransAmountLimit;
	}
	public BigDecimal getMonthTransAmountLimit() {
		return monthTransAmountLimit;
	}
	public void setMonthTransAmountLimit(BigDecimal monthTransAmountLimit) {
		this.monthTransAmountLimit = monthTransAmountLimit;
	}
	
}
