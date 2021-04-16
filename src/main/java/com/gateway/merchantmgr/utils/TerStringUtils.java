package com.gateway.merchantmgr.utils;

import org.springframework.util.StringUtils;

public class TerStringUtils {
	private static String TER_NO = "88816";
	public static String getTreNoInfo(String terNo){
		if(!StringUtils.isEmpty(terNo)){
			return "888"+(Integer.parseInt(terNo.substring(3,terNo.length()-1))+1)+"6";
		}
		return TER_NO;
	}

}
