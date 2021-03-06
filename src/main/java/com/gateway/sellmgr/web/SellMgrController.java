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
	 * ????????????
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
	 * ?????????????????????????????????
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
	
	/** ?????????????????? */
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
		sheet = book.createSheet("??????????????????", 0);
		String[] headerName = {"?????????","?????????","?????????","??????????????????","????????????",
				 "????????????","????????????","????????????","??????????????????","???????????????","????????????","????????????",
			"????????????","???????????????","?????????","?????????","?????????","?????????"};
		// ????????????
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
			sheet.addCell( new Label(col++, row, info.getMerNo()));//???????????????
			sheet.addCell( new Label(col++, row, info.getTerNo()));//???????????????
			sheet.addCell( new Label(col++, row, info.getTotalCount()));//?????????
			sheet.addCell( new Label(col++, row, info.getTransCount()));//?????????
			sheet.addCell( new Label(col++, row, info.getTransCurrency()+" "+info.getTransAmount()));//?????????
			sheet.addCell( new Label(col++, row, info.getTransSuccessCount()));//????????????
			sheet.addCell( new Label(col++, row, info.getTransCurrency()+" "+info.getTransSuccessAmount()));//?????????
			sheet.addCell( new Label(col++, row, info.getTransFailureCount()));//????????????
			sheet.addCell( new Label(col++, row, info.getDuplicateCount()));//????????????
			sheet.addCell( new Label(col++, row, info.getTransRiskCount()+""));
			sheet.addCell( new Label(col++, row, info.getDishonorCount()));
			sheet.addCell( new Label(col++, row, info.getRefundCount()));
			sheet.addCell( new Label(col++, row, info.getComplaintCount()));
			if("0".equals(info.getTotalCount())){
				sheet.addCell( new Label(col++, row, "0.00%"));
			}else{
				sheet.addCell( new Label(col++, row, nf.format(Integer.parseInt(info.getTransCount())*100.0/Integer.parseInt(info.getTotalCount()))+"%"));//?????????
			}
			sheet.addCell( new Label(col++, row, info.getSuccessRate()));
			sheet.addCell( new Label(col++, row, info.getDishonorRate()));
			sheet.addCell( new Label(col++, row, info.getRefundRate()));
			sheet.addCell( new Label(col++, row, info.getComplaintRate()));
		}
		book.write();
		book.close();
	}
	/** ?????????????????? 
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
				throw new ServiceException("?????????????????????");
			}
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<CountAnalysis> page=sellMgrService.queryFailureList(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "sellmgr/failureList";
	}
	/** ?????????????????? 
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
				throw new ServiceException("?????????????????????");
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
//			return ajaxDoneError("??????????????????");
//		}
		int count=sellMgrService.checkUserNameDuplicate(info);
		if(count>0){
			return ajaxDoneError("??????????????????");
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
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
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
			return ajaxDoneError("????????????");
		}
		info.setLastModifyBy(getLogAccount().getRealName());
		int i = sellMgrService.updateSellRefMerNo(info);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	@RequestMapping(value="/deleteSellRefMerNoByIds")
	public ModelAndView deleteSellRefMerNoByIds(String[] ids){
		int i=sellMgrService.deleteSellRefMerNoByIds(ids);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	@RequestMapping(value="/showMerNos")
	public String showMerNos(){
		return "sellmgr/showMerNos";
	}
	
	/**
	 * ????????????
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
	
	/** ?????????????????? */
	@RequestMapping(value="/uploadTransRecordFile")
	public void uploadTransRecordFile(HttpServletResponse resp) throws Exception{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "transRecordList.xls" );
		List<TransRecordInfo> list = sellMgrService.queryTransRecordInfo(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("??????????????????", 0);
		String[] headerName = { "?????????","?????????","?????????","?????????","??????????????????","?????????","????????????","IP","??????","??????","????????????","????????????","??????????????????","????????????","????????????",
								"??????","??????","??????","IP","??????????????????","????????????","?????????/ ???","????????????","??????","????????????","?????????/???","????????????"};
		// ????????????
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			TransRecordInfo info = list.get(row-1);
			TransDetailInfo eInfo = transMgrService.queryTransInfo(info.getTradeNo());
			sheet.addCell( new Label(col++, row, info.getMerNo()));//?????????
			sheet.addCell( new Label(col++, row, info.getTerNo()));//?????????
			sheet.addCell( new Label(col++, row, info.getTradeNo()));//???????????????
			sheet.addCell( new Label(col++, row, info.getOrderNo()));//???????????????
			sheet.addCell( new Label(col++, row, info.getEnterTime()));//??????????????????
			sheet.addCell( new Label(col++, row, info.getRespCode()));//?????????
			sheet.addCell( new Label(col++, row, info.getRespMsg()));//????????????
			sheet.addCell( new Label(col++, row, info.getClientIP()));//IP
			sheet.addCell( new Label(col++, row, info.getDescription()));//??????
			sheet.addCell( new Label(col++, row, info.getTransPortProtocol()));//??????
			sheet.addCell( new Label(col++, row, info.getCurrencyCode() +" " + info.getAmount()));//????????????
			sheet.addCell( new Label(col++, row, info.getMerURL()));//????????????
			sheet.addCell( new Label(col++, row, info.getSubmitURL()));//??????????????????
			sheet.addCell( new Label(col++, row, info.getReturnURL()));//????????????
			if(!StringUtils.isEmpty(eInfo.getGoodsInfoByte())){//????????????
				sheet.addCell(new Label(col++, row, new String(eInfo.getGoodsInfoByte(),"utf-8")));
				System.out.println("===="+new String(eInfo.getGoodsInfoByte(),"utf-8"));
			}else{
				sheet.addCell(new Label(col++, row, ""));
			}
			sheet.addCell( new Label(col++, row, eInfo.getCardFullName()));//??????
			sheet.addCell( new Label(col++, row, eInfo.getEmail()));//??????
			sheet.addCell( new Label(col++, row, eInfo.getCardFullPhone()));//??????
			sheet.addCell( new Label(col++, row, eInfo.getIpAddress()));//IP
			sheet.addCell( new Label(col++, row, eInfo.getSixAndFourCardNo()));//??????????????????
			sheet.addCell( new Label(col++, row, eInfo.getGrCountry()));//????????????
			sheet.addCell( new Label(col++, row, eInfo.getGrState()));//?????????/ ???
			sheet.addCell( new Label(col++, row, eInfo.getGrAddress()));//????????????
			sheet.addCell( new Label(col++, row, eInfo.getGrZipCode()));//??????
			sheet.addCell( new Label(col++, row, eInfo.getCardCountry()));//????????????
			sheet.addCell( new Label(col++, row, eInfo.getCardState()));//?????????/???
			sheet.addCell( new Label(col++, row, eInfo.getCardAddress()));//????????????
		}
		book.write();
		book.close();
	}
	
	/**
	 * ??????????????????
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
				throw new ServiceException("????????????????????????");
			}
			if (criteria.getCondition().get("formDate") == null
					|| "".equals(criteria.getCondition().get("formDate"))) {
				throw new ServiceException("????????????????????????");
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
	 * ??????????????????
	 * @param resp
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value="/exportMerchantReportForms")
	public void exportMerchantReportForms(HttpServletResponse resp,MerchantReportFormsInfo form) throws Exception {
//		resp.setContentType("application/vnd.ms-excel");
		String fileName="";
		if(form.getFormType()==1){
			fileName=form.getMerNo()+"??????????????????"+form.getStartDate().substring(0, 7);
		}else if(form.getFormType()==2){
			fileName=form.getMerNo()+"??????????????????"+form.getStartDate().replaceAll("-", "")+"-"+form.getEndDate().replaceAll("-", "");
		}else{
			fileName=form.getMerNo()+"??????????????????"+form.getStartDate();
		}
//		resp.setHeader("Content-disposition", "attachment;filename="
//				+ fileName+".xls");
		if(form.getFormType()==1){//???????????????
			this.createMonthForm(resp, form,fileName);
		}else if(form.getFormType()==2){//???????????????
			this.createWeekForm(resp, form,fileName);
		}else{//???????????????
			this.createDayForm(resp, form,fileName);
		}
	}
	
	/**
	 * ???????????????
	 * @param resp
	 * @param form
	 * @throws Exception 
	 */
	private void createDayForm(HttpServletResponse resp,MerchantReportFormsInfo form,String fileName) throws Exception {
		//???????????????
		File file = new File(this.getClass().getResource("").getPath()+File.separator+"dayFormModel.xlsx");
		ExcelTool tool = new ExcelTool();
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file)); 
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		{//?????????????????????
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
				unnormaldata[i][index++] = info.getMerNo();// ???????????????
				unnormaldata[i][index++] = info.getTerNo();// ???????????????
				unnormaldata[i][index++] = info.getTradeNo();// ?????????
				unnormaldata[i][index++] = info.getOrderNo();// ?????????
				unnormaldata[i][index++] = info.getCurrency() + " "
						+ info.getAmount();// ?????????
				unnormaldata[i][index++] = info.getWebsite();// ????????????
				unnormaldata[i][index++] = df.format(info.getDoDate());// ?????????
				unnormaldata[i][index++] = df.format(info.getDoEndDate());// ????????????
				unnormaldata[i][index++] = info.getDoReason();// ????????????
				index = 0;
			}
			tool.exportSimpleExcel(wb, sheet,"????????????????????? ", "?????????????????????", null, unnormaldata, 3);
		}
		{//?????????????????????
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
				unnormaldata[i][index++] = info.getMerNo();// ???????????????
				unnormaldata[i][index++] = info.getTerNo();// ???????????????
				unnormaldata[i][index++] = info.getTradeNo();// ?????????
				unnormaldata[i][index++] = info.getOrderNo();// ?????????
				unnormaldata[i][index++] = info.getRuleNameList();
				unnormaldata[i][index++] = info.getTradeList();// ????????????
				unnormaldata[i][index++] =detail.getMerBusCurrency()+" "+ detail.getMerTransAmount();
				unnormaldata[i][index++] = "????????????";// ????????????
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
			tool.exportSimpleExcel(wb, sheet,"?????????????????? ", "??????????????????", null, unnormaldata, 2);
		}
		renderFile(tool.toOutputRespond(), fileName+".xlsx",resp);
	}
	
	/**
	 * ???????????????
	 * @param resp
	 * @param form
	 * @throws Exception 
	 */
	private void createWeekForm(HttpServletResponse resp,MerchantReportFormsInfo form,String fileName) throws Exception {
		//???????????????
		File file = new File(this.getClass().getResource("").getPath()+File.separator+"weekFormModel.xlsx");
		ExcelTool tool = new ExcelTool();
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file)); 
		{//????????????
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
				unnormaldata[i][index++] = info.getMerNo();// ???????????????
				unnormaldata[i][index++] = info.getTerNo();// ???????????????
				unnormaldata[i][index++] = info.getTotalCount();// ?????????
				unnormaldata[i][index++] = info.getTransCount();// ?????????
				unnormaldata[i][index++] = info.getTransCurrency() + " "
						+ info.getMerSettleAmount();// ?????????
				unnormaldata[i][index++] = info.getTransSuccessCount();// ????????????
				unnormaldata[i][index++] = info.getTransCurrency() + " "
						+ info.getMerSettleSuccessAmount();// ?????????
				unnormaldata[i][index++] = info.getTransFailureCount();// ????????????
				unnormaldata[i][index++] = info.getDuplicateCount();// ????????????
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
							/ Integer.parseInt(info.getTotalCount())) + "%";// ?????????
				}
				unnormaldata[i][index++] = info.getSuccessRate();
				unnormaldata[i][index++] = info.getDishonorRate();
				unnormaldata[i][index++] = info.getRefundRate();
				unnormaldata[i][index++] = info.getComplaintRate();
				index = 0;
				
			}
			tool.exportSimpleExcel(wb, sheet,"???????????? ", "????????????", null, unnormaldata, 3);
		}
		{//???????????????
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
				unnormaldata[i][index++] = info.getDescription();// ???????????????
				unnormaldata[i][index++] = info.getCount();// ???????????????
				unnormaldata[i][index++] = nf.format(Double.parseDouble(info.getDecRate())*100)+"%";
				index = 0;
				
			}
			tool.exportSimpleExcel(wb, sheet,"??????????????? ", "???????????????", null, unnormaldata, 2);
		}
		{//???????????????
			Criteria criteria=getCriteria();
			criteria.put("description", "??????????????????????????????");
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
				unnormaldata[i][index++] = info.getDisReason();// ???????????????
				unnormaldata[i][index++] = info.getDisCount()+"";// ???????????????
				unnormaldata[i][index++] = nf.format(info.getDisRate()*100)+"%";
				index = 0;
			}
			tool.exportSimpleExcel(wb, sheet,"??????????????? ", "???????????????", null, unnormaldata, 10);
		}
		{//??????????????????
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
				unnormaldata[i][index++] = info.getRespMsg();// ???????????????
				unnormaldata[i][index++] = info.getCountRespMsg();// ???????????????
				unnormaldata[i][index++] = nf.format(Double.parseDouble(info.getRespMsgRate())*100)+"%";
				unnormaldata[i][index++] = info.getRemark();
				unnormaldata[i][index++] = info.getSuggest();
				index = 0;
			}
			tool.exportSimpleExcel(wb, sheet,"?????????????????? ", "??????????????????", null, unnormaldata, 2);
		}
		{//??????????????????
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
				unnormaldata[i][index++] = info.getReason();// ???????????????
				unnormaldata[i][index++] = info.getRiskCount()+"";// ???????????????
				unnormaldata[i][index++] = nf.format(info.getRiskRate()*100)+"%";
				index = 0;
			}
			tool.exportSimpleExcel(wb, sheet,"?????????????????? ", "??????????????????", null, unnormaldata, 2);
		}
		{//??????????????????
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
				unnormaldata[i][index++] = info.getReason();// ???????????????
				unnormaldata[i][index++] = info.getRiskCount()+"";// ???????????????
				unnormaldata[i][index++] = nf.format(info.getRiskRate()*100)+"%";
				index = 0;
			}
			tool.exportSimpleExcel(wb, sheet,"?????????????????? ", "??????????????????", null, unnormaldata, 2);
		}
		renderFile(tool.toOutputRespond(), fileName+".xlsx",resp);
	}
	/**
	 * ???????????????
	 * @param resp
	 * @param form
	 * @throws Exception 
	 */
	private void createMonthForm(HttpServletResponse resp,MerchantReportFormsInfo form,String fileName) throws Exception{
		//???????????????
		File file = new File(this.getClass().getResource("").getPath()
				+ File.separator + "monthFormModel.xlsx");
		ExcelTool tool = new ExcelTool();
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
		{// ????????????
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
				unnormaldata[i][index++] = info.getMerNo();// ???????????????
				unnormaldata[i][index++] = info.getTerNo();// ???????????????
				unnormaldata[i][index++] = info.getTotalCount();// ?????????
				unnormaldata[i][index++] = info.getTransCount();// ?????????
				unnormaldata[i][index++] = info.getTransCurrency() + " "
						+ info.getMerSettleAmount();// ?????????
				unnormaldata[i][index++] = info.getTransSuccessCount();// ????????????
				unnormaldata[i][index++] = info.getTransCurrency() + " "
						+ info.getMerSettleSuccessAmount();// ?????????
				unnormaldata[i][index++] = info.getTransFailureCount();// ????????????
				unnormaldata[i][index++] = info.getDuplicateCount();// ????????????
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
							/ Integer.parseInt(info.getTotalCount())) + "%";// ?????????
				}
				unnormaldata[i][index++] = info.getSuccessRate();
				unnormaldata[i][index++] = info.getDishonorRate();
				unnormaldata[i][index++] = info.getRefundRate();
				unnormaldata[i][index++] = info.getComplaintRate();
				index = 0;

			}
			tool.exportSimpleExcel(wb, sheet, "???????????? ", "????????????", null,
					unnormaldata, 3);
		}
		
		List<String> fileNames=new ArrayList<String>();
//		{// ??????????????????
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
//					Integer count=(Integer) dataset.getValue("??????");
//					dataset.setValue("??????", count+Integer.parseInt(info.getTransCount()));
//				}else{
//					dataset.setValue(info.getCardCountry()==null?"??????":info.getCardCountry(), Integer.parseInt(info.getTransCount()));
//				}
//				unnormaldata[i][index++] =  info.getCardCountry();// ??????
//				unnormaldata[i][index++] =  info.getTransCount();// ??????????????????
//				unnormaldata[i][index++] =  info.getTransCurrency() + " "
//						+ info.getTransAmount();// ????????????
//				unnormaldata[i][index++] =  info.getTransSuccessCount();// ????????????
//				unnormaldata[i][index++] =  info.getTransCurrency() + " "
//						+ info.getTransSuccessAmount();// ????????????
//				unnormaldata[i][index++] =  info.getTransFailureCount();// ????????????
//				unnormaldata[i][index++] =  info.getTransRiskCount() + "";// ???????????????
//				unnormaldata[i][index++] =  info.getDishonorCount();// ????????????
//				unnormaldata[i][index++] =  info.getRefundCount();// ????????????
//				unnormaldata[i][index++] =  info.getComplaintCount();// ????????????
//				unnormaldata[i][index++] =  info.getSuccessRate();// ?????????
//				unnormaldata[i][index++] =  info.getDishonorRate();// ?????????
//				unnormaldata[i][index++] =  info.getRefundRate();// ?????????
//				unnormaldata[i][index++] =  info.getComplaintRate();// ?????????
//				unnormaldata[i][index++] =  info.getTransRate();// ????????????
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
//			tool.exportSimpleExcel(wb, sheet, "?????????????????? ", "??????????????????", null,
//					unnormaldata, 2);
//		}
		{// ??????????????????
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
			dataset.setValue("??????", 0);
			for (int i = 0; i < list.size(); i++) {
				com.gateway.countAnalysis.model.CountAnalysis info = list.get(i);
				if(i>5){
					Number count= dataset.getValue("??????");
					dataset.setValue("??????", count.doubleValue()+Double.parseDouble(info.getTransCount()));
				}else{
					dataset.setValue(info.getCardCountry()==null?"??????":info.getCardCountry(), Integer.parseInt(info.getTransCount()));
				}
				unnormaldata[i][index++] =  info.getCardCountry();// ??????
				unnormaldata[i][index++] =  info.getTransCount();// ??????????????????
				unnormaldata[i][index++] =  info.getTransCurrency() + " "
						+ info.getTransAmount();// ????????????
				unnormaldata[i][index++] =  info.getTransSuccessCount();// ????????????
				unnormaldata[i][index++] =  info.getTransCurrency() + " "
						+ info.getTransSuccessAmount();// ????????????
				unnormaldata[i][index++] =  info.getTransFailureCount();// ????????????
				unnormaldata[i][index++] =  info.getDuplicateCount();// ??????????????????
				unnormaldata[i][index++] =  info.getTransRiskCount() + "";// ???????????????
				unnormaldata[i][index++] =  info.getDishonorCount();// ????????????
				unnormaldata[i][index++] =  info.getRefundCount();// ????????????
				unnormaldata[i][index++] =  info.getComplaintCount();// ????????????
				unnormaldata[i][index++] =  info.getSuccessRate();// ?????????
				unnormaldata[i][index++] =  info.getDishonorRate();// ?????????
				unnormaldata[i][index++] =  info.getRefundRate();// ?????????
				unnormaldata[i][index++] =  info.getComplaintRate();// ?????????
				unnormaldata[i][index++] =  info.getTransRate();// ????????????
				index = 0;
			}
			if(dataset.getValue("??????").doubleValue()==0){
				dataset.remove("??????");
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
			tool.exportSimpleExcel(wb, sheet, "?????????????????? ", "??????????????????", null,
					unnormaldata, 2);
		}
		{//??????????????????
			XSSFSheet sheet = wb.getSheetAt(2);
			//???????????????
			XYSeries timeseries = new XYSeries("??????");  
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
			JFreeChart chart=this.createProfile(timeseries,"??????????????????","??????","??????",max);
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
	        // ???????????????????????????
	        List<TransTimeCountInfo> list1=formMgrService.queryTransTimeCountInfo(getCriteria());
	        String[][] unnormaldata = new String[list1.size()][2];
	        int index=0;
	        for (int i = 0; i < list1.size(); i++) {
	        	TransTimeCountInfo info = list1.get(i);
				unnormaldata[i][index++] =  info.getHourStr();// ?????????
				unnormaldata[i][index++] =  info.getCount()+"";// ????????????
				index = 0;

			}
	        
	        tool.exportSimpleExcel(wb, sheet, "????????????????????? ", "?????????????????????", null,
					unnormaldata, 2);
			
		}
		{//?????????????????? //??????CNY????????????
			
			Criteria criteria=getCriteria();
			criteria.getCondition().put("currency", "CNY");
			List<TransAmountCountInfo> list =formMgrService.queryTransAmountCountInfo(criteria);
			
			XSSFSheet sheet = wb.getSheetAt(3);
			String[][] unnormaldata = new String[list.size()][2];
			int index = 0;

			for (int i = 0; i < list.size(); i++) {
				TransAmountCountInfo info = list.get(i);
				unnormaldata[i][index++] =  info.getRange();// ??????
				unnormaldata[i][index++] =  info.getCount()+"";// ??????????????????
				index = 0;
			}
			tool.exportSimpleExcel(wb, sheet, "???????????????????????? ", "????????????????????????", null,
					unnormaldata, 2);
		}
		{//?????????????????? //??????USD????????????
			
			Criteria criteria=getCriteria();
			criteria.getCondition().put("currency", "USD");
			List<TransAmountCountInfo> list =formMgrService.queryTransAmountCountInfo(criteria);
			
			XSSFSheet sheet = wb.getSheetAt(3);
			String[][] unnormaldata = new String[list.size()][2];
			int index = 0;

			for (int i = 0; i < list.size(); i++) {
				TransAmountCountInfo info = list.get(i);
				unnormaldata[i][index++] =  info.getRange();// ??????
				unnormaldata[i][index++] =  info.getCount()+"";// ??????????????????
				index = 0;
			}
			tool.exportSimpleExcel(wb, sheet, "???????????????????????? ", "????????????????????????", null,
					unnormaldata, 16);
		}
		renderFile(tool.toOutputRespond(), fileName+".xlsx",resp);
		for(String name:fileNames){
			File file1=new File(name);
			file1.delete();
			
		}
	}
	
	/**
	 * ??????????????????
	 * 
	 */
	private JFreeChart pieChart(DefaultPieDataset dataset){  
        DefaultPieDataset data = dataset;  
        JFreeChart chart = ChartFactory.createPieChart3D("??????????????????",data,true,false,false);  
      //???????????????  
        PiePlot pieplot = (PiePlot) chart.getPlot();  
        DecimalFormat df = new DecimalFormat("0.00%");//????????????DecimalFormat????????????????????????????????????  
        NumberFormat nf = NumberFormat.getNumberInstance();//????????????NumberFormat??????  
        StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0}  {2}", nf, df);//??????StandardPieSectionLabelGenerator??????  
        pieplot.setLabelGenerator(sp1);//???????????????????????????  
      
    //????????????????????????????????????  
        pieplot.setNoDataMessage("???????????????");  
        pieplot.setCircular(false);  
        pieplot.setLabelGap(0.02D);  
      
        pieplot.setIgnoreNullValues(true);//?????????????????????  
        pieplot.setIgnoreZeroValues(true);//?????????????????????  
        chart.getTitle().setFont(new Font("??????",Font.BOLD,50));//??????????????????  
        PiePlot piePlot= (PiePlot) chart.getPlot();//????????????????????????  
        piePlot.setLabelFont(new Font("??????",Font.BOLD,25));//????????????  
        chart.getLegend().setItemFont(new Font("??????",Font.BOLD,25));  
        return chart;
	} 
	/**
	 * ???????????????
	 * @return
	 */
	 private  JFreeChart createProfile(XYSeries timeseries,String title,String xName,String yName,int yMax) {  //?????????????????????????????????????????????  
		  XYSeriesCollection timeseriescollection = new XYSeriesCollection();  
          timeseriescollection.addSeries(timeseries);  
          XYDataset xydataset=timeseriescollection;
	        JFreeChart jfreechart = ChartFactory.createXYLineChart(title, xName, yName, xydataset,PlotOrientation.VERTICAL,true,true,true) ;
	        		//ChartFactory.createTimeSeriesChart(title, xName, yName,xydataset, true, true, true);  
	        XYPlot xyplot = (XYPlot) jfreechart.getPlot();  
	        NumberAxis dateaxis = (NumberAxis) xyplot.getDomainAxis();  
	        dateaxis .setTickUnit(new NumberTickUnit(1));
	        dateaxis.setLabelFont(new Font("??????",Font.BOLD,24));         //??????????????????  
	        dateaxis.setTickLabelFont(new Font("??????",Font.BOLD,22));  //????????????  
	        NumberAxis rangeAxis=(NumberAxis) xyplot.getRangeAxis();//????????????  
	        int step=1;
	        if(yMax>1000){
	        	step=20;
	        }else if(yMax>100){
	        	step=5;
	        }
	        rangeAxis .setTickUnit(new NumberTickUnit(step));
	        rangeAxis.setLabelFont(new Font("??????",Font.BOLD,25));  
	        jfreechart.getLegend().setItemFont(new Font("??????", Font.BOLD, 25));  
	        jfreechart.getTitle().setFont(new Font("??????",Font.BOLD,50));//??????????????????  
         return jfreechart;  
     }  
	 private String renderFile(byte[] bytes, String fileName,HttpServletResponse resp) throws Exception {
			resp.addHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("GB2312"), "ISO-8859-1"));
			return render(bytes, "application/vnd.ms-excel",resp);
		}
		protected String render(byte[] bytes, String contentType,HttpServletResponse resp) throws Exception {
			// ???????????????,????????????,https ie???????????????
			resp.setContentType(contentType);
			OutputStream out = new BufferedOutputStream(resp.getOutputStream());
			out.write(bytes);
			out.flush();
			out.close();
			return null;
		}
		
		/**
		 * ??????????????????
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
		 * ????????????????????????
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
			WritableSheet sheet = book.createSheet("????????????", 0);
			String[] headerName = { "?????????", "?????????","????????????","???????????????"};
			// ????????????
			for (int index = 0; index < headerName.length; index++) {
				Label label = new Label(index, 0, headerName[index]);
				sheet.addCell(label);
			}
			for (int row = 1; row <= list.size(); row++) {
				int col = 0;
				MerchantCapitalInfo info = list.get(row-1);
				sheet.addCell( new Label(col++, row, info.getMerNo()));
				sheet.addCell( new Label(col++, row, info.getTerNo()));
				sheet.addCell( new Label(col++, row, info.getAccountType()==0?"????????????":"???????????????"));
				sheet.addCell( new Label(col++, row, info.getCurrency()+" "+info.getTotalAmount()));
			}
			book.write();
			book.close();
		}
		
		/**
		 * ????????????????????????
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
		 * ????????????????????????
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
			WritableSheet sheet = book.createSheet("????????????", 0);
			String[] headerName = { "id","?????????","?????????","????????????","????????????","??????","??????",
					"??????????????????","????????????"
					,"????????????","????????????"};
			// ????????????
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
				sheet.addCell(new Label(col++, row, info.getAccountType()==0?"????????????":"???????????????"));
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
		 * ??????????????????
		 * @return
		 */
		@RequestMapping(value="/getListMerchant")
		public String getListMerchant(){
			Criteria criteria=getCriteria();
			if(!"get".equalsIgnoreCase(getRequest().getMethod())){
				if(getLogAccount().getUserRefMerNo()==null || "".equals(getLogAccount().getUserRefMerNo())){
					criteria.getCondition().put("merNos", "111");//?????????????????????????????????
				}else{
					criteria.getCondition().put("merNos", getLogAccount().getUserRefMerNo());
				}
				PageInfo<MerchantInfo> page = merchantMgrService.getListMerchant(criteria);
				getRequest().setAttribute("page",page);
			}
			return "sellmgr/merchantList";
		}
		/**
		 * ??????????????????
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
		 * ??????????????????????????????
		 * @param merNo
		 * @return
		 */
		@RequestMapping(value="/queryMerchantTransSettingBYmerNo")
		public String queryMerchantTransSettingBYmerNo(String merNo){
			List<TransSettingInfo> list = merchantMgrService.queryTransSettingInfo(merNo);
			getRequest().setAttribute("list", list);
			return "merchantmgr/transSetting";
		}
		
		/** ????????????????????? */
		@RequestMapping(value = "/queryTransHourCount")
		public String queryTransHourCount() {
			Criteria criteria = getCriteria();
			if(getLogAccount().getUserRefMerNo()==null || "".equals(getLogAccount().getUserRefMerNo())){
				criteria.getCondition().put("merNos", "111");//?????????????????????????????????
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
		 * ????????????????????????
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
			sheet = book.createSheet("??????????????????", 0);
			String[] headerName = new String[] { "?????????", "?????????", "????????????", "??????",
					"??????????????????", "????????????", "????????????", "????????????", "?????????"};
			// ????????????
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
				sheet.addCell(new Label(col++, row, info.getMerNo()));// ???????????????
				sheet.addCell(new Label(col++, row, info.getTerNo()));
				if (info.getCountYear() == null) {
					sheet.addCell(new Label(col++, row, ""));// ????????????
				} else if ("0".equals(info.getCountYear())) {
					sheet.addCell(new Label(col++, row, "????????????"));// ????????????
				} else {
					sheet.addCell(new Label(col++, row, info.getCountYear() + "-"
							+ info.getCountMonth()));// ????????????
				}
				if (getCriteria().getCondition().get("cardType") == null
						|| "".equals(getCriteria().getCondition().get("cardType"))) {
					sheet.addCell(new Label(col++, row, "??????"));// ????????????
				} else {
					sheet.addCell(new Label(col++, row, getCriteria()
							.getCondition().get("cardType").toString()));// ????????????
				}
				sheet.addCell(new Label(col++, row, info.getSuccessCount()));// ?????????
				sheet.addCell(new Label(col++, row, info.getCurrency() + " "
						+ info.getSuccessAmount()));// ?????????
				sheet.addCell(new Label(col++, row, info.getDisCount()));// ????????????
				sheet.addCell(new Label(col++, row, info.getCurrency() + " "
						+ info.getDisAmount()));// ?????????
				if (null == info.getSuccessCount()
						|| "0".equals(info.getSuccessCount())) {
					sheet.addCell(new Label(col++, row, "0.00%"));// ????????????
				} else {
					sheet.addCell(new Label(col++, row, nf.format(Double
							.parseDouble(info.getDisCount())
							/ Double.parseDouble(info.getSuccessCount()) * 100)
							+ "%"));// ????????????
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
				criteria.getCondition().put("merNos", "111");//?????????????????????????????????
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
			WritableSheet sheet = book.createSheet("????????????", 0);
			String[] headerName = { "?????????", "?????????", "?????????", "??????", "????????????", "????????????",
					"????????????", "??????????????????", "????????????", "????????????????????????", "CPD??????", "??????????????????", "?????????",
					"????????????", "?????????", "????????????", "????????????", "????????????", "????????????", "????????????", "????????????",
					"????????????", "????????????", "????????????", "????????????", "???????????????", "????????????????????????", "??????????????????",
					"????????????", "??????", "??????", "??????", "IP", "????????????", "????????????", "?????????/ ???",
					"????????????", "??????", "????????????", "????????????", "????????????", "?????????/???", "????????????" };
			// ????????????
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
					sheet.addCell(new Label(col++, row, transInfo.getMerNo()));// ?????????
					sheet.addCell(new Label(col++, row, transInfo.getTradeNo()));// ???????????????
					sheet.addCell(new Label(col++, row, transInfo.getOrderNo()));// ?????????
					sheet.addCell(new Label(col++, row, transInfo.getPayWebSite()));// ??????
					sheet.addCell(new Label(col++, row, transInfo
							.getMerBusCurrency()
							+ " "
							+ transInfo.getMerTransAmount()));// ????????????
					sheet.addCell(new Label(col++, row, BaseDataListener
							.getStringColumnKey("gw_transtype_info",
									transInfo.getRespCode() + "", "????????????")));// ????????????
					sheet.addCell(new Label(col++, row, transInfo.getTransDate()));// ????????????
					sheet.addCell(new Label(col++, row, info.getComplaintDate()));// ??????????????????
					sheet.addCell(new Label(col++, row, info
							.getComplaintTypeValue()));// ????????????
					sheet.addCell(new Label(col++, row, info.getDeadline()));// ????????????????????????
					sheet.addCell(new Label(col++, row, info.getCPDDate()));// CPD??????
					sheet.addCell(new Label(col++, row, BaseDataListener
							.getStringColumnKey("COMPLAINT_STATUS_1",
									info.getStatus() + "", "????????????")));// ??????????????????
					sheet.addCell(new Label(col++, row, info.getCreatedBy()));// ?????????
					sheet.addCell(new Label(col++, row,
							info.getCreatedDate() != null ? new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss").format(info
									.getCreatedDate()) : ""));// ????????????
					sheet.addCell(new Label(col++, row, info.getLastUpdateBy()));// ?????????
					sheet.addCell(new Label(
							col++,
							row,
							info.getLastUpdateDate() != null ? new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss").format(info
									.getLastUpdateDate()) : ""));// ????????????
					sheet.addCell(new Label(col++, row, transInfo.getCurrencyName()));// ????????????
					sheet.addCell(new Label(col++, row, info.getIsFake() == 0 ? "???"
							: "???"));// ????????????
					sheet.addCell(new Label(col++, row, transInfo
							.getDishonorStatus()));// ????????????
					sheet.addCell(new Label(col++, row, transInfo
							.getDishonorAmount()));// ????????????
					sheet.addCell(new Label(col++, row, transInfo.getRefundStatus()));// ????????????
					sheet.addCell(new Label(col++, row, transInfo.getRefundAmount()));// ????????????
					sheet.addCell(new Label(col++, row, transInfo.getFrozenStatus()));// ????????????
					sheet.addCell(new Label(col++, row, transInfo.getFrozenAmount()));// ????????????
					sheet.addCell(new Label(col++, row, Tools
							.parseWebInfoToResourceType(transInfo.getWebInfo())));// ????????????
					sheet.addCell(new Label(col++, row, transInfo.getTerNo()));// ?????????
					sheet.addCell(new Label(col++, row, transInfo.getAcquirer()));// ????????????????????????
					sheet.addCell(new Label(col++, row, transInfo
							.getSixAndFourCardNo()));// ??????????????????
					if (!org.springframework.util.StringUtils.isEmpty(transInfo
							.getGoodsInfoByte())) {// ????????????
						sheet.addCell(new Label(col++, row, new String(transInfo
								.getGoodsInfoByte(), "utf-8")));
						System.out
								.println("===="
										+ new String(transInfo.getGoodsInfoByte(),
												"utf-8"));
					} else {
						sheet.addCell(new Label(col++, row, ""));
					}
					sheet.addCell(new Label(col++, row, transInfo.getCardFullName()));// ??????
					sheet.addCell(new Label(col++, row, transInfo.getEmail()));// ??????
					sheet.addCell(new Label(col++, row, transInfo
							.getCardFullPhone()));// ??????
					sheet.addCell(new Label(col++, row, transInfo.getIpAddress()));// IP
					sheet.addCell(new Label(col++, row, transInfo.getCardCountry()));// ????????????
					sheet.addCell(new Label(col++, row, transInfo.getGrCountry()));// ????????????
					sheet.addCell(new Label(col++, row, transInfo.getGrState()));// ?????????/
																					// ???
					sheet.addCell(new Label(col++, row, transInfo.getGrAddress()));// ????????????
					sheet.addCell(new Label(col++, row, transInfo.getGrZipCode()));// ??????
					sheet.addCell(new Label(col++, row, transInfo.getIogistics()));// ????????????
					sheet.addCell(new Label(col++, row, transInfo.getTrackNo()));// ????????????
					sheet.addCell(new Label(col++, row, transInfo.getCardCountry()));// ????????????
					sheet.addCell(new Label(col++, row, transInfo.getCardState()));// ?????????/???
					sheet.addCell(new Label(col++, row, transInfo.getCardAddress()));// ????????????
				}
			}
			book.write();
			book.close();
		}
		
		/**
		 * ???????????????????????????
		 * @return
		 */
		@RequestMapping(value="/goAddSellRefSells")
		public String goAddSellRefSells(){
			List<UserInfo> users=sellMgrService.queryAllUsersInfo();
			getRequest().setAttribute("users", users);
			return "sellmgr/addSellRefSells";
		}
		
		/**
		 * ?????????????????????
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
					return ajaxDoneSuccess("????????????");
				}
			}else{
				return ajaxDoneError("????????????");
			}
			return ajaxDoneError("????????????");
		}
		
		/**
		 * ???????????????????????????
		 * @return
		 */
		@RequestMapping(value="/goUpdateSellRefSells")
		public String goUpdateSellRefSells(String sellMgr){
			//??????????????????
			List<UserInfo> users=sellMgrService.queryAllUsersInfo();
			getRequest().setAttribute("users", users);
			for(UserInfo userInfo : users) {
				if(sellMgr.equals(userInfo.getUserName())){
					getRequest().setAttribute("sellMgrRealName", userInfo.getRealName());
				}
			}
			getRequest().setAttribute("sellMgr", sellMgr);
			//????????????????????????????????????
			List<SellRefSellsInfo> sellRefSellsInfoList=sellMgrService.querySellRefuSellsBySellMgr(sellMgr);
			StringBuffer stringBuffer=new StringBuffer();
			for (SellRefSellsInfo sellRefSellsInfo : sellRefSellsInfoList) {
				stringBuffer.append(sellRefSellsInfo.getSell()+"#");
			}
			getRequest().setAttribute("sells", stringBuffer.toString());
			return "sellmgr/updateSellRefSells";
		}
		
		/**
		 * ?????????????????????
		 * @param info
		 * @return
		 */
		@RequestMapping("/updateSellRefSells")
		public ModelAndView updateSellRefSells(SellRefSellsInfo info){
			info.setCreateBy(getLogAccount().getRealName());
			int i = sellMgrService.updateSellRefSellsInfo(info);
			if(i>0){
				return ajaxDoneSuccess("????????????");
			}
			return ajaxDoneError("????????????");
		}
		
		/**
		 * ?????????????????????????????????
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
		 * ?????????????????????
		 * @param sellMgrs
		 * @return
		 */
		@RequestMapping("/deleteSellRefSellsInfo")
		public ModelAndView deleteSellRefSellsInfo(String[] sellMgrs){
			int count=sellMgrService.deleteSellRefSellsInfo(sellMgrs);
			if(count>0){
				return ajaxDoneSuccess("????????????");
			}
			return ajaxDoneError("????????????");
		}
		
		/** ???????????????????????? */
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

		/** ?????????????????????????????? */
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
			WritableSheet sheet = book.createSheet("????????????????????????", 0);
			String[] headerName = { "??????", "??????????????????", "????????????", "????????????", "????????????", "????????????",
					"???????????????", "????????????", "????????????", "????????????", "?????????", "?????????", "?????????", "?????????",
					"????????????" };
			// ????????????
			for (int index = 0; index < headerName.length; index++) {
				Label label = new Label(index, 0, headerName[index]);
				sheet.addCell(label);
			}
			for (int row = 1; row <= list.size(); row++) {
				int col = 0;
				CountAnalysis info = list.get(row - 1);
				sheet.addCell(new Label(col++, row, info.getCardCountry()));// ??????
				sheet.addCell(new Label(col++, row, info.getTransCount()));// ??????????????????
				sheet.addCell(new Label(col++, row, info.getTransCurrency() + " "
						+ info.getTransAmount()));// ????????????
				sheet.addCell(new Label(col++, row, info.getTransSuccessCount()));// ????????????
				sheet.addCell(new Label(col++, row, info.getTransCurrency() + " "
						+ info.getTransSuccessAmount()));// ????????????
				sheet.addCell(new Label(col++, row, info.getTransFailureCount()));// ????????????
				sheet.addCell(new Label(col++, row, info.getTransRiskCount() + ""));// ???????????????
				sheet.addCell(new Label(col++, row, info.getDishonorCount()));// ????????????
				sheet.addCell(new Label(col++, row, info.getRefundCount()));// ????????????
				sheet.addCell(new Label(col++, row, info.getComplaintCount()));// ????????????
				sheet.addCell(new Label(col++, row, info.getSuccessRate()));// ?????????
				sheet.addCell(new Label(col++, row, info.getDishonorRate()));// ?????????
				sheet.addCell(new Label(col++, row, info.getRefundRate()));// ?????????
				sheet.addCell(new Label(col++, row, info.getComplaintRate()));// ?????????
				sheet.addCell(new Label(col++, row, info.getTransRate()));// ????????????
			}
			book.write();
			book.close();
		}
		
		/**
		 * ??????????????????
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
		 * ????????????????????????
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
				for(String str:new String[]{"????????????","????????????","OA????????????","????????????","????????????",
						"?????????","????????????","?????????","????????????","?????????","???????????????","????????????","???????????????","???????????????",
						"????????????","????????????","????????????","?????????","????????????","????????????","?????????"}){
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
		 * ???????????????????????????
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
				return ajaxDoneSuccess("????????????");
			}else{
				return ajaxDoneError("????????????");
			}
		}
		
}
