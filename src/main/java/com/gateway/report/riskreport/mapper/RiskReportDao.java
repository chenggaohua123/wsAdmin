package com.gateway.report.riskreport.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.report.riskreport.model.GwRiskTransinfo;
import com.gateway.report.riskreport.model.InvesTotal;
import com.gateway.report.riskreport.model.RiskReportInfo;

public interface RiskReportDao {
	
	/**
	 * 风险码交易监控
	 * @param merNo
	 * @return
	 */
	public List<RiskReportInfo>  queryRiskTransLoseCount(Criteria criteria,RowBounds rd);
	
	public int countRiskTransLose(Criteria criteria);
	

	/**
	 * 风险交易查询
	 * @param criteria
	 * @return
	 */
	public List<GwRiskTransinfo> queryGwRiskTransList(Criteria criteria,RowBounds rd);
	
	/**
	 * 查询交易列表
	 * @param criteria
	 * @return
	 */
	public int countGwRiskTransList(Criteria criteria);
	
	/**
	 * 查询调查单统计
	 * @param criteria
	 * @return
	 */
	public List<InvesTotal> queryGwInvesTradeTotal(Criteria criteria,RowBounds rd);
	
	public int countGwInvesTradeTotal(Criteria criteria);
	
	
	public int selectInvesCount(Criteria criteria);
	
	
}
