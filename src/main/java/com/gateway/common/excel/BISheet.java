package com.gateway.common.excel;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.google.common.collect.Lists;


public class BISheet {
	
	public Sheet sheet;
	
	public List<BIRow> brList = Lists.newLinkedList();
	
	public int tabTheadNum = 0;
	
	public int maxRowIndex = 0;
	
	public BISheet(Sheet sheet){
		this.sheet = sheet;
	}
	
	public BIRow addRow(){
		Row row = sheet.createRow(maxRowIndex);
		maxRowIndex++;
		BIRow br = new BIRow(row);
		brList.add(br);
		return br;
	}
	
	public BIRow getRow(int rownum){
		return brList.get(rownum);
	}
	
	//组合加减号在左边[默认是在右边]
	public BISheet setRowSumsLeft(){
		sheet.setRowSumsRight(false);
		return this;
	}
	
	//组合加减号在上边[默认是在下边]
	public BISheet setRowSumsUp(){
		sheet.setRowSumsBelow(false);
		return this;
	}
}


