package com.gateway.common.utils;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[]m = new int[155];
		m['a'] = m['b'] = m['c'] = 2;
		m['d'] = m['e'] = m['f'] = 3;
		m['g'] = m['h'] = m['i'] = 4;
		m['j'] = m['k'] = m['l'] = 5;
		m['m'] = m['n'] = m['o'] = 6;
		m['p'] = m['q'] = m['r'] = m['s'] = 7;
		m['t'] = m['u'] = m['v'] = 8;
		m['w'] = m['x'] = m['y'] = m['z'] = 9;
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) 
		{
		String line = sc.nextLine();
		jiami(line,m);
		System.out.println();
		}
	}
	
private static void jiami(String line,int[]m){
	for (int i = 0; i < line.length(); i++){
		if (line.charAt(i) >= 'a'&& line.charAt(i) <= 'z'){
			System.out.print(m[line.charAt(i)]);
		}else if (line.charAt(i) >= 65 && line.charAt(i) <= 90){
			if(line.charAt(i)==90){
				System.out.print("a");
			}else{
				System.out.print((char)(line.charAt(i)+33));
			}
		}else{
			System.out.print(line.charAt(i)+"");
		} 
	}
}

}
