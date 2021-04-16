package com.gateway.complaint.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.complaint.model.ComplaintType;

public interface ComplaintTypeDao {
	/**
	 * 查询调查单列表
	 * */
	public List<ComplaintType> queryComplaintTypeInfoList(Criteria criteria);
	public List<ComplaintType> queryComplaintTypeInfoList(Criteria criteria,RowBounds rd);
	
	/** 统计调查单列表 */
	public int countComplaintTypeInfoList(Criteria criteria);
	
	/** 保存投诉类型 */
	public int addComplaintTypeInfo(ComplaintType info);
	
	/** 通过ID查询投诉类型 */
	public ComplaintType queryComplaintTypeInfoId(@Param("id") String id);
	
	/** 修改投诉类型 */
	public int updateComplaintTypeInfo(ComplaintType info);
	
	
}
