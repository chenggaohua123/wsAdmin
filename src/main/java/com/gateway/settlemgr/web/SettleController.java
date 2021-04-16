package com.gateway.settlemgr.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.jpos.iso.ISOException;
import org.springframework.stereotype.Controller;
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
import com.gateway.common.excetion.ServiceException;
import com.gateway.common.utils.Tools;
import com.gateway.settlemgr.model.AgentSettleInfo;
import com.gateway.settlemgr.model.DeductionTypeInfo;
import com.gateway.settlemgr.model.DisFineStepInfo;
import com.gateway.settlemgr.model.ExceptionSettleType;
import com.gateway.settlemgr.model.ExportInfo;
import com.gateway.settlemgr.model.GwSettleTransInfo;
import com.gateway.settlemgr.model.HandTransInfo;
import com.gateway.settlemgr.model.MerchantAccount;
import com.gateway.settlemgr.model.MerchantAccountAccess;
import com.gateway.settlemgr.model.MerchantAccountAccessDetail;
import com.gateway.settlemgr.model.MerchantSettleInfo;
import com.gateway.settlemgr.model.PoolSettleInfo;
import com.gateway.settlemgr.model.SettleTypeInfo;
import com.gateway.settlemgr.service.SettleMgrService;
import com.gateway.transmgr.model.TransInfo;

@Controller
@RequestMapping(value="/settlemgr")
public class SettleController extends BaseController{
	@Resource
	private SettleMgrService settleMgrService;
	
	
	public SettleMgrService getSettleMgrService() {
		return settleMgrService;
	}

	public void setSettleMgrService(SettleMgrService settleMgrService) {
		this.settleMgrService = settleMgrService;
	}

	/**
	 * 查询商户结算记录
	 * @return
	 */
	@RequestMapping(value="/merchantsettleList")
	public String merchantsettleList(){
		PageInfo<MerchantSettleInfo> page=settleMgrService.getMerchantSettleInfo(getCriteria());
		getRequest().setAttribute("page", page);
		return "settlemgr/merchantsettleList";
	}
	
	/**
	 * 生成商户划款记录
	 * @return
	 */
	@RequestMapping(value="/viewMerchantSettleList")
	public String viewMerchantSettleList(String settleDate){
		PageInfo<MerchantSettleInfo> page=settleMgrService.viewMerchantSettleList(getCriteria());
		getRequest().setAttribute("page", page);
		return "settlemgr/createMerchantsettleList";
	}
	
	/**
	 * 预览商户清算记录
	 * @param settleDate
	 * @return
	 */
	@RequestMapping(value="/createMerchantSettleList")
	public ModelAndView createMerchantSettleList(){
		criteria = getCriteria();
		if(null == criteria.getCondition().get("transDate") || "".equals(criteria.getCondition().get("transDate").toString().trim())){
			return ajaxDoneError("请选择清算的截止时间。");
		}
		Timestamp settleDate = null;
		try {
			settleDate = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH").parse(criteria.getCondition().get("transDate").toString()).getTime());
			criteria.getCondition().put("settleDate", settleDate);
		} catch (ParseException e) {
			return ajaxDoneError("清算截止时间格式有误。");
		}
		String batchNo = settleMgrService.createMerchantSettleList(criteria);
		return ajaxDoneSuccess("生效成功,批次号为："+batchNo);
	}
	
	
	/**
	 * 导出商户结算信息 
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	@RequestMapping(value="/exportMerchantSettle")
	public void exportMerchantSettle(HttpServletResponse resp) throws IOException, RowsExceededException, WriteException{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "merchantSettle.xls" ); 
		List<MerchantSettleInfo> list = settleMgrService.exportMerchantSettleInfo(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("商户结算列表", 0);
		String[] headerName = { "商户号", "终端号","交易笔数","交易手续费","交易金额","结算日期","批次号","结算金额","结算银行持卡人","结算卡号","结算名称"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			MerchantSettleInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getMerNo()));
			sheet.addCell( new Label(col++, row, info.getTerNo()));
			sheet.addCell( new Label(col++, row, info.getTransCount().toString()));
			sheet.addCell( new jxl.write.Number(col++, row, info.getForAmount().doubleValue()));
			sheet.addCell( new jxl.write.Number(col++, row, info.getTransAmount().doubleValue()));
			sheet.addCell( new Label(col++, row, info.getSettleDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getSettleDate()):""));
			sheet.addCell( new Label(col++, row, info.getBatchNo()));
			sheet.addCell( new jxl.write.Number(col++, row, info.getSettleAmount().doubleValue()));
			sheet.addCell( new Label(col++, row, info.getAccountName()));
			sheet.addCell( new Label(col++, row, info.getAccountNo()));
			sheet.addCell( new Label(col++, row, info.getAccountAddress()));
		}
		book.write();
		book.close();
	}
	/**
	 * 明细导出
	 * @param merNo
	 * @param terNo
	 * @param batchNo
	 */
	@RequestMapping(value="/exportTrans")
	public void exportTrans(HttpServletResponse resp,String merNo,String terNo,String batchNo)throws IOException, RowsExceededException, WriteException{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "settleTrans.xls" ); 
		List<GwSettleTransInfo> list=settleMgrService.queryDetailList(merNo, terNo, batchNo);
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("商户明细导出", 0);
		String[] headerName = { "交易流水号", "参考号","商户号","终端号","交易类型","交易金额","商户手续费","代理商手续费","父级代理商手续费","返回码","交易时间","交易通道","交易银行","结算日期","结算批次号"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			GwSettleTransInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getTradeNo()));
			sheet.addCell( new Label(col++, row, info.getRelNo()));
			sheet.addCell( new Label(col++, row, info.getMerNo()));
			sheet.addCell( new Label(col++, row, info.getTerNo()));
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("gw_transtype_info",info.getTransType(),"未知类型")));
			sheet.addCell( new jxl.write.Number(col++, row, info.getTransAmount().doubleValue()));
			sheet.addCell( new jxl.write.Number(col++, row, info.getMerForAmount().doubleValue()));
			sheet.addCell( new jxl.write.Number(col++, row, info.getAgentForAmount().doubleValue()));
			sheet.addCell( new jxl.write.Number(col++, row, info.getParentAgentForAmount().doubleValue()));
			sheet.addCell( new Label(col++, row, info.getRespCode()));
			sheet.addCell( new Label(col++, row, info.getTransDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getTransDate()):""));
			sheet.addCell( new Label(col++, row, info.getCurrencyName()));
			sheet.addCell( new Label(col++, row, info.getBankName()));
			sheet.addCell( new Label(col++, row, info.getSettleDate()));
			sheet.addCell( new Label(col++, row, info.getSettleBatchNo()));
		}
		book.write();
		book.close();
	}
	
	/**
	 * 代理商预览分润列表
	 * @return
	 */
	@RequestMapping(value="/viewAgentSettleInfo")
	public String viewAgentSettleInfo(){
		PageInfo<AgentSettleInfo> page = settleMgrService.viewAgentSettleInfo(getCriteria());
		getRequest().setAttribute("page", page);
		return "settlemgr/viewAgentSettleInfoList";
	}
	
	/**
	 * 创建代理商分润列表
	 * @return
	 */
	@RequestMapping(value="/createAgentSettleInfo")
	public String createAgentSettleInfo(){
		PageInfo<AgentSettleInfo> page = settleMgrService.createAgentSettleInfo(getCriteria());
		getRequest().setAttribute("page", page);
		return "settlemgr/createAgentSettleInfoList";
	}
	
	/**
	 * 生效代理分润列表
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/effectAgentSettleInfo")
	public ModelAndView effectAgentSettleInfo() throws ServiceException{
		String batchNo = settleMgrService.effectAgentSettleInfo(getCriteria());
		return ajaxDoneSuccess("生效成功。批次号为:"+batchNo);
	}
	
	/**
	 * 代理商分润列表
	 * @return
	 */
	@RequestMapping(value="/parentAgentSettleList")
	public String parentAgentSettleList(){
		Criteria criteria = getCriteria();
		criteria.getCondition().put("parentAgentNo", "0");
		PageInfo<AgentSettleInfo> page = settleMgrService.queryAgentSettleInfoList(criteria);
		getRequest().setAttribute("page", page);
		return "settlemgr/parentAgentSettleList";
	}
	
	/**
	 * 二级代理分润列表
	 * @return
	 */
	@RequestMapping(value="/agentSettleList")
	public String agentSettleList(){
		return "settlemgr/agentSettleList";
	}
	
	
	/**
	 * 分润导出
	 * @throws IOException   
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	@RequestMapping(value="/exportAgentSettleInfoList")
	public void exportAgentSettleInfoList(HttpServletResponse resp) throws IOException, RowsExceededException, WriteException{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "AgentSettleInfoList.xls" ); 
		Criteria criteria = getCriteria();
		criteria.getCondition().put("parentAgentNo", "0");
		List<AgentSettleInfo> list = settleMgrService.exportAgentSettleInfoList(criteria);
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("分润信息", 0);
		String[] headerName = { "代理商编号", "交易笔数","交易金额","交易手续费","高签部分手续费","二级代理高签手续费","二级代理分润","结算金额"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			AgentSettleInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getAgentNo()));
			sheet.addCell( new Label(col++, row, String.valueOf(info.getTransCount())));
			sheet.addCell( new jxl.write.Number(col++, row, info.getTransAmount().doubleValue()));
			sheet.addCell( new jxl.write.Number(col++, row, info.getTransForAmaount().doubleValue()));
			sheet.addCell( new jxl.write.Number(col++, row, info.getDiversityAgentForAmount().doubleValue()));
			sheet.addCell( new jxl.write.Number(col++, row, info.getDiversityParentAgentForAmount().doubleValue()));
			sheet.addCell( new jxl.write.Number(col++, row, info.getDiversitySplitParentAgentForAmount().doubleValue()));
			sheet.addCell( new jxl.write.Number(col++, row, info.getSettleAmount().doubleValue()));
			
		}
		book.write();
		book.close();
	}
	
	/**
	 * 商户虚拟账户
	 * */
	@RequestMapping(value="/listMerchantAccount")
	public String listMerchantAccount(){
		PageInfo<MerchantAccount> page = settleMgrService.listMerchantAccount(getCriteria());
		getRequest().setAttribute("page", page);
		return "settlemgr/listMerchantAccount";
	}
	/**
	 * 提现管理
	 * */
	@RequestMapping(value="/listMerchantAccountAccess")
	public String listMerchantAccountAccess(){
		Criteria criteria =getCriteria();
		criteria.getCondition().put("cashTypes", "(1,2,3,4,5,6,7,8,9,10)");
		List<DeductionTypeInfo> list=settleMgrService.queryDeductionTypeInfo(getCriteria());
		getRequest().setAttribute("dtList", list);
		PageInfo<MerchantAccountAccess> page=settleMgrService.listMerchantAccountAccess(criteria);
		getRequest().setAttribute("page", page);
		return "settlemgr/listMerchantAccountAccess";
	}
	/**
	 *跳转 审核提现
	 * @throws ServiceException 
	 * */
	@RequestMapping(value="/goCheckMerchantAccountAccess")
	public String goCheckMerchantAccountAccess(String id) throws ServiceException{
		MerchantAccountAccess ma=settleMgrService.queryMerchantAccountAccessById(id);
		if(!"0".equals(ma.getStatus())){
			throw new ServiceException("只能审核待审核的单");
		}
		getRequest().setAttribute("info", ma);
		return "settlemgr/checkMerchantAccountAccess";
	}
	/**
	 * 提现审核
	 * */
	@RequestMapping("/checkMerchantAccountAccess")
	public ModelAndView checkMerchantAccountAccess(MerchantAccountAccess ma) throws ServiceException{
		ma.setCheckBy(getLogAccount().getRealName());
		
		int i=settleMgrService.checkMerchantAccountAccess(ma);
		if(i>0){
			return ajaxDoneSuccess("审核成功");
		}else{
			return ajaxDoneError("审核失败");
		}
	}
	/**
	 *跳转 复核提现
	 * @throws ServiceException 
	 * */
	@RequestMapping(value="/goReCheckMerchantAccountAccess")
	public String goReCheckMerchantAccountAccess(String id) throws ServiceException{
		MerchantAccountAccess ma=settleMgrService.queryMerchantAccountAccessById(id);
		if(!"1".equals(ma.getStatus())){
			throw new ServiceException("只能复核审核通过的单");
		}
		if(ma.getCashType()==3||ma.getCashType()==4
				||ma.getCashType()==5||ma.getCashType()==6||
				ma.getCashType()==7||ma.getCashType()==8||ma.getCashType()==9||ma.getCashType()==10){
			if(getLogAccount().getRealName().equals(ma.getCheckBy())){
				throw new ServiceException("罚款、返还款、冻结款、解冻款需要不同账户复核后才能生效");
			}
		}
		getRequest().setAttribute("info", ma);
		return "settlemgr/reCheckMerchantAccountAccess";
	}
	/**
	 * 复核审核
	 * @throws ServiceException 
	 * */
	@RequestMapping("/reCheckMerchantAccountAccess")
	public ModelAndView reCheckMerchantAccountAccess(MerchantAccountAccess ma) throws ServiceException{
		ma.setReCheckBy(getLogAccount().getRealName());
		int i=settleMgrService.checkMerchantAccountAccess(ma);
		if(i>0){
			return ajaxDoneSuccess("复核成功");
		}else{
			return ajaxDoneError("复核失败");
		}
	}
	
	/**
	 *跳转 取消复核
	 * @throws ServiceException 
	 * */
	@RequestMapping(value="/goCancelReCheckMerchantAccountAccess")
	public String goCancelReCheckMerchantAccountAccess(String id) throws ServiceException{
		MerchantAccountAccess ma=settleMgrService.queryMerchantAccountAccessById(id);
		if(!"3".equals(ma.getStatus())){
			throw new ServiceException("复核审核通过的订单才能执行取消复核操作");
		}
		getRequest().setAttribute("info", ma);
		return "settlemgr/cancelReCheckMerchantAccountAccess";
	}
	
	
	/**
	 * 取消复核
	 * @throws ServiceException 
	 * */
	@RequestMapping("/cancelReCheckMerchantAccountAccess")
	public ModelAndView cancelReCheckMerchantAccountAccess(MerchantAccountAccess ma) throws ServiceException{
		//ma.setReCheckBy(getLogAccount().getRealName());
		MerchantAccountAccess maa=settleMgrService.queryMerchantAccountAccessById(ma.getId());
		if(!"3".equals(maa.getStatus())){
			return ajaxDoneError("复核审核通过的订单才能执行取消复核操作");
		}
		int i=0;
		if("6".equals(ma.getStatus())){
			ma.setReCheckBy(null);
			ma.setReCheckDate(null);
			ma.setStatus("1");
			i=settleMgrService.updateMerchantAccountAccessStatus(ma);
		}
		if(i>0){
			return ajaxDoneSuccess("操作成功");
		}else{
			return ajaxDoneError("操作失败");
		}
	}
	
	/**
	 *跳转 出款提现
	 * @throws ServiceException 
	 * */
	@RequestMapping(value="/goMoneyMerchantAccountAccess")
	public String goMoneyMerchantAccountAccess(String id) throws ServiceException{
		MerchantAccountAccess ma=settleMgrService.queryMerchantAccountAccessById(id);
		if(!"3".equals(ma.getStatus())){
			throw new ServiceException("只能出款复核通过的单");
		}
		getRequest().setAttribute("info", ma);
		return "settlemgr/moneyMerchantAccountAccess";
	}
	/**
	 * 提现出款
	 * @throws ServiceException 
	 * */
	@RequestMapping("/moneyMerchantAccountAccess")
	public ModelAndView moneyMerchantAccountAccess(MerchantAccountAccess ma) throws ServiceException{
		ma.setMoneyBy(getLogAccount().getRealName());
		int i=settleMgrService.checkMerchantAccountAccess(ma);
		if(i>0){
			return ajaxDoneSuccess("出款成功");
		}else{
			return ajaxDoneError("出款失败");
		}
	}
	
	/**
	 * 商户虚拟账户出入帐流水明细
	 * */
	@RequestMapping(value="/listMerchantAccountAccessDetail")
	public String listMerchantAccountAccessDetail(){
		PageInfo<MerchantAccountAccessDetail> page=settleMgrService.listMerchantAccountAccessDetail(getCriteria());
		getRequest().setAttribute("page", page);
		return "settlemgr/listMerchantAccountAccessDetail";
	}
	/**
	 * 查询商户结算方式
	 * */
	@RequestMapping(value="/listSettleTypeInfo")
	public String listSettleTypeInfo(){
		if(!"get".equalsIgnoreCase(getRequest().getMethod())){
			PageInfo<SettleTypeInfo> page=settleMgrService.listSettleTypeInfo(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "settlemgr/listSettleTypeInfo";
	}
	
	/**
	 * 分润导出
	 * @throws IOException   
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	@RequestMapping(value="/exportSettleTypeInfoList")
	public void exportSettleTypeInfoList(HttpServletResponse resp) throws IOException, RowsExceededException, WriteException{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "AgentSettleInfoList.xls" ); 
		Criteria criteria = getCriteria();
		criteria.getCondition().put("parentAgentNo", "0");
		List<SettleTypeInfo> list = settleMgrService.ExportSettleTypeInfo(criteria);
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("结算周期", 0);
		String[] headerName = { "商户号","终端号","结算周期(天)","结算类型","保证金结算周期(天)",
				"是否有效","创建人","创建时间","最后修改人","最后修改时间","备注"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			SettleTypeInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getMerNo()));
			sheet.addCell( new Label(col++, row, info.getTerNo()));
			sheet.addCell( new Label(col++, row, info.getSettleCycle()));
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("SETTLETYPE", info.getSettleType(),  info.getSettleType())));
			sheet.addCell( new Label(col++, row, info.getBondCycle()));
			sheet.addCell( new Label(col++, row, "1".equals(info.getEnabled())?"有效":"无效"));
			sheet.addCell( new Label(col++, row, info.getCreateBy()));
			sheet.addCell( new Label(col++, row, info.getCreateDate()));
			sheet.addCell( new Label(col++, row, info.getLastModify()));
			sheet.addCell( new Label(col++, row, info.getLastModifyDate()));
			sheet.addCell( new Label(col++, row, info.getRemark()));
		}
		book.write();
		book.close();
	}
	/**
	 * 跳转到添加商户结算条件页面
	 * */
	@RequestMapping(value="/goAddSettleTypeInfo")
	public String goAddSettleTypeInfo(){
		return "settlemgr/addSettleTypeInfo";
	}
	/**
	 * 跳转到修改商户结算条件页面
	 * */
	@RequestMapping(value="/goUpdateSettleTypeInfo")
	public String goUpdateSettleTypeInfo(String id){
		SettleTypeInfo settleTypeInfo=settleMgrService.querySettleTypeInfoById(id);
		getRequest().setAttribute("info", settleTypeInfo);
		return "settlemgr/updateSettleTypeInfo";
	}
	/**
	 * 添加商户结算条件
	 * */
	@RequestMapping(value="/addSettleTypeInfo")
	public ModelAndView addSettleTypeInfo(SettleTypeInfo settleTypeInfo){
		
		settleTypeInfo.setCreateBy(getLogAccount().getRealName());
		int i=settleMgrService.queryDuplicateByMerNoAndTerNo(settleTypeInfo.getMerNo(),settleTypeInfo.getTerNo());
		if(i>0){
			return ajaxDoneError("商户信息重复添加！");
		}
//		if(1 > Integer.parseInt(settleTypeInfo.getSettleCycle())){
//			return ajaxDoneError("结算周期不能小于1天");
//		}
//		if(1 > Integer.parseInt(settleTypeInfo.getBondCycle())){
//			return ajaxDoneError("保证金结算周期不能小于1天");
//		}
		int count=settleMgrService.insertSettleTypeInfo(settleTypeInfo);
		if(count>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	/**
	 * 修改商户结算条件
	 * */
	@RequestMapping(value="/updateSettleTypeInfo")
	public ModelAndView updateSettleTypeInfo(SettleTypeInfo settleTypeInfo){
//		if(1 > Integer.parseInt(settleTypeInfo.getSettleCycle())){
//			return ajaxDoneError("结算周期不能小于1天");
//		}
//		if(1 > Integer.parseInt(settleTypeInfo.getBondCycle())){
//			return ajaxDoneError("保证金结算周期不能天");
//		}
//		if(0 < settleTypeInfo.getFrozenPercent().compareTo(new BigDecimal("1"))){
//			return ajaxDoneError("冻结比例不能大于1");
//		}
//		if(0 > settleTypeInfo.getFrozenPercent().compareTo(new BigDecimal("0"))){
//			return ajaxDoneError("冻结比例不能小于0");
//		}
		settleTypeInfo.setLastModify(getLogAccount().getRealName());
		int count=settleMgrService.updateSettleTypeInfo(settleTypeInfo);
		if(count>0){
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	/**
	 * 跳转到提现页面
	 * @throws ServiceException 
	 * */
	@RequestMapping(value="/goUpdateMerchantAccount")
	public String goUpdateMerchantAccount(String id) throws ServiceException{
		String[] infos=id.split(",");
		if(infos.length!=2){
			throw new ServiceException("数据不全");
		}
		List<DeductionTypeInfo> list=settleMgrService.queryDeductionTypeInfo(getCriteria());
		getRequest().setAttribute("dtList", list);
		id=infos[0];
		String accountType=infos[1];
		MerchantAccount merchantAccount=settleMgrService.queryMerchantAccountById(id,accountType);
		getRequest().setAttribute("info", merchantAccount);
		return "settlemgr/updateMerchantAccount";
	}
	/**
	 * 修改商户虚拟账户 插入商户提现入账记录
	 * */
	@RequestMapping("/updateMerchantAccount")
	public ModelAndView updateMerchantAccout(MerchantAccountAccess merchantAccountAccess){
		if(merchantAccountAccess.getCashType()!=4&&merchantAccountAccess.getCashType()!=5){
			if(merchantAccountAccess.getOperateAmount().doubleValue()>merchantAccountAccess.getOperatedAmount().doubleValue()){
				return ajaxDoneError("操作金额过大");
			}
		}
		MerchantAccount ma=settleMgrService.queryMerchantAccountById(merchantAccountAccess.getAccountId());
		MerchantAccount merchantAccount=new MerchantAccount();
		merchantAccount.setId(merchantAccountAccess.getAccountId());
		if(merchantAccountAccess.getCashType()!=4){
			merchantAccountAccess.setDeductionType(null);
		}
		if(merchantAccountAccess.getAccountType()==0){//交易账户
			if(merchantAccountAccess.getCashType()==1){//提现
				merchantAccountAccess.setCashType(1);
				merchantAccountAccess.setAmount(merchantAccountAccess.getOperateAmount().multiply(new BigDecimal(-1)));
				merchantAccount.setTotalAmount(merchantAccountAccess.getOperateAmount().multiply(new BigDecimal(-1)));
				merchantAccount.setCashAmount(merchantAccountAccess.getOperateAmount().multiply(new BigDecimal(-1)));
				
			}else if(merchantAccountAccess.getCashType()==2){//冻结
				merchantAccountAccess.setCashType(4);
				merchantAccountAccess.setAmount(merchantAccountAccess.getOperateAmount().multiply(new BigDecimal(-1)));
				merchantAccount.setTotalAmount(merchantAccountAccess.getOperateAmount().multiply(new BigDecimal(-1)));
				merchantAccount.setCashAmount(merchantAccountAccess.getOperateAmount().multiply(new BigDecimal(-1)));
				merchantAccount.setFrozenAmount(merchantAccountAccess.getOperateAmount());
			}else if(merchantAccountAccess.getCashType()==3){//解冻
				merchantAccountAccess.setCashType(3);
				merchantAccountAccess.setAmount(merchantAccountAccess.getOperateAmount());
				merchantAccount.setTotalAmount(merchantAccountAccess.getOperateAmount());
				merchantAccount.setCashAmount(merchantAccountAccess.getOperateAmount());
				merchantAccount.setFrozenAmount(merchantAccountAccess.getOperateAmount().multiply(new BigDecimal(-1)));
			}else if(merchantAccountAccess.getCashType()==4){//扣款
				merchantAccountAccess.setCashType(5);
				merchantAccountAccess.setAmount(merchantAccountAccess.getOperateAmount().multiply(new BigDecimal(-1)));
				merchantAccount.setTotalAmount(merchantAccountAccess.getOperateAmount().multiply(new BigDecimal(-1)));
				merchantAccount.setCashAmount(merchantAccountAccess.getOperateAmount().multiply(new BigDecimal(-1)));
			}else if(merchantAccountAccess.getCashType()==5){//返款
				merchantAccountAccess.setCashType(6);
				merchantAccountAccess.setAmount(merchantAccountAccess.getOperateAmount());
				merchantAccount.setTotalAmount(merchantAccountAccess.getOperateAmount());
				merchantAccount.setCashAmount(merchantAccountAccess.getOperateAmount());
				
			}else{
				return ajaxDoneError("提现类型错误");
			}
		}else if(merchantAccountAccess.getAccountType()==1){//保证金账户
			if(merchantAccountAccess.getCashType()==1){//提现
				merchantAccountAccess.setCashType(2);
				merchantAccountAccess.setAmount(merchantAccountAccess.getOperateAmount().multiply(new BigDecimal(-1)));
				merchantAccount.setBondAmount(merchantAccountAccess.getOperateAmount().multiply(new BigDecimal(-1)));
				merchantAccount.setBondCashAmount(merchantAccountAccess.getOperateAmount().multiply(new BigDecimal(-1)));
			}else if(merchantAccountAccess.getCashType()==2){//冻结
				merchantAccountAccess.setCashType(9);
				merchantAccountAccess.setAmount(merchantAccountAccess.getOperateAmount().multiply(new BigDecimal(-1)));
				merchantAccount.setBondAmount(merchantAccountAccess.getOperateAmount().multiply(new BigDecimal(-1)));
				merchantAccount.setBondCashAmount(merchantAccountAccess.getOperateAmount().multiply(new BigDecimal(-1)));
				merchantAccount.setBondFrozenAmount(merchantAccountAccess.getOperateAmount());
			}else if(merchantAccountAccess.getCashType()==3){//解冻
				merchantAccountAccess.setCashType(10);
				merchantAccountAccess.setAmount(merchantAccountAccess.getOperateAmount());
				merchantAccount.setBondAmount(merchantAccountAccess.getOperateAmount());
				merchantAccount.setBondCashAmount(merchantAccountAccess.getOperateAmount());
				merchantAccount.setBondFrozenAmount(merchantAccountAccess.getOperateAmount().multiply(new BigDecimal(-1)));
			}else if(merchantAccountAccess.getCashType()==4){//扣款
				merchantAccountAccess.setCashType(7);
				merchantAccountAccess.setAmount(merchantAccountAccess.getOperateAmount().multiply(new BigDecimal(-1)));
				merchantAccount.setBondAmount(merchantAccountAccess.getOperateAmount().multiply(new BigDecimal(-1)));
				merchantAccount.setBondCashAmount(merchantAccountAccess.getOperateAmount().multiply(new BigDecimal(-1)));
			}else if(merchantAccountAccess.getCashType()==5){//返款
				merchantAccountAccess.setCashType(8);
				merchantAccountAccess.setAmount(merchantAccountAccess.getOperateAmount());
				merchantAccount.setBondAmount(merchantAccountAccess.getOperateAmount());
				merchantAccount.setBondCashAmount(merchantAccountAccess.getOperateAmount());
			}else{
				return ajaxDoneError("提现类型错误");
			}
		}else{
			return ajaxDoneError("账户类型");
		}
		merchantAccountAccess.setId(Tools.getAccessId());
		merchantAccountAccess.setStatus("0");//提现状态:0待审核 1审核通过 2审核不通过 3复核通过 4复核驳回 5已出款
		merchantAccountAccess.setMerNo(ma.getMerNo());
		merchantAccountAccess.setTerNo(ma.getTerNo());
		merchantAccountAccess.setCreateBy(getLogAccount().getRealName());
		int i=settleMgrService.insertMerchantAccountAccess(merchantAccountAccess,merchantAccount);
		if(i>0){
			return ajaxDoneSuccess("操作成功");
		}else{
			return ajaxDoneError("操作失败");
		}
	}
	/**
	 * 跳转到提现审核页面
	 * */
	@RequestMapping(value="/goUpdateMerchantAccountAccess")
	public String goUpdateMerchantAccountAccess(String id){
		MerchantAccountAccess merchantAccountAccess=settleMgrService.queryMerchantAccountAccessById(id);
		getRequest().setAttribute("info", merchantAccountAccess);
		return "settlemgr/updateMerchantAccountAccess";
	}
	/**
	 * 商户提现审核
	 * @throws ServiceException 
	 * */
	@RequestMapping(value="/updateMerchantAccountAccess")
	public ModelAndView updateMerchantAccountAccess(MerchantAccountAccess merchantAccountAccess) throws ServiceException{
//		merchantAccountAccess.setLastModify(getLogAccount().getRealName());
		int i=settleMgrService.updateMerchantAccountAccess(merchantAccountAccess);
		if(i>0){
			return ajaxDoneSuccess("审核成功");
		}else{
			return ajaxDoneError("审核失败");
		}
	}
	/**
	 * 出入帐明细列表显示
	 * */
	@RequestMapping(value="/listMerchantAccountInAndOut")
	public String listMerchantAccountInAndOut(){
		List<DeductionTypeInfo> list=settleMgrService.queryDeductionTypeInfo(getCriteria());
		getRequest().setAttribute("dtList", list);
		PageInfo<MerchantAccountAccess> page=settleMgrService.listMerchantAccountAccess(getCriteria());
		getRequest().setAttribute("page", page);
		return "settlemgr/listMerchantAccountInAndOut";
	}
	/**
	 * 导出出入账明细
	 */
	@RequestMapping(value="/exportListMerchantAccountInAndOut")
	public void exportListMerchantAccountInAndOut(HttpServletResponse resp) throws IOException, RowsExceededException, WriteException{
		OutputStream os = null;
		os = resp.getOutputStream();
		resp.reset(); // 来清除首部的空白行
		resp.setHeader("Content-Disposition", "attachment; filename=" + "merchantAccountInAndOut"+new Date().getTime()+".xlsx");
		resp.setContentType("application/octet-stream; charset=utf-8");
		Criteria criteria = getCriteria();
		List<MerchantAccountAccess> list = settleMgrService.exportListMerchantAccountInAndOut(criteria);
		BIWorkbook bw=new BIWorkbook();
		BISheet bs = bw.addSheet();
		BIRow br_0 = bs.addRow();
		for(String str:new String[] { "商户号","终端号","收款账户名","账户类型","出入账类型","扣款类型","提现币种",
				"提现金额","商户渠道","状态","出入账时间","出款说明"}){
			br_0.addCell().setCellValue(str, null);
		}
		for (int row = 1; row <= list.size(); row++) {
			BIRow br_1 = bs.addRow();
			MerchantAccountAccess info = list.get(row-1);
			br_1.addCell().setCellValue(info.getMerNo(),null);
			br_1.addCell().setCellValue(info.getTerNo(),null);
			br_1.addCell().setCellValue(info.getMerchantName(),null);
			br_1.addCell().setCellValue(info.getAccountType()==0?"交易账户":"保证金账户",null);
			br_1.addCell().setCellValue(BaseDataListener.getStringColumnKey("CASHTYPE", info.getCashType()+"",  info.getCashType()+""),null);
			br_1.addCell().setCellValue(info.getDeductionType(),null);
			br_1.addCell().setCellValue(info.getCurrency(),null);
			br_1.addCell().setCellValue(info.getAmount()+"",null);
			br_1.addCell().setCellValue(BaseDataListener.getStringColumnKey("MERCHANT_CHANNEL", info.getMerchantChannel()+"",  info.getMerchantChannel()+""),null);
			br_1.addCell().setCellValue(BaseDataListener.getStringColumnKey("ACCESSSTATUS", info.getStatus(), info.getStatus()),null);
			br_1.addCell().setCellValue(null!=info.getMoneyDate() ?(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getMoneyDate())):"",null);
			br_1.addCell().setCellValue(info.getMoneyRemark(),null);
		}
		bw.workbook.write(os);	
		os.flush();
		os.close();
	}
	/**
	 * 显示结算条件变更历史记录
	 * */
	@RequestMapping(value="/listSettleTypeInfoLog")
	public String listSettleTypeInfoLog(){
		Criteria criteria=getCriteria();
		criteria.getCondition().put("type", "log");
		PageInfo<SettleTypeInfo> page=settleMgrService.listSettleTypeInfo(criteria);
		getRequest().setAttribute("page", page);
		return "settlemgr/listSettleTypeInfoLog";
	}
	/**
	 * 导出结算明细 exportMerchantInAndOutDetail
	 * */
	@RequestMapping(value = "/exportMerchantInAndOutDetail")
	public void exportMerchantInAndOutDetail(HttpServletResponse resp,String id,String merNo,String terNo,String cashType) throws Exception {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "transList.xls");
		List<ExportInfo> list=null;
		if("1".equals(cashType)){//交易提现
			list = settleMgrService.exportInfoForCash(id,merNo,terNo,cashType);
		}else if("2".equals(cashType)){//保证金提现
			list = settleMgrService.exportInfoForBondCash(id,merNo,terNo);
		}
		else{
			list= settleMgrService.exportInfo(id,merNo,terNo,cashType);
		}
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("出入账明细", 0);
		String[] headerName = { "商户号","终端号","流水号","订单号","交易金额","结算金额",
				"账户类型","出入账类型","保证金","手续费","单笔手续费","涉及金额","出入账时间" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			ExportInfo info = list.get(row - 1);
			sheet.addCell(new Label(col++, row, info.getMerNo()));
			sheet.addCell(new Label(col++, row, info.getTerNo()));
			sheet.addCell(new Label(col++, row, info.getTradeNo()));
			sheet.addCell(new Label(col++, row, info.getOrderNo()));
			sheet.addCell(new Label(col++, row, info.getCurrency()+" "+info.getAmount()));//"交易金额"
			sheet.addCell(new Label(col++, row, info.getSettleCurrency()+" "+info.getSettleAmount()));
			sheet.addCell(new Label(col++, row, "0".equals(info.getAccountType())?"交易账户":"保证金账户"));
			sheet.addCell(new Label(col++, row, BaseDataListener.getStringColumnKey("CASHTYPE", info.getCashType(), info.getCashType()) ));
			sheet.addCell(new Label(col++, row, info.getSettleCurrency()+" "+info.getBondAmount()));//保证金
			sheet.addCell(new Label(col++, row, info.getSettleCurrency()+" "+info.getMerForAmount()));//手续费
			sheet.addCell(new Label(col++, row, info.getSettleCurrency()+" "+info.getSingleFee()));//单笔手续费
			sheet.addCell(new Label(col++, row, info.getSettleCurrency()+" "+info.getOperateAmount()));//涉及金额
			
			sheet.addCell(new Label(col++, row, info.getOperateDate()));//出入账时间
		}
		book.write();
		book.close();
		return;
	}
	/**
	 * 账户结算条件管理
	 * */
	@RequestMapping(value="/listExceptionSettleType")
	public String listExceptionSettleType(){
		PageInfo<ExceptionSettleType> page=settleMgrService.queryExceptionSettleType(getCriteria());
		getRequest().setAttribute("page", page);
		return "settlemgr/listExceptionSettleType";
	}
	/**
	 * 跳转到添加账户结算条件
	 * */
	@RequestMapping(value="/goAddExceptionSettleTypeInfo")
	public String goAddExceptionSettleTypeInfo(){
		return "settlemgr/addExceptionSettleTypeInfo";
	}
	/**
	 * 添加账户结算条件
	 * */
	@RequestMapping(value="/addExceptionSettleTypeInfo")
	public ModelAndView addExceptionSettleTypeInfo(ExceptionSettleType info,DisFineStepInfo dfInfo){
		int count=settleMgrService.queryDuplicateExceptionSettleTypeInfo(info);
		if(count>0){
			return ajaxDoneError("信息重复添加");
		}
		info.setCreateBy(getLogAccount().getRealName());
	
		int i=settleMgrService.addExceptionSettleTypeInfo(info,dfInfo);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	/**
	 * 跳转到账户结算条件修改页面
	 * @throws ServiceException 
	 * */
	@RequestMapping(value="/goUpdateExceptionSettleTypeInfo")
	public String goUpdateExceptionSettleTypeInfo(String id) throws ServiceException{
		ExceptionSettleType info=settleMgrService.queryExceptionSettleTypeInfoById(id);
		if(info.getIsAllOrOver()==2||info.getIsAllOrOver()==3){
			throw new ServiceException("阶梯拒付罚金不允许修改！");
		}
		getRequest().setAttribute("info", info);
		return "settlemgr/updateExceptionSettleTypeInfo";
	}
	/**
	 * 修改账户结算条件
	 * */
	@RequestMapping(value="/updateExceptionSettleTypeInfo")
	public ModelAndView updateExceptionSettleTypeInfo(ExceptionSettleType info){
		info.setLastModifyBy(getLogAccount().getRealName());
		int count=settleMgrService.updateExceptionSettleTypeInfo(info);
		if(count>0){
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	/**
	 * 删除账户结算条件
	 * */
	@ResponseBody
	@RequestMapping(value="/deleteExceptionSettleTypeInfo")
	public ModelAndView deleteExceptionSettleTypeInfo(String[] ids){
		System.out.println(ids);
		int count=settleMgrService.deleteExceptionSettleTypeInfo(ids);
		if(count>0){
			return ajaxDoneSuccess("删除成功");
		}else{
			return ajaxDoneError("删除失败");
		}
	}
	/**
	 * 导出提现信息 exportInfos
	 */
	@RequestMapping(value = "/exportInfos")
	public void exportInfos(HttpServletResponse resp) throws Exception {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "transList.xls");
		Criteria criteria =getCriteria();
		criteria.getCondition().put("cashTypes", "(1,2,3,4,5,6,7,8,9,10)");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<MerchantAccountAccess> list = settleMgrService.exportCashInfos(criteria);
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("出入账明细", 0);
		String[] headerName = {  "id","商户号","终端号","账户类型","操作类型","币种","金额",
				"状态","申请 人","申请时间","审核人","审核时间","审核意见","复核人","复核时间","复核意见"
				,"出款人","出款时间","出款说明","收款账户","收款人","收款银行","收款国家","收款省","收款市","银行行号","商户渠道"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			MerchantAccountAccess info = list.get(row - 1);
			sheet.addCell(new Label(col++, row, info.getId()));
			sheet.addCell(new Label(col++, row, info.getMerNo()));
			sheet.addCell(new Label(col++, row, info.getTerNo()));
			sheet.addCell(new Label(col++, row, info.getAccountType()==0?"交易账户":"保证金账户"));
			sheet.addCell(new Label(col++, row, BaseDataListener.getStringColumnKey("CASHTYPE", info.getCashType()+"", info.getCashType()+"") ));
			sheet.addCell(new Label(col++, row, info.getCurrency()));
			sheet.addCell(new Label(col++, row, (info.getAmount().doubleValue()<0?(info.getAmount().doubleValue())*-1:info.getAmount().doubleValue())+""));
			sheet.addCell(new Label(col++, row, BaseDataListener.getStringColumnKey("ACCESSSTATUS",info.getStatus()+"",info.getStatus()+"")));
			sheet.addCell(new Label(col++, row, info.getCreateBy()));
			sheet.addCell(new Label(col++, row, info.getCreateDate()!=null?sdf.format(info.getCreateDate()):""));
			sheet.addCell(new Label(col++, row, info.getCheckBy()));
			sheet.addCell(new Label(col++, row, info.getCheckDate()!=null?sdf.format(info.getCheckDate()):""));
			sheet.addCell(new Label(col++, row, info.getCheckRemark()));
			sheet.addCell(new Label(col++, row, info.getReCheckBy()));
			sheet.addCell(new Label(col++, row, info.getReCheckDate()!=null?sdf.format(info.getReCheckDate()):""));
			sheet.addCell(new Label(col++, row, info.getReCheckRemark()));
			sheet.addCell(new Label(col++, row, info.getMoneyBy()));
			sheet.addCell(new Label(col++, row, info.getMoneyDate()!=null?sdf.format(info.getMoneyDate()):""));
			sheet.addCell(new Label(col++, row, info.getMoneyRemark()));
			sheet.addCell(new Label(col++, row, info.getAccountNo()));
			sheet.addCell(new Label(col++, row, info.getAccountName()));
			sheet.addCell(new Label(col++, row, info.getAccountAddress()));
			sheet.addCell(new Label(col++, row, info.getAccountContryCode()));
			sheet.addCell(new Label(col++, row, info.getAccountState()));
			sheet.addCell(new Label(col++, row, info.getAccountCity()));
			sheet.addCell(new Label(col++, row, info.getBankNo()));
			sheet.addCell(new Label(col++, row, BaseDataListener.getStringColumnKey("MERCHANT_CHANNEL", info.getMerchantChannel()+"",  info.getMerchantChannel()+"")));
		}
		book.write();
		book.close();
		return;
	}
	/**
	 * 导出商户虚拟账户信息
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	@RequestMapping(value="/exportMerchantAccount")
	public void exportMerchantAccount(HttpServletResponse resp) throws IOException, RowsExceededException, WriteException{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "merchantSettle.xls" ); 
		List<MerchantAccount> list = settleMgrService.exportMerchantAccountInfo(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("商户虚拟账户列表", 0);
		String[] headerName = { "商户号", "终端号","账户类型","账户总金额","可提现金额","冻结金额","导出时间"};
		// 写入标题
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateStr=sdf.format(date);
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			MerchantAccount info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getMerNo()));
			sheet.addCell( new Label(col++, row, info.getTerNo()));
			sheet.addCell( new Label(col++, row, info.getAccountType()==0?"交易账户":"保证金账户"));
			sheet.addCell( new Label(col++, row, info.getCurrency()+" "+info.getTotalAmount()));
			sheet.addCell( new Label(col++, row, info.getCurrency()+" "+info.getCashAmount()));
			sheet.addCell( new Label(col++, row, info.getCurrency()+" "+info.getFrozenAmount()));
			sheet.addCell( new Label(col++, row, dateStr));
		}
		book.write();
		book.close();
	}
	@RequestMapping(value="/goAddDeductionType")
	public String goAddDeductionType(){
		return "settlemgr/addDeductionType";
	}
	@RequestMapping(value="/addDeductionType")
	public ModelAndView addDeductionType(String deductionType){
		int i=settleMgrService.addDeductionType(deductionType);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	@RequestMapping(value="/listDeductionTypeInfo")
	public String listDeductionTypeInfo(){
		List<DeductionTypeInfo> list=settleMgrService.queryDeductionTypeInfo(getCriteria());
		getRequest().setAttribute("list", list);
		return "settlemgr/listDeductionType";
	}
	@RequestMapping(value="/goUpdateDeductionType")
	public String goUpdateDeductionType(String id){
		DeductionTypeInfo info=settleMgrService.queryDeductionTypeInfoById(id);
		getRequest().setAttribute("info", info);
		return "settlemgr/updateDeductionType";
	}
	@RequestMapping(value="/updateDeductionType")
	public ModelAndView updateDeductionType(DeductionTypeInfo info){
		int i=settleMgrService.updateDeductionType(info);
		if(i>0){
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	@RequestMapping(value="/deleteDeductionType")
	public ModelAndView deleteDeductionType(String id){
		int i=settleMgrService.deleteDeductionTypeById(id);
		if(i>0){
			return ajaxDoneSuccess("删除成功");
		}else{
			return ajaxDoneError("删除失败");
		}
	}
	
	/**
	 * 跳转到批量出款页面
	 * @return
	 */
	@RequestMapping(value="/goBatchMoneyInfo")
	public String goBatchMoneyInfo(){
		return "settlemgr/uploadCheckFile";
	}
	
	/**
	 * 下载批量出款模板
	 * @param resp
	 */
	@RequestMapping(value="/downBatchMoneyFile")
	public void downBatchMoneyFile(HttpServletResponse resp){
		ArrayList<String> strArray = new ArrayList<String> ();
		strArray.add("id,出款日期,出款备注");
		strArray.add("1001,2016-03-28 00:00:00,OK");
		strArray.add("1001,2016-03-28 00:00:00,OK");
		downloadFile(resp, "bacthMoneyFile"+new Date().getTime()+".txt", "modelType:id,moneyDate,moenyRemark", strArray);
		return;
	}
	
	/**
	 * 接收批量出款文件
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws ISOException 
	 */
	@RequestMapping(value="/acceptBatchMoneyFile")
	public ModelAndView acceptBatchMoneyFile(DefaultMultipartHttpServletRequest request) throws Exception{
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		if(null != files){
			log.info("上传了"+files.size()+"个文件。");
			List<MerchantAccountAccess> list = new ArrayList<MerchantAccountAccess>();
			StringBuffer sb = new StringBuffer();//未匹配数据
			for(MultipartFile file :files){
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8"));//构造一个BufferedReader类来读取文件
				String line = br.readLine();
				int length = Tools.replaceBlank(line).length();//第一列
				log.info("第一列：line:"+line+"字符长度为："+length);
				String [] strInfo = line.split(":");
				line = br.readLine();//读取第二列
				line = br.readLine();//读取第3列
				int lineCount = 3;//从第3行开始读
				while(null != line){
					log.info("line:"+line);
					String [] fileds = line.split(",");
					log.info( "在第一列长度为10 的循环里面，第"+lineCount+ "行数据：" + line);
					int index = 0;
					if(fileds.length==3){
						MerchantAccountAccess info=new MerchantAccountAccess();
						String id=fileds[index++];
						String moneyDateStr=fileds[index++];
						String moneyRemark=fileds[index++];
						info.setId(id);
						info.setMoneyDateStr(moneyDateStr);
						info.setMoneyRemark(moneyRemark);
						info = settleMgrService.queryMerchantAccountAccessById(info.getId());
						if(null!=info){
							
							if(("3".equals(info.getStatus()) || "5".equals(info.getStatus()) )&&(1==info.getCashType() || 2 == info.getCashType())) {
								info.setMoneyRemark(moneyRemark);
								info.setMoneyDateStr(moneyDateStr);
								info.setMoneyBy(getLogAccount().getRealName());
								list.add(info);
								log.info(info.getId()+"上传数据已匹配");
							}else{
								log.info(line+ "未匹配上传数据");
								sb.append("第"+lineCount+ "行数据：" + line+",状态不是待出款状态;");
							}
						}else{
							sb.append("第"+lineCount+ "行数据：" + line+",数据不存在;");
						}
						
					}else{
						sb.append("第"+lineCount+ "行数据：" + line+",数据格式有误;");
					}
					line = br.readLine();
					lineCount++;
				}
			}
			getRequest().getSession().setAttribute("batchMoneyInfolist", list);
			
			getRequest().getSession().setAttribute("batchMoneyInfosb", sb + "");
		}
		return ajaxDoneSuccess("上传成功。返回信息");
	}
	
	@RequestMapping(value ="/goShowBatchMoneyInfo")
	public String goShowBatchMoneyInfo(){
		@SuppressWarnings("unchecked")
		List<MerchantAccountAccess> list = (List<MerchantAccountAccess>)getRequest().getSession().getAttribute("batchMoneyInfolist");
		String sb = (String)getRequest().getSession().getAttribute("batchMoneyInfosb");
		getRequest().getSession().removeAttribute("batchMoneyInfosb");
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("sb", sb);
		return "settlemgr/acceptCheckPageList";
	}
	
	/** 
	 * 批量出款
	 * @throws ServiceException 
	 * */
	@ResponseBody
	@RequestMapping(value ="/batchMoneyInfo")
	public ModelAndView batchMoneyInfo(String [] ids) throws ServiceException{
		@SuppressWarnings("unchecked")
		List<MerchantAccountAccess> list = (List<MerchantAccountAccess>)getRequest().getSession().getAttribute("batchMoneyInfolist");
		getRequest().getSession().removeAttribute("batchMoneyInfolist");
		int totalCount = ids.length;
		int count=0;
		for(String id:ids){
			for(MerchantAccountAccess info:list){
				if(id.equals(info.getId())){
					info.setStatus("5");
					info.setCheckBy(null);
					info.setReCheckBy(null);
					info.setMoneyBy(getLogAccount().getRealName());
					count+=settleMgrService.checkMerchantAccountAccess(info);
					break;
				}
			}
		}
		return ajaxDoneSuccess("选着条数数为：" + totalCount +" ;成功条数为：" + count);
	}
	/**
	 * 查询可以手工结算的订单
	 * @return
	 */
	@RequestMapping(value="/queryCanHandTransInfo")
	public String queryCanHandTransInfo(){
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			
		}else{
			PageInfo<TransInfo> list=settleMgrService.queryCanHandTransInfo(getCriteria());
			getRequest().setAttribute("list", list);
		}
		return "settlemgr/listCanHandTransInfo";
	}
	
	@RequestMapping(value="/addHandTransInfo")
	public ModelAndView addHandTransInfo(String[] tradeNos){
		List<HandTransInfo> list=settleMgrService.queryHandTransInfoByTradeNos(tradeNos);
		int i=0;
		for(HandTransInfo info:list){
			info.setCreateBy(getLogAccount().getRealName());
		}
		try {
			i=settleMgrService.addHandTransInfo(list);
		} catch (Exception e) {
			i=0;
			e.printStackTrace();
		}
		if(i>0){
			return ajaxDoneSuccess("添加成功"+i+"条订单");
		}else{
			return ajaxDoneSuccess("添加异常");
		}
	}
	/**
	 * 查询可以手工结算的订单状态
	 * @return
	 */
	@RequestMapping(value="/queryHandTransInfo")
	public String queryHandTransInfo(){
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			
		}else{
			PageInfo<HandTransInfo> list=settleMgrService.queryHandTransInfo(getCriteria());
			getRequest().setAttribute("page", list);
		}
		return "settlemgr/listHandTransInfo";
	}
	
	/**
	 * 查询商户交易报告
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/queryPoolSettleInfo")
	public String queryPoolSettleInfo() throws ServiceException{
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			
		}else{
			if(null==getCriteria().getCondition().get("merNo")||"".equals(getCriteria().getCondition().get("merNo"))){
				throw new ServiceException("商户号不能为空!");
			}
			PoolSettleInfo total=settleMgrService.queryTotalPoolSettleInfo(getCriteria());
			PageInfo<PoolSettleInfo> list=settleMgrService.queryPoolSettleInfo(getCriteria());
			getRequest().setAttribute("total", total);
			getRequest().setAttribute("page", list);
		}
		return "settlemgr/queryPoolSettleInfo";
	}
	/**
	 * 导出商户交易报告
	 * @param resp
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	@RequestMapping(value="/exportPoolSettleInfo")
	public void exportPoolSettleInfo(HttpServletResponse resp) throws IOException, RowsExceededException, WriteException{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "merchantSettle.xls" ); 
		List<PoolSettleInfo> list = settleMgrService.exportPoolSettleInfo(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("商户虚拟账户列表", 0);
		String[] headerName = { "日期" , "商户号", "终端号","交易额","手续费","单笔手续费","保证金","退款"
				,"拒付","冻结","解冻","提现","其他","余额"};
		// 写入标题
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateStr=sdf.format(date);
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		int row = 1;
		for (; row <= list.size(); row++) {
			int col = 0;
			PoolSettleInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getDateStr()));
			sheet.addCell( new Label(col++, row, info.getMerNo()));
			sheet.addCell( new Label(col++, row, info.getTerNo()));
			sheet.addCell( new Label(col++, row, info.getCurrency()+" "+info.getSettleAmount()));
			sheet.addCell( new Label(col++, row, info.getCurrency()+" "+info.getFeeAmount()));
			sheet.addCell( new Label(col++, row, info.getCurrency()+" "+info.getSingleAmount()));
			sheet.addCell( new Label(col++, row, info.getCurrency()+" "+info.getBondAmount()));
			sheet.addCell( new Label(col++, row, info.getCurrency()+" "+info.getRefundAmount()));
			sheet.addCell( new Label(col++, row, info.getCurrency()+" "+info.getDisAmount()));
			sheet.addCell( new Label(col++, row, info.getCurrency()+" "+info.getFroznAmount()));
			sheet.addCell( new Label(col++, row, info.getCurrency()+" "+info.getThawAmount()));
			sheet.addCell( new Label(col++, row, info.getCurrency()+" "+info.getCashAmount()));
			sheet.addCell( new Label(col++, row, info.getCurrency()+" "+info.getOtherAmount()));
			BigDecimal balance=info.getSettleAmount().
					subtract(info.getFeeAmount()).
					subtract(info.getSingleAmount()).
					add(info.getRefundAmount()).
					add(info.getDisAmount()).
					add(info.getThawAmount()).
					add(info.getFroznAmount()).
					add(info.getCashAmount())
					.add(info.getOtherAmount());
			sheet.addCell( new Label(col++, row, info.getCurrency()+" "+balance));
		}
		PoolSettleInfo total=settleMgrService.queryTotalPoolSettleInfo(getCriteria());
		sheet.addCell( new Label(0, row, "总计："));
		sheet.addCell( new Label(1, row, total.getCurrency()+" "+total.getTotalAmount()));
		book.write();
		book.close();
	}
	
	@RequestMapping(value="/exportPoolSettleInfoDesc")
	public void exportPoolSettleInfoDesc(HttpServletResponse resp,String merNo,String terNo,String dateStr) throws IOException, RowsExceededException, WriteException{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ dateStr+"--"+merNo+"-"+terNo+"交易报告明细"+".xls" ); 
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		{
			WritableSheet sheet = book.createSheet("交易额明细", 0);
			String[] headerName = { "流水号","订单号" , "商户号", "终端号","交易金额","结算金额","手续费","单笔手续费","保证金"};
			// 写入标题
			for (int index = 0; index < headerName.length; index++) {
				Label label = new Label(index, 0, headerName[index]);
				sheet.addCell(label);
			}
			List<TransInfo> list = settleMgrService.exportPoolSettleInfoForTrans(getCriteria());
			int row = 1;
			for (; row <= list.size(); row++) {
				int col = 0;
				TransInfo info = list.get(row-1);
				sheet.addCell( new Label(col++, row, info.getTradeNo()));
				sheet.addCell( new Label(col++, row, info.getOrderNo()));
				sheet.addCell( new Label(col++, row, info.getMerNo()));
				sheet.addCell( new Label(col++, row, info.getTerNo()));
				sheet.addCell( new Label(col++, row, info.getMerBusCurrency()+" "+info.getMerTransAmount()));
				sheet.addCell( new Label(col++, row, info.getMerSettleCurrency()+" "+info.getMerSettleAmount()));
				sheet.addCell( new Label(col++, row, info.getMerSettleCurrency()+" "+info.getMerForAmount()));
				sheet.addCell( new Label(col++, row, info.getMerSettleCurrency()+" "+info.getSingleFee()));
				sheet.addCell( new Label(col++, row, info.getMerSettleCurrency()+" "+info.getBondAmount()));
			}
		}
		{
			WritableSheet sheet = book.createSheet("拒付、冻结、解冻、退款、失败订单结算明细", 1);
			String[] headerName = { "流水号","订单号" , "商户号", "终端号","交易金额","结算金额","异常类型","异常金额"};
			// 写入标题
			for (int index = 0; index < headerName.length; index++) {
				Label label = new Label(index, 0, headerName[index]);
				sheet.addCell(label);
			}
			List<ExportInfo> list = settleMgrService.exportExceptiontTransInfo(getCriteria());
			int row = 1;
			for (; row <= list.size(); row++) {
				int col = 0;
				ExportInfo info = list.get(row-1);
				sheet.addCell(new Label(col++, row, info.getTradeNo()));
				sheet.addCell(new Label(col++, row, info.getOrderNo()));
				sheet.addCell(new Label(col++, row, info.getMerNo()));
				sheet.addCell(new Label(col++, row, info.getTerNo()));
				sheet.addCell(new Label(col++, row, info.getCurrency()+" "+info.getAmount()));
				sheet.addCell(new Label(col++, row, info.getSettleCurrency()+" "+info.getSettleAmount()));
				sheet.addCell(new Label(col++, row, BaseDataListener.getStringColumnKey("CASHTYPE", info.getCashType(), info.getCashType()) ));
				sheet.addCell(new Label(col++, row, info.getSettleCurrency()+" "+info.getOperateAmount()));
			}
		}
		{
			WritableSheet sheet = book.createSheet("提现及其他明细", 2);
			Criteria criteria=getCriteria();
			criteria.getCondition().put("cashTypes", "(1,2,5,6,7,8)");
			List<MerchantAccountAccess> list = settleMgrService.exportListMerchantAccountInAndOut(criteria);
			String[] headerName = { "商户号","终端号","类型","扣款类型","币种",
					"金额"};
			// 写入标题
			for (int index = 0; index < headerName.length; index++) {
				Label label = new Label(index, 0, headerName[index]);
				sheet.addCell(label);
			}
			for (int row = 1; row <= list.size(); row++) {
				int col = 0;
				MerchantAccountAccess info = list.get(row-1);
				sheet.addCell( new Label(col++, row, info.getMerNo()));
				sheet.addCell( new Label(col++, row, info.getTerNo()));
				sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("CASHTYPE", info.getCashType()+"",  info.getCashType()+"")));
				sheet.addCell( new Label(col++, row, info.getDeductionType()));
				sheet.addCell( new Label(col++, row, info.getCurrency()));
				sheet.addCell( new Label(col++, row, info.getAmount()+""));
			}
		}
		book.write();
		book.close();
	}
	
	
}
