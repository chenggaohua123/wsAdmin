package com.gateway.common.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class DictionarySeek {

	//������ܻ�������ַ�����
    private static char[] fullCharSource = { '1','2','3','4','5','6','7','8','9','0',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',  'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',  'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '{', '}', '|', ':', '"', '<', '>', '?', ';', '\'', ',', '.', '/', '-', '=', '`'};
    //�����ܵ����뼯�ϳ���
    private static int fullCharLength = fullCharSource.length;
    //maxLength�����ɵ��ַ�������󳤶�
    public static void generate(int maxLength) throws FileNotFoundException, UnsupportedEncodingException {
        //�����������߳�ʱ���Զ����������Ȼ����ת����Integer���͡�
        int counter = 0;
        StringBuilder buider = new StringBuilder();
        
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("C://�����ֵ�.txt"), "utf-8"));

        while (buider.toString().length() <= maxLength) {
            buider = new StringBuilder(maxLength*2);
            int _counter = counter;
            //10����ת����26����
            while (_counter >= fullCharLength) {
                //��õ�λ
                buider.insert(0, fullCharSource[_counter % fullCharLength]);
                _counter = _counter / fullCharLength;
                //���������ϵ��ֻ��10û��01�����⣬����������ǿ��Դ���01��
                _counter--;
            }
            //���λ
            buider.insert(0,fullCharSource[_counter]);
            counter++;
            
            pw.write(buider.toString()+"\n");
            System.out.println(buider.toString());
        }
    }
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
    	System.out.print("���ɵ��ֵ�λ�ã�D://�����ֵ�.txt"+"\n"+"����������Ҫ���ɵ��ֵ�λ����");
    	Scanner sc = new Scanner(System.in);
    	int x = sc.nextInt();
    	
    	DictionarySeek.generate(x);
	
	}
}
