package com.gateway.rpt.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gateway.common.excetion.ServiceException;

@Service
public interface IFileReportService {

	/**
	 * 功能：导出最简单的Excel报表，只有标题 + 导出时间 + 数据列表
	 * @param title 报表标题
	 * @param headData 导出列表的列表头
	 * @param data 导出的列表数据
	 * @return 报表文件输入流
	 */
	public byte[] exportSimpleExcel(String title, String[] headData, String[][] data)throws ServiceException;
	/**
	 * 功能：导出最简单的Excel报表，只有标题 + 导出时间 + 数据列表
	 * @param headData 导出列表的列表头
	 * @param data1 导出的结算交易数据
	 * @param data2 导出的结算保证金数据
	 * @return 报表文件输入流
	 */
	public byte[] exportSettleDetailExcel(String[] headData1, String[] headData2, 
			String[][] data1, String[][] data2)throws ServiceException;
	/**
	 * 功能：导出标准风格的Excel报表，标题 + 导出条件 + 时间 + 数据列表 + 图形（可选）
	 * @param title 报表标题
	 * @param condition 统计条件
	 * @param headData 列表的列表头
	 * @param data 数据列表
	 * @param graphByte 图形数据
	 * @return 报表文件输入流
	 */
	public byte[] exportCriterionExcel(String title, String[][] condition, String[] headData, String[][] data, byte[] graphByte)throws ServiceException;
	/**
	 * 功能：导入Excel文件类型报表
	 * @param entityName 实体名称
	 * @param propertyName 实体中的属性列表
	 * @param inStream  导入报表的文件输入流
	 * @return 实体对象列表
	 */
	public List<Object> importExcelReport(String entityName, String[] propertyName, InputStream inStream)throws ServiceException;
}
