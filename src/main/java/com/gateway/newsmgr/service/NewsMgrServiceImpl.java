package com.gateway.newsmgr.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.newsmgr.mapper.NewsMgrDao;
import com.gateway.newsmgr.model.News;

@Service
public class NewsMgrServiceImpl implements NewsMgrService {
	@Autowired
	private NewsMgrDao newsMgrDao;
	
	@Override
	public PageInfo<News> listNews(Criteria criteria) {
		PageInfo<News> page = new PageInfo<News>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(newsMgrDao.countNews(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<News> list = newsMgrDao.getListNews(criteria, rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public News getNewsInfoById(String id) {
		return newsMgrDao.getNewsInfoById(id);
	}
	@Override
	public int addNews(News news) {
		return newsMgrDao.addNews(news);
	}
	@Override
	public int updateNews(News news) {
		return newsMgrDao.updateNews(news);
	}
	@Override
	public int deleteNews(String id) {
		return newsMgrDao.deleteNews(id);
	}
}
