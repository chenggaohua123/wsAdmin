package com.gateway.transmgr.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.gateway.api.model.PicInfo;
import com.gateway.bankmgr.model.CurrencyInfo;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.utils.BankCardEncryp;
import com.gateway.common.utils.Funcs;
import com.gateway.common.utils.Tools;
import com.gateway.fraud.model.WhiteListInfo;
import com.gateway.refund.mapper.RefundMapper;
import com.gateway.refund.model.ExceptionFee;
import com.gateway.sysmgr.model.CardBinInfo;
import com.gateway.transmgr.mapper.TransMgrDao;
import com.gateway.transmgr.model.BankSettleDetail;
import com.gateway.transmgr.model.MulTransInfo;
import com.gateway.transmgr.model.SettleTransInfo;
import com.gateway.transmgr.model.TransCallbackInfo;
import com.gateway.transmgr.model.TransChangeInfo;
import com.gateway.transmgr.model.TransCount;
import com.gateway.transmgr.model.TransCountModel;
import com.gateway.transmgr.model.TransDetailInfo;
import com.gateway.transmgr.model.TransInfo;
import com.gateway.transmgr.model.TransLogInfo;
import com.gateway.transmgr.model.TransRecordInfo;
import com.gateway.transmgr.model.WhiteDishonorInfo;

@Service
public class TransMgrServiceImpl implements TransMgrService{
	
	@Autowired
	private TransMgrDao transMgrDao;
	@Autowired
	private RefundMapper refundMapper;

	@Override
	public PageInfo<TransInfo> getTransList(Criteria criteria) {
		PageInfo<TransInfo> page = new PageInfo<TransInfo>(criteria.getPageNum(), criteria.getPageSize());
		
		page.setTotal(transMgrDao.countTransList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		
		List<TransInfo> list = transMgrDao.getTransList(criteria, rb);
		for(TransInfo li:list){
			li.setWebInfo(Tools.parseWebInfoToResourceType(li.getWebInfo()));
			if(li.getCheckNo()!=null&&!"".equals(li.getCheckNo())){
				String cardNo;
				try {
					cardNo = Funcs.decrypt(li.getCheckNo())+"****"+Funcs.decrypt(li.getLast());
					li.setSixAndFourCardNo(cardNo);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		page.setData(list);
		return page;
	}
	
	@Override
	public List<TransInfo> getTransAmountList(Criteria criteria){
		//TransCount transCount = transMgrDao.queryTransCountInfo();
		//transCount
		List<TransInfo> info=transMgrDao.getTransAmountList(criteria);
		return info;
	}
	
	@Override
	public TransCount queryTransCount(Criteria criteria){
		//获取交易总数
		TransCount transCount = transMgrDao.queryTransCountInfo(criteria);
		transCount.setLabelMoney(getCountInfo("LabelMoney",criteria));//标签总金额
		transCount.setCountMoney(getCountInfo("CountMoney",criteria));//总金额
		transCount.setSettleMoney(getCountInfo("SettleMoney",criteria));//结算金额
		return transCount;
	}
	
	private List<TransCountModel> getCountInfo(String type,Criteria criteria){
		TransCountModel transCountModel = new TransCountModel();
		String sql = null;
		String where = null;
		if(type.equals("LabelMoney")){//标签总金额
			sql = "SELECT merBusCurrency currency, sum(merTransAmount) AS countMoney,sum(case when respCode='00' then merTransAmount else 0 end )as countSuccessMoney FROM ";
			where = " GROUP BY merBusCurrency ";
		}
		if(type.equals("CountMoney")){//总金额
			sql = "SELECT bankCurrency currency,sum(case when respCode='00' then bankTransAmount else 0 end )as countSuccessMoney FROM ";
			where = " GROUP BY bankCurrency ";
		}
		if(type.equals("SettleMoney")){//结算金额
			sql="SELECT merSettleCurrency currency,sum(case when respCode='00' then merSettleAmount else 0 end )as countSuccessMoney  FROM ";
			where=" GROUP BY merSettleCurrency ";
		}
		if(StringUtils.isEmpty(sql)){
			return null;
		}
		transCountModel.setWhere(where);
		transCountModel.setSql(sql);
		List<TransCountModel> list = transMgrDao.queryGroupCountInfo(transCountModel,criteria);
		return list;
	}
	
	@Override
	public TransInfo queryTransDetailByTradeNo(String tradeNo) {
		TransInfo transInfo = transMgrDao.queryTransDetailByTradeNo(tradeNo);
		if(StringUtils.isEmpty(transInfo)){
			return null;
		}
		/**
		 * 更新异常记录
		 * */
		ExceptionFee ef=refundMapper.queryExceptionFeeInfoByTradeNo(tradeNo);
		if(ef!=null){
			transInfo.setRefundFee(ef.getRefundFee());
			transInfo.setDisFee(ef.getDisFee());
			transInfo.setTransRefundFeeStatus(ef.getTransRefundFeeStatus());
		}
		transInfo.setWebInfo(Tools.parseWebInfoToResourceType(transInfo.getWebInfo()));
		if(null != transInfo){
			PicInfo picInfo = transMgrDao.queryTransSignInfoByTradeNo(tradeNo);
			if(null != picInfo){
				transInfo.setPicInfo(picInfo);
			}
		}
		return transInfo;
	}
	
	
	@Override
	public TransInfo queryTransInfoByTradeNo(String tradeNo) {
		TransInfo info=transMgrDao.queryTransListByTradeNo(tradeNo);
		info.setGoodsInfos(transMgrDao.queryGoodsInfoByTradeNo(tradeNo));
		return info; 
	}
	@Override
	public List<TransInfo> queryTransDetailByRelNo(String relNo) {
		return transMgrDao.queryTransDetailByRelNo(relNo);
	}

	@Override
	public List<TransInfo> exportTransList(Criteria criteria) {
		return transMgrDao.exportTransList(criteria);
	}

	@Override
	public PageInfo<SettleTransInfo> getSettleTransList(Criteria criteria) {
		PageInfo<SettleTransInfo> page = new PageInfo<SettleTransInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(transMgrDao.countSettleTransList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<SettleTransInfo> list = transMgrDao.getSettleTransList(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public SettleTransInfo querySettleTransDetailByTradeNo(String tradeNo) {
		SettleTransInfo transInfo = transMgrDao.querySettleTransDetailByTradeNo(tradeNo);
		if(null != transInfo){
			PicInfo picInfo = transMgrDao.queryTransSignInfoByTradeNo(tradeNo);
			if(null != picInfo){
				transInfo.setPicInfo(picInfo);
			}
		}
		return transInfo;
	}

	@Override
	public List<SettleTransInfo> querySettleTransDetailByRelNo(String relNo) {
		return transMgrDao.querySettleTransDetailByRelNo(relNo);
	}

	@Override
	public List<SettleTransInfo> exportSettleTransList(Criteria criteria) {
		return transMgrDao.getSettleTransList(criteria);
	}

	@Override
	public List<SettleTransInfo> getSettleTransAmount(Criteria criteria) {
		return transMgrDao.getSettleTransAmount(criteria);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int saveBankSettleDetail(List<BankSettleDetail> list) {
		Map<String, String> bankMerNoList = new HashMap<String, String>();
		Map<String, String> noMatchNobankMerNoList = new HashMap<String, String>();
		//获取当前的最大的批次号
		int batchNo = transMgrDao.getBankSettleMaxBatchNo();
		if(0 == batchNo){
			batchNo = 10000;
		}else{
			batchNo++;
		} 
		int count = 0;
		for(BankSettleDetail info :list){
			if(bankMerNoList.get(info.getMerchantNo()) == null){
				if(noMatchNobankMerNoList.get(info.getMerchantNo()) != null){
					continue;
				}
				List<CurrencyInfo> currencyList = transMgrDao.queryCurrencyInfoByMerNoAndTerNo(info.getMerchantNo(), info.getTerNo());
				if(null == currencyList || currencyList.size()<=0){
					noMatchNobankMerNoList.put(info.getMerchantNo(), info.getMerchantNo());
					continue;
				}else{
					bankMerNoList.put(info.getMerchantNo(), info.getMerchantNo());
				}
			}
			info.setBatchNo(batchNo);
			count += transMgrDao.saveBankSettleDetail(info);
		}
		return count;
	}

	@Override
	public PageInfo<BankSettleDetail> queryBankSettleDetail(Criteria criteria) {
		PageInfo<BankSettleDetail> page = new PageInfo<BankSettleDetail>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(transMgrDao.countBankSettleDetail(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<BankSettleDetail> list = transMgrDao.queryBankSettleDetail(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int updateCheckStatusSucceed(String[] ids) {
		return transMgrDao.updateCheckStatusSucceed(ids);
	}

	
	@Override
	public PageInfo<TransRecordInfo> getTransRecordList(Criteria criteria) {
		PageInfo<TransRecordInfo> page=new PageInfo<TransRecordInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(transMgrDao.countTransRecordInfo(criteria));
		RowBounds rb=new RowBounds(page.getOffset(), page.getPageSize());
		List<TransRecordInfo> list=transMgrDao.queryTransRecordInfo(criteria,rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public List<TransRecordInfo> queryTransRecordInfo(Criteria criteria){
		return transMgrDao.queryTransRecordInfo(criteria);
	}

	@Override
	public int insertTransInfo(TransInfo info) {
		
		return transMgrDao.insertTransInfo(info);
	}
	
	@Override
	public PageInfo<MulTransInfo> getMulTransInfoList(Criteria criteria) {
		PageInfo<MulTransInfo> page=new PageInfo<MulTransInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(transMgrDao.countMulTransInfo(criteria));
		RowBounds rb=new RowBounds(page.getOffset(), page.getPageSize());
		List<MulTransInfo> list=transMgrDao.queryMulTransInfo(criteria,rb);
		page.setData(list);
		return page;
	}

	@Override
	public TransDetailInfo queryTransInfo(String tradeNo) throws Exception{
		TransDetailInfo info = transMgrDao.queryTransInfo(tradeNo);
		if(StringUtils.isEmpty(info)){
			return new TransDetailInfo();
		}
		BigDecimal rate =new BigDecimal("0");
		try {
			rate= new BigDecimal(info.getBankTransAmount()).divide(new BigDecimal(info.getMerTransAmount()),6,BigDecimal.ROUND_HALF_UP);
		} catch (Exception e) {
		}
		info.setTransRate(rate.toString());
		info.setWebInfo(Tools.parseWebInfoToResourceType(info.getWebInfo()));
		String refundStatusTemp="1".equals(info.getRefundStatus())?"已退款":"未退款";
		info.setRefundStatus(refundStatusTemp);
		String dishonorStatusTemp="1".equals(info.getDishonorStatus())?"已拒付":"未拒付";
		info.setDishonorStatus(dishonorStatusTemp);
		String frozenStatusTemp="1".equals(info.getFrozenStatus())?"已冻结":"未冻结";
		info.setFrozenStatus(frozenStatusTemp);
		if(info.getCheckNo()!=null&&!"".equals(info.getCheckNo())){
		info.setSixAndFourCardNo(Funcs.decrypt(info.getCheckNo())+"***"+Funcs.decrypt(info.getLast()));
		}else{
			info.setSixAndFourCardNo("");
		}
		if(info.getCheckNo()!=null&&!"".equals(info.getCheckNo())){
			info.setCardFullNo(Funcs.decrypt(info.getCheckNo())+Funcs.decrypt(info.getMiddle())+Funcs.decrypt(info.getLast()));
			}else{
				info.setCardFullNo("");
			}
		return info;
	}
	@Override
	public List<TransDetailInfo> queryTransInfoByTradeNos(List<String> tradeNos) {
		return  transMgrDao.queryTransInfoByTradeNos(tradeNos);
	}


	@Override
	public List<TransInfo> exportTransByType(String ids) {
		String[] id=ids.split(",");
		List<TransInfo> TransInfoByType=new ArrayList<TransInfo>();
		for(int i=0;i<id.length;i++){
			TransInfoByType.add(transMgrDao.exportTransByType(id[i]));
		}
		return TransInfoByType;
		
	}

	@Override
	public List<TransInfo> queryTransByType(Criteria criteria) {
		//List<TransInfo> transInfoList=new ArrayList<TransInfo>();
		List<TransInfo> transInfo=transMgrDao.queryTransByType(criteria);
		for(TransInfo info:transInfo){
			String check=info.getCheckNo();
			String last=info.getLast();
			try {
				if(info.getCheckNo()!=null&&!"".equals(info.getCheckNo())){
					info.setSixAndFourCardNo(Funcs.decrypt(check)+"****"+Funcs.decrypt(last));
					
				}else{
					info.setSixAndFourCardNo("");
				}
				
			} catch (IOException e) {
			
				e.printStackTrace();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		return transInfo;
	}

	@Override
	public int batchAcceptCheckUpdateById(String[] ids) {
			
		return transMgrDao.batchAcceptCheckUpdateById(ids);
	}

	@Override
	public String checkAcceptDate(String[] ids) {
		String str = "";
		for(String id:ids){
			int i = transMgrDao.checkAcceptDate(id);
			if(0 < i){
				str += id + ",";
			}
		}
		return str;
	}

	
	@Override
	public List<String> checkDuplicateTrans(List<TransInfo> list) {
		return transMgrDao.checkDuplicateTrans(list);
	}
	
	@Override
	public int countTransInfo(Criteria criteria) {
		return transMgrDao.countTransList(criteria);
	}
	@Override
	public PageInfo<TransLogInfo> listTransLogInfo(Criteria criteria) {
		PageInfo<TransLogInfo> page=new PageInfo<TransLogInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(transMgrDao.countTransLogInfo(criteria));
		RowBounds rb=new RowBounds(page.getOffset(), page.getPageSize());
		List<TransLogInfo> list=transMgrDao.queryTransLogInfo(criteria,rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public List<TransLogInfo> exportTransLogInfos(Criteria criteria) {
		return transMgrDao.queryTransLogInfo(criteria);
	}
	
	@Override
	public CardBinInfo queryCardBinInfoByBin(String bin) {
		return transMgrDao.queryCardBinInfoByBin(bin);
	}
	
	@Override
	public PageInfo<TransCallbackInfo> queryTransCallbackInfoList(
			Criteria criteria) {
		PageInfo<TransCallbackInfo> page = new PageInfo<TransCallbackInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(transMgrDao.queryTransCallbackInfoCount(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), page.getPageSize());
		List<TransCallbackInfo> list = transMgrDao.queryTransCallbackInfoList(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public int saveTransCallbackInfo(TransCallbackInfo info) {
		return transMgrDao.saveTransCallbackInfo(info);
	}

	@Override
	public List<TransCallbackInfo> queryExportTransCallbackInfoList(
			Criteria criteria) {
		return transMgrDao.queryTransCallbackInfoList(criteria);
	}
	
	@Override
	public PageInfo<WhiteDishonorInfo> queryWhiteTransDishonorInfoList(
			Criteria criteria) {
		List<WhiteListInfo> whites = transMgrDao.querWhiteNotEncryptCheckNoInfoList();
		if(whites!=null && whites.size()>0){
			for(WhiteListInfo info : whites){
				try {
					info.setCheckNo(BankCardEncryp.eccryptSHA(info.getBlackText()));
					transMgrDao.updateWhiteCheckNoInfo(info);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			}
		}
		PageInfo<WhiteDishonorInfo> page = new PageInfo<WhiteDishonorInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(transMgrDao.queryWhiteTransDishonorCount(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), page.getPageSize());
		List<WhiteDishonorInfo> list = transMgrDao.queryWhiteTransDishonorInfoList(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public List<WhiteListInfo> queryWhiteInfoListByIds(String whiteIds) {
		return transMgrDao.queryWhiteInfoListByIds(whiteIds);
	}

	@Override
	public List<WhiteDishonorInfo> queryExportWhiteTransDishonorInfoList(
			Criteria criteria) {
		return transMgrDao.queryWhiteTransDishonorInfoList(criteria);
	}
	
	@Override
	public void updateCheckedFailTransIds(List<Integer> checkedFailTransId,String name) {
		List<String> list=transMgrDao.queryTradeNosByIds(checkedFailTransId);
		//存储勾兑成功的
		for(String tradeNo:list){
			transMgrDao.insertTransChangeInfo(tradeNo,name,"0");
		}
	}
	@Override
	public PageInfo<TransChangeInfo> getTransChangeInfoList(Criteria criteria) {
		PageInfo<TransChangeInfo> page = new PageInfo<TransChangeInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(transMgrDao.queryTransChangeInfoCount(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), page.getPageSize());
		List<TransChangeInfo> list = transMgrDao.queryTransChangeInfoList(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public int updateAutoCodeByTradeNo(String tradeNo, String autoCode) {
		return transMgrDao.updateAutoCodeByTradeNo(tradeNo, autoCode);
	}
	
	
	
}
