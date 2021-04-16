package com.gateway.countAnalysis.model;

/** 拒付率统计 */
public class DishonoeTotal {
	/** 商户号 */
	private String merNo;
	/** 终端号 */
	private String terNo;
	/** 年 */
	private String year;
	/** 月 */
	private String month;
	
	/** 交易总条数 */
	private String totalNum;
	/** 拒付总条数 */
	private String dishonoeNum;
	/** 总拒付率 */
	private String dishonoeColumn;
	
	/** 月交易总条数 */
	private String totalNumApril;
	/** 月拒付总条数 */
	private String dishonoeNumApril;
	/** 月拒付率 */
	private String dishonoeColumnApril;
	/** 多个交易单 */
	private String tradeNoS;
	
	/** 总投诉转换拒付笔数 */
	private String complaintToDishonoeNum;
	/** 总拒付转换率 */
	private String complaintToDishonoeColumn;
	/** 月投诉转换拒付笔数 */
	private String complaintToDishonoeNumApril;
	/** 月拒付转换率 */
	private String complaintToDishonoeColumnApril;
	
	public String getComplaintToDishonoeNumApril() {
		return complaintToDishonoeNumApril;
	}
	public void setComplaintToDishonoeNumApril(String complaintToDishonoeNumApril) {
		this.complaintToDishonoeNumApril = complaintToDishonoeNumApril;
	}
	public String getComplaintToDishonoeColumnApril() {
		return complaintToDishonoeColumnApril;
	}
	public void setComplaintToDishonoeColumnApril(
			String complaintToDishonoeColumnApril) {
		this.complaintToDishonoeColumnApril = complaintToDishonoeColumnApril;
	}
	public String getComplaintToDishonoeNum() {
		return complaintToDishonoeNum;
	}
	public void setComplaintToDishonoeNum(String complaintToDishonoeNum) {
		this.complaintToDishonoeNum = complaintToDishonoeNum;
	}
	public String getComplaintToDishonoeColumn() {
		return complaintToDishonoeColumn;
	}
	public void setComplaintToDishonoeColumn(String complaintToDishonoeColumn) {
		this.complaintToDishonoeColumn = complaintToDishonoeColumn;
	}
	public String getTradeNoS() {
		return tradeNoS;
	}
	public void setTradeNoS(String tradeNoS) {
		this.tradeNoS = tradeNoS;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getTerNo() {
		return terNo;
	}
	public void setTerNo(String terNo) {
		this.terNo = terNo;
	}
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	public String getDishonoeNum() {
		return dishonoeNum;
	}
	public void setDishonoeNum(String dishonoeNum) {
		this.dishonoeNum = dishonoeNum;
	}
	public String getDishonoeColumn() {
		return dishonoeColumn;
	}
	public void setDishonoeColumn(String dishonoeColumn) {
		this.dishonoeColumn = dishonoeColumn;
	}
	public String getTotalNumApril() {
		return totalNumApril;
	}
	public void setTotalNumApril(String totalNumApril) {
		this.totalNumApril = totalNumApril;
	}
	public String getDishonoeNumApril() {
		return dishonoeNumApril;
	}
	public void setDishonoeNumApril(String dishonoeNumApril) {
		this.dishonoeNumApril = dishonoeNumApril;
	}
	public String getDishonoeColumnApril() {
		return dishonoeColumnApril;
	}
	public void setDishonoeColumnApril(String dishonoeColumnApril) {
		this.dishonoeColumnApril = dishonoeColumnApril;
	}
}
