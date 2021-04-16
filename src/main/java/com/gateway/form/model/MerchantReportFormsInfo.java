package com.gateway.form.model;

import java.sql.Timestamp;

public class MerchantReportFormsInfo {
	private String merNo;
	private String terNo;
	private Timestamp formDate;
	private String cycle;
	private String startDate;
	private String endDate;
	/**
	 * 1.月报表 2.周报表 3.日报表
	 */
	private int formType;
	
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	public int getFormType() {
		return formType;
	}
	public void setFormType(int formType) {
		this.formType = formType;
	}
	public Timestamp getFormDate() {
		return formDate;
	}
	public void setFormDate(Timestamp formDate) {
		this.formDate = formDate;
	}
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	} 
	
	
	
}
