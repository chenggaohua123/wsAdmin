package com.gateway.faffmgr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
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


public interface FaffDao {
	/**
	 * 校验数据
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
	 * @param rb
	 * @return
	 */
	public List<TransCheckForQuery> queryTransCheckedInfoList(Criteria criteria,RowBounds rb);
	public List<TransCheckForQuery> queryTransCheckedInfoList(Criteria criteria);
	/**
	 * 统计对账数据条数
	 * @param criteria
	 * @return
	 */
	public int countTransCheckedInfoList(Criteria criteria);
	/**
	 *退款条数
	 * @param criteria
	 * @return
	 */
	public int countRefundInfoByTradeNo(Criteria criteria);
	/**
	 * 退款信息
	 * @param criteria
	 * @param rb
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
	public int updateBankCutAmount(@Param("id")String id, @Param("amount")String amount);
	/**
	 * 银行对账数据汇总
	 * @param criteria
	 * @return
	 */
	public List<TransCheckedCount> queryTransCheckedCount(Criteria criteria);
	/**
	 * 实现: 查找异常数据信息
	 * @param criteria
	 * @return 查找结果
	 */
	public List<IncomeInfo> queryIncomeInfoList(Criteria criteria, RowBounds rb);
	public List<IncomeInfo> queryIncomeInfoList(Criteria criteria);
	/**
	 * 实现 : 根据ID查找异常数据信息
	 * @param id
	 * @return 查找结果
	 */
	public IncomeInfo queryIncomeInfoById(@Param("id") String id);
	/**
	 * 实现:修改异常数据信息
	 * @param vo 修改数据
	 * @return 修改结果
	 */
	public int updateIncomeInfoById(@Param("vo") ReceiveIncomeInfo vo);
	/**
	 * 实现: 保存异常数据信息
	 * @param info 添加信息
	 * @return 保存结果
	 */
	public int saveIncomeInfo(@Param("vo") ReceiveIncomeInfo vo);
	/**
	 * 实现  : 查找总记录数
	 * @return
	 */
	public int queryIncomeInfoCount(Criteria criteria);
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
	public Double querySpendingAmount(@Param("type") int type);
	/**
	 * 统计风险资金池条数
	 * @param criteria
	 * @return
	 */
	public int RiskCapitalPoolInfoCount(Criteria criteria);
	/**
	 * 列表显示风险资金内容
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<RiskCapitalPoolInfo> RiskCapitalPoolInfo(Criteria criteria,
			RowBounds rb);
	/**
	 * 风险资金池汇总
	 * @param criteria
	 * @return
	 */
	public List<RiskCapitalPoolInfo> queryRiskCapitalPoolInfosTotal(Criteria criteria);
	/**
	 * 导出风险资金池明细
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
	public List<BankCostInfo> queryBankCostInfoList(Criteria criteria, RowBounds rb);
	public List<BankCostInfo> queryBankCostInfoList(Criteria criteria);
	/**
	 * 实现 : 查找银行成本记录总记录数
	 * @return
	 */
	public int queryBankCostInfoCount(Criteria criteria);
	/**
	 * 实现 : 根据ID查找银行成本录入信息
	 * @param id
	 * @return
	 */
	public BankCostInfo queryBankCostInfoById(@Param("id") String id);
	/**
	 * 实现 : 保存新增银行成本录入信息
	 * @param vo
	 * @return
	 */
	public int saveBankCostInfo(@Param("vo") ReceiveBankCostInfo vo);
	/**
	 * 实现 : 保存修改银行成本录入信息
	 * @param vo
	 * @return
	 */
	public int updateBankCostInfoById(@Param("vo") ReceiveBankCostInfo vo);
	
	/**
	 * 资金流条数
	 * @param criteria
	 * @return
	 */
	public int queryCapitalFlowListCount(Criteria criteria);
	/**
	 * 资金流信息
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<CapitalFlowInfo> queryCapitalFlowList(Criteria criteria,
			RowBounds rb);
	/**
	 * 统计商户费用信息条数
	 * @param criteria
	 * @return
	 */
	public int queryMerchantFeeInfoCount(Criteria criteria);
	/**
	 * 查询商户费用信息
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<MerchantFeeInfo> queryMerchantFeeInfoList(Criteria criteria,
			RowBounds rb);
	public List<MerchantFeeInfo> queryMerchantFeeInfoList(Criteria criteria);
	/**
	 * 通过ID查询商户费用信息
	 * @param id
	 * @return
	 */
	public MerchantFeeInfo queryMerchantFeeInfoById(String id);
	/**
	 * 保存商户费用信息
	 * @param form
	 * @return
	 */
	public int saveMerchantFeeInfo(MerchantFeeInfo form);
	/**
	 * 修改商户费用信息
	 * @param form
	 * @return
	 */
	public int updateMerchantFeeInfoById(MerchantFeeInfo form);
	/**
	 * 支付利润核算技术
	 * @param criteria
	 * @return
	 */
	public int queryPaymentProfitCount(Criteria criteria);
	/**
	 * 支付利润核算信息
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<CapitalFlowInfo> queryPaymentProfitList(Criteria criteria,
			RowBounds rb);
	
	/**
	 * 查询销售业绩总数
	 */
	public int querySalesPerformanceCount(Criteria criteria);
	
	/**
	 * 查询销售业绩
	 */
	public List<SalesPerformanceInfo> querySalesPerformanceInfo(Criteria criteria, RowBounds rb);
	
	/**
	 * 导出销售业绩详情
	 */
	public List<SalesPerformanceInfo> queryExportSalesPerformanceInfo(Criteria criteria);
	
}
