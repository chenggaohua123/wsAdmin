package com.gateway.report.transreport.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransTotal {
	private String merNo;
	private String terNo;
	private String cardType;
	private BigDecimal transAmount;
	private String transType;
	private Timestamp transDateStart;
	private Timestamp transDateEnd;
	
	private Timestamp transDate;
	
	private String currencyName;
	private String radType;
	private double forAmount;
	
	private double maxForAmount;
	
	private int count;
	
	
	
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getForAmount() {
		return forAmount;
	}
	public void setForAmount(double forAmount) {
		this.forAmount = forAmount;
	}
	public double getMaxForAmount() {
		return maxForAmount;
	}
	public void setMaxForAmount(double maxForAmount) {
		this.maxForAmount = maxForAmount;
	}
	public String getRadType() {
		return radType;
	}
	public void setRadType(String radType) {
		this.radType = radType;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public Timestamp getTransDate() {
		return transDate;
	}
	public void setTransDate(Timestamp transDate) {
		this.transDate = transDate;
	}
	public Timestamp getTransDateStart() {
		return transDateStart;
	}
	public void setTransDateStart(Timestamp transDateStart) {
		this.transDateStart = transDateStart;
	}
	public Timestamp getTransDateEnd() {
		return transDateEnd;
	}
	public void setTransDateEnd(Timestamp transDateEnd) {
		this.transDateEnd = transDateEnd;
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
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public BigDecimal getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	
}
