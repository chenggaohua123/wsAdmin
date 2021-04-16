package com.gateway.form.service;

import java.util.List;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.form.model.CountAnalysis;
import com.gateway.form.model.MerchantReportFormsInfo;
import com.gateway.form.model.TransAmountCountInfo;
import com.gateway.form.model.TransTimeCountInfo;

public interface FormMgrService {
	/**
	 * 列表显示可以导出报表的数据信息
	 * 
	 * @param criteria
	 * @return
	 */
	PageInfo<MerchantReportFormsInfo> queryMerchantReportForms(Criteria criteria);

	public List<CountAnalysis>queryCountAnalysisInfoAll(Criteria criteria);
	
	public List<CountAnalysis> queryTransCountInfoForDay(Criteria criteria);
	
	/**
	 * 获取商户每个时间段的交易
	 * @param criteria
	 * @return
	 */
	List<TransTimeCountInfo> queryTransTimeCountInfo(Criteria criteria);
	/**
	 * 处理交易金额分布
	 * @param criteria
	 * @return
	 */
	List<TransAmountCountInfo> queryTransAmountCountInfo(Criteria criteria);
}
