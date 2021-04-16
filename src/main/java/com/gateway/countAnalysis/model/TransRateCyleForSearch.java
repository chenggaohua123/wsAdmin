package com.gateway.countAnalysis.model;

public class TransRateCyleForSearch {
	private int id;
	private int cycle;
	private String startDate;
	private String endDate;
	private long dayCount;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCycle() {
		return cycle;
	}
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public long getDayCount() {
		return dayCount;
	}
	public void setDayCount(long dayCount) {
		this.dayCount = dayCount;
	}
	
}
