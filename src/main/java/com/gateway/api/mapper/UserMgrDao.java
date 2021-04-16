package com.gateway.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gateway.api.model.BusInfo;
import com.gateway.api.model.LoginHisInfo;
import com.gateway.api.model.MerchantInfo;
import com.gateway.api.model.ReSetPass;
import com.gateway.api.model.TerInfo;
import com.gateway.api.model.UserExtInfo;
import com.gateway.api.model.UserInfo;

public interface UserMgrDao {
	
	/**
	 * 添加app用户
	 * @param info
	 * @return
	 */
	public int addPhoneUser(@Param("info") UserInfo info);
	
	/**
	 * 根据手机号查询用户信息
	 * @param phoneNo
	 * @return
	 */
	public UserInfo queryUserInfoByPhoneNo(@Param("phoneNo") String phoneNo);
	
	/**
	 * 手机用户登录
	 * @param info
	 * @return
	 */
	public UserExtInfo login(@Param("info") UserInfo info);
	
	/**
	 * 查询该手机号下面的所有终端信息
	 * @param phoneNo
	 * @return
	 */
	public List<TerInfo> queryTerInfoByPhoneNo(@Param("phoneNo") String phoneNo);
	
	/**
	 * 查询app端支持的功能列表
	 * @param phoneNo
	 * @return
	 */
	public List<String> queryFunctionListByPhoneNo(@Param("phoneNo") String phoneNo);
	
	/**
	 * 查询用户登录的历史信息
	 * @param phoneNo
	 * @return
	 */
	public LoginHisInfo queryHisLoginInfoByPhoneNo(@Param("phoneNo") String phoneNo);
	
	/**
	 * 查询用户的营业信息
	 * @param phoneNo
	 * @return
	 */
	public BusInfo queryBusInfoByPhoneNo(@Param("phoneNo") String phoneNo);
	
	/**
	 * 增加登陆记录
	 * @param info
	 * @param phoneNo
	 * @return
	 */
	public int addLogHisInfo(@Param("info") LoginHisInfo info,@Param("phoneNo") String phoneNo );
	
	/**
	 * 根据手机号查询商户信息
	 * @param phoneNo
	 * @return
	 */
	public MerchantInfo queryMerchantInfoByPhone(@Param("phoneNo") String phoneNo);
	
	/**
	 * 修改密码
	 * @param info
	 * @return
	 */
	public int resetPass(@Param("info") ReSetPass info);
	
	/**
	 * 查询商户信息
	 * @param phoneNo
	 * @return
	 */
	public MerchantInfo queryMerchantInfoByPhoneNo(@Param("phoneNo") String phoneNo);
	
	/**
	 * 更新商户信息
	 * @param info
	 * @return
	 */
	public int updateMerchantInfo(@Param("info") MerchantInfo info);
	
	
	/**
	 * 更新终端信息
	 * @param info
	 * @return
	 */
	public int updateTerInfo(@Param("info") TerInfo info);
}
