package com.gateway.riskmgr.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.riskmgr.mapper.RiskMgrDao;
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
import com.gateway.transmgr.mapper.TransMgrDao;
import com.gateway.transmgr.model.TransInfo;


@Service
public class RiskMgrServiceImpl implements RiskMgrService{

	@Autowired
	private RiskMgrDao riskMgrDao;
	
	@Autowired
	private TransMgrDao transMgrDao;
	
	@Override
	public PageInfo<RiskTransInfo> queryRiskTransInfo(Criteria criteria) {
		PageInfo<RiskTransInfo> page = new PageInfo<RiskTransInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(riskMgrDao.countRiskTransInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<RiskTransInfo> list = riskMgrDao.getListRiskTransInfo(criteria, rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public List<ExportRiskTransInfo> exportRiskTransList(Criteria criteria) {
		return riskMgrDao.exportRiskTransList(criteria);
	}
	
	@Override
	public PageInfo<CountryLimit> queryMerchantCountryLimit(Criteria criteria) {
		PageInfo<CountryLimit> page = new PageInfo<CountryLimit>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(riskMgrDao.countMerchantCountryLimit(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<CountryLimit> list = riskMgrDao.queryMerchantCountryLimit(criteria, rb);
		page.setData(list);
		return page;
	}
	@Override
	public PageInfo<MerchantPayCountLimit> queryMerchantPayCountLimit(
			Criteria criteria) {
		PageInfo<MerchantPayCountLimit> page = new PageInfo<MerchantPayCountLimit>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(riskMgrDao.countMerchantPayCountLimit(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<MerchantPayCountLimit> list = riskMgrDao.queryMerchantPayCountLimit(criteria, rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public MerchantPayCountLimit queryMerchantPayCountLimitById(String id) {
		return riskMgrDao.queryMerchantPayCountLimitById(id);
	}
	
	@Override
	public int updateMerchantPayCountLimitById(MerchantPayCountLimit info) {
		return riskMgrDao.updateMerchantPayCountLimit(info);
	}
	@Override
	public PageInfo<BrandLimitInfo> queryBrandLimitInfo(Criteria criteria) {
		PageInfo<BrandLimitInfo> page = new PageInfo<BrandLimitInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(riskMgrDao.countBrandLimit(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<BrandLimitInfo> list = riskMgrDao.queryBrandLimit(criteria, rb);
		page.setData(list);
		return page;
	}
	@Override
	public int addBrandLimitInfo(BrandLimitInfo info) {
		return riskMgrDao.addBrandLimitInfo(info);
	}
	@Override
	public int deleteBrandLimitInfoByIds(String[] ids) {
		return riskMgrDao.deleteBrandLimitInfoByIds(ids);
	}
	@Override
	public BrandLimitInfo queryBrandLimitInfoById(String id) {
		return riskMgrDao.queryBrandLimitInfoById(id);
	}
	@Override
	public int updateBrandLimitInfo(BrandLimitInfo info) {
		return riskMgrDao.updateBrandLimitInfo(info);
	}
	
	@Override
	public PageInfo<BankLimitInfo> queryBankLimitInfo(Criteria criteria) {
		PageInfo<BankLimitInfo> page = new PageInfo<BankLimitInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(riskMgrDao.countBankLimit(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<BankLimitInfo> list = riskMgrDao.queryBankLimit(criteria, rb);
		page.setData(list);
		return page;
	}
	@Override
	public int addBankLimitInfo(BankLimitInfo info) {
		return riskMgrDao.addBankLimitInfo(info);
	}
	@Override
	public int deleteBankLimitInfoByIds(String[] ids) {
		return riskMgrDao.deleteBankLimitInfoByIds(ids);
	}
	@Override
	public BankLimitInfo queryBankLimitInfoById(String id) {
		return riskMgrDao.queryBankLimitInfoById(id);
	}
	@Override
	public int updateBankLimitInfo(BankLimitInfo info) {
		return riskMgrDao.updateBankLimitInfo(info);
	}
	
	@Override
	public PageInfo<CountryInfo> queryCountyList(Criteria criteria) {
		PageInfo<CountryInfo> page = new PageInfo<CountryInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(riskMgrDao.countCountryList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<CountryInfo> list = riskMgrDao.queryCountryList(criteria, rb);
		page.setData(list);
		return page;
	}
	
	
	@Override
	public PageInfo<MaxmindTransInfo> getMaxMindTransList(Criteria criteria) {
		PageInfo<MaxmindTransInfo> page = new PageInfo<MaxmindTransInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(riskMgrDao.countMaxMindTransList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<MaxmindTransInfo> list = riskMgrDao.queryMaxMindTransList(criteria, rb);
		page.setData(list);
		return page;
	}
	@Override
	public MaxmindTransInfo queryMaxmindInfoByTradeNo(String tradeNo) {
		return riskMgrDao.queryMaxmindInfoByTradeNo(tradeNo);
	}
	@Override
	public List<MaxmindTransInfo> exportTransList(Criteria criteria) {
		return riskMgrDao.exportTransList(criteria);
	}
	
	@Override
	public PageInfo<MaxmindWarnInfo> getMaxmindWarnList(Criteria criteria) {
		PageInfo<MaxmindWarnInfo> page = new PageInfo<MaxmindWarnInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(riskMgrDao.countMaxmindWarnList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<MaxmindWarnInfo> list = riskMgrDao.queryMaxmindWarnList(criteria, rb);
		if(list!=null && list.size()>0){
			for(MaxmindWarnInfo warn : list){
				TransInfo trans = transMgrDao.queryGwTransInfo(warn.getTxnID());
				if(trans!=null){
					warn.setRespCode(trans.getRespCode());
					warn.setRespMsg(trans.getRespMsg());
				}
			}
		}
		page.setData(list);
		return page;
	}
	
	@Override
	public List<MaxmindWarnInfo> exportMaxmindWarnList(Criteria criteria) {
		return riskMgrDao.queryMaxmindWarnList(criteria);
	}
	
	@Override
	public PageInfo<RiskCountryInfoLog> queryRiskCountryLogInfoList(
			Criteria criteria) {
		PageInfo<RiskCountryInfoLog> pageInfo = new PageInfo<RiskCountryInfoLog>(criteria.getPageNum(), criteria.getPageSize());
		pageInfo.setTotal(riskMgrDao.queryRiskCountryLogCount(criteria));
		RowBounds rb = new RowBounds(pageInfo.getOffset(), pageInfo.getPageSize());
		List<RiskCountryInfoLog> list = riskMgrDao.queryRiskCountryLogInfo(rb, criteria);
		pageInfo.setData(list);
		return pageInfo;
	}

	@Override
	public PageInfo<ThreatMetrixResultInfo> queryThreatMetrixInfoList(
			Criteria criteria) {
		PageInfo<ThreatMetrixResultInfo> page = new PageInfo<ThreatMetrixResultInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(riskMgrDao.queryThreatMetrixInfoCount(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), page.getPageSize());
		List<ThreatMetrixResultInfo> list = riskMgrDao.queryThreatMetrixInfoList(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public List<ThreatMetrixResultInfo> queryExportThreatMetrixInfoList(
			Criteria criteria) {
		return riskMgrDao.queryThreatMetrixInfoList(criteria);
	}

	@Override
	public ThreatMetrixResultInfo queryThreatMetrixInfoById(String id) {
		return riskMgrDao.queryThreatMetrixInfoById(id);
	}
	
	@Override
	public int addMerchantPayCountLimitById(MerchantPayCountLimit info) {
		return riskMgrDao.addMerchantPayCountLimitById(info);
	}
	
	@Override
	public int deleteMerchantPayCountLimit(String id) {
		return riskMgrDao.deleteMerchantPayCountLimit(id);
	}
	@Override
	public int modifyMerchantPayCountLimitById(MerchantPayCountLimit info) {
		return riskMgrDao.modifyMerchantPayCountLimitById(info);
	}
}
