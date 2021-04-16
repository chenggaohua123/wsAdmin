package com.gateway.countAnalysis.service;

import java.util.Collection;
import java.util.List;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.complaint.model.Complaint;
import com.gateway.countAnalysis.model.BrandCountInfo;
import com.gateway.countAnalysis.model.CompPercent;
import com.gateway.countAnalysis.model.CompRateInfo;
import com.gateway.countAnalysis.model.CountAnalysis;
import com.gateway.countAnalysis.model.CountryInfo;
import com.gateway.countAnalysis.model.CurrencyDisCount;
import com.gateway.countAnalysis.model.DisCount;
import com.gateway.countAnalysis.model.DisDesc;
import com.gateway.countAnalysis.model.DisPercent;
import com.gateway.countAnalysis.model.DishonoeTotal;
import com.gateway.countAnalysis.model.EuropeChannelInfo;
import com.gateway.countAnalysis.model.EuropeTransInfo;
import com.gateway.countAnalysis.model.ExportEuropeInfo;
import com.gateway.countAnalysis.model.ExportTransCountInfo;
import com.gateway.countAnalysis.model.FaildTransAnalysisInfo;
import com.gateway.countAnalysis.model.MerchantTransCountRateInfo;
import com.gateway.countAnalysis.model.RiskPaddingRateInfo;
import com.gateway.countAnalysis.model.RiskPercent;
import com.gateway.countAnalysis.model.TotalCurrnecySuccessCount;
import com.gateway.countAnalysis.model.TransCountInfo;
import com.gateway.countAnalysis.model.TransHourCount;
import com.gateway.countAnalysis.model.TransOrDisPercent;
import com.gateway.countAnalysis.model.TransReRunCount;
import com.gateway.countAnalysis.model.TransRecord;
import com.gateway.transmgr.model.TransRecordInfo;

public interface CountAnalysisService {
	
	/** 交易统计 */
	public PageInfo<CountAnalysis>queryCountAnalysisInfo(Criteria criteria);
	public List<CountAnalysis>queryCountAnalysisInfoAll(Criteria criteria);
	public PageInfo<CountAnalysis> queryCurrencyCountAnalysisInfo(Criteria criteria);
	public List<CountAnalysis> queryCurrencyCountAnalysisInfoAll(Criteria criteria);
	/** 统计数据 */
	public List<CountAnalysis> queryCountTransInfo(Criteria c);
	/** 统计数据画表格 */
	public PageInfo<CountAnalysis> queryCountTransInfoForTable(Criteria c);

	/** 网站交易统计 */
	public PageInfo<CountAnalysis> queryWebSiteList(Criteria criteria);
	public List<CountAnalysis> queryWebSiteListInfo(Criteria criteria);
	
	/** 拒付分析统计 */
	public PageInfo<DishonoeTotal> queryDishonoeTotalList(Criteria criteria);
	
	/** 国家交易分布 */
	public PageInfo<CountAnalysis> queryCountryList(Criteria criteria);
	public List<CountAnalysis> queryCountryListInfo(Criteria criteria);
	
	/** 失败订单分析 */
	public PageInfo<CountAnalysis> queryFailureList(Criteria criteria);
	/** 失败订单分析 */
	public List<CountAnalysis> queryFailureListAll(Criteria criteria);
	
	/** 风险订单分析 */
	public PageInfo<CountAnalysis> queryRiskList(Criteria criteria);
	/** 风险待处理订单分析 */
	public PageInfo<CountAnalysis> queryRiskPendingList(Criteria criteria);
	/** 风险待处理订单分析 */
	public PageInfo<RiskPaddingRateInfo> queryRiskTreat(Criteria criteria);
	/**
	 * 拒付统计分析
	 * */
	public PageInfo<DisCount> queryDishonoeCountList(Criteria criteria);
	/**
	 * 拒付占比
	 * */
	public List<DisPercent> queryDisPer(Criteria criteria);
	/**
	 * 查询投诉率
	 * */
	public PageInfo<CompRateInfo> queryCompRateInfo(Criteria criteria);
	/**
	 * 投诉占比
	 * */
	public List<CompPercent> queryCompPercent(Criteria criteria);
	/**
	 * 风险占比
	 * */
	public List<RiskPercent> queryShowRiskPerInfo(Criteria criteria);
	/**
	 * 风险待处理占比
	 * */
	public List<RiskPercent> queryShowRiskPendingPerInfo(Criteria criteria);
	/**
	 * 查询要导出的风险订单
	 * */
	public List<String> queryDownRiskInfo(Criteria criteria);
	/**
	 * 查询要导出的风险订单
	 * */
	public List<String> queryDownRiskPendingInfo(Criteria criteria);
	/**
	 * 查询订单跟踪占比
	 * @param criteria
	 * @return
	 */
	public PageInfo<TransRecord> queryTransRecordInfo(Criteria criteria);
	/**
	 * 通过错误信息查询符合要求的流水号
	 * @param criteria
	 * @return
	 */
	public List<String> queryFailureInfoByRespMsg(Criteria criteria);
	List<TotalCurrnecySuccessCount> queryCurrnecyTotalCount(Criteria criteria);
	
	
	public List<String> queryTransRecordInfoForExport(Criteria criteria);
	
	PageInfo<CurrencyDisCount> queryCurrencyDisCount(Criteria criteria);
	/**
	 * 导出渠道拒付分析
	 * @param criteria
	 * @return
	 */
	public List<CurrencyDisCount> exportCurrencyDisCount(Criteria criteria);
	/**
	 * 统计拒付原因占比
	 * @param currencyId
	 * @param countYear
	 * @param countMonth
	 * @param cardType
	 * @return
	 */
	public List<DisDesc> queryCurrencyDisDesc(Criteria criteria);
	
	public List<Complaint> queryListComplaintInfoList(Criteria criteria);
	/**
	 * 商户统计分析
	 * @param criteria
	 * @return
	 */
	public PageInfo<CurrencyDisCount> queryMerchantDisCount(Criteria criteria);
	public List<CurrencyDisCount> queryMerchantDisCountAll(Criteria criteria);
	/**
	 * 商户交易同比分析
	 * @param criteria
	 * @return
	 */
	public PageInfo<MerchantTransCountRateInfo> queryMerchantTransCountRate(Criteria criteria);
	
	public List<MerchantTransCountRateInfo> queryMerchantTransCountRateAll(
			Criteria criteria);
	/**
	 * 获取国家列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<CountryInfo> queryCountryInfos(Criteria criteria);
	/**
	 * 查询品牌交易列表
	 * @param criteria
	 * @return
	 */
	public Collection<BrandCountInfo> queryBrandCountInfo(Criteria criteria);
	/**
	 * 查询投诉订单流水号
	 * @param criteria
	 * @return
	 */
	List<String> queryCompCounttradeNos(Criteria criteria);
	/**
	 * 查询订单跟踪异常占比
	 * @param criteria
	 * @return
	 */
	public List<DisDesc> queryTransRecordDesc(Criteria criteria);
	/**
	 * 交易重跑分析
	 * @param criteria
	 * @return
	 */
	public PageInfo<TransReRunCount> queryTransRerunCountList(Criteria criteria);
	/**
	 * 导出交易重跑分析
	 * @param criteria
	 * @return
	 */
	public List<TransReRunCount> queryTransRerunCountListAll(Criteria criteria);
	
	/**
	 * 查询订单来源信息统计
	 */
	public PageInfo<TransCountInfo> queryTransAnalysisInfoList(Criteria criteria);
	
	/**
	 * 导出订单来源信息统计
	 */
	public List<ExportTransCountInfo> queryExportTransAnalysisInfoList(Criteria criteria);
	
	/**
	 * 查询失败原因构成信息
	 */
	public List<FaildTransAnalysisInfo> queryFaildTransAnalysisInfoList(Criteria criteria);
	
	/**
	 * 查询订单信息
	 */
	public List<TransRecordInfo> queryTransInfoList(Criteria criteria);
	
	/**
	 * 查询欧洲通道数据分析
	 */
	public PageInfo<EuropeTransInfo> queryEuropeTransInfoList(Criteria criteria);
	
	/**
	 * 查询导出欧洲通道数据分析
	 */
	public List<ExportEuropeInfo> queryExportEuropeTransInfoList(Criteria criteria);
	
	/**
	 * 查询信息
	 */
	public List<EuropeChannelInfo> queryEuropeChannelInfoList(Criteria criteria);
	/**
	 * 查询交易时间趋势分布
	 * @param criteria
	 * @return
	 */
	public List<TransHourCount> queryTransHourCount(Criteria criteria);
	
	
	/**
	 * 查询拒付或者交易占比趋势
	 * @param criteria
	 * @return
	 */
	public PageInfo<TransOrDisPercent> queryTransDisPercent(Criteria criteria);
	public List<TransOrDisPercent> queryTransOrDisPercentList(Criteria criteria);
}
