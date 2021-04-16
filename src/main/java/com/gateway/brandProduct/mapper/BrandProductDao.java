package com.gateway.brandProduct.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.gateway.brandProduct.model.BrandProductInfo;
import com.gateway.common.adapter.web.model.Criteria;

public interface BrandProductDao {
	/**
	 * 
	 * 
	 *品牌与产品分页查询 
	 */
	public List<BrandProductInfo> getBrandProductList(Criteria criteria,RowBounds rd);
	public List<BrandProductInfo> getBrandProductList(Criteria criteria);
	
	/**
	 * 
	 * 品牌与产品条数统计
	 */
	public int countBrandProductList(Criteria criteria);
	
	/**
	 * 
	 * 品牌与产品添加
	 */
	public int addBrandProductInfo(@Param("brandProductInfo")BrandProductInfo brandProductInfo);
	
	
	/**
	 * 
	 *根据id查询品牌与产品
	 */
	public BrandProductInfo queryBrandProductById(String id);
	/**
	 * 
	 *修改品牌与产品 
	 */
	public int updatebrandProduct(@Param("brandProductInfo")BrandProductInfo brandProductInfo);
	/**
	 * 验证信息是否重复
	 * */
	public int queryBrandProductDup(@Param("info")BrandProductInfo brandProductInfo);
	/**
	 * 删除产品品牌信息
	 * @param id
	 * @return
	 */
	public int deletebrandProductInfo(String id);
}
