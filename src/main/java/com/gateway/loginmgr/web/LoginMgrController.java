package com.gateway.loginmgr.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.framework.config.Constants;
import com.gateway.common.utils.SHA256Utils;
import com.gateway.loginmgr.model.UserInfo;
import com.gateway.loginmgr.service.LoginMgrService;
import com.gateway.sysmgr.model.MenuInfo;
import com.gateway.sysmgr.service.SysMgrService;
@Controller
public class LoginMgrController extends BaseController{
	
	@Resource
	private LoginMgrService loginMgrService;
	
	@Resource
	private SysMgrService sysMgrService;
	
	/**
	 * 跳转到登陆页面
	 * @return
	 */
	@RequestMapping(value="/login")
	public String loginIn(){
		return "login";
	}
	
	@RequestMapping(value="/dologin")
	@ResponseBody
	public UserInfo doLogin(UserInfo user){
		String password=user.getPassWord();
		//判断密码是否符合（密码长度8-16，同时包含大写字母、小写字母、数字、特殊字符）
		//Pattern pattern = Pattern.compile("^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)(?![a-z\\W_!@#$%^&*`~()-+=]+$)(?![0-9\\W_!@#$%^&*`~()-+=]+$)(?![a-zA-Z0-9]+$)(?![a-zA-Z\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9\\W_!@#$%^&*`~()-+=]+$)(?![0-9A-Z\\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\\W_!@#$%^&*`~()-+=]{8,16}$"); 
	    //boolean flag= pattern.matcher(password).matches();
	    //if(!flag){
	    //	return null;
	    //}
		user.setPassWord(SHA256Utils.getSHA256Encryption(user.getPassWord()+user.getUserName()));
		user = loginMgrService.doLogin(user);
		if(null != user){
			getRequest().getSession().setAttribute(Constants.AUTHENTICATION_KEY, user);
		}
		return user;
	}
	
	@RequestMapping(value="loginDialog")
	public String loginDialog(){
		return "loginDialog";
	}
	
	@RequestMapping(value="/doDialogLogin")
	public ModelAndView doDialogLogin(UserInfo user){
		user =  doLogin(user);
		if(null != user){
			return ajaxDoneSuccess("登陆成功","index");
		}else{
			return ajaxDoneError("登陆失败。");
		}
		
	}
	
	@RequestMapping(value="/index")
	public String index(){
		UserInfo user = (UserInfo)getRequest().getSession().getAttribute(Constants.AUTHENTICATION_KEY);
		if(null == user){
			return "login";
		}
		//查询菜单
		List<MenuInfo> listMenus = sysMgrService.queryUserMenuByUserId(user);
		//构建权限map
		Map<String, String> premissionMap = new HashMap<String, String>();
		for(MenuInfo menu:listMenus){
			premissionMap.put(menu.getActionName(), menu.getActionName());
		}
		getRequest().getSession().setAttribute(Constants.USER_PERMISSION, premissionMap);
		getRequest().getSession().setAttribute(Constants.USER_MENU_LIST, listMenus);
		return "index";
	}
	@RequestMapping(value="/logout")
	public String logout(){
		getRequest().getSession().removeAttribute(Constants.AUTHENTICATION_KEY);
		return "login";
	}
}
