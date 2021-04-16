package com.gateway.form.model;

import java.math.BigDecimal;

public class CountAnalysis {
	//总进入支付数量
	private String totalCount;
	private String merNo;
	/** 国家 */
	private String cardCountry;
	/**网站*/
	private String payWebsite;
	/**所属终端号*/
	private String terNo;
	/**交易完成笔数*/
	private String transCount;
	/**交易币种*/
	private String transCurrency;
	/**交易金额*/
	private String transAmount;
	/**成功笔数*/
	private String transSuccessCount;
	/**成功金额*/
	private String transSuccessAmount;
	/**失败笔数*/
	private String transFailureCount;
	/**风险单笔数*/
	private int transRiskCount;
	/**拒付笔数*/
	private String dishonorCount;
	/**退单笔数*/
	private String refundCount;
	/**投诉笔数*/
	private String complaintCount;
	/**成功率*/
	private String successRate;
	/**拒付率*/
	private String dishonorRate;
	/**退单率*/
	private String refundRate;
	/**投诉率*/
	private String complaintRate;
	/**交易占比*/
	private String transRate;
	/** 交易占比总交易数 */
	private String transTotal;
	/** 年 */
	private String year;
	/** 月 */
	private String month;
	/** 日 */
	private String day;
	
	/** 失败原因 */
	private String respMsg;
	/** 失败笔数 */
	private String countRespMsg;
	/** 订单号组 */
	private String tradeNoS;
	
	/** 总风险待处理订单成功笔数 */
	private String riskTreatCount;
	/** 月风险待处理订单成功笔数 */
	private String riskTreatCountApril;
	/** 总风险待处理订单转换拒付笔数 */
	private String riskTreatToDishonorCount;
	/** 月风险待处理订单转换拒付笔数 */
	private String riskTreatToDishonorCountApril;
	/** 总拒付转换率 */
	private String riskTreatToDishonorRate;
	/** 月拒付转换率 */
	private String riskTreatToDishonorRateApril;
	
	
	private String currencyName;
	private String currencyId;
	private String bankName;
	private String currencyEnabled;
	private String currencyBelongs;
	private String respMsgRate;
	
	private String duplicateCount;
	
	private BigDecimal merSettleAmount;
	private String merSettleCurrency;
	private BigDecimal merSettleSuccessAmount;
	
	
	private double rate;
	
	
	
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public BigDecimal getMerSettleSuccessAmount() {
		return merSettleSuccessAmount;
	}
	public void setMerSettleSuccessAmount(BigDecimal merSettleSuccessAmount) {
		this.merSettleSuccessAmount = merSettleSuccessAmount;
	}
	public BigDecimal getMerSettleAmount() {
		return merSettleAmount;
	}
	public void setMerSettleAmount(BigDecimal merSettleAmount) {
		this.merSettleAmount = merSettleAmount;
	}
	public String getMerSettleCurrency() {
		return merSettleCurrency;
	}
	public void setMerSettleCurrency(String merSettleCurrency) {
		this.merSettleCurrency = merSettleCurrency;
	}
	public String getDuplicateCount() {
		return duplicateCount;
	}
	public void setDuplicateCount(String duplicateCount) {
		this.duplicateCount = duplicateCount;
	}
	public String getRespMsgRate() {
		return respMsgRate;
	}
	public void setRespMsgRate(String respMsgRate) {
		this.respMsgRate = respMsgRate;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getCurrencyBelongs() {
		return currencyBelongs;
	}
	public void setCurrencyBelongs(String currencyBelongs) {
		this.currencyBelongs = currencyBelongs;
	}
	public String getCurrencyEnabled() {
		return currencyEnabled;
	}
	public void setCurrencyEnabled(String currencyEnabled) {
		this.currencyEnabled = currencyEnabled;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public String getRiskTreatCount() {
		return riskTreatCount;
	}
	public void setRiskTreatCount(String riskTreatCount) {
		this.riskTreatCount = riskTreatCount;
	}
	public String getRiskTreatCountApril() {
		return riskTreatCountApril;
	}
	public void setRiskTreatCountApril(String riskTreatCountApril) {
		this.riskTreatCountApril = riskTreatCountApril;
	}
	public String getRiskTreatToDishonorCount() {
		return riskTreatToDishonorCount;
	}
	public void setRiskTreatToDishonorCount(String riskTreatToDishonorCount) {
		this.riskTreatToDishonorCount = riskTreatToDishonorCount;
	}
	public String getRiskTreatToDishonorCountApril() {
		return riskTreatToDishonorCountApril;
	}
	public void setRiskTreatToDishonorCountApril(
			String riskTreatToDishonorCountApril) {
		this.riskTreatToDishonorCountApril = riskTreatToDishonorCountApril;
	}
	public String getRiskTreatToDishonorRate() {
		return riskTreatToDishonorRate;
	}
	public void setRiskTreatToDishonorRate(String riskTreatToDishonorRate) {
		this.riskTreatToDishonorRate = riskTreatToDishonorRate;
	}
	public String getRiskTreatToDishonorRateApril() {
		return riskTreatToDishonorRateApril;
	}
	public void setRiskTreatToDishonorRateApril(String riskTreatToDishonorRateApril) {
		this.riskTreatToDishonorRateApril = riskTreatToDishonorRateApril;
	}
	public String getTradeNoS() {
		return tradeNoS;
	}
	public void setTradeNoS(String tradeNoS) {
		this.tradeNoS = tradeNoS;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public String getCountRespMsg() {
		return countRespMsg;
	}
	public void setCountRespMsg(String countRespMsg) {
		this.countRespMsg = countRespMsg;
	}
	public String getCardCountry() {
		return cardCountry;
	}
	public void setCardCountry(String cardCountry) {
		this.cardCountry = cardCountry;
	}
	public String getTransTotal() {
		return transTotal;
	}
	public void setTransTotal(String transTotal) {
		this.transTotal = transTotal;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getTransCurrency() {
		return transCurrency;
	}
	public void setTransCurrency(String transCurrency) {
		this.transCurrency = transCurrency;
	}
	public String getPayWebsite() {
		return payWebsite;
	}
	public void setPayWebsite(String payWebsite) {
		this.payWebsite = payWebsite;
	}
	public String getTerNo() {
		return terNo;
	}
	public void setTerNo(String terNo) {
		this.terNo = terNo;
	}
	public String getTransCount() {
		return transCount;
	}
	public void setTransCount(String transCount) {
		this.transCount = transCount;
	}
	public String getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}
	public String getTransSuccessCount() {
		return transSuccessCount;
	}
	public void setTransSuccessCount(String transSuccessCount) {
		this.transSuccessCount = transSuccessCount;
	}
	public String getTransSuccessAmount() {
		return transSuccessAmount;
	}
	public void setTransSuccessAmount(String transSuccessAmount) {
		this.transSuccessAmount = transSuccessAmount;
	}
	public String getTransFailureCount() {
		return transFailureCount;
	}
	public void setTransFailureCount(String transFailureCount) {
		this.transFailureCount = transFailureCount;
	}
	
	public int getTransRiskCount() {
		return transRiskCount;
	}
	public void setTransRiskCount(int transRiskCount) {
		this.transRiskCount = transRiskCount;
	}
	public String getDishonorCount() {
		return dishonorCount;
	}
	public void setDishonorCount(String dishonorCount) {
		this.dishonorCount = dishonorCount;
	}
	public String getRefundCount() {
		return refundCount;
	}
	public void setRefundCount(String refundCount) {
		this.refundCount = refundCount;
	}
	public String getComplaintCount() {
		return complaintCount;
	}
	public void setComplaintCount(String complaintCount) {
		this.complaintCount = complaintCount;
	}
	public String getSuccessRate() {
		return successRate;
	}
	public void setSuccessRate(String successRate) {
		this.successRate = successRate;
	}
	public String getDishonorRate() {
		return dishonorRate;
	}
	public void setDishonorRate(String dishonorRate) {
		this.dishonorRate = dishonorRate;
	}
	public String getRefundRate() {
		return refundRate;
	}
	public void setRefundRate(String refundRate) {
		this.refundRate = refundRate;
	}
	public String getComplaintRate() {
		return complaintRate;
	}
	public void setComplaintRate(String complaintRate) {
		this.complaintRate = complaintRate;
	}
	public String getTransRate() {
		return transRate;
	}
	public void setTransRate(String transRate) {
		this.transRate = transRate;
	}
	
}
