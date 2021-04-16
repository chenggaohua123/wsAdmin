package com.gateway.riskmgr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
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


public interface RiskMgrDao {

	public int countRiskTransInfo(Criteria criteria);

	public List<RiskTransInfo> getListRiskTransInfo(Criteria criteria, RowBounds rb);
	/**
	 * 导出风险订单明细
	 * */
	public List<ExportRiskTransInfo> exportRiskTransList(Criteria criteria);
	/**
	 * 查询商户国家限制
	 * */
	public List<CountryLimit> queryMerchantCountryLimit(Criteria criteria, RowBounds rb);
	/**
	 * 统计商户国家限制条数
	 * */
	public int countMerchantCountryLimit(Criteria criteria);
	
	/**
	 * 查询商户重复支付限制
	 * */
	public List<MerchantPayCountLimit> queryMerchantPayCountLimit(Criteria criteria, RowBounds rb);
	/**
	 * 统计商户重复支付限制条数
	 * */
	public int countMerchantPayCountLimit(Criteria criteria);
	/**
	 * 通过Id查询商户重复支付限制 
	 * */
	public MerchantPayCountLimit queryMerchantPayCountLimitById(String  id);
	/**
	 * 通过ID修改商户重复支付限制 
	 * */
	public int updateMerchantPayCountLimit(MerchantPayCountLimit info);
	/**
	 * 统计品牌限制条数
	 * @param criteria
	 * @return
	 */
	public int countBrandLimit(Criteria criteria);
	/**
	 * 查询品牌限制信息
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<BrandLimitInfo> queryBrandLimit(Criteria criteria, RowBounds rb);
	/**
	 * 添加品牌限制
	 * @param info
	 * @return
	 */
	public int addBrandLimitInfo(BrandLimitInfo info);
	/**
	 * 删除品牌限制
	 * @param ids
	 * @return
	 */
	public int deleteBrandLimitInfoByIds(@Param("ids")String[] ids);
	/**
	 * 通过Id查询银行限制
	 * @param id
	 * @return
	 */
	public BrandLimitInfo queryBrandLimitInfoById(String id);
	/**
	 * 修改品牌限制
	 * @param info
	 * @return
	 */
	public int updateBrandLimitInfo(BrandLimitInfo info);
	
	/**
	 * 统计银行限制条数
	 * @param criteria
	 * @return
	 */
	public int countBankLimit(Criteria criteria);
	
	/**
	 * 查询银行限制信息
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<BankLimitInfo> queryBankLimit(Criteria criteria, RowBounds rb);
	/**
	 * 添加银行限制
	 * @param info
	 * @return
	 */
	public int addBankLimitInfo(BankLimitInfo info);
	/**
	 * 删除银行限制
	 * @param ids
	 * @return
	 */
	public int deleteBankLimitInfoByIds(@Param("ids")String[] ids);
	/**
	 * 通过Id查询银行限制
	 * @param id
	 * @return
	 */
	public BankLimitInfo queryBankLimitInfoById(String id);
	/**
	 * 修改品牌限制
	 * @param info
	 * @return
	 */
	public int updateBankLimitInfo(BankLimitInfo info);
	
	/**
	 * 查询国家列表
	 */
	public List<CountryInfo> queryCountryList(Criteria criteria ,RowBounds rb);
	/**
	 * 统计国家条数
	 * @param criteria
	 * @return
	 */
	public int countCountryList(Criteria criteria);
	/**
	 * 统计maxmind交易条数
	 * @param criteria
	 * @return
	 */
	public int countMaxMindTransList(@Param("cr")Criteria criteria);
	/**
	 * 查询maxmind交易信息
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<MaxmindTransInfo> queryMaxMindTransList(@Param("cr")Criteria criteria,
			RowBounds rb);
	/**
	 * maxmind信息查询
	 * @param tradeNo
	 * @return
	 */
	public MaxmindTransInfo queryMaxmindInfoByTradeNo(String tradeNo);

	public List<MaxmindTransInfo> exportTransList(@Param("cr")Criteria criteria);
	/**
	 * 统计maxmind警告条数
	 * @param criteria
	 * @return
	 */
	public int countMaxmindWarnList(Criteria criteria);
	/**
	 * 查询maxmind 警告信息
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<MaxmindWarnInfo> queryMaxmindWarnList(Criteria criteria,
			RowBounds rb);
	public List<MaxmindWarnInfo> queryMaxmindWarnList(Criteria criteria);
	
	/**
	 * 查询黑名单规则表日志
	 */
	public List<RiskCountryInfoLog> queryRiskCountryLogInfo(RowBounds rb, Criteria criteria);
	
	/**
	 *  查询黑名单规则表日志总数
	 */
	public int queryRiskCountryLogCount(Criteria criteria);
	
	/**
	 * ThreatMetrix返回信息
	 */
	public List<ThreatMetrixResultInfo> queryThreatMetrixInfoList(Criteria criteria, RowBounds rb);
	
	/**
	 * ThreatMetrix总数
	 */
	public int queryThreatMetrixInfoCount(Criteria criteria);
	
	/**
	 * 导出ThreatMetrix返回信息
	 */
	public List<ThreatMetrixResultInfo> queryThreatMetrixInfoList(Criteria criteria);
	
	/**
	 * 查询ThreatMetrix返回信息详情
	 */
	public ThreatMetrixResultInfo queryThreatMetrixInfoById(@Param("id") String id);

	public int addMerchantPayCountLimitById(MerchantPayCountLimit info);

	public int deleteMerchantPayCountLimit(String id);

	public int modifyMerchantPayCountLimitById(MerchantPayCountLimit info);
}