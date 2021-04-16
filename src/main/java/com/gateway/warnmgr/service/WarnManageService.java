package com.gateway.warnmgr.service;

import java.util.List;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.fraud.common.exception.FraudExcetion;
import com.gateway.warnmgr.model.BankInfo;
import com.gateway.warnmgr.model.MerchantWarnInfo;
import com.gateway.warnmgr.model.TransWarnMessageInfo;
import com.gateway.warnmgr.model.TransWarnPhoneInfo;
import com.gateway.warnmgr.model.TransWarnRuleInfo;



public interface WarnManageService {
	/**
	 * 商户监管控制列表显示
	 * @param criteria
	 * @return
	 */
	PageInfo<MerchantWarnInfo> queryMerchantWarnInfoList(Criteria criteria);
	
	List<MerchantWarnInfo> queryMerchantWarnInfoListAll(Criteria criteria);
	
	/**
	 * 查找交易预警监控信息
	 */
	public PageInfo<TransWarnRuleInfo> queryTransWarnRuleInfoList(Criteria criteria);
	
	/**
	 * 保存交易预警监控信息
	 */
	public int saveTransWarnRuleInfo(TransWarnRuleInfo info);
	
	/**
	 * 删除交易预警监控信息
	 */
	public int deleteTransWarnRuleInfo(String id) throws FraudExcetion;
	
	/**
	 * 批量删除交易预警监控信息
	 */
	public int deleteTransWarnRuleInfos(String[] ids) throws FraudExcetion;
	
	/**
	 * 修改交易预警监控信息
	 */
	public int updateTransWarnRuleInfoById(TransWarnRuleInfo info);
	
	/**
	 * 查找交易监控信息
	 */
	public TransWarnRuleInfo queryTransWarnRuleInfoById(String id);
	
	/**
	 * 查找银行信息
	 */
	public PageInfo<BankInfo> queryBankInfoList(Criteria criteria);
	
	/**
	 * 查找预警电话信息
	 */
	public PageInfo<TransWarnPhoneInfo> queryTransWarnPhoneInfoList(Criteria criteria);
	
	/**
	 * 添加预警电话信息
	 */
	public int saveTransWarnPhoneInfo(TransWarnPhoneInfo info);
	
	/**
	 * 删除预警电话信息
	 */
	public int deleteTransWarnPhoneInfoById(String id) throws FraudExcetion;
	
	/**
	 * 修改预警电话信息
	 */
	public int updateTransWarnPhoneInfoById(TransWarnPhoneInfo info);
	
	/**
	 * 查找预警电话信息
	 */
	public TransWarnPhoneInfo queryTransWarnPhoenInfoById(String id);
	
	/**
	 * 添加预警关联信息
	 */
	public int saveTransWarnWarnIdRelPhoneInfo(String[] ids, String warnId) throws FraudExcetion;
	
	/**
	 * 查找交易预警信息
	 */
	public List<TransWarnRuleInfo> queryPhoneRelTransRuleInfoByWarnIds(String warnIds);
	
	/**
	 * 添加短信信息
	 */
	public int saveTransWarnMessageInfo(TransWarnMessageInfo info) throws FraudExcetion;
	
	/**
	 * 修改短信信息
	 */
	public int updateTransWarnMessageInfoById(TransWarnMessageInfo info);
	
	/**
	 * 删除短信信息
	 */
	public int deleteTransWarnMessageInfoByWarnId(String warnId);
	
	/**
	 * 查找短信信息
	 */
	public TransWarnMessageInfo queryTransWarnMessageInfoByWarnId(String warnId);
}
