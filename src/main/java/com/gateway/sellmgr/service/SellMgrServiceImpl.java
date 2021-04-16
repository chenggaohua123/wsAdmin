package com.gateway.sellmgr.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.utils.Funcs;
import com.gateway.common.utils.Tools;
import com.gateway.complaint.model.Complaint;
import com.gateway.countAnalysis.model.CountAnalysis;
import com.gateway.countAnalysis.model.CurrencyDisCount;
import com.gateway.countAnalysis.model.DisDesc;
import com.gateway.loginmgr.model.UserInfo;
import com.gateway.riskmgr.model.ExportRiskTransInfo;
import com.gateway.riskmgr.model.RiskTransInfo;
import com.gateway.sellmgr.mapper.SellMgrDao;
import com.gateway.sellmgr.model.AgentRelSellInfo;
import com.gateway.sellmgr.model.MerchantCapitalInfo;
import com.gateway.sellmgr.model.MerchantSettleInfo;
import com.gateway.sellmgr.model.SellRefMerNo;
import com.gateway.sellmgr.model.SellRefSellsInfo;
import com.gateway.transmgr.model.TransInfo;
import com.gateway.transmgr.model.TransRecordInfo;
@Service
public class SellMgrServiceImpl implements SellMgrService {
	@Autowired
	private SellMgrDao sellMgrDao;
	@Override
	public PageInfo<TransInfo> getTransList(Criteria criteria) {
		PageInfo<TransInfo> page = new PageInfo<TransInfo>(criteria.getPageNum(), criteria.getPageSize());
		
		page.setTotal(sellMgrDao.countTransList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		
		List<TransInfo> list = sellMgrDao.getTransList(criteria, rb);
		for(TransInfo li:list){
			li.setWebInfo(Tools.parseWebInfoToResourceType(li.getWebInfo()));
			if(li.getCheckNo()!=null&&!"".equals(li.getCheckNo())){
				String cardNo;
				try {
					cardNo = Funcs.decrypt(li.getCheckNo())+"****"+Funcs.decrypt(li.getLast());
					li.setSixAndFourCardNo(cardNo);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		page.setData(list);
		return page;
	}
	
	@Override
	public TransInfo queryTransInfoByTradeNo(String tradeNo) {
		TransInfo info=sellMgrDao.queryTransListByTradeNo(tradeNo);
		info.setGoodsInfos(sellMgrDao.queryGoodsInfoByTradeNo(tradeNo));
		return info; 
	}
	
	@Override
	public PageInfo<CountAnalysis> queryCountAnalysisInfo(Criteria criteria) {
		PageInfo<CountAnalysis> page=new PageInfo<CountAnalysis>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(sellMgrDao.countCountAnalysisInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <CountAnalysis> list=sellMgrDao.queryCountAnalysisInfo(criteria,rb);
		page.setData(list);
		return page;
	}
	public List<CountAnalysis> queryCountAnalysisInfoAll(Criteria criteria) {
		List <CountAnalysis> list=sellMgrDao.queryCountAnalysisInfo(criteria);
		return list;
	}
	@Override
	public PageInfo<CountAnalysis> queryFailureList(Criteria criteria) {
		PageInfo<CountAnalysis> page=new PageInfo<CountAnalysis>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(sellMgrDao.countFailureList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <CountAnalysis> list=sellMgrDao.queryFailureList(criteria,rb);
		page.setData(list);
		return page;
	}
	/** 失败订单分析 */
	public List<CountAnalysis> queryFailureListAll(Criteria criteria){
		List <CountAnalysis> list=sellMgrDao.queryFailureList(criteria);
		return list;
	}
	@Override
	public PageInfo<RiskTransInfo> queryRiskTransInfo(Criteria criteria) {
		PageInfo<RiskTransInfo> page = new PageInfo<RiskTransInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(sellMgrDao.countRiskTransInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<RiskTransInfo> list = sellMgrDao.getListRiskTransInfo(criteria, rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public List<ExportRiskTransInfo> exportRiskTransList(Criteria criteria) {
		return sellMgrDao.exportRiskTransList(criteria);
	}
	
	@Override
	public PageInfo<SellRefMerNo> querySellRefMerNo(Criteria criteria) {
		PageInfo<SellRefMerNo> page = new PageInfo<SellRefMerNo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(sellMgrDao.countSellRefMerNo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<SellRefMerNo> list = sellMgrDao.getSellRefMerNo(criteria, rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public List<String> queryAllMerNo() {
		return sellMgrDao.queryAllMerNo();
	}
	@Override
	public List<UserInfo> queryAllUsersInfo() {
		return sellMgrDao.queryAllUsersInfo();
	}
	
	@Override
	public int checkUserNameDuplicate(SellRefMerNo info) {
		return sellMgrDao.checkUserNameDuplicate(info);
	}
	
	@Override
	public int addSellRefMerNo(SellRefMerNo info) {
		return sellMgrDao.addSellRefMerNo(info);
	}
	@Override
	public SellRefMerNo querySellMgrServiceById(String id) {
		return sellMgrDao.querySellMgrServiceById(id);
	}
	
	@Override
	public int updateSellRefMerNo(SellRefMerNo info) {
		return sellMgrDao.updateSellRefMerNo(info);
	}
	@Override
	public int deleteSellRefMerNoByIds(String[] ids) {
		return sellMgrDao.deleteSellRefMerNoByIds(ids);
	}
	
	@Override
	public PageInfo<TransRecordInfo> getTransRecordList(Criteria criteria) {
		PageInfo<TransRecordInfo> page=new PageInfo<TransRecordInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(sellMgrDao.countTransRecordInfo(criteria));
		RowBounds rb=new RowBounds(page.getOffset(), page.getPageSize());
		List<TransRecordInfo> list=sellMgrDao.queryTransRecordInfo(criteria,rb);
		page.setData(list);
		return page;
	}
	@Override
	public List<TransRecordInfo> queryTransRecordInfo(Criteria criteria){
		return sellMgrDao.queryTransRecordInfo(criteria);
	}
	
	@Override
	public PageInfo<MerchantCapitalInfo> queryMerchantCapitalInfoList(
			Criteria criteria) {
		PageInfo<MerchantCapitalInfo> pageInfo = new PageInfo<MerchantCapitalInfo>(criteria.getPageNum(), criteria.getPageSize());
		pageInfo.setTotal(sellMgrDao.queryMerchantCapitalInfoCount(criteria));
		RowBounds rb = new RowBounds(pageInfo.getOffset(), pageInfo.getPageSize());
		List<MerchantCapitalInfo> list = sellMgrDao.queryMerchantCapitalinfoList(rb, criteria);
		pageInfo.setData(list);
		return pageInfo;
	}

	@Override
	public List<MerchantCapitalInfo> queryMerchantCapitalExportInfoList(
			Criteria criteria) {
		return sellMgrDao.queryMerchantCapitalinfoList(criteria);
	}

	@Override
	public PageInfo<MerchantSettleInfo> queryMerchantSettleInfoList(
			Criteria criteria) {
		PageInfo<MerchantSettleInfo> pageInfo = new PageInfo<MerchantSettleInfo>(criteria.getPageNum(), criteria.getPageSize());
		pageInfo.setTotal(sellMgrDao.queryMerchantSettleInfoCount(criteria));
		RowBounds rb = new RowBounds(pageInfo.getOffset(), pageInfo.getPageSize());
		List<MerchantSettleInfo> list = sellMgrDao.queryMerchantSettleInfoList(rb, criteria);
		pageInfo.setData(list);
		return pageInfo;
	}

	@Override
	public List<MerchantSettleInfo> queryMerchantSettleExportInfoList(
			Criteria criteria) {
		return sellMgrDao.queryMerchantSettleInfoList(criteria);
	}
	
	@Override
	public PageInfo<CurrencyDisCount> queryMerchantDisCount(Criteria criteria) {
		PageInfo<CurrencyDisCount> page=new PageInfo<CurrencyDisCount>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(sellMgrDao.countmerchantDisRate(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <CurrencyDisCount> list=sellMgrDao.merchantDisRate(criteria,rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public List<CurrencyDisCount> queryMerchantDisCountAll(Criteria criteria) {
		return sellMgrDao.merchantDisRate(criteria);
	}
	
	@Override
	public List<DisDesc> queryCurrencyDisDesc(Criteria criteria) {
		List<DisDesc> list=sellMgrDao.queryCurrencyDisDesc(criteria);
		int total=0;
		for(DisDesc info:list){
			total+=info.getDisCount();
		}
		List<DisDesc> list1=new ArrayList<DisDesc>();
		for(DisDesc info:list){
			double rate=0;
			if(total!=0){
				rate=info.getDisCount()*1.0/total;
			}
			info.setDisRate(rate);
			list1.add(info);
		}
		return list1;
	}
	
	@Override
	public List<Complaint> queryListComplaintInfoList(Criteria criteria) {
		return sellMgrDao.queryComplaintInfoList(criteria);
	}
	
	@Override
	public PageInfo<SellRefSellsInfo> querySellRefSellsInfo(Criteria criteria) {
		PageInfo<SellRefSellsInfo> page=new PageInfo<SellRefSellsInfo>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(sellMgrDao.countSellRefSellsInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <SellRefSellsInfo> list=sellMgrDao.querySellRefSellsInfo(criteria,rb);
		page.setData(list);
		return page;
	}
	@Override
	public int addSellRefSellsInfo(SellRefSellsInfo info) {
		int count=0;
		if(null!=info.getSells()){
			for(String sell:info.getSells()){
				info.setSell(sell);
				count+=sellMgrDao.addSellRefSellsInfo(info);
			}
		}
		return count;
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public int updateSellRefSellsInfo(SellRefSellsInfo info) {
		int count=0;
		if(null!=info.getSells()){
			//先删
			sellMgrDao.deleteSellRefSellsInfo(info.getSellMgr());
			//后增
			for(String sell:info.getSells()){
				info.setSell(sell);
				count+=sellMgrDao.addSellRefSellsInfo(info);
			}
		}
		return count;
	}
	
	@Override
	public int deleteSellRefSellsInfo(String[] sellMgrs) {
		int count=0;
		for(String sellMgr:sellMgrs){
			count+=sellMgrDao.deleteSellRefSellsInfo(sellMgr);
		}
		return count;
	}
	@Override
	public List<SellRefSellsInfo> querySellRefuSellsBySellMgr(String sellMgr) {
		return sellMgrDao.querySellRefuSellsBySellMgr(sellMgr);
	}
	
	@Override
	public PageInfo<AgentRelSellInfo> queryAgentRelSellInfo(Criteria criteria) {
		PageInfo<AgentRelSellInfo> page=new PageInfo<AgentRelSellInfo>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(sellMgrDao.countAgentRelSellInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <AgentRelSellInfo> list=sellMgrDao.queryAgentRelSellInfo(criteria,rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public int addAgentRelSellInfo(AgentRelSellInfo info) {
		return sellMgrDao.addAgentRelSellInfo(info);
	}
	@Override
	public AgentRelSellInfo queryAgentRelSellInfoById(String id) {
		return sellMgrDao.queryAgentRelSellInfoById(id);
	}
	@Override
	public int updateAgentRelSellInfo(AgentRelSellInfo info) {
		return sellMgrDao.updateAgentRelSellInfo(info);
	}
	
}
