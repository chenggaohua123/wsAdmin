package com.gateway.merchantmgr.model;

import java.sql.Timestamp;
import java.util.List;

import com.gateway.sysmgr.model.GwPicInfo;

public class MerchantInfo {
	private int id;
	private String merNo;
	private String merchantName;
	private String phoneNo;
	private int enabled;
	private int type;
	private String countryCode;
	private String state;
	private String city;
	private String address;
	private String accountName;
	private String accountState;
	private String accountCity;
	private String accountContryCode;
	private String accountNo;
	private String accountAddress;
	private String applyFileDataPath;
	private Timestamp activationDate;
	private Timestamp expireDate;
	private Timestamp regDate;
	private String sales;

	private Timestamp updateDate;
	private String updatePerson;
	private String agentNo;
	private String relPhone;
	
	
	private String agentName;
	private String industry;
	private String email;
	
	private List<GwPicInfo> list;
	
	private String iDCardNo;
	
	private GwPicInfo picInfo;
	/** 0：未复核，1：已复核 */
	private String reState;
	private String topRate;
	private String agentMerRate;
	
	private String qq;
	private String linkName;
	private String merCurrencyType;
	private String linkPhoneNo;
	private String oaOrderNo;
	private String accountStatus;
	private String merLev;
	private String dirStatus;
	private String remark;
	private String hisFee;
	private String merchantAddress;
	private String swift;
	private String bankAddress;
	/** * 联行号 */
	private String bankNo;
	
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
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
	public String getDirStatus() {
		return dirStatus;
	}
	public void setDirStatus(String dirStatus) {
		this.dirStatus = dirStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getHisFee() {
		return hisFee;
	}
	public void setHisFee(String hisFee) {
		this.hisFee = hisFee;
	}
	public String getMerLev() {
		return merLev;
	}
	public void setMerLev(String merLev) {
		this.merLev = merLev;
	}
	public String getOaOrderNo() {
		return oaOrderNo;
	}
	public void setOaOrderNo(String oaOrderNo) {
		this.oaOrderNo = oaOrderNo;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getMerCurrencyType() {
		return merCurrencyType;
	}
	public void setMerCurrencyType(String merCurrencyType) {
		this.merCurrencyType = merCurrencyType;
	}
	public String getLinkPhoneNo() {
		return linkPhoneNo;
	}
	public void setLinkPhoneNo(String linkPhoneNo) {
		this.linkPhoneNo = linkPhoneNo;
	}
	public String getTopRate() {
		return topRate;
	}
	public void setTopRate(String topRate) {
		this.topRate = topRate;
	}
	public String getAgentMerRate() {
		return agentMerRate;
	}
	public void setAgentMerRate(String agentMerRate) {
		this.agentMerRate = agentMerRate;
	}
	public String getReState() {
		return reState;
	}

	public void setReState(String reState) {
		this.reState = reState;
	}
	public GwPicInfo getPicInfo() {
		return picInfo;
	}

	public void setPicInfo(GwPicInfo picInfo) {
		this.picInfo = picInfo;
	}

	public String getiDCardNo() {
		return iDCardNo;
	}

	public void setiDCardNo(String iDCardNo) {
		this.iDCardNo = iDCardNo;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<GwPicInfo> getList() {
		return list;
	}

	public void setList(List<GwPicInfo> list) {
		this.list = list;
	}

	public String getRelPhone() {
		return relPhone;
	}

	public void setRelPhone(String relPhone) {
		this.relPhone = relPhone;
	}

	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
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

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getApplyFileDataPath() {
		return applyFileDataPath;
	}

	public void setApplyFileDataPath(String applyFileDataPath) {
		this.applyFileDataPath = applyFileDataPath;
	}


	public Timestamp getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Timestamp activationDate) {
		this.activationDate = activationDate;
	}

	public Timestamp getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Timestamp expireDate) {
		this.expireDate = expireDate;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

}
