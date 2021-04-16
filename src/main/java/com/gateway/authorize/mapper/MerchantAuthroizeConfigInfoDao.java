package com.gateway.authorize.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.authorize.model.MerchantAuthroizeConfigInfo;
import com.gateway.common.adapter.web.model.Criteria;

/**
 * 商户预授权配置Dao
 * @author 
 * @email  
 * @version 创建时间：2019年11月5日  上午11:39:41
 */

public interface MerchantAuthroizeConfigInfoDao {
	/**
	 * 查询商户预授权配置信息列表
	 * @param criteria
	 * @param rd
	 * @return
	 */
	public List<MerchantAuthroizeConfigInfo> getMerchantAuthroizeConfigInfoList(Criteria criteria,RowBounds rd);
	public List<MerchantAuthroizeConfigInfo> getMerchantAuthroizeConfigInfoList(Criteria criteria);
	
	/**
	 * 统计
	 * @param criteria
	 * @return
	 */
	public int countMerchantAuthroizeConfigInfoList(Criteria criteria );
	
	/**
	 * 增加
	 * @param info
	 * @return
	 */
	public int addMerchantAuthroizeConfigInfo(@Param("merchantAuthroizeConfigInfo")MerchantAuthroizeConfigInfo merchantAuthroizeConfigInfo);
	
	/**
	 * 根据id查询
	 * @param id
	 */
	public MerchantAuthroizeConfigInfo queryMerchantAuthroizeConfigInfoById(int id);
	
	/**
	 * 更新
	 * @param merchantAuthroizeConfigInfo
	 * @return
	 */
	public int updateMerchantAuthroizeConfigInfo(@Param("merchantAuthroizeConfigInfo")MerchantAuthroizeConfigInfo merchantAuthroizeConfigInfo);
	
	
	/**
	 * 删除
	 * @param merchantAuthroizeConfigInfo
	 * @return
	 */
	public int deleteMerchantAuthroizeConfigInfo(int id);
	
}
