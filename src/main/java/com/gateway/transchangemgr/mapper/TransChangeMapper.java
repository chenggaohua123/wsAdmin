package com.gateway.transchangemgr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.transchangemgr.model.RefuseInfo;
import com.gateway.transchangemgr.model.TransCheckInfo;
import com.gateway.transmgr.model.TransInfo;

public interface TransChangeMapper {
	/**
	 * 统计上传勾兑总条数
	 * */
	public int countTransCheckInfo(Criteria criteria);
	/**
	 * 查询上传勾兑记录
	 * */
	public List<TransCheckInfo> queryCheckTransInfo(Criteria criteria);
	public List<TransCheckInfo> queryCheckTransInfo(Criteria criteria,RowBounds rb);
	/**
	 * 保存上传勾兑记录
	 * */
	public int saveTransCheckInfo(TransCheckInfo transCheckInfo);
	/**
	 * 通过流水号查询上传勾兑记录
	 * */
	public TransCheckInfo queryCheckTransInfoByTradeNo(String tradeNo);
	/**
	 * 更新勾兑异常单的数据为上传数据并将勾兑状态修改为未勾兑
	 * */
	public int updateTransExpCheckInfo(@Param("info")TransCheckInfo transCheckInfo);
	
	/**
	 * 通过ID查询上传勾兑记录
	 * */
	public TransCheckInfo queryCheckTransInfoById(String id);
	/**
	 * 通过流水号修改交易勾兑状态成功以及支付状态成功
	 * */
	public int updateTransInfoByTradeNo(@Param("info")TransCheckInfo transCheckInfo);
	/**
	 * 订单强制失败
	 * */
	public int updateTransFail(String tradeNo);
	/**
	 * 
	 *根据订单查询交易信息
	 */
	public List<RefuseInfo>queryRefuseInfoByOrderNo(@Param("orderNo")String orderNo);
	/**
	 * 
	 *根据流水号查询交易信息 
	 */
	public List<RefuseInfo>queryRefuseInfoByTradeNo(@Param("tradeNo")String tradeNo);
	/**
	 * 插入重跑记录
	 * */
	public int insertTransRerunInfo(@Param("tradeNo")String tradeNo,@Param("currencyId")String currencyId);
	/***
	 * 统计交易重跑数量
	 * */
	public int countTransList(@Param("cr")Criteria criteria);
	/**
	 * 列表显示交易重跑记录
	 * */
	public List<TransInfo> getTransList(@Param("cr")Criteria criteria, RowBounds rb);
	
	/***
	 * 统计交易待支付的数量
	 * */
	public int countTransHighRiskRerunInfoList(@Param("cr")Criteria criteria);
	/***
	 * 交易待支付列表
	 * */
	public List<TransInfo> getTransHighRiskRerunInfoList(@Param("cr")Criteria criteria,
			RowBounds rb);
	
	/** 查询交易表 */
	public List<TransInfo> queryTransInfo(Criteria criteria);
	
	/**根据流水号查询是否入账状态*/
	public TransCheckInfo queryTransAccess(@Param("tradeNo")String tradeNo);
	/**
	 * 将支付状态修改为待处理状态
	 * */
	public int updateTransToPendingByTradeNo(String tradeNo);
	
	public int updateTransReRunInfo(@Param("id")int id, @Param("respCode")String respCode, @Param("respMsg")String respMsg);
	public int selectMaxIdByTradeNo(String tradeNo);
	public List<String> getTransListForTransRerunTradeNos(@Param("cr")Criteria criteria);
}
