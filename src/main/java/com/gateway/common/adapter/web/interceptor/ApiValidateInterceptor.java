package com.gateway.common.adapter.web.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gateway.api.model.UserExtInfo;
import com.gateway.api.service.UserMgrService;
import com.gateway.api.utils.Constants;
import com.gateway.common.excetion.APIException;
import com.gateway.common.utils.SHA256Utils;
import com.google.common.base.Strings;

public class ApiValidateInterceptor extends HandlerInterceptorAdapter  {
	private static Map<String, String> ignoreMap = new HashMap<String, String>() ;
	static{
		ignoreMap.put("/api/usermgr/userregister", "/api/usermgr/userregister");
		ignoreMap.put("/api/usermgr/forgetPass", "/api/usermgr/forgetPass");
		ignoreMap.put("/api/basemgr/sendSms", "/api/basemgr/sendSms");
		ignoreMap.put("/api/weixin/gateWay", "/api/weixin/gateWay");
		ignoreMap.put("/api/download", "/api/download");
		ignoreMap.put("/api/login", "/api/login");
		 
	}

	@Autowired
	private UserMgrService userMgrService;
	
	private Logger logger = LoggerFactory.getLogger(ApiValidateInterceptor.class);
	@Override
	public void afterCompletion(HttpServletRequest arg0,HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		logger.info("afterCompletion----------------");
		
	}
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		logger.info("postHandle----------------");
	}
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		logger.info("preHandle----------------");
		String requestUri= arg0.getRequestURI();
		logger.info("拦截URL="+requestUri);
		if(ignoreMap.containsKey(requestUri.replaceFirst(arg0.getContextPath(),""))){
			return true;
		}
		String userName = arg0.getParameter("userName");
		String phoneNo = arg0.getParameter("phoneNo");
		String passWord = arg0.getParameter("passWord");
		if(Strings.isNullOrEmpty(userName)){
			throw new APIException(Constants.API_ERROE_CODE_0002,Constants.API_ERROE_CODE_0002_DESC);
		}
		if(Strings.isNullOrEmpty(phoneNo)){
			throw new APIException(Constants.API_ERROE_CODE_0003,Constants.API_ERROE_CODE_0003_DESC);
		}
		if(Strings.isNullOrEmpty(passWord)){
			throw new APIException(Constants.API_ERROE_CODE_0004,Constants.API_ERROE_CODE_0004);
		}
		UserExtInfo userExtInfo = userMgrService.validateUser(userName, phoneNo, SHA256Utils.getSHA256Encryption(passWord+phoneNo+userName) );
		if(null != userExtInfo){
			return true;
		}else{
			throw new APIException(Constants.API_ERROE_CODE_0001,Constants.API_ERROE_CODE_0001_DESC);
		}
	}
	public UserMgrService getUserMgrService() {
		return userMgrService;
	}
	public void setUserMgrService(UserMgrService userMgrService) {
		this.userMgrService = userMgrService;
	}

}
