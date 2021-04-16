package com.gateway.common.utils;

import java.io.IOException;import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class Test004 {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		DefaultHttpClient httpClient = Tools.getHttpClient();
		String testurl = "https://merchant.remitepay.com/api/GetRefund?merchantid=978250&signature=1c1a26d2a3161c80918e9645ed936f6b&year=2020&month=1";
		HttpGet httpGet = new HttpGet(testurl);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		System.out.println("status:" + httpResponse.getStatusLine());
	}
}
