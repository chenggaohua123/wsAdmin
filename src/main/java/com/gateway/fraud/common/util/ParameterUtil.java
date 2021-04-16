/**
 * Alipay.com Inc.
 * Copyright (c) 2005-2006 All Rights Reserved.
 */
package com.gateway.fraud.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

/**
 * 参数构造工具类
 * 
 * @author jun.huyj
 * @version $Id: ParameterUtil.java, v 0.1 Nov 10, 2008 8:49:33 PM jun.huyj Exp $
 */
public class ParameterUtil {

    /**
     * 将Map组装成待签名数据。
     * 待签名的数据必须按照一定的顺序排列 这个是支付宝提供的服务的规范，否则调用支付宝的服务会通不过签名验证
     * @param params
     * @return
     */
    public static String getSignData(Map<String, String> params) {
        StringBuffer content = new StringBuffer();

        // 按照key做排序
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            if ("sign".equals(key)||"sign_type".equals(key) || "signType".equals(key)) {
                continue;
            }
            String value = (String) params.get(key);
            if (value != null) {
                content.append((i == 0 ? "" : "&") + key + "=" + value);
            } else {
                content.append((i == 0 ? "" : "&") + key + "=");
            }

        }

        return content.toString();
    }

    /**
     * 将Map中的数据组装成url
     * @param params
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String mapToUrl(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (isFirst) {
                sb.append(key + "=" + URLEncoder.encode(value, "utf-8"));
                isFirst = false;
            } else {
                if (value != null) {
                    sb.append("&" + key + "=" + URLEncoder.encode(value, "utf-8"));
                } else {
                    sb.append("&" + key + "=");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 取得URL中的参数值。
     * <p>如不存在，返回空值。</p>
     * 
     * @param url
     * @param name
     * @return
     */
    public static String getParameter(String url, String name) {
        if (name == null || name.equals("")) {
            return null;
        }
        name = name + "=";
        int start = url.indexOf(name);
        if (start < 0) {
            return null;
        }
        start += name.length();
        int end = url.indexOf("&", start);
        if (end == -1) {
            end = url.length();
        }
        return url.substring(start, end);
    }
    
    
    
    

	public static String getUTF8ValueByMap(Map<String, Object> map, String key, String defaultV){
		String value = defaultV;
		try {
			value = map != null && map.get(key) != null && !map.get(key.trim()).equals("") ? map.get(key.trim()).toString().trim() : defaultV;
		} catch (Exception e) {
			value = defaultV;
		}
		return getStrUTF8(value);
	}
	
	public static String getValueByMap(Map<String, Object> map, String key, String defaultV){
		try {
			return map != null && map.get(key) != null && !map.get(key.trim()).equals("") ? map.get(key.trim()).toString().trim() : defaultV;
		} catch (Exception e) {
			return defaultV;
		}
	}
	
	public static Integer getIntegerValueByMap(Map<String, Object> map, String key, Integer defaultV){
		try {
			return map != null && map.get(key) != null && !map.get(key.trim()).equals("") ? Integer.valueOf(map.get(key.trim()).toString().trim()) : defaultV;
		} catch (Exception e) {
			return defaultV;
		}
	}
	
	public static double getDoubleValueByMap(Map<String, Object> map, String key, double defaultV){
		try {
			return map != null && map.get(key) != null && !map.get(key.trim()).equals("") ? Double.valueOf(map.get(key.trim()).toString().trim()) : defaultV;
		} catch (Exception e) {
			return defaultV;
		}
	}
    
	public static String getStrUTF8(String str){
		try {
			str = !str.equals("") ? new String(str.getBytes("ISO-8859-1")) : "";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
    
	/**
	 * 将value为数组类型的map转换成普通Map
	 * @param map	Map<String, String[]>
	 * @return		Map<String, Object>
	 */
	public static Map<String, Object> conversionToGeneralMap(Map<String, String[]> map){
		Map<String, Object> generalMap = new HashMap<String, Object>();
		String key = "";
		String value = "";
		for(Entry<String, String[]> entry : map.entrySet()){
			key = ""; value = "";
			key = entry.getKey();
			Object object = entry.getValue();
			if(object == null){
				value = "";
			}else if(object instanceof String[]){
				String[] array = (String[])object;
				for (int i = 0; i < array.length; i++) {
					value = array[i] + "";
				}
			}else{
				value = object.toString();
			}
			generalMap.put(key, value);
		}
		return generalMap;
	}
	
	public static Map<String, Object> getParameterByRequest(HttpServletRequest req){
		if(req != null){
			Map<String, String[]> paraMap = req.getParameterMap();
			Map<String, Object> paraMap_s = new HashMap<String, Object>();
			if(paraMap != null)
				paraMap_s = conversionToGeneralMap(paraMap);
			return paraMap_s;
		}
		return null;
	}
    
}
