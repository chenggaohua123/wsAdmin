package com.gateway.api.service;

import java.util.List;
import java.util.Map;

import com.gateway.api.model.BaseDataInfo;
import com.gateway.api.model.PicInfo;
import com.gateway.api.model.SMSVerificationInfo;
import com.gateway.api.model.TerInfo;
import com.gateway.api.model.VersionInfo;
import com.gateway.common.excetion.APIException;

public interface BaseMgrService {
	/**
	 * 保存图片信息
	 * @param info
	 * @return
	 */
	public PicInfo savePicInfo(PicInfo info) throws APIException;
	
	/**
	 * 图片下载
	 * @param info
	 * @return
	 * @throws APIException
	 */
	public PicInfo downLoadPic(PicInfo info) throws APIException;;
	
	/**
	 * 
	 * @param info
	 * @return
	 */
	public Map<String, List<BaseDataInfo>> getBaseData(BaseDataInfo info);
	
	/**
	 * 终端解绑
	 * @param info
	 * @return
	 */
	public int delSerNoFromTerNo(TerInfo info);
	
	/**
	 * 保存短信验证码
	 * @param info
	 * @return
	 */
	public int saveSMSVerificationInfo(SMSVerificationInfo info);
	
	/**
	 * 查询短信验证码
	 * @param phoneNo
	 * @return
	 */
	public int querySMSVerificationInfoByPhone(String phoneNo);
	
	/**
	 * 根据硬件终端序列号查询终端信息
	 * @param snNo
	 * @return
	 */
	public TerInfo queryTerInfoBySnNo(String snNo);
	
	/**
	 * 查询当前版本号
	 * @param info
	 * @return
	 */
	public VersionInfo queryVersionInfo(VersionInfo info);
	
}
