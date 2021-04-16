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
		WritableSheet sheet = book.createSheet("风险订单明细", 0);
		//String[] headerName = { "交易流水号", "参考号","商户号","终端号","交易类型","交易金额","商户手续费","返回码","交易时间","交易通道","交易银行","结算日期"};
		
		  String[] headerName={"商户号","终端号","流水号","订单号","网站","风控阻挡时间","规则ID","风控阻挡原因"
				  ,"处理方式","交易金额","前六后四卡号","姓名","邮箱","电话","IP","支付国家",
				  "收货国家","收货省/ 州","收货地址","邮编","账单国家","账单省/州","账单地址","欺诈分数"};
		// 写入标题
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
			sheet.addCell( new Label(col++, row, "拒绝交易"));
			sheet.addCell( new Label(col++,row,info.getCurrency()+" "+info.getAmount()));//交易币种
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
		WritableSheet sheet = book.createSheet("风险订单明细", 0);
		//String[] headerName = { "交易流水号", "参考号","商户号","终端号","交易类型","交易金额","商户手续费","返回码","交易时间","交易通道","交易银行","结算日期"};
		
		  String[] headerName={"商户号","终端号","流水号","订单号","网站","规则ID","风控阻挡原因","风控阻挡时间","有效处理截止时间"
				  ,"状态","交易金额","前六后四卡号","姓名","邮箱","电话","IP","支付国家","收货国家","收货省/ 州",
				  "收货地址","邮编","账单国家","账单省/州","账单地址","欺诈分数","支付返回信息"};
		// 写入标题
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
			sheet.addCell( new Label(col++, row, "00".equals(info.getRespCode())?"支付成功":"支付失败"));
			sheet.addCell( new Label(col++,row,info.getCurrency()+" "+info.getAmount()));//交易币种
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
	 * 查询商户国家限制
	 * */
	@RequestMapping(value="/listMerchantCountryLimit")
	public String listMerchantCountryLimit(){
		PageInfo<CountryLimit> page=riskMgrService.queryMerchantCountryLimit(getCriteria());
		getRequest().setAttribute("page", page);
		return "riskmgr/listMerchantCountryLimit";
	}
	
	/**
	 * 查询商户重复支付限制
	 * */
	@RequestMapping(value="/listMerchantPayCountLimit")
	public String listMerchantPayCountLimit(){
		PageInfo<MerchantPayCountLimit> page=riskMgrService.queryMerchantPayCountLimit(getCriteria());
		getRequest().setAttribute("page", page);
		return "riskmgr/listMerchantPayCountLimit";
	}
	/**
	 * 跳转到商户重复支付审核页面
	 * @throws ServiceException 
	 * */
	@RequestMapping(value="/goUpdateMerchantPayCountLimit")
	public String goUpdateMerchantPayCountLimit(String id) throws ServiceException{
		MerchantPayCountLimit info=riskMgrService.queryMerchantPayCountLimitById(id);
		if(!"0".equals(info.getStatus())){
			throw new ServiceException("请选择待审核的记录");
		}
		getRequest().setAttribute("info", info);
		return "riskmgr/updateMerchantCountryLimit";
	}
	/**
	 * 商户重复支付审核
	 * */
	@RequestMapping(value="/updateMerchantPayCountLimit")
	public ModelAndView updateMerchantPayCountLimit(MerchantPayCountLimit info){
		info.setLastModifyBy(getLogAccount().getRealName());
		int i=riskMgrService.updateMerchantPayCountLimitById(info);
		if(i>0){
			return ajaxDoneSuccess("审核成功");
		}else{
			return ajaxDoneError("审核失败");
		}
	}
	/**
	 * 跳转到添加或者修改
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
	 * 商户重复支付添加或者修改
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
			return ajaxDoneSuccess("保存成功");
		}else{
			return ajaxDoneError("保存失败");
		}
	}
	/**
	 * 商户重复支付添加或者修改
	 * */
	@RequestMapping(value="/deleteMerchantPayCountLimit")
	public ModelAndView deleteMerchantPayCountLimit(String id){
		int i=riskMgrService.deleteMerchantPayCountLimit(id);
		if(i>0){
			return ajaxDoneSuccess("删除成功");
		}else{
			return ajaxDoneError("删除失败");
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
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
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
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	@RequestMapping(value="/deleteBrandLimitInfo")
	public ModelAndView deleteBrandLimitInfo(String[] ids){
		int i=riskMgrService.deleteBrandLimitInfoByIds(ids);
		if(i>0){
			return ajaxDoneSuccess("删除成功");
		}else{
			return ajaxDoneError("删除失败");
		}
	}
	
	/**
	 * 银行限制
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
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
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
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	@RequestMapping(value="/deleteBankLimitInfo")
	public ModelAndView deleteBankLimitInfo(String[] ids){
		int i=riskMgrService.deleteBankLimitInfoByIds(ids);
		if(i>0){
			return ajaxDoneSuccess("删除成功");
		}else{
			return ajaxDoneError("删除失败");
		}
	}
	
	/**
	 * 查询国家列表
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
	 * 风险订单查询
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
	 *导出交易 
	 * @throws Exception 
	 */
	@RequestMapping(value="/exportMaxmindTransList")
	public void exportMaxmindTransList(HttpServletResponse resp) throws Exception{
		OutputStream os = null;
		os = resp.getOutputStream();
		resp.reset(); // 来清除首部的空白行
		resp.setHeader("Content-Disposition", "attachment; filename=" + "transList.xlsx");
		resp.setContentType("application/octet-stream; charset=utf-8");
		Criteria criteria=getCriteria();
		BIWorkbook bw=new BIWorkbook();
		BISheet bs = bw.addSheet();
		BIRow br_0 = bs.addRow();
		for(String str:new String[]{"商户号","流水号","订单号","交易时间","通道名称","交易金额","交易币种",
				  "结算金额","结算币种","银行交易金额","银行交易币种","手续费","单笔手续费","手续费币种","保证金","保证金币种","卡种","交易状态","交易返回信息","欺诈分数","拒付状态","拒付金额","退款状态","退款金额","冻结状态","冻结金额","订单来源","所属终端号","通道英文账单名称","前六后四卡号","网站","货物信息","姓名","邮箱","电话","IP","支付国家","收货国家","收货省/ 州","收货地址","邮编","货运方式","货运单号","账单国家","账单省/州","账单地址"
				  ,"分数","国家匹配","高危国家","IP到账单地址距离","ip精准范围","ip城市","ip州/省","IP地区名字"
				  ,"IP邮编","IP metrocode","IP区号","国家","IP国家名字","IP所在大洲","IP纬度","IP经度","IP时区","ip_asnum"
				  ,"用户类型","网络连接类型","IP所在的二级域名","IP所在ISP","IP所在单位","IP城市对的可能性","IP地域对的可能性"
				  ,"IP邮编对的可能性","IP国家对的可能性","是否为匿名代理","proxyScore","是否为透明代理","是否为知名公司代理",
				  "是否为免费邮件","高危邮箱","银行BIN匹配","BIN国家","用户提供的bin是否符合","BIN银行名字","电话号码BIN匹配"
				  ,"BIN电话号码","是否为预付卡","电话号码区号是否属于该邮编","高危收货地址","账单城市和邮编是否吻合",
				  "寄送城市是否和邮编吻合","错误"
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
				br_1.addCell().setCellValue("00".equals(info.getRespCode())?"支付成功":"支付失败"+"",null);
				br_1.addCell().setCellValue(info.getRespMsg()+"",null);
				br_1.addCell().setCellValue(info.getRiskScore()+"",null);
				br_1.addCell().setCellValue("1".equals(info.getDishonorStatus())?"已拒付":"未拒付"+"",null);
				br_1.addCell().setCellValue(info.getMerBusCurrency()+" "+ info.getDishonorAmount()+"",null);
				br_1.addCell().setCellValue("1".equals(info.getRefundStatus())?"已退款":"未退款"+"",null);
				br_1.addCell().setCellValue(info.getMerBusCurrency()+" "+ info.getRefundAmount(),null);
				br_1.addCell().setCellValue("1".equals(info.getFrozenStatus())?"已冻结":"未冻结",null);
				br_1.addCell().setCellValue(info.getMerBusCurrency()+" "+ info.getFrozenAmount(),null);
				//sheet.addCell( new Label(col++, row, "无伪冒状态"));//交易返回信息
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
	 * maxmind 警告查询
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
	 *导出交易 
	 * @throws Exception 
	 */
	@RequestMapping(value="/exportMaxmindWarnList")
	public void exportMaxmindWarnList(HttpServletResponse resp) throws Exception{
		OutputStream os = null;
		os = resp.getOutputStream();
		resp.reset(); // 来清除首部的空白行
		resp.setHeader("Content-Disposition", "attachment; filename=" + "transList.xlsx");
		resp.setContentType("application/octet-stream; charset=utf-8");
		Criteria criteria=getCriteria();
		BIWorkbook bw=new BIWorkbook();
		BISheet bs = bw.addSheet();
		BIRow br_0 = bs.addRow();
		for(String str:new String[]{"maxmindID","流水号","原因","原因码","创建时间"
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
	 * 查询黑名单规则表日志
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
	 * ThreatMetrix返回信息
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
	 * 查询ThreatMetrix返回信息详情
	 */
	@RequestMapping(value="/queryThreatMetrixDetail")
	public String queryThreatMetrixDetail(String id){
		ThreatMetrixResultInfo info = riskMgrService.queryThreatMetrixInfoById(id);
		getRequest().setAttribute("info", info);
		return "riskmgr/threatMetrixDetail";
	}
	
	/**
	 * 显示ThreatMetrix风险项详情
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
	 * 导出ThreatMetrix返回信息
	 * @throws IOException 
	 */
	@RequestMapping(value="/exportThreatMetrixInfoList")
	public void exportThreatMetrixInfoList(HttpServletRequest request, HttpServletResponse response) throws IOException{
		OutputStream os = null;
		os = response.getOutputStream();
		response.reset(); // 来清除首部的空白行
		response.setHeader("Content-Disposition", "attachment; filename=" + "threatMetrixReturnInfo.xlsx");
		response.setContentType("application/octet-stream; charset=utf-8");
		Criteria criteria=getCriteria();
		BIWorkbook bw=new BIWorkbook();
		BISheet bs = bw.addSheet();
		BIRow br_0 = bs.addRow();
		for (String str : new String[] { "订单号", "商户号", "终端号", "帐户邮箱地址", "帐户邮箱地址第一次看到时间",
				"帐户邮箱地址最后事件时间", "帐户邮箱地址上次更新时间", "帐户邮箱返回原因", "帐户邮箱地址评分",
				"帐户邮箱地址最差评分", "帐户名称", "帐户名称第一次看到时间", "帐户名称最后事件时间",
				"帐户名称上次更新时间", "帐户名称结果", "帐户名称评分", "帐户名称最差评分", "帐户电话",
				"帐户电话第一次看到时间", "帐户电话最后事件时间", "帐户电话上次更新时间", "帐户电话结果", "帐户电话评分",
				"帐户电话最差评分", "代理类型", "事件时间", "API 版本", "浏览器", "浏览器语言",
				"浏览器 String", "浏览器 String Hash", "浏览器版本", "CC BIN",
				"卡BIN 品牌", "卡BIN 类别", "卡BIN 国家", "发卡机构", "卡BIN 类型",
				"CSS Image Loaded", "Flash 偵測", "设备ID第一次看到时间", "设备ID",
				"设备ID信心值", "设备ID最后事件时间", "设备ID上次更新时间",
				"设备ID一致结果", "设备ID结果", "设备ID分數", "设备ID最差评分",
				"域名解析", "域名解析城市", "域名解析国家", "域名解析网络服务提供商",
				"域名解析纬度", "域名解析经度", "域名解析组织", "域名解析地区",
				"Cookies 启用", "Flash 启用", "Images 启用", "Javascript 启用", "事件类型",
				"Flash 语言", "Flash OS", "Flash 版本", "Smart ID 第一次看到时间",
				"Smart ID", "Smart ID 信心值", "Smart ID 最后事件時間",
				"Smart ID 上次更新时间", "Smart ID 一致结果", "Smart ID 结果",
				"Smart ID 评分", "Smart ID 最差评分", "Headers Name Hash",
				"Headers Order Hash", "诱捕指纹", "诱捕指纹检查", "诱捕指纹匹配 ",
				"HTTP Referer", "HTTP Referer 域名", "HTTP Referer URL",
				"Image Loaded", "输入IP", "输入IP 城市", "输入IP 第一次看到时间",
				"输入IP 国家", "输入IP 网络服务提供商", "输入IP 最后事件时间",
				"输入IP 上次更新时间", "输入IP 纬度", "输入IP 经度", "输入IP 组织",
				"输入IP 地区", "输入IP 结果", "输入IP 评分",
				"浏览器 (JavaScript侦测)", "浏览器 String (JavaScript侦测)",
				"浏览器 String Hash (JavaScript侦测)", "字体 Hash (JavaScript侦测)",
				"字体数量 (JavaScript侦测)", "OS (JavaScript侦测)", "MIME Type Hash",
				"MIME Type Number", "Org ID", "OS", "字体 Hash", "字体 Count",
				"OS 版本", "页面上的时间", "Plug-in Flash", "Plug-in Hash",
				"Plug-in Number", "Plug-in Windows Media Player", "策略", "评分",
				"Profiled Domain", "Profiled URL", "Profile 时间", "原因",
				"Related Request ID", "结果", "审查状态", "TMX 风险评级", "屏幕颜色深度",
				"屏幕分辨率", "屏幕分辨率", "API 服务类型", "Session ID", "Session ID 查询次数",
				"总结风险评分", "时区", "时区DST偏移", "TMX Variables", "True IP",
				"真实IP城市", "True IP 第一次看到时间", "真实IP国家",
				"真实IP网络服务提供商", "真实IP最后事件时间", "真实IP上次更新时间",
				"真实IP纬度", "真实IP经度", "真实IP组织", "真实IP地区",
				"真实IP结果", "真实IP评分", "真实IP最差评分", "UA 浏览器", "UA OS",
				"UA 平台" }) {
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
