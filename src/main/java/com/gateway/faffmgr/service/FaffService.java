package com.gateway.faffmgr.service;

import java.util.List;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
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

public interface FaffService {
	/**
	 * 校验交易数据
	 * @param info
	 * @return
	 */
	public TransCheckedInfo queryCheckedTransInfo(TransCheckInfo info);
	/**
	 * 对账数据插入
	 * @param info
	 * @return
	 */
	public int insertFaffUploadDateInfo(TransCheckInfo info);

	/**
	 * 对账数据查询
	 * @param criteria
	 * @return
	 */
	public PageInfo<TransCheckForQuery> queryTransCheckedInfoList(Criteria criteria);
	public List<TransCheckForQuery> queryTransCheckedInfoAll(Criteria criteria);
	/**
	 * 查询退款详情
	 * @param tradeNo
	 * @return
	 */
	public List<RefundInfo> queryRefundInfoByTradeNo(Criteria criteria);
	/**
	 * 查询银行扣款详情
	 * @param criteria
	 * @return
	 */
	public List<BankCutPaymentInfo> querybankCutPaymentDescInfoByTradeNo(
			Criteria criteria);
	/**
	 * 通过ID修改银行扣款金额
	 * @param id
	 * @param amount
	 * @return
	 */
	public int updateBankCutAmount(String id, String amount);
	/**
	 * 对账数据汇总
	 * @param criteria
	 * @return
	 */
	public List<TransCheckedCount> queryTransCheckedCount(Criteria criteria);
	/**
	 * 实现: 查找异常数据信息
	 * @param criteria
	 * @return 查找结果
	 */
	public PageInfo<IncomeInfo> queryIncomeInfoList(Criteria criteria);
	public List<IncomeInfo> queryIncomeInfoListList(Criteria criteria);
	/**
	 * 实现 : 根据ID查找异常数据信息
	 * @param id
	 * @return 查找结果
	 */
	public IncomeInfo queryIncomeInfoById(String id);
	/**
	 * 实现 :  根据ID修改异常数据信息
	 * @param info 修改信息
	 * @return 修改结果
	 */
	public int updateIncomeInfoById(ReceiveIncomeInfo info);
	/**
	 * 实现: 保存异常数据信息
	 * @param info 添加信息
	 * @return 保存结果
	 */
	public int saveIncomeInfo(ReceiveIncomeInfo info);
	/**
	 * 实现 : 查找收入金额
	 * @return
	 */
	public List<IncomeCapitalInfo> queryIncomeAmount(Criteria criteria);
	/**
	 * 实现 : 查找支出金额
	 * @param type
	 * @return
	 */
	public Double queryspendingAmount(int type);
	/**
	 * 实现风险资金池列表显示
	 * @param criteria
	 * @return
	 */
	public PageInfo<RiskCapitalPoolInfo> queryRiskCapitalPoolInfos(
			Criteria criteria);
	/**
	 * 风险资金池汇总
	 * @param criteria
	 * @return
	 */
	public List<RiskCapitalPoolInfo> queryRiskCapitalPoolInfosTotal(Criteria criteria);
	/**
	 * 导出异常明细
	 * @param criteria
	 * @return
	 */
	public List<RiskCapitalPoolInfo> exportRiskCapitalPoolInfos(
			Criteria criteria);
	/**
	 * 实现 : 查找银行成本录入信息
	 * @param criteria
	 * @return
	 */
	public PageInfo<BankCostInfo> queryBankCostInfoList(Criteria criteria);
	public List<BankCostInfo> queryBankCostInfoListList(Criteria criteria);
	/**
	 * 实现 : 根据ID查找银行成本录入信息
	 * @param id
	 * @return
	 */
	public BankCostInfo queryBankCostInfoById(String id);
	/**
	 * 实现 : 保存新增银行成本录入信息
	 * @param info
	 * @return
	 */
	public int saveBankCostInfo(ReceiveBankCostInfo info);
	/**
	 * 实现 : 保存修改银行成本录入信息
	 * @param info
	 * @return
	 */
	public int updateBankCostInfoById(ReceiveBankCostInfo info);
	/**
	 * 资金流核算
	 * @param criteria
	 * @return
	 */
	public PageInfo<CapitalFlowInfo> queryCapitalFlowList(Criteria criteria);
	/**
	 * 商户费用录入查询
	 * @param criteria
	 * @return
	 */
	public PageInfo<MerchantFeeInfo> queryMerchantFeeInfo(Criteria criteria);
	/**
	 * 通过ID查询商户费用录入信息
	 * @param id
	 * @return
	 */
	public MerchantFeeInfo queryMerchantFeeInfoById(String id);
	/**
	 * 更新商户费用录入信息
	 * @param form
	 * @return
	 */
	public int updateMerchantFeeInfoById(MerchantFeeInfo form);
	/**
	 * 保存商户费用录入信息
	 * @param form
	 * @return
	 */
	public int saveMerchantFeeInfo(MerchantFeeInfo form);
	/**
	 * 查询所有费用录入信息
	 * @param criteria
	 * @return
	 */
	public List<MerchantFeeInfo> queryMerchantFeeInfoListAll(Criteria criteria);
	/**
	 * 支付利润核算
	 * @param criteria
	 * @return
	 */
	public PageInfo<CapitalFlowInfo> queryPaymentProfitList(Criteria criteria);
	
	/**
	 * 查询销售业绩
	 */
	public PageInfo<SalesPerformanceInfo> querySalesPerformanceInfo(Criteria criteria);
	
	/**
	 * 导出销售业绩详情
	 */
	public List<SalesPerformanceInfo> queryExportSalesPerformanceInfo(Criteria criteria);
	
}
