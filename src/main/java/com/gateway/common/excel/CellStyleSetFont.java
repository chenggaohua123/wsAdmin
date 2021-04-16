package com.gateway.common.excel;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

public class CellStyleSetFont {
	
	//字体黄色
	public static void yellow(XSSFCellStyle xssfCellStyle , Workbook workbook) {
		XSSFFont font =  (XSSFFont)workbook.createFont();
		font.setColor(XSSFColorUtil.HEAD_FONT);
		xssfCellStyle.setFont(font);
	}
	
	//字体白色
	public static void white(XSSFCellStyle xssfCellStyle , Workbook workbook) {
		XSSFFont font =  (XSSFFont)workbook.createFont();
		font.setColor(XSSFColorUtil.COLOR_WHITE);
		xssfCellStyle.setFont(font);
	}
	
	//字体紅色
	public static void red(XSSFCellStyle xssfCellStyle , Workbook workbook) {
		XSSFFont font =  (XSSFFont)workbook.createFont();
		font.setColor(XSSFColorUtil.COLOR_RED);
		xssfCellStyle.setFont(font);
		
	}
	
	//字体绿色
	public static void green(XSSFCellStyle xssfCellStyle , Workbook workbook) {
		XSSFFont font =  (XSSFFont)workbook.createFont();
		font.setColor(XSSFColorUtil.COLOR_GREEN);
		xssfCellStyle.setFont(font);
	}
	
	//字体天空蓝色
	public static void sky_blue(XSSFCellStyle xssfCellStyle , Workbook workbook) {
		XSSFFont font =  (XSSFFont)workbook.createFont();
		font.setColor(XSSFColorUtil.COLOR_SKY_BLUE);
		xssfCellStyle.setFont(font);
	}
	//字体Arial
	public static void ZT(XSSFCellStyle xssfCellStyle , Workbook workbook) {
		XSSFFont font =  (XSSFFont)workbook.createFont();
		font.setFontName("arial");
		font.setFontHeightInPoints((short)9); //字体大小
		xssfCellStyle.setFont(font);
	}
	//字体宋体
	public static void ST(XSSFCellStyle xssfCellStyle , Workbook workbook) {
		XSSFFont font =  (XSSFFont)workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)10); //字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //粗体
		xssfCellStyle.setFont(font);
	}
	//字体宋体11号
	public static void ST11(XSSFCellStyle xssfCellStyle , Workbook workbook) {
		XSSFFont font =  (XSSFFont)workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)11); //字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //粗体
		xssfCellStyle.setFont(font);
	}
	//字体 宋体10号 黄色
	public static void ST10(XSSFCellStyle xssfCellStyle , Workbook workbook) {
		XSSFFont font =  (XSSFFont)workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)10); //字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //粗体
		font.setColor(XSSFColorUtil.HEAD_FONT);
		xssfCellStyle.setFont(font);
	}
	//字体宋体 斜体 加粗
	public static void STX(XSSFCellStyle xssfCellStyle , Workbook workbook) {
		XSSFFont font =  (XSSFFont)workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)10); //字体大小
		font.setItalic(true);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //粗体
		xssfCellStyle.setFont(font);
	}
	//字体Arial 红色
	public static void ZTRED(XSSFCellStyle xssfCellStyle , Workbook workbook) {
		XSSFFont font =  (XSSFFont)workbook.createFont();
		font.setFontName("arial");
		font.setColor(XSSFColorUtil.COLOR_RED);
		font.setFontHeightInPoints((short)9); //字体大小
		xssfCellStyle.setFont(font);
	}
}