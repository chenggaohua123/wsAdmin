package com.gateway.settlemgr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.common.adapter.web.model.Criteria;
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

public interface SettleMgrMapper {
	
	/**
	 * 分页查询商户结算列表
	 * @param criteria
	 * @param rd
	 * @return
	 */
	public List<MerchantSettleInfo> getMerchantSettleInfo(Criteria criteria,RowBounds rd);
	
	public int countListMerchantSettleInfo(Criteria criteria);
	
	/**
	 * 导出商户结算划款
	 * @param criteria
	 * 
	 * @return
	 */
	public List<MerchantSettleInfo> getMerchantSettleInfo(Criteria criteria); 
	
	/**
	 * 预览清算记录
	 * @param criteria
	 * @param rd
	 * @return
	 */
	public List<MerchantSettleInfo> viewMerchantSettleList(Criteria criteria,RowBounds rd);
	
	/**
	 * 预览清算记录
	 * @param criteria
	 * @param rd
	 * @return
	 */
	public List<MerchantSettleInfo> viewMerchantSettleList(Criteria criteria);
	/**
	 * 统计预览清算表时的清算记录
	 * @param criteria
	 * @return
	 */
	public int countViewMerchantSettleList(Criteria criteria);
	
	/**
	 * 获取批次号
	 * @return
	 */
	public int getMaxBatchNo();
	
	/**
	 * 根据交易日期更新交易记录的清算状态
	 * @param criteria
	 * @return
	 */
	public int updateSettleFlagByTransDate(Criteria criteria);
	
	/**
	 * 保存商户清算记录
	 * @param info
	 * @return
	 */
	public int saveMerchantSettleInfo(@Param("info") MerchantSettleInfo info);
	
	/**
	 * 把交易记录拷贝到已结算交易记录表
	 * @param batchNo
	 * @return
	 */
	public int copyTransToSeteleRecordByBatchNo(@Param("batchNo") String batchNo);
	
	/**
	 * 明细导出
	 * @param merNo
	 * @param terNo
	 * @param batchNo
	 * @return
	 */
	public List<GwSettleTransInfo> queryDetailList(@Param("merNo") String merNo,@Param("terNo") String terNo,@Param("batchNo")String batchNo);
	
	/**
	 * 代理商预览分润
	 * @param criteria
	 * @return
	 */
	public List<AgentSettleInfo> viewAgentSettleInfo(Criteria criteria,RowBounds rd);
	
	/**
	 * 代理商预览分润
	 * @param criteria
	 * @return
	 */
	public int countViewAgentSettleInfo(Criteria criteria);
	
	/**
	 * 创建代理商分润表
	 * @param criteria
	 * @param rd
	 * @return
	 */
	public List<AgentSettleInfo> createAgentSettleInfo(Criteria criteria,RowBounds rd);
	
	/**
	 * 创建代理商分润表
	 * @param criteria
	 * @param rd
	 * @return
	 */
	public List<AgentSettleInfo> createAgentSettleInfo(Criteria criteria);
	/**
	 * 统计代理商分润记录
	 * @param criteria
	 * @return
	 */
	public int countCreateAgentSettleInfo(Criteria criteria);
	
	/**
	 * 获取代理商分润结算最大批次号
	 * @return
	 */
	public int agentSettleMaxBatchNO();
	
	/**
	 * 更新代理划款记录标记
	 * @param criteria
	 * @return
	 */
	public int updateAgentSettleInfoBatchNo(Criteria criteria);
	
	/**
	 * 
	 * @return
	 */
	public int saveAgentSettleInfo(@Param("info") AgentSettleInfo info);
	
	/**
	 * 查询代理商分润列表
	 * @param criteria
	 * @return
	 */
	public List<AgentSettleInfo> queryAgentSettleInfoList(Criteria criteria,RowBounds rd);
	
	/**
	 * 导出分润
	 * @param criteria
	 * @return
	 */
	public List<AgentSettleInfo> queryAgentSettleInfoList(Criteria criteria);
	
	/**
	 * 查询代理商分润列表
	 * @param criteria
	 * @return
	 */
	public int countAgentSettleInfoList(Criteria criteria);
	/**
	 * 统计商户虚拟账户数量
	 * */
	public int countMerchantAccount(Criteria criteria);
	/**
	 * 查询商户虚拟账户
	 * */
	public List<MerchantAccount> queryMerchantAccount(Criteria criteria);
	public List<MerchantAccount> queryMerchantAccount(Criteria criteria,RowBounds rb);
	/**
	 * 统计出入帐数量
	 * */
	public int countMerchantAccountAccess(Criteria criteria);
	/**
	 * 查询商户虚拟账户出入帐明细
	 * */
	public List<MerchantAccountAccess> queryMerchantAccountAccess(Criteria criteria);
	public List<MerchantAccountAccess> queryMerchantAccountAccess(Criteria criteria, RowBounds rb);

	/**
	 * 统计出入帐流水明细数量
	 * */
	public int countMerchantAccountAccessDetail(Criteria criteria);
	/**
	 * 列出虚拟账户出入帐流水明细
	 * */
	public List<MerchantAccountAccessDetail> queryMerchantAccountAccessDetail(Criteria criteria);
	public List<MerchantAccountAccessDetail> queryMerchantAccountAccessDetail(Criteria criteria, RowBounds rb);
	/**
	 * 统计商户结算方式数量
	 * */
	public int countSettleTypeInfo(Criteria criteria);
	/**
	 * 查询商户结算方式
	 * */
	public List<SettleTypeInfo> querySettleTypeInfo(Criteria criteria,RowBounds rb);
	public List<SettleTypeInfo> querySettleTypeInfo(Criteria criteria);
	/**
	 * 查询商户结算条件重复的商户号和终端号
	 * */
	public int queryDuplicateByMerNoAndTerNo(@Param("merNo")String merNo, @Param("terNo")String terNo);
	/**
	 * 添加商户结算条件
	 * */
	public int insertSettleTypeInfo(@Param("info")SettleTypeInfo settleTypeInfo);
	/**
	 * 通过ID查询商户结算条件
	 * */
	public SettleTypeInfo querySettleTypeInfoById(String id);
	/**
	 * 通过ID修改商户结算条件
	 * */
	public int updateSettleTypeInfo(@Param("info")SettleTypeInfo settleTypeInfo);
	/**
	 * 通过商户号和终端号查询商户虚拟账户信息
	 * */
	public MerchantAccount queryMerchantAccountByMerNoAndTerNo(@Param("merNo")String merNo,@Param("terNo")String terNo);
	/**
	 * 插入入账信息
	 * */
	public int insertMerchantAccountAccess(@Param("info")MerchantAccountAccess merchantAccountAccess);
	/**
	 * 更新商户虚拟账户信息
	 * */
	public int updateMerchantAccount(@Param("info")MerchantAccount merchantAccount);
	/**
	 * 通过ID查询商户虚拟账户信息
	 * */
	public MerchantAccount queryMerchantAccountViewById(@Param("id")String id,@Param("accountType")String accountType);
	/**
	 * 通过ID查询商户虚拟账户出入帐数据
	 * */
	public MerchantAccountAccess queryMerchantAccountAccessById(String id);
	/**
	 * 通过ID修改提现记录审核状态
	 * */
	public int updateMerchantAccountAccess(@Param("info")MerchantAccountAccess merchantAccountAccess);
	/**
	 * 通过ID查询账户信息
	 * */
	public MerchantAccount queryMerchantAccountById(String id);
	/**
	 * 插入结算条件历史记录
	 * */
	public int insertSettleTypeLogInfo(@Param("info")SettleTypeInfo old);
	/**
	 * 导出结算明细
	 * */
	public List<ExportInfo> exportSettleDetailInfo(@Param("id")String id,@Param("merNo")String merNo,@Param("terNo")String terNo,@Param("cashType")String cashType);
	
	public List<ExportInfo> exportInfoForCash(@Param("id")String id,@Param("merNo")String merNo,@Param("terNo")String terNo,@Param("cashType")String cashType);
	
	/**
	 * 保证金提现导出明细
	 * @param id 提现批次号
	 * @param merNo
	 * @param terNo
	 * @return
	 */
	public List<ExportInfo> exportInfoForBondCash(@Param("id")String id,@Param("merNo")String merNo,@Param("terNo")String terNo);
	
	/**
	 * 统计账户结算条件条数
	 * */
	public int countExceptionSettleTypeInfo(Criteria criteria);
	/**
	 * 账户结算条件列表显示
	 * */
	public List<ExceptionSettleType> queryExceptionSettleTypeInfo(
			Criteria criteria, RowBounds rb);
	/**
	 * 添加账户结算条件
	 * */
	public int addExceptionSettleTypeInfo(ExceptionSettleType info);
	/**
	 * 校验信息是否重复添加
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
	public int deleteExceptionSettleTypeInfo(@Param("ids")String[] ids);
	/**
	 * 
	 * 导出出款信息
	 * @param criteria
	 * @return
	 */
	public List<MerchantAccountAccess> exportCashInfos(Criteria criteria);
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
	
	public int updateDeductionType(DeductionTypeInfo info);
	
	public int deleteDeductionTypeById(String id);
	
	public DeductionTypeInfo queryDeductionTypeInfoById(String id);
	
	/**
	 * 统计可以手工结算的订单的数量
	 * @param criteria
	 * @return
	 */
	public int countCanHandTransInfo(Criteria criteria);
	/**
	 * 查询可以手工结算的订单
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<TransInfo> queryCanHandTransInfo(Criteria criteria, RowBounds rb);
	/**
	 * 查询信息
	 * @param tradeNos
	 * @return
	 */
	public List<HandTransInfo> queryHandTransInfoByTradeNos(@Param("tradeNos")String[] tradeNos);
	/**
	 * 添加手工结算订单
	 * @param list
	 * @return
	 */
	public int addHandTransInfo(@Param("list")List<HandTransInfo> list);
	/**
	 * 手工结算订单状态
	 * @param criteria
	 * @return
	 */
	public int countHandTransInfo(Criteria criteria);
	/**
	 * 手工结算订单状态
	 * @param criteria
	 * @return
	 */	
	public List<HandTransInfo> queryHandTransInfo(Criteria criteria,
			RowBounds rb);

	/**
	 * 统计商户结算汇总条数
	 * @param criteria
	 * @return
	 */
	public int countPoolSettleInfo(Criteria criteria);
	/**
	 * 查询商户结算汇总
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<PoolSettleInfo> queryPoolSettleInfo(Criteria criteria,
			RowBounds rb);
	public List<PoolSettleInfo> queryPoolSettleInfo(Criteria criteria);
	/**
	 * 查询交易额明细
	 * @param criteria
	 * @return
	 */
	public List<TransInfo> exportPoolSettleInfoForTrans(Criteria criteria);
	
	/**
	 * 导出异常结算明细
	 * @author gaoyuan
	 * @param criteria
	 * @return
	 */
	public List<ExportInfo> exportExceptiontTransInfo(Criteria criteria);

	public void addDisFineSetpInfo(@Param("dfInfo")DisFineStepInfo dfInfo);
	
	/**
	 * 通过ID把复核通过修改成审核通过
	 * */
	public int updateMerchantAccountAccessStatus(@Param("info")MerchantAccountAccess merchantAccountAccess);
}
