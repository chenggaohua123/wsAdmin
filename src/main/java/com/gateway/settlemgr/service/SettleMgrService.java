package com.gateway.settlemgr.service;

import java.util.List;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.ServiceException;
import com.gateway.settlemgr.model.AgentSettleInfo;
import com.gateway.settlemgr.model.DeductionTypeInfo;
import com.gateway.settlemgr.model.DisFineStepInfo;
import com.gateway.settlemgr.model.ExceptionSettleType;
import com.gateway.settlemgr.model.ExportInfo;
import com.gateway.settlemgr.model.GwSettleTransInfo;
import com.gateway.settlemgr.model.HandTransInfo;
import com.gateway.settlemgr.model.MerchantAccount;
import com.gateway.settlemgr.model.MerchantAccountAccess;
import com.gateway.settlemgr.model.MerchantAccountAccessDetail;
import com.gateway.settlemgr.model.MerchantSettleInfo;
import com.gateway.settlemgr.model.PoolSettleInfo;
import com.gateway.settlemgr.model.SettleTypeInfo;
import com.gateway.transmgr.model.TransInfo;

public interface SettleMgrService {
	
	/**
	 * 查询商户结算列表
	 * @param criteria
	 * @param rd
	 * @return
	 */
	public PageInfo<MerchantSettleInfo> getMerchantSettleInfo(Criteria criteria);

	/**
	 * 导出商户结算划款
	 * @param criteria
	 * @return
	 */
	public List<MerchantSettleInfo> exportMerchantSettleInfo(Criteria criteria); 
	/**
	 * 结算指定日期的交易,返回结算的批次号
	 * @param settleDate
	 * @return
	 */
	public String createMerchantSettleList(Criteria criteria);
	
	/**
	 * 预览清算记录
	 * @param criteria
	 * @return
	 */
	public PageInfo<MerchantSettleInfo> viewMerchantSettleList(Criteria criteria);
	

	/**
	 * 明细导出
	 * @param merNo
	 * @param terNo
	 * @param batchNo
	 * @return
	 */
	public List<GwSettleTransInfo> queryDetailList(String merNo,String terNo,String batchNo);
	
	/**
	 * 代理商预览分润
	 * @param criteria
	 * @return
	 */
	public PageInfo<AgentSettleInfo> viewAgentSettleInfo(Criteria criteria);
	
	/**
	 * 创建代理商分润表
	 * @param criteria
	 * @return
	 */
	public PageInfo<AgentSettleInfo> createAgentSettleInfo(Criteria criteria);
	
	/**
	 * 生效代理分润列表，返回当前的批次号
	 * @param criteria
	 * @return
	 */
	public String effectAgentSettleInfo(Criteria criteria) throws ServiceException;
	
	/**
	 * 查询代理商分润列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<AgentSettleInfo> queryAgentSettleInfoList(Criteria criteria);
	
	public List<AgentSettleInfo> exportAgentSettleInfoList(Criteria criteria);
	/**
	 * 列出商户虚拟账户
	 * */
	public PageInfo<MerchantAccount> listMerchantAccount(Criteria criteria);
	/**
	 * 列出出入帐明细
	 * */
	public PageInfo<MerchantAccountAccess> listMerchantAccountAccess(Criteria criteria);
	/**
	 * 列出商户出入帐流水明细
	 * */
	public PageInfo<MerchantAccountAccessDetail> listMerchantAccountAccessDetail(Criteria criteria);
	/**
	 * 查询商户结算方式
	 * */
	public PageInfo<SettleTypeInfo> listSettleTypeInfo(Criteria criteria);
	/**
	 * 查询商户结算条件重复的商户号和终端号
	 * */
	public int queryDuplicateByMerNoAndTerNo(String merNo, String terNo);
	/**
	 * 添加商户结算条件
	 * */
	public int insertSettleTypeInfo(SettleTypeInfo settleTypeInfo);
	/**
	 * 通过ID查询商户结算条件
	 * */
	public SettleTypeInfo querySettleTypeInfoById(String id);
	/**
	 * 修改商户结算条件 并冻结部分提现金额
	 * */
	public int updateSettleTypeInfo(SettleTypeInfo settleTypeInfo);
	/**
	 * 通过商户虚拟账户ID查询商户虚拟账户信息
	 * */
	public MerchantAccount queryMerchantAccountById(String id,String accountType);
	/**
	 * 插入入账记录
	 * */
	public int insertMerchantAccountAccess(MerchantAccountAccess merchantAccountAccess,MerchantAccount merchantAccount);
	/**
	 * 通过ID查询商户虚拟账户出入帐数据
	 * */
	public MerchantAccountAccess queryMerchantAccountAccessById(String id);
	/**
	 * 商户虚拟账户提现审核
	 * */
	public int updateMerchantAccountAccess(MerchantAccountAccess merchantAccountAccess)  throws ServiceException ;
	
	/**
	 * 通过ID查询账户信息
	 * */
	public MerchantAccount queryMerchantAccountById(String id);
	/**
	 * 审核提现账户
	 * */
	public int checkMerchantAccountAccess(MerchantAccountAccess ma) throws ServiceException;
	/**
	 * 导出结算明细
	 * */
	public List<ExportInfo> exportInfo(String id,String merNo,String terNo,String cashType);
	/**
	 * 导出提现周期明细
	 * @param id
	 * @param merNo
	 * @param terNo
	 * @param cashType
	 * @return
	 */
	public List<ExportInfo> exportInfoForCash(String id, String merNo,
			String terNo, String cashType);
	/**
	 * 列表显示账户结算条件管理
	 * */
	public PageInfo<ExceptionSettleType> queryExceptionSettleType(
			Criteria criteria);
	/**
	 * 添加账户结算条件
	 * @param dfInfo 
	 * */
	public int addExceptionSettleTypeInfo(ExceptionSettleType info, DisFineStepInfo dfInfo);
	/**
	 * 校验数据是否重复添加
	 * */
	public int queryDuplicateExceptionSettleTypeInfo(ExceptionSettleType info);
	/**
	 * 通过ID查询账户结算条件
	 * */
	public ExceptionSettleType queryExceptionSettleTypeInfoById(String id);
	/**
	 * 通过ID修改账户结算条件
	 * */
	public int updateExceptionSettleTypeInfo(ExceptionSettleType info);
	/**
	 * 通过ID删除账户结算条件
	 * */
	public int deleteExceptionSettleTypeInfo(String[] ids);
	/**
	 * 导出提现信息
	 * @param criteria
	 * @return
	 */
	public List<MerchantAccountAccess> exportCashInfos(Criteria criteria);
	/**
	 *导出账户信息
	 * */
	public List<MerchantAccount> exportMerchantAccountInfo(Criteria criteria);
	/**
	 * 导出商户结算周期信息
	 * @param criteria
	 * @return
	 */
	public List<SettleTypeInfo> ExportSettleTypeInfo(Criteria criteria);
	/**
	 * 添加扣款类型
	 * @param deductionType
	 * @return
	 */
	public int addDeductionType(String deductionType);
	/**
	 * 获取所有扣款类型
	 * @param criteria
	 * @return
	 */
	public List<DeductionTypeInfo> queryDeductionTypeInfo(Criteria criteria);

	public DeductionTypeInfo queryDeductionTypeInfoById(String id);

	public int updateDeductionType(DeductionTypeInfo info);

	public int deleteDeductionTypeById(String id);
	/**
	 * 导出出入账明细
	 * @param criteria
	 * @return
	 */
	public List<MerchantAccountAccess> exportListMerchantAccountInAndOut(
			Criteria criteria);
	
	/**
	 * 查询可以手工结算的订单
	 * @param criteria
	 * @return
	 */
	public PageInfo<TransInfo> queryCanHandTransInfo(Criteria criteria);
	/**
	 * 查询要添加的信息
	 * @param tradeNos
	 * @return
	 */
	public List<HandTransInfo> queryHandTransInfoByTradeNos(String[] tradeNos);
	/**
	 * 添加待结算的订单
	 * @param list
	 * @return
	 */
	public int addHandTransInfo(List<HandTransInfo> list) throws Exception;
	/**
	 * 手工入账订单状态
	 * @param criteria
	 * @return
	 */
	public PageInfo<HandTransInfo> queryHandTransInfo(Criteria criteria);
	/**
	 * 查询商户结算汇总
	 * 每日汇总
	 * @param criteria
	 * @return
	 */
	public PageInfo<PoolSettleInfo> queryPoolSettleInfo(Criteria criteria);
	/**
	 * 总计商户结算汇总
	 * @param criteria
	 * @return
	 */
	public PoolSettleInfo queryTotalPoolSettleInfo(Criteria criteria);
	/**
	 * 导出商户交易报告
	 * @param criteria
	 * @return
	 */
	public List<PoolSettleInfo> exportPoolSettleInfo(Criteria criteria);
	/***
	 * 查询交易额明细
	 * @param criteria
	 * @return
	 */
	public List<TransInfo> exportPoolSettleInfoForTrans(Criteria criteria);
	
	/**
	 * 导出异常结算明细
	 * @param criteria
	 * @return
	 */
	public List<ExportInfo> exportExceptiontTransInfo(Criteria criteria);

	/**
	 * 保证金账户提现
	 * @param id
	 * @param merNo
	 * @param terNo
	 * @return
	 */
	public List<ExportInfo> exportInfoForBondCash(String id, String merNo,
			String terNo);
	/**
	 * 通过ID把复核通过修改成审核通过
	 * */
	public int updateMerchantAccountAccessStatus(MerchantAccountAccess merchantAccountAccess);
}
