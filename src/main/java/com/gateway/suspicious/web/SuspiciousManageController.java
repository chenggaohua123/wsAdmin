package com.gateway.suspicious.web;

import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excel.BIRow;
import com.gateway.common.excel.BISheet;
import com.gateway.common.excel.BIWorkbook;
import com.gateway.common.excetion.ServiceException;
import com.gateway.common.utils.Funcs;
import com.gateway.common.utils.Tools;
import com.gateway.fraud.common.exception.FraudExcetion;
import com.gateway.fraud.common.util.Constant;
import com.gateway.fraud.common.util.DwzJsonUtil;
import com.gateway.fraud.common.util.RequestUtil;
import com.gateway.fraud.model.BinInfo;
import com.gateway.fraud.model.BlackTextInfo;
import com.gateway.fraud.model.MerchantRefRuleProfileInfo;
import com.gateway.fraud.model.ParamInfo;
import com.gateway.fraud.model.RuleProcessClass;
import com.gateway.fraud.model.RuleProfileInfo;
import com.gateway.fraud.model.RulesInfo;
import com.gateway.fraud.model.RulesRefProFileInfo;
import com.gateway.suspicious.model.SuspiciousOrderInfo;
import com.gateway.suspicious.model.SuspiciousOrderListInfo;
import com.gateway.suspicious.model.SuspiciousQueryInfo;
import com.gateway.suspicious.model.SuspiciousRuleOrderInfo;
import com.gateway.suspicious.model.SuspiciousTriggerRuleInfo;
import com.gateway.suspicious.service.SuspiciousManageService;
import com.gateway.transmgr.model.TransInfo;

@Controller
@RequestMapping(value="/suspicious")
public class SuspiciousManageController extends BaseController {
	
	@Resource
	private SuspiciousManageService suspiciousManageService;

	public SuspiciousManageService getSuspiciousManageService() {
		return suspiciousManageService;
	}

	public void setSuspiciousManageService(
			SuspiciousManageService suspiciousManageService) {
		this.suspiciousManageService = suspiciousManageService;
	}

	@RequestMapping(value="/params_list")
	public String paramList(HttpServletRequest request){
		Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<ParamInfo> result =  suspiciousManageService.queryPageParamInfoList(criteria);
		request.setAttribute("result", result);
		return "suspicious/rule/params_list";
	}
	/**
	 * 查找来源参数
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryParamList")
	public String queryParamList(HttpServletRequest request){
		Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<ParamInfo> result =  suspiciousManageService.queryPageParamInfoList(criteria);
		request.setAttribute("result", result);
		return "suspicious/rule/params_lookup_list";
	}
	
	/**
	 * 查询规则匹配参数值
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryRuleParamList")
	public String queryRuleParamList(HttpServletRequest request){
		Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<ParamInfo> result =  suspiciousManageService.queryPageParamInfoList(criteria);
//		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
//		for(ParamInfo info:result){
//			Map<String,String> temp  = new HashMap<String, String>();
//			temp.put("ruleParamValueId", info.getParamId());
//			temp.put("ruleParamValueDescName", info.getParamDescName());
//			list.add(temp);
//		}
		request.setAttribute("result", result);
		return "suspicious/rule/ruleParams_lookup_list";
	}
	
	/**
	 * 查询规则处理类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryProcessClassList")
	public List<Map<String,String>> queryProcessClassList(HttpServletRequest request){
		Criteria criteria = RequestUtil.getCaCriteria(request);
		List<RuleProcessClass> result =  suspiciousManageService.queryProcessClassList(criteria);
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(RuleProcessClass info:result){
			Map<String,String> temp  = new HashMap<String, String>();
			temp.put("processClassId", info.getProcessClassId());
			temp.put("processClassName", info.getBeanName());
			temp.put("classDesc", info.getClassDesc());
			list.add(temp);
		}
		return list;
	}
	 
	/**
	 * 跳转添加参数界面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/goAddParams")
	public String goAddParams(HttpServletRequest request){
		return "suspicious/rule/params_add";
	}
	/**
	 * 添加参数
	 * @param info
	 * @param request
	 * @return
	 * @throws FraudExcetion
	 */
	@RequestMapping(value="/addParams")
	public ModelAndView addParams(ParamInfo info,HttpServletRequest request) throws FraudExcetion{
		if(null == info){
			throw new FraudExcetion("参数为空。");
		}
		info.setCreateBy(getLogAccount().getRealName());
		int i = suspiciousManageService.addParam(info);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	/**
	 * 跳转修改参数界面
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/goUpdateParams")
	public String goUpdateParams(String id,HttpServletRequest request){
		ParamInfo info = suspiciousManageService.queryParamInfoByParamId(id);
		request.setAttribute("result", info);
		return "suspicious/rule/params_update";
	}
	/**
	 * 保存修改参数
	 * @param info
	 * @param request
	 * @return
	 * @throws FraudExcetion
	 */
	@RequestMapping(value="/updateParams")
	public ModelAndView updateParams(ParamInfo info,HttpServletRequest request) throws FraudExcetion{
		if(null == info){
			throw new FraudExcetion("参数为空。");
		}
		info.setCreateBy(getLogAccount().getRealName());
		int i = suspiciousManageService.updateParamInfo(info);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	/**
	 * 删除参数
	 * @param paramId
	 * @return
	 * @throws FraudExcetion
	 */
	@RequestMapping(value="/delParamByParamId")
	public ModelAndView delParamByParamId(String paramId) throws FraudExcetion{
		if(null == paramId){
			throw new FraudExcetion("参数为空。");
		}
		int i = suspiciousManageService.delParamByParamId(paramId);
		ModelAndView mav = new ModelAndView("ajaxDone");
		if(i>0){
			
			mav.addObject("statusCode", "200");
			mav.addObject("message", "删除成功");
			return mav;
		}else{
			
			mav.addObject("statusCode", "200");
			mav.addObject("message", "删除失败");
			return mav;
		}
	}
	
	/**
	 * 查询参数的值
	 * @param paramId
	 * @return
	 * @throws FraudExcetion 
	 */
	@RequestMapping(value="/params_Value")
	public String getParamsValue(String paramId,HttpServletRequest request) throws FraudExcetion{
		if(null == paramId){
			throw new FraudExcetion("参数不能为空。");
		}
		ParamInfo info = suspiciousManageService.queryParamInfoByParamId(paramId);
		request.setAttribute("info", info);
		if("List".equals(info.getType()) || "String".equals(info.getType())){
			return "suspicious/rule/param_Value_List";
		}else if("Table".equals(info.getType())){
			return "suspicious/rule/param_Value_Table";
		}else{
			throw new FraudExcetion("参数类型不对。");
		}
	}
	
	/**
	 * 跳转设置参数的页面
	 * @param paramId
	 * @param request
	 * @return
	 * @throws FraudExcetion
	 */
	@RequestMapping(value="/goParams_setParamValue")
	public String goSetParamValue(String paramId,HttpServletRequest request) throws FraudExcetion{
		if(null == paramId || paramId.trim().length()==0){
			throw new FraudExcetion("参数为空。");
		}
		ParamInfo info = suspiciousManageService.queryParamInfoByParamId(paramId);
		if(null == info){
			throw new FraudExcetion("该参数不存在。");
		}
		if("1".equals(info.getComFrom())){
			throw new FraudExcetion("参数来源于程序的不能设置值。");
		}
		request.setAttribute("info", info);
		if("List".equals(info.getType()) || "String".equals(info.getType())){
			return "suspicious/rule/param_setValue_List";
		}else if("Table".equals(info.getType())){
			return "suspicious/rule/param_setValue_Table";
		}else{
			throw new FraudExcetion("参数类型不对。");
		}
	}
	
	/**
	 * 设置参数值
	 * @param info
	 * @param request
	 * @return
	 * @throws FraudExcetion
	 */
	@RequestMapping(value="/setParamValue")
	public ModelAndView setParamValue(ParamInfo info,HttpServletRequest request) throws FraudExcetion{
		boolean flag = false;
		if(null != info && null != info.getParamId() && info.getParamId().trim().length()>0){
			ParamInfo oldInfo = suspiciousManageService.queryParamInfoByParamId(info.getParamId());
			info.setCreateBy(getLogAccount().getRealName());
			if(null == oldInfo){
				throw new FraudExcetion("该参数不存在。");
			}
			if("1".equals(oldInfo.getComFrom())){
				throw new FraudExcetion("参数来源于程序的不能设置值。");
			}
			if("List".equals(oldInfo.getType()) || "String".equals(oldInfo.getType())){
				if(null != info.getStringValue() && info.getStringValue().length()>0){
					List<String> listValue = new ArrayList<String>();
					if("List".equals(oldInfo.getType())){
						String [] temps = info.getStringValue().split(",");
						for(String temp:temps){
							listValue.add(temp);
						}
					}else{
						listValue.add(info.getStringValue());
					}
					info.setListValue(listValue);
				}else{
					throw new FraudExcetion("请输入列表值");
				}
			}else if("Table".equals(oldInfo.getType())){
				//
				Map<String, String> tableValue = new HashMap<String, String>();
				tableValue.put(info.getColKeyName(), info.getColKeyValue());
				tableValue.put(info.getColValueName(),info.getColValue());
				info.setTableValue(tableValue);
			}else{
				throw new FraudExcetion("参数类型不对，");
			}
			flag = suspiciousManageService.setParamValue(oldInfo,info);
		}else{
			throw new FraudExcetion("参数错误。");
		}
		if(flag){
			return ajaxDoneSuccess("设置成功");
		}else{
			return ajaxDoneError("设置失败");
		}
	}
	
	/**
	 * 查询处理规则处理类信息
	 * @param processClassId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryProcessClass")
	public String queryProcessClass(String processClassId,HttpServletRequest request){
		return "suspicious/rule/rules_processClass_detail";
	}
	/**
	 * 查询规则列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/rules_list")
	public String rulesList(HttpServletRequest request){
		//Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<RulesInfo> result =  suspiciousManageService.queryPageRulesList(getCriteria());
		request.setAttribute("result", result);
		return "suspicious/rule/rules_list";
	}
	/**
	 * 跳转添加规则界面
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/goAddRules")
	public String goAddRules(String id,HttpServletRequest request){
		return "suspicious/rule/rules_add";
	}
	/**
	 * 保存添加规则信息
	 * @param info
	 * @param request
	 * @return
	 * @throws FraudExcetion
	 */
	@RequestMapping(value="/addRules")
	public ModelAndView addRules(RulesInfo info,HttpServletRequest request) throws FraudExcetion{
		if(null == info){
			throw new FraudExcetion("参数为空。");
		}
		RulesInfo rInfo = suspiciousManageService.queryRuleValueName(info);
		if(!StringUtils.isEmpty(rInfo)){
			return ajaxDoneError("规则名字不能重复");
		}
		info.setCreateBy(getLogAccount().getRealName());
		int i = suspiciousManageService.addRuleInfo(info);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	/**
	 * 跳转规则修改界面
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/goUpdateRules")
	public String goUpdateRules(String id, HttpServletRequest request){
		RulesInfo info = suspiciousManageService.queryRulesInfoDetailByRuleId(id);
		request.setAttribute("info", info);
		return "suspicious/rule/rules_update";
	}
	/**
	 * 保存修改规则信息
	 * @param info
	 * @param request
	 * @return
	 * @throws FraudExcetion
	 */
	@RequestMapping(value="/updateRules")
	public ModelAndView updateRules(RulesInfo info,HttpServletRequest request) throws FraudExcetion{
		if(null == info){
			throw new FraudExcetion("参数为空。");
		}
		info.setCreateBy(getLogAccount().getRealName());
		int i = suspiciousManageService.updateRuleInfo(info);
		if(i>0){
			return ajaxDoneSuccess("更新成功。");
		}else{
			return ajaxDoneError("更新失败");
		}
	}
	/**
	 * 查找规则信息详情
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryRuleInfoDetailByRulesId")
	public String queryRuleInfoDetailByRulesId(String id, HttpServletRequest request){
		RulesInfo info = suspiciousManageService.queryRulesInfoDetailByRuleId(id);
		request.setAttribute("result", info);
		return "suspicious/rule/rules_detail";
	}
	
	/**
	 * 查询规则集合表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryRuleProfile")
	public String queryRuleProfile(HttpServletRequest request){
		Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<RuleProfileInfo> list = suspiciousManageService.queryPageRuleFInfo(criteria);
		request.setAttribute("result", list);
		return "suspicious/rule/rules_profile_list";
	}
	
	/**
	 * 添加到增加规则集合的页面
	 * @return
	 */
	@RequestMapping(value="/goAddRulesProFile")
	public String goAddRulesProFile(){
		return "suspicious/rule/rules_profile_add";
	}
	
	/**
	 * 添加规则集合
	 * @param info
	 * @param request
	 * @return
	 * @throws FraudExcetion 
	 */
	@RequestMapping(value="/addRuleProfile")
	public ModelAndView addRuleProfile(RuleProfileInfo info,HttpServletRequest request) throws FraudExcetion{
		if(null == info){
			throw new FraudExcetion("参数为空。");
		}
		info.setCreateBy(getLogAccount().getRealName());
		int i = suspiciousManageService.addRuleProFileInfo(info);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	/**
	 * 跳转到更新集合页面
	 * @return
	 * @throws FraudExcetion 
	 */
	@RequestMapping(value="/goUpdateRulesProFile")
	public String goUpdateRulesProFile(String profileId,HttpServletRequest request) throws FraudExcetion{
		if(null == profileId || profileId.trim().length()==0){
			throw new FraudExcetion("参数不能为空。");
		}
		RuleProfileInfo info = suspiciousManageService.queryRuleProFileInfoByProfileId(profileId);
		request.setAttribute("info", info);
		return "suspicious/rule/rules_profile_update";
	}
	
	/**
	 * 更新结合信息
	 * @param info
	 * @return
	 */
	@RequestMapping(value="/updateRuleProfile")
	public ModelAndView updateRuleProfile(RuleProfileInfo info,HttpServletRequest request){
		info.setCreateBy(getLogAccount().getRealName());
		int i = suspiciousManageService.updateRuleProfileInfo(info);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	/**
	 * 跳转集合关联规则页面
	 * @param profileId
	 * @param request
	 * @return
	 * @throws FraudExcetion
	 */
	@RequestMapping(value="/goRefRuleProfileLits")
	public String goRefRuleProfileLits(String profileId,HttpServletRequest request) throws FraudExcetion{
		if(null == profileId || profileId.trim().length()==0){
			throw new FraudExcetion("参数不能为空。");
		}
		Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<RulesInfo> ruleList = suspiciousManageService.queryPageRulesList(criteria);
		request.setAttribute("ruleList", ruleList);
		request.setAttribute("profileId", profileId);
		return "suspicious/rule/rules_profile_ref_rules";
	}
	
	/**
	 * 添加规则到规则集合中去
	 * @param profileId
	 * @param rulesId
	 * @param request
	 * @return
	 * @throws FraudExcetion 
	 */
	@RequestMapping(value="/addRulesToRulesProFile")
	public ModelAndView addRulesToRulesProFile(String profileId,String [] rulesId,HttpServletRequest request) throws FraudExcetion{
		if(null == profileId || profileId.trim().length()==0){
			throw new FraudExcetion("集合不能为空。");
		}
		if(null == rulesId || rulesId.length ==0){
			throw new FraudExcetion("请选择规则。");
		}
		List<RulesRefProFileInfo> list = new ArrayList<RulesRefProFileInfo>();
		for(String ruleId:rulesId){
			RulesRefProFileInfo info = new RulesRefProFileInfo();
			info.setRuleId(ruleId);
			info.setProfileId(profileId);
			info.setCreateBy(getLogAccount().getRealName());
			list.add(info);
		}
		int i = suspiciousManageService.addRulesToRuleProfile(list);
		return new ModelAndView(Constant.JSON_VIEW, Constant.JSON_ROOT, DwzJsonUtil.getOkStatusMsg("添加成功,受影响数据："+i+"条。"));
	}
	
	/**
	 * 根据集合ID查询关联的规则列表
	 * @param profileId
	 * @param request
	 * @return
	 * @throws FraudExcetion 
	 */
	@RequestMapping(value="/queryRefRuleProfileLits")
	public String queryRefRuleProfileLits(String profileId,HttpServletRequest request) throws FraudExcetion{
		if(null == profileId || profileId.trim().length()==0){
			throw new FraudExcetion("请选择一个集合。");
		}
		Criteria criteria = RequestUtil.getCaCriteria(request); 
		PageInfo<RulesInfo> list = suspiciousManageService.queryPageRefRuleProfileLits(criteria);
		request.setAttribute("result", list);
		return "suspicious/rule/rules_profile_ref_rules_list";
	}
	
	/**
	 * 删除集合关联规则
	 * @param profileId
	 * @param request
	 * @return
	 * @throws FraudExcetion 
	 */
	@RequestMapping(value="/delRulesFromRulesProFile")
	public ModelAndView delRulesFromRulesProFile(String profileId,String [] rulesId,HttpServletRequest request) throws FraudExcetion{
		if(null == profileId || profileId.trim().length()==0 || null == rulesId || rulesId.length == 0){
			throw new FraudExcetion("参数为空，");
		}
		int i = suspiciousManageService.delRulesFromRulesProFile(profileId, rulesId);
		return new ModelAndView(Constant.JSON_VIEW, Constant.JSON_ROOT, DwzJsonUtil.getOkStatusMsg("删除成功,受影响数据："+i+"条。"));
	}
	
	
	/**************************/
	/**
	 * 黑名单数据查询
	 * @return
	 */
	@RequestMapping(value="/queryBlackInfoList")
	public String queryBlackInfoList(HttpServletRequest request){
		Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<BlackTextInfo> info = suspiciousManageService.queryPageBlackInfoList(criteria);
		request.setAttribute("result", info);
		return "suspicious/risk/blackText_list";
	}
	
	/**
	 * 跳转到添加黑名单数据页面
	 * @return
	 */
	@RequestMapping("/goAddBlackText")
	public String goAddBlackText(){
		return "suspicious/risk/blackText_add";
	}
	
	/**
	 * 添加黑名单数据
	 * @return
	 * @throws FraudExcetion 
	 */
	@RequestMapping(value="/addBlackTextInfo")
	public ModelAndView addBlackTextInfo(BlackTextInfo info, HttpServletRequest request) throws FraudExcetion{
		if(null == info){
			throw new FraudExcetion("参数不能为空。");
		}
		info.setCreateBy(getLogAccount().getRealName());
		if(com.gateway.fraud.common.util.Constants.RISKELEMENTTYPE_CARDNO.equals(info.getBlackType())){
			try {
				info.setBlackText(Funcs.eccryptSHA(info.getBlackText().trim()));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		BlackTextInfo temp = suspiciousManageService.queryBlackTextInfoByValueAndType(info.getBlackText(), info.getBlackType());
		if(null != temp){
			return ajaxDoneError("添加失败,已经存在该记录了。");
		}
		int i = suspiciousManageService.addBlackTextInfo(info);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	/**
	 * 添加黑名单数据
	 * @return
	 * @throws FraudExcetion 
	 */
	@RequestMapping(value="/delBlackTextById")
	public ModelAndView delBlackTextById(String blackId, HttpServletRequest request) throws FraudExcetion{
		if(null == blackId){
			throw new FraudExcetion("参数不能为空。");
		}
		int i = suspiciousManageService.delBlackTextById(blackId);
		if(i>0){
			return ajaxDoneSuccess("删除成功");
		}else{
			return ajaxDoneError("删除失败");
		}
	}
	
	/**
	 * bin数据查询
	 * @return
	 */
	@RequestMapping(value="/queryBinList")
	public String queryBinList(HttpServletRequest request){
		Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<BinInfo> info = suspiciousManageService.queryPageBinList(criteria);
		request.setAttribute("result", info);
		return "suspicious/risk/bin_list";
	}
	
	
	@RequestMapping(value="/goAddBinInfo")
	public String goAddBinInfo(){
		return "suspicious/risk/bin_add";
	}
	
	/**
	 * 添加BIN信息
	 * @param info
	 * @param request
	 * @return
	 * @throws FraudExcetion
	 */
	@RequestMapping(value="/addBinInfo")
	public ModelAndView addBinInfo(BinInfo info,HttpServletRequest request) throws FraudExcetion{
		if(null == info){
			throw new FraudExcetion("参数不能为空。");
		}
		info.setCreateBy(getLogAccount().getRealName());
		int i = suspiciousManageService.addBinInfo(info);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	/**
	 * 根据ID删除Bin信息
	 * @param id
	 * @param request
	 * @return
	 * @throws FraudExcetion
	 */
	@RequestMapping(value="/delBinInfotById")
	public ModelAndView delBinInfotById(String id, HttpServletRequest request) throws FraudExcetion{
		if(null == id){
			throw new FraudExcetion("参数不能为空。");
		}
		int i = suspiciousManageService.deleteBinInfoById(id);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	
	/**
	 * 跳转到批量添加黑名单页面
	 * @return
	 */
	@RequestMapping(value="/goBatchAddBlackText")
	public String goBatchAddBlackText(){
		return "suspicious/risk/batch_add_blacktype";
	}
	
	/**
	 * 查询账号关联集合列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryAccountRefProfileList")
	public String queryAccountRefProfileList(HttpServletRequest request){
		Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<MerchantRefRuleProfileInfo> result =  suspiciousManageService.queryPageAccountRefProfileList(criteria);
		request.setAttribute("result", result);
		return "suspicious/account/account_ref_profile_list";
	}
	/**
	 * 跳转到添加账号关联规则集合列表的页面
	 * @return
	 */
	@RequestMapping(value="/goAddProfileToAccount")
	public String goAddProfileToAccount(HttpServletRequest request){
		return "suspicious/account/account_ref_profile_add";
	}
	@RequestMapping(value="/goUpdateAccountRefProfileInfoById")
	public String goUpdateAccountRefProfileInfoById(String id,HttpServletRequest request) throws ServiceException{
		if(null == id || id.trim().length()==0){
			throw new ServiceException("参数不能为空。");
		}
		MerchantRefRuleProfileInfo info = suspiciousManageService.queryProfileInfoById(id);
		if(null == info){
			throw new ServiceException("记录不存在，不能更新。");
		}
		request.setAttribute("info", info);
		return "suspicious/account/account_ref_profile_update";
	}
	
	/**
	 * 更新账户关联规则列表数据
	 * @param info
	 * @param request
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/updateAccountRefProfileInfoById")
	public ModelAndView updateAccountRefProfileInfoById(MerchantRefRuleProfileInfo info, HttpServletRequest request) throws ServiceException{
		if(null == info || null == info.getId() || info.getId().trim().length()==0){
			throw new ServiceException("参数异常。");
		}
		info.setProFileId(request.getParameter("info.proFileId"));
		info.setProFileName(request.getParameter("info.proFileName"));
		info.setCreateBy(getLogAccount().getRealName());
		int i = suspiciousManageService.updateAccountRefProfileInfoById(info);
		if(i>0){
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	
	/**
	 * 添加账号关联规则集合记录
	 * @param info
	 * @param request
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/addProfileToAccount")
	public ModelAndView addProfileToAccount(MerchantRefRuleProfileInfo info, HttpServletRequest request) throws ServiceException{
		if(null == info){
			throw new ServiceException("参数为空。");
		}
		List<MerchantRefRuleProfileInfo> temp = suspiciousManageService.queryProfileInfoByMerNoAndTerNo(info.getMerNo(), info.getTerNo(),info.getProFileId());
		if(null != temp && temp.size()>0){
			throw new ServiceException("改记录已经存在，不能继续添加");
		}
		info.setProFileId(request.getParameter("info.proFileId"));
		info.setProFileName(request.getParameter("info.proFileName"));
		info.setCreateBy(getLogAccount().getRealName());
		int i = suspiciousManageService.addProfileToAccount(info);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	
	/**
	 * 查询集合列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryProfileList")
	public String queryProfileList(HttpServletRequest request){
		Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<RuleProfileInfo> list = suspiciousManageService.queryPageRuleFInfo(criteria);
		request.setAttribute("result", list);
 		return "suspicious/account/profile_lookup_list";
	}
	
	/**
	 * 删除账户关联规则集合
	 * @param id
	 * @param request
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/delAccountFromList")
	public ModelAndView delAccountFromList(String id,HttpServletRequest request) throws ServiceException{
		if(null == id || id.trim().length()==0){
			throw new ServiceException("参数不能为空。");
		}
		int i = suspiciousManageService.delAccountFromList(id);
		if(i>0){
			return ajaxDoneSuccess("删除成功");
		}else{
			return ajaxDoneError("删除失败");
		}
	}
	/**
	 * 查找可疑订单明细列表
	 * @return
	 */
	@RequestMapping(value="/querySuspiciousOrderList")
	public String querySuspiciousOrder(HttpServletRequest request){
		Criteria criteria = RequestUtil.getCaCriteria(request);
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String transDateStart=sdf1.format(date);
			criteria.getCondition().put("createDateStart", transDateStart);
			criteria.getCondition().put("createDateEnd", transDateStart);
			
			String orderDateStart = sdf1.format(date);
			criteria.getCondition().put("transCreateDateStart", orderDateStart);
			criteria.getCondition().put("transCreateDateEnd", orderDateStart);
			getRequest().setAttribute("form", criteria.getCondition());
		}
		String cardStart = Funcs.encrypt((String)criteria.getCondition().get("cardStart"));
		if(cardStart!=null && cardStart!=""){
			criteria.getCondition().put("cardStart", cardStart);
		}
		String cardEnd = Funcs.encrypt((String)criteria.getCondition().get("cardEnd"));
		if(cardEnd!=null && cardEnd!=""){
			criteria.getCondition().put("cardEnd", cardEnd);
		}
		PageInfo<SuspiciousOrderInfo> page = suspiciousManageService.querySuspiciousOrderInfo(criteria);
		getRequest().setAttribute("form", criteria.getCondition());
		request.setAttribute("page", page);
		return "suspicious/trans/transList";
	}
	/**
	 * 跳转集合规则页面
	 * @param profileId
	 * @param request
	 * @return
	 * @throws FraudExcetion
	 */
	@RequestMapping(value="/goRuleLits")
	public String goRuleLits(HttpServletRequest request) throws FraudExcetion{
		Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<RulesInfo> ruleList = suspiciousManageService.queryPageRulesList(criteria);
		request.setAttribute("ruleList", ruleList);
		return "suspicious/trans/ruleList";
	}
	/**
	 * 跳转关联流水号信息
	 * @throws Exception 
	 * @throws IOException 
	 */
	@RequestMapping(value="/goRelTradeNo")
	public String goRelTradeNo(SuspiciousQueryInfo form, HttpServletRequest request) throws IOException, Exception{
		TransInfo trans = suspiciousManageService.queryTransInfoByTradeNo(form.getTradeNo());
		trans.setIPAddress(trans.getIpAddress());
		List<String> tradeNoList = suspiciousManageService.querySuspiciousRuleTradeNoInfo(form, trans);
		SuspiciousRuleOrderInfo ruleOrder = suspiciousManageService.querySuspiciousRuleOrderInfo(form, tradeNoList);
		if(!(ruleOrder!=null)){
			ruleOrder = new SuspiciousRuleOrderInfo();
		}
		ruleOrder.setSusType(form.getSusType());
		ruleOrder.setRuleIds(form.getRuleIds());
		ruleOrder.setCreateDate(form.getCreateDate());
		ruleOrder.setMerNo(form.getMerNo());
		ruleOrder.setTerNo(form.getTerNo());
		ruleOrder.setTradeNo(form.getTradeNo());
		getRequest().setAttribute("order", ruleOrder);
		return "suspicious/trans/transDetailList";
	}
	/**
	 * 导出触犯规则交易记录
	 */
	@RequestMapping(value="/exportSuspiciousRuleTransInfo")
	public void exportSuspiciousRuleTransInfo(SuspiciousQueryInfo form, HttpServletRequest request, HttpServletResponse response){
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			response.reset(); // 来清除首部的空白行
			response.setHeader("Content-Disposition", "attachment; filename="
					+ "ruleTradeList.xls");
			response.setContentType("application/octet-stream; charset=utf-8");
			BIWorkbook bw = new BIWorkbook();
			BISheet bs = bw.addSheet();
			BIRow br_0 = bs.addRow();
			for (String str : new String[] { "交易流水号"}) {
				br_0.addCell().setCellValue(str, null);
			}
			List<String> list = suspiciousManageService.querySuspiciousRuleTransInfo(form);
			for (int row = 1; row <= list.size(); row++) {
				BIRow br_1 = bs.addRow();
				String tradeNo = list.get(row-1);
				br_1.addCell().setCellValue(tradeNo!=null?tradeNo:"", null);
			}
			bw.workbook.write(os);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 跳转触犯规则详情界面
	 */
	@RequestMapping(value="/goTriggerRulelist")
	public String goTriggerRuleList(String susType, String tradeNo, String createDate, HttpServletRequest request){
		SuspiciousTriggerRuleInfo rule = suspiciousManageService.querySuspiciousRuleInfo(susType, tradeNo, createDate);
		getRequest().setAttribute("rule", rule);
		return "suspicious/rule/ruleDetailList";
	}
	/**
	 * 导出可疑订单明细信息
	 */
	@RequestMapping(value="/exportSuspicioustOrderInfo")
	public void exportSuspiciousOrderInfo(HttpServletRequest request,
			HttpServletResponse response) {
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			response.reset(); // 来清除首部的空白行
			response.setHeader("Content-Disposition", "attachment; filename="
					+ "suspiciousTransList.xls");
			response.setContentType("application/octet-stream; charset=utf-8");
			Criteria criteria = getCriteria();
			BIWorkbook bw = new BIWorkbook();
			BISheet bs = bw.addSheet();
			BIRow br_0 = bs.addRow();
			for (String str : new String[] { "商户号", "终端号", "商户订单号", "交易流水号", "交易日期", 
					"交易金额", "拒付状态", "退款状态", "执行时间", "执行类型" , "关联流水号", 
					"触犯规则名称集"}) {
				br_0.addCell().setCellValue(str, null);
			}
			List<SuspiciousOrderListInfo> list = suspiciousManageService
					.querySuspiciousOrderListInfo(criteria);
			for (int row = 1; row <= list.size(); row++) {
				BIRow br_1 = bs.addRow();
				SuspiciousOrderListInfo order = list.get(row - 1);
				br_1.addCell().setCellValue(order.getMerNo()!=null?order.getMerNo():"", null);
				br_1.addCell().setCellValue(order.getTerNo()!=null?order.getTerNo():"", null);
				br_1.addCell().setCellValue(order.getOrderNo()!=null?order.getOrderNo():"", null);
				br_1.addCell().setCellValue(order.getTradeNo()!=null?order.getTradeNo():"", null);
				br_1.addCell().setCellValue(order.getTransDate()!=null?new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss").format(order.getTransDate()) : "", null);
				br_1.addCell().setCellValue((order.getMerBusCurrency()!=null?order.getMerBusCurrency():"")+(" "+order.getMerTransAmount()!=null?order.getMerTransAmount():""), null);
				if("0".equals(order.getIsRefund())){
					br_1.addCell().setCellValue("未拒付", null);
				}
				if("1".equals(order.getIsRefund())){
					br_1.addCell().setCellValue("待审核", null);
				}
				if("2".equals(order.getIsRefund())){
					br_1.addCell().setCellValue("已拒付", null);
				}
				if("0".equals(order.getIsDishonor())){
					br_1.addCell().setCellValue("未退款", null);
				}
				if("1".equals(order.getIsDishonor())){
					br_1.addCell().setCellValue("待审核", null);
				}
				if("2".equals(order.getIsDishonor())){
					br_1.addCell().setCellValue("已退款", null);
				}
//				br_1.addCell().setCellValue(order.getRuleId()!=null?order.getRuleId():"", null);
//				br_1.addCell().setCellValue(order.getRuleName()!=null?order.getRuleName():"", null);
				br_1.addCell().setCellValue(order.getCreateDate()!=null?order.getCreateDate():"", null);
				if("1".equals(order.getSusType())){
					br_1.addCell().setCellValue("按商户", null);
				}
				if("2".equals(order.getSusType())){
					br_1.addCell().setCellValue("按商户终端", null);
				}
				if("3".equals(order.getSusType())){
					br_1.addCell().setCellValue("按终端", null);
				}
				br_1.addCell().setCellValue(order.getTradeList()!=null?order.getTradeList():"", null);
//				br_1.addCell().setCellValue(order.getRuleIdList()!=null?order.getRuleIdList():"", null);
				br_1.addCell().setCellValue(order.getRuleNameList()!=null?order.getRuleNameList():"", null);
			}
			bw.workbook.write(os);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 根据流水号查询交易信息
	 * @throws Exception 
	 * @throws IOException 
	 */
	@RequestMapping(value="queryTransByTradeNo")
	public String queryTransByTradeNo(String tradeNo) throws IOException, Exception{
		TransInfo transInfo=suspiciousManageService.queryTransInfoByTradeNo(tradeNo);
		if(transInfo.getCheckNo()!=null&&!"".equals(transInfo.getCheckNo())){
		transInfo.setSixAndFourCardNo(Funcs.decrypt(transInfo.getCheckNo())+"***"+Funcs.decrypt(transInfo.getLast()));
		}
		transInfo.setWebInfo( Tools.parseWebInfoToResourceType(transInfo.getWebInfo()));
		getRequest().setAttribute("transInfo", transInfo);
		return "suspicious/trans/transDetail";
	}
}
