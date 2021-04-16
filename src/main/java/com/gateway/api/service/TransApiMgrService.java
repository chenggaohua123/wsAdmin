package com.gateway.api.service;

import java.util.List;

import com.gateway.api.model.HasUseLimitAmountInfo;
import com.gateway.api.model.QueryTerAmountLimitInfoCondition;
import com.gateway.api.model.SettleQueryCondtion;
import com.gateway.api.model.TotalSettleCondition;
import com.gateway.api.model.TotalTransInfoCondtion;
import com.gateway.api.model.TransQueryCondition;
import com.gateway.api.model.TransRegisterInfo;
import com.gateway.common.excetion.APIException;

public interface TransApiMgrService {
	
	/**
	 * 收款功能开通
	 * @param info
	 * @return
	 */
	public TransRegisterInfo transFunctionregister(TransRegisterInfo info) throws APIException;
	
	/**
	 * 交易列表查询
	 * @param info
	 * @return
	 * @throws APIException
	 */
	public TransQueryCondition queryTransList(TransQueryCondition info) throws APIException;
	
	/**
	 * 结算列表查询
	 * @param conn
	 * @return
	 */
	public SettleQueryCondtion querySettleList(SettleQueryCondtion conn);
	
	/**
	 * 结算统计
	 * @param info
	 * @return
	 */
	public TotalSettleCondition totalSettleList(TotalSettleCondition info);
	/**
	 * 交易统计，统计已结算的交易
	 * @param info
	 * @return
	 */
	public TotalTransInfoCondtion totalTransInfoList(TotalTransInfoCondtion info);
	
	/**
	 * 查询终端限额记录
	 * @param info
	 * @return
	 */
	public QueryTerAmountLimitInfoCondition queryTerAmountLimitInfo(QueryTerAmountLimitInfoCondition info);
	
	public List<HasUseLimitAmountInfo> queryHasUseLimitAmount(
			String startDate,
			String endDate,
			String queryType,
			String merNo,
			String terNo);
	
	
}
