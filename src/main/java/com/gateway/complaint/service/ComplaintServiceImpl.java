package com.gateway.complaint.service;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.utils.Funcs;
import com.gateway.common.utils.Tools;
import com.gateway.complaint.mapper.ComplaintDao;
import com.gateway.complaint.mapper.ComplaintTypeDao;
import com.gateway.complaint.model.Complaint;
import com.gateway.complaint.model.ComplaintResult;
import com.gateway.complaint.model.ComplaintType;
import com.gateway.complaint.model.WebsiteCardHolderInfo;
import com.gateway.complaint.model.WebsiteComplaintCardHolderInfo;
import com.gateway.complaint.model.WebsiteComplaintInfo;
import com.gateway.sysmgr.mapper.SysMgrDao;
import com.gateway.sysmgr.model.SendEmailReqInfo;
import com.gateway.transmgr.model.TransInfo;
import com.gateway.transmgr.service.TransMgrService;

@Service
public class ComplaintServiceImpl implements ComplaintService {
	@Autowired
	private ComplaintDao complaintDao;
	@Autowired
	private ComplaintTypeDao complaintTypeDao;
	@Autowired
	private TransMgrService transMgrService;
	@Autowired
	private SysMgrDao sysMgrDao;

	@Override
	public PageInfo<Complaint> queryListComplaintInfo(Criteria criteria) {
		PageInfo<Complaint> page = new PageInfo<Complaint>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(complaintDao.countComplaintInfoList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<Complaint> list = complaintDao.queryComplaintInfoList(criteria, rb);
		for(Complaint li:list){
			if(!StringUtils.isEmpty(li.getFilePath())){
				li.setFilePath("下载申诉资料");
			}
			if(li.getCheckNo()!=null&&!"".equals(li.getCheckNo())){
				//try{
				String cardNo;
				try {
					cardNo = Funcs.decrypt(li.getCheckNo())+"****"+Funcs.decrypt(li.getLast());
					li.setSixAndFourCardNo(cardNo);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		page.setData(list);
		return page;
	}

	@Override
	public PageInfo<ComplaintType> queryListComplaintTypeInfo(Criteria criteria) {
		PageInfo<ComplaintType> page = new PageInfo<ComplaintType>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(complaintTypeDao.countComplaintTypeInfoList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<ComplaintType> list = complaintTypeDao.queryComplaintTypeInfoList(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public int addComplaintTypeInfo(ComplaintType info) {
		return complaintTypeDao.addComplaintTypeInfo(info);
	}

	@Override
	public ComplaintType queryComplaintTypeInfoId(String id) {
		return complaintTypeDao.queryComplaintTypeInfoId(id);
	}

	@Override
	public int updateComplaintTypeInfo(ComplaintType info) {
		return complaintTypeDao.updateComplaintTypeInfo(info);
	}

	@Override
	public String addComplaintInfo(Complaint info) {
		if(StringUtils.isEmpty(info) || StringUtils.isEmpty(info.getTradeNo())){
			return info.getTradeNo()+"数据异常，请重试";
		}
		TransInfo tInfo = transMgrService.queryTransDetailByTradeNo(info.getTradeNo());
		if(StringUtils.isEmpty(tInfo)){
			return info.getTradeNo()+"订单号错误，请查证后再添加！";
		}
		if(!"1".equals(info.getIsMerchantSee())){//0商户可见 1不可见
			if(!"00".equals(tInfo.getRespCode())){//如果添加的交易不是交易成功时
				return info.getTradeNo()+"请添加交易成功的交易信息！";
			}
		}
		if(info.getType() == 1){//0,调查单，1拒付；2持卡人投诉
			if(Double.parseDouble(info.getAmount())==0){//拒付金额
				info.setAmount(tInfo.getTransAmount().toString());
			}
		}
		Complaint cInfo = complaintDao.queryComplaintTypeInfo(info);
		if(cInfo != null && info.getType() == 1 &&!"1".equals(info.getIsMerchantSee())){
			if(Double.parseDouble(cInfo.getAmount())==0){
				return info.getTradeNo()+"已经全额拒付";
			}
			if((Double.parseDouble(cInfo.getAmount()) + Double.parseDouble(info.getAmount()) )>tInfo.getTransAmount().doubleValue()){
				return info.getTradeNo()+"操作金额过大";
			}
			cInfo=null;
		}
		if(cInfo == null  &&info.getType() == 1 &&!"1".equals(info.getIsMerchantSee())){
			if(( Double.parseDouble(info.getAmount()) )>tInfo.getTransAmount().doubleValue()){
				return info.getTradeNo()+"操作金额过大";
			}
		}
		if(info.getType()==1&&"1".equals(info.getIsMerchantSee())){
			cInfo=null;
		}
		if(!StringUtils.isEmpty(cInfo)){
			return "订单号：" + info.getTradeNo() + ",已添加，请勿重复添加";
		}
		int i = complaintDao.addComplaintInfo(info);
		if(0 >= i){
			return info.getTradeNo()+"添加失败，请重试";
		}else{
			SendEmailReqInfo req=new SendEmailReqInfo();
			req.setTradeNo(info.getTradeNo());
			req.setStatus("0");
			if(info.getType()==0){
				req.setSendTypeId("12");//调查单 发邮件
			}else if(info.getType()==1){
				if("0".equals(info.getIsMerchantSee())){
					req.setSendTypeId("6");
				}
			}else if(info.getType()==2){
				req.setSendTypeId("7");
			}
			if(req.getSendTypeId()!=null&&Tools.isNumber(req.getSendTypeId())){
				sysMgrDao.addSendEmailReqInfo(req);
			}
		}
		return "";
	}

	@Override
	public int updateComplaintInfo(String[] ids,int status,String updateBy) {
		int count = 0;//操作成功条数
		for(int i=0;i< ids.length;i++){
			Complaint info = complaintDao.queryComplaintById(ids[i]);
			if(StringUtils.isEmpty(info) || "3".equals(info.getStatus())){//当状态为已退款，不能操作
				continue;
			}
			info.setStatus(status+"");
			info.setLastUpdateBy(updateBy);
			int t = complaintDao.updateComplaintInfo(info);
			if(t > 0 ){
				count++;
			}
		}
		return count;
	}

	@Override
	public int updateDishonorInfo(String[] ids, int status, String updateBy) {
		int count = 0;//操作成功条数
		for(int i=0;i< ids.length;i++){
			Complaint info = complaintDao.queryComplaintById(ids[i]);
			if(StringUtils.isEmpty(info) || "4".equals(info.getStatus())){//不允许操作已拒付状态
				continue;
			}
			info.setStatus(status+"");
			info.setLastUpdateBy(updateBy);
			int t = complaintDao.updateComplaintInfo(info);
			if(t > 0 ){
				count++;
			}
		}
		return  count;
	}

	@Override
	public Complaint queryComplaintById(String id) {
		return complaintDao.queryComplaintById(id);
	}

	@Override
	public List<Complaint> queryListComplaintInfoList(Criteria criteria) {
		return complaintDao.queryComplaintInfoList(criteria);
	}

	@Override
	public int countComplaintTypeByIdAndType(Complaint complaint) {
		return complaintDao.countComplaintTypeByIdAndType(complaint);
	}

	@Override
	public List<ComplaintResult> queryComplaintResultInfo(ComplaintResult result) {
		return complaintDao.queryComplaintResultInfo(result);
	}

	@Override
	public int addComplaintResultInfo(ComplaintResult result) {
		return complaintDao.addComplaintResultInfo(result);
	}

	@Override
	public int updateCardholderInfo(String[] ids, int status, String realName) {
		int count = 0;//操作成功条数
		for(int i=0;i< ids.length;i++){
			Complaint info = complaintDao.queryComplaintById(ids[i]);
			if(StringUtils.isEmpty(info) || "1".equals(info.getStatus())){//当状态为已退款，不能操作
				continue;
			}
			info.setStatus(status+"");
			info.setLastUpdateBy(realName);
			int t = complaintDao.updateComplaintInfo(info);
			if(t > 0 ){
				count++;
			}
		}
		return count;
	}
	@Override
	public Complaint queryDisInfoById(String id) {
		return complaintDao.queryDisInfoById(id);
	}
	
	@Override
	public int updateDisCPDDateById(Complaint info) {
		return complaintDao.updateDisCPDDateById(info);
	}
	
	@Override
	public int checkTradeNoInDis(String tradeNo) {
		return complaintDao.checkTradeNoInDis(tradeNo);
	}
	@Override
	public int updateDisOrderToIsCompById(String id) {
		return complaintDao.updateDisOrderToIsCompById(id);
	}
	@Override
	public int updateCompOrderToIsDis(String id) {
		return complaintDao.updateCompOrderToIsDis(id);
	}
	
	@Override
	public PageInfo<WebsiteComplaintInfo> queryWebsiteComplaintInfoList(
			Criteria criteria) {
		PageInfo<WebsiteComplaintInfo> page = new PageInfo<WebsiteComplaintInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(complaintDao.queryWebsiteComplaintInfoCount(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), page.getPageSize());
		List<WebsiteComplaintInfo> list = complaintDao.queryWebsiteComplaintInfoList(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public List<WebsiteComplaintCardHolderInfo> queryWebsiteComplaintCountInfo(
			String type, String payWebSite, String complaintType, String year, String month) {
		return complaintDao.queryWebsiteComplaintCountInfo(type, complaintType, payWebSite, year, month);
	}

	@Override
	public List<WebsiteCardHolderInfo> queryWebsiteCardHodlerInfo(
			String type, String complTradeNo, String complaintType, String year, String month) {
		List<WebsiteCardHolderInfo> cardHolder = complaintDao.queryCardHodlerInfo(complTradeNo);
		if(cardHolder!=null && cardHolder.size()>0){
			for(WebsiteCardHolderInfo info : cardHolder){
				List<WebsiteComplaintCardHolderInfo> list = complaintDao.queryWebsiteCardHodlerInfo(type, complaintType, info.getComplTradeNo(), year, month);
				info.setList(list);
			}
		}
		return cardHolder;
	}

	@Override
	public List<WebsiteComplaintInfo> exportCompWebsiteInfoList(
			Criteria criteria) {
		List<WebsiteComplaintInfo> list = complaintDao.queryWebsiteComplaintInfoList(criteria);
		if(list!=null && list.size()>0){
			for(WebsiteComplaintInfo info : list){
				info.setCount(complaintDao.queryWebsiteComplaintCountInfo((String) criteria.getCondition().get("type"), (String) criteria.getCondition().get("complaintType"), info.getPayWebSite(), (String) criteria.getCondition().get("year"), (String) criteria.getCondition().get("month")));
				List<WebsiteCardHolderInfo> cardHolders = complaintDao.queryCardHodlerInfo(info.getComplTradeNo());
				if(cardHolders!=null && cardHolders.size()>0){
					for(WebsiteCardHolderInfo cardHolder : cardHolders){
						List<WebsiteComplaintCardHolderInfo> com = complaintDao.queryWebsiteCardHodlerInfo((String) criteria.getCondition().get("type"), (String) criteria.getCondition().get("complaintType"), info.getComplTradeNo(), (String) criteria.getCondition().get("year"), (String) criteria.getCondition().get("month"));
						cardHolder.setList(com);
					}
				}
				info.setCardHolder(cardHolders);
			}
		}
		return list;
	}
	

	@Override
	public List<ComplaintType> queryComplaintTypeInfo(Criteria criteria) {
		return complaintTypeDao.queryComplaintTypeInfoList(criteria);
	}
	
}
