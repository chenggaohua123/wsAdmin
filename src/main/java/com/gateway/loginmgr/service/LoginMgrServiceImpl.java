package com.gateway.loginmgr.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.exceptions.JedisConnectionException;

import com.gateway.common.jedis.JedisClient;
import com.gateway.common.utils.GoogleAuthenticator;
import com.gateway.loginmgr.mapper.LoginMgrDao;
import com.gateway.loginmgr.model.UserInfo;

import ft.otp.verify.OTPVerify;

@Service
public class LoginMgrServiceImpl implements LoginMgrService{
	@Autowired
	private LoginMgrDao loginMgrDao;
	//@Autowired
	//private JedisClient jedisClient;
	@Override
	public UserInfo doLogin(UserInfo user) {
		UserInfo userInfo=loginMgrDao.doLogin(user);
		if(null!=userInfo){
			if("0".equals(userInfo.getVerificationType())){//0手机动态码登陆
				long t = System.currentTimeMillis();  
				GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();  
				googleAuthenticator.setWindowSize(0); //should give 5 * 30 seconds of grace...  
				long vilidateCode=new Long(user.getVilidateCode());
				boolean result = googleAuthenticator.check_code(userInfo.getVerificationCode(), vilidateCode, t);
				if(result) {
					return userInfo;
				}else{//更新登录失败次数
					/*try {
						String LOGIN_COUNT_USERNAME="LOGIN_COUNT_"+userInfo.getUserName().toUpperCase();
						if(!jedisClient.exists(LOGIN_COUNT_USERNAME)){//如果不存在
							jedisClient.setex(LOGIN_COUNT_USERNAME, 180, "1");
						}else{
							Long incrResult = jedisClient.incr(LOGIN_COUNT_USERNAME);
							if(incrResult>10){//失败超过10次，冻结登录
								loginMgrDao.freeze(userInfo);
								jedisClient.del(LOGIN_COUNT_USERNAME);
							}
						}
					} catch (JedisConnectionException e) {
						e.printStackTrace();
					}*/
				}
			}
			if("1".equals(userInfo.getVerificationType())){//1动态密码登陆
				Long iDrift = 0L;
				Long lSucc = 0L;
				Map<?, ?> hashMap = OTPVerify.ET_CheckPwdz201(userInfo.getSeed(), // 令牌密钥
						System.currentTimeMillis() / 1000, // 调用本接口计算机的当前时间
						0L, // 给0
						60, // 给60，因为每60秒变更新的动态口令
						iDrift.intValue(), // 漂移值，用于调整硬件与服务器的时间偏差，见手册说明
						20, // 认证窗口，见手册说明
						lSucc, // 成功值，用于调整硬件与服务器的时间偏差，见手册说明
						user.getVilidateCode());
				Long nReturn = (Long) hashMap.get("returnCode");
				if(nReturn == OTPVerify.OTP_SUCCESS){
					return userInfo;
				}else{//更新登录失败次数
					/*try {
						String LOGIN_COUNT_USERNAME="LOGIN_COUNT_"+userInfo.getUserName().toUpperCase();
						if(!jedisClient.exists(LOGIN_COUNT_USERNAME)){//如果不存在
							jedisClient.setex(LOGIN_COUNT_USERNAME, 180, "1");
						}else{
							Long incrResult = jedisClient.incr(LOGIN_COUNT_USERNAME);
							if(incrResult>10){//失败超过10次，冻结登录
								loginMgrDao.freeze(userInfo);
								jedisClient.del(LOGIN_COUNT_USERNAME);
							}
						}
					} catch (JedisConnectionException e) {
						e.printStackTrace();
					}*/
				}
			}
		}
		return null;
	}
	
}
