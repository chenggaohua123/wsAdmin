package com.gateway.refund.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jpos.iso.ISOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.BaseDataListener;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.APIException;
import com.gateway.common.excetion.ServiceException;
import com.gateway.common.utils.Funcs;
import com.gateway.common.utils.MD5Encryption;
import com.gateway.common.utils.SHA256Utils;
import com.gateway.common.utils.Tools;
import com.gateway.merchantmgr.model.MerchantTerInfo;
import com.gateway.merchantmgr.service.MerchantMgrService;
import com.gateway.refund.mapper.RefundMapper;
import com.gateway.refund.model.BankRefundInfo;
import com.gateway.refund.model.RefundInfo;
import com.gateway.refund.model.RefundModel;
import com.gateway.refund.service.RefundService;
import com.gateway.transchangemgr.service.TransChangeService;
import com.gateway.transmgr.model.TransInfo;
import com.gateway.transmgr.service.TransMgrService;

@Controller
@RequestMapping(value="/refund")
public class RefundController extends BaseController{
	
	private static Log logger = LogFactory.getLog(RefundController.class);
	@Autowired
	private RefundService refundServiceImpl;
	
	@Autowired
	private TransMgrService transMgrService;
	
	@Autowired 
	private RefundMapper refundMapper;
	@Autowired 
	private TransChangeService transChangeService;
	@Autowired 
	private MerchantMgrService merchantMgrService;
	@Value("${REFUND_URL}")
	private String REFUND_URL;
	
	
	@RequestMapping(value="/listRefundInfo")
	public String listRefundInfo(){
		PageInfo<RefundInfo> page=refundServiceImpl.queryRefundInfo(getCriteria());
		getRequest().setAttribute("page", page);
		return "refund/listRefundInfo";
	}
	
	@RequestMapping(value="/goDoRefundPage")
	public String goDoRefundPage(String id)throws APIException{
		RefundInfo refundInfo=refundServiceImpl.queryRefundInfoById(id);
		if(StringUtils.isEmpty(refundInfo)){
			throw new APIException("该数据不存在");
		}
		if(!"0".equals(refundInfo.getStatus())){
			throw new APIException("该订单已审核!");
		}
		getRequest().setAttribute("refund", refundInfo);
		return "refund/doRefund";
	}
	
	/**
	 * 批量审核退款成功或者驳回
	 * @param ids
	 * @param status 1审核驳回 2审核成功
	 * @return
	 * @throws APIException
	 */
	@RequestMapping(value="/checkRefundInfo")
	public ModelAndView checkRefundInfo(String[] ids,String status)throws APIException{
		int countTotal = ids.length;//总数条数
		int countSuccess = 0;
		String str = "";
		for(int i=0;i<countTotal;i++){
			RefundInfo info = refundServiceImpl.queryRefundInfoById(ids[i]);
			if(StringUtils.isEmpty(info)){
				continue;
			}
			/**
			 * isFrozen 是否冻结0:未，1:待审核，2已冻结      	
			 * transFrozen	是否冻结，统计冻结,0未冻结，1，已冻结      
			 *  isDishonor 是否拒付0:未，1:待审核，2已拒付
			 *  transDishonor 是否拒付，统计拒付,0未拒付，1，已拒付
			 *  
			 * 批量审核退款成功时 如果是已冻结||统计冻结已冻结||已拒付||统计拒付已拒付
			 */
			if(( ("1".equals(info.getIsFrozen())||"1".equals(info.getTransFrozen()) ) || (!"0".equals(info.getIsDishonor())||!"0".equals(info.getTransDishonor()) ) )&& "2".equals(status)){
				return ajaxDoneError("流水号:"+info.getTradeNo()+"已冻结/已拒付，请驳回退款之后再退款剩下的数据");
			}
			//审核成功时如果已经全额退款则不允许操作
			BigDecimal refundAmount = new BigDecimal(info.getRefundAmount()); 
			BigDecimal busAmount = new BigDecimal(info.getBusAmount());
			int compareTo = refundAmount.compareTo(busAmount);
			if( "2".equals(status) && "2".equals(info.getIsRefund()) && 0==compareTo){//gw_trans_info中已退款
				return ajaxDoneError("流水号:"+info.getTradeNo()+"已调查单/重复退款，请驳回退款之后再退款剩下的数据");
			}
			info.setStatus(status);
			info.setAuditor(getLogAccount().getRealName());
			final RefundInfo rInfo = setMoneyInfo(info);
			if(StringUtils.isEmpty(rInfo)){
				return ajaxDoneError("退款数据异常，请重试");
			}
			if("2".equals(status)){
				new Thread(new Runnable() {
					public void run() {
						try {
							refundServiceImpl.doRefund(rInfo);
						} catch (APIException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
			int y = refundServiceImpl.insertRefundTransInfo(info);
			if(0 < y){
				countSuccess++;
			}
			log.error("订单号：" + info.getTerNo() +"退款成功，退款金额为：" + info.getRefundCurrency() + " " + info.getRefundAmount());
		}
		return ajaxDoneSuccess("批量"+str+"总条数为：" + countTotal+",操作成功条数为： "+countSuccess);
	}
	
	/*@RequestMapping(value="/doRefund")
	public ModelAndView doRefund(RefundInfo refundInfo) throws APIException{
		if(null==refundInfo){
			return ajaxDoneError("退款信息不存在!");
		}
		refundInfo.setAuditor(getLogAccount().getRealName());
		int i = refundServiceImpl.updateRefundInfoById(refundInfo);
		if(0 < i){
			return ajaxDoneSuccess("退款审核成功");
		}
		return ajaxDoneError("退款审核失败");
	}*/
	
	/*	退款审核代码-->修改逻辑，代码勿删除*/
	@RequestMapping(value="/doRefund")
	public ModelAndView doRefund(RefundInfo refundInfo) throws APIException{
		if(null==refundInfo){
			return ajaxDoneError("退款信息不存在!");
		}
		refundInfo.setAuditor(getLogAccount().getRealName());
		if("1".equals(refundInfo.getStatus())){
			int i = refundServiceImpl.updateRefundInfoById(refundInfo);
			if(0 < i){
				return ajaxDoneSuccess("退款驳回成功");
			}
			return ajaxDoneError("退款驳回失败");
		}
		final RefundInfo rInfo = setMoneyInfo(refundInfo);
		if(StringUtils.isEmpty(rInfo)){
			return ajaxDoneError("退款数据异常，请重试");
		}
		new Thread(new Runnable() {
			public void run() {
				try {
					refundServiceImpl.doRefund(rInfo);
				} catch (APIException e) {
					e.printStackTrace();
				}
			}
		}).start();
		int i = refundServiceImpl.insertRefundTransInfo(refundInfo);
		if(0 < i){
			return ajaxDoneSuccess("退款通过成功");
		}
		log.error("订单号：" + refundInfo.getTerNo() +"退款成功，保存数据库失败，退款金额为：" + refundInfo.getRefundCurrency() + " " + refundInfo.getRefundAmount());
		return ajaxDoneError("退款通过失败");
	}
	
	@RequestMapping(value="/doRefundNotify")
	public ModelAndView doRefundNotify(String id) throws APIException{
		RefundInfo refundInfo=refundServiceImpl.queryRefundInfoById(id);
		if(null==refundInfo){
			return ajaxDoneError("退款信息不存在!");
		}
		int refundNotifyStatus = refundInfo.getRefundNotifyStatus();
		if(2==refundNotifyStatus){//通知失败时，才可以再次通知
			String status = refundInfo.getStatus();
			String refundNotifyURL = refundInfo.getRefundNotifyURL();
					//退款结果通知 start
					String respCode=null;
					String respMsg=null;
					switch (status) {
					case "1"://1,驳回
						respCode="01";//退款失败
						respMsg="failed";//退款失败
						break;
					case "2"://2 退款通过
						respCode="00";//退款成功
						respMsg="success";//退款成功
						break;
					default:
						return ajaxDoneError("退款通知失败");//返回处理结果
					}
					String tradeNo=refundInfo.getTradeNo();
					String merNo=refundInfo.getMerNo();
					String terNo=refundInfo.getTerNo();
					StringBuffer sb=new StringBuffer();
					sb.append("merNo="+merNo);
					sb.append("&");
					sb.append("terNo="+terNo);
					sb.append("&");
					sb.append("refundCurrency="+refundInfo.getRefundCurrency());
					sb.append("&");
					sb.append("refundAmount="+refundInfo.getRefundAmount().toString());
					sb.append("&");
					sb.append("busCurrency="+refundInfo.getBusCurrency());
					sb.append("&");
					sb.append("busAmount="+refundInfo.getBusAmount().toString());
					sb.append("&");
					sb.append("tradeNo="+refundInfo.getTradeNo());
					sb.append("&");
					MerchantTerInfo merchantTerInfo = merchantMgrService.queryTerInfoByMerNoAndTerNo(merNo, terNo);
					String shaKey=merchantTerInfo.getShaKey();
					if(shaKey==null){
						logger.info(refundInfo.getTradeNo()+"shaKey空");
						return ajaxDoneError("退款信息不存在!");//返回结束
					}
					sb.append(shaKey);
					String hashcode=SHA256Utils.getSHA256Encryption(sb.toString());
					// 获取商户号
					try {
						List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
						// 查询商户支付返回网址
						nvps.add(new BasicNameValuePair("orderNo", refundInfo.getOrderNo()));
						nvps.add(new BasicNameValuePair("merNo", refundInfo.getMerNo()));
						nvps.add(new BasicNameValuePair("terNo", refundInfo.getTerNo()));
						nvps.add(new BasicNameValuePair("tradeNo", tradeNo));
						nvps.add(new BasicNameValuePair("refundCurrency", refundInfo.getRefundCurrency()));
						nvps.add(new BasicNameValuePair("refundAmount", refundInfo.getRefundAmount()));
						nvps.add(new BasicNameValuePair("busAmount", refundInfo.getBusAmount()));
						nvps.add(new BasicNameValuePair("busCurrency", refundInfo.getBusCurrency()));
						nvps.add(new BasicNameValuePair("respCode", respCode));
						nvps.add(new BasicNameValuePair("respMsg", respMsg));
						nvps.add(new BasicNameValuePair("hashcode", hashcode));
						// 发送到商户提交的notifyUrl
						DefaultHttpClient httpClient = Tools.getHttpClient(); // 建立客户端连接
						HttpPost postMethod = new HttpPost(refundNotifyURL);
						postMethod.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
						HttpResponse resp1 = httpClient.execute(postMethod); // 处理发送
						int statusCode = resp1.getStatusLine().getStatusCode();
						logger.info(tradeNo + "通知商户退款结果:" + statusCode);
						if(200!=statusCode){
							return ajaxDoneError("通知商户退款结果失败");//返回结束
						}
						HttpEntity entity = resp1.getEntity();
						InputStream inSm = entity.getContent();
						Scanner inScn = new Scanner(inSm);
						String resultJson = null;
						if (inScn.hasNextLine()) {
							resultJson = inScn.nextLine();
						}
						logger.info(tradeNo + "通知商户退款结果返回resultJson:" + resultJson);
						JSONObject json = JSON.parseObject(resultJson);
						if ("1".equals(json.getString("refundNotifyStatus"))) {
							refundInfo.setRefundNotifyStatus(1);//通知成功
							refundMapper.updateRefundNotifyStatusById(refundInfo);
							return ajaxDoneError("通知商户退款结果成功");//返回结束
						} 
					} catch (Exception e) {
						logger.info(tradeNo + "通知退款结果+"+refundNotifyURL+"+更新通知状态异常信息:"+e.getMessage());
						return ajaxDoneError("通知商户退款结果异常");//返回结束
					}
					//退款结果通知 end
		}
		return ajaxDoneError("退款通知失败");//返回结束
	}
	
	/** 将交易金额","币种转换成银行金额","币种，用于 */
	private RefundInfo setMoneyInfo(RefundInfo refundInfo){
		RefundInfo rInfo = refundServiceImpl.queryRefundInfoById(refundInfo.getId());
		if(StringUtils.isEmpty(refundInfo.getId()) || StringUtils.isEmpty(rInfo)){
			return null;
		}
		TransInfo info = transMgrService.queryTransDetailByTradeNo(refundInfo.getTradeNo());
		BigDecimal rate = new BigDecimal( refundInfo.getRefundAmount()).divide(info.getMerTransAmount(),6,BigDecimal.ROUND_HALF_UP);
		rInfo.setRefundAmount(info.getBankTransAmount().multiply(rate)+"");
		rInfo.setRefundCurrency(info.getBankCurrency());
		rInfo.setBusAmount(info.getBankTransAmount()+"");
		rInfo.setBusCurrency(info.getBankCurrency());
		return rInfo;
	}
	
	/** 去新增单笔退款 */
	@RequestMapping(value="/goAddRefundInfo")
	public String goAddRefundInfo(){
		return "refund/addRefundInfo";
	}
	
	/** 新增单笔退款 */
	@RequestMapping(value="/addRefundInfo")
	public ModelAndView addRefundInfo(RefundInfo info)throws APIException{
		info.setApplyBy(getLogAccount().getRealName());
		String str = refundServiceImpl.checkRefundInfo(info);
		if(!StringUtils.isEmpty(str)){
			return ajaxDoneError(str);
		}
		int i = refundServiceImpl.addRefundInfo(info);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	/** 去上传退款信息页面 */
	@RequestMapping(value="/goAddRefundFile")
	public String goAddRefundFile(){
		return "refund/addRefundFile";
	}
	
	/** 批量上传退款信息 */
	@RequestMapping(value="/addRefundFile")
	public ModelAndView addRefundFile(DefaultMultipartHttpServletRequest request,HttpServletResponse res) throws IOException, ParseException, ISOException{
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		res.setContentType("text/html");
		int count=0;
		int total=0;
		if(null != files){
			log.info("上传了"+files.size()+"个文件。");
			for(MultipartFile file :files){
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));//构造一个BufferedReader类来读取文件
				String line = br.readLine();
				while(null != line){
					log.info("line:"+line);
					String [] fields = line.split(",");
					if(fields.length == 4){
						total++;
						int index = 0;
						RefundInfo info=new RefundInfo();
						info.setTradeNo(fields[index++].trim());
						info.setApplyBy(getLogAccount().getRealName());
						info.setMerNo(getLogAccount().getMerNo());
						info.setRefundAmount(fields[index++].trim());
						info.setRefundReason(fields[index++].trim());
						info.setRemark(fields[index++].trim());
						info.setApplyBy(getLogAccount().getRealName());
						String str = refundServiceImpl.checkRefundInfo(info);
						if(!StringUtils.isEmpty(str)){
							line = br.readLine();
							continue;
						}
						try {
							int i = refundServiceImpl.addRefundInfo(info);
							if(0 < i){
								count++;
							}
						} catch (APIException e) {
						}
					}else{
						log.info("错误line:"+line);
					}
					line = br.readLine();
				}
			}
			
		}
		return ajaxDoneSuccess("上传成功，上传了"+total+"条记录,成功上传了"+count+"条记录，失败了"+(total-count)+"条记录");
	}
	
	/** 导出退款信息 
	 * @throws Exception */
	@RequestMapping(value="/uploadRefundFile")
	public void uploadRefundFile(HttpServletResponse resp) throws Exception{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "refundInfoList.xls" ); 
		List<RefundInfo> list = refundServiceImpl.uploadRefundFile(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("异常交易列表", 0);
		String[] headerName = { "商户号","终端号","流水号","订单号","通道号","卡号","授权码","是否全额退款","交易金额","退款金额","银行交易金额","银行退款金额","退款原因","退款申请人","退款申请时间","退款状态","退款处理人","退款处理时间","备注"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			RefundInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getMerNo()));//商户号
			sheet.addCell( new Label(col++, row, info.getTerNo()));//终端号
			sheet.addCell( new Label(col++, row, info.getTradeNo()));//交易流水号
			sheet.addCell( new Label(col++, row, info.getOrderNo()));//交易流水号
			sheet.addCell( new Label(col++, row, info.getCurrencyName()));//交易流水号
			if(info.getCheckNo()!=null&&!"".equals(info.getCheckNo())){
				sheet.addCell(new Label(col++, row, Funcs.decrypt(info.getCheckNo())+Funcs.decrypt(info.getMiddle())+Funcs.decrypt(info.getLast())));
			}else{
				sheet.addCell(new Label(col++, row, ""));
			}
			sheet.addCell( new Label(col++, row, info.getAutoCode()));//交易流水号
			sheet.addCell( new Label(col++, row, Double.parseDouble(info.getBusAmount())==Double.parseDouble(info.getRefundAmount())?"全额退款":"部分退款"));//交易流水号
			sheet.addCell( new Label(col++, row, info.getBusCurrency()+ " " + info.getBusAmount()));//交易金额
			sheet.addCell( new Label(col++, row, info.getRefundCurrency() + " " + info.getRefundAmount()));//退款金额
			sheet.addCell( new Label(col++, row, info.getBankCurrency() + " " + info.getBankTransAmount()));//银行交易金额
			sheet.addCell( new Label(col++, row, info.getBankCurrency() + " " + sumAmount(info.getBusAmount(),info.getRefundAmount(),info.getBankTransAmount())));//银行退款金额
			sheet.addCell( new Label(col++, row, info.getRefundReason()));//退款原因
			sheet.addCell( new Label(col++, row, info.getApplyBy()));//退款申请人
			sheet.addCell( new Label(col++, row, info.getApplyDate()));//交易时间
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("CHECK_STATUS",info.getStatus()+"","未知类型")));//退款状态
			sheet.addCell( new Label(col++, row, info.getAuditor()));//退款处理人
			sheet.addCell( new Label(col++, row, info.getRefundDate()));//交易时间
			sheet.addCell( new Label(col++, row, info.getRemark()));//备注
		}
		book.write();
		book.close();
	}
	
	private String sumAmount(String busAmount,String refundAmount,String bankTransAmount){
		BigDecimal rate = new BigDecimal(refundAmount).divide(new BigDecimal(busAmount), 6, BigDecimal.ROUND_HALF_UP);
		return new BigDecimal(bankTransAmount).multiply(rate) + "";
	}
	
	
	@RequestMapping(value="/listBankRefundInfo")
	public String listBankRefundInfo(){
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String transDateStart=sdf.format(date);
			String transDateEnd=sdf.format(date);
			criteria.getCondition().put("dateStart", transDateStart);
			criteria.getCondition().put("dateEnd", transDateEnd);
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<BankRefundInfo> page=refundServiceImpl.queryBankRefundInfo(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "refund/listBankRefundInfo";
	}
	
	
	@RequestMapping(value="/goAddBankRefundInfo")
	public String goAddBankRefundInfo(){
		return "refund/addBankRefundInfo";
	}
	
	@RequestMapping("/addBankRefundInfo")
	public ModelAndView addBankRefundInfo(BankRefundInfo brInfo) throws APIException{
		if(refundMapper.queryTransInfoByTradeNo(brInfo.getTradeNo())<=0){
			return ajaxDoneError("订单号不存在");
		}
		brInfo.setCreateBy(getLogAccount().getRealName());
		RefundInfo refundInfo=setMoneyInfo(brInfo);
		RefundModel info=new RefundModel();
		String respStatus="";
		StringBuffer sb=new StringBuffer();
		TreeMap<String, String> map=new TreeMap<String, String>();
		map.put("tradeNo", refundInfo.getTradeNo());
		map.put("merNo", refundInfo.getMerNo());
		map.put("terNo", refundInfo.getTerNo());
		map.put("busAmount", refundInfo.getBusAmount());
		map.put("busCurrency", refundInfo.getBusCurrency());
		map.put("refundAmount", refundInfo.getRefundAmount());
		map.put("refundCurrency", refundInfo.getRefundCurrency());
		map.put("refundReason", refundInfo.getRefundReason());
		map.put("applyDate", refundInfo.getApplyDate());
		for(String key:map.keySet()){
			sb.append(key+"="+map.get(key)+"&");
		}
		DefaultHttpClient httpClient = Tools.getHttpClient(); // 建立客户端连接
		HttpPost postMethod = new HttpPost(REFUND_URL);
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		for(String key:map.keySet()){
			nvps.add(new BasicNameValuePair(key, map.get(key)));
		}
		postMethod.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		int statusCode = 0;
		try {
			HttpResponse resp = httpClient.execute(postMethod); // 处理发送
			statusCode = resp.getStatusLine().getStatusCode();
			HttpEntity entity = resp.getEntity();
			InputStream inSm = entity.getContent();
			Scanner inScn = new Scanner(inSm);
			String resultJson = null;
			if (inScn.hasNextLine()) {
				resultJson = inScn.nextLine();
			}
			JSONObject json = JSON.parseObject(resultJson);
			respStatus=json.getString("respStatus");
			if(null==respStatus||"".equals(respStatus)){
				logger.info(refundInfo.getTradeNo()+"  银行返回信息失败！");
				brInfo.setRefundStatus("0001");
				brInfo.setRefundMsg("银行返回信息失败");
				//更新日志表退款状态
				refundMapper.insertBankRefundInfo(brInfo);
			}else{
				info=JSON.toJavaObject(json, RefundModel.class);
				brInfo.setAutoCode(info.getAutoCode());
				brInfo.setBankOrderNo(info.getBankOrderNo());
				brInfo.setTradeDate(info.getTradeDate());
				brInfo.setTradeTime(info.getTradeTime());
				brInfo.setInvoiceNo(info.getInvoiceNo());
				brInfo.setRefNo(info.getRefNo());
				if("0000".equals(info.getRespCode())){
					brInfo.setRefundStatus("0000");
					brInfo.setRefundMsg("退款成功");
					refundMapper.insertBankRefundInfo(brInfo);
					return ajaxDoneSuccess("退款成功");
				}else{
					brInfo.setRefundStatus("0001");
					brInfo.setRefundMsg("退款失败:"+info.getRespMsg());
					refundMapper.insertBankRefundInfo(brInfo);
					return ajaxDoneError("退款失败:"+info.getRespMsg());
				}
				//更新日志表退款状态
			}
			throw new APIException("2002", "提交银行失败");
		} catch (Exception e) {
			brInfo.setRefundStatus("0001");
			brInfo.setRefundMsg("提交银行失败");
			//更新日志表退款状态
			refundMapper.insertBankRefundInfo(brInfo);
			logger.info(refundInfo.getTradeNo() + ":提交银行失败！");
			e.printStackTrace();
			throw new APIException("2002", "提交银行失败");
		} finally {
			try {
				postMethod.clone();
			} catch (CloneNotSupportedException e) {
			}
		}
		
	}
	
	@RequestMapping(value="/goUpdateBankRefundInfo")
	public String goUpdateBankRefundInfo(String id) throws ServiceException{
		BankRefundInfo info=refundMapper.queryBankRefundInfoById(id);
		if("0000".equals(info.getRefundStatus())){
			throw new ServiceException("该订单已退款成功!");
		}
		getRequest().setAttribute("info", info);
		return "refund/updateBankRefundInfo";
	}
	@RequestMapping(value="/updateBankRefundInfo")
	public ModelAndView updateBankRefundInfo(BankRefundInfo brInfo) throws APIException{
		if(refundMapper.queryTransInfoByTradeNo(brInfo.getTradeNo())<=0){
			return ajaxDoneError("订单号不存在");
		}
		brInfo.setCreateBy(getLogAccount().getRealName());	
		RefundInfo refundInfo=setMoneyInfo(brInfo);
		RefundModel info=new RefundModel();
		String respStatus="";
		StringBuffer sb=new StringBuffer();
		TreeMap<String, String> map=new TreeMap<String, String>();
		map.put("tradeNo", refundInfo.getTradeNo());
		map.put("merNo", refundInfo.getMerNo());
		map.put("terNo", refundInfo.getTerNo());
		map.put("busAmount", refundInfo.getBusAmount());
		map.put("busCurrency", refundInfo.getBusCurrency());
		map.put("refundAmount", refundInfo.getRefundAmount());
		map.put("refundCurrency", refundInfo.getRefundCurrency());
		map.put("refundReason", refundInfo.getRefundReason());
		map.put("applyDate", refundInfo.getApplyDate());
		for(String key:map.keySet()){
			sb.append(key+"="+map.get(key)+"&");
		}
		DefaultHttpClient httpClient = Tools.getHttpClient(); // 建立客户端连接
		HttpPost postMethod = new HttpPost(REFUND_URL);
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		for(String key:map.keySet()){
			nvps.add(new BasicNameValuePair(key, map.get(key)));
		}
		postMethod.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		try {
			HttpResponse resp = httpClient.execute(postMethod); // 处理发送
			HttpEntity entity = resp.getEntity();
			InputStream inSm = entity.getContent();
			Scanner inScn = new Scanner(inSm);
			String resultJson = null;
			if (inScn.hasNextLine()) {
				resultJson = inScn.nextLine();
			}
			JSONObject json = JSON.parseObject(resultJson);
			respStatus=json.getString("respStatus");
			if(null==respStatus||"".equals(respStatus)){
				logger.info(refundInfo.getTradeNo()+"  银行返回信息失败！");
				brInfo.setRefundStatus("0001");
				brInfo.setRefundMsg("银行返回信息失败");
				//更新日志表退款状态
				refundMapper.updateBankRefundInfo(brInfo);
			}else{
				info=JSON.toJavaObject(json, RefundModel.class);
				brInfo.setAutoCode(info.getAutoCode());
				brInfo.setBankOrderNo(info.getBankOrderNo());
				brInfo.setTradeDate(info.getTradeDate());
				brInfo.setTradeTime(info.getTradeTime());
				brInfo.setInvoiceNo(info.getInvoiceNo());
				brInfo.setRefNo(info.getRefNo());
				if("0000".equals(info.getRespCode())){
					brInfo.setRefundStatus("0000");
					brInfo.setRefundMsg("退款成功");
					refundMapper.updateBankRefundInfo(brInfo);
					return ajaxDoneSuccess("退款成功");
				}else{
					brInfo.setRefundStatus("0001");
					brInfo.setRefundMsg("退款失败:"+info.getRespMsg());
					refundMapper.updateBankRefundInfo(brInfo);
					return ajaxDoneError("退款失败:"+info.getRespMsg());
				}
				//更新日志表退款状态
			}
			throw new APIException("2002", "提交银行失败");
		} catch (Exception e) {
			brInfo.setRefundStatus("0001");
			brInfo.setRefundMsg("提交银行失败");
			//更新日志表退款状态
			refundMapper.updateBankRefundInfo(brInfo);
			logger.info(refundInfo.getTradeNo() + ":提交银行失败！");
			e.printStackTrace();
			throw new APIException("2002", "提交银行失败");
		} finally {
			try {
				postMethod.clone();
			} catch (CloneNotSupportedException e) {
			}
		}
	}
	
	private RefundInfo setMoneyInfo(BankRefundInfo brInfo){
		RefundInfo rInfo = new RefundInfo();
		rInfo.setTradeNo(brInfo.getTradeNo());
		rInfo.setRefundReason("银行退款");
		TransInfo info = transMgrService.queryTransDetailByTradeNo(brInfo.getTradeNo());
		rInfo.setMerNo(info.getMerNo());
		rInfo.setTerNo(info.getTerNo());
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    String  s = format1.format(info.getTransDate());
		rInfo.setApplyDate(s);
		rInfo.setRefundAmount(brInfo.getRefundAmount()+"");
		rInfo.setRefundCurrency(info.getBankCurrency());
		rInfo.setBusAmount(brInfo.getBankAmount()+"");
		rInfo.setBusCurrency(info.getBankCurrency());
		return rInfo;
	}
	
	
	
	/** 导出退款信息 
	 * @throws Exception */
	@RequestMapping(value="/exportBankRefundInfo")
	public void exportBankRefundInfo(HttpServletResponse resp) throws Exception{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "refundInfoList.xls" ); 
		List<BankRefundInfo> list = refundServiceImpl.exportBankRefundInfo(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("银行退款列表", 0);
		String[] headerName = { "流水号","银行交易金额","银行退款金额","退款状态","退款信息","处理人","退款时间","交易时间","备注"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			BankRefundInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getTradeNo()));//商户号
			sheet.addCell( new Label(col++, row, info.getBankCurrency() +" " +info.getBankAmount()));//终端号
			sheet.addCell( new Label(col++, row, info.getRefundCurrency() +" "+ info.getRefundAmount()));//交易流水号
			sheet.addCell( new Label(col++, row, "0000".equals(info.getRefundStatus())?"退款成功":"退款失败"));//交易流水号
			sheet.addCell( new Label(col++, row, info.getRefundMsg()));//交易流水号
			sheet.addCell( new Label(col++, row, info.getCreateBy()));//交易流水号
			sheet.addCell( new Label(col++, row, info.getRefundDate()));//交易时间
			sheet.addCell( new Label(col++, row, info.getTransDate()));//交易时间
			sheet.addCell( new Label(col++, row, info.getRemark()));//备注
		}
		book.write();
		book.close();
	}
	
	/**
	 * 跳转到批量上传银行退款模板页面
	 */
	@RequestMapping(value="/uploadBankBatchRefundFile")
	public String uploadBankBatchRefundFile(){
		return "refund/bankBatchRefund/uploadBankBatchRefundFile";
	}
	
	/** 批量退款下载上传模板 */
	@RequestMapping(value = "/exportBatchBankRefundModel")
	public void exportBatchBankRefundModel(HttpServletResponse resp){
		ArrayList<String> strArray = new ArrayList<String> ();
		strArray.add("流水号 ,银行交易金额,退款金额");
		strArray.add("BR1506161604560740,200.32,200.32");
		strArray.add("BR1506161604560741,200.32,200.32");
		resp.setContentType("application/zip");
		resp.addHeader("Content-Disposition", "attachment;filename=BatchBankRefundModel.txt");
		OutputStream outp = null;
		try {
			
			outp = resp.getOutputStream();
			PrintWriter pw=new PrintWriter(outp);
			pw.print("modelType:tradeNo,bankTransAmount,refundAmount\r\n");
			for(String str:strArray){
				pw.print(str + "\r\n");
			}
			pw.flush();
			outp.flush();
		} catch (Exception e) {
			
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
		return;
	}
	
	/**
	 * 跳转到显示批量退款信息页面
	 */
	@RequestMapping(value="/goShowBankRefundBatchInfo")
	public String goShowBankRefundBatchInfo(){
		return "refund/bankBatchRefund/showBankRefundBatchInfo";
	}
	
	/**
	 * 批量退款文件解析
	 * @throws Exception
	 */
	@RequestMapping(value ="/resBatchBankRefundFile")
	public ModelAndView resBatchBankRefundFile(DefaultMultipartHttpServletRequest request) throws Exception,
			WriteException {
		List<BankRefundInfo> list = new ArrayList<BankRefundInfo>();
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		StringBuffer sb = new StringBuffer();
		if (null != files) {
			log.info("上传了" + files.size() + "个文件。");
			Criteria criteria = new Criteria();
			for (MultipartFile file : files) {
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(),"GBK"));//
				// 构造一个BufferedReader类来读取文件
				String line = br.readLine();//第一列标题
				line = br.readLine();//第二列标题
				line = br.readLine();//读取第3列
				int lineCount = 3;//从第二行开始读
				while (null != line) {
					log.info("line:" + line);
					if(line==null||"".equals(line.replaceAll("\\s", ""))){
						line = br.readLine();
						continue;
					}
					String[] fileds = line.split(",");
					if (fileds.length==3) {
						int index = 0;
						criteria.getCondition().put("tradeNo", fileds[index++]);//流水号
						
						List<TransInfo> transInfo = transChangeService.queryTransInfo(criteria);
						if(!org.springframework.util.StringUtils.isEmpty(transInfo)&& 0 != transInfo.size()) {
							for(TransInfo li:transInfo){
								BankRefundInfo bri=new BankRefundInfo();
								bri.setTradeNo(li.getTradeNo());
								bri.setBankAmount(fileds[index++]);
								bri.setRefundAmount(fileds[index++]);
								if(Double.parseDouble(bri.getRefundAmount())<=Double.parseDouble(bri.getBankAmount())){
									list.add(bri);
								}else{
									sb.append("第"+lineCount+ "行数据：" +li.getTradeNo()+"退款金额过大;<br>");
								}
							}
						}else{
							sb.append("第"+lineCount+ "行数据：" +line+";流水号不存在<br>");
						}
					}else{
						sb.append("第"+lineCount+ "行数据：" +line+";数据错误<br>");
					}
					line = br.readLine();
					lineCount++;
				}
			}

		}
		getRequest().getSession().setAttribute("bankRefundList", list);
		getRequest().getSession().setAttribute("bankRefundErrorInfo", sb + "");
		log.info("错误信息："+sb.toString());
		return ajaxDoneSuccess("上传成功。返回信息");
	}
	/**
	 * 批量退款
	 * @param tradeNos
	 * @return
	 */
	@RequestMapping(value="/batchBankRefund")
	public ModelAndView batchBankRefund(String[] tradeNos){
		int count=0;
		List<BankRefundInfo> list =(List<BankRefundInfo>) getRequest().getSession().getAttribute("bankRefundList");
		List<BankRefundInfo> refundList=new ArrayList<BankRefundInfo>();
		for(BankRefundInfo info:list){
			for(String tradeNo:tradeNos){
				if(tradeNo.equalsIgnoreCase(info.getTradeNo())){
					count++;
					refundList.add(info);
					break;
				}
			}
		}
		final String realName=getLogAccount().getRealName();
		final List<BankRefundInfo> refundListInfo=refundList;
		new Thread(new Runnable() {
			public void run() {
				try {
					for(BankRefundInfo binfo:refundListInfo){
					
						Thread.sleep(800);
						binfo.setCreateBy(realName);
						updateBatchBankRefundInfo(binfo);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		return ajaxDoneSuccess("退款了"+count+"条数据");
	}
	
	public void updateBatchBankRefundInfo(BankRefundInfo brInfo) throws APIException{
		if(refundMapper.queryTransInfoByTradeNo(brInfo.getTradeNo())<=0){
			log.info(brInfo.getTradeNo()+"订单号不存在");
		}
		logger.info("退款流水号:"+brInfo.getTradeNo());
		RefundInfo refundInfo=setMoneyInfo(brInfo);
		brInfo.setBankCurrency(refundInfo.getBankCurrency());
		brInfo.setRefundCurrency(refundInfo.getBankCurrency());
		RefundModel info=new RefundModel();
		String respStatus="";
		StringBuffer sb=new StringBuffer();
		TreeMap<String, String> map=new TreeMap<String, String>();
		map.put("tradeNo", refundInfo.getTradeNo());
		map.put("merNo", refundInfo.getMerNo());
		map.put("terNo", refundInfo.getTerNo());
		map.put("busAmount", refundInfo.getBusAmount());
		map.put("busCurrency", refundInfo.getBusCurrency());
		map.put("refundAmount", refundInfo.getRefundAmount());
		map.put("refundCurrency", refundInfo.getRefundCurrency());
		map.put("refundReason", refundInfo.getRefundReason());
		map.put("applyDate", refundInfo.getApplyDate());
		for(String key:map.keySet()){
			sb.append(key+"="+map.get(key)+"&");
		}
		String md5=MD5Encryption.getSHA256Encryption(sb.toString());
		DefaultHttpClient httpClient = Tools.getHttpClient(); // 建立客户端连接
		HttpPost postMethod = new HttpPost(REFUND_URL);
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		for(String key:map.keySet()){
			nvps.add(new BasicNameValuePair(key, map.get(key)));
		}
		postMethod.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		try {
			HttpResponse resp = httpClient.execute(postMethod); // 处理发送
			HttpEntity entity = resp.getEntity();
			InputStream inSm = entity.getContent();
			Scanner inScn = new Scanner(inSm);
			String resultJson = null;
			if (inScn.hasNextLine()) {
				resultJson = inScn.nextLine();
			}
			JSONObject json = JSON.parseObject(resultJson);
			respStatus=json.getString("respStatus");
			if(null==respStatus||"".equals(respStatus)){
				logger.info(refundInfo.getTradeNo()+"  银行返回信息失败！");
				brInfo.setRefundStatus("0001");
				brInfo.setRefundMsg("银行返回信息失败");
				//更新日志表退款状态
				refundMapper.insertBankRefundInfo(brInfo);
			}else{
				info=JSON.toJavaObject(json, RefundModel.class);
				brInfo.setAutoCode(info.getAutoCode());
				brInfo.setBankOrderNo(info.getBankOrderNo());
				brInfo.setTradeDate(info.getTradeDate());
				brInfo.setTradeTime(info.getTradeTime());
				brInfo.setInvoiceNo(info.getInvoiceNo());
				brInfo.setRefNo(info.getRefNo());
				if("0000".equals(info.getRespCode())){
					brInfo.setRefundStatus("0000");
					brInfo.setRefundMsg("退款成功");
					refundMapper.insertBankRefundInfo(brInfo);
					log.info(brInfo.getTradeNo()+"退款成功");
					return ;
				}else{
					brInfo.setRefundStatus("0001");
					brInfo.setRefundMsg("退款失败:"+info.getRespMsg());
					refundMapper.insertBankRefundInfo(brInfo);
					log.info(brInfo.getTradeNo()+"退款失败:"+info.getRespMsg());
					return ;
				}
				//更新日志表退款状态
			}
			throw new APIException("2002", "提交银行失败");
		} catch (Exception e) {
			brInfo.setRefundStatus("0001");
			brInfo.setRefundMsg("提交银行失败");
			//更新日志表退款状态
			refundMapper.insertBankRefundInfo(brInfo);
			logger.info(refundInfo.getTradeNo() + ":提交银行失败！");
			e.printStackTrace();
			throw new APIException("2002", "提交银行失败");
		} finally {
			try {
				postMethod.clone();
			} catch (CloneNotSupportedException e) {
			}
		}
		
	}
	
}
