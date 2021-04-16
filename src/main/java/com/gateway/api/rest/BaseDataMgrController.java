package com.gateway.api.rest;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gateway.api.model.BaseInfo;
import com.gateway.api.model.PicInfo;
import com.gateway.api.model.SMSVerificationInfo;
import com.gateway.api.model.TerInfo;
import com.gateway.api.model.TerVerificationInfo;
import com.gateway.api.model.VersionInfo;
import com.gateway.api.service.BaseMgrService;
import com.gateway.api.utils.Constants;
import com.gateway.api.utils.SMSSendUtil;
import com.gateway.common.adapter.web.BaseDataListener;
import com.gateway.common.excetion.APIException;
import com.gateway.sysmgr.model.BaseDataInfo;
//import com.google.common.base.Strings;

@Controller
@RequestMapping(value="/api/basemgr")
public class BaseDataMgrController extends ApiBaseController{
	@Resource
	private BaseMgrService baseMgrService;
	
	
	private static String KEY="2B2821F6D9D2234132828AEA60EA39C93A8E243C";
	
	public BaseMgrService getBaseMgrService() {
		return baseMgrService;
	}

	public void setBaseMgrService(BaseMgrService baseMgrService) {
		this.baseMgrService = baseMgrService;
	}

	/**
	 * 上传图片
	 * @param info
	 * @return
	 * @throws APIException 
	 */
	@RequestMapping(value="/uploadPic")
	@ResponseBody
	public PicInfo uploadPic(PicInfo info) throws APIException{
		info = baseMgrService.savePicInfo(info);
		info.setPicBuffer("");
		return info;
	}
	
	/**
	 * 图片下载
	 * @return
	 * @throws APIException 
	 */
	@ResponseBody
	@RequestMapping(value="/downLoadPic")
	public PicInfo downLoadPic(PicInfo info) throws APIException{
		return baseMgrService.downLoadPic(info);
	}
	
	/**
	 * 基础数据下载
	 * @param info
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getBaseData")
	public List<BaseDataInfo> getBaseData(String tableName){
		List<BaseDataInfo> list = BaseDataListener.queryBaseDateByTableName(tableName);
		return list;
	}
	
	/**
	 * 终端解绑
	 * @param terInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delSerNoFromTerNo")
	public BaseInfo delSerNoFromTerNo(TerInfo terInfo,BaseInfo info){
		int i = baseMgrService.delSerNoFromTerNo(terInfo);
		if(i>0){
			info.setRespCode(Constants.API_ERROE_CODE_0000);
			info.setRespDesc(Constants.API_ERROE_CODE_0000_DESC);	
		}else{
			info.setRespCode(Constants.API_ERROE_CODE_9999);
			info.setRespDesc(Constants.API_ERROE_CODE_9999_DESC);	
		}
		return info;
	}
	
	/**
	 * 终端SN号验证
	 * @param info
	 * @return
	 * @throws APIException 
	 */
	@ResponseBody
	@RequestMapping(value="/terVerificationInfo")
	public TerVerificationInfo terVerificationInfo(TerVerificationInfo info) throws APIException{
		if(StringUtils.isEmpty(info.getSnNo())){
			throw new APIException(Constants.API_ERROE_CODE_0016,Constants.API_ERROE_CODE_0016_DESC);
		}
		TerInfo terInfo = baseMgrService.queryTerInfoBySnNo(info.getSnNo());
		if(null != terInfo){
			throw new APIException(Constants.API_ERROE_CODE_0017,Constants.API_ERROE_CODE_0017_DESC);
		}
		info.setRespCode(Constants.API_ERROE_CODE_0000);
		info.setRespDesc(Constants.API_ERROE_CODE_0000_DESC);	
		return info;
	}
	
	/**
	 * 发送短信验证码
	 * @param info
	 * @return
	 * @throws APIException 
	 */
	@ResponseBody
	@RequestMapping(value="/sendSms")
	public SMSVerificationInfo sendSms(final SMSVerificationInfo info) throws APIException{
		log.info("发送短信的手机号码："+info.getPhoneNo());
		if(StringUtils.isEmpty(info.getPhoneNo())){
			throw new APIException(Constants.API_ERROE_CODE_0003,Constants.API_ERROE_CODE_0003_DESC);
		}
		if(!KEY.equals(info.getKey())){
			throw new APIException(Constants.API_ERROE_CODE_0004,Constants.API_ERROE_CODE_0004_DESC);
		}
		if(StringUtils.isEmpty(info.getCentext())){
			throw new APIException(Constants.API_ERROE_CODE_0015,Constants.API_ERROE_CODE_0015_DESC);
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				String status=SMSSendUtil.sendLANZSMS("869402","hstkeji","2B2821F6D9D2234132828AEA60EA39C93A8E243C",info.getPhoneNo(),info.getCentext(),"","","");
				log.info("发送短信状态："+("0".equals(status)?"成功":"失败"));
				int i = baseMgrService.saveSMSVerificationInfo(info);
				log.info("数据插入状态："+(i>0?"成功":"失败"));
			}
		}).start();
		info.setRespCode(Constants.API_ERROE_CODE_0000);
		info.setRespDesc(Constants.API_ERROE_CODE_0000_DESC);	
		return info;
	}
	
	/**
	 * 查询最新的版本号
	 * @return
	 * @throws APIException 
	 */
	@ResponseBody
	@RequestMapping(value="/queryVersionInfo")
	public VersionInfo queryVersionInfo(VersionInfo info) throws APIException{
		if(StringUtils.isEmpty(info.getSysType())){
			throw new APIException(Constants.API_ERROE_CODE_0018,Constants.API_ERROE_CODE_0018_DESC);
		}
		info = baseMgrService.queryVersionInfo(info);
		if(null == info){
			throw new APIException(Constants.API_ERROE_CODE_0018,Constants.API_ERROE_CODE_0018_DESC);
		}
		info.setRespCode(Constants.API_ERROE_CODE_0000);
		info.setRespDesc(Constants.API_ERROE_CODE_0000_DESC);
		return info;
	}
	
	
	
}
