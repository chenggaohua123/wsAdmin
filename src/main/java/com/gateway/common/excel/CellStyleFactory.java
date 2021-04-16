package com.gateway.common.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

/**
 * 常用[API]
	// 设置单元格内容自动换行
	xssfCellStyle.setWrapText(true);
	// 设置单元格内容水平对其方式   
	// XSSFCellStyle.ALIGN_CENTER       居中对齐   
	// XSSFCellStyle.ALIGN_LEFT         左对齐   
	// XSSFCellStyle.ALIGN_RIGHT        右对齐   
	xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
	// 设置单元格内容垂直对其方式   
	// XSSFCellStyle.VERTICAL_TOP       上对齐   
	// XSSFCellStyle.VERTICAL_CENTER    中对齐   
	// XSSFCellStyle.VERTICAL_BOTTOM    下对齐   
	xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); 
 */

public class CellStyleFactory {
	
	//普通表头的样式[橘红背景色,黄字,灰色边框]
	public static XSSFCellStyle CELLSTYLE_001(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_11(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_1(xssfCellStyle);
		CellStyleSetFont.ST10(xssfCellStyle, workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		return xssfCellStyle;
	}
	//普通表头的样式[橘红背景色,黄字, 灰边框]
	public static XSSFCellStyle CELLSTYLE_001_GRAY_BORDER(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_11(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_1(xssfCellStyle);
		CellStyleSetFont.yellow(xssfCellStyle,workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		return xssfCellStyle;
	}
	//普通表头的样式[黄色背景色,有边框]
	public static XSSFCellStyle CELLSTYLE_0011(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_3(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		return xssfCellStyle;
	}
	//普通表头的样式[橘红背景色,黄字,有边框]整数
	public static XSSFCellStyle CELLSTYLE_001_INTEGER(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_1(xssfCellStyle);
		CellStyleSetFont.yellow(xssfCellStyle,workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表头的样式[橘红背景色,黄字,有边框]整数,1位小数
	public static XSSFCellStyle CELLSTYLE_001_INTEGER_1(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_1(xssfCellStyle);
		CellStyleSetFont.yellow(xssfCellStyle,workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表头的样式[橘红背景色,黄字,有边框]百分比
	public static XSSFCellStyle CELLSTYLE_001_PERCENT_1(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_1(xssfCellStyle);
		CellStyleSetFont.yellow(xssfCellStyle,workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表头的样式[天蓝色背景色,黄字,有边框]
	public static XSSFCellStyle CELLSTYLE_0010(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_5(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		return xssfCellStyle;
	}
	//普通表头的样式[橘红背景色,红字,有边框]
	public static XSSFCellStyle CELLSTYLE_001_RED(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_1(xssfCellStyle);
		CellStyleSetFont.red(xssfCellStyle,workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		return xssfCellStyle;
	}
	//普通表体样式[白底,黑字,有边框]整数
	public static XSSFCellStyle CELLSTYLE_002(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		return xssfCellStyle;
	}
	//普通表体样式[白底,黑字,有边框]整数 加粗
	public static XSSFCellStyle CELLSTYLE_002_BOLD(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		xssfCellStyle.getFont().setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//加粗
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		return xssfCellStyle;
	}
	//普通表体样式[白底,黑字,无边框]整数 加粗
	public static XSSFCellStyle CELLSTYLE_0022(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetFont.ST(xssfCellStyle, workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		return xssfCellStyle;
	}
	//普通表体样式[白底,宋体11号,无边框]整数 加粗-不换行-水平居左
	public static XSSFCellStyle CELLSTYLE_0022_Line(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetFont.ST11(xssfCellStyle, workbook);
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);//水平居左
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		return xssfCellStyle;
	}
	//普通表体样式[白底,黑字,有边框]整数
	public static XSSFCellStyle CELLSTYLE_002_INTEGER(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[白底,黑字,有边框]整数
	public static XSSFCellStyle CELLSTYLE_0022_INTEGER(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetFont.ZT(xssfCellStyle, workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);//水平居右
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[白底,红字,有边框]整数
	public static XSSFCellStyle CELLSTYLE_002_INTEGER_RED(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetFont.red(xssfCellStyle, workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[白底,蓝字,有边框]整数
	public static XSSFCellStyle CELLSTYLE_002_INTEGER_BLUE(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetFont.sky_blue(xssfCellStyle, workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[白底,黑字,有边框]整数一位小数
	public static XSSFCellStyle CELLSTYLE_002_INTEGER_1(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[白底,黑字,无边框]整数两位小数
	public static XSSFCellStyle CELLSTYLE_002_INTEGER_2(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer_2(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[白底,黑字,有边框]整数两位小数
	public static XSSFCellStyle CELLSTYLE_0022_INTEGER_2(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetFont.ZT(xssfCellStyle, workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer_2(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[白底,黑字,有边框]整数
	public static XSSFCellStyle CELLSTYLE_002_PERCENT(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[白底,黑字,有边框]百分比一位小数
	public static XSSFCellStyle CELLSTYLE_002_PERCENT_1(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[浅橘黄色,黑字,无边框]百分比一位小数
	public static XSSFCellStyle CELLSTYLE_0120_percent_1(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_11(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_4(xssfCellStyle);
		CellStyleSetFont.ZT(xssfCellStyle, workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);//水平居右
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	
	//普通表体样式[白底,黑字,无边框]百分比一位小数
	public static XSSFCellStyle CELLSTYLE_0022_PERCENT_1(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetFont.ZT(xssfCellStyle, workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);//水平居右
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[白底,黑字,有边框]百分比 两位小数
	public static XSSFCellStyle CELLSTYLE_002_PERCENT_2(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent_2(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[白底,红字,有边框]百分比一位小数
	public static XSSFCellStyle CELLSTYLE_002_PERCENT_3(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetFont.red(xssfCellStyle, workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[白底,红字,有边框]百分比两位小数
	public static XSSFCellStyle CELLSTYLE_002_PERCENT_6(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetFont.red(xssfCellStyle, workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent_2(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[黄色底,黑字,有边框]整数两位小数
	public static XSSFCellStyle CELLSTYLE_002_PERCENT_4(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_3(xssfCellStyle);//背景色
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent_2(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[红色底,黑字,有边框]整数两位小数
	public static XSSFCellStyle CELLSTYLE_002_PERCENT_5(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_7(xssfCellStyle);//背景色
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent_2(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[绿底,黑字,有边框]
	public static XSSFCellStyle CELLSTYLE_003(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_2(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		return xssfCellStyle;
	}
	//普通表体样式[绿底,黑字,有边框]整数
	public static XSSFCellStyle CELLSTYLE_003_INTEGER(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_2(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[黄底,黑字,有边框]
	public static XSSFCellStyle CELLSTYLE_004(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_3(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		return xssfCellStyle;
	}
	
	//普通表体样式[白底,白字,有边框]
	public static XSSFCellStyle CELLSTYLE_005(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetFont.white(xssfCellStyle,workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		return xssfCellStyle;
	}
	//普通表体样式[白底,紅字,有边框]
	public static XSSFCellStyle CELLSTYLE_006(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetFont.red(xssfCellStyle,workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		return xssfCellStyle;
	}
	
	//普通表体样式[白底,紅字,无边框]
	public static XSSFCellStyle CELLSTYLE_007(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetFont.red(xssfCellStyle,workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		return xssfCellStyle;
	}
	//普通表体样式[浅橘黄色底,黑字,有边框]
	public static XSSFCellStyle CELLSTYLE_008(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);//边框
		CellStyleSetCommon.setCellStyle_4(xssfCellStyle);//背景色
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		return xssfCellStyle;
	}
	//普通表体样式[浅橘黄色底,黑字,有边框]整数
	public static XSSFCellStyle CELLSTYLE_008_INTEGER(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_4(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[浅橘黄色底,黑字,有边框]整数,1位小数点
	public static XSSFCellStyle CELLSTYLE_008_INTEGER_1(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_4(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[黄底,黑字,有边框]整数,1位小数点
	public static XSSFCellStyle CELLSTYLE_008_INTEGER_YELLOW_1(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_3(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[浅橘黄色底,黑字,有边框]百分比
	public static XSSFCellStyle CELLSTYLE_008_PERCENT_1(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_4(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[浅橘黄色底,黑字,有边框]百分比，保留1位小数
	public static XSSFCellStyle CELLSTYLE_008_PERCENT_2(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_4(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[天蓝色底,黑字,有边框]
	public static XSSFCellStyle CELLSTYLE_009(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_5(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中	
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		return xssfCellStyle;
	}
	//普通表体样式[白底,绿字,有边框]
	public static XSSFCellStyle CELLSTYLE_010(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetFont.green(xssfCellStyle, workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		return xssfCellStyle;
	}
	//普通表体样式[白底,紅字,有边框]整数一位小数
	public static XSSFCellStyle CELLSTYLE_011(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetFont.red(xssfCellStyle, workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[白底,紅字,有边框]百分比保留一位小数
	public static XSSFCellStyle CELLSTYLE_012(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetFont.red(xssfCellStyle, workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[白底,紅字,无边框]百分比保留一位小数
	public static XSSFCellStyle CELLSTYLE_012_NO_BORDER(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetFont.red(xssfCellStyle, workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);//水平居右
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[浅黄底,黑字,有边框]
	public static XSSFCellStyle CELLSTYLE_013(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_6(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中	
		return xssfCellStyle;
	}
	//普通表体样式[浅黄底,黑字,有边框]整数
	public static XSSFCellStyle CELLSTYLE_013_INTEGER(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_6(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer(xssfCellStyle, workbook);		
		return xssfCellStyle;
	}
	//普通表体样式[浅黄底,黑字,有边框]整数，1位小数
	public static XSSFCellStyle CELLSTYLE_013_INTEGER_1(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_6(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer_1(xssfCellStyle, workbook);		
		return xssfCellStyle;
	}
	//普通表体样式[浅黄底,黑字,有边框]百分比
	public static XSSFCellStyle CELLSTYLE_013_PERCENT_1(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_6(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent_1(xssfCellStyle, workbook);		
		return xssfCellStyle;
	}
	//普通表体样式[红底,黑字,有边框]整数
	public static XSSFCellStyle CELLSTYLE_014(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_7(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[天蓝色2底,黑字,有边框]
	public static XSSFCellStyle CELLSTYLE_015(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_8(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中	
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		return xssfCellStyle;
	}
	//普通表体样式[天蓝色2底,黑字,有边框]整数
	public static XSSFCellStyle CELLSTYLE_015_INTEGER(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_8(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中	
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[天蓝色2底,黑字,有边框]整数，1位小数
	public static XSSFCellStyle CELLSTYLE_015_INTEGER_1(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_8(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中	
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[天蓝色2底,黑字,有边框]百分比
	public static XSSFCellStyle CELLSTYLE_015_PERCENT_1(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_8(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中	
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	
	//普通表体样式[深黄绿色底,黑字,有边框]
	public static XSSFCellStyle CELLSTYLE_016(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_9(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中	
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		return xssfCellStyle;
	}
	//普通表体样式[深黄绿色底,黑字,有边框]整数
	public static XSSFCellStyle CELLSTYLE_016_INTEGER(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_9(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中	
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[深黄绿色底,黑字,有边框]整数，1位小数
	public static XSSFCellStyle CELLSTYLE_016_INTEGER_1(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_9(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中	
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[深黄绿色底,黑字,有边框]百分比
	public static XSSFCellStyle CELLSTYLE_016_PERCENT_1(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_9(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中	
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表头的样式[黄底,红字,有边框]百分比 加粗
	public static XSSFCellStyle CELLSTYLE_017_PERCENT_2_BOLD(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_3(xssfCellStyle);
		CellStyleSetFont.red(xssfCellStyle, workbook);
		xssfCellStyle.getFont().setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);//加粗
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent_2(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表头的样式[粉红色底,红字,有边框]百分比 加粗
	public static XSSFCellStyle CELLSTYLE_018_PERCENT_2_BOLD(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_10(xssfCellStyle);
		CellStyleSetFont.red(xssfCellStyle, workbook);
		xssfCellStyle.getFont().setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);//加粗
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent_2(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[粉红色底,黑字,有边框]整数一位小数
	public static XSSFCellStyle CELLSTYLE_018_INTEGER_1(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_10(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[粉红色底,黑字,有边框]百分比,两位小数点
	public static XSSFCellStyle CELLSTYLE_018_PERCENT_2(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_0(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_10(xssfCellStyle);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent_2(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[白底,黑字Arial9,无边框]整数
	public static XSSFCellStyle CELLSTYLE_0019_INTEGER(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetFont.ZT(xssfCellStyle, workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);//水平居右
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[白底,黑字 ,Arial9号,无边框]整数一位小数
	public static XSSFCellStyle CELLSTYLE_0020_INTEGER_1(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetFont.ZT(xssfCellStyle, workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);//水平居右
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[浅橘黄色底,黑字,灰色边框]整数
	public static XSSFCellStyle CELLSTYLE_019_INTEGER(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_11(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_4(xssfCellStyle);
		CellStyleSetFont.ZT(xssfCellStyle, workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);//水平居右
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[白底,黑字,无边框]整数 斜体 加粗 
	public static XSSFCellStyle CELLSTYLE_0020(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetFont.STX(xssfCellStyle, workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		return xssfCellStyle;
	}
	//普通表体样式[浅橘黄色底,黑字 ,Arial9号,无边框]整数一位小数
	public static XSSFCellStyle CELLSTYLE_0021_INTEGER_1(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_11(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_4(xssfCellStyle);
		CellStyleSetFont.ZT(xssfCellStyle, workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);//水平居右
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.integer_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[白底,红字,无边框]百分比一位小数
	public static XSSFCellStyle CELLSTYLE_0023_PERCENT_1(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetFont.ZTRED(xssfCellStyle, workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);//水平居右
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[浅橘黄色,红字,无边框]百分比一位小数
	public static XSSFCellStyle CELLSTYLE_0121_percent_1(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		CellStyleSetCommon.setCellStyle_11(xssfCellStyle);
		CellStyleSetCommon.setCellStyle_4(xssfCellStyle);
		CellStyleSetFont.ZTRED(xssfCellStyle, workbook);
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);//水平居右
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		CellStyleSetFormat.percent_1(xssfCellStyle, workbook);
		return xssfCellStyle;
	}
	//普通表体样式[白底,黑字,无边框]整数
	public static XSSFCellStyle CELLSTYLE_0002(Workbook workbook){
		XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
		xssfCellStyle.setWrapText(true);//自动换行
		xssfCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
		xssfCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
		return xssfCellStyle;
	}
}