package com.gateway.vipsalesmgr.web;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.BaseDataListener;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excel.BIRow;
import com.gateway.common.excel.BISheet;
import com.gateway.common.excel.BIWorkbook;
import com.gateway.vipsalesmgr.model.VipSalesConsumeInfo;
import com.gateway.vipsalesmgr.model.VipSalesInfo;
import com.gateway.vipsalesmgr.model.VipSalesLogInfo;
import com.gateway.vipsalesmgr.service.VipSalesMgrService;

@Controller
@RequestMapping(value="/vipSales")
public class VipSalesMgrController extends BaseController {

	@Resource
	private VipSalesMgrService vipSalesMgrService;

	public VipSalesMgrService getVipSalesMgrService() {
		return vipSalesMgrService;
	}

	public void setVipSalesMgrService(VipSalesMgrService vipSalesMgrService) {
		this.vipSalesMgrService = vipSalesMgrService;
	}
	
	/**
	 * 实现:固定周期消费,信息查询
	 */
	@RequestMapping(value="/listVipSalesInfo")
	public String listVipSalesInfo(){
		Criteria criteria = getCriteria();
		if("post".equalsIgnoreCase(getRequest().getMethod())){
			PageInfo<VipSalesInfo> page = vipSalesMgrService.queryVipSalesInfoList(criteria);
			getRequest().setAttribute("page", page);
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			criteria.getCondition().put("createdDateStart", sdf.format(new Date()));
			criteria.getCondition().put("createdDateEnd", sdf.format(new Date()));
		}
		getRequest().setAttribute("form", criteria.getCondition());
		return "vipsales/listVipSalesInfo";
	}
	
	/**
	 * 实现: 查询交易变更信息
	 * @param uniqueID
	 * @return 查询结果
	 */
	@RequestMapping(value="/listVipSalesHistoryInfo")
	public String ListVipSalesHistoryInfo(String uniqueID){
		getRequest().setAttribute("uniqueID", uniqueID);
		Criteria criteria = getCriteria();
		PageInfo<VipSalesLogInfo> page = vipSalesMgrService.queryVipSalesLogInfoList(criteria);
		getRequest().setAttribute("page", page);
		return "vipsales/listVipSalesHistoryInfo";
	}
	
	@RequestMapping(value="/exportVipSalesInfo")
	public void exportVipSalesInfo(HttpServletResponse resp) throws JsonGenerationException, JsonMappingException, IOException{
		OutputStream os = null;
		os = resp.getOutputStream();
		resp.reset(); // 来清除首部的空白行
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		resp.setHeader("Content-Disposition", "attachment; filename=" + "vipsales.xlsx");
		resp.setContentType("application/octet-stream; charset=utf-8");
		Criteria criteria=getCriteria();
		BIWorkbook bw=new BIWorkbook();
		BISheet bs = bw.addSheet();
		BIRow br_0 = bs.addRow();
		for(String str:new String[]{"商户号","终端号","卡号","姓名","发卡行名称","账单地址","邮箱","电话","网址",
				  "ID号","金额","扣款周期","创建日期","更新日期"}){
			br_0.addCell().setCellValue(str, null);
		}
			List<VipSalesInfo> list = vipSalesMgrService.queryExportVipSalesInfoList(criteria);
			for (int row = 1; row <= list.size(); row++) {
				BIRow br_1 = bs.addRow();
				VipSalesInfo info = list.get(row-1);
				br_1.addCell().setCellValue(info.getMerNo(),null);
				br_1.addCell().setCellValue(info.getTerNo(),null);
				br_1.addCell().setCellValue((info.getStart()!=null?info.getStart():"")+"****"+(info.getEnd()!=null?info.getStart():""),null);
				br_1.addCell().setCellValue(info.getName(),null);
				br_1.addCell().setCellValue(info.getBankName(),null);
				br_1.addCell().setCellValue(info.getBillAddress(),null);
				br_1.addCell().setCellValue(info.getEmail(),null);
				br_1.addCell().setCellValue(info.getPhone(),null);
				br_1.addCell().setCellValue(info.getWebsite(),null);
				br_1.addCell().setCellValue(info.getUniqueID(),null);
				br_1.addCell().setCellValue((info.getCurrency()!=null?info.getCurrency():"")+" "+(info.getAmount()!=null?info.getAmount().toString():""),null);
				if(info.getSalesCycle()==0){
					br_1.addCell().setCellValue("非周期扣费",null);
				}else{
					br_1.addCell().setCellValue(info.getSalesCycle()+"天",null);
				}
				br_1.addCell().setCellValue(info.getCreateDate()!=null?sdf.format(info.getCreateDate()):"",null);
				br_1.addCell().setCellValue(info.getLastModifyDate()!=null?sdf.format(info.getLastModifyDate()):"",null);
			}
			
		bw.workbook.write(os);	
		os.flush();
		os.close();
	}
	
	@RequestMapping(value="/exportVipSalesLogInfo")
	public void exportVipSalesLogInfo(String uniqueID, HttpServletResponse resp) throws JsonGenerationException, JsonMappingException, IOException{
		OutputStream os = null;
		os = resp.getOutputStream();
		resp.reset(); // 来清除首部的空白行
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		resp.setHeader("Content-Disposition", "attachment; filename=" + "vipsalesLog.xlsx");
		resp.setContentType("application/octet-stream; charset=utf-8");
		BIWorkbook bw=new BIWorkbook();
		BISheet bs = bw.addSheet();
		BIRow br_0 = bs.addRow();
		for(String str:new String[]{"商户号","终端号","卡号","姓名","发卡行名称","账单地址","邮箱","电话","网址",
				  "ID号","金额","扣款周期","更新日期"}){
			br_0.addCell().setCellValue(str, null);
		}
			List<VipSalesLogInfo> list = vipSalesMgrService.queryVipSalesLogInfoListByUniqueID(uniqueID);
			for (int row = 1; row <= list.size(); row++) {
				BIRow br_1 = bs.addRow();
				VipSalesLogInfo info = list.get(row-1);
				br_1.addCell().setCellValue(info.getMerNo(),null);
				br_1.addCell().setCellValue(info.getTerNo(),null);
				br_1.addCell().setCellValue((info.getStart()!=null?info.getStart():"")+"****"+(info.getEnd()!=null?info.getStart():""),null);
				br_1.addCell().setCellValue(info.getName(),null);
				br_1.addCell().setCellValue(info.getBankName(),null);
				br_1.addCell().setCellValue(info.getBillAddress(),null);
				br_1.addCell().setCellValue(info.getEmail(),null);
				br_1.addCell().setCellValue(info.getPhone(),null);
				br_1.addCell().setCellValue(info.getWebsite(),null);
				br_1.addCell().setCellValue(info.getUniqueID(),null);
				br_1.addCell().setCellValue((info.getCurrency()!=null?info.getCurrency():"")+" "+(info.getAmount()!=null?info.getAmount().toString():""),null);
				if(info.getSalesCycle()==0){
					br_1.addCell().setCellValue("非周期扣费",null);
				}else{
					br_1.addCell().setCellValue(info.getSalesCycle()+"天",null);
				}
				br_1.addCell().setCellValue(info.getCreateDate()!=null?sdf.format(info.getCreateDate()):"",null);
			}
			
		bw.workbook.write(os);	
		os.flush();
		os.close();
	}
	
	/**
	 * 实现: 消费查询
	 * @param criteria
	 * @return 查询结果
	 */
	@RequestMapping(value="/listVipSalesConsume")
	public String vipSalesConsume(){
		Criteria criteria = getCriteria();
		if("post".equalsIgnoreCase(getRequest().getMethod())){
			PageInfo<VipSalesConsumeInfo> page = vipSalesMgrService.queryVipSalesConsumeInfoList(criteria);
			getRequest().setAttribute("page", page);
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			criteria.getCondition().put("transDateStart", sdf.format(new Date()));
			criteria.getCondition().put("transDateEnd", sdf.format(new Date()));
		}
		getRequest().setAttribute("form", criteria.getCondition());
		return "vipsales/listVipSalesConsumeInfo";
	}
	
	/**
	 * 实现: 导出消费查询
	 * @param criteria
	 * @return 查询结果
	 */
	@RequestMapping(value="/exportVipSalesConsume")
	public void exportVipSalesConsume(HttpServletResponse resp) throws JsonGenerationException, JsonMappingException, IOException{
		OutputStream os = null;
		os = resp.getOutputStream();
		resp.reset(); // 来清除首部的空白行
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		resp.setHeader("Content-Disposition", "attachment; filename=" + "vipsalesConsume.xlsx");
		resp.setContentType("application/octet-stream; charset=utf-8");
		Criteria criteria=getCriteria();
		BIWorkbook bw=new BIWorkbook();
		BISheet bs = bw.addSheet();
		BIRow br_0 = bs.addRow();
		for(String str:new String[]{"商户号","终端号","卡号","姓名","交易状态","邮箱","电话","网址",
				  "ID号","金额","交易返回原因","交易日期"}){
			br_0.addCell().setCellValue(str, null);
		}
			List<VipSalesConsumeInfo> list = vipSalesMgrService.queryExportVipSalesConsumeInfoList(criteria);
			for (int row = 1; row <= list.size(); row++) {
				BIRow br_1 = bs.addRow();
				VipSalesConsumeInfo info = list.get(row-1);
				br_1.addCell().setCellValue(info.getMerNo(),null);
				br_1.addCell().setCellValue(info.getTerNo(),null);
				br_1.addCell().setCellValue((info.getStart()!=null?info.getStart():"")+"****"+(info.getEnd()!=null?info.getStart():""),null);
				br_1.addCell().setCellValue(info.getName(),null);
				br_1.addCell().setCellValue(BaseDataListener.getStringColumnKey("RESP_INFO", info.getRespCode(), info.getRespCode()),null);
				br_1.addCell().setCellValue(info.getEmail(),null);
				br_1.addCell().setCellValue(info.getPhone(),null);
				br_1.addCell().setCellValue(info.getWebsite(),null);
				br_1.addCell().setCellValue(info.getUniqueID(),null);
				br_1.addCell().setCellValue((info.getCurrency()!=null?info.getCurrency():"")+" "+(info.getAmount()!=null?info.getAmount().toString():""),null);
				br_1.addCell().setCellValue(info.getRespMsg(),null);
				br_1.addCell().setCellValue(info.getTransDate()!=null?sdf.format(info.getTransDate()):"",null);
			}
			
		bw.workbook.write(os);	
		os.flush();
		os.close();
	}
	
}
