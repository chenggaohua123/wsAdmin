package com.gateway.fraud.common.util;

import java.io.Serializable;
import java.util.List;

public class PageUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	private int countRow;

	private int thisPage;

	@SuppressWarnings("rawtypes")
	private List dataContext;

	private String dataString;

	private int everyPageRow = 15;

	private String from;

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	//end

	public PageUtil() {
	}

	public int getCountPage() {
		int tempcount = this.countRow / this.everyPageRow
				+ (((this.countRow % this.everyPageRow) == 0) ? 0 : 1);
		return tempcount > 200 ? 200 : tempcount;
	}

	public String getCountRow() {
		return Integer.toString(countRow);
	}

	public int getCountIntRow() {
		return countRow;
	}

	public void setCountRow(int countRow) {
		this.countRow = countRow;
	}

	@SuppressWarnings("rawtypes")
	public List getDataContext() {
		return dataContext;
	}

	@SuppressWarnings("rawtypes")
	public void setDataContext(List dataContext) {
		this.dataContext = dataContext;
	}

	public int getThisPage() {
		return thisPage > this.getCountPage() ? this.getCountPage() : thisPage;
	}

	public void setThisPage(int thisPage) {
		this.thisPage = thisPage;
	}

	public int getThisPageRow() {
		return this.getDataContext().size();
	}

	public int getEveryPageRow() {
		return everyPageRow;
	}

	public void setEveryPageRow(int everyPageRow) {
		this.everyPageRow = everyPageRow;
	}

	public boolean getHasNext() {
		return this.getThisPage() >= this.getCountPage() ? false : true;
	}

	public boolean getHasPrevious() {
		return this.getThisPage() - 1 < 1 ? false : true;
	}

	public int getNextPage() {
		return this.getThisPage() >= this.getCountPage()
				? this.getThisPage()
				: this.getThisPage() + 1;
	}

	public int getPreviousPage() {
		return this.getThisPage() < 1 ? 0 : this.getThisPage() - 1;
	}

	public String getDataString() {
		return dataString;
	}

	public void setDataString(String dataString) {
		this.dataString = dataString;
	}

}
