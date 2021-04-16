package com.gateway.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.api.model.BusInfo;
import com.gateway.api.model.CurrencyInfo;
import com.gateway.api.model.HasUseLimitAmountInfo;
import com.gateway.api.model.MerchantInfo;
import com.gateway.api.model.PicInfo;
import com.gateway.api.model.QueryTerAmountLimitInfoCondition;
import com.gateway.api.model.SettleInfo;
import com.gateway.api.model.SettleQueryCondtion;
import com.gateway.api.model.TerAmountLimitInfo;
import com.gateway.api.model.TerInfo;
import com.gateway.api.model.TerInfoRelCurrencyInfo;
import com.gateway.api.model.TotalSettleCondition;
import com.gateway.api.model.TotalTransInfo;
import com.gateway.api.model.TotalTransInfoCondtion;
import com.gateway.api.model.TransInfo;
import com.gateway.api.model.TransQueryCondition;
import com.gateway.api.model.UserRelMerchantInfo;

public interface TransApiMgrDao {
	/**
	 * 添加商户信息
	 * @return
	 */
	public int addMerchantInfo(@Param("info") MerchantInfo info);
	
	/**
	 * 添加终端信息
	 * @return
	 */
	public int addMerchantTerInfo(@Param("info") TerInfo info);
	
	/**
	 * 插入商户的营业信息
	 * @param busInfo
	 * @return
	 */
	public int addBusInfo(@Param("info") BusInfo busInfo,@Param("phoneNo")String phoneNo);
	
	/**
	 * 添加图片信息
	 * @param info
	 * @return
	 */
	public int addPicInfo(@Param("info") PicInfo info);
	
	/**
	 * 绑定通道
	 * @return
	 */
	public int addCurrencyToTerInfo(@Param("info") TerInfoRelCurrencyInfo info);
	
	/**
	 * 查询默认绑定通道
	 * @return
	 */
	public CurrencyInfo queryCurrencyByDefaultCurrency();
	
	/**
	 * 增加用户关联商户号信息
	 * @param merNo
	 * @param phoneNo
	 * @return
	 */
	public int addUserToMerchnatRelInfo(@Param("merNo") String merNo,@Param("phoneNo") String phoneNo);
	
	/**
	 * 添加商户号关联代理记录
	 * @param merNo
	 * @param agentNo
	 * @return
	 */
	public int addMerchantToAgent(@Param("merNo") String merNo,@Param("terNo") String terNo,@Param("agentNo") String agentNo);
	
	/**
	 * 查询商户关联的代理商户号
	 * @param merNo
	 * @return
	 */
	public String queryAgentNoByMerNo(@Param("merNo") String merNo,@Param("terNo") String terNo);
	
	/**
	 * 根据SNNo查询绑定的终端号
	 * @param snNo
	 * @return
	 */
	public TerInfo queryTerInfoBySnNo(@Param("snNo") String snNo);
	
	/**
	 * 根据SNNo查询绑定的终端号
	 * @param snNo
	 * @return
	 */
	public String queryAgentInfoBySnNo(@Param("snNo") String snNo);
	
	/**
	 * 查询用户关商户信息
	 * @param phoneNp
	 * @return
	 */
	public UserRelMerchantInfo queryUserRelMerchantInfoByPhoneNo(@Param("phoneNo") String phoneNo);
	
	/**
	 * 更新用户状态
	 * @param phoneNo
	 * @return
	 */
	public int updateUserStatusByPhoneNo(@Param("phoneNo") String phoneNo,@Param("status") int status);
	
	/**
	 * 交易列表查询
	 * @param info
	 * @return
	 */
	public List<TransInfo> queryTransList(@Param("conn") TransQueryCondition info,RowBounds rd);
	/**
	 * 交易列表查询
	 * @param info
	 * @return
	 */
	public int countTransList(@Param("conn") TransQueryCondition info);
	
	/**
	 * 结算列表查询
	 * @param conn
	 * @return
	 */
	public List<SettleInfo> querySettleList(@Param("conn") SettleQueryCondtion conn ,RowBounds rd);
	/**
	 * 计算结算记录数
	 * @param conn
	 * @return
	 */
	public int countSettleList(@Param("conn") SettleQueryCondtion conn);
	
	
	/**
	 * 结算统计
	 * @param info
	 * @return
	 */
	public List<SettleInfo> totalSettleList(@Param("conn") TotalSettleCondition info ,RowBounds rd);
	
	/**
	 * 结算统计
	 * @param info
	 * @return
	 */
	public int countTotalSettleList(@Param("conn") TotalSettleCondition info);
	
	/**
	 * 已结算交易统计
	 * @param info
	 * @return
	 */
	public List<TotalTransInfo> totalTransInfoList( @Param("conn") TotalTransInfoCondtion info,RowBounds rd) ;
	
	/**
	 * 统计已结算交易列表
	 * @return
	 */
	public int countTotalTransInfoList(@Param("conn") TotalTransInfoCondtion info);
	
	/**
	 * 查询终端限额信息
	 * @param info
	 * @return
	 */
	public List<TerAmountLimitInfo> queryTerAmountLimitInfo(@Param("conn") QueryTerAmountLimitInfoCondition info);
	
	/**
	 * 查询每个终端已经使用的限额
	 * @param startDate
	 * @param endDate
	 * @param queryType
	 * @param merNo
	 * @param terNo
	 * @return
	 */
	public List<HasUseLimitAmountInfo> queryHasUseLimitAmount(
			@Param("startDate") String startDate,
			@Param("endDate")  String endDate,
			@Param("queryType") String queryType,
			@Param("merNo") String merNo,
			@Param("terNo") String terNo) ;
	
}
