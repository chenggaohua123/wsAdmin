package com.gateway.riskmgr.service;

import java.util.List;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.riskmgr.model.BankLimitInfo;
import com.gateway.riskmgr.model.BrandLimitInfo;
import com.gateway.riskmgr.model.CountryInfo;
import com.gateway.riskmgr.model.CountryLimit;
import com.gateway.riskmgr.model.ExportRiskTransInfo;
import com.gateway.riskmgr.model.MaxmindTransInfo;
import com.gateway.riskmgr.model.MaxmindWarnInfo;
import com.gateway.riskmgr.model.MerchantPayCountLimit;
import com.gateway.riskmgr.model.RiskCountryInfoLog;
import com.gateway.riskmgr.model.RiskTransInfo;
import com.gateway.riskmgr.model.ThreatMetrixResultInfo;



public interface RiskMgrService {

	/**
	 * 查询风控阻挡记录
	 * */
	public PageInfo<RiskTransInfo> queryRiskTransInfo(Criteria criteria);
	/**
	 * 导出风控明细
	 * */
	public List<ExportRiskTransInfo> exportRiskTransList(Criteria criteria);
	
	/**
	 * 查询商户国家限制
	 * */
	public PageInfo<CountryLimit> queryMerchantCountryLimit(Criteria criteria);
	
	/**
	 * 查询商户重复支付限制
	 * */
	public PageInfo<MerchantPayCountLimit> queryMerchantPayCountLimit(Criteria criteria);
	
	/**
	 * 通过ID查询商户重复支付限制
	 * */
	public MerchantPayCountLimit queryMerchantPayCountLimitById(String id);
	
	/**
	 * 通过ID修改商户重复支付限制
	 * */
	public int updateMerchantPayCountLimitById(MerchantPayCountLimit info);
	/**
	 * 查询品牌限制
	 * @param criteria
	 * @return
	 */
	public PageInfo<BrandLimitInfo> queryBrandLimitInfo(Criteria criteria);
	/**
	 * 添加品牌限制
	 * @param info
	 * @return
	 */
	public int addBrandLimitInfo(BrandLimitInfo info);
	/**
	 * 通过Id查询品牌限制信息
	 * @param id
	 * @return
	 */
	public BrandLimitInfo queryBrandLimitInfoById(String id);
	/**
	 * 修改品牌限制信息
	 * @param info
	 * @return
	 */
	public int updateBrandLimitInfo(BrandLimitInfo info);
	/**
	 * 通过id 删除信息
	 * @param ids
	 * @return
	 */
	public int deleteBrandLimitInfoByIds(String[] ids);
	
	/**
	 * 查询银行限制
	 * @param criteria
	 * @return
	 */
	public PageInfo<BankLimitInfo> queryBankLimitInfo(Criteria criteria);
	/**
	 * 添加银行限制
	 * @param info
	 * @return
	 */
	public int addBankLimitInfo(BankLimitInfo info);
	/**
	 * 通过Id查询银行限制信息
	 * @param id
	 * @return
	 */
	public BankLimitInfo queryBankLimitInfoById(String id);
	/**
	 * 修改银行限制信息
	 * @param info
	 * @return
	 */
	public int updateBankLimitInfo(BankLimitInfo info);
	/**
	 * 通过id 删除银行限制信息
	 * @param ids
	 * @return
	 */
	public int deleteBankLimitInfoByIds(String[] ids);
	/**
	 * 查询国家列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<CountryInfo> queryCountyList(Criteria criteria);
	
	/**
	 * 获取maxmind查询信息
	 * @param criteria
	 * @return
	 */
	public PageInfo<MaxmindTransInfo> getMaxMindTransList(Criteria criteria);
	/**
	 * 通过流水号查询maxmind信息
	 * @return
	 */
	public MaxmindTransInfo queryMaxmindInfoByTradeNo(String tradeNo);
	
	public List<MaxmindTransInfo> exportTransList(Criteria criteria);
	/**
	 * 查询maxmind 警告信息
	 * @param criteria
	 * @return
	 */
	public PageInfo<MaxmindWarnInfo> getMaxmindWarnList(Criteria criteria);
	/**
	 * 导出maxmind警告信息
	 * @param criteria
	 * @return
	 */
	public List<MaxmindWarnInfo> exportMaxmindWarnList(Criteria criteria);
	
	/**
	 * 查询黑名单规则表日志
	 */
	public PageInfo<RiskCountryInfoLog> queryRiskCountryLogInfoList(Criteria criteria);
	
	/**
	 * ThreatMetrix返回信息
	 */
	public PageInfo<ThreatMetrixResultInfo> queryThreatMetrixInfoList(Criteria criteria);
	
	/**
	 * 查询ThreatMetrix返回信息详情
	 */
	public ThreatMetrixResultInfo queryThreatMetrixInfoById(String id);
	
	/**
	 * 查询导出ThreatMetrix返回信息
	 */
	public List<ThreatMetrixResultInfo> queryExportThreatMetrixInfoList(Criteria criteria);
	/**
	 * 修改商户重复支付限定
	 * @param info
	 * @return
	 */
	public int modifyMerchantPayCountLimitById(MerchantPayCountLimit info);
	/**
	 * 添加商户重复支付限定
	 * @param info
	 * @return
	 */
	public int addMerchantPayCountLimitById(MerchantPayCountLimit info);
	
	/**
	 * 商户重复支付限定删除
	 * @param id
	 * @return
	 */
	public int deleteMerchantPayCountLimit(String id);
}
