package com.gateway.faffmgr.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jpos.iso.ISOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.BaseDataListener;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excel.BIRow;
import com.gateway.common.excel.BISheet;
import com.gateway.common.excel.BIWorkbook;
import com.gateway.common.utils.Tools;
import com.gateway.faffmgr.model.BankCostInfo;
import com.gateway.faffmgr.model.BankCutPaymentInfo;
import com.gateway.faffmgr.model.CapitalFlowInfo;
import com.gateway.faffmgr.model.IncomeCapitalInfo;
import com.gateway.faffmgr.model.IncomeInfo;
import com.gateway.faffmgr.model.MerchantFeeInfo;
import com.gateway.faffmgr.model.ReceiveBankCostInfo;
import com.gateway.faffmgr.model.ReceiveIncomeInfo;
import com.gateway.faffmgr.model.RefundInfo;
import com.gateway.faffmgr.model.RiskCapitalPoolInfo;
import com.gateway.faffmgr.model.SalesPerformanceInfo;
import com.gateway.faffmgr.model.TransCheckForQuery;
import com.gateway.faffmgr.model.TransCheckInfo;
import com.gateway.faffmgr.model.TransCheckedCount;
import com.gateway.faffmgr.model.TransCheckedInfo;
import com.gateway.faffmgr.service.FaffService;
/**
 * ????????????
 * @author gaoyuan
 *
 */
@Controller
@RequestMapping(value="/faffmgr")
public class FaffController extends BaseController {
	
	@Autowired
	private FaffService faffService;
	
	@RequestMapping(value="/showUploadFilePage")
	public String showUploadFilePage(){
		return "faffmgr/uploadCheckFile";
	}
	
	/**
	 * ??????????????????
	 * @param resp
	 * @param type
	 */
	@RequestMapping(value = "/downloadFaffModel")
	public void downloadFaffModel(HttpServletResponse resp,String type){
		if("2".equals(type)){
			ArrayList<String> strArray = new ArrayList<String> ();
			strArray.add("??????????????????????????????????????????????????????????????????????????????????????????");
			strArray.add("HP1599227530825,CNY,10.00,1.00,2015-12-12");
			strArray.add("HP1599227530825,CNY,10.00,1.00,2015-12-12");
			downloadFile(resp, "faffModel.txt", "modelType:sales", strArray);
			return;
		}
		if("3".equals(type)){
			ArrayList<String> strArray = new ArrayList<String> ();
			strArray.add("????????????????????????????????????????????????????????????????????????");
			strArray.add("HP1599227530825,CNY,10.00,2015-12-12");
			strArray.add("HP1599227530825,CNY,10.00,2015-12-12");
			downloadFile(resp, "faffModel.txt", "modelType:dis", strArray);
			return;
		}
		if("4".equals(type)){
			ArrayList<String> strArray = new ArrayList<String> ();
			strArray.add("????????????????????????????????????????????????????????????????????????");
			strArray.add("HP1599227530825,CNY,10.00,2015-12-12");
			strArray.add("HP1599227530825,CNY,10.00,2015-12-12");
			downloadFile(resp, "faffModel.txt", "modelType:refund", strArray);
		}
		if("5".equals(type)){
			ArrayList<String> strArray = new ArrayList<String> ();
			strArray.add("???????????????????????????????????????????????????????????????????????????????????????");
			strArray.add("HP1599227530825,CNY,10.00,2015-12-12");
			strArray.add("HP1599227530825,CNY,10.00,2015-12-12");
			downloadFile(resp, "faffModel.txt", "modelType:bond", strArray);
		}
	}
	
	/**
	 * ??????????????????
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws ISOException 
	 */
	@RequestMapping(value="/acceptCheckFile")
	public ModelAndView acceptCheckFile(DefaultMultipartHttpServletRequest request) throws Exception{
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		if(null != files){
			log.info("?????????"+files.size()+"????????????");
			StringBuffer sb = new StringBuffer();//???????????????
			StringBuffer transFail=new StringBuffer();
			Map<String,TransCheckInfo> successCheck=new HashMap<String,TransCheckInfo>();
			List<TransCheckedInfo> successChecked=new ArrayList<TransCheckedInfo>();
			for(MultipartFile file :files){
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(),"GBK"));//????????????BufferedReader??????????????????
				String line = br.readLine();
				int length = Tools.replaceBlank(line).length();//?????????
				log.info("????????????line:"+line+"??????????????????"+length);
				String [] strInfo = line.split(":");
				String name = "";
				if(!StringUtils.isEmpty(strInfo) && 2 == strInfo.length){
					name = strInfo[1];
				}
				if ("sales".equals(name)) {
					getRequest().getSession().setAttribute("faffUploadType", "sales");
				}
				if ("dis".equals(name)) {
					getRequest().getSession().setAttribute("faffUploadType", "dis");
				}
				if ("refund".equals(name)) {
					getRequest().getSession().setAttribute("faffUploadType", "refund");
				}
				if ("bond".equals(name)) {
					getRequest().getSession().setAttribute("faffUploadType", "bond");
				}
				line = br.readLine();//???????????????
				line = br.readLine();//?????????3???
				int lineCount = 2;//??????3????????????
				//???????????????????????????
				
				Criteria criteria = getCriteria();
				criteria.getCondition().put("ischecked", "0");
				while(null != line){
					log.info("line:"+line);
					lineCount++;
					line=line.replaceAll("\\s", "");
					String [] fileds = line.split(",");
					int i=0;
					if ("sales".equals(name)) {
						if(fileds.length==5){
							TransCheckInfo info=new TransCheckInfo();
							info.setTradeNo(fileds[i++]);
							info.setBankSettleCurrency(fileds[i++]);
							info.setBankSettleAmount(fileds[i++]);
							info.setBankFee(fileds[i++]);
							info.setSettleDate(fileds[i++]);
							info.setSettleType("sales");
							TransCheckedInfo checkedInfo=faffService.queryCheckedTransInfo(info);
							if(null!=checkedInfo){
								if(!"1".equals(checkedInfo.getAccess())){
									transFail.append("???"+lineCount+"???:"+info.getTradeNo()+"?????????;");
								}
								if(!"1".equals(checkedInfo.getIsChecked())){
									transFail.append("???"+lineCount+"???:"+info.getTradeNo()+"?????????;");
								}
								checkedInfo.setFaffAmount(info.getBankSettleAmount());
								successCheck.put(info.getTradeNo(),info);
								successChecked.add(checkedInfo);
							}else{
								sb.append("???"+lineCount+"?????????:"+line+" ??????????????????;");
							}
						}else{
							sb.append("???"+lineCount+"?????????:"+line+";");
						}
					}else if ("dis".equals(name)) {
						if(fileds.length==4){
							TransCheckInfo info=new TransCheckInfo();
							info.setTradeNo(fileds[i++]);
							info.setBankSettleCurrency(fileds[i++]);
							info.setBankSettleAmount(fileds[i++]);
							info.setSettleDate(fileds[i++]);
							info.setSettleType("dis");
							TransCheckedInfo checkedInfo=faffService.queryCheckedTransInfo(info);
							if(null!=checkedInfo){
								if(Integer.parseInt(checkedInfo.getIsDis())<=0){
									transFail.append("???"+lineCount+"???:"+info.getTradeNo()+"?????????;");
								}
								checkedInfo.setFaffAmount(info.getBankSettleAmount());
								successCheck.put(info.getTradeNo(),info);
								successChecked.add(checkedInfo);
							}else{
								sb.append("???"+lineCount+"?????????:"+line+" ??????????????????;");
							}
						}else{
							sb.append("???"+lineCount+"?????????:"+line+";");
						}
					}else if ("refund".equals(name)) {
						if(fileds.length==4){
							TransCheckInfo info=new TransCheckInfo();
							info.setTradeNo(fileds[i++]);
							info.setBankSettleCurrency(fileds[i++]);
							info.setBankSettleAmount(fileds[i++]);
							info.setSettleDate(fileds[i++]);
							info.setSettleType("refund");
							TransCheckedInfo checkedInfo=faffService.queryCheckedTransInfo(info);
							if(null!=checkedInfo){
								if(Integer.parseInt(checkedInfo.getIsRefund())<=0){
									transFail.append("???"+lineCount+"???:"+info.getTradeNo()+"?????????;");
								}
								checkedInfo.setFaffAmount(info.getBankSettleAmount());
								successCheck.put(info.getTradeNo(),info);
								successChecked.add(checkedInfo);
							}else{
								sb.append("???"+lineCount+"?????????:"+line+" ??????????????????;");
							}
						}else{
							sb.append("???"+lineCount+"?????????:"+line+";");
						}
					}else if ("bond".equals(name)) {
						if(fileds.length==4){
							TransCheckInfo info=new TransCheckInfo();
							info.setTradeNo(fileds[i++]);
							info.setBankSettleCurrency(fileds[i++]);
							info.setBankSettleAmount(fileds[i++]);
							info.setSettleDate(fileds[i++]);
							info.setSettleType("bond");
							TransCheckedInfo checkedInfo=faffService.queryCheckedTransInfo(info);
							if(null!=checkedInfo){
								if(Integer.parseInt(checkedInfo.getIsFaff())<=0){
									transFail.append("???"+lineCount+"???:"+info.getTradeNo()+"?????????????????????;");
								}
								successCheck.put(info.getTradeNo(),info);
								successChecked.add(checkedInfo);
							}else{
								sb.append("???"+lineCount+"?????????:"+line+" ??????????????????;");
							}
						}else{
							sb.append("???"+lineCount+"?????????:"+line+";");
						}
					}
					line = br.readLine();
					lineCount++;
				}
			}
			Double totalAmount=0.0;
			for(TransCheckedInfo i:successChecked){
				totalAmount+=Double.parseDouble(i.getFaffAmount()==null?"0.0":i.getFaffAmount());
			}
			getRequest().getSession().setAttribute("totalAmount", totalAmount);
			getRequest().getSession().setAttribute("successCheck", successCheck);
			getRequest().getSession().setAttribute("successChecked", successChecked);
			getRequest().getSession().setAttribute("transFail", transFail);
			getRequest().getSession().setAttribute("sb", sb + "");
		}
		return ajaxDoneSuccess("???????????????????????????");
	}
	
	/**
	 * ?????????????????????
	 * @return
	 */
	@RequestMapping(value ="/goQueryAcceptCheckPage")
	public String goQueryAcceptCheckPage(){
//		List<TransInfo> list = (List<TransInfo>)getRequest().getSession().getAttribute("list");
//		getRequest().getSession().removeAttribute("list");
//		String sb = (String)getRequest().getSession().getAttribute("sb");
//		getRequest().getSession().removeAttribute("sb");
//		getRequest().setAttribute("list", list);
//		getRequest().setAttribute("sb", sb);
		return "faffmgr/acceptCheckPageList";
	}
	
	/** ???????????? */
	@ResponseBody
	@RequestMapping(value ="/batchAcceptCheckUpdateByTradeNos")
	public ModelAndView batchAcceptCheckUpdateByTradeNos(String [] tradeNos){
		int totalCount = tradeNos.length;
		@SuppressWarnings("unchecked")
		Map<String,TransCheckInfo> successCheck=(Map<String, TransCheckInfo>) getRequest().getSession().getAttribute("successCheck");
		int count=0;
		Set<String> set=new TreeSet<String>();
		for(String tradeNo:tradeNos){
			set.add(tradeNo);
		}
		for(String tradeNo:set){
			TransCheckInfo info=successCheck.get(tradeNo);
			info.setCreateBy(getLogAccount().getRealName());
			count+=faffService.insertFaffUploadDateInfo(info);
		}
		return ajaxDoneSuccess("??????????????????" + totalCount +" ;??????????????????" + count);
	}
	
	@RequestMapping("/listTransCheckedInfo")
	public String listTransCheckedInfo(){
		Criteria criteria=getCriteria();
		if(getRequest().getMethod().equalsIgnoreCase("get")){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String transDateStart=sdf.format(date);
			criteria.getCondition().put("transDateStart", transDateStart);
			criteria.getCondition().put("transDateEnd", transDateStart);
			criteria.getCondition().put("transSettleStart", transDateStart);
			criteria.getCondition().put("transSettleEnd", transDateStart);
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<TransCheckForQuery> page=faffService.queryTransCheckedInfoList(criteria);
			List<TransCheckedCount> list=faffService.queryTransCheckedCount(criteria);
			getRequest().setAttribute("list", list);
			getRequest().setAttribute("page", page);
		}
		return "faffmgr/listTransCheckedInfo";
	}
	
	
	
	/**
	 *????????????????????????
	 * @throws Exception 
	 */
	@RequestMapping(value="/exportTransCheckedInfo")
	public void exportTransCheckedInfo(HttpServletResponse resp) throws Exception{
		OutputStream os = null;
		os = resp.getOutputStream();
		resp.reset(); // ???????????????????????????
		resp.setHeader("Content-Disposition", "attachment; filename=" + "transList.xls");
		resp.setContentType("application/octet-stream; charset=utf-8");
		Criteria criteria=getCriteria();
		BIWorkbook bw=new BIWorkbook();
		BISheet bs = bw.addSheet();
		BIRow br_0 = bs.addRow();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(String str:new String[]{"?????????","?????????","?????????","????????????","????????????","??????","?????????",
				"??????????????????","??????????????????","??????????????????","??????????????????","??????????????????","????????????",
				"????????????","????????????","????????????","????????????????????????","????????????????????????","????????????????????????",
				"????????????????????????","????????????","????????????","????????????","????????????","????????????????????????","????????????????????????"
				,"????????????????????????","????????????????????????","??????????????????","??????????????????"}){
			br_0.addCell().setCellValue(str, null);
		}
			List<TransCheckForQuery> list = faffService.queryTransCheckedInfoAll(criteria);
			for (int row = 1; row <= list.size(); row++) {
				BIRow br_1 = bs.addRow();
				TransCheckForQuery info = list.get(row-1);
				br_1.addCell().setCellValue(info.getMerNo(),null);
				br_1.addCell().setCellValue(info.getTerNo(),null);
				br_1.addCell().setCellValue(info.getTradeNo(),null);
				br_1.addCell().setCellValue(info.getTransDate()!=null?sdf.format(info.getTransDate()):"",null);
				br_1.addCell().setCellValue("????????????",null);
				br_1.addCell().setCellValue(info.getBankName(),null);
				br_1.addCell().setCellValue(info.getCurrencyName(),null);
				br_1.addCell().setCellValue(info.getBankCurrency(),null);
				br_1.addCell().setCellValue(info.getBankTransAmount(),null);
				br_1.addCell().setCellValue(info.getBankSettleCurrency(),null);
				br_1.addCell().setCellValue(info.getSalesSettleAmount(),null);
				br_1.addCell().setCellValue(sdf.format(info.getSettleDate()),null);
				br_1.addCell().setCellValue("1".equals(info.getTransRefund())?"?????????":"?????????",null);
				br_1.addCell().setCellValue(info.getBankCurrency(),null);
				br_1.addCell().setCellValue(info.getRefundAmount()+"",null);
				if("1".equals(info.getTransRefund())){
					br_1.addCell().setCellValue(info.getExceptionDate()!=null?sdf.format(info.getExceptionDate()):"",null);
				}else{
					br_1.addCell().setCellValue("",null);
				}
				br_1.addCell().setCellValue(null!=info.getRefundSettleAmount()&&(0.0!=Double.parseDouble(info.getRefundSettleAmount()))?"?????????":"?????????",null);
				br_1.addCell().setCellValue(info.getBankSettleCurrency(),null);
				br_1.addCell().setCellValue(info.getRefundSettleAmount(),null);
				br_1.addCell().setCellValue("",null);
				br_1.addCell().setCellValue("1".equals(info.getTransDishonor())?"?????????":"?????????",null);
				br_1.addCell().setCellValue(info.getBankCurrency(),null);
				br_1.addCell().setCellValue(info.getDisAmount(),null);
				if("1".equals(info.getTransDishonor())){
					br_1.addCell().setCellValue(info.getExceptionDate()!=null?sdf.format(info.getExceptionDate()):"",null);
				}else{
					br_1.addCell().setCellValue("",null);
				}
				br_1.addCell().setCellValue(null!=info.getDisSettleAmount()&&(0.0!=Double.parseDouble(info.getDisSettleAmount()))?"?????????":"?????????",null);
				br_1.addCell().setCellValue(info.getBankSettleCurrency(),null);
				br_1.addCell().setCellValue(info.getDisSettleAmount(),null);
				br_1.addCell().setCellValue("",null);
				br_1.addCell().setCellValue("1".equals(info.getIsComp())?"?????????":"?????????",null);
				br_1.addCell().setCellValue("1".equals(info.getIsSp())?"????????????":"???????????????",null);
				
			}
			
		bw.workbook.write(os);	
		os.flush();
		os.close();
	}
	/**
	 * ???????????????????????????/????????????
	 * @param tradeNo
	 * @return
	 */
	@RequestMapping("/refundDescInfo")
	public String refundDescInfo(){
		List<RefundInfo> page=faffService.queryRefundInfoByTradeNo(getCriteria());
		getRequest().setAttribute("page", page);
		return "faffmgr/refundDescInfo";
	}
	
	/**
	 * ?????????????????????????????????/????????????
	 * @param tradeNo
	 * @return
	 */
	@RequestMapping("/bankCutPaymentDescInfo")
	public String bankCutPaymentDescInfo(){
		List<BankCutPaymentInfo> page=faffService.querybankCutPaymentDescInfoByTradeNo(getCriteria());
		getRequest().setAttribute("settleType", getCriteria().getCondition().get("settleType"));
		getRequest().setAttribute("page", page);
		return "faffmgr/bankCutPaymentDescInfo";
	}
	
	/**
	 * ???????????????????????????????????????
	 * @param id
	 * @return
	 */
	@RequestMapping("/goUpdateBankCutAmount")
	public String goUpdateBankCutAmount(String id){
		getRequest().setAttribute("id", id);
		return "faffmgr/updateBankCutAmount";
	}
	/**
	 * ??????????????????????????????
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateBankCutAmount")
	public ModelAndView updateBankCutAmount(String id,String amount){
		int i=faffService.updateBankCutAmount(id,amount);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	/**
	 * ?????? : ??????????????????
	 * 
	 */
	@RequestMapping(value="/searchIncomeInfo")
	public String searchGwIncomeInfo(){
		PageInfo<IncomeInfo> page = faffService.queryIncomeInfoList(getCriteria());
		List<IncomeCapitalInfo> list = faffService.queryIncomeAmount(getCriteria());
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("page", page);
		return "faffmgr/incomePageList";
	}
	/**
	 * ?????? : ??????????????????
	 * @return id ????????????ID
	 */
	@RequestMapping(value="/showUpdateIncomePage")
	public String showUpdateIncomeInfoPage(String id){
		if(id!=null && id!=""){
			IncomeInfo income = faffService.queryIncomeInfoById(id);
			getRequest().setAttribute("income", income);
			return "faffmgr/updateIncomeInfo";
		}
		return "faffmgr/addIncomeInfo";
	}
	/**
	 * ?????? : ????????????????????????
	 * @param form ????????????
	 * @return ????????????
	 */
	@RequestMapping(value="/saveIncomeInfo")
	public ModelAndView saveIncomeInfo(ReceiveIncomeInfo form){
		form.setCreateBy(getLogAccount().getRealName());
		int i = faffService.saveIncomeInfo(form);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	/**
	 * ?????? : ??????ID??????????????????
	 * @param form ????????????
	 * @return ????????????
	 */
	@RequestMapping(value="/updateIncomeInfo")
	public ModelAndView updateIncomeInfo(ReceiveIncomeInfo form){
		form.setLastModifyBy(getLogAccount().getRealName());
		int i = faffService.updateIncomeInfoById(form);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	/**
	 * ?????? : ??????????????????
	 * @param response
	 */
	@RequestMapping(value="/exportIncomeExcel")
	public void exportIncomeIncomeInfo(HttpServletResponse response){
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			response.setHeader("Content-Disposition", "attachment; filename=" + "incomelist.xls");
			response.setContentType("application/octet-stream; charset=utf-8");
			Criteria criteria=getCriteria();
			BIWorkbook bw=new BIWorkbook();
			BISheet bs = bw.addSheet();
			BIRow br_0 = bs.addRow();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(String str:new String[]{"????????????id","????????????","????????????","????????????","??????","????????????","????????????","??????","?????????","????????????","?????????","????????????"}){
				br_0.addCell().setCellValue(str, null);
			}
				List<IncomeInfo> list = faffService.queryIncomeInfoListList(criteria);
				if(list!=null){
					for (int row = 1; row <= list.size(); row++) {
						BIRow br_1 = bs.addRow();
						IncomeInfo info = list.get(row-1);
						br_1.addCell().setCellValue(info.getId(),null);
						br_1.addCell().setCellValue(info.getBankName(),null);
						if(info.getType()==0){
							br_1.addCell().setCellValue("??????",null);
						}
						if(info.getType()==1){
							br_1.addCell().setCellValue("??????",null);
						}
						br_1.addCell().setCellValue(info.getIncomeDesc(),null);
						br_1.addCell().setCellValue(info.getCurrency()!=null?info.getCurrency():"",null);
						br_1.addCell().setCellValue(info.getAmount()!=null?info.getAmount():"0.00",null);
						br_1.addCell().setCellValue(info.getIncomeDate()!=null?sdf.format(info.getIncomeDate()):"",null);
						br_1.addCell().setCellValue(info.getRemark()!=null?info.getRemark():"",null);
						br_1.addCell().setCellValue(info.getCreateBy()!=null?info.getCreateBy():"",null);
						br_1.addCell().setCellValue(info.getCreateDate()!=null?sdf.format(info.getCreateDate()):"",null);
						br_1.addCell().setCellValue(info.getLastModifyBy()!=null?info.getLastModifyBy():"",null);
						br_1.addCell().setCellValue(info.getLastmodifyDate()!=null?sdf.format(info.getLastmodifyDate()):"",null);
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
	
	@RequestMapping("/listRiskCapitalPoolCount")
	public String listRiskCapitalPoolCount(){
		PageInfo<RiskCapitalPoolInfo> page=faffService.queryRiskCapitalPoolInfos(getCriteria());
		List<RiskCapitalPoolInfo> total=faffService.queryRiskCapitalPoolInfosTotal(getCriteria());
		getRequest().setAttribute("total", total);
		getRequest().setAttribute("page", page);
		return "faffmgr/listRiskCapitalPoolCount";
	}
	
	
	
	/**
	 * ?????? : ???????????????????????????????????????
	 * @param response
	 */
	@RequestMapping(value="/exportRiskCapitalPoolInfos")
	public void exportRiskCapitalPoolInfos(HttpServletResponse resp) throws Exception{
		OutputStream os = null;
		os = resp.getOutputStream();
		resp.reset(); // ???????????????????????????
		resp.setHeader("Content-Disposition", "attachment; filename=" + "transList.xls");
		resp.setContentType("application/octet-stream; charset=utf-8");
		Criteria criteria=getCriteria();
		BIWorkbook bw=new BIWorkbook();
		BISheet bs = bw.addSheet();
		BIRow br_0 = bs.addRow();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(String str:new String[]{"??????","????????????","????????????","????????????","????????????"}){
			br_0.addCell().setCellValue(str, null);
		}
			List<RiskCapitalPoolInfo> list = faffService.exportRiskCapitalPoolInfos(criteria);
			for (int row = 1; row <= list.size(); row++) {
				BIRow br_1 = bs.addRow();
				RiskCapitalPoolInfo info = list.get(row-1);
				br_1.addCell().setCellValue(info.getMoneyDate(),null);
				if(info.getType()==0){
					br_1.addCell().setCellValue("??????",null);
				}
				if(info.getType()==1){
					br_1.addCell().setCellValue("??????",null);
				}
				br_1.addCell().setCellValue(BaseDataListener.getStringColumnKey("CASHTYPE", info.getCashType()+"", "??????"),null);
				br_1.addCell().setCellValue(info.getCurrency()+" "+ info.getAmount(),null);
				br_1.addCell().setCellValue(info.getIncomeDate()!=null?sdf.format(info.getIncomeDate()):"",null);
			}
			
		bw.workbook.write(os);	
		os.flush();
		os.close();
	}
	/**
	 * ?????? : ??????????????????????????????
	 * @return
	 */
	@RequestMapping(value="/searchBankCoseList")
	public String searchBankCostInfo(){
		PageInfo<BankCostInfo> page = faffService.queryBankCostInfoList(getCriteria());
		getRequest().setAttribute("page", page);
		return "faffmgr/bankCostPageList";
	}
	/**
	 * ??????  : ????????????????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value="/showUpdateBankCostInfoPage")
	public String showUpdateBankCostInfoPage(String id){
		BankCostInfo bankcostInfo = new BankCostInfo();
		if(id!=null && id!=""){
			bankcostInfo = faffService.queryBankCostInfoById(id);
		}
		getRequest().setAttribute("info", bankcostInfo);
		return "faffmgr/updateBankCostInfo";
	}
	/**
	 * ?????? : ?????????????????????????????????????????????
	 * @param form
	 */
	@RequestMapping(value="/saveBankCostInfo")
	public ModelAndView updateBankCostInfo(ReceiveBankCostInfo form){
		int i = -1;
		if(form.getId()!=0){
			form.setLastModifyBy(getLogAccount().getRealName());
			i = faffService.updateBankCostInfoById(form);
		}else{
			form.setCreateBy(getLogAccount().getRealName());
			i = faffService.saveBankCostInfo(form);
		}
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	/**
	 * ?????? : ??????????????????????????????
	 */
	@RequestMapping(value="/exportBankCostInfo")
	public void exportBankCostInfo(HttpServletResponse response){
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			response.setHeader("Content-Disposition", "attachment; filename=" + "incomelist.xls");
			response.setContentType("application/octet-stream; charset=utf-8");
			Criteria criteria=getCriteria();
			BIWorkbook bw=new BIWorkbook();
			BISheet bs = bw.addSheet();
			BIRow br_0 = bs.addRow();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(String str:new String[]{"??????????????????id","????????????","??????","??????","????????????","??????","??????","??????","????????????","????????????","?????????","?????????","????????????"}){
				br_0.addCell().setCellValue(str, null);
			}
				List<BankCostInfo> list = faffService.queryBankCostInfoListList(criteria);
				if(list!=null){
					for (int row = 1; row <= list.size(); row++) {
						BIRow br_1 = bs.addRow();
						BankCostInfo info = list.get(row-1);
						br_1.addCell().setCellValue(info.getId(),null);
						br_1.addCell().setCellValue(info.getCostType(),null);
						br_1.addCell().setCellValue(info.getBankName()!=null?info.getBankName():"",null);
						br_1.addCell().setCellValue(info.getCurrencyName()!=null?info.getCurrencyName():"",null);
						br_1.addCell().setCellValue(info.getType()!=null?info.getType():"",null);
						br_1.addCell().setCellValue(info.getCount(),null);
						br_1.addCell().setCellValue(info.getAmount()!=null?info.getAmount():"0.00",null);
						br_1.addCell().setCellValue(info.getRemark()!=null?info.getRemark():"",null);
						br_1.addCell().setCellValue(info.getSettleDate()!=null?info.getSettleDate():"", null);
						br_1.addCell().setCellValue(info.getCreateDate()!=null?sdf.format(info.getCreateDate()):"",null);
						br_1.addCell().setCellValue(info.getCreateBy()!=null?info.getCreateBy():"",null);
						br_1.addCell().setCellValue(info.getLastModifyBy()!=null?info.getLastModifyBy():"",null);
						br_1.addCell().setCellValue(info.getLastModifyDate()!=null?sdf.format(info.getLastModifyDate()):"",null);
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
	 * ?????? : ???????????????
	 * @return
	 */
	@RequestMapping(value="/searchCapitalFlowList")
	public String searchCapitalFlowList(){
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			Calendar c=Calendar.getInstance();
			int year=c.get(Calendar.YEAR);
			int month=c.get(Calendar.MONTH)+1;
			criteria.getCondition().put("year", year+"");
			criteria.getCondition().put("month", month+"");
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<CapitalFlowInfo> page = faffService.queryCapitalFlowList(criteria);
			getRequest().setAttribute("page", page);
		}
		return "faffmgr/searchCapitalFlowList";
	}
	
	
	/**
	 * ?????? : ??????????????????????????????
	 * @return
	 */
	@RequestMapping(value="/searchMerchantFeeInfo")
	public String searchMerchantFeeInfo(){
		PageInfo<MerchantFeeInfo> page = faffService.queryMerchantFeeInfo(getCriteria());
		getRequest().setAttribute("page", page);
		return "faffmgr/searchMerchantFeeInfo";
	}
	/**
	 * ??????  : ????????????????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value="/showUpdateMerchantFeeInfoPage")
	public String showUpdateMerchantFeeInfoPage(String id){
		MerchantFeeInfo merchantFeeInfo = new MerchantFeeInfo();
		if(id!=null && id!=""){
			merchantFeeInfo = faffService.queryMerchantFeeInfoById(id);
		}
		getRequest().setAttribute("info", merchantFeeInfo);
		return "faffmgr/updateMerchantFeeInfoInfo";
	}
	/**
	 * ?????? : ?????????????????????????????????????????????
	 * @param form
	 */
	@RequestMapping(value="/updateMerchantFeeInfoInfo")
	public ModelAndView updateMerchantFeeInfoInfo(MerchantFeeInfo form){
		int i = -1;
		if(form.getId()!=0){
			form.setLastModifyBy(getLogAccount().getRealName());
			i = faffService.updateMerchantFeeInfoById(form);
		}else{
			form.setCreateBy(getLogAccount().getRealName());
			i = faffService.saveMerchantFeeInfo(form);
		}
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	/**
	 * ?????? : ??????????????????????????????
	 */
	@RequestMapping(value="/exportMerchantFeeInfoInfo")
	public void exportMerchantFeeInfoInfo(HttpServletResponse response){
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			response.setHeader("Content-Disposition", "attachment; filename=" + "incomelist.xls");
			response.setContentType("application/octet-stream; charset=utf-8");
			Criteria criteria=getCriteria();
			BIWorkbook bw=new BIWorkbook();
			BISheet bs = bw.addSheet();
			BIRow br_0 = bs.addRow();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(String str:new String[]{"????????????","?????????","?????????","????????????","????????????",
					"??????","????????????","????????????","?????????","????????????","?????????"}){
				br_0.addCell().setCellValue(str, null);
			}
				List<MerchantFeeInfo> list = faffService.queryMerchantFeeInfoListAll(criteria);
				if(list!=null){
					for (int row = 1; row <= list.size(); row++) {
						BIRow br_1 = bs.addRow();
						MerchantFeeInfo info = list.get(row-1);
						br_1.addCell().setCellValue(info.getFeeType(),null);
						br_1.addCell().setCellValue(info.getMerNo(),null);
						br_1.addCell().setCellValue(info.getTerNo(),null);
						br_1.addCell().setCellValue(info.getIncomeType()==0?"??????":"??????",null);
						br_1.addCell().setCellValue(info.getCurrency()+" "+info.getAmount(),null);
						br_1.addCell().setCellValue(info.getRemark()!=null?info.getRemark():"",null);
						br_1.addCell().setCellValue(sdf.format(info.getSettleDate()),null);
						br_1.addCell().setCellValue(sdf.format(info.getCreateDate()),null);
						br_1.addCell().setCellValue(info.getCreateBy(),null);
						br_1.addCell().setCellValue(sdf.format(info.getCreateDate()),null);
						br_1.addCell().setCellValue(info.getCreateBy(),null);
						br_1.addCell().setCellValue(info.getLastModifyDate()!=null?sdf.format(info.getLastModifyDate()):"",null);
						br_1.addCell().setCellValue(info.getLastModifyBy()!=null?info.getLastModifyBy():"",null);
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
	 * ?????? : ??????????????????
	 * @return
	 */
	@RequestMapping(value="/searchPaymentProfitList")
	public String searchPaymentProfitList(){
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			Calendar c=Calendar.getInstance();
			int year=c.get(Calendar.YEAR);
			int month=c.get(Calendar.MONTH)+1;
			criteria.getCondition().put("year", year+"");
			criteria.getCondition().put("month", month+"");
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<CapitalFlowInfo> page = faffService.queryPaymentProfitList(criteria);
			getRequest().setAttribute("page", page);
		}
		return "faffmgr/searchPaymentProfitList";
	}
	
	/**
	 * ??????????????????
	 */
	@RequestMapping(value="/salesPerformanceInfoList")
	public String salesPerformanceInfoList(){
		Criteria criteria = getCriteria();
		if("post".equalsIgnoreCase(getRequest().getMethod())){
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
		return "faffmgr/listSalesPerformanceInfo";
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
			BIWorkbook bw=new BIWorkbook();
			BISheet bs = bw.addSheet();
			BIRow br_0 = bs.addRow();
			for(String str:new String[]{"????????????","????????????","OA????????????","????????????","????????????",
					"?????????","????????????","?????????","????????????","?????????","???????????????","????????????","???????????????","???????????????",
					"????????????","????????????","????????????","?????????","????????????","????????????","?????????","??????","??????","????????????"}){
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
						br_1.addCell().setCellValue(info.getBankName(),null);
						br_1.addCell().setCellValue(info.getCurrencyName(),null);
						br_1.addCell().setCellValue(BaseDataListener.getStringColumnKey("TER_PRODUCT_TYPE", info.getProductType(), info.getProductType()),null);
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
	
}
