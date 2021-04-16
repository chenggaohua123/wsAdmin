package com.gateway.refund.service;

import java.util.List;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.APIException;
import com.gateway.refund.model.BankRefundInfo;
import com.gateway.refund.model.CountTransMoney;
import com.gateway.refund.model.RefundInfo;
import com.gateway.refund.model.RefundModel;
import com.gateway.refund.model.TransInfoLog;

public interface RefundService {
	/**
	 * 查询退款信息
	 * */
	public PageInfo<RefundInfo> queryRefundInfo(Criteria criteria);
	/**
	 * 通过ID查询退款信息
	 * */
	public RefundInfo queryRefundInfoById(String id);
	/**
	 * 处理退款
	 * @return respStatus
	 * */
	public RefundModel doRefund(RefundInfo refundInfo)throws APIException;
	
	/** 保存一条成功的退款交易记录 */
	public int insertRefundTransInfo(RefundInfo  refundInfo) throws APIException;
	
	/** 添加退款记录 */
	int insertTransInfoLog(TransInfoLog log) throws APIException;
	
	/**
	 * 根据ID更新退款记录状态
	 * */
	public int updateRefundInfoById(RefundInfo refundInfo) throws APIException;
	
	/** 检测退款信息是否能添加 return null:数据通过检测 */
	public String checkRefundInfo(RefundInfo info);
	
	/** 统计交易操作统计  */
	CountTransMoney countTransMoney(String tradeNo);
	
	/**  */
	public int addRefundInfo(RefundInfo info)throws APIException;
	
	/** 查询导出退款信息 */
	public List<RefundInfo> uploadRefundFile(Criteria criteria);
	
	/**
	 * 获取银行退款记录
	 * @param criteria
	 * @return
	 */
	public PageInfo<BankRefundInfo> queryBankRefundInfo(Criteria criteria);
	/**
	 * 
	 * 导出银行退款信息
	 * @param criteria
	 * @return
	 */
	public List<BankRefundInfo> exportBankRefundInfo(Criteria criteria);
	
}
