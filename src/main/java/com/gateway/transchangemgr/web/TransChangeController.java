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
	 * ??????????????????????????????
	 * */
	@RequestMapping(value = "/listCheckTrans")
	public String listCheckTrans() {
		PageInfo<TransCheckInfo> page = transChangeServiceImpl
				.queryCheckTransInfo(getCriteria());
		getRequest().setAttribute("page", page);
		return "transchangemgr/listCheckTrans";
	}

	/**
	 * ??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(value = "/uploadCheckFile")
	public String uploadCheckFile() {
		return "transchangemgr/uploadCheckFile";
	}

	/**
	 * ??????????????????
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
			log.info("?????????" + files.size() + "????????????");
			final List<TransCheckInfo> transChecked = new ArrayList<TransCheckInfo>();
			for (MultipartFile file : files) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						file.getInputStream()));// ????????????BufferedReader??????????????????
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
						log.info("??????line:" + line);
					}
					line = br.readLine();
				}
			}
			// ??????????????????
			if (transChecked.size() > 0) {
				new Thread(new Runnable() {
					public void run() {
						int count = transChangeServiceImpl
								.saveTransCheckDetail(transChecked);
						log.info("??????????????????" + count);
					}
				}).start();
			}

		}
		return ajaxDoneSuccess("???????????????");
	}

	/**
	 * ?????????????????????????????????
	 * 
	 * @throws ServiceException
	 * */
	@RequestMapping(value = "/goUpdateTransChecked")
	public String goUpdateTransChecked(String id) throws ServiceException {
		TransCheckInfo transCheckInfo = transChangeServiceImpl
				.queryCheckTransInfoById(id);
		if (null != transCheckInfo && transCheckInfo.getStatus() != 2) {
			throw new ServiceException("???????????????????????????");
		}
		getRequest().setAttribute("info", transCheckInfo);
		return "transchangemgr/updateTransChecked";
	}

	/**
	 * ??????????????????
	 * 
	 * @throws ServiceException
	 * */
	@RequestMapping(value = "/updateTransChecked")
	public ModelAndView updateTransChecked(TransCheckInfo transCheckInfo)
			throws ServiceException {
		transCheckInfo.setLastModifyBy(getLogAccount().getRealName());
		int i = transChangeServiceImpl.updateTransChecked(transCheckInfo);
		if (i > 0) {
			return ajaxDoneSuccess("????????????");
		} else {
			return ajaxDoneError("????????????");
		}
	}

	/**
	 * 
	 * ????????????????????????
	 **/
	@RequestMapping(value = "/goExportTransInfo")
	public String goExportTransInfo() {
		return "transchangemgr/transExport/transExport";
	}
	
	/** ?????????????????????????????? */
	@RequestMapping(value = "/downloadTransModel")
	public void downloadTransModel(HttpServletResponse resp,String type){
		if("1".equals(type)){
			ArrayList<String> strArray = new ArrayList<String> ();
			strArray.add("?????????");
			strArray.add("BR1506161604560740");
			strArray.add("BR1506161604560741");
			downloadFile(resp, "transModel.txt", "modelType:tradeNo", strArray);
			return;
		}
		if("2".equals(type)){
			ArrayList<String> strArray = new ArrayList<String> ();
			strArray.add("?????????,????????????");
			strArray.add("199227530825,20150808");
			strArray.add("199227530825,20150808");
			downloadFile(resp, "transModel.txt", "modelType:orderNo", strArray);
			return;
		}
		if("3".equals(type)){
			ArrayList<String> strArray = new ArrayList<String> ();
			strArray.add("??????????????????,????????????,??????????????????,?????????");
			strArray.add("123456****1234,20150808,6532");
			strArray.add("123456****1234,20150808,3214");
			downloadFile(resp, "transModel.txt", "modelType:cardInfo", strArray);
			return;
		}
		if("4".equals(type)){
			ArrayList<String> strArray = new ArrayList<String> ();
			strArray.add("?????????,?????????");
			strArray.add("1234569876541234,6532");
			strArray.add("1234569876541234,3214");
			downloadFile(resp, "transModel.txt", "modelType:cardNo", strArray);
		}
		if("5".equals(type)){
			ArrayList<String> strArray = new ArrayList<String> ();
			strArray.add("????????????,???????????????,????????????");
			strArray.add("021823160,653211,20150903");
			strArray.add("021823160,653211,20150903");
			downloadFile(resp, "transModel.txt", "modelType:ksDis", strArray);
		}
	}

	/**
	 * ??????????????????
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
			log.info("?????????" + files.size() + "????????????");
			Criteria criteria = new Criteria();
			for (MultipartFile file : files) {
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(),"GBK"));//
				// ????????????BufferedReader??????????????????
				String line = br.readLine();//???????????????
				int length = Tools.replaceBlank(line).length();
				log.info("????????????line:"+line+"??????????????????"+length);
				String [] strInfo = line.split(":");
				String name = "";
				if(!org.springframework.util.StringUtils.isEmpty(strInfo) || 2 == strInfo.length){
					name = strInfo[1];
				}
				line = br.readLine();//???????????????
				line = br.readLine();//?????????3???
				int lineCount = 3;//?????????????????????
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
							sb.append("???"+lineCount+ "????????????" +line+";");
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
							sb.append("???"+lineCount+ "????????????" + line+";");
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
						criteria.getCondition().put("checkNo", Funcs.encrypt(no[0]));//???6???
						criteria.getCondition().put("last", Funcs.encrypt(no[1]));//???4???
						criteria.getCondition().put("transDate", fileds[index++]);
						if(3 == fileds.length){
							criteria.getCondition().put("autoCode", fileds[index++]);//?????????
						}
						List<TransInfo> transInfo = transChangeServiceImpl.queryTransInfo(criteria);
						if(!org.springframework.util.StringUtils.isEmpty(transInfo)&& 0 != transInfo.size()) {
							for(TransInfo li:transInfo){
								list.add(li);
								sbr.append(li.getTradeNo()+",");
							}
						}else{
							sb.append("???"+lineCount+ "????????????" +line+";");
						}
					}
					if ("cardNo".equals(name)) {
						int index = 0;
						criteria.getCondition().put("checkToNo", Funcs.eccryptSHA(fileds[index++]));//?????????
//						criteria.getCondition().put("transDate", fileds[index++]);//????????????
//						if(3 == fileds.length){
							criteria.getCondition().put("autoCode", fileds[index++]);//?????????
//						}
						List<TransInfo> transInfo = transChangeServiceImpl.queryTransInfo(criteria);
						if(!org.springframework.util.StringUtils.isEmpty(transInfo)&& 0 != transInfo.size()) {
							for(TransInfo li:transInfo){
								list.add(li);
								sbr.append(li.getTradeNo()+",");
							}
						}else{
							sb.append("???"+lineCount+ "????????????" +line+";");
						}
					}
					if("ksDis".equals(name)){
						int index = 0;
						criteria.getCondition().put("merchantNo", fileds[index++]);//????????????
						criteria.getCondition().put("autoCode", fileds[index++]);//????????????
						criteria.getCondition().put("transDate", fileds[index++]);//????????????
						List<TransInfo> transInfo = transChangeServiceImpl.queryTransInfo(criteria);
						if(!org.springframework.util.StringUtils.isEmpty(transInfo)&& 0 != transInfo.size()) {
							for(TransInfo li:transInfo){
								list.add(li);
								sbr.append(li.getTradeNo()+",");
							}
						}else{
							sb.append("???"+lineCount+ "????????????" +line+";");
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
		return ajaxDoneSuccess("???????????????????????????");
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
	
	/** ?????????????????????????????? */
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
		resp.reset(); // ???????????????????????????
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "transLogList.xls");
		WritableWorkbook book = Workbook.createWorkbook(os);
		WritableSheet sheet = book.createSheet("????????????", 0);
		String[] headerName = { "?????????","???????????????","?????????","?????????","????????????","????????????","??????????????????","????????????","?????????","???????????????","?????????","??????","??????","????????????",
				"??????????????????","????????????","????????????","????????????","????????????","????????????","????????????","????????????","????????????","????????????","????????????","????????????",
				"????????????","????????????????????????","??????????????????","????????????","???????????????","???????????????","??????","???????????????","???????????????","IP","????????????","???????????????","????????????","?????????/ ???",
				"????????????","??????","????????????","????????????","????????????","?????????/???","????????????","????????????","??????????????????" ,"?????????","??????????????????","CPD??????","????????????","?????????"};
		// ????????????
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
				String refundStatusTemp="1".equals(transInfo.getRefundStatus())?"?????????":"?????????";
				transInfo.setRefundStatus(refundStatusTemp);
				String dishonorStatusTemp="1".equals(transInfo.getDishonorStatus())?"?????????":"?????????";
				transInfo.setDishonorStatus(dishonorStatusTemp);
				String frozenStatusTemp="1".equals(transInfo.getFrozenStatus())?"?????????":"?????????";
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
				
				
				sheet.addCell( new Label(col++, row, transInfo.getMerNo()));//?????????
				sheet.addCell( new Label(col++, row, transInfo.getTerNo()));//?????????
				sheet.addCell( new Label(col++, row, transInfo.getTradeNo()));//???????????????
				sheet.addCell( new Label(col++, row, transInfo.getOrderNo()));//?????????
				sheet.addCell( new Label(col++, row, transInfo.getTransDate()));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getMerBusCurrency() + " " + transInfo.getMerTransAmount()));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getBankCurrency() + " " + transInfo.getBankTransAmount()));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getMerSettleCurrency() + " " + transInfo.getMerSettleAmount()));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getMerSettleCurrency() + " " + transInfo.getMerForAmount()));//?????????
				sheet.addCell( new Label(col++, row, transInfo.getMerSettleCurrency() + " " + transInfo.getSingleFee()));//???????????????
				sheet.addCell( new Label(col++, row, transInfo.getMerSettleCurrency() + " " + transInfo.getBondAmount()));//?????????
				sheet.addCell( new Label(col++, row, transInfo.getCardType()));//??????
				sheet.addCell( new Label(col++, row, transInfo.getPayWebSite()));//??????
				sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("RESP_INFO",transInfo.getRespCode()+"","????????????")));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getRespMsg()));//??????????????????
				sheet.addCell( new Label(col++, row, transInfo.getRiskScore()));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getCurrencyName()));//????????????
				sheet.addCell( new Label(col++, row, "1".equals(transInfo.getIschecked())?"?????????":"?????????" ));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getAccess()==1?"?????????":"?????????" ));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getDishonorStatus()));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getDishonorAmount()));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getRefundStatus()));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getRefundAmount()));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getFrozenStatus()));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getFrozenAmount()));//????????????
				
				sheet.addCell( new Label(col++, row, "??????"));//????????????
				
				sheet.addCell( new Label(col++, row, Tools.parseWebInfoToResourceType(transInfo.getWebInfo())));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getAcquirer()));//????????????????????????
				sheet.addCell( new Label(col++, row, transInfo.getCardFullNo()));//??????????????????
				if(!org.springframework.util.StringUtils.isEmpty(transInfo.getGoodsInfoByte())){//????????????
					sheet.addCell(new Label(col++, row, new String(transInfo.getGoodsInfoByte(),"utf-8")));
					System.out.println("===="+new String(transInfo.getGoodsInfoByte(),"utf-8"));
				}else{
					sheet.addCell(new Label(col++, row, ""));
				}
				sheet.addCell( new Label(col++, row, transInfo.getCardFullName()));//??????
				sheet.addCell( new Label(col++, row, transInfo.getGrPerName()));//??????
				sheet.addCell( new Label(col++, row, transInfo.getEmail()));//??????
				sheet.addCell( new Label(col++, row, transInfo.getCardFullPhone()));//??????
				sheet.addCell( new Label(col++, row, transInfo.getGrphoneNumber()));//??????
				sheet.addCell( new Label(col++, row, transInfo.getIpAddress()));//IP
				sheet.addCell( new Label(col++, row, transInfo.getIpCountry()));//????????????
				sheet.addCell( new Label(col++, row, cb.getCountry_code()));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getGrCountry()));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getGrState()));//?????????/ ???
				sheet.addCell( new Label(col++, row, transInfo.getGrAddress()));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getGrZipCode()));//??????
				sheet.addCell( new Label(col++, row, transInfo.getIogistics()));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getTrackNo()));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getCardCountry()));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getCardState()));//?????????/???
				sheet.addCell( new Label(col++, row, transInfo.getCardAddress()));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getExceptionDate()));//????????????
				if(transInfo.getExceptionDate()!=null){
					if((Double.parseDouble(transInfo.getRefundAmount().replaceAll(",", ""))+Double.parseDouble(transInfo.getDishonorAmount().replaceAll(",", "")))==Double.parseDouble(transInfo.getMerTransAmount().replaceAll(",", ""))){
						sheet.addCell( new Label(col++, row, "????????????"));//????????????
					}else{
						sheet.addCell( new Label(col++, row, "????????????"));//????????????
					}
					
				}else{
					sheet.addCell( new Label(col++, row, ""));//????????????
				}
				sheet.addCell( new Label(col++, row, transInfo.getAutoCode()));//????????????
				sheet.addCell( new Label(col++, row, Double.parseDouble(transInfo.getRefundAmount().replaceAll(",", ""))*(Double.parseDouble(transInfo.getMerTransAmount().replaceAll(",", ""))/Double.parseDouble(transInfo.getMerSettleAmount().replaceAll(",", "")))+""));//??????????????????
				sheet.addCell( new Label(col++, row, transInfo.getCPDDate()));//CPD??????
				sheet.addCell( new Label(col++, row, "1".equals(transInfo.getIsFake())?"???":"???"));//CPD??????
				sheet.addCell( new Label(col++, row, transInfo.getBankPosNo()));//CPD??????
			}
		}

		book.write();
		book.close();
		os.flush();
		os.close();
	}

	/**
	 * ????????????
	 * */
	@RequestMapping(value = "/transSuccessToFail")
	public String transSuccessToFail() {
		return "transchangemgr/transSuccessToFail";
	}

	/**
	 * ?????????????????????????????????
	 * */
	@RequestMapping(value = "/updateTransFail")
	public ModelAndView updateTransFail(String tradeNo) {
		String tradeNos[]=tradeNo.split(",");
		int num=transChangeServiceImpl.updateTransFail(tradeNos,getLogAccount().getRealName());
		return ajaxDoneSuccess("??????????????????"+tradeNos.length+",????????????????????????"+num);
		
		
	}

	/**
	 * ???????????????????????????
	 * */
	@RequestMapping(value = "/uploadTransToReRunFile")
	public String uploadTransToReRunFile() {
		return "transchangemgr/uploadTransToReRunFile";
	}

	/**
	 * ?????????????????????????????????????????????
	 * */
	@RequestMapping(value = "/acceptTransToRunFile")
	public ModelAndView acceptTransToRunFile(
			DefaultMultipartHttpServletRequest request) throws IOException,
			ParseException, ISOException {
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		if (null != files) {
			log.info("?????????" + files.size() + "????????????");
			final List<ReRunTransModel> list = new ArrayList<ReRunTransModel>();
			for (MultipartFile file : files) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						file.getInputStream()));// ????????????BufferedReader??????????????????
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
			// ??????????????????
			if (list.size() > 0) {
				for (final ReRunTransModel info : list) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//????????????????????????????????????
					transChangeServiceImpl.updateTransToPendingByTradeNo(info.getTradeNo());
					//??????????????????
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
									.getHttpClient(); // ?????????????????????
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
										.execute(postMethod); // ????????????
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
		return ajaxDoneSuccess("???????????????");
	}

	/***
	 * ??????????????????????????????
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
	 * ??????????????????????????????
	 * */
	@RequestMapping(value = "/listTransHighRiskRerunInfo")
	public String listTransHighRiskRerunInfo() {
		PageInfo<TransInfo> page = transChangeServiceImpl
				.getTransHighRiskRerunInfoList(getCriteria());
		getRequest().setAttribute("page", page);
		return "transchangemgr/listTransHighRiskRerunInfo";
	}

	/**
	 * ?????????????????????????????????????????????
	 * */
	@RequestMapping(value = "/goRerunTransHighRiskRerunInfo")
	public String goRerunTransHighRiskRerunInfo(String tradeNo) {
		getRequest().setAttribute("tradeNo", tradeNo);
		return "transchangemgr/rerunTransHighRiskRerunInfo";
	}

	/**
	 * ????????????????????????????????????
	 * **/
//	@RequestMapping(value = "/rerunTransHighRiskRerunInfo")
//	public ModelAndView rerunTransHighRiskRerunInfo(String tradeNo) {
//		TreeMap<String, String> map = new TreeMap<String, String>();
//		map.put("tradeNo", tradeNo);
//		DefaultHttpClient httpClient = Tools.getHttpClient(); // ?????????????????????
//		HttpPost postMethod = new HttpPost(PaymentConfig.PAYMENT_ADDRESS
//				+ "payment/reRunTrans/reToPay/");
//		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
//		for (String key : map.keySet()) {
//			nvps.add(new BasicNameValuePair(key, map.get(key)));
//		}
//		postMethod.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
//		int statusCode = 0;
//		try {
//			HttpResponse resp = httpClient.execute(postMethod); // ????????????
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
//					return ajaxDoneSuccess("????????????");
//				} else {
//					return ajaxDoneSuccess("????????????");
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
//		return ajaxDoneError("????????????");
//	}
	@RequestMapping(value = "/exportTransRerunInfo")
	public void exportTransRerunInfo(HttpServletResponse resp) throws Exception, IOException,
			RowsExceededException, WriteException {
		List<String> tradeNos=transChangeServiceImpl
				.getTransListForTransRerunTradeNos(getCriteria());
		List<TransDetailInfo> list = transMgrService.queryTransInfoByTradeNos(tradeNos);
		OutputStream os=resp.getOutputStream();
		resp.reset(); // ???????????????????????????
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "transLogList.xls");
		WritableWorkbook book = Workbook.createWorkbook(os);
		WritableSheet sheet = book.createSheet("????????????", 0);
		String[] headerName = { "?????????","?????????","?????????","?????????","????????????","????????????","??????","??????","????????????",
				"??????????????????"};
		// ????????????
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		if (!org.springframework.util.StringUtils.isEmpty(list)) {
			
			for (int row = 1; row <= list.size(); row++) {
				
				
				int col = 0;
				TransDetailInfo transInfo = list.get(row - 1);
				sheet.addCell( new Label(col++, row, transInfo.getMerNo()));//?????????
				sheet.addCell( new Label(col++, row, transInfo.getTerNo()));//?????????
				sheet.addCell( new Label(col++, row, transInfo.getTradeNo()));//???????????????
				sheet.addCell( new Label(col++, row, transInfo.getOrderNo()));//?????????
				sheet.addCell( new Label(col++, row, transInfo.getTransDate()));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getMerBusCurrency() + " " + transInfo.getMerTransAmount()));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getCardType()));//??????
				sheet.addCell( new Label(col++, row, transInfo.getPayWebSite()));//??????
				sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("RESP_INFO",transInfo.getRespCode()+"","????????????")));//????????????
				sheet.addCell( new Label(col++, row, transInfo.getRespMsg()));//??????????????????
			}
		}

		book.write();
		book.close();
		os.flush();
		os.close();
	}
}
