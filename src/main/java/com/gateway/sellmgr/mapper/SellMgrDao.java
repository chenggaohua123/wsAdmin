package com.gateway.sellmgr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.complaint.model.Complaint;
import com.gateway.countAnalysis.model.CountAnalysis;
import com.gateway.countAnalysis.model.CurrencyDisCount;
import com.gateway.countAnalysis.model.DisDesc;
import com.gateway.loginmgr.model.UserInfo;
import com.gateway.riskmgr.model.ExportRiskTransInfo;
import com.gateway.riskmgr.model.RiskTransInfo;
import com.gateway.sellmgr.model.AgentRelSellInfo;
import com.gateway.sellmgr.model.MerchantCapitalInfo;
import com.gateway.sellmgr.model.MerchantSettleInfo;
import com.gateway.sellmgr.model.SellRefMerNo;
import com.gateway.sellmgr.model.SellRefSellsInfo;
import com.gateway.transmgr.model.GoodsInfo;
import com.gateway.transmgr.model.TransInfo;
import com.gateway.transmgr.model.TransRecordInfo;



public interface SellMgrDao {
	/**
	 * 查询交易列表
	 * @param criteria
	 * @param rd
	 * @return
	 */
	public List<TransInfo> getTransList(@Param("cr")Criteria criteria,RowBounds rd);
	/**
	 * 查询交易列表
	 * @param criteria
	 * @return
	 */
	public int countTransList(@Param("cr")Criteria criteria);
	
	/**
	 * 查询订单明细
	 * @param tradeNo
	 * @return
	 */
	public TransInfo queryTransListByTradeNo(String tradeNo);
	
	/**
	 * 通过流水号查询货物信息
	 * @param tradeNo
	 * @return
	 */
	public List<GoodsInfo> queryGoodsInfoByTradeNo(String tradeNo);
	
	int countCountAnalysisInfo(Criteria criteria);

	List<CountAnalysis> queryCountAnalysisInfo(Criteria criteria, RowBounds rb);
	List<CountAnalysis> queryCountAnalysisInfo(Criteria criteria);
	
	/** 失败订单分析 */
	List<CountAnalysis> queryFailureList(Criteria criteria,RowBounds rb);
	List<CountAnalysis> queryFailureList(Criteria criteria);
	int countFailureList(Criteria criteria);
	
	public int countRiskTransInfo(Criteria criteria);

	public List<RiskTransInfo> getListRiskTransInfo(Criteria criteria, RowBounds rb);
	public List<RiskTransInfo> getListRiskTransInfo(Criteria criteria);
	/**
	 * 统计销售员关联商户号数量
	 * @param criteria
	 * @return
	 */
	public int countSellRefMerNo(Criteria criteria);
	/**
	 * 列表显示销售员关联商户号
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<SellRefMerNo> getSellRefMerNo(Criteria criteria, RowBounds rb);
	/**
	 * 查询所有商户号
	 * @return
	 */
	public List<String> queryAllMerNo();
	/**
	 * 查询所有用户信息
	 * @return
	 */
	public List<UserInfo> queryAllUsersInfo();
	public int checkUserNameDuplicate(SellRefMerNo info);
	/**
	 * 添加销售员关联商户
	 * @param info
	 * @return
	 */
	public int addSellRefMerNo(SellRefMerNo info);
	public SellRefMerNo querySellMgrServiceById(String id);
	public int updateSellRefMerNo(SellRefMerNo info);
	public int deleteSellRefMerNoByIds(@Param("ids")String[] ids);
	
	/**
	 * 查询订单跟踪列表
	 * */
	public List<TransRecordInfo> queryTransRecordInfo(Criteria criteria);
	public List<TransRecordInfo> queryTransRecordInfo(Criteria criteria, RowBounds rd);
	/**
	 * 统计订单条数
	 * */
	public int countTransRecordInfo(Criteria criteria);
	
	/**
	 * 查询商户资金信息总数
	 */
	public int queryMerchantCapitalInfoCount(Criteria criteria);
	
	/**
	 * 查询商户资金信息
	 */
	public List<MerchantCapitalInfo> queryMerchantCapitalinfoList(RowBounds rb, Criteria criteria);
	
	/**
	 * 查询商户资金导出信息
	 */
	public List<MerchantCapitalInfo> queryMerchantCapitalinfoList(Criteria criteria);
	
	/**
	 * 查询商户结算信息
	 */
	public List<MerchantSettleInfo> queryMerchantSettleInfoList(RowBounds rb, Criteria criteria);
	
	/**
	 * 查询商户结算信息总数
	 */
	public int queryMerchantSettleInfoCount(Criteria criteria);
	
	/**
	 * 导出商户结算信息
	 */
	public List<MerchantSettleInfo> queryMerchantSettleInfoList(Criteria criteria);
	
	public List<ExportRiskTransInfo> exportRiskTransList(Criteria criteria);
	
	/**
	 * 统计商户条数
	 */
	public int countmerchantDisRate(Criteria criteria);
	
	/**
	 * 统计商户拒付信息
	 */
	List<CurrencyDisCount> merchantDisRate(Criteria criteria, RowBounds rb);
	
	List<CurrencyDisCount> merchantDisRate(Criteria criteria);
	
	/**
	 * 统计拒付原因占比
	 */
	List<DisDesc> queryCurrencyDisDesc(Criteria criteria);
	
	public List<Complaint> queryComplaintInfoList(Criteria criteria);
	/**
	 * 查询销售员主从
	 * @param criteria
	 * @return
	 */
	public List<SellRefSellsInfo> querySellRefSellsInfo(Criteria criteria, RowBounds rb);
	public int countSellRefSellsInfo(Criteria criteria);
	
	/**
	 * 通过主管查询销售员主从信息
	 * @param sellMgr
	 * @return
	 */
	public List<SellRefSellsInfo> querySellRefuSellsBySellMgr(String sellMgr);
	
	/**
	 * 通过主管删除销售员
	 * @param sellMgr
	 * @return
	 */
	public int deleteSellRefSellsInfo(String sellMgr);
	
	/***
	 * 添加销售员主从
	 * @param info
	 * @return
	 */
	public int addSellRefSellsInfo(SellRefSellsInfo info);
	public int countAgentRelSellInfo(Criteria criteria);
	public List<AgentRelSellInfo> queryAgentRelSellInfo(Criteria criteria,
			RowBounds rb);
	public int addAgentRelSellInfo(AgentRelSellInfo info);
	public AgentRelSellInfo queryAgentRelSellInfoById(String id);
	public int updateAgentRelSellInfo(AgentRelSellInfo info);
}
