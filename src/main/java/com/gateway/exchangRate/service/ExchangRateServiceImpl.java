package com.gateway.exchangRate.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.gateway.api.utils.Constants;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.APIException;
import com.gateway.exchangRate.mapper.ExchangRateDao;
import com.gateway.exchangRate.model.BankSourceRateDetail;
import com.gateway.exchangRate.model.BankSourceRateInfo;
import com.gateway.exchangRate.model.CheckBankSourceRateInfo;
import com.gateway.exchangRate.model.CreateCheckBankSourceRateInfo;
import com.gateway.exchangRate.model.ExchangRateInfo;
import com.gateway.exchangRate.model.MerExchangRateInfo;

@Service
public  class ExchangRateServiceImpl implements ExchangRateService{
	@Autowired
	private ExchangRateDao exchangRateDao;
	@Override
	public PageInfo<ExchangRateInfo> queryExchangRateList(Criteria criteria) {
		PageInfo<ExchangRateInfo> page = new PageInfo<ExchangRateInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(exchangRateDao.countExchangRateTotal(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		page.setData(exchangRateDao.queryExchangRateList(criteria, rb));
		return page;
	}
	
	@Override
	public int addExchangRate(ExchangRateInfo exchangRateInfo) {
		return exchangRateDao.addExchangRate(exchangRateInfo);
	}
	@Override
	public String queryExchangRate(ExchangRateInfo exchangRateInfo) {
		ExchangRateInfo info =exchangRateDao.queryExchangRate(exchangRateInfo);
		if(!StringUtils.isEmpty(info)){
			return "该汇率已存在，请勿重复添加";
		}
		ExchangRateInfo infoLog = exchangRateDao.queryExchangRateLog(exchangRateInfo);
		if(!StringUtils.isEmpty(infoLog)){
			return "该汇率已存在待审核列表，请勿重复添加";
		}
		return null;
	}
	@Override
	public ExchangRateInfo queryExchangRateInfoId(int id) {
		return exchangRateDao.queryExchangRateInfoId(id);
	}
	@Override
	public int updateExchangRate(ExchangRateInfo exchangRateInfo) {
		
		
		return exchangRateDao.updateExchangRate(exchangRateInfo);
	}
	
	
	@Override
	public PageInfo<ExchangRateInfo> queryMerchantExchangRateList(
			Criteria criteria) {
		PageInfo<ExchangRateInfo> page = new PageInfo<ExchangRateInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(exchangRateDao.countMerchantExchangRateTotal(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		page.setData(exchangRateDao.queryMerchantExchangRateList(criteria, rb));
		return page;
	}
	
	@Override
	public List<String> queryRateGroupNamesByType(String type) {
		return exchangRateDao.queryRateGroupNamesByType(type);
	}
	@Override
	public String queryMerNoByTerNo(String terNo) {
		return exchangRateDao.queryMerNoByTerNo(terNo);
	}
	
	@Override
	public int addMerchantExchangRate(MerExchangRateInfo merExchangRateInfo) {
		return exchangRateDao.addMerchantExchangRate(merExchangRateInfo);
	}
	
	@Override
	public MerExchangRateInfo queryMerchantExchangRateById(String id) {
		return exchangRateDao.queryMerchantExchangRateById(id);
	}
	
	@Override
	public int updateMerchantExchangRate(MerExchangRateInfo merExchangRateInfo) {
		return exchangRateDao.updateMerchantExchangRate(merExchangRateInfo);
	}
	@Override
	public PageInfo<BankSourceRateDetail> queryBankSourceRateList(Criteria criteria) {
		PageInfo<BankSourceRateDetail> page = new PageInfo<BankSourceRateDetail>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(exchangRateDao.countBankSourceRateInfoTotal(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		page.setData(exchangRateDao.queryBankSourceRateInfoList(criteria, rb));
		return page;
	}
	@Override
	public List<String> queryRateType() {
		return exchangRateDao.queryRateType();
	}
	@Override
	public PageInfo<BankSourceRateDetail> queryBankSourceRateLogListById(Criteria criteria) {
		PageInfo<BankSourceRateDetail> page = new PageInfo<BankSourceRateDetail>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(exchangRateDao.countBankSourceRateLogInfoTotal(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		page.setData(exchangRateDao.queryBankSourceRateLogInfoList(criteria, rb));
		return page;
	}
	@Override
	public List<String> querySourceCurrency() {
		return exchangRateDao.querySourceCurrency();
	}
	@Override
	public List<String> queryTargetCurrency() {
		return exchangRateDao.querytargetCurrency();
	}
	@Override
	public int createCheckBankSourceRate(CreateCheckBankSourceRateInfo info) {
		return exchangRateDao.createCheckBankSourceRate(info);
	}
	
	@Override
	public PageInfo<CheckBankSourceRateInfo> queryCheckBankSourceRateInfo(
			Criteria criteria) {
		PageInfo<CheckBankSourceRateInfo> page = new PageInfo<CheckBankSourceRateInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(exchangRateDao.countCheckBankSourceRateInfoTotal(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		page.setData(exchangRateDao.queryCheckBankSourceRateInfoList(criteria, rb));
		return page;
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int updateBankRateToExchangRate(String[] ids,String status,String updateBy) {
		int count=0;
		List<CheckBankSourceRateInfo> list=exchangRateDao.queryCheckBankSourceRateInfoByIds(ids);
		count=exchangRateDao.updateCheckBankSourceRateInfo(ids,status,updateBy);
		if("1".equals(status)){
			count=0;
			for(CheckBankSourceRateInfo info: list){
				info.setUpdateBy(updateBy);
				count+=exchangRateDao.updateBankRateToExchangRate(info);
			}
		}
		return count;
	}

	@Override
	public int insertExchangRateLog(ExchangRateInfo exchangRateInfo) {
		return exchangRateDao.insertExchangRateLog(exchangRateInfo);
	}

	@Override
	public PageInfo<ExchangRateInfo> queryExchangRateListLog(Criteria criteria) {
		PageInfo<ExchangRateInfo> page = new PageInfo<ExchangRateInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(exchangRateDao.countExchangRateTotalLog(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		page.setData(exchangRateDao.queryExchangRateLogList(criteria, rb));
		return page;
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public void checkExchangRate(int[] ids, int status, String updateBy) throws APIException{
		for(int i=0;i<ids.length;i++){
			ExchangRateInfo info = exchangRateDao.queryExchangRateLogId(ids[i]);
			if(StringUtils.isEmpty(info)){
				throw new APIException("数据异常，请重试");
			}
			info.setCheckStatus(status);
			info.setCheckBy(updateBy);
			int t = exchangRateDao.updateCheckExchangRateLog(info);
			if(t <= 0 ){
				throw new APIException("审核汇率数据异常id="+info.getId()+"，请重试");
			}
			if(1 == status){
				continue;
			}
			if(Constants.EXCHANG_RATE_OPERATION_TYPE_INSERT.equals(info.getOperationType())){//保存
				insertCheckExchangRate(info);
				continue;
			}
			updateCheckExchangRate(info);
		}
	}
	
	/** 审核修改操作 */
	private void updateCheckExchangRate(ExchangRateInfo info) throws APIException{
		info.setId(info.getRateId()+"");
		info.setUpdateBy(info.getCreateBy());
		int i = exchangRateDao.updateExchangRate(info);
		if(i <= 0){
			throw new APIException("数据异常，请重试");
		}
	}
	/** 审核添加操作 */
	private void insertCheckExchangRate(ExchangRateInfo info) throws APIException{
		int i = exchangRateDao.addExchangRate(info);
		if(i <= 0){
			throw new APIException("数据异常，请重试");
		}
		int id = exchangRateDao.getAddRateInfoId();
		ExchangRateInfo infos = new ExchangRateInfo();
		infos.setRateId(id);
		infos.setId(info.getId());
		int t = exchangRateDao.updateCheckExchangRateLogRateId(infos);
		if(t <= 0){
			throw new APIException("审核数据修改异常，请重试");
		}
	}

	@Override
	public BigDecimal getBankRate(BankSourceRateInfo info) {
		BigDecimal rate;
		if("CNY".equalsIgnoreCase(info.getSourceCurrencyCode())&&!"CNY".equalsIgnoreCase(info.getTargetCurrencyCode())){
			rate = exchangRateDao.getBankRateCNY(info);
		}else{
			rate = exchangRateDao.getBankRate(info);
		}
		return rate;
	}

	@Override
	public List<ExchangRateInfo> gotoCheckExchangRateLog(
			ExchangRateInfo exchangRateInfo) {
		return exchangRateDao.gotoCheckExchangRateLog(exchangRateInfo);
	}
	
	@Override
	public MerExchangRateInfo queryMerExchangRateInfo(MerExchangRateInfo merExchangRateInfo){
		return exchangRateDao.queryMerExchangRateInfo(merExchangRateInfo);
	}
	/**
	 * 实现：删除汇率记录
	 * @param vo 删除条件
	 * @return 删除结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	public int deleteExchangeRate(ExchangRateInfo vo) {
		return exchangRateDao.deleteExchangeRate(vo);
	}
	/**
	 * 实现：删除商户定制汇率记录
	 * @param vo 删除条件
	 * @return 删除结果
	 * @date 2015-09-12
	 * @author YWP
	 */
	public int deleteMerchantExchangeRate(ExchangRateInfo vo) {
		return exchangRateDao.deleteMerchantExchangeRate(vo);
	}
}
