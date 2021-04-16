package com.gateway.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gateway.api.mapper.UserMgrDao;
import com.gateway.api.model.BusInfo;
import com.gateway.api.model.LoginHisInfo;
import com.gateway.api.model.MerchantInfo;
import com.gateway.api.model.ReSetPass;
import com.gateway.api.model.TerInfo;
import com.gateway.api.model.UserExtInfo;
import com.gateway.api.model.UserInfo;
import com.gateway.api.utils.Constants;
import com.gateway.api.utils.SMSSendUtil;
import com.gateway.common.excetion.APIException;
import com.gateway.common.utils.SHA256Utils;
import com.gateway.common.utils.UUIDGenerator;
import com.gateway.merchantmgr.mapper.MerchantMgrDao;

@Service
public class UserMgrServiceImpl implements UserMgrService{

	@Autowired
	private UserMgrDao userMgrDao;
	@Autowired
	private MerchantMgrDao merchantMgrDao;
	
	@Override
	@Transactional(noRollbackFor=Exception.class)
	public boolean register(UserInfo info) throws APIException {
		info.setPassWord(SHA256Utils.getSHA256Encryption(info.getPassWord()+info.getPhoneNo()+info.getUserName()));
		info.setEnabled(Constants.USER_STATUS_1);
		info.setSystemId(Constants.USER_SYSTEMID);
		info.setShaKey(UUIDGenerator.nextId());
		UserInfo tempInfo = userMgrDao.queryUserInfoByPhoneNo(info.getPhoneNo());
		if(null != tempInfo){
			throw new APIException(Constants.API_ERROE_CODE_0006,Constants.API_ERROE_CODE_0007_DESC);
		}
//		if(null == info.getAgentNo() || "".equals(info.getAgentNo().trim())){
//			info.setAgentNo(Constants.DEFAULE_AGENT_NO);
//		}
//		AgentInfo agentInfo = merchantMgrDao.queryAgentByAgentNo(info.getAgentNo());
//		if(null == agentInfo){
//			throw new APIException(Constants.API_ERROE_CODE_0006,Constants.API_ERROE_CODE_0006_DESC);
//		}
		int i = userMgrDao.addPhoneUser(info);
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public UserExtInfo validateUser(String userName, String phoneNo, String passWord) {
		UserExtInfo userExtInfo = new UserExtInfo();
		userExtInfo.setUserName(userName);
		userExtInfo.setPhoneNo(phoneNo);
		userExtInfo.setPassWord(passWord);
		userExtInfo = userMgrDao.login(userExtInfo);
		return userExtInfo;
	}

	public UserMgrDao getUserMgrDao() {
		return userMgrDao;
	}

	public void setUserMgrDao(UserMgrDao userMgrDao) {
		this.userMgrDao = userMgrDao;
	}

	public MerchantMgrDao getMerchantMgrDao() {
		return merchantMgrDao;
	}

	public void setMerchantMgrDao(MerchantMgrDao merchantMgrDao) {
		this.merchantMgrDao = merchantMgrDao;
	}

	@Override
	@Transactional(noRollbackFor=Exception.class)
	public UserExtInfo login(UserExtInfo info) throws APIException {
		info.setPassWord(SHA256Utils.getSHA256Encryption(info.getPassWord()+info.getPhoneNo()+info.getUserName()));
		LoginHisInfo tempInfo = info.getLoginHisInfo();
		UserExtInfo userExtInfo = userMgrDao.login(info);
		if(null == userExtInfo){
			throw new APIException(Constants.API_ERROE_CODE_0001, Constants.API_ERROE_CODE_0001_DESC);
		}
		if(userExtInfo.getEnabled()==Constants.USER_STATUS_4){
			throw new APIException(Constants.API_ERROE_CODE_0001, Constants.API_ERROE_CODE_0001_DESC);
		}
		
		//查询商户信息
		
		MerchantInfo merchantInfo = userMgrDao.queryMerchantInfoByPhone(userExtInfo.getPhoneNo());
		
		userExtInfo.setMerchantInfo(merchantInfo);
		
		//查询上一次登陆信息
		LoginHisInfo loginInfo = userMgrDao.queryHisLoginInfoByPhoneNo(userExtInfo.getPhoneNo());
		if(null != loginInfo){
			userExtInfo.setLoginHisInfo(loginInfo);
		}
		//查询交易终端信息
		List<TerInfo> terList = userMgrDao.queryTerInfoByPhoneNo(userExtInfo.getPhoneNo());
		if(null != terList){
			userExtInfo.setTerInfoList(terList);
		}
		//营业信息
		BusInfo busInfo = userMgrDao.queryBusInfoByPhoneNo(userExtInfo.getPhoneNo());
		if(null != busInfo){
			userExtInfo.setBusInfo(busInfo);
		}
		
		//查询开通的功能列表
		List<String> functionList = userMgrDao.queryFunctionListByPhoneNo(userExtInfo.getPhoneNo());
		if(null != functionList){
			userExtInfo.setFunctionList(functionList);
		}
		
		//写入登陆记录
		if(null != tempInfo){
			userMgrDao.addLogHisInfo(tempInfo, info.getPhoneNo());
		}
		//设置返回值
		userExtInfo.setRespCode(Constants.API_ERROE_CODE_0000);
		userExtInfo.setRespDesc(Constants.API_ERROE_CODE_0000_DESC);
		return userExtInfo;
	}

	@Override
	public int resetPass(ReSetPass info) {
		info.setPassWord(SHA256Utils.getSHA256Encryption(info.getNewPassWord()+info.getPhoneNo()+info.getUserName()));
		int i = userMgrDao.resetPass(info);
		return i;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int forgetPass(ReSetPass info) throws APIException {
		UserInfo userInfo = userMgrDao.queryUserInfoByPhoneNo(info.getPhoneNo());
		info.setUserName(userInfo.getUserName());
		int sum=(int)(Math.random()*1000000);
		info.setPassWord(SHA256Utils.getSHA256Encryption(sum+info.getPhoneNo()+info.getUserName()));
		int i = userMgrDao.resetPass(info);
		if(i>0){
			String context = "尊敬的用户，您的密码已经重置为："+sum+"【环商通】" ;
			String status=SMSSendUtil.sendLANZSMS("869402","hstkeji","2B2821F6D9D2234132828AEA60EA39C93A8E243C",info.getPhoneNo(),context,"","","");
			if("0".equals(status)){
				return i;
			}else{
				throw new APIException(Constants.API_ERROE_CODE_0021, Constants.API_ERROE_CODE_0021_DESC); 
			}
		}
		return i;
	}

	@Override
	public int updateMerchantinfo(MerchantInfo info) {
		return userMgrDao.updateMerchantInfo(info);
	}

	@Override
	public int updateTerInfo(TerInfo info) {
		return userMgrDao.updateTerInfo(info);
	}
}
