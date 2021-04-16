package com.gateway.rocketmq.model;


public class ResultPayment {

	private String transType;//消费类型
	private String orderNo;//商户流水号
	private String merNo;//商户号
	private String terNo;//终端号
	private String currencyCode;//支付币种
	private String amount;//支付金额
	/** 交易流水号 */
	private String tradeNo;
	/**支付验签码 */ 	
	private String hashcode;
	//支付状态
	private String respCode;
	/** 支付信息 */
	private String respMsg;
	
	private String acquirer;
	private String settleCurrency;
	private String settleAmount;
	
	private String skipTo3DURL;
	/**异步通知的地址 */
	private String merNotifyURL;
	private String shaKey;
	
	
	public String getShaKey() {
		return shaKey;
	}
	public void setShaKey(String shaKey) {
		this.shaKey = shaKey;
	}
	public String getMerNotifyURL() {
		return merNotifyURL;
	}
	public void setMerNotifyURL(String merNotifyURL) {
		this.merNotifyURL = merNotifyURL;
	}
	public String getSkipTo3DURL() {
		return skipTo3DURL;
	}
	public void setSkipTo3DURL(String skipTo3DURL) {
		this.skipTo3DURL = skipTo3DURL;
	}
	public String getSettleCurrency() {
		return settleCurrency;
	}
	public void setSettleCurrency(String settleCurrency) {
		this.settleCurrency = settleCurrency;
	}
	public String getSettleAmount() {
		return settleAmount;
	}
	public void setSettleAmount(String settleAmount) {
		this.settleAmount = settleAmount;
	}
	
	public String getAcquirer() {
		return acquirer;
	}
	public void setAcquirer(String acquirer) {
		this.acquirer = acquirer;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getHashcode() {
		return hashcode;
	}
	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
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
	
	
	
}
