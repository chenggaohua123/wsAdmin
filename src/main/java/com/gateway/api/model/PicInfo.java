package com.gateway.api.model;

public class PicInfo extends BaseInfo {
	private String picExtName;
	private String picType;
	private String tradeNo;
	private String picBuffer;
	private String uploadBy;
	
	public String getUploadBy() {
		return uploadBy;
	}

	public void setUploadBy(String uploadBy) {
		this.uploadBy = uploadBy;
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

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getPicBuffer() {
		return picBuffer;
	}

	public void setPicBuffer(String picBuffer) {
		this.picBuffer = picBuffer;
	}

}
