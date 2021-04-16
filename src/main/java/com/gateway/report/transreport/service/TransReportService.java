package com.gateway.report.transreport.service;


import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.report.transreport.model.DishonoeTotal;
import com.gateway.report.transreport.model.MaxAmountTotal;
import com.gateway.report.transreport.model.TransTotal;

public interface TransReportService {

	
	/**
	 * 交易统计
	 * @param total
	 * @return
	 */
	public PageInfo<TransTotal> transTotalList(Criteria criteria);
	
	/**
	 * 通道交易统计
	 * @param criteria
	 * @return
	 */
	public PageInfo<TransTotal> transCurrencyTotalList(Criteria criteria);
	
	
	/**
	 * 大额交易统计
	 * @return
	 */
	public PageInfo<MaxAmountTotal> queryMaxAmountTotal(Criteria criteria);
	
	/** 统计商户拒付率 */
	public PageInfo<DishonoeTotal> queryDishonoeInfo(Criteria criteria);
}
