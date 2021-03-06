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
	 * ????????????
	 * @return
	 */
	@RequestMapping(value="/uploadCheckFile")
	public String uploadCheckFile(){
		return "transmgr/uploadCheckFile";
	}
	
	/** ?????????????????????????????? */
	@RequestMapping(value = "/downloadTransModel")
	public void downloadTransModel(HttpServletResponse resp,String type){
		if("2".equals(type)){
			ArrayList<String> strArray = new ArrayList<String> ();
			strArray.add("?????????,??????????????????");
			strArray.add("199227530825,10.01");
			strArray.add("199227530826,10.01");
			downloadFile(resp, "transModel.txt", "modelType:orderNo", strArray);
			return;
		}
		if("3".equals(type)){
			ArrayList<String> strArray = new ArrayList<String> ();
			strArray.add("??????????????????,????????????,??????????????????,?????????");
			strArray.add("123456****1234,20150808,10.00,6532");
			strArray.add("123456****1234,20150808,10.01,3214");
			downloadFile(resp, "transModel.txt", "modelType:cardInfo", strArray);
			return;
		}
		if("4".equals(type)){
			ArrayList<String> strArray = new ArrayList<String> ();
			strArray.add("?????????,????????????,??????????????????,?????????");
			strArray.add("1234569876541234,20150808,10.00,6532");
			strArray.add("1234569876541234,20150808,10.01,3214");
			downloadFile(resp, "transModel.txt", "modelType:cardNo", strArray);
		}
	}
	
	/**
	 * ????????????
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
	 * ????????????-????????????
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
	 * ???????????????
	 * @param tradeNo
	 * @param orderRespCode ????????????
	 * @return
	 * @throws APIException
	 */
	@ResponseBody
	@RequestMapping(value="/capture")
	public ModelAndView capture(String  tradeNo,String orderRespCode) throws APIException {
		if(!"0000".equals(orderRespCode)){
			return ajaxDoneError("?????????????????????????????????????????????");
		}
		DefaultHttpClient httpClient = Tools.getHttpClient(); // ?????????????????????
		HttpPost postMethod = new HttpPost(PAYMENT_ECP_CAPTURE_URL);
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("tradeNo", tradeNo));
		postMethod.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		try {
			HttpResponse resp = httpClient.execute(postMethod); // ????????????
			HttpEntity entity = resp.getEntity();
			String entityStr = EntityUtils.toString(entity, "UTF-8");
			System.out.println(entityStr);
			JSONObject json = (JSONObject) JSONPParserUtil.parseJSONP(entityStr);
			String respCode=json.getString("respCode");
			String respMsg=json.getString("respMsg");
			if("00".equals(respCode)){
				return ajaxDoneSuccess("?????????????????????:"+respMsg);
			}
			return ajaxDoneError("?????????????????????:"+respMsg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				postMethod.clone();
			} catch (CloneNotSupportedException e) {
				return ajaxDoneError("?????????????????????:"+e.getMessage());
			}
		}
		return ajaxDoneError("?????????????????????");
	}
	
	/**
	 * ???????????????
	 * @param tradeNo ?????????
	 * @param orderRespCode ????????????
	 * @return
	 * @throws APIException
	 */
	@ResponseBody
	@RequestMapping(value="/voidPre")
	public ModelAndView voidPre(String  tradeNo,String orderRespCode) throws APIException {
		if(!"0000".equals(orderRespCode)){
			return ajaxDoneError("?????????????????????????????????????????????");
		}
		DefaultHttpClient httpClient = Tools.getHttpClient(); // ?????????????????????
		HttpPost postMethod = new HttpPost(PAYMENT_ECP_VOID_URL);
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("tradeNo", tradeNo));
		postMethod.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		try {
			HttpResponse resp = httpClient.execute(postMethod); // ????????????
			HttpEntity entity = resp.getEntity();
			String entityStr = EntityUtils.toString(entity, "UTF-8");
			System.out.println(entityStr);
			JSONObject json = (JSONObject) JSONPParserUtil.parseJSONP(entityStr);
			String respCode=json.getString("respCode");
			String respMsg=json.getString("respMsg");
			if("00".equals(respCode)){
				return ajaxDoneSuccess("?????????????????????:"+respMsg);
			}
			return ajaxDoneError("?????????????????????:"+respMsg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				postMethod.clone();
			} catch (CloneNotSupportedException e) {
				return ajaxDoneError("?????????????????????:"+e.getMessage());
			}
		}
		return ajaxDoneError("?????????????????????");
	}
	
	/**
	 * ??????????????????
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
	 * ??????????????????
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws ISOException 
	 */
	@RequestMapping(value="/acceptCheckFile")
	public ModelAndView acceptCheckFile(DefaultMultipartHttpServletRequest request) throws Exception{
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		if(null != files){
			log.info("?????????"+files.size()+"????????????");
			List<TransInfo> list = new ArrayList<TransInfo>();
			StringBuffer sb = new StringBuffer();//???????????????
			StringBuffer transFail=new StringBuffer();
			List<Integer> tradeNoFails=new ArrayList<Integer>();
			for(MultipartFile file :files){
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(),"GBK"));//????????????BufferedReader??????????????????
				String line = br.readLine();
				int length = Tools.replaceBlank(line).length();//?????????
				log.info("????????????line:"+line+"??????????????????"+length);
				String [] strInfo = line.split(":");
				String name = "";
				if(!StringUtils.isEmpty(strInfo) && 2 == strInfo.length){
					name = strInfo[1];
				}
				line = br.readLine();//???????????????
				line = br.readLine();//?????????3???
				int lineCount = 3;//??????3????????????
				Criteria criteria = getCriteria();
				criteria.getCondition().put("ischecked", "0");
				while(null != line){
					log.info("line:"+line);
					String [] fileds = line.split(",");
					if ("orderNo".equals(name)) {
						log.info( "?????????????????????10 ?????????????????????"+lineCount+ "????????????" + line);
						int index = 0;
						criteria.getCondition().put("tradeNo", fileds[index++]);
						criteria.getCondition().put("bankTransAmount", fileds[index++]);
						List<TransInfo> transInfo = transChangeServiceImpl.queryTransInfo(criteria);
						if(!org.springframework.util.StringUtils.isEmpty(transInfo)&& 0 != transInfo.size()) {
							for(TransInfo li:transInfo){
								list.add(li);
								if(!"00".equals(li.getRespCode())){
									tradeNoFails.add(li.getId());
									transFail.append("???"+lineCount+ "????????????" + line+";");
								}
							}
							log.info("?????????????????????");
						}else{
							log.info("?????????????????????");
							sb.append("???"+lineCount+ "????????????" + line+";");
						}
					}
					if ("cardInfo".equals(name)) {
						log.info( "?????????????????????22 ?????????????????????"+lineCount+ "????????????" + line);
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
						criteria.getCondition().put("checkNo", Funcs.encrypt(no[0]));//???6???
						criteria.getCondition().put("last", Funcs.encrypt(no[1]));//???4???
						criteria.getCondition().put("transDate", fileds[index++]);
						criteria.getCondition().put("bankTransAmount", fileds[index++]);//??????????????????
						if(4 == fileds.length){
							criteria.getCondition().put("autoCode", fileds[index++]);//?????????
						}
						List<TransInfo> transInfo = transChangeServiceImpl.queryTransInfo(criteria);
						if(!org.springframework.util.StringUtils.isEmpty(transInfo)&& 0 != transInfo.size()) {
							for(TransInfo li:transInfo){
								list.add(li);
								if(!"00".equals(li.getRespCode())){
									tradeNoFails.add(li.getId());
									transFail.append("???"+lineCount+ "????????????" + line+";");
								}
							}
							log.info("?????????????????????");
						}else{
							log.info("?????????????????????");
							sb.append("???"+lineCount+ "????????????" +line+";");
						}
					}
					if ("cardNo".equals(name)) {
						log.info( "?????????????????????19 ?????????????????????"+lineCount+ "????????????" + line);
						int index = 0;
						criteria.getCondition().put("checkToNo", Funcs.eccryptSHA(fileds[index++]));//?????????
						criteria.getCondition().put("transDate", fileds[index++]);//????????????
						criteria.getCondition().put("bankTransAmount", fileds[index++]);//??????????????????
						if(4 == fileds.length){
							criteria.getCondition().put("autoCode", fileds[index++]);//?????????
						}
						List<TransInfo> transInfo = transChangeServiceImpl.queryTransInfo(criteria);
						if(!org.springframework.util.StringUtils.isEmpty(transInfo)&& 0 != transInfo.size()) {
							for(TransInfo li:transInfo){
								list.add(li);
								if(!"00".equals(li.getRespCode())){
									tradeNoFails.add(li.getId());
									transFail.append("???"+lineCount+ "????????????" + line+";");
								}
							}
							log.info("?????????????????????");
						}else{
							log.info("?????????????????????");
							sb.append("???"+lineCount+ "????????????" +line+";");
						}
					}
					line = br.readLine();
					lineCount++;
				}
			}
			//??????????????? ?????? ???????????????
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
		return ajaxDoneSuccess("???????????????????????????");
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
	
	/** ???????????? */
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
		modelView.addObject("message","??????????????????" + totalCount +" ;????????????????????????" + count);//?????????????????????????????????
		modelView.addObject("date",str);
		return modelView;*/
		return ajaxDoneSuccess("??????????????????" + totalCount +" ;????????????????????????" + count);
	}
	
	public void BankSettleDetail(){//?????????
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
				log.info("??????????????????"+count);
			}
		}).start();*/
	}

	/**
	 * ??????????????????
	 * @return
	 */
	@RequestMapping(value="/queryBankSettleDetail")
	public String queryBankSettleDetail(){
		PageInfo<BankSettleDetail> list = transMgrService.queryBankSettleDetail(getCriteria());
		getRequest().setAttribute("page", list);
		return "transmgr/bankSettleList"; 
	}
	
	/**
	 * ????????????????????????
	 * @return
	 */
	@RequestMapping(value="/updateCheckStatus")
	public ModelAndView updateCheckStatus(String [] ids){
		int count = transMgrService.updateCheckStatusSucceed(ids);
		return ajaxDoneSuccess("????????????????????????????????????"+count);
	}
	
	@RequestMapping(value="/queryTransDetailByRelNo")
	public String queryTransDetailByRelNo(String relNo){
		List<TransInfo> list = transMgrService.queryTransDetailByRelNo(relNo);
		getRequest().setAttribute("list", list);
		return "transmgr/transDetailList"; 
	}
	
	/**
	 * ?????????????????????????????????
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
	 * ?????????????????????????????????-????????????
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
	 *???????????? 
	 * @throws Exception 
	 */
	@RequestMapping(value="/exportTrans")
	public void exportTrans(HttpServletResponse resp) throws Exception{
		OutputStream os = null;
		os = resp.getOutputStream();
		resp.reset(); // ???????????????????????????
		resp.setHeader("Content-Disposition", "attachment; filename=" + "transList.xls");
		resp.setContentType("application/octet-stream; charset=utf-8");
		Criteria criteria=getCriteria();
		BIWorkbook bw=new BIWorkbook();
		BISheet bs = bw.addSheet();
		BIRow br_0 = bs.addRow();
		for(String str:new String[]{"?????????","?????????","?????????","????????????","????????????","????????????","????????????",
				  "????????????","????????????",
				 /* "??????????????????","??????????????????",*/
				  "?????????","???????????????","???????????????","?????????","???????????????","??????","????????????","??????????????????","????????????","????????????","????????????","????????????","????????????","????????????","????????????","????????????","???????????????","????????????????????????",
				  "??????????????????","???????????????","???????????????","?????????","??????","????????????","???????????????","???????????????","??????","??????","IP","????????????","????????????","?????????/ ???","????????????","??????","????????????","????????????","????????????","?????????/???","????????????",
				  /*"????????????????????????","????????????????????????","????????????????????????",*/
				  "?????????"}){
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
					respCodeInfo="????????????";
				}
				if("0000".equals(info.getRespCode())){
					respCodeInfo="???????????????";
				}
				if("01".equals(info.getRespCode())){
					respCodeInfo="????????????";
				}
				br_1.addCell().setCellValue(respCodeInfo,null);
				br_1.addCell().setCellValue(info.getRespMsg()+"",null);
				br_1.addCell().setCellValue(info.getRiskScore()+"",null);
				br_1.addCell().setCellValue("1".equals(info.getDishonorStatus())?"?????????":"?????????"+"",null);
				br_1.addCell().setCellValue(info.getMerBusCurrency()+" "+ info.getDishonorAmount()+"",null);
				br_1.addCell().setCellValue("1".equals(info.getRefundStatus())?"?????????":"?????????"+"",null);
				br_1.addCell().setCellValue(info.getMerBusCurrency()+" "+ info.getRefundAmount(),null);
				br_1.addCell().setCellValue("1".equals(info.getFrozenStatus())?"?????????":"?????????",null);
				br_1.addCell().setCellValue(info.getMerBusCurrency()+" "+ info.getFrozenAmount(),null);
				//sheet.addCell( new Label(col++, row, "???????????????"));//??????????????????
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
	 *?????????????????? 
	 * @throws Exception 
	 */
	@RequestMapping(value="/exportTransFinance")
	public void exportTransFinance(HttpServletResponse resp) throws Exception{
		OutputStream os = null;
		os = resp.getOutputStream();
		resp.reset(); // ???????????????????????????
		resp.setHeader("Content-Disposition", "attachment; filename=" + "transList.xls");
		resp.setContentType("application/octet-stream; charset=utf-8");
		Criteria criteria=getCriteria();
		BIWorkbook bw=new BIWorkbook();
		BISheet bs = bw.addSheet();
		BIRow br_0 = bs.addRow();
		for(String str:new String[]{"?????????","?????????","?????????","????????????","????????????","????????????","????????????",
				  "????????????","????????????",
				  "??????????????????","??????????????????",
				  "?????????","???????????????","???????????????","?????????","???????????????","??????","????????????","??????????????????","????????????","????????????","????????????","????????????","????????????","????????????","????????????","????????????","???????????????","????????????????????????",
				  "??????????????????","???????????????","???????????????","?????????","??????","????????????","???????????????","???????????????","??????","??????","IP","????????????","????????????","?????????/ ???","????????????","??????","????????????","????????????","????????????","?????????/???","????????????",
				  "????????????????????????","????????????????????????","????????????????????????",
				  "?????????"}){
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
					respCodeInfo="????????????";
				}
				if("0000".equals(info.getRespCode())){
					respCodeInfo="???????????????";
				}
				if("01".equals(info.getRespCode())){
					respCodeInfo="????????????";
				}
				br_1.addCell().setCellValue(respCodeInfo,null);
				br_1.addCell().setCellValue(info.getRespMsg()+"",null);
				br_1.addCell().setCellValue(info.getRiskScore()+"",null);
				br_1.addCell().setCellValue("1".equals(info.getDishonorStatus())?"?????????":"?????????"+"",null);
				br_1.addCell().setCellValue(info.getMerBusCurrency()+" "+ info.getDishonorAmount()+"",null);
				br_1.addCell().setCellValue("1".equals(info.getRefundStatus())?"?????????":"?????????"+"",null);
				br_1.addCell().setCellValue(info.getMerBusCurrency()+" "+ info.getRefundAmount(),null);
				br_1.addCell().setCellValue("1".equals(info.getFrozenStatus())?"?????????":"?????????",null);
				br_1.addCell().setCellValue(info.getMerBusCurrency()+" "+ info.getFrozenAmount(),null);
				//sheet.addCell( new Label(col++, row, "???????????????"));//??????????????????
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
	 * ????????????????????????
	 * @return
	 */
	@RequestMapping(value="/getSettleTransList")
	public String getSettleTransList(){
		PageInfo<SettleTransInfo> page = transMgrService.getSettleTransList(getCriteria());
		List<SettleTransInfo> settleTransAmount=transMgrService.getSettleTransAmount(getCriteria());
		getRequest().setAttribute("settleTransAmount",settleTransAmount);
	 	getRequest().setAttribute("page", page);
	 	//??????????????????
		return "transmgr/settleTransList";
	}
	
	/**
	 * ???????????????????????????
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
	 * ????????????????????? v
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
	 *???????????? 
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
		WritableSheet sheet = book.createSheet("??????????????????", 0);
		String[] headerName = { "???????????????", "?????????","?????????","?????????","????????????","????????????","???????????????","?????????","????????????","????????????","????????????","????????????","???????????????"};
		// ????????????
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
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("gw_transtype_info",info.getTransType(),"????????????")));
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
	 * ????????????????????????
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
		WritableSheet sheet = book.createSheet("??????????????????", 0);
		String[] headerName = new String []{ "???????????????", "?????????","?????????","?????????","????????????","????????????","???????????????","??????????????????","????????????????????????","?????????","????????????","????????????","???????????????"};
		// ????????????
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
			sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("gw_transtype_info",info.getTransType(),"????????????")));
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
	 * ????????????
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
	 * ??????????????????
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
	
	/** ?????????????????? */
	@RequestMapping(value="/uploadTransRecordFile")
	public void uploadTransRecordFile(HttpServletResponse resp) throws Exception{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "transRecordList.xls" );
		List<TransRecordInfo> list = transMgrService.queryTransRecordInfo(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("??????????????????", 0);
		String[] headerName = { "?????????","?????????","?????????","?????????","??????????????????","?????????","????????????","IP","??????","??????","????????????","????????????","??????????????????","????????????","????????????",
								"??????","??????","??????","IP","??????????????????","????????????","?????????/ ???","????????????","??????","????????????","?????????/???","????????????"};
		// ????????????
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			TransRecordInfo info = list.get(row-1);
			TransDetailInfo eInfo = transMgrService.queryTransInfo(info.getTradeNo());
			sheet.addCell( new Label(col++, row, info.getMerNo()));//?????????
			sheet.addCell( new Label(col++, row, info.getTerNo()));//?????????
			sheet.addCell( new Label(col++, row, info.getTradeNo()));//???????????????
			sheet.addCell( new Label(col++, row, info.getOrderNo()));//???????????????
			sheet.addCell( new Label(col++, row, info.getEnterTime()));//??????????????????
			sheet.addCell( new Label(col++, row, info.getRespCode()));//??????????????????
			sheet.addCell( new Label(col++, row, info.getRespMsg()));//??????????????????
			sheet.addCell( new Label(col++, row, info.getClientIP()));//IP
			sheet.addCell( new Label(col++, row, info.getDescription()));//??????
			sheet.addCell( new Label(col++, row, info.getTransPortProtocol()));//??????
			sheet.addCell( new Label(col++, row, info.getCurrencyCode() +" " + info.getAmount()));//????????????
			sheet.addCell( new Label(col++, row, info.getMerURL()));//????????????
			sheet.addCell( new Label(col++, row, info.getSubmitURL()));//??????????????????
			sheet.addCell( new Label(col++, row, info.getReturnURL()));//????????????
			if(!StringUtils.isEmpty(eInfo.getGoodsInfoByte())){//????????????
				sheet.addCell(new Label(col++, row, new String(eInfo.getGoodsInfoByte(),"utf-8")));
				System.out.println("===="+new String(eInfo.getGoodsInfoByte(),"utf-8"));
			}else{
				sheet.addCell(new Label(col++, row, ""));
			}
			sheet.addCell( new Label(col++, row, eInfo.getCardFullName()));//??????
			sheet.addCell( new Label(col++, row, eInfo.getEmail()));//??????
			sheet.addCell( new Label(col++, row, eInfo.getCardFullPhone()));//??????
			sheet.addCell( new Label(col++, row, eInfo.getIpAddress()));//IP
			sheet.addCell( new Label(col++, row, eInfo.getSixAndFourCardNo()));//??????????????????
			sheet.addCell( new Label(col++, row, eInfo.getGrCountry()));//????????????
			sheet.addCell( new Label(col++, row, eInfo.getGrState()));//?????????/ ???
			sheet.addCell( new Label(col++, row, eInfo.getGrAddress()));//????????????
			sheet.addCell( new Label(col++, row, eInfo.getGrZipCode()));//??????
			sheet.addCell( new Label(col++, row, eInfo.getCardCountry()));//????????????
			sheet.addCell( new Label(col++, row, eInfo.getCardState()));//?????????/???
			sheet.addCell( new Label(col++, row, eInfo.getCardAddress()));//????????????
		}
		book.write();
		book.close();
	}
	

	/**
	 * 
	 *??????email,cardNo,Ip?????????????????? 
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
		WritableSheet sheet = book.createSheet("????????????", 0);
		//String[] headerName = { "???????????????", "?????????","?????????","?????????","????????????","????????????","???????????????","?????????","????????????","????????????","????????????","????????????"};
		
		  String[] headerName={"?????????","?????????","?????????","????????????","????????????","????????????","?????????","???????????????","?????????","??????","????????????","??????????????????","????????????","????????????","????????????","????????????","????????????","????????????","????????????","????????????","????????????","???????????????","????????????????????????","??????????????????","??????","????????????","??????","??????","??????","IP","????????????","?????????/ ???","????????????","??????","????????????","????????????","????????????","?????????/???","????????????"};
		// ????????????
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
			sheet.addCell(new Label(col++, row, "00".equals(info.getRespCode())?"????????????":"????????????"));
			sheet.addCell( new Label(col++, row, info.getRespMsg()));//??????????????????
			sheet.addCell( new Label(col++, row, info.getRiskScore()));//????????????
			sheet.addCell(new Label(col++, row, "1".equals(info.getDishonorStatus())?"?????????":"?????????"));
			sheet.addCell(new Label(col++, row, info.getMerSettleCurrency()+" "+ info.getDishonorAmount()));
			sheet.addCell(new Label(col++, row, "1".equals(info.getRefundStatus())?"?????????":"?????????"));
			sheet.addCell(new Label(col++, row, info.getMerSettleCurrency()+" "+ info.getRefundAmount()));
			sheet.addCell(new Label(col++, row, "1".equals(info.getFrozenStatus())?"?????????":"?????????"));
			sheet.addCell(new Label(col++, row, info.getMerSettleCurrency()+" "+ info.getFrozenAmount()));
			sheet.addCell( new Label(col++, row, "???????????????"));//??????????????????
			sheet.addCell(new Label(col++, row, Tools.parseWebInfoToResourceType(info.getWebInfo())));//????????????????????????
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
	 *???????????????
	 * 
	 */
	/**
	 * ??????????????????
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
		WritableSheet sheet = book.createSheet("????????????", 0);
		//String[] headerName = { "???????????????", "?????????","?????????","?????????","????????????","????????????","???????????????","?????????","????????????","????????????","????????????","????????????"};
		
		  String[] headerName={"?????????","?????????","?????????","?????????","????????????","????????????",
				  "??????????????????",
				  "??????","????????????","??????","??????","??????","IP","????????????","?????????/ ???"
				  ,"????????????","??????","????????????","?????????/???"
				  ,"????????????"};
		// ????????????
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
	 * ??????
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
	 * ?????????????????????????????????????????????
	 */
	@RequestMapping(value="/toSelectSendInfo")
	public String toSelectSendInfo(){
		return "transmgr/selectCallbackSendInfo";
	}
	
	/**
	 * ????????????????????????????????????
	 */
	@RequestMapping(value="/toUploadTransCallbackInfo")
	public String toUploadTransCallbackInfo(TransCallbackInfo form){
		getRequest().setAttribute("info", form);
		return "transmgr/uploadTransCallbackFile";
	}
	
	/**
	 * ??????????????????????????????
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
							log.info("???"+linenum+"???????????????:"+"???????????????:"+info.getTradeNo()+",????????????:"+info.getEmailModel()+",????????????:"+info.getSendEmail()+",?????????:"+info.getUploadBy());
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
			log.info("????????????:"+successCount+"??????");
			log.info("????????????:"+faildCount+"??????");
			getRequest().getSession().setAttribute("failCallback", list);
			getRequest().getSession().setAttribute("callbackInfo", sb.toString());
		}
		return ajaxDoneError("??????????????????");
	}
	
	/**
	 * ??????????????????????????????
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
			sb.append("???????????????");
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
	 * ????????????????????????
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
			sb.append("???????????????");
			sb.append("\r\n");
			if(list!=null && list.size()>0){
				for(TransCallbackInfo info : list){
					log.info("??????????????????:???????????????:"+info.getTradeNo()+",????????????:"+info.getEmailModel()+",????????????:"+info.getSendEmail());
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
	 * ????????????????????????????????????
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
		WritableSheet sheet = book.createSheet("????????????", 0);
		//String[] headerName = { "???????????????", "?????????","?????????","?????????","????????????","????????????","???????????????","?????????","????????????","????????????","????????????","????????????"};
		
		  String[] headerName={"?????????","?????????","?????????","?????????","????????????","????????????","???????????????",
				  "????????????","????????????","?????????","????????????","??????????????????"};
		// ????????????
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
			sheet.addCell(new Label(col++, row, info.getEmailModel()!=null?("1".equals(info.getEmailModel())?"?????????????????????????????????":("2".equals(info.getEmailModel())?"?????????????????????????????????":("3".equals(info.getEmailModel())?"?????????????????????????????????":"????????????"))):"????????????"));
			sheet.addCell(new Label(col++, row, info.getEmail()!=null?info.getEmail():""));
			sheet.addCell(new Label(col++, row, info.getSendEmail()));
			sheet.addCell(new Label(col++, row, sdf.format(info.getUploadDate())));
			sheet.addCell(new Label(col++, row, info.getUploadBy()));
			sheet.addCell(new Label(col++, row, info.getSendStatus()!=null?("0".equals(info.getSendStatus())?"?????????":("1".equals(info.getSendStatus())?"????????????":("2".equals(info.getSendStatus())?"????????????":"?????????"))):"?????????"));
			sheet.addCell(new Label(col++, row, info.getSendTime()!=null?sdf.format(info.getSendTime()):""));
		}
			
			
		book.write();
		book.close();
	}
	
	/**
	 * ?????????????????????
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
	 * ???????????????????????????
	 */
	@RequestMapping(value="/queryWhiteInfo")
	public String queryWhiteInfo(String whiteIds){
		List<WhiteListInfo> list = transMgrService.queryWhiteInfoListByIds(whiteIds);
		getRequest().setAttribute("list", list);
		return "transmgr/listWhiteBlackTextInfo";
	}
	
	/**
	 * ?????????????????????????????????
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
		WritableSheet sheet = book.createSheet("???????????????????????????", 0);
		String[] headerName={"?????????","?????????","?????????","????????????","??????????????????","??????????????????","??????????????????","????????????"};
		// ????????????
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
	 * ??????????????????
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
	 * ??????????????????
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
				return ajaxDoneError("?????????????????????");
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
	 * ???????????????????????????
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
	 * ?????????????????????
	 * @return
	 */
	@RequestMapping(value="/doUpdateTrans")
	public ModelAndView doUpdateTrans(@RequestParam("tradeNo")String tradeNo,@RequestParam("autoCode")String autoCode){
		if(StringUtils.isEmpty(tradeNo)){
			return ajaxDoneError("?????????????????????");
		}
		if(StringUtils.isEmpty(autoCode)){
			return ajaxDoneError("???????????????????????????");
		}
		int i=transMgrService.updateAutoCodeByTradeNo(tradeNo, autoCode);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}
		return ajaxDoneError("????????????");
	}
}
