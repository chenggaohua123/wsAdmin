package com.gateway.brandProduct.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gateway.brandProduct.model.BrandProductInfo;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;

public interface BrandProductService {
	/**
	 *品牌与产品查询 
	 */
	public PageInfo<BrandProductInfo> getBrandProductList(Criteria criteria);
	
	/**
	 * 
	 *添加品牌与产品 
	 */
	public int addBrandProductInfo(BrandProductInfo brandProductInfo);
	
	
	/**
	 *根据id查询品牌与产品 
	 */
	public BrandProductInfo queryBrandProductById(String id);
	
	
	/**
	 *产品与品牌修改 
	 */
	public int updatebrandProduct(BrandProductInfo brandProductInfo);
	
	/**
	 * 验证信息是否重复
	 * */
	public int queryBrandProductDup(@Param("")BrandProductInfo brandProductInfo);
	/**
	 * 删除产品品牌信息
	 * @param id
	 * @return
	 */
	public int deletebrandProductInfo(String id);
	/**
	 * 导出产品品牌信息
	 * @param criteria
	 * @return
	 */
	public List<BrandProductInfo> ExportBrandProductList(Criteria criteria);
	
	
}
