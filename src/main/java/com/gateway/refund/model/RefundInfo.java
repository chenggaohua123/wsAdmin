package com.gateway.refund.model;

public class RefundInfo {
	private String id;
	/** 交易订单号 */
	private String tradeNo;
	/** 商户订单号 */
	private String orderNo;
	/** 商户号 */
	private String terNo;
	/** 终端号 */
	private String merNo;
	/** 交易金额 */
	private String busAmount;
	/** 交易币种 */
	private String busCurrency;
	/** 退款币种 */
	private String refundCurrency;
	/** 退款金额 */
	private String refundAmount;
	/** 银行交易币种 */
	private String bankCurrency;
	/** 银行交易金额 */
	private String bankTransAmount;
	/** 退款原因 */
	private String refundReason;
	/** 创建人 */
	private String applyBy;
	/** 创建时间 */
	private String applyDate;
	/** 退款时间 */
	private String refundDate;
	/** 退款审核人 */
	private String auditor;
	/** 备注 */
	private String remark;
	/** 状态 0 待审核 1,驳回2 退款通过 */
	private String status;
	/** 交易变更ID，关联交易变更表 */
	private int transLogId;
	/** 0来源商户退款，1来源交易变更 */
	private String reType;
	
	/**
	 * 退款新流水号
	 * */
	private String tradeNewNo;
	
	private String currencyName;
	private String checkNo;
	private String middle;
	private String last;
	private String autoCode;
	
	private String isChecked;
	/**  是否退款0:未，1:待审核，2已退款 */
	private String isRefund;
	/**  是否冻结0:未，1:待审核，2已冻结 */
	private String isFrozen;
	/**  是否冻结，统计冻结,0未冻结，1，已冻结 */
	private String transFrozen;
	
	/** 是否拒付0:未，1:待审核，2已拒付  */
	private String isDishonor;
	/** 是否拒付，统计拒付,0未拒付，1，已拒付  */
	private String transDishonor;
	/** 退款通知地址  */
	private String refundNotifyURL;
	/** 退款通知状态  */
	private int refundNotifyStatus;
	
	
	public String getIsRefund() {
		return isRefund;
	}
	public void setIsRefund(String isRefund) {
		this.isRefund = isRefund;
	}
	public int getRefundNotifyStatus() {
		return refundNotifyStatus;
	}
	public void setRefundNotifyStatus(int refundNotifyStatus) {
		this.refundNotifyStatus = refundNotifyStatus;
	}
	public String getRefundNotifyURL() {
		return refundNotifyURL;
	}
	public void setRefundNotifyURL(String refundNotifyURL) {
		this.refundNotifyURL = refundNotifyURL;
	}
	public String getIsDishonor() {
		return isDishonor;
	}
	public void setIsDishonor(String isDishonor) {
		this.isDishonor = isDishonor;
	}
	public String getTransDishonor() {
		return transDishonor;
	}
	public void setTransDishonor(String transDishonor) {
		this.transDishonor = transDishonor;
	}
	public String getIsFrozen() {
		return isFrozen;
	}
	public void setIsFrozen(String isFrozen) {
		this.isFrozen = isFrozen;
	}
	public String getTransFrozen() {
		return transFrozen;
	}
	public void setTransFrozen(String transFrozen) {
		this.transFrozen = transFrozen;
	}
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getCheckNo() {
		return checkNo;
	}
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}
	public String getMiddle() {
		return middle;
	}
	public void setMiddle(String middle) {
		this.middle = middle;
	}
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = last;
	}
	public String getAutoCode() {
		return autoCode;
	}
	public void setAutoCode(String autoCode) {
		this.autoCode = autoCode;
	}
	public String getTradeNewNo() {
		return tradeNewNo;
	}
	public void setTradeNewNo(String tradeNewNo) {
		this.tradeNewNo = tradeNewNo;
	}
	public String getReType() {
		return reType;
	}
	public void setReType(String reType) {
		this.reType = reType;
	}
	public String getBankCurrency() {
		return bankCurrency;
	}
	public void setBankCurrency(String bankCurrency) {
		this.bankCurrency = bankCurrency;
	}
	public String getBankTransAmount() {
		return bankTransAmount;
	}
	public void setBankTransAmount(String bankTransAmount) {
		this.bankTransAmount = bankTransAmount;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public int getTransLogId() {
		return transLogId;
	}
	public void setTransLogId(int transLogId) {
		this.transLogId = transLogId;
	}
	public String getApplyBy() {
		return applyBy;
	}
	public void setApplyBy(String applyBy) {
		this.applyBy = applyBy;
	}
	public String getBusAmount() {
		return busAmount;
	}
	public void setBusAmount(String busAmount) {
		this.busAmount = busAmount;
	}
	public String getBusCurrency() {
		return busCurrency;
	}
	public void setBusCurrency(String busCurrency) {
		this.busCurrency = busCurrency;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getTerNo() {
		return terNo;
	}
	public void setTerNo(String terNo) {
		this.terNo = terNo;
	}
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public String getRefundCurrency() {
		return refundCurrency;
	}
	public void setRefundCurrency(String refundCurrency) {
		this.refundCurrency = refundCurrency;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getRefundReason() {
		return refundReason;
	}
	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
