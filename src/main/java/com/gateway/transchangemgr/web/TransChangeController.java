package com.gateway.transchangemgr.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jpos.iso.ISOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.BaseDataListener;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.ServiceException;
import com.gateway.common.utils.Funcs;
import com.gateway.common.utils.PaymentConfig;
import com.gateway.common.utils.StringUtils;
import com.gateway.common.utils.Tools;
import com.gateway.sysmgr.model.CardBinInfo;
import com.gateway.transchangemgr.model.ReRunTransModel;
import com.gateway.transchangemgr.model.TransCheckInfo;
import com.gateway.transchangemgr.service.TransChangeService;
import com.gateway.transmgr.model.TransDetailInfo;
import com.gateway.transmgr.model.TransInfo;
import com.gateway.transmgr.service.TransMgrService;

@Controller
@RequestMapping(value = "/transchangemgr")
public class TransChangeController extends BaseController {
	@Autowired
	private TransChangeService transChangeServiceImpl;
	@Resource
	private TransMgrService transMgrService;
	/**
	 * 列表显示勾兑上传记录
	 * */
	@RequestMapping(value = "/listCheckTrans")
	public String listCheckTrans() {
		PageInfo<TransCheckInfo> page = transChangeServiceImpl
				.queryCheckTransInfo(getCriteria());
		getRequest().setAttribute("page", page);
		return "transchangemgr/listCheckTrans";
	}

	/**
	 * 勾兑文件上传
	 * 
	 * @return
	 */
	@RequestMapping(value = "/uploadCheckFile")
	public String uploadCheckFile() {
		return "transchangemgr/uploadCheckFile";
	}

	/**
	 * 接收勾兑文件
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws ISOException
	 */
	@RequestMapping(value = "/acceptCheckFile")
	public ModelAndView acceptCheckFile(
			DefaultMultipartHttpServletRequest request) throws IOException,
			ParseException, ISOException {
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		if (null != files) {
			log.info("上传了" + files.size() + "个文件。");
			final List<TransCheckInfo> transChecked = new ArrayList<TransCheckInfo>();
			for (MultipartFile file : files) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						file.getInputStream()));// 构造一个BufferedReader类来读取文件
				String line = br.readLine();
				while (null != line) {
					log.info("line:" + line);
					String[] fields = line.split(",");
					if (fields.length == 4) {
						int index = 0;
						TransCheckInfo info = new TransCheckInfo();
						info.setUpdateBy(getLogAccount().getRealName());
						info.setTradeNo(fields[index++]);
						info.setRelNo(fields[index++]);
						info.setCurrency(fields[index++]);
						info.setAmount(fields[index++]);
						transChecked.add(info);
					} else {
						log.info("错误line:" + line);
					}
					line = br.readLine();
				}
			}
			// 保存对账记录
			if (transChecked.size() > 0) {
				new Thread(new Runnable() {
					public void run() {
						int count = transChangeServiceImpl
								.saveTransCheckDetail(transChecked);
						log.info("更新记录数：" + count);
					}
				}).start();
			}

		}
		return ajaxDoneSuccess("上传成功。");
	}

	/**
	 * 跳转到复核异常勾兑页面
	 * 
	 * @throws ServiceException
	 * */
	@RequestMapping(value = "/goUpdateTransChecked")
	public String goUpdateTransChecked(String id) throws ServiceException {
		TransCheckInfo transCheckInfo = transChangeServiceImpl
				.queryCheckTransInfoById(id);
		if (null != transCheckInfo && transCheckInfo.getStatus() != 2) {
			throw new ServiceException("请选择勾兑异常记录");
		}
		getRequest().setAttribute("info", transCheckInfo);
		return "transchangemgr/updateTransChecked";
	}

	/**
	 * 复核勾兑记录
	 * 
	 * @throws ServiceException
	 * */
	@RequestMapping(value = "/updateTransChecked")
	public ModelAndView updateTransChecked(TransCheckInfo transCheckInfo)
			throws ServiceException {
		transCheckInfo.setLastModifyBy(getLogAccount().getRealName());
		int i = transChangeServiceImpl.updateTransChecked(transCheckInfo);
		if (i > 0) {
			return ajaxDoneSuccess("复核成功");
		} else {
			return ajaxDoneError("复核失败");
		}
	}

	/**
	 * 
	 * 跳转交易匹配导出
	 **/
	@RequestMapping(value = "/goExportTransInfo")
	public String goExportTransInfo() {
		return "transchangemgr/transExport/transExport";
	}
	
	/** 交易导出下载上传模板 */
	@RequestMapping(value = "/downloadTransModel")
	public void downloadTransModel(HttpServletResponse resp,String type){
		if("1".equals(type)){
			ArrayList<String> strArray = new ArrayList<String> ();
			strArray.add("流水号");
			strArray.add("BR1506161604560740");
			strArray.add("BR1506161604560741");
			downloadFile(resp, "transModel.txt", "modelType:tradeNo", strArray);
			return;
		}
		if("2".equals(type)){
			ArrayList<String> strArray = new ArrayList<String> ();
			strArray.add("订单号,交易日期");
			strArray.add("199227530825,20150808");
			strArray.add("199227530825,20150808");
			downloadFile(resp, "transModel.txt", "modelType:orderNo", strArray);
			return;
		}
		if("3".equals(type)){
			ArrayList<String> strArray = new ArrayList<String> ();
			strArray.add("前六后四卡号,交易日期,银行交易金额,授权码");
			strArray.add("123456****1234,20150808,6532");
			strArray.add("123456****1234,20150808,3214");
			downloadFile(resp, "transModel.txt", "modelType:cardInfo", strArray);
			return;
		}
		if("4".equals(type)){
			ArrayList<String> strArray = new ArrayList<String> ();
			strArray.add("全卡号,授权码");
			strArray.add("1234569876541234,6532");
			strArray.add("1234569876541234,3214");
			downloadFile(resp, "transModel.txt", "modelType:cardNo", strArray);
		}
		if("5".equals(type)){
			ArrayList<String> strArray = new ArrayList<String> ();
			strArray.add("商户号码,交易授权号,交易日期");
			strArray.add("021823160,653211,20150903");
			strArray.add("021823160,653211,20150903");
			downloadFile(resp, "transModel.txt", "modelType:ksDis", strArray);
		}
	}

	/**
	 * 交易匹配导出
	 * @throws Exception
	 */
	@RequestMapping(value ="/queryTransInfoList")
	public ModelAndView queryTransInfoList(DefaultMultipartHttpServletRequest request) throws Exception,
			WriteException {
		List<TransInfo> list = new ArrayList<TransInfo>();
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		StringBuffer sb = new StringBuffer();
		StringBuffer sbr = new StringBuffer();
		if (null != files) {
			log.info("上传了" + files.size() + "个文件。");
			Criteria criteria = new Criteria();
			for (MultipartFile file : files) {
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(),"GBK"));//
				// 构造一个BufferedReader类来读取文件
				String line = br.readLine();//第一列标题
				int length = Tools.replaceBlank(line).length();
				log.info("第一列：line:"+line+"字符长度为："+length);
				String [] strInfo = line.split(":");
				String name = "";
				if(!org.springframework.util.StringUtils.isEmpty(strInfo) || 2 == strInfo.length){
					name = strInfo[1];
				}
				line = br.readLine();//读取第二列
				line = br.readLine();//读取第3列
				int lineCount = 3;//从第二行开始读
				while (null != line) {
					log.info("line:" + line);
//					if(!line.contains(",")){
//						line = br.readLine();
//						continue;
//					}
					if(line==null||"".equals(line.trim())){
						line = br.readLine();
						continue;
					}
					String[] fileds = line.split(",");
					if ("tradeNo".equals(name)) {
						criteria.getCondition().put("tradeNo", line.trim());
						List<TransInfo> transInfo = transChangeServiceImpl.queryTransInfo(criteria);
						if(!org.springframework.util.StringUtils.isEmpty(transInfo)&& 0 != transInfo.size()) {
							for(TransInfo li:transInfo){
								list.add(li);
								sbr.append(li.getTradeNo()+",");
							}
						}else{
							sb.append("第"+lineCount+ "行数据：" +line+";");
						}
					}
					if ("orderNo".equals(name)) {
						int index = 0;
						criteria.getCondition().put("orderNo", fileds[index++]);
						criteria.getCondition().put("transDate", fileds[index++]);
						List<TransInfo> transInfo = transChangeServiceImpl.queryTransInfo(criteria);
						if(!org.springframework.util.StringUtils.isEmpty(transInfo)&& 0 != transInfo.size()) {
							for(TransInfo li:transInfo){
								list.add(li);
								sbr.append(li.getTradeNo()+",");
							}
						}else{
							sb.append("第"+lineCount+ "行数据：" + line+";");
						}
					}
					if ("cardInfo".equals(name)) {
						int index = 0;
						String[] str = fileds[index++].split("\\u002A");
						String[] no = new String[2];
						int i = 0;
						for(String s:str){
							if(!StringUtils.isEmpty(s)){
								no[i] = s;
								i++;
							}
						}
						criteria.getCondition().put("checkNo", Funcs.encrypt(no[0]));//前6位
						criteria.getCondition().put("last", Funcs.encrypt(no[1]));//后4位
						criteria.getCondition().put("transDate", fileds[index++]);
						if(3 == fileds.length){
							criteria.getCondition().put("autoCode", fileds[index++]);//授权码
						}
						List<TransInfo> transInfo = transChangeServiceImpl.queryTransInfo(criteria);
						if(!org.springframework.util.StringUtils.isEmpty(transInfo)&& 0 != transInfo.size()) {
							for(TransInfo li:transInfo){
								list.add(li);
								sbr.append(li.getTradeNo()+",");
							}
						}else{
							sb.append("第"+lineCount+ "行数据：" +line+";");
						}
					}
					if ("cardNo".equals(name)) {
						int index = 0;
						criteria.getCondition().put("checkToNo", Funcs.eccryptSHA(fileds[index++]));//全卡号
//						criteria.getCondition().put("transDate", fileds[index++]);//交易时间
//						if(3 == fileds.length){
							criteria.getCondition().put("autoCode", fileds[index++]);//授权码
//						}
						List<TransInfo> transInfo = transChangeServiceImpl.queryTransInfo(criteria);
						if(!org.springframework.util.StringUtils.isEmpty(transInfo)&& 0 != transInfo.size()) {
							for(TransInfo li:transInfo){
								list.add(li);
								sbr.append(li.getTradeNo()+",");
							}
						}else{
							sb.append("第"+lineCount+ "行数据：" +line+";");
						}
					}
					if("ksDis".equals(name)){
						int index = 0;
						criteria.getCondition().put("merchantNo", fileds[index++]);//商户号码
						criteria.getCondition().put("autoCode", fileds[index++]);//授权号码
						criteria.getCondition().put("transDate", fileds[index++]);//交易时间
						List<TransInfo> transInfo = transChangeServiceImpl.queryTransInfo(criteria);
						if(!org.springframework.util.StringUtils.isEmpty(transInfo)&& 0 != transInfo.size()) {
							for(TransInfo li:transInfo){
								list.add(li);
								sbr.append(li.getTradeNo()+",");
							}
						}else{
							sb.append("第"+lineCount+ "行数据：" +line+";");
						}
					}
					line = br.readLine();
					lineCount++;
				}
			}

		}
		getRequest().getSession().setAttribute("list", list);
		getRequest().getSession().setAttribute("sb", sb + "");
		getRequest().getSession().setAttribute("sbr", sbr + "");
		return ajaxDoneSuccess("上传成功。返回信息");
	}
	
	@RequestMapping(value ="/goQueryTransInfoList")
	public String goQueryTransInfoList(){
		@SuppressWarnings("unchecked")
		List<TransInfo> list = (List<TransInfo>)getRequest().getSession().getAttribute("list");
		getRequest().getSession().removeAttribute("list");
		String sb = (String)getRequest().getSession().getAttribute("sb");
		getRequest().getSession().removeAttribute("sb");
		String sbr = (String)getRequest().getSession().getAttribute("sbr");
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("sb", sb);
		getRequest().setAttribute("sbr", sbr);
		return "transchangemgr/transExport/transExportList";
	}
	
	/** 去显示未查出数据页面 */
	@RequestMapping(value ="/transExportData")
	public String transExportData(String sb){
		getRequest().setAttribute("sb", sb);
		return "transchangemgr/transExport/transExportData";
	}

	@RequestMapping(value = "/exportTransInfo")
	public void exportTransInfo(HttpServletResponse resp) throws Exception, IOException,
			RowsExceededException, WriteException {
		String sbr=(String) getRequest().getSession().getAttribute("sbr");
		String[] s = sbr.split(",");
		List<String> tradeNos=new ArrayList<String>();
		for(String tradeNo:s){
			if(StringUtils.isNotEmpty(tradeNo)){
				tradeNos.add(tradeNo);
			}
		}
		List<TransDetailInfo> list = transMgrService.queryTransInfoByTradeNos(tradeNos);
		OutputStream os=resp.getOutputStream();
		resp.reset(); // 来清除首部的空白行
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "transLogList.xls");
		WritableWorkbook book = Workbook.createWorkbook(os);
		WritableSheet sheet = book.createSheet("交易列表", 0);
		String[] headerName = { "商户号","所属终端号","流水号","订单号","交易时间","标签金额","银行交易金额","结算金额","手续费","单笔手续费","保证金","卡种","网站","交易状态",
				"交易返回信息","欺诈分数","支付通道","勾兑状态","入账状态","拒付状态","拒付金额","退款状态","退款金额","冻结状态","冻结金额","伪冒状态",
				"订单来源","通道英文账单名称","前六后四卡号","货物信息","持卡人姓名","收货人姓名","邮箱","持卡人电话","收货人电话","IP","支付国家","发卡行国家","收货国家","收货省/ 州",
				"收货地址","邮编","货运方式","货运单号","账单国家","账单省/州","账单地址","异常时间","是否全额退款" ,"授权号","银行退款金额","CPD时间","是否伪冒","发票号"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		if (!org.springframework.util.StringUtils.isEmpty(list)) {
			
			for (int row = 1; row <= list.size(); row++) {
				
				
				int col = 0;
				TransDetailInfo transInfo = list.get(row - 1);
				
				BigDecimal rate =new BigDecimal("0");
				CardBinInfo cb=new CardBinInfo();
				try {
					rate= new BigDecimal(transInfo.getBankTransAmount()).divide(new BigDecimal(transInfo.getMerTransAmount()),6,BigDecimal.ROUND_HALF_UP);
					cb=transMgrService.queryCardBinInfoByBin(Funcs.decrypt(transInfo.getCheckNo()));
				} catch (Exception e) {
				}
				if(null==cb){
					cb=new CardBinInfo();
				}
				transInfo.setTransRate(rate.toString());
				transInfo.setWebInfo(Tools.parseWebInfoToResourceType(transInfo.getWebInfo()));
				String refundStatusTemp="1".equals(transInfo.getRefundStatus())?"已退款":"未退款";
				transInfo.setRefundStatus(refundStatusTemp);
				String dishonorStatusTemp="1".equals(transInfo.getDishonorStatus())?"已拒付":"未拒付";
				transInfo.setDishonorStatus(dishonorStatusTemp);
				String frozenStatusTemp="1".equals(transInfo.getFrozenStatus())?"已冻结":"未冻结";
				transInfo.setFrozenStatus(frozenStatusTemp);
				if(transInfo.getCheckNo()!=null&&!"".equals(transInfo.getCheckNo())){
					transInfo.setSixAndFourCardNo(Funcs.decrypt(transInfo.getCheckNo())+"***"+Funcs.decrypt(transInfo.getLast()));
				}else{
					transInfo.setSixAndFourCardNo("");
				}
				if(transInfo.getCheckNo()!=null&&!"".equals(transInfo.getCheckNo())){
					transInfo.setCardFullNo(Funcs.decrypt(transInfo.getCheckNo())+"***"+Funcs.decrypt(transInfo.getLast()));
					}else{
						transInfo.setCardFullNo("");
					}
				
				
				sheet.addCell( new Label(col++, row, transInfo.getMerNo()));//商户号
				sheet.addCell( new Label(col++, row, transInfo.getTerNo()));//终端号
				sheet.addCell( new Label(col++, row, transInfo.getTradeNo()));//交易流水号
				sheet.addCell( new Label(col++, row, transInfo.getOrderNo()));//订单号
				sheet.addCell( new Label(col++, row, transInfo.getTransDate()));//交易时间
				sheet.addCell( new Label(col++, row, transInfo.getMerBusCurrency() + " " + transInfo.getMerTransAmount()));//交易金额
				sheet.addCell( new Label(col++, row, transInfo.getBankCurrency() + " " + transInfo.getBankTransAmount()));//结算金额
				sheet.addCell( new Label(col++, row, transInfo.getMerSettleCurrency() + " " + transInfo.getMerSettleAmount()));//结算金额
				sheet.addCell( new Label(col++, row, transInfo.getMerSettleCurrency() + " " + transInfo.getMerForAmount()));//手续费
				sheet.addCell( new Label(col++, row, transInfo.getMerSettleCurrency() + " " + transInfo.getSingleFee()));//单笔手续费
				sheet.addCell( new Label(col++, row, transInfo.getMerSettleCurrency() + " " + transInfo.getBondAmount()));//保证金
				sheet.addCell( new Label(col++, row, transInfo.getCardType()));//卡种
				sheet.addCell( new Label(col++, row, transInfo.getPayWebSite()));//网站
				sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("RESP_INFO",transInfo.getRespCode()+"","未知类型")));//交易状态
				sheet.addCell( new Label(col++, row, transInfo.getRespMsg()));//交易返回信息
				sheet.addCell( new Label(col++, row, transInfo.getRiskScore()));//欺诈分数
				sheet.addCell( new Label(col++, row, transInfo.getCurrencyName()));//支付通道
				sheet.addCell( new Label(col++, row, "1".equals(transInfo.getIschecked())?"已勾兑":"未勾兑" ));//勾兑状态
				sheet.addCell( new Label(col++, row, transInfo.getAccess()==1?"已入账":"未入账" ));//入账状态
				sheet.addCell( new Label(col++, row, transInfo.getDishonorStatus()));//拒付状态
				sheet.addCell( new Label(col++, row, transInfo.getDishonorAmount()));//拒付金额
				sheet.addCell( new Label(col++, row, transInfo.getRefundStatus()));//退款状态
				sheet.addCell( new Label(col++, row, transInfo.getRefundAmount()));//退款金额
				sheet.addCell( new Label(col++, row, transInfo.getFrozenStatus()));//冻结状态
				sheet.addCell( new Label(col++, row, transInfo.getFrozenAmount()));//冻结金额
				
				sheet.addCell( new Label(col++, row, "待定"));//伪冒状态
				
				sheet.addCell( new Label(col++, row, Tools.parseWebInfoToResourceType(transInfo.getWebInfo())));//订单来源
				sheet.addCell( new Label(col++, row, transInfo.getAcquirer()));//通道英文账单名称
				sheet.addCell( new Label(col++, row, transInfo.getCardFullNo()));//前六后四卡号
				if(!org.springframework.util.StringUtils.isEmpty(transInfo.getGoodsInfoByte())){//货物信息
					sheet.addCell(new Label(col++, row, new String(transInfo.getGoodsInfoByte(),"utf-8")));
					System.out.println("===="+new String(transInfo.getGoodsInfoByte(),"utf-8"));
				}else{
					sheet.addCell(new Label(col++, row, ""));
				}
				sheet.addCell( new Label(col++, row, transInfo.getCardFullName()));//姓名
				sheet.addCell( new Label(col++, row, transInfo.getGrPerName()));//姓名
				sheet.addCell( new Label(col++, row, transInfo.getEmail()));//邮箱
				sheet.addCell( new Label(col++, row, transInfo.getCardFullPhone()));//电话
				sheet.addCell( new Label(col++, row, transInfo.getGrphoneNumber()));//电话
				sheet.addCell( new Label(col++, row, transInfo.getIpAddress()));//IP
				sheet.addCell( new Label(col++, row, transInfo.getIpCountry()));//支付国家
				sheet.addCell( new Label(col++, row, cb.getCountry_code()));//支付国家
				sheet.addCell( new Label(col++, row, transInfo.getGrCountry()));//收货国家
				sheet.addCell( new Label(col++, row, transInfo.getGrState()));//收货省/ 州
				sheet.addCell( new Label(col++, row, transInfo.getGrAddress()));//收货地址
				sheet.addCell( new Label(col++, row, transInfo.getGrZipCode()));//邮编
				sheet.addCell( new Label(col++, row, transInfo.getIogistics()));//货运方式
				sheet.addCell( new Label(col++, row, transInfo.getTrackNo()));//货运单号
				sheet.addCell( new Label(col++, row, transInfo.getCardCountry()));//账单国家
				sheet.addCell( new Label(col++, row, transInfo.getCardState()));//账单省/州
				sheet.addCell( new Label(col++, row, transInfo.getCardAddress()));//账单地址
				sheet.addCell( new Label(col++, row, transInfo.getExceptionDate()));//异常时间
				if(transInfo.getExceptionDate()!=null){
					if((Double.parseDouble(transInfo.getRefundAmount().replaceAll(",", ""))+Double.parseDouble(transInfo.getDishonorAmount().replaceAll(",", "")))==Double.parseDouble(transInfo.getMerTransAmount().replaceAll(",", ""))){
						sheet.addCell( new Label(col++, row, "全额退款"));//异常时间
					}else{
						sheet.addCell( new Label(col++, row, "部分退款"));//异常时间
					}
					
				}else{
					sheet.addCell( new Label(col++, row, ""));//异常时间
				}
				sheet.addCell( new Label(col++, row, transInfo.getAutoCode()));//异常时间
				sheet.addCell( new Label(col++, row, Double.parseDouble(transInfo.getRefundAmount().replaceAll(",", ""))*(Double.parseDouble(transInfo.getMerTransAmount().replaceAll(",", ""))/Double.parseDouble(transInfo.getMerSettleAmount().replaceAll(",", "")))+""));//银行退款金额
				sheet.addCell( new Label(col++, row, transInfo.getCPDDate()));//CPD时间
				sheet.addCell( new Label(col++, row, "1".equals(transInfo.getIsFake())?"是":"否"));//CPD时间
				sheet.addCell( new Label(col++, row, transInfo.getBankPosNo()));//CPD时间
			}
		}

		book.write();
		book.close();
		os.flush();
		os.close();
	}

	/**
	 * 强制失败
	 * */
	@RequestMapping(value = "/transSuccessToFail")
	public String transSuccessToFail() {
		return "transchangemgr/transSuccessToFail";
	}

	/**
	 * 将订单支付状态改为失败
	 * */
	@RequestMapping(value = "/updateTransFail")
	public ModelAndView updateTransFail(String tradeNo) {
		String tradeNos[]=tradeNo.split(",");
		int num=transChangeServiceImpl.updateTransFail(tradeNos,getLogAccount().getRealName());
		return ajaxDoneSuccess("上传条数为："+tradeNos.length+",修改成功条数为："+num);
		
		
	}

	/**
	 * 上传重跑流水号页面
	 * */
	@RequestMapping(value = "/uploadTransToReRunFile")
	public String uploadTransToReRunFile() {
		return "transchangemgr/uploadTransToReRunFile";
	}

	/**
	 * 解析上传交易重跑文件并重跑交易
	 * */
	@RequestMapping(value = "/acceptTransToRunFile")
	public ModelAndView acceptTransToRunFile(
			DefaultMultipartHttpServletRequest request) throws IOException,
			ParseException, ISOException {
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		if (null != files) {
			log.info("上传了" + files.size() + "个文件。");
			final List<ReRunTransModel> list = new ArrayList<ReRunTransModel>();
			for (MultipartFile file : files) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						file.getInputStream()));// 构造一个BufferedReader类来读取文件
				String line = br.readLine();
				while (null != line) {
					log.info("line:" + line);
					if (line.contains(",")) {
						ReRunTransModel info = new ReRunTransModel();
						String[] str=line.split(",");
						if(str.length==4){
							info.setTradeNo(str[0]);
							if(!"0".equals(str[1])){
								info.setCurrencyId(str[1]);
							}
							info.setIsOrNotSubmitToBank(str[2]);
							info.setIsRisk(str[3]);
						}
						
						list.add(info);
					}
					line = br.readLine();
				}
			}
			// 重跑交易数据
			if (list.size() > 0) {
				for (final ReRunTransModel info : list) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//更新交易状态为银行待处理
					transChangeServiceImpl.updateTransToPendingByTradeNo(info.getTradeNo());
					//插入重跑记录
					transChangeServiceImpl.insertTransRerunInfo(info.getTradeNo(),info.getCurrencyId());
					info.setId(transChangeServiceImpl.selectMaxIdByTradeNo(info.getTradeNo()));
					new Thread(new Runnable() {
						public void run() {
							TreeMap<String, String> map = new TreeMap<String, String>();
							map.put("tradeNo", info.getTradeNo());
							map.put("currencyId", info.getCurrencyId());
							map.put("isOrNotSubmitToBank", info.getIsOrNotSubmitToBank());
							map.put("isRisk", info.getIsRisk());
							DefaultHttpClient httpClient = Tools
									.getHttpClient(); // 建立客户端连接
							HttpPost postMethod = new HttpPost(
									PaymentConfig.PAYMENT_ADDRESS
											+ "reRunTrans/reToPay/");
							List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
							for (String key : map.keySet()) {
								nvps.add(new BasicNameValuePair(key, map
										.get(key)));
							}
							postMethod.setEntity(new UrlEncodedFormEntity(nvps,
									Consts.UTF_8));
							int statusCode = 0;
							try {
								HttpResponse resp = httpClient
										.execute(postMethod); // 处理发送
								statusCode = resp.getStatusLine()
										.getStatusCode();
								System.out.println(statusCode);
								HttpEntity entity = resp.getEntity();
								InputStream inSm = entity.getContent();
								Scanner inScn = new Scanner(inSm);
								String resultJson = null;
								if (inScn.hasNextLine()) {
									resultJson = inScn.nextLine();
								}
								System.out.println("================="
										+ resultJson);
								JSONObject json = JSONObject
										.parseObject(resultJson);
								transChangeServiceImpl.updateTransReRunInfo(info.getId(),String.valueOf(json.get("respCode")),String.valueOf(json.get("respMsg")));
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								try {
									postMethod.clone();
								} catch (CloneNotSupportedException e) {
								}
							}
						}
					}).start();
					;
				}

			}

		}
		return ajaxDoneSuccess("上传成功。");
	}

	/***
	 * 列表显示交易重跑记录
	 * */
	@RequestMapping(value = "/listTransRerunInfo")
	public String listTransRerunInfo() {
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String transDateStart=sdf.format(date);
			String transDateEnd=sdf.format(date);
			criteria.getCondition().put("settleDateStart", transDateStart);
			criteria.getCondition().put("settleDateEnd", transDateEnd);
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<TransInfo> page = transChangeServiceImpl
					.getTransList(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "transchangemgr/listTransRerunInfo";
	}

	/***
	 * 列表高风险待处理订单
	 * */
	@RequestMapping(value = "/listTransHighRiskRerunInfo")
	public String listTransHighRiskRerunInfo() {
		PageInfo<TransInfo> page = transChangeServiceImpl
				.getTransHighRiskRerunInfoList(getCriteria());
		getRequest().setAttribute("page", page);
		return "transchangemgr/listTransHighRiskRerunInfo";
	}

	/**
	 * 跳转到高风险待处理订单重跑页面
	 * */
	@RequestMapping(value = "/goRerunTransHighRiskRerunInfo")
	public String goRerunTransHighRiskRerunInfo(String tradeNo) {
		getRequest().setAttribute("tradeNo", tradeNo);
		return "transchangemgr/rerunTransHighRiskRerunInfo";
	}

	/**
	 * 高风险待处理订单再次支付
	 * **/
//	@RequestMapping(value = "/rerunTransHighRiskRerunInfo")
//	public ModelAndView rerunTransHighRiskRerunInfo(String tradeNo) {
//		TreeMap<String, String> map = new TreeMap<String, String>();
//		map.put("tradeNo", tradeNo);
//		DefaultHttpClient httpClient = Tools.getHttpClient(); // 建立客户端连接
//		HttpPost postMethod = new HttpPost(PaymentConfig.PAYMENT_ADDRESS
//				+ "payment/reRunTrans/reToPay/");
//		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
//		for (String key : map.keySet()) {
//			nvps.add(new BasicNameValuePair(key, map.get(key)));
//		}
//		postMethod.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
//		int statusCode = 0;
//		try {
//			HttpResponse resp = httpClient.execute(postMethod); // 处理发送
//			statusCode = resp.getStatusLine().getStatusCode();
//			System.out.println(statusCode);
//			HttpEntity entity = resp.getEntity();
//			InputStream inSm = entity.getContent();
//			Scanner inScn = new Scanner(inSm);
//			String resultJson = null;
//			if (inScn.hasNextLine()) {
//				resultJson = inScn.nextLine();
//			}
//			System.out.println("=================" + resultJson);
//			JSONObject json = JSONObject.parseObject(resultJson);
//			if ("01".equals(json.get("respCode"))
//					|| "00".equals(json.get("respCode"))) {
//				transChangeServiceImpl.insertTransRerunInfo(tradeNo);
//				if ("00".equals(json.get("respCode"))) {
//					return ajaxDoneSuccess("支付成功");
//				} else {
//					return ajaxDoneSuccess("支付失败");
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				postMethod.clone();
//			} catch (CloneNotSupportedException e) {
//			}
//		}
//		return ajaxDoneError("无效返回");
//	}
	@RequestMapping(value = "/exportTransRerunInfo")
	public void exportTransRerunInfo(HttpServletResponse resp) throws Exception, IOException,
			RowsExceededException, WriteException {
		List<String> tradeNos=transChangeServiceImpl
				.getTransListForTransRerunTradeNos(getCriteria());
		List<TransDetailInfo> list = transMgrService.queryTransInfoByTradeNos(tradeNos);
		OutputStream os=resp.getOutputStream();
		resp.reset(); // 来清除首部的空白行
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "transLogList.xls");
		WritableWorkbook book = Workbook.createWorkbook(os);
		WritableSheet sheet = book.createSheet("交易列表", 0);
		String[] headerName = { "商户号","终端号","流水号","订单号","交易时间","标签金额","卡种","网站","交易状态",
				"交易返回信息"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		if (!org.springframework.util.StringUtils.isEmpty(list)) {
			
			for (int row = 1; row <= list.size(); row++) {
				
				
				int col = 0;
				TransDetailInfo transInfo = list.get(row - 1);
				sheet.addCell( new Label(col++, row, transInfo.getMerNo()));//商户号
				sheet.addCell( new Label(col++, row, transInfo.getTerNo()));//终端号
				sheet.addCell( new Label(col++, row, transInfo.getTradeNo()));//交易流水号
				sheet.addCell( new Label(col++, row, transInfo.getOrderNo()));//订单号
				sheet.addCell( new Label(col++, row, transInfo.getTransDate()));//交易时间
				sheet.addCell( new Label(col++, row, transInfo.getMerBusCurrency() + " " + transInfo.getMerTransAmount()));//交易金额
				sheet.addCell( new Label(col++, row, transInfo.getCardType()));//卡种
				sheet.addCell( new Label(col++, row, transInfo.getPayWebSite()));//网站
				sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("RESP_INFO",transInfo.getRespCode()+"","未知类型")));//交易状态
				sheet.addCell( new Label(col++, row, transInfo.getRespMsg()));//交易返回信息
			}
		}

		book.write();
		book.close();
		os.flush();
		os.close();
	}
}
