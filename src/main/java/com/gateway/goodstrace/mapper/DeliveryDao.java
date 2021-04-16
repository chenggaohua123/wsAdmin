package com.gateway.goodstrace.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.goodstrace.model.DeliveryInfo;
import com.gateway.goodstrace.model.ExpDeliveryInfo;
import com.gateway.goodstrace.model.IogisticsInfo;

@Repository
public interface DeliveryDao {
	public int saveDeliveryDetail(DeliveryInfo deliveryInfo);
	
	/**妥投查询*/
	public List<DeliveryInfo> queryDeliveryInfo(Criteria criteria,RowBounds rd);
	/**统计条数*/
	public int countDeliveryInfo(Criteria criteria);
	
	/**修改妥投状态*/
	public int updateDeliveryInfo(@Param("deliveryInfo")DeliveryInfo deliveryInfo);
	
	/**根据TradeNo更新货运信息状态*/
	public int updateDeliverInfoByTradeNo(@Param("deliveryInfo")DeliveryInfo deliveryInfo);
	
	/**根据id查询妥投信息*/
	public DeliveryInfo queryDeliveryInfoById(int id);
	/**根据TradeNo查询妥投信息*/
	public DeliveryInfo queryDeliveryInfoByTradeNo(@Param("TradeNo")String TradeNo);
	/**导出货运信息*/
	public List<ExpDeliveryInfo> exportDelivery(Criteria criteria);
	
	/**修改状态和备注*/
	public int UpdateStatusAndRemark(@Param("deliveryInfo")DeliveryInfo deliveryInfo);
	
	/**批量修改状态和备注*/
	public int updateRemarkAndStatusByTradeNo(@Param("deliveryInfo")DeliveryInfo deliveryInfo);
	
	/**
	 * 物流公司查询
	 * @param criteria
	 * @return
	 */
	public List<IogisticsInfo> getIogisticsInfo(Criteria criteria,RowBounds rd);
	public List<IogisticsInfo> getIogisticsInfo(Criteria criteria);
	/**
	 * 物流公司查询
	 * @param criteria
	 * @return
	 */
	public int countIogisticsInfo(Criteria criteria);
	
	/**
	 * 添加物流公司
	 * @param info
	 * @return
	 */
	public int addIogistics(@Param("info") IogisticsInfo info);
	
	/**
	 * 根据ID查询物流公司信息
	 * @param id
	 * @return
	 */
	public IogisticsInfo queryIogisticsById(@Param("id") String id);
	
	/**
	 * 更新物流公司
	 * @param info
	 * @return
	 */
	public int updateIogisticsById(@Param("info") IogisticsInfo info);

	public int deleteIogistics(String id);
}
