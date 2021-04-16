package com.gateway.fraud.model;



public class WhiteListInfo {
	private int id;
	private String blackType;//风控类型:卡号,邮箱,IP',
	private String merNo; // '商户号',
	private String terNo; // '终端号',
	private String createdBy; //创建人',
	private String createDate;//'创建时间',
	private String lastUpdateBy;//最终修改人',
	private String lastUpdateDate; //最终修改时间',
	private String enableFlag ; //是否开通: 0表示未开通,1表示开通',
	private String remark ;//备注
	private String blackText;//风控信息
	private int type;
	private String checkNo;
	
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBlackType() {
		return blackType;
	}
	public void setBlackType(String blackType) {
		this.blackType = blackType;
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getEnableFlag() {
		return enableFlag;
	}
	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBlackText() {
		return blackText;
	}
	public void setBlackText(String blackText) {
		this.blackText = blackText;
	}
	public String getCheckNo() {
		return checkNo;
	}
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}
	
	
}
