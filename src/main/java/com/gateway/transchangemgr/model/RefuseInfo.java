package com.gateway.transchangemgr.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.gateway.refund.model.TransInfoLog;

public class RefuseInfo {
	private int id;
	private String tradeNo;
	private String relNo;
	private BigDecimal transAmount;
	private String merNo;
	private String terNo;
	private String cardType;
	private String agentNo;
	private String parentAgentNo;
	private Timestamp transDate;
	private String settleDate;
	private String batchNo;
	private String posNo;
	private int currencyId;
	private BigDecimal merForAmount;
	private BigDecimal agentForAmount;
	private BigDecimal parentAgentForAmount;
	private String transType;
	private String respCode;
	private String cardNo;
	private String bankRefNo;
	private String bankPosNo;
	private String bankBatchNo;
	private String autoCode;
	private String bankTransDate;
	private String bankTransTime;
	private String currencyName;
	private String bankName;
	private double total;
	private String acquiringBank;
	private String cardName;
	private Timestamp transDateStart;
	private Timestamp transDateEnd;
	
	private Timestamp settleDateStart;
	private Timestamp settleDateEnd;
	private String checkedBatchNo;
	private String merchantName;
	
	private String checkToNo;
	private String checkNo;
	private String respMsg;
	private String IPAddress;
	private String email;
	private String last;
	/** 新增字段 */
	//商户交易币种
	private String merBusCurrency;
	//商户交易金额
	private BigDecimal merTransAmount;
	//银行交易币种
	private String bankCurrency;
	//银行交易金额
	private BigDecimal bankTransAmount;
	//商户结算币种
	private String merSettleCurrency;
	//商户结算金额
	private BigDecimal merSettleAmount;
	/** 保证金费率 */
	private BigDecimal bondAmount;
	//是否勾兑
	private int ischecked;
	/** 自联接ID  */
	private int rvfId;
	/** 是否退款0:未，1:待审核，2已退款 */
	private int isRefund;
	/** 是否拒付0:未，1:待审核，2已拒付 */
	private int isDishonor;
	/** 是否冻结0:未，1:待审核，2已冻结 */
	private int isFrozen;
	/** 是否解冻0:未，1:待审核，2已解冻 */
	private int isThaw;
	/** 订单号 */
	private String orderNo;
	/** 银行名 */
	private String bankN;
	/** 交易变更记录 */
	private TransInfoLog transInfoLog;
	/** 交易状态，1正常，2，退款，3拒付，4，冻结，5解冻 */
	private String transStatus;
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
	public String getRelNo() {
		return relNo;
	}
	public void setRelNo(String relNo) {
		this.relNo = relNo;
	}
	public BigDecimal getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
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
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	public String getParentAgentNo() {
		return parentAgentNo;
	}
	public void setParentAgentNo(String parentAgentNo) {
		this.parentAgentNo = parentAgentNo;
	}
	public Timestamp getTransDate() {
		return transDate;
	}
	public void setTransDate(Timestamp transDate) {
		this.transDate = transDate;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getPosNo() {
		return posNo;
	}
	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}
	public int getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(int currencyId) {
		this.currencyId = currencyId;
	}
	public BigDecimal getMerForAmount() {
		return merForAmount;
	}
	public void setMerForAmount(BigDecimal merForAmount) {
		this.merForAmount = merForAmount;
	}
	public BigDecimal getAgentForAmount() {
		return agentForAmount;
	}
	public void setAgentForAmount(BigDecimal agentForAmount) {
		this.agentForAmount = agentForAmount;
	}
	public BigDecimal getParentAgentForAmount() {
		return parentAgentForAmount;
	}
	public void setParentAgentForAmount(BigDecimal parentAgentForAmount) {
		this.parentAgentForAmount = parentAgentForAmount;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getBankRefNo() {
		return bankRefNo;
	}
	public void setBankRefNo(String bankRefNo) {
		this.bankRefNo = bankRefNo;
	}
	public String getBankPosNo() {
		return bankPosNo;
	}
	public void setBankPosNo(String bankPosNo) {
		this.bankPosNo = bankPosNo;
	}
	public String getBankBatchNo() {
		return bankBatchNo;
	}
	public void setBankBatchNo(String bankBatchNo) {
		this.bankBatchNo = bankBatchNo;
	}
	public String getAutoCode() {
		return autoCode;
	}
	public void setAutoCode(String autoCode) {
		this.autoCode = autoCode;
	}
	public String getBankTransDate() {
		return bankTransDate;
	}
	public void setBankTransDate(String bankTransDate) {
		this.bankTransDate = bankTransDate;
	}
	public String getBankTransTime() {
		return bankTransTime;
	}
	public void setBankTransTime(String bankTransTime) {
		this.bankTransTime = bankTransTime;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getAcquiringBank() {
		return acquiringBank;
	}
	public void setAcquiringBank(String acquiringBank) {
		this.acquiringBank = acquiringBank;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public Timestamp getTransDateStart() {
		return transDateStart;
	}
	public void setTransDateStart(Timestamp transDateStart) {
		this.transDateStart = transDateStart;
	}
	public Timestamp getTransDateEnd() {
		return transDateEnd;
	}
	public void setTransDateEnd(Timestamp transDateEnd) {
		this.transDateEnd = transDateEnd;
	}
	public Timestamp getSettleDateStart() {
		return settleDateStart;
	}
	public void setSettleDateStart(Timestamp settleDateStart) {
		this.settleDateStart = settleDateStart;
	}
	public Timestamp getSettleDateEnd() {
		return settleDateEnd;
	}
	public void setSettleDateEnd(Timestamp settleDateEnd) {
		this.settleDateEnd = settleDateEnd;
	}
	public String getCheckedBatchNo() {
		return checkedBatchNo;
	}
	public void setCheckedBatchNo(String checkedBatchNo) {
		this.checkedBatchNo = checkedBatchNo;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getCheckToNo() {
		return checkToNo;
	}
	public void setCheckToNo(String checkToNo) {
		this.checkToNo = checkToNo;
	}
	public String getCheckNo() {
		return checkNo;
	}
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public String getIPAddress() {
		return IPAddress;
	}
	public void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getMerSettleCurrency() {
		return merSettleCurrency;
	}
	public void setMerSettleCurrency(String merSettleCurrency) {
		this.merSettleCurrency = merSettleCurrency;
	}
	public BigDecimal getMerSettleAmount() {
		return merSettleAmount;
	}
	public void setMerSettleAmount(BigDecimal merSettleAmount) {
		this.merSettleAmount = merSettleAmount;
	}
	public BigDecimal getBondAmount() {
		return bondAmount;
	}
	public void setBondAmount(BigDecimal bondAmount) {
		this.bondAmount = bondAmount;
	}
	public int getIschecked() {
		return ischecked;
	}
	public void setIschecked(int ischecked) {
		this.ischecked = ischecked;
	}
	public int getRvfId() {
		return rvfId;
	}
	public void setRvfId(int rvfId) {
		this.rvfId = rvfId;
	}
	public int getIsRefund() {
		return isRefund;
	}
	public void setIsRefund(int isRefund) {
		this.isRefund = isRefund;
	}
	public int getIsDishonor() {
		return isDishonor;
	}
	public void setIsDishonor(int isDishonor) {
		this.isDishonor = isDishonor;
	}
	public int getIsFrozen() {
		return isFrozen;
	}
	public void setIsFrozen(int isFrozen) {
		this.isFrozen = isFrozen;
	}
	public int getIsThaw() {
		return isThaw;
	}
	public void setIsThaw(int isThaw) {
		this.isThaw = isThaw;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getBankN() {
		return bankN;
	}
	public void setBankN(String bankN) {
		this.bankN = bankN;
	}
	public TransInfoLog getTransInfoLog() {
		return transInfoLog;
	}
	public void setTransInfoLog(TransInfoLog transInfoLog) {
		this.transInfoLog = transInfoLog;
	}
	public String getTransStatus() {
		return transStatus;
	}
	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = last;
	}

	
}
