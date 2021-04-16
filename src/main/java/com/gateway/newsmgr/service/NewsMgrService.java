package com.gateway.newsmgr.service;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.newsmgr.model.News;

public interface NewsMgrService {
	/**
	 * 查询新闻公告列表
	 * */
	public PageInfo<News> listNews(Criteria criteria);
	/**
	 * 查看新闻
	 * */
	public News getNewsInfoById(String id);
	/**
	 * 添加新闻
	 * */
	public int addNews(News news);
	/**
	 * 修改新闻公告
	 * */
	public int updateNews(News news);
	/**
	 * 通过Id删除新闻
	 * @param id
	 * @return
	 */
	public int deleteNews(String id);

}
