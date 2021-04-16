package com.gateway.sysmgr.web;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jpos.iso.ISOException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.BaseDataListener;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.APIException;
import com.gateway.common.excetion.ServiceException;
import com.gateway.common.utils.Funcs;
import com.gateway.common.utils.GoogleAuthenticator;
import com.gateway.common.utils.SHA256Utils;
import com.gateway.common.utils.Tools;
import com.gateway.loginmgr.model.UserInfo;
import com.gateway.merchantmgr.model.AgentInfo;
import com.gateway.merchantmgr.service.MerchantMgrService;
import com.gateway.settlemgr.model.MerchantAccountAccess;
import com.gateway.sysmgr.model.BankRespMsgMgrInfo;
import com.gateway.sysmgr.model.BaseDataInfo;
import com.gateway.sysmgr.model.CardBinInfo;
import com.gateway.sysmgr.model.CheckToNoInfo;
import com.gateway.sysmgr.model.MenuInfo;
import com.gateway.sysmgr.model.RoleInfo;
import com.gateway.sysmgr.model.RoleRefMenuInfo;
import com.gateway.sysmgr.model.TransInfo;
import com.gateway.sysmgr.model.UserRelAgent;
import com.gateway.sysmgr.service.SysMgrService;

@Controller(value="sysmgr")
@RequestMapping(value="/sysmgr")
public class SysMgrController extends BaseController{
	@Resource
	private SysMgrService sysMgrService;
	
	@Resource
	private MerchantMgrService merchantMgrService;
	
	@RequestMapping(value="/getuserlist")
	public String getuserList(){
		Criteria criteria = getCriteria();
		PageInfo<UserInfo> page = sysMgrService.queryPageUser(criteria);
		getRequest().setAttribute("page", page);
		return "sysmgr/userlist";
	}
	
	@RequestMapping(value="/goAddUserInfo")
	public String addUserInfo(){
		return "sysmgr/addUserInfo";
	}
	
	@RequestMapping(value="/addUserInfo")
	public ModelAndView addUserInfo(UserInfo user){
		user.setCreateBy(getLogAccount().getRealName());
		user.setEnabled(1);
		user.setPassWord(SHA256Utils.getSHA256Encryption(user.getPassWord()+user.getUserName()));
		if("1".equals(user.getSystemId())){//用户的所属 1管理后台 2商户后台
			if("0".equals(user.getVerificationType())){//登录方式为0手机动态码登录
				//更新身份验证器secretKey
				user.setVerificationCode(GoogleAuthenticator.generateSecretKey(user.getSeed()));//更新google身份验证
			}
		}
		int i=sysMgrService.addUserInfo(user);
		if(i>0){
			return ajaxDoneSuccess("插入成功");
		}else{
			return ajaxDoneSuccess("插入失败");
		}
	}
	
	/**
	 * 查询可关联角色
	 * @param id
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/goreleInfo")
	public String goreleInfo(int id) throws ServiceException{
		UserInfo userInfo = sysMgrService.queryUserInfoById(id);
		if(null == userInfo){
			throw new ServiceException("该用户不存在。");
		}
		criteria = getCriteria();
		criteria.getCondition().put("systemId", userInfo.getSystemId());
		PageInfo<RoleInfo> page=sysMgrService.queryPageRole(criteria);
		List<RoleInfo> list=sysMgrService.queryPitchRole(userInfo.getId());
		getRequest().setAttribute("page",page);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("userId", userInfo.getId());
		return "sysmgr/roleRelevList";
	}
	
	/**
	 * 关联角色
	 * @param info
	 * @return
	 */
	@RequestMapping(value="/releRoleinfo")
	public ModelAndView releRoleinfo(int userId,String [] roleid){
		if(null!=roleid &&  roleid.length>0 && 0!= userId ){
			sysMgrService.addRoleRefUserInfo(roleid, userId);
			return ajaxDoneSuccess("保存成功");
		}else{
			return ajaxDoneSuccess("保存失败");
		}
		
	}
	
	
	@RequestMapping(value="/goUpdateUserInfo")
	public String updateUserInfo(int id){
		UserInfo userInfo=sysMgrService.queryUserInfoById(id);
		getRequest().setAttribute("userInfo", userInfo);
		return "sysmgr/updateUserInfo";
	}
	
	@RequestMapping(value="/updateUserInfo")
	public ModelAndView updateUserInfo(UserInfo user){
		String pWord = user.getPassWord();
		if(StringUtils.isEmpty(pWord) || "******".equals(pWord)){
			user.setPassWord(null);
		}else{
			//判断密码是否符合（密码长度8-16，同时包含大写字母、小写字母、数字、特殊字符）
			Pattern pattern = Pattern.compile("^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)(?![a-z\\W_!@#$%^&*`~()-+=]+$)(?![0-9\\W_!@#$%^&*`~()-+=]+$)(?![a-zA-Z0-9]+$)(?![a-zA-Z\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9\\W_!@#$%^&*`~()-+=]+$)(?![0-9A-Z\\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\\W_!@#$%^&*`~()-+=]{8,16}$"); 
		    boolean flag= pattern.matcher(pWord).matches();
		    if(!flag){
		    	return ajaxDoneError("密码长度8-16，同时包含大写字母、小写字母、数字、特殊字符,例：Abc@12345");
		    }
		}
		user.setCreateBy(getLogAccount().getRealName());
		if(null !=user.getPassWord() && !"".equals(user.getPassWord())){
			user.setPassWord(SHA256Utils.getSHA256Encryption(user.getPassWord()+user.getUserName()));
		}
		if("1".equals(user.getSystemId())){//用户的所属 1管理后台 2商户后台
				if("0".equals(user.getVerificationType())){//登录方式为0手机动态码登录
					//更新身份验证器secretKey
					user.setVerificationCode(GoogleAuthenticator.generateSecretKey(user.getSeed()));//更新google身份验证
				}
		}
		int i=sysMgrService.updateUserInfo(user);
		if(i>0){
			return ajaxDoneSuccess("更新成功");
		}else{
			return ajaxDoneError("更新失败");
		}
		
	}
	
	
	
	@RequestMapping(value="/getRoleList")
	public String getRoleList(){
		PageInfo<RoleInfo> page = sysMgrService.queryPageRole(getCriteria());
		getRequest().setAttribute("page", page);
		return "sysmgr/roleList";
	}
	
	/**
	 * 跳转添加角色页面
	 * @return
	 */
	@RequestMapping(value="/goaddRoleList")
	public String goaddRoleList(){
		return "sysmgr/addRoleInfo";
	}
	
	@RequestMapping(value="/addRoleList")
	public ModelAndView addRoleList(RoleInfo roleInfo){
		roleInfo.setCreateBy(getLogAccount().getRealName());
		roleInfo.setEnabled(1);
		int i=sysMgrService.addSysRoleInfo(roleInfo);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	/**
	 * 修改角色
	 * @return
	 */
	@RequestMapping(value="/goupdataddRole")
	public String goupdataddRole(int id){
		RoleInfo roleInfo=sysMgrService.queryRoleById(id);
		getRequest().setAttribute("roleInfo", roleInfo);
		return "sysmgr/updateAddRoleInfo";
	}
	
	@RequestMapping(value="/updateAddRole")
	public ModelAndView updateAddRole(RoleInfo roleInfo){
		roleInfo.setCreateBy(getLogAccount().getRealName());
		int i=sysMgrService.updateRoleInfo(roleInfo);
		if(i>0){
			return ajaxDoneSuccess("更新成功");
		}else{
			return ajaxDoneError("更新失败");
		}
	}
	
	/**
	 * 角色关联权限
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/addMenuToRole")
	public String goAddMenuToRole(int roleId) throws ServiceException{
		RoleInfo roleInfo =  sysMgrService.queryRoleById(roleId);
		if(null == roleInfo){
			throw new ServiceException("该角色不存在。");
		}
		criteria = getCriteria();
		criteria.getCondition().put("systemId", roleInfo.getSystemId());
		//查询系统中所有的菜单
		List<MenuInfo> menuList = sysMgrService.queryMenuList(criteria);
		//查询当前用户所有的权限
		List<RoleRefMenuInfo> userMenuList = sysMgrService.queryMenuNoByRoleId(roleId);
		getRequest().setAttribute("menuList", menuList);
		getRequest().setAttribute("userMenuList", userMenuList);
		getRequest().setAttribute("roleId", roleId);
		return "sysmgr/addMenuToRole";
	}
	
	/**
	 * 保存角色关联权限信息
	 * @param menuNo
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="/saveAddMenuToRole")
	public ModelAndView addMenuToRole(String [] menuNo,int roleId){
		if(null != menuNo && menuNo.length > 0 && 0 != roleId){
			sysMgrService.saveMenuToRole(menuNo, roleId);
			return ajaxDoneSuccess("关联成功");
		}else{
			return ajaxDoneError("关联失败。");
		}
	}
	
	public SysMgrService getSysMgrService() {
		return sysMgrService;
	}
	public void setSysMgrService(SysMgrService sysMgrService) {
		this.sysMgrService = sysMgrService;
	}
	
	/**
	 * 获取菜单列表
	 * @return
	 */
	@RequestMapping(value="/getMenuList")
	public String getMenuList(){
		List<MenuInfo> list = sysMgrService.queryMenuList(getCriteria());
		Map<Integer, List<MenuInfo>> menuMap = new HashMap<Integer, List<MenuInfo>>();
		for(MenuInfo menu : list){
			List<MenuInfo> temp = menuMap.get(menu.getSystemId());
			if(null == temp ){
				temp = new ArrayList<MenuInfo>();
				menuMap.put(menu.getSystemId(), temp);
			}
			temp.add(menu);
		}
		getRequest().setAttribute("menuMap", menuMap);
		return "sysmgr/menuList";
	}
	
	/**
	 * 跳转到添加菜单页面
	 * @param parentNo
	 * @return
	 */
	@RequestMapping(value="/goAddMenu")
	public String goAddMenu(String parentNo,int systemId){
		int menuNo = sysMgrService.queryMaxMenuNo();
		getRequest().setAttribute("parentNo", parentNo);
		getRequest().setAttribute("menuNo", menuNo);
		getRequest().setAttribute("systemId", systemId);
		return "sysmgr/addMenu";
	}
	
	/**
	 * 添加菜单信息
	 * @param menu
	 * @return
	 */
	@RequestMapping(value="/addMenuInfo")
	public ModelAndView addMenuInfo(MenuInfo menu){
		int count = sysMgrService.addMenuInfo(menu);
		if(count > 0){
			return ajaxDoneSuccess("添加成功。");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	@RequestMapping(value="/getBaseDataList")
	public String getBaseDataList(){
		PageInfo<BaseDataInfo> page = sysMgrService.getBaseDataList(getCriteria());
		getRequest().setAttribute("page", page);
		return "sysmgr/baseDateList";
	}
	
	/**
	 * 添加字典数据
	 * @return
	 */
	@RequestMapping(value="/goAddBaseDataInfo")
	public String goAddBaseDataInfo(){
		return "sysmgr/addBaseDateInfo";
	}
	
	/**
	 * 添加字典数据
	 * @param info
	 * @return
	 */
	@RequestMapping(value="/addBaseDataInfo")
	public ModelAndView addBaseDataInfo(BaseDataInfo info){
		info.setCreateBy(getLogAccount().getRealName());
		int count = sysMgrService.addBaseDataInfo(info);
		if(count > 0){
			return ajaxDoneSuccess("添加成功。");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	@RequestMapping(value="/goUpdateBaseDateInfo")
	public String goUpdateBaseDateInfo(int id){
		BaseDataInfo baseInfo = sysMgrService.queryBaseDateInfoById(id);
		getRequest().setAttribute("baseInfo", baseInfo);
		return "sysmgr/updateBaseDateInfo";
	}
	
	@RequestMapping(value="/updateBaseDateInfo")
	public ModelAndView updateBaseDateInfo(BaseDataInfo info){
		info.setCreateBy(getLogAccount().getRealName());
		int count = sysMgrService.updateBaseDateInfo(info);
		if(count > 0){
			return ajaxDoneSuccess("添加成功。");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	/**
	 * 基础数据查找带回
	 * @return
	 */
	@RequestMapping(value="/baseDateBrighBack")
	public String baseDateBrighBack(){
		PageInfo<BaseDataInfo> page = sysMgrService.getBaseDataList(getCriteria());
		getRequest().setAttribute("page", page);
		return "sysmgr/baseDataBrighBack";
	}
	
	/**
	 * 查询字典数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryBaseDateInfo")
	public List<BaseDataInfo> queryBaseDateInfo(){
		List<BaseDataInfo> list = BaseDataListener.queryBaseDateByTableName((String)getCriteria().getCondition().get("tableName"));
		if(null == list){
			list = sysMgrService.queryBaseDataList(getCriteria());
		}
		return list;
	}
	
	/**
	 * 查询用户详细信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/queryIDCordInfo")
	public String queryIDCordInfo(int id){
		UserInfo info=sysMgrService.querySysUserInfoBusInfo(id);
		getRequest().setAttribute("userInfo", info);
		getRequest().setAttribute("qrName", SHA256Utils.getSHA256Encryption(info.getUserName()));
		return "sysmgr/queryIDCordInfo";
	}
	
	/**
	 * 跳转上传图片信息
	 * @return
	 */
	@RequestMapping(value="/uploadPicFile")
	public String uploadPicFile(){
		return "sysmgr/uploadPicFile";
	}
	
	/**
	 * 添加图片信息
	 * @return
	 */
	@RequestMapping(value="/acceptPicFile")
	public ModelAndView acceptPicFile(DefaultMultipartHttpServletRequest request){
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		if(null != files){
			log.info("上传了"+files.size()+"个文件。");
		}
		return ajaxDoneSuccess("上传成功。");
	} 
	
	/**
	 * 跳转用户关联代理商页面
	 * @return
	 */
	@RequestMapping(value="/getAddUserRelAgentGo")
	public String getAddUserRelAgentGo(int id)throws Exception{
		UserInfo info=sysMgrService.querySysUserInfoBusInfo(id);
		if(info==null || !"2".equals(info.getSystemId())){
			throw new ServiceException("请选择代理商户号关联");
		}
		getRequest().setAttribute("id", id);
		return "sysmgr/addUserRelAgent";
	}
	
	/**
	 * 用户关联代理商查找带回
	 * @return
	 */
	@RequestMapping(value="/userRelAgetnBack")
	public String userRelAgetnBack(){
		PageInfo<AgentInfo> page = merchantMgrService.getAgentList(getCriteria());
		getRequest().setAttribute("page", page);
		return "sysmgr/userRelAgentBack";
	}
	
	
	
	/**
	 * 用户关联代理商
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/addUserRelAgent")
	public ModelAndView addUserRelAgent(int id,String agentNo){
		UserRelAgent userRelAgent=sysMgrService.queryUserRelAgent(id);
		if(userRelAgent!=null){
			return ajaxDoneError("不能重复关联");
		}
		UserRelAgent relAgent=new UserRelAgent();
		relAgent.setAgentNo(agentNo);
		relAgent.setUserId(id);
		relAgent.setCreateBy(getLogAccount().getRealName());
		sysMgrService.addUserRelAgent(relAgent);
		
		return ajaxDoneSuccess("关联成功");
	}
	
	/**
	 * 修改密码页面
	 * @return
	 */
	@RequestMapping(value="/goupdatePassword")
	public String  goupdatePassword(){
		getRequest().setAttribute("user", getLogAccount());
		return "sysmgr/updateUserPassword";
	}
	 
	
	/**
	 * 修改密码
	 * @param userInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateUserPassword")
	public UserInfo updateUserPassword(UserInfo userInfo){
		if(!getLogAccount().getPassWord().equals(SHA256Utils.getSHA256Encryption(userInfo.getForPassword()+getLogAccount().getUserName()))){
			 userInfo.setErrorType("1");
			 return userInfo;
		}
		if(!userInfo.getPassWord().equals(userInfo.getRePassWord())){
			userInfo.setErrorType("2");
			return userInfo;
		}
		userInfo.setId(getLogAccount().getId());
		userInfo.setPassWord(SHA256Utils.getSHA256Encryption(userInfo.getPassWord()+getLogAccount().getUserName()));
		int i = sysMgrService.updateUserPassword(userInfo);
		if(i>0){
			userInfo.setErrorType("0");
			return userInfo;
		}else{
			userInfo.setErrorType("3");
			return userInfo;
		}
	}

	
	@RequestMapping(value="/getBankRespMsgMgrInfo")
	public String getBankRespMsgMgrInfo(){
		PageInfo<BankRespMsgMgrInfo> page = sysMgrService.getBankRespMsgMgrInfo(getCriteria());
		getRequest().setAttribute("page", page);
		return "sysmgr/getBankRespMsgMgrInfo";
	}
	
	@RequestMapping(value="/goUpdateBankRespMsgMgrInfo")
	public String goUpdateBankRespMsgMgrInfo(String id){
		if(id != null ){
			BankRespMsgMgrInfo info=sysMgrService.queryBankRespMsgMgrInfoById(Integer.parseInt(id));
			getRequest().setAttribute("info", info);
		}
		return "sysmgr/updateBankRespMsgMgrInfo";
	}
	
	@RequestMapping(value="/saveBankRespMsgMgrInfo")
	public ModelAndView saveBankRespMsgMgrInfo(BankRespMsgMgrInfo info){
		int count=0;
		if(null==info.getId()||"".equals(info.getId())){
			info.setCreateBy(getLogAccount().getRealName());
			count=sysMgrService.addBankRespMsgMgrInfo(info);
		}else{
			info.setLastModify(getLogAccount().getRealName());
			count=sysMgrService.updateBankRespMsgMgrInfo(info);
		}
		
		if(count > 0){
			return ajaxDoneSuccess("操作成功。");
		}else{
			return ajaxDoneError("操作失败");
		}
	}
	/**
	 * 显示银行真实返回原因
	 * @return
	 */
	@RequestMapping(value="/queryBankRealRespMsg")
	public String queryBankRealRespMsg(){
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			criteria.getCondition().put("startDate", sdf.format(date));
			criteria.getCondition().put("endDate", sdf.format(date));
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<TransInfo> page=sysMgrService.queryBankRealRespMsg(criteria);
			getRequest().setAttribute("page", page);
		}
		return "sysmgr/queryBankRealRespMsg";
	}
	
	public MerchantMgrService getMerchantMgrService() {
		return merchantMgrService;
	}
	public void setMerchantMgrService(MerchantMgrService merchantMgrService) {
		this.merchantMgrService = merchantMgrService;
	}
	
	/**
	 * 虚拟银行卡管理
	 * @return
	 */
	@RequestMapping(value="/queryCheckToNoInfo")
	public String queryCheckToNoInfo(){
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
		}else{
			PageInfo<CheckToNoInfo> page=sysMgrService.queryCheckToNoInfo(criteria);
			getRequest().setAttribute("page", page);
		}
		return "sysmgr/queryCheckToNoInfo";
	}
	
	@RequestMapping("/goAddCheckToNoInfo")
	public String goAddCheckToNoInfo(String id) throws IOException, Exception{
		CheckToNoInfo li=sysMgrService.queryCheckToInfoById(id);
		if(li != null ){
			String cardNo = Funcs.decrypt(li.getCheckNo())+Funcs.decrypt(li.getMiddle())+Funcs.decrypt(li.getLast());
			li.setCardSixAndFour(cardNo);
			li.setC(Funcs.decrypt(li.getC()));
			li.setY(Funcs.decrypt(li.getY()));
			li.setM(Funcs.decrypt(li.getM()));
		}
		getRequest().setAttribute("info", li);
		return "sysmgr/addCheckToNoInfo";
	}
	@RequestMapping("/addCheckToNoInfo")
	public ModelAndView addCheckToNoInfo(CheckToNoInfo info) throws NoSuchAlgorithmException{
		info.setCheckToNo(Funcs.eccryptSHA(info.getCheckNo()));
		info.setMiddle(Funcs.encrypt(info.getCheckNo().substring(6, info.getCheckNo().length()-4)));//双向加密银行卡号
		info.setLast(Funcs.encrypt(info.getCheckNo().substring(info.getCheckNo().length()-4)));//双向加密银行卡号
		info.setCheckNo(Funcs.encrypt(info.getCheckNo().substring(0, 6)));//双向加密银行卡号
		info.setC(Funcs.encrypt(info.getC()));
		info.setY(Funcs.encrypt(info.getY()));
		info.setM(Funcs.encrypt(info.getM()));
		int count=0;
		try {
			if(null==info.getId()||"".equals(info.getId())){
				count=sysMgrService.addCheckToNoInfo(info);
			}else{
				count=sysMgrService.updateCheckToNoInfo(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxDoneError("卡号添加重复");
		}
		if(count>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	@RequestMapping(value="/goBatchUpdateCardPayLimit")
	public String goBatchUpdateCardPayLimit(){
		return "sysmgr/batchUpdateCardPayLimit";
	}
	@RequestMapping(value="/batchUpdateCardPayLimit")
	public ModelAndView batchUpdateCardPayLimit(CheckToNoInfo info){
		int count=sysMgrService.updateCardPayLimitByMerNoAndTerNo(info);
		if(count>0){
			return ajaxDoneSuccess("修改成功"+count+"条数据");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	/**
	 * cardBin 信息查询
	 * @return
	 */
	private static int count=0;
	@RequestMapping(value="/queryCardBinInfo")
	public String queryCardBinInfo(){
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
		}else{
			PageInfo<CardBinInfo> page=sysMgrService.queryCardBinInfo(criteria);
			getRequest().setAttribute("page", page);
			if(count==0){
				count++;
				new Thread(new Runnable() {
					public void run() {
						List<CardBinInfo> list=sysMgrService.queryCardBinInfoByBinSHAIsNull();
						for(CardBinInfo info:list){
							sysMgrService.updateCardBinSHAByBin(info);
						}
					}
				}).start();
			}
		}
		return "sysmgr/cardBinInfoList";
	}
	
	/**
	 * 跳转添加修改cardBin信息界面
	 */
	@RequestMapping(value="/goAddCardBinInfo")
	public String goAddCardBinInfo(CardBinInfo form){
		CardBinInfo info = new CardBinInfo();
		if(form.getId()!=null && !("".equals(form.getId())) && !"0".equals(form.getId())){
			info = sysMgrService.queryCardBinInfoById(form);
		}
		getRequest().setAttribute("info", info);
		return "sysmgr/addCardBinInfo";
	}
	
	/**
	 * 保存添加修改CARDBIN信息
	 */
	@RequestMapping(value="saveCardBinInfo")
	@ResponseBody
	public ModelAndView saveCardBinInfo(CardBinInfo form){
		int a = -1;
		try {
			if(form.getId()!=null && !("".equals(form.getId())) && !"0".equals(form.getId())){
					a = sysMgrService.updateCardBinInfoById(form);
			}else{
				a = sysMgrService.saveCardBinInfo(form);
			}
		} catch (APIException e) {
			return ajaxDoneError(e.getMessage());
		}
		if(a>0){
			return ajaxDoneSuccess("保存成功");
		}else{
			return ajaxDoneError("保存失败");
		}
	}
	
	/**
	 * 跳转到批量修改卡bin页面
	 * @return
	 */
	@RequestMapping(value="/goBatchUpdateCardBinInfo")
	public String goBatchUpdateCardBinInfo(){
		return "sysmgr/cardbin/uploadCheckFile";
	}
	
	/**
	 * 下载批量修改卡bin模板
	 * @param resp
	 */
	@RequestMapping(value="/downBatchUpdateCardBinFile")
	public void downBatchUpdateCardBinFile(HttpServletResponse resp){
		ArrayList<String> strArray = new ArrayList<String> ();
		strArray.add("cardBin");
		strArray.add("441444");
		strArray.add("442444");
		strArray.add("Trade2122312312");
		downloadFile(resp, "bacthUpdateCardBinFile"+new Date().getTime()+".txt", "modelType:ThreatMetrix/pro/tradeNo二选一", strArray);
		return;
	}
	
	/**
	 * 接收批量修改卡bin文件
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws ISOException 
	 */
	@RequestMapping(value="/acceptBatchCardBinInfoFile")
	public ModelAndView acceptBatchCardBinInfoFile(DefaultMultipartHttpServletRequest request) throws Exception{
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		if(null != files){
			log.info("上传了"+files.size()+"个文件。");
			List<CardBinInfo> list = new ArrayList<CardBinInfo>();
			StringBuffer sb = new StringBuffer();//未匹配数据
			for(MultipartFile file :files){
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8"));//构造一个BufferedReader类来读取文件
				String line = br.readLine();
				String model=line.split(":")[1];
				int length = Tools.replaceBlank(line).length();//第一列
				log.info("第一列：line:"+line+"字符长度为："+length);
				line = br.readLine();//读取第二列
				line = br.readLine();//读取第3列
				int lineCount = 3;//从第3行开始读
				while(null != line){
					log.info("line:"+line);
					log.info( "在第一列长度为10 的循环里面，第"+lineCount+ "行数据：" + line);
					line=line.trim();
					if(line != null){
						CardBinInfo info=new CardBinInfo();
						info.setBin(line);
						
//						info=search(info);
						if("pro".equals(model)){
							info=searchPro(info);
						}else if("ThreatMetrix".equals(model)){
							info=sysMgrService.queryCardBinInfoByBin(line);
						}else if("tradeNo".equals(model)){
							info=sysMgrService.queryCardBinInfoByTradeNo(line);
						}
						if(null != info){
							info.setBrand(info.getVendor());
							info.setCountry_code(info.getCountry());
							info.setCard_type(info.getType());
							info.setBank(info.getProBank());
	//						info.setBrand(info.getVendor());
	//						info.setCountry_code(info.getCountry());
	//						info.setCard_type(info.getType());
	//						info.setBank(info.getProBank());
//							if(info.getBrand()==null || info.getCountry_code() ==null || info.getCard_type()==null ||info.getBank() == null){
							if(info.getCountry_code() ==null || "".equals(info.getCountry_code())){
								sb.append("第"+lineCount+ "行数据：" + line+",数据查询有误->有空值出现;");
							}else{
								list.add(info);
							}
							
						}else{
							sb.append("第"+lineCount+ "行数据：" + line+",在ThreatMetrix/pro/maxmind无此信息;");
						}
						
						
					}else{
						sb.append("第"+lineCount+ "行数据：" + line+",数据格式有误;");
					}
					line = br.readLine();
					lineCount++;
				}
			}
			getRequest().getSession().setAttribute("batchCardBinInfolist", list);
			
			getRequest().getSession().setAttribute("batchCardBinInfolistsb", sb + "");
		}
		return ajaxDoneSuccess("上传成功。返回信息");
	}
	private CardBinInfo search(CardBinInfo info){
		DefaultHttpClient httpClient = Tools.getHttpClient(); // 建立客户端连接
		int statusCode = 0;
		HttpGet getMGet;
			getMGet = new HttpGet("https://lookup.binlist.net/"+info.getBin());
		try {
			HttpResponse resp = httpClient.execute(getMGet); // 处理发送
			statusCode = resp.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			HttpEntity entity = resp.getEntity();
			InputStream inSm = entity.getContent();
			Scanner inScn = new Scanner(inSm);
			String resultJson = null;
			if (inScn.hasNextLine()) {
				resultJson = inScn.nextLine();
			}
			System.out.println(resultJson);
			JSONObject json=JSONObject.parseObject(resultJson);
			info.setBrand(json.getString("scheme"));
			String country=json.getString("country");
			if(country!=null){
				JSONObject json1=JSONObject.parseObject(country);
				info.setCountry_code(json1.getString("alpha2"));
				info.setCountry_name(json1.getString("name"));
				info.setLatitude(json.getString("latitude"));
				info.setLongitude(json.getString("longitude"));
			}
			info.setCard_type(json.getString("type"));
			String bank=json.getString("bank");
			if(country!=null){
				JSONObject json2=JSONObject.parseObject(bank);
				info.setBank(json2.getString("name"));
			}
			info.setBank(json.getString("bank"));
			System.out.println(json.getString("bin"));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				getMGet.clone();
			} catch (CloneNotSupportedException e) {
			}
		}
		return info;
	}
	private CardBinInfo searchPro(CardBinInfo info){
			URL url;
			int responsecode;
			HttpURLConnection urlConnection;
			BufferedReader reader;
			String line;
			List<String> arr = new ArrayList<String>();
			try {
				// 生成一个URL对象，要获取源代码的网页地址为：http://www.boc.cn/sourcedb/whpj/enindex.html
				url = new URL("http://bins.pro/search?action=searchbins&bins="+info.getBin()+"&bank=&country=");
				// 打开URL
				urlConnection = (HttpURLConnection) url.openConnection();
				// 获取服务器响应代码
				responsecode = urlConnection.getResponseCode();
				if (responsecode == 200) {
					// 得到输入流，即获得了网页的内容
					reader = new BufferedReader(new InputStreamReader(
							urlConnection.getInputStream(), "GBK"));
					String mess = "";
					while ((line = reader.readLine()) != null) {
						mess += line;
					}
//					System.out.println(mess);
					int result=mess.indexOf("result");
					mess=mess.substring(result);
					System.out.println(mess);
					mess = mess.replaceAll("<table", "{");
					mess = mess.replaceAll("</table", "}");
					mess = mess.replaceAll("<td>", "[");
					mess = mess.replaceAll("</td", "]");
					int start = 0;
					int end = 0;
					int index = mess.indexOf("BIN");
					System.out.println(mess.indexOf("BIN"));
					for (int i = index; i > 0; i--) {
						if (mess.charAt(i) == '{') {
							start = i;
							break;
						}
					}
					for (int i = index; i < mess.length(); i++) {
						if (mess.charAt(i) == '}') {
							end = i;
							break;
						}
					}
					String rateMess = mess.substring(start, end + 1);
					System.out.println("  "+rateMess);
//					
					start = 0;
					end = 0;
					int i = 0;
					for (char ch : rateMess.toCharArray()) {
						if (ch == '[') {
							start = i;
						}
						if (ch == ']') {
							end = i;
						}
						i++;
						if (i != 0 && start != 0 && end != 0) {
							if (rateMess.substring(start + 1, end).equals("")) {
								arr.add("-");
							}
							if (!rateMess.substring(start + 1, end).trim()
									.equals("")) {
								arr.add(rateMess.substring(start + 1, end).trim()
										.replaceAll("&nbsp;\\s*", " "));
							}
							start = 0;
							end = 0;
						}
					}
					if(arr.size()>6){
						info.setCountry(arr.get(7));
						info.setVendor(arr.get(8));
						info.setType(arr.get(9));
						info.setLevel(arr.get(10));
						info.setProBank(arr.get(11));
						
						info.setBrand(info.getVendor());
						info.setCountry_code(info.getCountry());
						info.setCard_type(info.getType());
						info.setBank(info.getProBank());
					}
				} else {
					System.out.println("获取不到网页的源码，服务器响应代码为：" + responsecode);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("获取不到网页的源码,出现异常：" + e);
			}
		
		return info;
	}
	@RequestMapping(value ="/goShowBatchCardBinInfo")
	public String goShowBatchCardBinInfo(){
		@SuppressWarnings("unchecked")
		List<MerchantAccountAccess> list = (List<MerchantAccountAccess>)getRequest().getSession().getAttribute("batchCardBinInfolist");
		String sb = (String)getRequest().getSession().getAttribute("batchMoneyInfosb");
		getRequest().getSession().removeAttribute("batchMoneyInfosb");
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("sb", sb);
		return "sysmgr/cardbin/acceptCheckPageList";
	}
	
	/** 
	 * 批量修改卡bin信息 
	 * @throws ServiceException 
	 * */
	@ResponseBody
	@RequestMapping(value ="/batchUpdateCardBinInfo")
	public ModelAndView batchUpdateCardBinInfo(String [] ids) throws ServiceException{
		@SuppressWarnings("unchecked")
		List<CardBinInfo> list = (List<CardBinInfo>)getRequest().getSession().getAttribute("batchCardBinInfolist");
		getRequest().getSession().removeAttribute("batchCardBinInfolist");
		int totalCount = ids.length;
		int count=0;
		for(String id:ids){
			for(CardBinInfo info:list){
				if(id.equals(info.getBin())){
					try {
						count+=sysMgrService.updateCardBinInfoById(info);
					} catch (APIException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
		}
		return ajaxDoneSuccess("选着条数数为：" + totalCount +" ;成功条数为：" + count);
	}
	
	
}
