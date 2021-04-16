package com.gateway.complaint.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.complaint.model.Complaint;
import com.gateway.complaint.model.ComplaintResult;
import com.gateway.complaint.model.WebsiteCardHolderInfo;
import com.gateway.complaint.model.WebsiteComplaintCardHolderInfo;
import com.gateway.complaint.model.WebsiteComplaintInfo;

public interface ComplaintDao {
	/**
	 * 查询调查单列表
	 * */
	public List<Complaint> queryComplaintInfoList(Criteria criteria);
	public List<Complaint> queryComplaintInfoList(Criteria criteria,RowBounds rd);
	
	/** 统计调查单列表 */
	public int countComplaintInfoList(Criteria criteria);
	
	/** 添加调查单 */
	public int addComplaintInfo(Complaint info);
	
	/** 根据类型，订单号，查询重复单 */
	public Complaint queryComplaintTypeInfo(Complaint info);
	
	public Complaint queryComplaintById(String id);
	
	/** 修改调查单 */
	public int updateComplaintInfo(Complaint info);
	
	/** 通过ID，类型查询投诉类型 */
	public int countComplaintTypeByIdAndType(Complaint complaint);
	
	/**
	 * 通过ID查询处理结果
	 * */
	public List<ComplaintResult> queryComplaintResultInfo(ComplaintResult result);
	
	/** 添加处理结果 */
	public int addComplaintResultInfo(ComplaintResult result);
	/**
	 * 通过id查询拒付单信息
	 * @param id
	 * @return
	 */
	public Complaint queryDisInfoById(String id);
	/**
	 * 通过Id修改CPD时间
	 * @param info
	 * @return
	 */
	public int updateDisCPDDateById(@Param("info")Complaint info);
	/**
	 * 查询流水号是否在拒付列表中
	 * @param tradeNo
	 * @return
	 */
	public int checkTradeNoInDis(String tradeNo);
	public int updateDisOrderToIsCompById(String id);
	public int updateCompOrderToIsDis(String id);
	
	/**
	 * 查询投诉单网址信息
	 */
	public List<WebsiteComplaintInfo> queryWebsiteComplaintInfoList(Criteria criteria, RowBounds rb);
	
	/**
	 * 查询投诉单网址信息总数
	 */
	public int queryWebsiteComplaintInfoCount(Criteria criteria);
	
	/**
	 * 查询商户投诉网址投诉类型划分情况
	 */
	public List<WebsiteComplaintCardHolderInfo> queryWebsiteComplaintCountInfo(@Param("type") String type, @Param("complaintType") String complaintType, @Param("payWebSite") String payWebSite, @Param("year") String year, @Param("month") String month);
	
	/**
	 * 查询投诉人投诉历史记录
	 */
	public List<WebsiteComplaintCardHolderInfo> queryWebsiteCardHodlerInfo(@Param("type") String type, @Param("complaintType") String complaintType, @Param("complTradeNo") String complTradeNo, @Param("year") String year, @Param("month") String month);
	
	/**
	 * 查询投诉持卡人名字
	 */
	public List<WebsiteCardHolderInfo> queryCardHodlerInfo(@Param("complTradeNo") String complTradeNo); 
	
	/**
	 * 查询导出投诉单网址信息
	 */
	public List<WebsiteComplaintInfo> queryWebsiteComplaintInfoList(Criteria criteria);
}
