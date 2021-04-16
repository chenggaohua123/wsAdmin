package com.gateway.fraud.common.util;

import org.apache.commons.lang.StringUtils;

public class NumberUtils {
	
	public static Integer strToInt(String str) {
		int rtx = 0;
		try {
			rtx = Integer.parseInt(str);
		} catch (Exception e) {
			rtx = 0;
		}
		return rtx;
	}
	
	public static Integer strToInt(String str,int defaultInt) {
		try {
			if(StringUtils.isNotBlank(str))
				return Integer.parseInt(str);
			return defaultInt;
		} catch (Exception e) {
			return defaultInt;
		}
	}

	public static Long strToLong(String str) {
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			return -999L;
		}
	}
	
	public static Long strToLong(String str,Long defaultLong) {
		try {
			if(StringUtils.isNotBlank(str))
				return Long.parseLong(str);
			return defaultLong;
		} catch (Exception e) {
			return -999L;
		}
	}

}
