package com.gateway.emailmgr.model;

public class EmailInfo {
	private int id;
	/**  */
	private String emailHost;
	/** 邮箱账号 */
	private String emailAccount;
	/** 邮箱密码 */
	private String emailPassword;
	private String emailPort;
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
	/**  */
	private int sendCount;
	/** 邮箱类型,关联ID */
	private int emailType;
	private int sendCountLimit;
	/** 邮箱类型，类型名 */
	private String emailTypes;
	
	public String getEmailTypes() {
		return emailTypes;
	}
	public void setEmailTypes(String emailTypes) {
		this.emailTypes = emailTypes;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmailHost() {
		return emailHost;
	}
	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}
	public String getEmailAccount() {
		return emailAccount;
	}
	public void setEmailAccount(String emailAccount) {
		this.emailAccount = emailAccount;
	}
	public String getEmailPassword() {
		return emailPassword;
	}
	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}
	public String getEmailPort() {
		return emailPort;
	}
	public void setEmailPort(String emailPort) {
		this.emailPort = emailPort;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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
	public int getSendCount() {
		return sendCount;
	}
	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}
	public int getEmailType() {
		return emailType;
	}
	public void setEmailType(int emailType) {
		this.emailType = emailType;
	}
	public int getSendCountLimit() {
		return sendCountLimit;
	}
	public void setSendCountLimit(int sendCountLimit) {
		this.sendCountLimit = sendCountLimit;
	}

}
