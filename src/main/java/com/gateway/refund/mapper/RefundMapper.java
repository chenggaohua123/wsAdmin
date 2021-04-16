package com.gateway.refund.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.refund.model.BankRefundInfo;
import com.gateway.refund.model.CountTransMoney;
import com.gateway.refund.model.ExceptionFee;
import com.gateway.refund.model.RefundInfo;
import com.gateway.refund.model.RefundModel;
import com.gateway.transmgr.model.TransInfo;

public interface RefundMapper {
	
	
	/**
	 * 统计退款条数 
	 * */
	public int countRefundInfo(Criteria criteria);
	
	/**
	 * 查询退款信息
	 * */
	public List<RefundInfo> queryRefundInfo(Criteria criteria);

	public List<RefundInfo> queryRefundInfo(Criteria criteria, RowBounds rb);
	
	/**
	 * 通过Id查询退款信息
	 * */
	public RefundInfo queryRefundInfoById(String id);
	
	/**
	 * 通过交易流水号获取商户shaKey
	 * @return shaKey
	 * */
	public String getMerchantShaKeyByTradeNo(String tradeNo);
	
	/**
	 * 根据ID更新退款记录状态
	 * */
	public int updateRefundInfoById(@Param("info")RefundInfo refundInfo);
	
	/**
	 * 根据ID更新退款异步通知状态
	 * */
	public int updateRefundNotifyStatusById(@Param("info")RefundInfo refundInfo);
	
	public int insertRefundInfo(@Param("info")RefundInfo refundInfo);
	/** 统计交易操作统计  */
	public CountTransMoney countTransMoney(String tradeNo);

	/** 获取交易订单信息  */
	public TransInfo qeuryTrasnInfo(String tradeNo);

	/** 添加一个交易日志  */
	public int addRefundTransInfoLog(RefundInfo info);

	/** 获取当前添加的日志ID  */
	public int selectTransInfoLog();

	/** 添加一个退款信息  */
	public int addRefundInfo(RefundInfo info);
	/**
	 * 更新退款状态
	 * */
	public int updateTransLogInfoById(@Param("id")int id,@Param("refundStatus")String refundStatus);
	/**
	 * 查询异常结算条件
	 * */
	public ExceptionFee queryExceptionFeeInfoByTradeNo(String tradeNo);
	/**
	 * 
	 * */
	public int updateTransInfoByLogId(@Param("id")int transLogId,@Param("info")RefundModel info);
	/**
	 * 统计银行退款记录条数
	 * @param criteria
	 * @return
	 */
	public int countBankRefundInfo(Criteria criteria);
	/**
	 * 列表显示银行退款记录
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<BankRefundInfo> queryBankRefundInfo(Criteria criteria,
			RowBounds rb);
	public List<BankRefundInfo> queryBankRefundInfo(Criteria criteria);
	/**
	 * 判断订单是否存在
	 * @param tradeNo
	 * @return
	 */
	public int queryTransInfoByTradeNo(String tradeNo);
	/**
	 * 插入银行退款信息
	 * @param brInfo
	 * @return
	 */
	public int insertBankRefundInfo(BankRefundInfo brInfo);
	/**
	 *  修改银行退款
	 * @param brInfo
	 * @return
	 */
	public int updateBankRefundInfo(BankRefundInfo brInfo);
	/**
	 * 通过流水号查询银行退款信息
	 * */
	public BankRefundInfo queryBankRefundInfoById(String id);
}
