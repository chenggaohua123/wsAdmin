package com.gateway.loginmgr.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gateway.loginmgr.model.UserInfo;
@Repository
public interface LoginMgrDao{
	/**
	 * 处理登陆
	 * @param user
	 * @return
	 */
	public UserInfo doLogin(@Param("user") UserInfo user);
	
	/**
	 * 冻结用户
	 * @param user
	 * @return
	 */
	public UserInfo freeze(@Param("user") UserInfo user);
}
