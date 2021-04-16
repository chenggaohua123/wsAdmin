package com.gateway.emailmgr.service;


import java.util.List;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.emailmgr.model.Complaint;
import com.gateway.emailmgr.model.EmailInfo;
import com.gateway.emailmgr.model.EmailSendInfo;
import com.gateway.emailmgr.model.EmailSendInfoList;
import com.gateway.emailmgr.model.EmailSendType;
import com.gateway.emailmgr.model.EmailSubInfo;
import com.gateway.emailmgr.model.GwTradeRecord;
import com.gateway.emailmgr.model.MerchantEmailConfigInfo;
import com.gateway.emailmgr.model.RiskTransInfo;

public interface EmailMgrService {
	/**
	 * 添加邮件发送类型
	 * */
	public int addEmailSendType(EmailSendType info);
	/**
	 * 添加邮件发送类型
	 * */
	public int updateEmailSendType(EmailSendType info);
	/**
	 * 根据ID查询邮件发送类型
	 * */
	public EmailSendType queryEmailSendTypeById(String id);
	
	/**
	 * 查询邮件发送类型
	 * */
	public PageInfo<EmailSendType> queryEmailSendType(Criteria criteria);
	
	/**
	 * 添加邮箱
	 * */
	public int addEmailInfo(EmailInfo info);
	/**
	 * 修改邮箱
	 * */
	public int updateEmailInfo(EmailInfo info);
	
	/** 删除邮箱信息 */
	public int deleteEmailInfo(String id);
	/**
	 * 根据ID查询邮箱
	 * */
	public EmailInfo queryEmailInfoById(String id);
	
	/**
	 * 查询邮箱
	 * */
	public PageInfo<EmailInfo> queryEmailInfo(Criteria criteria);
	/**
	 * 添加发送邮件附带信息
	 * */
	public int addEmailSubInfo(EmailSubInfo info);
	/**
	 * 修改邮件发送附带信息
	 * */
	public int updateEmailSubInfo(EmailSubInfo info);
	/**
	 * 根据ID查询邮件发送附带信息
	 * */
	public EmailSubInfo queryEmailSubInfoById(String id);
	
	/**
	 * 查询邮件发送附带信息
	 * */
	public PageInfo<EmailSubInfo> queryEmailSubInfo(Criteria criteria);
	
	/** 获取邮件发送附带信息列表 */
	List<EmailSubInfo> selectEmailSubInfoList();
	
	/** 关联邮件发送附带信息 */
	int bindingEmailInfo(EmailSendType info);
	
	/**
	 * 查询邮件发送信息
	 */
	public PageInfo<EmailSendInfoList> queryEmailSendInfoList(Criteria criteria);
	
	/**
	 * 修改邮件发送状态
	 */
	public int updateEmailSendStatus(EmailSendInfo info);
	/**
	 * 查询持卡人需要的信息
	 * @param info
	 * @return
	 */
	public GwTradeRecord queryTradeRocordByTradeNo(EmailSendInfo info);
	/**
	 * 通过商户号终端号查询商户邮箱配置信息
	 * @param merNo
	 * @param terNo
	 * @param string
	 * @return
	 */
	public List<MerchantEmailConfigInfo> queryMerEmailConfigByMerNoAndTerNo(
			String merNo, String terNo, String string);
	
	public Complaint queryComplaintTransByTradeNoAndComplainType(
			String tradeNo, String string);
	public RiskTransInfo queryMerchantRiskTransInfo(String tradeNo);
	
}
