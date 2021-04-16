package com.gateway.countAnalysis.model;

import java.util.HashMap;
import java.util.Map;

public class BrandCountInfo {
	private String brand;
	private String brands;
	private int transCount;
	private int successCount;
	private int riskCount;
	private int failCount;
	private int dupCount;
	private int disCount;
	private int refundCount;
	private int comCount;
	private int fakeCount;
	private double transRate;
	private String currency;
	private double transAmount;
	private HashMap<String,Double> transAmountMap=new HashMap<String,Double>();
	private double successAmount;
	private HashMap<String,Double> successAmountMap=new HashMap<String,Double>();
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(double transAmount) {
		this.transAmount = transAmount;
	}
	public double getSuccessAmount() {
		return successAmount;
	}
	public void setSuccessAmount(double successAmount) {
		this.successAmount = successAmount;
	}
	public double getTransRate() {
		return transRate;
	}
	public void setTransRate(double transRate) {
		this.transRate = transRate;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getBrands() {
		return brands;
	}
	public void setBrands(String brands) {
		this.brands = brands;
	}
	public int getTransCount() {
		return transCount;
	}
	public void setTransCount(int transCount) {
		this.transCount = transCount;
	}
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	public int getRiskCount() {
		return riskCount;
	}
	public void setRiskCount(int riskCount) {
		this.riskCount = riskCount;
	}
	public int getFailCount() {
		return failCount;
	}
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}
	public int getDupCount() {
		return dupCount;
	}
	public void setDupCount(int dupCount) {
		this.dupCount = dupCount;
	}
	public int getDisCount() {
		return disCount;
	}
	public void setDisCount(int disCount) {
		this.disCount = disCount;
	}
	public int getRefundCount() {
		return refundCount;
	}
	public void setRefundCount(int refundCount) {
		this.refundCount = refundCount;
	}
	public int getComCount() {
		return comCount;
	}
	public void setComCount(int comCount) {
		this.comCount = comCount;
	}
	public int getFakeCount() {
		return fakeCount;
	}
	public void setFakeCount(int fakeCount) {
		this.fakeCount = fakeCount;
	}
	public HashMap<String, Double> getTransAmountMap() {
		return transAmountMap;
	}
	public void setTransAmountMap(HashMap<String, Double> transAmountMap) {
		this.transAmountMap = transAmountMap;
	}
	public HashMap<String, Double> getSuccessAmountMap() {
		return successAmountMap;
	}
	public void setSuccessAmountMap(HashMap<String, Double> successAmountMap) {
		this.successAmountMap = successAmountMap;
	}
	public BrandCountInfo add(BrandCountInfo info){
		this.setComCount(comCount+info.getComCount());
		this.setDisCount(disCount+info.getDisCount());
		this.setDupCount(dupCount+info.getDupCount());
		this.setFailCount(failCount+info.getFailCount());
		this.setFakeCount(fakeCount+info.getFakeCount());
		this.setRefundCount(refundCount+info.getRefundCount());
		this.setRiskCount(riskCount+info.getRiskCount());
		this.setSuccessCount(successCount+info.getSuccessCount());
		this.setTransCount(transCount+info.getTransCount());
		if(this.transAmountMap.containsKey(info.getCurrency())){//已经含有
			this.transAmountMap.put(info.getCurrency(), transAmountMap.get(info.getCurrency())+info.getTransAmount());
			this.successAmountMap.put(info.getCurrency(),successAmountMap.get(info.getCurrency())+info.getSuccessAmount());
		}else{
			this.transAmountMap.put(info.getCurrency(), info.getTransAmount());
			this.successAmountMap.put(info.getCurrency(), info.getSuccessAmount());
		}
		return this;
	}
	
	public String toString() {
			
		return this.brand+"#"+this.transAmountMap.toString()+"#"+this.successAmountMap.toString();
	}
	
}
