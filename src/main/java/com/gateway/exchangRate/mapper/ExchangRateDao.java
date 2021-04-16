package com.gateway.exchangRate.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.exchangRate.model.BankSourceRateDetail;
import com.gateway.exchangRate.model.BankSourceRateInfo;
import com.gateway.exchangRate.model.CheckBankSourceRateInfo;
import com.gateway.exchangRate.model.CreateCheckBankSourceRateInfo;
import com.gateway.exchangRate.model.ExchangRateInfo;
import com.gateway.exchangRate.model.MerExchangRateInfo;

public interface ExchangRateDao {
	/**
	 * 统计汇率记录条数
	 * */
	public int countExchangRateTotal(Criteria criteria);
	
	/**
	 * 查询汇率列表
	 * */
	public List<ExchangRateInfo> queryExchangRateList(Criteria criteria, RowBounds rb);
	
	public List<ExchangRateInfo> queryExchangRateList(Criteria criteria);
	
	/**
	 * 添加汇率
	 * */
	public int addExchangRate(@Param("exchangRateInfo")ExchangRateInfo exchangRateInfo);
	
	/**
	 * 通过组名源币种和目标币种查询汇率信息
	 * */
	public ExchangRateInfo queryExchangRate(@Param("info")ExchangRateInfo exchangRateInfo);
	
	/**
	 * 通过ID查询汇率信息
	 * */
	public ExchangRateInfo queryExchangRateInfoId(int id);
	
	/**
	 * 修改汇率信息
	 * */
	public int updateExchangRate(ExchangRateInfo exchangRateInfo);
	
	/**
	 * 统计商户定制汇率条数
	 * */
	public int countMerchantExchangRateTotal(Criteria criteria);
	/**
	 * 查询商户定制汇率信息
	 * */
	public List<ExchangRateInfo> queryMerchantExchangRateList(Criteria criteria, RowBounds rb);
	
	public List<ExchangRateInfo> queryMerchantExchangRateList(Criteria criteria);
	
	/**
	 * 根据类型查询汇率组名
	 * */
	public List<String> queryRateGroupNamesByType(@Param("type")String type);
	
	/**
	 * 根据终端号查询商户号
	 * */
	public String queryMerNoByTerNo(String terNo);
	
	/**
	 * 添加商户汇率定制信息
	 * */
	public int addMerchantExchangRate(@Param("info")MerExchangRateInfo merExchangRateInfo);

	/**
	 * 通过ID查找商户定制汇率信息
	 * */
	public MerExchangRateInfo queryMerchantExchangRateById(String id);

	/**
	 * 修改商户定制汇率信息
	 * */
	public int updateMerchantExchangRate(@Param("info")MerExchangRateInfo merExchangRateInfo);
	/**
	 * 显示中国银行外币对人民币汇率数量
	 * */
	public int countBankSourceRateInfoTotal(Criteria criteria);
	/**
	 * 显示中国银行外币对人民币汇率
	 * */
	public List<BankSourceRateDetail> queryBankSourceRateInfoList(Criteria criteria, RowBounds rb);
	public List<BankSourceRateDetail> queryBankSourceRateInfoList(Criteria criteria);
	/**
	 * 查询中行汇率类型
	 * */
	public List<String> queryRateType();
	/**
	 * 统计银行汇率历史记录条数
	 * */
	public int countBankSourceRateLogInfoTotal(Criteria criteria);
	/**
	 * 查询银行汇率历史记录
	 * */
	public List<BankSourceRateDetail> queryBankSourceRateLogInfoList(Criteria criteria, RowBounds rb);
	public List<BankSourceRateDetail> queryBankSourceRateLogInfoList(Criteria criteria);
	/**
	 * 查询所有汇率表原始币种
	 * */
	public List<String> querySourceCurrency();
	/**
	 * 查询汇率表
	 * */
	public List<String> querytargetCurrency();
	/**
	 * 生成银行汇率待审核记录
	 * */
	public int createCheckBankSourceRate(@Param("info")CreateCheckBankSourceRateInfo info);
	/**
	 * 统计银行汇率审核记录条数
	 * */
	public int countCheckBankSourceRateInfoTotal(Criteria criteria);
	/**
	 * 查询银行汇率审核记录
	 * */
	public List<CheckBankSourceRateInfo> queryCheckBankSourceRateInfoList(Criteria criteria, RowBounds rb);
	public List<CheckBankSourceRateInfo> queryCheckBankSourceRateInfoList(Criteria criteria);
	/**
	 * 通过id数组查询要审核的银行汇率信息
	 * */
	public List<CheckBankSourceRateInfo> queryCheckBankSourceRateInfoByIds(@Param("ids")String[] ids);
	/**
	 * 更新要审核的银行汇率信息
	 * */
	public int updateCheckBankSourceRateInfo(@Param("ids")String[] ids, @Param("status")String status,@Param("updateBy")String updateBy);
	/**
	 * 将银行汇率信息更新到汇率表中
	 * */
	public int updateBankRateToExchangRate(@Param("info")CheckBankSourceRateInfo info);
	
	public int insertExchangRateLog(@Param("info")ExchangRateInfo exchangRateInfo);

	/** 获取审核记录总条数 */
	public int countExchangRateTotalLog(Criteria criteria);

	/** 获取审核记录 */
	public List<ExchangRateInfo> queryExchangRateLogList(Criteria criteria,RowBounds rb);
	
	/** 获取历史详情 */
	public List<ExchangRateInfo> gotoCheckExchangRateLog(ExchangRateInfo exchangRateInfo);

	/** 通过ID查询 */
	public ExchangRateInfo queryExchangRateLogId(@Param("id")int id);
	
	public int updateCheckExchangRateLog(@Param("info")ExchangRateInfo exchangRateInfo);
	
	public ExchangRateInfo queryExchangRateLog(@Param("info")ExchangRateInfo exchangRateInfo);
	
	/** 获取银行汇率 */
	public BigDecimal getBankRate(@Param("info")BankSourceRateInfo info);
	public BigDecimal getBankRateCNY(@Param("info")BankSourceRateInfo info);
	
	/** 获取添加的汇率ID */
	public int getAddRateInfoId();

	/** 修改汇率与log关联ID */
	public int updateCheckExchangRateLogRateId(@Param("info")ExchangRateInfo infos);
	
	/** 查询商户号绑定汇率是否重复 */
	public MerExchangRateInfo queryMerExchangRateInfo(@Param("info")MerExchangRateInfo merExchangRateInfo);
	/**
	 * 实现：删除汇率记录
	 * @param vo 删除条件
	 * @return 删除结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	public int deleteExchangeRate(@Param("vo")ExchangRateInfo vo);
	/**
	 * 实现：删除商户定制汇率记录
	 * @param vo 删除条件
	 * @return 删除结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	public int deleteMerchantExchangeRate(@Param("vo")ExchangRateInfo vo);
}
