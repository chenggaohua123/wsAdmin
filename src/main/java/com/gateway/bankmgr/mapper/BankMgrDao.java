package com.gateway.bankmgr.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.bankmgr.model.BankConfigInfo;
import com.gateway.bankmgr.model.BankInfo;
import com.gateway.bankmgr.model.CurrencyConfigInfo;
import com.gateway.bankmgr.model.CurrencyInfo;
import com.gateway.bankmgr.model.GwPaymentPage;
import com.gateway.bankmgr.model.MasteKey;
import com.gateway.common.adapter.web.model.Criteria;

public interface BankMgrDao {
	/**
	 * 查询银行列表
	 * @param criteria
	 * @param rd
	 * @return
	 */
	public List<BankInfo> getBankList(Criteria criteria,RowBounds rd);
	public List<BankInfo> getBankList();
	
	/**
	 * 统计银行列表记录数
	 * @param criteria
	 * @return
	 */
	public int countBankList(Criteria criteria);
	
	/**
	 * 查询通道列表
	 * @param criteria
	 * @param rd
	 * @return
	 */
	public List<CurrencyInfo> getCurrencyList(Criteria criteria ,RowBounds rd);
	public List<CurrencyInfo> getCurrencyList();
	
	/**
	 * 统计渠道记录数
	 * @param criteria
	 * @return
	 */
	public int countCurrencyList(Criteria criteria);
	
	/**
	 * 添加银行信息
	 * @param bankInfo
	 * @return
	 */
	public int addBankInfo(@Param("bank")BankInfo bankInfo);
	

	/**
	 * 按id查询银行信息
	 * @param id
	 * @return
	 */
	public BankInfo queryBankInfoById(@Param("id")int id);
	
	/**
	 * 修改银行信息
	 * @param bankInfo
	 * @return
	 */
	public int updateBankInfo(@Param("bankInfo")BankInfo bankInfo);
	
	/**
	 * 添加通道信息
	 * @param currencyInfo
	 * @return
	 */
	public int addCurrencyInfo(@Param("info")CurrencyInfo currencyInfo);
	
	/**
	 * 添加通道信息key_store
	 * 
	 */
	public int addKeyStore(@Param("key")CurrencyInfo currencyInfo);
	
	/**
	 * 
	 *根据id查询key_store中是否存在keyAlias组合值 
	 * 
	 * 
	 */
	 public CurrencyInfo querykeyAlias(@Param("Alias")String keyAlias);
	 
	 /**
	  * 
	  * 更新keyAlias
	  * 
	  */
	 public int updatekeyAlias(@Param("info")String keyAlias);
	 /**
	  * 
	  * 更新keyvalue,checkvalue
	  * 
	  */
	 public int updatekeyandcheck(@Param("keyandcheck") CurrencyInfo info);
	 
	 /**
	  * 密钥查询
	  */
	 public List<CurrencyInfo> queryKeyValueInfo( Criteria criteria ,RowBounds rd );
	/**
	 *统计KeyValue记录数
	 */
	 public int countKeyValue(Criteria criteria);
	 
	/**
	 * 添加通道配置信息
	 * @param configInfo
	 * @return
	 */
	 
	
	public int addCurrencyConfigInfo(@Param("info")CurrencyConfigInfo configInfo);
	
	/**
	 * 
	 * 按id查询通道
	 */
	public CurrencyInfo queryCurrencyInfoById(@Param("id")int id);
	
	
	/**
	 * 查询配置信息
	 * @param configInfo
	 * @return
	 */
	public List<CurrencyConfigInfo> queryCurrencyConfig(@Param("currencyId")int currencyId);
	
	/**
	 * 修改通道配置
	 * @param info
	 * @return
	 */
	public int updateCurrencyInfo(@Param("info")CurrencyInfo info);
	
	/**
	 * 修改通道历史记录
	 * @param info
	 * @return
	 */
	public int addCurrencyInfoLog(@Param("info")CurrencyInfo info);
	
	/**
	 * 查询通道修改历史记录
	 * @param id
	 * @return
	 */
	public List<CurrencyInfo> queryCurrencyListLog(@Param("id")int id);
	
	/**
	 * 添加银行配置
	 * @param bankConfigInfo
	 * @return
	 */
	public int addBankConfigInfo(@Param("bank")BankConfigInfo bankConfigInfo);
	
	
	/**
	 * 查询银行配置
	 * @param id
	 * @return
	 */
	public List<BankConfigInfo> queryBankConfigList(@Param("bankId")int id);
	
	
	/**
	 * 修改配置
	 * @param configInfo
	 * @return
	 */
	public int updateCurrencyConfigInfo(@Param("info")CurrencyConfigInfo configInfo);
	
	/**
	 * 删除配置信息
	 * @param id
	 * @return
	 */
	public int deleteCurrencyConfig(@Param("id")int id);
	
	/**
	 * 根据id查询配置
	 * @param id
	 * @return
	 */
	public CurrencyConfigInfo queryCurrencyConfigByID(@Param("id")int id);
	
	/**
	 * 根据id查询银行配置
	 * @param id
	 * @return
	 */
	public BankConfigInfo queryBankConfig(@Param("id")int id);
	
	
	/**
	 * 删除银行配置
	 * @param id
	 * @return
	 */
	public int deleteBankConfig(@Param("id")int id);
	
	/** 
	 * 修改银行配置
	 * @param configInfo
	 * @return
	 */
	public int updateBankConfig(@Param("bank")BankConfigInfo configInfo);
	
	/**
	 * 查询主密钥
	 * @param criteria
	 * @param rd
	 * @return
	 */
	public List<MasteKey> queryMasteKeyList(Criteria criteria ,RowBounds rd );
	
	public int countMasteKey(Criteria criteria);
	
	/**
	 * 导出住密钥
	 * @param criteria
	 * @return
	 */
	public List<MasteKey> queryMasteKeyList(Criteria criteria);
	
	/**
	 * 添加主密钥
	 * @param list
	 * @return
	 */
	public int saveMasteKey(@Param("list")List<MasteKey> list);
	
	/**
	 * 根据sn号查询信息
	 * @param tersn
	 * @return
	 */
	public MasteKey queryMasteKeyBySn(@Param("tersn")String tersn);
	
	/**
	 * 更新主密钥
	 * @param key
	 * @return
	 */
	public int updateMasteKeyBySn(@Param("key") MasteKey key);
	
	/**
	 * 实现：查询支付页面管理列表
	 * @param vo 查询条件
	 * @return 支付页面列表
	 * @date 2015-09-12
	 * @author YWP
	 */
	public List<GwPaymentPage> searchPaymentPage(Criteria criteria ,RowBounds rd);
	public int countPaymentPage(Criteria criteria);
	/**
	 * 实现：根据支付页面ID查询支付页面信息
	 * @param vo 支付页面ID
	 * @return 支付页面信息
	 * @date 2015-09-12
	 * @author YWP
	 */
	public GwPaymentPage searchPaymentPageById(@Param("vo")GwPaymentPage vo);
	/**
	 * 实现：保存新增支付页面的内容
	 * @param vo 支付页面信息
	 * @return 保存结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	public int addPaymentPage(@Param("vo")GwPaymentPage vo);
	/**
	 * 实现：保存修改支付页面的内容
	 * @param vo 支付页面信息
	 * @return 保存结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	public int updatePaymentPage(@Param("vo")GwPaymentPage vo);
	/**
	 * 实现：删除支付页面
	 * @param vo 支付页面信息
	 * @return 删除结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	public int deletePaymentPage(@Param("vo")GwPaymentPage vo);
	/**
	 * 实现：通过通道id查银行商户号和密钥
	 * @param currencyId
	 * @return
	 */
	public Map<String,String> queryCurrencyConfigByCurrencyId(@Param("currencyId")int currencyId);
	/**
	 * 实现：通过银行id查和通道名称查通道
	 * @param bankId
	 * @return
	 */
	public List<CurrencyInfo> queryCurrencyIdByBankId(@Param("bankIdList")List<Integer> bankIdList,@Param("list")List<String> list);
}
