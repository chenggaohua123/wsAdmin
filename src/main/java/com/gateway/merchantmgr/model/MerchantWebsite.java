package com.gateway.merchantmgr.model;

public class MerchantWebsite {
	private String id;
	private String merNo;
	private String terNo;
	private String merWebSite;
	/** 网站语言 */
	private String merWebLanguage;
	/** 网站程序 */
	private String merWebProgram;
	/** 品牌 */
	private String brand;
	/** 产品 */
	private String product;
	private String MCC;
	private String status;
	private String message;
	private String createBy;
	private String createDate;
	private String appDate;
	private String appBy;
	private String isRiskCode;
	/** 商户网址英文账单*/
	private String webSiteId;
	private String merAcquirer;
	private String merAcquirerCountry;
	private String merAcquirerCity;
	private String merAcquirerCompany;
	private String remark;
	private String ids;
	/** 操作类型 */
	private String type;
	/** 操作时间 */
	private String operationDate;
	/** 操作人 */
	private String operationBy;
	
	
	public String getMerAcquirerCountry() {
		return merAcquirerCountry;
	}
	public void setMerAcquirerCountry(String merAcquirerCountry) {
		this.merAcquirerCountry = merAcquirerCountry;
	}
	public String getMerAcquirerCity() {
		return merAcquirerCity;
	}
	public void setMerAcquirerCity(String merAcquirerCity) {
		this.merAcquirerCity = merAcquirerCity;
	}
	public String getMerAcquirerCompany() {
		return merAcquirerCompany;
	}
	public void setMerAcquirerCompany(String merAcquirerCompany) {
		this.merAcquirerCompany = merAcquirerCompany;
	}
	public String getMerAcquirer() {
		return merAcquirer;
	}
	public void setMerAcquirer(String merAcquirer) {
		this.merAcquirer = merAcquirer;
	}
	private String merType;//1 国内商户 2 海外商户 3 网关商户
	private String brandStatus;//0 不限制 1 限制
	
	private String sysOperatedDate;
	
	
	
	public String getWebSiteId() {
		return webSiteId;
	}
	public void setWebSiteId(String webSiteId) {
		this.webSiteId = webSiteId;
	}
	public String getMerType() {
		return merType;
	}
	public void setMerType(String merType) {
		this.merType = merType;
	}
	public String getBrandStatus() {
		return brandStatus;
	}
	public void setBrandStatus(String brandStatus) {
		this.brandStatus = brandStatus;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}
	public String getOperationBy() {
		return operationBy;
	}
	public void setOperationBy(String operationBy) {
		this.operationBy = operationBy;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
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
	public String getMerWebLanguage() {
		return merWebLanguage;
	}
	public void setMerWebLanguage(String merWebLanguage) {
		this.merWebLanguage = merWebLanguage;
	}
	public String getMerWebProgram() {
		return merWebProgram;
	}
	public void setMerWebProgram(String merWebProgram) {
		this.merWebProgram = merWebProgram;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getMerWebSite() {
		return merWebSite;
	}
	public void setMerWebSite(String merWebSite) {
		this.merWebSite = merWebSite;
	}
	public String getMCC() {
		return MCC;
	}
	public void setMCC(String mCC) {
		MCC = mCC;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getAppDate() {
		return appDate;
	}
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	public String getAppBy() {
		return appBy;
	}
	public void setAppBy(String appBy) {
		this.appBy = appBy;
	}
	public String getIsRiskCode() {
		return isRiskCode;
	}
	public void setIsRiskCode(String isRiskCode) {
		this.isRiskCode = isRiskCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSysOperatedDate() {
		return sysOperatedDate;
	}
	public void setSysOperatedDate(String sysOperatedDate) {
		this.sysOperatedDate = sysOperatedDate;
	}
	
}
