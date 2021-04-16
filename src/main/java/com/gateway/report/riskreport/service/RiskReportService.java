package com.gateway.report.riskreport.service;


import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.report.riskreport.model.GwRiskTransinfo;
import com.gateway.report.riskreport.model.InvesTotal;
import com.gateway.report.riskreport.model.RiskReportInfo;

public interface RiskReportService {
	/**
	 * 风险码交易监控
	 * @param merNo
	 * @return
	 */
	public PageInfo<RiskReportInfo>  queryRiskTransLoseCount(Criteria criteria);
	
	/**
	 * 风险交易查询
	 * @param criteria
	 * @return
	 */
	public PageInfo<GwRiskTransinfo> queryGwRiskTransList(Criteria criteria);
	
	
	/**
	 * 查询调查单统计
	 * @param criteria
	 * @return
	 */
	public PageInfo<InvesTotal> queryGwInvesTradeTotal(Criteria criteria);
}
