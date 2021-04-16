package com.gateway.transmgr.service;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.transmgr.model.TransInfo;


public interface ServiceTransService {
	public PageInfo<TransInfo> queryServiceTransList(Criteria criteria) throws Exception;
	 
}
