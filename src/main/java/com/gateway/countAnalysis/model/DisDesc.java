package com.gateway.countAnalysis.model;

public class DisDesc {//拒付占比
	private String disReason;//拒付原因
	private int disCount;//拒付笔数
	private String disReasonId;//拒付原因ID
	private double disRate;//拒付占比
	public String getDisReason() {
		return disReason;
	}
	public void setDisReason(String disReason) {
		this.disReason = disReason;
	}
	public int getDisCount() {
		return disCount;
	}
	public void setDisCount(int disCount) {
		this.disCount = disCount;
	}
	public String getDisReasonId() {
		return disReasonId;
	}
	public void setDisReasonId(String disReasonId) {
		this.disReasonId = disReasonId;
	}
	public double getDisRate() {
		return disRate;
	}
	public void setDisRate(double disRate) {
		this.disRate = disRate;
	}
	
	
}
