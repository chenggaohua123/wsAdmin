package com.gateway.sysmgr.service;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.APIException;
import com.gateway.common.utils.BankCardEncryp;
import com.gateway.common.utils.Funcs;
import com.gateway.loginmgr.model.UserInfo;
import com.gateway.sysmgr.mapper.SysMgrDao;
import com.gateway.sysmgr.model.BankRespMsgMgrInfo;
import com.gateway.sysmgr.model.BaseDataInfo;
import com.gateway.sysmgr.model.CardBinInfo;
import com.gateway.sysmgr.model.CheckToNoInfo;
import com.gateway.sysmgr.model.MenuInfo;
import com.gateway.sysmgr.model.RoleInfo;
import com.gateway.sysmgr.model.RoleRefMenuInfo;
import com.gateway.sysmgr.model.TransInfo;
import com.gateway.sysmgr.model.UserRelAgent;

@Service
public class SysMgrServiceImpl implements SysMgrService{
	@Autowired
	private SysMgrDao sysMgrDao;
	@Override
	public List<MenuInfo> queryUserMenuByUserId(UserInfo user) {
		return sysMgrDao.queryUserMenuByUserId(user);
	}
	
	public SysMgrDao getSysMgrDao() {
		return sysMgrDao;
	}
	public void setSysMgrDao(SysMgrDao sysMgrDao) {
		this.sysMgrDao = sysMgrDao;
	}

	@Override
	public PageInfo<UserInfo> queryPageUser(Criteria criteria) {
		PageInfo<UserInfo> page = new PageInfo<UserInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(sysMgrDao.countUserList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<UserInfo> list = sysMgrDao.queryUserList(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public PageInfo<RoleInfo> queryPageRole(Criteria criteria) {
		PageInfo<RoleInfo> page = new PageInfo<RoleInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(sysMgrDao.countRoleInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<RoleInfo> list = sysMgrDao.queryPageRole(criteria, rb);
		page.setData(list);
		return page;
	}
	
	public List<RoleInfo> queryPitchRole(Integer userId) {
		List<RoleInfo> list = sysMgrDao.queryPitchRole(userId);
		return list;
	}

	@Override
	public int addUserInfo(UserInfo user) {
		return sysMgrDao.addUserInfo(user);
	}


	@Override
	public UserInfo queryUserInfoById(int id) {
		return sysMgrDao.queryUserInfoById(id);
	}

	@Override
	public int updateUserInfo(UserInfo user) {
		return sysMgrDao.updateUserInfo(user);
	}

	@Override
	public int addSysRoleInfo(RoleInfo roleInfo) {
		return sysMgrDao.addSysRoleInfo(roleInfo);
	}

	@Override
	public RoleInfo queryRoleById(int id) {
		return sysMgrDao.queryRoleById(id);
	}

	@Override
	public int updateRoleInfo(RoleInfo roleInfo) {
		return sysMgrDao.updateRoleInfo(roleInfo);
	}

	@Override
	public List<MenuInfo> queryMenuList(Criteria criteria) {
		return sysMgrDao.queryMenuList(criteria);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int saveMenuToRole(String[] menus, int roleId) {
		sysMgrDao.deleteRoleMenuByRoleId(roleId);
		return sysMgrDao.saveMenuToRole(menus, roleId);
	}

	@Override
	public List<RoleRefMenuInfo> queryMenuNoByRoleId(int roleId) {
		return sysMgrDao.queryMenuNoByRoleId(roleId);
	}

	@Override
	public int queryMaxMenuNo() {
		return sysMgrDao.queryMaxMenuNo();
	}

	@Override
	public int addMenuInfo(MenuInfo menu) {
		return sysMgrDao.addMenuInfo(menu);
	}


	@Transactional(rollbackFor = Exception.class)
	public int addRoleRefUserInfo(String[] roleIds, int userId) {
		sysMgrDao.deleteRoleUserByUserId(userId);
		return sysMgrDao.addRoleRefUserInfo(roleIds, userId);
	}

	@Override
	public PageInfo<BaseDataInfo> getBaseDataList(Criteria criteria) {
		PageInfo<BaseDataInfo> page = new PageInfo<BaseDataInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(sysMgrDao.countBaseDataList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<BaseDataInfo> list = sysMgrDao.getBaseDataList(criteria, rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public List<BaseDataInfo> queryBaseDataList(Criteria criteria){
		return sysMgrDao.getBaseDataList(criteria);
	}
	
	@Override
	public int addBaseDataInfo(BaseDataInfo info) {
		return sysMgrDao.addBaseDataInfo(info);
	}

	@Override
	public int updateBaseDateInfo(BaseDataInfo info) {
		return sysMgrDao.updateBaseDateInfo(info);
	}

	@Override
	public BaseDataInfo queryBaseDateInfoById(int id) {
		return sysMgrDao.queryBaseDataInfoById(id);
	}

	@Override
	public List<BaseDataInfo> queryBaseDataList(String tabelName,
			String columnKeyName, String columnVauleName) {
		return sysMgrDao.queryBaseDataListByTableName(tabelName, columnKeyName, columnVauleName);
	}

	@Override
	public UserInfo querySysUserInfoBusInfo(int id) {
		UserInfo info=sysMgrDao.querySysUserInfoBusInfo(id);
		if(null!=info.getPhoneNo()){
			info.setList(sysMgrDao.queryGwPicInfo(info.getPhoneNo()));
		}
		return info;
	}

	@Override
	public int addUserRelAgent(UserRelAgent relAgent) {
		return sysMgrDao.addUserRelAgent(relAgent);
	}

	@Override
	public UserRelAgent queryUserRelAgent(int id) {
		return sysMgrDao.queryUserRelAgent(id);
	}

	@Override
	public int updateUserPassword(UserInfo user) {
		return sysMgrDao.updateUserPassword(user);
	}

	@Override
	public List<UserInfo> queryUserInfoByMerNo(String merNo) {
		return sysMgrDao.queryUserInfoByMerNo(merNo);
	}
	@Override
	public PageInfo<BankRespMsgMgrInfo> getBankRespMsgMgrInfo(Criteria criteria) {
		PageInfo<BankRespMsgMgrInfo> page = new PageInfo<BankRespMsgMgrInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(sysMgrDao.countBankRespMsgMgrInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<BankRespMsgMgrInfo> list = sysMgrDao.getBankRespMsgMgrInfo(criteria, rb);
		page.setData(list);
		return page;
	}
	@Override
	public int addBankRespMsgMgrInfo(BankRespMsgMgrInfo info) {
		return sysMgrDao.addBankRespMsgMgrInfo(info);
	}
	
	@Override
	public BankRespMsgMgrInfo queryBankRespMsgMgrInfoById(int id) {
		return sysMgrDao.queryBankRespMsgMgrInfoById(id);
	}
	
	@Override
	public int updateBankRespMsgMgrInfo(BankRespMsgMgrInfo info) {
		return sysMgrDao.updateBankRespMsgMgrInfo(info);
	}
	@Override
	public PageInfo<TransInfo> queryBankRealRespMsg(Criteria criteria) {
		PageInfo<TransInfo> page = new PageInfo<TransInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(sysMgrDao.countBankRealRespMsg(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<TransInfo> list = sysMgrDao.queryBankRealRespMsg(criteria, rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public PageInfo<CheckToNoInfo> queryCheckToNoInfo(Criteria criteria) {
		PageInfo<CheckToNoInfo> page = new PageInfo<CheckToNoInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(sysMgrDao.countCheckToNoInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<CheckToNoInfo> list = sysMgrDao.queryCheckToNoInfo(criteria, rb);
		for(CheckToNoInfo li:list){
			if(li.getCheckNo()!=null&&!"".equals(li.getCheckNo())){
				String cardNo;
				try {
					cardNo = Funcs.decrypt(li.getCheckNo())+Funcs.decrypt(li.getMiddle())+Funcs.decrypt(li.getLast());
					li.setCardSixAndFour(cardNo);
//					li.setC(Funcs.decrypt(li.getC()));
//					li.setY(Funcs.decrypt(li.getY()));
//					li.setM(Funcs.decrypt(li.getM()));
				} catch (IOException e) {
					
					e.printStackTrace();
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		}
		page.setData(list);
		return page;
	}
	@Override
	public int addCheckToNoInfo(CheckToNoInfo info) {
		return sysMgrDao.addCheckToNoInfo(info);
	}
	@Override
	public int updateCheckToNoInfo(CheckToNoInfo info) {
		return sysMgrDao.updateCheckToNoInfo(info);
	}
	@Override
	public CheckToNoInfo queryCheckToInfoById(String id) {
		return sysMgrDao.queryCheckToNoInfoById(id);
	}
	@Override
	public int updateCardPayLimitByMerNoAndTerNo(CheckToNoInfo info) {
		return sysMgrDao.updateCardPayLimitByMerNoAndTerNo(info);
	}
	@Override
	public PageInfo<CardBinInfo> queryCardBinInfo(Criteria criteria) {
		PageInfo<CardBinInfo> page = new PageInfo<CardBinInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(sysMgrDao.countCardBinInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<CardBinInfo> list = sysMgrDao.queryCardBinInfo(criteria, rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public CardBinInfo queryCardBinInfoById(CardBinInfo info) {
		return sysMgrDao.queryCardBinInfoById(info);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int saveCardBinInfo(CardBinInfo info) throws APIException {
		int a = sysMgrDao.saveCardBinInfo(info);
		if(a>0){
			int count = sysMgrDao.queryCardBinInfoByBin(info);
			if(count!=1){
				throw new APIException("卡bin:"+info.getBin()+"已存在");
			}
		}else{
			throw new APIException("保存失败");
		}
		return a;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int updateCardBinInfoById(CardBinInfo info) throws APIException {
		int a = sysMgrDao.updateCardBinInfoById(info);
		if(a>0){
			int count = sysMgrDao.queryCardBinInfoByBin(info);
			if(count!=1){
				throw new APIException("卡bin:"+info.getBin()+"已存在");
			}
		}else{
			throw new APIException("修改失败");
		}
		return a;
	}
	@Override
	public void updateCardBinSHAByBin(CardBinInfo info) {
		try {
			sysMgrDao.updateCardBinSHAByBin(info.getId(),BankCardEncryp.encrypt(info.getBin()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<CardBinInfo> queryCardBinInfoByBinSHAIsNull() {
		return sysMgrDao.queryCardBinInfoByBinSHAIsNull();
	}
	
	@Override
	public CardBinInfo queryCardBinInfoByBin(String bin) {
		return sysMgrDao.queryCardBinInfoByBinNumber(bin);
	}
	@Override
	public CardBinInfo queryCardBinInfoByTradeNo(String tradeNo) {
		return sysMgrDao.queryCardBinInfoByTradeNo(tradeNo);
	}
}
