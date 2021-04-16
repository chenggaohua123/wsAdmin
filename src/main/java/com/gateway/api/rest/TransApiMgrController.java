package com.gateway.api.rest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gateway.api.model.BusInfo;
import com.gateway.api.model.HasUseLimitAmountInfo;
import com.gateway.api.model.QueryTerAmountLimitInfoCondition;
import com.gateway.api.model.SettleQueryCondtion;
import com.gateway.api.model.TerAmountLimitInfo;
import com.gateway.api.model.TerInfo;
import com.gateway.api.model.TotalSettleCondition;
import com.gateway.api.model.TotalTransInfoCondtion;
import com.gateway.api.model.TransQueryCondition;
import com.gateway.api.model.TransRegisterInfo;
import com.gateway.api.service.TransApiMgrService;
import com.gateway.api.utils.Constants;
import com.gateway.common.excetion.APIException;
//import com.google.common.base.Strings;

@Controller
@RequestMapping(value="/api/transmgr")
public class TransApiMgrController extends ApiBaseController{
	
	@Resource
	private TransApiMgrService transApiMgrService;
	
	
	public TransApiMgrService getTransApiMgrService() {
		return transApiMgrService;
	}

	public void setTransApiMgrService(TransApiMgrService transApiMgrService) {
		this.transApiMgrService = transApiMgrService;
	}

	/**
	 * 收款功能开通
	 * @param info
	 * @return
	 * @throws APIException 
	 */
	@ResponseBody
	@RequestMapping(value="/transfunctionregister")
	public TransRegisterInfo transFunctionregister(TransRegisterInfo transRegInfo,BusInfo busInfo,TerInfo terInfo) throws APIException{
		transRegInfo.setBusInfo(busInfo);
		transRegInfo.setTerInfo(terInfo);
		transRegInfo = transApiMgrService.transFunctionregister(transRegInfo);
		return transRegInfo;
	}
	
	/**
	 * 交易列表
	 * @param conn
	 * @return
	 * @throws APIException 
	 */
	@ResponseBody
	@RequestMapping(value="/queryTransList")
	public TransQueryCondition queryTransList(TransQueryCondition info) throws APIException{
		info = transApiMgrService.queryTransList(info);
		return info;
	}
	
	/**
	 * 结算列表查询
	 * @param conn
	 * @return
	 * @throws APIException 
	 */
	@ResponseBody
	@RequestMapping(value="/querySettleList")
	public SettleQueryCondtion querySettleList(SettleQueryCondtion conn) throws APIException{
		if(StringUtils.isEmpty(conn.getMerNo())){
			throw new APIException(Constants.API_ERROE_CODE_0020,Constants.API_ERROE_CODE_0020_DESC);
		}
		conn = transApiMgrService.querySettleList(conn);
		conn.setRespCode(Constants.API_ERROE_CODE_0000);
		conn.setRespDesc(Constants.API_ERROE_CODE_0000_DESC);	
		return conn;
	}
	
	/**
	 * 结算统计
	 * @param info
	 * @return
	 * @throws APIException 
	 */
	@ResponseBody
	@RequestMapping(value="/totalSettleList")
	public TotalSettleCondition totalSettleList(TotalSettleCondition info) throws APIException{
		if(StringUtils.isEmpty(info.getMerNo())){
			throw new APIException(Constants.API_ERROE_CODE_0020,Constants.API_ERROE_CODE_0020_DESC);
		}
		info = transApiMgrService.totalSettleList(info);
		info.setRespCode(Constants.API_ERROE_CODE_0000);
		info.setRespDesc(Constants.API_ERROE_CODE_0000_DESC);
		return info;
	}
	/**
	 * 交易详细信息
	 * @param info
	 * @return
	 * @throws APIException 
	 */
	@ResponseBody
	@RequestMapping(value="/totalTransInfoList")
	public TotalTransInfoCondtion totalTransInfoList(TotalTransInfoCondtion info) throws APIException{
		if(StringUtils.isEmpty(info.getMerNo())){
			throw new APIException(Constants.API_ERROE_CODE_0020,Constants.API_ERROE_CODE_0020_DESC);
		}
		if(StringUtils.isEmpty(info.getTotalBy())){
			throw new APIException(Constants.API_ERROE_CODE_0024,Constants.API_ERROE_CODE_0024_DESC);
		}
		info = transApiMgrService.totalTransInfoList(info);
		info.setRespCode(Constants.API_ERROE_CODE_0000);
		info.setRespDesc(Constants.API_ERROE_CODE_0000_DESC);
		return info;
	}
	
	/**
	 * 交易限额查询
	 * @param info
	 * @return
	 * @throws APIException 
	 */
	@ResponseBody
	@RequestMapping(value="/queryTerAmountLimitInfo")
	public QueryTerAmountLimitInfoCondition queryTerAmountLimitInfo(QueryTerAmountLimitInfoCondition info) throws APIException{
		info = transApiMgrService.queryTerAmountLimitInfo(info);
		if(null != info && null != info.getList()){
			//查询已使用的限额
			for(TerAmountLimitInfo limitInfo :info.getList()){
				//查询日限额
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Calendar cal1 = Calendar.getInstance();
				cal1.add(Calendar.DATE, -1);
				Calendar cal2 = Calendar.getInstance();
				List<HasUseLimitAmountInfo> dlimitInfo = transApiMgrService.queryHasUseLimitAmount(df.format(cal1.getTime()), df.format(cal2.getTime()), null, info.getMerNo(), info.getTerNo());
				if(null != dlimitInfo){
					for(HasUseLimitAmountInfo u:dlimitInfo){
						if("借记卡".equals(u.getCardType())){
							limitInfo.setHasUseDDayTransAmountLimit(u.getTransAmount()==null ? new BigDecimal(0):u.getTransAmount());
						}
						if("贷记卡".equals(u.getCardType())){
							limitInfo.setHasUseCDayTransAmountLimit(u.getTransAmount()==null ? new BigDecimal(0):u.getTransAmount());
						}
					}
				}
				Calendar cal3 = Calendar.getInstance();
				cal3.add(Calendar.MONTH, -1);
				cal3.set(Calendar.DATE, 1);
				Calendar cal4 = Calendar.getInstance();
				//查询月限额
				List<HasUseLimitAmountInfo> mlimitInfo = transApiMgrService.queryHasUseLimitAmount(df.format(cal3.getTime()), df.format(cal4.getTime()), null, info.getMerNo(), info.getTerNo());
				if(null != mlimitInfo){
					for(HasUseLimitAmountInfo u:mlimitInfo){
						if("借记卡".equals(u.getCardType())){
							limitInfo.setHasUseDMonthTransAmountLimit(u.getTransAmount()==null ? new BigDecimal(0):u.getTransAmount());
						}
						if("贷记卡".equals(u.getCardType())){
							limitInfo.setHasUseCMonthTransAmountLimit(u.getTransAmount()==null ? new BigDecimal(0):u.getTransAmount());
						}
					}
				}
			}
			
			info.setRespCode(Constants.API_ERROE_CODE_0000);
			info.setRespDesc(Constants.API_ERROE_CODE_0000_DESC);
		}else{
			throw new APIException(Constants.API_ERROE_CODE_0023,Constants.API_ERROE_CODE_0023_DESC);
		}
		return info;
	}
}
