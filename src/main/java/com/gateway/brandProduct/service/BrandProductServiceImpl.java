package com.gateway.brandProduct.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gateway.brandProduct.mapper.BrandProductDao;
import com.gateway.brandProduct.model.BrandProductInfo;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;

@Service
public class BrandProductServiceImpl implements BrandProductService{
	@Autowired
	BrandProductDao brandProductDao;

	@Override
	public PageInfo<BrandProductInfo> getBrandProductList(Criteria criteria) {
		PageInfo<BrandProductInfo> page = new PageInfo<BrandProductInfo>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(brandProductDao.countBrandProductList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<BrandProductInfo> list = brandProductDao.getBrandProductList(criteria, rb);
		page.setData(list);
		return page;
		//return brandProductDao.getBrandProductList(criteria);
	}

	@Override
	public int addBrandProductInfo(BrandProductInfo brandProductInfo) {
		
		return brandProductDao.addBrandProductInfo(brandProductInfo);
	}

	@Override
	public BrandProductInfo queryBrandProductById(String id) {
		
		return brandProductDao.queryBrandProductById(id);
	}

	@Override
	public int updatebrandProduct(BrandProductInfo brandProductInfo) {
		
		return brandProductDao.updatebrandProduct(brandProductInfo);
	}
	
	@Override
	public int queryBrandProductDup(BrandProductInfo brandProductInfo) {
		return brandProductDao.queryBrandProductDup(brandProductInfo);
	}
	@Override
	public int deletebrandProductInfo(String id) {
		return brandProductDao.deletebrandProductInfo(id);
	}
	
	@Override
	public List<BrandProductInfo> ExportBrandProductList(Criteria criteria) {
		return brandProductDao.getBrandProductList(criteria);
	}
}
