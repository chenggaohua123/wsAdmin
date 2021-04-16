package com.gateway.exchangRate.service;


import java.math.BigDecimal;
import java.util.List;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.APIException;
import com.gateway.exchangRate.model.BankSourceRateDetail;
import com.gateway.exchangRate.model.BankSourceRateInfo;
import com.gateway.exchangRate.model.CheckBankSourceRateInfo;
import com.gateway.exchangRate.model.CreateCheckBankSourceRateInfo;
import com.gateway.exchangRate.model.ExchangRateInfo;
import com.gateway.exchangRate.model.MerExchangRateInfo;


public interface ExchangRateService {
	/**
	 * 
	 *默认汇率查询 
	 */
	public PageInfo<ExchangRateInfo> queryExchangRateList(Criteria criteria);
	/**
	 * 添加汇率
	 * */
	public int addExchangRate(ExchangRateInfo exchangRateInfo);
	/**
	 * 通过组名源币种和目标币种查询汇率信息
	 * */
	public String queryExchangRate(ExchangRateInfo exchangRateInfo);
	/**
	 * 通过ID查询汇率信息
	 * */
	public ExchangRateInfo queryExchangRateInfoId(int id);
	/**
	 * 修改汇率信息
	 * */
	public int updateExchangRate(ExchangRateInfo exchangRateInfo);
	/**
	 * 查询商户定制汇率信息
	 * */
	public PageInfo<ExchangRateInfo> queryMerchantExchangRateList(Criteria criteria);
	/**
	 * 根据类型查询汇率组名
	 * */
	public List<String> queryRateGroupNamesByType(String type);
	/**
	 * 通过终端号查询商户号
	 * */
	public String queryMerNoByTerNo(String terNo);
	/**
	 * 添加商户定制汇率信息
	 * */
	public int addMerchantExchangRate(MerExchangRateInfo merExchangRateInfo);
	
	/**
	 * 通过ID查询商户定制汇率
	 * */
	public MerExchangRateInfo queryMerchantExchangRateById(String id);
	
	/**
	 * 修改商户定制汇率信息
	 * */
	public int updateMerchantExchangRate(MerExchangRateInfo merExchangRateInfo);
	/**
	 * 显示中国银行外币对人民币汇率
	 * */
	public PageInfo<BankSourceRateDetail> queryBankSourceRateList(Criteria criteria);
	
	public List<String> queryRateType();
	/**
	 * 查询银行历史汇率
	 * */
	public PageInfo<BankSourceRateDetail> queryBankSourceRateLogListById(Criteria criteria);
	/**
	 * 查询所有汇率表原始币种
	 * */
	public List<String> querySourceCurrency();
	/**
	 * 查询所有汇率表原始币种
	 * */
	public List<String> queryTargetCurrency();
	/**
	 * 生成待审核记录
	 * */
	public int createCheckBankSourceRate(CreateCheckBankSourceRateInfo info);
	/**
	 * 列表显示审核银行汇率信息
	 * */
	public PageInfo<CheckBankSourceRateInfo> queryCheckBankSourceRateInfo(Criteria criteria);
	/**
	 * 将审核通过的银行汇率更新到汇率表中
	 * */
	public int updateBankRateToExchangRate(String[] ids,String status,String updateBy);
	
	/** 保存一条带审核记录 */
	public int insertExchangRateLog(ExchangRateInfo exchangRateInfo);
	
	/** 查询审核汇率列表 */
	public PageInfo<ExchangRateInfo> queryExchangRateListLog(Criteria criteria);
	
	/** 审核记录后修改审核日志，修改汇率信息 */
	public void checkExchangRate(int[] ids, int status, String updateBy) throws APIException;
	
	public BigDecimal getBankRate(BankSourceRateInfo info);
	
	/** 汇率历史记录 */
	public List<ExchangRateInfo> gotoCheckExchangRateLog(ExchangRateInfo exchangRateInfo);
	
	/** 查询商户号绑定汇率是否重复  */
	MerExchangRateInfo queryMerExchangRateInfo(MerExchangRateInfo merExchangRateInfo);
	/**
	 * 实现：删除汇率记录
	 * @param vo 删除条件
	 * @return 删除结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	public int deleteExchangeRate(ExchangRateInfo vo);
	/**
	 * 实现：删除商户定制汇率记录
	 * @param vo 删除条件
	 * @return 删除结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	public int deleteMerchantExchangeRate(ExchangRateInfo vo);
}
