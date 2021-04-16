package com.gateway.bankOrder.model;

public class SxyBankRefuseModel {

	private String refuseId;	//拒付流水号
	private String bankNumber;	//拒付银行订单号
	private String refuseAmount;	//拒付金额
	private String refuseCompleteTime;	//拒付完成时间(yyyy-MM-dd HH:mm:ss)
	public String getRefuseId() {
		return refuseId;
	}
	public void setRefuseId(String refuseId) {
		this.refuseId = refuseId;
	}
	public String getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	public String getRefuseAmount() {
		return refuseAmount;
	}
	public void setRefuseAmount(String refuseAmount) {
		this.refuseAmount = refuseAmount;
	}
	public String getRefuseCompleteTime() {
		return refuseCompleteTime;
	}
	public void setRefuseCompleteTime(String refuseCompleteTime) {
		this.refuseCompleteTime = refuseCompleteTime;
	}
	
	
}
