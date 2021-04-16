package com.gateway.common.excel;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;



public class BIWorkbookReader { 
	
	public BIWorkbookReader(BIWorkbook bw){
		//默认从第0行开始读
		indexRowNum = 0;
		//默认读最后一行的偏移量为0[偏移量可正可负][正值为少读的行数]
		lastOffsetRowNum = 0;
		//默认从第0列开始读
		indexColumnNum = 0;
		//默认读最后一列的偏移量为0[偏移量可正可负][正值为少读的行数，负值为多度的行数]
		lastOffsetColumnNum = 0;
		//默认表头高度0
		tabTheadNum = 0;
		//是否读取表头样式
		tabTheadStyle = false;
		//是否读取表体样式
		tabBodyStyle = false;
		//设置bw对象
		this.bw = bw;
	}
	
	private int indexRowNum;
	
	private int lastOffsetRowNum;
	
	private int indexColumnNum;
	
	private int lastOffsetColumnNum;
	
	private int tabTheadNum;
	
	private boolean tabTheadStyle; //是否读取表头样式
	
	private boolean tabBodyStyle; //是否读取表体样式
	
	private BIWorkbook bw;
	
	private Map<String,DecimalFormat> decimalFormatCach = Maps.newHashMap();

	/**
	 * 读取excel文
	 * 
	 * @param wb
	 * @param sheetIndex
	 *            sheet页下标：从0开始
	 * @param startReadLine
	 *            开始读取的行:从0开始
	 * @param tailLine
	 *            去除最后读取的行 theadArr tbodyArr
	 */
	public Map<String,Object> readExcel() {
		int bsIndex = 0;
		Map<String,Object> bwMap = Maps.newHashMap();
		for(BISheet bs : bw.bsList){
			//如果设置了表头高度使用设置的值 //若没有设置则使用bs的值
			int bs_tabTheadNum = 0;
			if(tabTheadNum==0){
				bs_tabTheadNum = bs.tabTheadNum;
			}else{
				bs_tabTheadNum = tabTheadNum;
			}
			Map<String,Object> bsMap = Maps.newHashMap();
			bwMap.put("bs"+bsIndex, bsMap);
			//*****************************************读取表头************************************************[只读数字格式化]
			List<BIRow> headList = bs.brList.subList(indexRowNum, indexRowNum+bs_tabTheadNum);
			List<Map<String,String>> theadArr = Lists.newArrayList();
			bsMap.put("theadArr", theadArr);
			for(BIRow br : headList){
				addCell(theadArr,br.bcList);
			}
			//*****************************************读取表体*********************************************[数字格式化+字体颜色]
			List<BIRow> bodyList = bs.brList.subList(indexRowNum+bs_tabTheadNum,bs.brList.size());
			List<Map<String,String>> tbodyArr = Lists.newArrayList();
			bsMap.put("tbodyArr", tbodyArr);
			int tempMaxRead = 0;
			for(BIRow br : bodyList){
				addCell(tbodyArr,br.bcList);
				tempMaxRead++;
				//最多读1000行
				if(tempMaxRead>1000){
					break;
				}
			}
			bsIndex++;
		}
		return bwMap;
	}
	
	private void addCell(List<Map<String,String>> list , List<BICell> bcList){
		Map<String,String> bcMap = Maps.newHashMap();
		list.add(bcMap);
		for(BICell bc : bcList){
			String key = bc.cell.getColumnIndex()+"";
			String value = getFormatCellValue(bc.cell);
			bcMap.put(key, value);
		}
	}
	
//	/**
//	  * @Title: 获取一个单元格转换成HTML
//	  * @Description: TODO
//	  * @param @param cell
//	  * @param @return    设定文件
//	  * @return String    返回类型
//	  * @throws
//	 */
//	public String getHtmlCellValue(Font font,Cell cell,Map<String,Object> ruleMap) {
//		//读取文字数据
//	    String resultValue = PoiUtils.getFormatCellValue(cell);
//	    if(resultValue == null || StringUtils.isEmpty(resultValue.trim())){
//	    	return "";
//	    }
//		//单元格字体只有当规则样式取不到颜色的时候,才去单元格的样式
//	    String fontColor = PoiUtils.getFontColor(cell, font);
//		//黑色白色字体不返回
//		if(fontColor.equals("#000000") || fontColor.equals("#FFFFFF")){
//			fontColor = "";
//		}
//		//有颜色才生成
//	    if(fontColor.length()>0){
//	    	resultValue = "<span style='color:"+fontColor+"'>"+resultValue+"</span>";
//	    }
//	    return resultValue;
//	}
	
	private String getFormatCellValue(Cell cell) {
		String cellStr = "";
		if (cell == null) {
			return "";
		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			cellStr = getBooleanCellValue(cell);
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			cellStr = getNumericCellValue(cell);
		} else {
			cellStr = getStringCellValue(cell);
		}
		return cellStr;
	}
	
	private String getBooleanCellValue(Cell cell){
		String cellValue = String.valueOf(cell.getBooleanCellValue());
		return cellValue;
	}
	
	private String getNumericCellValue(Cell cell){
		//定义返回字符串
		String cellValue = "";
		//获得格式化
		CellStyle xs = cell.getCellStyle();
		String formatStr = xs.getDataFormatString();
		//检查缓存中是否有此DecimalFormat
        DecimalFormat df = decimalFormatCach.get(formatStr);
		try {
			//使用已经存在的DecimalFormat
			if (df != null){
				cellValue = df.format(cell.getNumericCellValue());
			//使用简单的DecimalFormat[#,##0 ...... 0.0% ...... 等等]
			}else if (formatStr.lastIndexOf("%") > 0 || formatStr.indexOf(",") > 0) {
				df = new DecimalFormat(formatStr);
				decimalFormatCach.put(formatStr, df);
				cellValue = df.format(cell.getNumericCellValue());
			}else {
				cellValue = String.valueOf(cell.getNumericCellValue());
			}
		} catch (Exception e) {
			cellValue = "格式转换出错请核对!";
		}
		return cellValue;
	}
	
	private String getStringCellValue(Cell cell){
		String cellValue = String.valueOf(cell.getStringCellValue()); 
		return cellValue;
	}
	
	public int getIndexRowNum() {
		return indexRowNum;
	}

	public void setIndexRowNum(int indexRowNum) {
		this.indexRowNum = indexRowNum;
	}

	public int getLastOffsetRowNum() {
		return lastOffsetRowNum;
	}

	public void setLastOffsetRowNum(int lastOffsetRowNum) {
		this.lastOffsetRowNum = lastOffsetRowNum;
	}

	public int getIndexColumnNum() {
		return indexColumnNum;
	}

	public void setIndexColumnNum(int indexColumnNum) {
		this.indexColumnNum = indexColumnNum;
	}

	public int getLastOffsetColumnNum() {
		return lastOffsetColumnNum;
	}

	public void setLastOffsetColumnNum(int lastOffsetColumnNum) {
		this.lastOffsetColumnNum = lastOffsetColumnNum;
	}

	public int getTabTheadNum() {
		return tabTheadNum;
	}

	public void setTabTheadNum(int tabTheadNum) {
		this.tabTheadNum = tabTheadNum;
	}

	public boolean isTabTheadStyle() {
		return tabTheadStyle;
	}

	public void setTabTheadStyle(boolean tabTheadStyle) {
		this.tabTheadStyle = tabTheadStyle;
	}

	public boolean isTabBodyStyle() {
		return tabBodyStyle;
	}

	public void setTabBodyStyle(boolean tabBodyStyle) {
		this.tabBodyStyle = tabBodyStyle;
	}

	public BIWorkbook getBw() {
		return bw;
	}

	public void setBw(BIWorkbook bw) {
		this.bw = bw;
	}
}

