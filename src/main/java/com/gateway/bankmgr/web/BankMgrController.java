package com.gateway.bankmgr.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.jpos.iso.ISOUtil;
import org.jpos.security.SecureDESKey;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.gateway.bankmgr.model.BankConfigInfo;
import com.gateway.bankmgr.model.BankInfo;
import com.gateway.bankmgr.model.CurrencyConfigInfo;
import com.gateway.bankmgr.model.CurrencyInfo;
import com.gateway.bankmgr.model.GwPaymentPage;
import com.gateway.bankmgr.model.MasteKey;
import com.gateway.bankmgr.service.BankMgrService;
import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.ServiceException;
import com.gateway.common.utils.Sjj1127SecurityModule;

@Controller
@RequestMapping(value="/bankMgr")
public class BankMgrController extends BaseController{
	
	@Resource
	private BankMgrService bankMgrService;
	
	/**
	 * 获取银行列表
	 * @return
	 */
	@RequestMapping(value="/getBankList")
	public String getBankList(){
		PageInfo<BankInfo> page = bankMgrService.getBankList(getCriteria());
		getRequest().setAttribute("page", page);
		return "bankmgr/bankList";
	}
	
	/**
	 * 跳转添加银行信息页面
	 * @return
	 */
	@RequestMapping(value="/goAddBankInfo")
	public String goAddBankInfo(){
		return "bankmgr/addBankInfo";
	}
	
	/**
	 * 添加银行信息
	 * @param bankInfo
	 * @return
	 */
	@RequestMapping(value="/addBankInfo")
	public ModelAndView addBankInfo(BankInfo bankInfo){
		bankInfo.setCreateBy(getLogAccount().getRealName());
		int i = bankMgrService.addBankInfo(bankInfo);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneSuccess("添加失败");
		}
	}
	
	/**
	 * 跳转修改银行页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/goUpdateBankInfo")
	public String goUpdateBankInfo(int id){
		BankInfo bankInfo=bankMgrService.queryBankInfoById(id);
		getRequest().setAttribute("bankInfo", bankInfo);
		return "bankmgr/updateBankInfo";
	}
	
	/**
	 * 修改银行
	 * @param bankInfo
	 * @return
	 */
	@RequestMapping(value="/updateBankInfo")
	public ModelAndView updateBankInfo(BankInfo bankInfo){
		bankInfo.setCreateBy(getLogAccount().getRealName());
		int i = bankMgrService.updateBankInfo(bankInfo);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneSuccess("添加失败");
		}
	}
	
	
	/**
	 * 
	 * 密钥查询
	 */
	
	@RequestMapping(value="/getkeyValueList")
	public String getkeyValueList(){
		PageInfo<CurrencyInfo> page=bankMgrService.getKeyValueList(getCriteria());
		getRequest().setAttribute("page", page);
		return "bankmgr/keyValueList";
	}
	
	
	/**
	 * 通道查找带回
	 * @return
	 */
	@RequestMapping(value="/getCurrencyListBrightBack")
	public String getCurrencyListBrightBack(){
		PageInfo<CurrencyInfo> page = bankMgrService.getCurrencyList(getCriteria());
		getRequest().setAttribute("page", page);
		return "bankmgr/currencyListBrighBack";
	}
	
	/**
	 * 银行查找带回
	 * @return
	 */
	@RequestMapping(value="/getBankListbrighBack")
	public String getBankListbrighBack(){
		PageInfo<BankInfo> page = bankMgrService.getBankList(getCriteria());
		getRequest().setAttribute("page", page);
		return "bankmgr/bankListBrightBack";
	}
	
	/**
	 * 查询通道
	 * @return
	 */
	@RequestMapping(value="/getCurrencyList")
	public String getCurrencyList(){
		PageInfo<CurrencyInfo> page = bankMgrService.getCurrencyList(getCriteria());
		getRequest().setAttribute("page", page);
		return "bankmgr/currencyList";
	}

	
	/**
	 * 添加渠道信息页面
	 * @return
	 */
	@RequestMapping(value="/goAddCurrencyInfo")
	public String goAddCurrencyInfo(){
		
		return "bankmgr/addCurrencyInfo";
	}
	
	/**
	 * 添加通道信息
	 * @param currencyInfo
	 * @return
	 */
	@RequestMapping(value="/addCurrencyInfo")
	public ModelAndView addCurrencyInfo(CurrencyInfo currencyInfo)throws ServiceException{
		currencyInfo.setCreateBy(getLogAccount().getRealName());
		int i=bankMgrService.addCurrencyInfo(currencyInfo);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneSuccess("添加失败");
		}
	}
	
	/**
	 * 跳转配置页面
	 * @return
	 */
	@RequestMapping(value="/goCurrencyConfigInfo")
	public String goCurrencyConfigInfo(int id){
		getRequest().setAttribute("currencyId", id);
		return "bankmgr/addCurrencyConfigInfo";
	}
	
	/**
	 * 添加配置
	 * 
	 */
	@RequestMapping(value="/addCurrencyConfigInfo")
	public ModelAndView currencyConfigInfo(CurrencyConfigInfo configInfo){
		configInfo.setCreateBy(getLogAccount().getRealName());
		int i=bankMgrService.addCurrencyConfigInfo(configInfo);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneSuccess("添加失败");
		}
	}
	
	/**
	 * 查看配置信息
	 * @return
	 */
	@RequestMapping(value="/queryCurrencyConfig")
	public String queryCurrencyConfig(int currencyId){
		List<CurrencyConfigInfo> list=bankMgrService.queryCurrencyConfig(currencyId);
		getRequest().setAttribute("list", list);
		return "bankmgr/queryCurrencyConfigInfo";
	}
	
	/**
	 * 跳转修改配置信息页面
	 * @param currencyId
	 * @return
	 */
	@RequestMapping(value="/goUpdateCurrencyConfig")
	public String goUpdateCurrencyConfig(int id){
		CurrencyConfigInfo configInfo=bankMgrService.queryCurrencyConfigByID(id);
		getRequest().setAttribute("configInfo", configInfo);
		return "bankmgr/updateCurrencyConfigInfo";
	}
	
	/**
	 * 修改配置信息
	 * @return
	 */
	@RequestMapping(value="/updateCurrencyConfig")
	public ModelAndView updateCurrencyConfig(CurrencyConfigInfo configInfo){
		configInfo.setCreateBy(getLogAccount().getRealName());
		int i=bankMgrService.updateCurrencyConfigInfo(configInfo);
		if(i>0){
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneSuccess("修改失败");
		}
	}
	
	/**
	 * 删除配置信息
	 * @return
	 */
	@RequestMapping(value="/deleteCurrencyConfig")
	public ModelAndView deleteCurrencyConfig(int id){
		int i=bankMgrService.deleteCurrencyConfig(id);
		if(i>0){
			return ajaxDoneSuccess("删除成功");
		}else{
			return ajaxDoneSuccess("删除失败");
		}
	}
	
	
	
	
	
	
	/**
	 * 跳转修改通道配置页面
	 * @param currencyId
	 * @return
	 */
	@RequestMapping(value="/goUpdateCurrencyInfo")
	public String goUpdateCurrencyInfo(int currencyId){
		CurrencyInfo currencyInfo=bankMgrService.queryCurrencyInfoById(currencyId);
		getRequest().setAttribute("currencyInfo", currencyInfo);
		return "bankmgr/updateCurrencyInfo";
	}
	
	/**
	 * 修改通道配置
	 * @param currencyInfo
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/updateCurrencyInfo")
	public ModelAndView updateCurrencyInfo(CurrencyInfo currencyInfo) throws ServiceException{
		currencyInfo.setCreateBy(getLogAccount().getRealName());
		int i=bankMgrService.updateCurrencyInfo(currencyInfo);
		if(i>0){
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneSuccess("修改失败");
		}
	}
	
	/**
	 * 查询通道历史记录
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/queryCurrencyListLog")
	public String queryCurrencyListLog(int id){
		List<CurrencyInfo> list=bankMgrService.queryCurrencyListLog(id);
		getRequest().setAttribute("list", list);
		return "bankmgr/queryCurrencyConfigInfoLog";
	}
	
	/**
	 * 跳转添加银行配置页面
	 * @return
	 */
	@RequestMapping(value="/goAddBankConfigInfo")
	public String goAddBankConfigInfo(int id){
		getRequest().setAttribute("bankId", id);
		return "bankmgr/addBankConfigInfo";
	}
	
	/**
	 * 添加银行配置
	 * @param configInfo
	 * @return
	 */
	@RequestMapping(value="/addBankConfigInfo")
	public ModelAndView addBankConfigInfo(BankConfigInfo configInfo){
		configInfo.setCreateBy(getLogAccount().getRealName());
		int i=bankMgrService.addBankConfigInfo(configInfo);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneSuccess("添加失败");
		}
	}
	
	/**
	 * 查看银行配置
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/queryBankConfigList")
	public String queryBankConfigList(int id){
		List<BankConfigInfo> list=bankMgrService.queryBankConfigList(id);
		getRequest().setAttribute("list", list);
		return "bankmgr/queryBankConfigInfoLog";
	}
	
	/**
	 * 跳转修改银行配置页面
	 * @return
	 */
	@RequestMapping(value="/goUpdateBankConfig")
	public String goUpdateBankConfig(int id){
		BankConfigInfo configInfo=bankMgrService.queryBankConfig(id);
		getRequest().setAttribute("configInfo", configInfo);
		return "bankmgr/updateBankConfigInfo";
	}
	
	/**
	 * 修改银行配置
	 * @param bankConfigInfo
	 * @return
	 */
	@RequestMapping(value="/updateBankConfig")
	public ModelAndView updateBankConfig(BankConfigInfo bankConfigInfo){
		bankConfigInfo.setCreateBy(getLogAccount().getRealName());
		int i=bankMgrService.updateBankConfig(bankConfigInfo);
		if(i>0){
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneSuccess("修改失败");
		}
	}
	
	/**
	 * 删除银行配置
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/deleteBankConfig")
	public ModelAndView deleteBankConfig(int id){
		int i=bankMgrService.deleteBankConfig(id);
		if(i>0){
			return ajaxDoneSuccess("删除成功");
		}else{
			return ajaxDoneSuccess("删除失败");
		}
	}
	
	
	/**
	 * 主密钥查询
	 * @return
	 */
	@RequestMapping(value="/queryMasteKey")
	public String queryMasteKey(){
		PageInfo<MasteKey> page=bankMgrService.queryMasteKeyList(getCriteria());
		getRequest().setAttribute("page", page);
		return "bankmgr/masteKeyList";
	}
	
	/**
	 * 跳转添加主密钥报文页面
	 * @return
	 */
	@RequestMapping(value="/goAcceptTerSn")
	public String goAcceptTerSn(){
		return "bankmgr/uploadKeyCheckFile";
	}
	
	
	/**
	 * 导入SN终端
	 * @return
	 */
	@RequestMapping(value="/acceptTerSn")
	public ModelAndView acceptTerSn(DefaultMultipartHttpServletRequest request) throws IOException{
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		List<MasteKey> list=new ArrayList<MasteKey>();
		if(null!=files){
			for(MultipartFile file:files){
				try{
				      BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));//构造一个BufferedReader类来读取文件
				      String s = null;
				      int i = 0;
				      while((s = br.readLine())!=null){//使用readLine方法，一次读一行
				    	  if(0==i){
				    		  i++;
				    		  continue;
				    	  }
				    	  String[] str=s.trim().split(",");
				    	  if(str.length==4){
				    		  MasteKey masteKey=new MasteKey();
				    		  masteKey.setStatus("0");
				    		  masteKey.setTersn(str[0]);
				    		  masteKey.setKey_index(str[1]);
				    		  masteKey.setBrand(str[2]);
				    		  masteKey.setType(str[3]);
				    		  masteKey.setSncreate(getLogAccount().getRealName());
				    		  if(null==bankMgrService.queryMasteKeyBySn(str[0])){
				    			  list.add(masteKey);
				    		  }
				    	  }else{
				    		  continue;
				    	  }
		              }
				     br.close();
		          }catch(Exception e){
		             e.printStackTrace();
		          }
			  }
		}
		int i = 0;
		if(list.size()>0){
			
			i=bankMgrService.saveMasteKey(list);
		}
		if(i>0){
			return ajaxDoneSuccess("添加成功，添加记录数："+i);
		}else{
			return ajaxDoneError("添加失败,请检查文件格式或者是否是重复的文件。");
		}
	}
	
	
	
	/**
	 *导出交易 
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	@RequestMapping(value="/exportMasteKey")
	public void exportMasteKey(HttpServletResponse resp) throws IOException, RowsExceededException, WriteException{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "masteKey.xls" ); 
		List<MasteKey> list = bankMgrService.exporMasteKey(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("主密钥查询导出", 0);
		String[] headerName = { "终端SN号", "主密钥密文","校验值","SN号上传时间","SN号上传人","传输秘钥索引","型号","品牌","秘钥导出时间","秘钥导出人"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			MasteKey info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getTersn()));
			//调用加密机生成主密钥索引
			if(null == info.getKey_content()){
				Sjj1127SecurityModule module = new Sjj1127SecurityModule();
				SecureDESKey key = module.generateKeyImpl((short) 128, "TMK");
				log.info("主密钥："+ISOUtil.hexString(key.getKeyBytes()));
				log.info("校验值："+ISOUtil.hexString(key.getKeyCheckValue()));
				//从主密钥加密的key加密到传输秘钥加密
				key = module.translateKey(
						"TMK", 
						"", 
						info.getKey_index(), 
						"FFFF", 
						"01", 
						null, 
						null, 
						key);
				log.info("转加密之后主密钥："+ISOUtil.hexString(key.getKeyBytes()));
				log.info("转加密之后校验值："+ISOUtil.hexString(key.getKeyCheckValue()));
				info.setKey_content(ISOUtil.hexString(key.getKeyBytes()));
				info.setCheck_value(ISOUtil.hexString(key.getKeyCheckValue()));
				info.setKey_person(getLogAccount().getUserName());
				info.setKey_expdate(new Timestamp(System.currentTimeMillis()));
				bankMgrService.updateMasteKeyBySn(info);
			}
			sheet.addCell( new Label(col++, row, info.getKey_content()));
			sheet.addCell( new Label(col++, row, info.getCheck_value()));
			sheet.addCell( new Label(col++, row, info.getSndate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getSndate()):""));
			sheet.addCell( new Label(col++, row, info.getSncreate()));
			sheet.addCell( new Label(col++, row, info.getKey_index()));
			sheet.addCell( new Label(col++, row, info.getType()));
			sheet.addCell( new Label(col++, row, info.getBrand()));
			sheet.addCell( new Label(col++, row, info.getKey_expdate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getKey_expdate()):""));
			sheet.addCell( new Label(col++, row, info.getKey_person()));
		}
		book.write();
		book.close();
	}
	/**
	 * 实现：查询支付页面管理列表
	 * @param vo 查询条件
	 * @return 支付页面列表
	 * @date 2015-09-12
	 * @author YWP
	 */
	@RequestMapping(value="/searchPaymentPage")
	public String searchMerchantPaymentPage() {
		PageInfo<GwPaymentPage> page = bankMgrService.searchPaymentPage(getCriteria());
		getRequest().setAttribute("page", page);
		return "bankmgr/SearchPaymentPage";
	}
	/**
	 * 实现：查询选择支付页面列表
	 * @param vo 查询条件
	 * @return 支付页面列表
	 * @date 2015-09-12
	 * @author YWP
	 */
	@RequestMapping(value="/selectPaymentPage")
	public String selectPaymentPage() {
		PageInfo<GwPaymentPage> page = bankMgrService.searchPaymentPage(getCriteria());
		getRequest().setAttribute("page", page);
		return "bankmgr/SelectPaymentPage";
	}
	/**
	 * 实现：跳转到支付页面新增页面
	 * @param vo 支付页面ID
	 * @return 新增支付页面
	 * @date 2015-09-12
	 * @author YWP
	 */
	@RequestMapping(value="/toAddPage")
	public String toAddPage(GwPaymentPage vo) {
		GwPaymentPage info = new GwPaymentPage();
		if(vo.getId()>0) {
			info = bankMgrService.searchPaymentPageById(vo);
		}
		getRequest().setAttribute("form", info);
		return "bankmgr/AddPaymentPage";
	}
	/**
	 * 实现：保存支付页面的内容
	 * @param vo 支付页面信息
	 * @return 保存结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	@RequestMapping(value="/savePaymentPage")
	public ModelAndView savePaymentPage(GwPaymentPage vo) {
		int a = bankMgrService.savePaymentPage(vo, getLogAccount());
		if(a > 0){
			return ajaxDoneSuccess("保存成功！");
		}else{
			return ajaxDoneError("保存失败！");
		}
	}
	/**
	 * 实现：删除支付页面
	 * @param vo 支付页面信息
	 * @return 删除结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	@RequestMapping(value="/deletePaymentPage")
	public ModelAndView deletePaymentPage(GwPaymentPage vo) {
		int a = bankMgrService.deletePaymentPage(vo);
		if(a > 0){
			return ajaxDoneSuccess("删除成功！");
		}else{
			return ajaxDoneError("删除失败！");
		}
	}
	
	public BankMgrService getBankMgrService() {
		return bankMgrService;
	}
	public void setBankMgrService(BankMgrService bankMgrService) {
		this.bankMgrService = bankMgrService;
	}
}