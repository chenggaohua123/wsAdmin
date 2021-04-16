package com.gateway.transchangemgr.service;


import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.ServiceException;
import com.gateway.transchangemgr.mapper.TransChangeMapper;
import com.gateway.transchangemgr.model.RefuseInfo;
import com.gateway.transchangemgr.model.TransCheckInfo;
import com.gateway.transmgr.mapper.TransMgrDao;
import com.gateway.transmgr.model.TransInfo;
@Service
public class TransChangeServiceImpl implements TransChangeService {
	@Autowired
	private TransChangeMapper transChangeMapper;
	@Override
	public PageInfo<TransCheckInfo> queryCheckTransInfo(Criteria criteria) {
		PageInfo<TransCheckInfo> page=new PageInfo<TransCheckInfo>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(transChangeMapper.countTransCheckInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		page.setData(transChangeMapper.queryCheckTransInfo(criteria,rb));
		return page;
	}

	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int saveTransCheckDetail(List<TransCheckInfo> transChecked) {
		int count=0;
		for(TransCheckInfo transCheckInfo:transChecked){
			TransCheckInfo temp=transChangeMapper.queryCheckTransInfoByTradeNo(transCheckInfo.getTradeNo());
			if(null!=temp&&temp.getStatus()==1){
				continue;
			}
			if(null!=temp&&temp.getStatus()!=1){
				transCheckInfo.setLastModifyBy(transCheckInfo.getUpdateBy());
				transCheckInfo.setStatus(0);
				count+=transChangeMapper.updateTransExpCheckInfo(transCheckInfo);
				continue;
			}
			count+=transChangeMapper.saveTransCheckInfo(transCheckInfo);
		}
		return count;
	}
	
	@Override
	public TransCheckInfo queryCheckTransInfoById(String id) {
		return transChangeMapper.queryCheckTransInfoById(id);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int updateTransChecked(TransCheckInfo transCheckInfo) throws ServiceException {
		int count=0;
		if(transCheckInfo.getStatus()==3){//勾兑记录无效
			count+=transChangeMapper.updateTransExpCheckInfo(transCheckInfo);
		}	
		if(transCheckInfo.getStatus()==1){
			count+=transChangeMapper.updateTransInfoByTradeNo(transCheckInfo);
			count+=transChangeMapper.updateTransExpCheckInfo(transCheckInfo);
			if(count==1){
				throw new ServiceException("订单不存在!");
			}
		}
		return count;
	}
	
	@Autowired
	private TransMgrDao transMgrDao;
	@Override
	public int updateTransFail(String tradeNos[],String name) {
		int num=0;
		for(int i=0;i<tradeNos.length;i++){
			TransCheckInfo temp =transChangeMapper.queryTransAccess(tradeNos[i]);
			if(null!=temp&&temp.getAccess().equals("0")){
				num+= transChangeMapper.updateTransFail(tradeNos[i]);
				transMgrDao.insertTransChangeInfo(tradeNos[i],name,"1");
//				num+=i;
			}
		}
		return num;
		
	}

	/**
	 *根据订单号查询交易信息 
	 **/
	@Override
	public List<RefuseInfo> queryRefuseInfoByOrderNo(String orderNo) {
		
		return transChangeMapper.queryRefuseInfoByOrderNo(orderNo);
	}

	/**
	 *根据流水号查询交易信息 
	 **/
	@Override
	public List<RefuseInfo> queryRefuseInfoByTradeNo(String tradeNo) {
		
		return transChangeMapper.queryRefuseInfoByTradeNo(tradeNo);
	}
	
	@Override
	public int insertTransRerunInfo(String tradeNo,String currencyId) {
		return transChangeMapper.insertTransRerunInfo(tradeNo,currencyId);
	}
	
	@Override
	public PageInfo<TransInfo> getTransList(Criteria criteria) {
		PageInfo<TransInfo> page = new PageInfo<TransInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(transChangeMapper.countTransList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<TransInfo> list = transChangeMapper.getTransList(criteria, rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public PageInfo<TransInfo> getTransHighRiskRerunInfoList(Criteria criteria) {
		PageInfo<TransInfo> page = new PageInfo<TransInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(transChangeMapper.countTransHighRiskRerunInfoList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<TransInfo> list = transChangeMapper.getTransHighRiskRerunInfoList(criteria, rb);
		page.setData(list);
		return page;
	}


	@Override
	public List<TransInfo> queryTransInfo(Criteria criteria) {
		return transChangeMapper.queryTransInfo(criteria);
	}
	/**
	 * 将支付状态修改为待处理状态
	 * */
	public int updateTransToPendingByTradeNo(String tradeNo){
		return transChangeMapper.updateTransToPendingByTradeNo(tradeNo);
	}
	
	@Override
	public int updateTransReRunInfo(int id, String respCode, String respMsg) {
		return transChangeMapper.updateTransReRunInfo(id,respCode,respMsg);
	}
	@Override
	public int selectMaxIdByTradeNo(String tradeNo) {
		return transChangeMapper.selectMaxIdByTradeNo(tradeNo);
	}
	@Override
	public List<String> getTransListForTransRerunTradeNos(Criteria criteria) {
		List<String> list = transChangeMapper.getTransListForTransRerunTradeNos(criteria);
		return list;
	}
}
