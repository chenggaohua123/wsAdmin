package com.gateway.vipsalesmgr.service;

import java.util.List;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.vipsalesmgr.model.VipSalesConsumeInfo;
import com.gateway.vipsalesmgr.model.VipSalesInfo;
import com.gateway.vipsalesmgr.model.VipSalesLogInfo;

public interface VipSalesMgrService {

	/**
	 * 实现: 固定周期消费,信息查询
	 * @param criteria
	 * @return
	 */
	public PageInfo<VipSalesInfo> queryVipSalesInfoList(Criteria criteria);
	
	/**
	 * 实现: 查询变更历史记录
	 * @param uniqueID
	 * @return 查询结果
	 */
	public PageInfo<VipSalesLogInfo> queryVipSalesLogInfoList(Criteria criteria);
	
	/**
	 * 实现: 固定周期消费,信息导出查询
	 * @param criteria
	 * @return
	 */
	public List<VipSalesInfo> queryExportVipSalesInfoList(Criteria criteria);
	
	/**
	 * 实现: 消费查询
	 * @param criteria
	 * @return 查询结果
	 */
	public PageInfo<VipSalesConsumeInfo> queryVipSalesConsumeInfoList(Criteria criteria);
	
	/**
	 * 实现: 导出消费查询
	 * @param criteria
	 * @return 查询结果
	 */
	public List<VipSalesConsumeInfo> queryExportVipSalesConsumeInfoList(Criteria criteria);
	
	/**
	 * 实现: 查询变更历史记录
	 * @param uniqueID
	 * @return 查询结果
	 */
	public List<VipSalesLogInfo> queryVipSalesLogInfoListByUniqueID(String uniqueID);
	
}
