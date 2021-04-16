package com.gateway.refund.service;

import java.util.List;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.APIException;
import com.gateway.refund.model.CountTransMoney;
import com.gateway.refund.model.TransInfoLog;
import com.gateway.transmgr.model.TransInfo;

public interface TransInfoLogService {
	/** 获取已勾兑的交易变更数据 */
	public PageInfo<TransInfoLog> queryTransInfo(Criteria criteria);
	
	
	public int insertTransInfoLog(TransInfoLog transInfoLog)throws APIException;

	/** 根据订单号，交易金额添加一条交易变更信息 */
	public int insertChangeTransInfo(TransInfoLog transInfoLog) throws APIException;

	/** 获取历史记录 */
	List<TransInfoLog> queryTransInfoLogList(String tradeNo);

	/** 查询最新的交易记录 */
	public TransInfoLog queryTransInfoLog(TransInfoLog transInfoLog);
	
	public CountTransMoney countTransMoney(String tradeNo);
	
	/** 获取交易异常列表 */
	public PageInfo<TransInfoLog> selectTransInfoList(Criteria criteria);
	
	/** 导出异常交易信息列表 */
	public List<TransInfo> exportTransLogList(Criteria criteria);
	
	/** 根据订单号查询异常列表 */
	public List<TransInfo> exportTradeNoTransLogList(String tradeNo);
}
