package com.gateway.exchangRate.web;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.APIException;
import com.gateway.exchangRate.model.BankSourceRateDetail;
import com.gateway.exchangRate.model.BankSourceRateInfo;
import com.gateway.exchangRate.model.CheckBankSourceRateInfo;
import com.gateway.exchangRate.model.CreateCheckBankSourceRateInfo;
import com.gateway.exchangRate.model.ExchangRateInfo;
import com.gateway.exchangRate.model.MerExchangRateInfo;
import com.gateway.exchangRate.service.ExchangRateService;

@Controller
@RequestMapping(value="/exchangRate")
public class ExchangRateController extends BaseController{
	
	@Autowired(required=true)
	private ExchangRateService exchangRateService;
	
	
	/**
	 * 
	 *汇率查询 
	 */
	@RequestMapping(value="/queryExchangRateList")
	public String queryExchangRateList(ExchangRateInfo exchangRateInfo){
	    PageInfo<ExchangRateInfo> page=exchangRateService.queryExchangRateList(getCriteria());
		getRequest().setAttribute("page", page);
		return "exchangRate/listExchangRate";
	}
	/**
	 * 
	 *跳转汇率新增 
	 */
	@RequestMapping(value="/goAddExchangRate")
	public String goAddExchangRate(){
		List<String> bankRateTypes=exchangRateService.queryRateType();
		getRequest().setAttribute("bankRateTypes", bankRateTypes);
		return "exchangRate/addExchangRate";
	}
	
	/**
	 * 
	 * 汇率新增
	 */
	@RequestMapping(value="/addExchangRate")
	public ModelAndView addExchangRate(ExchangRateInfo exchangRateInfo){
		
		String eri=exchangRateService.queryExchangRate(exchangRateInfo);
		if(null!=eri){
			return ajaxDoneError(eri);
		}
		exchangRateInfo.setCreateBy(getLogAccount().getRealName());
		int i=exchangRateService.addExchangRate(exchangRateInfo);
//		int i=exchangRateService.insertExchangRateLog(exchangRateInfo);
		if(i>0){
			return ajaxDoneSuccess("添加成功，提交待审核");
		}else{
			return ajaxDoneError("添加失败");
		}

	}
	/**
	 * 跳转汇率信息修改页面
	 * 
	 */
	
	@RequestMapping(value="/goUpdateExchangRate")
	public String goUpdateExchangRate(int id){
		List<String> bankRateTypes=exchangRateService.queryRateType();
		getRequest().setAttribute("bankRateTypes", bankRateTypes);
		ExchangRateInfo exchangRateInfo=exchangRateService.queryExchangRateInfoId(id);
		getRequest().setAttribute("exchangRateInfo", exchangRateInfo);
		return "exchangRate/updateExchangRate";
	}
	
	/**
	 * 
	 * 定制结算汇率修改
	 */
	@RequestMapping(value="/updateExchangRate")
	public ModelAndView updateExchangRate(ExchangRateInfo exchangRateInfo){
		String eri=exchangRateService.queryExchangRate(exchangRateInfo);
		if(null!=eri){
			return ajaxDoneError(eri);
		}
		exchangRateInfo.setCreateBy(getLogAccount().getRealName());
		int Rate=exchangRateService.updateExchangRate(exchangRateInfo);
//		int Rate=exchangRateService.insertExchangRateLog(exchangRateInfo);
		if(Rate>0){
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}

	}
	/**
	 * 实现：删除汇率记录
	 * @param form删除条件
	 * @return 删除结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	@RequestMapping(value="/deleteExchangeRate")
	public ModelAndView deleteExchangeRate(ExchangRateInfo form){
		int a = exchangRateService.deleteExchangeRate(form);
		if(a>0){
			return ajaxDoneSuccess("删除汇率成功！");
		}else{
			return ajaxDoneError("删除汇率失败！");
		}
	}

	/**
	 * 
	 *商户定制汇率查询 
	 */
	@RequestMapping(value="/queryMerchantExchangRateList")
	public String queryMerchantExchangRateList(ExchangRateInfo exchangRateInfo){
	    PageInfo<ExchangRateInfo> page=exchangRateService.queryMerchantExchangRateList(getCriteria());
		getRequest().setAttribute("page", page);
		return "exchangRate/listMerchantExchangRate";
	}
	/**
	 * 实现：删除商户定制汇率记录
	 * @param vo 删除条件
	 * @return 删除结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	@RequestMapping(value="/deleteMerchantExchangeRate")
	public ModelAndView deleteMerchantExchangeRate(ExchangRateInfo vo) {
		int a = exchangRateService.deleteMerchantExchangeRate(vo);
		if(a>0){
			return ajaxDoneSuccess("删除汇率成功！");
		}else{
			return ajaxDoneError("删除汇率失败！");
		}
	}
	/**
	 * 
	 * 
	 *跳转汇率商户定制新增 
	 */
	@RequestMapping(value="/goAddMerchantExchangRate")
	public String goAddMerchantExchangRate(){
		List<String> busGroupNames=exchangRateService.queryRateGroupNamesByType("bus");
		getRequest().setAttribute("bus", busGroupNames);
		return "exchangRate/addMerchantExchangRate";
	}
	/**
	 * 根据类型查询汇率组名
	 * */
	@ResponseBody
	@RequestMapping(value="/queryRateGroupNamesByType")
	public List<String> queryRateGroupNamesByType(String type){
		if(null==type){
			type="bus";
		}
		List<String> list=  exchangRateService.queryRateGroupNamesByType(type);
		return list;
	}
	/**
	 * 汇率商户定制新增
	 * */
	@RequestMapping(value="/addMerchantExchangRate")
	public ModelAndView addMerchantExchangRate(MerExchangRateInfo merExchangRateInfo){
		MerExchangRateInfo info = exchangRateService.queryMerExchangRateInfo(merExchangRateInfo);
		if(!StringUtils.isEmpty(info)){
			return ajaxDoneError("该商户不能重复绑定汇率组");
		}
		merExchangRateInfo.setCreateBy(getLogAccount().getRealName());
		int i=exchangRateService.addMerchantExchangRate(merExchangRateInfo);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	/**
	 * 跳转到修改商户定制汇率页面
	 * */
	@RequestMapping(value="/goUpdateMerchantExchangRate")
	public String goUpdateMerchantExchangRate(String id){
		MerExchangRateInfo merExchangRateInfo=exchangRateService.queryMerchantExchangRateById(id);
		List<String> list=  exchangRateService.queryRateGroupNamesByType(merExchangRateInfo.getType());
		list.remove("0");
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("info", merExchangRateInfo);
		return "exchangRate/updateMerchantExchangRate";
	}

	
	/**
	 * 修改商户定制汇率
	 * */
	@RequestMapping(value="/updateMerchantExchangRate")
	public ModelAndView updateMerchantExchangRate(MerExchangRateInfo merExchangRateInfo){
		MerExchangRateInfo info = exchangRateService.queryMerExchangRateInfo(merExchangRateInfo);
		if(!StringUtils.isEmpty(info)){
			return ajaxDoneError("该商户不能重复绑定汇率组");
		}
		merExchangRateInfo.setCreateBy(getLogAccount().getRealName());
		int i=exchangRateService.updateMerchantExchangRate(merExchangRateInfo);
		if(i>0){
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	/**
	 * 显示中国银行外币对人民币汇率
	 * */
	@RequestMapping("/listBankSourceRateInfo")
	public String listBankSourceRateInfo(){
		PageInfo<BankSourceRateDetail> page=exchangRateService.queryBankSourceRateList(getCriteria());
		getRequest().setAttribute("page", page);
		List<String> rateTypes=exchangRateService.queryRateType();
		getRequest().setAttribute("rateTypes", rateTypes);
		return "exchangRate/listBankSourceRate";
	}
	/**
	 * 显示银行汇率历史记录
	 * */
	@RequestMapping("/goListBankSourceRateLog")
	public String goListBankSourceRateLog(){
		PageInfo<BankSourceRateDetail> page=exchangRateService.queryBankSourceRateLogListById(getCriteria());
		getRequest().setAttribute("page", page);
		return "exchangRate/listBankSourceRateLog";
	}
	/**
	 * 跳转到银行汇率审核条件页面
	 * */
	@RequestMapping("/goCreateCheckBankSourceRate")
	public String goCreateCheckBankSourceRate(){
		List<String> bankRateTypes=exchangRateService.queryRateType();
		getRequest().setAttribute("bankRateTypes", bankRateTypes);
		List<String> sourceCurrencys=exchangRateService.querySourceCurrency();
		getRequest().setAttribute("sourceCurrencys", sourceCurrencys);
		List<String> targetCurrencys=exchangRateService.queryTargetCurrency();
		getRequest().setAttribute("targetCurrencys", targetCurrencys);
		List<String> groupNames=  exchangRateService.queryRateGroupNamesByType("");
		getRequest().setAttribute("groupNames", groupNames);
		return "exchangRate/createCheckBankSourceRate";
	}
	/**
	 * 
	 * 生成银行汇率审核信息
	 * */
	@RequestMapping("/createCheckBankSourceRate")
	public ModelAndView createCheckBankSourceRate(CreateCheckBankSourceRateInfo info){
		info.setCreateBy(getLogAccount().getRealName());
		int count=exchangRateService.createCheckBankSourceRate(info);
		if(count>0){
			return ajaxDoneSuccess("成功生成"+count+"条记录");
		}else{
			return ajaxDoneError("汇率表中没有相应的记录");
		}
	}
	/**
	 * 列表显示审核银行汇率信息
	 * */
	@RequestMapping("/listCheckBankSourceRate")
	public String listCheckBankSourceRate(){
		PageInfo<CheckBankSourceRateInfo> page=exchangRateService.queryCheckBankSourceRateInfo(getCriteria());
		getRequest().setAttribute("page", page);
		return "exchangRate/listCheckBankSourceRate";
	}
	
	@ResponseBody
	@RequestMapping("/checkBankSourceRate")
	public int checkBankSourceRate(String[] ids,String status){
		String updateBy=getLogAccount().getRealName();
		return exchangRateService.updateBankRateToExchangRate(ids,status,updateBy);
	}
	
	/** 批量审核汇率 */
	@ResponseBody
	@RequestMapping("/checkExchangRate")
	public int checkExchangRate(int[] ids,int status)throws APIException{
		String updateBy=getLogAccount().getRealName();
		exchangRateService.checkExchangRate(ids,status,updateBy);
		return 1;
	}
	
	/** 获取 */
	@RequestMapping("/gotoCheckExchangRateLog")
	public String gotoCheckExchangRateLog(int id){
		ExchangRateInfo info = new ExchangRateInfo();
		info.setRateId(id);
		List<ExchangRateInfo> list = exchangRateService.gotoCheckExchangRateLog(info);
		getRequest().setAttribute("list", list);
		return "exchangRate/listExchangRateInfoLog";
	}
	
	@ResponseBody
	@RequestMapping("/getBankRate")
	public String getBankRate(String sourceCurrency,String targetCurrency,String rateType){
		if(StringUtils.isEmpty(sourceCurrency) || StringUtils.isEmpty(targetCurrency) || StringUtils.isEmpty(rateType)){
			return null;
		}
		BankSourceRateInfo info = new BankSourceRateInfo();
		info.setSourceCurrencyCode(sourceCurrency);
		info.setTargetCurrencyCode(targetCurrency);
		info.setRateType(rateType);
		BigDecimal rate = exchangRateService.getBankRate(info);
		if(StringUtils.isEmpty(rate)){
			return null;
		}
		return rate + "";
	}
	
	/** 去审核汇率页面 */
	@RequestMapping("/toCheckExchangRate")
	public String toCheckExchangRate(){
		PageInfo<ExchangRateInfo> page=exchangRateService.queryExchangRateListLog(getCriteria());
		getRequest().setAttribute("page", page);
		return "exchangRate/listExchangRateLog";
	}
	
}
