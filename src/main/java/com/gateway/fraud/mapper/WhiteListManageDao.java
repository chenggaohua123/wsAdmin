package com.gateway.fraud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.fraud.model.WhiteListInfo;

public interface WhiteListManageDao {

	/**
	 * 
	 *查询白名单
	 */
	public List<WhiteListInfo> queryWhiteList(Criteria criteria, RowBounds rb);
	
	/**
	 * 查询导出白名单信息
	 */
	public List<WhiteListInfo> queryWhiteList(Criteria criteria);
	
	/**
	 *统计条数 
	 */
	public int countWhiteListInfo(Criteria criteria);
	
	/**
	 * 白名单添加
	 * 
	 */
	public int addWhiteList(@Param("whiteListInfo")WhiteListInfo whiteListInfo);
	
	/**
	 * 
	 *白名单删除 
	 */
	public int delWhiteList(@Param("id")int id);
	/**
	 * 
	 *白名单修改 
	 */
	public int updateWhiteList(@Param("whiteListInfo")WhiteListInfo whiteListInfo);
	
	/**
	 * 
	 *根据id查询白名单信息 
	 */
	public WhiteListInfo queryWhiteListById(@Param("id")int id);
	
	/**
	 * 查询白名单重复元素
	 */
	public int queryWhiteDupInfoCount(@Param("vo") WhiteListInfo vo);
}
