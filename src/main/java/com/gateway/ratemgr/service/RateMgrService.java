package com.gateway.ratemgr.service;


import java.util.List;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.ServiceException;
import com.gateway.ratemgr.model.RateInfo;

public interface RateMgrService {
	/**
	 * 分页查询费率信息
	 * @param criteria
	 * @return
	 */
	public PageInfo<RateInfo> getListRateInfo(Criteria criteria);
	
	
	/**
	 * 添加费率
	 * @param rate
	 * @return
	 */
	public int addRateInfo(RateInfo rate);
	
	
	/**
	 * 修改费率
	 * @param rate
	 * @return
	 */
	public int updateRateInfo(RateInfo rate)throws ServiceException ;
	
	
	/**
	 * 根据id查询费率
	 * @param id
	 * @return
	 */
	public RateInfo queryRateInfoById(int id);
	
	/**
	 * 查询历史记录
	 * @param rateId
	 * @return
	 */
	public List<RateInfo> queryRateInfoLogById(int rateId);
	
	/**
	 * 验证重复数据
	 * @param rateInfo
	 * @return
	 */
	public RateInfo queryRateInfoByTerNo(RateInfo rateInfo);
	
	/**
	 * 
	 *导出内容 
	 */
	public List<RateInfo> exportRateInfo(Criteria criteria);
	
	/**
	 * 查出通道名称与通道号
	 * 
	 */
	public List<RateInfo> getCurrencyName();
	
	/** 获取银行信息下拉列表 */
	public List<RateInfo> queryBankInfoList();
}
