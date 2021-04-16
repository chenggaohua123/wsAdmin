package com.gateway.api.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import com.gateway.api.utils.Base64Utils;
import com.gateway.common.utils.HttpClientPool;
//import com.google.common.collect.Maps;

public class UploadPicTest {
	public static void main(String [] agrs) throws ParseException, IOException{
		File file = new File("C:\\Users\\hmwen\\Desktop\\0d338744ebf81a4c6c6ad5dfd42a6059252da64f.jpg");
		HttpClient client = HttpClientPool.getThreadSafeHttpClient();
		Map<String,String> formEntity = new HashMap<String, String>();
		formEntity.put("userName", "hmwen");
		formEntity.put("phoneNo", "13570812756");
		formEntity.put("passWord", "123456");
		formEntity.put("picType", "idCardReverse");
		formEntity.put("picExtName", "jpg");
		FileInputStream fis = new FileInputStream(file);
	    byte[] buf = new byte[(int) file.length()];
	    fis.read(buf);
	    fis.close();
		formEntity.put("picBuffer", Base64Utils.encodeByBASE64(buf));
		//发送Notify
		HttpPost httpPost = new HttpPost("http://127.0.0.1:8089/gatewayadmin/api/basemgr/uploadPic");
		HttpResponse resp = HttpClientPool.doPost(client, httpPost, formEntity, "UTF-8");
		System.out.println(EntityUtils.toString(resp.getEntity()));
	}
}
