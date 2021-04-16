package com.gateway.report.riskreport.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.report.riskreport.model.GwRiskTransinfo;
import com.gateway.report.riskreport.model.InvesTotal;
import com.gateway.report.riskreport.model.RiskReportInfo;
import com.gateway.report.riskreport.service.RiskReportService;

@Controller
@RequestMapping(value="/riskReport")
public class RiskReportController extends BaseController{

	@Resource
	private RiskReportService reportService;

	public RiskReportService getReportService() {
		return reportService;
	}

	public void setReportService(RiskReportService reportService) {
		this.reportService = reportService;
	}
	
	/**
	 * 风险码交易监控
	 * @return
	 */
	@RequestMapping(value="/queryRiskTransLoseCount")
	public String queryRiskTransLoseCount(){
	  PageInfo<RiskReportInfo> page=reportService.queryRiskTransLoseCount(getCriteria());
	  getRequest().setAttribute("page",page);
	  return "riskReport/riskReportTotal";	
	}
	
	
	/**
	 * 风险交易查询
	 * @return
	 */
	@RequestMapping(value="/queryGwRiskTransList")
	public String queryGwRiskTransList(){
		  PageInfo<GwRiskTransinfo> page=reportService.queryGwRiskTransList(getCriteria());
		  getRequest().setAttribute("page",page);
		  return "riskReport/riskTransList";	
	}
	
	/**
	 * 统计调查单
	 * @return
	 */
	@RequestMapping(value="/queryGwInvesTradeTotal")
	public String queryGwInvesTradeTotal(){
		PageInfo<InvesTotal> page=reportService.queryGwInvesTradeTotal(getCriteria());
		getRequest().setAttribute("page",page);
		return "riskReport/riskInvesTotal";
	}
	
}
