package com.gateway.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import com.alibaba.fastjson15.JSONObject;
import com.gateway.bankOrder.model.SxyBankModel;
import com.gateway.bankOrder.model.SxyBankRefundModel;



public class MyTest {

	/*public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		//QuartzTestImpl quartzTestImpl=new QuartzTestImpl();
		/*LogInv logInv=new LogInv();
		QuartzTestImpl quartzTestImpl=(QuartzTestImpl) ProxyFactory.getProxy(QuartzTestImpl.class, logInv);
		quartzTestImpl.print();*/
		/*for(int i=0;i<1000;i++){
			System.out.print("我");
		}*/
		/*SxyBankModel sxy= new SxyBankModel();
		Method[] methods=sxy.getClass().getMethods();
		Method method=sxy.getClass().getMethod("getRefundDetails");
		Class clazz=method.getReturnType();
		System.out.println(clazz.isAssignableFrom(SxyBankRefundModel.class));*/
		/*for(Method m:methods){
			if("setRefundDetails".equals(m.getName())){
				for(Class clazz:m.getParameterTypes()){
					System.out.println(clazz);
					System.out.println(clazz.isAssignableFrom(SxyBankRefundModel.class));
				}
			}
		}
		
	}*/
	
	public static void main(String[] args){
		Scanner reader=new Scanner(System.in);
		while(reader.hasNext()){
		String str;
		str=reader.nextLine();
		char s[]=str.toCharArray();
		int i;
		for(i=0;i<str.length();i++){
		if(s[i]>='A' && s[i]<'Z'){
			s[i]=(char)(s[i]+33);
		}
			else if(s[i]=='Z')
		{
			s[i]='a';
		}
		else if(s[i]>='a' && s[i]<='c'){
			s[i]='2';
		}else if(s[i]>='d' && s[i]<='f'){
			s[i]='3';
		}else if(s[i]>='g' && s[i]<='i'){
			s[i]='4';
		}else if(s[i]>='j' && s[i]<='l'){
			s[i]='5';
		}else if(s[i]>='m' && s[i]<='o'){
			s[i]='6';
		}else if(s[i]>='p' && s[i]<='s'){
			s[i]='7';
		}else if(s[i]>='a' && s[i]<='c'){
			s[i]='8';
		}else if(s[i]>='w' && s[i]<='z'){
			s[i]='9';
		}
			System.out.print(s[i]);
		}
			System.out.println();
		}
	
	}
	
	
}
