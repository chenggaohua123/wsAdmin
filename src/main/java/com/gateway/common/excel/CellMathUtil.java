package com.gateway.common.excel;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

public class CellMathUtil {
	
	/**
	  * toString基础方法[私有]
	 */
	private static String toString(Object obj){
		if(obj == null){
			return "";
		}else{
			return String.valueOf(obj);
		}
	}
	
	/**
	  * toString方法[为0,"",null返回""]
	 */
	public static String toString_0(Object obj){
		String str = toString_1(obj);
		if("0".equals(str) || "0.0".equals(str)){
			return "";
		}else{
			return str;
		}
	}
	
	/**
	  * toString方法[为"",null返回""]
	 */
	public static String toString_1(Object obj){
		return toString(obj);
	}
	
	/**
	  * toString方法[为"",null返回"0"]
	 */
	public static String toString_2(Object obj){
		if(StringUtils.isEmpty(toString(obj))){
			return "0";
		}else{
			return obj.toString();
		}
	}
	
	/**
	  * toString方法[为"",null返回"-"]
	 */
	public static String toString_3(Object obj){
		if(StringUtils.isEmpty(toString(obj))){
			return "-";
		}else{
			return obj.toString();
		}
	}
	
	/**
	  * toNumber基础方法[私有]
	 */
	private static Object toNumber(Object obj){
		if(obj == null){
			return "";
		}
		try{
			if(obj instanceof Double){
				return obj;
			}else if(obj instanceof BigDecimal){
				return ((BigDecimal)obj).doubleValue();
			}else {
				return Double.valueOf(obj.toString());
			}
		}catch(Exception e){
			return obj;
		}
	}
	
	/**
	 * null或者""或者0		: 返回""
	 * 能format成double的	: 返回double
	 * 不能format成double的	: 返回原样
	 */
	public static Object toNumber_0(Object obj){
		Object reObj = toNumber(obj);
		if(reObj instanceof Double && (Double)reObj == 0){
			return "";
		}else{
			return reObj;
		}
	}
	
	/**
	 * null或者""			: 返回""
	 * 能format成double的	: 返回double
	 * 不能format成double的	: 返回原样
	 */
	public static Object toNumber_1(Object obj){
		return toNumber(obj);
	}
	
	/**
	 * null或者""			: 返回0
	 * 能format成double的	: 返回double
	 * 不能format成double的	: 返回原样
	 */
	public static Object toNumber_2(Object obj){
		Object reObj = toNumber(obj);
		if(reObj instanceof String && StringUtils.isEmpty((String)reObj)){
			return Double.valueOf(0);
		}else{
			return reObj;
		}
	}
	
	/**
	 * null或者""			: 返回"-"
	 * 能format成double的	: 返回double
	 * 不能format成double的	: 返回原样
	 */
	public static Object toNumber_3(Object obj){
		Object reObj = toNumber(obj);
		if(reObj instanceof String && StringUtils.isEmpty((String)reObj)){
			return "-";
		}else{
			return reObj;
		}
	}
	
	/**
	  * 计算使用基础方法[私有]
	 */
	private static Double toDouble(Object obj){
		if(obj instanceof Double){
			return (Double)obj;
		}else if(obj instanceof BigDecimal){
			return ((BigDecimal)obj).doubleValue();
		}else {
			return Double.valueOf(obj.toString());
		}
	}
	/**
	 * 简单四则[加+],但凡出现一点错误,都返回""
	 */
	public static Object add(Object a, Object b){
		try{
			if(a==null && b==null){
				return "";
			}
			Double double_b = Double.valueOf(0);
			Double double_a =  Double.valueOf(0);
			if(a != null){
				double_a = toDouble(a);
			}
			if(b != null){
				double_b = toDouble(b);
			}
			if(double_a+double_b==0){
				return "";
			}
			return double_a+double_b;
		}catch(Exception e){
			return "";
		}
	}
	/**
	 * 简单四则[减-],但凡出现一点错误,都返回""
	 */
	public static Object sub(Object a, Object b){
		try{
			if(a==null && b==null){
				return "";
			}
			Double double_b = Double.valueOf(0);
			Double double_a =  Double.valueOf(0);
			if(a != null){
				double_a = toDouble(a);
			}
			if(b != null){
				double_b = toDouble(b);
			}
			if(double_a-double_b==0){
				return "";
			}
			return double_a-double_b;
		}catch(Exception e){
			return "";
		}
	}
	/**
	 * 简单四则[乘*],但凡出现一点错误,都返回""
	 */
	public static Object mul(Object a, Object b){
		try{
			if(a==null || b==null){
				return "";
			}
			Double double_b = toDouble(b);
			Double double_a = toDouble(a);
			if(double_b==0 || double_a == 0){
				return "";
			}
			return double_a*double_b;
		}catch(Exception e){
			return "";
		}
	}
	/**
	 * 简单四则[除/],但凡出现一点错误,都返回""
	 */
	public static Object div(Object a, Object b){
		try{
			if(a==null || b==null){
				return "";
			}
			Double double_b = toDouble(b);
			Double double_a = toDouble(a);
			if(double_b==0 || double_a == 0){
				return "";
			}
			return double_a/double_b;
		}catch(Exception e){
			return "";
		}
	}
	
	/**
	 * 简单算法[同比]=[(本期-同期)/同期],但凡出现一点错误,都返回""
	 * @param a 本期
	 * @param b 同期
	 * 
	 */
	public static Object tb(Object a, Object b){
		try{
			if(a==null && b==null){
				return "";
			}			
			if(b==null){
				return "";
			}
			Double double_b = toDouble(b);
			Double double_a = Double.valueOf("0");	
					
			if(double_b == 0){
				return "";
			}
			if(a!=null){
				double_a=toDouble(a);;
			}
			return (double_a - double_b)/double_b;
		}catch(Exception e){
			return "";
		}
	}
	
	public static void main(String args[]){
		System.out.println(tb("150",BigDecimal.valueOf(100)));
		System.out.println(tb(150.0,BigDecimal.valueOf(100)));
	}
}
