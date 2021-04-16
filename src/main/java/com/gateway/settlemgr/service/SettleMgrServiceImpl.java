package com.gateway.settlemgr.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.ServiceException;
import com.gateway.settlemgr.mapper.SettleMgrMapper;
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

@Service
public class SettleMgrServiceImpl implements SettleMgrService{
	@Autowired
	private SettleMgrMapper settleMgrMapper;
	
	
	private static int DEFAUT_BATCHNO = 100000; 
	
	public SettleMgrMapper getSettleMgrMapper() {
		return settleMgrMapper;
	}
	public void setSettleMgrMapper(SettleMgrMapper settleMgrMapper) {
		this.settleMgrMapper = settleMgrMapper;
	}

	/**
	 * 通过ID查询账户信息
	 * */
	public MerchantAccount queryMerchantAccountById(String id){
		return settleMgrMapper.queryMerchantAccountById(id);
	}

	@Override
	public PageInfo<MerchantSettleInfo> getMerchantSettleInfo(Criteria criteria) {
		PageInfo<MerchantSettleInfo> page = new PageInfo<MerchantSettleInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(settleMgrMapper.countListMerchantSettleInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<MerchantSettleInfo> list = settleMgrMapper.getMerchantSettleInfo(criteria, rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public List<MerchantSettleInfo> exportMerchantSettleInfo(Criteria criteria) {
		return settleMgrMapper.getMerchantSettleInfo(criteria);
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String createMerchantSettleList(Criteria criteria) {
		//生成批次号
		int batchNo = settleMgrMapper.getMaxBatchNo();
		if(0 == batchNo){
			batchNo = DEFAUT_BATCHNO;
		}
		batchNo++;
		criteria.getCondition().put("batchNo", String.valueOf(batchNo));
		//统计指定日期的交易信息
		List<MerchantSettleInfo> list = settleMgrMapper.viewMerchantSettleList(criteria);
		//插入清算记录表
		for(MerchantSettleInfo info :list){
			info.setBatchNo(String.valueOf(batchNo));
			info.setSettleDate((Timestamp)criteria.getCondition().get("settleDate"));
			settleMgrMapper.saveMerchantSettleInfo(info);
		}
		//更新交易表的清算状态
		settleMgrMapper.updateSettleFlagByTransDate(criteria);
		//把当前的批次的交易记录移到已清算的交易表
		settleMgrMapper.copyTransToSeteleRecordByBatchNo(String.valueOf(batchNo));
		return String.valueOf(batchNo);
	}
	@Override
	public PageInfo<MerchantSettleInfo> viewMerchantSettleList(Criteria criteria) {
		PageInfo<MerchantSettleInfo> page = new PageInfo<MerchantSettleInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(settleMgrMapper.countViewMerchantSettleList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<MerchantSettleInfo> list = settleMgrMapper.viewMerchantSettleList(criteria, rb);
		page.setData(list);
		return page;
	}
	@Override
	public List<GwSettleTransInfo> queryDetailList(String merNo, String terNo,
			String batchNo) {
		return settleMgrMapper.queryDetailList(merNo, terNo, batchNo);
	}
	@Override
	public PageInfo<AgentSettleInfo> viewAgentSettleInfo(Criteria criteria) {
		PageInfo<AgentSettleInfo> page = new PageInfo<AgentSettleInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(settleMgrMapper.countViewAgentSettleInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<AgentSettleInfo> list = settleMgrMapper.viewAgentSettleInfo(criteria, rb);
		page.setData(list);
		return page;
	}
	@Override
	public PageInfo<AgentSettleInfo> createAgentSettleInfo(Criteria criteria) {
		PageInfo<AgentSettleInfo> page = new PageInfo<AgentSettleInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(settleMgrMapper.countCreateAgentSettleInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<AgentSettleInfo> list = settleMgrMapper.createAgentSettleInfo(criteria, rb);
		page.setData(list);
		return page;
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String effectAgentSettleInfo(Criteria criteria) throws ServiceException{
		//生成批次号
		int batchNo = settleMgrMapper.agentSettleMaxBatchNO();
		if(0 == batchNo){
			batchNo = DEFAUT_BATCHNO;
		}
		batchNo++;
		criteria.getCondition().put("batchNo", String.valueOf(batchNo));
		//插入代理结算记录
		List<AgentSettleInfo> list = settleMgrMapper.createAgentSettleInfo(criteria);
		if(null != list && list.size() > 0){
			for(AgentSettleInfo info:list){
				info.setSettleBatchNo(String.valueOf(batchNo));
				String transDate = criteria.getCondition().get("transDate").toString();
				try {
					info.setSettleDate(new Timestamp(new SimpleDateFormat("yyyy-MM").parse(transDate).getTime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				settleMgrMapper.saveAgentSettleInfo(info);
			}
		}else{
			throw new ServiceException("没有可以清算的数据.");
		}
		//修改商户结算记录的代理批次号
		settleMgrMapper.updateAgentSettleInfoBatchNo(criteria);
		return String.valueOf(batchNo);
	}
	@Override
	public PageInfo<AgentSettleInfo> queryAgentSettleInfoList(Criteria criteria) {
		PageInfo<AgentSettleInfo> page = new PageInfo<AgentSettleInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(settleMgrMapper.countAgentSettleInfoList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<AgentSettleInfo> list = settleMgrMapper.queryAgentSettleInfoList(criteria, rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public List<AgentSettleInfo> exportAgentSettleInfoList(Criteria criteria) {
		return settleMgrMapper.queryAgentSettleInfoList(criteria);
	}
	@Override
	public PageInfo<MerchantAccount> listMerchantAccount(Criteria criteria) {
		PageInfo<MerchantAccount> page=new PageInfo<MerchantAccount>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(settleMgrMapper.countMerchantAccount(criteria));
		RowBounds rb =new RowBounds(page.getOffset(),page.getPageSize());
		List<MerchantAccount> list= settleMgrMapper.queryMerchantAccount(criteria,rb);
		page.setData(list);
		return page;
	}
	@Override
	public PageInfo<MerchantAccountAccess> listMerchantAccountAccess(
			Criteria criteria) {
		PageInfo<MerchantAccountAccess> page=new PageInfo<MerchantAccountAccess>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(settleMgrMapper.countMerchantAccountAccess(criteria));
		RowBounds rb =new RowBounds(page.getOffset(),page.getPageSize());
		List<MerchantAccountAccess> list= settleMgrMapper.queryMerchantAccountAccess(criteria,rb);
		page.setData(list);
		return page;
	}
	@Override
	public List<MerchantAccountAccess> exportListMerchantAccountInAndOut(
			Criteria criteria) {
		List<MerchantAccountAccess> list= settleMgrMapper.queryMerchantAccountAccess(criteria);
		return list;
	}
	@Override
	public PageInfo<MerchantAccountAccessDetail> listMerchantAccountAccessDetail(Criteria criteria) {
		PageInfo<MerchantAccountAccessDetail> page=new PageInfo<MerchantAccountAccessDetail>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(settleMgrMapper.countMerchantAccountAccessDetail(criteria));
		RowBounds rb =new RowBounds(page.getOffset(),page.getPageSize());
		List<MerchantAccountAccessDetail> list= settleMgrMapper.queryMerchantAccountAccessDetail(criteria,rb);
		page.setData(list);
		return page;
	}
	@Override
	public PageInfo<SettleTypeInfo> listSettleTypeInfo(Criteria criteria) {
		PageInfo<SettleTypeInfo> page=new PageInfo<SettleTypeInfo>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(settleMgrMapper.countSettleTypeInfo(criteria));
		RowBounds rb =new RowBounds(page.getOffset(),page.getPageSize());
		List<SettleTypeInfo> list= settleMgrMapper.querySettleTypeInfo(criteria,rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public int queryDuplicateByMerNoAndTerNo(String merNo, String terNo) {
		return settleMgrMapper.queryDuplicateByMerNoAndTerNo(merNo, terNo);
	}
	@Override
	public int insertSettleTypeInfo(SettleTypeInfo settleTypeInfo) {
		return settleMgrMapper.insertSettleTypeInfo(settleTypeInfo);
	}
	@Override
	public SettleTypeInfo querySettleTypeInfoById(String id) {
		return settleMgrMapper.querySettleTypeInfoById(id);
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int updateSettleTypeInfo(SettleTypeInfo settleTypeInfo) {
		int count=0;
		SettleTypeInfo old=settleMgrMapper.querySettleTypeInfoById(settleTypeInfo.getId());
		settleMgrMapper.insertSettleTypeLogInfo(old);
		count+=settleMgrMapper.updateSettleTypeInfo(settleTypeInfo);
//		if(settleTypeInfo.getFrozenPercent().doubleValue()!=0){
//			MerchantAccount temp=settleMgrMapper.queryMerchantAccountByMerNoAndTerNo(settleTypeInfo.getMerNo(),settleTypeInfo.getTerNo());
//			MerchantAccountAccess merchantAccountAccess=new MerchantAccountAccess();
//			merchantAccountAccess.setId(Tools.getAccessId());
//			merchantAccountAccess.setAccountID(temp.getId());
//			merchantAccountAccess.setCurrency(temp.getCurrency());
//			merchantAccountAccess.setFrozenAmount(settleTypeInfo.getFrozenPercent().multiply(temp.getCashAmount()));
//			merchantAccountAccess.setCashAmount(settleTypeInfo.getFrozenPercent().multiply(temp.getCashAmount()).multiply(new BigDecimal(-1)));
//			merchantAccountAccess.setCashType(4);
//			merchantAccountAccess.setRemark("结算条件变更");
//			merchantAccountAccess.setCreateBy(settleTypeInfo.getLastModify());
//			
//			MerchantAccount merchantAccount=new MerchantAccount();
//			merchantAccount.setId(temp.getId());
//			merchantAccount.setFrozenAmount(merchantAccountAccess.getFrozenAmount());
//			merchantAccount.setCashAmount(merchantAccountAccess.getCashAmount());
//			count+=settleMgrMapper.insertMerchantAccountAccess(merchantAccountAccess);
//			count+=settleMgrMapper.updateMerchantAccount(merchantAccount);
//		}
		return count;
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public MerchantAccount queryMerchantAccountById(String id,String accountType) {
		return settleMgrMapper.queryMerchantAccountViewById(id,accountType);
	}
	@Override
	public int insertMerchantAccountAccess(MerchantAccountAccess merchantAccountAccess,MerchantAccount merchantAccount) {
		settleMgrMapper.updateMerchantAccount(merchantAccount);
		return settleMgrMapper.insertMerchantAccountAccess(merchantAccountAccess);
	}
	
	@Override
	public MerchantAccountAccess queryMerchantAccountAccessById(String id) {
		return settleMgrMapper.queryMerchantAccountAccessById(id);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int updateMerchantAccountAccess(MerchantAccountAccess merchantAccountAccess) throws ServiceException {
//		MerchantAccount merchantAccount =new MerchantAccount();
//		merchantAccount.setId(merchantAccountAccess.getAccountID());
//		if("1".equals(merchantAccountAccess.getStatus())){
//			if(merchantAccountAccess.getCashType()==3){
//				merchantAccount.setCashAmount(merchantAccountAccess.getFrozenAmount().multiply(new BigDecimal(-1)));
//			}
//		}else if("2".equals(merchantAccountAccess.getStatus())){
//			if(merchantAccountAccess.getCashType()==1){
//				merchantAccount.setCashAmount(merchantAccountAccess.getCashAmount().multiply(new BigDecimal(-1)));
//				merchantAccount.setTotalAmount(merchantAccountAccess.getTotalAmount().multiply(new BigDecimal(-1)));
//			}
//			if(merchantAccountAccess.getCashType()==2){
//				merchantAccount.setBondAmount(merchantAccountAccess.getBondAmount().multiply(new BigDecimal(-1)));
//				merchantAccount.setBondCashAmount(merchantAccountAccess.getBondCashAmount().multiply(new BigDecimal(-1)));
//			}
//			if(merchantAccountAccess.getCashType()==3){
//				merchantAccount.setFrozenAmount(merchantAccountAccess.getFrozenAmount().multiply(new BigDecimal(-1)));
//			}
//		}else{
//			throw new ServiceException("审核状态错误!");
//		}
//		settleMgrMapper.updateMerchantAccount(merchantAccount);
//		return settleMgrMapper.updateMerchantAccountAccess(merchantAccountAccess);
		return 0;
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int checkMerchantAccountAccess(MerchantAccountAccess ma) throws ServiceException {
		// 提现状态:0待审核 1审核通过 2审核不通过 3复核通过 4复核驳回 5已出款
		if("1".equals(ma.getStatus())||"3".equals(ma.getStatus())||"5".equals(ma.getStatus())){
			return settleMgrMapper.updateMerchantAccountAccess(ma);
		}else{
			MerchantAccount mat=setRollBackMerchantAccount(ma);
			settleMgrMapper.updateMerchantAccount(mat);
			int i=settleMgrMapper.updateMerchantAccountAccess(ma);	
			if(i<=0){
				throw new ServiceException("状态错误");
			}
			return 	i;
		}
	}
	public MerchantAccount setRollBackMerchantAccount(MerchantAccountAccess ma){
		MerchantAccountAccess maa=settleMgrMapper.queryMerchantAccountAccessById(ma.getId());
		MerchantAccount mat=new MerchantAccount();
		mat.setId(maa.getAccountId());
		if(1==maa.getCashType()||3==maa.getCashType()||4==maa.getCashType()||5==maa.getCashType()||6==maa.getCashType()){
			mat.setTotalAmount(maa.getAmount().multiply(new BigDecimal(-1)));
			mat.setCashAmount(maa.getAmount().multiply(new BigDecimal(-1)));
		}
		if(2==maa.getCashType()||7==maa.getCashType()||8==maa.getCashType()||9==maa.getCashType()||10==maa.getCashType()){
			mat.setBondCashAmount(maa.getAmount().multiply(new BigDecimal(-1)));
			mat.setBondAmount(maa.getAmount().multiply(new BigDecimal(-1)));
		}
		if(3==maa.getCashType()){
			mat.setFrozenAmount(maa.getAmount());
		}else if(4==maa.getCashType()){
			mat.setFrozenAmount(maa.getAmount());
		}else if(9==maa.getCashType()){
			mat.setBondFrozenAmount(maa.getAmount());
		}else if(10==maa.getCashType()){
			mat.setBondFrozenAmount(maa.getAmount());
		}
		return mat;
	}
	@Override
	public List<ExportInfo> exportInfo(String id,String merNo,String terNo,String cashType) {
		List<ExportInfo> list=settleMgrMapper.exportSettleDetailInfo(id,merNo,terNo,cashType);
		return list;
	}
	@Override
	public List<ExportInfo> exportInfoForCash(String id, String merNo,
			String terNo, String cashType) {
		List<ExportInfo> list=settleMgrMapper.exportInfoForCash(id,merNo,terNo,cashType);
		return list;
	}
	
	@Override
	public List<ExportInfo> exportInfoForBondCash(String id, String merNo, String terNo) {
		List<ExportInfo> list=settleMgrMapper.exportInfoForBondCash(id,merNo,terNo);
		return list;
	}

	@Override
	public PageInfo<ExceptionSettleType> queryExceptionSettleType(
			Criteria criteria) {
		PageInfo<ExceptionSettleType> page=new PageInfo<ExceptionSettleType>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(settleMgrMapper.countExceptionSettleTypeInfo(criteria));
		RowBounds rb =new RowBounds(page.getOffset(),page.getPageSize());
		List<ExceptionSettleType> list= settleMgrMapper.queryExceptionSettleTypeInfo(criteria,rb);
		page.setData(list);
		return page;
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int addExceptionSettleTypeInfo(ExceptionSettleType info,DisFineStepInfo dfInfo) {
		if(info.getGatherType()==2&&(info.getIsAllOrOver()==2||info.getIsAllOrOver()==3)){//拒付罚金阶梯收取
			settleMgrMapper.addDisFineSetpInfo(dfInfo);
			info.setStepId(dfInfo.getId());
		}
		return settleMgrMapper.addExceptionSettleTypeInfo(info);
	}
	@Override
	public int queryDuplicateExceptionSettleTypeInfo(ExceptionSettleType info) {
		return settleMgrMapper.queryDuplicateExceptionSettleTypeInfo(info);
	}
	@Override
	public ExceptionSettleType queryExceptionSettleTypeInfoById(String id) {
		return settleMgrMapper.queryExceptionSettleTypeInfoById(id);
	}
	@Override
	public int updateExceptionSettleTypeInfo(ExceptionSettleType info) {
		return settleMgrMapper.updateExceptionSettleTypeInfo(info);
	}
	@Override
	public int deleteExceptionSettleTypeInfo(String[] ids) {
		return settleMgrMapper.deleteExceptionSettleTypeInfo(ids);
	}
	
	@Override
	public List<MerchantAccountAccess> exportCashInfos(Criteria criteria) {
		return settleMgrMapper.exportCashInfos(criteria);
	}
	
	@Override
	public List<MerchantAccount> exportMerchantAccountInfo(Criteria criteria) {
		return settleMgrMapper.queryMerchantAccount(criteria);
	}
	
	@Override
	public List<SettleTypeInfo> ExportSettleTypeInfo(Criteria criteria) {
		return settleMgrMapper.querySettleTypeInfo(criteria);
	}
	
	@Override
	public int addDeductionType(String deductionType) {
		return settleMgrMapper.addDeductionType(deductionType);
	}
	
	@Override
	public List<DeductionTypeInfo> queryDeductionTypeInfo(Criteria criteria) {
		return settleMgrMapper.queryDeductionTypeInfo(criteria);
	}
	@Override
	public int deleteDeductionTypeById(String id) {
		return settleMgrMapper.deleteDeductionTypeById(id);
	}
	@Override
	public DeductionTypeInfo queryDeductionTypeInfoById(String id) {
		return settleMgrMapper.queryDeductionTypeInfoById(id);
	}
	@Override
	public int updateDeductionType(DeductionTypeInfo info) {
		return settleMgrMapper.updateDeductionType(info);
	}
	@Override
	public PageInfo<TransInfo> queryCanHandTransInfo(Criteria criteria) {
		PageInfo<TransInfo> page=new PageInfo<TransInfo>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(settleMgrMapper.countCanHandTransInfo(criteria));
		RowBounds rb =new RowBounds(page.getOffset(),page.getPageSize());
		List<TransInfo> list= settleMgrMapper.queryCanHandTransInfo(criteria,rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public List<HandTransInfo> queryHandTransInfoByTradeNos(String[] tradeNos) {
		return settleMgrMapper.queryHandTransInfoByTradeNos(tradeNos);
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int addHandTransInfo(List<HandTransInfo> list) throws Exception {
		int i=0;
		i=settleMgrMapper.addHandTransInfo(list);
		return i;
	}
	@Override
	public PageInfo<HandTransInfo> queryHandTransInfo(Criteria criteria) {
		PageInfo<HandTransInfo> page=new PageInfo<HandTransInfo>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(settleMgrMapper.countHandTransInfo(criteria));
		RowBounds rb =new RowBounds(page.getOffset(),page.getPageSize());
		List<HandTransInfo> list= settleMgrMapper.queryHandTransInfo(criteria,rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public PageInfo<PoolSettleInfo> queryPoolSettleInfo(Criteria criteria) {
		PageInfo<PoolSettleInfo> page=new PageInfo<PoolSettleInfo>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(settleMgrMapper.countPoolSettleInfo(criteria));
		RowBounds rb =new RowBounds(page.getOffset(),page.getPageSize());
		List<PoolSettleInfo> list= settleMgrMapper.queryPoolSettleInfo(criteria,rb);
		page.setData(list);
		return page;
	}
	@Override
	public PoolSettleInfo queryTotalPoolSettleInfo(Criteria criteria) {
		List<PoolSettleInfo> list= settleMgrMapper.queryPoolSettleInfo(criteria);
		PoolSettleInfo total=new PoolSettleInfo();
		total.setTotalAmount(new BigDecimal("0"));
		for(PoolSettleInfo info:list){
			total.setCurrency(info.getCurrency());
			BigDecimal balance=info.getSettleAmount().
					subtract(info.getFeeAmount()).
					subtract(info.getSingleAmount()).
					add(info.getRefundAmount()).
					add(info.getDisAmount()).
					add(info.getThawAmount()).
					add(info.getFroznAmount()).
					add(info.getCashAmount())
					.add(info.getOtherAmount());
			total.setTotalAmount(total.getTotalAmount().add(balance));
		}
		return total;
	}
	@Override
	public List<PoolSettleInfo> exportPoolSettleInfo(Criteria criteria) {
		return settleMgrMapper.queryPoolSettleInfo(criteria);
	}
	@Override
	public List<TransInfo> exportPoolSettleInfoForTrans(Criteria criteria) {
		return settleMgrMapper.exportPoolSettleInfoForTrans(criteria);
	}
	@Override
	public List<ExportInfo> exportExceptiontTransInfo(Criteria criteria) {
		return settleMgrMapper.exportExceptiontTransInfo(criteria);
	}
	@Override
	public int updateMerchantAccountAccessStatus(
			MerchantAccountAccess merchantAccountAccess) {
		return settleMgrMapper.updateMerchantAccountAccessStatus(merchantAccountAccess);
	}
}
