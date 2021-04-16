package com.gateway.syncomplaint.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component(value="urlHttpClient")
public class URLHttpClient {
	
	private static Log logger = LogFactory.getLog(URLHttpClient.class);

	/**
	 * 获取URL链接
	 */
	public HttpURLConnection createHttpUrlConnection(String url) throws IOException{
		URL realUrl = new URL(url);
        HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        // POST方法
        conn.setRequestMethod("POST");
        // 设置通用的请求属性
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.connect();
        return conn;
	}
	
	/**
	 * 以POST形式发送信息
	 * @throws IOException 
	 */
	public String sendPost(String url, String[] names, String[] values) throws IOException{
		 HttpURLConnection conn = createHttpUrlConnection(url);
		 OutputStreamWriter out  = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
         StringBuffer sb = new StringBuffer();
         if(names!=null && values!=null){
        	 if(names.length!=values.length){
        		 logger.info("参数名称与参数值不匹配");
        	 }
        	 for(int i=0; i<names.length; i++){
        		 sb.append(names[i]).append("=").append(values[i]);
        		 if(i<(names.length-1)){
        			 sb.append("&");
        		 }
        	 }
        	 out.write(sb.toString());
         }
         out.flush();
         BufferedReader in = new BufferedReader(
                 new InputStreamReader(conn.getInputStream(), "UTF-8"));
         StringBuffer result = new StringBuffer();
         String line = "";
         while ((line = in.readLine()) != null) {
             result.append(line);
         }
         out.close();
         in.close();
         conn.disconnect();
         return result.toString();
	}
	
	/**
	 * 下载文件
	 * @throws IOException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public void downloadFile(HttpServletRequest request,  
            HttpServletResponse response, String storeName, String url, Object obj) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		response.setContentType("application/octet-stream");  
        response.setHeader("Content-disposition", "attachment; filename="  
                + new String(storeName.getBytes("utf-8"), "ISO8859-1")); 
        //设置输出长度
//        response.setHeader("Content-Length", String.valueOf(fileLength));
		HttpURLConnection conn = createHttpUrlConnection(url);
		OutputStreamWriter out  = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
        StringBuffer sb = new StringBuffer();
        Class clazz = obj.getClass();
       Field[] fields = clazz.getDeclaredFields();
       if(fields!=null && fields.length>0){
    	   for(Field field : fields){
    		   if(field.getName()!=null){
    			   Method method = clazz.getMethod("get"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1, field.getName().length()), null);
    			   Object value = method.invoke(obj, null);
    			   if(value!=null){
    				   sb.append(field.getName()).append("=");
    				   sb.append(value).append("&");
    			   }
    		   }
    	   }
    	   out.write(sb.toString());
       }else{
        	logger.info("参数名称与参数值为空");
        	return;
      	}
        out.flush();
        BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
        BufferedOutputStream fileOut = new BufferedOutputStream(response.getOutputStream());
        int len = -1;
        byte[] bytes = new byte[1024];
        while((len=in.read(bytes))!=-1){
        	fileOut.write(bytes, 0, len);
        }
        fileOut.flush();
        out.close();
        fileOut.close();
        in.close();
        conn.disconnect();
	}
	
}
