package com.gateway.transchangemgr.service;

import java.util.List;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.ServiceException;
import com.gateway.transchangemgr.model.RefuseInfo;
import com.gateway.transchangemgr.model.TransCheckInfo;
import com.gateway.transmgr.model.TransInfo;

public interface TransChangeService {
	/**
	 * 查询上传勾兑历史记录
	 * */
	public PageInfo<TransCheckInfo> queryCheckTransInfo(Criteria criteria);
	/**
	 * 保存上传勾兑记录
	 * */
	public int saveTransCheckDetail(List<TransCheckInfo> transChecked);
	/**
	 * 通过ID查询上传勾兑记录
	 * */
	public TransCheckInfo queryCheckTransInfoById(String id);
	/**
	 * 复核勾兑记录
	 * */
	public int updateTransChecked(TransCheckInfo transCheckInfo) throws ServiceException ;
	/**
	 * 订单强制失败
	 * */
	public int updateTransFail(String tradeNos[],String name);
	
	/**
	 * 
	 * 根据订单号查询交易信息
	 * */
	public List<RefuseInfo> queryRefuseInfoByOrderNo(String orderNo);
	
	/**
	 * 
	 * 根据流水号查询交易信息
	 * */
	public List<RefuseInfo> queryRefuseInfoByTradeNo(String tradeNo);
	/**
	 * 插入重跑记录
	 * @param currencyId 
	 * */
	public int insertTransRerunInfo(String tradeNo, String currencyId);
	/**
	 * 列表显示交易重跑记录
	 * */
	public PageInfo<TransInfo> getTransList(Criteria criteria);
	/**
	 * 列表待处理支付订单
	 * */
	public PageInfo<TransInfo> getTransHighRiskRerunInfoList(Criteria criteria);
	
	/** 通过参数查询结果 */
	public List<TransInfo> queryTransInfo(Criteria criteria);
	/**
	 * 将支付状态修改为待处理状态
	 * */
	public int updateTransToPendingByTradeNo(String tradeNo);
	/**
	 * */
	public int updateTransReRunInfo(int id, String respCode, String respMsg);
	public int selectMaxIdByTradeNo(String tradeNo);
	/**
	 * 交易重跑记录流水号信息
	 * @param criteria
	 * @return
	 */
	public List<String> getTransListForTransRerunTradeNos(Criteria criteria);

}
