package com.gateway.emailmgr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.emailmgr.model.Complaint;
import com.gateway.emailmgr.model.EmailInfo;
import com.gateway.emailmgr.model.EmailSendInfo;
import com.gateway.emailmgr.model.EmailSendInfoList;
import com.gateway.emailmgr.model.EmailSendType;
import com.gateway.emailmgr.model.EmailSubInfo;
import com.gateway.emailmgr.model.GwTradeRecord;
import com.gateway.emailmgr.model.MerchantEmailConfigInfo;
import com.gateway.emailmgr.model.RiskTransInfo;


public interface EmailMgrDao {
	/**
	 * 添加邮件发送类型
	 * */
	public int addEmailSendType(@Param("info")EmailSendType info);
	/**
	 * 添加邮件发送类型
	 * */
	public int updateEmailSendType(@Param("info")EmailSendType info);
	/**
	 * 根据ID查询邮件发送类型
	 * */
	public EmailSendType queryEmailSendTypeById(String id);
	
	/**
	 * 查询邮件发送类型
	 * */
	public List<EmailSendType> queryEmailSendType(Criteria criteria);
	
	public List<EmailSendType> queryEmailSendType(Criteria criteria,RowBounds rd);
	/**
	 * 统计邮件发送类型数量
	 * */
	public int countEmailSendType(Criteria criteria);
	
	
	/** 往下处理填写sql语句  **/
	/**
	 * 邮箱添加
	 * */
	public int addEmailInfo(EmailInfo info);
	/**
	 * 邮箱修改
	 * */
	public int updateEmailInfo(@Param("info") EmailInfo info);
	/**
	 * 邮箱列表
	 * */
	public List<EmailInfo> queryEmailInfo(Criteria criteria);
	public List<EmailInfo> queryEmailInfo(Criteria criteria,RowBounds rd);
	/**
	 * 统计邮箱数量
	 * */
	public int countEmailInfo(Criteria criteria);
	/**
	 * 通过编号查询邮箱信息
	 * */
	public EmailInfo queryEmailInfoById(String id);
	/**
	 * 邮件发送附带信息添加
	 * */
	public int addEmailSubInfo(EmailSubInfo info);
	/**
	 * 修改邮件发送附带信息
	 * */
	public int updateEmailSubInfo(EmailSubInfo info);
	/**
	 * 邮件发送附带信息列表
	 * */
	public List<EmailSubInfo> queryEmailSubInfo(Criteria criteria);
	public List<EmailSubInfo> queryEmailSubInfo(Criteria criteria,RowBounds rd);
	/**
	 * 统计邮件发送附带信息数量
	 * */
	public int countEmailSubInfo(Criteria criteria);
	/**
	 * 通过编号查询邮件发送附带信息
	 * */
	public EmailSubInfo queryEmailSubInfoById(String id);
	
	/** 通过ID删除一条邮件信息 */
	public int deleteEmailInfo(@Param("id") String id);
	
	/** 关联邮件发送附带信息 */
	public int bindingEmailInfo(EmailSendType info);
	
	/**
	 * 查询邮件发送信息
	 */
	public List<EmailSendInfoList> queryEmailSendInfoList(Criteria criteria, RowBounds rb);
	
	/**
	 * 查询邮件发送总数
	 */
	public int queryEmailSendInfoCount(Criteria criteria);
	
	/**
	 * 修改邮件发送状态
	 */
	public int updateEmailSendStatus(@Param("id") String id);
	
	/**
	 * 保存邮件发送信息
	 */
	public int addEmailSendInfo(@Param("vo") EmailSendInfo vo);
	
	public GwTradeRecord queryTradeRocordByTradeNo(EmailSendInfo info);
	public List<MerchantEmailConfigInfo> queryMerEmailConfigByMerNoAndTerNo(@Param("merNo") String merNo ,@Param("terNo") String terNo,@Param("emailSendType") String emailSendType);
	
	/**
	 * 异常订单查询
	 * @param tradeNo
	 * @param complaintType
	 * @return
	 */
	public Complaint queryComplaintTransByTradeNoAndComplainType(@Param("tradeNo") String tradeNo,@Param("complaintType") String complaintType);
	
	/**
	 * 查找高风险订单
	 */
	public RiskTransInfo queryMerchantRiskTransInfo(@Param("tradeNo") String tradeNo);

}
