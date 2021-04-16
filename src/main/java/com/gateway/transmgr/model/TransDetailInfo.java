package com.gateway.transmgr.model;

import java.util.List;



public class TransDetailInfo {
	private String merNo; 
	private String tradeNo; 
	private String orderNo; 
	private String transDate; 
	private String merBusCurrency; 
	private String merTransAmount; 
	private String bankCurrency;
	private String bankTransAmount;
	private String merSettleCurrency; 
	private String merSettleAmount; 
	private String merForAmount; 
	private String bondAmount; 
	private String cardType; 
	private String respCode; 
	private String respMsg; 
	private String terNo; 
	private String checkNo; 
	private String middle;
	private String last; 
	private String acquirer; 
	private String payWebSite; 
	private String email; 
	private String grPerName; 
	private String grphoneNumber; 
	private String ipAddress; 
	private String grCountry; 
	private String grState; 
	private String grCity; 
	private String grAddress; 
	private String grZipCode; 
	private String cardCountry;	
	private String cardState; 
	private String cardCity; 
	private String cardAddress; 
	private String cardZipCode; 
	private String cardFullName; 
	private String cardEmail; 
	private String cardFullPhone; 
	private String trackNo; 
	private String iogistics; 
	private String refundStatus;                  
	private String refundAmount; 
	private String dishonorStatus;              
	private String dishonorAmount; 
	private String frozenStatus; 
	private String frozenAmount; 
	private List<GoodsInfo> goodsInfo;
	private byte[] goodsInfoByte;
	private String sixAndFourCardNo;
	private String singleFee;
	private String webInfo;
	/** 风险、欺诈 分数 */
	private String riskScore;
	/** 是否勾兑 */
	private String ischecked;
	/** 结算批次号 */
	private String batchNo;
	/** 支付通道 */
	private String currencyName;
	/** 交易类型 */
	private String transType;
	/** 交易汇率 */
	private String transRate;
	
	private int access;
	
	private String cardFullNo;
	
	private String exceptionDate;
	
	
	private String autoCode;
	
	private String ipCountry;
	private String CPDDate;
	
	
	private String isFake;
	
	private String bankPosNo;
	
	
	
	public String getBankPosNo() {
		return bankPosNo;
	}
	public void setBankPosNo(String bankPosNo) {
		this.bankPosNo = bankPosNo;
	}
	public String getIsFake() {
		return isFake;
	}
	public void setIsFake(String isFake) {
		this.isFake = isFake;
	}
	public String getCPDDate() {
		return CPDDate;
	}
	public void setCPDDate(String cPDDate) {
		CPDDate = cPDDate;
	}
	public String getIpCountry() {
		return ipCountry;
	}
	public void setIpCountry(String ipCountry) {
		this.ipCountry = ipCountry;
	}
	public String getAutoCode() {
		return autoCode;
	}
	public void setAutoCode(String autoCode) {
		this.autoCode = autoCode;
	}
	public String getExceptionDate() {
		return exceptionDate;
	}
	public void setExceptionDate(String exceptionDate) {
		this.exceptionDate = exceptionDate;
	}
	public String getCardFullNo() {
		return cardFullNo;
	}
	public void setCardFullNo(String cardFullNo) {
		this.cardFullNo = cardFullNo;
	}
	public String getMiddle() {
		return middle;
	}
	public void setMiddle(String middle) {
		this.middle = middle;
	}
	public int getAccess() {
		return access;
	}
	public void setAccess(int access) {
		this.access = access;
	}
	public String getTransRate() {
		return transRate;
	}
	public void setTransRate(String transRate) {
		this.transRate = transRate;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
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
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getIschecked() {
		return ischecked;
	}
	public void setIschecked(String ischecked) {
		this.ischecked = ischecked;
	}
	public String getRiskScore() {
		return riskScore;
	}
	public void setRiskScore(String riskScore) {
		this.riskScore = riskScore;
	}
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public String getWebInfo() {
		return webInfo;
	}
	public void setWebInfo(String webInfo) {
		this.webInfo = webInfo;
	}
	public String getSingleFee() {
		return singleFee;
	}
	public void setSingleFee(String singleFee) {
		this.singleFee = singleFee;
	}
	public byte[] getGoodsInfoByte() {
		return goodsInfoByte;
	}
	public void setGoodsInfoByte(byte[] goodsInfoByte) {
		this.goodsInfoByte = goodsInfoByte;
	}
	public String getCardFullPhone() {
		return cardFullPhone;
	}
	public void setCardFullPhone(String cardFullPhone) {
		this.cardFullPhone = cardFullPhone;
	}
	public String getCardFullName() {
		return cardFullName;
	}
	public void setCardFullName(String cardFullName) {
		this.cardFullName = cardFullName;
	}
	public String getCardEmail() {
		return cardEmail;
	}
	public void setCardEmail(String cardEmail) {
		this.cardEmail = cardEmail;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getMerBusCurrency() {
		return merBusCurrency;
	}
	public void setMerBusCurrency(String merBusCurrency) {
		this.merBusCurrency = merBusCurrency;
	}
	public String getMerTransAmount() {
		return merTransAmount;
	}
	public void setMerTransAmount(String merTransAmount) {
		this.merTransAmount = merTransAmount;
	}
	public String getMerSettleCurrency() {
		return merSettleCurrency;
	}
	public void setMerSettleCurrency(String merSettleCurrency) {
		this.merSettleCurrency = merSettleCurrency;
	}
	public String getMerSettleAmount() {
		return merSettleAmount;
	}
	public void setMerSettleAmount(String merSettleAmount) {
		this.merSettleAmount = merSettleAmount;
	}
	public String getMerForAmount() {
		return merForAmount;
	}
	public void setMerForAmount(String merForAmount) {
		this.merForAmount = merForAmount;
	}
	public String getBondAmount() {
		return bondAmount;
	}
	public void setBondAmount(String bondAmount) {
		this.bondAmount = bondAmount;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public String getTerNo() {
		return terNo;
	}
	public void setTerNo(String terNo) {
		this.terNo = terNo;
	}
	public String getCheckNo() {
		return checkNo;
	}
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = last;
	}
	public String getAcquirer() {
		return acquirer;
	}
	public void setAcquirer(String acquirer) {
		this.acquirer = acquirer;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getGrCountry() {
		return grCountry;
	}
	public void setGrCountry(String grCountry) {
		this.grCountry = grCountry;
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
	public String getCardCountry() {
		return cardCountry;
	}
	public void setCardCountry(String cardCountry) {
		this.cardCountry = cardCountry;
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
	public String getCardAddress() {
		return cardAddress;
	}
	public void setCardAddress(String cardAddress) {
		this.cardAddress = cardAddress;
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
	public List<GoodsInfo> getGoodsInfo() {
		return goodsInfo;
	}
	public void setGoodsInfo(List<GoodsInfo> goodsInfo) {
		this.goodsInfo = goodsInfo;
	}
	public String getPayWebSite() {
		return payWebSite;
	}
	public void setPayWebSite(String payWebSite) {
		this.payWebSite = payWebSite;
	}
	public String getSixAndFourCardNo() {
		return sixAndFourCardNo;
	}
	public void setSixAndFourCardNo(String sixAndFourCardNo) {
		this.sixAndFourCardNo = sixAndFourCardNo;
	}
	
	
}
