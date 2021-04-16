package com.gateway.countAnalysis.model;

import java.util.List;

public class ExportTransCountInfo {

	private String merNo;
	private String terNo;
	private String resourceType;
	private String webInfo;
	private String totalCount;
	private String transSuccessCount;
	private String transCount;
	private String transFailureCount;
	private String duplicateCount;
	private String transRiskCount;
	private String successRate;
	private String failedRate;
	private List<ExportFaildTransAnalysisInfo> faildList;
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
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getWebInfo() {
		return webInfo;
	}
	public void setWebInfo(String webInfo) {
		this.webInfo = webInfo;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getTransSuccessCount() {
		return transSuccessCount;
	}
	public void setTransSuccessCount(String transSuccessCount) {
		this.transSuccessCount = transSuccessCount;
	}
	public String getTransCount() {
		return transCount;
	}
	public void setTransCount(String transCount) {
		this.transCount = transCount;
	}
	public String getTransFailureCount() {
		return transFailureCount;
	}
	public void setTransFailureCount(String transFailureCount) {
		this.transFailureCount = transFailureCount;
	}
	public String getDuplicateCount() {
		return duplicateCount;
	}
	public void setDuplicateCount(String duplicateCount) {
		this.duplicateCount = duplicateCount;
	}
	public String getTransRiskCount() {
		return transRiskCount;
	}
	public void setTransRiskCount(String transRiskCount) {
		this.transRiskCount = transRiskCount;
	}
	public String getSuccessRate() {
		return successRate;
	}
	public void setSuccessRate(String successRate) {
		this.successRate = successRate;
	}
	public String getFailedRate() {
		return failedRate;
	}
	public void setFailedRate(String failedRate) {
		this.failedRate = failedRate;
	}
	public List<ExportFaildTransAnalysisInfo> getFaildList() {
		return faildList;
	}
	public void setFaildList(List<ExportFaildTransAnalysisInfo> faildList) {
		this.faildList = faildList;
	}
	
}
