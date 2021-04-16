package com.gateway.countAnalysis.model;

public class CompRateInfo {
	private String time;
	private String type;
	private int transSuccessCount;
	private int compCount;
	private double comRate;
	private int comToDisCount;
	private double comToDisRate;
	private byte[] tradeNo;
	private String tradeNos;
	private int successCount;
	private int comCount;
	private int disCount;
	
	
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	public int getComCount() {
		return comCount;
	}
	public void setComCount(int comCount) {
		this.comCount = comCount;
	}
	public int getDisCount() {
		return disCount;
	}
	public void setDisCount(int disCount) {
		this.disCount = disCount;
	}
	public CompRateInfo(){}
	public CompRateInfo(String time,String type){
		this.time=time;
		this.type=type;
		tradeNos="";
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
	public int getCompCount() {
		return compCount;
	}
	public void setCompCount(int compCount) {
		this.compCount = compCount;
	}
	public double getComRate() {
		return comRate;
	}
	public void setComRate(double comRate) {
		this.comRate = comRate;
	}
	public int getComToDisCount() {
		return comToDisCount;
	}
	public void setComToDisCount(int comToDisCount) {
		this.comToDisCount = comToDisCount;
	}
	public double getComToDisRate() {
		return comToDisRate;
	}
	public void setComToDisRate(double comToDisRate) {
		this.comToDisRate = comToDisRate;
	}
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
	
	
}
