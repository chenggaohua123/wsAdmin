package com.gateway.suspicious.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.utils.Funcs;
import com.gateway.fraud.common.exception.FraudExcetion;
import com.gateway.fraud.model.BaseTableValueInfo;
import com.gateway.fraud.model.BinInfo;
import com.gateway.fraud.model.BlackTextInfo;
import com.gateway.fraud.model.MerchantRefRuleProfileInfo;
import com.gateway.fraud.model.ParamInfo;
import com.gateway.fraud.model.ParamValueInfo;
import com.gateway.fraud.model.RuleProcessClass;
import com.gateway.fraud.model.RuleProfileInfo;
import com.gateway.fraud.model.RulesInfo;
import com.gateway.fraud.model.RulesRefProFileInfo;
import com.gateway.suspicious.mapper.SuspiciousManageDao;
import com.gateway.suspicious.model.SuspiciousOrderInfo;
import com.gateway.suspicious.model.SuspiciousOrderListInfo;
import com.gateway.suspicious.model.SuspiciousQueryInfo;
import com.gateway.suspicious.model.SuspiciousRuleInfo;
import com.gateway.suspicious.model.SuspiciousRuleOrderInfo;
import com.gateway.suspicious.model.SuspiciousTriggerRuleInfo;
import com.gateway.suspicious.model.SuspicioustTransInfo;
import com.gateway.transmgr.model.TransInfo;

@Service
public class SuspiciousManageServiceImpl implements SuspiciousManageService {
	
	@Autowired
	private SuspiciousManageDao suspiciousManageDao;
	
	/*public RedisTemplate<Serializable, Serializable> getJedisTemplate() {
		return jedisTemplate;
	}*/

	
	public SuspiciousManageDao getSuspiciousManageDao() {
		return suspiciousManageDao;
	}


	public void setSuspiciousManageDao(SuspiciousManageDao suspiciousManageDao) {
		this.suspiciousManageDao = suspiciousManageDao;
	}


	@Override
    public int countByCriteria(Criteria criteria) {
        return suspiciousManageDao.countByCriteria(criteria);
    }
	

	@Override
	public int addParam(ParamInfo info) {
		return suspiciousManageDao.addParam(info);
	}

	@Override
	public int updateParamInfo(ParamInfo info) {
		return suspiciousManageDao.updateParamInfo(info);
	}

	@Override
	public List<ParamInfo> queryParamInfoList(Criteria criteria) {
		return suspiciousManageDao.queryParamInfoList(criteria);
	}

	@Override
	@Transactional
	public ParamInfo queryParamInfoByParamId(String paramId) {
		ParamInfo info = suspiciousManageDao.queryParamInfoByParamId(paramId);
		if(null == info){
			return null;
		}else{
			//查询参数列表值
			List<ParamValueInfo> values = suspiciousManageDao.queryParamValuesInfoByParamId(paramId);
			info.setParamValuesList(values);
			String paramType = info.getType();
			if("String".equals(paramType)){
				List<String> list = suspiciousManageDao.queryParamListValuesByParamId(paramId);
				if(list.size() >0){
					info.setStringValue(list.get(0));
					info.setListValue(list);
				}
			}else if("List".equals(paramType)){
				List<String> list = suspiciousManageDao.queryParamListValuesByParamId(paramId);
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
						List<Map<String, String>> tableValueList = suspiciousManageDao.queryParamTableValues(tableName, colKey, colValue);
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
		return suspiciousManageDao.countParamsByCriteria(criteria);
	}

	@Override
	public int countRuleInfoByCriteria(Criteria criteria) {
		return suspiciousManageDao.countRuleInfoByCriteria(criteria);
	}

	@Override
	public List<RulesInfo> queryRulesList(Criteria criteria) {
		return suspiciousManageDao.queryRulesList(criteria);
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
		int i = suspiciousManageDao.addRuleInfo(info);
		return i;
	}
	
	@Override
	public RulesInfo queryRuleValueName(RulesInfo rulesInfo){
		return suspiciousManageDao.queryRuleValueName(rulesInfo);
	}

	@Override
	public int updateRuleInfo(RulesInfo info) {
		int i = suspiciousManageDao.updateRuleInfo(info);
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
			info = suspiciousManageDao.queryRulesInfoDetailByRuleId(ruleId);
		//}
		return info;
	}

	@Override
	public int delParamByParamId(String paramId) {
		return suspiciousManageDao.delParamByParamId(paramId);
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
		//首先删除久的值
		suspiciousManageDao.delParamValuesByParamId(oldinfo.getParamId());
		//插入更新的值
		if("String".equals(oldinfo.getType()) || "List".equals(oldinfo.getType())){
			for(String value:newInfo.getListValue()){
				ParamValueInfo paramValue = new ParamValueInfo();
				paramValue.setColKey(newInfo.getColKeyName());
				paramValue.setColValue(newInfo.getColValueName());
				paramValue.setParamId(newInfo.getParamId());
				paramValue.setTableName(newInfo.getTableName());
				paramValue.setValue(value);
				suspiciousManageDao.insertParamListValues(paramValue);
			}
		}else if("Table".equals(oldinfo.getType())){
			
			ParamValueInfo paramValue = new ParamValueInfo();
			paramValue.setColKey(newInfo.getColKeyName());
			paramValue.setColValue(newInfo.getColValueName());
			paramValue.setParamId(newInfo.getParamId());
			paramValue.setTableName(newInfo.getTableName());
			
			suspiciousManageDao.insertParamListValues(paramValue);
			
			BaseTableValueInfo baseTableValue = new BaseTableValueInfo();
			Map<String,String> tableValue = newInfo.getTableValue();
			baseTableValue.setColKey(tableValue.get(newInfo.getColKeyName()));
			baseTableValue.setColValue(tableValue.get(newInfo.getColValueName()));
			baseTableValue.setCreateBy(newInfo.getCreateBy());
			baseTableValue.setTableName(newInfo.getTableName());
			suspiciousManageDao.insertBaseTableInfo(baseTableValue);
			
		}else{
			throw new FraudExcetion("类型错误。");
		}
		newInfo = this.queryParamInfoByParamId(newInfo.getParamId());
		return true;
	}

	@Override
	public List<RuleProcessClass> queryProcessClassList(Criteria criteria) {
		return suspiciousManageDao.queryProcessClassList(criteria);
	}

	@Override
	public List<RuleProfileInfo> queryRuleProfileList(Criteria criteria) {
		return suspiciousManageDao.queryRuleProfileList(criteria);
	}

	@Override
	public PageInfo<RuleProfileInfo> queryPageRuleFInfo(Criteria criteria) {
		PageInfo<RuleProfileInfo> res = new PageInfo<RuleProfileInfo>(criteria.getPageNum(), criteria.getPageSize());
        int total=suspiciousManageDao.countRuleProfileInfo(criteria);
        res.setTotal(total);
        criteria.setOffset(res.getOffset());
        criteria.setRows( criteria.getPageSize());
        List<RuleProfileInfo> list = this.queryRuleProfileList(criteria);
        res.setData(list);
        return res;
	}

	@Override
	public RuleProfileInfo queryRuleProFileInfoByProfileId(String id) {
		return suspiciousManageDao.queryRuleProFileInfoByProfileId(id);
	}

	@Override
	public int addRuleProFileInfo(RuleProfileInfo info) {
		return suspiciousManageDao.addRuleProFileInfo(info);
	}

	@Override
	public int updateRuleProfileInfo(RuleProfileInfo info) {
		return suspiciousManageDao.updateRuleProfileInfo(info);
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
			List<RulesRefProFileInfo> tempList = suspiciousManageDao.queryRefRulesInfoByProFileIdAndRuleId(info.getProfileId(), info.getRuleId());
			if(null == tempList || tempList.size()==0){
				count +=suspiciousManageDao.addRulesToRuleProfile(info);
			}
		}
		return count;
	}

	@Override
	public List<RulesInfo> queryRefRuleProfileLits(Criteria criteria) {
		return suspiciousManageDao.queryRefRuleProfileLits(criteria);
	}

	@Override
	public PageInfo<RulesInfo> queryPageRefRuleProfileLits(Criteria criteria) {
		PageInfo<RulesInfo> res = new PageInfo<RulesInfo>(criteria.getPageNum(), criteria.getPageSize());
        int total=suspiciousManageDao.countRefRuleProfileList(criteria);
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
			count += suspiciousManageDao.delRulesFromRulesProFile(profileId, ruleId);
		}
		return count ;
	}

	@Override
	public List<MerchantRefRuleProfileInfo> queryAccountRefProfileList(
			Criteria criteria) {
		return suspiciousManageDao.queryAccountRefProfileList(criteria);
	}

	@Override
	public PageInfo<MerchantRefRuleProfileInfo> queryPageAccountRefProfileList(
			Criteria criteria) {
		PageInfo<MerchantRefRuleProfileInfo> res = new PageInfo<MerchantRefRuleProfileInfo>(criteria.getPageNum(), criteria.getPageSize());
        int total=suspiciousManageDao.countAccountRefProfile(criteria);
        res.setTotal(total);
        criteria.setOffset(res.getOffset());
        criteria.setRows( criteria.getPageSize());
        List<MerchantRefRuleProfileInfo> list = this.queryAccountRefProfileList(criteria);
        res.setData(list);
        return res;
	}

	@Override
	public int addProfileToAccount(MerchantRefRuleProfileInfo info) {
		return suspiciousManageDao.addProfileToAccount(info);
	}

	@Override
	public List<MerchantRefRuleProfileInfo> queryProfileInfoByMerNoAndTerNo(
			String merNo, String terNo,String profileId) {
		return suspiciousManageDao.queryProfileInfoByMerNoAndTerNo(merNo, terNo,profileId);
	}

	@Override
	public MerchantRefRuleProfileInfo queryProfileInfoById(String id) {
		return suspiciousManageDao.queryProfileInfoById(id);
	}

	@Override
	public int updateAccountRefProfileInfoById(MerchantRefRuleProfileInfo info) {
		return suspiciousManageDao.updateAccountRefProfileInfoById(info);
	}

	@Override
	public int delAccountFromList(String id) {
		return suspiciousManageDao.delAccountFromList(id);
	}

	@Override
	public RuleProcessClass queryRuleProcessClassByClassId(String classId) {
		return suspiciousManageDao.queryRuleProcessClassByClassId(classId);
	}


	@Override
	public List<BlackTextInfo> queryBlackInfoList(Criteria criteria) {
		return suspiciousManageDao.queryBlackInfoList(criteria);
	}

	@Override
	public PageInfo<BlackTextInfo> queryPageBlackInfoList(Criteria criteria) {
		PageInfo<BlackTextInfo> res = new PageInfo<BlackTextInfo>(criteria.getPageNum(), criteria.getPageSize());
        int total=suspiciousManageDao.countBlackInfoList(criteria);
        res.setTotal(total);
        criteria.setOffset(res.getOffset());
        criteria.setRows( criteria.getPageSize());
        List<BlackTextInfo> list = this.queryBlackInfoList(criteria);
        res.setData(list);
        return res;
	}

	@Override
	public List<BinInfo> queryBinList(Criteria criteria) {
		return suspiciousManageDao.queryBinList(criteria);
	}

	@Override
	public PageInfo<BinInfo> queryPageBinList(Criteria criteria) {
		PageInfo<BinInfo> res = new PageInfo<BinInfo>(criteria.getPageNum(), criteria.getPageSize());
        int total=suspiciousManageDao.countBinInfoList(criteria);
        res.setTotal(total);
        criteria.setOffset(res.getOffset());
        criteria.setRows( criteria.getPageSize());
        List<BinInfo> list = this.queryBinList(criteria);
        res.setData(list);
        return res;
	}

	@Override
	public int addBlackTextInfo(BlackTextInfo info) throws FraudExcetion {
		BlackTextInfo temp =  suspiciousManageDao.queryBlackTextInfoByValueAndType(info.getBlackText(), info.getBlackType());
		if(null != temp){
			throw new FraudExcetion("该条信息已经存在，不需要在添加。");
		}
		return suspiciousManageDao.addBlackTextInfo(info);
	}

	@Override
	public int delBlackTextById(String blackId) {
		return suspiciousManageDao.delBlackTextById(blackId);
	}

	@Override
	public int addBinInfo(BinInfo info) {
		return suspiciousManageDao.addBinInfo(info);
	}

	@Override
	public int deleteBinInfoById(String id) {
		return suspiciousManageDao.deleteBinInfoById(id);
	}

	@Override
	public BlackTextInfo queryBlackTextInfoByValueAndType(String value,
			String type) {
		return suspiciousManageDao.queryBlackTextInfoByValueAndType(value, type);
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


	@Override
	public PageInfo<SuspiciousOrderInfo> querySuspiciousOrderInfo(
			Criteria criteria) {
		PageInfo<SuspiciousOrderInfo> page = new PageInfo<SuspiciousOrderInfo>(criteria.getPageNum(), criteria.getPageSize());
		int total = suspiciousManageDao.querySuspiciousOrderCount(criteria);
		page.setTotal(total);
		RowBounds rb = new RowBounds(page.getOffset(), page.getPageSize());
		List<SuspiciousOrderInfo> list = suspiciousManageDao.querySuspiciousOrderInfo(criteria, rb);
		page.setData(list);
		return page;
	}
	

	@Override
	public SuspiciousRuleOrderInfo querySuspiciousRuleOrderInfo(SuspiciousQueryInfo info, List<String> tradeNos) throws IOException, Exception {
		SuspiciousRuleOrderInfo order = new SuspiciousRuleOrderInfo();
		List<SuspicioustTransInfo> list = new ArrayList<SuspicioustTransInfo>();
		if(tradeNos!=null && tradeNos.size()>0){
			for(String tradeNo : tradeNos){
				SuspicioustTransInfo susInfo = suspiciousManageDao.querySuspiciousRelTradeNo(tradeNo);
				if(susInfo!=null){
					if(susInfo.getCheckNo()!=null && !("".equals(susInfo.getCheckNo())) && susInfo.getLast()!=null && !("".equals(susInfo.getLast()))){
						susInfo.setSixAndFourCardNo(Funcs.decrypt(susInfo.getCheckNo())+"***"+Funcs.decrypt(susInfo.getLast()));
					}
				}
				list.add(susInfo);
			}
		}
		order.setList(list);
//		int count = suspiciousManageDao.querySuspiciousRelRradeNoCount(info.getRuleIds(), info.getMerNo(), info.getTerNo(), info.getSusType(), info.getCreateDate());
		order.setCount(tradeNos.size());
		return order;
	}
	
	
	@Override
	public List<String> querySuspiciousRuleTransInfo(SuspiciousQueryInfo info){
		TransInfo trans = suspiciousManageDao.queryTransListByTradeNo(info.getTradeNo());
		trans.setIPAddress(trans.getIpAddress());
		List<String> sigleAmount = suspiciousManageDao.querySuspiciousSingleAmountInfoList(info.getRuleIds());
		return suspiciousManageDao.querySuspiciousRuleTransInfo(info, trans, sigleAmount);
	}


	@Override
	public SuspiciousTriggerRuleInfo querySuspiciousRuleInfo(String susType, String tradeNo, String createDate) {
		SuspiciousTriggerRuleInfo rule = new SuspiciousTriggerRuleInfo();
		List<SuspiciousRuleInfo> list = suspiciousManageDao.querySuspiciousOrderRuleInfo(susType, tradeNo, createDate);
		rule.setList(list);
		int count = suspiciousManageDao.querySuspiciousOrderRuleInfoCount(susType, tradeNo, createDate);
		rule.setCount(count);
		return rule;
	}


	@Override
	public List<SuspiciousOrderListInfo> querySuspiciousOrderListInfo(
			Criteria criteria) {
		List<SuspiciousOrderListInfo> list = suspiciousManageDao.querySuspiciousOrderListInfo(criteria);
		if(list!=null && list.size()>0){
			for(SuspiciousOrderListInfo order : list){
				if(order.getRuleIdList()!=null && !("".equals(order.getRuleIdList()))){
					String tradeNoList = suspiciousManageDao.querySuspiciousOrderTradeNoInfo(order.getRuleIdList(), order.getCreateDate(), order.getSusType(), order.getMerNo(), order.getTerNo());
					order.setTradeList(tradeNoList);
					if(order.getTradeNo()!=null && order.getTradeNo()!=""){
						List<SuspiciousRuleInfo> list1 = suspiciousManageDao.querySuspiciousOrderRuleInfo(order.getSusType(), order.getTradeNo(), order.getCreateDate());
						order.setRuleIdList("");
						order.setRuleNameList("");
						for(SuspiciousRuleInfo si:list1){
							order.setRuleIdList(order.getRuleIdList()+" "+si.getRuleId());
							order.setRuleNameList(order.getRuleNameList()+" "+si.getRuleName());
						}
					}
				}
			}
		}
		return list;
	}
	
	@Override
	public TransInfo queryTransInfoByTradeNo(String tradeNo) {
		TransInfo info=suspiciousManageDao.queryTransListByTradeNo(tradeNo);
		info.setGoodsInfos(suspiciousManageDao.queryGoodsInfoByTradeNo(tradeNo));
		return info; 
	}


	@Override
	public List<String> querySuspiciousRuleTradeNoInfo(SuspiciousQueryInfo info, TransInfo trans) {
		List<String> sigleAmount = suspiciousManageDao.querySuspiciousSingleAmountInfoList(info.getRuleIds());
		return suspiciousManageDao.querySuspiciousRelTradeNoList(info, trans, sigleAmount);
	}

}
