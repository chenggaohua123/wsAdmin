package com.gateway.sysmgr.service;

import java.util.List;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.APIException;
import com.gateway.loginmgr.model.UserInfo;
import com.gateway.sysmgr.model.BankRespMsgMgrInfo;
import com.gateway.sysmgr.model.BaseDataInfo;
import com.gateway.sysmgr.model.CardBinInfo;
import com.gateway.sysmgr.model.CheckToNoInfo;
import com.gateway.sysmgr.model.MenuInfo;
import com.gateway.sysmgr.model.RoleInfo;
import com.gateway.sysmgr.model.RoleRefMenuInfo;
import com.gateway.sysmgr.model.TransInfo;
import com.gateway.sysmgr.model.UserRelAgent;

public interface SysMgrService {
	/**
	 * 根据用户ID查询用户的所有菜单 已经权限
	 * @param user
	 * @return
	 */
	public List<MenuInfo> queryUserMenuByUserId(UserInfo user);
	
	/**
	 * 查询菜单列表
	 * @param criteria
	 * @return
	 */
	public List<MenuInfo> queryMenuList(Criteria criteria);
	
	/**
	 * 查询最大的menuNo作为新增菜单的编号
	 * @return
	 */
	public int queryMaxMenuNo();
	
	/**
	 * 分页查询用户列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<UserInfo> queryPageUser(Criteria criteria);
	
	/**
	 * 分页查询角色列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<RoleInfo> queryPageRole(Criteria criteria);
	
	/**
	 * 查询用户关联角色列表
	 * @param criteria
	 * @return
	 */
	public List<RoleInfo> queryPitchRole(Integer userId);
	
	/**
	 * 根据RoleId查询角色信息
	 * @param id
	 * @return
	 */
	public RoleInfo queryRoleById(int id);
	
	/**
	 * 添加新用户
	 * @param user
	 * @return
	 */
	public int addUserInfo(UserInfo user);
	
	/**
	 * 修改密码
	 * @param user
	 * @return
	 */
	public int updateUserPassword(UserInfo user);
	
	/**
	 * 按id查询用户信息
	 * @param id
	 * @return
	 */
	public UserInfo queryUserInfoById(int id);
	
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(UserInfo user);
	
	
	/**
	 * 添加角色
	 * @param roleInfo
	 * @return
	 */
	public int addSysRoleInfo(RoleInfo roleInfo);
	
	/**
	 * 修改角色
	 * @param roleInfo
	 * @return
	 */
	public int updateRoleInfo(RoleInfo roleInfo);
	
	/**
	 * 把权限关联到角色
	 * @param menus
	 * @param roleId
	 * @return
	 */
	public int saveMenuToRole(String [] menus,int roleId);
	
	/**
	 * 根据角色ID查询角色拥有的菜单
	 * @param roleId
	 * @return
	 */
	public List<RoleRefMenuInfo> queryMenuNoByRoleId(int roleId);
	
	/**
	 * 保存菜单
	 * @param menu
	 * @return
	 */
	public int addMenuInfo(MenuInfo menu);

	
	/**
	 * 用户关联到角色
	 * @param roleIds
	 * @param userId
	 * @return
	 */
	public int addRoleRefUserInfo(String[] roleIds,int userId);
	
	/**
	 * 查询字典列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<BaseDataInfo> getBaseDataList(Criteria criteria);
	
	/**
	 * 添加字典数据
	 * @param info
	 * @return
	 */
	public int addBaseDataInfo(BaseDataInfo info);
	
	/**
	 * 更新字典数据
	 * @param info
	 * @return
	 */
	public int updateBaseDateInfo(BaseDataInfo info);
	
	/**
	 * 根据ID查询字典数据
	 * @param id
	 * @return
	 */
	public BaseDataInfo queryBaseDateInfoById(int id);

	/**
	 * 查询字典数据列表
	 * @param criteria
	 * @return
	 */
	List<BaseDataInfo> queryBaseDataList(Criteria criteria);
	
	/**
	 * 查询字典数据
	 * @param tabelName
	 * @param columnKeyName
	 * @param columnVauleName
	 * @return
	 */
	public List<BaseDataInfo> queryBaseDataList(String tabelName,String columnKeyName,String columnVauleName);
	
	
	/**
	 * 查询用户详细信息
	 * @param id
	 * @return
	 */
	public UserInfo querySysUserInfoBusInfo(int id);
	
	/**
	 * 用户关联代理商
	 * @param relAgent
	 * @return
	 */
	public int addUserRelAgent(UserRelAgent relAgent);
	
	/**
	 * 判断用户是否重复关联代理商
	 * @param id
	 * @return
	 */
	public UserRelAgent queryUserRelAgent(int id);
	
	/**
	 * 根据商户号查询商户后台用户信息
	 * @param merNo
	 * @return
	 */
	public List<UserInfo> queryUserInfoByMerNo(String merNo);
	
	/**
	 * 银行返回结果管理 
	 * @param criteria
	 * @return
	 */
	public PageInfo<BankRespMsgMgrInfo> getBankRespMsgMgrInfo(Criteria criteria);

	/**
	 * 添加银行返回结果信息
	 * @param info
	 * @return
	 */
	public int addBankRespMsgMgrInfo(BankRespMsgMgrInfo info);
	/**
	 * 修改银行返回结果信息
	 * @param info
	 * @return
	 */
	public int updateBankRespMsgMgrInfo(BankRespMsgMgrInfo info);
	/**
	 * 根据Id查询银行返回结果信息
	 * @param info
	 * @return
	 */
	public BankRespMsgMgrInfo queryBankRespMsgMgrInfoById(int id);
	/**
	 * 查询银行真实返回结果
	 * @param criteria
	 * @return
	 */
	public PageInfo<TransInfo> queryBankRealRespMsg(Criteria criteria);
	/**
	 * 查询虚拟银行卡
	 * @param criteria
	 * @return
	 */
	public PageInfo<CheckToNoInfo> queryCheckToNoInfo(Criteria criteria);
	/**
	 * 添加虚拟银行卡
	 * @param info
	 * @return
	 */
	public int addCheckToNoInfo(CheckToNoInfo info);

	public int updateCheckToNoInfo(CheckToNoInfo info);

	public CheckToNoInfo queryCheckToInfoById(String id);

	public int updateCardPayLimitByMerNoAndTerNo(CheckToNoInfo info);
	
	/**
	 * 查询卡bin 信息
	 * @param criteria
	 * @return
	 */
	public PageInfo<CardBinInfo> queryCardBinInfo(Criteria criteria);
	
	/**
	 * 查询卡BIN信息界面
	 */
	public CardBinInfo queryCardBinInfoById(CardBinInfo info);
	
	/**
	 * 保存卡BIN信息
	 */
	public int saveCardBinInfo(CardBinInfo info) throws APIException;
	
	/**
	 * 修改卡BIN信息
	 */
	public int updateCardBinInfoById(CardBinInfo info) throws APIException;
	
	public void updateCardBinSHAByBin(CardBinInfo info);

	public List<CardBinInfo> queryCardBinInfoByBinSHAIsNull();

	public CardBinInfo queryCardBinInfoByBin(String bin);
	
	public CardBinInfo queryCardBinInfoByTradeNo(String line);
}
