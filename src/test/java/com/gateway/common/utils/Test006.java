package com.gateway.common.utils;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

import org.junit.Test;

import com.spire.xls.Chart;
import com.spire.xls.ExcelChartType;
import com.spire.xls.ExcelVersion;
import com.spire.xls.HorizontalAlignType;
import com.spire.xls.VerticalAlignType;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import com.spire.xls.charts.ChartSerie;
import com.spire.xls.core.IXLSRanges;

public class Test006 {

	public static void main(String[] args) throws FileNotFoundException {
		//创建Workbook对象
        Workbook workbook = new Workbook();

        //获取第一个工作表
        Worksheet sheet = workbook.getWorksheets().get(0);

        //将图表数据写入工作表
        sheet.getCellRange("A1").setValue("年份");
        sheet.getCellRange("A2").setValue("2002");
        sheet.getCellRange("A3").setValue("2003");
        sheet.getCellRange("A4").setValue("2004");
        sheet.getCellRange("A5").setValue("2005");

        sheet.getCellRange("B1").setValue("销售额");
        sheet.getCellRange("B2").setNumberValue(4000);
        sheet.getCellRange("B3").setNumberValue(6000);
        sheet.getCellRange("B4").setNumberValue(7000);
        sheet.getCellRange("B5").setNumberValue(8500);

        //设置单元格样式
        sheet.getCellRange("A1:B1").setRowHeight(15);
        sheet.getCellRange("A1:B1").getCellStyle().setColor(Color.darkGray);
        sheet.getCellRange("A1:B1").getCellStyle().getExcelFont().setColor(Color.white);
        sheet.getCellRange("A1:B1").getCellStyle().setVerticalAlignment(VerticalAlignType.Center);
        sheet.getCellRange("A1:B1").getCellStyle().setHorizontalAlignment(HorizontalAlignType.Center);
        sheet.getCellRange("B2:C5").getCellStyle().setNumberFormat("\"￥\"#,##0");

        Worksheet sheet1 = workbook.getWorksheets().get(1);
        sheet1.getCellRange("A1").setValue("年份");
        sheet1.getCellRange("A2").setValue("2020");
        sheet1.getCellRange("B1").setValue("销售额");
        sheet1.getCellRange("B2").setNumberValue(11000);
        //添加饼图
        Chart chart = sheet.getCharts().add(ExcelChartType.Pie);

        //设置图表数据区域
       // sheet.get
        chart.setDataRange(sheet.getCellRange("B2:B3").addCombinedRange(sheet.getCellRange("B5").addCombinedRange(sheet1.getCellRange("B2"))));
        chart.setSeriesDataFromRange(false);

        //设置图表位置
        chart.setLeftColumn(3);
        chart.setTopRow(1);
        chart.setRightColumn(11);
        chart.setBottomRow(20);

        //设置图表标题
        chart.setChartTitle("年销售额");
        chart.getChartTitleArea().isBold(true);
        chart.getChartTitleArea().setSize(12);

        //设置系列标签
        ChartSerie cs = chart.getSeries().get(0);
        cs.setCategoryLabels(sheet.getCellRange("A2:A3").addCombinedRange(sheet.getCellRange("A5").addCombinedRange(sheet1.getCellRange("A2"))));
        cs.setValues(sheet.getCellRange("B2:B3").addCombinedRange(sheet.getCellRange("B5").addCombinedRange(sheet1.getCellRange("B2"))));
        cs.getDataPoints().getDefaultDataPoint().getDataLabels().hasValue(true);
        chart.getPlotArea().getFill().setVisible(false);

        //保存文档
        OutputStream os=new FileOutputStream(new File("d:/饼图.xlsx"));
        workbook.setVersion(ExcelVersion.Version2016);
        workbook.saveToStream(os);
        
        //workbook.saveToFile("饼图.xlsx", ExcelVersion.Version2016);
        
	}
	
	@Test
	public void test001(){
		String str="www.baidu.com.www.";
		System.out.println(str.indexOf("www."));
		String str1=str.replaceFirst("www.", "");
		System.out.println("str1:"+str1);
	}

	
	@Test
	public void test002(){
		String SEED = "llE85E1D7521662D53E57A165F1EA29C2B855C6DE9EA1D6B3F";
		String sys=GoogleAuthenticator.generateSecretKey(SEED);
		System.out.println(sys);
	}
	
	@Test
	public void test003(){
		System.out.println(SHA256Utils.getSHA256Encryption("admin_c123456"));
		
		System.out.println(new Date().getTime()+"52");
	}
	
}
