package com.gateway.warnmgr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.warnmgr.model.BankInfo;
import com.gateway.warnmgr.model.MerchantWarnInfo;
import com.gateway.warnmgr.model.TransWarnMessageInfo;
import com.gateway.warnmgr.model.TransWarnPhoneInfo;
import com.gateway.warnmgr.model.TransWarnRuleInfo;


public interface WarnManageDao {
	/**
	 * 统计商户监管条数
	 * @param criteria
	 * @return
	 */
	int countMerchantWarnInfoCount(Criteria criteria);
	/**
	 * 列表显示商户监管信息
	 * @param criteria
	 * @param rb
	 * @return
	 */
	List<MerchantWarnInfo> queryMerchantWarnInfoCount(Criteria criteria);
	
	List<MerchantWarnInfo> queryMerchantWarnInfoCount(Criteria criteria,
			RowBounds rb);
	
	/**
	 * 查找交易预警监控信息
	 */
	public List<TransWarnRuleInfo> queryTransWarnRuleInfoList(RowBounds rb, Criteria criteria);
	
	/**
	 * 查找交易预警监控总数
	 */
	public int queryTransWarnRuleInfoCount(Criteria criteria);
	
	/**
	 * 保存交易预警监控信息
	 */
	public int saveTransWarnRuleInfo(@Param("vo") TransWarnRuleInfo info);
	
	/**
	 * 删除交易预警监控信息
	 */
	public int deleteTransWarnRuleInfoById(@Param("id") String id);
	
	/**
	 * 修改交易预警监控信息
	 */
	
	public int updateTransWarnRuleInfoById(@Param("vo") TransWarnRuleInfo vo);
	
	/**
	 * 查找交易监控信息
	 */
	public TransWarnRuleInfo queryTransWarnRuleInfoById(String id);
	
	/**
	 * 查找银行信息
	 */
	public List<BankInfo> queryBankInfoList(RowBounds rb, Criteria criteria);
	
	/**
	 * 查找银行总数
	 */
	public int queryBankInfoCount(Criteria criteria);
	
	/**
	 * 查找预警电话信息
	 */
	public List<TransWarnPhoneInfo> queryTransWarnPhoneInfoList(RowBounds rb, @Param("cr") Criteria criteria);
	
	/**
	 * 查找电话信息总数
	 */
	public int queryTransWarnPhoneInfoCount(@Param("cr") Criteria criteria);
	
	/**
	 * 添加预警电话信息
	 */
	public int saveTransWarnPhoneInfo(@Param("vo") TransWarnPhoneInfo info);
	
	/**
	 * 删除预警电话信息
	 */
	public int deleteTransWarnPhoneInfoById(@Param("id") String id);
	
	/**
	 * 修改预警电话信息
	 */
	public int updateTransWarnPhoneInfoById(@Param("vo") TransWarnPhoneInfo info);
	
	/**
	 * 查找预警电话信息
	 */
	public TransWarnPhoneInfo queryTransWarnPhoenInfoById(@Param("id") String id);
	
	/**
	 * 添加管理预警信息
	 */
	public int saveTransWarnRelPhoneInfo(@Param("phoneId") String phoneId, @Param("warnId") String warnId);
	
	/**
	 * 删除预警关联电话信息
	 */
	public int deleteTransWarnRelPhoneInfoByWarnId(@Param("warnId") String warnId);
	
	/**
	 * 删除预警关联电话信息
	 */
	public int deleteTransWarnRelPhoneInfoByPhoneId(@Param("phoneId") String phoneId);
	
	/**
	 * 查找交易预警信息
	 */
	public List<TransWarnRuleInfo> queryPhoneRelTransRuleInfoByWarnIds(@Param("warnIds") String warnIds);
	
	/**
	 * 添加短信信息
	 */
	public int saveTransWarnMessageInfo(@Param("vo") TransWarnMessageInfo vo);
	
	/**
	 * 修改短信信息
	 */
	public int updateTransWarnMessageInfoById(@Param("vo") TransWarnMessageInfo vo);
	
	/**
	 * 删除短信信息
	 */
	public int deleteTransWarnMessageInfoById(@Param("id") String id);
	
	/**
	 * 删除预警短信信息
	 */
	public int deleteTransWarnMessageInfoByWarnId(@Param("warnId") String warnId);
	
	/**
	 * 查找短信信息
	 */
	public TransWarnMessageInfo queryTransWarnMessageInfoByWarnId(@Param("warnId") String warnId);
	
}
