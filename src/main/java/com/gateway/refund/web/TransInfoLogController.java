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
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

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
import com.gateway.api.utils.Constants;
import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.BaseDataListener;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.APIException;
import com.gateway.common.utils.MD5Encryption;
import com.gateway.common.utils.PaymentConfig;
import com.gateway.common.utils.Tools;
import com.gateway.refund.mapper.RefundMapper;
import com.gateway.refund.model.CountTransMoney;
import com.gateway.refund.model.RefundAgainInfo;
import com.gateway.refund.model.RefundInfo;
import com.gateway.refund.model.RefundModel;
import com.gateway.refund.model.TransInfoLog;
import com.gateway.refund.service.TransInfoLogService;
import com.gateway.transchangemgr.model.TransCheckInfo;
import com.gateway.transmgr.model.TransInfo;
import com.gateway.transmgr.service.TransMgrService;

@Controller
@RequestMapping(value="/transInfoLog")
public class TransInfoLogController extends BaseController{
	
	private static Log logger = LogFactory.getLog(TransInfoLogController.class);
	@Autowired
	private TransInfoLogService transInfoLogService;
	
	@Autowired
	private TransMgrService transMgrService;
	
	@Autowired 
	private RefundMapper refundMapper;
	
	@Value("${REFUND_URL}")
	private String REFUND_URL;
	
	/** 去交易变更列表 */
	@RequestMapping(value="/goDoTransInfo")
	public String goDoTransInfo(){
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String dateStr=sdf.format(new Date());
			criteria.getCondition().put("transDateLogStart", dateStr);
			criteria.getCondition().put("transDateLogEnd", dateStr);
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<TransInfoLog> page =transInfoLogService.queryTransInfo(criteria);
			getRequest().setAttribute("page", page);
		}
		return "refund/transInfoLogList";
	}
	
	/** 去添加交易变更页面 */
	@RequestMapping(value="/goDoTransInfoPage")
	public String goDoTransInfoPage(String tradeNo,String transType) throws APIException{
		TransInfo info = transMgrService.queryTransDetailByTradeNo(tradeNo);
		TransInfoLog transInfoLog = new TransInfoLog();
		transInfoLog.setTradeNo(tradeNo);
		transInfoLog.setStatus(0);
		TransInfoLog log = transInfoLogService.queryTransInfoLog(transInfoLog);//查询未审核的记录
		String error = checkTransInfoLogInfo(info,transType);
		if(!StringUtils.isEmpty(error)){
			throw new APIException(error);
		}
		info.setTransInfoLog(log);
		info.setTransType(transType);
		if(!StringUtils.isEmpty(log) && Constants.TRSAN_INFO_TYPE_FROZEN.equals(log.getTransType())){//冻结
			throw new APIException("该订单已有冻结交易变更");
		}
		if(Constants.TRSAN_INFO_TYPE_THAW.equals(transType)){//解冻
			TransInfoLog tLog = new TransInfoLog();
			tLog.setTradeNo(tradeNo);
			tLog.setStatus(2);
			tLog.setTransType(Constants.TRSAN_INFO_TYPE_FROZEN);
			TransInfoLog logInfg = transInfoLogService.queryTransInfoLog(tLog);//查询冻结变更
			if(StringUtils.isEmpty(logInfg)){
				throw new APIException("该订单没有冻结交易变更，不能解冻");
			}
			tLog.setTransType(Constants.TRSAN_INFO_TYPE_THAW);
			TransInfoLog logInfg1 = transInfoLogService.queryTransInfoLog(tLog);//查询冻结变更
//			if(!StringUtils.isEmpty(logInfg1)){
//				throw new APIException("该订单已经解冻");
//			}
			info.setTransInfoLog(logInfg);
		}
		getRequest().setAttribute("info", info);
		return "refund/addTransInfoLog";
	}
	
	/** 去添加退款交易变更页面 */
	@RequestMapping(value="/goDoRefundPage")
	public String goDoRefundPage(String tradeNo,String transType) throws APIException{
		TransInfo info = transMgrService.queryTransDetailByTradeNo(tradeNo);
		String error = checkTransInfoLogInfo(info,transType);
		if(!StringUtils.isEmpty(error)){
			throw new APIException(error);
		}
		checkMoneyTransInfoLog(info,transType);
		info.setTransType(transType);
		getRequest().setAttribute("info", info);
		return "refund/addTransInfoLog";
	}
	
	/** 去审核交易变更页面 */
	@RequestMapping(value="/goDoTransInfoCheckPage")
	public String goDoTransInfoCheckPage(String tradeNo,String transType) throws APIException{
		TransInfo info = transMgrService.queryTransDetailByTradeNo(tradeNo);
		TransInfoLog transInfoLog = new TransInfoLog();
		transInfoLog.setTradeNo(tradeNo);
		transInfoLog.setStatus(0);
		TransInfoLog log = transInfoLogService.queryTransInfoLog(transInfoLog);//查询未审核的记录
		String error = checkTransInfoLogInfo(info,transType);
		if(!StringUtils.isEmpty(error)){
			throw new APIException(error);
		}
		info.setTransInfoLog(log);
		info.setTransType(transType);
		if(StringUtils.isEmpty(log)){
			throw new APIException("该记录没有交易变更，不能审核！");
		}
		getRequest().setAttribute("info", info);
		return "refund/checkTransInfoLog";
	}
	
	/** 校验审核数据 */
	private String checkTransInfoLogInfo(TransInfo info,String transType){
		if(StringUtils.isEmpty(info)){
			return "数据异常，清重试！";
		}
		if(Constants.TRSAN_INFO_TYPE_CHECK.equals(transType)){
			return null;
		}
		// 判断该交易是否有未处理完的变更记录
		if(1 == info.getIsDishonor()){
			return "该交易有拒付交易变更未审核,请先审核";
		}
		if(1 == info.getIsFrozen()){
			return "该交易有冻结交易变更未审核,请先审核";
		}
		if(1 == info.getIsThaw()){
			return "该交易有解冻交易变更未审核,请先审核";
		}
		return null;
	}
	
	private void checkMoneyTransInfoLog(TransInfo info,String transType) throws APIException{
		TransInfoLog transInfoLog = new TransInfoLog();
		transInfoLog.setTradeNo(info.getTradeNo());
		transInfoLog.setStatus(0);
		TransInfoLog log = transInfoLogService.queryTransInfoLog(transInfoLog);//查询未审核的记录
		if(!StringUtils.isEmpty(log)){
			throw new APIException("该交易有未审核交易变更，请先审核");
		}
		CountTransMoney cMoney = transInfoLogService.countTransMoney(info.getTradeNo());
		if(Constants.TRSAN_INFO_TYPE_FROZEN.equals(transType)){//校验冻结
			if(0 != cMoney.getFrozenMoney().compareTo(cMoney.getThawMoney())){
				throw new APIException("该交易已有冻结交易变更，请先解冻后再冻结");
			}
		}
		if(Constants.TRSAN_INFO_TYPE_THAW.equals(transType)){//校验解冻
			if(0 == cMoney.getFrozenMoney().compareTo(cMoney.getThawMoney())){
				throw new APIException("该交易没有冻结交易变更，请先后冻结再解冻");
			}
		}
	}
	
	/** 去冻结交易变更页面 */
	@RequestMapping(value="/goToFrozenInfo")
	public String goToFrozenInfo(String tradeNo,String transType) throws APIException{//只能冻结一笔,并解冻后能再冻结
		TransInfo info = transMgrService.queryTransDetailByTradeNo(tradeNo);
		String error = checkTransInfoLogInfo(info,transType);
		if(!StringUtils.isEmpty(error)){
			throw new APIException(error);
		}
		checkMoneyTransInfoLog(info,transType);
		info.setTransType(transType);
		getRequest().setAttribute("info", info);
		return "refund/addTransInfoLog";
	}
	
	@RequestMapping(value="/insertTransInfoLog")
	public ModelAndView insertTransInfoLog(TransInfoLog transInfoLog)throws APIException{
		TransInfo info = transMgrService.queryTransDetailByTradeNo(transInfoLog.getTradeNo());//获取交易数据
		String error = checkTransInfoLogInfo(info,transInfoLog.getTransType());
		if(!StringUtils.isEmpty(error)){
			return ajaxDoneError(error);
		}
		if(StringUtils.isEmpty(transInfoLog.getTransMoney())){
			return ajaxDoneError("操作金额不能为空！");
		}
//		if(1 > transInfoLog.getTransMoney().compareTo(new BigDecimal(0))){
//			return ajaxDoneError("操作金额不能为0！");
//		}
		CountTransMoney cMoney = transInfoLogService.countTransMoney(transInfoLog.getTradeNo());
		if(!Constants.TRSAN_INFO_TYPE_THAW.equals(transInfoLog.getTransType())){//解冻为增加操作金额操作
//			if(0 == cMoney.getSurplusMoney().compareTo(new BigDecimal(0))){
//				return ajaxDoneError("该交易已无操作金额");
//			}
			if(-1 == cMoney.getSurplusMoney().compareTo(transInfoLog.getTransMoney())){
				return ajaxDoneError("剩余操作金额为："+cMoney.getSurplusMoney()+" ,操作金额不能大于" + cMoney.getSurplusMoney());
			}
		}
		transInfoLog.setCreateBy(getLogAccount().getRealName());
		transInfoLog.setReType("1");
		int i = transInfoLogService.insertTransInfoLog(transInfoLog);
		if(i > 0){
			return ajaxDoneSuccess("变更成功");
		}
		return ajaxDoneError("变更失败");
	}
	
	/** 审核交易变更信息 */
	@RequestMapping(value="/checkTransInfoLog")
	public ModelAndView checkTransInfoLog(TransInfoLog transInfoLog)throws APIException{
		transInfoLog.setCheckBy(getLogAccount().getRealName());
		int y = transInfoLogService.insertChangeTransInfo(transInfoLog);
		if(y > 0){
			return ajaxDoneSuccess("审核成功");
		}
		return ajaxDoneError("审核失败");
	}
	
	@RequestMapping(value="/quertTransInfoLog")
	public String quertTransInfoLog(String tradeNo){
		List<TransInfoLog> list = transInfoLogService.queryTransInfoLogList(tradeNo);
		getRequest().setAttribute("list", list);
		return "refund/queryTransInfoLogList";
	}
	
	/** 查询交易异常单信息 */
	@RequestMapping(value="/selectTransInfoLogList")
	public String selectTransInfoLogList(){
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String transDateStart=sdf.format(date);
			String transDateEnd=sdf.format(date);
			criteria.getCondition().put("transDateLogStart", transDateStart);
			criteria.getCondition().put("transDateLogEnd", transDateEnd);
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<TransInfoLog> page =transInfoLogService.selectTransInfoList(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "refund/selectTransInfoLogList";
	}
	
	/** 异常单导出 */
	@RequestMapping(value="/exportTransLogList")
	public void exportTransLogList(HttpServletResponse resp) throws IOException, RowsExceededException, WriteException{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "transLogList.xls" ); 
		List<TransInfo> list = transInfoLogService.exportTransLogList(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("异常交易列表", 0);
		String[] headerName = { "交易流水号","商户号","卡号","授权号","标签金额","交易金额","异常金额","操作状态","持卡人邮箱","交易时间","风险分数","商户订单号",
				"银行","卡种","支付网站","划款状态","勾兑状态","支付国家","账单国家","账单地址","持卡人姓名","持卡人电话","收货国家","收货地址","收货邮编","退款状态"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			TransInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getTradeNo()));//交易流水号
			sheet.addCell( new Label(col++, row, info.getMerNo()));//商户号
			sheet.addCell( new Label(col++, row, info.getCheckNo()));//卡号
			sheet.addCell( new Label(col++, row, info.getTerNo()));//授权号
			sheet.addCell( new Label(col++, row, info.getMerBusCurrency()+ " " + info.getMerTransAmount()));//标签金额
			sheet.addCell( new Label(col++, row, info.getBankCurrency()+ " "+ info.getBankTransAmount()));//交易金额
			sheet.addCell( new Label(col++, row, info.getTransCurrency() + " " + info.getTransMoney()));//异常金额
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("TRANS_LOG_TYPE",info.getTransLogType(),"未知类型")
							+BaseDataListener.getStringColumnKey("CHECK_STATUS",info.getStatus()+"","未知类型")));//操作类型
			sheet.addCell( new Label(col++, row, info.getCardEmail()));//持卡人邮箱
			sheet.addCell( new Label(col++, row, info.getTransDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getTransDate()):""));//交易时间
			sheet.addCell( new Label(col++, row, info.getTerNo()));//风险分数
			sheet.addCell( new Label(col++, row, info.getOrderNo()));//商户订单号
			sheet.addCell( new Label(col++, row, info.getBankName()));//银行g
			sheet.addCell( new Label(col++, row, info.getCardType()));//卡种
			sheet.addCell( new Label(col++, row, info.getPayWebSite()));//支付网站
			sheet.addCell( new Label(col++, row, info.getBatchNo() !=null?"已划款":"未划款"));//划款状态
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("TRANSCHECK",info.getIschecked()+"","未知类型")));//勾兑状态
			sheet.addCell( new Label(col++, row, info.getCardCountry()));//支付国家
			sheet.addCell( new Label(col++, row, info.getCardCountry()));//账单国家
			sheet.addCell( new Label(col++, row, info.getCardAddress()));//账单地址
			sheet.addCell( new Label(col++, row, info.getCardFullName()));//持卡人姓名
			sheet.addCell( new Label(col++, row, info.getCardFullPhone()));//持卡人电话
			sheet.addCell( new Label(col++, row, info.getGrCountry()));//收货国家
			sheet.addCell( new Label(col++, row, info.getGrAddress()));//收货地址
			sheet.addCell( new Label(col++, row, info.getGrZipCode()));//收货邮编
			sheet.addCell( new Label(col++, row, info.getRefundStatus()));//收货邮编
		}
		book.write();
		book.close();
	}
	
	/** 去上传流水号页面 */
	@RequestMapping(value="/goUploadTradeNo")
	public String goUploadTradeNo(){
		return "refund/uploadTradeNo";
	}
	
	/** 上传流水号 */
	@RequestMapping(value="/uplodeTradeNo")
	public ModelAndView uplodeTradeNo(DefaultMultipartHttpServletRequest request) throws IOException, ParseException, ISOException{
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		List<TransInfoLog> list = new ArrayList<TransInfoLog>();
		if(null != files){
			log.info("上传了"+files.size()+"个文件。");
			for(MultipartFile file :files){
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));//构造一个BufferedReader类来读取文件
				String line = br.readLine();
				while(null != line){
					log.info("line:"+line);
					String [] fields = line.split(",");
					if(fields.length == 1){
						int index = 0;
						TransInfoLog log = new TransInfoLog();
						log.setTradeNo(fields[index++]);
						list.add(log);
					}else{
						log.info("错误line:"+line);
					}
					line = br.readLine();
				}
			}
		}
		return ajaxDoneSuccess(JSON.toJSONString(list));
	}
	
	/** 流水号异常单导出 */
	@RequestMapping(value="/exportTradeNoTransLogList")
	public void exportTradeNoTransLogList(HttpServletResponse resp,String tradeList) throws IOException, RowsExceededException, WriteException{
		if(StringUtils.isEmpty(tradeList)){
			return;
		}
		String [] str = chackTradeNo(tradeList);
		List<TransInfo> list = new ArrayList<TransInfo>();
		for(String s:str){
			List<TransInfo> info = transInfoLogService.exportTradeNoTransLogList(s);
			if(!StringUtils.isEmpty(info)){
				for(TransInfo li:info){
					list.add(li);
				}
			}
		}
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "transLogList.xls" ); 
		//List<TransInfo> list = transInfoLogService.exportTransLogList(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("异常交易列表", 0);
		String[] headerName = { "交易流水号","商户号","卡号","授权号","标签金额","交易金额","异常金额","操作状态","持卡人邮箱","交易时间","风险分数","商户订单号",
				"银行","卡种","支付网站","划款状态","勾兑状态","支付国家","账单国家","账单地址","持卡人姓名","持卡人电话","收货国家","收货地址","收货邮编"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			TransInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getTradeNo()));//交易流水号
			sheet.addCell( new Label(col++, row, info.getMerNo()));//商户号
			sheet.addCell( new Label(col++, row, info.getCheckNo()));//卡号
			sheet.addCell( new Label(col++, row, info.getTerNo()));//授权号
			sheet.addCell( new Label(col++, row, info.getMerBusCurrency()+ " " + info.getMerTransAmount()));//标签金额
			sheet.addCell( new Label(col++, row, info.getBankCurrency()+ " "+ info.getBankTransAmount()));//交易金额
			sheet.addCell( new Label(col++, row, info.getTransCurrency() + " " + info.getTransMoney()));//异常金额
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("TRANS_LOG_TYPE",info.getTransLogType(),"未知类型")
							+BaseDataListener.getStringColumnKey("CHECK_STATUS",info.getStatus()+"","未知类型")));//操作类型
			sheet.addCell( new Label(col++, row, info.getCardEmail()));//持卡人邮箱
			sheet.addCell( new Label(col++, row, info.getTransDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getTransDate()):""));//交易时间
			sheet.addCell( new Label(col++, row, info.getTerNo()));//风险分数
			sheet.addCell( new Label(col++, row, info.getOrderNo()));//商户订单号
			sheet.addCell( new Label(col++, row, info.getBankName()));//银行g
			sheet.addCell( new Label(col++, row, info.getCardType()));//卡种
			sheet.addCell( new Label(col++, row, info.getPayWebSite()));//支付网站
			sheet.addCell( new Label(col++, row, info.getBatchNo() !=null?"已划款":"未划款"));//划款状态
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("TRANSCHECK",info.getIschecked()+"","未知类型")));//勾兑状态
			sheet.addCell( new Label(col++, row, info.getCardCountry()));//支付国家
			sheet.addCell( new Label(col++, row, info.getCardCountry()));//账单国家
			sheet.addCell( new Label(col++, row, info.getCardAddress()));//账单地址
			sheet.addCell( new Label(col++, row, info.getCardFullName()));//持卡人姓名
			sheet.addCell( new Label(col++, row, info.getCardFullPhone()));//持卡人电话
			sheet.addCell( new Label(col++, row, info.getGrCountry()));//收货国家
			sheet.addCell( new Label(col++, row, info.getGrAddress()));//收货地址
			sheet.addCell( new Label(col++, row, info.getGrZipCode()));//收货邮编
		}
		book.write();
		book.close();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String [] chackTradeNo(String tradeNo){
		String[] check=tradeNo.split(","); 
		ArrayList list=new ArrayList();
       for(int i=0;i<check.length;i++){
            if(!list.contains(check[i]))
            list.add(check[i]);       
        } 
        System.out.println("hehe"+list.size()+"haha");
        String [] li = new String[list.size()];
        for(int i=0;i<list.size();i++){
        	System.out.println("hehe"+list.get(i));  
        	li[i] = list.get(i)+"";
        }
        return li;
	}
	/**
	 * 跳转到再次退款页面
	 * */
	@RequestMapping(value="/goDoRefundAgain")
	public String goDoRefundAgain(RefundAgainInfo info){
		getRequest().setAttribute("info", info);
		return "refund/doRefundAgain";
	}
	
	/**
	 * 再次退款
	 * */
	@RequestMapping(value="/doRefundAgain")
	public ModelAndView doRefundAgain(TransInfoLog log) throws APIException {
		RefundInfo refundInfo=setMoneyInfo(log);
		RefundModel info=new RefundModel();
		System.out.println(refundInfo.getTransLogId());
//		String shaKey=refundMapper.getMerchantShaKeyByTradeNo(refundInfo.getTradeNo());
//		if(null==shaKey){
//			throw new APIException("1001","商户shaKey未设置");
//		}
		
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
		map.put("key", log.getKey());
		map.put("mid", log.getMid());
		System.out.println(sb.toString());
//		sb.append("shaKey="+shaKey);
		String md5=MD5Encryption.getSHA256Encryption(sb.toString());
		System.out.println(md5);
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
			System.out.println(statusCode);
			HttpEntity entity = resp.getEntity();
			InputStream inSm = entity.getContent();
			Scanner inScn = new Scanner(inSm);
			String resultJson = null;
			if (inScn.hasNextLine()) {
				resultJson = inScn.nextLine();
			}
			System.out.println(resultJson);
			JSONObject json = JSON.parseObject(resultJson);
			respStatus=json.getString("respStatus");
			if(null==respStatus||"".equals(respStatus)){
				logger.info(refundInfo.getTradeNo()+"  银行返回信息失败！");
				//更新日志表退款状态
				refundMapper.updateTransLogInfoById(refundInfo.getTransLogId(),"提交银行失败");
			}else{
//				info.setRefundCode(json.getString("respCode"));
//				info.setRefundStatus(json.getString("respStatus"));
//				info.setRespMsg(json.getString("respMsg"));
				info=JSON.toJavaObject(json, RefundModel.class);
				if("0000".equals(info.getRespCode())){
					info.setRespMsg(info.getRespCode()+":退款成功");
					refundMapper.updateTransLogInfoById(refundInfo.getTransLogId(),info.getRespMsg());
					refundMapper.updateTransInfoByLogId(refundInfo.getTransLogId(),info);
					return ajaxDoneSuccess("退款成功");
				}else if("P000".equals(info.getRespCode())){
					info.setRespMsg(info.getRespCode()+":退款中");
					refundMapper.updateTransLogInfoById(refundInfo.getTransLogId(),info.getRespMsg());
					refundMapper.updateTransInfoByLogId(refundInfo.getTransLogId(),info);
					return ajaxDoneSuccess("退款中");
				}else{
					info.setRespMsg(json.getString("respMsg")+":退款失败");
					refundMapper.updateTransLogInfoById(refundInfo.getTransLogId(),info.getRespMsg());
					refundMapper.updateTransInfoByLogId(refundInfo.getTransLogId(),info);
					return ajaxDoneError("退款失败:"+info.getRespMsg());
				}
				//更新日志表退款状态
			}
			throw new APIException("2002", "提交银行失败");
		} catch (Exception e) {
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
	
	private RefundInfo setMoneyInfo(TransInfoLog log){
		RefundInfo rInfo = new RefundInfo();
		rInfo.setTradeNo(log.getTradeNo());
		rInfo.setTransLogId(log.getId());
		rInfo.setRefundReason("再次退款");
		TransInfo info = transMgrService.queryTransDetailByTradeNo(log.getTradeNo());
		rInfo.setMerNo(info.getMerNo());
		rInfo.setTerNo(info.getTerNo());
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    String  s = format1.format(info.getTransDate());
		rInfo.setApplyDate(s);
		rInfo.setRefundAmount(log.getBankRefundTransAmount()+"");
		rInfo.setRefundCurrency(info.getBankCurrency());
		rInfo.setBusAmount(log.getBankTransAmount()+"");
		rInfo.setBusCurrency(info.getBankCurrency());
		return rInfo;
	}
	@RequestMapping("/goUploadbatchTransChange")
	public String goUploadbatchTransChange(){
		return "refund/uploadbatchTransChange";
	}
	
	/**
	 * 接收变更文件
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws ISOException
	 * @throws APIException 
	 */
	@RequestMapping(value = "/updateBatchTransChange")
	public ModelAndView updateBatchTransChange(
			DefaultMultipartHttpServletRequest request) throws IOException,
			ParseException, ISOException, APIException {
		HttpSession session=request.getSession();
		session.removeAttribute("batchTransChangeErrorInfo");
		List<String> batchTransChangeErrorInfo=new ArrayList<String>();
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
					if (fields.length == 3) {
						int index = 0;
						TransInfoLog transInfoLog = new TransInfoLog();
						transInfoLog.setTradeNo(fields[index++]);
						String amount=fields[index++];
						String type=fields[index++];
						transInfoLog.setTransMoney(new BigDecimal(amount));
						if("1".equals(type)){//退款
							transInfoLog.setTransType(Constants.TRSAN_INFO_TYPE_REFUND);
							transInfoLog.setRefundAmount(amount);
						}else if("2".equals(type)){//拒付
							transInfoLog.setTransType(Constants.TRSAN_INFO_TYPE_DISHONOR);
							transInfoLog.setDishonorAmount(amount);
						}else if("3".equals(type)){//冻结
							transInfoLog.setTransType(Constants.TRSAN_INFO_TYPE_FROZEN);
							transInfoLog.setFrozenAmount(amount);
						}else if("4".equals(type)){//解冻
							transInfoLog.setTransType(Constants.TRSAN_INFO_TYPE_THAW);
							transInfoLog.setThawAmount(amount);
						}else{
							batchTransChangeErrorInfo.add(transInfoLog.getTradeNo()+"变更类型错误!");
						}
						TransInfo info = transMgrService.queryTransDetailByTradeNo(transInfoLog.getTradeNo());//获取交易数据
						if(info==null){
							batchTransChangeErrorInfo.add(transInfoLog.getTradeNo()+"不存在！");
							line=br.readLine();
							continue;
						}
						if(!"00".equals(info.getRespCode())){
							batchTransChangeErrorInfo.add(transInfoLog.getTradeNo()+"为失败订单！");
							line=br.readLine();
							continue;
						}
						transInfoLog.setTransCurrency(info.getMerBusCurrency());
						String error = checkTransInfoLogInfo(info,transInfoLog.getTransType());
						if(!StringUtils.isEmpty(error)){
							batchTransChangeErrorInfo.add(transInfoLog.getTradeNo()+error);
							line=br.readLine();
							continue;
						}
						if(StringUtils.isEmpty(transInfoLog.getTransMoney())){
							batchTransChangeErrorInfo.add(transInfoLog.getTradeNo()+"操作金额不能为空！");
							line=br.readLine();
							continue;
						}
						if(1 > transInfoLog.getTransMoney().compareTo(new BigDecimal(0))){
							batchTransChangeErrorInfo.add(transInfoLog.getTradeNo()+"操作金额不能为0！");
							line=br.readLine();
							continue;
						}
						CountTransMoney cMoney = transInfoLogService.countTransMoney(transInfoLog.getTradeNo());
						if(!Constants.TRSAN_INFO_TYPE_THAW.equals(transInfoLog.getTransType())){//解冻为增加操作金额操作
							if(0 == cMoney.getSurplusMoney().compareTo(new BigDecimal(0))){
								batchTransChangeErrorInfo.add(transInfoLog.getTradeNo()+"已无操作金额");
								line=br.readLine();
								continue;
							}
							if(-1 == cMoney.getSurplusMoney().compareTo(transInfoLog.getTransMoney())){
								batchTransChangeErrorInfo.add(transInfoLog.getTradeNo()+"剩余操作金额为："+cMoney.getSurplusMoney()+" ,操作金额不能大于" + cMoney.getSurplusMoney());
								line=br.readLine();
								continue;
							}
						}else{
							if((cMoney.getFrozenMoney().doubleValue()+cMoney.getThawMoney().doubleValue())!=Double.parseDouble(transInfoLog.getThawAmount())){
								batchTransChangeErrorInfo.add(transInfoLog.getTradeNo()+"剩余冻结金额："+(cMoney.getFrozenMoney().doubleValue()+cMoney.getThawMoney().doubleValue())*-1+" ,解冻金额必须等于" +(cMoney.getFrozenMoney().doubleValue()+cMoney.getThawMoney().doubleValue())*-1);
								line=br.readLine();
								continue;
							}
						}
						transInfoLog.setCreateBy(getLogAccount().getRealName());
						transInfoLog.setReType("1");
						int i = transInfoLogService.insertTransInfoLog(transInfoLog);
						if(i > 0){
							batchTransChangeErrorInfo.add(transInfoLog.getTradeNo()+"变更成功");
							line=br.readLine();
							continue;
						}else{
							batchTransChangeErrorInfo.add(transInfoLog.getTradeNo()+"变更失败");
							line=br.readLine();
							continue;
						}
						
						
					} else {
						batchTransChangeErrorInfo.add("错误line:" + line);
						log.info("错误line:" + line);
					}
					line = br.readLine();
				}
			}
		}
		session.setAttribute("batchTransChangeErrorInfo", batchTransChangeErrorInfo);
		return ajaxDoneSuccess("上传成功。");
	}
	// 下载方法
		@RequestMapping("/downloadbatchTransChangeErrorInfo")
		public void downloadbatchTransChangeErrorInfo(HttpServletResponse resp) {
			resp.setContentType("application/zip");
			resp.addHeader("Content-Disposition", "attachment;filename=website.txt");
			OutputStream outp = null;
			try {
				List<String> list=(List<String>) getRequest().getSession().getAttribute("batchTransChangeErrorInfo");

				outp = resp.getOutputStream();
				PrintWriter pw = new PrintWriter(outp);
				for(String str:list){
					pw.print(str+"\r\n");
				}
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
		
	@RequestMapping(value="/batchCheckTransChangeInfo")
	public ModelAndView batchCheckTransChangeInfo(String[] tradeNos,int toBank) throws APIException{
		String transType="check";
		int count=0;
		for(String tradeNo:tradeNos){
			TransInfoLog transInfoLog = new TransInfoLog();
			transInfoLog.setTradeNo(tradeNo);
			transInfoLog.setStatus(0);
			TransInfoLog log = transInfoLogService.queryTransInfoLog(transInfoLog);//查询未审核的记录
			log.setTransType(transType);
			log.setStatus(2);
			log.setCheckBy(getLogAccount().getRealName());
			log.setToBank(toBank);
			count+= transInfoLogService.insertChangeTransInfo(log);
		}
		return ajaxDoneSuccess("复核成功"+count+"条记录");
	}
	
	
}
