package com.gateway.countAnalysis.model;

import java.sql.Timestamp;


/**
 * 商户交易同比分析
 * @author gaoyuan
 *
 */
public class MerchantTransCountRateInfo {
	private int id;
	private String cycle;
	private int successCount;
	private String merNo;
	private String enabled;/*商户状态*/
	private String type;/*商户性质*/
	private Timestamp activationDate;/*商户开通时间*/
	private int cycleDayCount;
	private double successRate;
	private String merSettleCurrency;
	private double successAmount;
	private int refundCount;
	private int disCount;
	private int signCount;
	private int shipCount;
	private int transTime;//交易时间
	private String lastTransDate;
	private String sales;
	
	
	
	
	
	
	
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	public String getLastTransDate() {
		return lastTransDate;
	}
	public void setLastTransDate(String lastTransDate) {
		this.lastTransDate = lastTransDate;
	}
	public int getTransTime() {
		return transTime;
	}
	public void setTransTime(int transTime) {
		this.transTime = transTime;
	}
	public int getShipCount() {
		return shipCount;
	}
	public void setShipCount(int shipCount) {
		this.shipCount = shipCount;
	}
	public int getSignCount() {
		return signCount;
	}
	public void setSignCount(int signCount) {
		this.signCount = signCount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Timestamp getActivationDate() {
		return activationDate;
	}
	public void setActivationDate(Timestamp activationDate) {
		this.activationDate = activationDate;
	}
	public int getCycleDayCount() {
		return cycleDayCount;
	}
	public void setCycleDayCount(int cycleDayCount) {
		this.cycleDayCount = cycleDayCount;
	}
	public double getSuccessRate() {
		return successRate;
	}
	public void setSuccessRate(double successRate) {
		this.successRate = successRate;
	}
	public String getMerSettleCurrency() {
		return merSettleCurrency;
	}
	public void setMerSettleCurrency(String merSettleCurrency) {
		this.merSettleCurrency = merSettleCurrency;
	}
	public double getSuccessAmount() {
		return successAmount;
	}
	public void setSuccessAmount(double successAmount) {
		this.successAmount = successAmount;
	}
	public int getRefundCount() {
		return refundCount;
	}
	public void setRefundCount(int refundCount) {
		this.refundCount = refundCount;
	}
	public int getDisCount() {
		return disCount;
	}
	public void setDisCount(int disCount) {
		this.disCount = disCount;
	}
	
	
	
	
}
