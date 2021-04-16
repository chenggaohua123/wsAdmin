package com.gateway.common.utils;

public class Test001 {

	//最小长度
	private static int min = 6;
	//最大长度
	private static int max = 10;
	//准备数字，大小写
	//private static char[] psw = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	private static char[] psw = {'0','1','2','3','4','5','6','7','8','9','a'};
	
	private static String password="a123456";
	public static void main(String[] args) {
		for(int i=min; i<=max; i++){
			permutation(psw, i);
		}
	}
	
	/**
	* 全排列入口
	* @param array 密码数据
	* @param n 密码长度
	*/
	private static String permutation(char[] array, int n) {
		return permutation("", array, n);
	}
	
	/**
	*
	* @param s 已生成临时字串
	* @param array 密码数据
	* @param n 剩余未生成的字符长度
	*/
	private static String permutation(String s, char[] array, int n) {
		String psw="";
		if(n == 1) {
			for(int i=0; i<array.length; i++) {
				//这是密码结果
				String result = s+array[i];
				System.out.println(result);
				if(result.equals(password)){
					psw=result;
					throw new RuntimeException(psw);
				}
			}
		} else {
			for(int i=0; i<array.length; i++) {
				permutation(s+array[i], array, n-1);
			}
		}
		return psw;
	}

}
