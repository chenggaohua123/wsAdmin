package com.gateway.faffmgr.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.faffmgr.mapper.FaffDao;
import com.gateway.faffmgr.model.BankCostInfo;
import com.gateway.faffmgr.model.BankCutPaymentInfo;
import com.gateway.faffmgr.model.CapitalFlowInfo;
import com.gateway.faffmgr.model.IncomeCapitalInfo;
import com.gateway.faffmgr.model.IncomeInfo;
import com.gateway.faffmgr.model.MerchantFeeInfo;
import com.gateway.faffmgr.model.ReceiveBankCostInfo;
import com.gateway.faffmgr.model.ReceiveIncomeInfo;
import com.gateway.faffmgr.model.RefundInfo;
import com.gateway.faffmgr.model.RiskCapitalPoolInfo;
import com.gateway.faffmgr.model.SalesPerformanceInfo;
import com.gateway.faffmgr.model.TransCheckForQuery;
import com.gateway.faffmgr.model.TransCheckInfo;
import com.gateway.faffmgr.model.TransCheckedCount;
import com.gateway.faffmgr.model.TransCheckedInfo;

@Service
public class FaffServiceImpl implements FaffService {
	@Autowired
	private FaffDao faffDao;
	
	@Override
	public TransCheckedInfo queryCheckedTransInfo(TransCheckInfo info) {
		return faffDao.queryCheckedTransInfo(info);
	}
	
	@Override
	public int insertFaffUploadDateInfo(TransCheckInfo info) {
		return faffDao.insertFaffUploadDateInfo(info);
	}
	
	@Override
	public PageInfo<TransCheckForQuery> queryTransCheckedInfoList(Criteria criteria) {
		PageInfo<TransCheckForQuery> page = new PageInfo<TransCheckForQuery>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(faffDao.countTransCheckedInfoList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		page.setData(faffDao.queryTransCheckedInfoList(criteria, rb));
		return page;
	}
	@Override
	public List<TransCheckForQuery> queryTransCheckedInfoAll(Criteria criteria) {
		return faffDao.queryTransCheckedInfoList(criteria);
	}
	
	@Override
	public List<RefundInfo> queryRefundInfoByTradeNo(Criteria criteria) {
		return faffDao.queryRefundInfoByTradeNo(criteria);
	}
	@Override
	public List<BankCutPaymentInfo> querybankCutPaymentDescInfoByTradeNo(
			Criteria criteria) {
		return faffDao.querybankCutPaymentDescInfoByTradeNo(criteria);
	}
	
	@Override
	public int updateBankCutAmount(String id, String amount) {
		return faffDao.updateBankCutAmount(id,amount);
	}
	@Override
	public List<TransCheckedCount> queryTransCheckedCount(Criteria criteria) {
		return faffDao.queryTransCheckedCount(criteria);
	}

	@Override
	public PageInfo<IncomeInfo> queryIncomeInfoList(Criteria criteria) {
		PageInfo<IncomeInfo> page = new PageInfo<IncomeInfo>(criteria.getPageNum(), criteria.getPageSize());
		int total = faffDao.queryIncomeInfoCount(criteria);
		page.setTotal(total);
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<IncomeInfo> list = faffDao.queryIncomeInfoList(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public IncomeInfo queryIncomeInfoById(String id) {
		return faffDao.queryIncomeInfoById(id);
	}

	@Override
	public int updateIncomeInfoById(ReceiveIncomeInfo info) {
		return faffDao.updateIncomeInfoById(info);
	}

	@Override
	public int saveIncomeInfo(ReceiveIncomeInfo info) {
		return faffDao.saveIncomeInfo(info);
	}

	@Override
	public List<IncomeCapitalInfo> queryIncomeAmount(Criteria criteria) {
		return faffDao.queryIncomeAmount(criteria);
	}

	@Override
	public Double queryspendingAmount(int type) {
		return faffDao.querySpendingAmount(type);
	}

	@Override
	public List<IncomeInfo> queryIncomeInfoListList(Criteria criteria) {
		return faffDao.queryIncomeInfoList(criteria);
	}
	
	@Override
	public PageInfo<RiskCapitalPoolInfo> queryRiskCapitalPoolInfos(
			Criteria criteria) {
		PageInfo<RiskCapitalPoolInfo> page = new PageInfo<RiskCapitalPoolInfo>(criteria.getPageNum(), criteria.getPageSize());
		int total = faffDao.RiskCapitalPoolInfoCount(criteria);
		page.setTotal(total);
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<RiskCapitalPoolInfo> list = faffDao.RiskCapitalPoolInfo(criteria, rb);
		page.setData(list);
		return page;
	}
	@Override
	public List<RiskCapitalPoolInfo> queryRiskCapitalPoolInfosTotal(Criteria criteria) {
		return faffDao.queryRiskCapitalPoolInfosTotal(criteria);
	}
	@Override
	public List<RiskCapitalPoolInfo> exportRiskCapitalPoolInfos(
			Criteria criteria) {
		return faffDao.exportRiskCapitalPoolInfos(criteria);
	}

	@Override
	public PageInfo<BankCostInfo> queryBankCostInfoList(Criteria criteria) {
		PageInfo<BankCostInfo> page = new PageInfo<BankCostInfo>(criteria.getPageNum(), criteria.getPageSize());
		int total = faffDao.queryBankCostInfoCount(criteria);
		page.setTotal(total);
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<BankCostInfo> list = faffDao.queryBankCostInfoList(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public BankCostInfo queryBankCostInfoById(String id) {
		return faffDao.queryBankCostInfoById(id);
	}

	@Override
	public int saveBankCostInfo(ReceiveBankCostInfo info) {
		return faffDao.saveBankCostInfo(info);
	}

	@Override
	public int updateBankCostInfoById(ReceiveBankCostInfo info) {
		return faffDao.updateBankCostInfoById(info);
	}

	@Override
	public List<BankCostInfo> queryBankCostInfoListList(Criteria criteria) {
		return faffDao.queryBankCostInfoList(criteria);
	}
	
	@Override
	public PageInfo<CapitalFlowInfo> queryCapitalFlowList(Criteria criteria) {
		PageInfo<CapitalFlowInfo> page = new PageInfo<CapitalFlowInfo>(criteria.getPageNum(), criteria.getPageSize());
		int total = faffDao.queryCapitalFlowListCount(criteria);
		page.setTotal(total);
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<CapitalFlowInfo> list = faffDao.queryCapitalFlowList(criteria, rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public PageInfo<MerchantFeeInfo> queryMerchantFeeInfo(Criteria criteria) {
		PageInfo<MerchantFeeInfo> page = new PageInfo<MerchantFeeInfo>(criteria.getPageNum(), criteria.getPageSize());
		int total = faffDao.queryMerchantFeeInfoCount(criteria);
		page.setTotal(total);
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<MerchantFeeInfo> list = faffDao.queryMerchantFeeInfoList(criteria, rb);
		page.setData(list);
		return page;
	}
	@Override
	public MerchantFeeInfo queryMerchantFeeInfoById(String id) {
		return faffDao.queryMerchantFeeInfoById(id);
	}
	@Override
	public List<MerchantFeeInfo> queryMerchantFeeInfoListAll(Criteria criteria) {
		return faffDao.queryMerchantFeeInfoList(criteria);
	}
	@Override
	public int saveMerchantFeeInfo(MerchantFeeInfo form) {
		return faffDao.saveMerchantFeeInfo(form);
	}
	@Override
	public int updateMerchantFeeInfoById(MerchantFeeInfo form) {
		return faffDao.updateMerchantFeeInfoById(form);
	}
	
	@Override
	public PageInfo<CapitalFlowInfo> queryPaymentProfitList(Criteria criteria) {
		PageInfo<CapitalFlowInfo> page = new PageInfo<CapitalFlowInfo>(criteria.getPageNum(), criteria.getPageSize());
		int total = faffDao.queryPaymentProfitCount(criteria);
		page.setTotal(total);
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<CapitalFlowInfo> list = faffDao.queryPaymentProfitList(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public PageInfo<SalesPerformanceInfo> querySalesPerformanceInfo(
			Criteria criteria) {
		PageInfo<SalesPerformanceInfo> page = new PageInfo<SalesPerformanceInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(faffDao.querySalesPerformanceCount(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), page.getPageSize());
		List<SalesPerformanceInfo> list = faffDao.querySalesPerformanceInfo(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public List<SalesPerformanceInfo> queryExportSalesPerformanceInfo(
			Criteria criteria) {
		return faffDao.queryExportSalesPerformanceInfo(criteria);
	}
	
	
}
