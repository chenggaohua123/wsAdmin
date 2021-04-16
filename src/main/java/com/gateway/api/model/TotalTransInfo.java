package com.gateway.api.model;

import java.math.BigDecimal;

public class TotalTransInfo {
	private String cardType;
	private BigDecimal transAmount;
	private BigDecimal forAmount;
	private String merNo;
	private String terNo;
	private String transDate;
	private String transTypeName;

	
	public BigDecimal getForAmount() {
		return forAmount;
	}

	public void setForAmount(BigDecimal forAmount) {
		this.forAmount = forAmount;
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

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getTransTypeName() {
		return transTypeName;
	}

	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}

}
