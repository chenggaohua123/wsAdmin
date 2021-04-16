package com.gateway.rpt.service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jxl.read.biff.BiffException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Service;

import com.gateway.common.excetion.ServiceException;
import com.gateway.rpt.util.ExcelTool;

@SuppressWarnings("unchecked")
@Service(value = "fileReportService")
public class FileReportServiceImpl implements IFileReportService {

	protected static final Logger logger = Logger.getLogger(FileReportServiceImpl.class);

	public byte[] exportSimpleExcel(String title, String[] headData, String[][] data) throws ServiceException{
		String erroMsg = null;
		ExcelTool tool = new ExcelTool();
		tool.exportSimpleExcel(title, headData, data);
		try {
			return tool.toByteOutputRespond();
		} catch (IOException e) {
			erroMsg = "Excel报表转换为输入流时发生异常！";
			logger.info(erroMsg);
			throw new ServiceException(erroMsg);
		}
	}
	/**
	 * 功能：导出最简单的Excel报表，只有标题 + 导出时间 + 数据列表
	 * @param headData 导出列表的列表头
	 * @param data1 导出的结算交易数据
	 * @param data2 导出的结算保证金数据
	 * @return 报表文件输入流
	 */
	public byte[] exportSettleDetailExcel(String[] headData1, String[] headData2, 
			String[][] data1, String[][] data2)throws ServiceException {
		String erroMsg = null;
		ExcelTool tool = new ExcelTool();
		tool.exportSettleDetailExcel(headData1, headData2, data1, data2);
		try {
			return tool.toByteOutputRespond();
		} catch (IOException e) {
			erroMsg = "Excel报表转换为输入流时发生异常！";
			logger.info(erroMsg);
			throw new ServiceException(erroMsg);
		}
	}
	public byte[] exportCriterionExcel(String title, String[][] condition, String[] headData, String[][] data, byte[] graphByte) throws ServiceException{
		String erroMsg = null;
		ExcelTool tool = new ExcelTool();
		tool.exportCriterionExcel(title, this.generateStatisticsCondition(condition), headData, data, graphByte);
		try {
			return tool.toByteOutputRespond();
		} catch (IOException e) {
			erroMsg = "Excel报表转换为输入流时发生异常！";
			logger.info(erroMsg);
			throw new ServiceException(erroMsg);
		}
	}
	public List<Object> importExcelReport(String entityName, String[] propertyName, InputStream inStream) throws ServiceException{
		String erroMsg = null;
		List entityList = null;
		if(null!=entityName && !"".equals(entityName) && null!=inStream && null!=propertyName && propertyName.length>0) {
			try {
				String data[][] = this.convertFileToExcelData(inStream, propertyName.length);
				entityList = this.convertDataToEntity(data, entityName, propertyName);
			} catch (ServiceException e) {
				throw new ServiceException(e.getMessage());
			} catch (ClassNotFoundException e) {
				erroMsg = "没有找到指定的实体对象，实体对象名称必须是全路径的名称！";
				logger.info(erroMsg);
				throw new ServiceException(erroMsg);
			} catch (InstantiationException e) {
				erroMsg = "将传入的实体重新实例化为对象时报错！";
				logger.info(erroMsg);
				throw new ServiceException(erroMsg);
			} catch (IllegalAccessException e) {
				erroMsg = "往实体对象的私有属性中设置数据报错！";
				logger.info(erroMsg);
				throw new ServiceException(erroMsg);
			} catch (SecurityException e) {
				erroMsg = "执行安全性出现异常！";
				logger.info(erroMsg);
				throw new ServiceException(erroMsg);
			} catch (NoSuchFieldException e) {
				erroMsg = "在传入的实体对象中没有找到对应的属性！";
				logger.info(erroMsg);
				throw new ServiceException(erroMsg);
			} catch (IOException e) {
				erroMsg = "导入文件输入流转换为文件流时出现异常！";
				logger.info(erroMsg);
				throw new ServiceException(erroMsg);
			}
		} else {
			erroMsg = "没有提供此文件类型的报表服务！";
			logger.info(erroMsg);
			throw new ServiceException(erroMsg);
		}
		return entityList;
	}
	
	/**
	 * 功能：将输入文件转换为Excel文件并读取文件中的数据
	 * @param 输入文件
	 * @param filedNameSize 导入Excel的列的规定大小
	 * @return 文件输入流中的数据
	 * @throws IOException 
	 * @throws IOException 
	 * @throws ServiceException 
	 * @throws BiffException 
	 */
	private String[][] convertFileToExcelData(InputStream inStream, int columnSize) throws IOException, ServiceException
	{
		String erroMsg = null;
		if (null != inStream) {
			HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(inStream));
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			// 获取行、列总数
			int rowSize = sheet.getLastRowNum();
			HSSFRow headRow = sheet.getRow(0);  
			int columSize = headRow.getPhysicalNumberOfCells();
			
			if(rowSize<1) {
				erroMsg = "导入文件的内容不能为空！";
				logger.info(erroMsg);
				throw new ServiceException(erroMsg);
			} 
			else if(columSize!=columnSize) {
				erroMsg = "传入实体属性数量与导入文件中的列数量不一致！";
				logger.info(erroMsg);
				throw new ServiceException(erroMsg);
			}
			else {
				// 存放Excel表格中的数据
				String data[][] = new String[rowSize][columSize];
				
				for (int i = 1; i <= rowSize; i++) {
					if (null != sheet.getRow(i)) {
						// 遍历读取Excel表格中的数据
						HSSFRow row = sheet.getRow(i);
						for(int j=0;j<row.getPhysicalNumberOfCells();j++) {
							if(null!=row.getCell(j)) {
								row.getCell(j).setCellType(1);
								data[i-1][j] = String.valueOf(row.getCell(j).getRichStringCellValue());
							}
							else {
								data[i-1][j] = "";
							}
						}
					}
					else {
						continue;
					}
				}
				return data;
			}
		} 
		else {
			erroMsg = "导入失败，导入文件为空！";
			logger.info(erroMsg);
			throw new ServiceException(erroMsg);
		}
	}

	/**
	 * 功能：将文件中的数据转换到实体对象中
	 * 
	 * @param data
	 *            文件中的数据列表
	 * @param entityName
	 *            实体对象全称
	 * @param filedName
	 *            实体对象属性列表
	 * @return 实体对象列表
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws ServiceException
	 */
	private List<Object> convertDataToEntity(String[][] data,
			String entityName, String[] filedName)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, SecurityException, NoSuchFieldException,
			ServiceException {
		// 数据实例列表
		List<Object> instanceList = new ArrayList();

		Class cla = Thread.currentThread().getContextClassLoader().loadClass(entityName);

		// 循环取出文件中的数据
		for (int i = 0; i < data.length; i++) {
			// 取出文件中的数据列表中的一行数据
			String[] tempData = data[i];
			Object instance = cla.newInstance();
			for (int k = 0; k < tempData.length; k++) {
				if (!"".equals(filedName[k].trim())) {
					// 往实体属性中写入数据
					Field field = cla.getDeclaredField(filedName[k]);
					field.setAccessible(true);
					if(field.getGenericType().toString().equals("class java.lang.Double")){
						field.set(instance, Double.parseDouble(tempData[k]));
					} else if(field.getGenericType().toString().equals("class java.lang.Integer")){
						field.set(instance, Integer.parseInt(tempData[k]));
					} else if(field.getGenericType().toString().equals("class java.lang.Integer")){
						field.set(instance, Integer.parseInt(tempData[k]));
					} else if(field.getGenericType().toString().equals("int")){
						field.set(instance, Integer.parseInt(tempData[k]));
					} else if(field.getGenericType().toString().equals("class java.math.BigDecimal")){
						field.set(instance, new BigDecimal(tempData[k]));
					} else {
						field.set(instance, tempData[k]);
					}
				}
			}
			instanceList.add(instance);
		}
		return instanceList;
	}

	/**
	 * 功能：将查询条件数组转换为字符串
	 * @param data 查询条件数组
	 * @return 查询条件的字符串
	 */
	private String generateStatisticsCondition(String[][] data) {
		StringBuilder sb = new StringBuilder();
		if(null!=data) {
			for(int i=0;i<data.length;i++) {
				String[] tempData = data[i];
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
				sb.append("\n");
			}
		}
		return sb.toString();
	}

}
