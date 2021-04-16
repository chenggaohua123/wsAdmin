package com.gateway.fraud.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.fraud.model.MerchantRefRuleProfileInfo;
import com.gateway.fraud.model.AutoRiskInfo;
import com.gateway.fraud.model.BaseTableValueInfo;
import com.gateway.fraud.model.BinInfo;
import com.gateway.fraud.model.BlackTextInfo;
import com.gateway.fraud.model.FraudInfo;
import com.gateway.fraud.model.ParamInfo;
import com.gateway.fraud.model.ParamValueInfo;
import com.gateway.fraud.model.RiskElementInfo;
import com.gateway.fraud.model.RuleProcessClass;
import com.gateway.fraud.model.RuleProfileInfo;
import com.gateway.fraud.model.RulesInfo;
import com.gateway.fraud.model.RulesRefProFileInfo;

public interface FraudManageDao {
	
	
	/**
	 * 查询记录笔数
	 * @param criteria
	 * @return
	 */
	public int countByCriteria(Criteria criteria);
	
	
	
	/**
	 * 插入账户信息
	 * @param info
	 * @return
	 */
	
	/**
	 * 添加规则参数信息
	 * @param info
	 * @return
	 */
	public int addParam(@Param("info") ParamInfo info);
	
	/**
	 * 查询规则处理类列表
	 * @param criteria
	 * @return
	 */
	public List<RuleProcessClass> queryProcessClassList(Criteria criteria);
	
	/**
	 * 更新参数信息
	 * @param info
	 * @return
	 */
	public int updateParamInfo(@Param("info") ParamInfo info);
	
	/**
	 * 查询参数列表
	 * @param criteria
	 * @return
	 */
	public List<ParamInfo> queryParamInfoList(Criteria criteria);
	
	/**
	 * 根据参数ID查询参数信息
	 * @param paramId
	 * @return
	 */
	public ParamInfo queryParamInfoByParamId(@Param("paramId") String paramId);
	
	/**
	 * 查询参数列表的值
	 * @param paramId
	 * @return
	 */
	public List<String> queryParamListValuesByParamId(@Param("paramId") String paramId); 
	
	public List<Map<String,String>> queryParamTableValues(@Param("tableName") String tableName,
			@Param("colKey")String colKey, @Param("colValue")String colValue);
	
	/**
	 * 查询参数详细信息列表
	 * @param paramId
	 * @return
	 */
	public List<ParamValueInfo> queryParamValuesInfoByParamId(@Param("paramId") String paramId);
	
	
	/**
	 * 删除参数信息
	 * @param paramId
	 * @return
	 */
	public int delParamByParamId(@Param("paramId")String paramId);
	
	/**
	 * 根据参数ID删除关联参数值
	 * @param paramId
	 * @return
	 */
	public int delParamValuesByParamId(@Param("paramId")String paramId); 
	
	/**
	 * 删除参数配型为Table的关联数据
	 * @param tableName
	 * @return
	 */
	public int delBaseTableInfoByTableName(@Param("tableName") String tableName);
	
	/**
	 * 插入参数类表
	 * @param info
	 * @return
	 */
	public int insertParamListValues(@Param("info") ParamValueInfo info);
	
	/**
	 * 插入tabel类型参数值
	 * @param info
	 * @return
	 */
	public int insertBaseTableInfo(@Param("info") BaseTableValueInfo info);
	/**
	 * 查询记录笔数
	 * @param criteria
	 * @return
	 */
	public int countParamsByCriteria(Criteria criteria);
	
	
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
	 * 添加规则信息
	 * @param info
	 * @return
	 */
	public int addRuleInfo(@Param("info")RulesInfo info);
	
	/**
	 * 更新规则信息
	 * @param info
	 * @return
	 */
	public int updateRuleInfo(@Param("info")RulesInfo info);
	
	/**
	 * 查询规则的详细信息
	 * @param ruleId
	 * @return
	 */
	public RulesInfo queryRulesInfoDetailByRuleId(@Param("ruleId")String ruleId);
	
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
	public int addRiskElementInfo(@Param("info") RiskElementInfo info);
	
	/**
	 * 删除风险预警元素
	 * @param elementId
	 * @return
	 */
	public int delRiskElementInfo(@Param("elementId") String elementId);
	
	/**
	 * 根据元素类型查询元素信息
	 * @param elementType
	 * @param elementValue
	 * @return
	 */
	public RiskElementInfo queryRiskElementInfoByTypeAndValue(@Param("elementType") String elementType,@Param("elementValue") String elementValue);
	
	/**
	 * 查询规则集合列表
	 * @param criteria
	 * @return
	 */
	public List<RuleProfileInfo> queryRuleProfileList(Criteria criteria);
	
	/**
	 * 查询规则集合总记录数
	 * @param criteria
	 * @return
	 */
	public int countRuleProfileInfo(Criteria criteria);
	
	/**
	 * 根据规则集合ID查询规则集合信息
	 * @param id
	 * @return
	 */
	public RuleProfileInfo queryRuleProFileInfoByProfileId(@Param("proFileId")String id);
	
	/**
	 * 添加规则集合记录
	 * @param info
	 * @return
	 */
	public int addRuleProFileInfo(@Param("info") RuleProfileInfo info);
	
	/**
	 * 更新规则集合
	 * @param info
	 * @return
	 */
	public int updateRuleProfileInfo(@Param("info") RuleProfileInfo info);
	
	/**
	 * 添加规则到规则集合列表
	 * @param list
	 * @return
	 */
	public int addRulesToRuleProfile(@Param("info") RulesRefProFileInfo info );
	
	/**
	 * 根据集合ID和规则ID查询关联数据
	 * @param profileId
	 * @param ruleId
	 * @return
	 */
	public List<RulesRefProFileInfo> queryRefRulesInfoByProFileIdAndRuleId(@Param("profileId") String profileId,@Param("ruleId")String ruleId);
	
	/**
	 * 查询集合关联的规则列表
	 * @param proFileId
	 * @return
	 */
	public List<RulesInfo> queryRefRuleProfileLits(Criteria criteria);
	
	/**
	 * 统计记录条数
	 * @param criteria
	 * @return
	 */
	public int countRefRuleProfileList(Criteria criteria);
	
	/**
	 * 根据规则ID删除规则集合中的规则
	 * @param profileId
	 * @param ruleId
	 * @return
	 */
	public int delRulesFromRulesProFile(@Param("profileId") String profileId,@Param("ruleId")String ruleId);
	
	/**
	 * 查询用户关联集合列表
	 * @param criteria
	 * @return
	 */
	public List<MerchantRefRuleProfileInfo> queryAccountRefProfileList(Criteria criteria);
	
	/**
	 * 用户关联规则集合的记录数
	 * @param criteria
	 * @return
	 */
	public int countAccountRefProfile(Criteria criteria);
	
	/**
	 * 添加用户关联规则集合记录
	 * @param info
	 * @return
	 */
	public int addProfileToAccount(@Param("info") MerchantRefRuleProfileInfo info);
	
	/**
	 * 根据账号和卡类型查询账号关联规则集合信息
	 * @param accountNo
	 * @param cardType
	 * @return
	 */
	public List<MerchantRefRuleProfileInfo> queryProfileInfoByMerNoAndTerNo(@Param("merNo") String merNo,@Param("terNo") String terNo,@Param("profileId") String profileId);
	
	/**
	 * 根据ID查询账号关联集合信息
	 * @param id
	 * @return
	 */
	public MerchantRefRuleProfileInfo queryProfileInfoById(@Param("id") String id);
	
	/**
	 * 更新账号关联账户信息
	 * @param info
	 * @return
	 */
	public int updateAccountRefProfileInfoById(@Param("info") MerchantRefRuleProfileInfo info);
	
	/**
	 * 根据ID删除账户关联规则集合数据
	 * @param id
	 * @return
	 */
	public int delAccountFromList(@Param("id") String id);
	
	/**
	 * 根据处理类ID查询规则处理类
	 * @param classId
	 * @return
	 */
	public RuleProcessClass queryRuleProcessClassByClassId(@Param("classId") String classId);
	
	
	/**
	 * 插入风控查询记录
	 * @param info
	 * @return
	 */
	public int addFraudRecord(@Param("info") FraudInfo info);
	
	/**
	 * 添加maxmind查询记录
	 * @param info
	 * @return
	 */
	public int addMaxmindInfo(@Param("info") FraudInfo info);
	
	/**
	 * 更新风控记录状态信息
	 * @param info
	 * @return
	 */
	public int updateQueryFraudRecordInfo(@Param("info") FraudInfo info);
	
	/**
	 * 查询风控记录列表
	 * @param criteria
	 * @return
	 */
	public List<FraudInfo> queryFraudRecordList(Criteria criteria);
	
	/**
	 * 统计风控记录总数
	 * @param criteria
	 * @return
	 */
	public int countFraudRecord(Criteria criteria);
	
	/**
	 * 查询风控详细信息
	 * @param txId
	 * @return
	 */
	public FraudInfo queryFraudDetailByTxId(@Param("txId") String txId);
	
	/**
	 * 查询某个时间段内的元素的支付次数
	 * @param hour
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	public int queryElementPayCount(@Param("hour") String hour,@Param("paramName") String paramName,@Param("paramValue") String paramValue);
	
	/**
	 * 查询某个元素是否在黑名单中
	 * @param blackText
	 * @param tableName
	 * @param blackType
	 * @return
	 */
	public int queryBlackText(@Param("blackText") String blackText,@Param("blackTextColname") String blackTextColname,@Param("blackType") String blackType);
	
	/**
	 * 
	 * @param blackText
	 * @param blackTextColname
	 * @param blackType
	 * @return
	 */
	public int queryIntervalInfo(@Param("paramValue") String blackText,@Param("type") String blackType);
	
	/**
	 * 查询黑名单数据
	 * @param criteria
	 * @return
	 */
	public List<BlackTextInfo> queryBlackInfoList(Criteria criteria);
	
	/**
	 * 统计黑名单记数
	 * @param criteria
	 * @return
	 */
	public int countBlackInfoList(Criteria criteria);
	
	/**
	 * 查询BIN信息列表
	 * @param criteria
	 * @return
	 */
	public List<BinInfo> queryBinList(Criteria criteria);
	
	/**
	 * 统计BIN记录数
	 * @param criteria
	 * @return
	 */
	public int countBinInfoList(Criteria criteria);
	
	/**
	 * 添加黑名单数据
	 * @param info
	 * @return
	 */
	public int addBlackTextInfo(@Param("info") BlackTextInfo info);
	
	/**
	 * 删除黑名单数据
	 * @param blackId
	 * @return
	 */
	public int delBlackTextById(@Param("blackId") String blackId);
	
	/**
	 * 添加BIN信息
	 * @param info
	 * @return
	 */
	public int addBinInfo(@Param("info") BinInfo info);
	
	/**
	 * 根据ID删除Bin信息
	 * @param id
	 * @return
	 */
	public int deleteBinInfoById(@Param("id") String id);
	
	/**
	 * 根据黑名单类型和值查询黑名单数据 
	 * @param value
	 * @param type
	 * @return 
	 */
	public BlackTextInfo queryBlackTextInfoByValueAndType(@Param("blackText") String value,@Param("blackType") String type);
	
	/**
	 * 查询账号review时通知review结果
	 * @param accountNo
	 * @return
	 */
	public String queryAccountNotifyUrlInfoByAccountNo(@Param("accountNo") String accountNo);
	
	
	/**
	 * 查询风险库查询记录
	 * @param criteria
	 * @return
	 */
	public List<AutoRiskInfo> queryBlackTextLimitList(Criteria criteria);
	
	/**
	 * 统计记录总数 
	 * @param criteria
	 * @return
	 */
	public int countBalckTextLimitRecord(Criteria criteria);
	
	/**
	 * 保存风控查询记录
	 * @param info
	 * @return
	 */
	public int saveQueryBlackTextRecord(@Param("info") AutoRiskInfo info);
	
	/** 查询重复的规则名 */
	public RulesInfo queryRuleValueName(RulesInfo rulesInfo);
}
