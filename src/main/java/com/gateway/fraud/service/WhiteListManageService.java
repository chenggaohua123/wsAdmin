package com.gateway.fraud.service;


import java.util.List;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.APIException;
import com.gateway.fraud.model.WhiteListInfo;

public interface WhiteListManageService {
	/**
	 *白名单查询 
	 */
	public PageInfo<WhiteListInfo> queryWhiteList(Criteria criteria);
	
	/**
	 * 查询导出白名单信息
	 */
	public List<WhiteListInfo> queryExportWhiteList(Criteria criteria);
	
	/**
	 * 白名单添加
	 * 
	 */
	public int addWhiteList(WhiteListInfo whiteListInfo);
	
	/**
	 *白名单删除 
	 */
	public int delWhiteList(int id);
	
	/**
	 * 
	 *白名单修改 
	 */
	public int updateWhiteList(WhiteListInfo whiteListInfo);
	
	/**
	 * 
	 *根据id查询白名单信息 
	 * 
	 */
	public WhiteListInfo queryWhiteListById(int id);
	
	/**
	 * 批量删除白名单信息
	 */
	public int deleteWhiteInfoList(String[] ids) throws APIException;
	
	/**
	 * 查询白名单重复元素
	 */
	public int queryWhiteDupInfoCount(WhiteListInfo info);
}
