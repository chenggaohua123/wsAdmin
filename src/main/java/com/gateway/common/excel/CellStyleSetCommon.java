package com.gateway.common.excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;


public class CellStyleSetCommon {
	
	//设置边框可见  - 黑色边框
	public static void setCellStyle_0(XSSFCellStyle cellStyle){
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);   
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);   
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);   
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);   
	}
	
	//设置背景色为橘红
	public static void setCellStyle_1(XSSFCellStyle cellStyle){
		cellStyle.setFillForegroundColor(XSSFColorUtil.HEAD_FOREGROUNDCOLOR);
		cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
	}
	
	
	//设置背景色为绿色
	public static void setCellStyle_2(XSSFCellStyle cellStyle){
		cellStyle.setFillForegroundColor(XSSFColorUtil.COLOR_GREEN);
		cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
	}
	
	//设置背景色为黄色
	public static void setCellStyle_3(XSSFCellStyle cellStyle){
		cellStyle.setFillForegroundColor(XSSFColorUtil.HEAD_FONT);
		cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
	}
	//设置背景色为浅橘黄色
	public static void setCellStyle_4(XSSFCellStyle cellStyle){
		cellStyle.setFillForegroundColor(XSSFColorUtil.COLOR_SHALLOW_YELLOW);
		cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
	}	
	//设置背景色为天蓝色
	public static void setCellStyle_5(XSSFCellStyle cellStyle){
		cellStyle.setFillForegroundColor(XSSFColorUtil.COLOR_SKY_BLUE);
		cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
	}
	//设置背景色为浅橘黄色
	public static void setCellStyle_6(XSSFCellStyle cellStyle){
		cellStyle.setFillForegroundColor(XSSFColorUtil.COLOR_PLAE_YELLOW);
		cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
	}
	//设置背景色为红色
	public static void setCellStyle_7(XSSFCellStyle cellStyle){
		cellStyle.setFillForegroundColor(XSSFColorUtil.COLOR_RED);
		cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
	}
	//设置背景色为天蓝色2
	public static void setCellStyle_8(XSSFCellStyle cellStyle){
		cellStyle.setFillForegroundColor(XSSFColorUtil.COLOR_SKY_BLUE2);
		cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
	}
	//设置背景色为深黄绿色
	public static void setCellStyle_9(XSSFCellStyle cellStyle){
		cellStyle.setFillForegroundColor(XSSFColorUtil.COLOR_YELLOW_BLUE);
		cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
	}
	
	//设置背景色为粉红色
	public static void setCellStyle_10(XSSFCellStyle cellStyle){
		cellStyle.setFillForegroundColor(XSSFColorUtil.COLOR_ROSE);
		cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
	}
	//设置边框可见-灰色边框
	public static void setCellStyle_11(XSSFCellStyle cellStyle){
		setCellStyle_0(cellStyle);		
		cellStyle.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, XSSFColorUtil.COLOR_GRAY);
		cellStyle.setBorderColor(XSSFCellBorder.BorderSide.TOP, XSSFColorUtil.COLOR_GRAY);
		cellStyle.setBorderColor(XSSFCellBorder.BorderSide.LEFT, XSSFColorUtil.COLOR_GRAY);
		cellStyle.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, XSSFColorUtil.COLOR_GRAY);
	}
}

