package com.gateway.goodstracem.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.goodstrace.model.DeliveryInfo;
import com.gateway.goodstrace.model.ExpDeliveryInfo;
import com.gateway.goodstrace.model.IogisticsInfo;
import com.gateway.goodstracem.mapper.DeliveryMDao;
@Service
public class DeliveryMServiceImpl implements DeliveryMService{
	@Autowired
	private DeliveryMDao deliveryMDao;
	/**妥投上传*/
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int saveDeliveryDetail(List<DeliveryInfo> deliveryInfolist){
		int count=0;
		for( DeliveryInfo deliveryInfo:deliveryInfolist){
			DeliveryInfo temp=deliveryMDao.queryDeliveryInfoByTradeNo(deliveryInfo.getTradeNo());
			if(null!=temp){
				count+=deliveryMDao.updateDeliverInfoByTradeNo(deliveryInfo);
				continue;
			}
			if(null==temp){
			count+=deliveryMDao.saveDeliveryDetail(deliveryInfo);
			}
		}
		return count;
		
	}
	/**查询妥投*/
	@Override
	public PageInfo<DeliveryInfo> queryDeliveryInfo(Criteria criteria) {
		PageInfo<DeliveryInfo> page=new PageInfo<DeliveryInfo>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(deliveryMDao.countDeliveryInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <DeliveryInfo> list=deliveryMDao.queryDeliveryInfo(criteria,rb);
		page.setData(list);
		return page;
	}
	@Override
	public int updateDeliveryInfo(DeliveryInfo deliveryInfo) {
		
		return deliveryMDao.updateDeliveryInfo(deliveryInfo);
	}
	@Override
	public DeliveryInfo queryDeliveryInfoById(int id) {
		
		return deliveryMDao.queryDeliveryInfoById(id);
	}
	@Override
	public List<ExpDeliveryInfo> exportDelivery(Criteria criteria) {
		
		return deliveryMDao.exportDelivery(criteria);
	}
	@Override
	public int UpdateStatusAndRemark(DeliveryInfo deliveryInfo) {
		
		return deliveryMDao.UpdateStatusAndRemark(deliveryInfo);
	}
	@Override
	public int saveDeliveryRemarkAndStatus(List<DeliveryInfo> deliveryInfolist) {
		
		int count=0;
		for( DeliveryInfo deliveryInfo:deliveryInfolist){
			
			DeliveryInfo temp=deliveryMDao.queryDeliveryInfoByTradeNo(deliveryInfo.getTradeNo());
			if(null!=temp){
				count+=deliveryMDao.updateRemarkAndStatusByTradeNo(deliveryInfo);
				continue;
			}
			if(null==temp){
			count+=deliveryMDao.saveDeliveryDetail(deliveryInfo);
			}
		}
		return count;
		
	}
	@Override
	public PageInfo<IogisticsInfo> getIogisticsInfo(Criteria criteria) {
		PageInfo<IogisticsInfo> page=new PageInfo<IogisticsInfo>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(deliveryMDao.countIogisticsInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <IogisticsInfo> list=deliveryMDao.getIogisticsInfo(criteria,rb);
		page.setData(list);
		return page;
	}
	@Override
	public int addIogistics(IogisticsInfo info) {
		return deliveryMDao.addIogistics(info);
	}
	@Override
	public IogisticsInfo queryIogisticsById(String id) {
		return deliveryMDao.queryIogisticsById(id);
	}
	@Override
	public int updateIogistics(IogisticsInfo info) {
		return deliveryMDao.updateIogisticsById(info);
	}
	@Override
	public int deleteIogistics(String id) {
		return deliveryMDao.deleteIogistics(id);
	}
	@Override
	public List<IogisticsInfo> getIogisticsInfoAll(Criteria criteria) {
		return deliveryMDao.getIogisticsInfo(criteria);
	}
}
