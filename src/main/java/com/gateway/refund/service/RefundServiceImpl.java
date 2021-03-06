package com.gateway.refund.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gateway.api.utils.Constants;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.APIException;
import com.gateway.common.utils.MD5Encryption;
import com.gateway.common.utils.SHA256Utils;
import com.gateway.common.utils.Tools;
import com.gateway.merchantmgr.mapper.MerchantMgrDao;
import com.gateway.merchantmgr.model.MerchantTerInfo;
import com.gateway.refund.mapper.RefundMapper;
import com.gateway.refund.mapper.TransInfoLogMapper;
import com.gateway.refund.model.BankRefundInfo;
import com.gateway.refund.model.CountTransMoney;
import com.gateway.refund.model.RefundInfo;
import com.gateway.refund.model.RefundModel;
import com.gateway.refund.model.TransInfoLog;
import com.gateway.transmgr.model.TransInfo;
import com.gateway.transmgr.service.TransMgrService;

@Service
public class RefundServiceImpl implements RefundService {
	private static Log logger = LogFactory.getLog(RefundServiceImpl.class);
	@Autowired
	private RefundMapper refundMapper;
	@Autowired
	private MerchantMgrDao merchantMgrDao;
	@Autowired
	private TransMgrService transMgrService;
	@Resource
	private TransInfoLogMapper transInfoLogMapper;
	@Value("${REFUND_URL}")
	private String REFUND_URL;

	@Override
	public PageInfo<RefundInfo> queryRefundInfo(Criteria criteria) {
		PageInfo<RefundInfo> page=new PageInfo<RefundInfo>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(refundMapper.countRefundInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		page.setData(refundMapper.queryRefundInfo(criteria, rb));
		return page;
	}

	@Override
	public RefundInfo queryRefundInfoById(String id) {
		return refundMapper.queryRefundInfoById(id);
	}
	
	@Override
	public RefundModel doRefund(RefundInfo refundInfo) throws APIException {
		RefundModel info=new RefundModel();
		System.out.println(refundInfo.getTransLogId());
//		String shaKey=refundMapper.getMerchantShaKeyByTradeNo(refundInfo.getTradeNo());
//		if(null==shaKey){
//			throw new APIException("1001","??????shaKey?????????");
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
		map.put("transLogId", String.valueOf(refundInfo.getTransLogId()));
		for(String key:map.keySet()){
			sb.append(key+"="+map.get(key)+"&");
		}
		System.out.println(sb.toString());
//		sb.append("shaKey="+shaKey);
		String md5=MD5Encryption.getSHA256Encryption(sb.toString());
		System.out.println(md5);
		DefaultHttpClient httpClient = Tools.getHttpClient(); // ?????????????????????
		HttpPost postMethod = new HttpPost(REFUND_URL);
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		for(String key:map.keySet()){
			nvps.add(new BasicNameValuePair(key, map.get(key)));
		}
		postMethod.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		int statusCode = 0;
		try {
			HttpResponse resp = httpClient.execute(postMethod); // ????????????
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
				logger.info(refundInfo.getTradeNo()+"  ???????????????????????????");
				//???????????????????????????
				refundMapper.updateTransLogInfoById(refundInfo.getTransLogId(),"0001:??????????????????");
			}else{
				info=JSON.toJavaObject(json, RefundModel.class);
				if("0000".equals(info.getRespCode())){
					info.setRespMsg(info.getRespCode()+":????????????");
					refundMapper.updateTransLogInfoById(refundInfo.getTransLogId(),info.getRespMsg());
					refundMapper.updateTransInfoByLogId(refundInfo.getTransLogId(),info);
				}else{
					info.setRespMsg(json.getString("respMsg")+":????????????");
					refundMapper.updateTransLogInfoById(refundInfo.getTransLogId(),info.getRespMsg());
					refundMapper.updateTransInfoByLogId(refundInfo.getTransLogId(),info);
				}
				return info;
			}
			throw new APIException("2002", "??????????????????");
		} catch (Exception e) {
			logger.info(refundInfo.getTradeNo() + ":?????????????????????");
			throw new APIException("2002", "??????????????????");
		} finally {
			try {
				postMethod.clone();
			} catch (CloneNotSupportedException e) {
			}
		}
	}
	
	private void updateRefundInfo(RefundInfo refundInfo) throws APIException{
		 refundMapper.updateRefundInfoById(refundInfo);
		TransInfoLog log = new TransInfoLog();
		log.setTradeNo(refundInfo.getTradeNo());
		try{
			log.setStatus(Integer.parseInt(refundInfo.getStatus()));
		}catch(Exception e){}
		if("1".equals(refundInfo.getStatus())){
			log.setStatus(0);
		}
		log.setTransType(Constants.TRSAN_INFO_TYPE_REFUND);
		transInfoLogMapper.updateTransInfoChange(log);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int insertRefundTransInfo(RefundInfo  refundInfo) throws APIException{
		TransInfo info = transMgrService.queryTransDetailByTradeNo(refundInfo.getTradeNo());
		info.setTransType(Constants.TRSAN_INFO_TYPE_REFUND);
		String transInfoNo = Tools.getChangeTransInfoNo(Constants.TRSAN_INFO_TYPE_REFUND);//?????????????????????
		info.setTradeNo(transInfoNo);
		BigDecimal rate = new BigDecimal( refundInfo.getRefundAmount()).divide(info.getMerTransAmount(),6,BigDecimal.ROUND_HALF_UP);
		info.setMerTransAmount(new BigDecimal( refundInfo.getRefundAmount()).multiply(new BigDecimal(-1)));
		info.setBankTransAmount(info.getBankTransAmount().multiply(rate).multiply(new BigDecimal(-1)));
		info.setMerSettleAmount(info.getMerSettleAmount().multiply(rate).multiply(new BigDecimal(-1)));
		info.setBondAmount(new BigDecimal(0));
		info.setMerForAmount(new BigDecimal(0));
		info.setAgentForAmount(new BigDecimal(0));
		info.setParentAgentForAmount(new BigDecimal(0));
		info.setRespCode("00");
		String status=refundInfo.getStatus();//?????? 0 ????????? 1,??????2 ????????????
		if(!"2".equals(status)){//0 ????????? 1,??????
			refundMapper.updateRefundInfoById(refundInfo);
			TransInfoLog log = transInfoLogMapper.queryTransInfoLogId(refundInfo.getTransLogId());
			log.setStatus(1);
			transInfoLogMapper.updateTransInfoLog(log);
		}else{//2 ????????????
			refundInfo.setTradeNewNo(transInfoNo);
			refundMapper.updateRefundInfoById(refundInfo);
			transMgrService.insertTransInfo(info);
			//??????????????????
			TransInfoLog log = transInfoLogMapper.queryTransInfoLogId(refundInfo.getTransLogId());
			log.setTradeNewNo(transInfoNo);
			log.setStatus(2);
			int s = transInfoLogMapper.updateTransInfoLog(log);
			if(0 >= s){
				logger.error("???????????? ??????ID???????????????????????????????????? ??????");
				throw new APIException("?????????????????????????????? ");
			}
			int j = transInfoLogMapper.updateTransInfoChange(log);
			if(0 >= j){
				logger.error("???????????? ?????????????????? ??????");
				throw new APIException("?????????????????????????????? ");
			}
		}
		String refundNotifyURL = refundInfo.getRefundNotifyURL();
		if(!StringUtils.isEmpty(refundNotifyURL)){
			//?????????????????? start
			String respCode=null;
			String respMsg=null;
			switch (status) {
			case "1"://1,??????
				respCode="01";//????????????
				respMsg="failed";//????????????
				break;
			case "2"://2 ????????????
				respCode="00";//????????????
				respMsg="success";//????????????
				break;
			default:
				return 1;//??????????????????
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
			MerchantTerInfo merchantTerInfo = merchantMgrDao.queryTerInfoByMerNoAndTerNo(merNo, terNo);
			String shaKey=merchantTerInfo.getShaKey();
			if(shaKey==null){
				logger.info(refundInfo.getTradeNo()+"shaKey???");
				return 1;//????????????
			}
			sb.append(shaKey);
			String hashcode=SHA256Utils.getSHA256Encryption(sb.toString());
			// ???????????????
			try {
				List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
				// ??????????????????????????????
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
				// ????????????????????????notifyUrl
				DefaultHttpClient httpClient = Tools.getHttpClient(); // ?????????????????????
				HttpPost postMethod = new HttpPost(refundNotifyURL);
				postMethod.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
				HttpResponse resp1 = httpClient.execute(postMethod); // ????????????
				int statusCode = resp1.getStatusLine().getStatusCode();
				logger.info(tradeNo + "????????????????????????:" + statusCode);
				if(200==statusCode){
					HttpEntity entity = resp1.getEntity();
					InputStream inSm = entity.getContent();
					Scanner inScn = new Scanner(inSm);
					String resultJson = null;
					if (inScn.hasNextLine()) {
						resultJson = inScn.nextLine();
					}
					logger.info(tradeNo + "??????????????????????????????resultJson:" + resultJson);
					JSONObject json = JSON.parseObject(resultJson);
					if ("1".equals(json.getString("refundNotifyStatus"))) {
						refundInfo.setRefundNotifyStatus(1);//????????????
						refundMapper.updateRefundNotifyStatusById(refundInfo);
						logger.info(tradeNo + "??????????????????????????????");
					} 
				}else {
					refundInfo.setRefundNotifyStatus(2);//????????????
					refundMapper.updateRefundNotifyStatusById(refundInfo);
					logger.info(tradeNo + "??????????????????????????????");
				}
			} catch (Exception e) {
				logger.info(tradeNo + "??????????????????+"+refundNotifyURL+"+??????????????????????????????:"+e.getMessage());
			}
			//?????????????????? end
		}
		return 1;
	}
	
	@Override
	public int insertTransInfoLog(TransInfoLog log) throws APIException{
		if(StringUtils.isEmpty(log) || StringUtils.isEmpty(log.getTradeNo())){
			throw new APIException("??????????????????");
		}
		TransInfo info = transMgrService.queryTransDetailByTradeNo(log.getTradeNo());//??????????????????
		if(StringUtils.isEmpty(info)){
			throw new APIException("??????????????????");
		}
		RefundInfo rInfo = new RefundInfo();
		rInfo.setTradeNo(log.getTradeNo());
		rInfo.setMerNo(info.getMerNo());
		rInfo.setTerNo(info.getTerNo());
		rInfo.setRefundCurrency(info.getMerBusCurrency());
		rInfo.setRefundAmount(log.getTransMoney()+"");
		rInfo.setRefundReason(log.getTransReason());//??????
		rInfo.setStatus("2");
		rInfo.setApplyBy(log.getCreateBy());
		rInfo.setRemark(log.getRemark());
		rInfo.setTransLogId(log.getId());
		rInfo.setReType("1");
		return refundMapper.insertRefundInfo(rInfo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int updateRefundInfoById(RefundInfo refundInfo) throws APIException{
		int y = refundMapper.updateRefundInfoById(refundInfo);
		if(0 >= y){
			logger.error("???????????? ??????ID???????????????????????? ??????");
			throw new APIException("?????????????????????????????? ");
		}
		TransInfoLog log = transInfoLogMapper.queryTransInfoLogId(refundInfo.getTransLogId());
		log.setStatus(1);
		int s = transInfoLogMapper.updateTransInfoLog(log);
		if(0 >= s){
			logger.error("???????????? ??????ID???????????????????????????????????? ??????");
			throw new APIException("?????????????????????????????? ");
		}
		log.setStatus(0);
		int j = transInfoLogMapper.updateTransInfoChange(log);
		if(0 >= j){
			logger.error("???????????? ?????????????????? ??????");
			throw new APIException("?????????????????????????????? ");
		}
		return 1;
		//return refundMapper.updateRefundInfoById(refundInfo);
	}

	@Override
	public String checkRefundInfo(RefundInfo info) {
		TransInfo tInfo = refundMapper.qeuryTrasnInfo(info.getTradeNo());
		if(StringUtils.isEmpty(tInfo)){
			return "?????????????????????????????????";
		}
		info.setMerNo(tInfo.getMerNo());
		info.setTerNo(tInfo.getTerNo());
		info.setRefundCurrency(tInfo.getMerBusCurrency());// ??????????????????
		CountTransMoney cMoney = countTransMoney(info.getTradeNo());
		if(0 == cMoney.getSurplusMoney().compareTo(new BigDecimal(0))){
			return "???????????????????????????";
		}
		if(-1 == cMoney.getSurplusMoney().compareTo(new BigDecimal(info.getRefundAmount()))){
			return "????????????????????????" + cMoney.getSurplusMoney();
		}
		return null;
	}
	
	@Override
	public CountTransMoney countTransMoney(String tradeNo) {
		CountTransMoney cMoney = transInfoLogMapper.countTransMoney(tradeNo);
		BigDecimal money = cMoney.getTransMoney().subtract(cMoney.getRefundMoney()).subtract(cMoney.getFrozenMoney()).subtract(cMoney.getDishonorMoney()).add(cMoney.getThawMoney());
		cMoney.setSurplusMoney(money);
		return cMoney;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int addRefundInfo(RefundInfo info)throws APIException{
		info.setReType("0");
		int i = refundMapper.addRefundTransInfoLog(info);//
		if(0 >= i){
			throw new APIException("??????????????????????????????LOG??????");
		}
		int id = refundMapper.selectTransInfoLog();
		info.setTransLogId(id);
		int y = refundMapper.addRefundInfo(info);
		if(0 >= y){
			throw new APIException("??????????????????????????????");
		}
		return 1;
	}

	@Override
	public List<RefundInfo> uploadRefundFile(Criteria criteria) {
		return refundMapper.queryRefundInfo(criteria);
	}

	
	@Override
	public PageInfo<BankRefundInfo> queryBankRefundInfo(Criteria criteria) {
		PageInfo<BankRefundInfo> page=new PageInfo<BankRefundInfo>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(refundMapper.countBankRefundInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		page.setData(refundMapper.queryBankRefundInfo(criteria, rb));
		return page;
	}
	
	@Override
	public List<BankRefundInfo> exportBankRefundInfo(Criteria criteria) {
		return refundMapper.queryBankRefundInfo(criteria);
	}
}
