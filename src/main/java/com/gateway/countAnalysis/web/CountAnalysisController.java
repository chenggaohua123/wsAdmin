package com.gateway.countAnalysis.web;

import java.awt.Color;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.BaseDataListener;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.utils.Funcs;
import com.gateway.common.utils.Tools;
import com.gateway.complaint.model.Complaint;
import com.gateway.countAnalysis.model.BrandCountInfo;
import com.gateway.countAnalysis.model.CompRateInfo;
import com.gateway.countAnalysis.model.CountAnalysis;
import com.gateway.countAnalysis.model.CountryInfo;
import com.gateway.countAnalysis.model.CurrencyDisCount;
import com.gateway.countAnalysis.model.DisDesc;
import com.gateway.countAnalysis.model.EuropeChannelInfo;
import com.gateway.countAnalysis.model.EuropeTransInfo;
import com.gateway.countAnalysis.model.ExportEuropeInfo;
import com.gateway.countAnalysis.model.ExportFaildTransAnalysisInfo;
import com.gateway.countAnalysis.model.ExportTransCountInfo;
import com.gateway.countAnalysis.model.FaildTransAnalysisInfo;
import com.gateway.countAnalysis.model.InitEuropeInfo;
import com.gateway.countAnalysis.model.MerchantTransCountRateInfo;
import com.gateway.countAnalysis.model.RiskPercent;
import com.gateway.countAnalysis.model.TransCountInfo;
import com.gateway.countAnalysis.model.TransHourCount;
import com.gateway.countAnalysis.model.TransOrDisPercent;
import com.gateway.countAnalysis.model.TransRateCyleForSearch;
import com.gateway.countAnalysis.model.TransReRunCount;
import com.gateway.countAnalysis.model.TransRecord;
import com.gateway.countAnalysis.service.CountAnalysisService;
import com.gateway.transmgr.model.GoodsInfo;
import com.gateway.transmgr.model.TransDetailInfo;
import com.gateway.transmgr.model.TransInfo;
import com.gateway.transmgr.model.TransRecordInfo;
import com.gateway.transmgr.service.TransMgrService;
import com.spire.xls.CellRange;
import com.spire.xls.Chart;
import com.spire.xls.ExcelChartType;
import com.spire.xls.ExcelVersion;
import com.spire.xls.HorizontalAlignType;
import com.spire.xls.VerticalAlignType;
import com.spire.xls.Worksheet;
import com.spire.xls.charts.ChartSerie;
import com.spire.xls.core.IXLSRange;

@Controller
@RequestMapping("/countAnalysis")
public class CountAnalysisController extends BaseController {
	@Resource
	private CountAnalysisService countAnalysisService;
	@Autowired
	private TransMgrService transMgrService;

	/** 查询交易统计 */
	@RequestMapping(value = "/queryCountAnalysisInfo")
	public String queryCountAnalysisInfo() {
		Criteria criteria = getCriteria();
		/*
		 * if(null==criteria.getCondition().get("transType")){
		 * criteria.getCondition().put("transType", "sales"); Map<String,
		 * String> par=new HashMap<String, String>(); par.put("transType",
		 * "sales"); getRequest().setAttribute("param", par); }
		 */
		if ("get".equalsIgnoreCase(getRequest().getMethod())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String transDateStart = sdf.format(date);
			String transDateEnd = sdf.format(date);
			criteria.getCondition().put("startDate", transDateStart);
			criteria.getCondition().put("endDate", transDateEnd);
			getRequest().setAttribute("form", criteria.getCondition());
		} else {
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<CountAnalysis> page = countAnalysisService
					.queryCountAnalysisInfo(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "countAnalysis/countAnalysisInfoList";
	}

	@RequestMapping(value = "/exportCountAnalysisInfo")
	public void exportCountAnalysisInfo(HttpServletResponse resp)
			throws Exception {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "failureTransList.xls");
		List<CountAnalysis> list = countAnalysisService
				.queryCountAnalysisInfoAll(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = null;
		sheet = book.createSheet("交易统计分析", 0);
		String[] headerName = { "商户号", "终端号", "总笔数", "交易完成笔数", "交易金额",
				"商户结算金额", "成功笔数", "成功金额", "商户结算成功金额", "失败笔数", "失败重复笔数",
				"风险单笔数", "拒付笔数", "退单笔数", "投诉笔数", "支付转换率", "成功率", "拒付率", "退单率",
				"投诉率" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			CountAnalysis info = list.get(row - 1);
			sheet.addCell(new Label(col++, row, info.getMerNo()));// 交易流水号
			sheet.addCell(new Label(col++, row, info.getTerNo()));// 交易流水号
			sheet.addCell(new Label(col++, row, info.getTotalCount()));// 订单号
			sheet.addCell(new Label(col++, row, info.getTransCount()));// 订单号
			sheet.addCell(new Label(col++, row, info.getTransCurrency() + " "
					+ info.getTransAmount()));// 订单号
			sheet.addCell(new Label(col++, row, info.getTransCurrency() + " "
					+ info.getMerSettleAmount()));// 订单号
			sheet.addCell(new Label(col++, row, info.getTransSuccessCount()));// 成功笔数
			sheet.addCell(new Label(col++, row, info.getTransCurrency() + " "
					+ info.getTransSuccessAmount()));// 订单号
			sheet.addCell(new Label(col++, row, info.getTransCurrency() + " "
					+ info.getMerSettleSuccessAmount()));// 订单号
			sheet.addCell(new Label(col++, row, info.getTransFailureCount()));// 失败笔数
			sheet.addCell(new Label(col++, row, info.getDuplicateCount()));// 失败笔数
			sheet.addCell(new Label(col++, row, info.getTransRiskCount() + ""));
			sheet.addCell(new Label(col++, row, info.getDishonorCount()));
			sheet.addCell(new Label(col++, row, info.getRefundCount()));
			sheet.addCell(new Label(col++, row, info.getComplaintCount()));
			if ("0".equals(info.getTotalCount())) {
				sheet.addCell(new Label(col++, row, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, row, nf.format(Integer
						.parseInt(info.getTransCount())
						* 100.0
						/ Integer.parseInt(info.getTotalCount())) + "%"));// 订单号
			}
			sheet.addCell(new Label(col++, row, info.getSuccessRate()));
			sheet.addCell(new Label(col++, row, info.getDishonorRate()));
			sheet.addCell(new Label(col++, row, info.getRefundRate()));
			sheet.addCell(new Label(col++, row, info.getComplaintRate()));
		}
		book.write();
		book.close();
	}

	/** 通道交易统计 */
	@RequestMapping(value = "/queryCurrencyCountAnalysisInfo")
	public String queryCurrencyCountAnalysisInfo() {
		Criteria criteria = getCriteria();
		/*
		 * if(null==criteria.getCondition().get("transType")){
		 * criteria.getCondition().put("transType", "sales"); Map<String,
		 * String> par=new HashMap<String, String>(); par.put("transType",
		 * "sales"); getRequest().setAttribute("param", par); }
		 */
		if ("get".equalsIgnoreCase(getRequest().getMethod())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String transDateStart = sdf.format(date);
			String transDateEnd = sdf.format(date);
			criteria.getCondition().put("startDate", transDateStart);
			criteria.getCondition().put("endDate", transDateEnd);
			getRequest().setAttribute("form", criteria.getCondition());
		} else {
			getRequest().setAttribute("total",
					countAnalysisService.queryCurrnecyTotalCount(criteria));
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<CountAnalysis> page = countAnalysisService
					.queryCurrencyCountAnalysisInfo(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "countAnalysis/countCurrencyAnalysisInfoList";
	}

	@RequestMapping(value = "/exportCurrencyCountAnalysisInfo")
	public void exportCurrencyCountAnalysisInfo(HttpServletResponse resp)
			throws Exception {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "failureTransList.xls");
		List<CountAnalysis> list = countAnalysisService
				.queryCurrencyCountAnalysisInfoAll(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = null;
		sheet = book.createSheet("通道统计分析", 0);
		String[] headerName = { "银行", "通道", "通道所属", "通道状态", "交易完成笔数", "交易金额",
				"成功笔数", "成功金额", "失败重复笔数", "失败笔数", "风险单笔数", "拒付笔数", "退单笔数",
				"投诉笔数", "成功率", "拒付率", "退单率", "投诉率" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			CountAnalysis info = list.get(row - 1);
			sheet.addCell(new Label(col++, row, info.getBankName()));// 交易流水号
			sheet.addCell(new Label(col++, row, info.getCurrencyName()));
			sheet.addCell(new Label(col++, row, info.getCurrencyBelongs()));
			sheet.addCell(new Label(col++, row, "1".equals(info
					.getCurrencyEnabled()) ? "开通" : "关闭"));
			sheet.addCell(new Label(col++, row, info.getTransCount()));// 订单号
			sheet.addCell(new Label(col++, row,
					info.getTransCurrency() == null ? "" : info
							.getTransCurrency() + " " + info.getTransAmount()));// 订单号
			sheet.addCell(new Label(col++, row, info.getTransSuccessCount()));// 成功笔数
			sheet.addCell(new Label(col++, row,
					info.getTransCurrency() == null ? "" : info
							.getTransCurrency()
							+ " "
							+ info.getTransSuccessAmount()));// 订单号
			sheet.addCell(new Label(col++, row, info.getDuplicateCount()));// 失败笔数
			sheet.addCell(new Label(col++, row, info.getTransFailureCount()));// 失败笔数
			sheet.addCell(new Label(col++, row, info.getTransRiskCount() + ""));
			sheet.addCell(new Label(col++, row, info.getDishonorCount()));
			sheet.addCell(new Label(col++, row, info.getRefundCount()));
			sheet.addCell(new Label(col++, row, info.getComplaintCount()));
			sheet.addCell(new Label(col++, row, info.getSuccessRate()));
			sheet.addCell(new Label(col++, row, info.getDishonorRate()));
			sheet.addCell(new Label(col++, row, info.getRefundRate()));
			sheet.addCell(new Label(col++, row, info.getComplaintRate()));
		}
		book.write();
		book.close();
	}

	//
	// @RequestMapping("/goTransTrend")
	// public String goTransTrend(){
	// return "countAnalysis/transTrend";
	// }
	//
	// @RequestMapping("/transTrend")
	// public void transTrend(HttpServletResponse res)throws Exception{
	// res.setContentType("text/html");
	// Criteria c = getCriteria();
	// String year = (String)c.getCondition().get("year");
	// if(StringUtils.isEmpty(year)){
	// c.getCondition().put("year",
	// Calendar.getInstance().get(Calendar.YEAR)+"");
	// c.getCondition().put("month",
	// (Calendar.getInstance().get(Calendar.MONTH)+1)+"");
	// }
	// List<CountAnalysis> list =
	// countAnalysisService.queryCountTransInfo(c);//dishonorService.updateDishonorInfo(ids,getLogAccount().getUserName());
	// if(!StringUtils.isEmpty(list)){
	// List<JSONObject> jList = new ArrayList<JSONObject>();
	// for(CountAnalysis li:list){
	// JSONObject json =JSONObject.fromObject(li);
	// jList.add(json);
	// }
	// res.getWriter().write(jList.toString());
	// return;
	// }
	// JSONObject json =JSONObject.fromObject(new CountAnalysis());
	// res.getWriter().write(json.toString());
	// }
	// @ResponseBody
	// @RequestMapping("/transTrendForTable")
	// public HashMap<String, Object> transTrendForTable(HttpServletResponse
	// res){
	// res.setContentType("text/html");
	// Criteria c = getCriteria();
	// String year = (String)c.getCondition().get("year");
	// if(StringUtils.isEmpty(year)){
	// c.getCondition().put("year",
	// Calendar.getInstance().get(Calendar.YEAR)+"");
	// c.getCondition().put("month",
	// (Calendar.getInstance().get(Calendar.MONTH)+1)+"");
	// }
	// PageInfo<CountAnalysis> list =
	// countAnalysisService.queryCountTransInfoForTable(c);//dishonorService.updateDishonorInfo(ids,getLogAccount().getUserName());
	// List<String> columnName=new ArrayList<String>();
	// columnName.add("year,month,day");
	// columnName.add("transCount");
	// columnName.add("transCurrency,transAmount");
	// columnName.add("transSuccessCount");
	// columnName.add("transCurrency,transSuccessAmount");
	// columnName.add("transFailureCount");
	// columnName.add("transRiskCount");
	// columnName.add("successRate");
	// return getAaData(list, columnName);
	// }
	//
	// /** 去网站交易统计页面 */
	// @RequestMapping("/goWebSiteList")
	// public String goWebSite(){
	// return "countAnalysis/webSiteList";
	// }
	//
	/** 网站交易统计 */
	@RequestMapping(value = "/webSiteList")
	public String webSiteList() {
		Criteria criteria = getCriteria();
		/*
		 * if(null==criteria.getCondition().get("transType")){
		 * criteria.getCondition().put("transType", "sales"); Map<String,
		 * String> par=new HashMap<String, String>(); par.put("transType",
		 * "sales"); getRequest().setAttribute("param", par); }
		 */
		if ("get".equalsIgnoreCase(getRequest().getMethod())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String transDateStart = sdf.format(date);
			String transDateEnd = sdf.format(date);
			criteria.getCondition().put("startDate", transDateStart);
			criteria.getCondition().put("endDate", transDateEnd);
			getRequest().setAttribute("form", criteria.getCondition());
		} else {
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<CountAnalysis> page = countAnalysisService
					.queryWebSiteList(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "countAnalysis/webSiteList";
	}

	/** 导出网站交易统计 */
	@RequestMapping(value = "/uploadWebSiteList")
	public void uploadWebSiteList(HttpServletResponse resp) throws IOException,
			RowsExceededException, WriteException {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "webSiteList.xls");
		Criteria criteria = getCriteria();
		criteria.getCondition().put("type", "1");
		List<CountAnalysis> list = countAnalysisService
				.queryWebSiteListInfo(criteria);
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("网站交易统计列表", 0);
		String[] headerName = { "网站", "商户号", "交易完成笔数", "交易金额", "成功笔数", "成功金额",
				"失败笔数", "重复笔数", "风险单笔数", "拒付笔数", "退单笔数", "投诉笔数", "成功率", "拒付率",
				"退单率", "投诉率", "交易占比" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			CountAnalysis info = list.get(row - 1);
			sheet.addCell(new Label(col++, row, info.getPayWebsite()));// 网站
			sheet.addCell(new Label(col++, row, info.getMerNo()));// 终端号
			sheet.addCell(new Label(col++, row, info.getTransCount()));// 交易完成笔数
			sheet.addCell(new Label(col++, row, info.getTransCurrency() + " "
					+ info.getTransAmount()));// 交易金额
			sheet.addCell(new Label(col++, row, info.getTransSuccessCount()));// 成功笔数
			sheet.addCell(new Label(col++, row, info.getTransCurrency() + " "
					+ info.getTransSuccessAmount()));// 成功金额
			sheet.addCell(new Label(col++, row, info.getTransFailureCount()));// 失败笔数
			sheet.addCell(new Label(col++, row, info.getDuplicateCount()));// 重复笔数
			sheet.addCell(new Label(col++, row, info.getTransRiskCount() + ""));// 风险单笔数
			sheet.addCell(new Label(col++, row, info.getDishonorCount()));// 拒付笔数
			sheet.addCell(new Label(col++, row, info.getRefundCount()));// 退单笔数
			sheet.addCell(new Label(col++, row, info.getComplaintCount()));// 投诉笔数
			sheet.addCell(new Label(col++, row, info.getSuccessRate()));// 成功率
			sheet.addCell(new Label(col++, row, info.getDishonorRate()));// 拒付率
			sheet.addCell(new Label(col++, row, info.getRefundRate()));// 退单率
			sheet.addCell(new Label(col++, row, info.getComplaintRate()));// 投诉率
			sheet.addCell(new Label(col++, row, info.getTransRate()));// 交易占比
		}
		book.write();
		book.close();
	}

	// /** 去拒付统计分析列表页面 */
	// @RequestMapping(value="/goCountDishonorList")
	// public String goCountDishonorList(){
	// return "countAnalysis/countDishonorList";
	// }
	//
	// /** 拒付统计分析列表 */
	// @ResponseBody
	// @RequestMapping(value="/countDishonorList")
	// public HashMap<String, Object> countDishonorList(){
	// Criteria criteria = getCriteria();
	// String year = (String)criteria.getCondition().get("year");
	// if(StringUtils.isEmpty(year)){
	// criteria.getCondition().put("year",
	// Calendar.getInstance().get(Calendar.YEAR)+"");
	// }
	// PageInfo<DisCount> page =
	// countAnalysisService.queryDishonoeCountList(criteria);
	// getRequest().setAttribute("page", page);
	// List<String> columnName=new ArrayList<String>();
	// columnName.add("time");
	// columnName.add("type");
	// columnName.add("transSuccessCount");
	// columnName.add("disCount");
	// columnName.add("disRate");
	// columnName.add("time,tradeNos");
	// return getAaData(page, columnName);
	// }
	// /**
	// * 拒付占比
	// * */
	// @ResponseBody
	// @RequestMapping(value="/showDisPer")
	// public List<DisPercent> showDisPer(String time){
	// String[] strs=time.split("-");
	// Criteria criteria=getCriteria();
	// criteria.getCondition().put("year", strs[0]);
	// criteria.getCondition().put("month", strs[1]);
	// return countAnalysisService.queryDisPer(criteria);
	// }
	//
	//
	// /** 导出拒付订单 */
	// @RequestMapping(value="/downloadTransInfo")
	// public void downloadTransInfo(HttpServletResponse resp) throws Exception{
	// resp.setContentType("application/vnd.ms-excel");
	// resp.setHeader("Content-disposition","attachment;filename="+
	// "dishonorTransList.xls" );
	// List<TransDetailInfo> list = new ArrayList<TransDetailInfo>();
	// String str = (String)getCriteria().getCondition().get("tradeNoS");
	// String [] strInfo = str.split(",");
	// for(String s:strInfo){
	// if(!StringUtils.isEmpty(s)){
	// TransDetailInfo transInfo =
	// transMgrService.queryTransDetailsByTradeNo(s);
	// list.add(transInfo);
	// }
	// }
	// WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
	// String type = (String)getCriteria().getCondition().get("type");
	// WritableSheet sheet = null;
	// if("1".equals(type)){
	// sheet = book.createSheet("拒付订单列表", 0);
	// String[] headerName =
	// {"流水号","订单号","网站","交易金额","交易时间","拒付通知时间","拒付原因","拒付状态","拒付金额","退款状态","退款金额","冻结状态","冻结金额",
	// "订单来源","所属终端号","通道英文账单名称","前六后四卡号","货物信息","姓名","邮箱","电话","IP","支付国家","收货国家","收货省/ 州","收货地址","邮编","货运方式",
	// "货运单号","账单国家","账单省/州","账单地址"};
	// // 写入标题
	// for (int index = 0; index < headerName.length; index++) {
	// Label label = new Label(index, 0, headerName[index]);
	// sheet.addCell(label);
	// }
	// }
	// if("2".equals(type)){
	// sheet = book.createSheet("投诉订单列表", 0);
	// String[] headerName =
	// {"流水号","订单号","网站","交易金额","交易时间","投诉通知时间","投诉原因","拒付状态","拒付金额","退款状态","退款金额","冻结状态","冻结金额",
	// "订单来源","所属终端号","通道英文账单名称","前六后四卡号","货物信息","姓名","邮箱","电话","IP","支付国家","收货国家","收货省/ 州","收货地址","邮编",
	// "货运方式","货运单号","账单国家","账单省/州","账单地址"};
	// // 写入标题
	// for (int index = 0; index < headerName.length; index++) {
	// Label label = new Label(index, 0, headerName[index]);
	// sheet.addCell(label);
	// }
	// }
	// for (int row = 1; row <= list.size(); row++) {
	// int col = 0;
	// TransDetailInfo info = list.get(row-1);
	// Complaint comInfo =
	// dishonorService.queryDishonorInfoTradeNo(info.getTradeNo());
	// if(StringUtils.isEmpty(comInfo)){
	// comInfo = new Complaint();
	// }
	// sheet.addCell( new Label(col++, row, info.getTradeNo()));//交易流水号
	// sheet.addCell( new Label(col++, row, info.getOrderNo()));//订单号
	// sheet.addCell( new Label(col++, row, info.getPayWebSite()));//网站
	// sheet.addCell( new Label(col++, row, info.getMerBusCurrency() + " " +
	// info.getMerTransAmount()));//交易金额
	// sheet.addCell( new Label(col++, row, info.getTransDate()));//交易时间
	// sheet.addCell( new Label(col++, row,
	// comInfo.getComplaintDate()));//拒付通知时间
	// sheet.addCell( new Label(col++, row,
	// comInfo.getComplaintTypeValue()));//拒付原因
	// sheet.addCell( new Label(col++, row, info.getDishonorStatus()));//拒付状态
	// sheet.addCell( new Label(col++, row, info.getDishonorAmount()));//拒付金额
	// sheet.addCell( new Label(col++, row, info.getRefundStatus()));//退款状态
	// sheet.addCell( new Label(col++, row, info.getRefundAmount()));//退款金额
	// sheet.addCell( new Label(col++, row, info.getFrozenStatus()));//冻结状态
	// sheet.addCell( new Label(col++, row, info.getFrozenAmount()));//冻结金额
	// sheet.addCell( new Label(col++, row,
	// Tools.parseWebInfoToResourceType(info.getWebInfo()==null?"":info.getWebInfo())));//订单来源
	// sheet.addCell( new Label(col++, row, info.getTerNo()));//所属终端号
	// sheet.addCell( new Label(col++, row, info.getAcquirer()));//通道英文账单名称
	// sheet.addCell( new Label(col++, row,
	// info.getSixAndFourCardNo()));//前六后四卡号
	// if(!StringUtils.isEmpty(info.getGoodsInfoByte())){//货物信息
	// sheet.addCell(new Label(col++, row, new
	// String(info.getGoodsInfoByte(),"utf-8")));
	// System.out.println("===="+new String(info.getGoodsInfoByte(),"utf-8"));
	// }else{
	// sheet.addCell(new Label(col++, row, ""));
	// }
	// sheet.addCell( new Label(col++, row, info.getCardFullName()));//姓名
	// sheet.addCell( new Label(col++, row, info.getEmail()));//邮箱
	// sheet.addCell( new Label(col++, row, info.getCardFullPhone()));//电话
	// sheet.addCell( new Label(col++, row, info.getIpAddress()));//IP
	// sheet.addCell( new Label(col++, row, info.getIpCountry()));//支付国家
	// sheet.addCell( new Label(col++, row, info.getGrCountry()));//收货国家
	// sheet.addCell( new Label(col++, row, info.getGrState()));//收货省/ 州
	// sheet.addCell( new Label(col++, row, info.getGrAddress()));//收货地址
	// sheet.addCell( new Label(col++, row, info.getGrZipCode()));//邮编
	// sheet.addCell( new Label(col++, row, info.getIogistics()));//货运方式
	// sheet.addCell( new Label(col++, row, info.getTrackNo()));//货运单号
	// sheet.addCell( new Label(col++, row, info.getCardCountry()));//账单国家
	// sheet.addCell( new Label(col++, row, info.getCardState()));//账单省/州
	// sheet.addCell( new Label(col++, row, info.getCardAddress()));//账单地址
	// }
	// book.write();
	// book.close();
	// }
	//
	// /** 去投诉统计分析列表页面 */
	// @RequestMapping(value="/goCountComplaintList")
	// public String goCountComplaintList(){
	// return "countAnalysis/countComplaintList";
	// }
	//
	/** 投诉统计分析列表 */
	@RequestMapping(value = "/countComplaintList")
	public String countComplaintList() {
		Criteria criteria = getCriteria();
		if ("get".equalsIgnoreCase(getRequest().getMethod())) {
			Calendar c = Calendar.getInstance();
			// 不处理
			criteria.getCondition().put("countYear", c.get(Calendar.YEAR));
			criteria.getCondition()
					.put("countMonth", c.get(Calendar.MONTH) + 1);
		} else {
			// Criteria criteria = getCriteria();
			// String year = (String)criteria.getCondition().get("year");
			// if(StringUtils.isEmpty(year)){
			// criteria.getCondition().put("year",
			// Calendar.getInstance().get(Calendar.YEAR)+"");
			// }
			criteria.put("type", 2);
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<CompRateInfo> page = countAnalysisService
					.queryCompRateInfo(criteria);
			getRequest().setAttribute("page", page);
		}
		return "countAnalysis/countComplaintList";
	}

	/**
	 * 投诉占比
	 * */
	@RequestMapping("/showComPer")
	public String showComPer(String time) {
		Criteria criteria = getCriteria();
		if (null != time && !"".equals(time) && !"0".equals(time)) {
			criteria.getCondition().put("year", time.substring(0, 4));
			criteria.getCondition().put("month", time.substring(4));
		}
		criteria.put("type", 2);
		getRequest().setAttribute("list",
				countAnalysisService.queryCompPercent(criteria));
		return "countAnalysis/queryCompCountDescInfo";
	}

	@RequestMapping(value = "/downloadCompCountInfo")
	public void downloadCompCountInfo(HttpServletResponse resp, String time)
			throws Exception {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "compTransList.xls");
		Criteria criteria = getCriteria();
		criteria.put("type", 2);
		if (null != time && !"".equals(time) && !"0".equals(time)) {
			criteria.getCondition().put("year", time.substring(0, 4));
			criteria.getCondition().put("month", time.substring(4));
		}
		List<String> strInfo = countAnalysisService
				.queryCompCounttradeNos(criteria);
		List<TransDetailInfo> list = new ArrayList<TransDetailInfo>();
		if (strInfo.size() > 0) {
			list = transMgrService.queryTransInfoByTradeNos(strInfo);
		}
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = null;
		sheet = book.createSheet("错误订单列表", 0);
		String[] headerName = { "商户号", "所属终端号", "通道名称", "流水号", "订单号", "交易时间",
				"交易金额", "卡种", "交易状态", "失败原因", "订单来源", "前六后四卡号", "网站", "姓名",
				"邮箱", "电话", "IP", "支付国家", "收货国家", "收货省/ 州", "收货地址", "邮编",
				"账单国家", "账单省/州", "账单地址" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			TransDetailInfo info = list.get(row - 1);
			sheet.addCell(new Label(col++, row, info.getMerNo()));// 交易流水号
			sheet.addCell(new Label(col++, row, info.getTerNo()));// 所属终端号
			sheet.addCell(new Label(col++, row, info.getCurrencyName()));// 所属终端号
			sheet.addCell(new Label(col++, row, info.getTradeNo()));// 交易流水号
			sheet.addCell(new Label(col++, row, info.getOrderNo()));// 订单号
			sheet.addCell(new Label(col++, row, info.getTransDate()));// 交易时间
			sheet.addCell(new Label(col++, row, info.getMerBusCurrency() + " "
					+ info.getMerTransAmount()));// 交易金额
			sheet.addCell(new Label(col++, row, info.getCardType()));// 卡种
			sheet.addCell(new Label(col++, row,
					"00".equals(info.getRespCode()) ? "支付成功" : "支付失败"));// 交易状态
			sheet.addCell(new Label(col++, row, info.getRespMsg()));// 失败原因
			sheet.addCell(new Label(col++, row, Tools
					.parseWebInfoToResourceType(info.getWebInfo() == null ? ""
							: info.getWebInfo())));// 订单来源
			if (info.getCheckNo() != null && !"".equals(info.getCheckNo())) {
				sheet.addCell(new Label(col++, row, Funcs.decrypt(info
						.getCheckNo()) + "***" + Funcs.decrypt(info.getLast())));
			} else {
				sheet.addCell(new Label(col++, row, ""));
			}
			sheet.addCell(new Label(col++, row, info.getPayWebSite()));// 网站
			sheet.addCell(new Label(col++, row, info.getGrPerName()));
			sheet.addCell(new Label(col++, row, info.getEmail()));// 邮箱
			sheet.addCell(new Label(col++, row, info.getGrphoneNumber()));// 电话
			sheet.addCell(new Label(col++, row, info.getIpAddress()));// IP
			sheet.addCell(new Label(col++, row, info.getIpCountry()));// 支付国家
			sheet.addCell(new Label(col++, row, info.getGrCountry()));// 收货国家
			sheet.addCell(new Label(col++, row, info.getGrState()));// 收货省/ 州
			sheet.addCell(new Label(col++, row, info.getGrAddress()));// 收货地址
			sheet.addCell(new Label(col++, row, info.getGrZipCode()));// 邮编
			sheet.addCell(new Label(col++, row, info.getCardCountry()));// 账单国家
			sheet.addCell(new Label(col++, row, info.getCardState()));// 账单省/州
			sheet.addCell(new Label(col++, row, info.getCardAddress()));// 账单地址
		}
		book.write();
		book.close();
	}

	/** 伪冒统计分析列表 */
	@RequestMapping(value = "/countFakeList")
	public String countFakeList() {
		Criteria criteria = getCriteria();
		if ("get".equalsIgnoreCase(getRequest().getMethod())) {
			Calendar c = Calendar.getInstance();
			// 不处理
			criteria.getCondition().put("countYear", c.get(Calendar.YEAR));
			criteria.getCondition()
					.put("countMonth", c.get(Calendar.MONTH) + 1);
		} else {
			// Criteria criteria = getCriteria();
			// String year = (String)criteria.getCondition().get("year");
			// if(StringUtils.isEmpty(year)){
			// criteria.getCondition().put("year",
			// Calendar.getInstance().get(Calendar.YEAR)+"");
			// }
			criteria.put("type", 3);
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<CompRateInfo> page = countAnalysisService
					.queryCompRateInfo(criteria);
			getRequest().setAttribute("page", page);
		}
		return "countAnalysis/countFakeList";
	}

	/**
	 * 伪冒占比
	 * */
	@RequestMapping("/showFakePer")
	public String showFakePer(String time) {
		Criteria criteria = getCriteria();
		if (null != time && !"".equals(time) && !"0".equals(time)) {
			criteria.getCondition().put("year", time.substring(0, 4));
			criteria.getCondition().put("month", time.substring(4));
		}
		criteria.put("type", 3);
		getRequest().setAttribute("list",
				countAnalysisService.queryCompPercent(criteria));
		return "countAnalysis/queryFakeCountDescInfo";
	}

	@RequestMapping(value = "/downloadFakeCountInfo")
	public void downloadFakeCountInfo(HttpServletResponse resp, String time)
			throws Exception {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "compTransList.xls");
		Criteria criteria = getCriteria();
		criteria.put("type", 3);
		if (null != time && !"".equals(time) && !"0".equals(time)) {
			criteria.getCondition().put("year", time.substring(0, 4));
			criteria.getCondition().put("month", time.substring(4));
		}
		List<String> strInfo = countAnalysisService
				.queryCompCounttradeNos(criteria);
		List<TransDetailInfo> list = new ArrayList<TransDetailInfo>();
		if (strInfo.size() > 0) {
			list = transMgrService.queryTransInfoByTradeNos(strInfo);
		}
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = null;
		sheet = book.createSheet("错误订单列表", 0);
		String[] headerName = { "商户号", "所属终端号", "通道名称", "流水号", "订单号", "交易时间",
				"交易金额", "银行交易金额", "卡种", "交易状态", "失败原因", "订单来源", "前六后四卡号", "网站", "姓名",
				"邮箱", "电话", "IP", "支付国家", "收货国家", "收货省/ 州", "收货地址", "邮编",
				"账单国家", "账单省/州", "账单地址" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			TransDetailInfo info = list.get(row - 1);
			sheet.addCell(new Label(col++, row, info.getMerNo()));// 交易流水号
			sheet.addCell(new Label(col++, row, info.getTerNo()));// 所属终端号
			sheet.addCell(new Label(col++, row, info.getCurrencyName()));// 所属终端号
			sheet.addCell(new Label(col++, row, info.getTradeNo()));// 交易流水号
			sheet.addCell(new Label(col++, row, info.getOrderNo()));// 订单号
			sheet.addCell(new Label(col++, row, info.getTransDate()));// 交易时间
			sheet.addCell(new Label(col++, row, info.getMerBusCurrency() + " "
					+ info.getMerTransAmount()));// 交易金额
			sheet.addCell(new Label(col++, row, info.getBankCurrency() + " "
					+ info.getBankTransAmount()));// 银行交易金额
			sheet.addCell(new Label(col++, row, info.getCardType()));// 卡种
			sheet.addCell(new Label(col++, row,
					"00".equals(info.getRespCode()) ? "支付成功" : "支付失败"));// 交易状态
			sheet.addCell(new Label(col++, row, info.getRespMsg()));// 失败原因
			sheet.addCell(new Label(col++, row, Tools
					.parseWebInfoToResourceType(info.getWebInfo() == null ? ""
							: info.getWebInfo())));// 订单来源
			if (info.getCheckNo() != null && !"".equals(info.getCheckNo())) {
				sheet.addCell(new Label(col++, row, Funcs.decrypt(info
						.getCheckNo()) + "***" + Funcs.decrypt(info.getLast())));
			} else {
				sheet.addCell(new Label(col++, row, ""));
			}
			sheet.addCell(new Label(col++, row, info.getPayWebSite()));// 网站
			sheet.addCell(new Label(col++, row, info.getGrPerName()));
			sheet.addCell(new Label(col++, row, info.getEmail()));// 邮箱
			sheet.addCell(new Label(col++, row, info.getGrphoneNumber()));// 电话
			sheet.addCell(new Label(col++, row, info.getIpAddress()));// IP
			sheet.addCell(new Label(col++, row, info.getIpCountry()));// 支付国家
			sheet.addCell(new Label(col++, row, info.getGrCountry()));// 收货国家
			sheet.addCell(new Label(col++, row, info.getGrState()));// 收货省/ 州
			sheet.addCell(new Label(col++, row, info.getGrAddress()));// 收货地址
			sheet.addCell(new Label(col++, row, info.getGrZipCode()));// 邮编
			sheet.addCell(new Label(col++, row, info.getCardCountry()));// 账单国家
			sheet.addCell(new Label(col++, row, info.getCardState()));// 账单省/州
			sheet.addCell(new Label(col++, row, info.getCardAddress()));// 账单地址
		}
		book.write();
		book.close();
	}

	// /** 去国家交易分布列表页面 */
	// @RequestMapping(value="/goCountCountryList")
	// public String goCountCountryList(){
	// return "countAnalysis/countCountryList";
	// }
	//
	/** 国家交易分布列表 */
	@RequestMapping(value = "/countCountryList")
	public String countCountryList() {
		Criteria criteria = getCriteria();
		if ("get".equalsIgnoreCase(getRequest().getMethod())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String transDateStart = sdf.format(date);
			String transDateEnd = sdf.format(date);
			criteria.getCondition().put("startDate",
					transDateStart + " 00:00:00");
			criteria.getCondition().put("endDate", transDateEnd + " 23:59:59");
			getRequest().setAttribute("form", criteria.getCondition());
		} else {
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<CountAnalysis> page = countAnalysisService
					.queryCountryList(criteria);

			getRequest().setAttribute("page", page);
		}
		return "countAnalysis/countCountryList";
	}

	@ResponseBody
	@RequestMapping(value = "/countCountryListForPic")
	public HashMap<String, Object> countCountryListForPic() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Vector<Vector<String>> vv = new Vector<Vector<String>>();
		Criteria criteria = getCriteria();
		/*
		 * if(null==criteria.getCondition().get("transType")){
		 * criteria.getCondition().put("transType", "sales"); Map<String,
		 * String> par=new HashMap<String, String>(); par.put("transType",
		 * "sales"); getRequest().setAttribute("param", par); }
		 */
		if ("get".equalsIgnoreCase(getRequest().getMethod())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String transDateStart = sdf.format(date);
			String transDateEnd = sdf.format(date);
			criteria.getCondition().put("startDate",
					transDateStart + " 00:00:00");
			criteria.getCondition().put("endDate", transDateEnd + " 23:59:59");
			getRequest().setAttribute("form", criteria.getCondition());
		} else {
			getRequest().setAttribute("form", criteria.getCondition());
			List<CountAnalysis> page = countAnalysisService
					.queryCountryListInfo(criteria);
			for (CountAnalysis info : page) {
				Vector<String> v = new Vector<String>();
				v.add(info.getCardCountry());
				v.add(info.getTransCount());
				vv.add(v);
			}
			map.put("aaData", vv);
		}
		return map;
	}

	/** 导出国家交易分布列表 */
	@RequestMapping(value = "/uploadCountryList")
	public void uploadCountryList(HttpServletResponse resp) throws IOException,
			RowsExceededException, WriteException {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "countryList.xls");
		Criteria criteria = getCriteria();
		List<CountAnalysis> list = countAnalysisService
				.queryCountryListInfo(criteria);
		
		
		/*WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("国家交易分布列表", 0);
		String[] headerName = { "国家", "交易完成笔数", "交易金额", "成功笔数", "成功金额", "失败笔数",
				"风险单笔数", "拒付笔数", "退单笔数", "投诉笔数", "成功率", "拒付率", "退单率", "投诉率",
				"交易占比" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			CountAnalysis info = list.get(row - 1);
			sheet.addCell(new Label(col++, row, info.getCardCountry()));// 国家
			sheet.addCell(new Label(col++, row, info.getTransCount()));// 交易完成笔数
			sheet.addCell(new Label(col++, row, info.getTransCurrency() + " "
					+ info.getTransAmount()));// 交易金额
			sheet.addCell(new Label(col++, row, info.getTransSuccessCount()));// 成功笔数
			sheet.addCell(new Label(col++, row, info.getTransCurrency() + " "
					+ info.getTransSuccessAmount()));// 成功金额
			sheet.addCell(new Label(col++, row, info.getTransFailureCount()));// 失败笔数
			sheet.addCell(new Label(col++, row, info.getTransRiskCount() + ""));// 风险单笔数
			sheet.addCell(new Label(col++, row, info.getDishonorCount()));// 拒付笔数
			sheet.addCell(new Label(col++, row, info.getRefundCount()));// 退单笔数
			sheet.addCell(new Label(col++, row, info.getComplaintCount()));// 投诉笔数
			sheet.addCell(new Label(col++, row, info.getSuccessRate()));// 成功率
			sheet.addCell(new Label(col++, row, info.getDishonorRate()));// 拒付率
			sheet.addCell(new Label(col++, row, info.getRefundRate()));// 退单率
			sheet.addCell(new Label(col++, row, info.getComplaintRate()));// 投诉率
			sheet.addCell(new Label(col++, row, info.getTransRate()));// 交易占比
		}
		book.write();
		book.close();*/
		 //创建Workbook对象
        com.spire.xls.Workbook workbook = new com.spire.xls.Workbook();
       
        //获取第一个工作表
        Worksheet sheet = workbook.getWorksheets().get(0);
        sheet.setName("国家交易分布列表");
        String[] headerName = { "国家", "交易完成笔数", "交易金额", "成功笔数", "成功金额", "失败笔数",
				"风险单笔数", "拒付笔数", "退单笔数", "投诉笔数", "成功率", "拒付率", "退单率", "投诉率",
				"交易占比" };
        String[] columnNames = { "A", "B", "C", "D", "E", "E","G", "H", "I", "G", "K", "L", "M", "N","O" };
        List<List<String>> paramsList=new ArrayList<>();
        int other=0;
        for (int i = 0; i <list.size(); i++) {
			List<String> sonList=new ArrayList<>();
			CountAnalysis info = list.get(i);
			sonList.add(info.getCardCountry());
			sonList.add(info.getTransCount());
			if(i>5){
				other+=Integer.valueOf(info.getTransCount());
			}
			sonList.add(info.getTransCurrency() + " "+ info.getTransAmount());
			sonList.add(info.getTransSuccessCount());
			sonList.add(info.getTransCurrency() + " "+ info.getTransSuccessAmount());
			sonList.add(info.getTransFailureCount());
			sonList.add(String.valueOf(info.getTransRiskCount()));
			sonList.add(info.getDishonorCount());
			sonList.add(info.getRefundCount());
			sonList.add(info.getComplaintCount());
			sonList.add(info.getSuccessRate());
			sonList.add(info.getDishonorRate());
			sonList.add(info.getRefundRate());
			sonList.add(info.getComplaintRate());
			sonList.add(info.getTransRate());
			paramsList.add(sonList);
			
		}
      //将数据写入工作表
        for (int i = 0; i < columnNames.length; i++) {
        	String line=columnNames[i];
        	sheet.getCellRange(line+"1").setValue(headerName[i]);
        	for (int row = 0; row < paramsList.size(); row++) {
        		List<String> sonList=paramsList.get(row);
        		sheet.getCellRange(line+(row+2)).setValue(sonList.get(i));
        	}
        	
		}
        //统计其它表格
        if(other>0){
        	sheet.getCellRange("A"+(list.size()+100)).setValue("其它");
            sheet.getCellRange("B"+(list.size()+100)).setValue(String.valueOf(other));
        }
        
        
        
      //设置单元格样式
	    sheet.getCellRange("A1:O1").setRowHeight(15);
	    sheet.getCellRange("A1:O1").getCellStyle().setColor(Color.darkGray);
	    sheet.getCellRange("A1:O1").getCellStyle().getExcelFont().setColor(Color.white);
	    sheet.getCellRange("A1:O1").getCellStyle().setVerticalAlignment(VerticalAlignType.Center);
	    sheet.getCellRange("A1:O1").getCellStyle().setHorizontalAlignment(HorizontalAlignType.Center);
	  
      //添加饼图
        Chart chart = sheet.getCharts().add(ExcelChartType.Pie);
      //设置图表数据区域
        //chart.setDataRange(sheet.getCellRange("B2:B6,B"+(list.size()+100)));
        //chart.setDataRange(sheet.getCellRange("B2,B3,B4,B5,B6,B"+(list.size()+100)));
        chart.setDataRange(sheet.getCellRange("B2:B6").addCombinedRange(sheet.getCellRange("B"+(list.size()+100))));
      //设置图表标题
        chart.setChartTitle("国家交易分布列表");
        chart.getChartTitleArea().isBold(true);
        chart.getChartTitleArea().setSize(12);
        chart.setSeriesDataFromRange(false);
      //设置图表位置
        chart.setLeftColumn(columnNames.length+1);
        chart.setTopRow(1);
        chart.setRightColumn(columnNames.length+18);
        chart.setBottomRow(30);
      //设置系列标签
        ChartSerie cs = chart.getSeries().get(0);
        cs.setCategoryLabels(sheet.getCellRange("A2:A6").addCombinedRange(sheet.getCellRange("A"+(list.size()+100))));
        cs.setValues(sheet.getCellRange("B2:B6").addCombinedRange(sheet.getCellRange("B"+(list.size()+100))));
        cs.getDataPoints().getDefaultDataPoint().getDataLabels().hasValue(true);
        chart.getPlotArea().getFill().setVisible(false);
        
      //下载文档
        workbook.saveToStream(resp.getOutputStream());
	}

	//
	// /** 去查询失败订单分析页面 */
	// @RequestMapping(value="goFailureList")
	// public String goFailureList(){
	// return "countAnalysis/failureList";
	// }
	//
	/** 失败订单分析 */
	@RequestMapping(value = "/failureList")
	public String failureList() {
		Criteria criteria = getCriteria();
		/*
		 * if(null==criteria.getCondition().get("transType")){
		 * criteria.getCondition().put("transType", "sales"); Map<String,
		 * String> par=new HashMap<String, String>(); par.put("transType",
		 * "sales"); getRequest().setAttribute("param", par); }
		 */
		if ("get".equalsIgnoreCase(getRequest().getMethod())) {

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String transDateStart = sdf1.format(date);
			criteria.getCondition().put("startDate",
					transDateStart + " 00:00:00");
			criteria.getCondition()
					.put("endDate", transDateStart + " 23:59:59");
			getRequest().setAttribute("form", criteria.getCondition());
		} else {
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<CountAnalysis> page = countAnalysisService
					.queryFailureList(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "countAnalysis/failureList";
	}

	/** 失败订单分析 */
	@ResponseBody
	@RequestMapping(value = "/failureListForPic")
	public HashMap<String, Object> failureListForPic() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Vector<Vector<String>> vv = new Vector<Vector<String>>();
		Criteria criteria = getCriteria();
		/*
		 * if(null==criteria.getCondition().get("transType")){
		 * criteria.getCondition().put("transType", "sales"); Map<String,
		 * String> par=new HashMap<String, String>(); par.put("transType",
		 * "sales"); getRequest().setAttribute("param", par); }
		 */
		if ("get".equalsIgnoreCase(getRequest().getMethod())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String transDateStart = sdf.format(date);
			String transDateEnd = sdf.format(date);
			criteria.getCondition().put("startDate", transDateStart);
			criteria.getCondition().put("endDate", transDateEnd);
			getRequest().setAttribute("form", criteria.getCondition());
		} else {
			getRequest().setAttribute("form", criteria.getCondition());
			List<CountAnalysis> page = countAnalysisService
					.queryFailureListAll(getCriteria());
			for (CountAnalysis info : page) {
				Vector<String> v = new Vector<String>();
				v.add(info.getRespMsg());
				v.add(info.getCountRespMsg());
				vv.add(v);
			}
			map.put("aaData", vv);
		}
		return map;
	}

	/** 导出错误订单 */
	@RequestMapping(value = "/exportFailTransInfo")
	public void exportFailTransInfo(HttpServletResponse resp) throws Exception {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "failureTransList.xls");
		List<TransInfo> list = new ArrayList<TransInfo>();
		List<String> str = countAnalysisService
				.queryFailureInfoByRespMsg(getCriteria());
		for (String s : str) {
			if (!StringUtils.isEmpty(s)) {
				TransInfo transInfo = transMgrService
						.queryTransInfoByTradeNo(s);
				list.add(transInfo);
			}
		}
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = null;
		sheet = book.createSheet("错误订单列表", 0);
		String[] headerName = { "商户号", "所属终端号", "通道名称", "流水号", "订单号", "交易时间",
				"交易金额", "卡种", "交易状态", "失败原因", "订单来源", "前六后四卡号", "网站", "货物信息",
				"姓名", "邮箱", "电话", "IP", "支付国家", "收货国家", "收货省/ 州", "收货地址", "邮编",
				"账单国家", "账单省/州", "账单地址" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			TransInfo info = list.get(row - 1);
			sheet.addCell(new Label(col++, row, info.getMerNo()));// 交易流水号
			sheet.addCell(new Label(col++, row, info.getTerNo()));// 所属终端号
			sheet.addCell(new Label(col++, row, info.getCurrencyName()));// 所属终端号
			sheet.addCell(new Label(col++, row, info.getTradeNo()));// 交易流水号
			sheet.addCell(new Label(col++, row, info.getOrderNo()));// 订单号
			sheet.addCell(new Label(col++, row, sdf.format(info.getTransDate())));// 交易时间
			sheet.addCell(new Label(col++, row, info.getMerBusCurrency() + " "
					+ info.getMerTransAmount()));// 交易金额
			sheet.addCell(new Label(col++, row, info.getCardType()));// 卡种
			sheet.addCell(new Label(col++, row,
					"00".equals(info.getRespCode()) ? "支付成功" : "支付失败"));// 交易状态
			sheet.addCell(new Label(col++, row, info.getRespMsg()));// 失败原因
			sheet.addCell(new Label(col++, row, Tools
					.parseWebInfoToResourceType(info.getWebInfo() == null ? ""
							: info.getWebInfo())));// 订单来源
			if (info.getCheckNo() != null && !"".equals(info.getCheckNo())) {
				sheet.addCell(new Label(col++, row, Funcs.decrypt(info
						.getCheckNo()) + "***" + Funcs.decrypt(info.getLast())));
			} else {
				sheet.addCell(new Label(col++, row, ""));
			}
			sheet.addCell(new Label(col++, row, info.getPayWebSite()));// 网站
			if (info.getGoodsInfos() != null) {// 货物信息
				StringBuffer sb = new StringBuffer();
				for (GoodsInfo gi : info.getGoodsInfos()) {
					sb.append(gi.getGoodsName() + "," + gi.getQuantity() + ","
							+ gi.getGoodsPrice() + " ");
				}
				sheet.addCell(new Label(col++, row, sb.toString()));
			} else {
				sheet.addCell(new Label(col++, row, ""));
			}
			sheet.addCell(new Label(col++, row, info.getGrPerName()));
			sheet.addCell(new Label(col++, row, info.getEmail()));// 邮箱
			sheet.addCell(new Label(col++, row, info.getGrphoneNumber()));// 电话
			sheet.addCell(new Label(col++, row, info.getIpAddress()));// IP
			sheet.addCell(new Label(col++, row, info.getIpCountry()));// 支付国家
			sheet.addCell(new Label(col++, row, info.getGrCountry()));// 收货国家
			sheet.addCell(new Label(col++, row, info.getGrState()));// 收货省/ 州
			sheet.addCell(new Label(col++, row, info.getGrAddress()));// 收货地址
			sheet.addCell(new Label(col++, row, info.getGrZipCode()));// 邮编
			sheet.addCell(new Label(col++, row, info.getCardCountry()));// 账单国家
			sheet.addCell(new Label(col++, row, info.getCardState()));// 账单省/州
			sheet.addCell(new Label(col++, row, info.getCardAddress()));// 账单地址
		}
		book.write();
		book.close();
	}

	/** 导出订单跟踪信息 */
	@RequestMapping(value = "/exportTransRecordInfo")
	public void exportTransRecordInfo(HttpServletResponse resp)
			throws Exception {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "transRecordList.xls");
		List<TransRecordInfo> list = transMgrService
				.queryTransRecordInfo(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("订单跟踪列表", 0);
		String[] headerName = { "商户号", "终端号", "流水号", "订单号", "进入系统时间", "异常码",
				"异常原因", "IP", "描述", "协议", "交易金额", "来源网址", "支付提交地址", "返回网址",
				"货物信息", "姓名", "邮箱", "电话", "IP", "前六后四卡号", "收货国家", "收货省/ 州",
				"收货地址", "邮编", "账单国家", "账单省/州", "账单地址" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			TransRecordInfo info = list.get(row - 1);
			TransDetailInfo eInfo = transMgrService.queryTransInfo(info
					.getTradeNo());
			sheet.addCell(new Label(col++, row, info.getMerNo()));// 商户号
			sheet.addCell(new Label(col++, row, info.getTerNo()));// 终端号
			sheet.addCell(new Label(col++, row, info.getTradeNo()));// 交易流水号
			sheet.addCell(new Label(col++, row, info.getOrderNo()));// 商户订单号
			sheet.addCell(new Label(col++, row, info.getEnterTime()));// 进入系统时间
			sheet.addCell(new Label(col++, row, info.getRespCode()));// 异常码
			sheet.addCell(new Label(col++, row, info.getRespMsg()));// 异常原因
			sheet.addCell(new Label(col++, row, info.getClientIP()));// IP
			sheet.addCell(new Label(col++, row, info.getDescription()));// 描述
			sheet.addCell(new Label(col++, row, info.getTransPortProtocol()));// 协议
			sheet.addCell(new Label(col++, row, info.getCurrencyCode() + " "
					+ info.getAmount()));// 交易金额
			sheet.addCell(new Label(col++, row, info.getMerURL()));// 来源网址
			sheet.addCell(new Label(col++, row, info.getSubmitURL()));// 支付提交地址
			sheet.addCell(new Label(col++, row, info.getReturnURL()));// 返回网址
			if (!StringUtils.isEmpty(eInfo.getGoodsInfoByte())) {// 货物信息
				sheet.addCell(new Label(col++, row, new String(eInfo
						.getGoodsInfoByte(), "utf-8")));
				System.out.println("===="
						+ new String(eInfo.getGoodsInfoByte(), "utf-8"));
			} else {
				sheet.addCell(new Label(col++, row, ""));
			}
			sheet.addCell(new Label(col++, row, eInfo.getCardFullName()));// 姓名
			sheet.addCell(new Label(col++, row, eInfo.getEmail()));// 邮箱
			sheet.addCell(new Label(col++, row, eInfo.getCardFullPhone()));// 电话
			sheet.addCell(new Label(col++, row, eInfo.getIpAddress()));// IP
			sheet.addCell(new Label(col++, row, eInfo.getSixAndFourCardNo()));// 前六后四卡号
			sheet.addCell(new Label(col++, row, eInfo.getGrCountry()));// 收货国家
			sheet.addCell(new Label(col++, row, eInfo.getGrState()));// 收货省/ 州
			sheet.addCell(new Label(col++, row, eInfo.getGrAddress()));// 收货地址
			sheet.addCell(new Label(col++, row, eInfo.getGrZipCode()));// 邮编
			sheet.addCell(new Label(col++, row, eInfo.getCardCountry()));// 账单国家
			sheet.addCell(new Label(col++, row, eInfo.getCardState()));// 账单省/州
			sheet.addCell(new Label(col++, row, eInfo.getCardAddress()));// 账单地址
		}
		book.write();
		book.close();
	}

	/**
	 * 交易跟踪统计
	 */
	@RequestMapping(value = "/countTransRecord")
	public String countTransRecord() {
		Criteria criteria = getCriteria();
		/*
		 * if(null==criteria.getCondition().get("transType")){
		 * criteria.getCondition().put("transType", "sales"); Map<String,
		 * String> par=new HashMap<String, String>(); par.put("transType",
		 * "sales"); getRequest().setAttribute("param", par); }
		 */
		if ("get".equalsIgnoreCase(getRequest().getMethod())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String transDateStart = sdf.format(date);
			String transDateEnd = sdf.format(date);
			criteria.getCondition().put("startDate", transDateStart);
			criteria.getCondition().put("endDate", transDateEnd);
			getRequest().setAttribute("form", criteria.getCondition());
		} else {
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<TransRecord> page = countAnalysisService
					.queryTransRecordInfo(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "countAnalysis/countTransRecord";
	}

	/**
	 * 风险订单分析
	 * 
	 * @return
	 */
	@RequestMapping(value = "/riskList")
	public String riskList() {

		Criteria criteria = getCriteria();
		/*
		 * if(null==criteria.getCondition().get("transType")){
		 * criteria.getCondition().put("transType", "sales"); Map<String,
		 * String> par=new HashMap<String, String>(); par.put("transType",
		 * "sales"); getRequest().setAttribute("param", par); }
		 */
		if ("get".equalsIgnoreCase(getRequest().getMethod())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String transDateStart = sdf.format(date);
			String transDateEnd = sdf.format(date);
			criteria.getCondition().put("startDate", transDateStart);
			criteria.getCondition().put("endDate", transDateEnd);
			getRequest().setAttribute("form", criteria.getCondition());
		} else {
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<CountAnalysis> page = countAnalysisService
					.queryRiskList(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "countAnalysis/riskList";
	}

	/**
	 * 风险订单占比
	 * */
	@RequestMapping(value = "/showRiskPer")
	public String showRiskPer() {
		List<RiskPercent> list = countAnalysisService
				.queryShowRiskPerInfo(getCriteria());
		getRequest().setAttribute("list", list);
		return "countAnalysis/queryRiskCountDescInfo";
	}

	@RequestMapping(value = "/downloadRiskInfo")
	public void downloadRiskInfo(HttpServletResponse resp) throws Exception {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "riskTransList.xls");
		List<String> strInfo = countAnalysisService
				.queryDownRiskInfo(getCriteria());
		List<TransDetailInfo> list = transMgrService
				.queryTransInfoByTradeNos(strInfo);
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = null;
		sheet = book.createSheet("风险订单列表", 0);
		String[] headerName = { "流水号", "订单号", "交易时间", "交易金额", "风险阻挡时间",
				"风险阻挡原因", "订单来源", "所属终端号", "前六后四卡号", "网站", "货物信息", "姓名", "邮箱",
				"电话", "IP", "支付国家", "收货国家", "收货省/ 州", "收货地址", "邮编", "账单国家",
				"账单省/州", "账单地址" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}

		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			TransDetailInfo info = list.get(row - 1);
			sheet.addCell(new Label(col++, row, info.getTradeNo()));// 交易流水号
			sheet.addCell(new Label(col++, row, info.getOrderNo()));// 订单号
			sheet.addCell(new Label(col++, row, info.getTransDate()));// 交易时间
			sheet.addCell(new Label(col++, row, info.getMerBusCurrency() + " "
					+ info.getMerTransAmount()));// 交易金额
			sheet.addCell(new Label(col++, row, info.getTransDate()));// 风险阻挡时间
			sheet.addCell(new Label(col++, row, info.getRespMsg()));// 风险阻挡原因
			sheet.addCell(new Label(col++, row, Tools
					.parseWebInfoToResourceType(info.getWebInfo() == null ? ""
							: info.getWebInfo())));// 订单来源
			sheet.addCell(new Label(col++, row, info.getTerNo()));// 所属终端号
			sheet.addCell(new Label(col++, row, info.getSixAndFourCardNo()));// 前六后四卡号
			sheet.addCell(new Label(col++, row, info.getPayWebSite()));// 网站
			if (!StringUtils.isEmpty(info.getGoodsInfoByte())) {// 货物信息
				sheet.addCell(new Label(col++, row, new String(info
						.getGoodsInfoByte(), "utf-8")));
				System.out.println("===="
						+ new String(info.getGoodsInfoByte(), "utf-8"));
			} else {
				sheet.addCell(new Label(col++, row, ""));
			}
			sheet.addCell(new Label(col++, row, info.getCardFullName()));// 姓名
			sheet.addCell(new Label(col++, row, info.getEmail()));// 邮箱
			sheet.addCell(new Label(col++, row, info.getCardFullPhone()));// 电话
			sheet.addCell(new Label(col++, row, info.getIpAddress()));// IP
			sheet.addCell(new Label(col++, row, info.getIpCountry()));// 支付国家
			sheet.addCell(new Label(col++, row, info.getGrCountry()));// 收货国家
			sheet.addCell(new Label(col++, row, info.getGrState()));// 收货省/ 州
			sheet.addCell(new Label(col++, row, info.getGrAddress()));// 收货地址
			sheet.addCell(new Label(col++, row, info.getGrZipCode()));// 邮编
			sheet.addCell(new Label(col++, row, info.getCardCountry()));// 账单国家
			sheet.addCell(new Label(col++, row, info.getCardState()));// 账单省/州
			sheet.addCell(new Label(col++, row, info.getCardAddress()));// 账单地址
		}
		book.write();
		book.close();
	}

	/**
	 * 风险订单分析
	 * 
	 * @return
	 */
	@RequestMapping(value = "/riskPendingList")
	public String riskPendingList() {

		Criteria criteria = getCriteria();
		/*
		 * if(null==criteria.getCondition().get("transType")){
		 * criteria.getCondition().put("transType", "sales"); Map<String,
		 * String> par=new HashMap<String, String>(); par.put("transType",
		 * "sales"); getRequest().setAttribute("param", par); }
		 */
		if ("get".equalsIgnoreCase(getRequest().getMethod())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String transDateStart = sdf.format(date);
			String transDateEnd = sdf.format(date);
			criteria.getCondition().put("startDate", transDateStart);
			criteria.getCondition().put("endDate", transDateEnd);
			getRequest().setAttribute("form", criteria.getCondition());
		} else {
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<CountAnalysis> page = countAnalysisService
					.queryRiskPendingList(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "countAnalysis/riskPendingList";
	}

	/**
	 * 风险订单占比
	 * */
	@RequestMapping(value = "/showRiskPendingPer")
	public String showRiskPendingPer() {
		List<RiskPercent> list = countAnalysisService
				.queryShowRiskPendingPerInfo(getCriteria());
		getRequest().setAttribute("list", list);
		return "countAnalysis/queryRiskCountDescInfo";
	}

	@RequestMapping(value = "/downloadRiskPendingInfo")
	public void downloadRiskPendingInfo(HttpServletResponse resp)
			throws Exception {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "riskTransList.xls");
		List<String> strInfo = countAnalysisService
				.queryDownRiskPendingInfo(getCriteria());
		List<TransDetailInfo> list = transMgrService
				.queryTransInfoByTradeNos(strInfo);
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = null;
		sheet = book.createSheet("风险订单列表", 0);
		String[] headerName = { "流水号", "订单号", "交易时间", "交易金额", "风险阻挡时间",
				"风险阻挡原因", "订单来源", "所属终端号", "前六后四卡号", "网站", "货物信息", "姓名", "邮箱",
				"电话", "IP", "支付国家", "收货国家", "收货省/ 州", "收货地址", "邮编", "账单国家",
				"账单省/州", "账单地址" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}

		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			TransDetailInfo info = list.get(row - 1);
			sheet.addCell(new Label(col++, row, info.getTradeNo()));// 交易流水号
			sheet.addCell(new Label(col++, row, info.getOrderNo()));// 订单号
			sheet.addCell(new Label(col++, row, info.getTransDate()));// 交易时间
			sheet.addCell(new Label(col++, row, info.getMerBusCurrency() + " "
					+ info.getMerTransAmount()));// 交易金额
			sheet.addCell(new Label(col++, row, info.getTransDate()));// 风险阻挡时间
			sheet.addCell(new Label(col++, row, info.getRespMsg()));// 风险阻挡原因
			sheet.addCell(new Label(col++, row, Tools
					.parseWebInfoToResourceType(info.getWebInfo() == null ? ""
							: info.getWebInfo())));// 订单来源
			sheet.addCell(new Label(col++, row, info.getTerNo()));// 所属终端号
			sheet.addCell(new Label(col++, row, info.getSixAndFourCardNo()));// 前六后四卡号
			sheet.addCell(new Label(col++, row, info.getPayWebSite()));// 网站
			if (!StringUtils.isEmpty(info.getGoodsInfoByte())) {// 货物信息
				sheet.addCell(new Label(col++, row, new String(info
						.getGoodsInfoByte(), "utf-8")));
				System.out.println("===="
						+ new String(info.getGoodsInfoByte(), "utf-8"));
			} else {
				sheet.addCell(new Label(col++, row, ""));
			}
			sheet.addCell(new Label(col++, row, info.getCardFullName()));// 姓名
			sheet.addCell(new Label(col++, row, info.getEmail()));// 邮箱
			sheet.addCell(new Label(col++, row, info.getCardFullPhone()));// 电话
			sheet.addCell(new Label(col++, row, info.getIpAddress()));// IP
			sheet.addCell(new Label(col++, row, info.getIpCountry()));// 支付国家
			sheet.addCell(new Label(col++, row, info.getGrCountry()));// 收货国家
			sheet.addCell(new Label(col++, row, info.getGrState()));// 收货省/ 州
			sheet.addCell(new Label(col++, row, info.getGrAddress()));// 收货地址
			sheet.addCell(new Label(col++, row, info.getGrZipCode()));// 邮编
			sheet.addCell(new Label(col++, row, info.getCardCountry()));// 账单国家
			sheet.addCell(new Label(col++, row, info.getCardState()));// 账单省/州
			sheet.addCell(new Label(col++, row, info.getCardAddress()));// 账单地址
		}
		book.write();
		book.close();
	}

	//
	// /** 去风险待处理订单分析页面 */
	// @RequestMapping(value="/goRiskTreatList")
	// public String goRiskTreatList(){
	// return "countAnalysis/riskTreatList";
	// }
	//
	// @ResponseBody
	// @RequestMapping(value="/riskTreatList")
	// public HashMap<String, Object> riskTreatList(){
	// Criteria criteria = getCriteria();
	// String year = (String)criteria.getCondition().get("year");
	// if(StringUtils.isEmpty(year)){
	// criteria.getCondition().put("year",
	// Calendar.getInstance().get(Calendar.YEAR)+"");
	// }
	// PageInfo<RiskPaddingRateInfo>
	// page=countAnalysisService.queryRiskTreat(criteria);
	// getRequest().setAttribute("page", page);
	// List<String> columnName=new ArrayList<String>();
	// columnName.add("time");
	// columnName.add("type");
	// columnName.add("riskToSuccessCount");
	// columnName.add("riskToSuccessToDisCount");
	// columnName.add("riskToDisRate");
	// columnName.add("tradeNo");
	// return getAaData(page, columnName);
	// }
	//
	// @RequestMapping(value="/downloadRiskTreatInfo")
	// public void downloadRiskTreatInfo(HttpServletResponse resp) throws
	// Exception{
	// resp.setContentType("application/vnd.ms-excel");
	// resp.setHeader("Content-disposition","attachment;filename="+
	// "riskTreatTransList.xls" );
	// List<TransDetailInfo> list = new ArrayList<TransDetailInfo>();
	// String str = (String)getCriteria().getCondition().get("tradeNoS");
	// String [] strInfo = str.split(",");
	// for(String s:strInfo){
	// if(!StringUtils.isEmpty(s)){
	// TransDetailInfo transInfo =
	// transMgrService.queryTransDetailsByTradeNo(s);
	// list.add(transInfo);
	// }
	// }
	// WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
	// WritableSheet sheet = null;
	// sheet = book.createSheet("风险待处理订单列表", 0);
	// String[] headerName =
	// {"流水号","订单号","网站","交易金额","交易时间","风控阻挡原因","拒付状态","拒付金额","退款状态","退款金额","冻结状态","冻结金额",
	// "订单来源","所属终端号","通道英文账单名称","前六后四卡号","货物信息","姓名","邮箱","电话","IP","支付国家","收货国家","收货省/ 州","收货地址",
	// "邮编","货运方式","货运单号","账单国家","账单省/州","账单地址"};
	// // 写入标题
	// for (int index = 0; index < headerName.length; index++) {
	// Label label = new Label(index, 0, headerName[index]);
	// sheet.addCell(label);
	// }
	//
	// for (int row = 1; row <= list.size(); row++) {
	// int col = 0;
	// TransDetailInfo info = list.get(row-1);
	// Complaint comInfo =
	// dishonorService.queryDishonorInfoTradeNo(info.getTradeNo());
	// if(StringUtils.isEmpty(comInfo)){
	// comInfo = new Complaint();
	// }
	// sheet.addCell( new Label(col++, row, info.getTradeNo()));//交易流水号
	// sheet.addCell( new Label(col++, row, info.getOrderNo()));//订单号
	// sheet.addCell( new Label(col++, row, info.getPayWebSite()));//网站
	// sheet.addCell( new Label(col++, row, info.getMerBusCurrency() + " " +
	// info.getMerTransAmount()));//交易金额
	// sheet.addCell( new Label(col++, row, info.getTransDate()));//交易时间
	// sheet.addCell( new Label(col++, row, info.getRespMsg()));//风险阻挡原因
	// sheet.addCell( new Label(col++, row, info.getDishonorStatus()));//拒付状态
	// sheet.addCell( new Label(col++, row, info.getDishonorAmount()));//拒付金额
	// sheet.addCell( new Label(col++, row, info.getRefundStatus()));//退款状态
	// sheet.addCell( new Label(col++, row, info.getRefundAmount()));//退款金额
	// sheet.addCell( new Label(col++, row, info.getFrozenStatus()));//冻结状态
	// sheet.addCell( new Label(col++, row, info.getFrozenAmount()));//冻结金额
	// sheet.addCell( new Label(col++, row,
	// Tools.parseWebInfoToResourceType(info.getWebInfo()==null?"":info.getWebInfo())));//订单来源
	// sheet.addCell( new Label(col++, row, info.getTerNo()));//所属终端号
	// sheet.addCell( new Label(col++, row, info.getAcquirer()));//通道英文账单名称
	// sheet.addCell( new Label(col++, row,
	// info.getSixAndFourCardNo()));//前六后四卡号
	// if(!StringUtils.isEmpty(info.getGoodsInfoByte())){//货物信息
	// sheet.addCell(new Label(col++, row, new
	// String(info.getGoodsInfoByte(),"utf-8")));
	// System.out.println("===="+new String(info.getGoodsInfoByte(),"utf-8"));
	// }else{
	// sheet.addCell(new Label(col++, row, ""));
	// }
	// sheet.addCell( new Label(col++, row, info.getCardFullName()));//姓名
	// sheet.addCell( new Label(col++, row, info.getEmail()));//邮箱
	// sheet.addCell( new Label(col++, row, info.getCardFullPhone()));//电话
	// sheet.addCell( new Label(col++, row, info.getIpAddress()));//IP
	// sheet.addCell( new Label(col++, row, info.getIpCountry()));//支付国家
	// sheet.addCell( new Label(col++, row, info.getGrCountry()));//收货国家
	// sheet.addCell( new Label(col++, row, info.getGrState()));//收货省/ 州
	// sheet.addCell( new Label(col++, row, info.getGrAddress()));//收货地址
	// sheet.addCell( new Label(col++, row, info.getGrZipCode()));//邮编
	// sheet.addCell( new Label(col++, row, info.getIogistics()));//货运方式
	// sheet.addCell( new Label(col++, row, info.getTrackNo()));//货运单号
	// sheet.addCell( new Label(col++, row, info.getCardCountry()));//账单国家
	// sheet.addCell( new Label(col++, row, info.getCardState()));//账单省/州
	// sheet.addCell( new Label(col++, row, info.getCardAddress()));//账单地址
	// }
	// book.write();
	// book.close();
	// }

	/** 渠道拒付统计 */
	@RequestMapping(value = "/queryCurrencyDisCount")
	public String queryCurrencyDisCount() {
		Criteria criteria = getCriteria();
		if ("get".equalsIgnoreCase(getRequest().getMethod())) {
			String month = (Calendar.getInstance().get(Calendar.MONTH) + 1)
					+ "";
			String year = (Calendar.getInstance().get(Calendar.YEAR)) + "";
			String countType = "1";
			criteria.getCondition().put("countMonth", month);
			criteria.getCondition().put("countYear", year);
			criteria.getCondition().put("countType", countType);
			getRequest().setAttribute("form", criteria.getCondition());
		} else {
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<CurrencyDisCount> page = countAnalysisService
					.queryCurrencyDisCount(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "countAnalysis/queryCurrencyDisCount";
	}

	@RequestMapping(value = "/exportCurrencyDisCount")
	public void exportCurrencyDisCount(HttpServletResponse resp)
			throws Exception {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "failureTransList.xls");
		List<CurrencyDisCount> list = countAnalysisService
				.exportCurrencyDisCount(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = null;
		sheet = book.createSheet("通道统计分析", 0);
		String[] headerName = new String[] { "银行名称", "通道名称", "开通状态", "通道使用时间",
				"银行商户号", "英文账单名称", "统计方式", "卡种", "交易成功笔数", "上月成功笔数", "上上月成功笔数",
				"拒付笔数", "拒付率", "伪冒笔数", "伪冒占比", "通道所属","备注" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			CurrencyDisCount info = list.get(row - 1);
			sheet.addCell(new Label(col++, row, info.getBankName()));// 交易流水号
			sheet.addCell(new Label(col++, row, info.getCurrencyName()));
			sheet.addCell(new Label(col++, row, BaseDataListener
					.getStringColumnKey("MERCHANTCONFIG", info.getEnabled(),
							"未知类型")));
			sheet.addCell(new Label(col++, row, info.getTdsj()));// 订单号
			sheet.addCell(new Label(col++, row, info.getMerchantNo()));// 订单号
			sheet.addCell(new Label(col++, row, info.getAcquirer()));// 成功笔数
			if (info.getCountYear() == null) {
				sheet.addCell(new Label(col++, row, ""));// 成功笔数
			} else if ("0".equals(info.getCountYear())) {
				sheet.addCell(new Label(col++, row, "总拒付率"));// 成功笔数
			} else {
				sheet.addCell(new Label(col++, row, info.getCountYear() + "-"
						+ info.getCountMonth()));// 成功笔数
			}
			if (getCriteria().getCondition().get("cardType") == null
					|| "".equals(getCriteria().getCondition().get("cardType"))) {
				sheet.addCell(new Label(col++, row, "所有"));// 成功笔数
			} else {
				sheet.addCell(new Label(col++, row, getCriteria()
						.getCondition().get("cardType").toString()));// 成功笔数
			}
			sheet.addCell(new Label(col++, row, info.getSuccessCount()));// 订单号
			// if(null!=getCriteria().getCondition().get("countYear")&&!"".equals(getCriteria().getCondition().get("countYear"))){
			// if(null!=getCriteria().getCondition().get("countMonth")&&!"".equals(getCriteria().getCondition().get("countMonth"))){
			sheet.addCell(new Label(col++, row, info.getLastSuccessCount()));// 订单号
			sheet.addCell(new Label(col++, row, info.getLastLastSuccessCount()));// 订单号
			// }
			// }
			sheet.addCell(new Label(col++, row, info.getDisCount()));// 失败笔数
			if (null == info.getSuccessCount()
					|| "0".equals(info.getSuccessCount())) {
				sheet.addCell(new Label(col++, row, "0.00%"));// 成功笔数
			} else {
				sheet.addCell(new Label(col++, row, nf.format(Double
						.parseDouble(info.getDisCount())
						/ Double.parseDouble(info.getSuccessCount()) * 100)
						+ "%"));// 成功笔数
			}
			sheet.addCell(new Label(col++, row, info.getFakeCount()));// 失败笔数
			if (null == info.getDisCount() || "0".equals(info.getDisCount())) {
				sheet.addCell(new Label(col++, row, "0.00%"));// 成功笔数
			} else {
				sheet.addCell(new Label(col++, row, nf.format(Double
						.parseDouble(info.getFakeCount())
						/ Double.parseDouble(info.getDisCount()) * 100) + "%"));// 成功笔数
			}
			sheet.addCell(new Label(col++, row, info.getCurrencyBelongs()));// 失败笔数
			sheet.addCell(new Label(col++, row, info.getRemark()));// 失败笔数
		}
		book.write();
		book.close();
	}

	@RequestMapping(value = "/listCurrencyDisCountDesc")
	public String listCurrencyDisCountDesc(String currencyId, String countYear,
			String countMonth, String cardType,String merNo,String terNo) {
		Criteria criteria=getCriteria();
		if ("0".equals(countYear)) {
			countYear = null;
			countMonth = null;
			criteria.getCondition().remove("countYear");
			criteria.getCondition().remove("countMonth");
		}
		List<DisDesc> list = countAnalysisService.queryCurrencyDisDesc(
				criteria);
		getRequest().setAttribute("currencyId", currencyId);
		getRequest().setAttribute("merNo", merNo);
		getRequest().setAttribute("terNo", terNo);
		getRequest().setAttribute("countYear", countYear);
		getRequest().setAttribute("countMonth", countMonth);
		getRequest().setAttribute("cardType", cardType);
		getRequest().setAttribute("list", list);
		return "countAnalysis/listCurrencyDisCountDesc";
	}

	@RequestMapping(value = "/exportCurrencyDisRateDescInfo")
	public void exportCurrencyDisRateDescInfo(HttpServletResponse resp)
			throws Exception, IOException, RowsExceededException,
			WriteException {
		Criteria criteria = getCriteria();
		criteria.getCondition().put("type", "1");
		List<Complaint> complaint = countAnalysisService
				.queryListComplaintInfoList(criteria);
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "transLogList.xls");
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("拒付列表", 0);
		String[] headerName = { "商户号", "流水号", "订单号", "网站", "交易金额", "交易状态",
				"交易时间", "拒付通知时间", "拒付原因", "拒付处理截止时间", "CPD日期", "拒付处理状态", "创建人",
				"创建时间", "处理人", "处理时间", "支付通道", "伪冒状态", "拒付状态", "拒付金额", "退款状态",
				"退款金额", "冻结状态", "冻结金额", "订单来源", "所属终端号", "通道英文账单名称", "前六后四卡号",
				"货物信息", "姓名", "邮箱", "电话", "IP", "支付国家", "收货国家", "收货省/ 州",
				"收货地址", "邮编", "货运方式", "货运单号", "账单国家", "账单省/州", "账单地址" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		if (!org.springframework.util.StringUtils.isEmpty(complaint)) {
			for (int row = 1; row <= complaint.size(); row++) {
				int col = 0;
				Complaint info = complaint.get(row - 1);
				TransDetailInfo transInfo = transMgrService.queryTransInfo(info
						.getTradeNo());
				sheet.addCell(new Label(col++, row, transInfo.getMerNo()));// 商户号
				sheet.addCell(new Label(col++, row, transInfo.getTradeNo()));// 交易流水号
				sheet.addCell(new Label(col++, row, transInfo.getOrderNo()));// 订单号
				sheet.addCell(new Label(col++, row, transInfo.getPayWebSite()));// 网站
				sheet.addCell(new Label(col++, row, transInfo
						.getMerBusCurrency()
						+ " "
						+ transInfo.getMerTransAmount()));// 交易金额
				sheet.addCell(new Label(col++, row, BaseDataListener
						.getStringColumnKey("gw_transtype_info",
								transInfo.getRespCode() + "", "未知类型")));// 交易状态
				sheet.addCell(new Label(col++, row, transInfo.getTransDate()));// 交易时间
				sheet.addCell(new Label(col++, row, info.getComplaintDate()));// 拒付通知时间
				sheet.addCell(new Label(col++, row, info
						.getComplaintTypeValue()));// 拒付原因
				sheet.addCell(new Label(col++, row, info.getDeadline()));// 拒付处理截止时间
				sheet.addCell(new Label(col++, row, info.getCPDDate()));// CPD日期
				sheet.addCell(new Label(col++, row, BaseDataListener
						.getStringColumnKey("COMPLAINT_STATUS_1",
								info.getStatus() + "", "未知类型")));// 拒付处理状态
				sheet.addCell(new Label(col++, row, info.getCreatedBy()));// 创建人
				sheet.addCell(new Label(col++, row,
						info.getCreatedDate() != null ? new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss").format(info
								.getCreatedDate()) : ""));// 创建时间
				sheet.addCell(new Label(col++, row, info.getLastUpdateBy()));// 处理人
				sheet.addCell(new Label(
						col++,
						row,
						info.getLastUpdateDate() != null ? new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss").format(info
								.getLastUpdateDate()) : ""));// 处理时间
				sheet.addCell(new Label(col++, row, transInfo.getCurrencyName()));// 支付通道
				sheet.addCell(new Label(col++, row, info.getIsFake() == 0 ? "非"
						: "是"));// 伪冒状态
				sheet.addCell(new Label(col++, row, transInfo
						.getDishonorStatus()));// 拒付状态
				sheet.addCell(new Label(col++, row, transInfo
						.getDishonorAmount()));// 拒付金额
				sheet.addCell(new Label(col++, row, transInfo.getRefundStatus()));// 退款状态
				sheet.addCell(new Label(col++, row, transInfo.getRefundAmount()));// 退款金额
				sheet.addCell(new Label(col++, row, transInfo.getFrozenStatus()));// 冻结状态
				sheet.addCell(new Label(col++, row, transInfo.getFrozenAmount()));// 冻结金额
				sheet.addCell(new Label(col++, row, Tools
						.parseWebInfoToResourceType(transInfo.getWebInfo())));// 订单来源
				sheet.addCell(new Label(col++, row, transInfo.getTerNo()));// 终端号
				sheet.addCell(new Label(col++, row, transInfo.getAcquirer()));// 通道英文账单名称
				sheet.addCell(new Label(col++, row, transInfo
						.getSixAndFourCardNo()));// 前六后四卡号
				if (!org.springframework.util.StringUtils.isEmpty(transInfo
						.getGoodsInfoByte())) {// 货物信息
					sheet.addCell(new Label(col++, row, new String(transInfo
							.getGoodsInfoByte(), "utf-8")));
					System.out
							.println("===="
									+ new String(transInfo.getGoodsInfoByte(),
											"utf-8"));
				} else {
					sheet.addCell(new Label(col++, row, ""));
				}
				sheet.addCell(new Label(col++, row, transInfo.getCardFullName()));// 姓名
				sheet.addCell(new Label(col++, row, transInfo.getEmail()));// 邮箱
				sheet.addCell(new Label(col++, row, transInfo
						.getCardFullPhone()));// 电话
				sheet.addCell(new Label(col++, row, transInfo.getIpAddress()));// IP
				sheet.addCell(new Label(col++, row, transInfo.getCardCountry()));// 支付国家
				sheet.addCell(new Label(col++, row, transInfo.getGrCountry()));// 收货国家
				sheet.addCell(new Label(col++, row, transInfo.getGrState()));// 收货省/
																				// 州
				sheet.addCell(new Label(col++, row, transInfo.getGrAddress()));// 收货地址
				sheet.addCell(new Label(col++, row, transInfo.getGrZipCode()));// 邮编
				sheet.addCell(new Label(col++, row, transInfo.getIogistics()));// 货运方式
				sheet.addCell(new Label(col++, row, transInfo.getTrackNo()));// 货运单号
				sheet.addCell(new Label(col++, row, transInfo.getCardCountry()));// 账单国家
				sheet.addCell(new Label(col++, row, transInfo.getCardState()));// 账单省/州
				sheet.addCell(new Label(col++, row, transInfo.getCardAddress()));// 账单地址
			}
		}
		book.write();
		book.close();
	}

	/**
	 * 商户拒付统计分析
	 * 
	 * @return
	 */
	@RequestMapping(value = "/merchantDisCountList")
	public String merchantDisCountList() {
		Criteria criteria = getCriteria();
		if ("get".equalsIgnoreCase(getRequest().getMethod())) {
			String month = (Calendar.getInstance().get(Calendar.MONTH) + 1)
					+ "";
			String year = (Calendar.getInstance().get(Calendar.YEAR)) + "";
			String countType = "1";
			criteria.getCondition().put("countMonth", month);
			criteria.getCondition().put("countYear", year);
			criteria.getCondition().put("countType", countType);
			getRequest().setAttribute("form", criteria.getCondition());
		} else {
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<CurrencyDisCount> page = countAnalysisService
					.queryMerchantDisCount(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "countAnalysis/merchantDisCount";
//		Criteria criteria = getCriteria();
//		if ("get".equalsIgnoreCase(getRequest().getMethod())) {
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			Calendar c = Calendar.getInstance();
//			criteria.getCondition().put("endDate", sdf.format(c.getTime()));
//			c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
//			criteria.getCondition().put("startDate", sdf.format(c.getTime()));
//			criteria.getCondition().put("isMerchantSee", "0");
//			criteria.getCondition().put("isSp", "0");
//			getRequest().setAttribute("form", criteria.getCondition());
//		} else {
//			getRequest().setAttribute("form", criteria.getCondition());
//			PageInfo<MerchantDisCount> page = countAnalysisService
//					.queryMerchantDisCount(getCriteria());
//			getRequest().setAttribute("page", page);
//		}
//		return "countAnalysis/merchantDisCount";
	}

	@RequestMapping(value = "/exportMerchantDisCountInfo")
	public void exportMerchantDisCountInfo(HttpServletResponse resp)
			throws Exception {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "failureTransList.xls");
		List<CurrencyDisCount> list = countAnalysisService
				.queryMerchantDisCountAll(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = null;
		sheet = book.createSheet("通道统计分析", 0);
		String[] headerName = new String[] { "商户号", "终端号"
				,"统计方式", "卡种", "交易成功笔数","交易占比","成功金额",
				"拒付笔数","拒付占比","拒付金额", "拒付率","拒付金额占比", "伪冒笔数","伪冒金额", "伪冒占比","伪冒金额占比","伪冒率","伪冒总笔数","伪冒总金额"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		int totalSuccessCount=0;
		int totalDisCount=0;
		for(CurrencyDisCount info:list){
			totalSuccessCount+=Integer.parseInt(info.getSuccessCount());
			totalDisCount+=Integer.parseInt(info.getDisCount());
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			CurrencyDisCount info = list.get(row - 1);
			sheet.addCell(new Label(col++, row, info.getMerNo()));// 交易流水号
			sheet.addCell(new Label(col++, row, info.getTerNo()));
			if (info.getCountYear() == null) {
				sheet.addCell(new Label(col++, row, ""));// 成功笔数
			} else if ("0".equals(info.getCountYear())) {
				sheet.addCell(new Label(col++, row, "总拒付率"));// 成功笔数
			} else {
				sheet.addCell(new Label(col++, row, info.getCountYear() + "-"
						+ info.getCountMonth()));// 成功笔数
			}
			if (getCriteria().getCondition().get("cardType") == null
					|| "".equals(getCriteria().getCondition().get("cardType"))) {
				sheet.addCell(new Label(col++, row, "所有"));// 成功笔数
			} else {
				sheet.addCell(new Label(col++, row, getCriteria()
						.getCondition().get("cardType").toString()));// 成功笔数
			}
			sheet.addCell(new Label(col++, row, info.getSuccessCount()));// 订单号
			if(totalSuccessCount != 0){
				sheet.addCell(new Label(col++, row, nf.format(Double.parseDouble(info.getSuccessCount())/totalSuccessCount*100)+"%"));// 订单号
			}else{
				sheet.addCell(new Label(col++, row, "0.00%"));
			}
			sheet.addCell(new Label(col++, row, info.getCurrency() +" " +info.getSuccessAmount()));// 订单号
			sheet.addCell(new Label(col++, row, info.getDisCount()));// 失败笔数
			if(totalDisCount != 0){
				sheet.addCell(new Label(col++, row, nf.format(Double.parseDouble(info.getDisCount())/totalDisCount*100)+"%"));// 订单号
			}else{
				sheet.addCell(new Label(col++, row, "0.00%"));
			}
			sheet.addCell(new Label(col++, row, info.getCurrency() +" " +info.getDisAmount()));// 订单号
			if (null == info.getSuccessCount()
					|| "0".equals(info.getSuccessCount())) {
				sheet.addCell(new Label(col++, row, "0.00%"));// 成功笔数
			} else {
				sheet.addCell(new Label(col++, row, nf.format(Double
						.parseDouble(info.getDisCount())
						/ Double.parseDouble(info.getSuccessCount()) * 100)
						+ "%"));// 成功笔数
			}
			if (null == info.getSuccessCount()
					|| "0".equals(info.getSuccessCount())) {
				sheet.addCell(new Label(col++, row, "0.00%"));// 成功笔数
			} else {
				sheet.addCell(new Label(col++, row, nf.format(Double
						.parseDouble(info.getDisAmount())
						/ Double.parseDouble(info.getSuccessAmount()) * 100)
						+ "%"));// 成功笔数
			}
			sheet.addCell(new Label(col++, row, info.getFakeCount()));// 失败笔数
			sheet.addCell(new Label(col++, row, info.getCurrency() +" " +info.getFakeAmount()));// 订单号
			if (null == info.getDisCount() || "0".equals(info.getDisCount())) {
				sheet.addCell(new Label(col++, row, "0.00%"));// 成功笔数
			} else {
				sheet.addCell(new Label(col++, row, nf.format(Double
						.parseDouble(info.getFakeCount())
						/ Double.parseDouble(info.getDisCount()) * 100) + "%"));// 成功笔数
			}
			if (null == info.getSuccessCount()
					|| "0".equals(info.getSuccessCount())) {
				sheet.addCell(new Label(col++, row, "0.00%"));// 成功笔数
			} else {
				sheet.addCell(new Label(col++, row, nf.format(Double
						.parseDouble(info.getFakeAmount())
						/ Double.parseDouble(info.getSuccessAmount()) * 100)
						+ "%"));// 成功笔数
			}
			if (null == info.getSuccessCount()
					|| "0".equals(info.getSuccessCount())) {
				sheet.addCell(new Label(col++, row, "0.00%"));// 成功笔数
			} else {
				sheet.addCell(new Label(col++, row, nf.format(Double
						.parseDouble(info.getFakeCount())
						/ Double.parseDouble(info.getSuccessCount()) * 100)
						+ "%"));// 成功笔数
			}
			sheet.addCell(new Label(col++, row, info.getTotalFakeCount()));// 失败笔数
			sheet.addCell(new Label(col++, row, info.getCurrency() +" " +info.getTotalFakeAmount()));// 订单号
		}
		book.write();
		book.close();
	}
//	@RequestMapping(value = "/exportMerchantDisCountInfo")
//	
//	public void exportMerchantDisCountInfo(HttpServletResponse resp)
//			throws Exception {
//		resp.setContentType("application/vnd.ms-excel");
//		resp.setHeader("Content-disposition", "attachment;filename="
//				+ "failureTransList.xls");
//		List<MerchantDisCount> list = countAnalysisService
//				.queryMerchantDisCountAll(getCriteria());
//		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
//		WritableSheet sheet = null;
//		sheet = book.createSheet("通道统计分析", 0);
//		String[] headerName = new String[] { "商户号", "终端号", "交易成功笔数", "拒付笔数",
//				"拒付率" };
//		// 写入标题
//		for (int index = 0; index < headerName.length; index++) {
//			Label label = new Label(index, 0, headerName[index]);
//			sheet.addCell(label);
//		}
//		NumberFormat nf = NumberFormat.getInstance();
//		nf.setMaximumFractionDigits(2);
//		nf.setMinimumFractionDigits(2);
//		for (int row = 1; row <= list.size(); row++) {
//			int col = 0;
//			MerchantDisCount info = list.get(row - 1);
//			sheet.addCell(new Label(col++, row, info.getMerNo()));// 交易流水号
//			sheet.addCell(new Label(col++, row, info.getTerNo()));
//			sheet.addCell(new Label(col++, row, info.getSuccessCount()));// 订单号
//			sheet.addCell(new Label(col++, row, info.getDisCount()));// 订单号
//			if (null == info.getSuccessCount()
//					|| "0".equals(info.getSuccessCount())) {
//				sheet.addCell(new Label(col++, row, "0.00%"));// 成功笔数
//			} else {
//				sheet.addCell(new Label(col++, row, nf.format(Double
//						.parseDouble(info.getDisCount())
//						/ Double.parseDouble(info.getSuccessCount()) * 100)
//						+ "%"));// 成功笔数
//			}
//		}
//		book.write();
//		book.close();
//	}

	/**
	 * 商户交易同比增长
	 * 
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/merchantTransCountRate")
	public String merchantTransCountRate() throws ParseException {
		Criteria criteria = getCriteria();
		if ("get".equalsIgnoreCase(getRequest().getMethod())) {
			// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			// Calendar c=Calendar.getInstance();
			// criteria.getCondition().put("endDate", sdf.format(c.getTime()));
			// c.set(Calendar.MONTH, c.get(Calendar.MONTH)-1);
			// criteria.getCondition().put("startDate",
			// sdf.format(c.getTime()));
			// criteria.getCondition().put("isMerchantSee", "0");
			// criteria.getCondition().put("isSp", "0");
			// getRequest().setAttribute("form", criteria.getCondition());
		} else {
			// 查询条件中的周期数
			String cycleCount = (String) criteria.getCondition().get(
					"cycleCount");
			if (cycleCount.matches("[0-9]+")) {
				if (Integer.parseInt(cycleCount) <= 0) {
					cycleCount = "1";
				}
			} else {
				cycleCount = "1";
			}
			String cycleType = (String) criteria.getCondition()
					.get("cycleType");
			if (null == cycleType || "".equals(cycleType)) {
				cycleType = "day";
			}
			// 周期内的天数
			String n = (String) criteria.getCondition().get("n");
			if (null == n || "".equals("n")) {
				n = "1";
			}
			if (n.matches("[0-9+]")) {
				if (Integer.parseInt(n) <= 0) {
					n = "1";
				}
			} else {
				n = "1";
			}
			// 如果周期不是自然天 那么设置周期内的时间数为1
			// 设置周期类型年月周的周期数为1 d：天 w：自然周 m：自然月 y：自然年
			if (!"day".equals(cycleType)) {
				n = "1";
			}
			List<TransRateCyleForSearch> list = new ArrayList<TransRateCyleForSearch>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 设置周期 天数 周期开始时间及结束时间
			for (int i = 1; i <= Integer.parseInt(cycleCount); i++) {
				TransRateCyleForSearch info = new TransRateCyleForSearch();
				info.setCycle(i);// 设置是第i个周期
				Calendar c = Calendar.getInstance();
				if ("day".equals(cycleType)) {
					// 计算结束时间
					c.add(Calendar.DATE, -Integer.parseInt(n) * (i - 1));
					info.setEndDate(sdf.format(c.getTime()));
					// 计算开始时间
					c.add(Calendar.DATE, -Integer.parseInt(n));
					info.setStartDate(sdf.format(c.getTime()));

					// 计算周期天数
					info.setDayCount(Integer.parseInt(n));
				} else if ("week".equals(cycleType)) {
					// 设置为本周第一天
					c.set(Calendar.DAY_OF_WEEK, 2);
					// 计算结束时间
					c.add(Calendar.WEEK_OF_YEAR, -Integer.parseInt(n) * (i - 1));
					info.setEndDate(sdf.format(c.getTime()));
					// 计算开始时间
					c.add(Calendar.WEEK_OF_YEAR, -1);
					info.setStartDate(sdf.format(c.getTime()));

					// 计算周期天数
					info.setDayCount((sdf.parse(info.getEndDate()).getTime() - sdf
							.parse(info.getStartDate()).getTime())
							/ 1000
							/ 60
							/ 60 / 24);
				} else if ("month".equals(cycleType)) {
					// 设置为本月第一天
					c.set(Calendar.DAY_OF_MONTH, 1);
					// 计算结束时间
					c.add(Calendar.MONTH, -Integer.parseInt(n) * (i - 1));
					info.setEndDate(sdf.format(c.getTime()));
					// 计算开始时间
					c.add(Calendar.MONTH, -1);
					info.setStartDate(sdf.format(c.getTime()));

					// 计算周期天数
					info.setDayCount((sdf.parse(info.getEndDate()).getTime() - sdf
							.parse(info.getStartDate()).getTime())
							/ 1000
							/ 60
							/ 60 / 24);
				} else if ("year".equals(cycleType)) {
					// 设置为本月第一天
					c.set(Calendar.DAY_OF_YEAR, 1);
					// 计算结束时间
					c.add(Calendar.YEAR, -Integer.parseInt(n) * (i - 1));
					info.setEndDate(sdf.format(c.getTime()));
					// 计算开始时间
					c.add(Calendar.YEAR, -1);
					info.setStartDate(sdf.format(c.getTime()));

					// 计算周期天数
					info.setDayCount((sdf.parse(info.getEndDate()).getTime() - sdf
							.parse(info.getStartDate()).getTime())
							/ 1000
							/ 60
							/ 60 / 24);
				}
				System.out.println(info.getStartDate());
				System.out.println(info.getEndDate());
				list.add(info);
			}
			// 用作查询数据集
			criteria.getCondition().put("cyclelist", list);
			PageInfo<MerchantTransCountRateInfo> page = countAnalysisService
					.queryMerchantTransCountRate(criteria);
			getRequest().setAttribute("page", page);
		}
		return "countAnalysis/merchantTransCountRate";
	}

	/**
	 * 导出商户交易同比
	 * 
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportMerchantTransCountRate")
	public void exportMerchantTransCountRate(HttpServletResponse resp)
			throws Exception {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "failureTransList.xls");
		//

		Criteria criteria = getCriteria();
		// 查询条件中的周期数
		String cycleCount = (String) criteria.getCondition().get("cycleCount");
		if (cycleCount.matches("[0-9]+")) {
			if (Integer.parseInt(cycleCount) <= 0) {
				cycleCount = "1";
			}
		} else {
			cycleCount = "1";
		}
		String cycleType = (String) criteria.getCondition().get("cycleType");
		if (null == cycleType || "".equals(cycleType)) {
			cycleType = "day";
		}
		// 周期内的天数
		String n = (String) criteria.getCondition().get("n");
		if (null == n || "".equals("n")) {
			n = "1";
		}
		if (n.matches("[0-9+]")) {
			if (Integer.parseInt(n) <= 0) {
				n = "1";
			}
		} else {
			n = "1";
		}
		// 如果周期不是自然天 那么设置周期内的时间数为1
		// 设置周期类型年月周的周期数为1 d：天 w：自然周 m：自然月 y：自然年
		if (!"day".equals(cycleType)) {
			n = "1";
		}
		List<TransRateCyleForSearch> list1 = new ArrayList<TransRateCyleForSearch>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 设置周期 天数 周期开始时间及结束时间
		for (int i = 1; i <= Integer.parseInt(cycleCount) + 1; i++) {
			TransRateCyleForSearch info = new TransRateCyleForSearch();
			info.setCycle(i);// 设置是第i个周期
			Calendar c = Calendar.getInstance();
			if ("day".equals(cycleType)) {
				// 计算结束时间
				c.add(Calendar.DATE, -Integer.parseInt(n) * (i - 1));
				info.setEndDate(sdf.format(c.getTime()));
				// 计算开始时间
				c.add(Calendar.DATE, -Integer.parseInt(n));
				info.setStartDate(sdf.format(c.getTime()));

				// 计算周期天数
				info.setDayCount(Integer.parseInt(n));
			} else if ("week".equals(cycleType)) {
				// 设置为本周第一天
				c.set(Calendar.DAY_OF_WEEK, 2);
				// 计算结束时间
				c.add(Calendar.WEEK_OF_YEAR, -Integer.parseInt(n) * (i - 1));
				info.setEndDate(sdf.format(c.getTime()));
				// 计算开始时间
				c.add(Calendar.WEEK_OF_YEAR, -1);
				info.setStartDate(sdf.format(c.getTime()));

				// 计算周期天数
				info.setDayCount((sdf.parse(info.getEndDate()).getTime() - sdf
						.parse(info.getStartDate()).getTime())
						/ 1000
						/ 60
						/ 60
						/ 24);
			} else if ("month".equals(cycleType)) {
				// 设置为本月第一天
				c.set(Calendar.DAY_OF_MONTH, 1);
				// 计算结束时间
				c.add(Calendar.MONTH, -Integer.parseInt(n) * (i - 1));
				info.setEndDate(sdf.format(c.getTime()));
				// 计算开始时间
				c.add(Calendar.MONTH, -1);
				info.setStartDate(sdf.format(c.getTime()));

				// 计算周期天数
				info.setDayCount((sdf.parse(info.getEndDate()).getTime() - sdf
						.parse(info.getStartDate()).getTime())
						/ 1000
						/ 60
						/ 60
						/ 24);
			} else if ("year".equals(cycleType)) {
				// 设置为本月第一天
				c.set(Calendar.DAY_OF_YEAR, 1);
				// 计算结束时间
				c.add(Calendar.YEAR, -Integer.parseInt(n) * (i - 1));
				info.setEndDate(sdf.format(c.getTime()));
				// 计算开始时间
				c.add(Calendar.YEAR, -1);
				info.setStartDate(sdf.format(c.getTime()));

				// 计算周期天数
				info.setDayCount((sdf.parse(info.getEndDate()).getTime() - sdf
						.parse(info.getStartDate()).getTime())
						/ 1000
						/ 60
						/ 60
						/ 24);
			}
			System.out.println(info.getStartDate());
			System.out.println(info.getEndDate());
			list1.add(info);
		}
		// 用作查询数据集
		criteria.getCondition().put("cyclelist", list1);
		// 最后一个周期
		String lastCycle = list1.get(list1.size() - 1).getStartDate() + "~"
				+ list1.get(list1.size() - 1).getEndDate();
		//
		List<MerchantTransCountRateInfo> list = countAnalysisService
				.queryMerchantTransCountRateAll(criteria);
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = null;
		sheet = book.createSheet("商户交易同比", 0);
		String[] headerName = new String[] { "周期", "商户号", "商户性质", "销售员","是否开通",
				"开通日期", "时间周期内交易天数", "成功率", "上一周期成功率", "成功率同比", "成功金额",
				"上一周期成功金额", "成功金额同比", "日平均交易金额", "上一周期日平均交易金额", "日均交易金额同比",
				"成功笔数", "上一周期成功笔数", "成功笔数同比", "退款笔数", "上一周期退款笔数", "退款笔数同比",
				"拒付笔数", "上一周期拒付笔数", "拒付笔数同比", "单笔平均交易金额", "上一周期单笔平均交易金额",
				"单笔平均交易金额同比", "发货率", "上一周期发货率", "发货率同比", "妥投率", "上一周期妥投率",
				"妥投率同比","最后交易时间" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		int rowForTable = 0;
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			// 取当前周期
			MerchantTransCountRateInfo info = list.get(row - 1);
			// 当前周期如果是最后一个周期不做处理
			if (info.getCycle().equals(lastCycle)) {
				continue;
			}
			rowForTable++;
			// 去上一周期
			MerchantTransCountRateInfo info1 = null;
			if (row < list.size()) {
				info1 = list.get(row);
				if (!info.getMerNo().equals(info1.getMerNo())
						|| info.getId() != (info1.getId() - 1)) {
					info1 = new MerchantTransCountRateInfo();
				}
			}
			if (info1 == null) {
				info1 = new MerchantTransCountRateInfo();
			}
			sheet.addCell(new Label(col++, rowForTable, info.getCycle()));// 交易流水号
			sheet.addCell(new Label(col++, rowForTable, info.getMerNo()));// 交易流水号
			sheet.addCell(new Label(col++, rowForTable, BaseDataListener
					.getStringColumnKey("MERCHANTTYPE", info.getType(),
							info.getType())));
			sheet.addCell(new Label(col++ , rowForTable, info.getSales()));
			sheet.addCell(new Label(col++, rowForTable, BaseDataListener
					.getStringColumnKey("MERCHANTSTATUS", info.getEnabled(),
							info.getEnabled())));
			sheet.addCell(new Label(col++, rowForTable, info
					.getActivationDate()!=null?sdf.format(info
					.getActivationDate()):""));
			sheet.addCell(new Label(col++, rowForTable, info.getTransTime()
					+ ""));
			sheet.addCell(new Label(col++, rowForTable, nf.format(info
					.getSuccessRate() * 100) + "%"));
			sheet.addCell(new Label(col++, rowForTable, nf.format(info1
					.getSuccessRate() * 100) + "%"));
			if (info1.getSuccessRate() == 0) {
				sheet.addCell(new Label(col++, rowForTable, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, rowForTable, nf.format((info
						.getSuccessRate() * 100 - info1.getSuccessRate() * 100)
						/ info.getSuccessRate()) + "%"));// 订单号
			}
			sheet.addCell(new Label(col++, rowForTable, info
					.getMerSettleCurrency()
					+ " "
					+ nf.format(info.getSuccessAmount())));// 成功金额
			sheet.addCell(new Label(col++, rowForTable, info1
					.getMerSettleCurrency()
					+ " "
					+ nf.format(info1.getSuccessAmount())));// 上一周期成功金额
			if (info1.getSuccessAmount() == 0) {
				sheet.addCell(new Label(col++, rowForTable, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, rowForTable, nf.format((info
						.getSuccessAmount() - info1.getSuccessAmount())
						/ info1.getSuccessAmount() * 100) + "%"));// 订单号
			}
			if (0 == info.getTransTime()) {
				sheet.addCell(new Label(col++, rowForTable, info
						.getMerSettleCurrency() + " " + "0.00"));
			} else {
				sheet.addCell(new Label(col++, rowForTable, info
						.getMerSettleCurrency()
						+ " "
						+ nf.format(info.getSuccessAmount()
								/ info.getTransTime())));
			}
			if (0 == info1.getTransTime()) {
				sheet.addCell(new Label(col++, rowForTable, info1
						.getMerSettleCurrency() + " " + "0.00"));
			} else {
				sheet.addCell(new Label(col++, rowForTable, info1
						.getMerSettleCurrency()
						+ " "
						+ nf.format(info1.getSuccessAmount()
								/ info1.getTransTime())));
			}
			if (info1.getSuccessAmount() == 0 || 0 == info1.getTransTime()) {
				sheet.addCell(new Label(col++, rowForTable, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, rowForTable, nf.format((info
						.getSuccessAmount() / info.getTransTime() - info1
						.getSuccessAmount() / info1.getTransTime())
						/ (info1.getSuccessAmount() / info1.getTransTime())
						* 100)
						+ "%"));
			}
			sheet.addCell(new Label(col++, rowForTable, info.getSuccessCount()
					+ ""));
			sheet.addCell(new Label(col++, rowForTable, info1.getSuccessCount()
					+ ""));
			if (info1.getSuccessCount() == 0) {
				sheet.addCell(new Label(col++, rowForTable, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, rowForTable, nf.format((info
						.getSuccessCount() * 1.0 - info1.getSuccessCount())
						/ info1.getSuccessCount() * 100) + "%"));
			}
			sheet.addCell(new Label(col++, rowForTable, info.getRefundCount()
					+ ""));
			sheet.addCell(new Label(col++, rowForTable, info1.getRefundCount()
					+ ""));
			if (info1.getRefundCount() == 0) {
				sheet.addCell(new Label(col++, rowForTable, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, rowForTable, nf.format((info
						.getRefundCount() * 1.0 - info1.getRefundCount())
						/ info1.getRefundCount() * 100) + "%"));
			}
			sheet.addCell(new Label(col++, rowForTable, info.getDisCount() + ""));
			sheet.addCell(new Label(col++, rowForTable, info1.getDisCount()
					+ ""));
			if (info1.getDisCount() == 0) {
				sheet.addCell(new Label(col++, rowForTable, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, rowForTable, nf.format((info
						.getDisCount() * 1.0 - info1.getDisCount())
						/ info1.getDisCount() * 100) + "%"));
			}
			if (info.getSuccessAmount() == 0 || info.getSuccessCount() == 0) {
				sheet.addCell(new Label(col++, rowForTable, "0.00"));
			} else {
				sheet.addCell(new Label(col++, rowForTable, info
						.getMerSettleCurrency()
						+ " "
						+ nf.format(info.getSuccessAmount()
								/ info.getSuccessCount())));
			}
			if (info1.getSuccessAmount() == 0 || info1.getSuccessCount() == 0) {
				sheet.addCell(new Label(col++, rowForTable, "0.00"));
			} else {
				sheet.addCell(new Label(col++, rowForTable, info1
						.getMerSettleCurrency()
						+ " "
						+ nf.format(info1.getSuccessAmount()
								/ info1.getSuccessCount())));
			}
			if (info1.getSuccessAmount() == 0 || info1.getSuccessCount() == 0) {
				sheet.addCell(new Label(col++, rowForTable, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, rowForTable, nf.format((info
						.getSuccessAmount() / info.getSuccessCount() - info1
						.getSuccessAmount() / info1.getSuccessCount())
						/ (info1.getSuccessAmount() / info1.getSuccessCount())
						* 100)
						+ "%"));
			}
			// 妥投率=查询周期内已妥投笔数/查询周期内发货笔数
			// 发货率=查询周期内已发货笔数/查询周期内成功笔数
			if (info.getSuccessCount() == 0) {// 发货率
				sheet.addCell(new Label(col++, rowForTable, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, rowForTable,
						nf.format((info.getShipCount() * 1.0)
								/ (info.getSuccessCount()) * 100)
								+ "%"));
			}
			if (info1.getSuccessCount() == 0) {// 上一周期发货率
				sheet.addCell(new Label(col++, rowForTable, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, rowForTable, nf.format((info1
						.getShipCount() * 1.0)
						/ (info1.getSuccessCount())
						* 100) + "%"));
			}
			if (info.getSuccessCount() == 0 || info1.getSuccessCount() == 0
					|| info1.getShipCount() == 0 || info.getShipCount() == 0) {// 发货率同比
				sheet.addCell(new Label(col++, rowForTable, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, rowForTable, nf.format((info
						.getShipCount() * 1.0 / info.getSuccessCount() - info1
						.getShipCount() / info1.getSuccessCount())
						/ (info1.getShipCount() / info1.getSuccessCount())
						* 100)
						+ "%"));
			}
			if (info.getShipCount() == 0) {// 妥投率
				sheet.addCell(new Label(col++, rowForTable, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, rowForTable, nf.format((info
						.getSignCount() * 1.0) / (info.getShipCount()) * 100)
						+ "%"));
			}
			if (info1.getShipCount() == 0) {// 上一周期发货率
				sheet.addCell(new Label(col++, rowForTable, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, rowForTable, nf.format((info1
						.getSignCount() * 1.0) / (info1.getShipCount()) * 100)
						+ "%"));
			}
			if (info.getSignCount() == 0 || info1.getSignCount() == 0
					|| info1.getShipCount() == 0 || info.getShipCount() == 0) {// 发货率同比
				sheet.addCell(new Label(col++, rowForTable, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, rowForTable, nf.format((info
						.getSignCount() * 1.0 / info.getShipCount() - info1
						.getSignCount() / info1.getShipCount())
						/ (info1.getSignCount() / info1.getShipCount()) * 100)
						+ "%"));
			}
			sheet.addCell(new Label(col++, rowForTable, info.getLastTransDate()));
		}
		book.write();
		book.close();
	}

	// public static void main(String[] args) {
	// Calendar c=Calendar.getInstance();
	// c.set(Calendar.DAY_OF_YEAR, 1);
	// System.out.println(c.getTime());
	// }

	/**
	 * 国家查找带回
	 * 
	 * @return
	 */
	@RequestMapping("/getCountryListBrightBack")
	public String getCountryListBrightBack() {
		PageInfo<CountryInfo> page = countAnalysisService
				.queryCountryInfos(getCriteria());
		getRequest().setAttribute("page", page);
		return "countAnalysis/getCountryListBrightBack";
	}

	@RequestMapping("/queryBrandCountInfo")
	public String queryBrandCountInfo() {
		Criteria criteria = getCriteria();
		if ("get".equalsIgnoreCase(getRequest().getMethod())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(new Date());
			criteria.getCondition().put("transDateStart", date);
			criteria.getCondition().put("transDateEnd", date);
			getRequest().setAttribute("form", criteria.getCondition());
		} else {
			getRequest().setAttribute("form", criteria.getCondition());
			Collection<BrandCountInfo> info = countAnalysisService
					.queryBrandCountInfo(criteria);
			getRequest().setAttribute("infos", info);
		}
		return "countAnalysis/queryBrandCountInfo";
	}

	/**
	 * 品牌统计分析饼图
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryBrandCountInfoForPic")
	public HashMap<String, Object> queryBrandCountInfoForPic() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Vector<Vector<String>> vv = new Vector<Vector<String>>();
		Criteria criteria = getCriteria();
		if ("get".equalsIgnoreCase(getRequest().getMethod())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(new Date());
			criteria.getCondition().put("transDateStart", date);
			criteria.getCondition().put("transDateEnd", date);
			getRequest().setAttribute("form", criteria.getCondition());
		} else {
			getRequest().setAttribute("form", criteria.getCondition());
			Collection<BrandCountInfo> infos = countAnalysisService
					.queryBrandCountInfo(criteria);
			for (BrandCountInfo info : infos) {
				Vector<String> v = new Vector<String>();
				v.add(info.getBrand());
				v.add(info.getTransCount() + "");
				vv.add(v);
			}
			map.put("aaData", vv);
		}
		return map;
	}

	@RequestMapping(value = "/exportbrandCountInfo")
	public void exportbrandCountInfo(HttpServletResponse resp) throws Exception {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "failureTransList.xls");
		List<BrandCountInfo> list = new ArrayList<BrandCountInfo>(
				countAnalysisService.queryBrandCountInfo(getCriteria()));
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = null;
		sheet = book.createSheet("交易统计分析", 0);
		String[] headerName = { "品牌", "交易完成笔数", "交易金额", "成功笔数", "成功金额", "失败笔数",
				"失败重复笔数", "风险单笔数", "拒付笔数", "退单笔数", "投诉笔数", "伪冒笔数", "成功率",
				"拒付率", "退单率", "投诉率", "伪冒率", "占比" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			BrandCountInfo info = list.get(row - 1);
			sheet.addCell(new Label(col++, row, info.getBrand()));// 交易流水号
			sheet.addCell(new Label(col++, row, info.getTransCount() + ""));// 订单号
			sheet.addCell(new Label(col++, row, info.getCurrency() + " "
					+ info.getTransAmount()));// 订单号
			sheet.addCell(new Label(col++, row, info.getSuccessCount() + ""));// 成功笔数
			sheet.addCell(new Label(col++, row, info.getCurrency() + " "
					+ info.getSuccessAmount()));// 订单号
			sheet.addCell(new Label(col++, row, info.getFailCount() + ""));// 失败笔数
			sheet.addCell(new Label(col++, row, info.getDupCount() + ""));// 失败笔数
			sheet.addCell(new Label(col++, row, info.getRiskCount() + ""));
			sheet.addCell(new Label(col++, row, info.getDisCount() + ""));
			sheet.addCell(new Label(col++, row, info.getRefundCount() + ""));
			sheet.addCell(new Label(col++, row, info.getComCount() + ""));
			sheet.addCell(new Label(col++, row, info.getFakeCount() + ""));
			if (info.getTransCount() - info.getRiskCount() - info.getDupCount() == 0) {
				sheet.addCell(new Label(col++, row, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, row, nf.format(info
						.getSuccessCount()
						* 100.0
						/ (info.getTransCount() - info.getRiskCount() - info
								.getDupCount()))
						+ "%"));// 订单号
			}
			if (info.getSuccessCount() == 0) {
				sheet.addCell(new Label(col++, row, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, row, nf.format(info
						.getDisCount() * 100.0 / (info.getSuccessCount()))
						+ "%"));// 订单号
			}
			if (info.getSuccessCount() == 0) {
				sheet.addCell(new Label(col++, row, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, row, nf.format(info
						.getRefundCount() * 100.0 / (info.getSuccessCount()))
						+ "%"));// 订单号
			}
			if (info.getSuccessCount() == 0) {
				sheet.addCell(new Label(col++, row, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, row, nf.format(info
						.getComCount() * 100.0 / (info.getSuccessCount()))
						+ "%"));// 订单号
			}
			if (info.getSuccessCount() == 0) {
				sheet.addCell(new Label(col++, row, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, row, nf.format(info
						.getFakeCount() * 100.0 / (info.getSuccessCount()))
						+ "%"));// 订单号
			}
			sheet.addCell(new Label(col++, row,
					nf.format(info.getTransRate() * 100.0) + "%"));// 订单号
		}
		book.write();
		book.close();
	}

	/**
	 * 订单跟踪异常原因构成
	 * 
	 * @return
	 */
	@RequestMapping(value = "/showTransRecordPer")
	public String showTransRecordPer() {
		List<DisDesc> list = countAnalysisService
				.queryTransRecordDesc(getCriteria());
		getRequest().setAttribute("list", list);
		return "countAnalysis/showTrecordCountDescInfo";
	}

	/**
	 * 交易重跑分析
	 * 
	 * @return
	 */
	@RequestMapping(value = "/transRerunCount")
	public String transRerunCount() {

		Criteria criteria = getCriteria();
		/*
		 * if(null==criteria.getCondition().get("transType")){
		 * criteria.getCondition().put("transType", "sales"); Map<String,
		 * String> par=new HashMap<String, String>(); par.put("transType",
		 * "sales"); getRequest().setAttribute("param", par); }
		 */
		if ("get".equalsIgnoreCase(getRequest().getMethod())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String transDateStart = sdf.format(date);
			String transDateEnd = sdf.format(date);
			criteria.getCondition().put("startDate", transDateStart);
			criteria.getCondition().put("endDate", transDateEnd);
			getRequest().setAttribute("form", criteria.getCondition());
		} else {
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<TransReRunCount> page = countAnalysisService
					.queryTransRerunCountList(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "countAnalysis/transRerunCount";
	}

	@RequestMapping(value = "/exportTransRerunCount")
	public void exportTransRerunCount(HttpServletResponse resp)
			throws Exception {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "failureTransList.xls");
		List<TransReRunCount> list = countAnalysisService
				.queryTransRerunCountListAll(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = null;
		sheet = book.createSheet("交易统计分析", 0);
		String[] headerName = { "重跑通道编号", "重跑通道名称", "重跑笔数", "重跑成功笔数", "重跑成功率",
				"重跑拒付笔数", "重跑拒付率" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			TransReRunCount info = list.get(row - 1);
			sheet.addCell(new Label(col++, row, info.getCurrencyId() + ""));// 交易流水号
			sheet.addCell(new Label(col++, row, info.getCurrencyName() + ""));// 订单号
			sheet.addCell(new Label(col++, row, info.getTransCount() + ""));// 失败笔数
			sheet.addCell(new Label(col++, row, info.getSuccessCount() + ""));// 成功笔数
			if (info.getTransCount() == 0) {
				sheet.addCell(new Label(col++, row, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, row, nf.format(info
						.getSuccessCount() * 100.0 / (info.getTransCount()))
						+ "%"));// 订单号
			}
			sheet.addCell(new Label(col++, row, info.getDisCount() + ""));// 成功笔数
			if (info.getSuccessCount() == 0) {
				sheet.addCell(new Label(col++, row, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, row, nf.format(info
						.getDisCount() * 100.0 / (info.getSuccessCount()))
						+ "%"));// 订单号
			}
		}
		book.write();
		book.close();
	}
	
	/**
	 *订单来源分析统计
	 */
	@RequestMapping(value="/queryTransAnalysisInfoList")
	public String queryTransAnalysisInfoList(){
		HttpServletRequest request = getRequest();
		Criteria criteria = getCriteria();
		if("get".equalsIgnoreCase(request.getMethod())){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			criteria.getCondition().put("startTransDate", sdf.format(new Date()));
			criteria.getCondition().put("endTransDate", sdf.format(new Date()));
		}else{
			PageInfo<TransCountInfo> pageInfo = countAnalysisService.queryTransAnalysisInfoList(criteria);
			request.setAttribute("page", pageInfo);
		}
		request.setAttribute("form", criteria.getCondition());
		return "countAnalysis/transtAnalysisInfoList";
	}
	
	/**
	 * 查找失败原因构成
	 */
	@RequestMapping(value="/queryFaildTransAnalysisInfoList")
	public String queryFaildTransAnalysisInfoList(String merNo, String terNo, String type, String resourceType, String startDate, String endDate){
		Criteria criteria = getCriteria();
//		criteria.getCondition().put("merNo", merNo);
//		criteria.getCondition().put("terNo", terNo);
//		criteria.getCondition().put("type", type);
//		criteria.getCondition().put("resourceType", resourceType);
//		criteria.getCondition().put("startDate", startDate);
//		criteria.getCondition().put("endDate", endDate);
		List<FaildTransAnalysisInfo> list = countAnalysisService.queryFaildTransAnalysisInfoList(criteria);
		getRequest().setAttribute("list", list);
		return "countAnalysis/showFaildTransCountDescInfo";
	}
	
	/**
	 * 导出订单来源分析统计信息
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @throws IOException 
	 */
	@RequestMapping(value="/exportTransAnalysisInfoList")
	public void exportTransAnalysisInfoList(HttpServletResponse response) throws RowsExceededException, WriteException, IOException{
		Criteria criteria = getCriteria();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="
				+ "analysisTransList.xls");
		List<ExportTransCountInfo> list = countAnalysisService.queryExportTransAnalysisInfoList(criteria);
		WritableWorkbook book = Workbook.createWorkbook(response.getOutputStream());
		WritableSheet sheet = null;
		sheet = book.createSheet("交易统计分析", 0);
		String[] headerName = { "商户号", "终端号", "订单来源", "总笔数", "交易完成笔数", "成功笔数", "失败笔数", "失败重复笔数",
				"风险单笔数", "支付转换率", "成功率", "失败原因构成"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			ExportTransCountInfo info = list.get(row - 1);
			sheet.addCell(new Label(col++, row, info.getMerNo()));// 交易流水号
			sheet.addCell(new Label(col++, row, info.getTerNo()));// 交易流水号
			sheet.addCell(new Label(col++, row, info.getResourceType()));// 交易流水号
			sheet.addCell(new Label(col++, row, info.getTotalCount()));// 订单号
			sheet.addCell(new Label(col++, row, info.getTransCount()));// 订单号
			sheet.addCell(new Label(col++, row, info.getTransSuccessCount()));// 成功笔数
			sheet.addCell(new Label(col++, row, info.getTransFailureCount()));// 失败笔数
			sheet.addCell(new Label(col++, row, info.getDuplicateCount()));// 失败笔数
			sheet.addCell(new Label(col++, row, info.getTransRiskCount() + ""));
			if ("0".equals(info.getTotalCount())) {
				sheet.addCell(new Label(col++, row, "0.00%"));
			} else {
				sheet.addCell(new Label(col++, row, nf.format(Integer.parseInt(info.getTransCount())* 100.0/Integer.parseInt(info.getTotalCount()))+"%"));// 订单号
			}
			sheet.addCell(new Label(col++, row, info.getSuccessRate()));
			StringBuffer sb = new StringBuffer();
			if(info.getFaildList()!=null && info.getFaildList().size()>0){
				for(ExportFaildTransAnalysisInfo trans : info.getFaildList()){
					sb.append(trans.getRespCode()).append(",").append(trans.getRespMsg()).append(",")
					.append(trans.getDisCount()).append(",").append(trans.getRate()*100).append("%")
					.append(";");
				}
			}
			sheet.addCell(new Label(col++, row, sb.toString()));
		}
		book.write();
		book.close();
	}
	
	/**
	 * 导出详情
	 */
	@RequestMapping(value="/exportTransDetailInfo")
	public void exportTransDetailInfo(HttpServletResponse resp) throws Exception{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "transList.xls");
		List<TransRecordInfo> list = countAnalysisService.queryTransInfoList(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("订单跟踪列表", 0);
		String[] headerName = { "商户号", "终端号", "流水号", "订单号", "进入系统时间", "异常码",
				"异常原因", "IP", "描述", "协议", "交易金额", "来源网址", "支付提交地址", "返回网址",
				"货物信息", "姓名", "邮箱", "电话", "IP", "前六后四卡号", "收货国家", "收货省/ 州",
				"收货地址", "邮编", "账单国家", "账单省/州", "账单地址" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			TransRecordInfo info = list.get(row - 1);
			TransDetailInfo eInfo = transMgrService.queryTransInfo(info
					.getTradeNo());
			sheet.addCell(new Label(col++, row, info.getMerNo()));// 商户号
			sheet.addCell(new Label(col++, row, info.getTerNo()));// 终端号
			sheet.addCell(new Label(col++, row, info.getTradeNo()));// 交易流水号
			sheet.addCell(new Label(col++, row, info.getOrderNo()));// 商户订单号
			sheet.addCell(new Label(col++, row, info.getEnterTime()));// 进入系统时间
			sheet.addCell(new Label(col++, row, info.getRespCode()));// 异常码
			sheet.addCell(new Label(col++, row, info.getRespMsg()));// 异常原因
			sheet.addCell(new Label(col++, row, info.getClientIP()));// IP
			sheet.addCell(new Label(col++, row, info.getDescription()));// 描述
			sheet.addCell(new Label(col++, row, info.getTransPortProtocol()));// 协议
			sheet.addCell(new Label(col++, row, info.getCurrencyCode() + " "
					+ info.getAmount()));// 交易金额
			sheet.addCell(new Label(col++, row, info.getMerURL()));// 来源网址
			sheet.addCell(new Label(col++, row, info.getSubmitURL()));// 支付提交地址
			sheet.addCell(new Label(col++, row, info.getReturnURL()));// 返回网址
			if (!StringUtils.isEmpty(eInfo.getGoodsInfoByte())) {// 货物信息
				sheet.addCell(new Label(col++, row, new String(eInfo
						.getGoodsInfoByte(), "utf-8")));
				System.out.println("===="
						+ new String(eInfo.getGoodsInfoByte(), "utf-8"));
			} else {
				sheet.addCell(new Label(col++, row, ""));
			}
			sheet.addCell(new Label(col++, row, eInfo.getCardFullName()));// 姓名
			sheet.addCell(new Label(col++, row, eInfo.getEmail()));// 邮箱
			sheet.addCell(new Label(col++, row, eInfo.getCardFullPhone()));// 电话
			sheet.addCell(new Label(col++, row, eInfo.getIpAddress()));// IP
			sheet.addCell(new Label(col++, row, eInfo.getSixAndFourCardNo()));// 前六后四卡号
			sheet.addCell(new Label(col++, row, eInfo.getGrCountry()));// 收货国家
			sheet.addCell(new Label(col++, row, eInfo.getGrState()));// 收货省/ 州
			sheet.addCell(new Label(col++, row, eInfo.getGrAddress()));// 收货地址
			sheet.addCell(new Label(col++, row, eInfo.getGrZipCode()));// 邮编
			sheet.addCell(new Label(col++, row, eInfo.getCardCountry()));// 账单国家
			sheet.addCell(new Label(col++, row, eInfo.getCardState()));// 账单省/州
			sheet.addCell(new Label(col++, row, eInfo.getCardAddress()));// 账单地址
		}
		book.write();
		book.close();
	}
	
	/**
	 * 欧洲通道分析
	 */
	@RequestMapping(value="/queryEuropeChannelInfoList")
	public String queryEuropeChannelInfoList(){
		HttpServletRequest request = getRequest();
		Criteria criteria = getCriteria();
		if("get".equalsIgnoreCase(request.getMethod())){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(new Date());
			criteria.getCondition().put("startDate", date);
			criteria.getCondition().put("endDate", date);
			request.setAttribute("form", criteria.getCondition());
		}else{
			request.setAttribute("form", criteria.getCondition());
			PageInfo<EuropeTransInfo> pageInfo = countAnalysisService.queryEuropeTransInfoList(criteria);
			getRequest().setAttribute("page", pageInfo);
		}
		return "countAnalysis/countEuropeAnalysisInfoList";
	}
	
	/**
	 * 获取欧洲通道返回信息
	 */
	@RequestMapping(value="/queryEuropeMessage")
	public String queryEuropeMessage(String tradeNo){
		Criteria criteria = getCriteria();
		criteria.getCondition().put("tradeNo", tradeNo);
		List<EuropeChannelInfo> list = countAnalysisService.queryEuropeChannelInfoList(criteria);
		getRequest().setAttribute("count", list.size());
		getRequest().setAttribute("list", list);
		return "countAnalysis/EuropeChannelInfo";
	}
	
	/**
	 * 导出信息
	 * @throws Exception 
	 */
	@RequestMapping(value="exportEuropeChannelInfo")
	public void exportEuropeChannelInfo(HttpServletResponse resp) throws Exception{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "euripeTransList.xls");
		List<ExportEuropeInfo> list = countAnalysisService.queryExportEuropeTransInfoList(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = null;
		sheet = book.createSheet("欧洲通道交易统计分析", 0);
		String[] headerName = {"商户号","流水号","订单号","交易时间","通道名称","交易金额","交易币种",
				  "结算金额","结算币种","银行交易金额","银行交易币种","手续费","单笔手续费","手续费币种","保证金",
				  "保证金币种","卡种","交易状态","交易返回信息","欺诈分数","拒付状态","拒付金额","退款状态",
				  "退款金额","冻结状态","冻结金额","订单来源","所属终端号","通道英文账单名称","前六后四卡号",
				  "网站","货物信息","姓名","邮箱","电话","IP","支付国家","收货国家","收货省/ 州",
				  "收货地址","邮编","货运方式","货运单号","账单国家","账单省/州","账单地址","z2通道信息",
				  "z5通道信息","z6通道信息","z14通道信息"};
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			ExportEuropeInfo info = list.get(row - 1);
			sheet.addCell(new Label(col++, row, info.getMerNo()));
			sheet.addCell(new Label(col++, row, info.getTradeNo()));
			sheet.addCell(new Label(col++, row, info.getOrderNo()));
			sheet.addCell(new Label(col++, row, info.getTransDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getTransDate()):""));
			sheet.addCell(new Label(col++, row, info.getCurrencyName()));
			sheet.addCell(new Label(col++, row, info.getMerTransAmount().doubleValue()+""));
			sheet.addCell(new Label(col++, row, info.getMerBusCurrency()+""));
			sheet.addCell(new Label(col++, row, info.getMerSettleAmount().doubleValue()+""));
			sheet.addCell(new Label(col++, row, info.getMerSettleCurrency()+""));
			sheet.addCell(new Label(col++, row, info.getBankTransAmount().doubleValue()+""));
			sheet.addCell(new Label(col++, row, info.getBankCurrency()+""));
			sheet.addCell(new Label(col++, row, info.getMerForAmount().doubleValue()+""));
			sheet.addCell(new Label(col++, row, info.getSingleFee().doubleValue()+""));
			sheet.addCell(new Label(col++, row, info.getMerSettleCurrency()+""));
			sheet.addCell(new Label(col++, row, info.getBondAmount().doubleValue()+""));
			sheet.addCell(new Label(col++, row, info.getMerSettleCurrency()+""));
			sheet.addCell(new Label(col++, row, info.getCardType()+""));
			sheet.addCell(new Label(col++, row, "00".equals(info.getRespCode())?"支付成功":"支付失败"+""));
			sheet.addCell(new Label(col++, row, info.getRespMsg()+""));
			sheet.addCell(new Label(col++, row, info.getRiskScore()+""));
			sheet.addCell(new Label(col++, row, "1".equals(info.getDishonorStatus())?"已拒付":"未拒付"+""));
			sheet.addCell(new Label(col++, row, info.getMerBusCurrency()+" "+ info.getDishonorAmount()+""));
			sheet.addCell(new Label(col++, row, "1".equals(info.getRefundStatus())?"已退款":"未退款"+""));
			sheet.addCell(new Label(col++, row, info.getMerBusCurrency()+" "+ info.getRefundAmount()));
			sheet.addCell(new Label(col++, row, "1".equals(info.getFrozenStatus())?"已冻结":"未冻结"));
			sheet.addCell(new Label(col++, row, info.getMerBusCurrency()+" "+ info.getFrozenAmount()));
			//sheet.addCell( new Label(col++, row, "无伪冒状态"));//交易返回信息
			sheet.addCell(new Label(col++, row, Tools.parseWebInfoToResourceType(info.getWebInfo())));
			sheet.addCell(new Label(col++, row, info.getTerNo()));
			sheet.addCell(new Label(col++, row, info.getAcquirer()));
			if(info.getCheckNo()!=null&&!"".equals(info.getCheckNo())){
				sheet.addCell(new Label(col++, row, ((info.getCheckNo()!=null && !("".equals(info.getCheckNo())))?Funcs.decrypt(info.getCheckNo()):"")
						+"****"+((info.getLast()!=null && !("".equals(info.getLast()))?Funcs.decrypt(info.getLast()):""))));
			}else{
				sheet.addCell(new Label(col++, row, ""));
			}
			sheet.addCell(new Label(col++, row, info.getPayWebSite()));
			if(null!=info.getGoodsInfo()){
				sheet.addCell(new Label(col++, row, new String(info.getGoodsInfo(),"utf-8")));
			}else{
				sheet.addCell(new Label(col++, row, ""));
			}
			sheet.addCell(new Label(col++, row, info.getGrPerName()));
			sheet.addCell(new Label(col++, row, info.getEmail()));
			sheet.addCell(new Label(col++, row, info.getGrphoneNumber()));
			sheet.addCell(new Label(col++, row, info.getIpAddress()));
			sheet.addCell(new Label(col++, row, info.getIpCountry()));
			sheet.addCell(new Label(col++, row, info.getGrCountry()));
			sheet.addCell(new Label(col++, row, info.getGrState()));
			sheet.addCell(new Label(col++, row, info.getGrAddress()));
			sheet.addCell(new Label(col++, row, info.getGrZipCode()));
			sheet.addCell(new Label(col++, row, info.getIogistics()));
			sheet.addCell(new Label(col++, row, info.getTrackNo()));
			sheet.addCell(new Label(col++, row, info.getCardCountry()));
			sheet.addCell(new Label(col++, row, info.getCardState()));
			sheet.addCell(new Label(col++, row, info.getCardAddress()));
			if(info.getAgentNo()!=null && !("".equals(info.getAgentNo()))){
				String[] code = info.getAgentNo().split("&");
				if(code!=null && code.length>0){
					for(int i=0; i<code.length; i++){
						if(code[i]!=null && !("".equals(code[i]))){
							String[] result = code[i].split("=");
							if(result.length==2){
								if("z14".equals(result[0].toLowerCase())){
									Map<String, String> map = InitEuropeInfo.getZ14ResultMap();
									info.setZ14Name(result[0]);
									info.setZ14Code(result[1]);
									info.setZ14Description(map.get(result[1]));
								}
								if("z2".equals(result[0].toLowerCase())){
									Map<String, String> map = InitEuropeInfo.getZ2ResultMap();
									info.setZ2Name(result[0]);
									info.setZ2Code(result[1]);
									String key = result[1]!=null?(result[1].length()>1?result[1].replaceAll("^0", ""):result[1]):"";
									info.setZ2Description(map.get(key));
								}
								if("z6".equals(result[0].toLowerCase())){
									Map<String, String> map = InitEuropeInfo.getZ6ResultMap();
									info.setZ6Name(result[0]);
									info.setZ6Code(result[1]);
									info.setZ6Description(map.get(result[1]));
								}
								if("z5".equals(result[0].toLowerCase())){
									info.setZ5Name(result[0]);
									info.setZ5Code(result[1]);
									info.setZ5Description(result[1]);
								}
							}
						}
					}
				}
			}
			sheet.addCell(new Label(col++, row, ((info.getZ2Code()!=null?info.getZ2Code():"")+" : "
					+(info.getZ2Description()!=null?info.getZ2Description():""))));
			sheet.addCell(new Label(col++, row, (info.getZ5Description()!=null?info.getZ5Description():"")));
			sheet.addCell(new Label(col++, row, ((info.getZ6Code()!=null?info.getZ6Code():"")+" : "
					+(info.getZ6Description()!=null?info.getZ6Description():""))));
			sheet.addCell(new Label(col++, row, ((info.getZ14Code()!=null?info.getZ14Code():"")+" : "
					+(info.getZ14Description()!=null?info.getZ14Description():""))));
		}
		book.write();
		book.close();
	}
	
	
	/** 时间段交易趋势 */
	@RequestMapping(value = "/queryTransHourCount")
	public String queryTransHourCount() {
		
		
		Criteria criteria = getCriteria();
		if ("get".equalsIgnoreCase(getRequest().getMethod())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String transDateStart = sdf.format(date);
			criteria.getCondition().put("startDate", transDateStart);
			criteria.getCondition().put("endDate", transDateStart);
			getRequest().setAttribute("form", criteria.getCondition());
		} else {
			getRequest().setAttribute("form", criteria.getCondition());
			List<TransHourCount> page = countAnalysisService
					.queryTransHourCount(getCriteria());
//			PageInfo<TransHourCount> page=new PageInfo<TransHourCount>();
//			page.setNowPage(1);
//			page.setNumPerPage(list.size());
//			page.setTotal(list.size());
//			page.setData(list);
//			page.setTotalPage(1);
//			page.setOffset(250);
			getRequest().setAttribute("page", page);
		}
		return "countAnalysis/queryTransHourCount";
	}
	
	@ResponseBody
	@RequestMapping("/queryTransHourCountForPic")
	public List<TransHourCount> queryTransHourCountForPic(){
		Criteria criteria = getCriteria();
		criteria.getCondition().put("currency", null);
		return countAnalysisService
				.queryTransHourCount(criteria);
	}
	
	/** 交易占比或者交易拒付趋势 */
	@RequestMapping(value = "/queryTransDisPercent")
	public String queryTransDisPercent() {
		Criteria criteria = getCriteria();
		if ("get".equalsIgnoreCase(getRequest().getMethod())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String transDateStart = sdf.format(date);
			criteria.getCondition().put("dateStart", transDateStart);
			criteria.getCondition().put("dateEnd", transDateStart);
			getRequest().setAttribute("form", criteria.getCondition());
		} else {
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<TransOrDisPercent> page = countAnalysisService
					.queryTransDisPercent(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "countAnalysis/queryTransDisPercent";
	}
	
	
	/**
	 * 导出交易拒付趋势
	 */
	@RequestMapping(value="/exportTransDisPercent")
	public void exportTransDisPercent(HttpServletResponse resp) throws Exception{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "transList.xls");
		List<TransOrDisPercent> list = countAnalysisService.queryTransOrDisPercentList(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("交易拒付趋势", 0);
		String[] headerName = { "时间段", "成功笔数", "拒付总笔数", "当月笔数","当月占比"
				,"第一月笔数","第一月占比"
				,"第二月笔数","第二月占比"
				,"第三月笔数","第三月占比"
				,"第四月笔数","第四月占比"
				,"第五月笔数","第五月占比"
				,"第六月笔数","第六月占比"
				,"第七月笔数","第七月占比"
				,"第八月笔数","第八月占比"
				,"第九月笔数","第九月占比"
				,"第十月笔数","第十月占比"
				,"第十一月笔数","第十一月占比"
				,"第十二月笔数","第十二月占比"
				,"其他月笔数","其他月月占比"
				};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		NumberFormat nf=NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			TransOrDisPercent info = list.get(row - 1);
			sheet.addCell(new Label(col++, row, info.getTransDate()));// 商户号
			sheet.addCell(new Label(col++, row, info.getSuccessCount()));// 终端号
			sheet.addCell(new Label(col++, row, info.getTotalDisCount()));// 交易流水号
			sheet.addCell(new Label(col++, row, info.getMonth_0()));// 商户订单号
			if(Double.parseDouble(info.getTotalDisCount())==0){
				sheet.addCell(new Label(col++, row, "0.00%"));// 商户订单号
			}else{
				sheet.addCell(new Label(col++, row, nf.format(Double.parseDouble(info.getMonth_0())/Double.parseDouble(info.getTotalDisCount())*100)+"%"));// 商户订单号
			}
			sheet.addCell(new Label(col++, row, info.getMonth_1()));// 商户订单号
			if(Double.parseDouble(info.getTotalDisCount())==0){
				sheet.addCell(new Label(col++, row, "0.00%"));// 商户订单号
			}else{
				sheet.addCell(new Label(col++, row, nf.format(Double.parseDouble(info.getMonth_1())/Double.parseDouble(info.getTotalDisCount())*100)+"%"));// 商户订单号
			}
			sheet.addCell(new Label(col++, row, info.getMonth_2()));// 商户订单号
			if(Double.parseDouble(info.getTotalDisCount())==0){
				sheet.addCell(new Label(col++, row, "0.00%"));// 商户订单号
			}else{
				sheet.addCell(new Label(col++, row, nf.format(Double.parseDouble(info.getMonth_2())/Double.parseDouble(info.getTotalDisCount())*100)+"%"));// 商户订单号
			}
			sheet.addCell(new Label(col++, row, info.getMonth_3()));// 商户订单号
			if(Double.parseDouble(info.getTotalDisCount())==0){
				sheet.addCell(new Label(col++, row, "0.00%"));// 商户订单号
			}else{
				sheet.addCell(new Label(col++, row, nf.format(Double.parseDouble(info.getMonth_3())/Double.parseDouble(info.getTotalDisCount())*100)+"%"));// 商户订单号
			}
			sheet.addCell(new Label(col++, row, info.getMonth_4()));// 商户订单号
			if(Double.parseDouble(info.getTotalDisCount())==0){
				sheet.addCell(new Label(col++, row, "0.00%"));// 商户订单号
			}else{
				sheet.addCell(new Label(col++, row, nf.format(Double.parseDouble(info.getMonth_4())/Double.parseDouble(info.getTotalDisCount())*100)+"%"));// 商户订单号
			}
			sheet.addCell(new Label(col++, row, info.getMonth_5()));// 商户订单号
			if(Double.parseDouble(info.getTotalDisCount())==0){
				sheet.addCell(new Label(col++, row, "0.00%"));// 商户订单号
			}else{
				sheet.addCell(new Label(col++, row, nf.format(Double.parseDouble(info.getMonth_5())/Double.parseDouble(info.getTotalDisCount())*100)+"%"));// 商户订单号
			}
			sheet.addCell(new Label(col++, row, info.getMonth_6()));// 商户订单号
			if(Double.parseDouble(info.getTotalDisCount())==0){
				sheet.addCell(new Label(col++, row, "0.00%"));// 商户订单号
			}else{
				sheet.addCell(new Label(col++, row, nf.format(Double.parseDouble(info.getMonth_6())/Double.parseDouble(info.getTotalDisCount())*100)+"%"));// 商户订单号
			}
			sheet.addCell(new Label(col++, row, info.getMonth_7()));// 商户订单号
			if(Double.parseDouble(info.getTotalDisCount())==0){
				sheet.addCell(new Label(col++, row, "0.00%"));// 商户订单号
			}else{
				sheet.addCell(new Label(col++, row, nf.format(Double.parseDouble(info.getMonth_7())/Double.parseDouble(info.getTotalDisCount())*100)+"%"));// 商户订单号
			}
			sheet.addCell(new Label(col++, row, info.getMonth_8()));// 商户订单号
			if(Double.parseDouble(info.getTotalDisCount())==0){
				sheet.addCell(new Label(col++, row, "0.00%"));// 商户订单号
			}else{
				sheet.addCell(new Label(col++, row, nf.format(Double.parseDouble(info.getMonth_8())/Double.parseDouble(info.getTotalDisCount())*100)+"%"));// 商户订单号
			}
			sheet.addCell(new Label(col++, row, info.getMonth_9()));// 商户订单号
			if(Double.parseDouble(info.getTotalDisCount())==0){
				sheet.addCell(new Label(col++, row, "0.00%"));// 商户订单号
			}else{
				sheet.addCell(new Label(col++, row, nf.format(Double.parseDouble(info.getMonth_9())/Double.parseDouble(info.getTotalDisCount())*100)+"%"));// 商户订单号
			}
			sheet.addCell(new Label(col++, row, info.getMonth_10()));// 商户订单号
			if(Double.parseDouble(info.getTotalDisCount())==0){
				sheet.addCell(new Label(col++, row, "0.00%"));// 商户订单号
			}else{
				sheet.addCell(new Label(col++, row, nf.format(Double.parseDouble(info.getMonth_10())/Double.parseDouble(info.getTotalDisCount())*100)+"%"));// 商户订单号
			}
			sheet.addCell(new Label(col++, row, info.getMonth_11()));// 商户订单号
			if(Double.parseDouble(info.getTotalDisCount())==0){
				sheet.addCell(new Label(col++, row, "0.00%"));// 商户订单号
			}else{
				sheet.addCell(new Label(col++, row, nf.format(Double.parseDouble(info.getMonth_11())/Double.parseDouble(info.getTotalDisCount())*100)+"%"));// 商户订单号
			}
			sheet.addCell(new Label(col++, row, info.getMonth_12()));// 商户订单号
			if(Double.parseDouble(info.getTotalDisCount())==0){
				sheet.addCell(new Label(col++, row, "0.00%"));// 商户订单号
			}else{
				sheet.addCell(new Label(col++, row, nf.format(Double.parseDouble(info.getMonth_12())/Double.parseDouble(info.getTotalDisCount())*100)+"%"));// 商户订单号
			}
			sheet.addCell(new Label(col++, row, info.getMonth_other()));// 商户订单号
			if(Double.parseDouble(info.getTotalDisCount())==0){
				sheet.addCell(new Label(col++, row, "0.00%"));// 商户订单号
			}else{
				sheet.addCell(new Label(col++, row, nf.format(Double.parseDouble(info.getMonth_other())/Double.parseDouble(info.getTotalDisCount())*100)+"%"));// 商户订单号
			}
		}
		book.write();
		book.close();
	}
}
