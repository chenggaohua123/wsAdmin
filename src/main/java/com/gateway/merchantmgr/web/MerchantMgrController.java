package com.gateway.merchantmgr.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.ibatis.annotations.Param;
import org.jpos.iso.ISOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.gateway.bankmgr.model.CurrencyInfo;
import com.gateway.bankmgr.model.MasteKey;
import com.gateway.bankmgr.service.BankMgrService;
import com.gateway.brandProduct.service.BrandProductService;
import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.BaseDataListener;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.APIException;
import com.gateway.common.excetion.ServiceException;
import com.gateway.common.utils.SHA256Utils;
import com.gateway.common.utils.UUIDGenerator;
import com.gateway.currency.mapper.CurrencyMapper;
import com.gateway.fraud.common.util.RequestUtil;
import com.gateway.fraud.model.MerchantRefRuleProfileInfo;
import com.gateway.fraud.service.FraudManageService;
import com.gateway.loginmgr.model.UserInfo;
import com.gateway.merchantmgr.mapper.MerchantMgrDao;
import com.gateway.merchantmgr.model.AgentInfo;
import com.gateway.merchantmgr.model.BatchUpdateCurrencyRelMerchantInfo;
import com.gateway.merchantmgr.model.BrandProductInfo;
import com.gateway.merchantmgr.model.CopyMerchantTerInfo;
import com.gateway.merchantmgr.model.CountryCurrencyInfo;
import com.gateway.merchantmgr.model.CountryCurrencyLogInfo;
import com.gateway.merchantmgr.model.GwAgentRelMerchant;
import com.gateway.merchantmgr.model.GwMerchantPaymentPage;
import com.gateway.merchantmgr.model.GwTernoLmitInfo;
import com.gateway.merchantmgr.model.GwUserRelMerchantInfo;
import com.gateway.merchantmgr.model.MccInfo;
import com.gateway.merchantmgr.model.MerchantConfig;
import com.gateway.merchantmgr.model.MerchantCurrencySpecialInfo;
import com.gateway.merchantmgr.model.MerchantDisFineInfo;
import com.gateway.merchantmgr.model.MerchantInfo;
import com.gateway.merchantmgr.model.MerchantRelCurrencyInfo;
import com.gateway.merchantmgr.model.MerchantSettleCycle;
import com.gateway.merchantmgr.model.MerchantTerInfo;
import com.gateway.merchantmgr.model.MerchantTypeInfo;
import com.gateway.merchantmgr.model.MerchantWebsite;
import com.gateway.merchantmgr.model.RegCodeInfo;
import com.gateway.merchantmgr.model.TerSnRelAgentInfo;
import com.gateway.merchantmgr.model.TransSettingInfo;
import com.gateway.merchantmgr.service.MerchantMgrService;
import com.gateway.merchantmgr.utils.TerStringUtils;
import com.gateway.ratemgr.model.RateInfo;
import com.gateway.ratemgr.service.RateMgrService;
import com.gateway.riskmgr.model.CountryInfo;
import com.gateway.riskmgr.service.RiskMgrService;
import com.gateway.settlemgr.model.SettleTypeInfo;
import com.gateway.settlemgr.service.SettleMgrService;
import com.gateway.suspicious.service.SuspiciousManageService;
import com.gateway.sysmgr.service.SysMgrService;
import com.gateway.transmgr.model.TransInfo;

@Controller
@RequestMapping(value="/merchantmgr")
public class MerchantMgrController extends BaseController{
	@Resource
	private MerchantMgrService merchantMgrService;
	
	
	@Autowired
	private RiskMgrService riskMgrService;
	@Resource
	private SysMgrService sysMgrService;
	
	@Resource
	private BankMgrService bankMgrService;
	@Autowired
	private RateMgrService rateMgrService;
	
	
	@Autowired
	private MerchantMgrDao merchantMgrDao;
	@Autowired
	private SettleMgrService settleMgrService;
	
	@Autowired
	private FraudManageService fraudManageService;
	
	@Autowired
	private SuspiciousManageService suspiciousManageService;
	
	@Autowired
	private BrandProductService brandProductService;
	@Autowired
	private CurrencyMapper currencyMapper;
	
	/**
	 * ??????????????????
	 * @return
	 */
	@RequestMapping(value="/getListMerchant")
	public String getListMerchant(){
		if(!"get".equalsIgnoreCase(getRequest().getMethod())){
			PageInfo<MerchantInfo> page = merchantMgrService.getListMerchant(getCriteria());
			getRequest().setAttribute("page",page);
		}
		return "merchantmgr/merchantList";
	}
	
	/**
	 * ??????????????????????????????
	 * @return
	 */
	@RequestMapping(value="/getMerchantTerList")
	public String getMerchantTerList(){
		if(!"get".equalsIgnoreCase(getRequest().getMethod())){
			PageInfo<MerchantTerInfo> page = merchantMgrService.getMerchantTerList(getCriteria());
			getRequest().setAttribute("page",page);
		}
		return "merchantmgr/terList";
	}
	
	/**
	 * ??????????????????????????????
	 * @return
	 */
	@RequestMapping(value="/goUpdateMerchant")
	public String goUpdateMerchant(int id){
		MerchantInfo merchantInfo= merchantMgrService.queryMerchantInfoById(id);
		getRequest().setAttribute("merchantInfo", merchantInfo);
		return "merchantmgr/updateMerchantInfo";
	}
	
	/**
	 * ??????????????????????????????
	 * @param merchantInfo
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/updateMerchant")
	public ModelAndView updateMerchant(MerchantInfo merchantInfo) throws ServiceException{
		merchantInfo.setUpdatePerson(getLogAccount().getRealName());
		int i=merchantMgrService.updateMerchantInfo(merchantInfo);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/**
	 * ????????????????????????
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/queryMerchantLogByMerNoList")
	public String queryMerchantLogByMerNoList(int id){
		MerchantInfo merchantInfo= merchantMgrService.queryMerchantInfoById(id);
		List<MerchantInfo> list=merchantMgrService.queryMerchantLogList(merchantInfo.getMerNo());
		getRequest().setAttribute("list", list);
		return "merchantmgr/merchantListLog";
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
		return "merchantmgr/merchantinfo";
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
	
	/**
	 * ????????????????????????
	 * @param id
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/goAddMerchantConfig")
	public String goAddMerchantConfig(int id) throws ServiceException{
		MerchantTerInfo merchantTerInfo= merchantMgrService.queryTerInfoById(id);
		if(null == merchantTerInfo){
			throw new ServiceException("?????????????????????");
		}
		getRequest().setAttribute("terInfo", merchantTerInfo);
		return "merchantmgr/addMerchantConfigInfo";
	}
	
	/**
	 * ??????????????????
	 * @param info
	 * @return
	 */
	@RequestMapping(value="/addMerchantConfig")
	public ModelAndView addMerchantConfig(MerchantConfig conInfo){
		conInfo.setCreateBy(getLogAccount().getRealName());
		int count = merchantMgrService.addMerchantConfig(conInfo);
		if(count > 0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	
	/**
	 * ????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value="/goAddTerInfo")
	public String goAddTerInfo(int id) throws APIException{
		MerchantInfo merchantInfo= merchantMgrService.queryMerchantInfoById(id);
		if(StringUtils.isEmpty(merchantInfo)){
			throw new APIException("??????????????????????????????");
		}
		MerchantTerInfo terInfo=new MerchantTerInfo();
		terInfo.setSourceCurrencyCode(merchantMgrService.querySourceCurrencyCode());
		getRequest().setAttribute("terInfo", terInfo);
		getRequest().setAttribute("merchantInfo", merchantInfo);
		return "merchantmgr/addMerchantTerInfo";
	}
	
	/**
	 * ??????????????????
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/addTerInfo")
	public ModelAndView addTerInfo(MerchantTerInfo terInfo ) throws ServiceException{
		MerchantInfo info = merchantMgrService.queryMerchantInfoByMerNo(terInfo.getMerNo());
		if(null == info){
			throw new ServiceException("?????????????????????");
		}
		String temp = merchantMgrService.queryMaxTerNo(terInfo.getMerNo());
		int num=Integer.valueOf(temp)+1;
		String terNo="";
		if(String.valueOf(num).length()<=1){
			terNo="0"+num;
		}else{
			terNo=String.valueOf(num);
		}
		terInfo.setTerNo(terNo);
		terInfo.setCreateby(getLogAccount().getRealName());
		terInfo.setShaKey(UUIDGenerator.nextId());
		int count = merchantMgrService.addTerInfo(terInfo);
		if(count > 0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	
	/**
	 * ????????????????????????
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/goUpdateTerInfo")
	public String goUpdateTerInfo(int id) throws ServiceException{
		MerchantTerInfo terInfo = merchantMgrService.queryTerInfoById(id);
		if(null == terInfo){
			throw new ServiceException("??????????????????");
		}
		terInfo.setSourceCurrencyCode(merchantMgrService.querySourceCurrencyCode());
		getRequest().setAttribute("terInfo", terInfo);
		return "merchantmgr/updateMerchantTerInfo";
	}
	
	/**
	 * ??????????????????
	 * @param terInfo
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/updateMerchantTerInfo")
	public ModelAndView updateMerchantTerInfo(MerchantTerInfo terInfo) throws ServiceException{
		terInfo.setUpdateBy(getLogAccount().getRealName());
		terInfo.setTransCurrency(terInfo.getTransCurrency().trim());
		int count = merchantMgrService.updateMerchantTerInfo(terInfo);
		if(count > 0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("???????????????");
		}
		
	}
	
	/**
	 * ????????????????????????
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/queryMerchantConfigList")
	public String queryMerchantConfigList(int id){
		MerchantTerInfo terInfo = merchantMgrService.queryTerInfoById(id);
		MerchantConfig config=new MerchantConfig();
		config.setTerNo(terInfo.getTerNo());
		List<MerchantConfig> configList=merchantMgrService.queryMerchantConfigInfo(config);
		getRequest().setAttribute("list", configList);
		return "merchantmgr/merchantConfigInfo";
	}
	
	/**
	 * ??????????????????????????????
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/queryTerInfoHis")
	public String queryTerInfoHis(int id){
		MerchantTerInfo terInfo = merchantMgrService.queryTerInfoById(id);
		List<MerchantTerInfo> list=merchantMgrService.queryTerInfoByMerNoAndTerNoLog(terInfo.getMerNo(), terInfo.getTerNo());
		getRequest().setAttribute("list", list);
		return "merchantmgr/merchantConfigListLog";
	}
	
	@RequestMapping(value="/queryTerInfo")
	public String queryTerInfo(int id){
		MerchantTerInfo terInfo = merchantMgrService.queryTerInfoById(id);
		getRequest().setAttribute("terInfo", terInfo);
		return "merchantmgr/merchantTerinfo";
	}
	
	/**
	 * ????????????????????????
	 * @return
	 */
	@RequestMapping(value="/getMerchnatRelCurrencyList")
	public String getMerchnatRelCurrencyList(){
		if(!"get".equalsIgnoreCase(getRequest().getMethod())){
			PageInfo<MerchantRelCurrencyInfo> page = merchantMgrService.getMerchnatRelCurrencyList(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "merchantmgr/merchantRelCurrencyList";
	}
	
	/**
	 * ?????????????????????????????????
	 * @return
	 */
	@RequestMapping(value="/goAddCurrencyToMerchant")
	public String goAddCurrencyToMerchant(){
		return "merchantmgr/addCurrencyToMerchnat"; 
	}
	
	/**
	 * ????????????????????????
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/addCurrencyToMerchnat")
	public ModelAndView addCurrencyToMerchnat(MerchantRelCurrencyInfo  info) throws ServiceException{
		
		info.setCreateBy(getLogAccount().getRealName());
		int count = merchantMgrService.addCurrencyToMerchnat(info);
		if(count > 0){
			return ajaxDoneSuccess("???????????????");
		}else{
			return ajaxDoneSuccess("???????????????");
		}
	}
	
	/**
	 * ???????????????????????????????????????
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/goUpdateCurrencyToMerchnat")
	public String goUpdateCurrencyToMerchnat(int id){
		MerchantRelCurrencyInfo info = merchantMgrService.queryMerchantRelCurrencyById(id);
		getRequest().setAttribute("info", info);
		return "merchantmgr/updateCurrencyToMerchnat";
	}
	
	/**
	 * ??????????????????????????????
	 * @param info
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value="/updateCurrencyToMechant")
	public ModelAndView updateCurrencyToMechant(MerchantRelCurrencyInfo info) throws ServiceException{
		info.setUpdateBy(getLogAccount().getRealName());
		int count = merchantMgrService.updateCurrencyToMerchnat(info);
		if(count > 0){
			return ajaxDoneSuccess("???????????????");
		}else{
			return ajaxDoneSuccess("???????????????");
		}
	}
	
	/**
	 * ???????????????????????????????????????
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value="/getCurrencyToMerchnatHisList")
	public String getCurrencyToMerchnatHisList(int id) throws ServiceException{
		List<MerchantRelCurrencyInfo> list = merchantMgrService.getCurrencyToMerchnatHisList(id);
		getRequest().setAttribute("list", list);
		return "merchantmgr/merchantRelCurrencyHisList";
	}
	
	/**
	 * ??????????????????????????????
	 * @return
	 */
	@RequestMapping(value="/goBatchUpdateCurrencyToMerchant")
	public String goBatchUpdateCurrencyToMerchant(){
		return "merchantmgr/batchUpdateCurrencyToMerchant";
	}
	
	@RequestMapping(value="/batchUpdateCurrencyToMerchant")
	public ModelAndView batchUpdateCurrencyToMerchant(BatchUpdateCurrencyRelMerchantInfo info) throws ServiceException{
		info.setUpdateBy(getLogAccount().getRealName());
		int count  = merchantMgrService.batchUpdateCurrencyToMerchant(info);
		return ajaxDoneSuccess("??????????????????????????????"+count+"??????");
	}
	
	@RequestMapping(value="/getAgentList")
	public String getAgentList(){
		PageInfo<AgentInfo> page = merchantMgrService.getAgentList(getCriteria());
		getRequest().setAttribute("page", page);
		return "merchantmgr/agentList";
	}
	
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping(value="/goAddAgentInfo")
	public String goAddAgentInfo(){
		return "merchantmgr/addAgentInfo";
	}
	
	/**
	 * ???????????????
	 * @param agentInfo
	 * @return
	 */
	@RequestMapping(value="/addAgentInfo")
	public ModelAndView addAgentInfo(AgentInfo agentInfo){
		agentInfo.setCreateBy(getLogAccount().getRealName());
		if(null==merchantMgrService.queryAgentByAgentNo(agentInfo.getAgentNo())){
			int i=merchantMgrService.addAgentInfo(agentInfo);
			if(i > 0){
				return ajaxDoneSuccess("???????????????");
			}else{
				return ajaxDoneError("???????????????");
			}
		}else{
			return ajaxDoneError("??????????????????????????????");
		}
	}
	
	
	/**
	 * ???????????????????????????
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/goUpdateAgentInfo")
	public String goUpdateAgentInfo(int id){
		AgentInfo agentInfo=merchantMgrService.queryAgentById(id);
		getRequest().setAttribute("agentInfo", agentInfo);
		return "merchantmgr/updateAgentInfo";
	}
	
	/**
	 * ???????????????
	 * @param agentInfo
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value="/updateAgentInfo")
	public ModelAndView updateAgentInfo(AgentInfo agentInfo) throws ServiceException{
		agentInfo.setCreateBy(getLogAccount().getRealName());
		int i=merchantMgrService.updateAgentInfo(agentInfo);
		if(i > 0){
			return ajaxDoneSuccess("???????????????");
		}else{
			return ajaxDoneSuccess("???????????????");
		}
	}
	
	/**
	 * ???????????????????????????
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/queryAgentLog")
	public String queryAgentLog(int id){
	  AgentInfo agentInfo=merchantMgrService.queryAgentById(id);
	   List<AgentInfo> list=merchantMgrService.queryAgentLog(agentInfo.getAgentNo());
	   getRequest().setAttribute("list", list);
	   return "merchantmgr/agentListLog";
	}
	
	/**
	 * ????????????????????????
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/queryAgentInfo")
	public String queryAgentInfo(int id){
		AgentInfo agentInfo=merchantMgrService.queryAgentById(id);
		getRequest().setAttribute("agentInfo", agentInfo);
		return "merchantmgr/agentInfo";
	}
	
	/**
	 * ?????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(value="/getMerListbrighBack")
	public String getMerListbrighBack(){
		criteria = getCriteria();
		criteria.getCondition().put("parentAgentNo", "0");
		criteria.getCondition().put("enabled", "1");
		PageInfo<AgentInfo> page = merchantMgrService.getAgentList(criteria);
		getRequest().setAttribute("list", page);
		return "merchantmgr/merchantListBrightBack";
	}
	
	/**
	 * ??????????????????????????????
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/goUpdateMerchantConfig")
	public String goUpdateMerchantConfig(int id){
		MerchantConfig config=merchantMgrService.queryMerchantConfigById(id);
		getRequest().setAttribute("config", config);
		return "merchantmgr/updateMerchantConfigInfo";
	}
	
	/**
	 * ??????????????????
	 * @param config
	 * @return
	 */
	@RequestMapping(value="/updateMerchantConfig")
	public ModelAndView updateMerchantConfig(MerchantConfig config){
		config.setCreateBy(getLogAccount().getRealName());
		int i=merchantMgrService.updateMerchantConfig(config);
		if(i > 0){
			return ajaxDoneSuccess("???????????????");
		}else{
			return ajaxDoneSuccess("???????????????");
		}
	}
	
	/**
	 * ??????????????????
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/deleteMerchantConfig")
	public ModelAndView deleteMerchantConfig(int id){
		int i=merchantMgrService.deleteMerchantConfig(id);
		if(i > 0){
			return ajaxDoneSuccess("???????????????");
		}else{
			return ajaxDoneSuccess("???????????????");
		}
	}
	
	/**
	 * ?????????????????????
	 * @return
	 */
	@RequestMapping(value="/goMerchantRelAgent")
	public String goMerchantRelAgent(int id){
		MerchantTerInfo terInfo=merchantMgrService.queryMerchantTerNoById(id);
		getRequest().setAttribute("terInfo", terInfo);
		return "merchantmgr/addMerchantRelAgent";
	}
	
	
	/**
	 * ??????????????????????????? ????????????
	 * @return
	 */
	@RequestMapping("/merchantRelAgentBack")
	public String merchantRelAgent(){
		PageInfo<AgentInfo> info=merchantMgrService.getAgentList(getCriteria());
		getRequest().setAttribute("info", info);
		return "merchantmgr/merchantRelagentListBack";
	}
	
	/**
	 * ???????????????
	 * @param merNo
	 * @param agentNo
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/merchantRelAgent")
	public ModelAndView merchantRelAgent(String merNo,String terNo,String agentNo) throws ServiceException{
		GwAgentRelMerchant relMerchant=merchantMgrService.queryAgentRelMerchant(merNo,terNo);
		if(relMerchant!=null){
			throw new ServiceException("?????????????????????");
		}
		int i=merchantMgrService.addMerchantRelAgent(merNo,terNo,getLogAccount().getRealName());
		if(i > 0){
			return ajaxDoneSuccess("???????????????");
		}else{
			return ajaxDoneSuccess("???????????????");
		}
	}
	
	

	/**
	 *????????????
	 * @return
	 */
	@RequestMapping(value="/goRefMerchantUser")
	public String gorefMerchantUser(int id){
		MerchantInfo merchantInfo=merchantMgrService.queryMerchantInfoById(id);
		getRequest().setAttribute("merchantInfo", merchantInfo);
		return "merchantmgr/addMerchantRelUser";
	}
	
	
	/**
	 * ???????????????????????? ????????????
	 * @return
	 */
	@RequestMapping("/merchantRelUserBack")
	public String merchantRelUser(){
		PageInfo<UserInfo> page = sysMgrService.queryPageUser(getCriteria());
		getRequest().setAttribute("page", page);
		return "merchantmgr/merchantRelUserListBack";
	}
	
	
	/**
	 * ??????????????????
	 * @param merNo
	 * @param agentNo
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/merchantRelUser")
	public ModelAndView merchantRelUser(GwUserRelMerchantInfo info) throws ServiceException{
		GwUserRelMerchantInfo userRelMerchantInfo=merchantMgrService.queryUserRelMerchant(info.getMerNo());
		if(userRelMerchantInfo!=null){
			throw new ServiceException("??????????????????");
		}
		info.setCreateBy(getLogAccount().getRealName());
		int i=merchantMgrService.addUserRefMerchant(info);
		if(i > 0){
			return ajaxDoneSuccess("???????????????");
		}else{
			return ajaxDoneSuccess("???????????????");
		}
	}
	
	
	
	
	
	
	/**
	 * ??????????????????
	 * @throws IOException   
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	@RequestMapping(value="/merchantExportTrans")
	public void exportTrans(HttpServletResponse resp) throws IOException, RowsExceededException, WriteException{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "merchantList.xls" ); 
		List<MerchantInfo> list = merchantMgrService.exportListMerchant(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("????????????", 0);
		String[] headerName = { "?????????", "????????????","???????????????","????????????","????????????","QQ???","????????????","????????????","????????????","????????????","?????????","OA????????????","??????????????????","??????????????????","??????????????????","????????????","????????????","????????????","??????????????????","??????"
				,"????????????","SWIFT","????????????"};
		// ????????????
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			MerchantInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getMerNo()));
			sheet.addCell( new Label(col++, row, info.getMerchantName()));
			sheet.addCell( new Label(col++, row, info.getLinkName()));
			sheet.addCell( new Label(col++, row, info.getPhoneNo()));
			sheet.addCell( new Label(col++, row, info.getEmail()));
			sheet.addCell( new Label(col++, row, info.getQq()));
			sheet.addCell( new Label(col++, row, info.getAddress()));
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("MERCHANTSTATUS",String.valueOf(info.getEnabled()),String.valueOf(info.getEnabled()))));
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("MERCHANT_ACCOUNT_STATUS",String.valueOf(info.getAccountStatus()),String.valueOf(info.getAccountStatus()))));
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("MERCHANT_DIR_STATUS",String.valueOf(info.getDirStatus()),String.valueOf(info.getDirStatus()))));
			sheet.addCell( new Label(col++, row, info.getSales()));
			sheet.addCell( new Label(col++, row, info.getOaOrderNo()));
			sheet.addCell( new Label(col++, row, info.getRegDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getRegDate()):""));
			sheet.addCell( new Label(col++, row, info.getActivationDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getActivationDate()):""));
			sheet.addCell( new Label(col++, row, info.getExpireDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getExpireDate()):""));
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("MERCHANTTYPE",String.valueOf(info.getType()),String.valueOf(info.getType()))));
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("MERCHNAT_CURRENCY_TYPE",String.valueOf(info.getMerCurrencyType()),String.valueOf(info.getMerCurrencyType()))));
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("INDUSTRYTYPE",String.valueOf(info.getIndustry()),String.valueOf(info.getIndustry()))));
			sheet.addCell( new Label(col++, row, info.getTopRate()));
			sheet.addCell( new Label(col++, row, info.getRemark()));
			sheet.addCell( new Label(col++, row, info.getMerchantAddress()));
			sheet.addCell( new Label(col++, row, info.getSwift()));
			sheet.addCell( new Label(col++, row, info.getBankAddress()));
		}
		book.write();
		book.close();
	}
	
	
	/**
	 * ??????????????????
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/checkTerInfo")
	public ModelAndView checkTerInfo(int id){
		MerchantTerInfo terInfo = merchantMgrService.queryTerInfoById(id);
		if(terInfo.getEnabled()==4){
			int i=merchantMgrService.updateChecnkTerNo(terInfo);
			if(i>0){
				return ajaxDoneSuccess("????????????");
			}else{
				return ajaxDoneError("????????????");
			}
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/**
	 * ????????????????????????????????????
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/terSnRelAgentInfoList")
	public String addSnNoToAgent(String id){
		PageInfo<TerSnRelAgentInfo> page = merchantMgrService.queryTerSNRelAgentInfoList(getCriteria());
		getRequest().setAttribute("page",page);
		return "merchantmgr/terSnRelAgentInfoList";
	}
	
	
	/**
	 * ??????????????????????????????
	 * @return
	 */
	@RequestMapping(value="/addTerSnRelAgentgo")
	public String addTerSnRelAgentgo(){
		return "merchantmgr/addSnNoToAgent";
	}
	
	
	/**
	 * ????????????????????????????????? ????????????
	 * @return
	 */
	@RequestMapping("/parentRelAgentBack")
	public String parentRelAgent(){
		criteria=getCriteria();
		criteria.getCondition().put("parentAgentNo", "0");
		PageInfo<AgentInfo> info=merchantMgrService.getAgentList(criteria);
		getRequest().setAttribute("info", info);
		return "merchantmgr/parentRelagentListBack";
	}
	
	/**
	 * ???????????????
	 * @return
	 */
	@RequestMapping(value="/parentRelMasteKeyBack")
	public String queryMasteKey(){
		criteria=getCriteria();
		criteria.getCondition().put("status", "0");
		PageInfo<MasteKey> page=bankMgrService.queryMasteKeyList(criteria);
		getRequest().setAttribute("page", page);
		return "merchantmgr/parentRelMasteKeyListBack";
	}
	
	
	/**
	 * ????????????
	 * @return
	 */
	@RequestMapping(value="/addTerSnRelAgent")
	public ModelAndView addTerSnRelAgent(TerSnRelAgentInfo agentInfo){
		agentInfo.setCreatBy(getLogAccount().getRealName());
		int i=merchantMgrService.addTerSnRelAgent(agentInfo);
		if(i > 0){
			return ajaxDoneSuccess("???????????????");
		}else{
			return ajaxDoneSuccess("???????????????");
		}
	}
	
	/**
	 * ??????????????????
	 * @param resp
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @throws IOException 
	 */
	@RequestMapping(value="/exportMerchant")
	public void exportMerchant(HttpServletResponse resp) throws RowsExceededException, WriteException, IOException{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "merchantTerList.xls" ); 
		List<MerchantTerInfo> list = merchantMgrService.exportMerchantTerList(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("????????????", 0);
		String[] headerName = { "?????????","?????????","????????????","????????????","????????????","???????????????","????????????","????????????"
				,"??????????????????","???????????????","???????????????","???????????????","????????????","??????","????????????","SWIFT","????????????","????????????"};
		// ????????????
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			MerchantTerInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getMerNo()));
			sheet.addCell( new Label(col++, row, info.getTerNo()));
			sheet.addCell( new Label(col++, row, info.getTerName()));
			sheet.addCell( new Label(col++, row, info.getTransCurrency()));
			sheet.addCell( new Label(col++, row, info.getSettleCurrency()));
			sheet.addCell( new Label(col++, row, info.getAccountName()));
			sheet.addCell( new Label(col++, row, info.getAccountAddress()));
			sheet.addCell( new Label(col++, row, info.getAccountNo()));
			sheet.addCell( new Label(col++, row, info.getAccountContryCode()));
			sheet.addCell( new Label(col++, row, info.getAccountState()));
			sheet.addCell( new Label(col++, row, info.getAccountCity()));
			sheet.addCell( new Label(col++, row, info.getShaKey()));
			sheet.addCell( new Label(col++, row, info.getEnabled()==1?"??????":"?????????"));
			sheet.addCell( new Label(col++, row, info.getRemark()));
			sheet.addCell( new Label(col++, row, info.getMerchantAddress()));
			sheet.addCell( new Label(col++, row, info.getSwift()));
			sheet.addCell( new Label(col++, row, info.getBankAddress()));
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("TER_PRODUCT_TYPE", info.getProductType()+"", info.getProductType()+"")));
		}
		book.write();
		book.close();
	}
	
	
	
	/**
	 * ??????????????????????????????
	 * @param resp
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @throws IOException 
	 */
	@RequestMapping(value="/exportTerSNRel")
	public void exportTerSNRel(HttpServletResponse resp) throws RowsExceededException, WriteException, IOException{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "TerSNRelList.xls" ); 
		List<TerSnRelAgentInfo> list = merchantMgrService.exportTerSNRelAgentInfoList(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("??????????????????", 0);
		String[] headerName = { "???????????????", "??????????????????","????????????","????????????","?????????","????????????","?????????"};
		// ????????????
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			TerSnRelAgentInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getSNNo()));
			sheet.addCell( new Label(col++, row, info.getParentAgentNo()));
			sheet.addCell( new Label(col++, row, info.getAgentNo()));
			sheet.addCell( new Label(col++, row, info.getCreateDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getCreateDate()):""));
			sheet.addCell( new Label(col++, row, info.getCreatBy()));
			sheet.addCell( new Label(col++, row, info.getUpdateDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getUpdateDate()):""));
			sheet.addCell( new Label(col++, row, info.getUpdateBy()));
		}
		book.write();
		book.close();
	}
	
	
	/**
	 * ??????????????????
	 * @return
	 */
	@RequestMapping(value="/terLimitInfoList")
	public String terLimitInfoList(){
		if(!"get".equalsIgnoreCase(getRequest().getMethod())){
			Criteria criteria=getCriteria();
			criteria.getCondition().put("amountOrCount", "0");
			NumberFormat nf=NumberFormat.getInstance();
			nf.setMaximumFractionDigits(3);
			nf.setMinimumFractionDigits(3);
			PageInfo<GwTernoLmitInfo> page = merchantMgrService.terLimitInfoList(criteria);
			for(GwTernoLmitInfo info:page.getData()){
				GwTernoLmitInfo info1=merchantMgrService.queryTerLimitRateInfo(info);
				info.setMonthAmount(info1.getMonthAmount());
				info.setDayAmount(info1.getDayAmount());
				info.setMonthRate(nf.format(info.getMonthAmount().doubleValue()/info.getMonthTransAmountLimit().doubleValue()*100)+"%");
				info.setDayRate(nf.format(info.getDayAmount().doubleValue()/info.getDayTransAmountLimit().doubleValue()*100)+"%");
			}
			getRequest().setAttribute("page",page);
		}
		return "merchantmgr/terLimitList";
	}
	
	/**
	 *???????????? 
	 * @throws Exception 
	 */
	@RequestMapping(value="/exportTerLimitInfo")
	public void exportTerLimitInfo(HttpServletResponse resp) throws Exception{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "TerLimitInfoList.xls" ); 
		Criteria criteria=getCriteria();
		criteria.getCondition().put("amountOrCount", "0");
		List<GwTernoLmitInfo> list = merchantMgrService.terLimitInfoListInfo(criteria);
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("????????????", 0);
		  String[] headerName={"?????????","?????????","????????????","????????????","????????????","?????????","???????????????","?????????????????????",
				  "?????????","???????????????","?????????????????????","?????????","????????????"};
		// ????????????
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		NumberFormat nf=NumberFormat.getInstance();
		nf.setMaximumFractionDigits(3);
		nf.setMinimumFractionDigits(3);
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			GwTernoLmitInfo info = list.get(row-1);
			GwTernoLmitInfo info1=merchantMgrService.queryTerLimitRateInfo(info);
			info.setMonthAmount(info1.getMonthAmount());
			info.setDayAmount(info1.getDayAmount());
			info.setMonthRate(nf.format(info.getMonthAmount().doubleValue()/info.getMonthTransAmountLimit().doubleValue()*100)+"%");
			info.setDayRate(nf.format(info.getDayAmount().doubleValue()/info.getDayTransAmountLimit().doubleValue()*100)+"%");
			sheet.addCell( new Label(col++, row, info.getMerNo()));
			sheet.addCell( new Label(col++, row, info.getTerNo()));
			sheet.addCell( new Label(col++, row, info.getMerchantName()));
			sheet.addCell( new Label(col++, row, info.getSettleCurrency()));
			sheet.addCell( new Label(col++, row, info.getSingleTransAmountLimit().toString()));
			sheet.addCell( new Label(col++, row, info.getDayTransAmountLimit().toString()));
			sheet.addCell( new Label(col++, row, info.getDayAmount().toString()));
			sheet.addCell( new Label(col++, row, info.getDayRate()));
			sheet.addCell( new Label(col++, row, info.getMonthTransAmountLimit().toString()));
			sheet.addCell( new Label(col++, row, info.getMonthAmount().toString()));
			sheet.addCell( new Label(col++, row, info.getMonthRate()));
			sheet.addCell( new Label(col++, row, info.getUpby()));
			sheet.addCell( new Label(col++, row, info.getUpdateDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getUpdateDate()):""));
		}
		book.write();
		book.close();
	}
	
	/**
	 * ????????????????????????
	 * @return
	 */
	@RequestMapping(value="/addTerLimitGo")
	public String addTerLimitGo(){
		return "merchantmgr/addTerLimitInfo";
	}
	
	/**
	 * ??????????????????
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/addTerLimit")
	public ModelAndView addTerLimit(GwTernoLmitInfo lmitInfo) throws ServiceException{
		lmitInfo.setUpby(getLogAccount().getRealName());
		if(!"".equals(lmitInfo.getMerNo())){
			MerchantInfo info = merchantMgrService.queryMerchantInfoByMerNo(lmitInfo.getMerNo());
			if(null == info){
				throw new ServiceException("?????????????????????");
			}
		}
		MerchantTerInfo temp = merchantMgrService.queryTerInfoByMerNoAndTerNo(lmitInfo.getMerNo(), lmitInfo.getTerNo());
		if(null == temp){
			throw new ServiceException("???????????????????????????");
		}
		int i = merchantMgrService.queryTerLimit(lmitInfo);
		if(0 != i){
			return ajaxDoneError("??????????????????????????????????????????");
		}
		int count = merchantMgrService.addTerLimit(lmitInfo);
		lmitInfo.setType("add");
		merchantMgrService.addTerLimitLog(lmitInfo);
		if(count > 0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/**
	 * ??????????????????????????????
	 * @return
	 */
	@RequestMapping(value="/updateTerNoGo")
	public String updateTerNoGo(int id){
		GwTernoLmitInfo lmitInfo=merchantMgrService.queryTerNoLimitById(id);
		getRequest().setAttribute("lmitInfo", lmitInfo);
		return "merchantmgr/updateTerNoLimit";
	}
			
			
	/**
	 * ??????????????????
	 * @param lmitInfo
	 * @return
	 */
	@RequestMapping(value="/updateTerNoLimit")
	public ModelAndView updateTerNoLimit(GwTernoLmitInfo lmitInfo){
		lmitInfo.setUpby(getLogAccount().getRealName());
		lmitInfo.setType("update");
		merchantMgrService.addTerLimitLog(lmitInfo);
		int count = merchantMgrService.updateTerNoLimit(lmitInfo);
		if(count > 0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/**
	 * ??????????????????
	 * @return
	 */
	@RequestMapping(value="/terLimitInfoCountList")
	public String terLimitInfoCountList(){
		Criteria criteria=getCriteria();
		if(!"get".equalsIgnoreCase(getRequest().getMethod())){
			NumberFormat nf=NumberFormat.getInstance();
			nf.setMaximumFractionDigits(3);
			nf.setMinimumFractionDigits(3);
			criteria.getCondition().put("amountOrCount", "1");
			PageInfo<GwTernoLmitInfo> page = merchantMgrService.terLimitInfoList(criteria);
			for(GwTernoLmitInfo info:page.getData()){
				GwTernoLmitInfo info1=merchantMgrService.queryTerLimitRateInfo(info);
				info.setMonthAmount(info1.getMonthAmount());
				info.setDayAmount(info1.getDayAmount());
				info.setMonthCount(info1.getMonthCount());
				info.setMonthRate(nf.format(info.getMonthAmount().doubleValue()/info.getMonthTransAmountLimit().doubleValue()*100)+"%");
				info.setDayRate(nf.format(info.getDayAmount().doubleValue()/info.getDayTransAmountLimit().doubleValue()*100)+"%");
				if(info.getMonthCountLimit()>0){
					info.setMonthCountRate(nf.format(Double.parseDouble(info1.getMonthCount())/info.getMonthCountLimit()*100)+"%");
				}
			}
			getRequest().setAttribute("page",page);
		}
		return "merchantmgr/terLimitCountList";
	}
	
	/**
	 *???????????? 
	 * @throws Exception 
	 */
	@RequestMapping(value="/exportTerLimitCountInfo")
	public void exportTerLimitCountInfo(HttpServletResponse resp) throws Exception{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "TerLimitInfoList.xls" ); 
		Criteria criteria=getCriteria();
		criteria.getCondition().put("amountOrCount", "1");
		List<GwTernoLmitInfo> list = merchantMgrService.terLimitInfoListInfo(criteria);
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("????????????", 0);
		  String[] headerName={"?????????","?????????","????????????","????????????","????????????","?????????","???????????????","?????????????????????",
				  "?????????","???????????????","?????????????????????","??????????????????","????????????","????????????????????????","?????????","????????????"};
		// ????????????
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		NumberFormat nf=NumberFormat.getInstance();
		nf.setMaximumFractionDigits(3);
		nf.setMinimumFractionDigits(3);
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			GwTernoLmitInfo info = list.get(row-1);
			GwTernoLmitInfo info1=merchantMgrService.queryTerLimitRateInfo(info);
			info.setMonthAmount(info1.getMonthAmount());
			info.setDayAmount(info1.getDayAmount());
			info.setMonthCount(info1.getMonthCount());
			info.setMonthRate(nf.format(info.getMonthAmount().doubleValue()/info.getMonthTransAmountLimit().doubleValue()*100)+"%");
			if(info.getMonthCountLimit()>0){
				info.setMonthCountRate(nf.format(Double.parseDouble(info1.getMonthCount())/info.getMonthCountLimit()*100)+"%");
			}
			info.setDayRate(nf.format(info.getDayAmount().doubleValue()/info.getDayTransAmountLimit().doubleValue()*100)+"%");
			sheet.addCell( new Label(col++, row, info.getMerNo()));
			sheet.addCell( new Label(col++, row, info.getTerNo()));
			sheet.addCell( new Label(col++, row, info.getMerchantName()));
			sheet.addCell( new Label(col++, row, info.getSettleCurrency()));
			sheet.addCell( new Label(col++, row, info.getSingleTransAmountLimit()+""));
			sheet.addCell( new Label(col++, row, info.getDayTransAmountLimit().toString()));
			sheet.addCell( new Label(col++, row, info.getDayAmount().toString()));
			sheet.addCell( new Label(col++, row, info.getDayRate()));
			sheet.addCell( new Label(col++, row, info.getMonthTransAmountLimit().toString()));
			sheet.addCell( new Label(col++, row, info.getMonthAmount().toString()));
			sheet.addCell( new Label(col++, row, info.getMonthRate()));
			sheet.addCell( new Label(col++, row, info.getMonthCountLimit()+""));
			sheet.addCell( new Label(col++, row, info.getMonthCount()));
			sheet.addCell( new Label(col++, row, info.getMonthCountRate()));
			sheet.addCell( new Label(col++, row, info.getUpby()));
			sheet.addCell( new Label(col++, row, info.getUpdateDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getUpdateDate()):""));
		}
		book.write();
		book.close();
	}
	
	/**
	 * ??????????????????
	 */
	@RequestMapping(value="/deleteTerLimitCountInfo")
	public ModelAndView deleteTerLimitCountInfo(String id){
		int count=merchantMgrService.deleteTerLimitCountInfoById(id);
		if(count>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
		
	}
	
	/**
	 * ????????????????????????
	 * @return
	 */
	@RequestMapping(value="/addTerLimitCountGo")
	public String addTerLimitCountGo(){
		return "merchantmgr/addTerLimitCountInfo";
	}
	
	/**
	 * ??????????????????
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/addTerLimitCount")
	public ModelAndView addTerLimitCount(GwTernoLmitInfo lmitInfo) throws ServiceException{
		lmitInfo.setUpby(getLogAccount().getRealName());
		if(!"".equals(lmitInfo.getMerNo())){
			MerchantInfo info = merchantMgrService.queryMerchantInfoByMerNo(lmitInfo.getMerNo());
			if(null == info){
				throw new ServiceException("?????????????????????");
			}
		}
		MerchantTerInfo temp = merchantMgrService.queryTerInfoByMerNoAndTerNo(lmitInfo.getMerNo(), lmitInfo.getTerNo());
		if(null == temp){
			throw new ServiceException("???????????????????????????");
		}
		int i = merchantMgrService.queryTerLimit(lmitInfo);
		if(0 != i){
			return ajaxDoneError("??????????????????????????????????????????");
		}
		int count = merchantMgrService.addTerLimit(lmitInfo);
		lmitInfo.setType("add");
		merchantMgrService.addTerLimitLog(lmitInfo);
		if(count > 0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/**
	 * ??????????????????????????????
	 * @return
	 */
	@RequestMapping(value="/updateTerNoCountGo")
	public String updateTerNoCountGo(int id){
		GwTernoLmitInfo lmitInfo=merchantMgrService.queryTerNoLimitById(id);
		getRequest().setAttribute("lmitInfo", lmitInfo);
		return "merchantmgr/updateTerNoLimitCount";
	}
			
			
	/**
	 * ??????????????????
	 * @param lmitInfo
	 * @return
	 */
	@RequestMapping(value="/updateTerNoLimitCount")
	public ModelAndView updateTerNoLimitCount(GwTernoLmitInfo lmitInfo){
		lmitInfo.setUpby(getLogAccount().getRealName());
		lmitInfo.setType("update");
		merchantMgrService.addTerLimitLog(lmitInfo);
		int count = merchantMgrService.updateTerNoLimit(lmitInfo);
		if(count > 0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	@RequestMapping(value="/goLmitInfoLog")
	public String goLmitInfoLog(String id){
		List<GwTernoLmitInfo> list = merchantMgrService.queryTerLimitLog(id);
		getRequest().setAttribute("list", list);
		return "merchantmgr/terLimitListLog";
	}
	
	/**
	 * ????????????
	 * @return
	 */
	@RequestMapping(value="/TerSnRelRecycle")
	public ModelAndView	TerSnRelRecycle(TerSnRelAgentInfo agentInfo){
		int i=merchantMgrService.updateTerSnRelRecycle(agentInfo);
		if(i > 0){
			return ajaxDoneSuccess("???????????????");
		}else{
			return ajaxDoneError("???????????????");
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/queryPicImg")
	public MerchantInfo queryPicImg(String phoneNo,String picType){
	     MerchantInfo merchantInfo=merchantMgrService.queryMerchantInfo(phoneNo,picType);
	     if(merchantInfo!=null){
	    	 return merchantInfo;
	     }else{
	    	 return null;
	     }
	}
	
	/**
	 * ?????????????????????????????????
	 * @return
	 */
	@RequestMapping(value="/getTerListBack")
	public String getMerchantByTerList(){
		Criteria criteria=getCriteria();
		criteria.getCondition().put("enabled", "1");
		PageInfo<MerchantTerInfo> page = merchantMgrService.getMerchantTerList(criteria);
		getRequest().setAttribute("page",page);
		return "merchantmgr/terListBack";
	}
	
	/**
	 * ????????????
	 * @param info
	 * @return
	 */
	@RequestMapping(value="/updateMerchantInfoReState")
	public ModelAndView updateMerchantInfoReState(MerchantInfo info){
		MerchantInfo merchantInfo=merchantMgrService.queryMerchantInfoById(info.getId());
		if(!StringUtils.isEmpty(merchantInfo) && "1".equals(merchantInfo.getReState())){
			return ajaxDoneError("???????????????????????????????????????");
		}
		List<MerchantInfo> list=merchantMgrService.queryMerchantLogList(merchantInfo.getMerNo());
		if(list.size()>0){
			MerchantInfo merchantInfo2=merchantMgrService.queryMerchantLogList(merchantInfo.getMerNo()).get(0);
			if(null!=merchantInfo2 && merchantInfo2.getUpdatePerson().equals(getLogAccount().getRealName())){
				return ajaxDoneError("?????????????????????");
			}
		}
		
		info.setReState("1");
		info.setEnabled(1);
		info.setUpdatePerson(getLogAccount().getRealName());
		int i=merchantMgrService.updateMerchantInfoReState(info);
		if(i > 0){
			return ajaxDoneSuccess("???????????????");
		}else{
			return ajaxDoneError("???????????????");
		}
	}
	/**
	 * 
	 * ????????????????????????
	 * **/
	@RequestMapping(value="/getMerchantSettleCycleList")
	public String getMerchantSettleCycleList(){
		if(!"get".equalsIgnoreCase(getRequest().getMethod())){
			PageInfo<MerchantSettleCycle> page = merchantMgrService.getMerchantSettleCycleList(getCriteria());
			getRequest().setAttribute("page",page);
		}
		return "merchantmgr/merchantSettleCycleList";
	}
	/**
	 * ?????????????????????????????????
	 * */
	@RequestMapping(value="/goAddMerchantSettleCycle")
	public String goAddMerchantSettleCycle(){
		return "merchantmgr/addMerchantSettleCycle";
	}
	
	@RequestMapping(value="/addMerchantSettleCycle")
	public ModelAndView addMerchantSettleCycle(MerchantSettleCycle msc) throws ServiceException{
		msc.setCreateBy(getLogAccount().getRealName());
		if(!"".equals(msc.getMerNo())){
			MerchantInfo info = merchantMgrService.queryMerchantInfoByMerNo(msc.getMerNo());
			if(null == info){
				throw new ServiceException("?????????????????????");
			}
		}
		int count = merchantMgrService.addMerchantSettleCycle(msc);
		if(count > 0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/**
	 * ??????ID????????????????????????
	 * */
	@RequestMapping(value="/goUpdateMerchantSettleCycle")
	public String goUpdateMerchantSettleCycle(String id){
		MerchantSettleCycle msc=merchantMgrService.queryMerchantSettleCycleByID(id);
		
		getRequest().setAttribute("msc", msc);
		return "merchantmgr/updateMerchantSettleCycle";
	}
	
	@RequestMapping(value="/updateMerchantSettleCycle")
	public ModelAndView updateMerchantSettleCycle(MerchantSettleCycle msc) throws ServiceException{
		msc.setModifyBy(getLogAccount().getRealName());
		if(!"".equals(msc.getMerNo())){
			MerchantInfo info = merchantMgrService.queryMerchantInfoByMerNo(msc.getMerNo());
			if(null == info){
				throw new ServiceException("?????????????????????");
			}
		}
		int count = merchantMgrService.updateMerchantSettleCycle(msc);
		if(count > 0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	/**
	 * ????????????????????????
	 * */
	@RequestMapping(value="/getListMerchantWebsite")
	public String getListMerchantWebsite(@RequestParam(value="websites",required=false)String[] websites){
		List<String> list=new ArrayList<String>();
		if(websites!=null&&websites.length>0){
			for(String str:websites){
				if(str.trim()!=""&&!"".equals(str)){
					list.add(str);
					if(str.indexOf("www.")==0){
						list.add(str.replaceFirst("www.", ""));
					}else{
						list.add("www."+str);
					}
				}
			}
		}
		Criteria cr=getCriteria();
		if(list.size()>0){
			cr.put("websiteList", list);
		}
		PageInfo<MerchantWebsite> page = merchantMgrService.getListMerchantWebsite(cr);
		getRequest().setAttribute("page",page);
		return "merchantmgr/merchantWebsiteList";
	}
	
	/**
	 * ????????????????????????
	 * */
	@RequestMapping(value="/exportWebsiteInfo")
	public void exportWebsiteInfo(HttpServletResponse resp) throws Exception{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "merchantWebsiteInfo.xls" ); 
		List<MerchantWebsite> list = merchantMgrDao.getListMerchantWebsite(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("????????????", 0);
		//String[] headerName = { "???????????????", "?????????","?????????","?????????","????????????","????????????","???????????????","?????????","????????????","????????????","????????????","????????????"};
		
		  String[] headerName={"?????????","?????????","??????","MCC","??????","??????","??????????????????","????????????","????????????","????????????","?????????","????????????","??????"};
		// ????????????
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			MerchantWebsite info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getMerNo()));
			sheet.addCell( new Label(col++, row, info.getTerNo()));
			sheet.addCell( new Label(col++, row, info.getMerWebSite()));
			sheet.addCell( new Label(col++, row, info.getMCC()));
			sheet.addCell( new Label(col++, row, info.getBrand()));
			sheet.addCell( new Label(col++, row, info.getProduct()));
			sheet.addCell( new Label(col++, row, info.getMerWebLanguage()));
			sheet.addCell( new Label(col++, row, info.getMerWebProgram()));
			sheet.addCell( new Label(col++, row, info.getMessage()));
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("MERCHANTWEBSITESTATUS",info.getStatus(),"????????????")));
			sheet.addCell( new Label(col++, row ,info.getAppBy()));
			sheet.addCell( new Label(col++, row, info.getAppDate()));
			sheet.addCell( new Label(col++, row, info.getRemark()));
		}
			
			
		book.write();
		book.close();
	}
	/**
	 * ?????????????????????????????????
	 * */
	@RequestMapping("/goAddMerchantWebsite")
	public String goAddMerchantWebSite(){
		return "merchantmgr/addMerchantWebsite";
	}
	/**
	 * ??????????????????
	 * @throws ServiceException 
	 * */
	@RequestMapping(value="/addMerchantWebsite")
	public ModelAndView addMerchantWebsite(MerchantWebsite merchantWebsite) throws ServiceException{
		if(!"".equals(merchantWebsite.getMerNo())){
			int i = merchantMgrService.queryMerchantInfoByMerNo(merchantWebsite.getMerNo(),merchantWebsite.getTerNo());
			if(i == 0){
				throw new ServiceException("????????????????????????????????????");
			}
		}
		int y = merchantMgrService.queryMerchantWebsite(merchantWebsite);
		if(0 != y ){
			return ajaxDoneError("???????????????????????????????????????");
		}
		merchantWebsite.setCreateBy(getLogAccount().getRealName());
		int count = merchantMgrService.addMerchantWebsite(merchantWebsite);
		if(count > 0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/** ??????????????????????????? */
	@RequestMapping(value="/goUploadMerchantWebsite")
	public String goUploadMerchantWebsite(){
		return "merchantmgr/uploadMerchantWebsite";
	}
	
	/** ?????????????????? */
	@RequestMapping(value="/uploadMerchantWebsite")
	public ModelAndView uploadMerchantWebsite(DefaultMultipartHttpServletRequest request,HttpServletResponse res)throws IOException, ParseException, ISOException{
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		res.setContentType("text/html");
		int count=0;
		int total=0;
		StringBuffer errorWebsitStr=new StringBuffer("");
		List<MerchantWebsite> infos=new ArrayList<MerchantWebsite>();
		if(null != files){
			log.info("?????????"+files.size()+"????????????");
			for(MultipartFile file :files){
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8"));//????????????BufferedReader??????????????????
				String line = br.readLine();
				while(null != line){
					log.info("line:"+line);
					if(!line.contains("|")){
						String [] fields = line.split(",");
						if(fields.length == 3){
							total++;
							int index = 0;
							MerchantWebsite info=new MerchantWebsite();
							info.setMerNo(fields[index++]);
							info.setTerNo(fields[index++]);
							info.setMerWebSite(fields[index++]);
							int i = merchantMgrService.queryMerchantInfoByMerNo(info.getMerNo(),info.getTerNo());
							if(i == 0){
								errorWebsitStr.append("??????????????????????????????"+line+"\r\n");
								line = br.readLine();
								continue;
							}
							int y = merchantMgrService.queryMerchantWebsite(info);
							if(0 != y ){
								errorWebsitStr.append("???????????????"+line+"\r\n");
								line = br.readLine();
								continue;
							}
							try {
								info.setCreateBy(getLogAccount().getRealName());
								int s =  merchantMgrService.addMerchantWebsite(info);
								if(0 < s){
									count++;
								}
							} catch (Exception e) {
							}
						}else if(fields.length == 4){
							total++;
							int index = 0;
							MerchantWebsite info=new MerchantWebsite();
							info.setMerNo(fields[index++]);
							info.setTerNo(fields[index++]);
							info.setMerWebSite(fields[index++]);
							info.setBrand(fields[index++]);
							int i = merchantMgrService.queryMerchantInfoByMerNo(info.getMerNo(),info.getTerNo());
							if(i == 0){
								errorWebsitStr.append("??????????????????????????????"+line+"\r\n");
								line = br.readLine();
								continue;
							}
							int y = merchantMgrService.queryMerchantWebsite(info);
							if(0 != y ){
								errorWebsitStr.append("???????????????"+line+"\r\n");
								line = br.readLine();
								continue;
							}
							com.gateway.brandProduct.model.BrandProductInfo binfo=new com.gateway.brandProduct.model.BrandProductInfo();
							binfo.setBpname(info.getBrand());
							int z=brandProductService.queryBrandProductDup(binfo);
							if(z<1){
								errorWebsitStr.append("??????????????????"+line+"\r\n");
								line = br.readLine();
								continue;
							}
							try {
								info.setCreateBy(getLogAccount().getRealName());
								int s =  merchantMgrService.addMerchantWebsite(info);
								if(0 < s){
									count++;
								}
							} catch (Exception e) {
							}
						}else{
							errorWebsitStr.append("???????????????"+line+"\r\n");
							log.info("??????line:"+line);
						}
					}else{
						String [] fields = line.split("\\|");
						if(fields.length == 4){
							total++;
							int index = 0;
							MerchantWebsite info=new MerchantWebsite();
							info.setMerNo(fields[index++]);
							info.setTerNo(fields[index++]);
							info.setMerWebSite(fields[index++]);
							info.setBrand(fields[index++]);
							int i = merchantMgrService.queryMerchantInfoByMerNo(info.getMerNo(),info.getTerNo());
							if(i == 0){
								errorWebsitStr.append("??????????????????????????????"+line+"\r\n");
								line = br.readLine();
								continue;
							}
							int y = merchantMgrService.queryMerchantWebsite(info);
							if(0 == y ){
								errorWebsitStr.append("??????????????????"+line+"\r\n");
								line = br.readLine();
								continue;
							}
							if(info.getBrand()!=null){
								String[] brands=info.getBrand().split(",");
								int flag=0;
								for(String brand:brands){
									com.gateway.brandProduct.model.BrandProductInfo binfo=new com.gateway.brandProduct.model.BrandProductInfo();
									binfo.setBpname(brand);
									int z=brandProductService.queryBrandProductDup(binfo);
									if(z<1){
										flag=1;
										break;
									}
								}
								if(flag==1){
									errorWebsitStr.append("??????????????????"+line+"\r\n");
									line = br.readLine();
									continue;	
								}
							}
							try {
								info.setCreateBy(getLogAccount().getRealName());
								int s =  merchantMgrService.updateMerchantWebsiteByMerNoAndTerNoAndWebSite(info);
								if(0 < s){
									count++;
								}
							} catch (Exception e) {
							}
						}else{
							errorWebsitStr.append("???????????????"+line+"\r\n");
							log.info("??????line:"+line);
						}
					}
					line = br.readLine();
				}
			}
			
		}
		getRequest().getSession().setAttribute("errorWebsitStr", errorWebsitStr.toString());
		log.info(errorWebsitStr.toString());
		return ajaxDoneSuccess("????????????????????????"+total+"?????????,???????????????"+count+"?????????????????????"+(total-count)+"?????????");
	}
	
	// ??????????????????????????????
	@RequestMapping("/downloadbatchWebsiteErrorInfo")
	public void downloadbatchTransChangeErrorInfo(HttpServletResponse resp) {
		resp.setContentType("application/zip");
		resp.addHeader("Content-Disposition", "attachment;filename=errorWebsite.txt");
		OutputStream outp = null;
		try {
			String str= (String) getRequest().getSession().getAttribute("errorWebsitStr");
			outp = resp.getOutputStream();
			PrintWriter pw = new PrintWriter(outp);
			pw.print(str+"\r\n");
			pw.flush();
			outp.flush();
		} catch (Exception e) {
			// log.error("", e);
		} finally {
			if (outp != null) {
				try {
					outp.close();
					outp = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * ?????????????????????????????????
	 * @throws ServiceException 
	 * */
	@RequestMapping(value="/goUpdateMerchantWebsite")
	public String goUpdateMerchantWebsite(String id) throws ServiceException{
		MerchantWebsite merchantWebsite=merchantMgrService.queryMerchantWebsiteById(id);
//		if(!"0".equals(merchantWebsite.getStatus())){
//			throw new ServiceException("??????????????????????????????");
//		}
		getRequest().setAttribute("info", merchantWebsite);
		return "merchantmgr/updateMerchantWebsite";
	}
	/**
	 * ??????????????????
	 * */
	@RequestMapping(value="/updateMerchantWebsite")
	public ModelAndView updateMerchantWebsite(MerchantWebsite merchantWebsite){
		merchantWebsite.setAppBy(getLogAccount().getRealName());
		int count = merchantMgrService.updateMerchantWebsite(merchantWebsite);
		if(count > 0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/** ?????????????????? */
	@RequestMapping(value="/goBatchCheckMerchantWebsite")
	public String goBatchCheckMerchantWebsite(String[] ids,String type){
		List<MerchantWebsite> list = new ArrayList<MerchantWebsite>();
		for(String id :ids){
			MerchantWebsite merchantWebsite=merchantMgrService.queryMerchantWebsiteById(id);
				list.add(merchantWebsite);
		}
		String newIds = "";
		for(int i =0;i<list.size();i++){
			newIds = newIds + list.get(i).getId()+",";
		}
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("ids", newIds);
		if("0".equals(type)){
			return "merchantmgr/batchCheckMerchantWebsite";
		}
		return "merchantmgr/setMerchantWebsite";
	}
	
	/** ???????????? */
	@RequestMapping(value="/batchCheckMerchantWebsite")
	public ModelAndView batchCheckMerchantWebsite(MerchantWebsite web){
		if(StringUtils.isEmpty(web) || StringUtils.isEmpty(web.getIds())){
			return ajaxDoneError("????????????");
		}
		String [] str = web.getIds().split(",");
		int countTotal = str.length;
		int countInt = 0;
		for(String id:str){
			MerchantWebsite merchantWebsite=merchantMgrService.queryMerchantWebsiteById(id);
			merchantWebsite.setStatus(web.getStatus());
			merchantWebsite.setMessage(web.getMessage());
			merchantWebsite.setRemark(web.getRemark());
			merchantWebsite.setMCC(web.getMCC());
			merchantWebsite.setBrand(web.getBrand());
			merchantWebsite.setProduct(web.getProduct());
			merchantWebsite.setAppBy(getLogAccount().getRealName());
			int count = merchantMgrService.updateMerchantWebsite(merchantWebsite);
			if(count > 0){
				countInt++;
			}
		}
		return ajaxDoneSuccess("????????????????????????" + countTotal + "????????????????????????????????????" + countInt + "???");
	}
	
	/** ?????????????????? */
	@RequestMapping(value="/setMerchantWebsite")
	public ModelAndView setMerchantWebsite(MerchantWebsite web){
		if(StringUtils.isEmpty(web) || StringUtils.isEmpty(web.getIds())){
			return ajaxDoneError("??????????????????");
		}
		String [] str = web.getIds().split(",");
		for(String id:str){
			MerchantWebsite merchantWebsite=merchantMgrService.queryMerchantWebsiteById(id);
			merchantWebsite.setProduct(web.getProduct());
			merchantWebsite.setBrand(web.getBrand());
			merchantWebsite.setMCC(web.getMCC());
			merchantWebsite.setOperationBy(getLogAccount().getRealName());//?????????
			merchantMgrService.updateMerchantWebsiteInfo(merchantWebsite);
		}
		return ajaxDoneSuccess("??????????????????");
	}
	
	/** ??????????????????????????? */
	@RequestMapping(value="/goUpdateMerchantWebsiteInfo")
	public String goUpdateMerchantWebsiteInfo(String id) throws ServiceException{
		MerchantWebsite merchantWebsite=merchantMgrService.queryMerchantWebsiteById(id);
//		if(!"0".equals(merchantWebsite.getStatus())){
//			throw new ServiceException("??????????????????????????????");
//		}
		getRequest().setAttribute("info", merchantWebsite);
		return "merchantmgr/updateMerchantWebsiteInfo";
	}
	
	/** ???????????????????????? */
	@RequestMapping(value="/updateMerchantWebsiteInfo")
	public ModelAndView updateMerchantWebsiteInfo(MerchantWebsite merchantWebsite){
		merchantWebsite.setOperationBy(getLogAccount().getRealName());//?????????
		int count = merchantMgrService.updateMerchantWebsiteInfo(merchantWebsite);
		if(count > 0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/** ?????????????????? */
	@RequestMapping(value="/deleteWebsite")
	public ModelAndView deleteWebsite(String [] ids){
		for(String id:ids){
			MerchantWebsite merchantWebsite=merchantMgrService.queryMerchantWebsiteById(id);
			merchantMgrService.insertOperationLog(merchantWebsite, getLogAccount().getRealName(), "delete");
		}
		int i = merchantMgrService.deleteWebsite(ids);
		if(0 < i){
			return ajaxDoneSuccess("????????????");
		}
		return ajaxDoneError("????????????");
	}
	
	/** ?????????????????? */
	@RequestMapping(value="/queryWebsiteLogList")
	public String queryWebsiteLogList(){
		PageInfo<MerchantWebsite> page = merchantMgrService.queryWebsiteLogList(getCriteria());
		getRequest().setAttribute("page",page);
		return "merchantmgr/websiteLogList";
	}
	
	/** ?????????????????? */
	@ResponseBody
	@RequestMapping(value="/checkTerNoStatus")
	public ModelAndView checkTerNoStatus(int id)throws APIException{
		MerchantTerInfo info = merchantMgrService.queryMerchantTerNoById(id);
		if(StringUtils.isEmpty(info)){
			throw new APIException("???????????????????????????");
		}
		if(StringUtils.isEmpty(info.getSettleCurrency())){
			return ajaxDoneError("????????????????????????????????????????????????????????????????????????????????????");
		}
		if(1 == info.getReStatus()){
			return ajaxDoneError("???????????????????????????????????????");
		}
		info.setReStatus(1);
		info.setEnabled(1);
		int i = merchantMgrService.updateMerchantTerNoInfo(info);
		if(i > 0){
			return ajaxDoneSuccess("????????????");
		}
		return ajaxDoneError("????????????");
	}
	/**
	 * ????????????
	 * @param id
	 * @return
	 * @throws APIException 
	 */
	@RequestMapping(value="/goReSetPass")
	public String goReSetPass(Integer id) throws APIException{
		MerchantInfo merchantInfo= merchantMgrService.queryMerchantInfoById(id);
		if(null == merchantInfo){
			throw new APIException("????????????????????????");
		}
		//???????????????????????????
		List<UserInfo> list = sysMgrService.queryUserInfoByMerNo(merchantInfo.getMerNo());
		getRequest().setAttribute("list", list);
		return "merchantmgr/resetPass";
	}
	
	/**
	 * ????????????
	 * @param id
	 * @return
	 * @throws APIException 
	 */
	@RequestMapping(value="/reSetPass")
	public String reSetPass(Integer id) throws APIException{
		UserInfo userInfo = sysMgrService.queryUserInfoById(id);
		if(null == userInfo){
			throw new APIException("????????????????????????");
		}
		getRequest().setAttribute("user", userInfo);
		return "merchantmgr/inputPass";
	}
	
	/**
	 * ????????????
	 * @return
	 */
	@RequestMapping(value="/updatePass")
	public ModelAndView updatePass(Integer id,String passWord){
		UserInfo userInfo = sysMgrService.queryUserInfoById(id);
		String temppass =SHA256Utils.getSHA256Encryption(passWord+userInfo.getUserName());
		userInfo.setPassWord(temppass);
		int i = sysMgrService.updateUserInfo(userInfo);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping(value="/regCodeMgrList")
	public String regCodeMgrList(){
		PageInfo<RegCodeInfo> page =  merchantMgrService.queryRegCodeList(getCriteria());
		getRequest().setAttribute("page", page);
		return "merchantmgr/regCodeList";
	}
	
	/**
	 * ?????????????????????
	 * @return
	 */
	@RequestMapping(value="/goAddRegCode")
	public String goAddRegCode(){
		return "merchantmgr/addRegCode";
	}
	
	/**
	 * ???????????????
	 * @param info
	 * @return
	 */
	@RequestMapping(value="/excuAddRegCode")
	public ModelAndView excuAddRegCode(RegCodeInfo info){
		info.setCreateBy(getLogAccount().getUserName());
		int i = merchantMgrService.excuAddRegCode(info);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/**
	 * ???????????????
	 * @param String[] ids
	 * @return page
	 * */
	@RequestMapping(value="/deleteRegCodeInfo")
	public ModelAndView deleteRegCodeInfo(String [] ids){
		int count = merchantMgrService.deleteRegCodeInfo(ids);
		return ajaxDoneSuccess("??????????????????" + ids.length+" ,????????????????????????" + count);
	}
	
	/**
	 * ????????????????????????????????????
	 * @param vo ????????????
	 * @return ????????????????????????????????????
	 * @date 2015-09-12
	 * @author YWP
	 */
	@RequestMapping(value="/searchMerchantPaymentPage")
	public String searchMerchantPaymentPage() {
		PageInfo<GwMerchantPaymentPage> page = merchantMgrService.searchMerchantPaymentPage(getCriteria());
		getRequest().setAttribute("page", page);
		return "merchantmgr/SearchMerchantPaymentPage";
	}
	/**
	 * ????????????????????????????????????
	 * @param vo ????????????
	 * @return ????????????????????????????????????
	 * @date 2015-09-12
	 * @author YWP
	 */
	@RequestMapping(value="/toAddPage")
	public String toAddPage(GwMerchantPaymentPage vo) {
		GwMerchantPaymentPage info = new GwMerchantPaymentPage();
		if(vo.getId()>0) {
			info = merchantMgrService.searchMerchantPaymentPageById(vo);
		}
		getRequest().setAttribute("form", info);
		return "merchantmgr/AddMerchantPaymentPage";
	}
	/**
	 * ?????????????????????????????????????????????
	 * @param vo ????????????????????????
	 * @return ????????????
	 * @date 2015-09-12
	 * @author YWP
	 */
	@RequestMapping(value="/saveMerchantPage")
	public ModelAndView saveMerchantPaymentPage(GwMerchantPaymentPage vo) {
		int a = merchantMgrService.saveMerchantPaymentPage(vo, getLogAccount());
		if(a > 0){
			return ajaxDoneSuccess("???????????????");
		}else{
			return ajaxDoneError("???????????????");
		}
	}
	/**
	 * ?????????????????????????????????????????????
	 * @param vo ????????????????????????
	 * @return ????????????
	 * @date 2015-09-12
	 * @author YWP
	 */
	@RequestMapping(value="/deleteMerchantPage")
	public ModelAndView deleteMerchantPaymentPage(GwMerchantPaymentPage vo) {
		int a = merchantMgrService.deleteMerchantPaymentPage(vo);
		if(a > 0){
			return ajaxDoneSuccess("???????????????");
		}else{
			return ajaxDoneError("???????????????");
		}
	}
	
	/**
	 *?????????????????? 
	 * 
	 */
	@RequestMapping("/getBrandInfo")
	public String getBrandInfo(){
		Criteria criteria=getCriteria();
		String bpname=(String) criteria.getCondition().get("bpname");
		if(bpname!=null){
			String[] bpnames=bpname.trim().split("\\s+");
			criteria.getCondition().put("bpname", bpnames);
		}
		PageInfo<BrandProductInfo> page = merchantMgrService.getBrandInfo(criteria);
		getRequest().setAttribute("page",page);
		return "merchantmgr/BrandInfoList";
	}
	/**
	 * mcc????????????
	 *
	 */
	
	@RequestMapping("/getMccInfo")
	public String getMccInfo(){
		Criteria criteria=getCriteria();
		PageInfo<MccInfo> page = merchantMgrService.getMccInfo(criteria);
		getRequest().setAttribute("page",page);
		return "merchantmgr/mccInfoList";
	}
	/**
	 *??????????????????
	 * 
	 */
	@RequestMapping("/getProductInfo")
	public String getProductInfo(){
		Criteria criteria=getCriteria();
		String bpname=(String) criteria.getCondition().get("bpname");
		if(bpname!=null){
			String[] bpnames=bpname.trim().split("\\s+");
			criteria.getCondition().put("bpname", bpnames);
		}
		PageInfo<BrandProductInfo> page = merchantMgrService.getProductInfo(criteria);
		getRequest().setAttribute("page",page);
		return "merchantmgr/ProductInfoList";
	}
	
	/**
	 * ????????????????????????
	 * @throws IOException   
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	@RequestMapping(value="/exportMerchantRefCurrency")
	public void exportMerchantRefCurrency(HttpServletResponse resp) throws IOException, RowsExceededException, WriteException{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "merchantList.xls" ); 
		List<MerchantRelCurrencyInfo> list = merchantMgrService.getMerchnatRelCurrencyForExport(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("??????????????????", 0);
		String[] headerName = { "?????????", "?????????","????????????","????????????","????????????"
				,"????????????","???????????????","???????????????","??????????????????","????????????","????????????","????????????","?????????"};
		// ????????????
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			MerchantRelCurrencyInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getMerNo()));
			sheet.addCell( new Label(col++, row, info.getTerNo()));
			sheet.addCell( new Label(col++, row, info.getMerchantName()));
			sheet.addCell( new Label(col++, row, info.getBankName()));
			sheet.addCell( new Label(col++, row, info.getCurrencyName()));
			sheet.addCell( new Label(col++, row, info.getAcquirer()));
			sheet.addCell( new Label(col++, row, info.getCurrencyName2()));
			sheet.addCell( new Label(col++, row, info.getCurrencyName3()));
			sheet.addCell( new Label(col++, row, info.getAutoChange()==0?"?????????":"??????"));
			sheet.addCell( new Label(col++, row, info.getCardType()));
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("AGENT_STATUS",String.valueOf(info.getEnabled()),String.valueOf(info.getEnabled()))));
			sheet.addCell( new Label(col++, row, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(info.getCreateDate())));
			sheet.addCell( new Label(col++, row, info.getCreateBy()));
		}
		book.write();
		book.close();
	}
	
	/**
	 * ???????????????????????????????????????
	 */
	@RequestMapping(value="/goCopyMerchantTerInfo")
	public String goCopyMerchantTerInfo(int id){
		MerchantTerInfo newTerInfo=merchantMgrDao.queryMerchantTerNoById(id);
		getRequest().setAttribute("newTerInfo", newTerInfo);
		return "merchantmgr/onekeytoopen/copyedMerchantInfo";
	}
	
	/**
	 * ?????????????????????????????????
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/copyMerchantTerInfo")
	@Transactional(rollbackFor=Exception.class)
	public ModelAndView copyMerchantTerInfo(CopyMerchantTerInfo info) throws ServiceException{
		String msg="";
		//??????????????????
		MerchantTerInfo oldTerInfo=merchantMgrDao.queryTerInfoByMerNoAndTerNo(info.getMerNo(), info.getTerNo());
		oldTerInfo.setId(info.getId());
		oldTerInfo.setMerNo(info.getNewMerNo());
		oldTerInfo.setTerNo(info.getNewTerNo());
		oldTerInfo.setUpdateBy(getLogAccount().getRealName());
		int i=merchantMgrService.updateMerchantTerInfo(oldTerInfo);
		if(i>0){
			msg="????????????????????????<br>";
		}else{
			return ajaxDoneError("????????????????????????");
		}
		Criteria criteria=getCriteria();
		criteria.getCondition().put("merNo", info.getMerNo());
		criteria.getCondition().put("terNo", info.getTerNo());
		//??????????????????
		i=0;
		PageInfo<RateInfo> oldRateInfoList=rateMgrService.getListRateInfo(criteria);
		for(RateInfo ri:oldRateInfoList.getData()){
			ri.setMerNo(info.getNewMerNo());
			ri.setTerNo(info.getNewTerNo());
			ri.setCreateBy(getLogAccount().getRealName());
			i+=rateMgrService.addRateInfo(ri);
		}
		msg+="?????????"+i+"???????????????<br>";
		//??????????????????????????????
		i=0;
		PageInfo<MerchantRelCurrencyInfo> mrcPage=merchantMgrService.getMerchnatRelCurrencyList(criteria);
		for(MerchantRelCurrencyInfo mrci:mrcPage.getData()){
			mrci.setMerNo(info.getNewMerNo());
			mrci.setTerNo(info.getNewTerNo());
			mrci.setCreateBy(getLogAccount().getRealName());
			i+=merchantMgrService.addCurrencyToMerchnat(mrci);
			
		}
		msg+="?????????"+i+"???????????????????????????<br>";
		//??????????????????
		PageInfo<GwTernoLmitInfo> gtpage=merchantMgrService.terLimitInfoList(criteria);
		i=0;
		for(GwTernoLmitInfo mrci:gtpage.getData()){
			mrci.setMerNo(info.getNewMerNo());
			mrci.setTerNo(info.getNewTerNo());
			mrci.setCreateby(getLogAccount().getRealName());
			i+=merchantMgrService.addTerLimit(mrci);
			
		}
		msg+="?????????"+i+"???????????????<br>";
		//??????????????????
		i=0;
		PageInfo<SettleTypeInfo> stipage=settleMgrService.listSettleTypeInfo(criteria);
		for(SettleTypeInfo mrci:stipage.getData()){
			mrci.setMerNo(info.getNewMerNo());
			mrci.setTerNo(info.getNewTerNo());
			mrci.setCreateBy(getLogAccount().getRealName());
			i+=settleMgrService.insertSettleTypeInfo(mrci);
			
		}
		msg+="?????????"+i+"???????????????<br>";
		//???????????????????????????
		i=0;
		PageInfo<MerchantRefRuleProfileInfo> result =  fraudManageService.queryPageAccountRefProfileList(criteria);
		for(MerchantRefRuleProfileInfo mrci:result.getData()){
			mrci.setMerNo(info.getNewMerNo());
			mrci.setTerNo(info.getNewTerNo());
			mrci.setCreateBy(getLogAccount().getRealName());
			i+=fraudManageService.addProfileToAccount(mrci);
					
		}
		msg+="?????????"+i+"????????????????????????";
		return ajaxDoneSuccess(msg);
	}
	
	/**
	 * ???????????????????????????????????????
	 */
	@RequestMapping(value="/goUpdateSpareTerNo")
	public String goUpdateSpareTerNo(int id){
		MerchantTerInfo merchantTerInfo=merchantMgrDao.queryMerchantTerNoById(id);
		getRequest().setAttribute("merchantTerInfo", merchantTerInfo);
		return "merchantmgr/goUpdateSpareTerNo";
	}
	
	@RequestMapping(value="/updateSpareTerNo")
	public ModelAndView updateSpareTerNo(MerchantTerInfo merchantTerInfo){
		int a = -1;
		a = merchantMgrService.updateSpareTerNo(merchantTerInfo);
		if(a>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/**
	 * ????????????????????????????????????
	*/
	@RequestMapping(value="/goSysMerchantWebsite")
	public String goMerchantWebsite(HttpServletRequest request){
		Criteria criteria = RequestUtil.getCaCriteria(request);
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String operateDateStart=sdf1.format(date);
			String operateDateEnd = sdf1.format(date);
			criteria.getCondition().put("operateDateStart", operateDateStart);
			criteria.getCondition().put("operateDateEnd", operateDateEnd);
			getRequest().setAttribute("form", criteria.getCondition());
		}
		getRequest().setAttribute("form", criteria.getCondition());
		PageInfo<MerchantWebsite> page = merchantMgrService.querySysMerchantWebsiteList(criteria);
		getRequest().setAttribute("page",page);
		return "merchantmgr/sys/merchantWebsiteList";
	}
	/** 
	 * ???????????????????????????
	*/
	@RequestMapping(value="/goSysUpdateMerchantWebsiteInfo")
	public String goUpdateMerchantWebsiteInfoPage(String id){
		MerchantWebsite merchantWebsite=merchantMgrService.querySysMerchantWebsitInfoById(id);
		getRequest().setAttribute("info", merchantWebsite);
		return "merchantmgr/sys/updateMerchantWebsiteInfo";
	}
	/**
	 * ??????
	 */
	@RequestMapping(value="/exportSysMerchantWebsite")
	public void exportSysMerchantWebsite(HttpServletResponse resp) throws IOException, RowsExceededException, WriteException{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "sysMerchantWebsiteInfo.xls" ); 
		List<MerchantWebsite> list = merchantMgrService.queryExportSysMerchantWebsiteInfo(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("????????????????????????", 0);
		String[] headerName={"?????????","?????????","??????","MCC","??????","??????","??????????????????","????????????","????????????","????????????","?????????","????????????","??????","????????????"};
		// ????????????
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			MerchantWebsite info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getMerNo()));
			sheet.addCell( new Label(col++, row, info.getTerNo()));
			sheet.addCell( new Label(col++, row, info.getMerWebSite()));
			sheet.addCell( new Label(col++, row, info.getMCC()));
			sheet.addCell( new Label(col++, row, info.getBrand()));
			sheet.addCell( new Label(col++, row, info.getProduct()));
			sheet.addCell( new Label(col++, row, info.getMerWebLanguage()));
			sheet.addCell( new Label(col++, row, info.getMerWebProgram()));
			sheet.addCell( new Label(col++, row, info.getMessage()));
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("MERCHANTWEBSITESTATUS",info.getStatus(),"????????????")));
			sheet.addCell( new Label(col++, row ,info.getAppBy()));
			sheet.addCell( new Label(col++, row, info.getAppDate()));
			sheet.addCell( new Label(col++, row, info.getRemark()));
			sheet.addCell( new Label(col++, row, info.getSysOperatedDate()!=null?info.getSysOperatedDate():""));
		}
		book.write();
		book.close();
	}
	
	/**
	 * ????????????????????????
	 */
	@RequestMapping(value="/queryMerchantDisFineList")
	public String queryMerchantDisFineList(){
		Criteria criteria = getCriteria();
		PageInfo<MerchantDisFineInfo> page = merchantMgrService.queryMerchantDisFineList(criteria);
		getRequest().setAttribute("page", page);
		return "merchantmgr/merchantDisFineInfoList";
	}
	
	/**
	 * ????????????,????????????????????????????????????
	 */
	@RequestMapping(value="/goAddMerchantDisFineInfo")
	public String goAddMerchantDisFineInfo(MerchantDisFineInfo form){
		MerchantDisFineInfo info = new MerchantDisFineInfo();
		if(form.getMerNo()!=null && form.getTerNo()!=null && !("".equals(form.getMerNo())) && !("".equals(form.getTerNo()))){
			form = merchantMgrService.queryMerchantDisFineInfo(form.getMerNo(), form.getTerNo());
			if(form!=null){
				info = form;
			}
		}
		getRequest().setAttribute("info", info);
		return "merchantmgr/addMerchantDisFineInfo";
	}
	
	/**
	 * ????????????,????????????????????????????????????
	 */
	@RequestMapping(value="/saveMerchantDisFineInfo")
	@ResponseBody
	public ModelAndView saveMerchantDisFineInfo(MerchantDisFineInfo form){
		UserInfo user = getLogAccount();
		form.setCreateBy(user.getRealName());
		form.setModify(user.getRealName());
		int a = -1;
		try {
			a = merchantMgrService.saveMerchantDisFineList(form);
		} catch (APIException e) {
			return ajaxDoneError(e.getMessage());
		}
		if(a>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/**
	 * ????????????????????????????????????
	 */
	@RequestMapping(value="/delMerchantDisFineInfo")
	@ResponseBody
	public ModelAndView delMerchantDisFineInfo(MerchantDisFineInfo form){
		int a = merchantMgrService.deleteMerchantDisFineInfo(form);
		if(a>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	
	@RequestMapping("/goAddOrUpdateMerchantRefCurrencySpecial")
	public String goAddOrUpdateMerchantRefCurrencySpecial(String id){
		MerchantCurrencySpecialInfo info=merchantMgrService.queryMerchantCurrencySpecialInfoByMerchantCurrencyId(id);
		info.setMerchantCurrencyId(Integer.parseInt(id));
		//?????????????????????
		String currencyDayAmountIds = info.getCurrencyDayAmountIds();
		if(null!=currencyDayAmountIds){
			String currencyDayAmountNames = currencyMapper.getCurrencyDayAmountNamesByIds(currencyDayAmountIds);
			info.setCurrencyDayAmountNames(currencyDayAmountNames);
		}
		getRequest().setAttribute("info", info);
		return "merchantmgr/updateMerchantRefCurrencySpecial";
	}
	
	
	
	/**
	 * ????????????????????????????????????
	 */
	@RequestMapping(value="/saveMerchantCurrencySpecialInfo")
	public ModelAndView saveMerchantCurrencySpecialInfo(MerchantCurrencySpecialInfo info){
		int a=0;
		if(info.getId()!= 0){
			info.setLastModifyBy(getLogAccount().getRealName());
			a=merchantMgrService.updateMerchantCurrencySpecialInfo(info);
		}else{
			info.setCreateBy(getLogAccount().getRealName());
			a=merchantMgrService.addMerchantCurrencySpecialInfo(info);
		}
		if(a>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	
	/**
	 * ????????????????????????????????????
	 */
	@RequestMapping(value="/queryCountryCurrencyInfoList")
	public String countryCurrencyInfoList(){
		HttpServletRequest request = getRequest();
		Criteria criteria = getCriteria();
		if("get".equalsIgnoreCase(request.getMethod())){
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			criteria.getCondition().put("startDate", sdf.format(new Date()));
//			criteria.getCondition().put("endDate", sdf.format(new Date()));
//			request.setAttribute("form", criteria.getCondition());
		}else{
			PageInfo<CountryCurrencyInfo> pageInfo = merchantMgrService.queryCountryCurrencyInfoList(criteria);
			request.setAttribute("page", pageInfo);
			request.setAttribute("form", criteria.getCondition());
		}
		return "merchantmgr/countryCurrencyInfoList";
	}
	
	/**
	 * ?????????????????????????????????????????????
	 */
	@RequestMapping(value="/toAdd")
	public String toAdd(CountryCurrencyInfo form){
		CountryCurrencyInfo info = new CountryCurrencyInfo();
		if(form.getId()!=null && !("".equals(form.getId())) && !("0".equals(form.getId()))){
			info = merchantMgrService.queryCountryCurrencyInfoById(form.getId());
		}
		getRequest().setAttribute("info", info);
		return "merchantmgr/addCountryCurrencyInfo";
	}
	
	/**
	 * ??????????????????????????????????????????
	 */
	@RequestMapping(value="/saveCountryCurrencyInfo")
	public ModelAndView saveCountryCurrencyInfo(CountryCurrencyInfo form){
		UserInfo user = getLogAccount();
		if(null==form.getCountryCode() || "".equals(form.getCountryCode())){
			form.setCountryCode("0");
		}
		if(null==form.getBrand() || "".equals(form.getBrand())){
			form.setBrand("0");
		}
		int a = -1;
		if(form.getId()!=null && !("".equals(form.getId())) && !("0".equals(form.getId()))){
			form.setModifyBy(user.getRealName());
			try {
				a = merchantMgrService.updateCountryCurrencyInfoById(form);
			} catch (APIException e) {
				return ajaxDoneError(e.getMessage());
			}
		}else{
			form.setCreateBy(user.getRealName());
			try {
				a = merchantMgrService.saveCountryCurrencyInfo(form);
			} catch (APIException e) {
				return ajaxDoneError(e.getMessage());
			}
		}
		if(a>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/**
	 * ??????????????????????????????
	 */
	@RequestMapping(value="/delCountryCurrencyInfo")
	public ModelAndView delCountryCurrencyInfo(CountryCurrencyInfo form){
		UserInfo user = getLogAccount();
		int a = -1;
		try {
			a = merchantMgrService.deleteCountryCurrencyInfoById(form.getId(), user);
		} catch (APIException e) {
			return ajaxDoneError(e.getMessage());
		}
		if(a>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/**
	 * ??????????????????????????????LOG??????
	 */
	@RequestMapping(value="/queryCountryCurrencyInfoLogList")
	public String queryCountryCurrencyInfoLogList(){
		HttpServletRequest request = getRequest();
		Criteria criteria =getCriteria();
		if("get".equalsIgnoreCase(request.getMethod())){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			criteria.getCondition().put("startDate", sdf.format(new Date()));
			criteria.getCondition().put("endDate", sdf.format(new Date()));
			request.setAttribute("form", criteria.getCondition());
		}else{
			PageInfo<CountryCurrencyLogInfo> pageInfo = merchantMgrService.queryCountryCurrencyLogInfoList(criteria);
			request.setAttribute("page", pageInfo);
			request.setAttribute("form", criteria.getCondition());
		}
		return "merchantmgr/countryCurrencyLogInfoList";
	}
	
	/**
	 * ?????????????????????,???????????????
	 */
	@RequestMapping(value="/queryMerchantMerAndTerNoList")
	public String queryMerNoInfoList(String type){
		if(type!=null && !("".equals(type))){
			Criteria criteria = getCriteria();
			HttpServletRequest request = getRequest();
			if("1".equals(type)){
				criteria.put("type", type);
				PageInfo<MerchantTerInfo> page = merchantMgrService.queryMerchantMerNoInfoList(criteria);
				List<MerchantTerInfo> list = page.getData();
				MerchantTerInfo mer = new MerchantTerInfo();
				mer.setMerchantName("??????");
				mer.setMerNo("0");
				list.add(0, mer);
				mer.setEnabled(1);
				request.setAttribute("page", page);
				return "merchantmgr/merchantMerNo_look_up";
			}
			if("2".equals(type)){
				criteria.put("type", type);
				PageInfo<MerchantTerInfo> page = merchantMgrService.queryMerchantTerNoInfoList(criteria);
				List<MerchantTerInfo> list = page.getData();
				MerchantTerInfo mer = new MerchantTerInfo();
				mer.setTerNo("0");
				mer.setTerName("??????");
				mer.setEnabled(1);
				list.add(0, mer);
				request.setAttribute("page", page);
				return "merchantmgr/merchantTerNo_look_up";
			}
			if("3".equals(type)){
				criteria.put("type", type);
				PageInfo<MerchantTypeInfo> page = merchantMgrService.queryMerchantTypeInfoList(criteria);
				List<MerchantTypeInfo> list = page.getData();
				MerchantTypeInfo merType = new MerchantTypeInfo();
				merType.setType("-1");
				merType.setTypeName("??????");
				list.add(0, merType);
				request.setAttribute("page", page);
				return "merchantmgr/merchantType_look_up";
			}
		}
		return null;
	}
	
	/**
	 * ????????????????????????
	 */
	@RequestMapping(value="/getBrandInfoList")
	public String getBrandInfoList(){
		Criteria criteria=getCriteria();
		String bpname=(String) criteria.getCondition().get("bpname");
		if(bpname!=null){
			String[] bpnames=bpname.trim().split("\\s+");
			criteria.getCondition().put("bpname", bpnames);
		}
		PageInfo<BrandProductInfo> page = merchantMgrService.getBrandInfo(criteria);
		getRequest().setAttribute("page",page);
		return "merchantmgr/brandInfoList_backup";
	}
	
	/**
	 * ????????????????????????
	 */
	@RequestMapping(value="/getCountryInfoList")
	public String getCountryInfoList(String countrys){
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
		return "merchantmgr/countryInfoList_backup";
	}
	
	/**
	 * ????????????????????????
	 */
	@RequestMapping("/getCurrencyListBrightBackMany")
	public String getCurrencyListBrightBackMany(@RequestParam(name="currencyIds",required=false) String currencyIds){
		Criteria criteria=getCriteria();
		Integer pageNum= criteria.getPageNum();
		Integer pageSize=criteria.getPageSize();
		//??????????????????(??????????????????)???????????????????????????????????????????????????
		criteria.setPageNum(1);;
		criteria.setPageSize(Integer.MAX_VALUE);
		PageInfo<CurrencyInfo> pageData = bankMgrService.getCurrencyList(criteria);
		//??????????????????
		PageInfo<CurrencyInfo> page = new PageInfo<CurrencyInfo>(pageNum, pageSize);
		page.setTotal(pageData.getTotal());
		page.setData(pageData.getData());
		getRequest().setAttribute("page", page);
		List<Long> idsList=new ArrayList<Long>();
		if(null!=currencyIds && !"".equals(currencyIds)){
			String[] ids=currencyIds.split(",");
			if(ids.length>0){
				for (int i = 0; i < ids.length; i++) {
					String strId=ids[i];
					if(!"".equals(strId)){
						Long id=Long.parseLong(strId);
						idsList.add(id);
					}
					
				}
			}
		}
		getRequest().setAttribute("idsList", idsList);
		getRequest().setAttribute("currencyIds", currencyIds);
		return "bankmgr/currencyListBrighBackMany";
	}

	
	@RequestMapping("/queryShopify")
	public String queryShopify(@RequestParam("id")String id,HttpServletRequest request){
		MerchantTerInfo info=merchantMgrService.getShopifyById(id);
		request.setAttribute("data", info);
		return "merchantmgr/updateShopify";
	}
	
	@RequestMapping("/updateShopify")
	public ModelAndView updateShopify(@RequestParam("id")String id,@RequestParam("shopifyOnOff")String shopifyOnOff){
		int i=merchantMgrService.updateShopifyById(shopifyOnOff, id);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
		
	}
	
	
	@RequestMapping("/goQureyManyWebsite")
	public String goQureyManyWebsite(){
		return "merchantmgr/qureyManyWebsite";
	}
	
	/** ?????????????????????????????? */
	@ResponseBody
	@RequestMapping(value="/qureyManyWebsite")
	public String qureyManyWebsite(DefaultMultipartHttpServletRequest request,HttpServletResponse res)throws IOException, ParseException, ISOException{
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		res.setContentType("text/html");
		List<String> list=new ArrayList<String>();
		StringBuffer sb=new StringBuffer();
		if(null != files){
			log.info("?????????"+files.size()+"????????????");
			for(MultipartFile file :files){
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8"));//????????????BufferedReader??????????????????
				String line = br.readLine();
				line = br.readLine();
				while(null != line){
					log.info("line:"+line);
					line=line.trim();
					sb.append(line);
					sb.append(",");
					list.add(line);
					line = br.readLine();
				}
			}
			
		}
		String message=sb.substring(0,sb.lastIndexOf(","));
		
		return message;
		//request.getSession().setAttribute("qureyManyWebsiteList", list);
		
		//return ajaxDoneSuccess("????????????????????????"+list.size()+"?????????");
	}
	
	
	
	
}