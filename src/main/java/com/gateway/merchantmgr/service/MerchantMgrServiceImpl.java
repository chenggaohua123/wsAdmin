package com.gateway.merchantmgr.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.APIException;
import com.gateway.common.excetion.ServiceException;
import com.gateway.currency.mapper.CurrencyMapper;
import com.gateway.loginmgr.model.UserInfo;
import com.gateway.merchantmgr.mapper.MerchantMgrDao;
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
import com.gateway.sysmgr.mapper.SysMgrDao;

@Service
public class MerchantMgrServiceImpl implements MerchantMgrService{
	@Autowired
	private MerchantMgrDao merchantMgrDao;
	@Autowired
	private SysMgrDao sysMgrDao;
	@Autowired
	private CurrencyMapper currencyMapper;
	
	@Override
	public PageInfo<MerchantInfo> getListMerchant(Criteria criteria) {
		PageInfo<MerchantInfo> page = new PageInfo<MerchantInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(merchantMgrDao.countMerchant(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<MerchantInfo> list = merchantMgrDao.getListMerchant(criteria, rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public PageInfo<MerchantTerInfo> getMerchantTerList(Criteria criteria) {
		PageInfo<MerchantTerInfo> page = new PageInfo<MerchantTerInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(merchantMgrDao.countMerchantTerList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<MerchantTerInfo> list = merchantMgrDao.getMerchantTerList(criteria, rb);
		page.setData(list);
		return page;
	}
	@Override
	public MerchantInfo queryMerchantInfoById(int id) {
		MerchantInfo info= merchantMgrDao.queryMerchantInfoById(id);
		if(null!=info.getPhoneNo()){
			info.setList(sysMgrDao.queryGwPicInfo(info.getPhoneNo()));
		}
		return info;
	
	}
	
	@Override
	public MerchantInfo queryMerchantInfo(String phoneNo, String picType) {
		MerchantInfo merchantInfo=new MerchantInfo();
		if(null!=phoneNo){
			merchantInfo.setPicInfo(sysMgrDao.ajaxGwPicInfo(phoneNo, picType));
			
		}
		return merchantInfo;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int updateMerchantInfo(MerchantInfo merchantInfo) throws ServiceException {
		MerchantInfo temp = merchantMgrDao.queryMerchantInfoById(merchantInfo.getId());
		if(null == temp){
			throw new ServiceException("该商户不存在");
		}
		temp.setUpdatePerson(merchantInfo.getUpdatePerson());
		int i = merchantMgrDao.addMerchantInfoLog(temp);//添加历史记录
		if(i>0){
			merchantInfo.setReState("0");
			return merchantMgrDao.updateMerchantInfo(merchantInfo);
		}else{
			return i;
		}
	}
	
	@Override
	public List<MerchantInfo> queryMerchantLogList(String merNo) {
		List<MerchantInfo> list = merchantMgrDao.queryMerchantLogList(merNo);
		return list;
	}
	
	@Override
	@Transactional
	public int addTerInfo(MerchantTerInfo terInfo) {
		return merchantMgrDao.addTerInfo(terInfo);
		
	}
	@Override
	public MerchantInfo queryMerchantInfoByMerNo(String merNo) {
		return merchantMgrDao.queryMerchantInfoByMerNo(merNo);
	}
	@Override
	public MerchantTerInfo queryTerInfoById(int id) {
		MerchantTerInfo info= merchantMgrDao.queryTerInfoById(id);
		if(null!=info.getPhoneNo()){
			info.setList(sysMgrDao.queryGwPicInfo(info.getPhoneNo()));
		}
		return info;
	
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int updateMerchantTerInfo(MerchantTerInfo terInfo) throws ServiceException {
		MerchantTerInfo temp = merchantMgrDao.queryTerInfoById(terInfo.getId());
		if(null == temp){
			throw new ServiceException("该终端不存在");
		}
		temp.setUpdateBy(terInfo.getUpdateBy());
		int i = merchantMgrDao.addTerInfoHis(temp);
		if(i>0){
			return merchantMgrDao.updateMerchantTerInfo(terInfo);
		}else{
			return i;
		}
	}
	@Override
	public MerchantTerInfo queryTerInfoByMerNoAndTerNo(String merNo,String terNo) {
		return merchantMgrDao.queryTerInfoByMerNoAndTerNo(merNo, terNo);
	}
	@Override
	public int addTerInfoHis(MerchantTerInfo terInfo) {
		return merchantMgrDao.addTerInfoHis(terInfo);
	}
	@Override
	public int addMerchantConfig(MerchantConfig config) {
		return merchantMgrDao.addMerchantConfig(config);
	}
	@Override
	public List<MerchantConfig> queryMerchantConfigInfo(MerchantConfig config) {
		return merchantMgrDao.queryMerchantConfigInfo(config);
	}
	@Override
	public List<MerchantTerInfo> queryTerInfoByMerNoAndTerNoLog(String merNo,
			String terNo) {
		return merchantMgrDao.queryTerInfoByMerNoAndTerNoLog(merNo, terNo);
	}
	@Override
	public PageInfo<MerchantRelCurrencyInfo> getMerchnatRelCurrencyList(Criteria criteria) {
		PageInfo<MerchantRelCurrencyInfo> page = new PageInfo<MerchantRelCurrencyInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(merchantMgrDao.countMerchnatRelCurrencyList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<MerchantRelCurrencyInfo> list = merchantMgrDao.getMerchnatRelCurrencyList(criteria, rb);
		for (MerchantRelCurrencyInfo merchantRelCurrencyInfo : list) {
			String currencyDayAmountIds = merchantRelCurrencyInfo.getCurrencyDayAmountIds();
			if(null==currencyDayAmountIds){
				continue;
			}
			String currencyDayAmountNames = currencyMapper.getCurrencyDayAmountNamesByIds(currencyDayAmountIds);
			merchantRelCurrencyInfo.setCurrencyDayAmountNames(currencyDayAmountNames);
		}
		page.setData(list);
		return page;
	}
	@Override
	public int addCurrencyToMerchnat(MerchantRelCurrencyInfo info) throws ServiceException {
		MerchantInfo temp = merchantMgrDao.queryMerchantInfoByMerNo(info.getMerNo());
		if(null == temp){
			throw new ServiceException("该商户不存在。");
		}
		if(!"0".equals(info.getTerNo())){
			MerchantTerInfo terInfo =  merchantMgrDao.queryTerInfoByMerNoAndTerNo(info.getMerNo(), info.getTerNo());
			if(null == terInfo){
				throw new ServiceException("该终端不存在。");
			}
		}
		checkmerNoCurrencyInfo(info);
		return merchantMgrDao.addCurrencyToMerchnat(info);
	}
	
	/** 判断商户终端下卡种是否重复添加通道 */
	private void checkmerNoCurrencyInfo(MerchantRelCurrencyInfo info)throws ServiceException{
		int i = merchantMgrDao.checkmerNoCurrencyInfo(info);
		if(0 < i){
			throw new ServiceException("请勿重复绑定通道");
		}
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int updateCurrencyToMerchnat(MerchantRelCurrencyInfo info)throws ServiceException {
		checkmerNoCurrencyInfo(info);
		MerchantRelCurrencyInfo temp = merchantMgrDao.queryMerchantRelCurrencyById(info.getId());
		if(null == temp){
			throw new ServiceException("该记录不存在。");
		}
		info.setCreateDate(temp.getCreateDate());
		info.setCreateBy(temp.getCreateBy());
		temp.setUpdateBy(info.getUpdateBy());
		//保存历史记录
		merchantMgrDao.addCurrencyToMerchantLog(temp);
		//更新当前记录
		return merchantMgrDao.updateCurrencyToMerchnat(info);
	}
	@Override
	public MerchantRelCurrencyInfo queryMerchantRelCurrencyById(int id) {
		return merchantMgrDao.queryMerchantRelCurrencyById(id);
	}
	@Override
	public List<MerchantRelCurrencyInfo> getCurrencyToMerchnatHisList(int id) throws ServiceException {
		MerchantRelCurrencyInfo temp = merchantMgrDao.queryMerchantRelCurrencyById(id);
		if(null == temp){
			throw new ServiceException("该记录不存在。");
		}
		return merchantMgrDao.getCurrencyToMerchnatHisList(temp.getMerNo(), temp.getTerNo(), temp.getCardType());
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int batchUpdateCurrencyToMerchant(BatchUpdateCurrencyRelMerchantInfo info) throws ServiceException {
		if(null == info.getCurrencyId()){
			throw new ServiceException("修改的目标通道不能为空。");
		}
		
		if(null == info.getOriCurrencyId()){
			throw new ServiceException("原通道不能为空。");
		}
		if(info.getCurrencyId().equals(info.getOriCurrencyId())){
			throw new ServiceException("原通道和目标通道不能一样。");
		}
		if(null != info.getMerNo() && !"0".equals(info.getMerNo())){
			String [] merNos = info.getMerNo().split(",");
			info.setMerNos(merNos);
		}
		if(null != info.getTerNo() && !"0".equals(info.getTerNo())){
			String [] terNos = info.getTerNo().split(",");
			info.setTerNos(terNos);
		}
		//保存历史记录
		merchantMgrDao.batchAddCurrencyToMerchnatLog(info);
		return merchantMgrDao.batchUpdateCurrencyToMerchant(info);
	}
	@Override
	public PageInfo<AgentInfo> getAgentList(Criteria criteria) {
		PageInfo<AgentInfo> page = new PageInfo<AgentInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(merchantMgrDao.countAgentList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<AgentInfo> list = merchantMgrDao.getAgentList(criteria, rb);
		page.setData(list);
		return page;
	}
	
	
	
	public int addAgentInfo(AgentInfo agentInfo) {
		return merchantMgrDao.addAgentInfo(agentInfo);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int updateAgentInfo(AgentInfo agentInfo) throws ServiceException {
		AgentInfo temp = merchantMgrDao.queryAgentById(agentInfo.getId());
		if(null == temp){
			throw new ServiceException("该代理商户不存在。");
		}
		temp.setUcreateBy(agentInfo.getCreateBy());
		temp.setId(null);
		
		int i = merchantMgrDao.addAgentLogInfo(temp);
		if(i>0){
			return merchantMgrDao.updateAgentInfo(agentInfo);
		}else{
			return i;
		}
	}
	
	@Override
	public AgentInfo queryAgentById(int id) {
		return merchantMgrDao.queryAgentById(id);
	}
	
	@Override
	public List<AgentInfo> queryAgentLog(String agentNo) {
		return merchantMgrDao.queryAgentLog(agentNo);
	}
	@Override
	public int updateMerchantConfig(MerchantConfig config) {
		return merchantMgrDao.updateMerchantConfig(config);
	}
	
	@Override
	public int deleteMerchantConfig(int id) {
		return merchantMgrDao.deleteMerchantConfig(id);
	}
	@Override
	public MerchantConfig queryMerchantConfigById(int id) {
		return merchantMgrDao.queryMerchantConfigById(id);
	}
	@Override
	public int addMerchantRelAgent(String merNo,String terNo, String createBy) {
		return merchantMgrDao.addMerchantRelAgent(merNo,terNo, createBy);
	}
	@Override
	public GwAgentRelMerchant queryAgentRelMerchant(String merNo,String terNo) {
		return merchantMgrDao.queryAgentRelMerchant(merNo,terNo);
	}
	@Override
	public List<MerchantInfo> exportMerchant(Criteria criteria) {
		return merchantMgrDao.getListMerchant(criteria);
	}
	@Override
	public int addUserRefMerchant(GwUserRelMerchantInfo info) {
		return merchantMgrDao.addUserRefMerchant(info);
	}
	@Override
	public GwUserRelMerchantInfo queryUserRelMerchant(String merNo) {
		return merchantMgrDao.queryUserRelMerchant(merNo);
	}
	@Override
	public int updateChecnkTerNo(MerchantTerInfo terInfo) {
		terInfo.setEnabled(1);
		return merchantMgrDao.updateChecnkTerNo(terInfo);
	}
	@Override
	public PageInfo<TerSnRelAgentInfo> queryTerSNRelAgentInfoList(Criteria criteria) {
		PageInfo<TerSnRelAgentInfo> page = new PageInfo<TerSnRelAgentInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(merchantMgrDao.countTerSNRelAgentInfoList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<TerSnRelAgentInfo> list = merchantMgrDao.queryTerSNRelAgentInfoList(criteria, rb);
		page.setData(list);
		return page;
	}
	
	@Transactional()
	public int addTerSnRelAgent(TerSnRelAgentInfo agentInfo) {
		String[] sn=agentInfo.getSNNo().split(",");
		
		List<TerSnRelAgentInfo> list=merchantMgrDao.queryTersnRelAgentList(sn);
		int k = 0;
		for(int i=0;i<sn.length;i++){
			if(null != list && list.size() > 0){
				for(TerSnRelAgentInfo info :list){
					if(!info.getSNNo().equalsIgnoreCase(sn[i])){
						agentInfo.setSNNo(sn[i]);
						agentInfo.setState("2");
						int m=merchantMgrDao.addTerSnRelAgent(agentInfo);
						if(m>0){
							k+=merchantMgrDao.updateKeyMaste(sn[i],"1");
						}
					}
				}
			}else{
				agentInfo.setSNNo(sn[i]);
				agentInfo.setState("2");
				int m=merchantMgrDao.addTerSnRelAgent(agentInfo);
				if(m>0){
					k+=merchantMgrDao.updateKeyMaste(sn[i],"1");
				}
			}
		}
		return k;
	}
	@Override
	public List<MerchantTerInfo> exportMerchantTerList(Criteria criteria) {
		return merchantMgrDao.getMerchantTerList(criteria);
	}
	@Override
	public List<TerSnRelAgentInfo> exportTerSNRelAgentInfoList(Criteria criteria) {
		return merchantMgrDao.queryTerSNRelAgentInfoList(criteria);
	}
	@Override
	public PageInfo<GwTernoLmitInfo> terLimitInfoList(Criteria criteria) {
		PageInfo<GwTernoLmitInfo> page = new PageInfo<GwTernoLmitInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(merchantMgrDao.countterLimitInfoList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<GwTernoLmitInfo> list = merchantMgrDao.terLimitInfoList(criteria, rb);
		page.setData(list);
		return page;
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int addTerLimit(GwTernoLmitInfo lmitInfo) {
		int count=0;
		count+=merchantMgrDao.addTerLimit(lmitInfo);
		if(lmitInfo.getMonthCountLimit()>0){
			count+=merchantMgrDao.updateTerLimitForMonthCountLimitByMerNo(lmitInfo.getMonthCountLimit(),lmitInfo.getMerNo(),lmitInfo.getCardType());
		}
		return count;
	}
	@Override
	public int deleteTerLimitCountInfoById(String id) {
		return merchantMgrDao.deleteTerLimitCountInfoById(id);
	}
	@Override
	public int queryTerLimit(GwTernoLmitInfo lmitInfo){
		return merchantMgrDao.queryTerLimit(lmitInfo);
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int updateTerNoLimit(GwTernoLmitInfo lmitInfo) {
		int count=0;
		count+=merchantMgrDao.updateTerNoLimit(lmitInfo);
		if(lmitInfo.getMonthCountLimit()>0){
			count+=merchantMgrDao.updateTerLimitForMonthCountLimitByMerNo(lmitInfo.getMonthCountLimit(),lmitInfo.getMerNo(),lmitInfo.getCardType());
		}
		return count;
	}
	@Override
	public GwTernoLmitInfo queryTerLimitRateInfo(GwTernoLmitInfo info) {
		return merchantMgrDao.queryTerLimitRateInfo(info);
	}
	@Override
	public GwTernoLmitInfo queryTerNoLimitById(int id) {
		return merchantMgrDao.queryTerNoLimitById(id);
	}
	
	@Override
	@Transactional
	public int updateTerSnRelRecycle(TerSnRelAgentInfo agentInfo) {
		int i=0;
		String[] ids=agentInfo.getIds().split(",");
		List<TerSnRelAgentInfo> list=merchantMgrDao.queryTersnRelAgentById(ids);
		for(TerSnRelAgentInfo ter:list){
			if("6".equals(ter.getState())){
			   i+=merchantMgrDao.updateKeyMaste(ter.getSNNo(),"0");	
			   if(i>0){
				  i+=merchantMgrDao.deleteTerSnRelRecycle(ter);
			    }
			}
	    }
	   return i;
	}
	
	@Override
	public AgentInfo queryAgentByAgentNo(String agentNo) {
		return merchantMgrDao.queryAgentByAgentNo(agentNo);
	}
	@Override
	public int updateMerchantInfoReState(MerchantInfo merchantInfo) {
		MerchantInfo temp = merchantMgrDao.queryMerchantInfoById(merchantInfo.getId());
		if(null == temp){
			return -1;
		}
		temp.setUpdatePerson(merchantInfo.getUpdatePerson());
		int i = merchantMgrDao.addMerchantInfoLog(temp);//添加历史记录
		if(i>0){
			return merchantMgrDao.updateMerchantInfoReState(merchantInfo);
		}else{
			return i;
		}
	}
	
	@Override
	public MerchantTerInfo queryMerchantTerNoById(int id) {
		return merchantMgrDao.queryMerchantTerNoById(id);
	}
	@Override
	public PageInfo<MerchantSettleCycle> getMerchantSettleCycleList(
			Criteria criteria) {
		PageInfo<MerchantSettleCycle> page = new PageInfo<MerchantSettleCycle>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(merchantMgrDao.countMerchantSettleCycle(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<MerchantSettleCycle> list = merchantMgrDao.getListMerchantSettleCycle(criteria, rb);
		page.setData(list);
		return page;
	}
	@Override
	public int addMerchantSettleCycle(MerchantSettleCycle msc) {
		return merchantMgrDao.addMerchantSettleCycle(msc);
	}
	
	@Override
	public MerchantSettleCycle queryMerchantSettleCycleByID(String id) {
		return merchantMgrDao.queryMerchantSettleCycleByID(id);
	}
	
	@Override
	public int updateMerchantSettleCycle(MerchantSettleCycle msc) {
		return merchantMgrDao.updateMerchantSettleCycle(msc);
	}
	
	@Override
	public PageInfo<MerchantWebsite> getListMerchantWebsite(Criteria criteria) {
		PageInfo<MerchantWebsite> page = new PageInfo<MerchantWebsite>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(merchantMgrDao.countMerchantWebsite(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<MerchantWebsite> list = merchantMgrDao.getListMerchantWebsite(criteria, rb);
		page.setData(list);
		return page;
	}
	@Override
	public int queryMerchantInfoByMerNo(String merNo, String terNo) {
		return merchantMgrDao.queryMerchantInfoByMerNoAndTerNo(merNo,terNo);
	}
	
	@Override
	public int updateMerchantWebsite(MerchantWebsite merchantWebsite) {
		merchantWebsite.setAppDate(getNewDate());
		insertOperationLog(merchantWebsite, merchantWebsite.getAppBy(), "check");
		return merchantMgrDao.updateMerchantWebsite(merchantWebsite);
	}
	@Override
	public int addMerchantWebsite(MerchantWebsite merchantWebsite) {
		merchantWebsite.setCreateDate(getNewDate());
		merchantWebsite.setStatus("1");
		insertOperationLog(merchantWebsite,merchantWebsite.getCreateBy(),"add");
		return merchantMgrDao.addMerchantWebsite(merchantWebsite);
	}
	
	private static String getNewDate(){
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");   
		return sDateFormat.format(new java.util.Date());
	}
	
	@Override
	public MerchantWebsite queryMerchantWebsiteById(String id) {
		return merchantMgrDao.queryMerchantWebsiteById(id);
	}
	@Override
	public String queryMaxTerNo(String merNo) {
		return merchantMgrDao.queryMaxTerNo(merNo);
	}
	@Override
	public int updateMerchantTerNoInfo(MerchantTerInfo terInfo) throws APIException{
		MerchantTerInfo temp = merchantMgrDao.queryTerInfoById(terInfo.getId());
		if(null == temp){
			throw new APIException("该终端不存在");
		}
		temp.setUpdateBy(terInfo.getUpdateBy());
		int i = merchantMgrDao.addTerInfoHis(temp);
		if(0 < i){
			return merchantMgrDao.updateMerchantTerNoInfo(terInfo);
		}
		return 0;
	}
	@Override
	public int queryMerchantWebsite(MerchantWebsite merchantWebsite) {
		return merchantMgrDao.queryMerchantWebsite(merchantWebsite);
	}
	@Override
	public int updateMerchantWebsiteInfo(MerchantWebsite merchantWebsite) {
		MerchantWebsite info = queryMerchantWebsiteById(merchantWebsite.getId());
		merchantWebsite.setCreateBy(info.getCreateBy());
		merchantWebsite.setCreateDate(info.getCreateDate());
		merchantWebsite.setStatus(info.getStatus());
		insertOperationLog(merchantWebsite, merchantWebsite.getOperationBy(), "update");
		return merchantMgrDao.updateMerchantWebsiteInfo(merchantWebsite);
	}
	@Override
	public int deleteWebsite(String[] ids) {
		int i = 0;
		for(String id:ids){
			i = i + merchantMgrDao.deleteWebsite(id);
		}
		return i;
	}
	
	/** 保存操作日志 */
	@Override
	public int insertOperationLog(MerchantWebsite merchantWebsite,String operationBy,String type){
		merchantWebsite.setOperationBy(operationBy);
		merchantWebsite.setType(type);
		return merchantMgrDao.insertOperationLog(merchantWebsite);
	}
	@Override
	public PageInfo<MerchantWebsite> queryWebsiteLogList(Criteria criteria) {
		PageInfo<MerchantWebsite> page = new PageInfo<MerchantWebsite>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(merchantMgrDao.countWebsiteLogList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<MerchantWebsite> list = merchantMgrDao.queryWebsiteLogList(criteria, rb);
		page.setData(list);
		return page;
	}
	
	
	@Override
	public List<TransSettingInfo> queryTransSettingInfo(String merNo) {
		return merchantMgrDao.queryTransSettingInfo(merNo);
	}

	@Override
	public List<MerchantInfo> exportListMerchant(Criteria criteria) {
		return  merchantMgrDao.getListMerchant(criteria);
	}

	@Override
	public PageInfo<RegCodeInfo> queryRegCodeList(Criteria criteria) {
		PageInfo<RegCodeInfo> page = new PageInfo<RegCodeInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(merchantMgrDao.countRegCodeList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<RegCodeInfo> list = merchantMgrDao.queryRegCodeList(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public int excuAddRegCode(RegCodeInfo info) {
		return merchantMgrDao.excuAddRegCode(info);
	}
	
	/**
	 * 实现：终端号支付页面绑定
	 * @param vo 查询条件
	 * @return 终端号绑定的支付页面列表
	 * @date 2015-09-12
	 * @author YWP
	 */
	public PageInfo<GwMerchantPaymentPage> searchMerchantPaymentPage(Criteria vo) {
		PageInfo<GwMerchantPaymentPage> page = new PageInfo<GwMerchantPaymentPage>(vo.getPageNum(), vo.getPageSize());
		page.setTotal(merchantMgrDao.countMerchantPaymentPage(vo));
		RowBounds rb = new RowBounds(page.getOffset(), vo.getPageSize());
		List<GwMerchantPaymentPage> list = merchantMgrDao.searchMerchantPaymentPage(vo, rb);
		page.setData(list);
		return page;
	}
	/**
	 * 实现：根据ID查询终端号绑定的支付页面
	 * @param vo 查询条件
	 * @return 终端号绑定的支付页面
	 * @date 2015-09-12
	 * @author YWP
	 */
	public GwMerchantPaymentPage searchMerchantPaymentPageById(GwMerchantPaymentPage vo) {
		return merchantMgrDao.searchMerchantPaymentPageById(vo);
	}
	/**
	 * 实现：保存终端号绑定的支付页面
	 * @param vo 绑定支付页面信息
	 * @param user 当前登录用户
	 * @return 保存结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	public int saveMerchantPaymentPage(GwMerchantPaymentPage vo, UserInfo user) {
		int a = 0;
		if(vo.getId()>0) {
			vo.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
			vo.setLastUpdatePerson(user.getUserName());
			a = merchantMgrDao.updateMerchantPaymentPage(vo);
		} else {
			vo.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			vo.setCreatedPerson(user.getUserName());
			vo.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
			vo.setLastUpdatePerson(user.getUserName());
			try {
				a = merchantMgrDao.addMerchantPaymentPage(vo);
			} catch (Exception e) {
				return a;
			}
		
		}
		return a;
	}
	/**
	 * 实现：删除终端号绑定的支付页面
	 * @param vo 绑定支付页面信息
	 * @return 删除结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	public int deleteMerchantPaymentPage(GwMerchantPaymentPage vo) {
		return merchantMgrDao.deleteMerchantPaymentPage(vo);
	}

	@Override
	public List<GwTernoLmitInfo> terLimitInfoListInfo(Criteria criteria) {
		return merchantMgrDao.terLimitInfoList(criteria);
	}

	@Override
	public int addTerLimitLog(GwTernoLmitInfo lmitInfo) {
		return merchantMgrDao.addTerLimitLog(lmitInfo);
	}

	@Override
	public List<GwTernoLmitInfo> queryTerLimitLog(String id) {
		return merchantMgrDao.queryTerLimitLog(id);
	}

	@Override
	public PageInfo<MccInfo> getMccInfo(Criteria criteria) {
		
		PageInfo<MccInfo> page = new PageInfo<MccInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(merchantMgrDao.countMccInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		merchantMgrDao.updateMccSearchCountBySearchContants(criteria);
		List<MccInfo> list = merchantMgrDao.getMccInfo(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public PageInfo<BrandProductInfo> getBrandInfo(Criteria criteria) {
		PageInfo<BrandProductInfo> page = new PageInfo<BrandProductInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(merchantMgrDao.countBrandInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		merchantMgrDao.updateBrandSearchCountBySearchContants(criteria);
		List<BrandProductInfo> list = merchantMgrDao.getBrandInfo(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public PageInfo<BrandProductInfo> getProductInfo(Criteria criteria) {
		PageInfo<BrandProductInfo> page = new PageInfo<BrandProductInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(merchantMgrDao.countProductInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		merchantMgrDao.updateBrandProductSearchCountBySearchContants(criteria);
		List<BrandProductInfo> list = merchantMgrDao.getProductInfo(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public String querySourceCurrencyCode() {
		List<MerchantTerInfo> list = merchantMgrDao.querySourceCurrencyCode();
		StringBuffer sb = new StringBuffer();
		for(MerchantTerInfo li:list){
			sb.append(li.getSourceCurrencyCode() + ",");
		}
		if(!StringUtils.isEmpty(sb)){
			return sb.toString().substring(0, sb.length()-1);
		}
		return "";
	}

	@Override
	public int deleteRegCodeInfo(String[] ids) {
		int i = 0;
		for(String id:ids){
			int y = merchantMgrDao.deleteRegCodeInfo(id);
			if(0<y){
				i++;
			}
		}
		return i;
	}
	@Override
	public List<MerchantRelCurrencyInfo> getMerchnatRelCurrencyForExport(
			Criteria criteria) {
		return merchantMgrDao.getMerchnatRelCurrencyList(criteria);
	}
	/**
	 *代理商账户管理
	 * 
	 */
	@Override
	public AgentUserInfo queryAgentUserInfo(AgentUserInfo user) {
		return merchantMgrDao.searchAgentUserInfo(user);
	}
	/**
	 *保存修改代理商账户管理
	 * 
	 */
	@Override
	@Transactional
	public int updateAgentUserInfo(AgentUserInfo user) {
		return merchantMgrDao.updateAgentUserInfo(user);
	}
	/**
	 *更具商户的编号获取商户的终端号信息
	 * 
	 */
	@Override
	public List<MerchantTerInfo> getAgentMerchantTerList(MerchantTerInfo mer) {
		return merchantMgrDao.getAgentMerchantTerList(mer);
	}
	/**
	 *保存代理商分配的用户信息
	 * 
	 */
	@Override
	@Transactional
	public int saveAgentMerchantInfo(GWAgentMerchantInfo info) {
		return merchantMgrDao.saveAgentMerchantInfo(info);
	}
	/**
	 *查找现有的代理商户
	 * 
	 */
	@Override
	public List<GWAgentMerchantInfo> queryAgentMerchantInfo(
			GWAgentMerchantInfo mer) {
		return merchantMgrDao.queryAgentMerchantInfo(mer);
	}
	/**
	 * 查看所有账户
	 * 
	 */
	@Override
	public PageInfo<AgentUserInfo> getListAgentUser(Criteria criteria) {
		PageInfo<AgentUserInfo> page = new PageInfo<AgentUserInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(merchantMgrDao.countMerchant(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<AgentUserInfo> list = merchantMgrDao.queryAgentUserInfoList(criteria, rb);
		page.setData(list);
		return page;
	}
	/**
	 * 代理商账户管理(删)
	 * 
	 */
	@Override
	public int deleteAgentUser(AgentUserInfo user) {
		return merchantMgrDao.deleteAgentUserInfo(user);
	}
	/**
	 * 保存新增代理商账户管理
	 * 
	 */
	@Override
	public int saveAgentUserInfo(AgentUserInfo user) {
		return merchantMgrDao.saveAgentUserInfo(user);
	}
	/**
	 * 查询商户的终端信息
	 * 
	 */
	@Override
	public PageInfo<MerchantTerInfo> getAgentMerchantTerInfoList(Criteria criteria) {
		PageInfo<MerchantTerInfo> page = new PageInfo<MerchantTerInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(merchantMgrDao.queryAgentMerchantTerInfoCount(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<MerchantTerInfo> list = merchantMgrDao.queryAgentMerchantTerInfoList(criteria, rb);
		page.setData(list);
		return page;
	}
	/**
	 * 根据账户和密码查找代理商用户
	 * 
	 */
	@Override
	public AgentUserInfo queryAgentUserInfoByName(AgentUserInfo user) {
		return merchantMgrDao.queryAgentUserInfoByName(user);
	}
	/**
	 * 根据代理商编号，商户编号，端口号查询
	 * 
	 */
	@Override
	public GWAgentMerchantInfo queryAgentMerchantInfoByName(
			GWAgentMerchantInfo info) {
		return merchantMgrDao.queryAgentMerchantInfoByName(info);
	}
	/**
	 * 根据代理商编号，商户编号，查找端口是否存在
	 * 
	 */
	@Override
	public MerchantTerInfo queryAgentMerchantTerByName(MerchantTerInfo info) {
		return merchantMgrDao.queryAgentMerchantTerByName(info);
	}
	
	@Override
	public PageInfo<MerchantWebsite> querySysMerchantWebsiteList(Criteria criteria) {
		PageInfo<MerchantWebsite> page = new PageInfo<MerchantWebsite>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(merchantMgrDao.querySysMerchantWebsiteCount(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), page.getPageSize());
		List<MerchantWebsite> list = merchantMgrDao.querySysMerchantWebsiteList(criteria, rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public MerchantWebsite querySysMerchantWebsitInfoById(String id) {
		return merchantMgrDao.querySysMerchantWebsitInfoById(id);
	}
	
	@Override
	public List<MerchantWebsite> queryExportSysMerchantWebsiteInfo(
			Criteria criteria) {
		return merchantMgrDao.queryExportSysMerchantWebsiteInfo(criteria);
	}

	@Override
	public PageInfo<MerchantDisFineInfo> queryMerchantDisFineList(Criteria criteria) {
		PageInfo<MerchantDisFineInfo> page = new PageInfo<MerchantDisFineInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(merchantMgrDao.queryMerchantDidFineInfoCount(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), page.getPageSize());
		List<MerchantDisFineInfo> list = merchantMgrDao.queryMerchantDisFineInfoList(rb, criteria);
		if(list!=null && list.size()>0){
			for(MerchantDisFineInfo info : list){
				List<MerchantDisFineRuleInfo> ruleList = merchantMgrDao.queryMerhcantDisFineRuleInfoList(info.getMerNo(), info.getTerNo());
				info.setRuleList(ruleList);
			}
		}
		page.setData(list);
		return page;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int saveMerchantDisFineList(MerchantDisFineInfo info) throws APIException {
		int a = -1;
		List<MerchantDisFineRuleInfo> ruleList = new ArrayList<MerchantDisFineRuleInfo>();
		for(int i=0; i<info.getAmounts().size(); i++){
			MerchantDisFineRuleInfo rule = new MerchantDisFineRuleInfo();
			rule.setAmount(info.getAmounts().get(i));
			rule.setCurrency(info.getCurrency());
			rule.setEnd("∞".equals(info.getEnds().get(i))?null:info.getEnds().get(i));
			rule.setStart(info.getStarts().get(i));
			rule.setCreateBy(info.getCreateBy());
			rule.setModify(info.getModify());
			rule.setMerNo(info.getMerNo());
			rule.setTerNo(info.getTerNo());
			rule.setEnabled(info.getEnabled());
			ruleList.add(rule);
		}
		info.setRuleList(ruleList);
		if(info.getId()!=null && !("".equals(info.getId()))){
			a = merchantMgrDao.deleteMerchantDisFineInfo(info.getMerNo(), info.getTerNo());
			if(!(a>=0)){
				throw new APIException("保存失败");
			}
//			String[] ids = info.getId().split(",");
			/*
			List<String> delIds = new ArrayList<String>();
			for(int i=0; i<ids.length; i++){
				delIds.add(ids[i]);
			}
			a = merchantMgrDao.deleteMerchantDisFineInfoByIds(delIds);
			if(!(a>0)){
				throw new APIException("保存失败");
			}
			*/
//			if(ids.length==info.getAmounts().size()){
//				for(int i=0; i<ids.length; i++){
//					String id = ids[i];
//					a = merchantMgrDao.updateMerchantDisFineInfoById(id, info, info.getRuleList().get(i));
//					if(!(a>0)){
//						throw new APIException("保存失败");
//					}
//				}
//			}else if(ids.length>info.getAmounts().size()){
//				for(int i=0; i<info.getAmounts().size(); i++){
//					a = merchantMgrDao.updateMerchantDisFineInfoById(ids[i], info, info.getRuleList().get(i));
//					if(!(a>0)){
//						throw new APIException("保存失败");
//					}
//				}
//				
//				List<String> delIds = new ArrayList<String>();
//				for(int i=info.getAmounts().size(); i<ids.length; i++){
//					delIds.add(ids[i]);
//				}
//				a = merchantMgrDao.deleteMerchantDisFineInfoByIds(delIds);
//				if(!(a>0)){
//					throw new APIException("保存失败");
//				}
//			}else if(ids.length<info.getAmounts().size()){
//				for(int i=0; i<ids.length; i++){
//					String id = ids[i];
//					a = merchantMgrDao.updateMerchantDisFineInfoById(id, info, info.getRuleList().get(i));
//					if(!(a>0)){
//						throw new APIException("保存失败");
//					}
//				}
//				List<MerchantDisFineRuleInfo> list1 = new ArrayList<MerchantDisFineRuleInfo>();
//				for(int i=ids.length; i<info.getAmounts().size(); i++){
//					list1.add(ruleList.get(i));
//				}
//				info.setRuleList(list1);
//				a = merchantMgrDao.saveMerchantDisFineInfo(info);
//				if(!(a>0)){
//					throw new APIException("保存失败");
//				}
//			}
		}else{
			MerchantDisFineInfo dis = merchantMgrDao.queryMerchantDisFineInfo(info.getMerNo(), info.getTerNo());
			if(dis!=null){
				throw new APIException("你所要保存商户号为:"+info.getMerNo()+"终端号为:"+info.getTerNo()+"的信息已存在,请去修改");
			}
//			a = merchantMgrDao.saveMerchantDisFineInfo(info);
		}
		a = merchantMgrDao.saveMerchantDisFineInfo(info);
		return a;
	}

	@Override
	public MerchantDisFineInfo queryMerchantDisFineInfo(String merNo, String terNo) {
		MerchantDisFineInfo info = merchantMgrDao.queryMerchantDisFineInfo(merNo, terNo);
		if(info!=null){
			List<MerchantDisFineRuleInfo> ruleList = merchantMgrDao.queryMerhcantDisFineRuleInfoList(merNo, terNo);
			info.setRuleList(ruleList);
		}
		return info;
	}

	@Override
	public int deleteMerchantDisFineInfo(MerchantDisFineInfo info) {
//		List<String> list = new ArrayList<String>();
//		String[] ids = id.split(",");
//		for(String id1 : ids){
//			list.add(id1);
//		}
//		return merchantMgrDao.deleteMerchantDisFineInfoByIds(list);
		return merchantMgrDao.deleteMerchantDisFineInfo(info.getMerNo(), info.getTerNo());
	}
	
	@Override
	public MerchantCurrencySpecialInfo queryMerchantCurrencySpecialInfoByMerchantCurrencyId(
			String merchantCurrencyId) {
		return merchantMgrDao.queryMerchantCurrencySpecialInfoByMerchantCurrencyId(merchantCurrencyId);
	}
	@Override
	public int addMerchantCurrencySpecialInfo(MerchantCurrencySpecialInfo info) {
		return merchantMgrDao.addMerchantCurrencySpecialInfo(info);
	}
	
	@Override
	public int updateMerchantCurrencySpecialInfo(
			MerchantCurrencySpecialInfo info) {
		return merchantMgrDao.updateMerchantCurrencySpecialInfo(info);
	}
	
	@Override
	public PageInfo<CountryCurrencyInfo> queryCountryCurrencyInfoList(
			Criteria criteria) {
		PageInfo<CountryCurrencyInfo> pageInfo = new PageInfo<CountryCurrencyInfo>(criteria.getPageNum(), criteria.getPageSize());
		pageInfo.setTotal(merchantMgrDao.queryCountryCurrencyInfoCount(criteria));
		RowBounds rb = new RowBounds(pageInfo.getOffset(), pageInfo.getPageSize());
		List<CountryCurrencyInfo> list = merchantMgrDao.queryCountryCurrencyInfoList(rb, criteria);
		pageInfo.setData(list);
		return pageInfo;
	}

	@Override
	public CountryCurrencyInfo queryCountryCurrencyInfoById(String id) {
		return merchantMgrDao.queryCountryCurrencyInfoById(id);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int saveCountryCurrencyInfo(CountryCurrencyInfo info) throws APIException {
		int a = merchantMgrDao.saveCountryCurrencyInfo(info);
		if(a>0){
			CountryCurrencyLogInfo log = new CountryCurrencyLogInfo();
			log.setOperateType("1");
			log.setCardType(info.getCardType());
			log.setCountryCode(info.getCountryCode());
			log.setCurrencyId(info.getCurrencyId());
			log.setMerNo(info.getMerNo());
			log.setTerNo(info.getTerNo());
			log.setBrand(info.getBrand());
			log.setEnabled(info.getEnabled());
			log.setMerType(info.getMerType());
			log.setModifyBy(info.getCreateBy());
			a = merchantMgrDao.saveCountryCurrencyLogInfo(log);
			if(!(a>0)){
				throw new APIException("保存log失败");
			}
		}
		return a;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int updateCountryCurrencyInfoById(CountryCurrencyInfo info) throws APIException {
		CountryCurrencyInfo cci = merchantMgrDao.queryCountryCurrencyInfoById(info.getId());
		if(null == cci){
			throw new APIException("修改信息不存在");
		}
		int a = merchantMgrDao.updateCountryCurrencyInfoById(info);
		if(a>0){
			CountryCurrencyLogInfo log = new CountryCurrencyLogInfo();
			log.setOperateType("2");
			log.setCardType(cci.getCardType()+"->"+info.getCardType());
			if(cci.getCountryCode()==null || "".equals(cci.getCountryCode())){
				cci.setCountryCode("0");
			}
			if(cci.getBrand()==null || "".equals(cci.getBrand())){
				cci.setBrand("0");
			}
			log.setCountryCode(cci.getCountryCode()+"->"+info.getCountryCode());
			log.setCurrencyId(cci.getCurrencyId()+"->"+info.getCurrencyId());
			log.setMerNo(cci.getMerNo()+"->"+info.getMerNo());
			log.setTerNo(cci.getTerNo()+"->"+info.getTerNo());
			log.setBrand(cci.getBrand()+"->"+info.getBrand());
			log.setEnabled(cci.getEnabled()+"->"+info.getEnabled());
			log.setMerType(cci.getMerType()+"->"+info.getMerType());
			log.setModifyBy(info.getModifyBy());
			a = merchantMgrDao.saveCountryCurrencyLogInfo(log);
			if(!(a>0)){
				throw new APIException("保存log失败");
			}
		}
		return a;
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int deleteCountryCurrencyInfoById(String id, UserInfo user) throws APIException {
		CountryCurrencyInfo cci = merchantMgrDao.queryCountryCurrencyInfoById(id);
		if(null == cci){
			throw new APIException("删除信息不存在");
		}
		int a = merchantMgrDao.deleteCountryCurrencyInfoById(id);
		if(a>0){
			CountryCurrencyLogInfo log = new CountryCurrencyLogInfo();
			log.setOperateType("3");
			log.setCardType(cci.getCardType());
			if(cci.getCountryCode()==null || "".equals(cci.getCountryCode())){
				cci.setCountryCode("0");
			}
			if(cci.getBrand()==null || "".equals(cci.getBrand())){
				cci.setBrand("0");
			}
			log.setCountryCode(cci.getCountryCode());
			log.setCurrencyId(cci.getCurrencyId());
			log.setMerNo(cci.getMerNo());
			log.setTerNo(cci.getTerNo());
			log.setBrand(cci.getBrand());
			log.setEnabled(cci.getEnabled());
			log.setMerType(cci.getMerType());
			log.setModifyBy(user.getRealName());
			a = merchantMgrDao.saveCountryCurrencyLogInfo(log);
			if(!(a>0)){
				throw new APIException("保存log失败");
			}
		}
		return a;
	}

	@Override
	public PageInfo<CountryCurrencyLogInfo> queryCountryCurrencyLogInfoList(
			Criteria criteria) {
		PageInfo<CountryCurrencyLogInfo> pageInfo = new PageInfo<CountryCurrencyLogInfo>(criteria.getPageNum(), criteria.getPageSize());
		pageInfo.setTotal(merchantMgrDao.queryCountryCurrencyLogInfoCount(criteria));
		RowBounds rb = new RowBounds(pageInfo.getOffset(), pageInfo.getPageSize());
		List<CountryCurrencyLogInfo> list = merchantMgrDao.queryCountryCurrencyLogInfoList(rb, criteria);
		pageInfo.setData(list);
		return pageInfo;
	}

	@Override
	public PageInfo<MerchantTerInfo> queryMerchantMerNoInfoList(Criteria criteria) {
		PageInfo<MerchantTerInfo> pageInfo = new PageInfo<MerchantTerInfo>(criteria.getPageNum(), criteria.getPageSize());
		pageInfo.setTotal(merchantMgrDao.queryMerchantMerNoInfoCount(criteria));
		RowBounds rb = new RowBounds(pageInfo.getOffset(), pageInfo.getPageSize());
		List<MerchantTerInfo> list = merchantMgrDao.queryMerchantMerNoInfoList(rb, criteria);
		pageInfo.setData(list);
		return pageInfo;
	}

	@Override
	public PageInfo<MerchantTerInfo> queryMerchantTerNoInfoList(Criteria criteria) {
		PageInfo<MerchantTerInfo> pageInfo = new PageInfo<MerchantTerInfo>(criteria.getPageNum(), criteria.getPageSize());
		pageInfo.setTotal(merchantMgrDao.queryMerchantTerNoInfoCount(criteria));
		RowBounds rb = new RowBounds(pageInfo.getOffset(), pageInfo.getPageSize());
		List<MerchantTerInfo> list = merchantMgrDao.queryMerchantTerNoInfoList(rb, criteria);
		pageInfo.setData(list);
		return pageInfo;
	}

	@Override
	public PageInfo<MerchantTypeInfo> queryMerchantTypeInfoList(
			Criteria criteria) {
		PageInfo<MerchantTypeInfo> pageInfo = new PageInfo<MerchantTypeInfo>(criteria.getPageNum(), criteria.getPageSize());
		pageInfo.setTotal(merchantMgrDao.queryMerchantTypeInfoCount(criteria));
		RowBounds rb = new RowBounds(pageInfo.getOffset(), pageInfo.getPageSize());
		List<MerchantTypeInfo> list = merchantMgrDao.queryMerchantTypeInfoList(rb, criteria);
		pageInfo.setData(list);
		return pageInfo;
	}
	
	@Override
	public int updateMerchantWebsiteByMerNoAndTerNoAndWebSite(
			MerchantWebsite info) {
		return merchantMgrDao.updateMerchantWebsiteByMerNoAndTerNoAndWebSite(info);
	}
	
	@Override
	public int updateSpareTerNo(MerchantTerInfo merchantTerInfo) {
		return merchantMgrDao.updateSpareTerNo(merchantTerInfo);
	}

	@Override
	public MerchantTerInfo getShopifyById(String id) {
		
		return merchantMgrDao.getShopifyById(id);
	}

	@Override
	public int updateShopifyById(String shopifyOnOff, String id) {
		
		return merchantMgrDao.updateShopifyById(shopifyOnOff, id);
	}
}