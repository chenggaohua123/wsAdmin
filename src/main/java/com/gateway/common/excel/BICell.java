package com.gateway.common.excel;

import java.io.InputStream;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;

//import com.yougou.bi.report.common.constans.PublicConstans;


public class BICell {
	
	public Cell cell;
	
	public BICell(Cell cell){
		this.cell = cell;
	}
	
	public BICell setCellValue(Object objectValue, CellStyle cellStyle) {
		cell.setCellStyle(cellStyle);
		if(objectValue == null){
			return this;
		}
		if(objectValue instanceof Double){
			setCellValue((Double)objectValue);
		}else{
			setCellValue(CellMathUtil.toString_1(objectValue));
		}
		return this;
	}
	
	private BICell setCellValue(String strValue) {
		if(StringUtils.isNotEmpty(strValue)){
			cell.setCellValue(strValue);
		}
		return this;
	}
	
	private BICell setCellValue(Double doubleValue) {
		cell.setCellValue(doubleValue);
		return this;
	}
	
	public BICell setCellStyle(CellStyle cellStyle) {
		cell.setCellStyle(cellStyle);
		return this;
	}
	
	//单元格合并[上下左右]
	public BICell setMergedRegion(int up, int down, int left, int right) {
		Sheet sheet = cell.getSheet();
		int rowIndex = cell.getRowIndex();
		int columnIndex = cell.getColumnIndex();
		
		int a_rowIndex = rowIndex-up;
		int a_columnIndex = columnIndex-left;
		
		int b_rowIndex = rowIndex+down;
		int b_columnIndex = columnIndex+right;
		
		CellRangeAddress cellRangeAddress = new CellRangeAddress(a_rowIndex, b_rowIndex, a_columnIndex, b_columnIndex);
		sheet.addMergedRegion(cellRangeAddress);
		return this;
	}
	
	//单元格分组[行:上下]
	public BICell setGroupRow(int up, int down) {
		Sheet sheet = cell.getSheet();
		int rowIndex = cell.getRowIndex();
		int a_rowIndex = rowIndex-up;
		int b_rowIndex = rowIndex+down;
		sheet.groupRow(a_rowIndex, b_rowIndex);
		return this;
	}
	
	//单元格分组[列:左右]
	public BICell setGroupColumn(int left, int right) {
		Sheet sheet = cell.getSheet();
		int columnIndex = cell.getColumnIndex();
		int a_columnIndex = columnIndex-left;
		int b_columnIndex = columnIndex+right;
		sheet.groupColumn(a_columnIndex, b_columnIndex);
		return this;
	}
	
	//单元格宽度[width==>字符]
	public BICell setColumnWidth(int width) {
		int columnIndex = cell.getColumnIndex();
		cell.getSheet().setColumnWidth(columnIndex, width*256);
		return this;
	}
	
	//单元格高度[height==>像素]
	public BICell setRowHeight(int height) {
		cell.getRow().setHeightInPoints(height);
		return this;
	}
	
//	public BICell setCellImg(String urlValue, CellStyle cellStyle){
//		//设置样式
//		cell.setCellStyle(cellStyle);
//		//设置单元格的值和样式
//		cell.setCellValue(urlValue);
//		if(StringUtils.isEmpty(urlValue)){
//			return this;
//		}
//		//如果不是以.jpg,.png,.gif结尾一律不处理
//		String urlStr = PublicConstans.PICTURE_URL+urlValue;
//		urlStr = urlStr.replace("\\", "/");
//		if(urlStr.endsWith(".jpg")){
//			urlStr = urlStr.replace(".jpg", "_"+PublicConstans.PIC_SIZE+".jpg");
//		}else if(urlStr.endsWith(".JPG")){
//			urlStr = urlStr.replace(".JPG", "_"+PublicConstans.PIC_SIZE+".JPG");
//		}else if(urlStr.endsWith(".jpeg")){
//			urlStr = urlStr.replace(".jpeg", "_"+PublicConstans.PIC_SIZE+".jpeg");
//		}else if(urlStr.endsWith(".JPEG")){
//			urlStr = urlStr.replace(".JPEG", "_"+PublicConstans.PIC_SIZE+".JPEG");
//		}else if(urlStr.endsWith(".png")){
//			urlStr = urlStr.replace(".png", "_"+PublicConstans.PIC_SIZE+".png");
//		}else if(urlStr.endsWith(".PNG")){
//			urlStr = urlStr.replace(".PNG", "_"+PublicConstans.PIC_SIZE+".PNG");
//		}else if(urlStr.endsWith(".gif")){
//			urlStr = urlStr.replace(".gif", "_"+PublicConstans.PIC_SIZE+".gif");
//		}else if(urlStr.endsWith(".GIF")){
//			urlStr = urlStr.replace(".GIF", "_"+PublicConstans.PIC_SIZE+".GIF");
//		}else if(urlStr.endsWith(".bmp")){
//			urlStr = urlStr.replace(".bmp", "_"+PublicConstans.PIC_SIZE+".bmp");
//		}else if(urlStr.endsWith(".BMP")){
//			urlStr = urlStr.replace(".BMP", "_"+PublicConstans.PIC_SIZE+".BMP");
//		}else{
//			return this;
//		}
//		//设置默认宽度
//		this.setRowHeight(70);
//		//设置单元格的值和样式
//		Sheet sheet = cell.getSheet();
//		Workbook workbook = sheet.getWorkbook();
//		//打开URL,若无法打开流则认为图片不存在.
//		URL url = null;
//		InputStream fis = null;
//		byte[] bytes = null;
//		try {
//			url = new URL(urlStr);
//			fis = url.openStream();
//			bytes = IOUtils.toByteArray(fis);
//			XSSFDrawing drawing = (XSSFDrawing)sheet.createDrawingPatriarch();
//			XSSFClientAnchor anchorInit = drawing.createAnchor(10000, 10000, Integer.MAX_VALUE, Integer.MAX_VALUE,cell.getColumnIndex(),cell.getRowIndex(),cell.getColumnIndex(),cell.getRowIndex());
//			int picIndex = workbook.addPicture(bytes, SXSSFWorkbook.PICTURE_TYPE_PNG);
//			drawing.createPicture(anchorInit, picIndex);
//		} catch (Exception e) {
//			//这里出错不处理
//		} finally{
//			IOUtils.closeQuietly(fis);
//		}
//		return this;
//	}
}

