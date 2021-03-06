package com.gateway.fraud.web;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;








import com.gateway.fraud.model.BinInfo;
import com.gateway.fraud.model.BlackTextInfo;
import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.utils.Funcs;
import com.gateway.fraud.common.exception.FraudExcetion;
import com.gateway.fraud.model.ParamInfo;
import com.gateway.fraud.model.RuleProcessClass;
import com.gateway.fraud.model.RuleProfileInfo;
import com.gateway.fraud.model.RulesInfo;
import com.gateway.fraud.model.RulesRefProFileInfo;
import com.gateway.fraud.service.FraudManageService;
import com.gateway.fraud.common.util.Constant;
import com.gateway.fraud.common.util.DwzJsonUtil;
import com.gateway.fraud.common.util.MessageDigestUtil;
import com.gateway.fraud.common.util.RequestUtil;
//import com.gateway.common.utils.Funcs;
@Controller
@RequestMapping("/fraud/rule")
public class FraudRuleManageController extends BaseController implements Constant{
	@Resource
	private FraudManageService fraudManageService;
	
	public FraudManageService getFraudManageService() {
		return fraudManageService;
	}
	public void setFraudManageService(FraudManageService fraudManageService) {
		this.fraudManageService = fraudManageService;
	}
	@RequestMapping(value="/params_list")
	public String paramList(HttpServletRequest request){
		Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<ParamInfo> result =  fraudManageService.queryPageParamInfoList(criteria);
		request.setAttribute("result", result);
		return "fraud/rule/params_list";
	}
	
	@RequestMapping(value="/queryParamList")
	public String queryParamList(HttpServletRequest request){
		Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<ParamInfo> result =  fraudManageService.queryPageParamInfoList(criteria);
		request.setAttribute("result", result);
		return "fraud/rule/params_lookup_list";
	}
	
	/**
	 * ???????????????????????????
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryRuleParamList")
	public String queryRuleParamList(HttpServletRequest request){
		Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<ParamInfo> result =  fraudManageService.queryPageParamInfoList(criteria);
//		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
//		for(ParamInfo info:result){
//			Map<String,String> temp  = new HashMap<String, String>();
//			temp.put("ruleParamValueId", info.getParamId());
//			temp.put("ruleParamValueDescName", info.getParamDescName());
//			list.add(temp);
//		}
		request.setAttribute("result", result);
		return "fraud/rule/ruleParams_lookup_list";
	}
	
	/**
	 * ?????????????????????
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryProcessClassList")
	public List<Map<String,String>> queryProcessClassList(HttpServletRequest request){
		Criteria criteria = RequestUtil.getCaCriteria(request);
		List<RuleProcessClass> result =  fraudManageService.queryProcessClassList(criteria);
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
	 
	
	@RequestMapping(value="/goAddParams")
	public String goAddParams(HttpServletRequest request){
		return "fraud/rule/params_add";
	}
	@RequestMapping(value="/addParams")
	public ModelAndView addParams(ParamInfo info,HttpServletRequest request) throws FraudExcetion{
		if(null == info){
			throw new FraudExcetion("???????????????");
		}
		info.setCreateBy(getLogAccount().getRealName());
		int i = fraudManageService.addParam(info);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	@RequestMapping(value="/goUpdateParams")
	public String goUpdateParams(String id,HttpServletRequest request){
		ParamInfo info = fraudManageService.queryParamInfoByParamId(id);
		request.setAttribute("result", info);
		return "fraud/rule/params_update";
	}
	
	@RequestMapping(value="/updateParams")
	public ModelAndView updateParams(ParamInfo info,HttpServletRequest request) throws FraudExcetion{
		if(null == info){
			throw new FraudExcetion("???????????????");
		}
		info.setCreateBy(getLogAccount().getRealName());
		int i = fraudManageService.updateParamInfo(info);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	@RequestMapping(value="/delParamByParamId")
	public ModelAndView delParamByParamId(String paramId) throws FraudExcetion{
		if(null == paramId){
			throw new FraudExcetion("???????????????");
		}
		int i = fraudManageService.delParamByParamId(paramId);
		ModelAndView mav = new ModelAndView("ajaxDone");
		if(i>0){
			
			mav.addObject("statusCode", "200");
			mav.addObject("message", "????????????");
			return mav;
		}else{
			
			mav.addObject("statusCode", "200");
			mav.addObject("message", "????????????");
			return mav;
		}
	}
	
	/**
	 * ??????????????????
	 * @param paramId
	 * @return
	 * @throws FraudExcetion 
	 */
	@RequestMapping(value="/params_Value")
	public String getParamsValue(String paramId,HttpServletRequest request) throws FraudExcetion{
		if(null == paramId){
			throw new FraudExcetion("?????????????????????");
		}
		ParamInfo info = fraudManageService.queryParamInfoByParamId(paramId);
		request.setAttribute("info", info);
		if("List".equals(info.getType()) || "String".equals(info.getType())){
			return "fraud/rule/param_Value_List";
		}else if("Table".equals(info.getType())){
			return "fraud/rule/param_Value_Table";
		}else{
			throw new FraudExcetion("?????????????????????");
		}
	}
	
	/**
	 * ???????????????????????????
	 * @param paramId
	 * @param request
	 * @return
	 * @throws FraudExcetion
	 */
	@RequestMapping(value="/goParams_setParamValue")
	public String goSetParamValue(String paramId,HttpServletRequest request) throws FraudExcetion{
		if(null == paramId || paramId.trim().length()==0){
			throw new FraudExcetion("???????????????");
		}
		ParamInfo info = fraudManageService.queryParamInfoByParamId(paramId);
		if(null == info){
			throw new FraudExcetion("?????????????????????");
		}
		if("1".equals(info.getComFrom())){
			throw new FraudExcetion("??????????????????????????????????????????");
		}
		request.setAttribute("info", info);
		if("List".equals(info.getType()) || "String".equals(info.getType())){
			return "fraud/rule/param_setValue_List";
		}else if("Table".equals(info.getType())){
			return "fraud/rule/param_setValue_Table";
		}else{
			throw new FraudExcetion("?????????????????????");
		}
	}
	
	/**
	 * ???????????????
	 * @param info
	 * @param request
	 * @return
	 * @throws FraudExcetion
	 */
	@RequestMapping(value="/setParamValue")
	public ModelAndView setParamValue(ParamInfo info,HttpServletRequest request) throws FraudExcetion{
		boolean flag = false;
		if(null != info && null != info.getParamId() && info.getParamId().trim().length()>0){
			ParamInfo oldInfo = fraudManageService.queryParamInfoByParamId(info.getParamId());
			info.setCreateBy(getLogAccount().getRealName());
			if(null == oldInfo){
				throw new FraudExcetion("?????????????????????");
			}
			if("1".equals(oldInfo.getComFrom())){
				throw new FraudExcetion("??????????????????????????????????????????");
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
					throw new FraudExcetion("??????????????????");
				}
			}else if("Table".equals(oldInfo.getType())){
				//
				Map<String, String> tableValue = new HashMap<String, String>();
				tableValue.put(info.getColKeyName(), info.getColKeyValue());
				tableValue.put(info.getColValueName(),info.getColValue());
				info.setTableValue(tableValue);
			}else{
				throw new FraudExcetion("?????????????????????");
			}
			flag = fraudManageService.setParamValue(oldInfo,info);
		}else{
			throw new FraudExcetion("???????????????");
		}
		if(flag){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/**
	 * ?????????????????????????????????
	 * @param processClassId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryProcessClass")
	public String queryProcessClass(String processClassId,HttpServletRequest request){
		return "fraud/rule/rules_processClass_detail";
	}
	
	@RequestMapping(value="/rules_list")
	public String rulesList(HttpServletRequest request){
		//Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<RulesInfo> result =  fraudManageService.queryPageRulesList(getCriteria());
		request.setAttribute("result", result);
		return "fraud/rule/rules_list";
	}
	
	@RequestMapping(value="/goAddRules")
	public String goAddRules(String id,HttpServletRequest request){
		return "fraud/rule/rules_add";
	}
	
	@RequestMapping(value="/addRules")
	public ModelAndView addRules(RulesInfo info,HttpServletRequest request) throws FraudExcetion{
		if(null == info){
			throw new FraudExcetion("???????????????");
		}
		RulesInfo rInfo = fraudManageService.queryRuleValueName(info);
		if(!StringUtils.isEmpty(rInfo)){
			return ajaxDoneError("????????????????????????");
		}
		info.setCreateBy(getLogAccount().getRealName());
		int i = fraudManageService.addRuleInfo(info);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	@RequestMapping(value="/goUpdateRules")
	public String goUpdateRules(String id, HttpServletRequest request){
		RulesInfo info = fraudManageService.queryRulesInfoDetailByRuleId(id);
		request.setAttribute("info", info);
		return "fraud/rule/rules_update";
	}
	
	@RequestMapping(value="/updateRules")
	public ModelAndView updateRules(RulesInfo info,HttpServletRequest request) throws FraudExcetion{
		if(null == info){
			throw new FraudExcetion("???????????????");
		}
		info.setCreateBy(getLogAccount().getRealName());
		int i = fraudManageService.updateRuleInfo(info);
		if(i>0){
			return ajaxDoneSuccess("???????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	@RequestMapping(value="/queryRuleInfoDetailByRulesId")
	public String queryRuleInfoDetailByRulesId(String id, HttpServletRequest request){
		RulesInfo info = fraudManageService.queryRulesInfoDetailByRuleId(id);
		request.setAttribute("result", info);
		return "fraud/rule/rules_detail";
	}
	
	/**
	 * ?????????????????????
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryRuleProfile")
	public String queryRuleProfile(HttpServletRequest request){
		Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<RuleProfileInfo> list = fraudManageService.queryPageRuleFInfo(criteria);
		request.setAttribute("result", list);
		return "fraud/rule/rules_profile_list";
	}
	
	/**
	 * ????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value="/goAddRulesProFile")
	public String goAddRulesProFile(){
		return "fraud/rule/rules_profile_add";
	}
	
	/**
	 * ??????????????????
	 * @param info
	 * @param request
	 * @return
	 * @throws FraudExcetion 
	 */
	@RequestMapping(value="/addRuleProfile")
	public ModelAndView addRuleProfile(RuleProfileInfo info,HttpServletRequest request) throws FraudExcetion{
		if(null == info){
			throw new FraudExcetion("???????????????");
		}
		info.setCreateBy(getLogAccount().getRealName());
		int i = fraudManageService.addRuleProFileInfo(info);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/**
	 * ???????????????????????????
	 * @return
	 * @throws FraudExcetion 
	 */
	@RequestMapping(value="/goUpdateRulesProFile")
	public String goUpdateRulesProFile(String profileId,HttpServletRequest request) throws FraudExcetion{
		if(null == profileId || profileId.trim().length()==0){
			throw new FraudExcetion("?????????????????????");
		}
		RuleProfileInfo info = fraudManageService.queryRuleProFileInfoByProfileId(profileId);
		request.setAttribute("info", info);
		return "fraud/rule/rules_profile_update";
	}
	
	/**
	 * ??????????????????
	 * @param info
	 * @return
	 */
	@RequestMapping(value="/updateRuleProfile")
	public ModelAndView updateRuleProfile(RuleProfileInfo info,HttpServletRequest request){
		info.setCreateBy(getLogAccount().getRealName());
		int i = fraudManageService.updateRuleProfileInfo(info);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/**
	 * ??????????????????????????????
	 * @param profileId
	 * @param request
	 * @return
	 * @throws FraudExcetion
	 */
	@RequestMapping(value="/goRefRuleProfileLits")
	public String goRefRuleProfileLits(String profileId,HttpServletRequest request) throws FraudExcetion{
		if(null == profileId || profileId.trim().length()==0){
			throw new FraudExcetion("?????????????????????");
		}
		Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<RulesInfo> ruleList = fraudManageService.queryPageRulesList(criteria);
		request.setAttribute("ruleList", ruleList);
		request.setAttribute("profileId", profileId);
		return "fraud/rule/rules_profile_ref_rules";
	}
	
	/**
	 * ?????????????????????????????????
	 * @param profileId
	 * @param rulesId
	 * @param request
	 * @return
	 * @throws FraudExcetion 
	 */
	@RequestMapping(value="/addRulesToRulesProFile")
	public ModelAndView addRulesToRulesProFile(String profileId,String [] rulesId,HttpServletRequest request) throws FraudExcetion{
		if(null == profileId || profileId.trim().length()==0){
			throw new FraudExcetion("?????????????????????");
		}
		if(null == rulesId || rulesId.length ==0){
			throw new FraudExcetion("??????????????????");
		}
		List<RulesRefProFileInfo> list = new ArrayList<RulesRefProFileInfo>();
		for(String ruleId:rulesId){
			RulesRefProFileInfo info = new RulesRefProFileInfo();
			info.setRuleId(ruleId);
			info.setProfileId(profileId);
			info.setCreateBy(getLogAccount().getRealName());
			list.add(info);
		}
		int i = fraudManageService.addRulesToRuleProfile(list);
		return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getOkStatusMsg("????????????,??????????????????"+i+"??????"));
	}
	
	/**
	 * ????????????ID???????????????????????????
	 * @param profileId
	 * @param request
	 * @return
	 * @throws FraudExcetion 
	 */
	@RequestMapping(value="/queryRefRuleProfileLits")
	public String queryRefRuleProfileLits(String profileId,HttpServletRequest request) throws FraudExcetion{
		if(null == profileId || profileId.trim().length()==0){
			throw new FraudExcetion("????????????????????????");
		}
		Criteria criteria = RequestUtil.getCaCriteria(request); 
		PageInfo<RulesInfo> list = fraudManageService.queryPageRefRuleProfileLits(criteria);
		request.setAttribute("result", list);
		return "fraud/rule/rules_profile_ref_rules_list";
	}
	
	/**
	 * 
	 * @param profileId
	 * @param request
	 * @return
	 * @throws FraudExcetion 
	 */
	@RequestMapping(value="/delRulesFromRulesProFile")
	public ModelAndView delRulesFromRulesProFile(String profileId,String [] rulesId,HttpServletRequest request) throws FraudExcetion{
		if(null == profileId || profileId.trim().length()==0 || null == rulesId || rulesId.length == 0){
			throw new FraudExcetion("???????????????");
		}
		int i = fraudManageService.delRulesFromRulesProFile(profileId, rulesId);
		return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getOkStatusMsg("????????????,??????????????????"+i+"??????"));
	}
	
	
	/**************************/
	/**
	 * ?????????????????????
	 * @return
	 */
	@RequestMapping(value="/queryBlackInfoList")
	public String queryBlackInfoList(HttpServletRequest request){
		Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<BlackTextInfo> info = fraudManageService.queryPageBlackInfoList(criteria);
		request.setAttribute("result", info);
		return "/fraud/risk/blackText_list";
	}
	
	/**
	 * ????????????????????????????????????
	 * @return
	 */
	@RequestMapping("/goAddBlackText")
	public String goAddBlackText(){
		return "/fraud/risk/blackText_add";
	}
	
	/**
	 * ?????????????????????
	 * @return
	 * @throws FraudExcetion 
	 */
	@RequestMapping(value="/addBlackTextInfo")
	public ModelAndView addBlackTextInfo(BlackTextInfo info, HttpServletRequest request) throws FraudExcetion{
		if(null == info){
			throw new FraudExcetion("?????????????????????");
		}
		//????????????
		if("EMAIL".equals(info.getBlackType())){
			if(info.getBlackText()==null||!info.getBlackText().contains("@")){
				return ajaxDoneError("??????????????????");
			}
		}
		if("IP".equals(info.getBlackType())){
			if(info.getBlackText()==null||!info.getBlackText().contains(".")){
				return ajaxDoneError("IP???????????????");
			}
		}
		if("CARDNO".equals(info.getBlackType())){
			if(info.getBlackText()==null||!info.getBlackText().matches("[0-9]{10,}")){
				return ajaxDoneError("?????????????????????");
			}
		}
		//?????????????????????
		if("sixAndFourCardNo".equals(info.getBlackType())){
			if(info.getBlackText()==null||!info.getBlackText().matches("[0-9]{6}[*]{3}[0-9]{4}")){
				return ajaxDoneError("??????????????????????????????");
			}
		}
		info.setCreateBy(getLogAccount().getRealName());
		if(com.gateway.fraud.common.util.Constants.RISKELEMENTTYPE_CARDNO.equals(info.getBlackType())){
			try {
				info.setBlackText(Funcs.eccryptSHA(info.getBlackText().trim()));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		BlackTextInfo temp = fraudManageService.queryBlackTextInfoByValueAndType(info.getBlackText(), info.getBlackType());
		if(null != temp){
			return ajaxDoneError("????????????,???????????????????????????");
		}
		int i = fraudManageService.addBlackTextInfo(info);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/**
	 * ?????????????????????
	 * @return
	 * @throws FraudExcetion 
	 */
	@RequestMapping(value="/delBlackTextById")
	public ModelAndView delBlackTextById(String blackId, HttpServletRequest request) throws FraudExcetion{
		if(null == blackId){
			throw new FraudExcetion("?????????????????????");
		}
		int i = fraudManageService.delBlackTextById(blackId);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/**
	 * bin????????????
	 * @return
	 */
	@RequestMapping(value="/queryBinList")
	public String queryBinList(HttpServletRequest request){
		Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<BinInfo> info = fraudManageService.queryPageBinList(criteria);
		request.setAttribute("result", info);
		return "/fraud/risk/bin_list";
	}
	
	
	@RequestMapping(value="/goAddBinInfo")
	public String goAddBinInfo(){
		return "/fraud/risk/bin_add";
	}
	
	/**
	 * ??????BIN??????
	 * @param info
	 * @param request
	 * @return
	 * @throws FraudExcetion
	 */
	@RequestMapping(value="/addBinInfo")
	public ModelAndView addBinInfo(BinInfo info,HttpServletRequest request) throws FraudExcetion{
		if(null == info){
			throw new FraudExcetion("?????????????????????");
		}
		info.setCreateBy(getLogAccount().getRealName());
		int i = fraudManageService.addBinInfo(info);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/**
	 * ??????ID??????Bin??????
	 * @param id
	 * @param request
	 * @return
	 * @throws FraudExcetion
	 */
	@RequestMapping(value="/delBinInfotById")
	public ModelAndView delBinInfotById(String id, HttpServletRequest request) throws FraudExcetion{
		if(null == id){
			throw new FraudExcetion("?????????????????????");
		}
		int i = fraudManageService.deleteBinInfoById(id);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	
	/**
	 * ????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value="/goBatchAddBlackText")
	public String goBatchAddBlackText(){
		return "/fraud/risk/batch_add_blacktype";
	}
	/**************************/
	/**
	 * ????????????ID????????????
	 * */
//	@RequestMapping(value="/deleteRules")
//	public ModelAndView deleteRules(String id){
//		int i=fraudManageService.deleteRulesById(id);
//		if(i>0){
//			return ajaxDoneSuccess("????????????");
//		}else{
//			return ajaxDoneError("????????????");
//		}
//	}
}
