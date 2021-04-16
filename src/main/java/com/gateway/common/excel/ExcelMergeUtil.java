package com.gateway.common.excel;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.google.common.collect.Maps;


public class ExcelMergeUtil {
	
	private static final String DFFAULT_SPACE = "Ea#&nsDpE";
	
	public static void mergeColumn(Sheet sheet,ExcelMergeBean excelMergeBean){
		if(sheet == null || excelMergeBean == null){
			return;
		}
		List<String> onlyFields = excelMergeBean.getOnlyFields();
		int rowMin = excelMergeBean.getRowMin();
		int rowMax = excelMergeBean.getRowMax();
		int columnMin = excelMergeBean.getColumnMin();
		int columnMax = excelMergeBean.getColumnMax();
		
		Map<String,Cell> columnMap = Maps.newHashMap();
		for(int i=rowMin, l=sheet.getLastRowNum(); i<=l && i<=rowMax; i++){
			Row row = sheet.getRow(i);
			for(int j=columnMin, n=row.getLastCellNum()-1; j<=n && j<=columnMax; j++){
				//列合并
				Cell lastColumn = columnMap.get("lastColumn_"+j);
				Cell thisColumn = row.getCell(j);
				if(i == rowMin || lastColumn== null){
					columnMap.put("lastColumn_"+j, thisColumn);
					continue;
				}
				String lastColumnValue = cellToString(lastColumn);
				String thisColumnValue = cellToString(thisColumn); 
				if(lastColumnValue.equals(thisColumnValue) && i<l && i<rowMax){
					continue;
				}
				columnMap.put("lastColumn_"+j, thisColumn);
				if(onlyFields==null || onlyFields.size()==0 || onlyFields.contains(lastColumnValue)){
					int a_rowIndex = lastColumn.getRowIndex();
					int b_rowIndex = thisColumn.getRowIndex()-1;
					if(lastColumnValue.equals(thisColumnValue)){
						b_rowIndex++;
					}
					if(a_rowIndex == b_rowIndex){
						continue;
					}
					CellRangeAddress cellRangeAddress = new CellRangeAddress(a_rowIndex, b_rowIndex, j, j);
					sheet.addMergedRegion(cellRangeAddress);
				}
			}
		}
	}
	
	public static void mergeRow(Sheet sheet,ExcelMergeBean excelMergeBean){
		if(sheet == null || excelMergeBean == null){
			return;
		}
		List<String> onlyFields = excelMergeBean.getOnlyFields();
		int rowMin = excelMergeBean.getRowMin();
		int rowMax = excelMergeBean.getRowMax();
		int columnMin = excelMergeBean.getColumnMin();
		int columnMax = excelMergeBean.getColumnMax();
		
		Map<String,Cell> rowMap = Maps.newHashMap();
		for(int i=rowMin, l=sheet.getLastRowNum(); i<=l && i<=rowMax; i++){
			Row row = sheet.getRow(i);
			for(int j=columnMin, n=row.getLastCellNum()-1; j<=n && j<=columnMax; j++){
				//列合并
				Cell lastColumn = rowMap.get("lastCell");
				Cell thisColumn = row.getCell(j);
				if(j == columnMin || lastColumn== null){
					rowMap.put("lastCell", thisColumn);
					continue;
				}
				String lastColumnValue = cellToString(lastColumn);
				String thisColumnValue = cellToString(thisColumn); 
				if(lastColumnValue.equals(thisColumnValue) && j<n && j<columnMax ){
					continue;
				}
				rowMap.put("lastCell", thisColumn);
				if(onlyFields==null || onlyFields.size()==0 || onlyFields.contains(lastColumnValue)){
					int a_columnIndex = lastColumn.getColumnIndex();
					int b_columnIndex = thisColumn.getColumnIndex()-1;
					if(lastColumnValue.equals(thisColumnValue)){
						b_columnIndex++;
					}
					if(a_columnIndex == b_columnIndex){
						continue;
					}
					CellRangeAddress cellRangeAddress = new CellRangeAddress(i, i, a_columnIndex, b_columnIndex);
					sheet.addMergedRegion(cellRangeAddress);
				}
			}
		}
	}
	
	// 获取一个表格的数据,如果表格为空返回一个DFFAULT_SPACE字符串,这个字符串使之代表空用于比较而已不会改变xls的数据
	public static String cellToString(Cell cell) {
		String reStr = ExcelCommonUtil.getCellStringValue(cell);
		if (reStr == null || reStr.trim().equals("")) {
			return DFFAULT_SPACE;
		} else {
			return reStr;
		}
	}

}
