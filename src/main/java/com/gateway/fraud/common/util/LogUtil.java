package com.gateway.fraud.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class LogUtil {
	public static final String LOG_SPLIT = ",";
	public static final String KEY_REQUEST_ID = "requestid";
	public static final String LOG_MARKS_QUOTATION = "\"";

	public static String getLogStr(Object... params) {
		return StringUtils.join(params, LOG_SPLIT);
	}

	public static String getLogStr(HttpServletRequest request) {
		String paramStr = JSONObject.toJSONString(request.getParameterMap());
		return getLogStr("[url]", request.getRequestURL().toString(), "[param]", paramStr);
	}
}