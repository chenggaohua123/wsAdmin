package com.gateway.newsmgr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.ServiceException;
import com.gateway.newsmgr.model.News;
import com.gateway.newsmgr.service.NewsMgrService;
@Controller
@RequestMapping(value="/newsmgr")
public class NewsMgrController extends BaseController {
	@Autowired
	private NewsMgrService newsMgrServiceImpl;
	
	/**
	 * 列表显示新闻公告
	 * */
	@RequestMapping(value="/listNews")
	public String listNews(){
		PageInfo<News> page=newsMgrServiceImpl.listNews(getCriteria());
		getRequest().setAttribute("page", page);
 		return "newsmgr/listNews";
	}
	/**
	 * 跳转到添加新闻公告
	 * */
	@RequestMapping(value="/goAddNews")
	public String goAddNews(){
		return "newsmgr/addNews";
	}
	/**
	 * 添加新闻公告
	 * */
	@RequestMapping(value="/addNews")
	public ModelAndView addNews(News news){
		news.setCreateBy(getLogAccount().getRealName());
		int i=newsMgrServiceImpl.addNews(news);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	/**
	 * 跳转到修改新闻公告
	 * @throws ServiceException 
	 * */
	@RequestMapping(value="/goUpdateNews")
	public String goUpdateNews(String id) throws ServiceException{
		News news=newsMgrServiceImpl.getNewsInfoById(id);
		if(news.getEnableFlag()==1){
			throw new ServiceException("不能修改已发布的新闻");
		}
		getRequest().setAttribute("info", news);
		return "newsmgr/updateNews";
	}
	/**
	 * 修改新闻公告
	 * */
	@RequestMapping(value="/updateNews")
	public ModelAndView updateNews(News news){
		news.setLastModify(getLogAccount().getRealName());
		int i=newsMgrServiceImpl.updateNews(news);
		if(i>0){
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	/**
	 * 查看新闻
	 * */
	@RequestMapping(value="/showNews")
	public String showNews(String id){
		News news=newsMgrServiceImpl.getNewsInfoById(id);
		getRequest().setAttribute("info", news);
		return "newsmgr/showNews";
	}
	@RequestMapping(value="/deleteNews")
	public ModelAndView deleteNews(String id){
		int i=newsMgrServiceImpl.deleteNews(id);
		if(i>0){
			return ajaxDoneSuccess("删除成功");
		}else{
			return ajaxDoneError("删除失败");
		}
	}
}

