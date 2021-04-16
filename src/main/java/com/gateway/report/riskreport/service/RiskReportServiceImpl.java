package com.gateway.report.riskreport.service;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.report.riskreport.mapper.RiskReportDao;
import com.gateway.report.riskreport.model.GwRiskTransinfo;
import com.gateway.report.riskreport.model.InvesTotal;
import com.gateway.report.riskreport.model.RiskReportInfo;

@Service
public class RiskReportServiceImpl implements RiskReportService{
	
	@Autowired
	private RiskReportDao riskReportDao;

	public RiskReportDao getRiskReportDao() {
		return riskReportDao;
	}

	public void setRiskReportDao(RiskReportDao riskReportDao) {
		this.riskReportDao = riskReportDao;
	}

	@Override
	public PageInfo<RiskReportInfo> queryRiskTransLoseCount(Criteria criteria) {
		PageInfo<RiskReportInfo> page = new PageInfo<RiskReportInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(riskReportDao.countRiskTransLose(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<RiskReportInfo> list = riskReportDao.queryRiskTransLoseCount(criteria, rb);
		DecimalFormat formate = new DecimalFormat("0.00");
		int count=0;
		for(RiskReportInfo s:list){
			count+=s.getRiskCount();
		}
		for(RiskReportInfo s:list){
			s.setRiskRate(formate.format((s.getRiskCount()*1.0/count)*100)+"%");
		}
		page.setData(list);
		return page;
	}

	
	@Override
	public PageInfo<GwRiskTransinfo> queryGwRiskTransList(Criteria criteria) {
		PageInfo<GwRiskTransinfo> page = new PageInfo<GwRiskTransinfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(riskReportDao.countGwRiskTransList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<GwRiskTransinfo> list = riskReportDao.queryGwRiskTransList(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public PageInfo<InvesTotal> queryGwInvesTradeTotal(Criteria criteria) {
		PageInfo<InvesTotal> page = new PageInfo<InvesTotal>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(riskReportDao.countGwInvesTradeTotal(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<InvesTotal> list = riskReportDao.queryGwInvesTradeTotal(criteria, rb);
		DecimalFormat formate = new DecimalFormat("0.00");
		int count=0;
		if(null==criteria.getCondition().get("type")||criteria.getCondition().get("type").equals("")){
			count=riskReportDao.selectInvesCount(criteria);
		}
		for(InvesTotal i:list){
			if(null==criteria.getCondition().get("type")||criteria.getCondition().get("type").equals("")){
				i.setTotalCount(count);
				i.setInvesRate(formate.format((i.getInvesCount()*1.0/i.getTotalCount())*100)+"%");
			}else{
				i.setInvesRate(formate.format((i.getInvesCount()*1.0/i.getTotalCount())*100)+"%");
			}
		}
		page.setData(list);
		return page;
	} 
}
