package com.gateway.refund.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.refund.model.CountTransMoney;
import com.gateway.refund.model.TransInfoLog;
import com.gateway.transmgr.model.TransInfo;

public interface TransInfoLogMapper {
	/** 获取已勾兑的交易变更数据 */
	public List<TransInfoLog> queryToTransInfo(Criteria criteria, RowBounds rd);
	
	/**
	 * 统计获取已勾兑的交易变更数据条数 
	 * */
	public int countTransInfo(Criteria criteria);
	
	/** 获取最新操作记录 */
	public TransInfoLog queryTransInfoLog(@Param("transInfoLog") TransInfoLog transInfoLog);
	
	/** 获取最新操作记录列表 */
	public List<TransInfoLog> queryTransInfoLogList(@Param("tradeNo") String tradeNo);
	
	/** 保存 */
	public int insertTransInfoLog(TransInfoLog transInfoLog);
	
	/** 修改交易记录 */
	public int updateTransInfoChange(TransInfoLog transInfoLog);

	public TransInfoLog queryTransInfoLogInfo(@Param("tradeNo") String transNo);
	
	/** 修改数据 */
	public int updateTransInfoLog(TransInfoLog transInfoLog);

	public TransInfoLog queryTransInfoLogId(@Param("id")int id);
	
	/** 统计交易操作统计 */
	public CountTransMoney countTransMoney(@Param("tradeNo")String tradeNo);
	
	/** 获取交易异常单数量 */
	public int countTransInfoLogList(Criteria criteria);
	
	/** 获取交易异常列表 */
	public List<TransInfoLog> selectTransInfoLogList(Criteria criteria, RowBounds rd);
	public List<TransInfoLog> selectTransInfoLogList(Criteria criteria);

	/** 分页导出异常交易信息 */
	public List<TransInfo> exportTransLogList(Criteria criteria);
	
	/** 通过流水号查询异常交易信息 */
	public List<TransInfo> exportTradeNoTransLogList(@Param("tradeNo") String tradeNo);
}
