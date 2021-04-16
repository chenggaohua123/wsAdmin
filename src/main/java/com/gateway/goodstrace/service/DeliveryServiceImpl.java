package com.gateway.goodstrace.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.goodstrace.mapper.DeliveryDao;
import com.gateway.goodstrace.model.DeliveryInfo;
import com.gateway.goodstrace.model.ExpDeliveryInfo;
import com.gateway.goodstrace.model.IogisticsInfo;
@Service
public class DeliveryServiceImpl implements DeliveryService{
	@Autowired
	private DeliveryDao deliveryDao;
	/**妥投上传*/
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int saveDeliveryDetail(List<DeliveryInfo> deliveryInfolist){
		int count=0;
		for( DeliveryInfo deliveryInfo:deliveryInfolist){
			
			DeliveryInfo temp=deliveryDao.queryDeliveryInfoByTradeNo(deliveryInfo.getTradeNo());
			if(null!=temp){
				count+=deliveryDao.updateDeliverInfoByTradeNo(deliveryInfo);
				continue;
			}
			if(null==temp){
			count+=deliveryDao.saveDeliveryDetail(deliveryInfo);
			}
		}
		return count;
		
	}
	/**查询妥投*/
	@Override
	public PageInfo<DeliveryInfo> queryDeliveryInfo(Criteria criteria) {
		PageInfo<DeliveryInfo> page=new PageInfo<DeliveryInfo>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(deliveryDao.countDeliveryInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <DeliveryInfo> list=deliveryDao.queryDeliveryInfo(criteria,rb);
		page.setData(list);
		return page;
	}
	@Override
	public int updateDeliveryInfo(DeliveryInfo deliveryInfo) {
		
		return deliveryDao.updateDeliveryInfo(deliveryInfo);
	}
	@Override
	public DeliveryInfo queryDeliveryInfoById(int id) {
		
		return deliveryDao.queryDeliveryInfoById(id);
	}
	@Override
	public List<ExpDeliveryInfo> exportDelivery(Criteria criteria) {
		
		return deliveryDao.exportDelivery(criteria);
	}
	@Override
	public int UpdateStatusAndRemark(DeliveryInfo deliveryInfo) {
		
		return deliveryDao.UpdateStatusAndRemark(deliveryInfo);
	}
	@Override
	public int saveDeliveryRemarkAndStatus(List<DeliveryInfo> deliveryInfolist) {
		
		int count=0;
		for( DeliveryInfo deliveryInfo:deliveryInfolist){
			
			DeliveryInfo temp=deliveryDao.queryDeliveryInfoByTradeNo(deliveryInfo.getTradeNo());
			if(null!=temp){
				count+=deliveryDao.updateRemarkAndStatusByTradeNo(deliveryInfo);
				continue;
			}
			if(null==temp){
			count+=deliveryDao.saveDeliveryDetail(deliveryInfo);
			}
		}
		return count;
		
	}
	@Override
	public PageInfo<IogisticsInfo> getIogisticsInfo(Criteria criteria) {
		PageInfo<IogisticsInfo> page=new PageInfo<IogisticsInfo>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(deliveryDao.countIogisticsInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <IogisticsInfo> list=deliveryDao.getIogisticsInfo(criteria,rb);
		page.setData(list);
		return page;
	}
	@Override
	public int addIogistics(IogisticsInfo info) {
		return deliveryDao.addIogistics(info);
	}
	@Override
	public IogisticsInfo queryIogisticsById(String id) {
		return deliveryDao.queryIogisticsById(id);
	}
	@Override
	public int updateIogistics(IogisticsInfo info) {
		return deliveryDao.updateIogisticsById(info);
	}
	@Override
	public int deleteIogistics(String id) {
		return deliveryDao.deleteIogistics(id);
	}
	@Override
	public List<IogisticsInfo> getIogisticsInfoAll(Criteria criteria) {
		return deliveryDao.getIogisticsInfo(criteria);
	}
}
