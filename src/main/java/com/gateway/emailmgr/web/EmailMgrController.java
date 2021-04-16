package com.gateway.emailmgr.web;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.ServiceException;
import com.gateway.common.utils.SHA256Utils;
import com.gateway.emailmgr.model.Complaint;
import com.gateway.emailmgr.model.EmailInfo;
import com.gateway.emailmgr.model.EmailSendInfo;
import com.gateway.emailmgr.model.EmailSendInfoList;
import com.gateway.emailmgr.model.EmailSendType;
import com.gateway.emailmgr.model.EmailSubInfo;
import com.gateway.emailmgr.model.GwTradeRecord;
import com.gateway.emailmgr.model.MerchantEmailConfigInfo;
import com.gateway.emailmgr.model.RiskTransInfo;
import com.gateway.emailmgr.service.EmailMgrService;
import com.gateway.emailmgr.util.EmailUtil;
@Controller
@RequestMapping("/emailmgr")
public class EmailMgrController extends BaseController {
	@Autowired
	private EmailMgrService emailMgrService;
	/**
	 * 查询邮件发送类型
	 * */
	@RequestMapping("/listEmailSendType")
	public String listEmailSendType(){
		PageInfo<EmailSendType> page=emailMgrService.queryEmailSendType(getCriteria());
		getRequest().setAttribute("page", page);
		return "emailmgr/listEmailSendType";
	}
	/**
	 * 跳转到添加邮件发送类型页面
	 * */
	@RequestMapping("/goAddEmailSendType")
	public String goAddEmailSendType(){
		return "emailmgr/addEmailSendType";
	}
	/**
	 * 添加邮件发送类型
	 * */
	@RequestMapping("/addEmailSendType")
	public ModelAndView addEmailSendType(EmailSendType info){
		return emailMgrService.addEmailSendType(info)>0?ajaxDoneSuccess("添加成功"):ajaxDoneError("添加失败");
	}
	/**
	 * 跳转到邮件发送类型修改页面
	 * */
	@RequestMapping("/goUpdateEmailSendType")
	public String goUpdateEmailSendType(String id){
		EmailSendType info=emailMgrService.queryEmailSendTypeById(id);
		getRequest().setAttribute("info", info);
		return "emailmgr/updateEmailSendType";
	}
	/**
	 * 邮件发送类型修改
	 * */
	@RequestMapping("/updateEmailSendType")
	public ModelAndView updateEmailSendType(EmailSendType info){
		return emailMgrService.updateEmailSendType(info)>0?ajaxDoneSuccess("添加成功"):ajaxDoneError("添加失败");
	}
	
	/** 去绑定邮件发送附带信息页面 */
	@RequestMapping("/goBindingEmailInfoList")
	public String goBindingEmailInfoList(String id){
		List<EmailSubInfo> list = emailMgrService.selectEmailSubInfoList();
		getRequest().setAttribute("typeId", id);
		EmailSendType eInfo = emailMgrService.queryEmailSendTypeById(id);
		getRequest().setAttribute("emailSubId", eInfo.getEmailSubId());
		getRequest().setAttribute("list", list);
		return "emailmgr/bindingEmailInfoList";
	}
	
	/** 关联邮件发送附带信息 */
	@RequestMapping("/bindingEmailInfo")
	public ModelAndView bindingEmailInfo(EmailSendType info){
		return emailMgrService.bindingEmailInfo(info)>0?ajaxDoneSuccess("关联成功"):ajaxDoneError("关联失败");
	}
	
	/********* 添加 页面  **********/
	/****  处理邮箱   ****/
	/**
	 * 查询邮箱
	 * */
	@RequestMapping("/listEmailInfo")
	public String listEmailInfo(){
		PageInfo<EmailInfo> page=emailMgrService.queryEmailInfo(getCriteria());
		getRequest().setAttribute("page", page);
		return "emailmgr/listEmailInfo";
	}
	/**
	 * 跳转到添加邮箱页面
	 * */
	@RequestMapping("/goAddEmailInfo")
	public String goAddEmailInfo(){
		return "emailmgr/addEmailInfo";
	}
	/**
	 * 添加邮箱
	 * */
	@RequestMapping("/addEmailInfo")
	public ModelAndView addEmailInfo(EmailInfo info){
		info.setCreateBy(getLogAccount().getRealName());
		return emailMgrService.addEmailInfo(info)>0?ajaxDoneSuccess("添加成功"):ajaxDoneError("添加失败");
	}
	/**
	 * 跳转到邮箱修改页面
	 * */
	@RequestMapping("/goUpdateEmailInfo")
	public String goUpdateEmailInfo(String id){
		EmailInfo info=emailMgrService.queryEmailInfoById(id);
		getRequest().setAttribute("info", info);
		return "emailmgr/updateEmailInfo";
	}
	/**
	 * 邮箱修改
	 * */
	@RequestMapping("/updateEmailInfo")
	public ModelAndView updateEmailInfo(EmailInfo info){
		info.setLastModifyBy(getLogAccount().getRealName());
		return emailMgrService.updateEmailInfo(info)>0?ajaxDoneSuccess("添加成功"):ajaxDoneError("添加失败");
	}
	
	/** 删除邮箱信息 */
	@RequestMapping("/deleteEmailInfo")
	public ModelAndView deleteEmailInfo(String id){
		EmailInfo info = emailMgrService.queryEmailInfoById(id);
		if(StringUtils.isEmpty(info)){
			ajaxDoneError("该数据不存在或已删除");
		}
		return emailMgrService.deleteEmailInfo(id)>0?ajaxDoneSuccess("删除成功"):ajaxDoneError("删除失败");
	}
	/****  处理邮件发送附带信息 *****/
	
	/**
	 * 查询邮件发送附带信息
	 * */
	@RequestMapping("/listEmailSubInfo")
	public String listEmailSubInfo(){
		PageInfo<EmailSubInfo> page=emailMgrService.queryEmailSubInfo(getCriteria());
		getRequest().setAttribute("page", page);
		return "emailmgr/listEmailSubInfo";
	}
	/**
	 * 跳转到添加邮件发送附带信息页面
	 * */
	@RequestMapping("/goAddEmailSubInfo")
	public String goAddEmailSubInfo(){
		return "emailmgr/addEmailSubInfo";
	}
	/**
	 * 添加邮件发送附带信息
	 * */
	@RequestMapping("/addEmailSubInfo")
	public ModelAndView addEmailSubInfo(EmailSubInfo info){
		info.setCreateBy(getLogAccount().getRealName());
		return emailMgrService.addEmailSubInfo(info)>0?ajaxDoneSuccess("添加成功"):ajaxDoneError("添加失败");
	}
	/**
	 * 跳转到邮件发送附带信息修改页面
	 * */
	@RequestMapping("/goUpdateEmailSubInfo")
	public String goUpdateEmailSubInfo(String id){
		EmailSubInfo info=emailMgrService.queryEmailSubInfoById(id);
		getRequest().setAttribute("info", info);
		return "emailmgr/updateEmailSubInfo";
	}
	/**
	 * 邮件发送附带信息修改
	 * */
	@RequestMapping("/updateEmailSubInfo")
	public ModelAndView updateEmailSubInfo(EmailSubInfo info){
		info.setLastModifyBy(getLogAccount().getRealName());
		return emailMgrService.updateEmailSubInfo(info)>0?ajaxDoneSuccess("修改成功"):ajaxDoneError("修改失败");
	}
	
	/**
	 * 统计显示邮件已发送信息
	 */
	@RequestMapping(value="/queryEmailSendInfoList")
	public String queryEmailSendInfoList(){
		Criteria criteria = getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(new Date());
			criteria.getCondition().put("sendStart", date);
			criteria.getCondition().put("sendEnd", date);
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			PageInfo<EmailSendInfoList> page = emailMgrService.queryEmailSendInfoList(criteria);
			getRequest().setAttribute("page", page);
			getRequest().setAttribute("form", criteria.getCondition());
		}
		return "emailmgr/listEmailSendInfo";
	}
	
	/**
	 * 修改邮件发送信息
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/updateEmailSendStatus")
	public ModelAndView updateEmailSendStatus(EmailSendInfo form) throws ServiceException{
		int a = emailMgrService.updateEmailSendStatus(form);
		if(form.getIds() == null || "".equals(form.getIds())){
			throw new ServiceException("未激活邮件不能发送！");
		}
		if(a>0){
			return ajaxDoneSuccess("修改为重新发送成功");
		}else{
			return ajaxDoneError("修改为重新发送失败");
		}
	}
	
	
	@RequestMapping("/showEmailView")
	public String showEmailView(String id) throws ServiceException{
		String url=EmailUtil.emailConfigMap.get(id);
		if(url==null){
			throw new ServiceException("邮件模板不存在");
		}
		return "emailmgr/emailBoard/"+url;
	}
	
	@RequestMapping(value="/showEmailDesc")
	public String showEmailDesc(String tradeNo,String type) throws ServiceException{
		EmailSendInfo info =new EmailSendInfo();
		info.setTradeNo(tradeNo);
		info.setSendTypeId(type);
		try {
			if("1".equals(type)){
				GwTradeRecord result=emailMgrService.queryTradeRocordByTradeNo(info);
				List<MerchantEmailConfigInfo> emailConfigs = emailMgrService.queryMerEmailConfigByMerNoAndTerNo(result.getMerNo(), result.getTerNo(),"1");
				for(MerchantEmailConfigInfo emailConfig:emailConfigs ){
					if(null != emailConfig && !"1".equals(emailConfig.getStatus())){
						throw new ServiceException("商户设置不发送");
					}
				}
					getRequest().setAttribute("tradeNo", info.getTradeNo());
					getRequest().setAttribute("randomCode", SHA256Utils.getSHA256Encryption(info.getTradeNo()));
					getRequest().setAttribute("order", result.getOrderNo());
					getRequest().setAttribute("email", result.getCardHoldEmail());
					getRequest().setAttribute("sourceamount", result.getAmount());
					getRequest().setAttribute("currency", result.getCurrency());
					getRequest().setAttribute("merwebsite", result.getPayWebSite());
					getRequest().setAttribute("randomQueryCode", "");
					getRequest().setAttribute("tradeDateTime",result.getTransDate());
					getRequest().setAttribute("acquirer", result.getAcquirer());
					getRequest().setAttribute("tel", result.getTel());
					getRequest().setAttribute("fax", result.getFax());
					getRequest().setAttribute("helpwebsite", result.getHelpWebsite());
					getRequest().setAttribute("replayEmail", result.getReplyEmail());
			}else if("2".equals(type)){
				GwTradeRecord result=emailMgrService.queryTradeRocordByTradeNo(info);
				getRequest().setAttribute("merNo", result.getMerNo());
				getRequest().setAttribute("TradeNo", info.getTradeNo());
				getRequest().setAttribute("order", result.getOrderNo());
				getRequest().setAttribute("terNo", result.getTerNo());
				getRequest().setAttribute("sourceamount", result.getAmount());
				getRequest().setAttribute("currency", result.getCurrency());
				getRequest().setAttribute("tradeDatetime",result.getTransDate());
				getRequest().setAttribute("merwebsite", result.getPayWebSite());
				getRequest().setAttribute("acquirer", result.getAcquirer());
				getRequest().setAttribute("tel", result.getTel());
				getRequest().setAttribute("fax", result.getFax());
				getRequest().setAttribute("helpwebsite", result.getHelpWebsite());
				getRequest().setAttribute("replayEmail", result.getReplyEmail());
				List<MerchantEmailConfigInfo> emailConfigs = emailMgrService.queryMerEmailConfigByMerNoAndTerNo(result.getMerNo(), result.getTerNo(),"2");
				for(MerchantEmailConfigInfo emailConfig:emailConfigs ){
					if(null != emailConfig && !"1".equals(emailConfig.getStatus())){
						throw new ServiceException("商户设置不发送");
					}
				}
			}else if("3".equals(type)){
				GwTradeRecord result=emailMgrService.queryTradeRocordByTradeNo(info);
					getRequest().setAttribute("email", result.getCardHoldEmail());
					getRequest().setAttribute("tradeNo", info.getTradeNo());
					getRequest().setAttribute("orderNo", result.getOrderNo());
					getRequest().setAttribute("sourceAmount", result.getAmount());
					getRequest().setAttribute("currency", result.getCurrency());
					getRequest().setAttribute("tradeDateTime",result.getTransDate());
					getRequest().setAttribute("tel", result.getTel());
					getRequest().setAttribute("Fax", result.getFax());
					getRequest().setAttribute("helpwebsite", result.getHelpWebsite());
					getRequest().setAttribute("replayEmail", result.getReplyEmail());
				
				List<MerchantEmailConfigInfo> emailConfigs = emailMgrService.queryMerEmailConfigByMerNoAndTerNo(result.getMerNo(), result.getTerNo(),"3");
				for(MerchantEmailConfigInfo emailConfig:emailConfigs ){
					if(null != emailConfig && !"1".equals(emailConfig.getStatus())){
						throw new ServiceException("商户设置不发送");
					}
				}
			}else if("4".equals(type)){
				
				GwTradeRecord result=emailMgrService.queryTradeRocordByTradeNo(info);
					getRequest().setAttribute("TradeNo", info.getTradeNo());
					getRequest().setAttribute("order", result.getOrderNo());
					getRequest().setAttribute("Iogistics", result.getIogistics());
					getRequest().setAttribute("trackno", result.getTrackNo());
					getRequest().setAttribute("IogisticsUrl", result.getIogisticsUrl());
					getRequest().setAttribute("email", result.getCardHoldEmail());
					getRequest().setAttribute("merwebsite", result.getCardHoldEmail());
					getRequest().setAttribute("sourceamount", result.getAmount());
					getRequest().setAttribute("Currency", result.getCurrency());
					getRequest().setAttribute("merwebsite", result.getPayWebSite());
					getRequest().setAttribute("tradeDateTime",result.getTransDate());
					getRequest().setAttribute("acquirer", result.getAcquirer());
					getRequest().setAttribute("tel", result.getTel());
					getRequest().setAttribute("fax", result.getFax());
					getRequest().setAttribute("helpwebsite", result.getHelpWebsite());
					getRequest().setAttribute("replayEmail", result.getReplyEmail());
				List<MerchantEmailConfigInfo> emailConfigs = emailMgrService.queryMerEmailConfigByMerNoAndTerNo(result.getMerNo(), result.getTerNo(),type);
				for(MerchantEmailConfigInfo emailConfig:emailConfigs ){
					if(null != emailConfig && !"1".equals(emailConfig.getStatus())){
						throw new ServiceException("商户设置不发送");
					}
				}
			}else if("5".equals(type)){
				GwTradeRecord result=emailMgrService.queryTradeRocordByTradeNo(info);
					getRequest().setAttribute("TradeNo", info.getTradeNo());
					getRequest().setAttribute("order", result.getOrderNo());
					getRequest().setAttribute("email", result.getCardHoldEmail());
					getRequest().setAttribute("amount", result.getAmount());
					getRequest().setAttribute("Currency", result.getCurrency());
					getRequest().setAttribute("merwebsite", result.getPayWebSite());
					getRequest().setAttribute("tradeDateTime",result.getTransDate());
					getRequest().setAttribute("iew_refundamount", result.getRefundAmount());
					getRequest().setAttribute("view_currency", result.getCurrency());
					getRequest().setAttribute("acquirer", "");
					getRequest().setAttribute("tel", result.getTel());
					getRequest().setAttribute("Fax", result.getFax());
					getRequest().setAttribute("helpwebsite", result.getHelpWebsite());
					getRequest().setAttribute("replayEmail", result.getReplyEmail());
					getRequest().setAttribute("contactEmail", result.getReplyEmail());	

				List<MerchantEmailConfigInfo> emailConfigs = emailMgrService.queryMerEmailConfigByMerNoAndTerNo(result.getMerNo(), result.getTerNo(),type);
				for(MerchantEmailConfigInfo emailConfig:emailConfigs ){
					if(null != emailConfig && !"1".equals(emailConfig.getStatus())){
						throw new ServiceException("商户设置不发送");
					}
				}
			}else if("6".equals(type)){
				
				GwTradeRecord result=emailMgrService.queryTradeRocordByTradeNo(info);
				Complaint complain = emailMgrService.queryComplaintTransByTradeNoAndComplainType(result.getTradeNo(), "1");
				
				getRequest().setAttribute("tradeNo", info.getTradeNo());
				getRequest().setAttribute("reason", complain.getComplaintTypeValue());
				getRequest().setAttribute("merNo", result.getMerNo());
				getRequest().setAttribute("terNo", result.getTerNo());
				getRequest().setAttribute("order", result.getOrderNo());
				getRequest().setAttribute("tel", result.getTel());
				getRequest().setAttribute("deadline", complain.getDeadline());
				getRequest().setAttribute("website", result.getHelpWebsite());

				List<MerchantEmailConfigInfo> emailConfigs = emailMgrService.queryMerEmailConfigByMerNoAndTerNo(result.getMerNo(), result.getTerNo(),type);
				for(MerchantEmailConfigInfo emailConfig:emailConfigs ){
					if(null != emailConfig && !"1".equals(emailConfig.getStatus())){
						throw new ServiceException("商户设置不发送");
					}
				}
			}else if("7".equals(type)){
				
				GwTradeRecord result=emailMgrService.queryTradeRocordByTradeNo(info);
				Complaint complain = emailMgrService.queryComplaintTransByTradeNoAndComplainType(result.getTradeNo(), "2");
				getRequest().setAttribute("tradeNo", info.getTradeNo());
				getRequest().setAttribute("reason", complain.getComplaintTypeValue());
				getRequest().setAttribute("complaintsDesc", complain.getRemark()==null?"":complain.getRemark());
				getRequest().setAttribute("merNo", result.getMerNo());
				getRequest().setAttribute("terNo", result.getTerNo());
				getRequest().setAttribute("order", result.getOrderNo());
				getRequest().setAttribute("tel", result.getTel());
				getRequest().setAttribute("deadline", complain.getDeadline());
				getRequest().setAttribute("website", result.getHelpWebsite());
				getRequest().setAttribute("replayEmail", result.getReplyEmail());
				List<MerchantEmailConfigInfo> emailConfigs = emailMgrService.queryMerEmailConfigByMerNoAndTerNo(result.getMerNo(), result.getTerNo(),type);
				for(MerchantEmailConfigInfo emailConfig:emailConfigs ){
					if(null != emailConfig && !"1".equals(emailConfig.getStatus())){
						throw new ServiceException("商户设置不发送");
					}
				}
			}else if("11".equals(type)){
				
				RiskTransInfo riskInfo = emailMgrService.queryMerchantRiskTransInfo(info.getTradeNo());
				getRequest().setAttribute("merNo", riskInfo.getMerNo());
				getRequest().setAttribute("terNo", riskInfo.getTerNo());
				getRequest().setAttribute("tradeNo", riskInfo.getTradeNo());
				getRequest().setAttribute("orderNo", riskInfo.getOrderNo());
				getRequest().setAttribute("doReason",
						riskInfo.getDoReason() != null ? riskInfo.getDoReason() : "");
				getRequest().setAttribute("payWebSite", riskInfo.getWebsite());
				getRequest().setAttribute("merchantName", riskInfo.getMerchantName());
				getRequest().setAttribute("transDate", riskInfo.getTransDate());
				getRequest().setAttribute("merTransAmount", riskInfo.getMerTransAmount());
				getRequest().setAttribute("merBusCurrency", riskInfo.getMerBusCurrency());

				List<MerchantEmailConfigInfo> emailConfigs = emailMgrService.queryMerEmailConfigByMerNoAndTerNo(riskInfo.getMerNo(), riskInfo.getTerNo(),type);
				for(MerchantEmailConfigInfo emailConfig:emailConfigs ){
					if(null != emailConfig && !"1".equals(emailConfig.getStatus())){
						throw new ServiceException("商户设置不发送");
					}
				}
			}else if("12".equals(type)){
				
				GwTradeRecord result=emailMgrService.queryTradeRocordByTradeNo(info);
				Complaint complain = emailMgrService.queryComplaintTransByTradeNoAndComplainType(result.getTradeNo(), "0");
				
				getRequest().setAttribute("tradeNo", info.getTradeNo());
				getRequest().setAttribute("reason", complain.getComplaintTypeValue());
				getRequest().setAttribute("merNo", result.getMerNo());
				getRequest().setAttribute("tel", result.getTel());
				getRequest().setAttribute("terNo", result.getTerNo());
				getRequest().setAttribute("order", result.getOrderNo());
				getRequest().setAttribute("deadline", complain.getDeadline());
				getRequest().setAttribute("website", result.getHelpWebsite());

				List<MerchantEmailConfigInfo> emailConfigs = emailMgrService.queryMerEmailConfigByMerNoAndTerNo(result.getMerNo(), result.getTerNo(),type);
				for(MerchantEmailConfigInfo emailConfig:emailConfigs ){
					if(null != emailConfig && !"1".equals(emailConfig.getStatus())){
						throw new ServiceException("商户设置不发送");
					}
				}
			}else{
				throw new ServiceException("邮件类型错误");
			}
		} catch(ServiceException e){
			throw new ServiceException("商户设置不发送");
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("数据异常");
		}
		
		
		String url=EmailUtil.emailConfigMap.get(type);
		if(url==null){
			throw new ServiceException("邮件模板不存在");
		}
		return "emailmgr/emailBoard/"+url;
	}
	
}
