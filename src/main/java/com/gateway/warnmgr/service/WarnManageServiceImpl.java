package com.gateway.warnmgr.service;

import java.util.List;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.fraud.common.exception.FraudExcetion;
import com.gateway.warnmgr.mapper.WarnManageDao;
import com.gateway.warnmgr.model.BankInfo;
import com.gateway.warnmgr.model.MerchantWarnInfo;
import com.gateway.warnmgr.model.TransWarnMessageInfo;
import com.gateway.warnmgr.model.TransWarnPhoneInfo;
import com.gateway.warnmgr.model.TransWarnRuleInfo;


@Service
public class WarnManageServiceImpl implements WarnManageService{
	@Autowired
	private WarnManageDao warnManageDao;
	
	@Override
	public PageInfo<MerchantWarnInfo> queryMerchantWarnInfoList(
			Criteria criteria) {
		PageInfo<MerchantWarnInfo> page=new PageInfo<MerchantWarnInfo>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(warnManageDao.countMerchantWarnInfoCount(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <MerchantWarnInfo> list=warnManageDao.queryMerchantWarnInfoCount(criteria,rb);
		page.setData(list);
		return page;
	}
	@Override
	public List<MerchantWarnInfo> queryMerchantWarnInfoListAll(
			Criteria criteria) {
		return warnManageDao.queryMerchantWarnInfoCount(criteria);
	}
	
	@Override
	public PageInfo<TransWarnRuleInfo> queryTransWarnRuleInfoList(
			Criteria criteria) {
		PageInfo<TransWarnRuleInfo> pageInfo = new PageInfo<TransWarnRuleInfo>(criteria.getPageNum(), criteria.getPageSize());
		int count = warnManageDao.queryTransWarnRuleInfoCount(criteria);
		pageInfo.setTotal(count);
		RowBounds rb = new RowBounds(pageInfo.getOffset(), pageInfo.getPageSize());
		List<TransWarnRuleInfo> list = warnManageDao.queryTransWarnRuleInfoList(rb, criteria);
		pageInfo.setData(list);
		return pageInfo;
	}

	@Override
	public int saveTransWarnRuleInfo(TransWarnRuleInfo info) {
		return warnManageDao.saveTransWarnRuleInfo(info);
	}

	@Override
	public int deleteTransWarnRuleInfo(String id) throws FraudExcetion {
		int a = warnManageDao.deleteTransWarnRelPhoneInfoByWarnId(id);
		if(!(a>=0)){
			throw new FraudExcetion("删除失败");
		}
		a = warnManageDao.deleteTransWarnMessageInfoByWarnId(id);
		if(!(a>=0)){
			throw new FraudExcetion("删除失败");
		}
		return warnManageDao.deleteTransWarnRuleInfoById(id);
	}

	@Override
	public int deleteTransWarnRuleInfos(String[] ids) throws FraudExcetion {
		int a = -1;
		if(ids!=null && ids.length>0){
			for(String id : ids){
				a = warnManageDao.deleteTransWarnRuleInfoById(id);
				if(!(a>0)){
					throw new FraudExcetion("删除失败");
				}
				a = warnManageDao.deleteTransWarnRelPhoneInfoByWarnId(id);
				if(!(a>=0)){
					throw new FraudExcetion("删除失败");
				}
				a = warnManageDao.deleteTransWarnMessageInfoByWarnId(id);
				if(!(a>=0)){
					throw new FraudExcetion("删除失败");
				}
			}
		}
		return a;
	}

	@Override
	public int updateTransWarnRuleInfoById(TransWarnRuleInfo info) {
		return warnManageDao.updateTransWarnRuleInfoById(info);
	}

	@Override
	public TransWarnRuleInfo queryTransWarnRuleInfoById(String id) {
		return warnManageDao.queryTransWarnRuleInfoById(id);
	}

	@Override
	public PageInfo<BankInfo> queryBankInfoList(Criteria criteria) {
		PageInfo<BankInfo> pageInfo = new PageInfo<BankInfo>(criteria.getPageNum(), criteria.getPageSize());
		int count = warnManageDao.queryBankInfoCount(criteria);
		pageInfo.setTotal(count);
		RowBounds rb = new RowBounds(pageInfo.getOffset(), pageInfo.getPageSize());
		List<BankInfo> list = warnManageDao.queryBankInfoList(rb, criteria);
		pageInfo.setData(list);
		return pageInfo;
	}

	@Override
	public PageInfo<TransWarnPhoneInfo> queryTransWarnPhoneInfoList(
			Criteria criteria) {
		PageInfo<TransWarnPhoneInfo> pageInfo = new PageInfo<TransWarnPhoneInfo>(criteria.getPageNum(), criteria.getPageSize());
		int count = warnManageDao.queryTransWarnPhoneInfoCount(criteria);
		pageInfo.setTotal(count);
		RowBounds rb = new RowBounds(pageInfo.getOffset(), pageInfo.getPageSize());
		List<TransWarnPhoneInfo> list = warnManageDao.queryTransWarnPhoneInfoList(rb, criteria);
		if(list!=null && list.size()>0){
			for(TransWarnPhoneInfo phoneInfo : list){
				if(phoneInfo.getWarnId()!=null && !("".equals(phoneInfo.getWarnId()))){
					if((String) criteria.getCondition().get("warnId")!=null && !("".equals((String) criteria.getCondition().get("warnId")))){
						if(phoneInfo.getWarnId().contains((String) criteria.getCondition().get("warnId"))){
							phoneInfo.setChecked(true);
						}
					}
				}
			}
		}
		pageInfo.setData(list);
		return pageInfo;
	}

	@Override
	public int saveTransWarnPhoneInfo(TransWarnPhoneInfo info) {
		return warnManageDao.saveTransWarnPhoneInfo(info);
	}

	@Override
	public int deleteTransWarnPhoneInfoById(String id) throws FraudExcetion {
		int a = warnManageDao.deleteTransWarnRelPhoneInfoByPhoneId(id);
		if(!(a>=0)){
			throw new FraudExcetion("删除失败");
		}
		return warnManageDao.deleteTransWarnPhoneInfoById(id);
	}

	@Override
	public int updateTransWarnPhoneInfoById(TransWarnPhoneInfo info) {
		return warnManageDao.updateTransWarnPhoneInfoById(info);
	}

	@Override
	public TransWarnPhoneInfo queryTransWarnPhoenInfoById(String id) {
		return warnManageDao.queryTransWarnPhoenInfoById(id);
	}

	@Override
	public int saveTransWarnWarnIdRelPhoneInfo(String[] ids, String warnId) throws FraudExcetion {
		int a = warnManageDao.deleteTransWarnRelPhoneInfoByWarnId(warnId);
		if(!(a>=0)){
			throw new FraudExcetion("添加失败");
		}
		for(String phoneId : ids){
			a = warnManageDao.saveTransWarnRelPhoneInfo(phoneId, warnId);
			if(!(a>0)){
				throw new FraudExcetion("添加失败");
			}
		}
		return a;
	}

	@Override
	public List<TransWarnRuleInfo> queryPhoneRelTransRuleInfoByWarnIds(
			String warnIds) {
		return warnManageDao.queryPhoneRelTransRuleInfoByWarnIds(warnIds);
	}

	@Override
	public int saveTransWarnMessageInfo(TransWarnMessageInfo info) throws FraudExcetion {
		TransWarnMessageInfo message = warnManageDao.queryTransWarnMessageInfoByWarnId(info.getWarnId());
		if(message!=null){
			throw new FraudExcetion("短信信息已存在，不能添加");
		}
		return warnManageDao.saveTransWarnMessageInfo(info);
	}

	@Override
	public int updateTransWarnMessageInfoById(TransWarnMessageInfo info) {
		return warnManageDao.updateTransWarnMessageInfoById(info);
	}

	@Override
	public int deleteTransWarnMessageInfoByWarnId(String warnId) {
		return warnManageDao.deleteTransWarnMessageInfoByWarnId(warnId);
	}

	@Override
	public TransWarnMessageInfo queryTransWarnMessageInfoByWarnId(String warnId) {
		return warnManageDao.queryTransWarnMessageInfoByWarnId(warnId);
	}
	
}
