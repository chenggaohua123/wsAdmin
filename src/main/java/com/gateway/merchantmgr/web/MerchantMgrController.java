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
	 * 获取商户列表
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
	 * 查询商户的终端列表地
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
	 * 跳转修改商户信息页面
	 * @return
	 */
	@RequestMapping(value="/goUpdateMerchant")
	public String goUpdateMerchant(int id){
		MerchantInfo merchantInfo= merchantMgrService.queryMerchantInfoById(id);
		getRequest().setAttribute("merchantInfo", merchantInfo);
		return "merchantmgr/updateMerchantInfo";
	}
	
	/**
	 * 修改商户保存之前记录
	 * @param merchantInfo
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/updateMerchant")
	public ModelAndView updateMerchant(MerchantInfo merchantInfo) throws ServiceException{
		merchantInfo.setUpdatePerson(getLogAccount().getRealName());
		int i=merchantMgrService.updateMerchantInfo(merchantInfo);
		if(i>0){
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	
	/**
	 * 查看商户历史记录
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
	 * 商户详细信息
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
	
	/**
	 * 添加商户配置信息
	 * @param id
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/goAddMerchantConfig")
	public String goAddMerchantConfig(int id) throws ServiceException{
		MerchantTerInfo merchantTerInfo= merchantMgrService.queryTerInfoById(id);
		if(null == merchantTerInfo){
			throw new ServiceException("该终端不存在，");
		}
		getRequest().setAttribute("terInfo", merchantTerInfo);
		return "merchantmgr/addMerchantConfigInfo";
	}
	
	/**
	 * 添加配置信息
	 * @param info
	 * @return
	 */
	@RequestMapping(value="/addMerchantConfig")
	public ModelAndView addMerchantConfig(MerchantConfig conInfo){
		conInfo.setCreateBy(getLogAccount().getRealName());
		int count = merchantMgrService.addMerchantConfig(conInfo);
		if(count > 0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	
	/**
	 * 给商户管理列表添加终端号
	 * @return
	 */
	@RequestMapping(value="/goAddTerInfo")
	public String goAddTerInfo(int id) throws APIException{
		MerchantInfo merchantInfo= merchantMgrService.queryMerchantInfoById(id);
		if(StringUtils.isEmpty(merchantInfo)){
			throw new APIException("该商户不存在，请重试");
		}
		MerchantTerInfo terInfo=new MerchantTerInfo();
		terInfo.setSourceCurrencyCode(merchantMgrService.querySourceCurrencyCode());
		getRequest().setAttribute("terInfo", terInfo);
		getRequest().setAttribute("merchantInfo", merchantInfo);
		return "merchantmgr/addMerchantTerInfo";
	}
	
	/**
	 * 添加终端信息
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/addTerInfo")
	public ModelAndView addTerInfo(MerchantTerInfo terInfo ) throws ServiceException{
		MerchantInfo info = merchantMgrService.queryMerchantInfoByMerNo(terInfo.getMerNo());
		if(null == info){
			throw new ServiceException("商户号不存在。");
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
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	
	/**
	 * 修改商户终端信息
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/goUpdateTerInfo")
	public String goUpdateTerInfo(int id) throws ServiceException{
		MerchantTerInfo terInfo = merchantMgrService.queryTerInfoById(id);
		if(null == terInfo){
			throw new ServiceException("终端不存在。");
		}
		terInfo.setSourceCurrencyCode(merchantMgrService.querySourceCurrencyCode());
		getRequest().setAttribute("terInfo", terInfo);
		return "merchantmgr/updateMerchantTerInfo";
	}
	
	/**
	 * 更新终端信息
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
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败。");
		}
		
	}
	
	/**
	 * 查看商户配置信息
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
	 * 查询终端历史修改记录
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
	 * 商户通道绑定列表
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
	 * 跳转到商户绑定通道页面
	 * @return
	 */
	@RequestMapping(value="/goAddCurrencyToMerchant")
	public String goAddCurrencyToMerchant(){
		return "merchantmgr/addCurrencyToMerchnat"; 
	}
	
	/**
	 * 添加商户绑定记录
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/addCurrencyToMerchnat")
	public ModelAndView addCurrencyToMerchnat(MerchantRelCurrencyInfo  info) throws ServiceException{
		
		info.setCreateBy(getLogAccount().getRealName());
		int count = merchantMgrService.addCurrencyToMerchnat(info);
		if(count > 0){
			return ajaxDoneSuccess("添加成功。");
		}else{
			return ajaxDoneSuccess("添加失败。");
		}
	}
	
	/**
	 * 跳转到商户修改通道信息页面
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
	 * 修改商户绑定通道信息
	 * @param info
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value="/updateCurrencyToMechant")
	public ModelAndView updateCurrencyToMechant(MerchantRelCurrencyInfo info) throws ServiceException{
		info.setUpdateBy(getLogAccount().getRealName());
		int count = merchantMgrService.updateCurrencyToMerchnat(info);
		if(count > 0){
			return ajaxDoneSuccess("修改成功。");
		}else{
			return ajaxDoneSuccess("修改失败。");
		}
	}
	
	/**
	 * 查看商户绑定通道的历史记录
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
	 * 批量修改商户绑定通道
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
		return ajaxDoneSuccess("修改成功。更新记录："+count+"条。");
	}
	
	@RequestMapping(value="/getAgentList")
	public String getAgentList(){
		PageInfo<AgentInfo> page = merchantMgrService.getAgentList(getCriteria());
		getRequest().setAttribute("page", page);
		return "merchantmgr/agentList";
	}
	
	/**
	 * 跳转增加代理商页面
	 * @return
	 */
	@RequestMapping(value="/goAddAgentInfo")
	public String goAddAgentInfo(){
		return "merchantmgr/addAgentInfo";
	}
	
	/**
	 * 添加代理商
	 * @param agentInfo
	 * @return
	 */
	@RequestMapping(value="/addAgentInfo")
	public ModelAndView addAgentInfo(AgentInfo agentInfo){
		agentInfo.setCreateBy(getLogAccount().getRealName());
		if(null==merchantMgrService.queryAgentByAgentNo(agentInfo.getAgentNo())){
			int i=merchantMgrService.addAgentInfo(agentInfo);
			if(i > 0){
				return ajaxDoneSuccess("添加成功。");
			}else{
				return ajaxDoneError("添加失败。");
			}
		}else{
			return ajaxDoneError("当前代理商已有此商户");
		}
	}
	
	
	/**
	 * 跳转修改代理商页面
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
	 * 修改代理商
	 * @param agentInfo
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value="/updateAgentInfo")
	public ModelAndView updateAgentInfo(AgentInfo agentInfo) throws ServiceException{
		agentInfo.setCreateBy(getLogAccount().getRealName());
		int i=merchantMgrService.updateAgentInfo(agentInfo);
		if(i > 0){
			return ajaxDoneSuccess("添加成功。");
		}else{
			return ajaxDoneSuccess("添加失败。");
		}
	}
	
	/**
	 * 查看代理商历史记录
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
	 * 代理商户详细显示
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
	 * 代理商查找带回
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
	 * 跳转修改配置信息页面
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
	 * 修改配置信息
	 * @param config
	 * @return
	 */
	@RequestMapping(value="/updateMerchantConfig")
	public ModelAndView updateMerchantConfig(MerchantConfig config){
		config.setCreateBy(getLogAccount().getRealName());
		int i=merchantMgrService.updateMerchantConfig(config);
		if(i > 0){
			return ajaxDoneSuccess("修改成功。");
		}else{
			return ajaxDoneSuccess("修改失败。");
		}
	}
	
	/**
	 * 删除配置信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/deleteMerchantConfig")
	public ModelAndView deleteMerchantConfig(int id){
		int i=merchantMgrService.deleteMerchantConfig(id);
		if(i > 0){
			return ajaxDoneSuccess("删除成功。");
		}else{
			return ajaxDoneSuccess("删除失败。");
		}
	}
	
	/**
	 * 商户绑定代理商
	 * @return
	 */
	@RequestMapping(value="/goMerchantRelAgent")
	public String goMerchantRelAgent(int id){
		MerchantTerInfo terInfo=merchantMgrService.queryMerchantTerNoById(id);
		getRequest().setAttribute("terInfo", terInfo);
		return "merchantmgr/addMerchantRelAgent";
	}
	
	
	/**
	 * 跳转商户绑定代理商 查找带回
	 * @return
	 */
	@RequestMapping("/merchantRelAgentBack")
	public String merchantRelAgent(){
		PageInfo<AgentInfo> info=merchantMgrService.getAgentList(getCriteria());
		getRequest().setAttribute("info", info);
		return "merchantmgr/merchantRelagentListBack";
	}
	
	/**
	 * 绑定代理商
	 * @param merNo
	 * @param agentNo
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/merchantRelAgent")
	public ModelAndView merchantRelAgent(String merNo,String terNo,String agentNo) throws ServiceException{
		GwAgentRelMerchant relMerchant=merchantMgrService.queryAgentRelMerchant(merNo,terNo);
		if(relMerchant!=null){
			throw new ServiceException("已绑定代理商。");
		}
		int i=merchantMgrService.addMerchantRelAgent(merNo,terNo,getLogAccount().getRealName());
		if(i > 0){
			return ajaxDoneSuccess("绑定成功。");
		}else{
			return ajaxDoneSuccess("绑定失败。");
		}
	}
	
	

	/**
	 *关联用户
	 * @return
	 */
	@RequestMapping(value="/goRefMerchantUser")
	public String gorefMerchantUser(int id){
		MerchantInfo merchantInfo=merchantMgrService.queryMerchantInfoById(id);
		getRequest().setAttribute("merchantInfo", merchantInfo);
		return "merchantmgr/addMerchantRelUser";
	}
	
	
	/**
	 * 跳转商户绑定用户 查找带回
	 * @return
	 */
	@RequestMapping("/merchantRelUserBack")
	public String merchantRelUser(){
		PageInfo<UserInfo> page = sysMgrService.queryPageUser(getCriteria());
		getRequest().setAttribute("page", page);
		return "merchantmgr/merchantRelUserListBack";
	}
	
	
	/**
	 * 绑定关联用户
	 * @param merNo
	 * @param agentNo
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/merchantRelUser")
	public ModelAndView merchantRelUser(GwUserRelMerchantInfo info) throws ServiceException{
		GwUserRelMerchantInfo userRelMerchantInfo=merchantMgrService.queryUserRelMerchant(info.getMerNo());
		if(userRelMerchantInfo!=null){
			throw new ServiceException("已绑定用户。");
		}
		info.setCreateBy(getLogAccount().getRealName());
		int i=merchantMgrService.addUserRefMerchant(info);
		if(i > 0){
			return ajaxDoneSuccess("绑定成功。");
		}else{
			return ajaxDoneSuccess("绑定失败。");
		}
	}
	
	
	
	
	
	
	/**
	 * 商户信息导出
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
		WritableSheet sheet = book.createSheet("终端列表", 0);
		String[] headerName = { "商户号", "商户名称","商户联系人","商户电话","商户邮箱","QQ号","商户地址","开户状态","激活状态","直连状态","销售员","OA业务单号","商户注册日期","合同生效日期","合同失效日期","商户类别","通道类型","商户行业","实收费用历史","备注"
				,"商户地址","SWIFT","银行地址"};
		// 写入标题
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
	 * 审核商户终端
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/checkTerInfo")
	public ModelAndView checkTerInfo(int id){
		MerchantTerInfo terInfo = merchantMgrService.queryTerInfoById(id);
		if(terInfo.getEnabled()==4){
			int i=merchantMgrService.updateChecnkTerNo(terInfo);
			if(i>0){
				return ajaxDoneSuccess("审核成功");
			}else{
				return ajaxDoneError("审核失败");
			}
		}else{
			return ajaxDoneError("重新审核");
		}
	}
	
	/**
	 * 将终端下发到指定的代理商
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
	 * 跳转添加终端下发页面
	 * @return
	 */
	@RequestMapping(value="/addTerSnRelAgentgo")
	public String addTerSnRelAgentgo(){
		return "merchantmgr/addSnNoToAgent";
	}
	
	
	/**
	 * 跳转商户绑定顶级代理商 查找带回
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
	 * 主密钥查询
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
	 * 终端下发
	 * @return
	 */
	@RequestMapping(value="/addTerSnRelAgent")
	public ModelAndView addTerSnRelAgent(TerSnRelAgentInfo agentInfo){
		agentInfo.setCreatBy(getLogAccount().getRealName());
		int i=merchantMgrService.addTerSnRelAgent(agentInfo);
		if(i > 0){
			return ajaxDoneSuccess("添加成功。");
		}else{
			return ajaxDoneSuccess("添加失败。");
		}
	}
	
	/**
	 * 导出商户终端
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
		WritableSheet sheet = book.createSheet("终端列表", 0);
		String[] headerName = { "商户号","终端号","终端名称","交易币种","结算币种","收款账户名","收款银行","收款账号"
				,"银行所在国家","银行所在省","银行所在市","交易安全码","终端状态","备注","商户地址","SWIFT","银行地址","产品类型"};
		// 写入标题
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
			sheet.addCell( new Label(col++, row, info.getEnabled()==1?"开通":"未开通"));
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
	 * 导出终端终端下发列表
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
		WritableSheet sheet = book.createSheet("终端下发列表", 0);
		String[] headerName = { "终端序列号", "父级代理商号","代理商号","下发时间","下发人","修改时间","修改人"};
		// 写入标题
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
	 * 终端配额信息
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
	 *导出交易 
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
		WritableSheet sheet = book.createSheet("限额列表", 0);
		  String[] headerName={"商户号","终端号","商户名称","限制币种","单笔限额","日限额","日交易金额","日限额使用比例",
				  "月限额","月交易金额","月限额使用比例","操作人","操作时间"};
		// 写入标题
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
	 * 跳转终端配额页面
	 * @return
	 */
	@RequestMapping(value="/addTerLimitGo")
	public String addTerLimitGo(){
		return "merchantmgr/addTerLimitInfo";
	}
	
	/**
	 * 添加终端配额
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/addTerLimit")
	public ModelAndView addTerLimit(GwTernoLmitInfo lmitInfo) throws ServiceException{
		lmitInfo.setUpby(getLogAccount().getRealName());
		if(!"".equals(lmitInfo.getMerNo())){
			MerchantInfo info = merchantMgrService.queryMerchantInfoByMerNo(lmitInfo.getMerNo());
			if(null == info){
				throw new ServiceException("商户号不存在。");
			}
		}
		MerchantTerInfo temp = merchantMgrService.queryTerInfoByMerNoAndTerNo(lmitInfo.getMerNo(), lmitInfo.getTerNo());
		if(null == temp){
			throw new ServiceException("该商户的终端不存在");
		}
		int i = merchantMgrService.queryTerLimit(lmitInfo);
		if(0 != i){
			return ajaxDoneError("该商户下的终端已添加终端配额");
		}
		int count = merchantMgrService.addTerLimit(lmitInfo);
		lmitInfo.setType("add");
		merchantMgrService.addTerLimitLog(lmitInfo);
		if(count > 0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	/**
	 * 跳转修改终端配额页面
	 * @return
	 */
	@RequestMapping(value="/updateTerNoGo")
	public String updateTerNoGo(int id){
		GwTernoLmitInfo lmitInfo=merchantMgrService.queryTerNoLimitById(id);
		getRequest().setAttribute("lmitInfo", lmitInfo);
		return "merchantmgr/updateTerNoLimit";
	}
			
			
	/**
	 * 修改终端配额
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
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	
	/**
	 * 终端配额信息
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
	 *导出交易 
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
		WritableSheet sheet = book.createSheet("限额列表", 0);
		  String[] headerName={"商户号","终端号","商户名称","限制币种","单笔限额","日限额","日交易金额","日限额使用比例",
				  "月限额","月交易金额","月限额使用比例","月限额总笔数","月总笔数","月限总笔数使用率","操作人","操作时间"};
		// 写入标题
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
	 * 删除终端配额
	 */
	@RequestMapping(value="/deleteTerLimitCountInfo")
	public ModelAndView deleteTerLimitCountInfo(String id){
		int count=merchantMgrService.deleteTerLimitCountInfoById(id);
		if(count>0){
			return ajaxDoneSuccess("删除成功");
		}else{
			return ajaxDoneError("删除失败");
		}
		
	}
	
	/**
	 * 跳转终端配额页面
	 * @return
	 */
	@RequestMapping(value="/addTerLimitCountGo")
	public String addTerLimitCountGo(){
		return "merchantmgr/addTerLimitCountInfo";
	}
	
	/**
	 * 添加终端配额
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/addTerLimitCount")
	public ModelAndView addTerLimitCount(GwTernoLmitInfo lmitInfo) throws ServiceException{
		lmitInfo.setUpby(getLogAccount().getRealName());
		if(!"".equals(lmitInfo.getMerNo())){
			MerchantInfo info = merchantMgrService.queryMerchantInfoByMerNo(lmitInfo.getMerNo());
			if(null == info){
				throw new ServiceException("商户号不存在。");
			}
		}
		MerchantTerInfo temp = merchantMgrService.queryTerInfoByMerNoAndTerNo(lmitInfo.getMerNo(), lmitInfo.getTerNo());
		if(null == temp){
			throw new ServiceException("该商户的终端不存在");
		}
		int i = merchantMgrService.queryTerLimit(lmitInfo);
		if(0 != i){
			return ajaxDoneError("该商户下的终端已添加终端配额");
		}
		int count = merchantMgrService.addTerLimit(lmitInfo);
		lmitInfo.setType("add");
		merchantMgrService.addTerLimitLog(lmitInfo);
		if(count > 0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	/**
	 * 跳转修改终端配额页面
	 * @return
	 */
	@RequestMapping(value="/updateTerNoCountGo")
	public String updateTerNoCountGo(int id){
		GwTernoLmitInfo lmitInfo=merchantMgrService.queryTerNoLimitById(id);
		getRequest().setAttribute("lmitInfo", lmitInfo);
		return "merchantmgr/updateTerNoLimitCount";
	}
			
			
	/**
	 * 修改终端配额
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
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	
	@RequestMapping(value="/goLmitInfoLog")
	public String goLmitInfoLog(String id){
		List<GwTernoLmitInfo> list = merchantMgrService.queryTerLimitLog(id);
		getRequest().setAttribute("list", list);
		return "merchantmgr/terLimitListLog";
	}
	
	/**
	 * 机具回收
	 * @return
	 */
	@RequestMapping(value="/TerSnRelRecycle")
	public ModelAndView	TerSnRelRecycle(TerSnRelAgentInfo agentInfo){
		int i=merchantMgrService.updateTerSnRelRecycle(agentInfo);
		if(i > 0){
			return ajaxDoneSuccess("回收成功。");
		}else{
			return ajaxDoneError("回收失败。");
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
	 * 商户号，终端号查找带回
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
	 * 商户复核
	 * @param info
	 * @return
	 */
	@RequestMapping(value="/updateMerchantInfoReState")
	public ModelAndView updateMerchantInfoReState(MerchantInfo info){
		MerchantInfo merchantInfo=merchantMgrService.queryMerchantInfoById(info.getId());
		if(!StringUtils.isEmpty(merchantInfo) && "1".equals(merchantInfo.getReState())){
			return ajaxDoneError("该商户已复核，不能重复复核");
		}
		List<MerchantInfo> list=merchantMgrService.queryMerchantLogList(merchantInfo.getMerNo());
		if(list.size()>0){
			MerchantInfo merchantInfo2=merchantMgrService.queryMerchantLogList(merchantInfo.getMerNo()).get(0);
			if(null!=merchantInfo2 && merchantInfo2.getUpdatePerson().equals(getLogAccount().getRealName())){
				return ajaxDoneError("不能由本人复核");
			}
		}
		
		info.setReState("1");
		info.setEnabled(1);
		info.setUpdatePerson(getLogAccount().getRealName());
		int i=merchantMgrService.updateMerchantInfoReState(info);
		if(i > 0){
			return ajaxDoneSuccess("复核成功。");
		}else{
			return ajaxDoneError("复核失败。");
		}
	}
	/**
	 * 
	 * 商户结算周期查询
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
	 * 跳转到添加商户结算周期
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
				throw new ServiceException("商户号不存在。");
			}
		}
		int count = merchantMgrService.addMerchantSettleCycle(msc);
		if(count > 0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	/**
	 * 通过ID查询商户结算周期
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
				throw new ServiceException("商户号不存在。");
			}
		}
		int count = merchantMgrService.updateMerchantSettleCycle(msc);
		if(count > 0){
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	/**
	 * 显示商户网址绑定
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
	 * 导出商户网站信息
	 * */
	@RequestMapping(value="/exportWebsiteInfo")
	public void exportWebsiteInfo(HttpServletResponse resp) throws Exception{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "merchantWebsiteInfo.xls" ); 
		List<MerchantWebsite> list = merchantMgrDao.getListMerchantWebsite(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("交易列表", 0);
		//String[] headerName = { "交易流水号", "参考号","商户号","终端号","交易类型","交易金额","商户手续费","返回码","交易时间","交易通道","交易银行","结算日期"};
		
		  String[] headerName={"商户号","终端号","网址","MCC","品牌","产品","网站程序语言","网站程序","审核意见","审核状态","审批人","审批日期","备注"};
		// 写入标题
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
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("MERCHANTWEBSITESTATUS",info.getStatus(),"未知状态")));
			sheet.addCell( new Label(col++, row ,info.getAppBy()));
			sheet.addCell( new Label(col++, row, info.getAppDate()));
			sheet.addCell( new Label(col++, row, info.getRemark()));
		}
			
			
		book.write();
		book.close();
	}
	/**
	 * 跳转到商户网址添加页面
	 * */
	@RequestMapping("/goAddMerchantWebsite")
	public String goAddMerchantWebSite(){
		return "merchantmgr/addMerchantWebsite";
	}
	/**
	 * 商户网址添加
	 * @throws ServiceException 
	 * */
	@RequestMapping(value="/addMerchantWebsite")
	public ModelAndView addMerchantWebsite(MerchantWebsite merchantWebsite) throws ServiceException{
		if(!"".equals(merchantWebsite.getMerNo())){
			int i = merchantMgrService.queryMerchantInfoByMerNo(merchantWebsite.getMerNo(),merchantWebsite.getTerNo());
			if(i == 0){
				throw new ServiceException("商户号或者终端号不正确！");
			}
		}
		int y = merchantMgrService.queryMerchantWebsite(merchantWebsite);
		if(0 != y ){
			return ajaxDoneError("该网站已添加，请勿重复添加");
		}
		merchantWebsite.setCreateBy(getLogAccount().getRealName());
		int count = merchantMgrService.addMerchantWebsite(merchantWebsite);
		if(count > 0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	/** 去批量上传网站页面 */
	@RequestMapping(value="/goUploadMerchantWebsite")
	public String goUploadMerchantWebsite(){
		return "merchantmgr/uploadMerchantWebsite";
	}
	
	/** 批量上传网站 */
	@RequestMapping(value="/uploadMerchantWebsite")
	public ModelAndView uploadMerchantWebsite(DefaultMultipartHttpServletRequest request,HttpServletResponse res)throws IOException, ParseException, ISOException{
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		res.setContentType("text/html");
		int count=0;
		int total=0;
		StringBuffer errorWebsitStr=new StringBuffer("");
		List<MerchantWebsite> infos=new ArrayList<MerchantWebsite>();
		if(null != files){
			log.info("上传了"+files.size()+"个文件。");
			for(MultipartFile file :files){
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8"));//构造一个BufferedReader类来读取文件
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
								errorWebsitStr.append("商户号或终端号错误："+line+"\r\n");
								line = br.readLine();
								continue;
							}
							int y = merchantMgrService.queryMerchantWebsite(info);
							if(0 != y ){
								errorWebsitStr.append("网站重复："+line+"\r\n");
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
								errorWebsitStr.append("商户号或终端号错误："+line+"\r\n");
								line = br.readLine();
								continue;
							}
							int y = merchantMgrService.queryMerchantWebsite(info);
							if(0 != y ){
								errorWebsitStr.append("网站重复："+line+"\r\n");
								line = br.readLine();
								continue;
							}
							com.gateway.brandProduct.model.BrandProductInfo binfo=new com.gateway.brandProduct.model.BrandProductInfo();
							binfo.setBpname(info.getBrand());
							int z=brandProductService.queryBrandProductDup(binfo);
							if(z<1){
								errorWebsitStr.append("品牌不存在："+line+"\r\n");
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
							errorWebsitStr.append("格式错误："+line+"\r\n");
							log.info("错误line:"+line);
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
								errorWebsitStr.append("商户号或终端号错误："+line+"\r\n");
								line = br.readLine();
								continue;
							}
							int y = merchantMgrService.queryMerchantWebsite(info);
							if(0 == y ){
								errorWebsitStr.append("网站不存在："+line+"\r\n");
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
									errorWebsitStr.append("品牌不存在："+line+"\r\n");
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
							errorWebsitStr.append("格式错误："+line+"\r\n");
							log.info("错误line:"+line);
						}
					}
					line = br.readLine();
				}
			}
			
		}
		getRequest().getSession().setAttribute("errorWebsitStr", errorWebsitStr.toString());
		log.info(errorWebsitStr.toString());
		return ajaxDoneSuccess("上传成功，上传了"+total+"条记录,成功上传了"+count+"条记录，失败了"+(total-count)+"条记录");
	}
	
	// 下载上传失败错误信息
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
	 * 跳转到商户网址审核页面
	 * @throws ServiceException 
	 * */
	@RequestMapping(value="/goUpdateMerchantWebsite")
	public String goUpdateMerchantWebsite(String id) throws ServiceException{
		MerchantWebsite merchantWebsite=merchantMgrService.queryMerchantWebsiteById(id);
//		if(!"0".equals(merchantWebsite.getStatus())){
//			throw new ServiceException("请选择未审核的信息！");
//		}
		getRequest().setAttribute("info", merchantWebsite);
		return "merchantmgr/updateMerchantWebsite";
	}
	/**
	 * 商户网址审核
	 * */
	@RequestMapping(value="/updateMerchantWebsite")
	public ModelAndView updateMerchantWebsite(MerchantWebsite merchantWebsite){
		merchantWebsite.setAppBy(getLogAccount().getRealName());
		int count = merchantMgrService.updateMerchantWebsite(merchantWebsite);
		if(count > 0){
			return ajaxDoneSuccess("审核成功");
		}else{
			return ajaxDoneError("审核失败");
		}
	}
	
	/** 批量审核网站 */
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
	
	/** 批量审核 */
	@RequestMapping(value="/batchCheckMerchantWebsite")
	public ModelAndView batchCheckMerchantWebsite(MerchantWebsite web){
		if(StringUtils.isEmpty(web) || StringUtils.isEmpty(web.getIds())){
			return ajaxDoneError("审核失败");
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
		return ajaxDoneSuccess("审核提交数据为：" + countTotal + "条，审核成功数据条数为：" + countInt + "条");
	}
	
	/** 批量设置数据 */
	@RequestMapping(value="/setMerchantWebsite")
	public ModelAndView setMerchantWebsite(MerchantWebsite web){
		if(StringUtils.isEmpty(web) || StringUtils.isEmpty(web.getIds())){
			return ajaxDoneError("数据设置失败");
		}
		String [] str = web.getIds().split(",");
		for(String id:str){
			MerchantWebsite merchantWebsite=merchantMgrService.queryMerchantWebsiteById(id);
			merchantWebsite.setProduct(web.getProduct());
			merchantWebsite.setBrand(web.getBrand());
			merchantWebsite.setMCC(web.getMCC());
			merchantWebsite.setOperationBy(getLogAccount().getRealName());//操作人
			merchantMgrService.updateMerchantWebsiteInfo(merchantWebsite);
		}
		return ajaxDoneSuccess("数据设置成功");
	}
	
	/** 去修改商户网站信息 */
	@RequestMapping(value="/goUpdateMerchantWebsiteInfo")
	public String goUpdateMerchantWebsiteInfo(String id) throws ServiceException{
		MerchantWebsite merchantWebsite=merchantMgrService.queryMerchantWebsiteById(id);
//		if(!"0".equals(merchantWebsite.getStatus())){
//			throw new ServiceException("请选择未审核的信息！");
//		}
		getRequest().setAttribute("info", merchantWebsite);
		return "merchantmgr/updateMerchantWebsiteInfo";
	}
	
	/** 修改商户网站信息 */
	@RequestMapping(value="/updateMerchantWebsiteInfo")
	public ModelAndView updateMerchantWebsiteInfo(MerchantWebsite merchantWebsite){
		merchantWebsite.setOperationBy(getLogAccount().getRealName());//操作人
		int count = merchantMgrService.updateMerchantWebsiteInfo(merchantWebsite);
		if(count > 0){
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	
	/** 批量删除网站 */
	@RequestMapping(value="/deleteWebsite")
	public ModelAndView deleteWebsite(String [] ids){
		for(String id:ids){
			MerchantWebsite merchantWebsite=merchantMgrService.queryMerchantWebsiteById(id);
			merchantMgrService.insertOperationLog(merchantWebsite, getLogAccount().getRealName(), "delete");
		}
		int i = merchantMgrService.deleteWebsite(ids);
		if(0 < i){
			return ajaxDoneSuccess("删除成功");
		}
		return ajaxDoneError("删除失败");
	}
	
	/** 查询历史记录 */
	@RequestMapping(value="/queryWebsiteLogList")
	public String queryWebsiteLogList(){
		PageInfo<MerchantWebsite> page = merchantMgrService.queryWebsiteLogList(getCriteria());
		getRequest().setAttribute("page",page);
		return "merchantmgr/websiteLogList";
	}
	
	/** 商户终端管理 */
	@ResponseBody
	@RequestMapping(value="/checkTerNoStatus")
	public ModelAndView checkTerNoStatus(int id)throws APIException{
		MerchantTerInfo info = merchantMgrService.queryMerchantTerNoById(id);
		if(StringUtils.isEmpty(info)){
			throw new APIException("该数据错误，请重试");
		}
		if(StringUtils.isEmpty(info.getSettleCurrency())){
			return ajaxDoneError("该终端没有添加结算币种，请点击修改终端，添加终端结算币种");
		}
		if(1 == info.getReStatus()){
			return ajaxDoneError("该终端已复核，不能重复复核");
		}
		info.setReStatus(1);
		info.setEnabled(1);
		int i = merchantMgrService.updateMerchantTerNoInfo(info);
		if(i > 0){
			return ajaxDoneSuccess("复核成功");
		}
		return ajaxDoneError("复核失败");
	}
	/**
	 * 重置密码
	 * @param id
	 * @return
	 * @throws APIException 
	 */
	@RequestMapping(value="/goReSetPass")
	public String goReSetPass(Integer id) throws APIException{
		MerchantInfo merchantInfo= merchantMgrService.queryMerchantInfoById(id);
		if(null == merchantInfo){
			throw new APIException("商户信息不存在。");
		}
		//查询所有的用户列表
		List<UserInfo> list = sysMgrService.queryUserInfoByMerNo(merchantInfo.getMerNo());
		getRequest().setAttribute("list", list);
		return "merchantmgr/resetPass";
	}
	
	/**
	 * 重置密码
	 * @param id
	 * @return
	 * @throws APIException 
	 */
	@RequestMapping(value="/reSetPass")
	public String reSetPass(Integer id) throws APIException{
		UserInfo userInfo = sysMgrService.queryUserInfoById(id);
		if(null == userInfo){
			throw new APIException("商户信息不存在。");
		}
		getRequest().setAttribute("user", userInfo);
		return "merchantmgr/inputPass";
	}
	
	/**
	 * 更新密码
	 * @return
	 */
	@RequestMapping(value="/updatePass")
	public ModelAndView updatePass(Integer id,String passWord){
		UserInfo userInfo = sysMgrService.queryUserInfoById(id);
		String temppass =SHA256Utils.getSHA256Encryption(passWord+userInfo.getUserName());
		userInfo.setPassWord(temppass);
		int i = sysMgrService.updateUserInfo(userInfo);
		if(i>0){
			return ajaxDoneSuccess("重置成功");
		}else{
			return ajaxDoneError("操作失败");
		}
	}
	
	/**
	 * 商户注册推荐码管理
	 * @return
	 */
	@RequestMapping(value="/regCodeMgrList")
	public String regCodeMgrList(){
		PageInfo<RegCodeInfo> page =  merchantMgrService.queryRegCodeList(getCriteria());
		getRequest().setAttribute("page", page);
		return "merchantmgr/regCodeList";
	}
	
	/**
	 * 添加注册推荐码
	 * @return
	 */
	@RequestMapping(value="/goAddRegCode")
	public String goAddRegCode(){
		return "merchantmgr/addRegCode";
	}
	
	/**
	 * 添加推荐码
	 * @param info
	 * @return
	 */
	@RequestMapping(value="/excuAddRegCode")
	public ModelAndView excuAddRegCode(RegCodeInfo info){
		info.setCreateBy(getLogAccount().getUserName());
		int i = merchantMgrService.excuAddRegCode(info);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	/**
	 * 删除推荐码
	 * @param String[] ids
	 * @return page
	 * */
	@RequestMapping(value="/deleteRegCodeInfo")
	public ModelAndView deleteRegCodeInfo(String [] ids){
		int count = merchantMgrService.deleteRegCodeInfo(ids);
		return ajaxDoneSuccess("删除条数为：" + ids.length+" ,删除成功条数为：" + count);
	}
	
	/**
	 * 实现：终端号支付页面绑定
	 * @param vo 查询条件
	 * @return 终端号绑定的支付页面列表
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
	 * 实现：终端号支付页面绑定
	 * @param vo 查询条件
	 * @return 终端号绑定的支付页面列表
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
	 * 实现：保存终端号绑定的支付页面
	 * @param vo 绑定支付页面信息
	 * @return 保存结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	@RequestMapping(value="/saveMerchantPage")
	public ModelAndView saveMerchantPaymentPage(GwMerchantPaymentPage vo) {
		int a = merchantMgrService.saveMerchantPaymentPage(vo, getLogAccount());
		if(a > 0){
			return ajaxDoneSuccess("保存成功！");
		}else{
			return ajaxDoneError("保存失败！");
		}
	}
	/**
	 * 实现：删除终端号绑定的支付页面
	 * @param vo 绑定支付页面信息
	 * @return 删除结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	@RequestMapping(value="/deleteMerchantPage")
	public ModelAndView deleteMerchantPaymentPage(GwMerchantPaymentPage vo) {
		int a = merchantMgrService.deleteMerchantPaymentPage(vo);
		if(a > 0){
			return ajaxDoneSuccess("删除成功！");
		}else{
			return ajaxDoneError("删除失败！");
		}
	}
	
	/**
	 *品牌数据带回 
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
	 * mcc数据带回
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
	 *产品数据带回
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
	 * 商户通道绑定导出
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
		WritableSheet sheet = book.createSheet("绑定通道列表", 0);
		String[] headerName = { "商户号", "终端号","商户名称","银行名称","通道名称"
				,"英文账单","待切换通道","欧元区通道","是否自动切换","绑定卡种","开通状态","绑定时间","绑定人"};
		// 写入标题
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
			sheet.addCell( new Label(col++, row, info.getAutoChange()==0?"不切换":"切换"));
			sheet.addCell( new Label(col++, row, info.getCardType()));
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("AGENT_STATUS",String.valueOf(info.getEnabled()),String.valueOf(info.getEnabled()))));
			sheet.addCell( new Label(col++, row, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(info.getCreateDate())));
			sheet.addCell( new Label(col++, row, info.getCreateBy()));
		}
		book.write();
		book.close();
	}
	
	/**
	 * 跳转到选择要复制的商户终端
	 */
	@RequestMapping(value="/goCopyMerchantTerInfo")
	public String goCopyMerchantTerInfo(int id){
		MerchantTerInfo newTerInfo=merchantMgrDao.queryMerchantTerNoById(id);
		getRequest().setAttribute("newTerInfo", newTerInfo);
		return "merchantmgr/onekeytoopen/copyedMerchantInfo";
	}
	
	/**
	 * 复制终端信息到指定终端
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/copyMerchantTerInfo")
	@Transactional(rollbackFor=Exception.class)
	public ModelAndView copyMerchantTerInfo(CopyMerchantTerInfo info) throws ServiceException{
		String msg="";
		//更新终端信息
		MerchantTerInfo oldTerInfo=merchantMgrDao.queryTerInfoByMerNoAndTerNo(info.getMerNo(), info.getTerNo());
		oldTerInfo.setId(info.getId());
		oldTerInfo.setMerNo(info.getNewMerNo());
		oldTerInfo.setTerNo(info.getNewTerNo());
		oldTerInfo.setUpdateBy(getLogAccount().getRealName());
		int i=merchantMgrService.updateMerchantTerInfo(oldTerInfo);
		if(i>0){
			msg="更新终端信息成功<br>";
		}else{
			return ajaxDoneError("更新终端信息失败");
		}
		Criteria criteria=getCriteria();
		criteria.getCondition().put("merNo", info.getMerNo());
		criteria.getCondition().put("terNo", info.getTerNo());
		//插入商户费率
		i=0;
		PageInfo<RateInfo> oldRateInfoList=rateMgrService.getListRateInfo(criteria);
		for(RateInfo ri:oldRateInfoList.getData()){
			ri.setMerNo(info.getNewMerNo());
			ri.setTerNo(info.getNewTerNo());
			ri.setCreateBy(getLogAccount().getRealName());
			i+=rateMgrService.addRateInfo(ri);
		}
		msg+="插入了"+i+"条费率记录<br>";
		//插入商户通道绑定信息
		i=0;
		PageInfo<MerchantRelCurrencyInfo> mrcPage=merchantMgrService.getMerchnatRelCurrencyList(criteria);
		for(MerchantRelCurrencyInfo mrci:mrcPage.getData()){
			mrci.setMerNo(info.getNewMerNo());
			mrci.setTerNo(info.getNewTerNo());
			mrci.setCreateBy(getLogAccount().getRealName());
			i+=merchantMgrService.addCurrencyToMerchnat(mrci);
			
		}
		msg+="插入了"+i+"条商户通道绑定信息<br>";
		//插入商户限额
		PageInfo<GwTernoLmitInfo> gtpage=merchantMgrService.terLimitInfoList(criteria);
		i=0;
		for(GwTernoLmitInfo mrci:gtpage.getData()){
			mrci.setMerNo(info.getNewMerNo());
			mrci.setTerNo(info.getNewTerNo());
			mrci.setCreateby(getLogAccount().getRealName());
			i+=merchantMgrService.addTerLimit(mrci);
			
		}
		msg+="插入了"+i+"条商户限额<br>";
		//插入结算周期
		i=0;
		PageInfo<SettleTypeInfo> stipage=settleMgrService.listSettleTypeInfo(criteria);
		for(SettleTypeInfo mrci:stipage.getData()){
			mrci.setMerNo(info.getNewMerNo());
			mrci.setTerNo(info.getNewTerNo());
			mrci.setCreateBy(getLogAccount().getRealName());
			i+=settleMgrService.insertSettleTypeInfo(mrci);
			
		}
		msg+="插入了"+i+"条结算周期<br>";
		//插入绑定风控规则组
		i=0;
		PageInfo<MerchantRefRuleProfileInfo> result =  fraudManageService.queryPageAccountRefProfileList(criteria);
		for(MerchantRefRuleProfileInfo mrci:result.getData()){
			mrci.setMerNo(info.getNewMerNo());
			mrci.setTerNo(info.getNewTerNo());
			mrci.setCreateBy(getLogAccount().getRealName());
			i+=fraudManageService.addProfileToAccount(mrci);
					
		}
		msg+="插入了"+i+"条绑定风控规则组";
		return ajaxDoneSuccess(msg);
	}
	
	/**
	 * 跳转到选择要切换的商户终端
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
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	
	/**
	 * 显示商户绑定系统关闭网址
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
	 * 去修改商户网站信息
	*/
	@RequestMapping(value="/goSysUpdateMerchantWebsiteInfo")
	public String goUpdateMerchantWebsiteInfoPage(String id){
		MerchantWebsite merchantWebsite=merchantMgrService.querySysMerchantWebsitInfoById(id);
		getRequest().setAttribute("info", merchantWebsite);
		return "merchantmgr/sys/updateMerchantWebsiteInfo";
	}
	/**
	 * 导出
	 */
	@RequestMapping(value="/exportSysMerchantWebsite")
	public void exportSysMerchantWebsite(HttpServletResponse resp) throws IOException, RowsExceededException, WriteException{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "sysMerchantWebsiteInfo.xls" ); 
		List<MerchantWebsite> list = merchantMgrService.queryExportSysMerchantWebsiteInfo(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("系统关闭网址列表", 0);
		String[] headerName={"商户号","终端号","网址","MCC","品牌","产品","网站程序语言","网站程序","审核意见","审核状态","审批人","审批日期","备注","执行时间"};
		// 写入标题
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
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("MERCHANTWEBSITESTATUS",info.getStatus(),"未知状态")));
			sheet.addCell( new Label(col++, row ,info.getAppBy()));
			sheet.addCell( new Label(col++, row, info.getAppDate()));
			sheet.addCell( new Label(col++, row, info.getRemark()));
			sheet.addCell( new Label(col++, row, info.getSysOperatedDate()!=null?info.getSysOperatedDate():""));
		}
		book.write();
		book.close();
	}
	
	/**
	 * 商户拒付扣款限制
	 */
	@RequestMapping(value="/queryMerchantDisFineList")
	public String queryMerchantDisFineList(){
		Criteria criteria = getCriteria();
		PageInfo<MerchantDisFineInfo> page = merchantMgrService.queryMerchantDisFineList(criteria);
		getRequest().setAttribute("page", page);
		return "merchantmgr/merchantDisFineInfoList";
	}
	
	/**
	 * 跳转添加,修改商户拒付扣款限制信息
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
	 * 保存添加,修改商户拒付扣款限制信息
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
			return ajaxDoneSuccess("保存成功");
		}else{
			return ajaxDoneError("保存失败");
		}
	}
	
	/**
	 * 删除商户拒付扣款限制信息
	 */
	@RequestMapping(value="/delMerchantDisFineInfo")
	@ResponseBody
	public ModelAndView delMerchantDisFineInfo(MerchantDisFineInfo form){
		int a = merchantMgrService.deleteMerchantDisFineInfo(form);
		if(a>0){
			return ajaxDoneSuccess("删除成功");
		}else{
			return ajaxDoneError("删除失败");
		}
	}
	
	
	@RequestMapping("/goAddOrUpdateMerchantRefCurrencySpecial")
	public String goAddOrUpdateMerchantRefCurrencySpecial(String id){
		MerchantCurrencySpecialInfo info=merchantMgrService.queryMerchantCurrencySpecialInfoByMerchantCurrencyId(id);
		info.setMerchantCurrencyId(Integer.parseInt(id));
		//设置备用通道名
		String currencyDayAmountIds = info.getCurrencyDayAmountIds();
		if(null!=currencyDayAmountIds){
			String currencyDayAmountNames = currencyMapper.getCurrencyDayAmountNamesByIds(currencyDayAmountIds);
			info.setCurrencyDayAmountNames(currencyDayAmountNames);
		}
		getRequest().setAttribute("info", info);
		return "merchantmgr/updateMerchantRefCurrencySpecial";
	}
	
	
	
	/**
	 * 保存商户自动切换通道信息
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
			return ajaxDoneSuccess("操作成功");
		}else{
			return ajaxDoneError("操作失败");
		}
	}
	
	
	/**
	 * 查询商户国家通道数据列表
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
	 * 跳转添加，修改商户国家通道信息
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
	 * 保存添加修改商户国家通道信息
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
			return ajaxDoneSuccess("保存成功");
		}else{
			return ajaxDoneError("保存失败");
		}
	}
	
	/**
	 * 删除商户国家通道信息
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
			return ajaxDoneSuccess("删除成功");
		}else{
			return ajaxDoneError("删除失败");
		}
	}
	
	/**
	 * 查询商户国家通道数据LOG列表
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
	 * 查询所有商户号,终端号信息
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
				mer.setMerchantName("所有");
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
				mer.setTerName("所有");
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
				merType.setTypeName("所有");
				list.add(0, merType);
				request.setAttribute("page", page);
				return "merchantmgr/merchantType_look_up";
			}
		}
		return null;
	}
	
	/**
	 * 品牌信息查找带回
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
	 * 国家信息查找带回
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
	 * 通道信息多条带回
	 */
	@RequestMapping("/getCurrencyListBrightBackMany")
	public String getCurrencyListBrightBackMany(@RequestParam(name="currencyIds",required=false) String currencyIds){
		Criteria criteria=getCriteria();
		Integer pageNum= criteria.getPageNum();
		Integer pageSize=criteria.getPageSize();
		//取消分页查询(查询所以数据)，但是要保留分页页码和每页显示数据
		criteria.setPageNum(1);;
		criteria.setPageSize(Integer.MAX_VALUE);
		PageInfo<CurrencyInfo> pageData = bankMgrService.getCurrencyList(criteria);
		//重组分页数据
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
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
		
	}
	
	
	@RequestMapping("/goQureyManyWebsite")
	public String goQureyManyWebsite(){
		return "merchantmgr/qureyManyWebsite";
	}
	
	/** 批量上传查询网站信息 */
	@ResponseBody
	@RequestMapping(value="/qureyManyWebsite")
	public String qureyManyWebsite(DefaultMultipartHttpServletRequest request,HttpServletResponse res)throws IOException, ParseException, ISOException{
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		res.setContentType("text/html");
		List<String> list=new ArrayList<String>();
		StringBuffer sb=new StringBuffer();
		if(null != files){
			log.info("上传了"+files.size()+"个文件。");
			for(MultipartFile file :files){
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8"));//构造一个BufferedReader类来读取文件
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
		
		//return ajaxDoneSuccess("上传成功，上传了"+list.size()+"条记录");
	}
	
	
	
	
}