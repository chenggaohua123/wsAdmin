package com.gateway.api.rest;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gateway.api.model.BaseInfo;
import com.gateway.api.model.LoginHisInfo;
import com.gateway.api.model.MerchantInfo;
import com.gateway.api.model.ReSetPass;
import com.gateway.api.model.TerInfo;
import com.gateway.api.model.UserExtInfo;
import com.gateway.api.model.UserInfo;
import com.gateway.api.service.UserMgrService;
import com.gateway.api.utils.Constants;
import com.gateway.common.excetion.APIException;
//import com.google.common.base.Strings;

@Controller
@RequestMapping(value="/api/usermgr")
public class UserMgrController extends ApiBaseController{
	
	@Resource
	private UserMgrService userMgrService;
	
	/**
	 * 用户注册接口
	 * @param userInfo
	 * @return
	 * @throws APIException 
	 */
	@ResponseBody
	@RequestMapping(value="/userregister")
	public UserInfo register(UserInfo userInfo) throws APIException{
		if(StringUtils.isEmpty(userInfo.getPassWord())){
			throw new APIException(Constants.API_ERROE_CODE_0004,Constants.API_ERROE_CODE_0004_DESC);
		}
		if(StringUtils.isEmpty(userInfo.getUserName())){
			throw new APIException(Constants.API_ERROE_CODE_0002,Constants.API_ERROE_CODE_0002_DESC);
		}
		if(StringUtils.isEmpty(userInfo.getPhoneNo())){
			throw new APIException(Constants.API_ERROE_CODE_0003,Constants.API_ERROE_CODE_0003_DESC);
		}
//		if(Strings.isNullOrEmpty(userInfo.getEmail())){
//			throw new APIException(Constants.API_ERROE_CODE_0005,Constants.API_ERROE_CODE_0005_DESC);
//		}
		boolean flag = userMgrService.register(userInfo);
		userInfo.setPassWord("");
		if(flag){
			userInfo.setRespCode(Constants.API_ERROE_CODE_0000);
			userInfo.setRespDesc(Constants.API_ERROE_CODE_0000_DESC);
		}
		return userInfo;
	}

	/**
	 * 手机登录接口
	 * @param userInfo
	 * @return
	 * @throws APIException 
	 */
	@ResponseBody
	@RequestMapping(value="/login")
	public UserExtInfo login(UserExtInfo userInfo,LoginHisInfo loginHisInfo) throws APIException{
		String ipAddress = getRequest().getRemoteAddr();
		loginHisInfo.setLastLoginIP(ipAddress);
		userInfo.setLoginHisInfo(loginHisInfo);
		return userMgrService.login(userInfo);
	}
	
	/**
	 * 修改密码
	 * @param info
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/resetPass")
	public ReSetPass resetPass(ReSetPass info){
		int i = userMgrService.resetPass(info);
		if(i>0){
			info.setRespCode(Constants.API_ERROE_CODE_0000);
			info.setRespDesc(Constants.API_ERROE_CODE_0000_DESC);
		}else{
			info.setRespCode(Constants.API_ERROE_CODE_9999);
			info.setRespDesc(Constants.API_ERROE_CODE_9999_DESC);
		}
		return info;
	}
	
	/**
	 * 忘记密码，密码通过发送短信到注册时的手机号
	 * @param baseInfo
	 * @return
	 * @throws APIException 
	 */
	@ResponseBody
	@RequestMapping(value="/forgetPass")
	public BaseInfo forgetPass(ReSetPass baseInfo) throws APIException{
		int i = userMgrService.forgetPass(baseInfo);
		if(i>0){
			baseInfo.setRespCode(Constants.API_ERROE_CODE_0000);
			baseInfo.setRespDesc(Constants.API_ERROE_CODE_0000_DESC);
		}else{
			baseInfo.setRespCode(Constants.API_ERROE_CODE_9999);
			baseInfo.setRespDesc(Constants.API_ERROE_CODE_9999_DESC);
		}
		return baseInfo;
	}
	
	/**
	 * 更新商户信息
	 * @return
	 */
	@RequestMapping(value="/updateMerchantinfo")
	@ResponseBody
	public BaseInfo updateMerchantinfo(BaseInfo info,MerchantInfo merchantinfo){
		int i = userMgrService.updateMerchantinfo(merchantinfo);
		if(i>0){
			info.setRespCode(Constants.API_ERROE_CODE_0000);
			info.setRespDesc(Constants.API_ERROE_CODE_0000_DESC);
			return info;
		}else{
			info.setRespCode(Constants.API_ERROE_CODE_9999);
			info.setRespDesc(Constants.API_ERROE_CODE_9999_DESC);
			return info;
		}
	}
	
	/**
	 * 修改终端信息
	 * @param info
	 * @param terInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateTerInfo")
	public BaseInfo updateTerInfo(BaseInfo info,TerInfo terInfo){
		int i = userMgrService.updateTerInfo(terInfo);
		if(i>0){
			info.setRespCode(Constants.API_ERROE_CODE_0000);
			info.setRespDesc(Constants.API_ERROE_CODE_0000_DESC);
			return info;
		}else{
			info.setRespCode(Constants.API_ERROE_CODE_9999);
			info.setRespDesc(Constants.API_ERROE_CODE_9999_DESC);
			return info;
		}
	}
	
	public UserMgrService getUserMgrService() {
		return userMgrService;
	}

	public void setUserMgrService(UserMgrService userMgrService) {
		this.userMgrService = userMgrService;
	}
	
}
