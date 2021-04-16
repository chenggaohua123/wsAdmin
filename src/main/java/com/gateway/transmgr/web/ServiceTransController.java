package com.gateway.transmgr.web;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.utils.Funcs;
import com.gateway.common.utils.Tools;
import com.gateway.sysmgr.model.CardBinInfo;
import com.gateway.transmgr.model.TransInfo;
import com.gateway.transmgr.service.ServiceTransService;
import com.gateway.transmgr.service.TransMgrService;

/** 客服交易数据查询 */
@Controller
@RequestMapping(value="/transmgr/serviceTrans")
public class ServiceTransController extends BaseController{
	
	@Resource
	private ServiceTransService serviceTransService;
	
	@Resource
	private TransMgrService transMgrService;
	
	@RequestMapping(value="/goServiceTransListPage")
	public String goServiceTransListPage(){
		return "transmgr/serviceTrans/serviceTransList";
	}
	
	/** 去客服交易数据查询 */
	@RequestMapping(value="/goServiceTransList")
	public String goServiceTransList() throws Exception{
		Criteria criteria = getCriteria();
		Map<String, Object> map = criteria.getCondition();
		if(checkParam(map)){
			getRequest().setAttribute("respMsg", "商户号、流水号、订单号、交易网站、邮箱、IP、前六后四卡号、姓名、电话其中任一查询条件不能为空");
		}else{
			PageInfo<TransInfo> list = serviceTransService.queryServiceTransList(criteria);
			for(TransInfo info:list.getData()){
				info.setCbInfo(transMgrService.queryCardBinInfoByBin(info.getCheckNo()));
				}
			getRequest().setAttribute("page", list);
		}
		return "transmgr/serviceTrans/serviceTransList";
	}
	
	private boolean checkParam(Map<String, Object> map){
		if(org.springframework.util.StringUtils.isEmpty(map.get("merNo"))&& 
				org.springframework.util.StringUtils.isEmpty(map.get("tradeNo"))&&
				org.springframework.util.StringUtils.isEmpty(map.get("orderNo"))&&
				org.springframework.util.StringUtils.isEmpty(map.get("payWebSite"))&&
				org.springframework.util.StringUtils.isEmpty(map.get("email"))&&
				org.springframework.util.StringUtils.isEmpty(map.get("ipAddress"))&&
				org.springframework.util.StringUtils.isEmpty(map.get("cardStart"))&&
				org.springframework.util.StringUtils.isEmpty(map.get("cardEnd"))&&
				org.springframework.util.StringUtils.isEmpty(map.get("cardFullName"))&&
				org.springframework.util.StringUtils.isEmpty(map.get("cardFullPhone"))){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据流水号查询交易信息
	 * @return
	 */
	@RequestMapping(value="/queryTransInfo")
	public String queryTransInfo(String tradeNo)throws Exception{
		TransInfo transInfo=transMgrService.queryTransInfoByTradeNo(tradeNo);
		if(transInfo.getGrPerName()==null  || "".equals(transInfo.getGrPerName())){
			transInfo.setGrPerName(transInfo.getCardFullName());
		}
		if(transInfo.getCheckNo()!=null&&!"".equals(transInfo.getCheckNo())){
		transInfo.setSixAndFourCardNo(Funcs.decrypt(transInfo.getCheckNo())+"***"+Funcs.decrypt(transInfo.getLast()));
		}
		CardBinInfo cbInfo=transMgrService.queryCardBinInfoByBin(Funcs.decrypt(transInfo.getCheckNo()));
		getRequest().setAttribute("cbInfo", cbInfo);
		transInfo.setWebInfo( Tools.parseWebInfoToResourceType(transInfo.getWebInfo()));
		getRequest().setAttribute("transInfo", transInfo);
		return "transmgr/serviceTrans/serviceTransDetail";
	}
	
}
