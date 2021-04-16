package com.gateway.fraud.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.APIException;
import com.gateway.fraud.mapper.WhiteListManageDao;
import com.gateway.fraud.model.WhiteListInfo;

@Service
public class WhiteListManageServiceImpl implements WhiteListManageService{
	@Autowired
	private WhiteListManageDao whiteListManageDao;

	@Override
	public PageInfo<WhiteListInfo> queryWhiteList(Criteria criteria) {
		PageInfo<WhiteListInfo> page = new PageInfo<WhiteListInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(whiteListManageDao.countWhiteListInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		page.setData(whiteListManageDao.queryWhiteList(criteria, rb));
		return page;
		
	}
	/**
	 *白名单添加 
	 */
	@Override
	public int addWhiteList(WhiteListInfo whiteListInfo) {
		
		return whiteListManageDao.addWhiteList(whiteListInfo);
	}
	/**
	 * 
	 *白名单删除 
	 */
	@Override
	public int delWhiteList(int id) {
		return whiteListManageDao.delWhiteList(id);
	}
	/**
	 * 
	 * 白名单修改
	 */
	@Override
	public int updateWhiteList(WhiteListInfo whiteListInfo) {
		return whiteListManageDao.updateWhiteList(whiteListInfo);
	}
	@Override
	public WhiteListInfo queryWhiteListById(int id) {
		
		return whiteListManageDao.queryWhiteListById(id);
	}
	@Override
	public List<WhiteListInfo> queryExportWhiteList(Criteria criteria) {
		return whiteListManageDao.queryWhiteList(criteria);
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int deleteWhiteInfoList(String[] ids) throws APIException {
		int a = -1;
		for(String id : ids){
			a = whiteListManageDao.delWhiteList(Integer.parseInt(id));
			if(!(a>0)){
				throw new APIException("删除失败");
			}
		}
		return a;
	}
	@Override
	public int queryWhiteDupInfoCount(WhiteListInfo info) {
		return whiteListManageDao.queryWhiteDupInfoCount(info);
	}
}
