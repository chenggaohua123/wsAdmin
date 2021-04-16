package com.gateway.authorize.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gateway.authorize.mapper.MerchantAuthroizeConfigInfoDao;
import com.gateway.authorize.model.MerchantAuthroizeConfigInfo;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.ServiceException;
import com.gateway.merchantmgr.model.MerchantRelCurrencyInfo;
import com.gateway.sysmgr.mapper.SysMgrDao;

@Service
public class MerchantAuthroizeConfigInfoServiceImpl implements MerchantAuthroizeConfigInfoService{
	@Autowired
	private MerchantAuthroizeConfigInfoDao merchantAuthroizeConfigInfoDao;
	@Autowired
	private SysMgrDao sysMgrDao;
	
	@Override
	public PageInfo<MerchantAuthroizeConfigInfo> getMerchantAuthroizeConfigInfoList(Criteria criteria) {
		PageInfo<MerchantAuthroizeConfigInfo> page = new PageInfo<MerchantAuthroizeConfigInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(merchantAuthroizeConfigInfoDao.countMerchantAuthroizeConfigInfoList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<MerchantAuthroizeConfigInfo> list = merchantAuthroizeConfigInfoDao.getMerchantAuthroizeConfigInfoList(criteria, rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public int addMerchantAuthroizeConfigInfo(MerchantAuthroizeConfigInfo merchantAuthroizeConfigInfo) {
		return merchantAuthroizeConfigInfoDao.addMerchantAuthroizeConfigInfo(merchantAuthroizeConfigInfo);
	}

	@Override
	public int updateMerchantAuthroizeConfigInfo(MerchantAuthroizeConfigInfo merchantAuthroizeConfigInfo) {
		return merchantAuthroizeConfigInfoDao.updateMerchantAuthroizeConfigInfo(merchantAuthroizeConfigInfo);
	}

	@Override
	public MerchantAuthroizeConfigInfo queryMerchantAuthroizeConfigInfoById(int id) {
		return merchantAuthroizeConfigInfoDao.queryMerchantAuthroizeConfigInfoById(id);
	}

	@Override
	public int deleteMerchantAuthroizeConfigInfo(int id) {
			return merchantAuthroizeConfigInfoDao.deleteMerchantAuthroizeConfigInfo(id);
	}

}