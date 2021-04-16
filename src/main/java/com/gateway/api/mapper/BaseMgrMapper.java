package com.gateway.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gateway.api.model.PicInfo;
import com.gateway.api.model.SMSVerificationInfo;
import com.gateway.api.model.TerInfo;
import com.gateway.api.model.UserForOther;
import com.gateway.api.model.VersionInfo;

public interface BaseMgrMapper {
	/**
	 * 保存图片信息
	 * @param info
	 * @return
	 */
	public int savePicInfo(@Param("info") PicInfo info);
	
	/**
	 * 查询图片列表
	 * @return
	 */
	public List<PicInfo> queryPicInfoByTradeNoAndPhoneNoAndType(@Param("tradeNo") String tradeNo,
			@Param("phoneNo") String phoneNo,
			@Param("picType") String pciType);
	
	/**
	 * 终端解绑
	 * @param info
	 * @return
	 */
	public int delSerNoFromTerNo(@Param("info") TerInfo info);
	
	/**
	 * 根据硬件终端序列号查询终端信息
	 * @param snNo
	 * @return
	 */
	public TerInfo queryTerInfoBySnNo(@Param("snNo") String snNo);
	
	/**
	 * 保存短信验证码
	 * @param info
	 * @return
	 */
	public int saveSMSVerificationInfo(@Param("info") SMSVerificationInfo info);
	
	/**
	 * 查询短信验证码
	 * @param phoneNo
	 * @return
	 */
	public int querySMSVerificationInfoByPhone(@Param("phoneNo") String phoneNo);
	
	/**
	 * 查询当前版本号
	 * @param info
	 * @return
	 */
	public VersionInfo queryVersionInfo(@Param("info") VersionInfo info);

	public UserForOther queryUserInfo(UserForOther info);
}
