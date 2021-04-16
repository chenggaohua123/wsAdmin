package com.gateway.fraud.common.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

public class HttpClient {

	public static String post(String reqUrl, Map<String,String> reqParams) throws Exception {
		return execute(reqUrl, reqParams, "POST");
	}
	public static String get(String reqUrl, Map<String,String> reqParams) throws Exception {
		return execute(reqUrl, reqParams, "GET");
	}
	private static String execute(String reqUrl, Map<String,String> reqParams, String method) throws Exception {
		String response = "";
		String invokeUrl = reqUrl;
		URL serverUrl = new URL(invokeUrl);
		HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();

		conn.setRequestMethod(method);
		conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false); // 不允许使用缓存
		conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charsert", "UTF-8");
		conn.connect();
		String params = mapToUrl(reqParams);
		conn.getOutputStream().write(params.getBytes());

		InputStream is = conn.getInputStream();

		BufferedReader in = new BufferedReader(new InputStreamReader(is,"utf-8"));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		response = URLDecoder.decode(buffer.toString(), "utf-8");
		conn.disconnect();
		return response;
	}
	
    public static String postFile(String actionUrl, Map<String, String> params, Map<String, byte[]> files)
            throws Exception
    {
        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--", LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";

        URL uri = new URL(actionUrl);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setReadTimeout(6 * 1000); // 缓存的最长时间
        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false); // 不允许使用缓存
        conn.setRequestMethod("POST");
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

        // 首先组拼文本类型的参数
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet())
        {
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEND);
            sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
            sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
            sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
            sb.append(LINEND);
            sb.append(entry.getValue());
            sb.append(LINEND);
        }

        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
        outStream.write(sb.toString().getBytes());
        InputStream is = null;
        // 发送文件数据
        if (files != null)
        {
            for (Map.Entry<String, byte[]> file : files.entrySet())
            {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(PREFIX);
                sb1.append(BOUNDARY);
                sb1.append(LINEND);
                sb1.append("Content-Disposition: form-data; name=\"pic\"; filename=\"" + file.getKey() + "\"" + LINEND);
                sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
                sb1.append(LINEND);
                outStream.write(sb1.toString().getBytes());

                outStream.write(file.getValue());

                outStream.write(LINEND.getBytes());
            }
        }
        // 请求结束标志
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
        outStream.write(end_data);
        outStream.flush();
        // 得到响应码
        int res = conn.getResponseCode();
        String response = "";
        if (res == 200)
        {
            is = conn.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is,"utf-8"));
    		StringBuffer buffer = new StringBuffer();
    		String line = "";
    		while ((line = in.readLine()) != null) {
    			buffer.append(line);
    		}
    		response = URLDecoder.decode(buffer.toString(), "utf-8");
    		conn.disconnect();
    		
        }
        outStream.close();
        conn.disconnect();
        return response;
    }
    
    private static String mapToUrl(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (isFirst) {
                sb.append(key + "=" + URLEncoder.encode(value, "utf-8"));
                isFirst = false;
            } else {
                if (value != null) {
                    sb.append("&" + key + "=" + URLEncoder.encode(value, "utf-8"));
                } else {
                    sb.append("&" + key + "=");
                }
            }
        }
        return sb.toString();
    }
}
