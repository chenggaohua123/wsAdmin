package com.gateway.loginmgr.service;

import com.gateway.loginmgr.model.UserInfo;


public interface LoginMgrService {
	/**
	 * 登陆系统
	 * @param user
	 * @return
	 */
	public UserInfo doLogin(UserInfo user);
	
}
