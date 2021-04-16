package com.gateway.api.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import com.gateway.common.utils.HttpClientPool;
//import com.google.common.collect.Maps;

public class RegisterTest {
	
	public static void main(String [] agrs) throws ParseException, IOException{
		HttpClient client = HttpClientPool.getThreadSafeHttpClient();
		Map<String,String> formEntity =new HashMap<String, String>();
		formEntity.put("userName", "hmwen");
		formEntity.put("phoneNo", "13570812747");
		formEntity.put("passWord", "123456");
		formEntity.put("realName", "温洪明");
		formEntity.put("email", "wenhongming@qq.com");
		formEntity.put("agentNo", "13570812759");
		//发送Notify
		HttpPost httpPost = new HttpPost("http://121.201.32.201:8080/mpos/api/usermgr/userregister");
		HttpResponse resp = HttpClientPool.doPost(client, httpPost, formEntity, "UTF-8");
		System.out.println(EntityUtils.toString(resp.getEntity()));
		
		
	}
}
