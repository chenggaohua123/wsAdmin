package com.gateway.authorize.web;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gateway.authorize.model.MerchantAuthroizeConfigInfo;
import com.gateway.authorize.service.MerchantAuthroizeConfigInfoService;
import com.gateway.bankmgr.service.BankMgrService;
import com.gateway.brandProduct.service.BrandProductService;
import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.ServiceException;
import com.gateway.fraud.service.FraudManageService;
import com.gateway.merchantmgr.mapper.MerchantMgrDao;
import com.gateway.ratemgr.service.RateMgrService;
import com.gateway.riskmgr.service.RiskMgrService;
import com.gateway.settlemgr.service.SettleMgrService;
import com.gateway.suspicious.service.SuspiciousManageService;
import com.gateway.sysmgr.service.SysMgrService;

/**
 * 商户终端预授权自动确认配置
 * @author honghao
 * @email  honghao@bringallpay.com
 * @version 创建时间：2019年11月5日  上午10:06:35
 */
@Controller
@RequestMapping(value="/merchantAuthroizeConfigInfo")
public class MerchantAuthroizeConfigInfoController extends BaseController{
	@Resource
	private MerchantAuthroizeConfigInfoService  merchantAuthroizeConfigInfoService;
	
	
	@Autowired
	private RiskMgrService riskMgrService;
	@Resource
	private SysMgrService sysMgrService;
	
	@Resource
	private BankMgrService bankMgrService;
	@Autowired
	private RateMgrService rateMgrService;
	
	
	@Autowired
	private MerchantMgrDao merchantMgrDao;
	@Autowired
	private SettleMgrService settleMgrService;
	
	@Autowired
	private FraudManageService fraudManageService;
	
	@Autowired
	private SuspiciousManageService suspiciousManageService;
	
	@Autowired
	private BrandProductService brandProductService;

	/**
	 * 商户预授权确认配置列表
	 * @return
	 */
	@RequestMapping(value="/getMerchantAuthroizeConfigInfoList")
	public String getMerchantAuthroizeConfigInfoList(){
		PageInfo<MerchantAuthroizeConfigInfo> page = merchantAuthroizeConfigInfoService.getMerchantAuthroizeConfigInfoList(getCriteria());
		getRequest().setAttribute("page", page);
		return "authroize/merchantAuthroizeConfigInfoList";
	}
	
	/**
	 * 跳转到商户预授权确认配置新增页面
	 * @return
	 */
	@RequestMapping(value="/goAddMerchantAuthroizeConfigInfo")
	public String goAddMerchantAuthroizeConfigInfo(){
		return "authroize/addMerchantAuthroizeConfigInfo"; 
	}
	
	/**
	 * 添加商户预授权配置
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/addMerchantAuthroizeConfigInfo")
	public ModelAndView addCurrencyToMerchnat(MerchantAuthroizeConfigInfo  merchantAuthroizeConfigInfo) throws ServiceException{
		String realName = getLogAccount().getRealName();
		merchantAuthroizeConfigInfo.setCreateBy(realName);
		merchantAuthroizeConfigInfo.setUpdateBy(realName);
		int count = merchantAuthroizeConfigInfoService.addMerchantAuthroizeConfigInfo(merchantAuthroizeConfigInfo);
		if(count > 0){
			return ajaxDoneSuccess("添加成功。");
		}else{
			return ajaxDoneSuccess("添加失败。");
		}
	}
	
	/**
	 * 跳转到商户预授权配置修改页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/goUpdateMerchantAuthroizeConfigInfo")
	public String goUpdateMerchantAuthroizeConfigInfo(int id){
		MerchantAuthroizeConfigInfo merchantAuthroizeConfigInfo = merchantAuthroizeConfigInfoService.queryMerchantAuthroizeConfigInfoById(id);
		getRequest().setAttribute("merchantAuthroizeConfigInfo", merchantAuthroizeConfigInfo);
		return "authroize/updateMerchantAuthroizeConfigInfo";
	}
	
	/**
	 * 修改商户预授权配置
	 * @param info
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value="/updateMerchantAuthroizeConfigInfo")
	public ModelAndView updateMerchantAuthroizeConfigInfo(MerchantAuthroizeConfigInfo merchantAuthroizeConfigInfo) throws ServiceException{
		merchantAuthroizeConfigInfo.setUpdateBy(getLogAccount().getRealName());
		int count = merchantAuthroizeConfigInfoService.updateMerchantAuthroizeConfigInfo(merchantAuthroizeConfigInfo);
		if(count > 0){
			return ajaxDoneSuccess("修改成功。");
		}else{
			return ajaxDoneSuccess("修改失败。");
		}
	}
	
	/**
	 * 跳转到商户预授权配置修改页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/goDeleteMerchantAuthroizeConfigInfo")
	public String goDeleteMerchantAuthroizeConfigInfo(int id){
		MerchantAuthroizeConfigInfo merchantAuthroizeConfigInfo = merchantAuthroizeConfigInfoService.queryMerchantAuthroizeConfigInfoById(id);
		getRequest().setAttribute("merchantAuthroizeConfigInfo", merchantAuthroizeConfigInfo);
		return "authroize/updateMerchantAuthroizeConfigInfo";
	}
	
	/**
	 * 修改商户预授权配置
	 * @param info
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value="/deleteMerchantAuthroizeConfigInfo")
	public ModelAndView deleteMerchantAuthroizeConfigInfo(int  id) throws ServiceException{
		int count = merchantAuthroizeConfigInfoService.deleteMerchantAuthroizeConfigInfo(id);
		if(count > 0){
			return ajaxDoneSuccess("删除成功。");
		}else{
			return ajaxDoneSuccess("删除失败。");
		}
	}
	
}