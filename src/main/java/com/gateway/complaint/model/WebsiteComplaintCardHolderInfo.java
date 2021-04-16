package com.gateway.complaint.model;

public class WebsiteComplaintCardHolderInfo {

	private String type;//投诉类型
	
	private String complaintType;//调查单类型
	
	private String complaintDesc;//描述
	
	private int complaintCount;//投诉总数
	
	private String payWebSite;//投诉网址

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComplaintType() {
		return complaintType;
	}

	public void setComplaintType(String complaintType) {
		this.complaintType = complaintType;
	}

	public String getComplaintDesc() {
		return complaintDesc;
	}

	public void setComplaintDesc(String complaintDesc) {
		this.complaintDesc = complaintDesc;
	}

	public int getComplaintCount() {
		return complaintCount;
	}

	public void setComplaintCount(int complaintCount) {
		this.complaintCount = complaintCount;
	}

	public String getPayWebSite() {
		return payWebSite;
	}

	public void setPayWebSite(String payWebSite) {
		this.payWebSite = payWebSite;
	}

	@Override
	public String toString() {
		return "\r\n[投诉类型=" + (type!=null?("0".equals(type)?"调查单":("1".equals(type)?"拒付":("2".equals(type)?"持卡人投诉":("3".equals(type)?"伪冒单":"")))):"")
				+ "; 投诉描述="
				+ complaintDesc + "; 总数=" + complaintCount
				+ "; 网址=" + payWebSite + "]";
	}
	
}
