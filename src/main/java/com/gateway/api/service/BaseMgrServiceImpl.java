package com.gateway.api.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gateway.api.mapper.BaseMgrMapper;
import com.gateway.api.mapper.TransApiMgrDao;
import com.gateway.api.model.BaseDataInfo;
import com.gateway.api.model.PicInfo;
import com.gateway.api.model.SMSVerificationInfo;
import com.gateway.api.model.TerInfo;
import com.gateway.api.model.VersionInfo;
import com.gateway.api.utils.Constants;
import com.gateway.common.excetion.APIException;

@Service
public class BaseMgrServiceImpl implements BaseMgrService {

	@Autowired
	private BaseMgrMapper baseMgrMapper;
	@Autowired
	private TransApiMgrDao transApiMgrDao;
	
	public TransApiMgrDao getTransApiMgrDao() {
		return transApiMgrDao;
	}

	public void setTransApiMgrDao(TransApiMgrDao transApiMgrDao) {
		this.transApiMgrDao = transApiMgrDao;
	}

	public BaseMgrMapper getBaseMgrMapper() {
		return baseMgrMapper;
	}

	public void setBaseMgrMapper(BaseMgrMapper baseMgrMapper) {
		this.baseMgrMapper = baseMgrMapper;
	}

	@Override
	public PicInfo savePicInfo(PicInfo info) throws APIException {
		List<PicInfo> tempInfo = baseMgrMapper.queryPicInfoByTradeNoAndPhoneNoAndType(info.getTradeNo(), 
				info.getPhoneNo(),
				info.getPicType());
		if(null != tempInfo && tempInfo.size() > 0){
			throw new APIException(Constants.API_ERROE_CODE_0013,Constants.API_ERROE_CODE_0013_DESC);
		}
		baseMgrMapper.savePicInfo(info);
		info.setRespCode(Constants.API_ERROE_CODE_0000);
		info.setRespDesc(Constants.API_ERROE_CODE_0000_DESC);
		return info;
	}

	@Override
	public Map<String, List<BaseDataInfo>> getBaseData(BaseDataInfo info) {
		return null;
	}

	@Override
	public PicInfo downLoadPic(PicInfo info) throws APIException {
		List<PicInfo> tempInfo = baseMgrMapper.queryPicInfoByTradeNoAndPhoneNoAndType(info.getTradeNo(), 
				info.getPhoneNo(),
				info.getPicType());
		if(null != tempInfo && tempInfo.size()>0){
			info = tempInfo.get(0);
			info.setRespCode(Constants.API_ERROE_CODE_0000);
			info.setRespDesc(Constants.API_ERROE_CODE_0000_DESC);	
			return info;
		}else{
			throw new APIException(Constants.API_ERROE_CODE_0014,Constants.API_ERROE_CODE_0014_DESC);
		}
	}

	@Override
	public int delSerNoFromTerNo(TerInfo info) {
		return baseMgrMapper.delSerNoFromTerNo(info);
	}

	@Override
	public int saveSMSVerificationInfo(SMSVerificationInfo info) {
		return baseMgrMapper.saveSMSVerificationInfo(info);
	}

	@Override
	public int querySMSVerificationInfoByPhone(String phoneNo) {
		return baseMgrMapper.querySMSVerificationInfoByPhone(phoneNo);
	}

	@Override
	public TerInfo queryTerInfoBySnNo(String snNo) {
		//根据SN号查询代理商信息
		String agentNo = transApiMgrDao.queryAgentInfoBySnNo(snNo);
		if(null == agentNo){
			return null;
		}
		return baseMgrMapper.queryTerInfoBySnNo(snNo);
	}

	@Override
	public VersionInfo queryVersionInfo(VersionInfo info) {
		return baseMgrMapper.queryVersionInfo(info);
	}
	
}
