package com.gateway.transmgr.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.fraud.model.WhiteListInfo;
import com.gateway.sysmgr.model.CardBinInfo;
import com.gateway.transmgr.model.BankSettleDetail;
import com.gateway.transmgr.model.MulTransInfo;
import com.gateway.transmgr.model.SettleTransInfo;
import com.gateway.transmgr.model.TransCallbackInfo;
import com.gateway.transmgr.model.TransChangeInfo;
import com.gateway.transmgr.model.TransCount;
import com.gateway.transmgr.model.TransDetailInfo;
import com.gateway.transmgr.model.TransInfo;
import com.gateway.transmgr.model.TransLogInfo;
import com.gateway.transmgr.model.TransRecordInfo;
import com.gateway.transmgr.model.WhiteDishonorInfo;

public interface TransMgrService {
	
	/**
	 * 查询交易列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<TransInfo> getTransList(Criteria criteria);
	
	/**
	 *  交易金额统计
	 * @param criteria
	 * @return
	 */
	public List<TransInfo> getTransAmountList(Criteria criteria);
	
	/**
	 * 导出交易列表
	 * @param criteria
	 * @return
	 */
	public List<TransInfo> exportTransList(Criteria criteria);
	
	/**
	 * 订单详细信息
	 * @param tradeNo
	 * @return
	 */
	public TransInfo queryTransDetailByTradeNo(String tradeNo);
	
	/**
	 * 订单明细查询
	 * */
	public TransInfo queryTransInfoByTradeNo(String tradeNo);
	
	
	/**
	 * 订单历史信息
	 * @param tradeNo
	 * @return
	 */
	public List<TransInfo> queryTransDetailByRelNo(String relNo);
	
	/**
	 * 查询结算交易
	 * @param criteria
	 * @param rd
	 * @return
	 */
	public PageInfo<SettleTransInfo> getSettleTransList(Criteria criteria);
	/**
	 *结算交易根据交易类型金额统计 
	 * 
	 */
	public List<SettleTransInfo> getSettleTransAmount(Criteria criteria);
	
	
	/**
	 * 结算交易查询详细记录
	 * @param tradeNo
	 * @return
	 */
	public SettleTransInfo querySettleTransDetailByTradeNo(String tradeNo);
	
	
	/**
	 * 结算历史参考号
	 * @param relNo
	 * @return
	 */
	public List<SettleTransInfo> querySettleTransDetailByRelNo(String relNo);
	
	/**
	 * 导出交易列表
	 * @param criteria
	 * @return
	 */
	public List<SettleTransInfo> exportSettleTransList(Criteria criteria);
	
	/**
	 * 保存银行对账记录
	 * @param list
	 * @return
	 */
	public int saveBankSettleDetail(List<BankSettleDetail> list);
	
	/**
	 * 银行结算记录查询
	 * @param criteria
	 * @return
	 */
	public PageInfo<BankSettleDetail> queryBankSettleDetail(Criteria criteria);
	/**
	 * 修改对账为成功状态
	 * @param ids
	 * @return
	 */
	public int updateCheckStatusSucceed(String [] ids);

	/**
	 * 查询订单跟踪列表
	 * */
	public PageInfo<TransRecordInfo> getTransRecordList(Criteria criteria);


	/** 计算交易统计数据 */
	TransCount queryTransCount(Criteria criteria);

	/** 添加一条交易记录 */
	int insertTransInfo(TransInfo info);
	
	/**
	 * 	综合信息查询
	 * */
	public PageInfo<MulTransInfo> getMulTransInfoList(Criteria criteria);

	/** 查询订单跟踪列表 */
	List<TransRecordInfo> queryTransRecordInfo(Criteria criteria);

	
	/** 通过订单号查询详细信息 */
	TransDetailInfo queryTransInfo(String tradeNo) throws Exception;
	/**根据不同类型查询明显*/
	public List<TransInfo>queryTransByType(Criteria criteria);
	
	/**根据类型导出对应的交易信息*/
	public List <TransInfo> exportTransByType(String ids);
	
	public int batchAcceptCheckUpdateById(String[] ids);

	/** 检测是否勾兑，返回勾兑成功ID字符串 */
	public String checkAcceptDate(String[] ids);
	
	/**
	 * 检索当前勾兑重复的订单 
	 * */
	public List<String> checkDuplicateTrans(List<TransInfo> list);


	public int countTransInfo(Criteria criteria);

	/**
	 * 通过流水号 list 导出记录
	 * @param tradeNos
	 * @return
	 */
	public List<TransDetailInfo> queryTransInfoByTradeNos(List<String> tradeNos);
	
	/**
	 * 支付日志查询
	 * @param criteria
	 * @return
	 */
	public PageInfo<TransLogInfo> listTransLogInfo(Criteria criteria);

	/**
	 * 导出支付日志
	 * @param criteria
	 * @return
	 */
	public List<TransLogInfo> exportTransLogInfos(Criteria criteria);
	
	/**
	 * 查询卡bin信息
	 * @param decrypt
	 * @return
	 */
	public CardBinInfo queryCardBinInfoByBin(String bin);
	
	/**
	 * 查询回访邮件信息
	 */
	public PageInfo<TransCallbackInfo> queryTransCallbackInfoList(Criteria criteria);
	
	/**
	 * 保存回访邮件信息
	 */
	public int saveTransCallbackInfo(TransCallbackInfo info);
	
	/**
	 * 查询导出邮件信息
	 */
	public List<TransCallbackInfo> queryExportTransCallbackInfoList(Criteria criteria);
	
	/**
	 * 白名单交易拒付
	 */
	public PageInfo<WhiteDishonorInfo> queryWhiteTransDishonorInfoList(Criteria criteria);
	
	/**
	 * 查询白名单信息
	 */
	public List<WhiteListInfo> queryWhiteInfoListByIds(String whiteIds);
	
	/**
	 * 白名单交易拒付导出信息
	 */
	public List<WhiteDishonorInfo> queryExportWhiteTransDishonorInfoList(Criteria criteria);


	public void updateCheckedFailTransIds(List<Integer> checkedFailTransId,String name);


	public PageInfo<TransChangeInfo> getTransChangeInfoList(Criteria criteria);
	
	public int updateAutoCodeByTradeNo(String tradeNo,String autoCode);
}
