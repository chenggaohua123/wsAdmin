package com.gateway.report.transreport.web;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.report.transreport.model.CountAnalysis;
import com.gateway.report.transreport.model.DishonoeTotal;
import com.gateway.report.transreport.model.MaxAmountTotal;
import com.gateway.report.transreport.model.TransTotal;
import com.gateway.report.transreport.service.TransReportService;

@Controller
@RequestMapping(value="/transReport")
public class TransReportController extends BaseController{

	@Resource
	private TransReportService transReportService;
	
	
	public TransReportService getTransReportService() {
		return transReportService;
	}
	public void setTransReportService(TransReportService transReportService) {
		this.transReportService = transReportService;
	}


	/**
	 * 交易统计
	 * @return
	 */
	@RequestMapping(value="/transTotalList")
	public String transTotalList(){
//		PageInfo<CountAnalysis> page=transReportService.queryCountAnalysisInfo(getCriteria());
//		getRequest().setAttribute("page", page);
		return "transReport/transTotal";
	}
	

	/**
	 * 通道交易统计
	 * @return
	 */
	@RequestMapping(value="/transCurrencyTotalList")
	public String transCurrencyTotalList(){
		PageInfo<TransTotal> page=transReportService.transCurrencyTotalList(getCriteria());
		getRequest().setAttribute("page", page);
		return "transReport/transCurrencyTotal";
	}
	
	@RequestMapping(value="/queryMaxAmountTotal")
	public String queryMaxAmountTotal(){
		PageInfo<MaxAmountTotal> page=transReportService.queryMaxAmountTotal(getCriteria());
		getRequest().setAttribute("page", page);	
		return "transReport/maxTransAmountTotal";
	}
	
	/** 查询拒付交易统计 */
	@RequestMapping(value="/goListDishonorTotal")
	public String goListDishonorTotal(){
		Criteria criteria=getCriteria();
		String yearMonth = (String)criteria.getCondition().get("yearMonth");
		if(StringUtils.isEmpty(yearMonth)){
			return "transReport/listDishonorTotal";
		}
		String [] a = yearMonth.split("-");
		criteria.getCondition().put("year", a[0]);
		criteria.getCondition().put("month", a[1]);
		PageInfo<DishonoeTotal> page=transReportService.queryDishonoeInfo(criteria);
		getRequest().setAttribute("page", page);
		return "transReport/listDishonorTotal";
	}
	
}
