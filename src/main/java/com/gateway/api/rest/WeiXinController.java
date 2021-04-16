package com.gateway.api.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gateway.api.model.WeiXinRespInfo;

@Controller
@RequestMapping(value="/api/weixin")
public class WeiXinController extends ApiBaseController{
	
	/**
	 * 微信回调接口
	 * @param info
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value="/gateWay")
	public String gateWay(WeiXinRespInfo info) throws IOException{
		BufferedReader rd = new BufferedReader(new InputStreamReader(getRequest().getInputStream()));
		String temp = rd.readLine();
		String postStr = "";
		while(null != temp){
			postStr+= temp;
			temp = rd.readLine();
		}
		log.info("postStr="+postStr);
		return info.getEchostr();
	}
}
