package com.gateway.sellmgr.service;

import java.util.List;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
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
import com.gateway.transmgr.model.TransInfo;
import com.gateway.transmgr.model.TransRecordInfo;

public interface SellMgrService {
	/**
	 * 查询交易列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<TransInfo> getTransList(Criteria criteria);

	/**
	 * 订单明细查询
	 * */
	public TransInfo queryTransInfoByTradeNo(String tradeNo);
	/**
	 * 交易统计
	 * @param criteria
	 * @return
	 */
	public PageInfo<CountAnalysis>queryCountAnalysisInfo(Criteria criteria);
	public List<CountAnalysis>queryCountAnalysisInfoAll(Criteria criteria);
	
	/** 失败订单分析 */
	public PageInfo<CountAnalysis> queryFailureList(Criteria criteria);
	/** 失败订单分析 */
	public List<CountAnalysis> queryFailureListAll(Criteria criteria);
	
	/**
	 * 查询风控阻挡记录
	 * */
	public PageInfo<RiskTransInfo> queryRiskTransInfo(Criteria criteria);
	/**
	 * 列表显示销售员关联商户号
	 * @param criteria
	 * @return
	 */
	public PageInfo<SellRefMerNo> querySellRefMerNo(Criteria criteria);
	/**
	 * 查询所有商户号
	 * @return
	 */
	public List<String> queryAllMerNo();
	/**
	 * 查询所有用户列表
	 * @return
	 */
	public List<UserInfo> queryAllUsersInfo();
	/**
	 * 检查用户是否重复
	 * @param info
	 * @return
	 */
	public int checkUserNameDuplicate(SellRefMerNo info);

	public int addSellRefMerNo(SellRefMerNo info);

	public SellRefMerNo querySellMgrServiceById(String id);

	public int updateSellRefMerNo(SellRefMerNo info);

	public int deleteSellRefMerNoByIds(String[] ids);
	
	/**
	 * 查询订单跟踪列表
	 * */
	public PageInfo<TransRecordInfo> getTransRecordList(Criteria criteria);
	/** 查询订单跟踪列表 */
	List<TransRecordInfo> queryTransRecordInfo(Criteria criteria);
	
	/**
	 * 查询商户资金信息
	 */
	public PageInfo<MerchantCapitalInfo> queryMerchantCapitalInfoList(Criteria criteria);
	
	/**
	 * 导出商户资金信息
	 */
	public List<MerchantCapitalInfo> queryMerchantCapitalExportInfoList(Criteria criteria);
	
	/**
	 * 查询商户结算信息
	 */
	public PageInfo<MerchantSettleInfo> queryMerchantSettleInfoList(Criteria criteria);
	
	/**
	 * 导出商户结算信息
	 */
	public List<MerchantSettleInfo> queryMerchantSettleExportInfoList(Criteria criteria);
	
	public List<ExportRiskTransInfo> exportRiskTransList(Criteria criteria);
	
	/**
	 * 商户统计分析
	 */
	public PageInfo<CurrencyDisCount> queryMerchantDisCount(Criteria criteria);
	
	public List<CurrencyDisCount> queryMerchantDisCountAll(Criteria criteria);
	
	/**
	 * 统计拒付原因占比
	 */
	public List<DisDesc> queryCurrencyDisDesc(Criteria criteria);
	
	public List<Complaint> queryListComplaintInfoList(Criteria criteria);
	/**
	 * 查询销售员主从
	 * @param criteria
	 * @return
	 */
	public PageInfo<SellRefSellsInfo> querySellRefSellsInfo(Criteria criteria);
	/**
	 * 删除销售员主从
	 * @param sellMgrs
	 * @return
	 */
	public int deleteSellRefSellsInfo(String[] sellMgrs);
	/**
	 * 添加销售员主从
	 * @param info
	 * @return
	 */
	public int addSellRefSellsInfo(SellRefSellsInfo info);
	
	/**
	 * 修改销售员主从
	 * @param info
	 * @return
	 */
	public int updateSellRefSellsInfo(SellRefSellsInfo info);
	
	/**
	 * 根据主管查询销售员主从
	 * @param sellMgr
	 * @return
	 */
	public List<SellRefSellsInfo> querySellRefuSellsBySellMgr(String sellMgr);

	public PageInfo<AgentRelSellInfo> queryAgentRelSellInfo(Criteria criteria);

	public AgentRelSellInfo queryAgentRelSellInfoById(String id);

	public int updateAgentRelSellInfo(AgentRelSellInfo info);

	public int addAgentRelSellInfo(AgentRelSellInfo info);
	
}
