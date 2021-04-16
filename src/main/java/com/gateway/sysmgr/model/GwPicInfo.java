package com.gateway.sysmgr.model;

import java.sql.Timestamp;

public class GwPicInfo {
	private String tradeNo;
	
	private String phoneNo;
	
	private String picExtName;
	
	private String picType;
	
	private String picBuffer;
	
	private Timestamp uploadDate;
	
	private String uploadBy;

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPicExtName() {
		return picExtName;
	}

	public void setPicExtName(String picExtName) {
		this.picExtName = picExtName;
	}

	public String getPicType() {
		return picType;
	}

	public void setPicType(String picType) {
		this.picType = picType;
	}

	public String getPicBuffer() {
		return picBuffer;
	}

	public void setPicBuffer(String picBuffer) {
		this.picBuffer = picBuffer;
	}

	public Timestamp getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Timestamp uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getUploadBy() {
		return uploadBy;
	}

	public void setUploadBy(String uploadBy) {
		this.uploadBy = uploadBy;
	}
	
	

}
