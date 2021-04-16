package com.gateway.api.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gateway.api.mapper.TransApiMgrDao;
import com.gateway.api.mapper.UserMgrDao;
import com.gateway.api.model.BusInfo;
import com.gateway.api.model.CurrencyInfo;
import com.gateway.api.model.HasUseLimitAmountInfo;
import com.gateway.api.model.MerchantInfo;
import com.gateway.api.model.QueryTerAmountLimitInfoCondition;
import com.gateway.api.model.SettleInfo;
import com.gateway.api.model.SettleQueryCondtion;
import com.gateway.api.model.TerAmountLimitInfo;
import com.gateway.api.model.TerInfo;
import com.gateway.api.model.TerInfoRelCurrencyInfo;
import com.gateway.api.model.TotalSettleCondition;
import com.gateway.api.model.TotalTransInfo;
import com.gateway.api.model.TotalTransInfoCondtion;
import com.gateway.api.model.TransInfo;
import com.gateway.api.model.TransQueryCondition;
import com.gateway.api.model.TransRegisterInfo;
import com.gateway.api.model.UserInfo;
import com.gateway.api.model.UserRelMerchantInfo;
import com.gateway.api.utils.Constants;
import com.gateway.common.excetion.APIException;

@Service
public class TransApiMgrServiceImpl implements TransApiMgrService{
	public static final Log log = LogFactory.getLog(TransApiMgrServiceImpl.class);
	@Autowired
	private UserMgrDao userMgrDao;
	@Autowired
	private TransApiMgrDao transApiMgrDao;

	public UserMgrDao getUserMgrDao() {
		return userMgrDao;
	}

	public void setUserMgrDao(UserMgrDao userMgrDao) {
		this.userMgrDao = userMgrDao;
	}
	public TransApiMgrDao getTransApiMgrDao() {
		return transApiMgrDao;
	}

	public void setTransApiMgrDao(TransApiMgrDao transApiMgrDao) {
		this.transApiMgrDao = transApiMgrDao;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public TransRegisterInfo transFunctionregister(TransRegisterInfo info) throws APIException {
		BusInfo busInfo = info.getBusInfo();
		TerInfo terInfo = info.getTerInfo();
		//查询用户信息
		UserInfo userInfo = userMgrDao.queryUserInfoByPhoneNo(info.getPhoneNo());
		if(null == userInfo){
			throw new APIException(Constants.API_ERROE_CODE_0008,Constants.API_ERROE_CODE_0008_DESC);
		}
		if(userInfo.getEnabled() != Constants.USER_STATUS_1){
			throw new APIException(Constants.API_ERROE_CODE_0010,Constants.API_ERROE_CODE_0010_DESC);
		}
	
		//根据查询商户信息
		MerchantInfo merchantInfo =  userMgrDao.queryMerchantInfoByPhone(info.getPhoneNo());
	
		//增加商户信息
		if(null == merchantInfo){
			merchantInfo = new MerchantInfo();
			try {
				merchantInfo.setMerNo(ISOUtil.padright(info.getPhoneNo(), 15, '0') );
			} catch (ISOException e) {
				
			}
			merchantInfo.setIDCardNo(busInfo.getIDCardNo());
			merchantInfo.setPhoneNo(info.getPhoneNo());
			merchantInfo.setAccountAddress(terInfo.getAccountAddress());
			merchantInfo.setAccountName(terInfo.getAccountName());
			merchantInfo.setAccountNo(terInfo.getAccountNo());
			merchantInfo.setAddress(busInfo.getComAdress());
			merchantInfo.setState(busInfo.getProvince());
			merchantInfo.setCity(busInfo.getCity());
			merchantInfo.setIndustry(busInfo.getIndustry());
			merchantInfo.setEmail(busInfo.getEmail());
			merchantInfo.setType(busInfo.getMerchantType());
			merchantInfo.setMerchantName((busInfo.getComName()==null||"".equals(busInfo.getComName().trim())?userInfo.getRealName():busInfo.getComName()));
			//生成用户商户编号
			transApiMgrDao.addMerchantInfo(merchantInfo);
		}
		
		//增加用户关联商户信息表
		UserRelMerchantInfo userRrlMerchantInfo = transApiMgrDao.queryUserRelMerchantInfoByPhoneNo(info.getPhoneNo());
		if(null == userRrlMerchantInfo){
			transApiMgrDao.addUserToMerchnatRelInfo(merchantInfo.getMerNo(), info.getPhoneNo());
		}
		
		//查询商户终端信息
		List<TerInfo> terList = userMgrDao.queryTerInfoByPhoneNo(info.getPhoneNo());
		if(null == terList || terList.size()<=0){
			//增加终端信息
			terInfo.setTerName((busInfo.getComName()==null||"".equals(busInfo.getComName().trim())?userInfo.getRealName():busInfo.getComName()));
			terInfo.setMerNo(merchantInfo.getMerNo());
			try {
				terInfo.setTerNo(ISOUtil.takeLastN(info.getPhoneNo(), 8));
			} catch (ISOException e) {
			}
			transApiMgrDao.addMerchantTerInfo(terInfo);
			//绑定通道信息
			//查询默认绑定的通道信息
			CurrencyInfo currencyInfo = transApiMgrDao.queryCurrencyByDefaultCurrency();
			if(null == currencyInfo){
				throw new APIException(Constants.API_ERROE_CODE_0009,Constants.API_ERROE_CODE_0009_DESC);
			}
			TerInfoRelCurrencyInfo relCurrency = new TerInfoRelCurrencyInfo();
			relCurrency.setCurrencyId(currencyInfo.getId());
			relCurrency.setMerNo(terInfo.getMerNo());
			relCurrency.setTerNo(terInfo.getTerNo());
			relCurrency.setEnabaled(1);
			transApiMgrDao.addCurrencyToTerInfo(relCurrency);
		}
		//商户号关联代理商号
		
		if(null != terInfo.getSerNo() && !"".equals(terInfo.getSerNo())){
			//根据SN号查询代理商户号
			TerInfo tempTerInfo = transApiMgrDao.queryTerInfoBySnNo(terInfo.getSerNo());
			if(null == tempTerInfo){
				//根据SN号查询代理商信息
				String agentNo = transApiMgrDao.queryAgentInfoBySnNo(terInfo.getSerNo().replace("\n", ""));
				log.info("开通的终端序列号为："+terInfo.getSerNo());
				log.info("终端绑定的代理商户号为："+agentNo);
				if(null == agentNo){
					throw new APIException(Constants.API_ERROE_CODE_0026,Constants.API_ERROE_CODE_0026_DESC); 
				}
				String  tempAgentNo =transApiMgrDao.queryAgentNoByMerNo(merchantInfo.getMerNo(),terInfo.getTerNo());
				if(null == tempAgentNo){
					transApiMgrDao.addMerchantToAgent(merchantInfo.getMerNo(),terInfo.getTerNo(),agentNo);
				}else if(!agentNo.equalsIgnoreCase(tempAgentNo)){
					throw new APIException(Constants.API_ERROE_CODE_0027,Constants.API_ERROE_CODE_0027_DESC); 
				}
			}else{
				throw new APIException(Constants.API_ERROE_CODE_0025,Constants.API_ERROE_CODE_0025_DESC);
			}
		}else{
			throw new APIException(Constants.API_ERROE_CODE_0025,Constants.API_ERROE_CODE_0025_DESC);
		}
		
		BusInfo tempBusInfo = userMgrDao.queryBusInfoByPhoneNo(info.getPhoneNo());
		if(null == tempBusInfo){
			//增加用户营业信息
			transApiMgrDao.addBusInfo(busInfo,info.getPhoneNo());
		}
		//更新用户状态
		transApiMgrDao.updateUserStatusByPhoneNo(info.getPhoneNo(), Constants.USER_STATUS_2);
		info.setRespCode(Constants.API_ERROE_CODE_0000);
		info.setRespDesc(Constants.API_ERROE_CODE_0000_DESC);
		return info;
	}

	@Override
	public TransQueryCondition queryTransList(TransQueryCondition info) throws APIException {
		MerchantInfo merchantInfo = userMgrDao.queryMerchantInfoByPhone(info.getPhoneNo());
		if(null == merchantInfo){
			throw new APIException(Constants.API_ERROE_CODE_0012, Constants.API_ERROE_CODE_0012_DESC);
		}
		info.setMerNo(merchantInfo.getMerNo());
		RowBounds rd = new RowBounds((info.getPageNum()-1)*info.getPageSize(), info.getPageSize());
		int total = transApiMgrDao.countTransList(info);
		List<TransInfo> list = transApiMgrDao.queryTransList(info, rd);
		info.setTransList(list);
		info.setTotal(total);
		
		info.setRespCode(Constants.API_ERROE_CODE_0000);
		info.setRespDesc(Constants.API_ERROE_CODE_0000_DESC);
		return info;
	}

	@Override
	public SettleQueryCondtion querySettleList(SettleQueryCondtion conn) {
		RowBounds rd = new RowBounds((conn.getPageNum()-1)*conn.getPageSize(), conn.getPageSize());
		List<SettleInfo> list = transApiMgrDao.querySettleList(conn,rd);
		conn.setSettleList(list);
		int count = transApiMgrDao.countSettleList(conn);
		conn.setTotal(count);
		return conn;
	}

	@Override
	public TotalSettleCondition totalSettleList(TotalSettleCondition info) {
		RowBounds rd = new RowBounds((info.getPageNum()-1)*info.getPageSize(), info.getPageSize());
		List<SettleInfo> list = transApiMgrDao.totalSettleList(info,rd);
		info.setSettleList(list);
		int count = transApiMgrDao.countTotalSettleList(info);
		info.setTotal(count);
		return info;
	}

	@Override
	public TotalTransInfoCondtion totalTransInfoList(TotalTransInfoCondtion info) {
		RowBounds rd = new RowBounds((info.getPageNum()-1)*info.getPageSize(), info.getPageSize());
		List<TotalTransInfo> list = transApiMgrDao.totalTransInfoList(info,rd);
		info.setTransList(list);
		int count = transApiMgrDao.countTotalTransInfoList(info);
		info.setTotal(count);
		return info;
	}

	@Override
	public QueryTerAmountLimitInfoCondition queryTerAmountLimitInfo(QueryTerAmountLimitInfoCondition info) {
		List<TerAmountLimitInfo> list = transApiMgrDao.queryTerAmountLimitInfo(info);
		info.setList(list);
		return info;
	}

	@Override
	public List<HasUseLimitAmountInfo> queryHasUseLimitAmount(String startDate,
			String endDate, String queryType, String merNo, String terNo) {
		return transApiMgrDao.queryHasUseLimitAmount(startDate, endDate, queryType, merNo, terNo);
	}
}
