package com.gateway.taskmgr.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.taskmgr.mapper.TaskManageDao;
import com.gateway.taskmgr.model.CardBinInfo;
import com.gateway.taskmgr.model.TaskInfo;
import com.gateway.taskmgr.model.TaskInfoLog;

@Service
public class TaskManageServiceImpl implements TaskManageService{
	@Autowired
	private TaskManageDao taskManageDao;

	public TaskManageDao getTaskManageDao() {
		return taskManageDao;
	}

	public void setTaskManageDao(TaskManageDao taskManageDao) {
		this.taskManageDao = taskManageDao;
	}

	@Override
	public PageInfo<TaskInfo> queryTaskInfoList(Criteria criteria) {
		PageInfo<TaskInfo> page = new PageInfo<TaskInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(taskManageDao.countTaskInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<TaskInfo> list = taskManageDao.queryTaskInfoList(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public PageInfo<TaskInfoLog> queryTaskInfoLogList(Criteria criteria) {
		PageInfo<TaskInfoLog> page = new PageInfo<TaskInfoLog>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(taskManageDao.countTaskExcuLog(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<TaskInfoLog> list = taskManageDao.queryTaskInfoLogList(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public int addTaskInfo(TaskInfo taskInfo) {
		return taskManageDao.addTaskInfo(taskInfo);
	}

	@Override
	public TaskInfo queryTaskInfoByJobNo(Integer id) {
		return taskManageDao.queryTaskInfoByJobNo(id);
	}

	@Override
	public int updateTaskInfo(TaskInfo taskInfo) {
		return taskManageDao.updateTaskInfo(taskInfo);
	}

	@Override
	public PageInfo<CardBinInfo> queryCardBin(Criteria criteria) {
		PageInfo<CardBinInfo> page = new PageInfo<CardBinInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(taskManageDao.countCardBin(criteria));
		RowBounds br = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<CardBinInfo> list = taskManageDao.queryCardBin(criteria, br);
		page.setData(list);
		return page;
	}

	@Override
	public int addCardBin(CardBinInfo binInfo) {
		return taskManageDao.addCardBin(binInfo);
	}

	@Override
	public int updateCardBin(CardBinInfo binInfo) {
		return taskManageDao.updateCardBin(binInfo);
	}

	@Override
	public CardBinInfo queryCardBinById(Integer id) {
		return taskManageDao.queryCardBinById(id);
	}
	
	
	
}
