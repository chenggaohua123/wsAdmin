package com.gateway.api.model;

public class TerInfoRelCurrencyInfo {
	private String merNo;
	private String terNo;
	private int currencyId;
	private String cardType = "0";
	private int enabaled;

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

	public int getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(int currencyId) {
		this.currencyId = currencyId;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public int getEnabaled() {
		return enabaled;
	}

	public void setEnabaled(int enabaled) {
		this.enabaled = enabaled;
	}

}
