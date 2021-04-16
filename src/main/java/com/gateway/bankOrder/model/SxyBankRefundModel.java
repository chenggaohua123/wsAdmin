package com.gateway.bankOrder.model;

public class SxyBankRefundModel {

	private String status;		//支付状态  初始化 INIT,成功 SUCCESS,失败 FAILED,处理中 PROCESSING
	private String refundId;	//退款流水号
	private String requestId;	//退款请求号(商户自定义请求号，不能重复)
	private String bankNumber;	//退款银行订单号
	private String refundAmount;//退款金额
	private String refundSubmissionTime;//退款提交时间(yyyy-MM-dd HH:mm:ss)
	private String refundCompleteTime;//退款完成时间(yyyy-MM-dd HH:mm:ss)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getRefundSubmissionTime() {
		return refundSubmissionTime;
	}
	public void setRefundSubmissionTime(String refundSubmissionTime) {
		this.refundSubmissionTime = refundSubmissionTime;
	}
	public String getRefundCompleteTime() {
		return refundCompleteTime;
	}
	public void setRefundCompleteTime(String refundCompleteTime) {
		this.refundCompleteTime = refundCompleteTime;
	}
	
	
}
