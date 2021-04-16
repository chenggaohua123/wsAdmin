package com.gateway.api.utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class SMSSendUtil {
	
	private static String LANZUrl = "http://www.lanz.net.cn/LANZGateway/DirectSendSMSs.asp";
	private static Log logger = LogFactory.getLog(SMSSendUtil.class);

	
	/**
	 * 发送短信
	 * @param userid
	 * @param account
	 * @param password
	 * @param mobile
	 * @param content
	 * @param sendDate
	 * @param sendTime
	 * @param postFixNum
	 */
	public static String sendLANZSMS(String userid, String account, String password,
			String mobile, String content, String sendDate, String sendTime,
			String postFixNum) {
		DefaultHttpClient httpclient = new DefaultHttpClient();;
		HttpPost post = new HttpPost(LANZUrl);
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("UserID",userid));
        nvps.add(new BasicNameValuePair("Account", account));
        nvps.add(new BasicNameValuePair("Password", password));
        nvps.add(new BasicNameValuePair("Phones", mobile));
        nvps.add(new BasicNameValuePair("Content",content));
        nvps.add(new BasicNameValuePair("SendDate", sendDate));
        nvps.add(new BasicNameValuePair("SendTime", sendTime));
        nvps.add(new BasicNameValuePair("PostFixNum", postFixNum));
        nvps.add(new BasicNameValuePair("SMSType", "1"));
        try {
			post.setEntity(new UrlEncodedFormEntity(nvps,"GB2312"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
        
        HttpResponse response;
		try {
			response = httpclient.execute(post);
			StatusLine statusLine = response.getStatusLine();
			String respXML = EntityUtils.toString(response.getEntity());
			logger.info("返回状态吗："+statusLine);
			logger.info("返回参数："+respXML);
			Map<String, String> respMap=  parseLANZXmlResult(respXML);
			if(null != respMap && null != respMap.get("ErrorNum")){
				String status = respMap.get("ErrorNum");
				if("0".equals(status)){
					logger.info("短信发送内容："+content);
					logger.info("短信发送成功，发送号码为："+respMap.get("PhonesSend"));
					logger.info("短信拒绝发送号码为："+respMap.get("ErrPhones"));
					return status;
				}else{
					logger.info("短信发送异常，发送号码为："+mobile+"错误信息为："+respMap.get("message"));
				}
				
			}else{
				logger.info("短信发送异常，发送号码为："+mobile);
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "-1";
	}
	
	/**
	 * 解析返回数据
	 * @param xmlResult
	 * @return
	 */
	private static Map<String, String> parseLANZXmlResult(String xmlResult){
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		Map<String, String> respMap = new HashMap<String, String>();
		try {
			doc = builder.build(new StringReader(xmlResult));
			Element root = doc.getRootElement();
			respMap.put("ErrorNum",root.getChild("ErrorNum") != null? root.getChild("ErrorNum").getText():"");
			respMap.put("PhonesSend",root.getChild("PhonesSend") != null? root.getChild("PhonesSend").getText():"");
			respMap.put("ErrPhones",root.getChild("ErrPhones") != null? root.getChild("ErrPhones").getText():"");
			return respMap;
		}catch(IOException e){
			
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return null;
	}
}
