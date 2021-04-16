package com.gateway.form.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.form.model.CountAnalysis;
import com.gateway.form.model.MerchantReportFormsInfo;
import com.gateway.form.model.TransAmountCountInfo;
import com.gateway.form.model.TransTimeCountInfo;

public interface FormMgrDao {
	/**
	 * 统计可以导出报表商户的条数
	 * @param criteria
	 * @return
	 */
	int countMerchantReportForms(Criteria criteria);
	/**
	 * 列表显示可以导出报表的商户
	 * @param criteria
	 * @param rb
	 * @return
	 */
	List<MerchantReportFormsInfo> queryMerchantReportForms(Criteria criteria,
			RowBounds rb);
	/**
	 * 商户月交易分析
	 * @param criteria
	 * @return
	 */
	List<CountAnalysis> queryCountAnalysisInfo(Criteria criteria);
	/**
	 * 商户日交易笔数
	 * @param criteria
	 * @return
	 */
	List<CountAnalysis> queryTransCountInfoForDay(Criteria criteria);
	/**
	 * 获取商户每个时间段的交易
	 * eg 00-01 01-02
	 * @param criteria
	 * @return
	 */
	List<TransTimeCountInfo> queryTransTimeCountInfo(Criteria criteria);
	/**
	 * 处理交易金额分布
	 * @param criteria
	 * @return
	 */
	TransAmountCountInfo queryTransAmountCountInfo(Criteria criteria);
	

}
