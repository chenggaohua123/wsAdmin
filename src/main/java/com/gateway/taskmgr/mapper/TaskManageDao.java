package com.gateway.taskmgr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.taskmgr.model.CardBinInfo;
import com.gateway.taskmgr.model.TaskInfo;
import com.gateway.taskmgr.model.TaskInfoLog;

public interface TaskManageDao {
	/**
	 * 查询所有工作信息列表
	 * 
	 * @param page
	 * @param conn
	 * @return
	 */
	public List<TaskInfo> queryTaskInfoList(Criteria criteria,RowBounds rb);
	
	public int countTaskInfo(Criteria criteria);

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
	public TaskInfo queryTaskInfoByJobNo(@Param("id") Integer id);
	
	/**
	 * 更新一条工作记录信息
	 * 
	 * @param taskInfo
	 * @return
	 */
	public int updateTaskInfo(@Param("taskInfo") TaskInfo taskInfo);
	
	/**
	 * 查询所有工作信息log列表
	 * 
	 * @param page
	 * @param conn
	 * @return
	 */
	public List<TaskInfoLog> queryTaskInfoLogList(Criteria criteria,RowBounds rb);

	public int countTaskExcuLog(Criteria criteria);
	
	/**
	 * 查询银行卡号信息
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<CardBinInfo> queryCardBin(Criteria criteria,RowBounds rb);
	
	public int countCardBin(Criteria criteria);
	
	/**
	 * 添加银行卡信息
	 * @param binInfo
	 * @return
	 */
	public int addCardBin(@Param("card")CardBinInfo binInfo);
	
	/**
	 * 修改银行卡信息
	 * @param binInfo
	 * @return
	 */
	public int updateCardBin(@Param("card")CardBinInfo binInfo);
	
	/**
	 * 根据id查询银行卡
	 * @param id
	 * @return
	 */
	public CardBinInfo queryCardBinById(@Param("id")Integer id);
}
