package com.gateway.bankmgr.service;

import java.util.List;
import java.util.Map;

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

public interface BankMgrService {
	/**
	 * 分页查询银行列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<BankInfo> getBankList(Criteria criteria);
	
	/**
	 * 分页查询通道列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<CurrencyInfo> getCurrencyList(Criteria criteria);
	
	/**
	 * 
	 * 查询密钥
	 * 
	 * 
	 */
	public PageInfo<CurrencyInfo> getKeyValueList(Criteria criteria);
	/**
	 * 添加银行信息
	 * @param bankInfo
	 * @return
	 */
	public int addBankInfo(BankInfo bankInfo);
	
	/**
	 * 按id查询银行
	 * @param id
	 * @return
	 */
	public BankInfo queryBankInfoById(int id);
	
	/**
	 * 修改银行信息
	 * @param bankInfo
	 * @return
	 */
	public int updateBankInfo(BankInfo bankInfo);
	
	/**
	 * 添加通道信息
	 * @param currencyInfo
	 * @return
	 */
	public int addCurrencyInfo(CurrencyInfo currencyInfo);
	
	/**
	 * 添加通道信息key_store
	 * 
	 */
	 public int addKeyStore(CurrencyInfo currencyInfo)throws ServiceException;
	 
	 
	
	/**
	 * 添加通道配置信息
	 * @param configInfo
	 * @return
	 */
	public int addCurrencyConfigInfo(CurrencyConfigInfo configInfo);
	
	
	/**
	 * 
	 * 按id查询通道
	 */
	public CurrencyInfo queryCurrencyInfoById(int id);
	
	
	/**
	 * 查询配置信息
	 * @param configInfo
	 * @return
	 */
	public List<CurrencyConfigInfo> queryCurrencyConfig(int currencyId);
	
	
	/**
	 * 修改通道配置
	 * @param info
	 * @return
	 */
	public int updateCurrencyInfo(CurrencyInfo info) throws ServiceException;
	
	
	/**
	 * 查询通道修改历史记录
	 * @param id
	 * @return
	 */
	public List<CurrencyInfo> queryCurrencyListLog(int id);
	
	/**
	 * 添加银行配置
	 * @param bankConfigInfo
	 * @return
	 */
	public int addBankConfigInfo(BankConfigInfo bankConfigInfo);
	
	
	/**
	 * 查询银行配置
	 * @param id
	 * @return
	 */
	public List<BankConfigInfo> queryBankConfigList(int id);
	
	
	/**
	 * 修改配置
	 * @param configInfo
	 * @return
	 */
	public int updateCurrencyConfigInfo(CurrencyConfigInfo configInfo);
	
	/**
	 * 删除配置信息
	 * @param id
	 * @return
	 */
	public int deleteCurrencyConfig(int id);
	
	/**
	 * 根据id查询配置
	 * @param id
	 * @return
	 */
	public CurrencyConfigInfo queryCurrencyConfigByID(int id);
	
	/**
	 * 根据id查询银行配置
	 * @param id
	 * @return
	 */
	public BankConfigInfo queryBankConfig(int id);
	
	
	/**
	 * 删除银行配置
	 * @param id
	 * @return
	 */
	public int deleteBankConfig(int id);
	
	/**
	 * 修改银行配置
	 * @param configInfo
	 * @return
	 */
	public int updateBankConfig(BankConfigInfo configInfo);
	
	
	/**
	 * 查询主密钥
	 * @param criteria
	 * @return
	 */
	public PageInfo<MasteKey> queryMasteKeyList(Criteria criteria);
	
	
	/**
	 * 添加主密钥
	 * @param list
	 * @return
	 */
	public int saveMasteKey(List<MasteKey> list);
	
	/**
	 * 根据sn号查询信息
	 * @param tersn
	 * @return
	 */
	public MasteKey queryMasteKeyBySn(String tersn);
	
	/**
	 * 导出主密钥
	 * @param criteria
	 * @return
	 */
	public List<MasteKey> exporMasteKey(Criteria criteria);
	
	/**
	 * 更新主密钥
	 * @param key
	 * @return
	 */
	public int updateMasteKeyBySn(MasteKey key);
	/**
	 * 实现：查询支付页面管理列表
	 * @param vo 查询条件
	 * @return 支付页面列表
	 * @date 2015-09-12
	 * @author YWP
	 */
	public PageInfo<GwPaymentPage> searchPaymentPage(Criteria vo);
	/**
	 * 实现：根据支付页面ID查询支付页面信息
	 * @param vo 支付页面ID
	 * @return 支付页面信息
	 * @date 2015-09-12
	 * @author YWP
	 */
	public GwPaymentPage searchPaymentPageById(GwPaymentPage vo);
	/**
	 * 实现：保存支付页面的内容
	 * @param vo 支付页面信息
	 * @param user 当前登录用户信息
	 * @return 保存结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	public int savePaymentPage(GwPaymentPage vo, UserInfo user);
	/**
	 * 实现：删除支付页面
	 * @param vo 支付页面信息
	 * @return 删除结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	public int deletePaymentPage(GwPaymentPage vo);
	/**
	 * 实现：通过通道id查银行商户号和密钥
	 * @param currencyId
	 * @return
	 * @date 2019-12-19
	 * @author cgh
	 */
	public Map<String,String> queryCurrencyConfigByCurrencyId(int currencyId);
	/**
	 * 实现：通过银行id查和通道名称查通道
	 * @param bankId
	 * @return
	 * @date 2019-12-19
	 * @author cgh
	 */
	public List<CurrencyInfo> queryCurrencyIdByBankId(List<Integer> bankIdList,List<String> list);
}
