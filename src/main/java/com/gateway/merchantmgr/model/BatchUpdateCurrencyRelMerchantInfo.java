package com.gateway.merchantmgr.model;

public class BatchUpdateCurrencyRelMerchantInfo {
	private String merNo;
	private String terNo;
	private String cardType;
	private String oriCurrencyId;
	private String currencyId;
	private int enabled;
	private String updateBy;
	private String [] merNos;
	private String [] terNos;
	

	public String[] getMerNos() {
		return merNos;
	}

	public void setMerNos(String[] merNos) {
		this.merNos = merNos;
	}

	public String[] getTerNos() {
		return terNos;
	}

	public void setTerNos(String[] terNos) {
		this.terNos = terNos;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
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

	public String getOriCurrencyId() {
		return oriCurrencyId;
	}

	public void setOriCurrencyId(String oriCurrencyId) {
		this.oriCurrencyId = oriCurrencyId;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

}
