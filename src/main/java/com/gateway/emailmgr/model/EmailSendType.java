package com.gateway.emailmgr.model;

public class EmailSendType {
	private String id;
	private String sendType;
	private String sendService;
	private String emailSubject;
	private String name;
	private int emailSubId;
	private int type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getEmailSubId() {
		return emailSubId;
	}
	public void setEmailSubId(int emailSubId) {
		this.emailSubId = emailSubId;
	}
	public String getEmailSubject() {
		return emailSubject;
	}
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getSendService() {
		return sendService;
	}
	public void setSendService(String sendService) {
		this.sendService = sendService;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
