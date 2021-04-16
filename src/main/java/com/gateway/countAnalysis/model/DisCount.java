package com.gateway.countAnalysis.model;

public class DisCount {
	private String time;
	private String type;
	private int transSuccessCount;
	private int disCount;
	private double disRate;
	private byte[] tradeNo;
	private String tradeNos;
	
	
	public byte[] getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(byte[] tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getTradeNos() {
		return tradeNos;
	}
	public void setTradeNos(String tradeNos) {
		this.tradeNos = tradeNos;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTransSuccessCount() {
		return transSuccessCount;
	}
	public void setTransSuccessCount(int transSuccessCount) {
		this.transSuccessCount = transSuccessCount;
	}
	public int getDisCount() {
		return disCount;
	}
	public void setDisCount(int disCount) {
		this.disCount = disCount;
	}
	public double getDisRate() {
		return disRate;
	}
	public void setDisRate(double disRate) {
		this.disRate = disRate;
	}
	
	
	
}
