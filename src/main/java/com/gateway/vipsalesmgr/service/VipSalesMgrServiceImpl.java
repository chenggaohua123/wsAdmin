package com.gateway.vipsalesmgr.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.vipsalesmgr.mapper.VipSalesMgrDao;
import com.gateway.vipsalesmgr.model.VipSalesConsumeInfo;
import com.gateway.vipsalesmgr.model.VipSalesInfo;
import com.gateway.vipsalesmgr.model.VipSalesLogInfo;

@Service
public class VipSalesMgrServiceImpl implements VipSalesMgrService {

	@Autowired
	private VipSalesMgrDao vipSalesMgrDao;

	public VipSalesMgrDao getVipSalesMgrDao() {
		return vipSalesMgrDao;
	}

	public void setVipSalesMgrDao(VipSalesMgrDao vipSalesMgrDao) {
		this.vipSalesMgrDao = vipSalesMgrDao;
	}

	@Override
	public PageInfo<VipSalesInfo> queryVipSalesInfoList(Criteria criteria) {
		PageInfo<VipSalesInfo> page = new PageInfo<VipSalesInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(vipSalesMgrDao.queryVipSalesCount(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), page.getPageSize());
		List<VipSalesInfo> list = vipSalesMgrDao.queryVipSalesInfoList(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public PageInfo<VipSalesLogInfo> queryVipSalesLogInfoList(Criteria criteria) {
		PageInfo<VipSalesLogInfo> page = new PageInfo<VipSalesLogInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(vipSalesMgrDao.queryVipSalesLogCount(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), page.getPageSize());
		List<VipSalesLogInfo> list = vipSalesMgrDao.queryVipSalesLogInfoList(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public List<VipSalesInfo> queryExportVipSalesInfoList(Criteria criteria) {
		List<VipSalesInfo> list = vipSalesMgrDao.queryVipSalesInfoList(criteria);
		return list;
	}

	@Override
	public PageInfo<VipSalesConsumeInfo> queryVipSalesConsumeInfoList(
			Criteria criteria) {
		PageInfo<VipSalesConsumeInfo> page = new PageInfo<VipSalesConsumeInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(vipSalesMgrDao.queryVipSalesConsumeCount(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), page.getPageSize());
		List<VipSalesConsumeInfo> list = vipSalesMgrDao.queryVipSalesConsumeInfoList(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public List<VipSalesConsumeInfo> queryExportVipSalesConsumeInfoList(
			Criteria criteria) {
		return vipSalesMgrDao.queryVipSalesConsumeInfoList(criteria);
	}

	@Override
	public List<VipSalesLogInfo> queryVipSalesLogInfoListByUniqueID(
			String uniqueID) {
		return vipSalesMgrDao.queryVipSalesLogInfoListByUniqueID(uniqueID);
	}
	
}
