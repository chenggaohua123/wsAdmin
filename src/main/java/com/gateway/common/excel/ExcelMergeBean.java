package com.gateway.common.excel;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

public class ExcelMergeBean implements Serializable {
	
	private ExcelMergeBean(){}
	
	private static final long serialVersionUID = -7496165994399031831L;
	
	private int rowMin;
	private int rowMax;
	private int columnMin;
	private int columnMax;
	private List<String> onlyFields;
	
	public static ExcelMergeBean newInstance(int rowMin,int rowMax,int columnMin,int columnMax,String... args){
		ExcelMergeBean bean = new ExcelMergeBean();
		bean.setRowMin(rowMin);
		bean.setRowMax(rowMax);
		bean.setColumnMin(columnMin);
		bean.setColumnMax(columnMax);
		List<String> list = Lists.newArrayList();
		for(String s : args){
			list.add(s);
		}
		bean.setOnlyFields(list);
		return bean;
	}
	
	public static ExcelMergeBean newInstance(int rowMin,int rowMax,int columnMin,int columnMax){
		ExcelMergeBean bean = new ExcelMergeBean();
		bean.setRowMin(rowMin);
		bean.setRowMax(rowMax);
		bean.setColumnMin(columnMin);
		bean.setColumnMax(columnMax);
		return bean;
	}
	
	public static ExcelMergeBean newInstance(String... args){
		ExcelMergeBean bean = new ExcelMergeBean();
		bean.setRowMin(0);
		bean.setRowMax(Integer.MAX_VALUE);
		bean.setColumnMin(0);
		bean.setColumnMax(Integer.MAX_VALUE);
		List<String> list = Lists.newArrayList();
		for(String s : args){
			list.add(s);
		}
		bean.setOnlyFields(list);
		return bean;
	}
	
	public static ExcelMergeBean newInstance(){
		ExcelMergeBean bean = new ExcelMergeBean();
		bean.setRowMin(0);
		bean.setRowMax(Integer.MAX_VALUE);
		bean.setColumnMin(0);
		bean.setColumnMax(Integer.MAX_VALUE);
		return bean;
	}
	
	public int getRowMin() {
		return rowMin;
	}
	public void setRowMin(int rowMin) {
		this.rowMin = rowMin;
	}
	public int getRowMax() {
		return rowMax;
	}
	public void setRowMax(int rowMax) {
		this.rowMax = rowMax;
	}
	public int getColumnMin() {
		return columnMin;
	}
	public void setColumnMin(int columnMin) {
		this.columnMin = columnMin;
	}
	public int getColumnMax() {
		return columnMax;
	}
	public void setColumnMax(int columnMax) {
		this.columnMax = columnMax;
	}
	public List<String> getOnlyFields() {
		return onlyFields;
	}
	public void setOnlyFields(List<String> onlyFields) {
		this.onlyFields = onlyFields;
	}
	
}
