package com.gateway.exchangRate.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ExchangRateInfo {
	private String id;
	/** 组名 */
	private String groupName;
	/** 原始币种 */
	private String sourceCurrency;
	/** 目标币种 */
	private String targetCurrency;
	/** 汇率 */
	private String rate;
	/** 偏移值 */
	private BigDecimal offsetValue;
	/** 是否有效 */
	private int enabled;
	/** 汇率类型 */
	private String type;
	/**  */
	private String bankRate;
	/** 审核人 */
	private String checkBy;
	/** 审核时间 */
	private Timestamp checkDate;
	/** 银行汇率类型 */
	private String rateType;
	/** 修改时间 */
	private String updateDate;
	/** 修改人 */
	private String updateBy;
	/** 创建人 */
	private String createBy;
	/** 创建时间 */
	private Timestamp createDate;
	/** 操作类型 */
	private String operationType;
	/** 在修改汇率Id */
	private int rateId;
	/** 审核状态 */
	private int checkStatus;
	
	private int exceptionType;
	
	
	private int bankId;
	
	private String bankName;
	
	
	
	public int getBankId() {
		return bankId;
	}
	public void setBankId(int bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public int getExceptionType() {
		return exceptionType;
	}
	public void setExceptionType(int exceptionType) {
		this.exceptionType = exceptionType;
	}
	public int getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}
	public int getRateId() {
		return rateId;
	}
	public void setRateId(int rateId) {
		this.rateId = rateId;
	}
	public String getCheckBy() {
		return checkBy;
	}
	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}
	public Timestamp getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Timestamp checkDate) {
		this.checkDate = checkDate;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public BigDecimal getOffsetValue() {
		return offsetValue;
	}
	public void setOffsetValue(BigDecimal offsetValue) {
		this.offsetValue = offsetValue;
	}
	public String getBankRate() {
		return bankRate;
	}
	public void setBankRate(String bankRate) {
		this.bankRate = bankRate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
