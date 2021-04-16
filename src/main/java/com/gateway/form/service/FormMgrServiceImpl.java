package com.gateway.form.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.form.mapper.FormMgrDao;
import com.gateway.form.model.CountAnalysis;
import com.gateway.form.model.MerchantReportFormsInfo;
import com.gateway.form.model.TransAmountCountInfo;
import com.gateway.form.model.TransTimeCountInfo;

@Service
public class FormMgrServiceImpl implements FormMgrService {

	@Autowired
	private FormMgrDao formMgrDao;

	@Override
	public PageInfo<MerchantReportFormsInfo> queryMerchantReportForms(
			Criteria criteria) {
		PageInfo<MerchantReportFormsInfo> page = new PageInfo<MerchantReportFormsInfo>(
				criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(formMgrDao.countMerchantReportForms(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		page.setData(formMgrDao.queryMerchantReportForms(criteria, rb));
		return page;
	}
	
	@Override
	public List<CountAnalysis> queryCountAnalysisInfoAll(Criteria criteria) {
		List <CountAnalysis> list=formMgrDao.queryCountAnalysisInfo(criteria);
		return list;
	}
	@Override
	public List<CountAnalysis> queryTransCountInfoForDay(Criteria criteria) {
		List <CountAnalysis> list=formMgrDao.queryTransCountInfoForDay(criteria);
		return list;
	}
	@Override
	public List<TransTimeCountInfo> queryTransTimeCountInfo(Criteria criteria) {
		List<TransTimeCountInfo> list=formMgrDao.queryTransTimeCountInfo(criteria);
		List<TransTimeCountInfo> list1=new ArrayList<TransTimeCountInfo>();
		for(int i=0;i<=23;i++){
			boolean flag=true;
			TransTimeCountInfo s=new TransTimeCountInfo();
			for(TransTimeCountInfo info:list){
				if(info.getHour()==i){
					s.setHourStr(info.getHour()+":00-"+info.getHour()+":59");
					s.setCount(info.getCount());
					list1.add(s);
					flag=false;
					break;
				}
			}
			if(flag){
				s.setHourStr(i+":00-"+i+":59");
				s.setCount(0);
				list1.add(s);
			}
		}
		return list1;
	}
	@Override
	public List<TransAmountCountInfo> queryTransAmountCountInfo(
			Criteria criteria) {
		TransAmountCountInfo info=formMgrDao.queryTransAmountCountInfo(criteria);
		List<TransAmountCountInfo> list=new ArrayList<TransAmountCountInfo>();
		if("CNY".equalsIgnoreCase(info.getCurrency())){
			list.add(new TransAmountCountInfo("0-200",info.getA1(),info.getCurrency()));
			list.add(new TransAmountCountInfo("201-400",info.getA2(),info.getCurrency()));
			list.add(new TransAmountCountInfo("401-600",info.getA3(),info.getCurrency()));
			list.add(new TransAmountCountInfo("601-800",info.getA4(),info.getCurrency()));
			list.add(new TransAmountCountInfo("801-1000",info.getA5(),info.getCurrency()));
			list.add(new TransAmountCountInfo("1001-1500",info.getA6(),info.getCurrency()));
			list.add(new TransAmountCountInfo("1501-2000",info.getA7(),info.getCurrency()));
			list.add(new TransAmountCountInfo("2001-3000",info.getA8(),info.getCurrency()));
			list.add(new TransAmountCountInfo("3001-4000",info.getA9(),info.getCurrency()));
			list.add(new TransAmountCountInfo("4001-5000",info.getA10(),info.getCurrency()));
			list.add(new TransAmountCountInfo("5000以上",info.getA11(),info.getCurrency()));
		}else{
			list.add(new TransAmountCountInfo("0-20",info.getA1(),info.getCurrency()));
			list.add(new TransAmountCountInfo("21-40",info.getA2(),info.getCurrency()));
			list.add(new TransAmountCountInfo("41-60",info.getA3(),info.getCurrency()));
			list.add(new TransAmountCountInfo("61-80",info.getA4(),info.getCurrency()));
			list.add(new TransAmountCountInfo("81-100",info.getA5(),info.getCurrency()));
			list.add(new TransAmountCountInfo("101-150",info.getA6(),info.getCurrency()));
			list.add(new TransAmountCountInfo("151-200",info.getA7(),info.getCurrency()));
			list.add(new TransAmountCountInfo("201-300",info.getA8(),info.getCurrency()));
			list.add(new TransAmountCountInfo("301-400",info.getA9(),info.getCurrency()));
			list.add(new TransAmountCountInfo("401-500",info.getA10(),info.getCurrency()));
			list.add(new TransAmountCountInfo("500以上",info.getA11(),info.getCurrency()));
		}
		return list;
	}
}
