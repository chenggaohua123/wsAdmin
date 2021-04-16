package com.gateway.emailmgr.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.emailmgr.mapper.EmailMgrDao;
import com.gateway.emailmgr.model.Complaint;
import com.gateway.emailmgr.model.EmailInfo;
import com.gateway.emailmgr.model.EmailSendInfo;
import com.gateway.emailmgr.model.EmailSendInfoList;
import com.gateway.emailmgr.model.EmailSendType;
import com.gateway.emailmgr.model.EmailSubInfo;
import com.gateway.emailmgr.model.GwTradeRecord;
import com.gateway.emailmgr.model.MerchantEmailConfigInfo;
import com.gateway.emailmgr.model.RiskTransInfo;

@Service
public class EmailMgrServiceImpl implements EmailMgrService {
	@Autowired
	private EmailMgrDao emailMgrDao;
	@Override
	public int addEmailSendType(EmailSendType info) {
		return emailMgrDao.addEmailSendType(info);
	}

	@Override
	public int updateEmailSendType(EmailSendType info) {
		return emailMgrDao.updateEmailSendType(info);
	}

	@Override
	public EmailSendType queryEmailSendTypeById(String id) {
		return emailMgrDao.queryEmailSendTypeById(id);
	}

	@Override
	public PageInfo<EmailSendType> queryEmailSendType(Criteria criteria) {
		PageInfo<EmailSendType> page = new PageInfo<EmailSendType>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(emailMgrDao.countEmailSendType(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<EmailSendType> list = emailMgrDao.queryEmailSendType(criteria, rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public int addEmailInfo(EmailInfo info) {
		return emailMgrDao.addEmailInfo(info);
	}

	@Override
	public int updateEmailInfo(EmailInfo info) {
		return emailMgrDao.updateEmailInfo(info);
	}

	@Override
	public EmailInfo queryEmailInfoById(String id) {
		return emailMgrDao.queryEmailInfoById(id);
	}

	@Override
	public PageInfo<EmailInfo> queryEmailInfo(Criteria criteria) {
		PageInfo<EmailInfo> page = new PageInfo<EmailInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(emailMgrDao.countEmailInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<EmailInfo> list = emailMgrDao.queryEmailInfo(criteria, rb);
		page.setData(list);
		return page;
	}
	
	
	@Override
	public int addEmailSubInfo(EmailSubInfo info) {
		return emailMgrDao.addEmailSubInfo(info);
	}

	@Override
	public int updateEmailSubInfo(EmailSubInfo info) {
		return emailMgrDao.updateEmailSubInfo(info);
	}

	@Override
	public EmailSubInfo queryEmailSubInfoById(String id) {
		return emailMgrDao.queryEmailSubInfoById(id);
	}

	@Override
	public PageInfo<EmailSubInfo> queryEmailSubInfo(Criteria criteria) {
		PageInfo<EmailSubInfo> page = new PageInfo<EmailSubInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(emailMgrDao.countEmailSubInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<EmailSubInfo> list = emailMgrDao.queryEmailSubInfo(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public int deleteEmailInfo(String id) {
		return emailMgrDao.deleteEmailInfo(id);
	}
	
	@Override
	public List<EmailSubInfo> selectEmailSubInfoList(){
		Criteria criteria = new Criteria();
		return emailMgrDao.queryEmailSubInfo(criteria);
	}
	
	/** 关联邮件发送附带信息 */
	@Override
	public int bindingEmailInfo(EmailSendType info){
		return emailMgrDao.bindingEmailInfo(info);
	}

	@Override
	public PageInfo<EmailSendInfoList> queryEmailSendInfoList(Criteria criteria) {
		PageInfo<EmailSendInfoList> page = new PageInfo<EmailSendInfoList>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(emailMgrDao.queryEmailSendInfoCount(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), page.getPageSize());
		List<EmailSendInfoList> list = emailMgrDao.queryEmailSendInfoList(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public int updateEmailSendStatus(EmailSendInfo info) {
		int a = -1;
		if(info.getIds()!=null && !("".equals(info.getIds()))){
			String[] count = info.getIds().split(",");
			a = emailMgrDao.updateEmailSendStatus(count[0]);
//			if(count!=null && count.length>0){
//				for(String id : count){
//					if(id!=null && !("".equals(id))){
//						a = emailMgrDao.updateEmailSendStatus(id);
//					}
//				}
//			}
		}
		
//		else{
//			a = emailMgrDao.addEmailSendInfo(info);
//		}
		return a;
	}
	
	@Override
	public List<MerchantEmailConfigInfo> queryMerEmailConfigByMerNoAndTerNo(
			String merNo, String terNo, String string) {
		return emailMgrDao.queryMerEmailConfigByMerNoAndTerNo(merNo,terNo,string);
	}
	
	@Override
	public GwTradeRecord queryTradeRocordByTradeNo(EmailSendInfo info) {
		return emailMgrDao.queryTradeRocordByTradeNo(info);
	}
	
	@Override
	public Complaint queryComplaintTransByTradeNoAndComplainType(
			String tradeNo, String string) {
		return emailMgrDao.queryComplaintTransByTradeNoAndComplainType(tradeNo,string);
	}
	
	@Override
	public RiskTransInfo queryMerchantRiskTransInfo(String tradeNo) {
		return emailMgrDao.queryMerchantRiskTransInfo(tradeNo);
	}
	
}
