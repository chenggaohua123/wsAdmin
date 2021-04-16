package com.gateway.vipsalesmgr.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class VipSalesInfo {

	private int id;
	private String merNo;
	private String terNo;
	private int salesFlag;
	private String start;
	private String cv;
	private String m;
	private String y;
	private String name;
	private String bankName;
	private String billAddress;
	private String billCity;
	private String billState;
	private String billCountry;
	private String cycleType;
	private int salesCycle;
	private String email;
	private String uniqueID;
	private Timestamp createDate;
	private Timestamp lastModifyDate;
	private int enabled;
	private String currency;
	private BigDecimal amount;
	private String end;
	private String mid;
	private int isRisk;
	private String phone;
	private String website;
	private List<VipSalesLogInfo> logInfo;
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
	public int getSalesFlag() {
		return salesFlag;
	}
	public void setSalesFlag(int salesFlag) {
		this.salesFlag = salesFlag;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getCv() {
		return cv;
	}
	public void setCv(String cv) {
		this.cv = cv;
	}
	public String getM() {
		return m;
	}
	public void setM(String m) {
		this.m = m;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBillAddress() {
		return billAddress;
	}
	public void setBillAddress(String billAddress) {
		this.billAddress = billAddress;
	}
	public String getBillCity() {
		return billCity;
	}
	public void setBillCity(String billCity) {
		this.billCity = billCity;
	}
	public String getBillState() {
		return billState;
	}
	public void setBillState(String billState) {
		this.billState = billState;
	}
	public String getBillCountry() {
		return billCountry;
	}
	public void setBillCountry(String billCountry) {
		this.billCountry = billCountry;
	}
	public String getCycleType() {
		return cycleType;
	}
	public void setCycleType(String cycleType) {
		this.cycleType = cycleType;
	}
	public int getSalesCycle() {
		return salesCycle;
	}
	public void setSalesCycle(int salesCycle) {
		this.salesCycle = salesCycle;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUniqueID() {
		return uniqueID;
	}
	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public Timestamp getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(Timestamp lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public int getIsRisk() {
		return isRisk;
	}
	public void setIsRisk(int isRisk) {
		this.isRisk = isRisk;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public List<VipSalesLogInfo> getLogInfo() {
		return logInfo;
	}
	public void setLogInfo(List<VipSalesLogInfo> logInfo) {
		this.logInfo = logInfo;
	}
	
}
