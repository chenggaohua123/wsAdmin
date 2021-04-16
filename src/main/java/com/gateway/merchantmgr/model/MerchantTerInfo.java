package com.gateway.merchantmgr.model;

import java.sql.Timestamp;
import java.util.List;

import com.gateway.sysmgr.model.GwPicInfo;

public class MerchantTerInfo {
	private int id;
	private String merNo;
	private String terNo;
	private String spareTerNo;// 备用终端号
	private int isChangeToSpareTerNo;// 是否切换到备用终端号 0否 1是
	private String serNo;
	private String createby;
	private Timestamp createDate;
	private int enabled;
	private String shaKey;
	private String remark;
	private String terName;
	private String accountName;
	private String accountState;
	private String accountCity;
	private String accountContryCode;
	private String accountNo;
	private String accountAddress;
	private String updateBy;
	private Timestamp updateDate;

	private List<GwPicInfo> list;
	private String phoneNo;
	private String merchantName;
	private String agentNo;
	private String bluetoothName;
	/** 0：未复核，1：已复核 */
	private int reStatus;
	/** 结算币种 */
	private String settleCurrency;
	private String transCurrency;
	private String email;
	/** 源币种 */
	private String sourceCurrencyCode;

	private String brandStatus;

	private int isRisk;
	private String merchantAddress;
	private String swift;
	private String bankAddress;
	/*** 联行号 */
	private String bankNo;
	/** 商户产品类型 在字典管理中设置 默认0未设置 1 */
	private int productType;
	/** 商户渠道 在字典管理中设置 */
	private int merchantChannel;
	/** shopify商户开关 */
	private int shopifyOnOff;

	
	public int getShopifyOnOff() {
		return shopifyOnOff;
	}

	public void setShopifyOnOff(int shopifyOnOff) {
		this.shopifyOnOff = shopifyOnOff;
	}

	public int getProductType() {
		return productType;
	}

	public void setProductType(int productType) {
		this.productType = productType;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public int getMerchantChannel() {
		return merchantChannel;
	}

	public void setMerchantChannel(int merchantChannel) {
		this.merchantChannel = merchantChannel;
	}

	public String getMerchantAddress() {
		return merchantAddress;
	}

	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
	}

	public String getSwift() {
		return swift;
	}

	public void setSwift(String swift) {
		this.swift = swift;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getBrandStatus() {
		return brandStatus;
	}

	public void setBrandStatus(String brandStatus) {
		this.brandStatus = brandStatus;
	}

	public String getSourceCurrencyCode() {
		return sourceCurrencyCode;
	}

	public void setSourceCurrencyCode(String sourceCurrencyCode) {
		this.sourceCurrencyCode = sourceCurrencyCode;
	}

	public String getTransCurrency() {
		return transCurrency;
	}

	public void setTransCurrency(String transCurrency) {
		this.transCurrency = transCurrency;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSettleCurrency() {
		return settleCurrency;
	}

	public void setSettleCurrency(String settleCurrency) {
		this.settleCurrency = settleCurrency;
	}

	public int getReStatus() {
		return reStatus;
	}

	public void setReStatus(int reStatus) {
		this.reStatus = reStatus;
	}

	public String getBluetoothName() {
		return bluetoothName;
	}

	public void setBluetoothName(String bluetoothName) {
		this.bluetoothName = bluetoothName;
	}

	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public List<GwPicInfo> getList() {
		return list;
	}

	public void setList(List<GwPicInfo> list) {
		this.list = list;
	}

	public String getSerNo() {
		return serNo;
	}

	public void setSerNo(String serNo) {
		this.serNo = serNo;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getTerName() {
		return terName;
	}

	public void setTerName(String terName) {
		this.terName = terName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountState() {
		return accountState;
	}

	public void setAccountState(String accountState) {
		this.accountState = accountState;
	}

	public String getAccountCity() {
		return accountCity;
	}

	public void setAccountCity(String accountCity) {
		this.accountCity = accountCity;
	}

	public String getAccountContryCode() {
		return accountContryCode;
	}

	public void setAccountContryCode(String accountContryCode) {
		this.accountContryCode = accountContryCode;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountAddress() {
		return accountAddress;
	}

	public void setAccountAddress(String accountAddress) {
		this.accountAddress = accountAddress;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getCreateby() {
		return createby;
	}

	public void setCreateby(String createby) {
		this.createby = createby;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getShaKey() {
		return shaKey;
	}

	public void setShaKey(String shaKey) {
		this.shaKey = shaKey;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getIsRisk() {
		return isRisk;
	}

	public void setIsRisk(int isRisk) {
		this.isRisk = isRisk;
	}

	public String getSpareTerNo() {
		return spareTerNo;
	}

	public void setSpareTerNo(String spareTerNo) {
		this.spareTerNo = spareTerNo;
	}

	public int getIsChangeToSpareTerNo() {
		return isChangeToSpareTerNo;
	}

	public void setIsChangeToSpareTerNo(int isChangeToSpareTerNo) {
		this.isChangeToSpareTerNo = isChangeToSpareTerNo;
	}

}
