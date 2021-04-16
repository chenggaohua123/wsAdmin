package com.gateway.common.framework.context;

import com.gateway.loginmgr.model.UserInfo;


public class DefaultAppContext implements AppContext {

	private UserInfo user = null;


	public DefaultAppContext() {
	}

	public UserInfo getUser() {
		return this.user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

}
