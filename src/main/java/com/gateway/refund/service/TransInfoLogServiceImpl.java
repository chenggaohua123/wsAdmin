package com.gateway.refund.service;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.gateway.api.utils.Constants;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.APIException;
import com.gateway.common.utils.Tools;
import com.gateway.refund.mapper.TransInfoLogMapper;
import com.gateway.refund.model.CountTransMoney;
import com.gateway.refund.model.RefundInfo;
import com.gateway.refund.model.RefundModel;
import com.gateway.refund.model.TransInfoLog;
import com.gateway.transmgr.model.TransInfo;
import com.gateway.transmgr.service.TransMgrService;

@Service
public class TransInfoLogServiceImpl implements TransInfoLogService {
	//private static Log logger = LogFactory.getLog(TransInfoLogServiceImpl.class);
	@Resource
	private TransInfoLogMapper transInfoLogMapper;
	
	@Autowired
	private TransMgrService transMgrService;
	
	@Autowired
	private RefundService refundService;
	
	@Override
	public PageInfo<TransInfoLog> queryTransInfo(Criteria criteria) {
		PageInfo<TransInfoLog> page=new PageInfo<TransInfoLog>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(transInfoLogMapper.countTransInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<TransInfoLog> list = transInfoLogMapper.queryToTransInfo(criteria,rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public List<TransInfoLog> queryTransInfoLogList(String tradeNo){
		return transInfoLogMapper.queryTransInfoLogList(tradeNo);
	}


	@Transactional(rollbackFor=Exception.class)
	@Override
	public int insertTransInfoLog(TransInfoLog transInfoLog) throws APIException{
		int i = transInfoLogMapper.insertTransInfoLog(transInfoLog);//保存日志
		if(i < 0){
			throw new APIException("保存日志记录失败");
		}
		transInfoLog.setStatus(1);
		return transInfoLogMapper.updateTransInfoChange(transInfoLog);//修改交易信息数据
	}
	
	@Override
	public int insertChangeTransInfo(TransInfoLog transInfoLog) throws APIException{
		TransInfoLog log = transInfoLogMapper.queryTransInfoLogId(transInfoLog.getId());//获取当前审核交易记录
		transInfoLog.setTransType(log.getTransType());
		log.setTradeNewNo(transInfoLog.getTradeNewNo());//流水号
		log.setCheckBy(transInfoLog.getCheckBy());//审核人
		log.setStatus(transInfoLog.getStatus());
		log.setId(transInfoLog.getId());
		if(2 == transInfoLog.getStatus()){//当申请通过添加一条交易记录
			int i = 0;
			if(Constants.TRSAN_INFO_TYPE_REFUND.equals(log.getTransType())){//退款单独处理，添加一条退款记录
				i = refundService.insertTransInfoLog(log);
				log.setStatus(2);//退款状态：提交到退款管理
				RefundInfo refundInfo = new RefundInfo();
				refundInfo.setRefundAmount(log.getTransMoney()+"");
				refundInfo.setTradeNo(log.getTradeNo());
				refundInfo.setTransLogId(log.getId());
				final RefundInfo rInfo = setMoneyInfo(log);
				rInfo.setTransLogId(refundInfo.getTransLogId());
				if(StringUtils.isEmpty(rInfo)){
					throw new APIException("退款数据异常，请重试");
				}
				if(transInfoLog.getToBank()==0){//发送到银行
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								refundService.doRefund(rInfo);
							} catch (APIException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}).start();
				}
			}
			i = insertTransInfo(transInfoLog);
			log.setTradeNewNo(transInfoLog.getTradeNewNo());
			
			if(i <= 0){//添加失败
				throw new APIException("添加审核交易记录失败！");
			}
		}
		int y = transInfoLogMapper.updateTransInfoLog(log);//修改日志
		if(y <= 0){
			throw new APIException("修改交易记录LOG错误");
		}
		if(1 == transInfoLog.getStatus()){
			transInfoLog.setStatus(0);//未通过
		}
		int t = transInfoLogMapper.updateTransInfoChange(transInfoLog);//修改交易记录
		if(t <= 0){
			throw new APIException("修改交易记录错误");
		}
		return 1;
	}
	
	private RefundInfo setMoneyInfo(TransInfoLog log){
		RefundInfo rInfo = new RefundInfo();
		rInfo.setTradeNo(log.getTradeNo());
		rInfo.setRefundReason("交易变更退款");
		TransInfo info = transMgrService.queryTransDetailByTradeNo(log.getTradeNo());
		BigDecimal rate = log.getTransMoney().divide(info.getMerTransAmount(),6,BigDecimal.ROUND_HALF_UP);
		rInfo.setMerNo(info.getMerNo());
		rInfo.setTerNo(info.getTerNo());
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    String  s = format1.format(info.getTransDate());
		rInfo.setApplyDate(s);
		rInfo.setRefundAmount(info.getBankTransAmount().multiply(rate)+"");
		rInfo.setRefundCurrency(info.getBankCurrency());
		rInfo.setBusAmount(info.getBankTransAmount()+"");
		rInfo.setBusCurrency(info.getBankCurrency());
		return rInfo;
	}
	
	/** 保存交易记录 */
	private int insertTransInfo(TransInfoLog transInfoLog){
		TransInfo info = transMgrService.queryTransDetailByTradeNo(transInfoLog.getTradeNo());
		info.setTransType(transInfoLog.getTransType());
		String transInfoNo = Tools.getChangeTransInfoNo(transInfoLog.getTransType());//生成交易流水号
		transInfoLog.setTradeNewNo(transInfoNo);
		info.setTradeNo(transInfoNo);
		BigDecimal rate = transInfoLog.getTransMoney().divide(info.getMerTransAmount(),6,BigDecimal.ROUND_HALF_UP);
		if(Constants.TRSAN_INFO_TYPE_THAW.equals(transInfoLog.getTransType())){//解冻
			info.setMerTransAmount(transInfoLog.getTransMoney());
			info.setBankTransAmount(info.getBankTransAmount().multiply(rate));
			info.setMerSettleAmount(info.getMerSettleAmount().multiply(rate));
		}else{
			info.setMerTransAmount(transInfoLog.getTransMoney().multiply(new BigDecimal(-1)));
			info.setBankTransAmount(info.getBankTransAmount().multiply(rate).multiply(new BigDecimal(-1)));
			info.setMerSettleAmount(info.getMerSettleAmount().multiply(rate).multiply(new BigDecimal(-1)));
		}
		info.setBondAmount(new BigDecimal(0));
		info.setMerForAmount(new BigDecimal(0));
		info.setRelNo(null);
		info.setIschecked(1);
		info.setIsDishonor(0);
		info.setIsFrozen(0);
		info.setIsRefund(0);
		info.setIsThaw(0);
		info.setBatchNo(null);
		//info.set
		return transMgrService.insertTransInfo(info);
	}
	

	@Override
	public TransInfoLog queryTransInfoLog(TransInfoLog transInfoLog) {
		return transInfoLogMapper.queryTransInfoLog(transInfoLog);
	}

	@Override
	public CountTransMoney countTransMoney(String tradeNo) {
		CountTransMoney cMoney = transInfoLogMapper.countTransMoney(tradeNo);
		BigDecimal money = cMoney.getTransMoney().subtract(cMoney.getRefundMoney()).subtract(cMoney.getFrozenMoney()).subtract(cMoney.getDishonorMoney()).add(cMoney.getThawMoney());
		cMoney.setSurplusMoney(money);
		return cMoney;
	}

	@Override
	public PageInfo<TransInfoLog> selectTransInfoList(Criteria criteria) {
		PageInfo<TransInfoLog> page=new PageInfo<TransInfoLog>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(transInfoLogMapper.countTransInfoLogList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<TransInfoLog> list = transInfoLogMapper.selectTransInfoLogList(criteria,rb);
		page.setData(list);
		return page;
	}

	@Override
	public List<TransInfo> exportTransLogList(Criteria criteria) {
		List<TransInfo> list = transInfoLogMapper.exportTransLogList(criteria);
		return list;
	}

	@Override
	public List<TransInfo> exportTradeNoTransLogList(String tradeNo) {
		return transInfoLogMapper.exportTradeNoTransLogList(tradeNo);
	}
	
}
