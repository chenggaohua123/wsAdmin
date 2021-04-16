package com.gateway.fraud.common.util;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

public class HttpClientPool {

	private static int MAX_PER_ROUTE = 10;
	private static int MAX_TOTAL     = 100;
	private static int SO_TIMEOUT    = 10000;
    private static int CONNECTION_TIMETOU = 10000;
    
	public HttpClientPool() {
		
	}
	
	public static HttpClient getThreadSafeHttpClient() {
		return getThreadSafeHttpClient(MAX_PER_ROUTE,MAX_TOTAL,SO_TIMEOUT,CONNECTION_TIMETOU);
	}
	
	public static HttpClient getThreadSafeHttpClient(int maxPerRoute,int maxTotal,int soTimeout,int connectionTimeout){
		
		SchemeRegistry schemeRegistry = new SchemeRegistry();  
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));  
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));  
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
		
		//设置连接最大数  
		cm.setMaxTotal(maxTotal);  
		//设置每个Route的连接最大数  
		cm.setDefaultMaxPerRoute(maxPerRoute);  
		  
		HttpClient client = new DefaultHttpClient(cm);
		
		HttpParams parentParams = new BasicHttpParams();
		parentParams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

    	parentParams.setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
    	
    	parentParams.setParameter(ClientPNames.CONN_MANAGER_TIMEOUT, connectionTimeout);
    	parentParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
    	parentParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
    	
		return client;
	}
	
	public static HttpResponse doPost(HttpClient httpClient,HttpPost httpPost,Map<String,String> formEntity,String charset) {
		
		try {
			
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			
			for(Map.Entry<String,String> entity : formEntity.entrySet()){
				nvps.add(new BasicNameValuePair(entity.getKey(), entity.getValue()));
			}
			
			//添加头部信息
		    httpPost.setHeader("User-Agent","wPayNotify");
	
		    HttpClientParams.setCookiePolicy(httpClient.getParams(),CookiePolicy.BROWSER_COMPATIBILITY);
		    httpPost.setEntity(new UrlEncodedFormEntity(nvps,charset));
            HttpResponse response = httpClient.execute(httpPost);
            
            return response;
            
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public static HttpResponse doGet(HttpClient httpClient,HttpGet httpGet,Map<String,String> formEntity,String charset){
		try{
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for(Map.Entry<String,String> entity : formEntity.entrySet()){//account and password
				nvps.add(new BasicNameValuePair(entity.getKey(), entity.getValue()));
			}
			//添加头部信息
			httpGet.setHeader("User-Agent","wPayNotify");
	
		    HttpClientParams.setCookiePolicy(httpClient.getParams(),CookiePolicy.BROWSER_COMPATIBILITY);
		    
		    String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps, charset));  
		    httpGet.setURI(new URI(httpGet.getURI().toString() + "?" + str));  
            
            HttpResponse response = httpClient.execute(httpGet);
            return response;
		}catch (Exception e) {
			return null;
		}
		
	}
	
	/**
	 * 构造get请求url
	 * @param baseURL
	 * @param paramMap
	 * @param charset
	 * @return
	 */
	public static String buildURL(String baseURL, Map<String, String> paramMap, String charset) {
		if (Strings.isNullOrEmpty(baseURL)) {
			return null;
		}
		
		if (paramMap == null || paramMap.size() <= 0) {
			return baseURL;
		}
		
		String resultURL = baseURL;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for(Map.Entry<String,String> entity : paramMap.entrySet()){//account and password
			nvps.add(new BasicNameValuePair(entity.getKey(), entity.getValue()));
		}
		try {
			String paramStr = EntityUtils.toString(new UrlEncodedFormEntity(nvps, charset));
			if (baseURL.contains("?")) {
				resultURL = baseURL + "&" + paramStr;
			} else {
				resultURL = baseURL + "?" + paramStr;
			}
		} catch (Exception e) {
			return null;
		}
		
		return resultURL;
	}
	
	public static void main(String[] args) throws Exception {
		HttpClient client = getThreadSafeHttpClient();
		Map<String,String> formEntity = Maps.newHashMap();
		formEntity.put("id", "123");
		formEntity.put("name", "liaopeng");
		//发送Notify
		HttpPost httpPost = new HttpPost("http://localhost:8080/wpay/service/test");
		HttpResponse resp = HttpClientPool.doPost(client, httpPost, formEntity, "UTF-8");
		System.out.println(EntityUtils.toString(resp.getEntity()));
		
		HttpGet httpGet = new HttpGet("http://localhost:8080/wpay/service/test1");
		Map<String,String> params = Maps.newHashMap();
		HttpResponse getResp = HttpClientPool.doGet(client, httpGet, params, "UTF-8");
		System.out.println(EntityUtils.toString(getResp.getEntity()));
	}
}
