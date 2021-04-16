package com.gateway.emailmgr.util;

import java.util.HashMap;
import java.util.Map;

public class EmailUtil {
	public static Map<String, String> emailConfigMap;
	static{
		emailConfigMap=new HashMap<String, String>();
		emailConfigMap.put("1", "succeed_hooppay_holder");
		emailConfigMap.put("2", "succeed_hooppay_merchant");
		emailConfigMap.put("3", "Fails_hooppay_holder");
		emailConfigMap.put("4", "uploadtraceno_hooppay_holder");
		emailConfigMap.put("5", "refund_hooppay_holder");
		emailConfigMap.put("6", "protest_hooppay_merchant");
		emailConfigMap.put("7", "complaints_hooppay_merchant");
		emailConfigMap.put("10", "register_hooppay_merchant");
		emailConfigMap.put("11", "risk_trans_merchant");
		emailConfigMap.put("12", "survey_hooppay_merchant");
		emailConfigMap.put("13", "sendAuthorizationToCardHolder");
	}
}
