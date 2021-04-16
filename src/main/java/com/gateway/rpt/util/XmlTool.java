package com.gateway.rpt.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


public class XmlTool {

	SimpleDateFormat formate = new SimpleDateFormat("yyy-MM-dd");
	ByteArrayOutputStream byteArray = null;
	Document doc = null;
	Element root = null;
	
	/**
	 * 功能：导出标准风格的XML文件
	 * @param title
	 * @param condition
	 * @param dataHead
	 * @param data
	 */
	public void exportCriterionXML(String title, String[][] condition, String[] dataHead, String[][] data)
	{
		doc = DocumentHelper.createDocument();
		root = doc.addElement("Root");
		// 插入报表标题
		this.setTitle(title);
		// 插入报表填报日期
		this.setCreateDate();
		// 插入报表数据筛选条件
		this.setCondition(condition);
		// 插入报表标题栏数据
		this.setDataHead(dataHead);
		// 插入报表记录数据
		this.setDataList(data);
	}
	/**
	 * 功能：导出简约风格的XML文件
	 * @param title
	 * @param dataHead
	 * @param data
	 */
	public void exportSimpleXML(String title, String[] dataHead, String[][] data)
	{
		doc = DocumentHelper.createDocument();
		root = doc.addElement("Root");
		
		// 插入报表标题
		this.setTitle(title);
		// 插入报表填报日期
		this.setCreateDate();
		// 插入报表标题栏数据
		this.setDataHead(dataHead);
		// 插入报表记录数据
		this.setDataList(data);
	}
	/**
	 * 功能：生成报表标题
	 * @param title
	 */
	private void setTitle(String title) {
		Element xmlTitle = root.addElement("Title");
		xmlTitle.setText(title==null?"":title);
	}
	/**
	 * 功能：生成填报日期
	 */
	private void setCreateDate() {
		Element xmlCreateDate = root.addElement("CreatedDate");
		xmlCreateDate.setText(formate.format(new Date()));
	}
	/**
	 * 功能：生成报表条件
	 * @param data
	 */
	private void setCondition(String[][] data) {
		Element xmlConditionData = root.addElement("DataCondition");
		if(null!=data) {
			for(int i=0;i<data.length;i++) {
				Element xmlRowData = xmlConditionData.addElement("RowData");
				String[] rowData = data[i];
				for(int j=0;j<rowData.length;j++){
					Element xmlColumnData = xmlRowData.addElement("ColumnData");
					xmlColumnData.setText(rowData[j]);
				}
			}
		}
	}
	/**
	 * 功能：生成报表列表头
	 * @param dataHead
	 */
	private void setDataHead(String[] dataHead) {
		Element xmlDataHead = root.addElement("DataHead");
		Element xmlRowData = xmlDataHead.addElement("RowData");
		if(null!=dataHead)
		{
			for(int i=0;i<dataHead.length;i++) {
				Element xmlColumnData = xmlRowData.addElement("ColumnData");
				xmlColumnData.setText(dataHead[i]);
			}
		}
	}
	/**
	 * 功能：生成报表数据列表
	 * @param data
	 */
	private void setDataList(String[][] data) {
		Element xmlData = root.addElement("DataList");
		if(null!=data) {
			for(int i=0;i<data.length;i++) {
				Element xmlRowData = xmlData.addElement("RowData");
				String[] rowData = data[i];
				for(int j=0;j<rowData.length;j++){
					Element xmlColumnData = xmlRowData.addElement("ColumnData");
					xmlColumnData.setText(rowData[j]==null?"":rowData[j]);
				}
			}
		}
	}
	/**
	 * 功能：把XML转换为InputStream
	 * @return InputStream
	 * @throws IOException
	 */
	public InputStream toOutputRespond() throws IOException
	{
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		OutputFormat formate = new OutputFormat();
		formate.setEncoding("UTF-8");
		XMLWriter writer = new XMLWriter(formate);
		writer.setOutputStream(byteArray);
		writer.write(doc);
		doc = null;
		return new ByteArrayInputStream(byteArray.toByteArray());
	}
}
