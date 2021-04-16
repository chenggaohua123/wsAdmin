package com.gateway.common.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.google.common.collect.Lists;


public class BIWorkbook {
	
	public static int DEF_SIZE = -1;
	
	public Workbook workbook;
	
	private int indexRowNum;
	
	private int tabTheadNum;
	
	public List<BISheet> bsList = Lists.newLinkedList();
	
	public BIWorkbook(){
		workbook = new SXSSFWorkbook(DEF_SIZE);
	}
	
	public BISheet addSheet(){
		Sheet sheet = workbook.createSheet();
		BISheet bs = new BISheet(sheet);
		bsList.add(bs);
		return bs;
	}
	
	public void writeToFile(String destPath) throws IOException{
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(destPath);
			workbook.write(fos);
			fos.flush();
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(fos);
		}
	}

	public int getIndexRowNum() {
		return indexRowNum;
	}

	public void setIndexRowNum(int indexRowNum) {
		this.indexRowNum = indexRowNum;
	}

	public int getTabTheadNum() {
		return tabTheadNum;
	}

	public void setTabTheadNum(int tabTheadNum) {
		this.tabTheadNum = tabTheadNum;
	}

}

