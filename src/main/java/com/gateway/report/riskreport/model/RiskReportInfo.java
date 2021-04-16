package com.gateway.report.riskreport.model;

import java.sql.Timestamp;



public class RiskReportInfo {
	private int riskCount;
	private String respCode;
	private String riskRate;
	private String merNo;
	private String terNo;
	private String radType;
	
	private Timestamp transDateEnd;
	private Timestamp transDateStart;
	
	public Timestamp getTransDateEnd() {
		return transDateEnd;
	}
	public void setTransDateEnd(Timestamp transDateEnd) {
		this.transDateEnd = transDateEnd;
	}
	public Timestamp getTransDateStart() {
		return transDateStart;
	}
	public void setTransDateStart(Timestamp transDateStart) {
		this.transDateStart = transDateStart;
	}
	public String getRadType() {
		return radType;
	}
	public void setRadType(String radType) {
		this.radType = radType;
	}
	public String getTerNo() {
		return terNo;
	}
	public void setTerNo(String terNo) {
		this.terNo = terNo;
	}
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	
	public String getRiskRate() {
		return riskRate;
	}
	public void setRiskRate(String riskRate) {
		this.riskRate = riskRate;
	}
	public int getRiskCount() {
		return riskCount;
	}
	public void setRiskCount(int riskCount) {
		this.riskCount = riskCount;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
}
