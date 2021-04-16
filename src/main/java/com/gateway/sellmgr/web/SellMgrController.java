package com.gateway.sellmgr.web;

import java.awt.Font;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.BaseDataListener;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excel.BIRow;
import com.gateway.common.excel.BISheet;
import com.gateway.common.excel.BIWorkbook;
import com.gateway.common.excetion.ServiceException;
import com.gateway.common.utils.Funcs;
import com.gateway.common.utils.SHA256Utils;
import com.gateway.common.utils.Tools;
import com.gateway.complaint.model.Complaint;
import com.gateway.countAnalysis.model.CountAnalysis;
import com.gateway.countAnalysis.model.CurrencyDisCount;
import com.gateway.countAnalysis.model.DisDesc;
import com.gateway.countAnalysis.model.RiskPercent;
import com.gateway.countAnalysis.model.TransHourCount;
import com.gateway.countAnalysis.model.TransRecord;
import com.gateway.countAnalysis.service.CountAnalysisService;
import com.gateway.faffmgr.model.SalesPerformanceInfo;
import com.gateway.faffmgr.service.FaffService;
import com.gateway.form.model.MerchantReportFormsInfo;
import com.gateway.form.model.TransAmountCountInfo;
import com.gateway.form.model.TransTimeCountInfo;
import com.gateway.form.service.FormMgrService;
import com.gateway.loginmgr.model.UserInfo;
import com.gateway.merchantmgr.model.MerchantInfo;
import com.gateway.merchantmgr.model.TransSettingInfo;
import com.gateway.merchantmgr.service.MerchantMgrService;
import com.gateway.riskmgr.model.ExportRiskTransInfo;
import com.gateway.riskmgr.model.RiskTransInfo;
import com.gateway.riskmgr.service.RiskMgrService;
import com.gateway.rpt.util.ExcelTool;
import com.gateway.sellmgr.model.AgentRelSellInfo;
import com.gateway.sellmgr.model.MerchantCapitalInfo;
import com.gateway.sellmgr.model.MerchantSettleInfo;
import com.gateway.sellmgr.model.SellRefMerNo;
import com.gateway.sellmgr.model.SellRefSellsInfo;
import com.gateway.sellmgr.service.SellMgrService;
import com.gateway.settlemgr.model.DeductionTypeInfo;
import com.gateway.settlemgr.service.SettleMgrService;
import com.gateway.suspicious.model.SuspiciousOrderListInfo;
import com.gateway.suspicious.service.SuspiciousManageService;
import com.gateway.sysmgr.model.CardBinInfo;
import com.gateway.transmgr.model.TransCount;
import com.gateway.transmgr.model.TransDetailInfo;
import com.gateway.transmgr.model.TransInfo;
import com.gateway.transmgr.model.TransRecordInfo;
import com.gateway.transmgr.service.TransMgrService;

@Controller
@RequestMapping("/sellmgr")
public class SellMgrController extends BaseController {
	@Autowired
	private SellMgrService sellMgrService;
	
	@Autowired
	private FormMgrService formMgrService;
	
	@Autowired
	private TransMgrService transMgrService;

	@Autowired
	private RiskMgrService riskMgrService;
	
	@Autowired
	private SettleMgrService settleMgrService;
	
	@Autowired
	private CountAnalysisService countAnalysisService;
	
	
	@Autowired
	private SuspiciousManageService suspiciousManageService;
	
	@Autowired
	private MerchantMgrService merchantMgrService;
	
	@Autowired
	private FaffService faffService;
	/**
	 * 交易查询
	 * @return
	 */
	@RequestMapping(value="/listTransInfo")
	public String getTransList(){
		Criteria criteria=getCriteria();
        /*if(null==criteria.getCondition().get("transType")){
        	criteria.getCondition().put("transType", "sales");
        	Map<String, String> par=new HashMap<String, String>();
        	par.put("transType", "sales");
        	getRequest().setAttribute("param", par);
        }*/
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
			criteria.getCondition().put("isSell", "yes");
			PageInfo<TransInfo> page = sellMgrService.getTransList(criteria);
			if(null!=criteria.getCondition().get("transDateEnd")&&!"".equals(criteria.getCondition().get("transDateEnd").toString())){
				criteria.getCondition().put("transDateEnd", criteria.getCondition().get("transDateEnd").toString()+" 23:59:59.999");
			}
			TransCount transCount=transMgrService.queryTransCount(criteria);
			for(TransInfo info:page.getData()){
				try {
					info.setCbInfo(transMgrService.queryCardBinInfoByBin(Funcs.decrypt(info.getCheckNo())));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			getRequest().setAttribute("page", page);
			getRequest().setAttribute("transCount", transCount);
		}
		return "sellmgr/transList";
	}
	
	/**
	 * 根据流水号查询交易信息
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 */
	@RequestMapping(value="/queryTransByTradeNo")
	public String queryTransByTradeNo(String tradeNo) throws IOException, Exception{
		TransInfo transInfo=sellMgrService.queryTransInfoByTradeNo(tradeNo);
		if(transInfo.getCheckNo()!=null&&!"".equals(transInfo.getCheckNo())){
		transInfo.setSixAndFourCardNo(Funcs.decrypt(transInfo.getCheckNo())+"***"+Funcs.decrypt(transInfo.getLast()));
		}
		CardBinInfo cbInfo=transMgrService.queryCardBinInfoByBin(Funcs.decrypt(transInfo.getCheckNo()));
		getRequest().setAttribute("cbInfo", cbInfo);
		transInfo.setWebInfo( Tools.parseWebInfoToResourceType(transInfo.getWebInfo()));
		getRequest().setAttribute("transInfo", transInfo);
		return "sellmgr/transDetail";
	}
	
	/** 查询交易统计 */
	@RequestMapping(value="/queryCountAnalysisInfo")
	public String queryCountAnalysisInfo(){
		Criteria criteria=getCriteria();
        /*if(null==criteria.getCondition().get("transType")){
        	criteria.getCondition().put("transType", "sales");
        	Map<String, String> par=new HashMap<String, String>();
        	par.put("transType", "sales");
        	getRequest().setAttribute("param", par);
        }*/
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String transDateStart=sdf.format(date);
			String transDateEnd=sdf.format(date);
			criteria.getCondition().put("startDate", transDateStart);
			criteria.getCondition().put("endDate", transDateEnd);
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<CountAnalysis> page=sellMgrService.queryCountAnalysisInfo(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "sellmgr/countAnalysisInfoList";
	}
	@RequestMapping(value="/exportCountAnalysisInfo")
	public void exportCountAnalysisInfo(HttpServletResponse resp) throws Exception{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "failureTransList.xls" ); 
		List<CountAnalysis> list = sellMgrService.queryCountAnalysisInfoAll(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = null;
		sheet = book.createSheet("交易统计分析", 0);
		String[] headerName = {"商户号","终端号","总笔数","交易完成笔数","交易金额",
				 "成功笔数","成功金额","失败笔数","失败重复笔数","风险单笔数","拒付笔数","退单笔数",
			"投诉笔数","支付转换率","成功率","拒付率","退单率","投诉率"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		NumberFormat nf=NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			CountAnalysis info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getMerNo()));//交易流水号
			sheet.addCell( new Label(col++, row, info.getTerNo()));//交易流水号
			sheet.addCell( new Label(col++, row, info.getTotalCount()));//订单号
			sheet.addCell( new Label(col++, row, info.getTransCount()));//订单号
			sheet.addCell( new Label(col++, row, info.getTransCurrency()+" "+info.getTransAmount()));//订单号
			sheet.addCell( new Label(col++, row, info.getTransSuccessCount()));//成功笔数
			sheet.addCell( new Label(col++, row, info.getTransCurrency()+" "+info.getTransSuccessAmount()));//订单号
			sheet.addCell( new Label(col++, row, info.getTransFailureCount()));//失败笔数
			sheet.addCell( new Label(col++, row, info.getDuplicateCount()));//失败笔数
			sheet.addCell( new Label(col++, row, info.getTransRiskCount()+""));
			sheet.addCell( new Label(col++, row, info.getDishonorCount()));
			sheet.addCell( new Label(col++, row, info.getRefundCount()));
			sheet.addCell( new Label(col++, row, info.getComplaintCount()));
			if("0".equals(info.getTotalCount())){
				sheet.addCell( new Label(col++, row, "0.00%"));
			}else{
				sheet.addCell( new Label(col++, row, nf.format(Integer.parseInt(info.getTransCount())*100.0/Integer.parseInt(info.getTotalCount()))+"%"));//订单号
			}
			sheet.addCell( new Label(col++, row, info.getSuccessRate()));
			sheet.addCell( new Label(col++, row, info.getDishonorRate()));
			sheet.addCell( new Label(col++, row, info.getRefundRate()));
			sheet.addCell( new Label(col++, row, info.getComplaintRate()));
		}
		book.write();
		book.close();
	}
	/** 失败订单分析 
	 * @throws ServiceException */
	@RequestMapping(value="/failureList")
	public String failureList() throws ServiceException{
		Criteria criteria=getCriteria();
        /*if(null==criteria.getCondition().get("transType")){
        	criteria.getCondition().put("transType", "sales");
        	Map<String, String> par=new HashMap<String, String>();
        	par.put("transType", "sales");
        	getRequest().setAttribute("param", par);
        }*/
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String transDateStart=sdf.format(date);
			String transDateEnd=sdf.format(date);
			criteria.getCondition().put("startDate", transDateStart);
			criteria.getCondition().put("endDate", transDateEnd);
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			if(criteria.getCondition().get("merNo")==null||criteria.getCondition().get("merNo").toString().trim().equals("")){
				throw new ServiceException("商户号不能为空");
			}
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<CountAnalysis> page=sellMgrService.queryFailureList(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "sellmgr/failureList";
	}
	/** 失败订单分析 
	 * @throws ServiceException */
	@ResponseBody
	@RequestMapping(value="/failureListForPic")
	public HashMap<String, Object> failureListForPic() throws ServiceException{
		HashMap<String, Object> map=new HashMap<String, Object>();
		Vector<Vector<String>> vv=new Vector<Vector<String>>();
		Criteria criteria=getCriteria();
        /*if(null==criteria.getCondition().get("transType")){
        	criteria.getCondition().put("transType", "sales");
        	Map<String, String> par=new HashMap<String, String>();
        	par.put("transType", "sales");
        	getRequest().setAttribute("param", par);
        }*/
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String transDateStart=sdf.format(date);
			String transDateEnd=sdf.format(date);
			criteria.getCondition().put("startDate", transDateStart);
			criteria.getCondition().put("endDate", transDateEnd);
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			if(criteria.getCondition().get("merNo")==null||criteria.getCondition().get("merNo").toString().trim().equals("")){
				throw new ServiceException("商户号不能为空");
			}
			getRequest().setAttribute("form", criteria.getCondition());
			List<CountAnalysis> page=sellMgrService.queryFailureListAll(getCriteria());
			for(CountAnalysis info:page){
				Vector<String> v=new Vector<String>();
				v.add(info.getRespMsg());
				v.add(info.getCountRespMsg());
				vv.add(v);
			}
			map.put("aaData", vv);
		}
		return map;
	}
	
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
			PageInfo<RiskTransInfo> page=sellMgrService.queryRiskTransInfo(criteria);
			getRequest().setAttribute("page", page);
		}
		return "sellmgr/listRiskTransInfo";
	}
	
	@RequestMapping(value="/exportRiskTransInfo")
	public void exportRiskTransInfo(HttpServletResponse resp) throws Exception{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "transList.xls" ); 
		Criteria criteria=getCriteria();
//		criteria.getCondition().put("doStatus", "reject");
		List<ExportRiskTransInfo> list = sellMgrService.exportRiskTransList(criteria);
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
	
	@RequestMapping(value="/listSellRefMerNo")
	public String listSellRefMerNo(){
		PageInfo<SellRefMerNo> page=sellMgrService.querySellRefMerNo(getCriteria());
		getRequest().setAttribute("page", page);
		return "sellmgr/listSellRefMerNo";
	}
	
	@RequestMapping(value="/goAddSellRefMerNo")
	public String goAddSellRefMerNo(){
		List<UserInfo> users=sellMgrService.queryAllUsersInfo();
		getRequest().setAttribute("users", users);
		return "sellmgr/addSellRefMerNo";
	}
	
	@RequestMapping(value="/addSellRefMerNo")
	public ModelAndView addSellRefMerNo(SellRefMerNo info){
//		if(info.getMerNos()==null){
//			return ajaxDoneError("请选择商户号");
//		}
		int count=sellMgrService.checkUserNameDuplicate(info);
		if(count>0){
			return ajaxDoneError("用户重复添加");
		}
//		String merNostr="";
//		for(String merNo:info.getMerNos()){
//			merNostr+=merNo+",";
//		}
//		if(merNostr.endsWith(",")){
//			merNostr=merNostr.substring(0, merNostr.length()-1);
//		}
//		info.setMerNo(merNostr);
		info.setCreateBy(getLogAccount().getRealName());
		int i=sellMgrService.addSellRefMerNo(info);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	@RequestMapping(value="/goUpdateSellRefMerNo")
	public String goUpdateSellRefMerNo(String id){
		List<UserInfo> users=sellMgrService.queryAllUsersInfo();
		getRequest().setAttribute("users", users);
		SellRefMerNo info=sellMgrService.querySellMgrServiceById(id);
		getRequest().setAttribute("info", info);
		return "sellmgr/updateSellRefMerNo";
	}
	
	@RequestMapping(value="/updateSellRefMerNo")
	public ModelAndView updateSellRefMerNo(SellRefMerNo info){
		int count=sellMgrService.checkUserNameDuplicate(info);
		if(count>0){
			return ajaxDoneError("用户重复");
		}
		info.setLastModifyBy(getLogAccount().getRealName());
		int i = sellMgrService.updateSellRefMerNo(info);
		if(i>0){
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	
	@RequestMapping(value="/deleteSellRefMerNoByIds")
	public ModelAndView deleteSellRefMerNoByIds(String[] ids){
		int i=sellMgrService.deleteSellRefMerNoByIds(ids);
		if(i>0){
			return ajaxDoneSuccess("删除成功");
		}else{
			return ajaxDoneError("删除失败");
		}
	}
	
	@RequestMapping(value="/showMerNos")
	public String showMerNos(){
		return "sellmgr/showMerNos";
	}
	
	/**
	 * 订单跟踪
	 * 
	 * */
	@RequestMapping(value="/getTransRecordList")
	public String getTransRecordList(){
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String transDateStart=sdf1.format(date);
			criteria.getCondition().put("transDateStart", transDateStart);
			criteria.getCondition().put("transDateEnd", transDateStart);
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<TransRecordInfo> page = sellMgrService.getTransRecordList(criteria);
			getRequest().setAttribute("page", page);
		}
		return "sellmgr/transRecordList";
	}
	
	/** 导出订单跟踪 */
	@RequestMapping(value="/uploadTransRecordFile")
	public void uploadTransRecordFile(HttpServletResponse resp) throws Exception{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "transRecordList.xls" );
		List<TransRecordInfo> list = sellMgrService.queryTransRecordInfo(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("订单跟踪列表", 0);
		String[] headerName = { "商户号","终端号","流水号","订单号","进入系统时间","异常码","异常原因","IP","描述","协议","交易金额","来源网址","支付提交地址","返回网址","货物信息",
								"姓名","邮箱","电话","IP","前六后四卡号","收货国家","收货省/ 州","收货地址","邮编","账单国家","账单省/州","账单地址"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			TransRecordInfo info = list.get(row-1);
			TransDetailInfo eInfo = transMgrService.queryTransInfo(info.getTradeNo());
			sheet.addCell( new Label(col++, row, info.getMerNo()));//商户号
			sheet.addCell( new Label(col++, row, info.getTerNo()));//终端号
			sheet.addCell( new Label(col++, row, info.getTradeNo()));//交易流水号
			sheet.addCell( new Label(col++, row, info.getOrderNo()));//商户订单号
			sheet.addCell( new Label(col++, row, info.getEnterTime()));//进入系统时间
			sheet.addCell( new Label(col++, row, info.getRespCode()));//异常码
			sheet.addCell( new Label(col++, row, info.getRespMsg()));//异常原因
			sheet.addCell( new Label(col++, row, info.getClientIP()));//IP
			sheet.addCell( new Label(col++, row, info.getDescription()));//描述
			sheet.addCell( new Label(col++, row, info.getTransPortProtocol()));//协议
			sheet.addCell( new Label(col++, row, info.getCurrencyCode() +" " + info.getAmount()));//交易金额
			sheet.addCell( new Label(col++, row, info.getMerURL()));//来源网址
			sheet.addCell( new Label(col++, row, info.getSubmitURL()));//支付提交地址
			sheet.addCell( new Label(col++, row, info.getReturnURL()));//返回网址
			if(!StringUtils.isEmpty(eInfo.getGoodsInfoByte())){//货物信息
				sheet.addCell(new Label(col++, row, new String(eInfo.getGoodsInfoByte(),"utf-8")));
				System.out.println("===="+new String(eInfo.getGoodsInfoByte(),"utf-8"));
			}else{
				sheet.addCell(new Label(col++, row, ""));
			}
			sheet.addCell( new Label(col++, row, eInfo.getCardFullName()));//姓名
			sheet.addCell( new Label(col++, row, eInfo.getEmail()));//邮箱
			sheet.addCell( new Label(col++, row, eInfo.getCardFullPhone()));//电话
			sheet.addCell( new Label(col++, row, eInfo.getIpAddress()));//IP
			sheet.addCell( new Label(col++, row, eInfo.getSixAndFourCardNo()));//前六后四卡号
			sheet.addCell( new Label(col++, row, eInfo.getGrCountry()));//收货国家
			sheet.addCell( new Label(col++, row, eInfo.getGrState()));//收货省/ 州
			sheet.addCell( new Label(col++, row, eInfo.getGrAddress()));//收货地址
			sheet.addCell( new Label(col++, row, eInfo.getGrZipCode()));//邮编
			sheet.addCell( new Label(col++, row, eInfo.getCardCountry()));//账单国家
			sheet.addCell( new Label(col++, row, eInfo.getCardState()));//账单省/州
			sheet.addCell( new Label(col++, row, eInfo.getCardAddress()));//账单地址
		}
		book.write();
		book.close();
	}
	
	/**
	 * 商户报表下载
	 * 
	 * @throws ServiceException
	 */
	@RequestMapping(value="/listMerchantReportForms")
	public String listMerchantReportForms() throws ServiceException {
		Criteria criteria = getCriteria();
		if ("get".equalsIgnoreCase(getRequest().getMethod())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String transDateStart = sdf.format(date);
			criteria.getCondition().put("formDate", transDateStart); 
			getRequest().setAttribute("form", criteria.getCondition());
		} else {
			if (criteria.getCondition().get("formType") == null
					|| "".equals(criteria.getCondition().get("formType"))) {
				throw new ServiceException("报表类型不能为空");
			}
			if (criteria.getCondition().get("formDate") == null
					|| "".equals(criteria.getCondition().get("formDate"))) {
				throw new ServiceException("报表时间不能为空");
			}
			criteria.getCondition().put("sellType", 1);
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<MerchantReportFormsInfo> page = formMgrService
					.queryMerchantReportForms(criteria);
			getRequest().setAttribute("page", page);
		}
		return "sellmgr/listMerchantReportForms";
	}
	
	/**
	 * 导出报表数据
	 * @param resp
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value="/exportMerchantReportForms")
	public void exportMerchantReportForms(HttpServletResponse resp,MerchantReportFormsInfo form) throws Exception {
//		resp.setContentType("application/vnd.ms-excel");
		String fileName="";
		if(form.getFormType()==1){
			fileName=form.getMerNo()+"商户交易月报"+form.getStartDate().substring(0, 7);
		}else if(form.getFormType()==2){
			fileName=form.getMerNo()+"商户交易周报"+form.getStartDate().replaceAll("-", "")+"-"+form.getEndDate().replaceAll("-", "");
		}else{
			fileName=form.getMerNo()+"商户交易日报"+form.getStartDate();
		}
//		resp.setHeader("Content-disposition", "attachment;filename="
//				+ fileName+".xls");
		if(form.getFormType()==1){//生成月报表
			this.createMonthForm(resp, form,fileName);
		}else if(form.getFormType()==2){//生成周报表
			this.createWeekForm(resp, form,fileName);
		}else{//生成日报表
			this.createDayForm(resp, form,fileName);
		}
	}
	
	/**
	 * 生成日报表
	 * @param resp
	 * @param form
	 * @throws Exception 
	 */
	private void createDayForm(HttpServletResponse resp,MerchantReportFormsInfo form,String fileName) throws Exception {
		//加载模板，
		File file = new File(this.getClass().getResource("").getPath()+File.separator+"dayFormModel.xlsx");
		ExcelTool tool = new ExcelTool();
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file)); 
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		{//风险待处理订单
			Criteria criteria=getCriteria();
			criteria.getCondition().put("doStatus", "review");
			criteria.getCondition().put("transDateStart", criteria.getCondition().get("startDate"));
			criteria.getCondition().put("transDateEnd", criteria.getCondition().get("endDate"));
			List<ExportRiskTransInfo> list = riskMgrService.exportRiskTransList(criteria);
			XSSFSheet sheet = wb.getSheetAt(0);
			String [][] unnormaldata = new String[list.size()][9];
			int index = 0;
			for(int i = 0 ; i < list.size();i++){
				ExportRiskTransInfo info = list.get(i);
				unnormaldata[i][index++] = info.getMerNo();// 交易流水号
				unnormaldata[i][index++] = info.getTerNo();// 交易流水号
				unnormaldata[i][index++] = info.getTradeNo();// 订单号
				unnormaldata[i][index++] = info.getOrderNo();// 订单号
				unnormaldata[i][index++] = info.getCurrency() + " "
						+ info.getAmount();// 订单号
				unnormaldata[i][index++] = info.getWebsite();// 成功笔数
				unnormaldata[i][index++] = df.format(info.getDoDate());// 订单号
				unnormaldata[i][index++] = df.format(info.getDoEndDate());// 失败笔数
				unnormaldata[i][index++] = info.getDoReason();// 失败笔数
				index = 0;
			}
			tool.exportSimpleExcel(wb, sheet,"风险待处理订单 ", "风险待处理订单", null, unnormaldata, 3);
		}
		{//风险待处理订单
			Criteria criteria=getCriteria();
			criteria.getCondition().put("doStatus", "review");
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=sdf.parse(criteria.getCondition().get("startDate").toString());
			Calendar c=Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DAY_OF_MONTH, 1);
			
			criteria.getCondition().put("createDateStart", sdf.format(c.getTime()));
			criteria.getCondition().put("createDateEnd", sdf.format(c.getTime()));
			criteria.getCondition().put("susType", "2");
			List<SuspiciousOrderListInfo> list = suspiciousManageService
					.querySuspiciousOrderListInfo(criteria);
			XSSFSheet sheet = wb.getSheetAt(1);
			String [][] unnormaldata = new String[list.size()][23];
			int index = 0;
			for(int i = 0 ; i < list.size();i++){
				SuspiciousOrderListInfo info = list.get(i);
				TransDetailInfo detail=transMgrService.queryTransInfo(info.getTradeNo());
				unnormaldata[i][index++] = info.getMerNo();// 交易流水号
				unnormaldata[i][index++] = info.getTerNo();// 交易流水号
				unnormaldata[i][index++] = info.getTradeNo();// 订单号
				unnormaldata[i][index++] = info.getOrderNo();// 订单号
				unnormaldata[i][index++] = info.getRuleNameList();
				unnormaldata[i][index++] = info.getTradeList();// 成功笔数
				unnormaldata[i][index++] =detail.getMerBusCurrency()+" "+ detail.getMerTransAmount();
				unnormaldata[i][index++] = "支付成功";// 失败笔数
				unnormaldata[i][index++] = detail.getTransDate();
				unnormaldata[i][index++] = detail.getPayWebSite();
				unnormaldata[i][index++] = detail.getCardFullName();
				unnormaldata[i][index++] = detail.getSixAndFourCardNo();
				unnormaldata[i][index++] = detail.getIpAddress();
				unnormaldata[i][index++] = detail.getEmail();
				unnormaldata[i][index++] = detail.getCardFullPhone();
				unnormaldata[i][index++] = detail.getIpCountry();
				unnormaldata[i][index++] = detail.getGrCountry();
				unnormaldata[i][index++] = detail.getGrState();
				unnormaldata[i][index++] = detail.getGrAddress();
				unnormaldata[i][index++] = detail.getCardCountry();
				unnormaldata[i][index++] = detail.getCardState();
				unnormaldata[i][index++] = detail.getCardAddress();
				unnormaldata[i][index++] = detail.getCardZipCode();
				index = 0;
			}
			tool.exportSimpleExcel(wb, sheet,"可疑订单列表 ", "可疑订单列表", null, unnormaldata, 2);
		}
		renderFile(tool.toOutputRespond(), fileName+".xlsx",resp);
	}
	
	/**
	 * 生成周报表
	 * @param resp
	 * @param form
	 * @throws Exception 
	 */
	private void createWeekForm(HttpServletResponse resp,MerchantReportFormsInfo form,String fileName) throws Exception {
		//加载模板，
		File file = new File(this.getClass().getResource("").getPath()+File.separator+"weekFormModel.xlsx");
		ExcelTool tool = new ExcelTool();
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file)); 
		{//交易情况
			List<com.gateway.form.model.CountAnalysis> list = formMgrService
					.queryCountAnalysisInfoAll(getCriteria());
			XSSFSheet sheet = wb.getSheetAt(0);
			String [][] unnormaldata = new String[list.size()][18];
			int index = 0;
			
			NumberFormat nf=NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			nf.setMinimumFractionDigits(2);
			
			for(int i = 0 ; i < list.size();i++){
				com.gateway.form.model.CountAnalysis info = list.get(i);
				unnormaldata[i][index++] = info.getMerNo();// 交易流水号
				unnormaldata[i][index++] = info.getTerNo();// 交易流水号
				unnormaldata[i][index++] = info.getTotalCount();// 订单号
				unnormaldata[i][index++] = info.getTransCount();// 订单号
				unnormaldata[i][index++] = info.getTransCurrency() + " "
						+ info.getMerSettleAmount();// 订单号
				unnormaldata[i][index++] = info.getTransSuccessCount();// 成功笔数
				unnormaldata[i][index++] = info.getTransCurrency() + " "
						+ info.getMerSettleSuccessAmount();// 订单号
				unnormaldata[i][index++] = info.getTransFailureCount();// 失败笔数
				unnormaldata[i][index++] = info.getDuplicateCount();// 失败笔数
				unnormaldata[i][index++] = info.getTransRiskCount() + "";
				unnormaldata[i][index++] =info.getDishonorCount();
				unnormaldata[i][index++] = info.getRefundCount();
				unnormaldata[i][index++] = info.getComplaintCount();
				if ("0".equals(info.getTotalCount())) {
					unnormaldata[i][index++] = "0.00%";
				} else {
					unnormaldata[i][index++] = nf.format(Integer
							.parseInt(info.getTransCount())
							* 100.0
							/ Integer.parseInt(info.getTotalCount())) + "%";// 订单号
				}
				unnormaldata[i][index++] = info.getSuccessRate();
				unnormaldata[i][index++] = info.getDishonorRate();
				unnormaldata[i][index++] = info.getRefundRate();
				unnormaldata[i][index++] = info.getComplaintRate();
				index = 0;
				
			}
			tool.exportSimpleExcel(wb, sheet,"交易情况 ", "交易情况", null, unnormaldata, 3);
		}
		{//转换率分析
			List<TransRecord> list = countAnalysisService
					.queryTransRecordInfo(getCriteria()).getData();
			XSSFSheet sheet = wb.getSheetAt(1);
			String [][] unnormaldata = new String[list.size()][3];
			int index = 0;
			
			NumberFormat nf=NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			nf.setMinimumFractionDigits(2);
			for(int i = 0 ; i < list.size();i++){
				TransRecord info = list.get(i);
				unnormaldata[i][index++] = info.getDescription();// 交易流水号
				unnormaldata[i][index++] = info.getCount();// 交易流水号
				unnormaldata[i][index++] = nf.format(Double.parseDouble(info.getDecRate())*100)+"%";
				index = 0;
				
			}
			tool.exportSimpleExcel(wb, sheet,"转换率分析 ", "转换率分析", null, unnormaldata, 2);
		}
		{//转换率分析
			Criteria criteria=getCriteria();
			criteria.put("description", "接收商户网站交易数据");
			List<DisDesc> list = countAnalysisService
					.queryTransRecordDesc(criteria);
			XSSFSheet sheet = wb.getSheetAt(1);
			String [][] unnormaldata = new String[list.size()][3];
			int index = 0;
			
			NumberFormat nf=NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			nf.setMinimumFractionDigits(2);
			for(int i = 0 ; i < list.size();i++){
				DisDesc info = list.get(i);
				unnormaldata[i][index++] = info.getDisReason();// 交易流水号
				unnormaldata[i][index++] = info.getDisCount()+"";// 交易流水号
				unnormaldata[i][index++] = nf.format(info.getDisRate()*100)+"%";
				index = 0;
			}
			tool.exportSimpleExcel(wb, sheet,"转换率分析 ", "转换率分析", null, unnormaldata, 10);
		}
		{//失败原因分析
			List<com.gateway.countAnalysis.model.CountAnalysis> list = countAnalysisService
					.queryFailureListAll(getCriteria());
			XSSFSheet sheet = wb.getSheetAt(2);
			String [][] unnormaldata = new String[list.size()][5];
			int index = 0;
			NumberFormat nf=NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			nf.setMinimumFractionDigits(2);
			for(int i = 0 ; i < list.size();i++){
				com.gateway.countAnalysis.model.CountAnalysis info = list.get(i);
				unnormaldata[i][index++] = info.getRespMsg();// 交易流水号
				unnormaldata[i][index++] = info.getCountRespMsg();// 交易流水号
				unnormaldata[i][index++] = nf.format(Double.parseDouble(info.getRespMsgRate())*100)+"%";
				unnormaldata[i][index++] = info.getRemark();
				unnormaldata[i][index++] = info.getSuggest();
				index = 0;
			}
			tool.exportSimpleExcel(wb, sheet,"失败原因分析 ", "失败原因分析", null, unnormaldata, 2);
		}
		{//风险订单分析
			List<RiskPercent> list = countAnalysisService
					.queryShowRiskPerInfo(getCriteria());
			XSSFSheet sheet = wb.getSheetAt(3);
			String [][] unnormaldata = new String[list.size()][3];
			int index = 0;
			
			NumberFormat nf=NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			nf.setMinimumFractionDigits(2);
			for(int i = 0 ; i < list.size();i++){
				RiskPercent info = list.get(i);
				unnormaldata[i][index++] = info.getReason();// 交易流水号
				unnormaldata[i][index++] = info.getRiskCount()+"";// 交易流水号
				unnormaldata[i][index++] = nf.format(info.getRiskRate()*100)+"%";
				index = 0;
			}
			tool.exportSimpleExcel(wb, sheet,"风险订单分析 ", "风险订单分析", null, unnormaldata, 2);
		}
		{//风险订单分析
			List<RiskPercent> list = countAnalysisService
					.queryShowRiskPendingPerInfo(getCriteria());
			XSSFSheet sheet = wb.getSheetAt(4);
			String [][] unnormaldata = new String[list.size()][3];
			int index = 0;
			
			NumberFormat nf=NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			nf.setMinimumFractionDigits(2);
			for(int i = 0 ; i < list.size();i++){
				RiskPercent info = list.get(i);
				unnormaldata[i][index++] = info.getReason();// 交易流水号
				unnormaldata[i][index++] = info.getRiskCount()+"";// 交易流水号
				unnormaldata[i][index++] = nf.format(info.getRiskRate()*100)+"%";
				index = 0;
			}
			tool.exportSimpleExcel(wb, sheet,"风险订单分析 ", "风险订单分析", null, unnormaldata, 2);
		}
		renderFile(tool.toOutputRespond(), fileName+".xlsx",resp);
	}
	/**
	 * 生成月报表
	 * @param resp
	 * @param form
	 * @throws Exception 
	 */
	private void createMonthForm(HttpServletResponse resp,MerchantReportFormsInfo form,String fileName) throws Exception{
		//加载模板，
		File file = new File(this.getClass().getResource("").getPath()
				+ File.separator + "monthFormModel.xlsx");
		ExcelTool tool = new ExcelTool();
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
		{// 交易情况
			List<com.gateway.form.model.CountAnalysis> list = formMgrService
					.queryCountAnalysisInfoAll(getCriteria());
			XSSFSheet sheet = wb.getSheetAt(0);
			String[][] unnormaldata = new String[list.size()][18];
			int index = 0;

			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			nf.setMinimumFractionDigits(2);

			for (int i = 0; i < list.size(); i++) {
				com.gateway.form.model.CountAnalysis info = list.get(i);
				unnormaldata[i][index++] = info.getMerNo();// 交易流水号
				unnormaldata[i][index++] = info.getTerNo();// 交易流水号
				unnormaldata[i][index++] = info.getTotalCount();// 订单号
				unnormaldata[i][index++] = info.getTransCount();// 订单号
				unnormaldata[i][index++] = info.getTransCurrency() + " "
						+ info.getMerSettleAmount();// 订单号
				unnormaldata[i][index++] = info.getTransSuccessCount();// 成功笔数
				unnormaldata[i][index++] = info.getTransCurrency() + " "
						+ info.getMerSettleSuccessAmount();// 订单号
				unnormaldata[i][index++] = info.getTransFailureCount();// 失败笔数
				unnormaldata[i][index++] = info.getDuplicateCount();// 失败笔数
				unnormaldata[i][index++] = info.getTransRiskCount() + "";
				unnormaldata[i][index++] = info.getDishonorCount();
				unnormaldata[i][index++] = info.getRefundCount();
				unnormaldata[i][index++] = info.getComplaintCount();
				if ("0".equals(info.getTotalCount())) {
					unnormaldata[i][index++] = "0.00%";
				} else {
					unnormaldata[i][index++] = nf.format(Integer.parseInt(info
							.getTransCount())
							* 100.0
							/ Integer.parseInt(info.getTotalCount())) + "%";// 订单号
				}
				unnormaldata[i][index++] = info.getSuccessRate();
				unnormaldata[i][index++] = info.getDishonorRate();
				unnormaldata[i][index++] = info.getRefundRate();
				unnormaldata[i][index++] = info.getComplaintRate();
				index = 0;

			}
			tool.exportSimpleExcel(wb, sheet, "交易情况 ", "交易情况", null,
					unnormaldata, 3);
		}
		
		List<String> fileNames=new ArrayList<String>();
//		{// 交易国家分布
//			List<com.gateway.countAnalysis.model.CountAnalysis> list = countAnalysisService
//					.queryCountryListInfo(getCriteria());
//			XSSFSheet sheet = wb.getSheetAt(1);
//			String[][] unnormaldata = new String[list.size()][18];
//			int index = 0;
//
//			NumberFormat nf = NumberFormat.getInstance();
//			nf.setMaximumFractionDigits(2);
//			nf.setMinimumFractionDigits(2);
//			DefaultPieDataset dataset = new DefaultPieDataset(); 
//			for (int i = 0; i < list.size(); i++) {
//				com.gateway.countAnalysis.model.CountAnalysis info = list.get(i);
//				if(i>5){
//					Integer count=(Integer) dataset.getValue("其他");
//					dataset.setValue("其他", count+Integer.parseInt(info.getTransCount()));
//				}else{
//					dataset.setValue(info.getCardCountry()==null?"未知":info.getCardCountry(), Integer.parseInt(info.getTransCount()));
//				}
//				unnormaldata[i][index++] =  info.getCardCountry();// 国家
//				unnormaldata[i][index++] =  info.getTransCount();// 交易完成笔数
//				unnormaldata[i][index++] =  info.getTransCurrency() + " "
//						+ info.getTransAmount();// 交易金额
//				unnormaldata[i][index++] =  info.getTransSuccessCount();// 成功笔数
//				unnormaldata[i][index++] =  info.getTransCurrency() + " "
//						+ info.getTransSuccessAmount();// 成功金额
//				unnormaldata[i][index++] =  info.getTransFailureCount();// 失败笔数
//				unnormaldata[i][index++] =  info.getTransRiskCount() + "";// 风险单笔数
//				unnormaldata[i][index++] =  info.getDishonorCount();// 拒付笔数
//				unnormaldata[i][index++] =  info.getRefundCount();// 退单笔数
//				unnormaldata[i][index++] =  info.getComplaintCount();// 投诉笔数
//				unnormaldata[i][index++] =  info.getSuccessRate();// 成功率
//				unnormaldata[i][index++] =  info.getDishonorRate();// 拒付率
//				unnormaldata[i][index++] =  info.getRefundRate();// 退单率
//				unnormaldata[i][index++] =  info.getComplaintRate();// 投诉率
//				unnormaldata[i][index++] =  info.getTransRate();// 交易占比
//				index = 0;
//
//			}
//			JFreeChart chart=pieChart(dataset);
//			Date date=new Date();
////			String png="E:/"+"temp"+date.getTime()+".png";
//			String png="/tmp/"+"temp"+date.getTime()+".png";
//			File file1=new File(png);
//			fileNames.add(png);
//			ChartUtilities.saveChartAsJPEG(file1, chart, 1024, 768);
//			FileInputStream in=new FileInputStream(file1);
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();  
//			int b=in.read();
//			while(b!=-1){
//				bos.write(b);
//				b=in.read();
//			}
//			byte[] fileByte=bos.toByteArray();
//			in.close();
//			bos.close();
//			XSSFDrawing patriarch = sheet.createDrawingPatriarch(); 
//	        XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 400, 255, (short)1, list.size()+3, (short)13, 50); 
//	        patriarch.createPicture(anchor , wb.addPicture(fileByte, XSSFWorkbook.PICTURE_TYPE_PNG));
//			tool.exportSimpleExcel(wb, sheet, "交易国家分布 ", "交易国家分布", null,
//					unnormaldata, 2);
//		}
		{// 交易国家分布
			Criteria criteria=getCriteria();
			criteria.put("isSell", "YES");
			String endDate= criteria.getCondition().get("endDate").toString();
			criteria.getCondition().put("endDate", endDate+" 23:59:59");
			List<com.gateway.countAnalysis.model.CountAnalysis> list = countAnalysisService
					.queryCountryListInfo(criteria);
			XSSFSheet sheet = wb.getSheetAt(1);
			String[][] unnormaldata = new String[list.size()][16];
			int index = 0;
			
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			nf.setMinimumFractionDigits(2);
			DefaultPieDataset dataset = new DefaultPieDataset(); 
			dataset.setValue("其他", 0);
			for (int i = 0; i < list.size(); i++) {
				com.gateway.countAnalysis.model.CountAnalysis info = list.get(i);
				if(i>5){
					Number count= dataset.getValue("其他");
					dataset.setValue("其他", count.doubleValue()+Double.parseDouble(info.getTransCount()));
				}else{
					dataset.setValue(info.getCardCountry()==null?"未知":info.getCardCountry(), Integer.parseInt(info.getTransCount()));
				}
				unnormaldata[i][index++] =  info.getCardCountry();// 国家
				unnormaldata[i][index++] =  info.getTransCount();// 交易完成笔数
				unnormaldata[i][index++] =  info.getTransCurrency() + " "
						+ info.getTransAmount();// 交易金额
				unnormaldata[i][index++] =  info.getTransSuccessCount();// 成功笔数
				unnormaldata[i][index++] =  info.getTransCurrency() + " "
						+ info.getTransSuccessAmount();// 成功金额
				unnormaldata[i][index++] =  info.getTransFailureCount();// 失败笔数
				unnormaldata[i][index++] =  info.getDuplicateCount();// 失败重复笔数
				unnormaldata[i][index++] =  info.getTransRiskCount() + "";// 风险单笔数
				unnormaldata[i][index++] =  info.getDishonorCount();// 拒付笔数
				unnormaldata[i][index++] =  info.getRefundCount();// 退单笔数
				unnormaldata[i][index++] =  info.getComplaintCount();// 投诉笔数
				unnormaldata[i][index++] =  info.getSuccessRate();// 成功率
				unnormaldata[i][index++] =  info.getDishonorRate();// 拒付率
				unnormaldata[i][index++] =  info.getRefundRate();// 退单率
				unnormaldata[i][index++] =  info.getComplaintRate();// 投诉率
				unnormaldata[i][index++] =  info.getTransRate();// 交易占比
				index = 0;
			}
			if(dataset.getValue("其他").doubleValue()==0){
				dataset.remove("其他");
			}
			JFreeChart chart=pieChart(dataset);
			Date date=new Date();
//			String png="E:/"+"temp"+date.getTime()+".png";
			String png="/tmp/"+"temp"+date.getTime()+".png";
			File file1=new File(png);
			fileNames.add(png);
			ChartUtilities.saveChartAsJPEG(file1, chart, 1024, 768);
			FileInputStream in=new FileInputStream(file1);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();  
			int b=in.read();
			while(b!=-1){
				bos.write(b);
				b=in.read();
			}
			byte[] fileByte=bos.toByteArray();
			in.close();
			bos.close();
			XSSFDrawing patriarch = sheet.createDrawingPatriarch(); 
	        XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 400, 255, (short)1, list.size()+3, (short)13, list.size()+50); 
	        patriarch.createPicture(anchor , wb.addPicture(fileByte, XSSFWorkbook.PICTURE_TYPE_PNG));
			tool.exportSimpleExcel(wb, sheet, "交易国家分布 ", "交易国家分布", null,
					unnormaldata, 2);
		}
		{//交易时间分布
			XSSFSheet sheet = wb.getSheetAt(2);
			//生成曲线图
			XYSeries timeseries = new XYSeries("笔数");  
			List<com.gateway.form.model.CountAnalysis> list = formMgrService.queryTransCountInfoForDay(getCriteria());
			Calendar c=Calendar.getInstance();
			String startDate=form.getStartDate();
			DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=sdf.parse(startDate);
			c.setTime(date);
			int month=c.get(Calendar.MONTH);
			int max=0;
			while(month==c.get(Calendar.MONTH)){
				int day=c.get(Calendar.DATE);
				boolean flag=true;
				for(com.gateway.form.model.CountAnalysis cou:list){
					if(Integer.parseInt(cou.getDay())==day){
						if(Integer.parseInt(cou.getTransCount())>max){
							max=Integer.parseInt(cou.getTransCount());
						}
						timeseries.add(day,Integer.parseInt(cou.getTransCount()));
						flag=false;
						break;
					}
				}
				if(flag){
					timeseries.add(day,0);
				}
				c.set(Calendar.DATE, day+1);
			}
			JFreeChart chart=this.createProfile(timeseries,"交易时间分布","日期","笔数",max);
			Date date1=new Date();
//			String png="E:/"+"temp"+date1.getTime()+"Profile.png";
			String png="/tmp/"+"temp"+date1.getTime()+"Profile.png";
			File file1=new File(png);
			fileNames.add(png);
			ChartUtilities.saveChartAsJPEG(file1, chart, 1024, 768);
			FileInputStream in=new FileInputStream(file1);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();  
			int b=in.read();
			while(b!=-1){
				bos.write(b);
				b=in.read();
			}
			byte[] fileByte=bos.toByteArray();
			in.close();
			bos.close();
			XSSFDrawing patriarch = sheet.createDrawingPatriarch(); 
	        XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 400, 255, (short)3, 0, (short)18, 27); 
	        patriarch.createPicture(anchor , wb.addPicture(fileByte, XSSFWorkbook.PICTURE_TYPE_PNG));
	        // 处理交易时间段分布
	        List<TransTimeCountInfo> list1=formMgrService.queryTransTimeCountInfo(getCriteria());
	        String[][] unnormaldata = new String[list1.size()][2];
	        int index=0;
	        for (int i = 0; i < list1.size(); i++) {
	        	TransTimeCountInfo info = list1.get(i);
				unnormaldata[i][index++] =  info.getHourStr();// 投诉率
				unnormaldata[i][index++] =  info.getCount()+"";// 交易占比
				index = 0;

			}
	        
	        tool.exportSimpleExcel(wb, sheet, "交易时间段分布 ", "交易时间段分布", null,
					unnormaldata, 2);
			
		}
		{//交易金额分布 //处理CNY金额分布
			
			Criteria criteria=getCriteria();
			criteria.getCondition().put("currency", "CNY");
			List<TransAmountCountInfo> list =formMgrService.queryTransAmountCountInfo(criteria);
			
			XSSFSheet sheet = wb.getSheetAt(3);
			String[][] unnormaldata = new String[list.size()][2];
			int index = 0;

			for (int i = 0; i < list.size(); i++) {
				TransAmountCountInfo info = list.get(i);
				unnormaldata[i][index++] =  info.getRange();// 国家
				unnormaldata[i][index++] =  info.getCount()+"";// 交易完成笔数
				index = 0;
			}
			tool.exportSimpleExcel(wb, sheet, "交易金额区间分布 ", "交易金额区间分布", null,
					unnormaldata, 2);
		}
		{//交易金额分布 //处理USD金额分布
			
			Criteria criteria=getCriteria();
			criteria.getCondition().put("currency", "USD");
			List<TransAmountCountInfo> list =formMgrService.queryTransAmountCountInfo(criteria);
			
			XSSFSheet sheet = wb.getSheetAt(3);
			String[][] unnormaldata = new String[list.size()][2];
			int index = 0;

			for (int i = 0; i < list.size(); i++) {
				TransAmountCountInfo info = list.get(i);
				unnormaldata[i][index++] =  info.getRange();// 国家
				unnormaldata[i][index++] =  info.getCount()+"";// 交易完成笔数
				index = 0;
			}
			tool.exportSimpleExcel(wb, sheet, "交易金额区间分布 ", "交易金额区间分布", null,
					unnormaldata, 16);
		}
		renderFile(tool.toOutputRespond(), fileName+".xlsx",resp);
		for(String name:fileNames){
			File file1=new File(name);
			file1.delete();
			
		}
	}
	
	/**
	 * 生成饼图图片
	 * 
	 */
	private JFreeChart pieChart(DefaultPieDataset dataset){  
        DefaultPieDataset data = dataset;  
        JFreeChart chart = ChartFactory.createPieChart3D("交易国家分布",data,true,false,false);  
      //设置百分比  
        PiePlot pieplot = (PiePlot) chart.getPlot();  
        DecimalFormat df = new DecimalFormat("0.00%");//获得一个DecimalFormat对象，主要是设置小数问题  
        NumberFormat nf = NumberFormat.getNumberInstance();//获得一个NumberFormat对象  
        StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0}  {2}", nf, df);//获得StandardPieSectionLabelGenerator对象  
        pieplot.setLabelGenerator(sp1);//设置饼图显示百分比  
      
    //没有数据的时候显示的内容  
        pieplot.setNoDataMessage("无数据显示");  
        pieplot.setCircular(false);  
        pieplot.setLabelGap(0.02D);  
      
        pieplot.setIgnoreNullValues(true);//设置不显示空值  
        pieplot.setIgnoreZeroValues(true);//设置不显示负值  
        chart.getTitle().setFont(new Font("宋体",Font.BOLD,50));//设置标题字体  
        PiePlot piePlot= (PiePlot) chart.getPlot();//获取图表区域对象  
        piePlot.setLabelFont(new Font("宋体",Font.BOLD,25));//解决乱码  
        chart.getLegend().setItemFont(new Font("黑体",Font.BOLD,25));  
        return chart;
	} 
	/**
	 * 生成曲线图
	 * @return
	 */
	 private  JFreeChart createProfile(XYSeries timeseries,String title,String xName,String yName,int yMax) {  //这个数据集有点多，但都不难理解  
		  XYSeriesCollection timeseriescollection = new XYSeriesCollection();  
          timeseriescollection.addSeries(timeseries);  
          XYDataset xydataset=timeseriescollection;
	        JFreeChart jfreechart = ChartFactory.createXYLineChart(title, xName, yName, xydataset,PlotOrientation.VERTICAL,true,true,true) ;
	        		//ChartFactory.createTimeSeriesChart(title, xName, yName,xydataset, true, true, true);  
	        XYPlot xyplot = (XYPlot) jfreechart.getPlot();  
	        NumberAxis dateaxis = (NumberAxis) xyplot.getDomainAxis();  
	        dateaxis .setTickUnit(new NumberTickUnit(1));
	        dateaxis.setLabelFont(new Font("黑体",Font.BOLD,24));         //水平底部标题  
	        dateaxis.setTickLabelFont(new Font("宋体",Font.BOLD,22));  //垂直标题  
	        NumberAxis rangeAxis=(NumberAxis) xyplot.getRangeAxis();//获取柱状  
	        int step=1;
	        if(yMax>1000){
	        	step=20;
	        }else if(yMax>100){
	        	step=5;
	        }
	        rangeAxis .setTickUnit(new NumberTickUnit(step));
	        rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,25));  
	        jfreechart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 25));  
	        jfreechart.getTitle().setFont(new Font("宋体",Font.BOLD,50));//设置标题字体  
         return jfreechart;  
     }  
	 private String renderFile(byte[] bytes, String fileName,HttpServletResponse resp) throws Exception {
			resp.addHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("GB2312"), "ISO-8859-1"));
			return render(bytes, "application/vnd.ms-excel",resp);
		}
		protected String render(byte[] bytes, String contentType,HttpServletResponse resp) throws Exception {
			// 不使用缓存,如果使用,https ie下不能下载
			resp.setContentType(contentType);
			OutputStream out = new BufferedOutputStream(resp.getOutputStream());
			out.write(bytes);
			out.flush();
			out.close();
			return null;
		}
		
		/**
		 * 商户资金查询
		 */
		@RequestMapping(value="/queryMerchantCapitalInfoList")
		public String queryMerchantCapitalInfoList(){
			HttpServletRequest request = getRequest();
			Criteria criteria = getCriteria();
			UserInfo user = getLogAccount();
			criteria.getCondition().put("merNos", user.getUserRefMerNo());
			if("get".equalsIgnoreCase(request.getMethod())){
				
			}else{
				PageInfo<MerchantCapitalInfo> page = sellMgrService.queryMerchantCapitalInfoList(criteria);
				request.setAttribute("page", page);
				request.setAttribute("form", criteria.getCondition());
			}
			return "sellmgr/merchantCapitalInfoList";
		}
		
		/**
		 * 导出商户资金信息
		 * @throws IOException 
		 * @throws WriteException 
		 * @throws RowsExceededException 
		 */
		@RequestMapping(value="/exportMerchantCapitalInfo")
		public void exportMerchantCapitalInfo(HttpServletResponse response) throws IOException, RowsExceededException, WriteException{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition","attachment;filename="+ "merchantSettle.xls" ); 
			Criteria criteria = getCriteria();
			UserInfo user = getLogAccount();
			criteria.getCondition().put("merNos", user.getUserRefMerNo());
			List<MerchantCapitalInfo> list = sellMgrService.queryMerchantCapitalExportInfoList(criteria);
			WritableWorkbook book;
			book = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = book.createSheet("商户资金", 0);
			String[] headerName = { "商户号", "终端号","账户类型","账户总金额"};
			// 写入标题
			for (int index = 0; index < headerName.length; index++) {
				Label label = new Label(index, 0, headerName[index]);
				sheet.addCell(label);
			}
			for (int row = 1; row <= list.size(); row++) {
				int col = 0;
				MerchantCapitalInfo info = list.get(row-1);
				sheet.addCell( new Label(col++, row, info.getMerNo()));
				sheet.addCell( new Label(col++, row, info.getTerNo()));
				sheet.addCell( new Label(col++, row, info.getAccountType()==0?"交易账户":"保证金账户"));
				sheet.addCell( new Label(col++, row, info.getCurrency()+" "+info.getTotalAmount()));
			}
			book.write();
			book.close();
		}
		
		/**
		 * 查询商户结算信息
		 */
		@RequestMapping(value="/queryMerchantSettlerInfoList")
		public String queryMerchantSettlerInfoList(){
			HttpServletRequest request = getRequest();
			Criteria criteria = getCriteria();
			UserInfo user = getLogAccount();
			criteria.getCondition().put("merNos", user.getUserRefMerNo());
			if("get".equalsIgnoreCase(request.getMethod())){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				criteria.getCondition().put("dateStart", sdf.format(new Date()));
				criteria.getCondition().put("dateEnd", sdf.format(new Date()));
				criteria.getCondition().put("sellterDateStart", sdf.format(new Date()));
				criteria.getCondition().put("sellterDateEnd", sdf.format(new Date()));
				request.setAttribute("form", criteria.getCondition());
			}else{
				criteria.getCondition().put("cashTypes", "(1,2,3,4,5,6,7,8,9,10)");
				List<DeductionTypeInfo> list=settleMgrService.queryDeductionTypeInfo(criteria);
				getRequest().setAttribute("dtList", list);
				PageInfo<MerchantSettleInfo> page = sellMgrService.queryMerchantSettleInfoList(criteria);
				request.setAttribute("page", page);
				request.setAttribute("form", criteria.getCondition());
			}
			return "sellmgr/merchantSettleInfoList";
		}
		
		/**
		 * 导出商户结算信息
		 * @throws IOException 
		 * @throws WriteException 
		 * @throws RowsExceededException 
		 */
		@RequestMapping(value="/exportMerchantSettleInfo")
		public void exportMerchantSettleInfo(HttpServletResponse response) throws IOException, RowsExceededException, WriteException{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ "merchantTransList.xls");
			Criteria criteria =getCriteria();
			criteria.getCondition().put("cashTypes", "(1,2,3,4,5,6,7,8,9,10)");
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			UserInfo user = getLogAccount();
			criteria.getCondition().put("merNos", user.getUserRefMerNo());
			List<MerchantSettleInfo> list = sellMgrService.queryMerchantSettleExportInfoList(criteria);
			WritableWorkbook book = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = book.createSheet("商户提现", 0);
			String[] headerName = { "id","商户号","终端号","账户类型","操作类型","币种","金额",
					"提现审核状态","申请时间"
					,"出款时间","出款说明"};
			// 写入标题
			for (int index = 0; index < headerName.length; index++) {
				Label label = new Label(index, 0, headerName[index]);
				sheet.addCell(label);
			}
			for (int row = 1; row <= list.size(); row++) {
				int col = 0;
				MerchantSettleInfo info = list.get(row - 1);
				sheet.addCell(new Label(col++, row, info.getId()));
				sheet.addCell(new Label(col++, row, info.getMerNo()));
				sheet.addCell(new Label(col++, row, info.getTerNo()));
				sheet.addCell(new Label(col++, row, info.getAccountType()==0?"交易账户":"保证金账户"));
				sheet.addCell(new Label(col++, row, BaseDataListener.getStringColumnKey("CASHTYPE", info.getCashType()+"", info.getCashType()+"") ));
				sheet.addCell(new Label(col++, row, info.getCurrency()));
				sheet.addCell(new Label(col++, row, info.getAmount().doubleValue()+""));
				sheet.addCell(new Label(col++, row, BaseDataListener.getStringColumnKey("ACCESSSTATUS",info.getStatus()+"",info.getStatus()+"")));
				sheet.addCell(new Label(col++, row, info.getCreateDate()!=null?sdf.format(info.getCreateDate()):""));
				sheet.addCell(new Label(col++, row, info.getMoneyDate()!=null?sdf.format(info.getMoneyDate()):""));
				sheet.addCell(new Label(col++, row, info.getMoneyRemark()));
			}
			book.write();
			book.close();
			return;
		}
		/**
		 * 获取商户列表
		 * @return
		 */
		@RequestMapping(value="/getListMerchant")
		public String getListMerchant(){
			Criteria criteria=getCriteria();
			if(!"get".equalsIgnoreCase(getRequest().getMethod())){
				if(getLogAccount().getUserRefMerNo()==null || "".equals(getLogAccount().getUserRefMerNo())){
					criteria.getCondition().put("merNos", "111");//设置一个不存在的商户号
				}else{
					criteria.getCondition().put("merNos", getLogAccount().getUserRefMerNo());
				}
				PageInfo<MerchantInfo> page = merchantMgrService.getListMerchant(criteria);
				getRequest().setAttribute("page",page);
			}
			return "sellmgr/merchantList";
		}
		/**
		 * 商户详细信息
		 * @param id
		 * @return
		 */
		@RequestMapping(value="/queryMerchantById")
		public String queryMerchantById(int id){
			MerchantInfo merchantInfo= merchantMgrService.queryMerchantInfoById(id);
			getRequest().setAttribute("merchant", merchantInfo);
			return "sellmgr/merchantinfo";
		}
		/**
		 * 查询商户交易设置信息
		 * @param merNo
		 * @return
		 */
		@RequestMapping(value="/queryMerchantTransSettingBYmerNo")
		public String queryMerchantTransSettingBYmerNo(String merNo){
			List<TransSettingInfo> list = merchantMgrService.queryTransSettingInfo(merNo);
			getRequest().setAttribute("list", list);
			return "merchantmgr/transSetting";
		}
		
		/** 时间段交易趋势 */
		@RequestMapping(value = "/queryTransHourCount")
		public String queryTransHourCount() {
			Criteria criteria = getCriteria();
			if(getLogAccount().getUserRefMerNo()==null || "".equals(getLogAccount().getUserRefMerNo())){
				criteria.getCondition().put("merNos", "111");//设置一个不存在的商户号
			}else{
				criteria.getCondition().put("merNos", getLogAccount().getUserRefMerNo());
			}
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
						.queryTransHourCount(criteria);
//				PageInfo<TransHourCount> page=new PageInfo<TransHourCount>();
//				page.setNowPage(1);
//				page.setNumPerPage(list.size());
//				page.setTotal(list.size());
//				page.setData(list);
//				page.setTotalPage(1);
//				page.setOffset(250);
				getRequest().setAttribute("page", page);
			}
			return "sellmgr/queryTransHourCount";
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
				PageInfo<CurrencyDisCount> page = sellMgrService
						.queryMerchantDisCount(getCriteria());
				getRequest().setAttribute("page", page);
			}
			return "sellmgr/merchantDisCount";
		}

		@RequestMapping(value = "/exportMerchantDisCountInfo")
		public void exportMerchantDisCountInfo(HttpServletResponse resp)
				throws Exception {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-disposition", "attachment;filename="
					+ "failureTransList.xls");
			List<CurrencyDisCount> list = sellMgrService
					.queryMerchantDisCountAll(getCriteria());
			WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
			WritableSheet sheet = null;
			sheet = book.createSheet("通道统计分析", 0);
			String[] headerName = new String[] { "商户号", "终端号", "统计方式", "卡种",
					"交易成功笔数", "成功金额", "拒付笔数", "拒付金额", "拒付率"};
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
				sheet.addCell(new Label(col++, row, info.getCurrency() + " "
						+ info.getSuccessAmount()));// 订单号
				sheet.addCell(new Label(col++, row, info.getDisCount()));// 失败笔数
				sheet.addCell(new Label(col++, row, info.getCurrency() + " "
						+ info.getDisAmount()));// 订单号
				if (null == info.getSuccessCount()
						|| "0".equals(info.getSuccessCount())) {
					sheet.addCell(new Label(col++, row, "0.00%"));// 成功笔数
				} else {
					sheet.addCell(new Label(col++, row, nf.format(Double
							.parseDouble(info.getDisCount())
							/ Double.parseDouble(info.getSuccessCount()) * 100)
							+ "%"));// 成功笔数
				}
			}
			book.write();
			book.close();
		}
		
		@ResponseBody
		@RequestMapping("/queryTransHourCountForPic")
		public List<TransHourCount> queryTransHourCountForPic(){
			Criteria criteria = getCriteria();
			if(getLogAccount().getUserRefMerNo()==null || "".equals(getLogAccount().getUserRefMerNo())){
				criteria.getCondition().put("merNos", "111");//设置一个不存在的商户号
			}else{
				criteria.getCondition().put("merNos", getLogAccount().getUserRefMerNo());
			}
			criteria.getCondition().put("currency", null);
			return countAnalysisService
					.queryTransHourCount(criteria);
		}
	
		@RequestMapping(value="/listCurrencyDisCountDesc")
		public String listCurrencyDisCountDesc(String currencyId, String countYear,
				String countMonth, String cardType,String merNo,String terNo) {
			Criteria criteria=getCriteria();
			if ("0".equals(countYear)) {
				countYear = null;
				countMonth = null;
				criteria.getCondition().remove("countYear");
				criteria.getCondition().remove("countMonth");
			}
			List<DisDesc> list = sellMgrService.queryCurrencyDisDesc(
					criteria);
			getRequest().setAttribute("currencyId", currencyId);
			getRequest().setAttribute("merNo", merNo);
			getRequest().setAttribute("terNo", terNo);
			getRequest().setAttribute("countYear", countYear);
			getRequest().setAttribute("countMonth", countMonth);
			getRequest().setAttribute("cardType", cardType);
			getRequest().setAttribute("list", list);
			return "sellmgr/listCurrencyDisCountDesc";
		}
		
		@RequestMapping(value = "/exportCurrencyDisRateDescInfo")
		public void exportCurrencyDisRateDescInfo(HttpServletResponse resp)throws Exception, IOException, RowsExceededException,WriteException {
			Criteria criteria = getCriteria();
			criteria.getCondition().put("type", "1");
			List<Complaint> complaint = sellMgrService
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
		 * 销售员主从新增页面
		 * @return
		 */
		@RequestMapping(value="/goAddSellRefSells")
		public String goAddSellRefSells(){
			List<UserInfo> users=sellMgrService.queryAllUsersInfo();
			getRequest().setAttribute("users", users);
			return "sellmgr/addSellRefSells";
		}
		
		/**
		 * 销售员主从新增
		 * @param info
		 * @return
		 */
		@RequestMapping("/addSellRefSells")
		public ModelAndView addSellRefSells(SellRefSellsInfo info){
			List<SellRefSellsInfo> sellRefSellsInfoList=sellMgrService.querySellRefuSellsBySellMgr(info.getSellMgr());
			if(sellRefSellsInfoList.isEmpty()){
				info.setCreateBy(getLogAccount().getRealName());
				int i = sellMgrService.addSellRefSellsInfo(info);
				if(i>0){
					return ajaxDoneSuccess("添加成功");
				}
			}else{
				return ajaxDoneError("重复添加");
			}
			return ajaxDoneError("添加失败");
		}
		
		/**
		 * 销售员主从修改页面
		 * @return
		 */
		@RequestMapping(value="/goUpdateSellRefSells")
		public String goUpdateSellRefSells(String sellMgr){
			//所有用户信息
			List<UserInfo> users=sellMgrService.queryAllUsersInfo();
			getRequest().setAttribute("users", users);
			for(UserInfo userInfo : users) {
				if(sellMgr.equals(userInfo.getUserName())){
					getRequest().setAttribute("sellMgrRealName", userInfo.getRealName());
				}
			}
			getRequest().setAttribute("sellMgr", sellMgr);
			//根据主管姓名查询员工列表
			List<SellRefSellsInfo> sellRefSellsInfoList=sellMgrService.querySellRefuSellsBySellMgr(sellMgr);
			StringBuffer stringBuffer=new StringBuffer();
			for (SellRefSellsInfo sellRefSellsInfo : sellRefSellsInfoList) {
				stringBuffer.append(sellRefSellsInfo.getSell()+"#");
			}
			getRequest().setAttribute("sells", stringBuffer.toString());
			return "sellmgr/updateSellRefSells";
		}
		
		/**
		 * 销售员主从修改
		 * @param info
		 * @return
		 */
		@RequestMapping("/updateSellRefSells")
		public ModelAndView updateSellRefSells(SellRefSellsInfo info){
			info.setCreateBy(getLogAccount().getRealName());
			int i = sellMgrService.updateSellRefSellsInfo(info);
			if(i>0){
				return ajaxDoneSuccess("修改成功");
			}
			return ajaxDoneError("修改失败");
		}
		
		/**
		 * 条件查询销售员主从列表
		 * @return
		 */
		@RequestMapping("/listSellRefSells")
		public String listSellRefSells(){
			if (!"get".equalsIgnoreCase(getRequest().getMethod())) {
				PageInfo<SellRefSellsInfo> page = sellMgrService
						.querySellRefSellsInfo(getCriteria());
				getRequest().setAttribute("page", page);
			} 
			return "sellmgr/listSellRefSells";
		}
		
		/**
		 * 删除销售员主从
		 * @param sellMgrs
		 * @return
		 */
		@RequestMapping("/deleteSellRefSellsInfo")
		public ModelAndView deleteSellRefSellsInfo(String[] sellMgrs){
			int count=sellMgrService.deleteSellRefSellsInfo(sellMgrs);
			if(count>0){
				return ajaxDoneSuccess("删除成功");
			}
			return ajaxDoneError("删除失败");
		}
		
		/** 国家交易分布列表 */
		@RequestMapping(value = "/countCountryList")
		public String countCountryList() {
			Criteria criteria = getCriteria();
			criteria.put("isSell", "YES");
			if ("get".equalsIgnoreCase(getRequest().getMethod())) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				String transDateStart = sdf.format(date);
				String transDateEnd = sdf.format(date);
				criteria.getCondition().put("startDate", transDateStart + " 00:00:00");
				criteria.getCondition().put("endDate", transDateEnd + " 23:59:59");
				getRequest().setAttribute("form", criteria.getCondition());
			} else {
				getRequest().setAttribute("form", criteria.getCondition());
				PageInfo<CountAnalysis> page = countAnalysisService.queryCountryList(criteria);
				getRequest().setAttribute("page", page);
			}
			return "sellmgr/countCountryList";
		}

		@ResponseBody
		@RequestMapping(value = "/countCountryListForPic")
		public HashMap<String, Object> countCountryListForPic() {
			HashMap<String, Object> map = new HashMap<String, Object>();
			Vector<Vector<String>> vv = new Vector<Vector<String>>();
			Criteria criteria = getCriteria();
			criteria.put("isSell", "YES");
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
			criteria.put("isSell", "YES");
			List<CountAnalysis> list = countAnalysisService
					.queryCountryListInfo(criteria);
			WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
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
			book.close();
		}
		
		/**
		 * 销售业绩查询
		 */
		@RequestMapping(value="/salesPerformanceInfoList")
		public String salesPerformanceInfoList(){
			Criteria criteria = getCriteria();
			if("post".equalsIgnoreCase(getRequest().getMethod())){
				criteria.getCondition().put("type", "1");
				PageInfo<SalesPerformanceInfo> page = faffService.querySalesPerformanceInfo(criteria);
				getRequest().setAttribute("form", criteria.getCondition());
				getRequest().setAttribute("page", page);
			}else{
				SimpleDateFormat year = new SimpleDateFormat("yyyy");
				SimpleDateFormat month = new SimpleDateFormat("M");
				criteria.getCondition().put("year", year.format(new Date()));
				criteria.getCondition().put("month", month.format(new Date()));
				getRequest().setAttribute("form", criteria.getCondition());
			}
			return "sellmgr/listSalesPerformanceInfo";
		}
		
		/**
		 * 导出销售业绩详情
		 */
		@RequestMapping(value="/exportsalesPerformanceInfo")
		public void exportsalesPerformanceInfo(HttpServletResponse response, HttpServletRequest request){
			OutputStream out = null;
			try {
				out = response.getOutputStream();
				response.setHeader("Content-Disposition", "attachment; filename=" + "salesPerformance.xlsx");
				response.setContentType("application/octet-stream; charset=utf-8");
				Criteria criteria=getCriteria();
				criteria.getCondition().put("type", "1");
				BIWorkbook bw=new BIWorkbook();
				BISheet bs = bw.addSheet();
				BIRow br_0 = bs.addRow();
				for(String str:new String[]{"现销售员","原销售员","OA业务单号","开户状态","查询日期",
						"商户号","商户行业","终端号","成功金额","手续费","手续费扣率","成功笔数","总拒付金额","总拒付笔数",
						"总拒付率","拒付金额","拒付笔数","拒付率","退款金额","退款笔数","退款率"}){
					br_0.addCell().setCellValue(str, null);
				}
					List<SalesPerformanceInfo> list = faffService.queryExportSalesPerformanceInfo(criteria);
					if(list!=null){
						for (int row = 1; row <= list.size(); row++) {
							BIRow br_1 = bs.addRow();
							SalesPerformanceInfo info = list.get(row-1);
							br_1.addCell().setCellValue(info.getSellName(),null);
							br_1.addCell().setCellValue(info.getOldSellName(),null);
							br_1.addCell().setCellValue(info.getOaOrderNo(),null);
							br_1.addCell().setCellValue(BaseDataListener.getStringColumnKey("MERCHANTSTATUS", info.getEnabled()+"", info.getEnabled()+""),null);
							br_1.addCell().setCellValue(info.getQueryDate(),null);
							br_1.addCell().setCellValue(info.getMerNo(),null);
							br_1.addCell().setCellValue(BaseDataListener.getStringColumnKey("INDUSTRYTYPE", info.getIndustry(), info.getIndustry()),null);
							br_1.addCell().setCellValue(info.getTerNo(),null);
							br_1.addCell().setCellValue(info.getCurrency()+" "+info.getSuccessAmount(), null);
							br_1.addCell().setCellValue(info.getPoundage(), null);
							br_1.addCell().setCellValue(info.getPoundageRate(), null);
							br_1.addCell().setCellValue(info.getSuccessCount(),null);
							br_1.addCell().setCellValue(info.getCurrency()+" "+info.getDisCountAmount(),null);
							br_1.addCell().setCellValue(info.getDisCountCount(),null);
							br_1.addCell().setCellValue(info.getDisCountRate(),null);
							br_1.addCell().setCellValue(info.getCurrency()+" "+info.getDisAmount(),null);
							br_1.addCell().setCellValue(info.getDisCount(),null);
							br_1.addCell().setCellValue(info.getDisRate(),null);
							br_1.addCell().setCellValue(info.getCurrency()+" "+info.getRefundAmount(),null);
							br_1.addCell().setCellValue(info.getRefundCount(),null);
							br_1.addCell().setCellValue(info.getRefundRate(),null);
						}
					}
					bw.workbook.write(out);	
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				if(out!=null){
					try {
						out.flush();
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		/**
		 * 查询销售代理商关系
		 * @param merNo
		 * @return
		 */
		@RequestMapping(value="/queryAgentRelSellInfo")
		public String queryAgentRelSellInfo(){
			PageInfo<AgentRelSellInfo> list = sellMgrService.queryAgentRelSellInfo(getCriteria());
			getRequest().setAttribute("page", list);
			return "sellmgr/queryAgentRelSellInfo";
		}
		
		@RequestMapping(value="/goAddAgentRelSellInfo")
		public String goAddAgentRelSellInfo(String id){
			List<UserInfo> users=sellMgrService.queryAllUsersInfo();
			getRequest().setAttribute("users", users);
			if(id!=null && !"".equals(id)){
				AgentRelSellInfo info=sellMgrService.queryAgentRelSellInfoById(id);
				getRequest().setAttribute("info", info);
			}
			return "sellmgr/addAgentRelSellInfo";
		}
		
		@RequestMapping(value="/addAgentRelSellInfo")
		public ModelAndView addAgentRelSellInfo(AgentRelSellInfo info){
			int i=0;
			String id=info.getId();
			String shaKey=SHA256Utils.getSHA256Encryption(info.getAgentName()+new Date().getTime()+"").substring(0, 20);
			info.setShaKey(shaKey);
			info.setCreateBy(getLogAccount().getRealName());
			if(id!=null && !"".equals(id)){
				i=sellMgrService.updateAgentRelSellInfo(info);
			}else{
				i=sellMgrService.addAgentRelSellInfo(info);
			}
			if(i>0){
				return ajaxDoneSuccess("操作成功");
			}else{
				return ajaxDoneError("操作失败");
			}
		}
		
}
