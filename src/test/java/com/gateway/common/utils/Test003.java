package com.gateway.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpVersion;
import org.apache.http.protocol.HTTP;

import com.gateway.bankOrder.utils.BankOrderUtils;
import com.sun.net.ssl.SSLContext;

public class Test003 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		interfaceUtil("https://merchant.remitepay.com/api/GetRefund?merchantid=978250&signature=1c1a26d2a3161c80918e9645ed936f6b&year=2020&month=1", "");//get����
	}
	
	public static void interfaceUtil(String path,String data) {
        try {
        	
            URL url = new URL(path);
            //�����վ֤�鲻��������
			BankOrderUtils.trustAllHttpsCertificates();
			HttpsURLConnection.setDefaultHostnameVerifier(BankOrderUtils.getHv());
            //�򿪺�url֮�������
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            PrintWriter out = null;
            
            
            //SSLContext sc = SSLContext.getInstance("TLSv1.2");
            //sc.init(null, null, null);
            //conn.setSSLSocketFactory(sc.getSocketFactory());

            
            //SSLContext sc = SSLContext.getInstance("TLSv1.2");
            //sc.init(null, null, null);
            //conn.setSSLSocketFactory(sc.getSocketFactory());
            
            /**����URLConnection�Ĳ�������ͨ����������****start***/
           
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)"); 
            
            /**����URLConnection�Ĳ�������ͨ����������****end***/
            
            //�����Ƿ���httpUrlConnection����������Ƿ��httpUrlConnection���룬���ⷢ��post�����������������
            //��õ�Http�����޷���get��post��get������Ի�ȡ��̬ҳ�棬Ҳ���԰Ѳ�������URL�ִ����棬���ݸ�servlet��
            //post��get�� ��֮ͬ������post�Ĳ������Ƿ���URL�ִ����棬���Ƿ���http����������ڡ�
            conn.setDoOutput(true);
            conn.setDoInput(true);
            
            conn.setRequestMethod("GET");//GET��POST����ȫ��д
            /**GET��������*****start*/
            /**
             * ���ֻ�Ƿ���GET��ʽ����ʹ��connet����������Զ����Դ֮���ʵ�����Ӽ��ɣ�
             * �������POST��ʽ��������Ҫ��ȡURLConnectionʵ����Ӧ����������������������
             * */
            conn.connect(); 
            
            /**GET��������*****end*/
            
            /***POST��������****start*/
            
            /*out = new PrintWriter(conn.getOutputStream());//��ȡURLConnection�����Ӧ������� 
            
            out.print(data);//�����������������
            
            out.flush();//��������
            */            
            /***POST��������****end*/
            
            //��ȡURLConnection�����Ӧ��������
            InputStream is = conn.getInputStream();
            //����һ���ַ�������
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            while ((str = br.readLine()) != null) {
            	str=new String(str.getBytes(),"UTF-8");//���������������
                System.out.println(str);
            }
            //�ر���
            is.close();
            //�Ͽ����ӣ����д�ϣ�disconnect���ڵײ�tcp socket���ӿ���ʱ���жϡ�������ڱ������߳�ʹ�þͲ��жϡ�
            //�̶����̵߳Ļ��������disconnect�����ӻ����ֱ࣬���շ�������Ϣ��д��disconnect������һЩ��
            conn.disconnect();
            System.out.println("��������");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
