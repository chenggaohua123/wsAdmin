package com.gateway.form.web;

import java.awt.Font;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestMapping;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.ServiceException;
import com.gateway.countAnalysis.model.DisDesc;
import com.gateway.countAnalysis.model.RiskPercent;
import com.gateway.countAnalysis.model.TransRecord;
import com.gateway.countAnalysis.service.CountAnalysisService;
import com.gateway.form.model.CountAnalysis;
import com.gateway.form.model.MerchantReportFormsInfo;
import com.gateway.form.model.TransAmountCountInfo;
import com.gateway.form.model.TransTimeCountInfo;
import com.gateway.form.service.FormMgrService;
import com.gateway.riskmgr.model.ExportRiskTransInfo;
import com.gateway.riskmgr.service.RiskMgrService;
import com.gateway.rpt.util.ExcelTool;
import com.gateway.suspicious.model.SuspiciousOrderListInfo;
import com.gateway.suspicious.service.SuspiciousManageService;
import com.gateway.transmgr.model.TransDetailInfo;
import com.gateway.transmgr.service.TransMgrService;


@Controller
@RequestMapping(value = "/formmgr")
public class FormMgrController extends BaseController {

	@Autowired
	private FormMgrService formMgrService;
	
	@Autowired
	private CountAnalysisService countAnalysisService;
	
	@Autowired
	private RiskMgrService riskMgrService;
	
	@Autowired
	private SuspiciousManageService suspiciousManageService;
	
	@Autowired
	private TransMgrService transMgrService;
	
	private static String path="/tmp/";
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
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<MerchantReportFormsInfo> page = formMgrService
					.queryMerchantReportForms(criteria);
			getRequest().setAttribute("page", page);
		}
		return "formmgr/listMerchantReportForms";
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
			List<CountAnalysis> list = formMgrService
					.queryCountAnalysisInfoAll(getCriteria());
			XSSFSheet sheet = wb.getSheetAt(0);
			String [][] unnormaldata = new String[list.size()][18];
			int index = 0;
			
			NumberFormat nf=NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			nf.setMinimumFractionDigits(2);
			
			for(int i = 0 ; i < list.size();i++){
				CountAnalysis info = list.get(i);
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
			List<CountAnalysis> list = formMgrService
					.queryCountAnalysisInfoAll(getCriteria());
			XSSFSheet sheet = wb.getSheetAt(0);
			String[][] unnormaldata = new String[list.size()][18];
			int index = 0;

			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			nf.setMinimumFractionDigits(2);

			for (int i = 0; i < list.size(); i++) {
				CountAnalysis info = list.get(i);
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
//			String png=path+"temp"+date.getTime()+".png";
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
			String png=path+"temp"+date.getTime()+".png";
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
			List<CountAnalysis> list = formMgrService.queryTransCountInfoForDay(getCriteria());
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
				for(CountAnalysis cou:list){
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
			String png=path+"temp"+date1.getTime()+"Profile.png";
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
}
