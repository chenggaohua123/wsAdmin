package com.gateway.merchantmgr.service;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.APIException;
import com.gateway.common.excetion.ServiceException;
import com.gateway.loginmgr.model.UserInfo;
import com.gateway.merchantmgr.model.AgentInfo;
import com.gateway.merchantmgr.model.AgentUserInfo;
import com.gateway.merchantmgr.model.BatchUpdateCurrencyRelMerchantInfo;
import com.gateway.merchantmgr.model.BrandProductInfo;
import com.gateway.merchantmgr.model.CountryCurrencyInfo;
import com.gateway.merchantmgr.model.CountryCurrencyLogInfo;
import com.gateway.merchantmgr.model.GWAgentMerchantInfo;
import com.gateway.merchantmgr.model.GwAgentRelMerchant;
import com.gateway.merchantmgr.model.GwMerchantPaymentPage;
import com.gateway.merchantmgr.model.GwTernoLmitInfo;
import com.gateway.merchantmgr.model.GwUserRelMerchantInfo;
import com.gateway.merchantmgr.model.MccInfo;
import com.gateway.merchantmgr.model.MerchantConfig;
import com.gateway.merchantmgr.model.MerchantCurrencySpecialInfo;
import com.gateway.merchantmgr.model.MerchantDisFineInfo;
import com.gateway.merchantmgr.model.MerchantInfo;
import com.gateway.merchantmgr.model.MerchantRelCurrencyInfo;
import com.gateway.merchantmgr.model.MerchantSettleCycle;
import com.gateway.merchantmgr.model.MerchantTerInfo;
import com.gateway.merchantmgr.model.MerchantTypeInfo;
import com.gateway.merchantmgr.model.MerchantWebsite;
import com.gateway.merchantmgr.model.RegCodeInfo;
import com.gateway.merchantmgr.model.TerSnRelAgentInfo;
import com.gateway.merchantmgr.model.TransSettingInfo;

public interface MerchantMgrService {
	/**
	 * 获取商户列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<MerchantInfo> getListMerchant(Criteria criteria);
	
	/**
	 * 获取商户列表
	 * @param criteria
	 * @return
	 */
	public List<MerchantInfo> exportListMerchant(Criteria criteria);
	
	/**
	 * 导出商户信息
	 * @param criteria
	 * @return
	 */
	public List<MerchantInfo> exportMerchant(Criteria criteria);
	
	/**
	 * 获取商户终端列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<MerchantTerInfo> getMerchantTerList(Criteria criteria);
	
	/**
	 * 导出商户终端列表
	 * @param criteria
	 * @return
	 */
	public List<MerchantTerInfo> exportMerchantTerList(Criteria criteria);
	
	
	/**
	 * 根据id查询终端信息
	 * @param id
	 * @return
	 */
	public MerchantTerInfo queryMerchantTerNoById(int id);
	
	/**
	 * 根据id查询商户信息
	 * @param id
	 * @return
	 */
	public MerchantInfo queryMerchantInfoById(int id);
	
	
	/**
	 * 根据商户号查询商户信息
	 * @param id
	 * @return
	 */
	public MerchantInfo queryMerchantInfoByMerNo(String merNo);
	
	

	/**
	 * 修改商户信息
	 * @param merchantInfo
	 * @return
	 */
	public int updateMerchantInfo(MerchantInfo merchantInfo)throws ServiceException;
	
	
	/**
	 * 查询商户历史记录
	 * @param criteria
	 * @return
	 */
	public List<MerchantInfo> queryMerchantLogList(String merNo);
	
	
	/**
	 * 添加商户终端信息
	 * @param terInfo
	 * @return
	 */
	public int addTerInfo(MerchantTerInfo terInfo);
	
	/**
	 * 添加商户终端信息
	 * @param terInfo
	 * @return
	 */
	public int addTerInfoHis(MerchantTerInfo terInfo);
	
	/**
	 * 根据ID查询终端信息
	 * @param id
	 * @return
	 */
	public MerchantTerInfo queryTerInfoById(int id);
	
	/**
	 * 根据商户号和终端号查询终端嘻嘻
	 * @param merNo
	 * @param terNo
	 * @return
	 */
	public MerchantTerInfo queryTerInfoByMerNoAndTerNo(String merNo, String terNo);
	
	/** 获取商户最大终端号值 */
	public String queryMaxTerNo(String merNo);
	/**
	 * 查询终端历史修改记录
	 * @param merNo
	 * @param terNo
	 * @return
	 */
	public List<MerchantTerInfo> queryTerInfoByMerNoAndTerNoLog(String merNo, String terNo);
	
	
	
	/**
	 * 更新终端信息
	 * @param terInfo
	 * @return
	 */
	public int updateMerchantTerInfo(MerchantTerInfo terInfo) throws ServiceException;

	
	/**
	 * 添加配置信息
	 * @param config
	 * @return
	 */
	public int addMerchantConfig(MerchantConfig config);
	
	
	/**
	 * 查询商户配置信息
	 * @param config
	 * @return
	 */
	public List<MerchantConfig> queryMerchantConfigInfo(MerchantConfig config);
	
	/**
	 * 分页查询商户绑定通道列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<MerchantRelCurrencyInfo> getMerchnatRelCurrencyList(Criteria criteria);
	
	/**
	 * 添加商户绑定通道记录
	 * @param info
	 * @return
	 */
	public int  addCurrencyToMerchnat(MerchantRelCurrencyInfo  info) throws ServiceException;
	
	/**
	 * 添加商户绑定通道记录
	 * @param info
	 * @return
	 */
	public int  updateCurrencyToMerchnat(MerchantRelCurrencyInfo  info) throws ServiceException;
	
	/**
	 * 根据id查询商户绑定通道信息
	 * @param id
	 * @return
	 */
	public MerchantRelCurrencyInfo queryMerchantRelCurrencyById(int id);
	
	/**
	 * 查询商户通道绑定配置
	 * @param id
	 * @return
	 */
	public List<MerchantRelCurrencyInfo> getCurrencyToMerchnatHisList(int id) throws ServiceException ;
	
	/**
	 * 批量更新商户通道修改信息
	 * @param info
	 * @return
	 * @throws ServiceException
	 */
	public int batchUpdateCurrencyToMerchant(BatchUpdateCurrencyRelMerchantInfo info) throws ServiceException;
	
	/**
	 * 分页查询代理商列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<AgentInfo> getAgentList(Criteria criteria);
	
	
	/**
	 * 添加代理商
	 * @return
	 */
	public int addAgentInfo(AgentInfo agentInfo);
	
	/**
	 * 修改代理商
	 * @param agentInfo
	 * @return
	 */
	public int updateAgentInfo(AgentInfo agentInfo)throws ServiceException;
	
	
	
	/**
	 * 根据id查询代理商号
	 * @param id
	 * @return
	 */
	public AgentInfo queryAgentById(int id);
	
	/**
	 * 查询历史记录
	 * @param agentNo
	 * @return
	 */
	public List<AgentInfo> queryAgentLog(String agentNo);
	
	

	/**
	 * 修改配置信息
	 * @param config
	 * @return
	 */
	public int updateMerchantConfig(MerchantConfig config);
	
	/**
	 * 删除配置信息
	 * @param id
	 * @return
	 */
	public int deleteMerchantConfig(int id);

	
	/**
	 * 根据id查询配置信息
	 * @param id
	 * @return
	 */
	public MerchantConfig queryMerchantConfigById(int id);
	
	
	/**
	 * 商户绑定代理商
	 * @param merNo
	 * @param agentNo
	 * @param createBy
	 * @return
	 */
	public int addMerchantRelAgent(String merNo,String terNo,String createBy);
	
	/**
	 * 查询商户绑定代理商
	 * @param merNo
	 * @return
	 */
	public GwAgentRelMerchant queryAgentRelMerchant(String merNo,String terNo);

	
	/**
	 * 商户添加绑定关联用户
	 * @param info
	 * @return
	 */
	public int addUserRefMerchant(GwUserRelMerchantInfo info);
	
	
	/**
	 * 查询是否绑定重复用户
	 * @param merNo
	 * @return
	 */
	public GwUserRelMerchantInfo queryUserRelMerchant(String merNo);
	
	/**
	 * 审核商户终端
	 * @param terInfo
	 * @return
	 */
	public int updateChecnkTerNo(MerchantTerInfo terInfo);
	
	/**
	 * 查询终端下发列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<TerSnRelAgentInfo> queryTerSNRelAgentInfoList(Criteria criteria);
	
	public List<TerSnRelAgentInfo> exportTerSNRelAgentInfoList(Criteria criteria);
	
	
	
	
	/**
	 * 终端下发
	 * @param agentInfo
	 * @return
	 */
	public int addTerSnRelAgent(TerSnRelAgentInfo agentInfo);
	
	/**
	 * 终端配额查询
	 * @param criteria
	 * @param rd
	 * @return
	 */
	public PageInfo<GwTernoLmitInfo> terLimitInfoList(Criteria criteria);
	public List<GwTernoLmitInfo> terLimitInfoListInfo(Criteria criteria);
	public GwTernoLmitInfo queryTerLimitRateInfo(GwTernoLmitInfo info);
	
	/**
	 * 添加配额信息
	 * @param lmitInfo
	 * @return
	 */
	public int addTerLimit(GwTernoLmitInfo lmitInfo);
	/** 添加日志 */
	public int addTerLimitLog(GwTernoLmitInfo lmitInfo);
	
	public List<GwTernoLmitInfo> queryTerLimitLog(String id);

	/**
	 * 修改终端配额信息
	 * @param lmitInfo
	 * @return
	 */
	public int updateTerNoLimit(GwTernoLmitInfo lmitInfo);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public GwTernoLmitInfo queryTerNoLimitById(int id);
	
	
	/**
	 * 机具回收
	 * @param ids
	 * @return
	 */
	public int updateTerSnRelRecycle(TerSnRelAgentInfo agentInfo);
	
	/**
	 * ajax加载图片
	 * @param phoneNo
	 * @param picType
	 * @return
	 */
	public MerchantInfo queryMerchantInfo(String phoneNo,String picType);
	
	/**
	 * 验证当前代理商是否存在此代理
	 * @param agentNo
	 * @param parentAgentNo
	 * @return
	 */
	public AgentInfo queryAgentByAgentNo(String agentNo);
	
	
	/**
	 * 商户复核
	 * @param merchantInfo
	 * @return
	 */
	public int updateMerchantInfoReState(MerchantInfo merchantInfo);
	/**
	 * 商户结算周期列表
	 * 
	 * */
	public PageInfo<MerchantSettleCycle> getMerchantSettleCycleList(Criteria criteria);
	/**
	 * 添加商户结算周期
	 * */
	public int addMerchantSettleCycle(MerchantSettleCycle msc);
	/**
	 * 通过ID查询商户结算周期
	 * */
	public MerchantSettleCycle queryMerchantSettleCycleByID(String id);
	/**
	 * 修改商户结算周期
	 * */
	public int updateMerchantSettleCycle(MerchantSettleCycle msc);
	/**
	 * 商户网址绑定列表
	 * */
	public PageInfo<MerchantWebsite> getListMerchantWebsite(Criteria criteria);
	
	/**
	 * 添加商户网址
	 * */
	public int addMerchantWebsite(MerchantWebsite merchantWebsite) ;
	/**
	 * 通过ID查询商户网址
	 * */
	public MerchantWebsite queryMerchantWebsiteById(String id);
	/**
	 * 通过ID审核商户网址
	 * */
	public int updateMerchantWebsite(MerchantWebsite merchantWebsite);
	
	/** 查询商户同一终端下是否重复添加同一网站 */
	public int queryMerchantWebsite(MerchantWebsite merchantWebsite);
	/**
	 * 通过商户号终端号查询商户信息 
	 * */
	public int queryMerchantInfoByMerNo(String merNo, String terNo);
	
	/** 更新终端审核状态 
	 * @throws APIException 
	 * @throws ServiceException */
	public int updateMerchantTerNoInfo(MerchantTerInfo terInfo) throws APIException;

	/** 查询商户号终端号 添加终端限额条数 */
	int queryTerLimit(GwTernoLmitInfo lmitInfo);

	/** 修改商户网站信息 */
	public int updateMerchantWebsiteInfo(MerchantWebsite merchantWebsite);

	/** 批量删除 */
	public int deleteWebsite(String[] ids);

	/** 保存操作日志 */
	int insertOperationLog(MerchantWebsite merchantWebsite, String cperationBy,String type);

	/** 查询操作日志数据 */
	public PageInfo<MerchantWebsite> queryWebsiteLogList(Criteria criteria);
/**
	 * 查询商户的交易设置信息
	 * @param merNo
	 * @param terNo
	 * @return
	 */
	public List<TransSettingInfo> queryTransSettingInfo(String merNo);
	
	/**
	 * 查询注册码列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<RegCodeInfo> queryRegCodeList(Criteria criteria);
	/**
	 * 添加推荐码
	 * @param info
	 * @return
	 */
	public int excuAddRegCode(RegCodeInfo info);
	/**
	 * 实现：终端号支付页面绑定
	 * @param criteria 查询条件
	 * @return 终端号绑定的支付页面列表
	 * @date 2015-09-12
	 * @author YWP
	 */
	public PageInfo<GwMerchantPaymentPage> searchMerchantPaymentPage(Criteria criteria);
	/**
	 * 实现：根据ID查询终端号绑定的支付页面
	 * @param vo 查询条件
	 * @return 终端号绑定的支付页面
	 * @date 2015-09-12
	 * @author YWP
	 */
	public GwMerchantPaymentPage searchMerchantPaymentPageById(GwMerchantPaymentPage vo);
	/**
	 * 实现：保存终端号绑定的支付页面
	 * @param vo 绑定支付页面信息
	 * @param user 当前登录用户
	 * @return 保存结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	public int saveMerchantPaymentPage(GwMerchantPaymentPage vo, UserInfo user);
	/**
	 * 实现：删除终端号绑定的支付页面
	 * @param vo 绑定支付页面信息
	 * @return 删除结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	public int deleteMerchantPaymentPage(GwMerchantPaymentPage vo);
	
	/**
	 * 
	 *查询Mcc
	 */
	public PageInfo<MccInfo> getMccInfo(Criteria criteria);
	/**
	 *查询品牌 
	 */
	public PageInfo<BrandProductInfo> getBrandInfo(Criteria criteria);
	
	/**查询产品*/
	public PageInfo<BrandProductInfo> getProductInfo(Criteria criteria);
	
	public String querySourceCurrencyCode();
	
	public int deleteRegCodeInfo(String[] ids);
	/**
	 * 导出商户绑定通道
	 * @param criteria
	 * @return
	 */
	public List<MerchantRelCurrencyInfo> getMerchnatRelCurrencyForExport(
			Criteria criteria);
	
	/**
	 *代理商账户管理
	 * 
	 */
	public AgentUserInfo queryAgentUserInfo(AgentUserInfo user);
	/**
	 *保存修改代理商账户管理
	 * 
	 */
	public int updateAgentUserInfo(AgentUserInfo user);
	/**
	 *根据商户的编号获取商户的终端号信息
	 * 
	 */
	public List<MerchantTerInfo> getAgentMerchantTerList(MerchantTerInfo mer);
	/**
	 *保存代理商分配的用户信息
	 * 
	 */
	public int saveAgentMerchantInfo(GWAgentMerchantInfo info);
	/**
	 *查找现有的代理商户
	 * 
	 */
	public List<GWAgentMerchantInfo> queryAgentMerchantInfo(GWAgentMerchantInfo mer);
	/**
	 * 查看所有账户
	 * 
	 */
	public PageInfo<AgentUserInfo> getListAgentUser(Criteria criteria);
	/**
	 * 代理商账户管理(删)
	 * 
	 */
	public int deleteAgentUser(AgentUserInfo user);
	/**
	 * 保存新增代理商账户管理
	 * 
	 */
	public int saveAgentUserInfo(AgentUserInfo user);
	/**
	 * 查询商户的终端信息
	 * 
	 */
	public PageInfo<MerchantTerInfo> getAgentMerchantTerInfoList(Criteria criteria);
	/**
	 * 根据账户和密码查找代理商用户
	 * 
	 */
	public AgentUserInfo queryAgentUserInfoByName(AgentUserInfo user);
	/**
	 * 根据代理商编号，商户编号，端口号查询
	 * 
	 */
	public GWAgentMerchantInfo queryAgentMerchantInfoByName(GWAgentMerchantInfo info);
	/**
	 * 根据代理商编号，商户编号，查找端口是否存在
	 * 
	 */
	public MerchantTerInfo queryAgentMerchantTerByName(MerchantTerInfo info);
	
	/**
	 * 商户网址绑定列表
	*/
	public PageInfo<MerchantWebsite> querySysMerchantWebsiteList(Criteria criteria);
	
	/**
	 * 查找网址信息
	 */
	public MerchantWebsite querySysMerchantWebsitInfoById(String id);
	
	/**
	 * 导出网址信息
	 */
	public List<MerchantWebsite> queryExportSysMerchantWebsiteInfo(Criteria criteria);
	
	/**
	 * 查询商户拒付扣款限制信息
	 */
	public PageInfo<MerchantDisFineInfo> queryMerchantDisFineList(Criteria criteria);
	
	/**
	 * 保存商户拒付扣款限制信息
	 */
	public int saveMerchantDisFineList(MerchantDisFineInfo info) throws APIException;
	
	/**
	 * 查询商户拒付扣款信息信息
	 */
	public MerchantDisFineInfo queryMerchantDisFineInfo(String merNo, String terNo);
	
	/**
	 * 删除商户拒付扣款信息
	 */
	public int deleteMerchantDisFineInfo(MerchantDisFineInfo info);

	public int deleteTerLimitCountInfoById(String id);
	/**
	 * 通过商户绑定通道ID查询商户自动切换通道条件
	 * @param id
	 * @return
	 */
	public MerchantCurrencySpecialInfo queryMerchantCurrencySpecialInfoByMerchantCurrencyId(
			String merchantCurrencyId);

	public int updateMerchantCurrencySpecialInfo(
			MerchantCurrencySpecialInfo info);

	public int addMerchantCurrencySpecialInfo(MerchantCurrencySpecialInfo info);
	
	
	/**
	 * 查询商户国家通道数据列表
	 */
	public PageInfo<CountryCurrencyInfo> queryCountryCurrencyInfoList(Criteria criteria);
	
	/**
	 * 	查询商户国家通道信息
	 */
	public CountryCurrencyInfo queryCountryCurrencyInfoById(String id);
	
	/**
	 * 保存添加商户国家通道信息
	 */
	public int saveCountryCurrencyInfo(CountryCurrencyInfo info) throws APIException;
	
	/**
	 * 保存修改商户国建通道信息
	 */
	public int updateCountryCurrencyInfoById(CountryCurrencyInfo info) throws APIException;
	
	/**
	 * 删除商户国家通道信息
	 */
	public int deleteCountryCurrencyInfoById(String id, UserInfo user) throws APIException;
	
	/**
	 * 查询商户国家通道数据LOG列表
	 */
	public PageInfo<CountryCurrencyLogInfo> queryCountryCurrencyLogInfoList(Criteria criteria);
	
	/**
	 * 获取商户号信息
	 */
	public PageInfo<MerchantTerInfo> queryMerchantMerNoInfoList(Criteria criteria);
	
	/**
	 * 获取终端号信息
	 */
	public PageInfo<MerchantTerInfo> queryMerchantTerNoInfoList(Criteria criteria);
	
	/**
	 * 查询商户类型信息
	 */
	public PageInfo<MerchantTypeInfo> queryMerchantTypeInfoList(Criteria criteria);

	/**
	 * 更新商户网站信息
	 */
	public int updateMerchantWebsiteByMerNoAndTerNoAndWebSite(
			MerchantWebsite info);

	/**
	 * 修改备用终端
	 * @param merchantTerInfo
	 * @return
	 */
	public int updateSpareTerNo(MerchantTerInfo merchantTerInfo);
	/**
	 * 查询商户终端shopify状态
	 * @param id
	 * @return
	 */
	public MerchantTerInfo getShopifyById(String id);
	/**
	 * 修改商户终端shopify状态
	 * @param shopifyOnOff
	 * @param id
	 * @return
	 */
	public int updateShopifyById(String shopifyOnOff,String id);
	
}
