package com.gateway.sysmgr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.loginmgr.model.UserInfo;
import com.gateway.sysmgr.model.BankRespMsgMgrInfo;
import com.gateway.sysmgr.model.BaseDataInfo;
import com.gateway.sysmgr.model.CardBinInfo;
import com.gateway.sysmgr.model.CheckToNoInfo;
import com.gateway.sysmgr.model.GwPicInfo;
import com.gateway.sysmgr.model.MenuInfo;
import com.gateway.sysmgr.model.RoleInfo;
import com.gateway.sysmgr.model.RoleRefMenuInfo;
import com.gateway.sysmgr.model.SendEmailReqInfo;
import com.gateway.sysmgr.model.TransInfo;
import com.gateway.sysmgr.model.UserRelAgent;

public interface SysMgrDao {
	/**
	 * 根据用户ID查询用户的所有菜单
	 * @param user
	 * @return
	 */
	public List<MenuInfo> queryUserMenuByUserId(@Param("user") UserInfo user);
	
	/**
	 * 查询菜单列表
	 * @param criteria
	 * @return
	 */
	public List<MenuInfo> queryMenuList(Criteria criteria);
	
	/**
	 * 查询用户列表
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<UserInfo> queryUserList(Criteria criteria,RowBounds rb);
	
	/**
	 * 统计用户记录数
	 * @param criteria
	 * @return
	 */
	public int countUserList(Criteria criteria);
	
	/**
	 * 查询角色列表
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<RoleInfo> queryPageRole(Criteria criteria,RowBounds rb);
	
	/**
	 * 查询已经关了用户角色
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<RoleInfo> queryPitchRole(@Param("userId")Integer userId);
	
	/**
	 * 根据ROLEID查询角色信息
	 * @param id
	 * @return
	 */
	public RoleInfo queryRoleById(int id);
	
	/**
	 * 返回角色记录条数
	 * @param criteria
	 * @return
	 */
	public int countRoleInfo(Criteria criteria);
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	public int addUserInfo(@Param("info") UserInfo user);
	
	/**
	 * 修改密码
	 * @param user
	 * @return
	 */
	public int updateUserPassword(@Param("info")UserInfo user);
	
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
	public int updateUserInfo(@Param("info")UserInfo user);
	
	/**
	 * 添加角色
	 * @param roleInfo
	 * @return
	 */
	public int addSysRoleInfo(@Param("info")RoleInfo roleInfo);
	
	/**
	 * 修改角色
	 * @param roleInfo
	 * @return
	 */
	public int updateRoleInfo(@Param("info")RoleInfo roleInfo);
	
	/**
	 * 把权限分配到角色
	 * @param menus
	 * @param roleId
	 * @return
	 */
	public int saveMenuToRole(@Param("menus") String[] menus, @Param("roleId")int roleId);
	
	/**
	 * 删除角色关联的权限信息
	 * @param roleId
	 * @return
	 */
	public int deleteRoleMenuByRoleId(@Param("roleId") int roleId);
	
	/**
	 * 根据角色ID查询角色拥有的菜单
	 * @param roleId
	 * @return
	 */
	public List<RoleRefMenuInfo> queryMenuNoByRoleId(@Param("roleId") int roleId); 
	
	/**
	 * 查询最大的菜单编号作为新增菜单的编号
	 * @return
	 */
	public int queryMaxMenuNo();
	
	/**
	 * 保存菜单
	 * @param menu
	 * @return
	 */
	public int addMenuInfo(@Param("menu") MenuInfo menu);
	
	/**
	 * 分页查询基础数据列表
	 * @param userId
	 * @return
	 */
	public int deleteRoleUserByUserId(@Param("userId")int userId);
	
	/**
	 * 用户关联到角色
	 * @param roleIds
	 * @param userId
	 * @return
	 */
	public int addRoleRefUserInfo(@Param("roleIds") String[] roleIds, @Param("userId")int userId);
	
	/**
	public List<BaseDataInfo> getBaseDataList(Criteria criteria);
	 * 统计基础数据条数
	 * @param criteria
	 * @return
	 */
	public int countBaseDataList(Criteria criteria);
	
	/**
	 * 添加字段数据
	 * @param info
	 * @return
	 */
	public int addBaseDataInfo(@Param("info") BaseDataInfo info);
	
	/**
	 * 修改字典数据
	 * @param info
	 * @return
	 */
	public int updateBaseDateInfo(@Param("info") BaseDataInfo info);
	
	/**
	 * 根据ID查询字典数据信息
	 * @param id
	 * @return
	 */
	public BaseDataInfo queryBaseDataInfoById(@Param("id") int id);
	
	/**
	 * 查询字典列表
	 * @param criteria
	 * @return
	 */
	public List<BaseDataInfo> getBaseDataList(Criteria criteria);
	
	/**
	 * 分页查询
	 * @param criteria
	 * @param rd
	 * @return
	 */
	public List<BaseDataInfo> getBaseDataList(Criteria criteria,RowBounds rd);
	
	/**
	 * 查询基础数据
	 * @param tabelName
	 * @param columnKeyName
	 * @param columnVauleName
	 * @return
	 */
	public List<BaseDataInfo> queryBaseDataListByTableName(@Param("tableName") String tabelName,
			@Param("columnKeyName")String columnKeyName,
			@Param("columnVauleName")String columnVauleName);
	
	/**
	 * 查询用户详细信息
	 * @param id
	 * @return
	 */
	public UserInfo querySysUserInfoBusInfo(@Param("id")int id);
	
	/**
	 * 查询图片
	 * @param phoneNo
	 * @return
	 */
	public List<GwPicInfo> queryGwPicInfo(@Param("phoneNo")String phoneNo);
	
	/**
	 * 用户关联代理商
	 * @param relAgent
	 * @return
	 */
	public int addUserRelAgent(@Param("info")UserRelAgent relAgent);
	
	/**
	 * 判断用户是否重复关联代理商
	 * @param id
	 * @return
	 */
	public UserRelAgent queryUserRelAgent(@Param("id")int id);
	
	/**
	 * 加载图片
	 * @param phoneNo
	 * @param picType
	 * @return
	 */
	public GwPicInfo ajaxGwPicInfo(@Param("phoneNo")String phoneNo,@Param("picType")String picType);
	
	/**
	 * 根据商户号查询商户后台用户信息
	 * @param merNo
	 * @return
	 */
	public List<UserInfo> queryUserInfoByMerNo(@Param("merNo") String merNo);
	
	/**
	 * 添加邮件发送请求
	 * @param req
	 * @return
	 */
	public int addSendEmailReqInfo(@Param("info") SendEmailReqInfo req);
	/**
	 * 银行返回结果管理
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<BankRespMsgMgrInfo> getBankRespMsgMgrInfo(Criteria criteria,
			RowBounds rb);
	/**
	 * 统计银行返回结果管理条数
	 * @param criteria
	 * @return
	 */
	public int countBankRespMsgMgrInfo(Criteria criteria);
	/**
	 * 添加银行返回结果信息
	 * @param info
	 * @return
	 */
	public int addBankRespMsgMgrInfo(BankRespMsgMgrInfo info);
	/**
	 * 通过ID查询银行返回结果信息
	 * @param info
	 * @return
	 */
	public BankRespMsgMgrInfo queryBankRespMsgMgrInfoById(int id);
	/**
	 * 修改银行返回结果信息
	 * @param info
	 * @return
	 */
	public int updateBankRespMsgMgrInfo(BankRespMsgMgrInfo info);
	/**
	 * 统计银行真实返回结果条数
	 * @param criteria
	 * @return
	 */
	public int countBankRealRespMsg(Criteria criteria);
	/**
	 * 查询银行真实返回结果
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<TransInfo> queryBankRealRespMsg(Criteria criteria, RowBounds rb);
	
	/**
	 * 统计虚拟银行卡数量
	 * @param criteria
	 * @return
	 */
	public int countCheckToNoInfo(Criteria criteria);
	/**
	 * 查询虚拟银行卡信息
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<CheckToNoInfo> queryCheckToNoInfo(Criteria criteria,
			RowBounds rb);
	
	public int addCheckToNoInfo(CheckToNoInfo info);

	public int updateCheckToNoInfo(CheckToNoInfo info);
	
	public CheckToNoInfo queryCheckToNoInfoById(String id);

	public int updateCardPayLimitByMerNoAndTerNo(CheckToNoInfo info);
	
	/**
	 * 查询卡bin信息
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<CardBinInfo> queryCardBinInfo(Criteria criteria, RowBounds rb);
	/**
	 * 统计卡bin数量
	 * @param criteria
	 * @return
	 */
	public int countCardBinInfo(Criteria criteria);
	
	/**
	 * 查询卡BIN信息界面
	 */
	public CardBinInfo queryCardBinInfoById(@Param("vo") CardBinInfo vo);
	
	/**
	 * 保存卡BIN信息
	 */
	public int saveCardBinInfo(@Param("vo") CardBinInfo vo);
	
	/**
	 * 修改卡BIN信息
	 */
	public int updateCardBinInfoById(@Param("vo") CardBinInfo vo);
	
	/**
	 * 根据卡bin
	 */
	public int queryCardBinInfoByBin(@Param("vo") CardBinInfo vo);
	
	public void updateCardBinSHAByBin(@Param("id")String id, @Param("binSHA")String binSHA);

	public List<CardBinInfo> queryCardBinInfoByBinSHAIsNull();
	
	public CardBinInfo queryCardBinInfoByBinNumber(String bin);
	
	public CardBinInfo queryCardBinInfoByTradeNo(String tradeNo);
}
