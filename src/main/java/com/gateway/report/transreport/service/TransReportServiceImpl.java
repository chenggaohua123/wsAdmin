package com.gateway.report.transreport.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.report.transreport.mapper.TransReportDao;
import com.gateway.report.transreport.model.DishonoeTotal;
import com.gateway.report.transreport.model.MaxAmountTotal;
import com.gateway.report.transreport.model.TransTotal;

@Service
public class TransReportServiceImpl implements TransReportService{
	
	@Autowired
	private TransReportDao transReportDao;
	
	@Override
	public PageInfo<TransTotal> transTotalList(Criteria criteria) {
		PageInfo<TransTotal> page = new PageInfo<TransTotal>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(transReportDao.countTotal(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<TransTotal> list=transReportDao.transTotalList(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public PageInfo<TransTotal> transCurrencyTotalList(Criteria criteria) {
		PageInfo<TransTotal> page = new PageInfo<TransTotal>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(transReportDao.countCurrency(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<TransTotal> list=transReportDao.transCurrencyTotalList(criteria, rb);
		page.setData(list);
		return page;
	}

	@Override
	public PageInfo<MaxAmountTotal> queryMaxAmountTotal(Criteria criteria) {
		PageInfo<MaxAmountTotal> page = new PageInfo<MaxAmountTotal>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(transReportDao.countMaxAmountTotal(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<MaxAmountTotal> list=transReportDao.queryMaxAmountTotal(criteria, rb);
		DecimalFormat formate = new DecimalFormat("0.00");
		for(MaxAmountTotal m:list){
			m.setMaxAmountRisk(formate.format(m.getTransAmount()/m.getTotalAmount())+"%");
		}
		page.setData(list);
		return page;
	}

	@Override
	public PageInfo<DishonoeTotal> queryDishonoeInfo(Criteria criteria) {
		PageInfo<DishonoeTotal> page = new PageInfo<DishonoeTotal>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(transReportDao.countDishonoeInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<DishonoeTotal> list=transReportDao.queryDishonoeInfo(criteria, rb);
		for(DishonoeTotal m:list){
			if(!"0".equals(m.getDishonoeNum()) && !"0".equals(m.getTotalNum())){
				BigDecimal rate = new BigDecimal(m.getDishonoeNum()).divide(new BigDecimal(m.getTotalNum()),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
				m.setDishonoeColumn(rate + "%");
			}else{
				m.setDishonoeColumn("0.00%");
			}
			if(!"0".equals(m.getDishonoeNumApril()) && !"0".equals(m.getTotalNumApril())){
				BigDecimal rateApril = new BigDecimal(m.getDishonoeNumApril()).divide(new BigDecimal(m.getTotalNumApril()),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
				m.setDishonoeColumnApril(rateApril + "%");
			}else{
				m.setDishonoeColumnApril("0.00%");
			}
		}
		page.setData(list);
		return page;
	}
	
	
}
