package com.gateway.riskmgr.web;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.BaseDataListener;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excel.BIRow;
import com.gateway.common.excel.BISheet;
import com.gateway.common.excel.BIWorkbook;
import com.gateway.common.excetion.ServiceException;
import com.gateway.common.utils.Funcs;
import com.gateway.common.utils.Tools;
import com.gateway.riskmgr.model.BankLimitInfo;
import com.gateway.riskmgr.model.BrandLimitInfo;
import com.gateway.riskmgr.model.CountryInfo;
import com.gateway.riskmgr.model.CountryLimit;
import com.gateway.riskmgr.model.ExportRiskTransInfo;
import com.gateway.riskmgr.model.MaxmindTransInfo;
import com.gateway.riskmgr.model.MaxmindWarnInfo;
import com.gateway.riskmgr.model.MerchantPayCountLimit;
import com.gateway.riskmgr.model.RiskCountryInfoLog;
import com.gateway.riskmgr.model.RiskTransInfo;
import com.gateway.riskmgr.model.ThreatMetrixResultInfo;
import com.gateway.riskmgr.service.RiskMgrService;


@Controller
@RequestMapping(value = "/riskmgr")
public class RiskMgrController extends BaseController {
	
	@Autowired
	private RiskMgrService riskMgrService;
	
	
	@RequestMapping(value="/listRiskTransInfo")
	public String listRiskTransInfo(){
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String transDateStart=sdf.format(date);
			String transDateEnd=sdf.format(date);
			criteria.getCondition().put("transDateStart", transDateStart);
			criteria.getCondition().put("transDateEnd", transDateEnd);
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			criteria.getCondition().put("doStatus", "reject");
			PageInfo<RiskTransInfo> page=riskMgrService.queryRiskTransInfo(criteria);
			getRequest().setAttribute("page", page);
		}
		return "riskmgr/listRiskTransInfo";
	}
	
	@RequestMapping(value="/listRiskPendingTransInfo")
	public String listRiskPendingTransInfo(){
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String transDateStart=sdf.format(date);
			String transDateEnd=sdf.format(date);
			criteria.getCondition().put("transDateStart", transDateStart);
			criteria.getCondition().put("transDateEnd", transDateEnd);
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			criteria.getCondition().put("doStatus", "review");
			PageInfo<RiskTransInfo> page=riskMgrService.queryRiskTransInfo(criteria);
			getRequest().setAttribute("page", page);
		}
		return "riskmgr/listRiskPendingTransInfo";
	}
	
	@RequestMapping(value="/exportRiskTransInfo")
	public void exportRiskTransInfo(HttpServletResponse resp) throws Exception{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "transList.xls" ); 
		Criteria criteria=getCriteria();
		criteria.getCondition().put("doStatus", "reject");
		List<ExportRiskTransInfo> list = riskMgrService.exportRiskTransList(criteria);
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("??????????????????", 0);
		//String[] headerName = { "???????????????", "?????????","?????????","?????????","????????????","????????????","???????????????","?????????","????????????","????????????","????????????","????????????"};
		
		  String[] headerName={"?????????","?????????","?????????","?????????","??????","??????????????????","??????ID","??????????????????"
				  ,"????????????","????????????","??????????????????","??????","??????","??????","IP","????????????",
				  "????????????","?????????/ ???","????????????","??????","????????????","?????????/???","????????????","????????????"};
		// ????????????
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			ExportRiskTransInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getMerNo()));
			sheet.addCell( new Label(col++, row, info.getTerNo()));
			sheet.addCell( new Label(col++, row, info.getTradeNo()));
			sheet.addCell( new Label(col++, row, info.getOrderNo()));
			sheet.addCell( new Label(col++, row, info.getWebsite()));
			sheet.addCell( new Label(col++, row, info.getDoDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getDoDate()):""));
			sheet.addCell( new Label(col++, row, info.getRuleId()));
			sheet.addCell( new Label(col++, row, info.getDoReason()));
			sheet.addCell( new Label(col++, row, "????????????"));
			sheet.addCell( new Label(col++,row,info.getCurrency()+" "+info.getAmount()));//????????????
			if(info.getCheckNo()!=null&&!"".equals(info.getCheckNo())){
				sheet.addCell(new Label(col++, row, Funcs.decrypt(info.getCheckNo())+"***"+Funcs.decrypt(info.getLast())));
			}else{
				sheet.addCell(new Label(col++, row, ""));
			}
			sheet.addCell( new Label(col++, row,info.getCardFullName()));
			sheet.addCell( new Label(col++, row,info.getEmail()));
			sheet.addCell( new Label(col++, row,info.getCardFullPhone()));
			sheet.addCell( new Label(col++, row,info.getIp()));
			sheet.addCell( new Label(col++, row,info.getIpCountry()));
			sheet.addCell( new Label(col++, row,info.getGrCountry()));
			sheet.addCell( new Label(col++, row,info.getGrState()));
			sheet.addCell( new Label(col++, row,info.getGrAddress()));
			sheet.addCell( new Label(col++, row,info.getGrZipCode()));
			sheet.addCell( new Label(col++, row,info.getCardCountry()));
			sheet.addCell( new Label(col++, row,info.getCardState()));
			sheet.addCell( new Label(col++, row,info.getCardAddress()));
			sheet.addCell( new Label(col++, row,info.getRiskScore()));
			
		}
			book.write();
			book.close();
	}
	@RequestMapping(value="/exportRiskPendingTransInfo")
	public void exportRiskPendingTransInfo(HttpServletResponse resp) throws Exception{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "transList.xls" ); 
		Criteria criteria=getCriteria();
		criteria.getCondition().put("doStatus", "review");
		List<ExportRiskTransInfo> list = riskMgrService.exportRiskTransList(criteria);
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("??????????????????", 0);
		//String[] headerName = { "???????????????", "?????????","?????????","?????????","????????????","????????????","???????????????","?????????","????????????","????????????","????????????","????????????"};
		
		  String[] headerName={"?????????","?????????","?????????","?????????","??????","??????ID","??????????????????","??????????????????","????????????????????????"
				  ,"??????","????????????","??????????????????","??????","??????","??????","IP","????????????","????????????","?????????/ ???",
				  "????????????","??????","????????????","?????????/???","????????????","????????????","??????????????????"};
		// ????????????
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			ExportRiskTransInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getMerNo()));
			sheet.addCell( new Label(col++, row, info.getTerNo()));
			sheet.addCell( new Label(col++, row, info.getTradeNo()));
			sheet.addCell( new Label(col++, row, info.getOrderNo()));
			sheet.addCell( new Label(col++, row, info.getWebsite()));
			sheet.addCell( new Label(col++, row, info.getRuleId()));
			sheet.addCell( new Label(col++, row, info.getDoReason()));
			sheet.addCell( new Label(col++, row, info.getDoDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getDoDate()):""));
			sheet.addCell( new Label(col++, row, info.getDoEndDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getDoEndDate()):""));
			sheet.addCell( new Label(col++, row, "00".equals(info.getRespCode())?"????????????":"????????????"));
			sheet.addCell( new Label(col++,row,info.getCurrency()+" "+info.getAmount()));//????????????
			if(info.getCheckNo()!=null&&!"".equals(info.getCheckNo())){
				sheet.addCell(new Label(col++, row, Funcs.decrypt(info.getCheckNo())+"***"+Funcs.decrypt(info.getLast())));
			}else{
				sheet.addCell(new Label(col++, row, ""));
			}
			sheet.addCell( new Label(col++, row,info.getCardFullName()));
			sheet.addCell( new Label(col++, row,info.getEmail()));
			sheet.addCell( new Label(col++, row,info.getCardFullPhone()));
			sheet.addCell( new Label(col++, row,info.getIp()));
			sheet.addCell( new Label(col++, row,info.getIpCountry()));
			sheet.addCell( new Label(col++, row,info.getGrCountry()));
			sheet.addCell( new Label(col++, row,info.getGrState()));
			sheet.addCell( new Label(col++, row,info.getGrAddress()));
			sheet.addCell( new Label(col++, row,info.getGrZipCode()));
			sheet.addCell( new Label(col++, row,info.getCardCountry()));
			sheet.addCell( new Label(col++, row,info.getCardState()));
			sheet.addCell( new Label(col++, row,info.getCardAddress()));
			sheet.addCell( new Label(col++, row,info.getRiskScore()));
			sheet.addCell( new Label(col++, row,info.getRespMsg()));
			
		}
			book.write();
			book.close();
	}
	
	/**
	 * ????????????????????????
	 * */
	@RequestMapping(value="/listMerchantCountryLimit")
	public String listMerchantCountryLimit(){
		PageInfo<CountryLimit> page=riskMgrService.queryMerchantCountryLimit(getCriteria());
		getRequest().setAttribute("page", page);
		return "riskmgr/listMerchantCountryLimit";
	}
	
	/**
	 * ??????????????????????????????
	 * */
	@RequestMapping(value="/listMerchantPayCountLimit")
	public String listMerchantPayCountLimit(){
		PageInfo<MerchantPayCountLimit> page=riskMgrService.queryMerchantPayCountLimit(getCriteria());
		getRequest().setAttribute("page", page);
		return "riskmgr/listMerchantPayCountLimit";
	}
	/**
	 * ???????????????????????????????????????
	 * @throws ServiceException 
	 * */
	@RequestMapping(value="/goUpdateMerchantPayCountLimit")
	public String goUpdateMerchantPayCountLimit(String id) throws ServiceException{
		MerchantPayCountLimit info=riskMgrService.queryMerchantPayCountLimitById(id);
		if(!"0".equals(info.getStatus())){
			throw new ServiceException("???????????????????????????");
		}
		getRequest().setAttribute("info", info);
		return "riskmgr/updateMerchantCountryLimit";
	}
	/**
	 * ????????????????????????
	 * */
	@RequestMapping(value="/updateMerchantPayCountLimit")
	public ModelAndView updateMerchantPayCountLimit(MerchantPayCountLimit info){
		info.setLastModifyBy(getLogAccount().getRealName());
		int i=riskMgrService.updateMerchantPayCountLimitById(info);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	/**
	 * ???????????????????????????
	 * @throws ServiceException 
	 * */
	@RequestMapping(value="/goAddOrUpdateMerchantPayCountLimit")
	public String goAddOrUpdateMerchantPayCountLimit(String id) throws ServiceException{
		if(null != id && !"".equals(id)){
			MerchantPayCountLimit info=riskMgrService.queryMerchantPayCountLimitById(id);
			getRequest().setAttribute("info", info);
		}
		return "riskmgr/addOrUpdateMerchantCountryLimit";
	}
	/**
	 * ????????????????????????????????????
	 * */
	@RequestMapping(value="/addOrUpdateMerchantPayCountLimit")
	public ModelAndView addOrUpdateMerchantPayCountLimit(MerchantPayCountLimit info){
		info.setLastModifyBy(getLogAccount().getRealName());
		int i=0;
		info.setCreateBy(getLogAccount().getRealName());
		if(null != info.getId() && ! "".equals(info.getId())){
			i=riskMgrService.modifyMerchantPayCountLimitById(info);
		}else{
			i=riskMgrService.addMerchantPayCountLimitById(info);
		}
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	/**
	 * ????????????????????????????????????
	 * */
	@RequestMapping(value="/deleteMerchantPayCountLimit")
	public ModelAndView deleteMerchantPayCountLimit(String id){
		int i=riskMgrService.deleteMerchantPayCountLimit(id);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	
	
	
	@RequestMapping(value="/listBrandLimitInfo")
	public String listBrandLimitInfo(){
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			if("0".equals(criteria.getCondition().get("bankId"))){
				criteria.getCondition().put("bankId", "");
			}
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<BrandLimitInfo> page=riskMgrService.queryBrandLimitInfo(criteria);
			getRequest().setAttribute("page", page);
		}
		return "riskmgr/listBrandLimitInfo";
	}
	
	@RequestMapping(value="/goAddBrandLimitInfo")
	public String goAddBrandLimitInfo(){
		return "riskmgr/addBrandLimitInfo";
	}
	@RequestMapping(value="/addBrandLimitInfo")
	public ModelAndView addBrandLimitInfo(BrandLimitInfo info){
		info.setCreateBy(getLogAccount().getRealName());
		int i=riskMgrService.addBrandLimitInfo(info);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	@RequestMapping(value="/goUpdateBrandLimitInfo")
	public String goUpdateBrandLimitInfo(String id ){
		
		BrandLimitInfo info=riskMgrService.queryBrandLimitInfoById(id);
		getRequest().setAttribute("info", info);
		return "riskmgr/updateBrandLimitInfo";
	}
	@RequestMapping(value="/updateBrandLimitInfo")
	public ModelAndView updateBrandLimitInfo(BrandLimitInfo info){
		info.setCreateBy(getLogAccount().getRealName());
		int i=riskMgrService.updateBrandLimitInfo(info);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	@RequestMapping(value="/deleteBrandLimitInfo")
	public ModelAndView deleteBrandLimitInfo(String[] ids){
		int i=riskMgrService.deleteBrandLimitInfoByIds(ids);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/**
	 * ????????????
	 * @return
	 */
	@RequestMapping(value="/listBankLimitInfo")
	public String listBankLimitInfo(){
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			if("0".equals(criteria.getCondition().get("bankId"))){
				criteria.getCondition().put("bankId", "");
			}
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<BankLimitInfo> page=riskMgrService.queryBankLimitInfo(criteria);
			getRequest().setAttribute("page", page);
		}
		return "riskmgr/listBankLimitInfo";
	}
	
	@RequestMapping(value="/goAddBankLimitInfo")
	public String goAddBankLimitInfo(){
		return "riskmgr/addBankLimitInfo";
	}
	@RequestMapping(value="/addBankLimitInfo")
	public ModelAndView addBankLimitInfo(BankLimitInfo info){
		info.setCreateBy(getLogAccount().getRealName());
		int i=riskMgrService.addBankLimitInfo(info);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	@RequestMapping(value="/goUpdateBankLimitInfo")
	public String goUpdateBankLimitInfo(String id ){
		
		BankLimitInfo info=riskMgrService.queryBankLimitInfoById(id);
		getRequest().setAttribute("info", info);
		return "riskmgr/updateBankLimitInfo";
	}
	@RequestMapping(value="/updateBankLimitInfo")
	public ModelAndView updateBankLimitInfo(BankLimitInfo info){
		info.setCreateBy(getLogAccount().getRealName());
		int i=riskMgrService.updateBankLimitInfo(info);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	@RequestMapping(value="/deleteBankLimitInfo")
	public ModelAndView deleteBankLimitInfo(String[] ids){
		int i=riskMgrService.deleteBankLimitInfoByIds(ids);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/**
	 * ??????????????????
	 * @return
	 */
	@RequestMapping(value="/queryCountryList")
	public String queryCountryList(String countrys){
		String[] countryList=null;
		if(countrys!=null){
			countryList=countrys.split(",");
		}
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			getRequest().getSession().setAttribute("countrySet", null);
		}
		Set<String> countrySet=(Set<String>) getRequest().getSession().getAttribute("countrySet");
		if(countrySet==null){
			countrySet=new TreeSet<String>();
		}
		if(countryList!=null){
			for(String str:countryList){
				if(!"".equals(str)){
					countrySet.add(str);
				}
			}
		}
		getRequest().getSession().setAttribute("countrySet", countrySet);
		PageInfo<CountryInfo> page=riskMgrService.queryCountyList(getCriteria());
		getRequest().setAttribute("page", page);
		return "riskmgr/CoutryInfoList";
	}
	
	/**
	 * ??????????????????
	 * @return
	 */
	@RequestMapping(value="/getMaxmindTransList")
	public String getMaxmindTransList(){
		Criteria criteria=getCriteria();
        /*if(null==criteria.getCondition().get("transType")){
        	criteria.getCondition().put("transType", "sales");
        	Map<String, String> par=new HashMap<String, String>();
        	par.put("transType", "sales");
        	getRequest().setAttribute("param", par);
        }*/
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String transDateStart=sdf1.format(date);
			criteria.getCondition().put("transDateStart", transDateStart+" 00:00:00");
			criteria.getCondition().put("transDateEnd", transDateStart+" 23:59:59");
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<MaxmindTransInfo> page = riskMgrService.getMaxMindTransList(criteria);
			//List<TransInfo>  Total=transMgrService.getTransAmountList(criteria);
			getRequest().setAttribute("page", page);
		}
		return "riskmgr/maxmindTransList";
	}
	@RequestMapping("/queryMaxmindInfoByTradeNo")
	public String queryMaxmindInfoByTradeNo(String tradeNo){
		MaxmindTransInfo info=riskMgrService.queryMaxmindInfoByTradeNo(tradeNo);
		getRequest().setAttribute("transInfo", info);
		return "riskmgr/maxmindResultInfo";
	}
	//exportMaxmindTransList
	
	/**
	 *???????????? 
	 * @throws Exception 
	 */
	@RequestMapping(value="/exportMaxmindTransList")
	public void exportMaxmindTransList(HttpServletResponse resp) throws Exception{
		OutputStream os = null;
		os = resp.getOutputStream();
		resp.reset(); // ???????????????????????????
		resp.setHeader("Content-Disposition", "attachment; filename=" + "transList.xlsx");
		resp.setContentType("application/octet-stream; charset=utf-8");
		Criteria criteria=getCriteria();
		BIWorkbook bw=new BIWorkbook();
		BISheet bs = bw.addSheet();
		BIRow br_0 = bs.addRow();
		for(String str:new String[]{"?????????","?????????","?????????","????????????","????????????","????????????","????????????",
				  "????????????","????????????","??????????????????","??????????????????","?????????","???????????????","???????????????","?????????","???????????????","??????","????????????","??????????????????","????????????","????????????","????????????","????????????","????????????","????????????","????????????","????????????","???????????????","????????????????????????","??????????????????","??????","????????????","??????","??????","??????","IP","????????????","????????????","?????????/ ???","????????????","??????","????????????","????????????","????????????","?????????/???","????????????"
				  ,"??????","????????????","????????????","IP?????????????????????","ip????????????","ip??????","ip???/???","IP????????????"
				  ,"IP??????","IP metrocode","IP??????","??????","IP????????????","IP????????????","IP??????","IP??????","IP??????","ip_asnum"
				  ,"????????????","??????????????????","IP?????????????????????","IP??????ISP","IP????????????","IP?????????????????????","IP?????????????????????"
				  ,"IP?????????????????????","IP?????????????????????","?????????????????????","proxyScore","?????????????????????","???????????????????????????",
				  "?????????????????????","????????????","??????BIN??????","BIN??????","???????????????bin????????????","BIN????????????","????????????BIN??????"
				  ,"BIN????????????","??????????????????","???????????????????????????????????????","??????????????????","?????????????????????????????????",
				  "?????????????????????????????????","??????"
		}){
			br_0.addCell().setCellValue(str, null);
		}
			List<MaxmindTransInfo> list = riskMgrService.exportTransList(criteria);
			for (int row = 1; row <= list.size(); row++) {
				BIRow br_1 = bs.addRow();
				MaxmindTransInfo info = list.get(row-1);
				br_1.addCell().setCellValue(info.getMerNo(),null);
				br_1.addCell().setCellValue(info.getTradeNo(),null);
				br_1.addCell().setCellValue(info.getOrderNo(),null);
				br_1.addCell().setCellValue(info.getTransDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getTransDate()):"",null);
				br_1.addCell().setCellValue(info.getCurrencyName(),null);
				br_1.addCell().setCellValue(info.getMerTransAmount().doubleValue()+"",null);
				br_1.addCell().setCellValue(info.getMerBusCurrency()+"",null);
				br_1.addCell().setCellValue(info.getMerSettleAmount().doubleValue()+"",null);
				br_1.addCell().setCellValue(info.getMerSettleCurrency()+"",null);
				br_1.addCell().setCellValue(info.getBankTransAmount().doubleValue()+"",null);
				br_1.addCell().setCellValue(info.getBankCurrency()+"",null);
				br_1.addCell().setCellValue(info.getMerForAmount().doubleValue()+"",null);
				br_1.addCell().setCellValue(info.getSingleFee().doubleValue()+"",null);
				br_1.addCell().setCellValue(info.getMerSettleCurrency()+"",null);
				br_1.addCell().setCellValue(info.getBondAmount().doubleValue()+"",null);
				br_1.addCell().setCellValue(info.getMerSettleCurrency()+"",null);
				br_1.addCell().setCellValue(info.getCardType()+"",null);
				br_1.addCell().setCellValue("00".equals(info.getRespCode())?"????????????":"????????????"+"",null);
				br_1.addCell().setCellValue(info.getRespMsg()+"",null);
				br_1.addCell().setCellValue(info.getRiskScore()+"",null);
				br_1.addCell().setCellValue("1".equals(info.getDishonorStatus())?"?????????":"?????????"+"",null);
				br_1.addCell().setCellValue(info.getMerBusCurrency()+" "+ info.getDishonorAmount()+"",null);
				br_1.addCell().setCellValue("1".equals(info.getRefundStatus())?"?????????":"?????????"+"",null);
				br_1.addCell().setCellValue(info.getMerBusCurrency()+" "+ info.getRefundAmount(),null);
				br_1.addCell().setCellValue("1".equals(info.getFrozenStatus())?"?????????":"?????????",null);
				br_1.addCell().setCellValue(info.getMerBusCurrency()+" "+ info.getFrozenAmount(),null);
				//sheet.addCell( new Label(col++, row, "???????????????"));//??????????????????
				br_1.addCell().setCellValue(Tools.parseWebInfoToResourceType(info.getWebInfo()),null);
				br_1.addCell().setCellValue(info.getTerNo(),null);
				br_1.addCell().setCellValue(info.getAcquirer(),null);
				
				
				
				if(info.getCheckNo()!=null&&!"".equals(info.getCheckNo())){
					br_1.addCell().setCellValue( Funcs.decrypt(info.getCheckNo())+"***"+Funcs.decrypt(info.getLast()),null);
				}else{
					br_1.addCell().setCellValue( "",null);
				}
				br_1.addCell().setCellValue(info.getPayWebSite(),null);
				if(null!=info.getGoodsInfo()){
					br_1.addCell().setCellValue(new String(info.getGoodsInfo(),"utf-8"),null);
				}else{
					br_1.addCell().setCellValue( "",null);
				}
				br_1.addCell().setCellValue( info.getGrPerName(),null);
				br_1.addCell().setCellValue( info.getEmail(),null);
				br_1.addCell().setCellValue( info.getGrphoneNumber(),null);
				br_1.addCell().setCellValue( info.getIpAddress(),null);
				br_1.addCell().setCellValue( info.getIpCountry(),null);
				br_1.addCell().setCellValue( info.getGrCountry(),null);
				br_1.addCell().setCellValue( info.getGrState(),null);
				br_1.addCell().setCellValue( info.getGrAddress(),null);
				br_1.addCell().setCellValue( info.getGrZipCode(),null);
				br_1.addCell().setCellValue( info.getIogistics(),null);
				br_1.addCell().setCellValue( info.getTrackNo(),null);
				br_1.addCell().setCellValue( info.getCardCountry(),null);
				br_1.addCell().setCellValue( info.getCardState(),null);
				br_1.addCell().setCellValue( info.getCardAddress(),null);
				
				br_1.addCell().setCellValue( info.getRiskScore(),null);
				br_1.addCell().setCellValue( info.getCountryMatch(),null);
				br_1.addCell().setCellValue( info.getHighRiskCountry(),null);
				br_1.addCell().setCellValue( info.getDistance()+" km",null);
				br_1.addCell().setCellValue( info.getIp_accuracyRadius(),null);
				br_1.addCell().setCellValue( info.getIp_city(),null);
				br_1.addCell().setCellValue( info.getIp_region(),null);
				br_1.addCell().setCellValue( info.getIp_regionName(),null);
				br_1.addCell().setCellValue( info.getIp_postalCode(),null);
				br_1.addCell().setCellValue( info.getIp_metroCode(),null);
				br_1.addCell().setCellValue( info.getIp_areaCode(),null);
				br_1.addCell().setCellValue( info.getCountryCode(),null);
				br_1.addCell().setCellValue( info.getIp_countryName(),null);
				br_1.addCell().setCellValue( info.getIp_continentCode(),null);
				br_1.addCell().setCellValue( info.getIp_latitude(),null);
				br_1.addCell().setCellValue( info.getIp_longitude(),null);
				br_1.addCell().setCellValue( info.getIp_timeZone(),null);
				br_1.addCell().setCellValue( info.getIp_asnum(),null);
				br_1.addCell().setCellValue( info.getIp_userType(),null);
				br_1.addCell().setCellValue( info.getIp_netSpeedCell(),null);
				br_1.addCell().setCellValue( info.getIp_domain(),null);
				br_1.addCell().setCellValue( info.getIp_isp(),null);
				br_1.addCell().setCellValue( info.getIp_org(),null);
				br_1.addCell().setCellValue( info.getIp_cityConf(),null);
				br_1.addCell().setCellValue( info.getIp_regionConf(),null);
				br_1.addCell().setCellValue( info.getIp_postalConf(),null);
				br_1.addCell().setCellValue( info.getIp_countryConf(),null);
				br_1.addCell().setCellValue( info.getAnonymousProxy(),null);
				br_1.addCell().setCellValue( info.getProxyScore(),null);
				br_1.addCell().setCellValue( info.getIsTransProxy(),null);
				br_1.addCell().setCellValue( info.getIp_corporateProxy(),null);
				br_1.addCell().setCellValue( info.getFreeMail(),null);
				br_1.addCell().setCellValue( info.getCarderEmail(),null);
				br_1.addCell().setCellValue( info.getBinMatch(),null);
				br_1.addCell().setCellValue( info.getBinCountry(),null);
				br_1.addCell().setCellValue( info.getBinNameMatch(),null);
				br_1.addCell().setCellValue( info.getBinName(),null);
				br_1.addCell().setCellValue( info.getBinPhoneMatch(),null);
				br_1.addCell().setCellValue( info.getBinPhone(),null);
				br_1.addCell().setCellValue( info.getPrepaid(),null);
				br_1.addCell().setCellValue( info.getCustPhoneInBillingLoc(),null);
				br_1.addCell().setCellValue( info.getShipForward(),null);
				br_1.addCell().setCellValue( info.getCityPostalMatch(),null);
				br_1.addCell().setCellValue( info.getShipCityPostalMatch(),null);
				br_1.addCell().setCellValue( info.getErr(),null);
			}
			
		bw.workbook.write(os);	
		os.flush();
		os.close();
	}
	/**
	 * maxmind ????????????
	 * @return
	 */
	@RequestMapping(value="/getMaxmindWarnList")
	public String getMaxmindWarnList(){
		Criteria criteria=getCriteria();
        /*if(null==criteria.getCondition().get("transType")){
        	criteria.getCondition().put("transType", "sales");
        	Map<String, String> par=new HashMap<String, String>();
        	par.put("transType", "sales");
        	getRequest().setAttribute("param", par);
        }*/
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String transDateStart=sdf1.format(date);
			criteria.getCondition().put("transDateStart", transDateStart+" 00:00:00");
			criteria.getCondition().put("transDateEnd", transDateStart+" 23:59:59");
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<MaxmindWarnInfo> page = riskMgrService.getMaxmindWarnList(criteria);
			getRequest().setAttribute("page", page);
		}
		return "riskmgr/maxmindWarnList";
	}
//	
	/**
	 *???????????? 
	 * @throws Exception 
	 */
	@RequestMapping(value="/exportMaxmindWarnList")
	public void exportMaxmindWarnList(HttpServletResponse resp) throws Exception{
		OutputStream os = null;
		os = resp.getOutputStream();
		resp.reset(); // ???????????????????????????
		resp.setHeader("Content-Disposition", "attachment; filename=" + "transList.xlsx");
		resp.setContentType("application/octet-stream; charset=utf-8");
		Criteria criteria=getCriteria();
		BIWorkbook bw=new BIWorkbook();
		BISheet bs = bw.addSheet();
		BIRow br_0 = bs.addRow();
		for(String str:new String[]{"maxmindID","?????????","??????","?????????","????????????"
		}){
			br_0.addCell().setCellValue(str, null);
		}
			List<MaxmindWarnInfo> list = riskMgrService.exportMaxmindWarnList(criteria);
			for (int row = 1; row <= list.size(); row++) {
				BIRow br_1 = bs.addRow();
				MaxmindWarnInfo info = list.get(row-1);
				br_1.addCell().setCellValue(info.getMaxmindID(),null);
				br_1.addCell().setCellValue(info.getTxnID(),null);
				br_1.addCell().setCellValue(info.getReason(),null);
				br_1.addCell().setCellValue(info.getReason_code(),null);
				br_1.addCell().setCellValue(info.getCreateDate()+"",null);
			}
			
		bw.workbook.write(os);	
		os.flush();
		os.close();
	}
	
	/**
	 * ??????????????????????????????
	 */
	@RequestMapping(value="/queryRiskCountryInfoLogList")
	public String queryRiskCountryInfoLogList(){
		HttpServletRequest request = getRequest();
		if("get".equalsIgnoreCase(request.getMethod())){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Criteria criteria = getCriteria();
			criteria.getCondition().put("startDate", sdf.format(new Date()));
			criteria.getCondition().put("endDate", sdf.format(new Date()));
			request.setAttribute("form", criteria.getCondition());
		}else{
			request.setAttribute("form", getCriteria().getCondition());
			PageInfo<RiskCountryInfoLog> pageInfo = riskMgrService.queryRiskCountryLogInfoList(getCriteria());
			request.setAttribute("page", pageInfo);
		}
		return "riskmgr/riskCountryLogInfoList";
	}
	
	/**
	 * ThreatMetrix????????????
	 */
	@RequestMapping(value="/queryThreatMetrixInfoList")
	public String queryThreatMetrixInfoList(){
		Criteria criteria = getCriteria();
		if("post".equalsIgnoreCase(getRequest().getMethod())){
			PageInfo<ThreatMetrixResultInfo> page = riskMgrService.queryThreatMetrixInfoList(criteria);
			getRequest().setAttribute("page", page);
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String time = sdf.format(new Date());
			criteria.getCondition().put("transDateStart", time+" 00:00:00");
			criteria.getCondition().put("transDateEnd", time+" 23:59:59");
		}
		getRequest().setAttribute("form", criteria.getCondition());
		return "riskmgr/listThreatMetrixInfo";
	}
	
	/**
	 * ??????ThreatMetrix??????????????????
	 */
	@RequestMapping(value="/queryThreatMetrixDetail")
	public String queryThreatMetrixDetail(String id){
		ThreatMetrixResultInfo info = riskMgrService.queryThreatMetrixInfoById(id);
		getRequest().setAttribute("info", info);
		return "riskmgr/threatMetrixDetail";
	}
	
	/**
	 * ??????ThreatMetrix???????????????
	 */
	@RequestMapping(value="/queryRiskInfoList")
	public String queryRiskInfoList(String id){
		ThreatMetrixResultInfo info = riskMgrService.queryThreatMetrixInfoById(id);
		if(null!=info){
			if(null!=info.getReason_code() && !("".equals(info.getReason_code()))){
				JSONArray jsonArray = new JSONArray().parseArray(info.getReason_code());
				if(jsonArray!=null && jsonArray.size()>0){
					List<String> list = new ArrayList<String>();
					for(Object obj : jsonArray){
						if(obj!=null){
							list.add(BaseDataListener.getThreadMetrixCNInfo((String) obj));
						}
					}
					getRequest().setAttribute("list", list);
				}
			}
		}
		return "riskmgr/listThreatMetrixRiskInfo";
	}
	
	/**
	 * ??????ThreatMetrix????????????
	 * @throws IOException 
	 */
	@RequestMapping(value="/exportThreatMetrixInfoList")
	public void exportThreatMetrixInfoList(HttpServletRequest request, HttpServletResponse response) throws IOException{
		OutputStream os = null;
		os = response.getOutputStream();
		response.reset(); // ???????????????????????????
		response.setHeader("Content-Disposition", "attachment; filename=" + "threatMetrixReturnInfo.xlsx");
		response.setContentType("application/octet-stream; charset=utf-8");
		Criteria criteria=getCriteria();
		BIWorkbook bw=new BIWorkbook();
		BISheet bs = bw.addSheet();
		BIRow br_0 = bs.addRow();
		for (String str : new String[] { "?????????", "?????????", "?????????", "??????????????????", "???????????????????????????????????????",
				"????????????????????????????????????", "????????????????????????????????????", "????????????????????????", "????????????????????????",
				"??????????????????????????????", "????????????", "?????????????????????????????????", "??????????????????????????????",
				"??????????????????????????????", "??????????????????", "??????????????????", "????????????????????????", "????????????",
				"?????????????????????????????????", "??????????????????????????????", "??????????????????????????????", "??????????????????", "??????????????????",
				"????????????????????????", "????????????", "????????????", "API ??????", "?????????", "???????????????",
				"????????? String", "????????? String Hash", "???????????????", "CC BIN",
				"???BIN ??????", "???BIN ??????", "???BIN ??????", "????????????", "???BIN ??????",
				"CSS Image Loaded", "Flash ??????", "??????ID?????????????????????", "??????ID",
				"??????ID?????????", "??????ID??????????????????", "??????ID??????????????????",
				"??????ID????????????", "??????ID??????", "??????ID??????", "??????ID????????????",
				"????????????", "??????????????????", "??????????????????", "?????????????????????????????????",
				"??????????????????", "??????????????????", "??????????????????", "??????????????????",
				"Cookies ??????", "Flash ??????", "Images ??????", "Javascript ??????", "????????????",
				"Flash ??????", "Flash OS", "Flash ??????", "Smart ID ?????????????????????",
				"Smart ID", "Smart ID ?????????", "Smart ID ??????????????????",
				"Smart ID ??????????????????", "Smart ID ????????????", "Smart ID ??????",
				"Smart ID ??????", "Smart ID ????????????", "Headers Name Hash",
				"Headers Order Hash", "????????????", "??????????????????", "?????????????????? ",
				"HTTP Referer", "HTTP Referer ??????", "HTTP Referer URL",
				"Image Loaded", "??????IP", "??????IP ??????", "??????IP ?????????????????????",
				"??????IP ??????", "??????IP ?????????????????????", "??????IP ??????????????????",
				"??????IP ??????????????????", "??????IP ??????", "??????IP ??????", "??????IP ??????",
				"??????IP ??????", "??????IP ??????", "??????IP ??????",
				"????????? (JavaScript??????)", "????????? String (JavaScript??????)",
				"????????? String Hash (JavaScript??????)", "?????? Hash (JavaScript??????)",
				"???????????? (JavaScript??????)", "OS (JavaScript??????)", "MIME Type Hash",
				"MIME Type Number", "Org ID", "OS", "?????? Hash", "?????? Count",
				"OS ??????", "??????????????????", "Plug-in Flash", "Plug-in Hash",
				"Plug-in Number", "Plug-in Windows Media Player", "??????", "??????",
				"Profiled Domain", "Profiled URL", "Profile ??????", "??????",
				"Related Request ID", "??????", "????????????", "TMX ????????????", "??????????????????",
				"???????????????", "???????????????", "API ????????????", "Session ID", "Session ID ????????????",
				"??????????????????", "??????", "??????DST??????", "TMX Variables", "True IP",
				"??????IP??????", "True IP ?????????????????????", "??????IP??????",
				"??????IP?????????????????????", "??????IP??????????????????", "??????IP??????????????????",
				"??????IP??????", "??????IP??????", "??????IP??????", "??????IP??????",
				"??????IP??????", "??????IP??????", "??????IP????????????", "UA ?????????", "UA OS",
				"UA ??????" }) {
			br_0.addCell().setCellValue(str, null);
		}
		List<ThreatMetrixResultInfo> list = riskMgrService.queryExportThreatMetrixInfoList(getCriteria());
		for (int row = 1; row <= list.size(); row++) {
			BIRow br_1 = bs.addRow();
			ThreatMetrixResultInfo info = list.get(row-1);
			br_1.addCell().setCellValue(info.getTradeNo(),null);
			br_1.addCell().setCellValue(info.getMerNo(),null);
			br_1.addCell().setCellValue(info.getTerNo(),null);
			br_1.addCell().setCellValue(info.getAccount_email(),null);
			br_1.addCell().setCellValue(info.getAccount_email_first_seen(),null);
			br_1.addCell().setCellValue(info.getAccount_email_last_event(),null);
			br_1.addCell().setCellValue(info.getAccount_email_last_update(),null);
			br_1.addCell().setCellValue(info.getAccount_email_result(),null);
			br_1.addCell().setCellValue(info.getAccount_email_score(),null);
			br_1.addCell().setCellValue(info.getAccount_email_worst_score(),null);
			br_1.addCell().setCellValue(info.getAccount_name(),null);
			br_1.addCell().setCellValue(info.getAccount_name_first_seen(),null);
			br_1.addCell().setCellValue(info.getAccount_name_last_event(),null);
			br_1.addCell().setCellValue(info.getAccount_name_last_update(),null);
			br_1.addCell().setCellValue(info.getAccount_name_result(),null);
			br_1.addCell().setCellValue(info.getAccount_name_score(),null);
			br_1.addCell().setCellValue(info.getAccount_name_worst_score(),null);
			br_1.addCell().setCellValue(info.getAccount_telephone(),null);
			br_1.addCell().setCellValue(info.getAccount_telephone_first_seen(),null);
			br_1.addCell().setCellValue(info.getAccount_telephone_last_event(),null);
			br_1.addCell().setCellValue(info.getAccount_telephone_last_update(),null);
			br_1.addCell().setCellValue(info.getAccount_telephone_result(),null);
			br_1.addCell().setCellValue(info.getAccount_telephone_score(),null);
			br_1.addCell().setCellValue(info.getAccount_telephone_worst_score(),null);
			br_1.addCell().setCellValue(info.getAgent_type(),null);
			br_1.addCell().setCellValue(info.getApi_call_datetime(),null);
			br_1.addCell().setCellValue(info.getApi_version(),null);
			br_1.addCell().setCellValue(info.getBrowser(),null);
			br_1.addCell().setCellValue(info.getBrowser_language(),null);
			br_1.addCell().setCellValue(info.getBrowser_string(),null);
			br_1.addCell().setCellValue(info.getBrowser_string_hash(),null);
			br_1.addCell().setCellValue(info.getBrowser_version(),null);
			br_1.addCell().setCellValue(info.getCc_bin_number(),null);
			br_1.addCell().setCellValue(info.getCc_bin_number_brand(),null);
			br_1.addCell().setCellValue(info.getCc_bin_number_category(),null);
			br_1.addCell().setCellValue(info.getCc_bin_number_geo(),null);
			br_1.addCell().setCellValue(info.getCc_bin_number_org(),null);
			br_1.addCell().setCellValue(info.getCc_bin_number_type(),null);
			br_1.addCell().setCellValue(info.getCss_image_loaded(),null);
			br_1.addCell().setCellValue(info.getDetected_fl(),null);
			br_1.addCell().setCellValue(info.getDevice_first_seen(),null);
			br_1.addCell().setCellValue(info.getDevice_id(),null);
			br_1.addCell().setCellValue(info.getDevice_id_confidence(),null);
			br_1.addCell().setCellValue(info.getDevice_last_event(),null);
			br_1.addCell().setCellValue(info.getDevice_last_update(),null);
			br_1.addCell().setCellValue(info.getDevice_match_result(),null);
			br_1.addCell().setCellValue(info.getDevice_result(),null);
			br_1.addCell().setCellValue(info.getDevice_score(),null);
			br_1.addCell().setCellValue(info.getDevice_worst_score(),null);
			br_1.addCell().setCellValue(info.getDns_ip(),null);
			br_1.addCell().setCellValue(info.getDns_ip_city(),null);
			br_1.addCell().setCellValue(info.getDns_ip_geo(),null);
			br_1.addCell().setCellValue(info.getDns_ip_isp(),null);
			br_1.addCell().setCellValue(info.getDns_ip_latitude(),null);
			br_1.addCell().setCellValue(info.getDns_ip_longitude(),null);
			br_1.addCell().setCellValue(info.getDns_ip_organization(),null);
			br_1.addCell().setCellValue(info.getDns_ip_region(),null);
			br_1.addCell().setCellValue(info.getEnabled_ck(),null);
			br_1.addCell().setCellValue(info.getEnabled_fl(),null);
			br_1.addCell().setCellValue(info.getEnabled_im(),null);
			br_1.addCell().setCellValue(info.getEnabled_js(),null);
			br_1.addCell().setCellValue(info.getEvent_type(),null);
			br_1.addCell().setCellValue(info.getFlash_lang(),null);
			br_1.addCell().setCellValue(info.getFlash_os(),null);
			br_1.addCell().setCellValue(info.getFlash_version(),null);
			br_1.addCell().setCellValue(info.getFuzzy_device_first_seen(),null);
			br_1.addCell().setCellValue(info.getFuzzy_device_id(),null);
			br_1.addCell().setCellValue(info.getFuzzy_device_id_confidence(),null);
			br_1.addCell().setCellValue(info.getFuzzy_device_last_event(),null);
			br_1.addCell().setCellValue(info.getFuzzy_device_last_update(),null);
			br_1.addCell().setCellValue(info.getFuzzy_device_match_result(),null);
			br_1.addCell().setCellValue(info.getFuzzy_device_result(),null);
			br_1.addCell().setCellValue(info.getFuzzy_device_score(),null);
			br_1.addCell().setCellValue(info.getFuzzy_device_worst_score(),null);
			br_1.addCell().setCellValue(info.getHeaders_name_value_hash(),null);
			br_1.addCell().setCellValue(info.getHeaders_order_string_hash(),null);
			br_1.addCell().setCellValue(info.getHoneypot_fingerprint(),null);
			br_1.addCell().setCellValue(info.getHoneypot_fingerprint_check(),null);
			br_1.addCell().setCellValue(info.getHoneypot_fingerprint_match(),null);
			br_1.addCell().setCellValue(info.getHttp_referer(),null);
			br_1.addCell().setCellValue(info.getHttp_referer_domain(),null);
			br_1.addCell().setCellValue(info.getHttp_referer_url(),null);
			br_1.addCell().setCellValue(info.getImage_loaded(),null);
			br_1.addCell().setCellValue(info.getInput_ip_address(),null);
			br_1.addCell().setCellValue(info.getInput_ip_city(),null);
			br_1.addCell().setCellValue(info.getInput_ip_first_seen(),null);
			br_1.addCell().setCellValue(info.getInput_ip_geo(),null);
			br_1.addCell().setCellValue(info.getInput_ip_isp(),null);
			br_1.addCell().setCellValue(info.getInput_ip_last_event(),null);
			br_1.addCell().setCellValue(info.getInput_ip_last_update(),null);
			br_1.addCell().setCellValue(info.getInput_ip_latitude(),null);
			br_1.addCell().setCellValue(info.getInput_ip_longitude(),null);
			br_1.addCell().setCellValue(info.getInput_ip_organization(),null);
			br_1.addCell().setCellValue(info.getInput_ip_region(),null);
			br_1.addCell().setCellValue(info.getInput_ip_result(),null);
			br_1.addCell().setCellValue(info.getInput_ip_score(),null);
			br_1.addCell().setCellValue(info.getJs_browser(),null);
			br_1.addCell().setCellValue(info.getJs_browser_string(),null);
			br_1.addCell().setCellValue(info.getJs_browser_string_hash(),null);
			br_1.addCell().setCellValue(info.getJs_fonts_hash(),null);
			br_1.addCell().setCellValue(info.getJs_fonts_number(),null);
			br_1.addCell().setCellValue(info.getJs_os(),null);
			br_1.addCell().setCellValue(info.getMime_type_hash(),null);
			br_1.addCell().setCellValue(info.getMime_type_number(),null);
			br_1.addCell().setCellValue(info.getOrg_id(),null);
			br_1.addCell().setCellValue(info.getOs(),null);
			br_1.addCell().setCellValue(info.getOs_fonts_hash(),null);
			br_1.addCell().setCellValue(info.getOs_fonts_number(),null);
			br_1.addCell().setCellValue(info.getOs_version(),null);
			br_1.addCell().setCellValue(info.getPage_time_on(),null);
			br_1.addCell().setCellValue(info.getPlugin_flash(),null);
			br_1.addCell().setCellValue(info.getPlugin_hash(),null);
			br_1.addCell().setCellValue(info.getPlugin_number(),null);
			br_1.addCell().setCellValue(info.getPlugin_windows_media_player(),null);
			br_1.addCell().setCellValue(info.getPolicy(),null);
			br_1.addCell().setCellValue(info.getPolicy_score(),null);
			br_1.addCell().setCellValue(info.getProfiled_domain(),null);
			br_1.addCell().setCellValue(info.getProfiled_url(),null);
			br_1.addCell().setCellValue(info.getProfiling_datetime(),null);
			StringBuffer sb = new StringBuffer();
			try {
				if(info.getReason_code()!=null && !("".equals(info.getReason_code()))){
					JSONArray jsonArray = JSONArray.parseArray(info.getReason_code());
					if(jsonArray!=null && jsonArray.size()>0){
						for(Object obj : jsonArray){
							sb.append(BaseDataListener.getThreadMetrixCNInfo((String) obj)).append(";");
						}
					}
				}
			} catch (Exception e) {
				
			}
			br_1.addCell().setCellValue(sb.toString(),null);
			br_1.addCell().setCellValue(info.getRequest_id(),null);
			br_1.addCell().setCellValue(info.getRequest_result(),null);
			br_1.addCell().setCellValue(info.getReview_status(),null);
			br_1.addCell().setCellValue(info.getRisk_rating(),null);
			br_1.addCell().setCellValue(info.getScreen_color_depth(),null);
			br_1.addCell().setCellValue(info.getScreen_dpi(),null);
			br_1.addCell().setCellValue(info.getScreen_res(),null);
			br_1.addCell().setCellValue(info.getService_type(),null);
			br_1.addCell().setCellValue(info.getSession_id(),null);
			br_1.addCell().setCellValue(info.getSession_id_query_count(),null);
			br_1.addCell().setCellValue(info.getSummary_risk_score(),null);
			br_1.addCell().setCellValue(info.getTime_zone(),null);
			br_1.addCell().setCellValue(info.getTime_zone_dst_offset(),null);
			br_1.addCell().setCellValue(info.getTmx_variables(),null);
			br_1.addCell().setCellValue(info.getTrue_ip(),null);
			br_1.addCell().setCellValue(info.getTrue_ip_city(),null);
			br_1.addCell().setCellValue(info.getTrue_ip_first_seen(),null);
			br_1.addCell().setCellValue(info.getTrue_ip_geo(),null);
			br_1.addCell().setCellValue(info.getTrue_ip_isp(),null);
			br_1.addCell().setCellValue(info.getTrue_ip_last_event(),null);
			br_1.addCell().setCellValue(info.getTrue_ip_last_update(),null);
			br_1.addCell().setCellValue(info.getTrue_ip_latitude(),null);
			br_1.addCell().setCellValue(info.getTrue_ip_longitude(),null);
			br_1.addCell().setCellValue(info.getTrue_ip_organization(),null);
			br_1.addCell().setCellValue(info.getTrue_ip_region(),null);
			br_1.addCell().setCellValue(info.getTrue_ip_result(),null);
			br_1.addCell().setCellValue(info.getTrue_ip_score(),null);
			br_1.addCell().setCellValue(info.getTrue_ip_worst_score(),null);
			br_1.addCell().setCellValue(info.getUa_browser(),null);
			br_1.addCell().setCellValue(info.getUa_os(),null);
			br_1.addCell().setCellValue(info.getUa_platform(),null);
		}
		
		bw.workbook.write(os);	
		os.flush();
		os.close();
	}
	
}
