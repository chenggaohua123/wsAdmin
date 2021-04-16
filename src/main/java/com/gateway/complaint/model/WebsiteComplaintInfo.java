package com.gateway.complaint.model;

import java.util.List;

public class WebsiteComplaintInfo {

	private int complCount;//投诉总次数
	
	private String tradeNo;//交易流水号
	
	private int type;//投诉类型
	
	private String payWebSite;//支付网址
	
	private String merNo;//商户号
	
	private String terNo;//终端号
	
	private String websiteStatus;//网址状态
	
	private String complTradeNo;//投诉订单流水号
	
	private String brand;//网址品牌信息
	
	private String product;//网址产品信息
	
	private List<WebsiteComplaintCardHolderInfo> count;
	
	private List<WebsiteCardHolderInfo> cardHolder;
	
	public int getComplCount() {
		return complCount;
	}

	public void setComplCount(int complCount) {
		this.complCount = complCount;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPayWebSite() {
		return payWebSite;
	}

	public void setPayWebSite(String payWebSite) {
		this.payWebSite = payWebSite;
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

	public String getWebsiteStatus() {
		return websiteStatus;
	}

	public void setWebsiteStatus(String websiteStatus) {
		this.websiteStatus = websiteStatus;
	}

	public String getComplTradeNo() {
		return complTradeNo;
	}

	public void setComplTradeNo(String complTradeNo) {
		this.complTradeNo = complTradeNo;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public List<WebsiteComplaintCardHolderInfo> getCount() {
		return count;
	}

	public void setCount(List<WebsiteComplaintCardHolderInfo> count) {
		this.count = count;
	}

	public List<WebsiteCardHolderInfo> getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(List<WebsiteCardHolderInfo> cardHolder) {
		this.cardHolder = cardHolder;
	}
	
}
