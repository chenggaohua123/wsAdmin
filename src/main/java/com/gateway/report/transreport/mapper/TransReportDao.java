package com.gateway.report.transreport.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.report.transreport.model.DishonoeTotal;
import com.gateway.report.transreport.model.MaxAmountTotal;
import com.gateway.report.transreport.model.TransTotal;

public interface TransReportDao {

	/**
	 * 交易统计
	 * @param total
	 * @return
	 */
	public List<TransTotal> transTotalList(Criteria criteria,RowBounds rd);
	
	public int countTotal(Criteria criteria);
	
	/**
	 * 通道交易统计
	 * @param criteria
	 * @return
	 */
	public List<TransTotal> transCurrencyTotalList(Criteria criteria,RowBounds rd);
	
	public int countCurrency(Criteria criteria);
	
	
	/**
	 * 大额交易统计
	 * @return
	 */
	public List<MaxAmountTotal> queryMaxAmountTotal(Criteria criteria,RowBounds rd);
	
	public int countMaxAmountTotal(Criteria criteria);
	
	/** 统计商户拒付率 */
	public List<DishonoeTotal> queryDishonoeInfo(Criteria criteria,RowBounds rd);
	public int countDishonoeInfo(Criteria criteria);
}
