package com.gateway.transmgr.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.gateway.api.model.PicInfo;
import com.gateway.refund.model.TransInfoLog;
import com.gateway.sysmgr.model.CardBinInfo;

public class TransInfo {
	private int id;
	private String tradeNo;
	private String tradeOldNo;
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
	/** 授权码 */
	private String autoCode;
	private String bankTransDate;
	private String bankTransTime;
	private String currencyName;
	private String bankName;
	private double total;
	private String acquiringBank;
	private String cardName;
	private BigDecimal singleFee;

	private Timestamp transDateStart;
	private Timestamp transDateEnd;

	private Timestamp settleDateStart;
	private Timestamp settleDateEnd;
	private String checkedBatchNo;
	private String merchantName;

	private String checkToNo;
	private String checkNo;
	private String middle;
	private String last;
	private String respMsg;
	private String email;
	private String sixAndFourCardNo;
	/** 新增字段 */
	// 商户交易币种
	private String merBusCurrency;
	// 商户交易金额
	private BigDecimal merTransAmount;
	// 银行交易币种
	private String bankCurrency;
	// 银行交易金额
	private BigDecimal bankTransAmount;
	// 商户结算币种
	private String merSettleCurrency;
	// 商户结算金额
	private BigDecimal merSettleAmount;
	/** 保证金费率 */
	private BigDecimal bondAmount;
	// 是否勾兑
	private int ischecked;
	/** 自联接ID */
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

	/** 异常单查询 */
	/** 操作币种 */
	private String transCurrency;
	/** 操作金额 */
	private BigDecimal transMoney;
	/** 异常单状态 */
	private int status;
	/** 异常单类型 */
	private String transLogType;
	/** 制服网址 */
	private String payWebSite;
	/** 卡信息 --->国家 */
	private String cardCountry;
	/** 卡信息 --->详细地址 */
	private String cardAddress;
	/** 持卡人姓名 */
	private String cardFullName;
	/** 持卡人电话 */
	private String cardFullPhone;
	/** 持卡人邮箱 */
	private String cardEmail;
	/** 收货信息 --->国家 */
	private String grCountry;
	/** 收货信息 --->详细地址 */
	private String grAddress;
	/** 收货信息 --->邮编 */
	private String grZipCode;

	private Timestamp reRunDate;

	private int reRunStatus;
	private String webInfo;
	// 退款状态
	private String refundStatus;
	private String refundAmount;
	// 拒付状态
	private String dishonorStatus;
	private String dishonorAmount;
	// 冻结状态
	private String frozenStatus;
	private String frozenAmount;

	private byte[] goodsInfo;
	private String goodsInfoStr;
	private String payWebsite;
	private String ipCountry;

	// 收货国家

	private String grState;
	private String grCity;

	private String cardState;
	private String cardCity;

	private String cardZipCode;

	private String trackNo;
	// 货运方式
	private String iogistics;
	private String grPerName;
	private String grphoneNumber;
	private String ipAddress;
	private String IPAddress;
	private String acquirer;
	private String riskScore;
	private String access;
	
	private String transDishonor;//0未拒付，1，已拒付
	private String transFrozen;//0未冻结，1，已冻结
	private String transThaw;//0未解冻，1，已解冻
	private String transRefund;//0未退款，1，已退款
	private BigDecimal disFee;
	private BigDecimal refundFee;
	private int transRefundFeeStatus;
	
	private String bankRealAmount;
	private String bankRealCurrency;
	private String bankRealRate;
	
	private String oldCurrencyName;
	private String newCurrencyName;
	
	
	
	private List<GoodsInfo> goodsInfos;
	
	private CardBinInfo cbInfo;
	
	
	
	public CardBinInfo getCbInfo() {
		return cbInfo;
	}
	public void setCbInfo(CardBinInfo cbInfo) {
		this.cbInfo = cbInfo;
	}
	public String getOldCurrencyName() {
		return oldCurrencyName;
	}
	public void setOldCurrencyName(String oldCurrencyName) {
		this.oldCurrencyName = oldCurrencyName;
	}
	public String getNewCurrencyName() {
		return newCurrencyName;
	}
	public void setNewCurrencyName(String newCurrencyName) {
		this.newCurrencyName = newCurrencyName;
	}
	public String getBankRealAmount() {
		return bankRealAmount;
	}
	public void setBankRealAmount(String bankRealAmount) {
		this.bankRealAmount = bankRealAmount;
	}
	public String getBankRealCurrency() {
		return bankRealCurrency;
	}
	public void setBankRealCurrency(String bankRealCurrency) {
		this.bankRealCurrency = bankRealCurrency;
	}
	public String getBankRealRate() {
		return bankRealRate;
	}
	public void setBankRealRate(String bankRealRate) {
		this.bankRealRate = bankRealRate;
	}
	public List<GoodsInfo> getGoodsInfos() {
		return goodsInfos;
	}
	public void setGoodsInfos(List<GoodsInfo> goodsInfos) {
		this.goodsInfos = goodsInfos;
	}
	public String getGoodsInfoStr() {
		return goodsInfoStr;
	}
	public void setGoodsInfoStr(String goodsInfoStr) {
		this.goodsInfoStr = goodsInfoStr;
	}
	public BigDecimal getDisFee() {
		return disFee;
	}
	public void setDisFee(BigDecimal disFee) {
		this.disFee = disFee;
	}
	public BigDecimal getRefundFee() {
		return refundFee;
	}
	public void setRefundFee(BigDecimal refundFee) {
		this.refundFee = refundFee;
	}
	public int getTransRefundFeeStatus() {
		return transRefundFeeStatus;
	}
	public void setTransRefundFeeStatus(int transRefundFeeStatus) {
		this.transRefundFeeStatus = transRefundFeeStatus;
	}
	public String getTradeOldNo() {
		return tradeOldNo;
	}
	public void setTradeOldNo(String tradeOldNo) {
		this.tradeOldNo = tradeOldNo;
	}
	public String getIPAddress() {
		return IPAddress;
	}
	public void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}
	public BigDecimal getSingleFee() {
		return singleFee;
	}
	public void setSingleFee(BigDecimal singleFee) {
		this.singleFee = singleFee;
	}

	public int getReRunStatus() {
		return reRunStatus;
	}

	public void setReRunStatus(int reRunStatus) {
		this.reRunStatus = reRunStatus;
	}

	public Timestamp getReRunDate() {
		return reRunDate;
	}

	public void setReRunDate(Timestamp reRunDate) {
		this.reRunDate = reRunDate;
	}

	public String getCardEmail() {
		return cardEmail;
	}

	public void setCardEmail(String cardEmail) {
		this.cardEmail = cardEmail;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTransCurrency() {
		return transCurrency;
	}

	public void setTransCurrency(String transCurrency) {
		this.transCurrency = transCurrency;
	}

	public BigDecimal getTransMoney() {
		return transMoney;
	}

	public void setTransMoney(BigDecimal transMoney) {
		this.transMoney = transMoney;
	}

	public String getTransLogType() {
		return transLogType;
	}

	public void setTransLogType(String transLogType) {
		this.transLogType = transLogType;
	}

	public String getPayWebSite() {
		return payWebSite;
	}

	public void setPayWebSite(String payWebSite) {
		this.payWebSite = payWebSite;
	}

	public String getCardCountry() {
		return cardCountry;
	}

	public void setCardCountry(String cardCountry) {
		this.cardCountry = cardCountry;
	}

	public String getCardAddress() {
		return cardAddress;
	}

	public void setCardAddress(String cardAddress) {
		this.cardAddress = cardAddress;
	}

	public String getCardFullName() {
		return cardFullName;
	}

	public void setCardFullName(String cardFullName) {
		this.cardFullName = cardFullName;
	}

	public String getCardFullPhone() {
		return cardFullPhone;
	}

	public void setCardFullPhone(String cardFullPhone) {
		this.cardFullPhone = cardFullPhone;
	}

	public String getGrCountry() {
		return grCountry;
	}

	public void setGrCountry(String grCountry) {
		this.grCountry = grCountry;
	}

	public String getGrAddress() {
		return grAddress;
	}

	public void setGrAddress(String grAddress) {
		this.grAddress = grAddress;
	}

	public String getGrZipCode() {
		return grZipCode;
	}

	public void setGrZipCode(String grZipCode) {
		this.grZipCode = grZipCode;
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

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	
	public TransInfoLog getTransInfoLog() {
		return transInfoLog;
	}

	public void setTransInfoLog(TransInfoLog transInfoLog) {
		this.transInfoLog = transInfoLog;
	}

	public String getBankN() {
		return bankN;
	}

	public void setBankN(String bankN) {
		this.bankN = bankN;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public int getIschecked() {
		return ischecked;
	}

	public void setIschecked(int ischecked) {
		this.ischecked = ischecked;
	}

	public BigDecimal getBondAmount() {
		return bondAmount;
	}

	public void setBondAmount(BigDecimal bondAmount) {
		this.bondAmount = bondAmount;
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

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getCheckedBatchNo() {
		return checkedBatchNo;
	}

	public void setCheckedBatchNo(String checkedBatchNo) {
		this.checkedBatchNo = checkedBatchNo;
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

	private PicInfo picInfo;

	public PicInfo getPicInfo() {
		return picInfo;
	}

	public void setPicInfo(PicInfo picInfo) {
		this.picInfo = picInfo;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
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

	public String getWebInfo() {
		return webInfo;
	}

	public void setWebInfo(String webInfo) {
		this.webInfo = webInfo;
	}

	public String getSixAndFourCardNo() {
		return sixAndFourCardNo;
	}

	public void setSixAndFourCardNo(String sixAndFourCardNo) {
		this.sixAndFourCardNo = sixAndFourCardNo;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getDishonorStatus() {
		return dishonorStatus;
	}

	public void setDishonorStatus(String dishonorStatus) {
		this.dishonorStatus = dishonorStatus;
	}

	public String getDishonorAmount() {
		return dishonorAmount;
	}

	public void setDishonorAmount(String dishonorAmount) {
		this.dishonorAmount = dishonorAmount;
	}

	public String getFrozenStatus() {
		return frozenStatus;
	}

	public void setFrozenStatus(String frozenStatus) {
		this.frozenStatus = frozenStatus;
	}

	public String getFrozenAmount() {
		return frozenAmount;
	}

	public void setFrozenAmount(String frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	public byte[] getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(byte[] goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public String getPayWebsite() {
		return payWebsite;
	}

	public void setPayWebsite(String payWebsite) {
		this.payWebsite = payWebsite;
	}

	public String getGrState() {
		return grState;
	}

	public void setGrState(String grState) {
		this.grState = grState;
	}

	public String getGrCity() {
		return grCity;
	}

	public void setGrCity(String grCity) {
		this.grCity = grCity;
	}

	public String getCardState() {
		return cardState;
	}

	public void setCardState(String cardState) {
		this.cardState = cardState;
	}

	public String getCardCity() {
		return cardCity;
	}

	public void setCardCity(String cardCity) {
		this.cardCity = cardCity;
	}

	public String getCardZipCode() {
		return cardZipCode;
	}

	public void setCardZipCode(String cardZipCode) {
		this.cardZipCode = cardZipCode;
	}

	public String getTrackNo() {
		return trackNo;
	}

	public void setTrackNo(String trackNo) {
		this.trackNo = trackNo;
	}

	public String getIogistics() {
		return iogistics;
	}

	public void setIogistics(String iogistics) {
		this.iogistics = iogistics;
	}

	public String getGrPerName() {
		return grPerName;
	}

	public void setGrPerName(String grPerName) {
		this.grPerName = grPerName;
	}

	public String getGrphoneNumber() {
		return grphoneNumber;
	}

	public void setGrphoneNumber(String grphoneNumber) {
		this.grphoneNumber = grphoneNumber;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getAcquirer() {
		return acquirer;
	}

	public void setAcquirer(String acquirer) {
		this.acquirer = acquirer;
	}

	public String getRiskScore() {
		return riskScore;
	}

	public void setRiskScore(String riskScore) {
		this.riskScore = riskScore;
	}
	public String getIpCountry() {
		return ipCountry;
	}
	public void setIpCountry(String ipCountry) {
		this.ipCountry = ipCountry;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public String getTransDishonor() {
		return transDishonor;
	}
	public void setTransDishonor(String transDishonor) {
		this.transDishonor = transDishonor;
	}
	public String getTransFrozen() {
		return transFrozen;
	}
	public void setTransFrozen(String transFrozen) {
		this.transFrozen = transFrozen;
	}
	public String getTransThaw() {
		return transThaw;
	}
	public void setTransThaw(String transThaw) {
		this.transThaw = transThaw;
	}
	public String getTransRefund() {
		return transRefund;
	}
	public void setTransRefund(String transRefund) {
		this.transRefund = transRefund;
	}

}
