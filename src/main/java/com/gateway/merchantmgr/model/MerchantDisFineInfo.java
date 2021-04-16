package com.gateway.merchantmgr.model;

import java.sql.Timestamp;
import java.util.List;

public class MerchantDisFineInfo {

	private String merNo;
	private String terNo;
	private List<MerchantDisFineRuleInfo> ruleList;
	private String enabled;
	private String createBy;
	private Timestamp createDate;
	private String modify;
	private Timestamp modifyDate;
	private List<String> starts;
	private List<String> ends;
	private List<String> amounts;
	private List<String> ids;
	private String currency;
	private String id;
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
	public List<MerchantDisFineRuleInfo> getRuleList() {
		return ruleList;
	}
	public void setRuleList(List<MerchantDisFineRuleInfo> ruleList) {
		this.ruleList = ruleList;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getModify() {
		return modify;
	}
	public void setModify(String modify) {
		this.modify = modify;
	}
	public Timestamp getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}
	public List<String> getStarts() {
		return starts;
	}
	public void setStarts(List<String> starts) {
		this.starts = starts;
	}
	public List<String> getEnds() {
		return ends;
	}
	public void setEnds(List<String> ends) {
		this.ends = ends;
	}
	public List<String> getAmounts() {
		return amounts;
	}
	public void setAmounts(List<String> amounts) {
		this.amounts = amounts;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
