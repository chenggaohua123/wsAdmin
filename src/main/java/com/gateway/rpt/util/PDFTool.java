package com.gateway.rpt.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



public class PDFTool {
	SimpleDateFormat formate = new SimpleDateFormat("yyy-MM-dd");
	private Document document = null;
	private PdfPTable table = null;
	ByteArrayOutputStream byteArray = null;

	/**
	 * 功能：导出标准格风格的PDF文件
	 * @param title   报表标题
	 * @param data  导出报表数据
	 * @param condition 导出数据的条件
	 * @param headData 导出数据的导航标签
	 * @param imgByte 导出图片的字节数组
	 * @throws DocumentException 
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	public void exportCriterionPDF(String title, String[][]data, String[][] condition, String[] headData, byte[] imgByte) throws DocumentException, IOException
	{
		byteArray = new ByteArrayOutputStream();
		document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, byteArray);
		document.open();
		// BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		BaseFont bf = BaseFont.createFont( "c://windows//fonts//simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		table = new PdfPTable(headData.length);
	    table.setHorizontalAlignment(table.ALIGN_LEFT);
	    table.setWidthPercentage(80f);
        
	    // 插入报表标题
        this.setTitle(title, bf);
        // 插入报表数据筛选条件
        this.setCondition(condition, bf);
        // 插入报表填报日期
        this.setCreateDate("日期：" + formate.format(new Date()), bf);
        // 插入报表标题栏数据
        this.setGridHead(headData, bf);
        // 插入报表记录数据
        this.setGridData(data, bf);
        document.add(table);
        // 插入报表统计图片
        this.setGraph(imgByte);
	}
	/**
	 * 功能：导出简约风格的PDF文件
	 * @param title 报表标题
	 * @param tableHead 导出数据的导航标签
	 */
	@SuppressWarnings("static-access")
	public void exportSimpleExcel(String title, String[][]data, String[] headData) throws DocumentException, IOException
	{
		byteArray = new ByteArrayOutputStream();
		document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, byteArray);
		document.open();
		
		BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		table = new PdfPTable(headData.length);
	    table.setHorizontalAlignment(table.ALIGN_LEFT);
	    table.setWidthPercentage(80f);
	    
	    // 插入报表标题
        this.setTitle(title, bf);
        // 插入报表填报日期
        this.setCreateDate("日期：" + formate.format(new Date()), bf);
        // 插入报表标题栏数据
        this.setGridHead(headData, bf);
        // 插入报表记录数据
        this.setGridData(data, bf);
        document.add(table);
	}
	@SuppressWarnings("static-access")
	private void setTitle(String title, BaseFont bf) throws DocumentException
	{
		Font font = new Font(bf, 18, Font.NORMAL);
		font.setStyle(font.BOLD);
		Paragraph paTitle=new Paragraph(title, font);
		paTitle.setAlignment(paTitle.ALIGN_CENTER);
		document.add(paTitle);
		document.add(new Chunk("\n"));
	}
	@SuppressWarnings("static-access")
	private void setCondition(String[][] data, BaseFont bf) throws DocumentException
	{
		Font font = new Font(bf, 12, Font.NORMAL);
		String[] condition = this.generateCondition(data);
		for(int i=0;i<condition.length;i++){
			Paragraph paCondition = new Paragraph(condition[i], font);
			paCondition.setAlignment(paCondition.ALIGN_LEFT);
			document.add(paCondition);
			document.add(new Chunk());
		}
	}
	@SuppressWarnings("static-access")
	private void setCreateDate(String date, BaseFont bf) throws DocumentException
	{
		Font font = new Font(bf, 12, Font.NORMAL);
		Paragraph paDate = new Paragraph(date, font);
		paDate.setAlignment(paDate.ALIGN_LEFT);
		document.add(paDate);
		document.add(new Chunk());
	}
	@SuppressWarnings("static-access")
	private void setGridHead(String[] data, BaseFont bf)
	{
		PdfPCell cell = null;
		Font font = new Font(bf, 13, Font.NORMAL);
		font.setStyle(font.BOLD);
		table.setHeaderRows(0);
		for(int i=0;i<data.length;i++)
		{
			Paragraph graph = new Paragraph(data[i], font);
			cell = new PdfPCell(graph);
			cell.setBackgroundColor(new BaseColor(153, 204, 0));
			cell.setFixedHeight(25f);
			cell.setHorizontalAlignment(graph.ALIGN_CENTER);
			cell.setVerticalAlignment(graph.ALIGN_MIDDLE);
			table.addCell(cell);
		}
	}
	private void setGridData(String[][] data, BaseFont bf)
	{
		Font font = new Font(bf, 12, Font.NORMAL);
		PdfPCell cell = null;
		for(int i=0;i<data.length;i++) {
			String[] tempData = data[i];
			for(int j=0;j<tempData.length;j++) {
				cell = new PdfPCell(new Paragraph(tempData[j], font));
       		 	table.addCell(cell);
			}
		}
	}
	private void setGraph(byte[] imgByte) throws MalformedURLException, IOException, DocumentException
	{
		Image img = Image.getInstance(imgByte);
		// 图片比例系数
		img.setXYRatio(0.3f);
		document.add(img);
	}
	private String[] generateCondition(String[][] data) {
		String[] result = new String[data.length];
		if(null!=data) {
			for(int i=0;i<data.length;i++) {
				String[] tempData = data[i];
				StringBuilder sb = new StringBuilder();
				for(int j=0;j<tempData.length;j++) {
					if(!"".equals(tempData[j])) {
						sb.append(tempData[j]);
						if(j%2==0) {
							sb.append("：");
						} else {
							sb.append("     ");
						}
					}
				}
				result[i] = sb.toString();
			}
		}
		return result;
	}
	/**
	 * 功能：把Excel转换为InputStream
	 * 
	 * @return InputStream
	 * @throws IOException
	 * @throws DocumentException 
	 */
	public InputStream toOutputRespond() throws IOException, DocumentException {
		document.close();
		document = null;
		return new ByteArrayInputStream(byteArray.toByteArray());
	}
}
