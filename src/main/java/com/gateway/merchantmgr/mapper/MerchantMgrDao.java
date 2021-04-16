package com.gateway.merchantmgr.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
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
import com.gateway.merchantmgr.model.MerchantDisFineRuleInfo;
import com.gateway.merchantmgr.model.MerchantInfo;
import com.gateway.merchantmgr.model.MerchantRelCurrencyInfo;
import com.gateway.merchantmgr.model.MerchantSettleCycle;
import com.gateway.merchantmgr.model.MerchantTerInfo;
import com.gateway.merchantmgr.model.MerchantTypeInfo;
import com.gateway.merchantmgr.model.MerchantWebsite;
import com.gateway.merchantmgr.model.RegCodeInfo;
import com.gateway.merchantmgr.model.TerSnRelAgentInfo;
import com.gateway.merchantmgr.model.TransSettingInfo;

public interface MerchantMgrDao {
	/**
	 * 查询商户列表
	 * @param criteria
	 * @param rd
	 * @return
	 */
	public List<MerchantInfo> getListMerchant(Criteria criteria ,RowBounds rd); 
	
	public List<MerchantInfo> getListMerchant(Criteria criteria); 
	
	/**
	 * 统计商户记录数
	 * @param criteria
	 * @return
	 */
	public int countMerchant(Criteria criteria );
	
	/**
	 * 查询商户终端列表
	 * @param criteria
	 * @param rd
	 * @return
	 */
	public List<MerchantTerInfo> getMerchantTerList(Criteria criteria,RowBounds rd); 
	

	public List<MerchantTerInfo> getMerchantTerList(Criteria criteria); 
	
	/**
	 * 统计商户终端记录条数
	 * @param criteria
	 * @return
	 */
	public int countMerchantTerList(Criteria criteria);
	
	/**
	 * 根据id查询商户信息
	 * @param id
	 * @return
	 */
	public MerchantInfo queryMerchantInfoById(@Param("id") int id);
	
	/**
	 * 修改商户信息
	 * @param merchantInfo
	 * @return
	 */
	public int updateMerchantInfo(@Param("info")MerchantInfo merchantInfo);
	
	/**
	 * 添加商户历史信息
	 * @param merchantInfo
	 * @return
	 */
	public int addMerchantInfoLog(@Param("info")MerchantInfo merchantInfo);
	
	/**
	 * 查询历史记录
	 * @param criteria
	 * @param rd
	 * @return
	 */
	public List<MerchantInfo> queryMerchantLogList(String  merNo);
	
	
	/**
	 * 添加商户终端信息
	 * @param terInfo
	 * @return
	 */
	public int addTerInfo(@Param("terInfo") MerchantTerInfo terInfo);
	
	/**
	 * 根据id查询终端信息
	 * @param id
	 * @return
	 */
	public MerchantTerInfo queryMerchantTerNoById(@Param("id")int id);
	
	/**
	 * 添加商户终端信息
	 * @param terInfo
	 * @return
	 */
	public int addTerInfoHis(@Param("terInfo") MerchantTerInfo terInfo);
	
	/**
	 * 根据商户号查询商户信息
	 * @param id
	 * @return
	 */
	public MerchantInfo queryMerchantInfoByMerNo(@Param("merNo") String merNo);
	
	
	/**
	 * 根据ID查询终端信息
	 * @param id
	 * @return
	 */
	public MerchantTerInfo queryTerInfoById(@Param("id") int id);
	
	/**
	 * 更新终端信息
	 * @param terInfo
	 * @return
	 */
	public int updateMerchantTerInfo(@Param("terInfo") MerchantTerInfo terInfo);
	
	/** 更新终端审核状态 */
	public int updateMerchantTerNoInfo(@Param("terInfo") MerchantTerInfo terInfo);
	
	/**
	 * 根据商户号和终端号查询终端信息
	 * @param merNo
	 * @param terNo
	 * @return
	 */
	public MerchantTerInfo queryTerInfoByMerNoAndTerNo(@Param("merNo")String merNo,@Param("terNo") String terNo);
	
	
	/**
	 * 查询终端历史修改记录
	 * @param merNo
	 * @param terNo
	 * @return
	 */
	public List<MerchantTerInfo> queryTerInfoByMerNoAndTerNoLog(@Param("merNo")String merNo,@Param("terNo") String terNo);
	
	/**
	 * 添加配置信息
	 * @param config
	 * @return
	 */
	public int addMerchantConfig(@Param("config")MerchantConfig config);
	
	/**
	 * 查询商户配置信息
	 * @param config
	 * @return
	 */
	public List<MerchantConfig> queryMerchantConfigInfo(@Param("config")MerchantConfig config);
	
	/**
	 * 查询商户通道绑定列表
	 * @param criteria
	 * @param rd
	 * @return
	 */
	public List<MerchantRelCurrencyInfo> getMerchnatRelCurrencyList(Criteria criteria,RowBounds rd);
	public List<MerchantRelCurrencyInfo> getMerchnatRelCurrencyList(Criteria criteria);
	
	/**
	 * 统计商户绑定通道记录数
	 * @param criteria
	 * @return
	 */
	public int countMerchnatRelCurrencyList(Criteria criteria);
	
	/**
	 * 添加商户绑定通道记录
	 * @param info
	 * @return
	 */
	public int  addCurrencyToMerchnat(@Param("info") MerchantRelCurrencyInfo  info);
	
	/**
	 * 修改商户绑定通道信息
	 * @param info
	 * @return
	 */
	public int  updateCurrencyToMerchnat(@Param("info") MerchantRelCurrencyInfo  info);
	
	/**
	 * 插入通道绑定信息历史记录
	 * @param info
	 * @return
	 */
	public int addCurrencyToMerchantLog(@Param("info") MerchantRelCurrencyInfo  info);
	
	/**
	 * 根据id查询商户绑定通道信息
	 * @param id
	 * @return
	 */
	public MerchantRelCurrencyInfo queryMerchantRelCurrencyById(@Param("id") int id);
	
	/**
	 * 查询商户绑定记录
	 * @param id
	 * @return
	 */
	public List<MerchantRelCurrencyInfo> getCurrencyToMerchnatHisList(@Param("merNo") String merNo,@Param("terNo") String terNo ,@Param("cardType")String cardType);
	
	/**
	 * 批量更新商户通道绑定信息
	 * @param info
	 * @return
	 */
	public int batchUpdateCurrencyToMerchant(@Param("info") BatchUpdateCurrencyRelMerchantInfo info);
	/**
	 * 批量写入商户通道绑定历史记录
	 * @param info
	 * @return
	 */
	public int batchAddCurrencyToMerchnatLog(@Param("info") BatchUpdateCurrencyRelMerchantInfo info);
	
	/**
	 * 查询代理商列表
	 * @param criteria
	 * @return
	 */
	public List<AgentInfo> getAgentList(Criteria criteria,RowBounds rd);
	
	/**
	 * 统计代理商记录数
	 * @param criteria
	 * @return
	 */
	public int countAgentList(Criteria criteria);
	
	/**
	 * 添加代理商
	 * @return
	 */
	public int addAgentInfo(@Param("info")AgentInfo agentInfo);
	
	/**
	 * 代理商历史记录表
	 * @param agentInfo
	 * @return
	 */
	public int addAgentLogInfo(@Param("info")AgentInfo agentInfo);
	
	
	/**
	 * 修改代理商
	 * @param agentInfo
	 * @return
	 */
	public int updateAgentInfo(@Param("info")AgentInfo agentInfo);
	
	/**
	 * 根据id查询代理商号
	 * @param id
	 * @return
	 */
	public AgentInfo queryAgentById(@Param("id")int id);
	
	
	/**
	 * 根据id查询代理商号
	 * @param id
	 * @return
	 */
	public AgentInfo queryAgentByAgentNo(@Param("agentNo") String agentNo);
	
	/**
	 * 查询历史记录
	 * @param agentNo
	 * @return
	 */
	public List<AgentInfo> queryAgentLog(@Param("agentNo")String agentNo);
	
	/**
	 * 修改配置信息
	 * @param config
	 * @return
	 */
	public int updateMerchantConfig(@Param("config")MerchantConfig config);
	
	/**
	 * 删除配置信息
	 * @param id
	 * @return
	 */
	public int deleteMerchantConfig(@Param("id")int id);
	
	/**
	 * 根据id查询配置信息
	 * @param id
	 * @return
	 */
	public MerchantConfig queryMerchantConfigById(@Param("id")int id);
	
	/**
	 * 商户绑定代理商
	 * @param merNo
	 * @param agentNo
	 * @param createBy
	 * @return
	 */
	public int addMerchantRelAgent(@Param("merNo")String merNo,@Param("terNo")String terNo,@Param("createBy")String createBy);
	
	
	/**
	 * 根据商户号终端号查询商户绑定代理商
	 * @param merNo
	 * @return
	 */
	public GwAgentRelMerchant queryAgentRelMerchant(@Param("merNo")String merNo,@Param("terNo")String terNo);
	
	
	/**
	 * 根据商户号查询商户绑定代理商
	 * @param merNo
	 * @return
	 */
	public GwAgentRelMerchant queryAgentRelMerchantByMerNo(@Param("merNo")String merNo);
	
	
	
	
	/**
	 * 商户添加绑定关联用户
	 * @param info
	 * @return
	 */
	public int addUserRefMerchant(@Param("info")GwUserRelMerchantInfo info);
	
	/**
	 * 查询是否绑定重复用户
	 * @param merNo
	 * @return
	 */
	public GwUserRelMerchantInfo queryUserRelMerchant(@Param("merNo")String merNo);
	
	/**
	 * 审核商户终端
	 * @param terInfo
	 * @return
	 */
	public int updateChecnkTerNo(@Param("terInfo")MerchantTerInfo terInfo);
	/**
	 * 查询终端下发关联的代理商列表 
	 * @param criteria
	 * @param rd
	 * @return
	 */
	public List<TerSnRelAgentInfo> queryTerSNRelAgentInfoList(Criteria criteria,RowBounds rd);
	
	public List<TerSnRelAgentInfo> queryTerSNRelAgentInfoList(Criteria criteria);
	
	/**
	 * 查询已经绑定过的SN号
	 * @param snNo
	 * @return
	 */
	public List<TerSnRelAgentInfo> queryTersnRelAgentList(@Param("snNo") String [] snNo);
	
	
	/**
	 * 统计终端下发的记录数
	 * @param criteria
	 * @return
	 */
	public int countTerSNRelAgentInfoList(Criteria criteria);
	
	/**
	 * 终端下发
	 * @param agentInfo
	 * @return
	 */
	public int addTerSnRelAgent(@Param("ter")TerSnRelAgentInfo agentInfo);
	
	/**
	 * 更新终端sn状态
	 * @param tersn
	 * @return
	 */
	public int updateKeyMaste(@Param("tersn")String tersn,@Param("status")String status);
	
	
	/**
	 * 终端配额查询
	 * @param criteria
	 * @param rd
	 * @return
	 */
	public List<GwTernoLmitInfo> terLimitInfoList(Criteria criteria,RowBounds rd);
	
	public int countterLimitInfoList(Criteria criteria);
	
	public GwTernoLmitInfo queryTerLimitRateInfo(@Param("info")GwTernoLmitInfo info);
	
	/**
	 * 添加配额信息
	 * @param lmitInfo
	 * @return
	 */
	public int addTerLimit(@Param("limit")GwTernoLmitInfo lmitInfo);
	/** 添加日志 */
	public int addTerLimitLog(@Param("limit")GwTernoLmitInfo lmitInfo);
	
	public List<GwTernoLmitInfo> queryTerLimitLog(String id);
	
	/** 通过商户号，终端号，查询配额信息数量 */
	public int queryTerLimit(@Param("limit")GwTernoLmitInfo lmitInfo);
	
	/**
	 * 修改终端配额信息
	 * @param lmitInfo
	 * @return
	 */
	public int updateTerNoLimit(@Param("limit")GwTernoLmitInfo lmitInfo);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public GwTernoLmitInfo queryTerNoLimitById(@Param("id")int id);
	
	
	/**
	 * 查询需回收机具
	 * @param ids
	 * @return
	 */
	public List<TerSnRelAgentInfo> queryTersnRelAgentById(@Param("ids")String[] ids);
	
	/**
	 * 机具回收
	 * @param ids
	 * @return
	 */
	public int deleteTerSnRelRecycle(@Param("ter")TerSnRelAgentInfo ter);
	
	/**
	 * 商户复核
	 * @param merchantInfo
	 * @return
	 */
	public int updateMerchantInfoReState(@Param("info")MerchantInfo merchantInfo);
	/**
	 * 统计商户结算周期总数
	 * */
	public int countMerchantSettleCycle(Criteria criteria);
	/**
	 * 获取商户结算周期配置
	 * */
	public List<MerchantSettleCycle> getListMerchantSettleCycle(Criteria criteria);
	
	public List<MerchantSettleCycle> getListMerchantSettleCycle(Criteria criteria, RowBounds rb);
	/**
	 * 添加商户结算周期
	 * */
	public int addMerchantSettleCycle(@Param("msc")MerchantSettleCycle msc);
	/**
	 * 通过商户结算周期
	 * */
	public MerchantSettleCycle queryMerchantSettleCycleByID(String id);
	/**
	 * 修改商户结算周期
	 * */
	public int updateMerchantSettleCycle(@Param("msc")MerchantSettleCycle msc);

	/**
	 * 商户绑定网址计数
	 * */
	public int countMerchantWebsite(Criteria criteria);
	/**
	 * 查询商户绑定网址列表
	 * */
	public List<MerchantWebsite> getListMerchantWebsite(Criteria criteria,RowBounds rb);
	
	public List<MerchantWebsite> getListMerchantWebsite(Criteria criteria);
	/**
	 * 添加商户网址
	 * */
	public int addMerchantWebsite(@Param("info")MerchantWebsite merchantWebsite);
	/**
	 * 审核商户网址
	 * */
	public int updateMerchantWebsite(@Param("info")MerchantWebsite merchantWebsite);
	public int updateMerchantWebsiteByMerNoAndTerNoAndWebSite(@Param("info")MerchantWebsite merchantWebsite);
	
	/**
	 * 通过ID查询商户网址
	 * */
	public MerchantWebsite queryMerchantWebsiteById(String id);
	/**
	 * 通过商户号和终端号查询商户信息
	 * */
	public int queryMerchantInfoByMerNoAndTerNo(@Param("merNo")String merNo, @Param("terNo")String terNo);

	/** 获取商户最大终端号值 */
	public String queryMaxTerNo(String merNo);

	/** 查询商户同一终端下是否重复添加同一网站  */
	public int queryMerchantWebsite(MerchantWebsite merchantWebsite);

	/** 修改商户网站信息  */
	public int updateMerchantWebsiteInfo(@Param("info")MerchantWebsite merchantWebsite);

	int deleteWebsite(@Param("id")String id);

	/** 保存操作日志 */
	public int insertOperationLog(@Param("info")MerchantWebsite merchantWebsite);

	/** 统计条数 */
	public int countWebsiteLogList(Criteria criteria);

	/** 查询日志列表 */
	public List<MerchantWebsite> queryWebsiteLogList(Criteria criteria,RowBounds rb);
	
	/**
	 * 查询商户的交易设置信息
	 * @param merNo
	 * @param terNo
	 * @return
	 */
	public List<TransSettingInfo> queryTransSettingInfo(@Param("merNo") String merNo);
	
		
	/**
	 * 查询注册码列表
	 * @param criteria
	 * @return
	 */
	public List<RegCodeInfo> queryRegCodeList(Criteria criteria,RowBounds rb);
	
		/**
	 * 查询注册码列表
	 * @param criteria
	 * @return
	 */
	public int countRegCodeList(Criteria criteria);
	
	/**
	 * 添加推荐码
	 * @param info
	 * @return
	 */
	public int excuAddRegCode(@Param("info") RegCodeInfo info);
	
	/**
	 * 实现：终端号支付页面绑定
	 * @param vo 查询条件
	 * @return 终端号绑定的支付页面列表
	 * @date 2015-09-12
	 * @author YWP
	 */
	public List<GwMerchantPaymentPage> searchMerchantPaymentPage(Criteria criteria ,RowBounds rd);
	public int countMerchantPaymentPage(Criteria vo);
	/**
	 * 实现：根据ID查询终端号绑定的支付页面
	 * @param vo 查询条件
	 * @return 终端号绑定的支付页面
	 * @date 2015-09-12
	 * @author YWP
	 */
	public GwMerchantPaymentPage searchMerchantPaymentPageById(@Param("vo")GwMerchantPaymentPage vo);
	/**
	 * 实现：保存新增终端号绑定的支付页面
	 * @param vo 绑定支付页面信息
	 * @return 保存结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	public int addMerchantPaymentPage(@Param("vo")GwMerchantPaymentPage vo);
	/**
	 * 实现：保存修改终端号绑定的支付页面
	 * @param vo 绑定支付页面信息
	 * @return 保存结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	public int updateMerchantPaymentPage(@Param("vo")GwMerchantPaymentPage vo);
	/**
	 * 实现：删除终端号绑定的支付页面
	 * @param vo 绑定支付页面信息
	 * @return 删除结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	public int deleteMerchantPaymentPage(@Param("vo")GwMerchantPaymentPage vo);

	/** 终端配额查询 */
	public List<GwTernoLmitInfo> terLimitInfoList(Criteria criteria);
	
	/**
	 * 
	 * mcc查询
	 */
	public List<MccInfo> getMccInfo(Criteria criteria,RowBounds rd);
	
	public List<BrandProductInfo> getProductInfo(Criteria criteria,RowBounds rd);
	
	public List<BrandProductInfo> getBrandInfo(Criteria criteria,RowBounds rd);
	
	/**
	 * 
	 *mcc条数统计 
	 */
	public int countMccInfo(Criteria criteria);
	
	public int countProductInfo(Criteria criteria);
	
	public int countBrandInfo(Criteria criteria);
	
	/** 判断商户终端下卡种是否重复添加通道 */
	public int checkmerNoCurrencyInfo(MerchantRelCurrencyInfo info);
	
	/** 获取源币种 */
	public List<MerchantTerInfo> querySourceCurrencyCode();
	
	/** 删除推荐码 */
	public int deleteRegCodeInfo(String id);
	/**
	 *代理商账户管理
	 * 
	 */
	public AgentUserInfo searchAgentUserInfo(@Param("vo") AgentUserInfo vo);
	/**
	 *保存修改代理商账户管理
	 * 
	 */
	public int updateAgentUserInfo(@Param("vo") AgentUserInfo vo);
	/**
	 *更具商户的编号获取商户的终端号信息
	 * 
	 */
	public List<MerchantTerInfo> getAgentMerchantTerList(@Param("vo") MerchantTerInfo vo);
	/**
	 *保存代理商分配的用户信息
	 * 
	 */
	public int saveAgentMerchantInfo(@Param("vo") GWAgentMerchantInfo vo);
	/**
	 *查找现有的代理商户
	 * 
	 */
	public List<GWAgentMerchantInfo> queryAgentMerchantInfo(@Param("list") GWAgentMerchantInfo vo);
	/**
	 * 查看所有账户
	 * 
	 */
	public List<AgentUserInfo> queryAgentUserInfoList(Criteria criteria ,RowBounds rd);
	/**
	 * 代理商账户管理(删)
	 * 
	 */
	public int deleteAgentUserInfo(@Param("vo") AgentUserInfo vo);
	/**
	 * 保存新增代理商账户管理
	 * 
	 */
	public int saveAgentUserInfo(@Param("vo") AgentUserInfo user);
	/**
	 * 查询商户的终端信息
	 * 
	 */
	public List<MerchantTerInfo> queryAgentMerchantTerInfoList(Criteria criteria ,RowBounds rd);
	/**
	 * 查询商户的终端总数
	 * 
	 */
	public Integer queryAgentMerchantTerInfoCount(Criteria criteria);
	/**
	 * 根据账户和密码查找代理商用户
	 * 
	 */
	public AgentUserInfo queryAgentUserInfoByName(@Param("vo") AgentUserInfo vo);
	/**
	 * 根据代理商编号，商户编号，端口号查询
	 * 
	 */
	public GWAgentMerchantInfo queryAgentMerchantInfoByName(@Param("vo") GWAgentMerchantInfo vo);
	/**
	 * 根据代理商编号，商户编号，查找端口是否存在
	 * 
	 */
	public MerchantTerInfo queryAgentMerchantTerByName(@Param("vo") MerchantTerInfo vo);
	/**
	 * 通过查询内容更新查询次数
	 * @param criteria
	 */
	public void updateBrandProductSearchCountBySearchContants(Criteria criteria);
	/**
	 * 通过查询内容更新查询次数
	 * @param criteria
	 */
	public void updateBrandSearchCountBySearchContants(Criteria criteria);
	/**
	 * 通过查询内容更新查询次数
	 * @param criteria
	 */
	public void updateMccSearchCountBySearchContants(Criteria criteria);
	
	/**
	 * 查找网址总数
	 */
	public int querySysMerchantWebsiteCount(Criteria criteria);
	
	/**
	 * 查找网址信息
	 */
	public List<MerchantWebsite> querySysMerchantWebsiteList(Criteria criteria, RowBounds rb);
	
	/**
	 * 查找网址信息
	 */
	public MerchantWebsite querySysMerchantWebsitInfoById(@Param("id") String id);
	
	/**
	 * 导出网址信息
	 */
	public List<MerchantWebsite> queryExportSysMerchantWebsiteInfo(Criteria criteria);
	
	/**
	 * 查询商户拒付扣款规则信息
	 */
	public List<MerchantDisFineInfo> queryMerchantDisFineInfoList(@Param("rb") RowBounds rb, Criteria criteria);
	
	/**
	 * 查询商户拒付扣款规则总数
	 */
	public int queryMerchantDidFineInfoCount(Criteria criteria);
	
	/**
	 * 查询规则信息
	 */
	public List<MerchantDisFineRuleInfo> queryMerhcantDisFineRuleInfoList(@Param("merNo") String merNo, @Param("terNo") String terNo);
	
	/**
	 * 查询商户拒付扣款信息信息
	 */
	public MerchantDisFineInfo queryMerchantDisFineInfo(@Param("merNo") String merNo, @Param("terNo") String terNo);
	
	/**
	 * 保存商户拒付扣款信息
	 */
	public int saveMerchantDisFineInfo(@Param("vo") MerchantDisFineInfo vo);
	
	/**
	 * 删除修改信息
	 */
	public int deleteMerchantDisFineInfoByIds(@Param("list") List<String> list);
	
	/**
	 * 修改信息
	 */
	public int updateMerchantDisFineInfoById(@Param("id") String id, @Param("vo1") MerchantDisFineInfo vo1, @Param("vo2") MerchantDisFineRuleInfo vo2);
	
	/**
	 * 删除修改信息
	 */
	public int deleteMerchantDisFineInfo(@Param("merNo") String merNo, @Param("terNo") String terNo);

	public int updateTerLimitForMonthCountLimitByMerNo(@Param("monthCountLimit")int monthCountLimit,
			@Param("merNo")String merNo,@Param("cardType")String cardType);

	public int deleteTerLimitCountInfoById(String id);

	public MerchantCurrencySpecialInfo queryMerchantCurrencySpecialInfoByMerchantCurrencyId(
			String merchantCurrencyId);

	public int addMerchantCurrencySpecialInfo(MerchantCurrencySpecialInfo info);

	public int updateMerchantCurrencySpecialInfo(
			MerchantCurrencySpecialInfo info);
	
	/**
	 * 查询商户国家通道信息列表	
	 */
	public List<CountryCurrencyInfo> queryCountryCurrencyInfoList(RowBounds rb, Criteria criteria);
	
	/**
	 * 查询商户国家通道信息总数
	 */
	public int queryCountryCurrencyInfoCount(Criteria criteria);
	
	/**
	 * 查询商户国家通道信息
	 */
	public CountryCurrencyInfo queryCountryCurrencyInfoById(@Param("id") String id);
//	
	/**
	 * 保存商户国建通道信息
	 */
	public int saveCountryCurrencyInfo(@Param("vo") CountryCurrencyInfo vo);
	
	/**
	 * 修改商户国家通道信息
	 */
	public int updateCountryCurrencyInfoById(@Param("vo") CountryCurrencyInfo vo);
	
	/**
	 * 删除商户国建通道信息
	 */
	public int deleteCountryCurrencyInfoById(@Param("id") String id);
	
	/**
	 * 查询商户国家通道LOG信息列表
	 */
	public List<CountryCurrencyLogInfo> queryCountryCurrencyLogInfoList(RowBounds rb, Criteria criteria);
	
	/**
	 * 查询商户国家通道LOG信息总数
	 */
	public int queryCountryCurrencyLogInfoCount(Criteria criteria);
//	
	/**
	 * 保存商户国家通道LOG信息
	 */
	public int saveCountryCurrencyLogInfo(@Param("vo") CountryCurrencyLogInfo vo);
	
	/**
	 * 获取商户号信息
	 */
	public List<MerchantTerInfo> queryMerchantMerNoInfoList(RowBounds rb, Criteria criteria);
	
	/**
	 * 查询商户号总数
	 */
	public int queryMerchantMerNoInfoCount(Criteria criteria);
	
	/**
	 * 获取终端号信息
	 */
	public List<MerchantTerInfo> queryMerchantTerNoInfoList(RowBounds rb, Criteria criteria);
	
	/**	
	 * 查询终端号总素
	 */
	public int queryMerchantTerNoInfoCount(Criteria criteria);
	
	/**
	 * 查询商户类型信息
	 */
	public List<MerchantTypeInfo> queryMerchantTypeInfoList(RowBounds rb, Criteria criteria);
	
	/**
	 * 查询商户类型总数
	 */
	public int queryMerchantTypeInfoCount(Criteria criteria);
	
	/**
	 * 修改备用终端
	 * @param merchantTerInfo
	 * @return
	 */
	public int updateSpareTerNo(@Param("merchantTerInfo")MerchantTerInfo merchantTerInfo);
	
	/**
	 * 查询商户终端shopify状态
	 * @param id
	 * @return
	 */
	public MerchantTerInfo getShopifyById(@Param("id")String id);
	/**
	 * 修改商户终端shopify状态
	 * @param shopifyOnOff
	 * @param id
	 * @return
	 */
	public int updateShopifyById(@Param("shopifyOnOff")String shopifyOnOff,@Param("id")String id);
}
