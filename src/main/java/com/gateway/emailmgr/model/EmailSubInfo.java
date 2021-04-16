package com.gateway.emailmgr.model;

public class EmailSubInfo {
	private int id;
	private String tel;
	private String fax;
	/** 邮箱 */
	private String email;
	private String replyEmail;
	private String helpWebsite;
	/** 创建人 */
	private String createBy;
	/** 创建时间 */
	private String createDate;
	/** 修改人 */
	private String lastModifyBy;
	/** 修改时间 */
	private String lastModifyDate;
	/** 是否有效 */
	private int enabled;
	private String website;
	private String typeId;
	
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getReplyEmail() {
		return replyEmail;
	}
	public void setReplyEmail(String replyEmail) {
		this.replyEmail = replyEmail;
	}
	public String getHelpWebsite() {
		return helpWebsite;
	}
	public void setHelpWebsite(String helpWebsite) {
		this.helpWebsite = helpWebsite;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getLastModifyBy() {
		return lastModifyBy;
	}
	public void setLastModifyBy(String lastModifyBy) {
		this.lastModifyBy = lastModifyBy;
	}
	public String getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(String lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
}
