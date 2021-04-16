package com.gateway.ratemgr.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.ServiceException;
import com.gateway.ratemgr.mapper.RateMgrMapper;
import com.gateway.ratemgr.model.RateInfo;

@Service
public class RateMgrServiceImpl implements RateMgrService{
	@Autowired
	private RateMgrMapper rateMgrMapper;
	
	public RateMgrMapper getRateMgrMapper() {
		return rateMgrMapper;
	}

	public void setRateMgrMapper(RateMgrMapper rateMgrMapper) {
		this.rateMgrMapper = rateMgrMapper;
	}

	@Override
	public PageInfo<RateInfo> getListRateInfo(Criteria criteria) {
		PageInfo<RateInfo> page = new PageInfo<RateInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(rateMgrMapper.countListRateInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<RateInfo> list = rateMgrMapper.getListRateInfo(criteria, rb);
		page.setData(list);
		return page;
	
	}

	@Override
	public int addRateInfo(RateInfo rate) {
		return rateMgrMapper.addRateInfo(rate);
	}

	@Transactional(rollbackFor=Exception.class)
	public int updateRateInfo(RateInfo rate) throws ServiceException {
		int i = rateMgrMapper.addRateLogInfo(rate.getId());//添加历史记录
		if(i>0){
			i = rateMgrMapper.updateRateInfo(rate);
			return i;
		}else{
			return i;
		}
	}


	@Override
	public RateInfo queryRateInfoById(int id) {
		return rateMgrMapper.queryRateInfoById(id);
	}

	@Override
	public List<RateInfo> queryRateInfoLogById(int rateId) {
		return rateMgrMapper.queryRateInfoLogById(rateId);
	}

	@Override
	public RateInfo queryRateInfoByTerNo(RateInfo rateInfo) {
		return rateMgrMapper.queryRateInfoByTerNo(rateInfo);
	}

	@Override
	public List<RateInfo> exportRateInfo(Criteria criteria) {
		
		return rateMgrMapper.exportRateInfo(criteria);
	}

	@Override
	public List<RateInfo> getCurrencyName() {
		
		return rateMgrMapper.getCurrencyName();
	}

	@Override
	public List<RateInfo> queryBankInfoList() {
		List<RateInfo> listInfo = new ArrayList<RateInfo>();
		List<RateInfo> list = rateMgrMapper.queryBankInfoList();
		RateInfo rateInfo = new RateInfo();
		rateInfo.setBankId("0");
		rateInfo.setBankName("所有");
		listInfo.add(rateInfo);
		for(RateInfo li:list){
			listInfo.add(li);
		}
		return listInfo;
	}
	
	

}
