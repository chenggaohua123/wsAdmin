package com.gateway.common.excel;

import org.apache.poi.ss.usermodel.Cell;

public class ExcelCommonUtil {
	
	public static String getCellStringValue(Cell cell) {
		String cellValue = "";
		if (cell == null){
			return cellValue;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:// 字符串类型
			cellValue = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC: // 数值类型
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA: // 公式
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		}
		return cellValue;
	}
	

	
}
