package com.gateway.taskmgr.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.taskmgr.model.CardBinInfo;
import com.gateway.taskmgr.model.TaskInfo;
import com.gateway.taskmgr.model.TaskInfoLog;
import com.gateway.taskmgr.service.TaskManageService;


@Controller(value="taskmgr")
@RequestMapping(value="/taskmgr")
public class TaskMgrController extends BaseController {

	@Resource
	private TaskManageService taskManageService;

	public TaskManageService getTaskManageService() {
		return taskManageService;
	}
	public void setTaskManageService(TaskManageService taskManageService) {
		this.taskManageService = taskManageService;
	}

	/**
	 * 查询工作信息列表
	 * 
	 * @param conn
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryTaskInfoList")
	public String queryTaskInfoList() {
			PageInfo<TaskInfo> page = taskManageService.queryTaskInfoList(getCriteria());
			getRequest().setAttribute("page", page);
			return "taskmgr/taskInfoList";
	}
	
	/**
	 * 跳转到添加工作记录页面
	 * 
	 * @param model
	 * @return 
	 */
	@RequestMapping(value = "/goAddTaskInfo")
	public String addTaskInfo(Model model) {
		getRequest().setAttribute("taskInfo", new TaskInfo());
		return "taskmgr/addTaskInfo";
	}

	/**
	 * 添加工作记录并提交，返回工作信息列表页面
	 * 
	 * @param bankInfo
	 * @return br,resp
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/addTaskInfo")
	public ModelAndView addTaskInfo(@Validated TaskInfo taskInfo, BindingResult br,
			HttpServletResponse resp) {
		Map<String, String> retMap = new HashMap<String, String>();
		if (br.hasErrors()) {
			List<FieldError> s = br.getFieldErrors();
			Iterator<FieldError> it = s.iterator();
			String str = "";
			while (it.hasNext()) {
				str = str + " " + it.next().getDefaultMessage();
			}
			return ajaxDoneSuccess(str);
		} else {
			int i = taskManageService.addTaskInfo(taskInfo);
			if (i > 0) {
				return ajaxDoneSuccess("添加成功。");
			} else {
				return ajaxDoneError("添加失败");
			}
		}
	}

	/**
	 * 根据工作编号查询一条工作记录的具体信息，准备做修改
	 * 
	 * @param jobNo
	 * @return
	 */
	@RequestMapping(value = "/goUpdateTaskInfo")
	public String updateTaskInfo(Integer id) {
		TaskInfo taskInfo = taskManageService.queryTaskInfoByJobNo(id);
		getRequest().setAttribute("taskInfo", taskInfo);
		return "taskmgr/updateTaskInfo";
	}

	/**
	 * 对查询出来的工作记录的具体信息进行修改
	 * 
	 * @param taskInfo
	 * @param br
	 * @param resp
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/updateTaskInfo", method = RequestMethod.POST)
	public ModelAndView updateTaskInfo(@Validated TaskInfo taskInfo, BindingResult br,
			HttpServletResponse resp) {
		Map<String, String> retMap = new HashMap<String, String>();
		if (br.hasErrors()) {
			List<ObjectError> s = br.getAllErrors();
			Iterator<ObjectError> it = s.iterator();
			String str = "";
			while (it.hasNext()) {
				str = str + " " + it.next().getDefaultMessage();
			}
			return ajaxDoneSuccess(str);
		} else {
			int i = taskManageService.updateTaskInfo(taskInfo);
			if (i > 0) {
				return ajaxDoneSuccess("添加成功。");
			} else {
				return ajaxDoneError("添加失败");
			}
		}
	}
	
	/**
	 * 查询工作信息log列表
	 * 
	 * @param conn
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryTaskInfoLogList")
	public String queryTaskInfoLogList() {
		PageInfo<TaskInfoLog> list = taskManageService.queryTaskInfoLogList(getCriteria());
		getRequest().setAttribute("page", list);
		return "taskmgr/taskInfoLogList";
	}
	
	
	
	/**
	 * 查询银行卡号
	 * @return
	 */
	@RequestMapping(value="/queryCardBin")
	public String queryCardBin(){
		PageInfo<CardBinInfo> list = taskManageService.queryCardBin(getCriteria());
		getRequest().setAttribute("page", list);
		return "taskmgr/cardBinInfoList";
	}
	
	/**
	 * 跳转添加银行卡页面
	 * @return
	 */
	@RequestMapping(value="/goAddCardInfo")
	public String goAddCardInfo(){
		return "taskmgr/addCardInfo";
	}
	
	/**
	 * 添加银行卡信息
	 * @param binInfo
	 * @return
	 */
	@RequestMapping(value="/addCardInfo")
	public ModelAndView addCardInfo(CardBinInfo binInfo){
		int i = taskManageService.addCardBin(binInfo);
		if (i > 0) {
			return ajaxDoneSuccess("添加成功。");
		} else {
			return ajaxDoneError("添加失败");
		}
	}
	
	/**
	 * 跳转修改银行卡号页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/goUpdateCardInfo")
	public String goUpdateCardInfo(Integer id){
		CardBinInfo binInfo=taskManageService.queryCardBinById(id);
		getRequest().setAttribute("binInfo", binInfo);
		return "taskmgr/updateCardInfo";
	}
	
	/**
	 * 修改银行卡号
	 * @param binInfo
	 * @return
	 */
	@RequestMapping(value="/updateCardInfo")
	public ModelAndView updateCardInfo(CardBinInfo binInfo){
		int i = taskManageService.updateCardBin(binInfo);
		if (i > 0) {
			return ajaxDoneSuccess("修改成功。");
		} else {
			return ajaxDoneError("修改失败");
		}
	}
}
