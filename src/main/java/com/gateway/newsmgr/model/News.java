package com.gateway.newsmgr.model;

import java.sql.Timestamp;

public class News {
	private int id;
	private String newsTitle;
	private String newsContext;
	private int newsType;
	private int isTop;
	private String belongTo;
	private String createBy;
	private Timestamp createDate;
	private Timestamp startDate;
	private Timestamp endDate;
	private Timestamp topDate;
	private int enableFlag;
	private String lastModify;
	private Timestamp lastModifyDate;
	
	
	public Timestamp getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(Timestamp lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public String getLastModify() {
		return lastModify;
	}
	public void setLastModify(String lastModify) {
		this.lastModify = lastModify;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNewsTitle() {
		return newsTitle;
	}
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}
	public String getNewsContext() {
		return newsContext;
	}
	public void setNewsContext(String newsContext) {
		this.newsContext = newsContext;
	}
	public int getNewsType() {
		return newsType;
	}
	public void setNewsType(int newsType) {
		this.newsType = newsType;
	}
	public int getIsTop() {
		return isTop;
	}
	public void setIsTop(int isTop) {
		this.isTop = isTop;
	}
	public String getBelongTo() {
		return belongTo;
	}
	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public Timestamp getTopDate() {
		return topDate;
	}
	public void setTopDate(Timestamp topDate) {
		this.topDate = topDate;
	}
	public int getEnableFlag() {
		return enableFlag;
	}
	public void setEnableFlag(int enableFlag) {
		this.enableFlag = enableFlag;
	}
	
	
	
}
