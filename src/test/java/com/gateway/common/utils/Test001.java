package com.gateway.common.utils;

public class Test001 {

	//��С����
	private static int min = 6;
	//��󳤶�
	private static int max = 10;
	//׼�����֣���Сд
	//private static char[] psw = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	private static char[] psw = {'0','1','2','3','4','5','6','7','8','9','a'};
	
	private static String password="a123456";
	public static void main(String[] args) {
		for(int i=min; i<=max; i++){
			permutation(psw, i);
		}
	}
	
	/**
	* ȫ�������
	* @param array ��������
	* @param n ���볤��
	*/
	private static String permutation(char[] array, int n) {
		return permutation("", array, n);
	}
	
	/**
	*
	* @param s ��������ʱ�ִ�
	* @param array ��������
	* @param n ʣ��δ���ɵ��ַ�����
	*/
	private static String permutation(String s, char[] array, int n) {
		String psw="";
		if(n == 1) {
			for(int i=0; i<array.length; i++) {
				//����������
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
