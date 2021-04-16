package com.gateway.fraud.web;

import java.util.List;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


//import com.common.util.SysPropertiesConfig;
//import com.gateway.fraud.model.AccountInfo;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.ServiceException;
import com.gateway.fraud.model.MerchantRefRuleProfileInfo;
import com.gateway.fraud.model.AutoRiskInfo;
import com.gateway.fraud.model.FraudInfo;
import com.gateway.fraud.model.RuleProfileInfo;
import com.gateway.fraud.service.FraudManageService;
import com.gateway.fraud.common.util.Constant;
import com.gateway.fraud.common.util.RequestUtil;


@Controller
@RequestMapping("/fraud/account")
public class FraudAccountManageController extends BaseController implements Constant{
	@Resource
	private FraudManageService fraudManageService;
	
	public FraudManageService getFraudManageService() {
		return fraudManageService;
	}
	public void setFraudManageService(FraudManageService fraudManageService) {
		this.fraudManageService = fraudManageService;
	}

	
	/**
	 * 查询账号关联集合列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryAccountRefProfileList")
	public String queryAccountRefProfileList(HttpServletRequest request){
		Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<MerchantRefRuleProfileInfo> result =  fraudManageService.queryPageAccountRefProfileList(criteria);
		request.setAttribute("result", result);
		return "fraud/account/account_ref_profile_list";
	}
	/**
	 * 跳转到添加账号关联规则集合列表的页面
	 * @return
	 */
	@RequestMapping(value="/goAddProfileToAccount")
	public String goAddProfileToAccount(HttpServletRequest request){
		return "fraud/account/account_ref_profile_add";
	}
	@RequestMapping(value="/goUpdateAccountRefProfileInfoById")
	public String goUpdateAccountRefProfileInfoById(String id,HttpServletRequest request) throws ServiceException{
		if(null == id || id.trim().length()==0){
			throw new ServiceException("参数不能为空。");
		}
		MerchantRefRuleProfileInfo info = fraudManageService.queryProfileInfoById(id);
		if(null == info){
			throw new ServiceException("记录不存在，不能更新。");
		}
		request.setAttribute("info", info);
		return "fraud/account/account_ref_profile_update";
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
		int i = fraudManageService.updateAccountRefProfileInfoById(info);
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
		List<MerchantRefRuleProfileInfo> temp = fraudManageService.queryProfileInfoByMerNoAndTerNo(info.getMerNo(), info.getTerNo(),info.getProFileId());
		if(null != temp && temp.size()>0){
			throw new ServiceException("改记录已经存在，不能继续添加");
		}
		info.setProFileId(request.getParameter("info.proFileId"));
		info.setProFileName(request.getParameter("info.proFileName"));
		info.setCreateBy(getLogAccount().getRealName());
		int i = fraudManageService.addProfileToAccount(info);
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
		PageInfo<RuleProfileInfo> list = fraudManageService.queryPageRuleFInfo(criteria);
		request.setAttribute("result", list);
 		return "fraud/account/profile_lookup_list";
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
		int i = fraudManageService.delAccountFromList(id);
		if(i>0){
			return ajaxDoneSuccess("删除成功");
		}else{
			return ajaxDoneError("删除失败");
		}
	}
	
	/**
	 * 风控记录查询
	 * @return
	 */
	@RequestMapping(value="/queryFraudRecordList")
	public String queryFraudRecordList(HttpServletRequest request){
		Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<FraudInfo> info = fraudManageService.queryPageFraudRecordList(criteria);
		request.setAttribute("result", info);
		return "fraud/account/fraudrecord_list";
	}
	
	
	/**
	 * 黑名单库查询记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryBlackTextLimitList")
	public String queryBlackTextLimitList(HttpServletRequest request){
		Criteria criteria = RequestUtil.getCaCriteria(request);
		PageInfo<AutoRiskInfo> info = fraudManageService.queryPageBlackTextLimitList(criteria);
		request.setAttribute("result", info);
		return "fraud/account/blackrecord_list";
	}
	
	/**
	 * 查询风控详细信息
	 * @param txId
	 * @param request
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/queryFraudDetailByTxId")
	public String queryFraudDetailByTxId(String txId,HttpServletRequest request) throws ServiceException{
		if(null == txId || txId.trim().length()==0){
			throw new ServiceException("参数不全。");
		}
		FraudInfo info = fraudManageService.queryFraudDetailByTxId(txId);
		request.setAttribute("info", info);
		return "fraud/account/fraudrecord_detail";
	}
	
	
	/*@RequestMapping(value="/reviewRecord/{opCode}")
	public ModelAndView reviewRecord(@PathVariable String opCode,String [] txId,HttpServletRequest request){
		if(null == opCode || "".equals(opCode.trim())){
			return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getErrorStatusMsg("请选则操作方式"));
		}
		int count = 0 ;
		//String reviewUrl =  (String)SysPropertiesConfig.getContextProperty("record.review.url");
		for(String tempTxId : txId){
			FraudInfo info = fraudManageService.queryFraudDetailByTxId(tempTxId);
			//AccountInfo accountInfo = fraudManageService.queryFraudAccountInfoByAccountNo(info.getAccountNo());
			if(null == accountInfo){
				continue;
			}
			if(null != info && "0001".equals(info.getRet())){
				HttpClient clinet =  HttpUtil.getThreadSafeHttpClient();
				Map<String, String> param =Maps.newHashMap();
				param.put("accountNo", accountInfo.getAccountNo());
				param.put("passWord", accountInfo.getPass());
				param.put("orderNo", info.getOrderNo());
				param.put("opCode", opCode);
				param.put("tradeNo", info.getTxId());
				HttpResponse resp =  HttpUtil.doPost(clinet, new HttpPost(reviewUrl), param, "UTF-8");
				try{
					String respStr = EntityUtils.toString(resp.getEntity());
					ReviewInfo retObj = JSON.parseObject(respStr, ReviewInfo.class);
					if("0002".equals(retObj.getRet()) || "0000".equals(retObj.getRet())){
						count++;
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				continue;
			}
		}
		return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getOkStatusMsg(count+"操作成功"));
	}*/
	
}
