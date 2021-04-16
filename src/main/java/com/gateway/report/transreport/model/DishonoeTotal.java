package com.gateway.report.transreport.model;

/** 拒付率统计 */
public class DishonoeTotal {
	private String merNo;
	
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
