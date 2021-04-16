package com.gateway.bankOrder.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.gateway.bankOrder.model.BankOrderModel;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;

public interface BankOrderService {

	//调用银行接口请求退款订单
	public Map<String,Object> getRefundOrder(String url,int merchantid,String signature,int year,int month);
	
	//把json格式转换成BankOrderMode对象
	public List<BankOrderModel> getBankOrderModelList(JSONArray json);
	//分页处理
	public PageInfo<BankOrderModel> getPageList(Criteria criteria,List<BankOrderModel> list);
	
	public PageInfo<BankOrderModel> getPage(Criteria criteria,String url,Map<String,String> map,String isLimit);
}
