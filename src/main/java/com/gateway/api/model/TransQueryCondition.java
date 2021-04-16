package com.gateway.api.model;

import java.util.ArrayList;
import java.util.List;

public class TransQueryCondition extends BaseInfo{
	private String merNo;
	private String transType ="";
	private String transTimeStart="";
	private String transTimeEnd="";
	private String tradeNo="";
	private String terNo="";
	private String relNo="";
	private String isSettle = "";
	private String isSucceed="";
	private String cardNo = "";
	private String isNormal="";
	private List<TransInfo> transList = new ArrayList<TransInfo>();
	private int pageNum = 1;
	private int pageSize = 20;
	private int total = 0;
	
	public String getIsNormal() {
		return isNormal;
	}
	public void setIsNormal(String isNormal) {
		this.isNormal = isNormal;
	}
	public String getIsSucceed() {
		return isSucceed;
	}
	public void setIsSucceed(String isSucceed) {
		this.isSucceed = isSucceed;
	}
	public String getIsSettle() {
		return isSettle;
	}
	public void setIsSettle(String isSettle) {
		this.isSettle = isSettle;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
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
	public List<TransInfo> getTransList() {
		return transList;
	}
	public void setTransList(List<TransInfo> transList) {
		this.transList = transList;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getTransTimeStart() {
		return transTimeStart;
	}
	public void setTransTimeStart(String transTimeStart) {
		this.transTimeStart = transTimeStart;
	}
	public String getTransTimeEnd() {
		return transTimeEnd;
	}
	public void setTransTimeEnd(String transTimeEnd) {
		this.transTimeEnd = transTimeEnd;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getRelNo() {
		return relNo;
	}
	public void setRelNo(String relNo) {
		this.relNo = relNo;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
}
