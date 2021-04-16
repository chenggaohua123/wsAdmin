package com.gateway.ratemgr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.ratemgr.model.RateInfo;

public interface RateMgrMapper {
	/**
	 * 分页查询费率信息
	 * @param criteria
	 * @return
	 */
	public List<RateInfo> getListRateInfo(Criteria criteria,RowBounds rd);
	
	/**
	 * 统计费率记录数
	 * @param criteria
	 * @return
	 */
	public int countListRateInfo(Criteria criteria);
	
	/**
	 * 添加费率
	 * @param rate
	 * @return
	 */
	public int addRateInfo(@Param("rate")RateInfo rate);
	
	/**
	 * 修改费率
	 * @param rate
	 * @return
	 */
	public int updateRateInfo(@Param("rate")RateInfo rate);
	
	
	/**
	 * 添加历史修改记录费率
	 * @param rate
	 * @return
	 */
	public int addRateLogInfo(int id);
	
	/**
	 * 根据id查询费率
	 * @param id
	 * @return
	 */
	public RateInfo queryRateInfoById(@Param("id")int id);
	
	/**
	 * 查询历史记录
	 * @param rateId
	 * @return
	 */
	public List<RateInfo> queryRateInfoLogById(@Param("rateId")int rateId);
	
	/**
	 * 验证重复数据
	 * @param rateInfo
	 * @return
	 */
	public RateInfo queryRateInfoByTerNo(@Param("rate")RateInfo rateInfo);
	
	/**
	 * 
	 * 
	 *导出费率内容
	 *
	 **/
	public List<RateInfo> exportRateInfo(Criteria criteria);
	
	/**
	 * 
	 * 
	 */
	public List<RateInfo>getCurrencyName();
	
	public List<RateInfo> queryBankInfoList();
}
