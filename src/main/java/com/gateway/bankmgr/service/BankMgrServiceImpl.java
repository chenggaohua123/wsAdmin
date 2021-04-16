package com.gateway.bankmgr.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gateway.bankmgr.mapper.BankMgrDao;
import com.gateway.bankmgr.model.BankConfigInfo;
import com.gateway.bankmgr.model.BankInfo;
import com.gateway.bankmgr.model.CurrencyConfigInfo;
import com.gateway.bankmgr.model.CurrencyInfo;
import com.gateway.bankmgr.model.GwPaymentPage;
import com.gateway.bankmgr.model.MasteKey;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.ServiceException;
import com.gateway.loginmgr.model.UserInfo;

@Service
public class BankMgrServiceImpl implements BankMgrService{
	
	@Autowired
	private BankMgrDao bankMgrDao;
	
	@Override
	public PageInfo<BankInfo> getBankList(Criteria criteria) {
		PageInfo<BankInfo> page = new PageInfo<BankInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(bankMgrDao.countBankList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<BankInfo> list = bankMgrDao.getBankList(criteria, rb);
		page.setData(list);
		return page;
	
	}
	public BankMgrDao getBankMgrDao() {
		return bankMgrDao;
	}
	public void setBankMgrDao(BankMgrDao bankMgrDao) {
		this.bankMgrDao = bankMgrDao;
	}
	
	@Override
	public PageInfo<CurrencyInfo> getCurrencyList(Criteria criteria) {
		PageInfo<CurrencyInfo> page = new PageInfo<CurrencyInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(bankMgrDao.countCurrencyList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<CurrencyInfo> list = bankMgrDao.getCurrencyList(criteria, rb);
		page.setData(list);
		return page;
	}
	
	
	@Override
	public int addBankInfo(BankInfo bankInfo) {
		return bankMgrDao.addBankInfo(bankInfo);
	}
	@Override
	public BankInfo queryBankInfoById(int id) {
		return bankMgrDao.queryBankInfoById(id);
	}
	@Override
	public int updateBankInfo(BankInfo bankInfo) {
		return bankMgrDao.updateBankInfo(bankInfo);
	}
	public PageInfo<CurrencyInfo> getKeyValueList(Criteria criteria){
		PageInfo<CurrencyInfo> page=new PageInfo<CurrencyInfo>(criteria.getPageNum(),criteria.getPageSize());
		//CurrencyInfo info= new CurrencyInfo();
		page.setTotal(bankMgrDao.countKeyValue(criteria));
		RowBounds rb=new RowBounds(page.getOffset(),criteria.getPageSize());
		List<CurrencyInfo> list=bankMgrDao.queryKeyValueInfo(criteria, rb);
		page.setData(list);
		return  page;
	}
	
	@Override
	@Transactional(noRollbackFor=Exception.class)
	public int addCurrencyInfo(CurrencyInfo currencyInfo){
		currencyInfo.setKeyType("TMK");
		currencyInfo.setClassName("org.jpos.security.SecureDESKey");
		int keyLength=128;
		currencyInfo.setKeyLength(keyLength);
		//组合值
		String keyAlias;
		keyAlias=currencyInfo.getMerchantNo()+currencyInfo.getTerNo()+"."+currencyInfo.getKeyType();
		
		CurrencyInfo temp=bankMgrDao.querykeyAlias(keyAlias);
		
		if(null != temp){
			
			bankMgrDao.updatekeyAlias(keyAlias);
		}else{
			currencyInfo.setKeyAlias(keyAlias);
			bankMgrDao.addKeyStore(currencyInfo);
		}
		return bankMgrDao.addCurrencyInfo(currencyInfo);
	}
	@Override
	public int addKeyStore(CurrencyInfo currencyInfo){
		return bankMgrDao.addKeyStore(currencyInfo);
	}
	@Override
	public int addCurrencyConfigInfo(CurrencyConfigInfo configInfo) {
		return bankMgrDao.addCurrencyConfigInfo(configInfo);
	}
	@Override
	public CurrencyInfo queryCurrencyInfoById(int id) {
		return bankMgrDao.queryCurrencyInfoById(id);
	}
	@Override
	public List<CurrencyConfigInfo> queryCurrencyConfig(
			int currencyId) {
		return bankMgrDao.queryCurrencyConfig(currencyId);
	}
	
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int updateCurrencyInfo(CurrencyInfo info) throws ServiceException {
		CurrencyInfo temp = bankMgrDao.queryCurrencyInfoById(info.getId());
		info.setKeyType("TMK");
		if(null == temp){
			throw new ServiceException("该商户不存在");
		}
		temp.setUcreateBy(info.getCreateBy());
		temp.setCurrencyId(info.getId());
		int i = bankMgrDao.addCurrencyInfoLog(temp);//添加历史记录
		
		String keyAlias;//组合值
		keyAlias=info.getMerchantNo()+info.getTerNo()+"."+info.getKeyType();
		info.setKeyAlias(keyAlias);
		if(null!=info.getKeyValue() && null!=info.getCheckValue()){
			bankMgrDao.updatekeyandcheck(info);
		}
		
		if(i>0){
			return bankMgrDao.updateCurrencyInfo(info);
		}else{
			return i;
		}
	}
	
	
	
	public List<CurrencyInfo> queryCurrencyListLog(int id) {
		return bankMgrDao.queryCurrencyListLog(id);
	}
	
	@Override
	public int addBankConfigInfo(BankConfigInfo bankConfigInfo) {
		return bankMgrDao.addBankConfigInfo(bankConfigInfo);
	}
	
	@Override
	public List<BankConfigInfo> queryBankConfigList(int id) {
		return bankMgrDao.queryBankConfigList(id);
	}
	@Override
	public int updateCurrencyConfigInfo(CurrencyConfigInfo configInfo) {
		return bankMgrDao.updateCurrencyConfigInfo(configInfo);
	}
	@Override
	public int deleteCurrencyConfig(int id) {
		return bankMgrDao.deleteCurrencyConfig(id);
	}
	@Override
	public CurrencyConfigInfo queryCurrencyConfigByID(int id) {
		return bankMgrDao.queryCurrencyConfigByID(id);
	}
	
	@Override
	public BankConfigInfo queryBankConfig(int id) {
		return bankMgrDao.queryBankConfig(id);
	}
	
	@Override
	public int deleteBankConfig(int id) {
		return bankMgrDao.deleteBankConfig(id);
	}
	
	
	@Override
	public int updateBankConfig(BankConfigInfo configInfo) {
		return bankMgrDao.updateBankConfig(configInfo);
	}
	
	@Override
	public PageInfo<MasteKey> queryMasteKeyList(Criteria criteria) {
		PageInfo<MasteKey> page=new PageInfo<MasteKey>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(bankMgrDao.countMasteKey(criteria));
		RowBounds rb=new RowBounds(page.getOffset(),criteria.getPageSize());
		List<MasteKey> list=bankMgrDao.queryMasteKeyList(criteria, rb);
		page.setData(list);
		return  page;
	}
	
	/**
	 * 实现：查询支付页面管理列表
	 * @param vo 查询条件
	 * @return 支付页面列表
	 * @date 2015-09-12
	 * @author YWP
	 */
	public PageInfo<GwPaymentPage> searchPaymentPage(Criteria vo) {
		PageInfo<GwPaymentPage> page = new PageInfo<GwPaymentPage>(vo.getPageNum(), vo.getPageSize());
		page.setTotal(bankMgrDao.countPaymentPage(vo));
		RowBounds rb = new RowBounds(page.getOffset(), vo.getPageSize());
		List<GwPaymentPage> list = bankMgrDao.searchPaymentPage(vo, rb);
		page.setData(list);
		return page;
	}
	/**
	 * 实现：根据支付页面ID查询支付页面信息
	 * @param vo 支付页面ID
	 * @return 支付页面信息
	 * @date 2015-09-12
	 * @author YWP
	 */
	public GwPaymentPage searchPaymentPageById(GwPaymentPage vo) {
		return bankMgrDao.searchPaymentPageById(vo);
	}
	/**
	 * 实现：保存支付页面的内容
	 * @param vo 支付页面信息
	 * @param user 当前登录用户信息
	 * @return 保存结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	public int savePaymentPage(GwPaymentPage vo, UserInfo user) {
		int a = 0;
		if(vo.getId()>0) {
			vo.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
			vo.setLastUpdatePerson(user.getUserName());
			a = bankMgrDao.updatePaymentPage(vo);
		} else {
			vo.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			vo.setCreatedPerson(user.getUserName());
			vo.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
			vo.setLastUpdatePerson(user.getUserName());
			a = bankMgrDao.addPaymentPage(vo);
		}
		return a;
	}
	/**
	 * 实现：删除支付页面
	 * @param vo 支付页面信息
	 * @return 删除结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	public int deletePaymentPage(GwPaymentPage vo) {
		return bankMgrDao.deletePaymentPage(vo);
	}
	
	@Override
	public int saveMasteKey(List<MasteKey> list) {
		return bankMgrDao.saveMasteKey(list);
	}
	
	
	@Override
	public MasteKey queryMasteKeyBySn(String tersn) {
		return bankMgrDao.queryMasteKeyBySn(tersn);
	}
	
	@Override
	public List<MasteKey> exporMasteKey(Criteria criteria) {
		return bankMgrDao.queryMasteKeyList(criteria);
	}
	@Override
	public int updateMasteKeyBySn(MasteKey key) {
		return bankMgrDao.updateMasteKeyBySn(key);
	}
	@Override
	public Map<String, String> queryCurrencyConfigByCurrencyId(int currencyId) {
		return bankMgrDao.queryCurrencyConfigByCurrencyId(currencyId);
	}
	@Override
	public List<CurrencyInfo> queryCurrencyIdByBankId(List<Integer> bankIdList,List<String> list) {
		if(list==null||list.size()==0){
			return null;
		}
		return bankMgrDao.queryCurrencyIdByBankId(bankIdList, list);
	}	

}
