package com.gateway.fraud.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.fraud.common.exception.FraudExcetion;
import com.gateway.fraud.mapper.FraudManageDao;
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
import com.gateway.fraud.service.FraudManageService;
@Service
public class FraudManageServiceImpl implements FraudManageService{ 
	
	//@Autowired
	//private RedisTemplate<Serializable, Serializable> jedisTemplate;
	@Autowired
	private FraudManageDao fraudManageDao;

	public FraudManageDao getFraudManageDao() {
		return fraudManageDao;
	}

	public void setFraudManageDao(FraudManageDao fraudManageDao) {
		this.fraudManageDao = fraudManageDao;
	}

	/*public RedisTemplate<Serializable, Serializable> getJedisTemplate() {
		return jedisTemplate;
	}*/

	
	@Override
    public int countByCriteria(Criteria criteria) {
        return fraudManageDao.countByCriteria(criteria);
    }
	

	@Override
	public int addParam(ParamInfo info) {
		return fraudManageDao.addParam(info);
	}

	@Override
	public int updateParamInfo(ParamInfo info) {
		return fraudManageDao.updateParamInfo(info);
	}

	@Override
	public List<ParamInfo> queryParamInfoList(Criteria criteria) {
		return fraudManageDao.queryParamInfoList(criteria);
	}

	@Override
	@Transactional
	public ParamInfo queryParamInfoByParamId(String paramId) {
		ParamInfo info = fraudManageDao.queryParamInfoByParamId(paramId);
		if(null == info){
			return null;
		}else{
			//查询参数列表值
			List<ParamValueInfo> values = fraudManageDao.queryParamValuesInfoByParamId(paramId);
			info.setParamValuesList(values);
			String paramType = info.getType();
			if("String".equals(paramType)){
				List<String> list = fraudManageDao.queryParamListValuesByParamId(paramId);
				if(list.size() >0){
					info.setStringValue(list.get(0));
					info.setListValue(list);
				}
			}else if("List".equals(paramType)){
				List<String> list = fraudManageDao.queryParamListValuesByParamId(paramId);
				if(list.size() >0){
					info.setStringValue(list.get(0));
					info.setListValue(list);
				}
			}else if("Table".equals(paramType)){
				if(values.size()>0){
					ParamValueInfo value = values.get(0);
					String tableName = value.getTableName();
					String colKey = value.getColKey();
					String colValue = value.getColValue();
					if(null != tableName && null != colKey && null != colValue && 
							tableName.trim().length() > 0 && colKey.trim().length()>0 && colValue.trim().length()>0){
						List<Map<String, String>> tableValueList = fraudManageDao.queryParamTableValues(tableName, colKey, colValue);
						Map<String, String> tableValue = new HashMap<String, String>();
						for(Map<String,String> tempMap:tableValueList){
							for(int i = 0 ; i < tempMap.size();i++){
								tableValue.put(tempMap.keySet().toArray()[i].toString(), tempMap.get(tempMap.keySet().toArray()[i]));
							}
						}
						info.setTableValue(tableValue);
						info.setTableName(tableName);
						info.setColKeyName(colKey);
						info.setColValueName(colValue);
						info.setColKeyValue(tableValue.get(colKey));
						info.setColValue(tableValue.get(colValue));
					}
				}
			}else{
				return null;
			}
		}
		return info;
	}

	@Override
	public PageInfo<ParamInfo> queryPageParamInfoList(Criteria criteria) {
		PageInfo<ParamInfo> res = new PageInfo<ParamInfo>(criteria.getPageNum(), criteria.getPageSize());
        int total=this.countParamsByCriteria(criteria);
        res.setTotal(total);
        criteria.setOffset(res.getOffset());
        criteria.setRows( criteria.getPageSize());
        List<ParamInfo> list = this.queryParamInfoList(criteria);
        res.setData(list);
		return res;
	}

	@Override
	public int countParamsByCriteria(Criteria criteria) {
		return fraudManageDao.countParamsByCriteria(criteria);
	}

	@Override
	public int countRuleInfoByCriteria(Criteria criteria) {
		return fraudManageDao.countRuleInfoByCriteria(criteria);
	}

	@Override
	public List<RulesInfo> queryRulesList(Criteria criteria) {
		return fraudManageDao.queryRulesList(criteria);
	}

	@Override
	public PageInfo<RulesInfo> queryPageRulesList(Criteria criteria) {
		PageInfo<RulesInfo> res = new PageInfo<RulesInfo>(criteria.getPageNum(), criteria.getPageSize());
        int total=this.countRuleInfoByCriteria(criteria);
        res.setTotal(total);
        criteria.setOffset(res.getOffset());
        criteria.setRows( criteria.getPageSize());
        List<RulesInfo> list = this.queryRulesList(criteria);
        res.setData(list);
		return res;
	}

	@Override
	public int addRuleInfo(RulesInfo info) {
		int i = fraudManageDao.addRuleInfo(info);
		return i;
	}
	
	@Override
	public RulesInfo queryRuleValueName(RulesInfo rulesInfo){
		return fraudManageDao.queryRuleValueName(rulesInfo);
	}

	@Override
	public int updateRuleInfo(RulesInfo info) {
		int i = fraudManageDao.updateRuleInfo(info);
		//插入成功，更新缓存信息
		/*if(i > 0){
			jedisTemplate.opsForHash().put(RedisKeyConfig.RULES_INFO, info.getRuleId(), info);
		}*/
		return i;
	}

	@Override
	public RulesInfo queryRulesInfoDetailByRuleId(String ruleId) {
		RulesInfo info = null;
		//info = (RulesInfo) jedisTemplate.opsForHash().get(RedisKeyConfig.RULES_INFO, ruleId);
		//if(null == info){
			info = fraudManageDao.queryRulesInfoDetailByRuleId(ruleId);
		//}
		return info;
	}

	@Override
	public List<RiskElementInfo> queryRiskElementInfo(Criteria criteria) {
		return fraudManageDao.queryRiskElementInfo(criteria);
	}

	@Override
	public PageInfo<RiskElementInfo> queryPageRiskElementInfo(Criteria criteria) {
		PageInfo<RiskElementInfo> res = new PageInfo<RiskElementInfo>(criteria.getPageNum(), criteria.getPageSize());
        int total=this.queryRiskRiskElementCount(criteria);
        res.setTotal(total);
        criteria.setOffset(res.getOffset());
        criteria.setRows( criteria.getPageSize());
        List<RiskElementInfo> list = this.queryRiskElementInfo(criteria);
        res.setData(list);
        return res;
	}

	@Override
	public int queryRiskRiskElementCount(Criteria criteria) {
		return fraudManageDao.queryRiskRiskElementCount(criteria);
	}

	@Override
	@Transactional
	public int addRiskElementInfo(RiskElementInfo info) throws FraudExcetion {
		RiskElementInfo temp =  fraudManageDao.queryRiskElementInfoByTypeAndValue(info.getElementType(), info.getElementValue());
		if(null != temp){
			throw new FraudExcetion("该条信息已经存在，不需要在添加。");
		}
		return fraudManageDao.addRiskElementInfo(info);
	}
	@Transactional
	public int batchAddRiskElement(List<RiskElementInfo> list){
		int count = 0;
		for(RiskElementInfo info:list){
			RiskElementInfo temp =  fraudManageDao.queryRiskElementInfoByTypeAndValue(info.getElementType(), info.getElementValue());
			if(null != temp){
				continue;
			}
			count+=fraudManageDao.addRiskElementInfo(info);
		}
		return count;
	}
	/**
	 * 删除风险预警元素
	 * @param elementId
	 * @return
	 */
	public int delRiskElementInfo( String elementId){
		return fraudManageDao.delRiskElementInfo(elementId);
	}

	@Override
	public RiskElementInfo queryRiskElementInfoByTypeAndValue(
			String elementType, String elementValue) {
		return fraudManageDao.queryRiskElementInfoByTypeAndValue(elementType, elementValue);
	}

	@Override
	public int delParamByParamId(String paramId) {
		return fraudManageDao.delParamByParamId(paramId);
	}

	@Override
	@Transactional
	public boolean setParamValue(ParamInfo oldinfo,ParamInfo newInfo) throws FraudExcetion {
		if(null == oldinfo || null == newInfo){
			throw new FraudExcetion("参数为空。");
		}
		if("1".equals(oldinfo.getComFrom())){
			throw new FraudExcetion("参数来源于程序的不能设置值。");
		}
		//首先删除旧的值
		fraudManageDao.delParamValuesByParamId(oldinfo.getParamId());
		//插入更新的值
		if("String".equals(oldinfo.getType()) || "List".equals(oldinfo.getType())){
			for(String value:newInfo.getListValue()){
				ParamValueInfo paramValue = new ParamValueInfo();
				paramValue.setColKey(newInfo.getColKeyName());
				paramValue.setColValue(newInfo.getColValueName());
				paramValue.setParamId(newInfo.getParamId());
				paramValue.setTableName(newInfo.getTableName());
				paramValue.setValue(value);
				fraudManageDao.insertParamListValues(paramValue);
			}
		}else if("Table".equals(oldinfo.getType())){
			
			ParamValueInfo paramValue = new ParamValueInfo();
			paramValue.setColKey(newInfo.getColKeyName());
			paramValue.setColValue(newInfo.getColValueName());
			paramValue.setParamId(newInfo.getParamId());
			paramValue.setTableName(newInfo.getTableName());
			
			fraudManageDao.insertParamListValues(paramValue);
			
			BaseTableValueInfo baseTableValue = new BaseTableValueInfo();
			Map<String,String> tableValue = newInfo.getTableValue();
			baseTableValue.setColKey(tableValue.get(newInfo.getColKeyName()));
			baseTableValue.setColValue(tableValue.get(newInfo.getColValueName()));
			baseTableValue.setCreateBy(newInfo.getCreateBy());
			baseTableValue.setTableName(newInfo.getTableName());
			fraudManageDao.insertBaseTableInfo(baseTableValue);
			
		}else{
			throw new FraudExcetion("类型错误。");
		}
		newInfo = this.queryParamInfoByParamId(newInfo.getParamId());
		return true;
	}

	@Override
	public List<RuleProcessClass> queryProcessClassList(Criteria criteria) {
		return fraudManageDao.queryProcessClassList(criteria);
	}

	@Override
	public List<RuleProfileInfo> queryRuleProfileList(Criteria criteria) {
		return fraudManageDao.queryRuleProfileList(criteria);
	}

	@Override
	public PageInfo<RuleProfileInfo> queryPageRuleFInfo(Criteria criteria) {
		PageInfo<RuleProfileInfo> res = new PageInfo<RuleProfileInfo>(criteria.getPageNum(), criteria.getPageSize());
        int total=fraudManageDao.countRuleProfileInfo(criteria);
        res.setTotal(total);
        criteria.setOffset(res.getOffset());
        criteria.setRows( criteria.getPageSize());
        List<RuleProfileInfo> list = this.queryRuleProfileList(criteria);
        res.setData(list);
        return res;
	}

	@Override
	public RuleProfileInfo queryRuleProFileInfoByProfileId(String id) {
		return fraudManageDao.queryRuleProFileInfoByProfileId(id);
	}

	@Override
	public int addRuleProFileInfo(RuleProfileInfo info) {
		return fraudManageDao.addRuleProFileInfo(info);
	}

	@Override
	public int updateRuleProfileInfo(RuleProfileInfo info) {
		return fraudManageDao.updateRuleProfileInfo(info);
	}

	@Override
	@Transactional
	public int addRulesToRuleProfile(List<RulesRefProFileInfo> list) throws FraudExcetion {
		if(null == list || list.size()==0){
			throw new FraudExcetion("参数不能为空。");
		}
		int count = 0;
		for(RulesRefProFileInfo info :list){
			//过滤重复数据
			List<RulesRefProFileInfo> tempList = fraudManageDao.queryRefRulesInfoByProFileIdAndRuleId(info.getProfileId(), info.getRuleId());
			if(null == tempList || tempList.size()==0){
				count +=fraudManageDao.addRulesToRuleProfile(info);
			}
		}
		return count;
	}

	@Override
	public List<RulesInfo> queryRefRuleProfileLits(Criteria criteria) {
		return fraudManageDao.queryRefRuleProfileLits(criteria);
	}

	@Override
	public PageInfo<RulesInfo> queryPageRefRuleProfileLits(Criteria criteria) {
		PageInfo<RulesInfo> res = new PageInfo<RulesInfo>(criteria.getPageNum(), criteria.getPageSize());
        int total=fraudManageDao.countRefRuleProfileList(criteria);
        res.setTotal(total);
        criteria.setOffset(res.getOffset());
        criteria.setRows( criteria.getPageSize());
        List<RulesInfo> list = this.queryRefRuleProfileLits(criteria);
        res.setData(list);
        return res;
	}

	@Override
	@Transactional
	public int delRulesFromRulesProFile(String profileId, String [] rulesId) throws FraudExcetion {
		if(null == profileId || profileId.trim().length()==0 || null == rulesId || rulesId.length == 0){
			throw new FraudExcetion("参数为空，");
		}
		int count = 0;
		for(String ruleId :rulesId){
			count += fraudManageDao.delRulesFromRulesProFile(profileId, ruleId);
		}
		return count ;
	}

	@Override
	public List<MerchantRefRuleProfileInfo> queryAccountRefProfileList(
			Criteria criteria) {
		return fraudManageDao.queryAccountRefProfileList(criteria);
	}

	@Override
	public PageInfo<MerchantRefRuleProfileInfo> queryPageAccountRefProfileList(
			Criteria criteria) {
		PageInfo<MerchantRefRuleProfileInfo> res = new PageInfo<MerchantRefRuleProfileInfo>(criteria.getPageNum(), criteria.getPageSize());
        int total=fraudManageDao.countAccountRefProfile(criteria);
        res.setTotal(total);
        criteria.setOffset(res.getOffset());
        criteria.setRows( criteria.getPageSize());
        List<MerchantRefRuleProfileInfo> list = this.queryAccountRefProfileList(criteria);
        res.setData(list);
        return res;
	}

	@Override
	public int addProfileToAccount(MerchantRefRuleProfileInfo info) {
		return fraudManageDao.addProfileToAccount(info);
	}

	@Override
	public List<MerchantRefRuleProfileInfo> queryProfileInfoByMerNoAndTerNo(
			String merNo, String terNo,String profileId) {
		return fraudManageDao.queryProfileInfoByMerNoAndTerNo(merNo, terNo,profileId);
	}

	@Override
	public MerchantRefRuleProfileInfo queryProfileInfoById(String id) {
		return fraudManageDao.queryProfileInfoById(id);
	}

	@Override
	public int updateAccountRefProfileInfoById(MerchantRefRuleProfileInfo info) {
		return fraudManageDao.updateAccountRefProfileInfoById(info);
	}

	@Override
	public int delAccountFromList(String id) {
		return fraudManageDao.delAccountFromList(id);
	}

	@Override
	public RuleProcessClass queryRuleProcessClassByClassId(String classId) {
		return fraudManageDao.queryRuleProcessClassByClassId(classId);
	}


	@Override
	@Transactional
	public int addQueryFruadRecordInfo(FraudInfo info) {
		int count = 0;
		count += fraudManageDao.addFraudRecord(info);
		count +=fraudManageDao.addMaxmindInfo(info);
		return count;
	}
	
	@Override
	@Transactional
	public int updateQueryFraudRecordInfo(FraudInfo info){
		return fraudManageDao.updateQueryFraudRecordInfo(info);
	}

	@Override
	public List<FraudInfo> queryFraudRecordList(Criteria criteria) {
		return fraudManageDao.queryFraudRecordList(criteria);
	}

	@Override
	public PageInfo<FraudInfo> queryPageFraudRecordList(Criteria criteria) {
		PageInfo<FraudInfo> res = new PageInfo<FraudInfo>(criteria.getPageNum(), criteria.getPageSize());
        int total=fraudManageDao.countFraudRecord(criteria);
        res.setTotal(total);
        criteria.setOffset(res.getOffset());
        criteria.setRows( criteria.getPageSize());
        List<FraudInfo> list = this.queryFraudRecordList(criteria);
        res.setData(list);
        return res;
	}

	@Override
	public FraudInfo queryFraudDetailByTxId(String txId) {
		return fraudManageDao.queryFraudDetailByTxId(txId);
	}

	@Override
	public List<BlackTextInfo> queryBlackInfoList(Criteria criteria) {
		return fraudManageDao.queryBlackInfoList(criteria);
	}

	@Override
	public PageInfo<BlackTextInfo> queryPageBlackInfoList(Criteria criteria) {
		PageInfo<BlackTextInfo> res = new PageInfo<BlackTextInfo>(criteria.getPageNum(), criteria.getPageSize());
        int total=fraudManageDao.countBlackInfoList(criteria);
        res.setTotal(total);
        criteria.setOffset(res.getOffset());
        criteria.setRows( criteria.getPageSize());
        List<BlackTextInfo> list = this.queryBlackInfoList(criteria);
        res.setData(list);
        return res;
	}

	@Override
	public List<BinInfo> queryBinList(Criteria criteria) {
		return fraudManageDao.queryBinList(criteria);
	}

	@Override
	public PageInfo<BinInfo> queryPageBinList(Criteria criteria) {
		PageInfo<BinInfo> res = new PageInfo<BinInfo>(criteria.getPageNum(), criteria.getPageSize());
        int total=fraudManageDao.countBinInfoList(criteria);
        res.setTotal(total);
        criteria.setOffset(res.getOffset());
        criteria.setRows( criteria.getPageSize());
        List<BinInfo> list = this.queryBinList(criteria);
        res.setData(list);
        return res;
	}

	@Override
	public int addBlackTextInfo(BlackTextInfo info) throws FraudExcetion {
		BlackTextInfo temp =  fraudManageDao.queryBlackTextInfoByValueAndType(info.getBlackText(), info.getBlackType());
		if(null != temp){
			throw new FraudExcetion("该条信息已经存在，不需要在添加。");
		}
		return fraudManageDao.addBlackTextInfo(info);
	}

	@Override
	public int delBlackTextById(String blackId) {
		return fraudManageDao.delBlackTextById(blackId);
	}

	@Override
	public int addBinInfo(BinInfo info) {
		return fraudManageDao.addBinInfo(info);
	}

	@Override
	public int deleteBinInfoById(String id) {
		return fraudManageDao.deleteBinInfoById(id);
	}

	@Override
	public BlackTextInfo queryBlackTextInfoByValueAndType(String value,
			String type) {
		return fraudManageDao.queryBlackTextInfoByValueAndType(value, type);
	}

	@Override
	public String queryAccountNotifyUrlInfoByAccountNo(String accountNo) {
		return fraudManageDao.queryAccountNotifyUrlInfoByAccountNo(accountNo);
	}


	@Override
	public int batchAddBlackTextInfo(List<BlackTextInfo> list) {
		int count = 0;
		for(BlackTextInfo info:list){
			try {
				count +=this.addBlackTextInfo(info);
			} catch (FraudExcetion e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
	public List<AutoRiskInfo> queryBlackTextLimitList(Criteria criteria){
		return fraudManageDao.queryBlackTextLimitList(criteria);
	}
	
	@Override
	public PageInfo<AutoRiskInfo> queryPageBlackTextLimitList(Criteria criteria) {
		PageInfo<AutoRiskInfo> res = new PageInfo<AutoRiskInfo>(criteria.getPageNum(), criteria.getPageSize());
        int total=fraudManageDao.countBalckTextLimitRecord(criteria);
        res.setTotal(total);
        criteria.setOffset(res.getOffset());
        criteria.setRows( criteria.getPageSize());
        List<AutoRiskInfo> list = this.queryBlackTextLimitList(criteria);
        res.setData(list);
        return res;
	}

	@Override
	public int saveQueryBlackTextRecord(AutoRiskInfo info) {
		return fraudManageDao.saveQueryBlackTextRecord(info);
	}

}
