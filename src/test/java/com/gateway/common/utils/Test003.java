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
		interfaceUtil("https://merchant.remitepay.com/api/GetRefund?merchantid=978250&signature=1c1a26d2a3161c80918e9645ed936f6b&year=2020&month=1", "");//get请求
	}
	
	public static void interfaceUtil(String path,String data) {
        try {
        	
            URL url = new URL(path);
            //解决网站证书不信任问题
			BankOrderUtils.trustAllHttpsCertificates();
			HttpsURLConnection.setDefaultHostnameVerifier(BankOrderUtils.getHv());
            //打开和url之间的连接
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            PrintWriter out = null;
            
            
            //SSLContext sc = SSLContext.getInstance("TLSv1.2");
            //sc.init(null, null, null);
            //conn.setSSLSocketFactory(sc.getSocketFactory());

            
            //SSLContext sc = SSLContext.getInstance("TLSv1.2");
            //sc.init(null, null, null);
            //conn.setSSLSocketFactory(sc.getSocketFactory());
            
            /**设置URLConnection的参数和普通的请求属性****start***/
           
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)"); 
            
            /**设置URLConnection的参数和普通的请求属性****end***/
            
            //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            //最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
            //post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
            conn.setDoOutput(true);
            conn.setDoInput(true);
            
            conn.setRequestMethod("GET");//GET和POST必须全大写
            /**GET方法请求*****start*/
            /**
             * 如果只是发送GET方式请求，使用connet方法建立和远程资源之间的实际连接即可；
             * 如果发送POST方式的请求，需要获取URLConnection实例对应的输出流来发送请求参数。
             * */
            conn.connect(); 
            
            /**GET方法请求*****end*/
            
            /***POST方法请求****start*/
            
            /*out = new PrintWriter(conn.getOutputStream());//获取URLConnection对象对应的输出流 
            
            out.print(data);//发送请求参数即数据
            
            out.flush();//缓冲数据
            */            
            /***POST方法请求****end*/
            
            //获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();
            //构造一个字符流缓存
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            while ((str = br.readLine()) != null) {
            	str=new String(str.getBytes(),"UTF-8");//解决中文乱码问题
                System.out.println(str);
            }
            //关闭流
            is.close();
            //断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
            //固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
            conn.disconnect();
            System.out.println("完整结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
