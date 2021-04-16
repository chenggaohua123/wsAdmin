package com.gateway.goodstracem.service;

import java.util.List;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.goodstrace.model.DeliveryInfo;
import com.gateway.goodstrace.model.ExpDeliveryInfo;
import com.gateway.goodstrace.model.IogisticsInfo;

public interface DeliveryMService {
	/**妥投单上传保存*/
	public int saveDeliveryDetail(List<DeliveryInfo> deliveryInfo);
	/**妥投单查询*/
	public PageInfo<DeliveryInfo>queryDeliveryInfo(Criteria criteria);
	/**修改妥投状态*/
	public int updateDeliveryInfo(DeliveryInfo deliveryInfo);
	/**妥投修改所需查询*/
	public DeliveryInfo queryDeliveryInfoById(int id);
	/**妥投批量导出*/
	public List<ExpDeliveryInfo> exportDelivery(Criteria criteria);
	
	/**运单修改*/
	public int UpdateStatusAndRemark(DeliveryInfo deliveryInfo);
	/**批量货运状态修改*/
	public int saveDeliveryRemarkAndStatus(List<DeliveryInfo> deliveryInfo);
	
	/**
	 * 物流公司查询
	 * @param criteria
	 * @return
	 */
	public PageInfo<IogisticsInfo> getIogisticsInfo(Criteria criteria);
	
	/**
	 * 添加物流公司
	 * @param info
	 * @return
	 */
	public int addIogistics(IogisticsInfo info);
	
	/**
	 * 根据ID查询物流公司信息
	 * @param id
	 * @return
	 */
	public IogisticsInfo queryIogisticsById(String id);
	
	/**
	 * 更新物流公司
	 * @param info
	 * @return
	 */
	public int updateIogistics(IogisticsInfo info);
	public int deleteIogistics(String id);
	public List<IogisticsInfo> getIogisticsInfoAll(Criteria criteria);
}
