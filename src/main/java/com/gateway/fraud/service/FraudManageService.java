package com.gateway.fraud.service;

import java.util.List;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.fraud.common.exception.FraudExcetion;
import com.gateway.fraud.model.MerchantRefRuleProfileInfo;
import com.gateway.fraud.model.AutoRiskInfo;
import com.gateway.fraud.model.BinInfo;
import com.gateway.fraud.model.BlackTextInfo;
import com.gateway.fraud.model.FraudInfo;
import com.gateway.fraud.model.ParamInfo;
import com.gateway.fraud.model.RiskElementInfo;
import com.gateway.fraud.model.RuleProcessClass;
import com.gateway.fraud.model.RuleProfileInfo;
import com.gateway.fraud.model.RulesInfo;
import com.gateway.fraud.model.RulesRefProFileInfo;

public interface FraudManageService {
	
	/**
	 * 查询记录数
	 * @param criteria
	 * @return
	 */
	public int countByCriteria(Criteria criteria);
	
	
	
	/**
	 * 添加规则参数信息
	 * @param info
	 * @return
	 */
	public int addParam(ParamInfo info);
	
	/**
	 * 更新参数信息
	 * @param info
	 * @return
	 */
	public int updateParamInfo(ParamInfo info);
	
	/**
	 * 删除参数信息
	 * @param paramId
	 * @return
	 */
	public int delParamByParamId(String paramId);
	
	/**
	 * 查询参数列表
	 * @param criteria
	 * @return
	 */
	public List<ParamInfo> queryParamInfoList(Criteria criteria);
	
	/**
	 * 查询规则处理类列表
	 * @param criteria
	 * @return
	 */
	public List<RuleProcessClass> queryProcessClassList(Criteria criteria);
	
	/**
	 * 根据参数ID查询参数信息
	 * @param paramId
	 * @return
	 */
	public ParamInfo queryParamInfoByParamId(String paramId);
	
	/**
	 * 分页查询参数列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<ParamInfo> queryPageParamInfoList(Criteria criteria);
	/**
	 * 查询记录笔数
	 * @param criteria
	 * @return
	 */
	public int countParamsByCriteria(Criteria criteria);
	
	/**
	 * 设置参数的值
	 * @param info
	 * @return
	 */
	public boolean setParamValue(ParamInfo oldinfo,ParamInfo newInfo) throws FraudExcetion ;
	
	/**
	 * 统计规则记录
	 * @param criteria
	 * @return
	 */
	public int countRuleInfoByCriteria(Criteria criteria);
	
	/**
	 * 查询规则列表
	 * @param criteria
	 * @return
	 */
	public List<RulesInfo> queryRulesList(Criteria criteria);
	
	/**
	 * 分页查询规则列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<RulesInfo> queryPageRulesList(Criteria criteria);
	
	/**
	 * 添加规则信息
	 * @param info
	 * @return
	 */
	public int addRuleInfo(RulesInfo info);
	
	/**
	 * 更新规则信息
	 * @param info
	 * @return
	 */
	public int updateRuleInfo(RulesInfo info);
	
	/**
	 * 查询规则的详细信息
	 * @param ruleId
	 * @return
	 */
	public RulesInfo queryRulesInfoDetailByRuleId(String ruleId);
	
	/**
	 * 查询风险预警元素列表
	 * @param criteria
	 * @return
	 */
	public List<RiskElementInfo> queryRiskElementInfo(Criteria criteria);
	
	/**
	 * 分页查询风险预警元素
	 * @param criteria
	 * @return
	 */
	public PageInfo<RiskElementInfo> queryPageRiskElementInfo(Criteria criteria);
	
	/**
	 * 分页统计条数
	 * @param criteria
	 * @return
	 */
	public int queryRiskRiskElementCount(Criteria criteria);
	
	/**
	 * 添加风险预警元素
	 * @param info
	 * @return
	 */
	public int addRiskElementInfo(RiskElementInfo info) throws FraudExcetion ;
	
	/**
	 * 批量添加风险元素
	 * @param list
	 * @return
	 */
	public int batchAddRiskElement(List<RiskElementInfo> list);
	
	
	/**
	 * 批量添加黑名单
	 * @param list
	 * @return
	 */
	public int batchAddBlackTextInfo(List<BlackTextInfo> list);
	
	/**
	 * 删除风险预警元素
	 * @param elementId
	 * @return
	 */
	public int delRiskElementInfo( String elementId);
	
	/**
	 * 根据元素类型查询元素信息
	 * @param elementType
	 * @param elementValue
	 * @return
	 */
	public RiskElementInfo queryRiskElementInfoByTypeAndValue(String elementType,String elementValue);
	
	/**
	 * 查询规则集合列表
	 * @param criteria
	 * @return
	 */
	public List<RuleProfileInfo> queryRuleProfileList(Criteria criteria);
	
	/**
	 * 分页查询规则集合列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<RuleProfileInfo> queryPageRuleFInfo(Criteria criteria);
	
	/**
	 * 根据规则集合ID查询规则集合信息
	 * @param id
	 * @return
	 */
	public RuleProfileInfo queryRuleProFileInfoByProfileId(String id);
	
	/**
	 * 添加规则集合记录
	 * @param info
	 * @return
	 */
	public int addRuleProFileInfo(RuleProfileInfo info);
	
	/**
	 * 更新规则集合
	 * @param info
	 * @return
	 */
	public int updateRuleProfileInfo(RuleProfileInfo info);
	
	/**
	 * 添加规则到规则集合列表
	 * @param list
	 * @return
	 */
	public int addRulesToRuleProfile(List<RulesRefProFileInfo> list ) throws FraudExcetion ;
	
	/**
	 * 查询集合关联的规则列表
	 * @param proFileId
	 * @return
	 */
	public List<RulesInfo> queryRefRuleProfileLits(Criteria criteria);
	
	/**
	 * 分页查询询集合关联的规则列表
	 * @param proFileId
	 * @return
	 */
	public PageInfo<RulesInfo> queryPageRefRuleProfileLits(Criteria criteria);
	
	/**
	 * 根据规则ID删除规则集合中的规则
	 * @param profileId
	 * @param ruleId
	 * @return
	 */
	public int delRulesFromRulesProFile(String profileId,String [] rulesId) throws FraudExcetion;
	
	/**
	 * 查询用户关联集合列表
	 * @param criteria
	 * @return
	 */
	public List<MerchantRefRuleProfileInfo> queryAccountRefProfileList(Criteria criteria);
	
	/**
	 * 查询用户关联集合列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<MerchantRefRuleProfileInfo> queryPageAccountRefProfileList(Criteria criteria);
	
	/**
	 * 添加用户关联规则集合记录
	 * @param info
	 * @return
	 */
	public int addProfileToAccount(MerchantRefRuleProfileInfo info);
	
	/**
	 * 根据账号和卡类型查询账号关联规则集合信息
	 * @param accountNo
	 * @param cardType
	 * @return
	 */
	public List<MerchantRefRuleProfileInfo> queryProfileInfoByMerNoAndTerNo(String merNo,String terNo,String profileId);
	
	/**
	 * 根据ID查询账号关联集合信息
	 * @param id
	 * @return
	 */
	public MerchantRefRuleProfileInfo queryProfileInfoById(String id);
	
	/**
	 * 更新账号关联账户信息
	 * @param info
	 * @return
	 */
	public int updateAccountRefProfileInfoById(MerchantRefRuleProfileInfo info);
	
	/**
	 * 根据ID删除账户关联规则集合数据
	 * @param id
	 * @return
	 */
	public int delAccountFromList(String id);
	
	/**
	 * 根据处理类ID查询规则处理类
	 * @param classId
	 * @return
	 */
	public RuleProcessClass queryRuleProcessClassByClassId(String classId);
	
	/**
	 * 插入风控查询记录
	 * @param info
	 * @return
	 */
	public int addQueryFruadRecordInfo(FraudInfo info);
	
	/**
	 * 更新风控记录状态信息
	 * @param info
	 * @return
	 */
	public int updateQueryFraudRecordInfo(FraudInfo info);
	
	/**
	 * 查询风控记录列表
	 * @param criteria
	 * @return
	 */
	public List<FraudInfo> queryFraudRecordList(Criteria criteria);
	
	/**
	 * 分页查询风控记录列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<FraudInfo> queryPageFraudRecordList(Criteria criteria);
	
	
	/**
	 * 查询黑名单查询记录
	 * @param criteria
	 * @return
	 */
	public PageInfo<AutoRiskInfo> queryPageBlackTextLimitList(Criteria criteria);
	
	 
	public List<AutoRiskInfo> queryBlackTextLimitList(Criteria criteria);
	
	/**
	 * 查询风控详细信息
	 * @param txId
	 * @return
	 */
	public FraudInfo queryFraudDetailByTxId(String txId);
	
	/**
	 * 查询黑名单数据
	 * @param criteria
	 * @return
	 */
	public List<BlackTextInfo> queryBlackInfoList(Criteria criteria);
	
	/**
	 * 分页查询黑名单数据
	 * @param criteria
	 * @return
	 */
	public PageInfo<BlackTextInfo> queryPageBlackInfoList(Criteria criteria);
	
	/**
	 * 查询BIN信息列表
	 * @param criteria
	 * @return
	 */
	public List<BinInfo> queryBinList(Criteria criteria);
	
	/**
	 * 分页查询BIN信息列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<BinInfo> queryPageBinList(Criteria criteria);
	
	/**
	 * 添加黑名单数据
	 * @param info
	 * @return
	 */
	public int addBlackTextInfo(BlackTextInfo info) throws FraudExcetion;
	
	/**
	 * 根据黑名单类型和值查询黑名单数据 
	 * @param value
	 * @param type
	 * @return
	 */
	public BlackTextInfo queryBlackTextInfoByValueAndType(String value,String type);
	
	/**
	 * 删除黑名单数据
	 * @param blackId
	 * @return
	 */
	public int delBlackTextById(String blackId);
	
	/**
	 * 添加BIN信息
	 * @param info
	 * @return
	 */
	public int addBinInfo(BinInfo info);
	
	/**
	 * 根据ID删除Bin信息
	 * @param id
	 * @return
	 */
	public int deleteBinInfoById(String id);
	
	/**
	 * 查询账号review时通知review结果
	 * @param accountNo
	 * @return
	 */
	public String queryAccountNotifyUrlInfoByAccountNo(String accountNo);
	
	/**
	 * 保存黑名单匹配记录
	 * @param info
	 * @return
	 */
	public int saveQueryBlackTextRecord(AutoRiskInfo info);


	/** 查询重复的规则名 */
	RulesInfo queryRuleValueName(RulesInfo rulesInfo);

}
