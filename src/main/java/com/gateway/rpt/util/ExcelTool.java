package com.gateway.rpt.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelTool {
	private SimpleDateFormat formate = new SimpleDateFormat("yyy-MM-dd");
	private XSSFFont font = null;
	private XSSFWorkbook wb = null;

	/**
	 * 功能：导出标准风格的Excel文件
	 * @param title 报表标题
	 * @param condition 导出数据的条件
	 * @param tableHead  导出数据的导航标签
	 */
	public void exportCriterionExcel(String title, String condition, String[] tableHead, String[][] data, byte[] byteArray) 
	{
		// 起始行行号
		int startNo = 0;
		wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("new sheet");
		sheet.setDisplayGridlines(true);
		// 插入报表标题
		startNo = this.setBigTitle(title, sheet, tableHead.length, startNo);
		// 插入报表数据筛选条件
		if(null!=condition && !"".equals(condition)){
			startNo = this.setQueryCondition(condition, sheet, (short) tableHead.length, (short)startNo);
		}
		// 插入报表填报日期
		startNo = setCreateDate(sheet, (short) tableHead.length, (short)startNo);
		// 插入报表标题栏数据
		if (null!=tableHead && tableHead.length>0) {
			startNo = this.setGridHead(tableHead, sheet, (short) tableHead.length, (short)startNo);
		}
		// 插入报表记录数据
		if (null!=data && data.length>0) {
			startNo = this.setGridData(sheet, data, startNo);
		}
		// 插入报表统计图片
		if(null!=byteArray && byteArray.length>0) {
			startNo = this.setGraph(sheet, byteArray, (short)startNo);
		}
		// 自动设置最小列宽
		if(tableHead.length>0) {
			for(int i=0;i<tableHead.length;i++) {
				if(tableHead.length<6){
					sheet.setColumnWidth(i, (int)30000/tableHead.length);
				} else {
					sheet.setColumnWidth(i, 5000);
				}
			}
		}
	}

	/**
	 * 功能：导出简约风格的Excel文件
	 * @param title 报表标题
	 * @param tableHead 导出数据的导航标签
	 */
	public void exportSimpleExcel(String title, String[] tableHead, String[][] data) {
		// 起始行行号
		int startNo = 0;
		wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("new sheet");
		// 插入报表标题
//		if(null!=title) {
//			startNo = this.setBigTitle(title, sheet, tableHead.length, startNo);
//		}
		// 插入报表填报日期
//		startNo = this.setCreateDate(sheet, (short)tableHead.length, (short)startNo);
		// 插入报表标题栏数据
		if (null != tableHead) {
			startNo = this.setGridHead(tableHead, sheet, (short) tableHead.length, (short)startNo);
		}
		// 插入报表记录数据
		if (data.length>0) {
			startNo = this.setGridData(sheet, data, startNo);
		}
		// 自动设置最小列宽
		if(tableHead.length>0) {
			for(int i=0;i<tableHead.length;i++) {
				if(tableHead.length<6){
					sheet.setColumnWidth(i, (int)30000/tableHead.length);
				} else {
					sheet.setColumnWidth(i, 5000);
				}
			}
		}
	}

	/**
	 * 功能：导出简约风格的Excel文件
	 * @param title 报表标题
	 * @param tableHead 导出数据的导航标签
	 */
	public void exportSettleDetailExcel(String[] tableHead1, String[] tableHead2, 
			String[][] data1, String[][] data2) {
		// 起始行行号
		int startNo = 0;
		wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("new sheet");
		// 插入报表标题栏数据
		if (null != tableHead1) {
			startNo = this.setGridHead(tableHead1, sheet, (short) tableHead1.length, (short)startNo);
		}
		// 插入报表记录数据
		if (data1.length>0) {
			startNo = this.setGridData(sheet, data1, startNo);
		}
		// 插入数据二
		if (data2.length>0) {
			startNo = startNo + 2;
			// 插入报表标题栏数据
			if (null != tableHead2) {
				startNo = this.setGridHead(tableHead2, sheet, (short) tableHead2.length, (short)startNo);
			}
			// 插入报表记录数据
			if (data2.length>0) {
				startNo = this.setGridData(sheet, data2, startNo);
			}
		}
		
		// 自动设置最小列宽
		if(tableHead1.length>0) {
			for(int i=0;i<tableHead1.length;i++) {
				if(tableHead1.length<6){
					sheet.setColumnWidth(i, (int)30000/tableHead1.length);
				} else {
					sheet.setColumnWidth(i, 5000);
				}
			}
		}
	}
	
	public void exportSimpleExcel(XSSFWorkbook workBook,String sheetName,String title, String[] tableHead, String[][] data) {
		// 起始行行号
				int startNo = 0;
				if(null != workBook){
					wb = workBook;
				}else{
					wb = new XSSFWorkbook();
				}
				XSSFSheet sheet = wb.createSheet(sheetName);
				// 插入报表标题
//				if(null!=title) {
//					startNo = this.setBigTitle(title, sheet, tableHead.length, startNo);
//				}
				// 插入报表填报日期
//				startNo = this.setCreateDate(sheet, (short)tableHead.length, (short)startNo);
				// 插入报表标题栏数据
				if (null != tableHead) {
					startNo = this.setGridHead(tableHead, sheet, (short) tableHead.length, (short)startNo);
				}
				// 插入报表记录数据
				if (data.length>0) {
					startNo = this.setGridData(sheet, data, startNo);
				}
				// 自动设置最小列宽
				if(tableHead.length>0) {
					for(int i=0;i<tableHead.length;i++) {
						if(tableHead.length<6){
							sheet.setColumnWidth(i, (int)30000/tableHead.length);
						} else {
							sheet.setColumnWidth(i, 5000);
						}
					}
				}
	}
	
	/**
	 * 功能：导出简约风格的Excel文件
	 * @param title 报表标题
	 * @param tableHead 导出数据的导航标签
	 */
	public void exportSimpleExcel(XSSFWorkbook workBook,XSSFSheet sheet ,String sheetName,String title, String[] tableHead, String[][] data,int startNo) {
		// 起始行行号
				if(null != workBook){
					wb = workBook;
				}else{
					wb = new XSSFWorkbook();
				}
				// 插入报表标题
//				if(null!=title) {
//					startNo = this.setBigTitle(title, sheet, tableHead.length, startNo);
//				}
				// 插入报表填报日期
//				startNo = this.setCreateDate(sheet, (short)tableHead.length, (short)startNo);
				// 插入报表标题栏数据
				if (null != tableHead) {
					startNo = this.setGridHead(tableHead, sheet, (short) tableHead.length, (short)startNo);
				}
				// 插入报表记录数据
				if (data.length>0) {
					startNo = this.setGridData(sheet, data, startNo);
				}
				// 自动设置最小列宽
				if(null != tableHead &&  tableHead.length>0) {
					for(int i=0;i<tableHead.length;i++) {
						if(tableHead.length<6){
							sheet.setColumnWidth(i, (int)30000/tableHead.length);
						} else {
							sheet.setColumnWidth(i, 5000);
						}
					}
				}
	}
	
	/**
	 * 功能：往导出报表中写入列表记录数据
	 * @param sheet 当前页签
	 * @param data 列表数据
	 * @param startNo 从第几行开始写入记录
	 */
	private short setGridData(XSSFSheet sheet, String[][] data, int startNo) {
		XSSFCellStyle cellStyle = wb.createCellStyle();
		int rowNo = startNo;
		XSSFRow row;
		
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderTop((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderBottom((short) 1);
		cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		for (int i = 0; i < data.length; i++) {
			rowNo = startNo+i;
			row = sheet.createRow(rowNo);
			this.inverData(data[i], row, cellStyle);
		}
		return (short)(rowNo+2);
	}
	/**
	 * 功能：在Excel中导出图片
	 * @param sheet 图片导入的工作表
	 * @param img 导入的图片字节数组
	 * @param startNo 从哪行开始导入
	 * @return
	 */
	private short setGraph(XSSFSheet sheet, byte[] img, Short startNo)
	{
		XSSFDrawing patriarch = sheet.createDrawingPatriarch(); 
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 400, 255, (short)1, startNo, (short)4, 25); 
        patriarch.createPicture(anchor , wb.addPicture(img, XSSFWorkbook.PICTURE_TYPE_PNG));
        return (short)(startNo+1);
	}

	/**
	 * 功能：把数组数据写入到Excel表格中
	 * @param data 数据
	 * @param row
	 */
	private void inverData(String[] data, XSSFRow row, XSSFCellStyle cellStyle) {
		int colindex = 0;
		XSSFCell cell = null;
		for (int i = 0; i < data.length; i++) {
			cell = row.createCell(colindex);
			cell.setCellStyle(cellStyle);
			colindex++;
			cell.setCellValue(data[i]);
		}
	}

	/**
	 *  功能：设置Excel报表标题
	 * @param Bigtitle 标题
	 * @param sheet 工作表
	 * @param endClumNo 结束列序号
	 * @param startNo 开始行序号
	 * @return 下一开始行序号
	 */
	private int setBigTitle(String Bigtitle, XSSFSheet sheet, int endClumNo, int startNo) {
		XSSFRow row = sheet.createRow(startNo);
		row.setHeight((short)1200);
		XSSFCellStyle cellStyle = wb.createCellStyle();
		font = wb.createFont();
		XSSFCell cell;
		
		// (赵始行,起始列,结束行,结束列)
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (endClumNo - 1)));
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 18);
		font.setFontName("黑体");
		cellStyle.setFont(font);
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

		cell = row.createCell(0);
		// 设置cell编码解决中文高位字节截断
		// cell.setEncoding(XSSFCell.ENCODING_UTF_16);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new HSSFRichTextString(Bigtitle==null?"":Bigtitle));
		// 动态设置sheet宽度
		sheet.setColumnWidth(0, (1000 * endClumNo));
		return (startNo+1);
	}

	private int setQueryCondition(String condition, XSSFSheet sheet, int endClumNo, int startNo) {
		XSSFRow row = sheet.createRow(startNo);
		row.setHeight((short) 900);
		XSSFCellStyle cellStyle = wb.createCellStyle();
		font = wb.createFont();
		XSSFCell cell;
		
		sheet.addMergedRegion(new CellRangeAddress(startNo, startNo, 0, (endClumNo-1)));
		font.setColor(HSSFColor.BLACK.index);
		// 字体加粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 字体大小
		font.setFontHeightInPoints((short) 11);
		font.setFontName("宋体");
		cellStyle.setWrapText(true);
		cellStyle.setFont(font);
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		cell = row.createCell(0);
		// cell.setEncoding(XSSFCell.ENCODING_UTF_16);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(condition);
		return (startNo+1);
	}

	private int setCreateDate(XSSFSheet sheet, int endClumNo, int startNo) {
		XSSFRow row = sheet.createRow(startNo);
		row.setHeight((short) 500);
		XSSFCellStyle cellStyle = wb.createCellStyle();
		XSSFCell cell;
		sheet.addMergedRegion(new CellRangeAddress(startNo, startNo, 0, (endClumNo - 1)));
		font = wb.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 11);
		font.setFontName("宋体");
		cellStyle.setFont(font);
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		cell = row.createCell(0);
		// cell.setEncoding(XSSFCell.ENCODING_UTF_16);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new HSSFRichTextString("日期：" + formate.format(new Date())));
		return (startNo+1);
	}

	/**
	 * 设置Excel标题头
	 * 
	 * @param title
	 * @param row
	 * @param cellStyle
	 */
	private int setGridHead(String[] head, XSSFSheet sheet, int endClumNo, int startNo) {
		XSSFRow row = sheet.createRow(startNo);
		XSSFCellStyle cellStyle = wb.createCellStyle();
		font = wb.createFont();
		// 自定义颜色
		// HSSFPalette palette = wb.getCustomPalette();
		// palette.setColorAtIndex((short)10, (byte)102, (byte)153, (byte)0);
		// cellStyle.setFillForegroundColor((short)10);
		XSSFCell cell;
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(HSSFColor.LIME.index);
		cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		font.setFontHeightInPoints((short) 11);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		for (int i = 0; i < head.length; i++) {
			cell = row.createCell(i);
			// CellRangeAddress region = new CellRangeAddress(startNo, startNo+1, i, i);
			// sheet.addMergedRegion(region);
			//this.setRegionStyle(sheet, region, cellStyle);
			// cell.setEncoding(XSSFCell.ENCODING_UTF_16);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(head[i]);
			sheet.setColumnWidth(i, (short) (1000 * head[i].length()));
		}
		row.setHeight((short)400);
		return (short)(startNo+1);
	}

	/**
	 * 功能：合并单元格后的样式控制
	 * @param sheet 需要控制样式的页签
	 * @param region 合并单元格的区域对象
	 * @param cs 合并后的样式
	 */
	@SuppressWarnings("unused")
	private void setRegionStyle(XSSFSheet sheet, CellRangeAddress region, XSSFCellStyle cs)
	{
		for (int i = region.getFirstRow(); i <= region.getLastRow(); i++)
		{
			XSSFRow row = sheet.getRow(i);
			if(null==row)
			{
				row = sheet.createRow((short)i);
			}
			if (region.getFirstColumn() <= region.getLastColumn())
			{
				for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) 
				{
					XSSFCell cell = row.getCell(j);
					if (cell == null)
					{
						cell = row.createCell(j);
					}
					cell.setCellStyle(cs);
				}
			}
		}
	}

	/**
	 * 功能：把Excel转换为InputStream
	 * @return InputStream
	 * @throws IOException
	 */
	public byte[] toOutputRespond() throws IOException {
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		wb.write(byteArray);
		wb = null;
		return byteArray.toByteArray();
	}
	/**
	 * 功能：把Excel转换为Byte
	 * @return byte
	 * @throws IOException
	 */
	public byte[] toByteOutputRespond() throws IOException {
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		wb.write(byteArray);
		wb = null;
		return byteArray.toByteArray();
	}
}
