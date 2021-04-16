package com.gateway.transmgr.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.transmgr.model.TransInfo;


public interface ServiceTransDao {
	
	/** 查询列表 */
	public List<TransInfo> queryServiceTransList(Criteria criteria,RowBounds rd);
	public int countServiceTransList(Criteria criteria);
	
}
