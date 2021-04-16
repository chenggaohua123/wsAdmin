package com.gateway.complaint.service;

import java.util.List;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.complaint.model.Complaint;
import com.gateway.complaint.model.ComplaintResult;
import com.gateway.complaint.model.ComplaintType;
import com.gateway.complaint.model.WebsiteCardHolderInfo;
import com.gateway.complaint.model.WebsiteComplaintCardHolderInfo;
import com.gateway.complaint.model.WebsiteComplaintInfo;



public interface ComplaintService {
	/** 调查单列表 */
	public PageInfo<Complaint> queryListComplaintInfo(Criteria criteria);
	public List<Complaint> queryListComplaintInfoList(Criteria criteria);
	/** 添加调查单 */
	public String addComplaintInfo(Complaint info);
	
	/** 通过ID查询投诉信息 */
	public Complaint queryComplaintById(String id);
	
	/** 投诉类型列表 */
	public PageInfo<ComplaintType> queryListComplaintTypeInfo(Criteria criteria);
	
	/** 保存投诉类型 */
	public int addComplaintTypeInfo(ComplaintType info);
	
	/** 通过ID查询投诉类型 */
	public ComplaintType queryComplaintTypeInfoId(String id);
	
	/** 通过ID，类型查询投诉类型 */
	public int countComplaintTypeByIdAndType(Complaint complaint);
	
	/** 修改投诉类型 */
	public int updateComplaintTypeInfo(ComplaintType info);
	
	/** 批量受理调查单 */
	public int updateComplaintInfo(String[] ids,int status,String updateBy);
	
	/** 批量修改拒付单 */
	public int updateDishonorInfo(String[] ids, int status, String updateBy);

	/** 根据ID，systemID查询投诉单处理结果 */
	public List<ComplaintResult> queryComplaintResultInfo(ComplaintResult result);
	
	public int addComplaintResultInfo(ComplaintResult result);
	
	/** 批量修改操作 */
	public int updateCardholderInfo(String[] ids, int status, String realName);
	/**
	 * 通过Id查询拒付单信息
	 * @param id
	 * @return
	 */
	public Complaint queryDisInfoById(String id);
	/**
	 * 通过Id修改CPD时间
	 * @param info
	 * @return
	 */
	public int updateDisCPDDateById(Complaint info);
	/**
	 * 查询流水号是否存在拒付列表中
	 * @param tradeNo
	 * @return
	 */
	public int checkTradeNoInDis(String tradeNo);
	/**
	 * 标记订单为已申诉
	 * @param id
	 * @return
	 */
	public int updateDisOrderToIsCompById(String id);
	
	/**
	 * 标记调查订单为已拒付
	 */
	public int updateCompOrderToIsDis(String id);
	
	/**
	 * 查询投诉单网址信息
	 */
	public PageInfo<WebsiteComplaintInfo> queryWebsiteComplaintInfoList(Criteria criteria);
	
	/**
	 * 查询商户投诉网址投诉类型划分情况
	 */
	public List<WebsiteComplaintCardHolderInfo> queryWebsiteComplaintCountInfo(String type, String payWebSite, String complaintType, String year, String month);
	
	/**
	 * 查询投诉人投诉历史记录
	 */
	public List<WebsiteCardHolderInfo> queryWebsiteCardHodlerInfo(String type, String complTradeNo, String complaintType, String year, String month);
	
	/**
	 * 导出统计商户投诉网址信息
	 */
	public List<WebsiteComplaintInfo> exportCompWebsiteInfoList(Criteria criteria);
	
	/**
	 * 查询调查单类型
	 */
	public List<ComplaintType> queryComplaintTypeInfo(Criteria criteria);
}
