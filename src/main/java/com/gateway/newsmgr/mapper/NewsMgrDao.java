package com.gateway.newsmgr.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.newsmgr.model.News;

public interface NewsMgrDao {
	/**
	 * 统计新闻公告条数
	 * */
	public int countNews(Criteria criteria);
	/**
	 *查询新闻公告列表 
	 * */
	public List<News> getListNews(Criteria criteria, RowBounds rb);
	/**
	 * 通过ID查询新闻信息
	 * */
	public News getNewsInfoById(String id);
	/**
	 * 添加新闻公告
	 * */
	public int addNews(News news);
	/**
	 * 修改新闻公告
	 * */
	public int updateNews(News news);
	public int deleteNews(String id);

}
