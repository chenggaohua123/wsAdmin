package com.gateway.transmgr.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BankSettleDetail {
	private Integer id;
	private Timestamp settleDate;
	private String merchantNo;
	private String terNo;
	private String msgType;
	private String pscode;
	private String cardNo;
	private BigDecimal transAmount;
	private String posNo;
	private Timestamp transDate;
	private String serviceCode;
	private String transtypecode;
	private String isuingBank;
	private String refNo;
	private String oriPosNo;
	private String oriRefNo;
	private Timestamp oriTransDate;
	private BigDecimal forAmount;
	private int batchNo;
	private int checkSucceed;
	public int getCheckSucceed() {
		return checkSucceed;
	}

	public void setCheckSucceed(int checkSucceed) {
		this.checkSucceed = checkSucceed;
	}

	public int getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(int batchNo) {
		this.batchNo = batchNo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Timestamp settleDate) {
		this.settleDate = settleDate;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getTerNo() {
		return terNo;
	}

	public void setTerNo(String terNo) {
		this.terNo = terNo;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getPscode() {
		return pscode;
	}

	public void setPscode(String pscode) {
		this.pscode = pscode;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public BigDecimal getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}

	public String getPosNo() {
		return posNo;
	}

	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}

	public Timestamp getTransDate() {
		return transDate;
	}

	public void setTransDate(Timestamp transDate) {
		this.transDate = transDate;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getTranstypecode() {
		return transtypecode;
	}

	public void setTranstypecode(String transtypecode) {
		this.transtypecode = transtypecode;
	}

	public String getIsuingBank() {
		return isuingBank;
	}

	public void setIsuingBank(String isuingBank) {
		this.isuingBank = isuingBank;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getOriPosNo() {
		return oriPosNo;
	}

	public void setOriPosNo(String oriPosNo) {
		this.oriPosNo = oriPosNo;
	}

	public String getOriRefNo() {
		return oriRefNo;
	}

	public void setOriRefNo(String oriRefNo) {
		this.oriRefNo = oriRefNo;
	}

	public Timestamp getOriTransDate() {
		return oriTransDate;
	}

	public void setOriTransDate(Timestamp oriTransDate) {
		this.oriTransDate = oriTransDate;
	}

	public BigDecimal getForAmount() {
		return forAmount;
	}

	public void setForAmount(BigDecimal forAmount) {
		this.forAmount = forAmount;
	}

}
