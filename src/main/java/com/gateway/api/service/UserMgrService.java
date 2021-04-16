package com.gateway.api.service;

import com.gateway.api.model.MerchantInfo;
import com.gateway.api.model.ReSetPass;
import com.gateway.api.model.TerInfo;
import com.gateway.api.model.UserExtInfo;
import com.gateway.api.model.UserInfo;
import com.gateway.common.excetion.APIException;

public interface UserMgrService {
	/**
	 * 注册用户
	 * @param info
	 * @return
	 */
	public boolean register(UserInfo info)  throws APIException;
	
	/**
	 * 验证用户的有效性
	 * @param userName
	 * @param phoneNo
	 * @param passWord
	 * @return
	 */
	public UserExtInfo validateUser(String userName,String phoneNo,String passWord);
	
	/**
	 * 手机用户登录
	 * @param info
	 * @return
	 */
	public UserExtInfo login(UserExtInfo info) throws APIException ;
	
	/**
	 * 修改密码
	 * @param info
	 * @return
	 */
	public int resetPass(ReSetPass info);
	
	/**
	 * 忘记密码
	 * @param info
	 * @return
	 */
	public int forgetPass(ReSetPass info) throws APIException ;
	
	/**
	 * 更新商户信息
	 * @param info
	 * @return
	 */
	public int updateMerchantinfo(MerchantInfo info);
	
	/**
	 * 更新终端信息
	 * @param info
	 * @return
	 */
	public int updateTerInfo(TerInfo info);
	
}
