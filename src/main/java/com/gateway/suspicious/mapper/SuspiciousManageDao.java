package com.gateway.suspicious.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.fraud.model.BaseTableValueInfo;
import com.gateway.fraud.model.BinInfo;
import com.gateway.fraud.model.BlackTextInfo;
import com.gateway.fraud.model.MerchantRefRuleProfileInfo;
import com.gateway.fraud.model.ParamInfo;
import com.gateway.fraud.model.ParamValueInfo;
import com.gateway.fraud.model.RiskElementInfo;
import com.gateway.fraud.model.RuleProcessClass;
import com.gateway.fraud.model.RuleProfileInfo;
import com.gateway.fraud.model.RulesInfo;
import com.gateway.fraud.model.RulesRefProFileInfo;
import com.gateway.suspicious.model.SuspiciousOrderInfo;
import com.gateway.suspicious.model.SuspiciousOrderListInfo;
import com.gateway.suspicious.model.SuspiciousQueryInfo;
import com.gateway.suspicious.model.SuspiciousRuleInfo;
import com.gateway.suspicious.model.SuspiciousRuleListInfo;
import com.gateway.suspicious.model.SuspicioustTransInfo;
import com.gateway.transmgr.model.GoodsInfo;
import com.gateway.transmgr.model.TransInfo;

public interface SuspiciousManageDao {
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
	 * 分页查询风险预警元素
	 * @param criteria
	 * @return
	 */
	public PageInfo<RiskElementInfo> queryPageRiskElementInfo(Criteria criteria);
	
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
	
	/** 查询重复的规则名 */
	public RulesInfo queryRuleValueName(RulesInfo rulesInfo);
	/**
	 * 查找可疑订单明细
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<SuspiciousOrderInfo> querySuspiciousOrderInfo(Criteria criteria, RowBounds rb);
	/**
	 * 查找可疑订单总数量
	 * @param criteria
	 * @return
	 */
	public int querySuspiciousOrderCount(Criteria criteria);
	/**
	 * 字段名触犯规则流水号信息 
	 */
	public List<String> querySuspiciousRelTradeNoList(@Param("vo") SuspiciousQueryInfo vo, @Param("trans") TransInfo trans, @Param("sigleAmount") List<String> sigleAmount);
	/**
	 * 字段名触犯规则流水号信息 
	 */
	public SuspicioustTransInfo querySuspiciousRelTradeNo(@Param("tradeNo") String tradeNo);
	/**
	 * 导出触犯规则流水号信息
	 */
	public List<String> querySuspiciousRuleTransInfo(@Param("vo") SuspiciousQueryInfo vo, @Param("trans") TransInfo trans, @Param("sigleAmount") List<String> sigleAmount);
	/**
	 * 根据交易订单规则ID查找订单所触犯的规则
	 */
	public List<SuspiciousRuleInfo> querySuspiciousOrderRuleInfo(@Param("susType") String susType, @Param("tradeNo") String tradeNo, @Param("createDate") String createDate);
	/**
	 * 根据交易订单规则ID查找订单所触犯的规则总数
	 */
	public int querySuspiciousOrderRuleInfoCount(@Param("susType") String susType, @Param("tradeNo") String tradeNo, @Param("createDate") String createDate);
	/**
	 * 查找导出订单信息
	 */
	public List<SuspiciousOrderListInfo> querySuspiciousOrderListInfo(Criteria criteria);
	/**
	 * 查找流水号信息
	 */
	public String querySuspiciousOrderTradeNoInfo(@Param("ruleIdList") String ruleIdList, @Param("createDate") String createDate, @Param("susType") String susType, @Param("merNo") String merNo, @Param("terNo") String terNo);
	/**
	 * 查找规则信息
	 */
	public SuspiciousRuleListInfo querySuspiciousOrderRuleListInfo(@Param("ruleIdList") String ruleIdList);
	/**
	 * 查询订单明细
	 * */
	
	public TransInfo queryTransListByTradeNo(@Param("tradeNo") String tradeNo);
	/**
	 * 通过流水号查询货物信息
	 * */
	public List<GoodsInfo> queryGoodsInfoByTradeNo(@Param("tradeNo") String tradeNo);
	
	/**
	 * 查询规则单笔交易金额限制值
	 */
	public List<String> querySuspiciousSingleAmountInfoList(@Param("ruleIds") String ruleIds);
}
