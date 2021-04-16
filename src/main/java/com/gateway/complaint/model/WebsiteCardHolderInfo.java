package com.gateway.complaint.model;

import java.util.List;

public class WebsiteCardHolderInfo {

	private String cardHolderName;
	
	private String complTradeNo;
	
	private List<WebsiteComplaintCardHolderInfo> list;

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getComplTradeNo() {
		return complTradeNo;
	}

	public void setComplTradeNo(String complTradeNo) {
		this.complTradeNo = complTradeNo;
	}

	public List<WebsiteComplaintCardHolderInfo> getList() {
		return list;
	}

	public void setList(List<WebsiteComplaintCardHolderInfo> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "[\r\n持卡人=" + cardHolderName
				+ ";\r\n 持卡人投诉记录=" + list + "\r\n]";
	}
	
}
