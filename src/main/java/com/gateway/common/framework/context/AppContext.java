package com.gateway.common.framework.context;

import com.gateway.loginmgr.model.UserInfo;


public interface AppContext {

	UserInfo getUser();

	void setUser(UserInfo user);

}
