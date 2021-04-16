package com.gateway.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;




public class Test002 {

	public static void main(String[] args) throws Exception {
		
		DefaultHttpClient httpClient =Tools.getHttpClient(); // �����ͻ�������
		//DefaultHttpClient httpClient = new DefaultHttpClient(); 
		//http://127.0.0.1:8080/cldAdmin/login
		//HttpPost httpGet = new HttpPost("https://merchant.remitepay.com/api/GetRefund");
		HttpPost httpGet = new HttpPost("https://merchant.remitepay.com/api/GetRefund");
		// ������򵥵��ַ�������     
        StringEntity reqEntity = new StringEntity("merchantid=978250&signature=1c1a26d2a3161c80918e9645ed936f6b&year=2020&month=1");
        // ��������     
        reqEntity.setContentType("application/x-www-form-urlencoded");     
       // �������������     
        httpGet.setEntity(reqEntity);     
      
        //httpGet.setProtocolVersion(HttpVersion.HTTP_1_0);
        //httpGet.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
        
       // ִ��     
       HttpResponse response = httpClient.execute(httpGet);     
       HttpEntity entity = response.getEntity();     
       System.out.println(response.getStatusLine());     
       
       //EntityUtils.consume(entity);        //���չٷ��ĵ���˵�������߶��ͷ��˲ſ����������ͷ�����
      if (entity != null) {     
         System.out.println("Response content length: " + entity.getContentLength());  //�õ��������ݵĳ���    
       }     
       // ��ʾ���     
       BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));   
         
       String line = null;     
      while ((line = reader.readLine()) != null) {     
           System.out.println(line);     
       }     
	}
	
	
	
	
	
}
