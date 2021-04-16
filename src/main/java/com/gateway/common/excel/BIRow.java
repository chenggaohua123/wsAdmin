package com.gateway.common.excel;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.google.common.collect.Lists;


public class BIRow {
	
	public Row row;
	
	public List<BICell> bcList = Lists.newLinkedList();
	
	public int maxCellIndex = 0;
	
	public BIRow(Row row){
		this.row = row;
	}
	
	public BICell addCell(){
		Cell cell = row.createCell(maxCellIndex);
		maxCellIndex++;
		BICell bc = new BICell(cell);
		bcList.add(bc);
		return bc; 
	}
	
	public BICell getCell(int cellnum){
		return bcList.get(cellnum);
	}
	
	//单元格分组[行:上下]
	public BIRow setGroupRow(int up, int down) {
		Sheet sheet = row.getSheet();
		int rowIndex = row.getRowNum();
		int a_rowIndex = rowIndex-up;
		int b_rowIndex = rowIndex+down;
		sheet.groupRow(a_rowIndex, b_rowIndex);
		return this;
	}
	
	//设置行高
	public BIRow setHeightInPoints(float height){
		row.setHeightInPoints(height);
		return this;
	}
}
