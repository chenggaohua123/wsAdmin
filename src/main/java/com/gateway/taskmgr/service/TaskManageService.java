package com.gateway.taskmgr.service;


import org.apache.ibatis.annotations.Param;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.taskmgr.model.CardBinInfo;
import com.gateway.taskmgr.model.TaskInfo;
import com.gateway.taskmgr.model.TaskInfoLog;

public interface TaskManageService {
	/**
	 * 查询所有工作信息列表
	 * @param page
	 * @param conn
	 * @return
	 */
	public PageInfo<TaskInfo> queryTaskInfoList(Criteria criteria);
	
	/**
	 * 添加一条工作信息记录
	 * 
	 * @param taskInfo
	 * @return
	 */
	public int addTaskInfo(@Param("taskInfo") TaskInfo taskInfo);
	
	/**
	 * 根据jobNo查找一条工作记录信息
	 * 
	 * @param jobNo
	 * @return
	 */
	public TaskInfo queryTaskInfoByJobNo(Integer id);
	
	/**
	 * 更新一条工作记录信息
	 * 
	 * @param taskInfo
	 * @return
	 */
	public int updateTaskInfo(@Param("taskInfo") TaskInfo taskInfo);
	
	/**
	 * 查询所有工作信息log列表
	 * @param page
	 * @param conn
	 * @return
	 */
	public PageInfo<TaskInfoLog> queryTaskInfoLogList(Criteria criteria);
	
	/**
	 * 查询银行卡号信息
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public PageInfo<CardBinInfo> queryCardBin(Criteria criteria);

	

	/**
	 * 添加银行卡信息
	 * @param binInfo
	 * @return
	 */
	public int addCardBin(CardBinInfo binInfo);
	
	/**
	 * 修改银行卡信息
	 * @param binInfo
	 * @return
	 */
	public int updateCardBin(CardBinInfo binInfo);
	
	/**
	 * 根据id查询银行卡
	 * @param id
	 * @return
	 */
	public CardBinInfo queryCardBinById(Integer id);
}
