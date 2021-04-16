package com.gateway.fraud.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	/**
	 * 返回时间字符串格式：yyyyMMddHHmmss
	 * @param date
	 * @return
	 */
	public static String date2String(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(date);
	}
	
	/**
	 * 取当前时间函数 HH.<BR>
	 * 
	 * @return 返回当前时间字符串
	 */
	public static String getCurrentHourTime() {
		SimpleDateFormat f = new SimpleDateFormat("HH");
		String d = f.format(new Date());
		return d;
	}
	
	/**
	 * 返回时间字符串格式：yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String date2String2(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
	/**
	 * 返回时间字符串格式：yyyy/MM/dd
	 * @param date
	 * @return
	 */
	public static String date2String3(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		return format.format(date);
	}
	public static String formateDate(String strDate, int type) {
		SimpleDateFormat datetimeFormat = null;
		try {
			switch (type) {
			case 0:
				datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				break;
			case 1:
				datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				break;
			case 2:
				datetimeFormat = new SimpleDateFormat("yyyy-MM-dd");
				break;
			case 3:
				datetimeFormat = new SimpleDateFormat("yyyy-MM");
				break;
			case 4:
				datetimeFormat = new SimpleDateFormat("yyyyMMdd");
				break;
			case 5:
				datetimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				break;
			default:
				break;
			}
			Date temp = null;
			if(type ==4){
				temp = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
			}else if(type ==5){
				temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate);
			}else{
				temp = datetimeFormat.parse(strDate);
			}
	        return datetimeFormat.format(temp);
		} catch (Exception e) {
			return strDate;
		}
	}
}
