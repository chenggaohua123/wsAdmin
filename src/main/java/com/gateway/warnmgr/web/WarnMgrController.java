package com.gateway.warnmgr.web;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.BaseDataListener;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.ServiceException;
import com.gateway.fraud.common.exception.FraudExcetion;
import com.gateway.loginmgr.model.UserInfo;
import com.gateway.ratemgr.model.RateInfo;
import com.gateway.ratemgr.service.RateMgrService;
import com.gateway.warnmgr.model.BankInfo;
import com.gateway.warnmgr.model.MerchantWarnInfo;
import com.gateway.warnmgr.model.TransWarnMessageInfo;
import com.gateway.warnmgr.model.TransWarnPhoneInfo;
import com.gateway.warnmgr.model.TransWarnRuleInfo;
import com.gateway.warnmgr.service.WarnManageService;

@Controller
@RequestMapping(value="/warnmgr")
public class WarnMgrController extends BaseController {

	@Autowired
	private WarnManageService warnManageService;
	
	@Autowired
	private RateMgrService rateMgrService;
	
	/**
	 * 商户监控预警
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/listMerchantWarnInfo")
	public String transRerunCount() throws ServiceException{
		
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String transDateStart=sdf.format(date);
			String transDateEnd=sdf.format(date);
			criteria.getCondition().put("startDate", transDateStart);
			criteria.getCondition().put("endDate", transDateEnd);
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			if(null==criteria.getCondition().get("startDate")||"".equals(criteria.getCondition().get("startDate"))||null==criteria.getCondition().get("endDate")||"".equals(criteria.getCondition().get("endDate"))){
				throw new ServiceException("交易时间不能为空！");
			}
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<MerchantWarnInfo> page=warnManageService.queryMerchantWarnInfoList(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "warnmgr/listMerchantWarnInfo";
	}
	@RequestMapping(value="/exportMerchantWarnInfo")
	public void exportMerchantWarnInfo(HttpServletResponse resp) throws Exception{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "failureTransList.xls" ); 
		List<MerchantWarnInfo> list = warnManageService.queryMerchantWarnInfoListAll(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = null;
		sheet = book.createSheet("交易统计分析", 0);
		String[] headerName = {"商户号","商户类别","查询交易天数","实际交易天数","成功交易金额"
				,"成功交易笔数","日平均交易金额","单笔平均交易金额","最后一笔交易时间"};
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
			MerchantWarnInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getMerNo()));
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("MERCHANTTYPE", info.getType()+"", "未知")));
			sheet.addCell( new Label(col++, row, info.getDateCount()+""));
			sheet.addCell( new Label(col++, row, info.getTransDateCount()+""));
			sheet.addCell( new Label(col++, row, info.getBankCurrency()+" "+info.getSuccessAmount()+""));
			sheet.addCell( new Label(col++, row, info.getSuccessCount()+""));
			if(info.getTransDateCount()==0){
				sheet.addCell( new Label(col++, row, info.getBankCurrency()+" "+"0.00"));
			}else{
				sheet.addCell( new Label(col++, row, info.getBankCurrency()+" "+nf.format(info.getSuccessAmount()/(info.getTransDateCount()))+""));//订单号
			}
			if(info.getSuccessCount()==0){
				sheet.addCell( new Label(col++, row, info.getBankCurrency()+" "+"0.00"));
			}else{
				sheet.addCell( new Label(col++, row, info.getBankCurrency()+" "+nf.format(info.getSuccessAmount()/(info.getSuccessCount()))+""));//订单号
			}
			sheet.addCell( new Label(col++, row, info.getLastTransDate()+""));
		}
		book.write();
		book.close();
	}
	
	/**
	 * 查找交易监控信息
	 */
	@RequestMapping(value="/listTransWarnRuleInfo")
	public String queryListTransWarnRuleInfo(){
		Criteria criteria=getCriteria();
		criteria.getCondition().put("queryType", 1);
		PageInfo<TransWarnRuleInfo> pageInfo = warnManageService.queryTransWarnRuleInfoList(criteria);
		getRequest().setAttribute("page", pageInfo);
		return "warnmgr/transWarnRuleInfoList";
	}
	
	/**
	 * 跳转添加交易监控管理界面
	 */
	@RequestMapping(value="/goAddTransWarnRule")
	public String goAddTransWarnRule(){
		return "warnmgr/transWarnRule_add";
	}
	
	/**
	 * 添加交易监控管理
	 */
	@RequestMapping(value="/addTransWarnRuleInfo")
	public ModelAndView addTransWarnRuleInfo(TransWarnRuleInfo form, HttpServletRequest request){
		UserInfo userInfo = getLogAccount();
		form.setCreateBy(userInfo.getRealName());
		int a = warnManageService.saveTransWarnRuleInfo(form);
		if(a>0){
			return ajaxDoneSuccess("保存成功");
		}else{
			return ajaxDoneError("保存失败");
		}
	}
	
	/**
	 * 删除交易监控管理信息
	 */
	@RequestMapping(value="/delTransWarnRuleInfo")
	public ModelAndView delTransWarnRuleInfo(String id) {
		if(null == id){
			return ajaxDoneError("参数为空");
		}
		int a = -1;
		try {
			a = warnManageService.deleteTransWarnRuleInfo(id);
		} catch (FraudExcetion e) {
			return ajaxDoneError("删除失败");
		}
		if(a>=0){
			return ajaxDoneSuccess("删除成功");
		}else{
			return ajaxDoneError("删除失败");
		}
	}
	
	/**
	 * 批量删除交易监控管理信息
	 */
	@RequestMapping(value="/delTransWarnRuleInfos")
	public ModelAndView delTransWarnRuleInfos(String[] ids){
		if(null == ids || !(ids.length>0)){
			return ajaxDoneError("参数为空");
		}
		int a = -1;
		try {
			a = warnManageService.deleteTransWarnRuleInfos(ids);
		} catch (FraudExcetion e) {
			return ajaxDoneError("批量删除失败");
		}
		if(a>=0){
			return ajaxDoneSuccess("批量删除成功");
		}else{
			return ajaxDoneError("批量删除失败");
		}
	}
	
	/**
	 * 跳转修改交易监控界面
	 */
	@RequestMapping(value="/goUpdateTransWarnRule")
	public String goUpdateTransWarnRule(String id){
		TransWarnRuleInfo transWarn = warnManageService.queryTransWarnRuleInfoById(id);
		getRequest().setAttribute("warn", transWarn);
		return "warnmgr/transWarnRule_update";
	}
	
	/**
	 * 修改交易监控信息
	 */
	@RequestMapping(value="/updateTransWarnRuleInfo")
	public ModelAndView updateTransWarnRuleInfo(TransWarnRuleInfo form){
		UserInfo userInfo = getLogAccount();
		form.setLastModifyBy(userInfo.getRealName());
		int a = warnManageService.updateTransWarnRuleInfoById(form);
		if(a>0){
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	
	/**
	 * 查看预警信息
	 */
	@RequestMapping(value="/queryWarnTranInfo")
	public String queryWarnTransInfo(String warnIds){
		List<TransWarnRuleInfo> warnRuleInfo = warnManageService.queryPhoneRelTransRuleInfoByWarnIds(warnIds);
		getRequest().setAttribute("list", warnRuleInfo);
		return "warnmgr/transWarnDetail";
	}
	
	/**
	 * 跳转关联电话信息界面
	 */
	@RequestMapping(value="/goTransWarnRelPhone")
	public String goTransWarnRelPhone(TransWarnRuleInfo form, HttpServletRequest request){
		Criteria criteria = getCriteria();
		String warnId = getRequest().getParameter("warnId");
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			criteria.getCondition().put("warnId", warnId);
		}
		PageInfo<TransWarnPhoneInfo> pageInfo = warnManageService.queryTransWarnPhoneInfoList(criteria);
		getRequest().setAttribute("warnId", warnId);
		getRequest().setAttribute("page", pageInfo);
		return "warnmgr/transWarnRelPhoneInfoList";
	}
	
	/**
	 * 管理电话
	 */
	@RequestMapping(value="/addTransWarnRelPhone")
	public ModelAndView transWarnRelPhone(String[] ids, String warnId){
		int a = -1;
		try {
			a = warnManageService.saveTransWarnWarnIdRelPhoneInfo(ids, warnId);
		} catch (FraudExcetion e) {
			return ajaxDoneError("修改失败");
		}
		if(a>0){
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	
	/**
	 * 查找银行信息
	 */
	@RequestMapping(value="/queryBankList")
	public String queryBankList(){
		PageInfo<BankInfo> pageInfo = warnManageService.queryBankInfoList(getCriteria());
		getRequest().setAttribute("result", pageInfo);
		return "warnmgr/bank_lookup_list";
	}
	
	/**
	 * 获取银行下拉列表 
	 */
	@ResponseBody
	@RequestMapping(value="/queryBankInfoList")
	public List<RateInfo> queryBankInfoList(){
		return rateMgrService.queryBankInfoList();
	}
	
	/**
	 * 跳转添加用户电话信息界面
	 */
	@RequestMapping(value="/listWarnPhoneInfo")
	public String goWarnPhoneInfoList(){
		PageInfo<TransWarnPhoneInfo> pageInfo = warnManageService.queryTransWarnPhoneInfoList(getCriteria());
		getRequest().setAttribute("page", pageInfo);
		return "warnmgr/transWarnPhoneInfoList";
	}
	
	/**
	 * 添加电话信息
	 */
	@RequestMapping(value="/addWarnPhoenInfo")
	public ModelAndView addWarnPhoenInfo(TransWarnPhoneInfo form){
		int a = warnManageService.saveTransWarnPhoneInfo(form);
		if(a>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	/**
	 * 删除电话信息
	 * @throws FraudExcetion 
	 */
	@RequestMapping(value="/delWarnPhoneInfo")
	public ModelAndView delWarnPhoneInfo(TransWarnPhoneInfo form){
		int a = -1;
		try {
			a = warnManageService.deleteTransWarnPhoneInfoById(form.getId());
		} catch (FraudExcetion e) {
			return ajaxDoneError("删除失败");
		}
		if(a>0){
			return ajaxDoneSuccess("删除成功");
		}else{
			return ajaxDoneError("删除失败");
		}
	}
	
	/**
	 * 跳转新增电话信息界面
	 */
	@RequestMapping(value="/goAddWarnPhoneInfo")
	public String goAddWarnPhoneInfo(TransWarnPhoneInfo form){
		return "warnmgr/transWarnPhone_add";
	}
	
	/**
	 * 跳转修改电话信息界面
	 */
	@RequestMapping(value="/goUpdateWarnPhoneInfo")
	public String goUpdateWarnPhoneInfo(TransWarnPhoneInfo form){
		TransWarnPhoneInfo info = null;
		if(form.getId()!=null && !("".equals(form.getId()))){
			info = warnManageService.queryTransWarnPhoenInfoById(form.getId());
		}
		if(null == info){
			info = new TransWarnPhoneInfo();
		}
		getRequest().setAttribute("info", info);
		return "warnmgr/transWarnPhone_update";
	}
	
	/**
	 * 修改电话信息
	 */
	@RequestMapping(value="/updateWarnPhoneInfo")
	public ModelAndView updateWarnPhoneInfo(TransWarnPhoneInfo form){
		int a = warnManageService.updateTransWarnPhoneInfoById(form);
		if(a>0){
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	
	/**
	 * 跳转短信内容界面
	 */
	@RequestMapping(value="/goMessageModelInfoList")
	public String goMessageInfoList(TransWarnMessageInfo form){
		return "warnmgr/transWarnMessageInfoList";
	}
	
	/**
	 * 跳转添加短信模板信息
	 */
	@RequestMapping(value="/goAddMessageModel")
	public String goAddMessageModel(TransWarnMessageInfo form){
		getRequest().setAttribute("warnId", form.getWarnId());
		return "warnmgr/transWarnMessage_add";
	}
	
	/**
	 * 保存添加短信模板信息
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/addMessageModelInfo")
	public ModelAndView addMessageModelInfo(TransWarnMessageInfo form) throws UnsupportedEncodingException{
		if(null==form.getMessage() && "".equals(form.getMessage())){
			return ajaxDoneSuccess("短信内容不能为空");
		}
		UserInfo userInfo = getLogAccount();
		form.setCreateBy(userInfo.getRealName());
		int a = -1;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("交易预警:");
			sb.append(form.getMessage());
			form.setMessage(sb.toString());
			a = warnManageService.saveTransWarnMessageInfo(form);
		} catch (FraudExcetion e) {
			return ajaxDoneError(e.getMessage());
		}
		if(a>0){
			return ajaxDoneSuccess("保存成功");
		}else{
			return ajaxDoneError("保存失败");
		}
	}
	
	/**
	 * 删除短信模板信息
	 */
	@RequestMapping(value="/delMessageModelInfo")
	public ModelAndView delMessageModelInfo(TransWarnMessageInfo form){
		if(null==form.getWarnId() || "".equals(form.getWarnId())){
			return ajaxDoneError("没有短信信息");
		}
		int a = warnManageService.deleteTransWarnMessageInfoByWarnId(form.getWarnId());
		if(a==0){
			return ajaxDoneSuccess("没有短信信息");
		}
		if(a>0){
			return ajaxDoneSuccess("删除成功");
		}else{
			return ajaxDoneError("删除失败");
		}
	}
	
	/**
	 * 修改短信模板信息
	 */
	@RequestMapping(value="/goUpdateMessageModelInfo")
	public String goUpdateMessageModelInfo(TransWarnMessageInfo form){
		TransWarnMessageInfo message = warnManageService.queryTransWarnMessageInfoByWarnId(form.getWarnId());
		if(message!=null){
			message.setMessage(message.getMessage().substring(message.getMessage().indexOf(":")+1, message.getMessage().lastIndexOf("【")));
		}
		getRequest().setAttribute("info", message);
		return "warnmgr/transWarnMessage_update";
	}
	
	/**
	 * 保存修改短信模板信息
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/updateMessageModelInfo")
	public ModelAndView updateMessageModelInfo(TransWarnMessageInfo form) throws UnsupportedEncodingException{
		if(null==form.getId() || "".equals(form.getId())){
			return ajaxDoneError("短信信息不存在");
		}
		UserInfo userInfo = getLogAccount();
		form.setLastModifyBy(userInfo.getRealName());
		StringBuffer sb = new StringBuffer();
		sb.append("交易预警:");
		sb.append(form.getMessage());
		form.setMessage(sb.toString());
		int a = warnManageService.updateTransWarnMessageInfoById(form);
		if(a>0){
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	
	/**
	 * 跳转商户交易预警配置信息界面
	 */
	@RequestMapping(value="/listMerchantWarnRuleInfo")
	public String listMerchantWarnRuleInfo(){
		Criteria criteria = getCriteria();
		criteria.getCondition().put("queryType", 2);
		PageInfo<TransWarnRuleInfo> pageInfo = warnManageService.queryTransWarnRuleInfoList(criteria);
		getRequest().setAttribute("page", pageInfo);
		return "warnmgr/merchantWarnRuleInfoList";
	}
	
	/**
	 * 跳转添加商户交易监控管理界面
	 */
	@RequestMapping(value="/goAddMerchantTransWarnRule")
	public String goAddMerchantTransWarnRule(){
		return "warnmgr/merchantTransWarnRule_add";
	}
	
	/**
	 * 跳转修改商户交易监控界面
	 */
	@RequestMapping(value="/goUpdateMerchantTransWarnRule")
	public String goUpdateMerchantTransWarnRule(String id){
		TransWarnRuleInfo transWarn = warnManageService.queryTransWarnRuleInfoById(id);
		getRequest().setAttribute("warn", transWarn);
		return "warnmgr/merchantTransWarnRule_update";
	}
	
	/**
	 * 跳转添加短信模板信息
	 */
	@RequestMapping(value="/goAddMerchantMessageModel")
	public String goAddMerchantMessageModel(TransWarnMessageInfo form){
		getRequest().setAttribute("warnId", form.getWarnId());
		return "warnmgr/merchantTransWarnMessage_add";
	}
	
	@RequestMapping(value="/goUpdateMerchantMessageModelInfo")
	public String goUpdateMerchantMessageModelInfo(TransWarnMessageInfo form){
		TransWarnMessageInfo message = warnManageService.queryTransWarnMessageInfoByWarnId(form.getWarnId());
		if(message!=null){
			message.setMessage(message.getMessage().substring(message.getMessage().indexOf(":")+1, message.getMessage().lastIndexOf("【")));
		}
		getRequest().setAttribute("info", message);
		return "warnmgr/merchantTransWarnMessage_update";
	}
	
}

