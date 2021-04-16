package com.gateway.refund.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/** 交易变更日志 */
public class TransInfoLog {
	private int id;
	/** 流水号 */
	private String tradeNo;
	/** 便更时间 */
	private Timestamp createDate;
	/** 变更人 */
	private String createBy;
	/** 变更类型 */
	private String transType;
	/** 审核人 */
	private String checkBy;
	/** 审核时间 */
	private Timestamp checkDate;
	/** 新关联的流水号 */
	private String tradeNewNo;
	/** 变更金额 */
	private BigDecimal transMoney;
	/** 变更币种 */
	private String transCurrency;
	/** 备注 */
	private String remark;
	/** 状态，0：待审核，1：未通过，2已通过 */
	private int status;
	/** 交易变更原因 */
	private String transReason;
	/** 商户交易币种 标签币种 */
	private String merBusCurrency;
	/** 商户交易金额 标签金额 */
	private BigDecimal merTransAmount;
	/** 交易日期 */
	private String transDate;
	/** 银行交易币种 */
	private String bankCurrency;
	/** 银行交易金额 */
	private BigDecimal bankTransAmount;
	/** 商户订单号 */
	private String orderNo;
	/** 商户号 */
	private String merNo;
	/** 0来源商户退款，1来源交易变更 */
	private String reType;
	private String refundAmount;
	private String dishonorAmount;
	private String frozenAmount;
	private String thawAmount;
	private String currencyName;
	private String isRefund;
	private String isDishonor;
	private String isFrozen;
	private String isThaw;
	private String refundStatus;
	private BigDecimal bankRefundTransAmount;
	private String mid;
	private String key;
	
	
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	private int toBank;
	
	
	public int getToBank() {
		return toBank;
	}
	public void setToBank(int toBank) {
		this.toBank = toBank;
	}
	public BigDecimal getBankRefundTransAmount() {
		return bankRefundTransAmount;
	}
	public void setBankRefundTransAmount(BigDecimal bankRefundTransAmount) {
		this.bankRefundTransAmount = bankRefundTransAmount;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	public String getIsRefund() {
		return isRefund;
	}
	public void setIsRefund(String isRefund) {
		this.isRefund = isRefund;
	}
	public String getIsDishonor() {
		return isDishonor;
	}
	public void setIsDishonor(String isDishonor) {
		this.isDishonor = isDishonor;
	}
	public String getIsFrozen() {
		return isFrozen;
	}
	public void setIsFrozen(String isFrozen) {
		this.isFrozen = isFrozen;
	}
	public String getIsThaw() {
		return isThaw;
	}
	public void setIsThaw(String isThaw) {
		this.isThaw = isThaw;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getDishonorAmount() {
		return dishonorAmount;
	}
	public void setDishonorAmount(String dishonorAmount) {
		this.dishonorAmount = dishonorAmount;
	}
	public String getFrozenAmount() {
		return frozenAmount;
	}
	public void setFrozenAmount(String frozenAmount) {
		this.frozenAmount = frozenAmount;
	}
	public String getThawAmount() {
		return thawAmount;
	}
	public void setThawAmount(String thawAmount) {
		this.thawAmount = thawAmount;
	}
	public String getReType() {
		return reType;
	}
	public void setReType(String reType) {
		this.reType = reType;
	}
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getBankCurrency() {
		return bankCurrency;
	}
	public void setBankCurrency(String bankCurrency) {
		this.bankCurrency = bankCurrency;
	}
	public BigDecimal getBankTransAmount() {
		return bankTransAmount;
	}
	public void setBankTransAmount(BigDecimal bankTransAmount) {
		this.bankTransAmount = bankTransAmount;
	}
	public String getMerBusCurrency() {
		return merBusCurrency;
	}
	public void setMerBusCurrency(String merBusCurrency) {
		this.merBusCurrency = merBusCurrency;
	}
	public BigDecimal getMerTransAmount() {
		return merTransAmount;
	}
	public void setMerTransAmount(BigDecimal merTransAmount) {
		this.merTransAmount = merTransAmount;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getTransReason() {
		return transReason;
	}
	public void setTransReason(String transReason) {
		this.transReason = transReason;
	}
	public String getTransCurrency() {
		return transCurrency;
	}
	public void setTransCurrency(String transCurrency) {
		this.transCurrency = transCurrency;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public BigDecimal getTransMoney() {
		return transMoney;
	}
	public void setTransMoney(BigDecimal transMoney) {
		this.transMoney = transMoney;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
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
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getTradeNewNo() {
		return tradeNewNo;
	}
	public void setTradeNewNo(String tradeNewNo) {
		this.tradeNewNo = tradeNewNo;
	}
}
