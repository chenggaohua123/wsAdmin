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

public class TransQueryTest {
	public static void main(String [] agrs) throws ParseException, IOException{
		HttpClient client = HttpClientPool.getThreadSafeHttpClient();
		Map<String,String> formEntity = new HashMap<String, String>();
		formEntity.put("userName", "kenwill");
		formEntity.put("phoneNo", "13823699667");
		formEntity.put("passWord", "123456");
//		formEntity.put("cardNo", "6225882116400091");
		formEntity.put("isSucceed", "succeed");
		//发送Notify
		HttpPost httpPost = new HttpPost("http://121.201.32.201:8080/mpos/api/transmgr/queryTransList");
		HttpResponse resp = HttpClientPool.doPost(client, httpPost, formEntity, "UTF-8");
		System.out.println(EntityUtils.toString(resp.getEntity()));
	}
}
