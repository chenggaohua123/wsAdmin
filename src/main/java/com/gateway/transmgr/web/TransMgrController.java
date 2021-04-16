package com.gateway.transmgr.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
import org.apache.http.util.EntityUtils;
import org.jpos.iso.ISOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.gateway.bankmgr.mapper.BankMgrDao;
import com.gateway.bankmgr.model.BankInfo;
import com.gateway.bankmgr.model.CurrencyInfo;
import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.BaseDataListener;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excel.BIRow;
import com.gateway.common.excel.BISheet;
import com.gateway.common.excel.BIWorkbook;
import com.gateway.common.excetion.APIException;
import com.gateway.common.utils.Funcs;
import com.gateway.common.utils.JSONPParserUtil;
import com.gateway.common.utils.Tools;
import com.gateway.fraud.model.WhiteListInfo;
import com.gateway.loginmgr.model.UserInfo;
import com.gateway.settlemgr.model.GwSettleTransInfo;
import com.gateway.settlemgr.service.SettleMgrService;
import com.gateway.sysmgr.model.CardBinInfo;
import com.gateway.transchangemgr.service.TransChangeService;
import com.gateway.transmgr.model.BankSettleDetail;
import com.gateway.transmgr.model.MulTransInfo;
import com.gateway.transmgr.model.SettleTransInfo;
import com.gateway.transmgr.model.TransCallbackInfo;
import com.gateway.transmgr.model.TransChangeInfo;
import com.gateway.transmgr.model.TransCount;
import com.gateway.transmgr.model.TransDetailInfo;
import com.gateway.transmgr.model.TransInfo;
import com.gateway.transmgr.model.TransLogInfo;
import com.gateway.transmgr.model.TransRecordInfo;
import com.gateway.transmgr.model.WhiteDishonorInfo;
import com.gateway.transmgr.service.TransMgrService;

@Controller
@RequestMapping(value="/transmgr")
public class TransMgrController extends BaseController{
	
	@Resource
	private TransMgrService transMgrService;
	
	@Resource
	private SettleMgrService settleMgrService;
	
	@Autowired
	private TransChangeService transChangeServiceImpl;
	@Resource
	public BankMgrDao bankMgrDao;
	
	@Value("${PAYMENT_ECP_CAPTURE_URL}")
	private String PAYMENT_ECP_CAPTURE_URL;
	
	@Value("${PAYMENT_ECP_VOID_URL}")
	private String PAYMENT_ECP_VOID_URL;
	
	/**
	 * 交易对账
	 * @return
	 */
	@RequestMapping(value="/uploadCheckFile")
	public String uploadCheckFile(){
		return "transmgr/uploadCheckFile";
	}
	
	/** 交易导出下载上传模板 */
	@RequestMapping(value = "/downloadTransModel")
	public void downloadTransModel(HttpServletResponse resp,String type){
		if("2".equals(type)){
			ArrayList<String> strArray = new ArrayList<String> ();
			strArray.add("流水号,银行交易金额");
			strArray.add("199227530825,10.01");
			strArray.add("199227530826,10.01");
			downloadFile(resp, "transModel.txt", "modelType:orderNo", strArray);
			return;
		}
		if("3".equals(type)){
			ArrayList<String> strArray = new ArrayList<String> ();
			strArray.add("前六后四卡号,交易日期,银行交易金额,授权码");
			strArray.add("123456****1234,20150808,10.00,6532");
			strArray.add("123456****1234,20150808,10.01,3214");
			downloadFile(resp, "transModel.txt", "modelType:cardInfo", strArray);
			return;
		}
		if("4".equals(type)){
			ArrayList<String> strArray = new ArrayList<String> ();
			strArray.add("全卡号,交易日期,银行交易金额,授权码");
			strArray.add("1234569876541234,20150808,10.00,6532");
			strArray.add("1234569876541234,20150808,10.01,3214");
			downloadFile(resp, "transModel.txt", "modelType:cardNo", strArray);
		}
	}
	
	/**
	 * 交易查询
	 * @return
	 */
	@RequestMapping(value="/getTransList")
	public String getTransList(){
		Criteria criteria=getCriteria();
        /*if(null==criteria.getCondition().get("transType")){
        	criteria.getCondition().put("transType", "sales");
        	Map<String, String> par=new HashMap<String, String>();
        	par.put("transType", "sales");
        	getRequest().setAttribute("param", par);
        }*/
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String transDateStart=sdf.format(date);
			criteria.getCondition().put("transDateStart", transDateStart+" 00:00:00");
			criteria.getCondition().put("transDateEnd", transDateStart+" 23:59:59");
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<TransInfo> page = transMgrService.getTransList(criteria);
			//List<TransInfo>  Total=transMgrService.getTransAmountList(criteria);
			
			TransCount transCount=transMgrService.queryTransCount(criteria);
			for(TransInfo info:page.getData()){
				try {
					info.setCbInfo(transMgrService.queryCardBinInfoByBin(Funcs.decrypt(info.getCheckNo())));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			getRequest().setAttribute("page", page);
			//getRequest().setAttribute("Totallist", Total);
			getRequest().setAttribute("transCount", transCount);
		}
		return "transmgr/transList";
	}
	
	/**
	 * 交易查询-财务专用
	 * @return
	 */
	@RequestMapping(value="/getTransListFinance")
	public String getTransListFinance(){
		Criteria criteria=getCriteria();
        /*if(null==criteria.getCondition().get("transType")){
        	criteria.getCondition().put("transType", "sales");
        	Map<String, String> par=new HashMap<String, String>();
        	par.put("transType", "sales");
        	getRequest().setAttribute("param", par);
        }*/
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String transDateStart=sdf.format(date);
			criteria.getCondition().put("transDateStart", transDateStart+" 00:00:00");
			criteria.getCondition().put("transDateEnd", transDateStart+" 23:59:59");
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<TransInfo> page = transMgrService.getTransList(criteria);
			//List<TransInfo>  Total=transMgrService.getTransAmountList(criteria);
			
			TransCount transCount=transMgrService.queryTransCount(criteria);
			for(TransInfo info:page.getData()){
				try {
					info.setCbInfo(transMgrService.queryCardBinInfoByBin(Funcs.decrypt(info.getCheckNo())));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			getRequest().setAttribute("page", page);
			//getRequest().setAttribute("Totallist", Total);
			getRequest().setAttribute("transCount", transCount);
		}
		return "transmgr/transListFinance";
	}
	
	/**
	 * 预授权确认
	 * @param tradeNo
	 * @param orderRespCode 订单状态
	 * @return
	 * @throws APIException
	 */
	@ResponseBody
	@RequestMapping(value="/capture")
	public ModelAndView capture(String  tradeNo,String orderRespCode) throws APIException {
		if(!"0000".equals(orderRespCode)){
			return ajaxDoneError("只能对预授权成功的订单进行操作");
		}
		DefaultHttpClient httpClient = Tools.getHttpClient(); // 建立客户端连接
		HttpPost postMethod = new HttpPost(PAYMENT_ECP_CAPTURE_URL);
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("tradeNo", tradeNo));
		postMethod.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		try {
			HttpResponse resp = httpClient.execute(postMethod); // 处理发送
			HttpEntity entity = resp.getEntity();
			String entityStr = EntityUtils.toString(entity, "UTF-8");
			System.out.println(entityStr);
			JSONObject json = (JSONObject) JSONPParserUtil.parseJSONP(entityStr);
			String respCode=json.getString("respCode");
			String respMsg=json.getString("respMsg");
			if("00".equals(respCode)){
				return ajaxDoneSuccess("预授权确认成功:"+respMsg);
			}
			return ajaxDoneError("预授权确认失败:"+respMsg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				postMethod.clone();
			} catch (CloneNotSupportedException e) {
				return ajaxDoneError("预授权确认失败:"+e.getMessage());
			}
		}
		return ajaxDoneError("预授权确认失败");
	}
	
	/**
	 * 预授权撤销
	 * @param tradeNo 流水号
	 * @param orderRespCode 订单状态
	 * @return
	 * @throws APIException
	 */
	@ResponseBody
	@RequestMapping(value="/voidPre")
	public ModelAndView voidPre(String  tradeNo,String orderRespCode) throws APIException {
		if(!"0000".equals(orderRespCode)){
			return ajaxDoneError("只能对预授权成功的订单进行操作");
		}
		DefaultHttpClient httpClient = Tools.getHttpClient(); // 建立客户端连接
		HttpPost postMethod = new HttpPost(PAYMENT_ECP_VOID_URL);
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("tradeNo", tradeNo));
		postMethod.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		try {
			HttpResponse resp = httpClient.execute(postMethod); // 处理发送
			HttpEntity entity = resp.getEntity();
			String entityStr = EntityUtils.toString(entity, "UTF-8");
			System.out.println(entityStr);
			JSONObject json = (JSONObject) JSONPParserUtil.parseJSONP(entityStr);
			String respCode=json.getString("respCode");
			String respMsg=json.getString("respMsg");
			if("00".equals(respCode)){
				return ajaxDoneSuccess("预授权撤销成功:"+respMsg);
			}
			return ajaxDoneError("预授权撤销失败:"+respMsg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				postMethod.clone();
			} catch (CloneNotSupportedException e) {
				return ajaxDoneError("预授权撤销失败:"+e.getMessage());
			}
		}
		return ajaxDoneError("预授权撤销失败");
	}
	
	/**
	 * 测试交易查询
	 * @return
	 */
	@RequestMapping(value="/getTestTransList")
	public String getTestTransList(){
		Criteria criteria=getCriteria();
		criteria.put("transType", "test");
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String transDateStart=sdf.format(date);
			criteria.getCondition().put("transDateStart", transDateStart+" 00:00:00");
			criteria.getCondition().put("transDateEnd", transDateStart+" 23:59:59");
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<TransInfo> page = transMgrService.getTransList(criteria);
			//List<TransInfo>  Total=transMgrService.getTransAmountList(criteria);
			getRequest().setAttribute("page", page);
		}
		return "transmgr/testTransList";
	}
	/**
	 * 接收勾兑文件
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws ISOException 
	 */
	@RequestMapping(value="/acceptCheckFile")
	public ModelAndView acceptCheckFile(DefaultMultipartHttpServletRequest request) throws Exception{
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		if(null != files){
			log.info("上传了"+files.size()+"个文件。");
			List<TransInfo> list = new ArrayList<TransInfo>();
			StringBuffer sb = new StringBuffer();//未匹配数据
			StringBuffer transFail=new StringBuffer();
			List<Integer> tradeNoFails=new ArrayList<Integer>();
			for(MultipartFile file :files){
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(),"GBK"));//构造一个BufferedReader类来读取文件
				String line = br.readLine();
				int length = Tools.replaceBlank(line).length();//第一列
				log.info("第一列：line:"+line+"字符长度为："+length);
				String [] strInfo = line.split(":");
				String name = "";
				if(!StringUtils.isEmpty(strInfo) && 2 == strInfo.length){
					name = strInfo[1];
				}
				line = br.readLine();//读取第二列
				line = br.readLine();//读取第3列
				int lineCount = 3;//从第3行开始读
				Criteria criteria = getCriteria();
				criteria.getCondition().put("ischecked", "0");
				while(null != line){
					log.info("line:"+line);
					String [] fileds = line.split(",");
					if ("orderNo".equals(name)) {
						log.info( "在第一列长度为10 的循环里面，第"+lineCount+ "行数据：" + line);
						int index = 0;
						criteria.getCondition().put("tradeNo", fileds[index++]);
						criteria.getCondition().put("bankTransAmount", fileds[index++]);
						List<TransInfo> transInfo = transChangeServiceImpl.queryTransInfo(criteria);
						if(!org.springframework.util.StringUtils.isEmpty(transInfo)&& 0 != transInfo.size()) {
							for(TransInfo li:transInfo){
								list.add(li);
								if(!"00".equals(li.getRespCode())){
									tradeNoFails.add(li.getId());
									transFail.append("第"+lineCount+ "行数据：" + line+";");
								}
							}
							log.info("上传数据已匹配");
						}else{
							log.info("未匹配上传数据");
							sb.append("第"+lineCount+ "行数据：" + line+";");
						}
					}
					if ("cardInfo".equals(name)) {
						log.info( "在第一列长度为22 的循环里面，第"+lineCount+ "行数据：" + line);
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
						criteria.getCondition().put("bankTransAmount", fileds[index++]);//银行交易金额
						if(4 == fileds.length){
							criteria.getCondition().put("autoCode", fileds[index++]);//授权码
						}
						List<TransInfo> transInfo = transChangeServiceImpl.queryTransInfo(criteria);
						if(!org.springframework.util.StringUtils.isEmpty(transInfo)&& 0 != transInfo.size()) {
							for(TransInfo li:transInfo){
								list.add(li);
								if(!"00".equals(li.getRespCode())){
									tradeNoFails.add(li.getId());
									transFail.append("第"+lineCount+ "行数据：" + line+";");
								}
							}
							log.info("上传数据已匹配");
						}else{
							log.info("未匹配上传数据");
							sb.append("第"+lineCount+ "行数据：" +line+";");
						}
					}
					if ("cardNo".equals(name)) {
						log.info( "在第一列长度为19 的循环里面，第"+lineCount+ "行数据：" + line);
						int index = 0;
						criteria.getCondition().put("checkToNo", Funcs.eccryptSHA(fileds[index++]));//全卡号
						criteria.getCondition().put("transDate", fileds[index++]);//交易时间
						criteria.getCondition().put("bankTransAmount", fileds[index++]);//银行交易金额
						if(4 == fileds.length){
							criteria.getCondition().put("autoCode", fileds[index++]);//授权码
						}
						List<TransInfo> transInfo = transChangeServiceImpl.queryTransInfo(criteria);
						if(!org.springframework.util.StringUtils.isEmpty(transInfo)&& 0 != transInfo.size()) {
							for(TransInfo li:transInfo){
								list.add(li);
								if(!"00".equals(li.getRespCode())){
									tradeNoFails.add(li.getId());
									transFail.append("第"+lineCount+ "行数据：" + line+";");
								}
							}
							log.info("上传数据已匹配");
						}else{
							log.info("未匹配上传数据");
							sb.append("第"+lineCount+ "行数据：" +line+";");
						}
					}
					line = br.readLine();
					lineCount++;
				}
			}
			//查询重复的 订单 已卡号为准
			List<String> duplicateTradeNos=new ArrayList<String>();
			HashMap<String, String> map=new HashMap<String, String>();
			for(TransInfo info:list){
				String value=map.get(info.getCheckToNo());
				if(null==value){
					map.put(info.getCheckToNo(), info.getTradeNo());
				}else{
					map.put(info.getCheckToNo(), value+","+info.getTradeNo());
				}
			}
			for(String str:map.values()){
				if(str.contains(",")){
					for(String s:str.split(",")){
						duplicateTradeNos.add(s);
					}
				}
			}
			getRequest().getSession().setAttribute("duplicateTradeNos", duplicateTradeNos);
			getRequest().getSession().setAttribute("list", list);
			
			getRequest().getSession().setAttribute("sb", sb + "");
			getRequest().getSession().setAttribute("tradeNoFails", tradeNoFails);
			getRequest().getSession().setAttribute("transFail", transFail.toString());
		}
		return ajaxDoneSuccess("上传成功。返回信息");
	}
	
	@RequestMapping(value ="/goQueryAcceptCheckPage")
	public String goQueryAcceptCheckPage(){
		@SuppressWarnings("unchecked")
		List<TransInfo> list = (List<TransInfo>)getRequest().getSession().getAttribute("list");
		getRequest().getSession().removeAttribute("list");
		String sb = (String)getRequest().getSession().getAttribute("sb");
		getRequest().getSession().removeAttribute("sb");
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("sb", sb);
		return "transmgr/acceptCheckPageList";
	}
	
	/** 批量勾兑 */
	@ResponseBody
	@RequestMapping(value ="/batchAcceptCheckUpdateById")
	public ModelAndView batchAcceptCheckUpdateById(String [] ids){
		int totalCount = ids.length;
		List<Integer> tradeIdFails = (List<Integer>)getRequest().getSession().getAttribute("tradeNoFails");
		getRequest().getSession().removeAttribute("tradeNoFails");
		int count = transMgrService.batchAcceptCheckUpdateById(ids);
		List<Integer> checkedFailTransId=new ArrayList<Integer>();
		if(tradeIdFails != null&& tradeIdFails.size()>0){
			for(String id:ids){
				int idInt=Integer.parseInt(id);
				if(tradeIdFails.contains(idInt)){
					checkedFailTransId.add(idInt);
				}
			}
		}
		if(checkedFailTransId.size()>0){
			transMgrService.updateCheckedFailTransIds(checkedFailTransId,getLogAccount().getRealName());
		}
//		String str = transMgrService.checkAcceptDate(ids);
		//getRequest().setAttribute("date", str);
		/*ModelAndView modelView = new ModelAndView("ajaxDone");
		modelView.addObject("statusCode", 200);
		modelView.addObject("message","勾兑条数为：" + totalCount +" ;勾兑成功条数为：" + count);//感谢二楼的指正，已修改
		modelView.addObject("date",str);
		return modelView;*/
		return ajaxDoneSuccess("勾兑条数为：" + totalCount +" ;勾兑成功条数为：" + count);
	}
	
	public void BankSettleDetail(){//原对账
		/*BankSettleDetail info = new BankSettleDetail();
		info.setSettleDate(new Timestamp(df1.parse(fileds[index++]).getTime()));
		info.setMerchantNo(fileds[index++]);
		info.setTerNo(fileds[index++]);
		info.setMsgType(fileds[index++]);
		info.setPscode(fileds[index++]);
		info.setCardNo(fileds[index++]);
		info.setTransAmount(new  BigDecimal(fileds[index++]));
		info.setPosNo(fileds[index++]);
		info.setTransDate(new Timestamp(df.parse(Calendar.getInstance().get(Calendar.YEAR)+fileds[index++]).getTime()));
		info.setServiceCode(fileds[index++]);
		info.setTranstypecode(fileds[index++]);
		info.setIsuingBank(fileds[index++]);
		info.setRefNo(fileds[index++]);
		info.setOriPosNo(fileds[index++]);
		info.setOriRefNo(fileds[index++]);
		info.setOriTransDate(new Timestamp(df.parse(Calendar.getInstance().get(Calendar.YEAR)+fileds[index++]).getTime()));
		info.setForAmount(new  BigDecimal(fileds[index++]));
		String posNo = ISOUtil.takeLastN(info.getRefNo(), 6);
		info.setRefNo(ISOUtil.takeLastN(df2.format(info.getTransDate()),6) + info.getPosNo());*/
		/*info.setPosNo(posNo);*/
		/*new Thread(new Runnable() {
			public void run() {
				int count = transMgrService.saveBankSettleDetail(settleList);
				log.info("更新记录数："+count);
			}
		}).start();*/
	}

	/**
	 * 银行结算记录
	 * @return
	 */
	@RequestMapping(value="/queryBankSettleDetail")
	public String queryBankSettleDetail(){
		PageInfo<BankSettleDetail> list = transMgrService.queryBankSettleDetail(getCriteria());
		getRequest().setAttribute("page", list);
		return "transmgr/bankSettleList"; 
	}
	
	/**
	 * 修改对账成功状态
	 * @return
	 */
	@RequestMapping(value="/updateCheckStatus")
	public ModelAndView updateCheckStatus(String [] ids){
		int count = transMgrService.updateCheckStatusSucceed(ids);
		return ajaxDoneSuccess("修改成功。更新记录数为："+count);
	}
	
	@RequestMapping(value="/queryTransDetailByRelNo")
	public String queryTransDetailByRelNo(String relNo){
		List<TransInfo> list = transMgrService.queryTransDetailByRelNo(relNo);
		getRequest().setAttribute("list", list);
		return "transmgr/transDetailList"; 
	}
	
	/**
	 * 根据流水号查询交易信息
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 */
	@RequestMapping(value="/queryTransByTradeNo")
	public String queryTransByTradeNo(String tradeNo) throws IOException, Exception{
		TransInfo transInfo=transMgrService.queryTransInfoByTradeNo(tradeNo);
		if(transInfo.getCheckNo()!=null&&!"".equals(transInfo.getCheckNo())){
		transInfo.setSixAndFourCardNo(Funcs.decrypt(transInfo.getCheckNo())+"***"+Funcs.decrypt(transInfo.getLast()));
		}
		CardBinInfo cbInfo=transMgrService.queryCardBinInfoByBin(Funcs.decrypt(transInfo.getCheckNo()));
		getRequest().setAttribute("cbInfo", cbInfo);
		transInfo.setWebInfo( Tools.parseWebInfoToResourceType(transInfo.getWebInfo()));
		getRequest().setAttribute("transInfo", transInfo);
		return "transmgr/transDetail";
	}
	
	/**
	 * 根据流水号查询交易信息-财务专用
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 */
	@RequestMapping(value="/queryTransByTradeNoFinance")
	public String queryTransByTradeNoFinance(String tradeNo) throws IOException, Exception{
		TransInfo transInfo=transMgrService.queryTransInfoByTradeNo(tradeNo);
		if(transInfo.getCheckNo()!=null&&!"".equals(transInfo.getCheckNo())){
		transInfo.setSixAndFourCardNo(Funcs.decrypt(transInfo.getCheckNo())+"***"+Funcs.decrypt(transInfo.getLast()));
		}
		CardBinInfo cbInfo=transMgrService.queryCardBinInfoByBin(Funcs.decrypt(transInfo.getCheckNo()));
		getRequest().setAttribute("cbInfo", cbInfo);
		transInfo.setWebInfo( Tools.parseWebInfoToResourceType(transInfo.getWebInfo()));
		getRequest().setAttribute("transInfo", transInfo);
		return "transmgr/transDetailFinance";
	}
	
	
	/**
	 *导出交易 
	 * @throws Exception 
	 */
	@RequestMapping(value="/exportTrans")
	public void exportTrans(HttpServletResponse resp) throws Exception{
		OutputStream os = null;
		os = resp.getOutputStream();
		resp.reset(); // 来清除首部的空白行
		resp.setHeader("Content-Disposition", "attachment; filename=" + "transList.xls");
		resp.setContentType("application/octet-stream; charset=utf-8");
		Criteria criteria=getCriteria();
		BIWorkbook bw=new BIWorkbook();
		BISheet bs = bw.addSheet();
		BIRow br_0 = bs.addRow();
		for(String str:new String[]{"商户号","流水号","订单号","交易时间","通道名称","交易金额","交易币种",
				  "结算金额","结算币种",
				 /* "银行交易金额","银行交易币种",*/
				  "手续费","单笔手续费","手续费币种","保证金","保证金币种","卡种","交易状态","交易返回信息","欺诈分数","拒付状态","拒付金额","退款状态","退款金额","冻结状态","冻结金额","订单来源","所属终端号","通道英文账单名称",
				  "前六后四卡号","发卡行国家","发卡行名称","卡类型","网站","货物信息","收货人姓名","持卡人姓名","邮箱","电话","IP","支付国家","收货国家","收货省/ 州","收货地址","邮编","货运方式","货运单号","账单国家","账单省/州","账单地址",
				  /*"银行真实交易币种","银行真实交易金额","银行真实交易汇率",*/
				  "参考号"}){
			br_0.addCell().setCellValue(str, null);
		}
			List<TransInfo> list = transMgrService.exportTransList(criteria);
			for (int row = 1; row <= list.size(); row++) {
				BIRow br_1 = bs.addRow();
				TransInfo info = list.get(row-1);
				CardBinInfo cbInfo=transMgrService.queryCardBinInfoByBin(Funcs.decrypt(info.getCheckNo()));
				if(cbInfo==null){
					cbInfo=new CardBinInfo();
				}
				br_1.addCell().setCellValue(info.getMerNo(),null);
				br_1.addCell().setCellValue(info.getTradeNo(),null);
				br_1.addCell().setCellValue(info.getOrderNo(),null);
				br_1.addCell().setCellValue(info.getTransDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getTransDate()):"",null);
				br_1.addCell().setCellValue(info.getCurrencyName(),null);
				br_1.addCell().setCellValue(info.getMerTransAmount().doubleValue()+"",null);
				br_1.addCell().setCellValue(info.getMerBusCurrency()+"",null);
				br_1.addCell().setCellValue(info.getMerSettleAmount().doubleValue()+"",null);
				br_1.addCell().setCellValue(info.getMerSettleCurrency()+"",null);
				/*br_1.addCell().setCellValue(info.getBankTransAmount().doubleValue()+"",null);
				br_1.addCell().setCellValue(info.getBankCurrency()+"",null);*/
				br_1.addCell().setCellValue(info.getMerForAmount().doubleValue()+"",null);
				br_1.addCell().setCellValue(info.getSingleFee().doubleValue()+"",null);
				br_1.addCell().setCellValue(info.getMerSettleCurrency()+"",null);
				br_1.addCell().setCellValue(info.getBondAmount().doubleValue()+"",null);
				br_1.addCell().setCellValue(info.getMerSettleCurrency()+"",null);
				br_1.addCell().setCellValue(info.getCardType()+"",null);
				String respCodeInfo="";
				if("00".equals(info.getRespCode())){
					respCodeInfo="支付成功";
				}
				if("0000".equals(info.getRespCode())){
					respCodeInfo="预授权成功";
				}
				if("01".equals(info.getRespCode())){
					respCodeInfo="支付失败";
				}
				br_1.addCell().setCellValue(respCodeInfo,null);
				br_1.addCell().setCellValue(info.getRespMsg()+"",null);
				br_1.addCell().setCellValue(info.getRiskScore()+"",null);
				br_1.addCell().setCellValue("1".equals(info.getDishonorStatus())?"已拒付":"未拒付"+"",null);
				br_1.addCell().setCellValue(info.getMerBusCurrency()+" "+ info.getDishonorAmount()+"",null);
				br_1.addCell().setCellValue("1".equals(info.getRefundStatus())?"已退款":"未退款"+"",null);
				br_1.addCell().setCellValue(info.getMerBusCurrency()+" "+ info.getRefundAmount(),null);
				br_1.addCell().setCellValue("1".equals(info.getFrozenStatus())?"已冻结":"未冻结",null);
				br_1.addCell().setCellValue(info.getMerBusCurrency()+" "+ info.getFrozenAmount(),null);
				//sheet.addCell( new Label(col++, row, "无伪冒状态"));//交易返回信息
				br_1.addCell().setCellValue(Tools.parseWebInfoToResourceType(info.getWebInfo()),null);
				br_1.addCell().setCellValue(info.getTerNo(),null);
				br_1.addCell().setCellValue(info.getAcquirer(),null);
				
				
				
				if(info.getCheckNo()!=null&&!"".equals(info.getCheckNo())){
					br_1.addCell().setCellValue( Funcs.decrypt(info.getCheckNo())+"***"+Funcs.decrypt(info.getLast()),null);
				}else{
					br_1.addCell().setCellValue( "",null);
				}
				br_1.addCell().setCellValue( cbInfo.getCountry_code(),null);
				br_1.addCell().setCellValue( cbInfo.getBank(),null);
				br_1.addCell().setCellValue( cbInfo.getCard_type(),null);
				br_1.addCell().setCellValue(info.getPayWebSite(),null);
				if(null!=info.getGoodsInfo()){
					br_1.addCell().setCellValue(new String(info.getGoodsInfo(),"utf-8"),null);
				}else{
					br_1.addCell().setCellValue( "",null);
				}
				br_1.addCell().setCellValue( info.getGrPerName(),null);
				br_1.addCell().setCellValue( info.getCardFullName(),null);
				br_1.addCell().setCellValue( info.getEmail(),null);
				br_1.addCell().setCellValue( info.getGrphoneNumber(),null);
				br_1.addCell().setCellValue( info.getIpAddress(),null);
				br_1.addCell().setCellValue( info.getIpCountry(),null);
				br_1.addCell().setCellValue( info.getGrCountry(),null);
				br_1.addCell().setCellValue( info.getGrState(),null);
				br_1.addCell().setCellValue( info.getGrAddress(),null);
				br_1.addCell().setCellValue( info.getGrZipCode(),null);
				br_1.addCell().setCellValue( info.getIogistics(),null);
				br_1.addCell().setCellValue( info.getTrackNo(),null);
				br_1.addCell().setCellValue( info.getCardCountry(),null);
				br_1.addCell().setCellValue( info.getCardState(),null);
				br_1.addCell().setCellValue( info.getCardAddress(),null);
				/*br_1.addCell().setCellValue(  info.getBankRealCurrency()==null?"": info.getBankRealCurrency(),null);
				br_1.addCell().setCellValue( info.getBankRealAmount()==null?"": info.getBankRealAmount(),null);
				br_1.addCell().setCellValue( info.getBankRealRate(),null);*/
				br_1.addCell().setCellValue( info.getBankRefNo(),null);
			}
			
		bw.workbook.write(os);	
		os.flush();
		os.close();
	}
	
	/**
	 *财务导出交易 
	 * @throws Exception 
	 */
	@RequestMapping(value="/exportTransFinance")
	public void exportTransFinance(HttpServletResponse resp) throws Exception{
		OutputStream os = null;
		os = resp.getOutputStream();
		resp.reset(); // 来清除首部的空白行
		resp.setHeader("Content-Disposition", "attachment; filename=" + "transList.xls");
		resp.setContentType("application/octet-stream; charset=utf-8");
		Criteria criteria=getCriteria();
		BIWorkbook bw=new BIWorkbook();
		BISheet bs = bw.addSheet();
		BIRow br_0 = bs.addRow();
		for(String str:new String[]{"商户号","流水号","订单号","交易时间","通道名称","交易金额","交易币种",
				  "结算金额","结算币种",
				  "银行交易金额","银行交易币种",
				  "手续费","单笔手续费","手续费币种","保证金","保证金币种","卡种","交易状态","交易返回信息","欺诈分数","拒付状态","拒付金额","退款状态","退款金额","冻结状态","冻结金额","订单来源","所属终端号","通道英文账单名称",
				  "前六后四卡号","发卡行国家","发卡行名称","卡类型","网站","货物信息","收货人姓名","持卡人姓名","邮箱","电话","IP","支付国家","收货国家","收货省/ 州","收货地址","邮编","货运方式","货运单号","账单国家","账单省/州","账单地址",
				  "银行真实交易币种","银行真实交易金额","银行真实交易汇率",
				  "参考号"}){
			br_0.addCell().setCellValue(str, null);
		}
			List<TransInfo> list = transMgrService.exportTransList(criteria);
			for (int row = 1; row <= list.size(); row++) {
				BIRow br_1 = bs.addRow();
				TransInfo info = list.get(row-1);
				CardBinInfo cbInfo=transMgrService.queryCardBinInfoByBin(Funcs.decrypt(info.getCheckNo()));
				if(cbInfo==null){
					cbInfo=new CardBinInfo();
				}
				br_1.addCell().setCellValue(info.getMerNo(),null);
				br_1.addCell().setCellValue(info.getTradeNo(),null);
				br_1.addCell().setCellValue(info.getOrderNo(),null);
				br_1.addCell().setCellValue(info.getTransDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getTransDate()):"",null);
				br_1.addCell().setCellValue(info.getCurrencyName(),null);
				br_1.addCell().setCellValue(info.getMerTransAmount().doubleValue()+"",null);
				br_1.addCell().setCellValue(info.getMerBusCurrency()+"",null);
				br_1.addCell().setCellValue(info.getMerSettleAmount().doubleValue()+"",null);
				br_1.addCell().setCellValue(info.getMerSettleCurrency()+"",null);
				br_1.addCell().setCellValue(info.getBankTransAmount().doubleValue()+"",null);
				br_1.addCell().setCellValue(info.getBankCurrency()+"",null);
				br_1.addCell().setCellValue(info.getMerForAmount().doubleValue()+"",null);
				br_1.addCell().setCellValue(info.getSingleFee().doubleValue()+"",null);
				br_1.addCell().setCellValue(info.getMerSettleCurrency()+"",null);
				br_1.addCell().setCellValue(info.getBondAmount().doubleValue()+"",null);
				br_1.addCell().setCellValue(info.getMerSettleCurrency()+"",null);
				br_1.addCell().setCellValue(info.getCardType()+"",null);
				String respCodeInfo="";
				if("00".equals(info.getRespCode())){
					respCodeInfo="支付成功";
				}
				if("0000".equals(info.getRespCode())){
					respCodeInfo="预授权成功";
				}
				if("01".equals(info.getRespCode())){
					respCodeInfo="支付失败";
				}
				br_1.addCell().setCellValue(respCodeInfo,null);
				br_1.addCell().setCellValue(info.getRespMsg()+"",null);
				br_1.addCell().setCellValue(info.getRiskScore()+"",null);
				br_1.addCell().setCellValue("1".equals(info.getDishonorStatus())?"已拒付":"未拒付"+"",null);
				br_1.addCell().setCellValue(info.getMerBusCurrency()+" "+ info.getDishonorAmount()+"",null);
				br_1.addCell().setCellValue("1".equals(info.getRefundStatus())?"已退款":"未退款"+"",null);
				br_1.addCell().setCellValue(info.getMerBusCurrency()+" "+ info.getRefundAmount(),null);
				br_1.addCell().setCellValue("1".equals(info.getFrozenStatus())?"已冻结":"未冻结",null);
				br_1.addCell().setCellValue(info.getMerBusCurrency()+" "+ info.getFrozenAmount(),null);
				//sheet.addCell( new Label(col++, row, "无伪冒状态"));//交易返回信息
				br_1.addCell().setCellValue(Tools.parseWebInfoToResourceType(info.getWebInfo()),null);
				br_1.addCell().setCellValue(info.getTerNo(),null);
				br_1.addCell().setCellValue(info.getAcquirer(),null);
				
				
				
				if(info.getCheckNo()!=null&&!"".equals(info.getCheckNo())){
					br_1.addCell().setCellValue( Funcs.decrypt(info.getCheckNo())+"***"+Funcs.decrypt(info.getLast()),null);
				}else{
					br_1.addCell().setCellValue( "",null);
				}
				br_1.addCell().setCellValue( cbInfo.getCountry_code(),null);
				br_1.addCell().setCellValue( cbInfo.getBank(),null);
				br_1.addCell().setCellValue( cbInfo.getCard_type(),null);
				br_1.addCell().setCellValue(info.getPayWebSite(),null);
				if(null!=info.getGoodsInfo()){
					br_1.addCell().setCellValue(new String(info.getGoodsInfo(),"utf-8"),null);
				}else{
					br_1.addCell().setCellValue( "",null);
				}
				br_1.addCell().setCellValue( info.getGrPerName(),null);
				br_1.addCell().setCellValue( info.getCardFullName(),null);
				br_1.addCell().setCellValue( info.getEmail(),null);
				br_1.addCell().setCellValue( info.getGrphoneNumber(),null);
				br_1.addCell().setCellValue( info.getIpAddress(),null);
				br_1.addCell().setCellValue( info.getIpCountry(),null);
				br_1.addCell().setCellValue( info.getGrCountry(),null);
				br_1.addCell().setCellValue( info.getGrState(),null);
				br_1.addCell().setCellValue( info.getGrAddress(),null);
				br_1.addCell().setCellValue( info.getGrZipCode(),null);
				br_1.addCell().setCellValue( info.getIogistics(),null);
				br_1.addCell().setCellValue( info.getTrackNo(),null);
				br_1.addCell().setCellValue( info.getCardCountry(),null);
				br_1.addCell().setCellValue( info.getCardState(),null);
				br_1.addCell().setCellValue( info.getCardAddress(),null);
				br_1.addCell().setCellValue(  info.getBankRealCurrency()==null?"": info.getBankRealCurrency(),null);
				br_1.addCell().setCellValue( info.getBankRealAmount()==null?"": info.getBankRealAmount(),null);
				br_1.addCell().setCellValue( info.getBankRealRate(),null);
				br_1.addCell().setCellValue( info.getBankRefNo(),null);
			}
			
		bw.workbook.write(os);	
		os.flush();
		os.close();
	}
	
	/**
	 * 查询结算交易列表
	 * @return
	 */
	@RequestMapping(value="/getSettleTransList")
	public String getSettleTransList(){
		PageInfo<SettleTransInfo> page = transMgrService.getSettleTransList(getCriteria());
		List<SettleTransInfo> settleTransAmount=transMgrService.getSettleTransAmount(getCriteria());
		getRequest().setAttribute("settleTransAmount",settleTransAmount);
	 	getRequest().setAttribute("page", page);
	 	//添加金额统计
		return "transmgr/settleTransList";
	}
	
	/**
	 * 根据流水号查询信息
	 * @param tradeNo
	 * @return
	 */
	@RequestMapping(value="/querySettleTransDetailByTradeNo")
	public String querySettleTransDetailByTradeNo(String tradeNo){
		SettleTransInfo settleTransInfo=transMgrService.querySettleTransDetailByTradeNo(tradeNo);
		getRequest().setAttribute("settleTransInfo", settleTransInfo);
		return "transmgr/settleTransDetail";
	}
	
	/**
	 * 根据参考号查询 v
	 * @param relNo
	 * @return
	 */
	@RequestMapping(value="/querySettleTransDetailByRelNo")
	public String querySettleTransDetailByRelNo(String relNo){
		List<SettleTransInfo> list = transMgrService.querySettleTransDetailByRelNo(relNo);
		getRequest().setAttribute("list", list);
		return "transmgr/SettleTransDetailList"; 
	}
	
	
	
	/**
	 *导出交易 
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	@RequestMapping(value="/exportSettleTrans")
	public void exportSettleTrans(HttpServletResponse resp) throws IOException, RowsExceededException, WriteException{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "transList.xls" ); 
		List<SettleTransInfo> list = transMgrService.exportSettleTransList(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("结算交易列表", 0);
		String[] headerName = { "交易流水号", "参考号","商户号","终端号","交易类型","交易金额","商户手续费","返回码","交易时间","交易通道","交易银行","结算日期","结算批次号"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			SettleTransInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getTradeNo()));
			sheet.addCell( new Label(col++, row, info.getRelNo()));
			sheet.addCell( new Label(col++, row, info.getMerNo()));
			sheet.addCell( new Label(col++, row, info.getTerNo()));
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("gw_transtype_info",info.getTransType(),"未知类型")));
			sheet.addCell( new jxl.write.Number(col++, row, info.getTransAmount().doubleValue()));
			sheet.addCell( new jxl.write.Number(col++, row, info.getMerForAmount().doubleValue()));
			/*sheet.addCell( new jxl.write.Number(col++, row, info.getAgentForAmount().doubleValue()));
			sheet.addCell( new jxl.write.Number(col++, row, info.getParentAgentForAmount().doubleValue()));*/
			sheet.addCell( new Label(col++, row, info.getRespCode()));
			sheet.addCell( new Label(col++, row, info.getTransDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getTransDate()):""));
			sheet.addCell( new Label(col++, row, info.getCurrencyName()));
			sheet.addCell( new Label(col++, row, info.getAcquiringBank()));
			sheet.addCell( new Label(col++, row, info.getSettleDate()));
			sheet.addCell( new Label(col++, row, info.getSettleBatchNo()));
		}
		book.write();
		book.close();
	}
	

	/**
	 * 明细结算交易导出
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	@RequestMapping(value="/exportTransInfo")
	public void exportTransInfo(HttpServletResponse resp,String merNo,String terNo,String settleBatchNo) throws IOException, RowsExceededException, WriteException{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "transList.xls" ); 
		List<GwSettleTransInfo> list=settleMgrService.queryDetailList(merNo, terNo, settleBatchNo);
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("结算交易列表", 0);
		String[] headerName = new String []{ "交易流水号", "参考号","商户号","终端号","交易类型","交易金额","商户手续费","代理商手续费","父级代理商手续费","返回码","交易时间","结算日期","结算批次号"};
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
			sheet.addCell( new Label(col++, row, info.getSettleDate()));
			sheet.addCell( new Label(col++, row, info.getSettleBatchNo()));
		}
		book.write();
		book.close();
	}
	
	
	/**
	 * 订单跟踪
	 * 
	 * */
	@RequestMapping(value="/getTransRecordList")
	public String getTransRecordList(){
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String transDateStart=sdf1.format(date);
			criteria.getCondition().put("transDateStart", transDateStart);
			criteria.getCondition().put("transDateEnd", transDateStart);
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<TransRecordInfo> page = transMgrService.getTransRecordList(criteria);
			getRequest().setAttribute("page", page);
		}
		return "transmgr/transRecordList";
	}
	/**
	 * 综合信息查询
	 * 
	 * */
	@RequestMapping(value="/listMulTransInfo")
	public String listMulTransInfo(){
		Criteria criteria=getCriteria();
	 	PageInfo<MulTransInfo> page = transMgrService.getMulTransInfoList(criteria);
	 	List<BankInfo> bankInfos=bankMgrDao.getBankList();
	 	List<CurrencyInfo> currencyInfos=bankMgrDao.getCurrencyList();
	 	getRequest().setAttribute("page", page);
	 	getRequest().setAttribute("bankInfos", bankInfos);
	 	getRequest().setAttribute("currencyInfos", currencyInfos);
		return "transmgr/listMulTransInfo";
	}
	
	/** 导出订单跟踪 */
	@RequestMapping(value="/uploadTransRecordFile")
	public void uploadTransRecordFile(HttpServletResponse resp) throws Exception{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "transRecordList.xls" );
		List<TransRecordInfo> list = transMgrService.queryTransRecordInfo(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("订单跟踪列表", 0);
		String[] headerName = { "商户号","终端号","流水号","订单号","进入系统时间","异常码","异常原因","IP","描述","协议","交易金额","来源网址","支付提交地址","返回网址","货物信息",
								"姓名","邮箱","电话","IP","前六后四卡号","收货国家","收货省/ 州","收货地址","邮编","账单国家","账单省/州","账单地址"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			TransRecordInfo info = list.get(row-1);
			TransDetailInfo eInfo = transMgrService.queryTransInfo(info.getTradeNo());
			sheet.addCell( new Label(col++, row, info.getMerNo()));//商户号
			sheet.addCell( new Label(col++, row, info.getTerNo()));//终端号
			sheet.addCell( new Label(col++, row, info.getTradeNo()));//交易流水号
			sheet.addCell( new Label(col++, row, info.getOrderNo()));//商户订单号
			sheet.addCell( new Label(col++, row, info.getEnterTime()));//进入系统时间
			sheet.addCell( new Label(col++, row, info.getRespCode()));//进入系统时间
			sheet.addCell( new Label(col++, row, info.getRespMsg()));//进入系统时间
			sheet.addCell( new Label(col++, row, info.getClientIP()));//IP
			sheet.addCell( new Label(col++, row, info.getDescription()));//描述
			sheet.addCell( new Label(col++, row, info.getTransPortProtocol()));//协议
			sheet.addCell( new Label(col++, row, info.getCurrencyCode() +" " + info.getAmount()));//交易金额
			sheet.addCell( new Label(col++, row, info.getMerURL()));//来源网址
			sheet.addCell( new Label(col++, row, info.getSubmitURL()));//支付提交地址
			sheet.addCell( new Label(col++, row, info.getReturnURL()));//返回网址
			if(!StringUtils.isEmpty(eInfo.getGoodsInfoByte())){//货物信息
				sheet.addCell(new Label(col++, row, new String(eInfo.getGoodsInfoByte(),"utf-8")));
				System.out.println("===="+new String(eInfo.getGoodsInfoByte(),"utf-8"));
			}else{
				sheet.addCell(new Label(col++, row, ""));
			}
			sheet.addCell( new Label(col++, row, eInfo.getCardFullName()));//姓名
			sheet.addCell( new Label(col++, row, eInfo.getEmail()));//邮箱
			sheet.addCell( new Label(col++, row, eInfo.getCardFullPhone()));//电话
			sheet.addCell( new Label(col++, row, eInfo.getIpAddress()));//IP
			sheet.addCell( new Label(col++, row, eInfo.getSixAndFourCardNo()));//前六后四卡号
			sheet.addCell( new Label(col++, row, eInfo.getGrCountry()));//收货国家
			sheet.addCell( new Label(col++, row, eInfo.getGrState()));//收货省/ 州
			sheet.addCell( new Label(col++, row, eInfo.getGrAddress()));//收货地址
			sheet.addCell( new Label(col++, row, eInfo.getGrZipCode()));//邮编
			sheet.addCell( new Label(col++, row, eInfo.getCardCountry()));//账单国家
			sheet.addCell( new Label(col++, row, eInfo.getCardState()));//账单省/州
			sheet.addCell( new Label(col++, row, eInfo.getCardAddress()));//账单地址
		}
		book.write();
		book.close();
	}
	

	/**
	 * 
	 *根据email,cardNo,Ip查询交易信息 
	 */
	@RequestMapping("/queryTransByType")
	public String queryTransByType(){
		Criteria c = getCriteria();
		if(null!=c.getCondition().get("cardNo")){
			String s = c.getCondition().get("cardNo")+"";
			String [] str = s.split("\\u002A\\u002A\\u002A\\u002A");
			c.getCondition().put("checkNo", Funcs.encrypt(str[0]));
			c.getCondition().put("last", Funcs.encrypt(str[1]));
		}
		List<TransInfo> list=transMgrService.queryTransByType(c);
		getRequest().setAttribute("list", list);
		String ids = "";
		for(TransInfo li:list){
			ids = ids + li.getId()+",";
		}
		getRequest().setAttribute("ids", ids);
		return "transmgr/transByType";	
	}
	
	
	
	@RequestMapping(value="/exportTransByType")
	public void exportTransByType(HttpServletResponse resp,String ids) throws Exception{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "transList.xls" );
		
		List<TransInfo> list = transMgrService.exportTransByType(ids);
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("交易列表", 0);
		//String[] headerName = { "交易流水号", "参考号","商户号","终端号","交易类型","交易金额","商户手续费","返回码","交易时间","交易通道","交易银行","结算日期"};
		
		  String[] headerName={"商户号","流水号","订单号","交易时间","交易金额","结算金额","手续费","单笔手续费","保证金","卡种","交易状态","交易返回信息","欺诈分数","拒付状态","拒付金额","退款状态","退款金额","冻结状态","冻结金额","伪冒状态","订单来源","所属终端号","通道英文账单名称","前六后四卡号","网站","货物信息","姓名","邮箱","电话","IP","收货国家","收货省/ 州","收货地址","邮编","货运方式","货运单号","账单国家","账单省/州","账单地址"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			TransInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getMerNo()));
			sheet.addCell( new Label(col++, row, info.getTradeNo()));
			sheet.addCell( new Label(col++, row, info.getOrderNo()));
			sheet.addCell( new Label(col++, row, info.getTransDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getTransDate()):""));
			sheet.addCell( new jxl.write.Number(col++, row, info.getTransAmount().doubleValue()));
			sheet.addCell( new jxl.write.Number(col++, row, info.getMerSettleAmount().doubleValue()));
			sheet.addCell( new jxl.write.Number(col++, row, info.getMerForAmount().doubleValue()));
			sheet.addCell( new jxl.write.Number(col++, row, info.getSingleFee().doubleValue()));
			
			sheet.addCell( new jxl.write.Number(col++, row, info.getBondAmount().doubleValue()));
			sheet.addCell( new Label(col++, row, info.getCardType()));
			sheet.addCell(new Label(col++, row, "00".equals(info.getRespCode())?"支付成功":"支付失败"));
			sheet.addCell( new Label(col++, row, info.getRespMsg()));//交易返回信息
			sheet.addCell( new Label(col++, row, info.getRiskScore()));//欺诈分数
			sheet.addCell(new Label(col++, row, "1".equals(info.getDishonorStatus())?"已拒付":"未拒付"));
			sheet.addCell(new Label(col++, row, info.getMerSettleCurrency()+" "+ info.getDishonorAmount()));
			sheet.addCell(new Label(col++, row, "1".equals(info.getRefundStatus())?"已退款":"未退款"));
			sheet.addCell(new Label(col++, row, info.getMerSettleCurrency()+" "+ info.getRefundAmount()));
			sheet.addCell(new Label(col++, row, "1".equals(info.getFrozenStatus())?"已冻结":"未冻结"));
			sheet.addCell(new Label(col++, row, info.getMerSettleCurrency()+" "+ info.getFrozenAmount()));
			sheet.addCell( new Label(col++, row, "无伪冒状态"));//交易返回信息
			sheet.addCell(new Label(col++, row, Tools.parseWebInfoToResourceType(info.getWebInfo())));//所需要的包未加载
			sheet.addCell( new Label(col++, row, info.getTerNo()));
			sheet.addCell(new Label(col++, row, info.getAcquirer()));
			if(info.getCheckNo()!=null&&!"".equals(info.getCheckNo())){
				sheet.addCell(new Label(col++, row, Funcs.decrypt(info.getCheckNo())+"***"+Funcs.decrypt(info.getLast())));
			}else{
				sheet.addCell(new Label(col++, row, ""));
			}
			
			sheet.addCell(new Label(col++, row, info.getPayWebsite()));
			if(null!=info.getGoodsInfo()){
				sheet.addCell(new Label(col++, row, new String(info.getGoodsInfo(),"utf-8")));
				System.out.println("===="+new String(info.getGoodsInfo(),"utf-8"));
			}else{
				sheet.addCell(new Label(col++, row, ""));
			}
			sheet.addCell(new Label(col++, row, info.getGrPerName()));
			sheet.addCell(new Label(col++, row, info.getEmail()));
			sheet.addCell(new Label(col++, row, info.getGrphoneNumber()));
			sheet.addCell(new Label(col++, row, info.getIpAddress()));
			sheet.addCell(new Label(col++, row, info.getGrCountry()));
			sheet.addCell(new Label(col++, row, info.getGrState()));
			sheet.addCell(new Label(col++, row, info.getGrAddress()));
			sheet.addCell(new Label(col++, row, info.getGrZipCode()));
			sheet.addCell(new Label(col++, row, info.getIogistics()));
			sheet.addCell(new Label(col++, row, info.getTrackNo()));
			sheet.addCell(new Label(col++, row, info.getCardCountry()));
			sheet.addCell(new Label(col++, row, info.getCardState()));
			sheet.addCell(new Label(col++, row, info.getCardAddress()));
		}
			
			
		book.write();
		book.close();
	}
	
	/**
	 * 
	 *交易伪冒单
	 * 
	 */
	/**
	 * 交易伪冒查询
	 * @return
	 */
	@RequestMapping(value="/getFakeTransList")
	public String getFakeTransList(){
		Criteria criteria=getCriteria();
	 	PageInfo<TransInfo> page = transMgrService.getTransList(criteria);
	 	List<TransInfo>  Total=transMgrService.getTransAmountList(criteria);
	 	TransCount transCount=transMgrService.queryTransCount(criteria);
	 	getRequest().setAttribute("page", page);
	 	getRequest().setAttribute("Totallist", Total);
	 	getRequest().setAttribute("transCount", transCount);
		return "transmgr/faketransList";
	}
	
	@RequestMapping(value="/listTransLogInfo")
	public String listTransLogInfo(){
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String transDateStart=sdf.format(date);
			criteria.getCondition().put("transDateStart", transDateStart);
			criteria.getCondition().put("transDateEnd", transDateStart);
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<TransLogInfo> page = transMgrService.listTransLogInfo(criteria);
			getRequest().setAttribute("page", page);
		}
		return "transmgr/listTransLogInfo";
	}
	
	
	@RequestMapping(value="/exportTransLogInfoFile")
	public void exportTransLogInfoFile(HttpServletResponse resp,String ids) throws Exception{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "transList.xls" );
		
		List<TransLogInfo> list = transMgrService.exportTransLogInfos(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("交易列表", 0);
		//String[] headerName = { "交易流水号", "参考号","商户号","终端号","交易类型","交易金额","商户手续费","返回码","交易时间","交易通道","交易银行","结算日期"};
		
		  String[] headerName={"商户号","终端号","流水号","订单号","交易时间","交易金额",
				  "前六后四卡号",
				  "网站","货物信息","姓名","邮箱","电话","IP","收货国家","收货省/ 州"
				  ,"收货地址","邮编","账单国家","账单省/州"
				  ,"账单地址"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			TransLogInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getMerNo()));
			sheet.addCell( new Label(col++, row, info.getTerNo()));
			sheet.addCell( new Label(col++, row, info.getTradeNo()));
			sheet.addCell( new Label(col++, row, info.getOrderNo()));
			sheet.addCell( new Label(col++, row, info.getTransDate()));
			sheet.addCell(new Label(col++, row, info.getCurrencyCode()+" "+info.getAmount()));
			if(info.getOfr()!=null&&!"".equals(info.getOfr())){
				sheet.addCell(new Label(col++, row, Funcs.decrypt(info.getOfr())+"***"+Funcs.decrypt(info.getTtr())));
			}else{
				sheet.addCell(new Label(col++, row, ""));
			}
			
			sheet.addCell(new Label(col++, row, info.getMerMgrURL()));
			sheet.addCell(new Label(col++, row, info.getGoodsString()));
			sheet.addCell(new Label(col++, row, info.getGrPerName()));
			sheet.addCell(new Label(col++, row, info.getGrEmail()));
			sheet.addCell(new Label(col++, row, info.getGrphoneNumber()));
			sheet.addCell(new Label(col++, row, info.getPayIP()));
			sheet.addCell(new Label(col++, row, info.getGrCountry()));
			sheet.addCell(new Label(col++, row, info.getGrState()));
			sheet.addCell(new Label(col++, row, info.getGrAddress()));
			sheet.addCell(new Label(col++, row, info.getGrZipCode()));
			sheet.addCell(new Label(col++, row, info.getCardCountry()));
			sheet.addCell(new Label(col++, row, info.getCardState()));
			sheet.addCell(new Label(col++, row, info.getCardAddress()));
		}
			
			
		book.write();
		book.close();
	}
	
	/**
	 * 回访
	 */
	@RequestMapping(value="/queryTransCallback")
	public String queryTransEmailCallback(){
		Criteria criteria = getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(new Date());
			criteria.getCondition().put("uploadDateStart", date);
			criteria.getCondition().put("uploadDateEnd", date);
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<TransCallbackInfo> page = transMgrService.queryTransCallbackInfoList(criteria);
			getRequest().setAttribute("page", page);
		}
		return "transmgr/transCallbackList";
	}
	
	/**
	 * 跳转选择发送邮箱和邮件模板界面
	 */
	@RequestMapping(value="/toSelectSendInfo")
	public String toSelectSendInfo(){
		return "transmgr/selectCallbackSendInfo";
	}
	
	/**
	 * 跳转批量上传回访信息界面
	 */
	@RequestMapping(value="/toUploadTransCallbackInfo")
	public String toUploadTransCallbackInfo(TransCallbackInfo form){
		getRequest().setAttribute("info", form);
		return "transmgr/uploadTransCallbackFile";
	}
	
	/**
	 * 批量上传交易回访信息
	 * @throws IOException 
	 */
	@RequestMapping(value="/uploadTransCallbackInfos")
	public ModelAndView uploadTransCallbackInfos(TransCallbackInfo form, DefaultMultipartHttpServletRequest request, HttpServletResponse resp) throws IOException{
		UserInfo user = getLogAccount();
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		if(files!=null && files.size()>0){
			List<TransCallbackInfo> list = new ArrayList<TransCallbackInfo>();
			int successCount = 0;
			int faildCount = 0;
			int linenum = 1;
			StringBuffer sb = new StringBuffer();
			for(MultipartFile file : files){
				BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
				String line = reader.readLine();
				while(line!=null && !("".equals(line))){
					linenum++;
					line = reader.readLine();
					if(line!=null && !("".equals(line))){
						String[] lines = line.split(",");
						if(lines!=null && lines.length>0 && lines.length==1){
							TransCallbackInfo info = new TransCallbackInfo();
							info.setTradeNo(lines[0]);
							info.setEmailModel(form.getEmailModel());
							info.setSendEmail(form.getSendEmail());
							info.setSendStatus("0");
							info.setUploadBy(user.getRealName());
							log.info("第"+linenum+"行的数据为:"+"交易流水号:"+info.getTradeNo()+",邮件模型:"+info.getEmailModel()+",发送邮箱:"+info.getSendEmail()+",上传人:"+info.getUploadBy());
							int a =  -1;
							try{
								a = transMgrService.saveTransCallbackInfo(info);
							}catch(Exception e){
								a = -1;
							}
							if(a>0){
								successCount++;
							}else{
								faildCount++;
								list.add(info);
							}
						}
					}
				}
			}
			log.info("保存成功:"+successCount+"记录");
			log.info("保存失败:"+faildCount+"记录");
			getRequest().getSession().setAttribute("failCallback", list);
			getRequest().getSession().setAttribute("callbackInfo", sb.toString());
		}
		return ajaxDoneError("没有信息上传");
	}
	
	/**
	 * 下载批量上传模板信息
	 */
	@RequestMapping(value="/downloadTransCallbackModel")
	public void downloadTransCallbackModel(HttpServletResponse resp){
		resp.setContentType("application/zip");
		resp.setHeader("Content-Type","text/html;charset=UTF-8");
		resp.addHeader("Content-Disposition", "attachment;filename=\"transCallback.txt\"");
		OutputStream ou = null;
		PrintWriter bos = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("交易流水号");
			sb.append("\r\n");
			sb.append("BR1512181143048950");
			ou = resp.getOutputStream();
			bos = new PrintWriter(ou);
			bos.print(sb.toString());
			bos.flush();
			ou.flush();
			ou.close();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 下载上传失败信息
	 */
	@RequestMapping(value="/downloadFailCallbackInfoList")
	public void downloadFailCallbackInfoList(HttpServletResponse resp){
		resp.setContentType("application/zip");
		resp.setHeader("Content-Type","text/html;charset=UTF-8");
		resp.addHeader("Content-Disposition", "attachment;filename=\"uploadCallbackFail.txt\"");
		List<TransCallbackInfo> list = (List<TransCallbackInfo>) getRequest().getSession().getAttribute("failCallback");
		OutputStream ou = null;
		PrintWriter bos = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("交易流水号");
			sb.append("\r\n");
			if(list!=null && list.size()>0){
				for(TransCallbackInfo info : list){
					log.info("失败记录信息:交易流水号:"+info.getTradeNo()+",邮件模型:"+info.getEmailModel()+",发送邮箱:"+info.getSendEmail());
					sb.append(info.getTradeNo());
					sb.append("\r\n");
				}
			}
			ou = resp.getOutputStream();
			bos = new PrintWriter(ou);
			bos.print(sb.toString());
			bos.flush();
			ou.flush();
			ou.close();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转查询上传失败信息界面
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @throws IOException 
	 */
	@RequestMapping(value="/exportTransCallbackInfoList")
	public void exportTransCallbackInfoList(HttpServletResponse resp) throws RowsExceededException, WriteException, IOException{
		Criteria criteria = getCriteria();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TransCallbackInfo> list = transMgrService.queryExportTransCallbackInfoList(criteria);
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "transListCallback.xls" );
		
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("交易列表", 0);
		//String[] headerName = { "交易流水号", "参考号","商户号","终端号","交易类型","交易金额","商户手续费","返回码","交易时间","交易通道","交易银行","结算日期"};
		
		  String[] headerName={"商户号","终端号","流水号","订单号","交易时间","邮件模型","持卡人邮箱",
				  "发送邮箱","发送日期","发送人","发送状态","邮件发送时间"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			TransCallbackInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getMerNo()));
			sheet.addCell( new Label(col++, row, info.getTerNo()));
			sheet.addCell( new Label(col++, row, info.getTradeNo()));
			sheet.addCell( new Label(col++, row, info.getOrderNo()));
			sheet.addCell( new Label(col++, row, info.getTransDate()!=null?sdf.format(info.getTransDate()):""));
			sheet.addCell(new Label(col++, row, info.getEmailModel()!=null?("1".equals(info.getEmailModel())?"虚拟商户持卡人回访模板":("2".equals(info.getEmailModel())?"正品商户持卡人回访模板":("3".equals(info.getEmailModel())?"拒付订单持卡人回访模板":"其它模板"))):"其它模板"));
			sheet.addCell(new Label(col++, row, info.getEmail()!=null?info.getEmail():""));
			sheet.addCell(new Label(col++, row, info.getSendEmail()));
			sheet.addCell(new Label(col++, row, sdf.format(info.getUploadDate())));
			sheet.addCell(new Label(col++, row, info.getUploadBy()));
			sheet.addCell(new Label(col++, row, info.getSendStatus()!=null?("0".equals(info.getSendStatus())?"未发送":("1".equals(info.getSendStatus())?"发送失败":("2".equals(info.getSendStatus())?"发送成功":"未发送"))):"未发送"));
			sheet.addCell(new Label(col++, row, info.getSendTime()!=null?sdf.format(info.getSendTime()):""));
		}
			
			
		book.write();
		book.close();
	}
	
	/**
	 * 白名单交易拒付
	 */
	@RequestMapping(value="/whiteTransDishonor")
	public String whiteTransDishonor(){
		Criteria criteria = getCriteria();
		if("post".equalsIgnoreCase(getRequest().getMethod())){
			PageInfo<WhiteDishonorInfo> page = transMgrService.queryWhiteTransDishonorInfoList(criteria);
			getRequest().setAttribute("form", criteria.getCondition());
			getRequest().setAttribute("page", page);
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			criteria.getCondition().put("dateStart", sdf.format(new Date()));
			criteria.getCondition().put("dateEnd", sdf.format(new Date()));
			getRequest().setAttribute("form", criteria.getCondition());
		}
		return "transmgr/listWhiteDishonorInfo";
	}
	
	/**
	 * 查询白名单规则信息
	 */
	@RequestMapping(value="/queryWhiteInfo")
	public String queryWhiteInfo(String whiteIds){
		List<WhiteListInfo> list = transMgrService.queryWhiteInfoListByIds(whiteIds);
		getRequest().setAttribute("list", list);
		return "transmgr/listWhiteBlackTextInfo";
	}
	
	/**
	 * 导出白名单交易拒付信息
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @throws IOException 
	 */
	@RequestMapping(value="/exprotWhiteTransDishonor")
	public void exportWhiteTransDishonorInfo(HttpServletRequest request, HttpServletResponse response) throws RowsExceededException, WriteException, IOException{
		Criteria criteria = getCriteria();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<WhiteDishonorInfo> list = transMgrService.queryExportWhiteTransDishonorInfoList(criteria);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename="+ "whiteTransDishonor.xls" );
		
		WritableWorkbook book = Workbook.createWorkbook(response.getOutputStream());
		WritableSheet sheet = book.createSheet("白名单交易拒付列表", 0);
		String[] headerName={"商户号","终端号","流水号","交易状态","交易返回原因","拒付录入日期","伪冒录入日期","规则内容"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			WhiteDishonorInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getMerNo()));
			sheet.addCell( new Label(col++, row, info.getTerNo()));
			sheet.addCell( new Label(col++, row, info.getTradeNo()));
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("RESP_INFO", info.getRespCode(), info.getRespCode())));
			sheet.addCell( new Label(col++, row, info.getRespMsg()));;
			sheet.addCell( new Label(col++, row, info.getDisCreatedDate()!=null?sdf.format(info.getDisCreatedDate()):""));
			sheet.addCell( new Label(col++, row, info.getFakeCreatedDate()!=null?sdf.format(info.getFakeCreatedDate()):""));
			sheet.addCell( new Label(col++, row, info.getBlackTexts()));
		}
		book.write();
		book.close();
	}

	/**
	 * 交易强制记录
	 * 
	 * */
	@RequestMapping(value="/getTransChangeInfoList")
	public String getTransChangeInfoList(){
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String transDateStart=sdf1.format(date);
			criteria.getCondition().put("transDateStart", transDateStart);
			criteria.getCondition().put("transDateEnd", transDateStart);
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<TransChangeInfo> page = transMgrService.getTransChangeInfoList(criteria);
			getRequest().setAttribute("page", page);
		}
		return "transmgr/transChangeInfoList";
	}
	
	/**
	 * 交易修复查询
	 * @return
	 */
	@RequestMapping(value="/queryTransList")
	public ModelAndView queryTransList(){
		Criteria criteria=getCriteria();
		ModelAndView model=new ModelAndView();
		model.setViewName("transmgr/queryTransList");;
		String tradeNo=(String) criteria.getCondition().get("tradeNo");
		
		if("post".equalsIgnoreCase(getRequest().getMethod())){
			if(StringUtils.isEmpty(tradeNo)){
				return ajaxDoneError("流水号不能为空");
			}
			PageInfo<TransInfo> page = transMgrService.getTransList(criteria);
			//getRequest().setAttribute("page", page);
			model.addObject("page", page);
		}
		
		
		//getRequest().setAttribute("form", criteria.getCondition());
		model.addObject("form", criteria.getCondition());
		return model;
	}
	
	/**
	 * 修改交易参考号页面
	 * @return
	 */
	@RequestMapping(value="/updateTrans")
	public String updateTrans(){
		Criteria criteria=getCriteria();
		String tradeNo=(String) criteria.getCondition().get("tradeNo");
		if(!StringUtils.isEmpty(tradeNo)){
				PageInfo<TransInfo> page = transMgrService.getTransList(criteria);
				if(page.getData()!=null&&page.getData().size()>0){
					getRequest().setAttribute("trans", page.getData().get(0));
				}
		}
		return "transmgr/updateTrans";
	}
	/**
	 * 修改交易参考号
	 * @return
	 */
	@RequestMapping(value="/doUpdateTrans")
	public ModelAndView doUpdateTrans(@RequestParam("tradeNo")String tradeNo,@RequestParam("autoCode")String autoCode){
		if(StringUtils.isEmpty(tradeNo)){
			return ajaxDoneError("流水号不能为空");
		}
		if(StringUtils.isEmpty(autoCode)){
			return ajaxDoneError("交易参考号不能为空");
		}
		int i=transMgrService.updateAutoCodeByTradeNo(tradeNo, autoCode);
		if(i>0){
			return ajaxDoneSuccess("修改成功");
		}
		return ajaxDoneError("修改失败");
	}
}
