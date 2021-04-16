package com.gateway.common.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

public class CellStyleSetFormat {
	
	//整数
	public static void integer(XSSFCellStyle xssfCellStyle , Workbook workbook) {
		short dataFormatIndex = workbook.createDataFormat().getFormat("#,##0");
		xssfCellStyle.setDataFormat(dataFormatIndex);
	}
	
	//整数[一位小数]
	public static void integer_1(XSSFCellStyle xssfCellStyle , Workbook workbook) {
		short dataFormatIndex = workbook.createDataFormat().getFormat("#,##0.0");
		xssfCellStyle.setDataFormat(dataFormatIndex);
	}
		
	//整数[两位小数]
	public static void integer_2(XSSFCellStyle xssfCellStyle , Workbook workbook) {
		short dataFormatIndex = workbook.createDataFormat().getFormat("#,##0.00");
		xssfCellStyle.setDataFormat(dataFormatIndex);
	}
	
	//百分数
	public static void percent(XSSFCellStyle xssfCellStyle , Workbook workbook) {
		short dataFormatIndex = workbook.createDataFormat().getFormat("0%");
		xssfCellStyle.setDataFormat(dataFormatIndex);
	}
	
	//百分数[一位小数]
	public static void percent_1(XSSFCellStyle xssfCellStyle , Workbook workbook) {
		short dataFormatIndex = workbook.createDataFormat().getFormat("0.0%");
		xssfCellStyle.setDataFormat(dataFormatIndex);
	}
	
	//百分数[两位小数]
	public static void percent_2(XSSFCellStyle xssfCellStyle , Workbook workbook) {
		short dataFormatIndex = workbook.createDataFormat().getFormat("0.00%");
		xssfCellStyle.setDataFormat(dataFormatIndex);
	}
	
}