package com.gateway.bankOrder.utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import com.alibaba.fastjson.JSONArray;  
import com.alibaba.fastjson.JSONObject;  
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.Map.Entry;



public class BankOrderUtils {

	/**
	 * 解决网站证书不信任问题
	 * @throws Exception
	 */
	public static void trustAllHttpsCertificates() throws Exception {  
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];  
        javax.net.ssl.TrustManager tm = new miTM();  
        trustAllCerts[0] = tm;  
        /*javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext  
                .getInstance("SSL"); */
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext  
                .getInstance("TLSv1.2"); 
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc  
                .getSocketFactory());  
    }  

 private static HostnameVerifier hv = new HostnameVerifier() {  
        public boolean verify(String urlHostName, SSLSession session) {  
            System.out.println("Warning: URL Host: " + urlHostName + " vs. "  
                               + session.getPeerHost());  
            return true;  
        }  
    };
    
    
    
    public static HostnameVerifier getHv() {
	return hv;
}
    

    private static List<Map<String, Object>> json2List(Object json) {  
        JSONArray jsonArr = (JSONArray) json;  
        List<Map<String, Object>> arrList = new ArrayList<Map<String, Object>>();  
        for (int i = 0; i < jsonArr.size(); ++i) {  
            arrList.add(strJson2Map(jsonArr.getString(i)));  
        }  
        return arrList;  
    }  
  
    public static Map<String, Object> strJson2Map(String json) {  
        JSONObject jsonObject = JSONObject.parseObject(json);  
        Map<String, Object> resMap = new HashMap<String, Object>();  
        Iterator<Entry<String, Object>> it = jsonObject.entrySet().iterator();  
        while (it.hasNext()) {  
            Map.Entry<String, Object> param = (Map.Entry<String, Object>) it.next();  
            if (param.getValue() instanceof JSONObject) {  
                resMap.put(param.getKey(), strJson2Map(param.getValue().toString()));  
            } else if (param.getValue() instanceof JSONArray) {  
                resMap.put(param.getKey(), json2List(param.getValue()));  
            } else {  
                resMap.put(param.getKey(), JSONObject.toJSONString(param.getValue(), SerializerFeature.WriteClassName));  
            }  
        }  
        return resMap;  
    }  
    
    public static Map<String, Object> getMap(String json) {
    	JSONObject jsonObject = JSONObject.parseObject(json);
    	Map<String, Object> valueMap = new HashMap<String, Object>();
    	valueMap.putAll(jsonObject);
    	return valueMap;
    }

	static class miTM implements javax.net.ssl.TrustManager,  
    javax.net.ssl.X509TrustManager {  
	    public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
	        return null;  
	    }  
	
	    public boolean isServerTrusted(  
	            java.security.cert.X509Certificate[] certs) {  
	        return true;  
	    }  
	
	    public boolean isClientTrusted(  
	            java.security.cert.X509Certificate[] certs) {  
	        return true;  
	    }  
	
	    public void checkServerTrusted(  
	            java.security.cert.X509Certificate[] certs, String authType)  
	            throws java.security.cert.CertificateException {  
	        return;  
	    }  
	
	    public void checkClientTrusted(  
	            java.security.cert.X509Certificate[] certs, String authType)  
	            throws java.security.cert.CertificateException {  
	        return;  
	    }  
	}



	
}
