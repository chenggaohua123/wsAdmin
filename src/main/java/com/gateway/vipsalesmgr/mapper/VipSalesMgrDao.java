package com.gateway.vipsalesmgr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.vipsalesmgr.model.VipSalesConsumeInfo;
import com.gateway.vipsalesmgr.model.VipSalesInfo;
import com.gateway.vipsalesmgr.model.VipSalesLogInfo;

public interface VipSalesMgrDao {

	/**
	 * 实现: 固定周期消费,信息查询
	 * @param criteria
	 * @return 查询结果
	 */
	public List<VipSalesInfo> queryVipSalesInfoList(Criteria criteria, RowBounds rb);
	
	/**
	 * 实现: 查询总数
	 * @param criteria
	 * @return 查询结果
	 */
	public int queryVipSalesCount(Criteria criteria);
	
	/**
	 * 实现: 查询变更历史记录总数
	 * @param uniqueID
	 * @return 查询结果
	 */
	public int queryVipSalesLogCount(Criteria criteria);
	
	/**
	 * 实现: 查询变更历史记录
	 * @param uniqueID
	 * @return 查询结果
	 */
	public List<VipSalesLogInfo> queryVipSalesLogInfoList(Criteria criteria, RowBounds rb);
	
	/**
	 * 实现: 查询变更历史记录
	 * @param uniqueID
	 * @return 查询结果
	 */
	public List<VipSalesLogInfo> queryVipSalesLogInfoListByUniqueID(@Param("uniqueID") String uniqueID);
	
	/**
	 * 实现: 固定周期消费,信息导出查询
	 * @param criteria
	 * @return 查询结果
	 */
	public List<VipSalesInfo> queryVipSalesInfoList(Criteria criteria);
	
	/**
	 * 实现: 消费查询
	 * @param criteria
	 * @return 查询结果
	 */
	public List<VipSalesConsumeInfo> queryVipSalesConsumeInfoList(Criteria criteria, RowBounds rb);
	
	/**
	 * 实现: 消费总数查询
	 * @param criteria
	 * @return 查询结果
	 */
	public int queryVipSalesConsumeCount(Criteria criteria);
	
	/**
	 * 实现: 导出消费查询
	 * @param criteria
	 * @return 查询结果
	 */
	public List<VipSalesConsumeInfo> queryVipSalesConsumeInfoList(Criteria criteria);
	
}
