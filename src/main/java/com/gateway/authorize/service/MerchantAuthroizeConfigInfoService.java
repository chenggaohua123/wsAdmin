package com.gateway.authorize.service;


import com.gateway.authorize.model.MerchantAuthroizeConfigInfo;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;

public interface MerchantAuthroizeConfigInfoService {

	/**
	 * 分页查询商户预授权配置信息列表
	 * @param criteria
	 * @return
	 */
	public PageInfo<MerchantAuthroizeConfigInfo> getMerchantAuthroizeConfigInfoList(Criteria criteria);
	
	/**
	 * 添加商户添加户预授权配置信息
	 * @param info
	 * @return
	 */
	public int  addMerchantAuthroizeConfigInfo(MerchantAuthroizeConfigInfo  merchantAuthroizeConfigInfo);
	
	/**
	 * 修改商户户预授权配置信息
	 * @param info
	 * @return
	 */
	public int  updateMerchantAuthroizeConfigInfo(MerchantAuthroizeConfigInfo  merchantAuthroizeConfigInfo);
	
	/**
	 * 根据id查询商户预授权配置信息
	 * @param id
	 * @return
	 */
	public MerchantAuthroizeConfigInfo queryMerchantAuthroizeConfigInfoById(int id);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int  deleteMerchantAuthroizeConfigInfo(int  id);
	
}
