package com.gateway.bankOrder.model;

public class SxyBankModel {

	private String tradeNo;//铂赢奥订单流水号
	private String status;//响应状态 响应正常SUCCESS 响应异常ERROR
	private String pstatus;//支付状态  初始化 INIT,成功 SUCCESS,失败 FAILED,处理中 PROCESSING
	private String pastatusDescription;//支付状态说明
	private String merchantId;//商户编号 同请求参数
	private String paymentModeAlias;	//支付方式  返回值为 VISA,Master,AE,JCB
	private String requestId;		//订单号  同请求参数
	private String serialNumber;	//首信易支付系统交易流水号
	private String bankNumber;		//银行订单号
	private String orderCurrency;	//订单币种
	private String orderAmount;		//订单金额
	private String submissionTime;	//订单提交时间(yyyy-MM-dd HH:mm:ss)
	private String completeTime;	//订单扣款时间(yyyy-MM-dd HH:mm:ss)
	private String totalRefundCount;//退款总笔数(该支付订单共计退款次数)
	private String totalRefundAmount;//退款总金额(该支付订单共计退款金额)
	private SxyBankRefundModel refundDetails;	//退款详情(创建实体类)
	private String totalRefuseCount;//拒付总笔数(该支付订单共计拒付次数)
	private String totalRefuseAmount;//拒付总金额(该支付订单共计拒付金额)
	private SxyBankRefuseModel refuseDetails;	//拒付详情(创建实体类)
	private String remark;			//备注(在下单请求中提交的备注信息，返回给商户)
	private String hmac;			//参数签名
	
	
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPstatus() {
		return pstatus;
	}
	public void setPstatus(String pstatus) {
		this.pstatus = pstatus;
	}
	public String getPastatusDescription() {
		return pastatusDescription;
	}
	public void setPastatusDescription(String pastatusDescription) {
		this.pastatusDescription = pastatusDescription;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getPaymentModeAlias() {
		return paymentModeAlias;
	}
	public void setPaymentModeAlias(String paymentModeAlias) {
		this.paymentModeAlias = paymentModeAlias;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	public String getOrderCurrency() {
		return orderCurrency;
	}
	public void setOrderCurrency(String orderCurrency) {
		this.orderCurrency = orderCurrency;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getSubmissionTime() {
		return submissionTime;
	}
	public void setSubmissionTime(String submissionTime) {
		this.submissionTime = submissionTime;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	public String getTotalRefundCount() {
		return totalRefundCount;
	}
	public void setTotalRefundCount(String totalRefundCount) {
		this.totalRefundCount = totalRefundCount;
	}
	public String getTotalRefundAmount() {
		return totalRefundAmount;
	}
	public void setTotalRefundAmount(String totalRefundAmount) {
		this.totalRefundAmount = totalRefundAmount;
	}
	public String getTotalRefuseCount() {
		return totalRefuseCount;
	}
	public void setTotalRefuseCount(String totalRefuseCount) {
		this.totalRefuseCount = totalRefuseCount;
	}
	public String getTotalRefuseAmount() {
		return totalRefuseAmount;
	}
	public void setTotalRefuseAmount(String totalRefuseAmount) {
		this.totalRefuseAmount = totalRefuseAmount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
	public SxyBankRefundModel getRefundDetails() {
		return refundDetails;
	}
	public void setRefundDetails(SxyBankRefundModel refundDetails) {
		this.refundDetails = refundDetails;
	}
	public SxyBankRefuseModel getRefuseDetails() {
		return refuseDetails;
	}
	public void setRefuseDetails(SxyBankRefuseModel refuseDetails) {
		this.refuseDetails = refuseDetails;
	}
	
	
}
