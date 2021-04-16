package com.gateway.countAnalysis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
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
import com.gateway.countAnalysis.model.EuropeTransInfo;
import com.gateway.countAnalysis.model.ExportEuropeInfo;
import com.gateway.countAnalysis.model.ExportFaildTransAnalysisInfo;
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

public interface CountAnalysisDao {

	int countCountAnalysisInfo(Criteria criteria);

	List<CountAnalysis> queryCountAnalysisInfo(Criteria criteria, RowBounds rb);
	List<CountAnalysis> queryCountAnalysisInfo(Criteria criteria);
	int countCurrencyCountAnalysisInfo(Criteria criteria);

	List<CountAnalysis> queryCurrencyCountAnalysisInfo(Criteria criteria, RowBounds rb);
	List<CountAnalysis> queryCurrencyCountAnalysisInfo(Criteria criteria);
	
	public List<TotalCurrnecySuccessCount> queryCurrnecyTotalCount(Criteria criteria);
	/** 统计数据 */
	List<CountAnalysis> queryCountTransInfo(Criteria criteria);
	
	/** 网站交易统计 */
	List<CountAnalysis> queryWebSiteList(Criteria criteria);
	List<CountAnalysis> queryWebSiteList(Criteria criteria, RowBounds rb);
	public int countWebSiteList(Criteria criteria);
	
	/** 拒付统计分析 */
	List<DishonoeTotal> queryDishonoeTotalList(Criteria criteria);
	
	/** 国家分布 */
	List<CountAnalysis> queryCountryList(Criteria criteria);
	List<CountAnalysis> queryCountryList(Criteria criteria,RowBounds rb);
//	List<CountAnalysis> queryCountryListForPic(Criteria criteria);
	int countCountryList(Criteria criteria);
	
	/** 失败订单分析 */
	List<CountAnalysis> queryFailureList(Criteria criteria,RowBounds rb);
	List<CountAnalysis> queryFailureList(Criteria criteria);
	int countFailureList(Criteria criteria);
	
	/** 风险订单分析 */
	List<CountAnalysis> queryRiskList(Criteria criteria,RowBounds rb);
	List<CountAnalysis> queryRiskList(Criteria criteria);
	int countRiskList(Criteria criteria);
	
	/** 风险待处理订单分析 */
	List<CountAnalysis> queryRiskPendingList(Criteria criteria,RowBounds rb);
	List<CountAnalysis> queryRiskPendingList(Criteria criteria);
	int countRiskPendingList(Criteria criteria);
	/** 风险待处理订单分析 */
	List<RiskPaddingRateInfo> queryRiskTreat(Criteria criteria,RowBounds rb);
	/** 风险待处理订单计数 */
	int countRiskTreat(Criteria criteria);
	/**
	 * 统计拒付分析数量
	 * */
	public int countDisCountList(Criteria criteria);
	/**
	 * 拒付分析列表显示
	 * */
	List<DisCount> queryDisCountList(Criteria criteria, RowBounds rb);
	/**
	 * 拒付占比
	 * */
	List<DisPercent> queryDisPer(Criteria criteria);
	/**
	 * 投诉率列表
	 * */
	public List<CompRateInfo> queryCompRate(Criteria criteria, RowBounds rb);
	public List<CompRateInfo> queryCompRate(Criteria criteria);
	/**
	 * 统计总条数
	 * */
	public int countComRate(Criteria criteria);
	/**
	 * 投诉占比
	 * */
	public List<CompPercent> queryCompPer(Criteria criteria);
	public List<String> queryCompCounttradeNos(Criteria criteria);
	/**
	 * 风险占比
	 * */
	List<RiskPercent> queryShowRiskPerInfo(Criteria criteria);
	/**
	 * 查询要下载的风险订单号
	 * */
	public List<String> queryDownRiskInfo(Criteria criteria);
	/**
	 * 风险待处理占比
	 * */
	List<RiskPercent> queryShowRiskPendingPerInfo(Criteria criteria);
	/**
	 * 查询要下载的风险待处理订单号
	 * */
	public List<String> queryDownRiskPendingInfo(Criteria criteria);
	/**
	 * 交易跟踪统计 数量
	 * @param criteria
	 * @return
	 */
	int countTransRecord(Criteria criteria);
	/**
	 * 交易跟踪统计
	 * @param criteria
	 * @param rb
	 * @return
	 */
	List<TransRecord> queryTransRecord(Criteria criteria, RowBounds rb);
	/**
	 * 通过错误原因查询流水号信息
	 * @param criteria
	 * @return
	 */
	List<String> queryFailureInfoByRespMsg(Criteria criteria);
	List<String> queryTransRecordInfoForExport(Criteria criteria);
	
	List<CurrencyDisCount> currencyDisRate(Criteria criteria,RowBounds rb);
	List<CurrencyDisCount> currencyDisRate(Criteria criteria);
	int countCurrencyDisRate(Criteria criteria);
	/**
	 * 统计拒付原因占比
	 * @param currencyId
	 * @param countYear
	 * @param countMonth
	 * @param cardType
	 * @return
	 */
	List<DisDesc> queryCurrencyDisDesc(Criteria criteria);
	
	public List<Complaint> queryComplaintInfoList(Criteria criteria);
	/**
	 * 统计商户条数
	 * @param criteria
	 * @return
	 */
	public int countmerchantDisRate(Criteria criteria);
	/**
	 * 统计商户拒付比例
	 * @param criteria
	 * @param rb
	 * @return
	 */
	List<CurrencyDisCount> merchantDisRate(Criteria criteria, RowBounds rb);
	List<CurrencyDisCount> merchantDisRate(Criteria criteria);

	
	/**
	 * 商户交易同比计数
	 * @param criteria
	 * @return
	 */
	int countMerchantTransCountRate(Criteria criteria);
	/**
	 * 商户交易同比数据查询
	 * @param criteria
	 * @param rb
	 * @return
	 */
	List<MerchantTransCountRateInfo> queryMerchantTransCountRate(@Param("cr")Criteria criteria, RowBounds rb);

	List<MerchantTransCountRateInfo> queryMerchantTransCountRate(
			@Param("cr")Criteria criteria);

	/**
	 * 统计国家条数
	 * @param criteria
	 * @return
	 */
	int countCountryInfos(Criteria criteria);
	/**
	 * 查询国家信息
	 * @param criteria
	 * @param rb
	 * @return
	 */
	List<CountryInfo> queryCountryInfos(Criteria criteria, RowBounds rb);
	/**
	 * 查询品牌统计信息
	 * @param criteria
	 * @return
	 */
	public List<BrandCountInfo> queryBrandCountInfo(Criteria criteria);
	/**
	 * 查询订单跟踪异常原因占比
	 * @param criteria
	 * @return
	 */
	List<DisDesc> queryTransRecordDesc(Criteria criteria);
	/**
	 * 查询交易重跑分析总条数
	 * @param criteria
	 * @return
	 */
	int countTransRerunCount(Criteria criteria);
	/**
	 * 交易重跑分析
	 * @param criteria
	 * @param rb
	 * @return
	 */
	List<TransReRunCount> queryTransRerunCount(Criteria criteria, RowBounds rb);
	List<TransReRunCount> queryTransRerunCount(Criteria criteria);
	
	/**
	 * 查询订单来源信息统计
	 */
	public List<TransCountInfo> queryTransAnalysisInfoList(RowBounds rb, Criteria criteria);
	
	/**
	 * 查询订单来源信息总和
	 */
	public int queryTransAnalysisCount(Criteria criteria);
	
	/**
	 * 导出订单来源信息统计
	 */
	public List<ExportTransCountInfo> queryExportTransAnalysisInfoList(Criteria criteria);
	
	/**
	 * 查询失败原因构成信息
	 */
	public List<FaildTransAnalysisInfo> queryFaildTransAnalysisInfoList(Criteria criteria);
	
	/**
	 * 导出信息
	 */
	public List<ExportFaildTransAnalysisInfo> queryExportFaildTransAnalysisInfoList(Criteria criteria);
	
	/**
	 * 查询订单信息
	 */
	public List<TransRecordInfo> queryTransInfoList(Criteria criteria);
	/**
	 * 查询欧洲通道数据分析结果
	 */
	public List<EuropeTransInfo> queryEuropeTransInfoList(RowBounds rb, Criteria criteria);
	
	/**
	 * 查询欧洲通道数据分析结果总数
	 */
	public int queryEuropeTransInfoListCount(Criteria criteria);
	
	/**
	 * 查询导出欧洲通道数据分析
	 */
	public List<ExportEuropeInfo> queryExportEuropeTransInfoList(Criteria criteria);
	
	/**
	 * 查询导出欧洲通道数据
	 */
	public String queryEuropeChannelInfo(Criteria criteria);
	/**
	 * 交易趋势查询
	 * @param criteria
	 * @return
	 */
	List<TransHourCount> queryTransHourCount(RowBounds rb,Criteria criteria);
	List<TransHourCount> queryTransHourCount(Criteria criteria);
	
	
	/**
	 * 统计交易占比或者拒付占比条数
	 * @param criteria
	 * @return
	 */
	int countTransOrDisPercent(Criteria criteria);
	/**
	 * 查询交易占比或者拒付占比数据
	 * @param rb
	 * @param criteria
	 * @return
	 */
	List<TransOrDisPercent> queryTransDisPercent(RowBounds rb, Criteria criteria);
	List<TransOrDisPercent> queryTransDisPercent(Criteria criteria);
}
