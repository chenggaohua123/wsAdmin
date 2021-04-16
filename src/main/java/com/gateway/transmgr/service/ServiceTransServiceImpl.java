package com.gateway.transmgr.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.utils.Funcs;
import com.gateway.common.utils.Tools;
import com.gateway.transmgr.mapper.ServiceTransDao;
import com.gateway.transmgr.model.TransInfo;

@Service
public class ServiceTransServiceImpl implements ServiceTransService{
	
	@Autowired
	private ServiceTransDao serviceTransDao;

	@Override
	public PageInfo<TransInfo> queryServiceTransList(Criteria criteria) throws Exception{
		PageInfo<TransInfo> page = new PageInfo<TransInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(serviceTransDao.countServiceTransList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<TransInfo> list = serviceTransDao.queryServiceTransList(criteria, rb);
		for(TransInfo li:list){
			li.setWebInfo(Tools.parseWebInfoToResourceType(li.getWebInfo()));
			li.setCheckNo(Funcs.decrypt(li.getCheckNo()));
			li.setLast(Funcs.decrypt(li.getLast()));
		}
		page.setData(list);
		return page;
	}

}
