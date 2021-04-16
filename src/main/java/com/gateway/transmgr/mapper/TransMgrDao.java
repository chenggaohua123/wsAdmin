package com.gateway.transmgr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.api.model.PicInfo;
import com.gateway.bankmgr.model.CurrencyInfo;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.fraud.model.WhiteListInfo;
import com.gateway.sysmgr.model.CardBinInfo;
import com.gateway.transmgr.model.BankSettleDetail;
import com.gateway.transmgr.model.GoodsInfo;
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

public interface TransMgrDao {
	/**
	 * 查询交易列表
	 * @param criteria
	 * @return
	 */
	public List<TransInfo> getTransList(@Param("cr")Criteria criteria,RowBounds rd);
	
	/**
	 * 导出交易列表
	 * @param criteria
	 * @return
	 */
	public List<TransInfo> exportTransList(@Param("cr")Criteria criteria);
	
	/**
	 * 查询交易列表
	 * @param criteria
	 * @return
	 */
	public int countTransList(@Param("cr")Criteria criteria);
	
	
	/**
	 *交易金额统计条数
	 *@param criteria
	 *@return 
	 */
	public int countTransAmountList(Criteria criteria);
	
	/**
	 * 
	 * 交易金额统计
	 * 
	 */
	public List<TransInfo> getTransAmountList(@Param("cr")Criteria criteria);
	
	/**
	 * 订单详细信息
	 * @param tradeNo
	 * @return
	 */
	public TransInfo queryTransDetailByTradeNo(String tradeNo);
	/**
	 * 查询订单明细
	 * */
	
	public TransInfo queryTransListByTradeNo(String tradeNo);
	
	/**
	 * 查询交易签名
	 * @param tradeNo
	 * @return
	 */
	public PicInfo queryTransSignInfoByTradeNo(@Param("tradeNo") String tradeNo);	
	/**
	 * 订单历史信息
	 * @param tradeNo
	 * @return
	 */
	public List<TransInfo> queryTransDetailByRelNo(@Param("relNo") String relNo);
	
	/**
	 * 查询结算交易
	 * @param criteria
	 * @param rd
	 * @return
	 */
	public List<SettleTransInfo> getSettleTransList(Criteria criteria,RowBounds rd);
	
	/**
	 *结算交易根据交易类型金额统计 
	 * 
	 */
	public List<SettleTransInfo> getSettleTransAmount(Criteria criteria);
	
	/**
	 * 导出结算交易列表
	 * @param criteria
	 * @return
	 */
	public List<SettleTransInfo> getSettleTransList(Criteria criteria);
	
	
	public int countSettleTransList(Criteria criteria);
	
	
	/**
	 * 结算交易查询详细记录
	 * @param tradeNo
	 * @return
	 */
	public SettleTransInfo querySettleTransDetailByTradeNo(@Param("tradeNo")String tradeNo);
	
	/**
	 * 结算历史参考号
	 * @param relNo
	 * @return
	 */
	public List<SettleTransInfo> querySettleTransDetailByRelNo(@Param("relNo") String relNo);
	
	/**
	 * 根据流水号查询交易信息
	 * @param tradeNO
	 * @return
	 */
	public TransInfo queryGwTransInfo(@Param("tradeNo")String tradeNO);
	
	/**
	 * 保存对账记录
	 * @param bankSettleInfo
	 * @return
	 */
	public int saveBankSettleDetail(@Param("info") BankSettleDetail bankSettleInfo);
	
	/**
	 * 根据银行商户号和终端号查询通道信息
	 * @param merNo
	 * @param terNo
	 * @return
	 */
	public List<CurrencyInfo> queryCurrencyInfoByMerNoAndTerNo(@Param("merNo") String merNo,@Param("terNo")String terNo);
	
	/**
	 * 获取对账文件的批次号
	 * @return
	 */
	public int getBankSettleMaxBatchNo();
	
	/**
	 * 查询银行结算记录
	 * @param criteria
	 * @return
	 */
	public List<BankSettleDetail> queryBankSettleDetail(Criteria criteria,RowBounds rd);
	
	/**
	 * 统计银行结算记录
	 * @param criteria
	 * @return
	 */
	public int countBankSettleDetail(Criteria criteria);
	
	/**
	 * 修改对账状态为成功
	 * @param ids
	 * @return
	 */
	public int updateCheckStatusSucceed(@Param("ids") String[] ids);
	
	/**
	 * 查询订单跟踪列表
	 * */
	public List<TransRecordInfo> queryTransRecordInfo(Criteria criteria);
	public List<TransRecordInfo> queryTransRecordInfo(Criteria criteria, RowBounds rd);
	/**
	 * 统计订单条数
	 * */
	public int countTransRecordInfo(Criteria criteria);
	
	/** 统计交易数 */
	public TransCount queryTransCountInfo(@Param("cr")Criteria criteria);
	
	public List<TransCountModel> queryGroupCountInfo(@Param("info")TransCountModel transCountModel,@Param("cr")Criteria criteria);
	
	/** 添加一条交易记录 */
	public int insertTransInfo(@Param("transInfo")TransInfo transInfo);

	
	/**
	 * 统计综合信息查询条数
	 * */
	public int countMulTransInfo(Criteria criteria);
	/**
	 * 综合信息查询
	 * */
	public List<MulTransInfo> queryMulTransInfo(Criteria criteria, RowBounds rb);
	
	/** 通过订单号查询详细信息 */
	TransDetailInfo queryTransInfo(String tradeNo);
	/**
	 * 通过流水号列表查询订单详细信息
	 * @param tradeNos
	 * @return
	 */
	public List<TransDetailInfo> queryTransInfoByTradeNos(@Param("tradeNos")List<String> tradeNos);
	
	/**通过不同类型查询详细信息*/
	
	public List<TransInfo>queryTransByType(Criteria criteria);
	
	/**根据类型不同导出交易信息*/
	/**
	 * 导出交易列表
	 * @param criteria
	 * @return
	 */
	public TransInfo exportTransByType(String id);
	
	public int batchAcceptCheckUpdateById(@Param("ids")String[] ids);

	/** 检测是否勾兑，1表示勾兑成功，0表示勾兑失败 */
	public int checkAcceptDate(String id);
	
	/**
	 * 通过流水号查询货物信息
	 * */
	public List<GoodsInfo> queryGoodsInfoByTradeNo(String tradeNo);
	/**
	 * 检索当前重复的订单
	 * @param list
	 * @return
	 */
	public List<String> checkDuplicateTrans(@Param("list")List<TransInfo> list);

	/**
	 * 统计支付日志中条数
	 * @param criteria
	 * @return
	 */
	public int countTransLogInfo(Criteria criteria);
	/**
	 * 支付日志查询
	 * @param criteria
	 * @param rb
	 * @return
	 */
	public List<TransLogInfo> queryTransLogInfo(Criteria criteria, RowBounds rb);
	public List<TransLogInfo> queryTransLogInfo(Criteria criteria);
	/**
	 * 通过
	 * @param bin
	 * @return
	 */
	public CardBinInfo queryCardBinInfoByBin(String bin);
	
	/**
	 * 查询邮件回访信息
	 */
	public List<TransCallbackInfo> queryTransCallbackInfoList(@Param("cr") Criteria criteria, RowBounds rb);
	
	/**
	 * 查询邮件回访信息总数
	 */
	public int queryTransCallbackInfoCount(@Param("cr") Criteria criteria);
	
	/**
	 * 保存上传邮件回访信息
	 */
	public int saveTransCallbackInfo(@Param("vo") TransCallbackInfo vo);
	
	/**
	 * 查询导出邮件信息
	 */
	public List<TransCallbackInfo> queryTransCallbackInfoList(@Param("cr") Criteria criteria);
	
	/**
	 * 白名单交易拒付
	 */
	public List<WhiteDishonorInfo> queryWhiteTransDishonorInfoList(Criteria criteria, RowBounds rb);
	
	/**
	 * 白名单交易拒付总数
	 */
	public int queryWhiteTransDishonorCount(Criteria criteria);
	
	/**
	 * 查询白名单信息
	 */
	public List<WhiteListInfo> queryWhiteInfoListByIds(@Param("whiteIds") String whiteIds);
	
	/**
	 * 白名单交易拒付
	 */
	public List<WhiteDishonorInfo> queryWhiteTransDishonorInfoList(Criteria criteria);
	
	/**
	 * 查询未加密卡号的白名单信息
	 */
	public List<WhiteListInfo> querWhiteNotEncryptCheckNoInfoList();
	
	/**
	 * 添加卡号加密信息
	 */
	public int updateWhiteCheckNoInfo(@Param("vo") WhiteListInfo vo);

	public List<String> queryTradeNosByIds(@Param("ids")
			List<Integer> checkedFailTransId);

	public int insertTransChangeInfo(@Param("tradeNo")String tradeNo, 
			@Param("name")String name,
			@Param("type")String type);

	public int queryTransChangeInfoCount(Criteria criteria);

	public List<TransChangeInfo> queryTransChangeInfoList(Criteria criteria,
			RowBounds rb);
	/**
	 * 修改autoCode
	 * @param tradeNo
	 * @param autoCode
	 * @return
	 */
	public int updateAutoCodeByTradeNo(@Param("tradeNo")String tradeNo,@Param("autoCode")String autoCode);
}
