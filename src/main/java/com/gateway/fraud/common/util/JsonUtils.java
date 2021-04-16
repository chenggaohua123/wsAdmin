package com.gateway.fraud.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.alibaba.fastjson.JSON;


/**
 * base on fastjson
 * 
 * @author Kevin Jiang
 *
 */
public class JsonUtils {

	
	@SuppressWarnings("unchecked")
	public static <T> Map<String, Object> beanToMap(T bean) {
		if (bean == null) {
			return new HashMap<String, Object>();
		}
		String json = JSON.toJSONString(bean);
		Map<String, Object> map = JSON.parseObject(json, Map.class);
		return map;
	}

	@SuppressWarnings("unchecked")
	public static <T> Map<String, String> beanToStringMap(T bean) {
		if (bean == null) {
			return new HashMap<String, String>();
		}
		String json = JSON.toJSONString(bean);
		Map<String, Object> map = JSON.parseObject(json, Map.class);
		
		Map<String, String> stringMap = new HashMap<String, String>();
		for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {
			String key = iter.next();
			String value = map.get(key).toString();
			stringMap.put(key, value);
		}
		
		return stringMap;
	}

	public static <T> T stringMapToBean(Map<String, String> map, Class<T> clazz) {
		if (map == null || map.size() == 0) {
			return null;
		}
		String json = JSON.toJSONString(map);
		T bean = JSON.parseObject(json, clazz);
		return bean;
	}

	public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
		if (map == null || map.size() == 0) {
			return null;
		}
		String json = JSON.toJSONString(map);
		T bean = JSON.parseObject(json, clazz);
		return bean;
	}
	

 	
	public static boolean checkJsonObject(String json) {
		if (json.startsWith("{") && json.endsWith("}")) {
			return true;
		}
		return false;
	}

	public static boolean checkJsonList(String json) {
		if (json.startsWith("[") && json.endsWith("]")) {
			return true;
		}
		return false;
	}



}
