package com.gateway.countAnalysis.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.complaint.model.Complaint;
import com.gateway.countAnalysis.mapper.CountAnalysisDao;
import com.gateway.countAnalysis.model.BrandCountInfo;
import com.gateway.countAnalysis.model.CompPercent;
import com.gateway.countAnalysis.model.CompRateInfo;
import com.gateway.countAnalysis.model.CountAnalysis;
import com.gateway.countAnalysis.model.CountryInfo;
import com.gateway.countAnalysis.model.CurrencyDisCount;
import com.gateway.countAnalysis.model.DisCount;
import com.gateway.countAnalysis.model.DisDesc;
import com.gateway.countAnalysis.model.DisPercent;
import com.gateway.countAnalysis.model.DishonoeTotal;
import com.gateway.countAnalysis.model.EuropeChannelInfo;
import com.gateway.countAnalysis.model.EuropeTransInfo;
import com.gateway.countAnalysis.model.ExportEuropeInfo;
import com.gateway.countAnalysis.model.ExportFaildTransAnalysisInfo;
import com.gateway.countAnalysis.model.ExportTransCountInfo;
import com.gateway.countAnalysis.model.FaildTransAnalysisInfo;
import com.gateway.countAnalysis.model.InitEuropeInfo;
import com.gateway.countAnalysis.model.MerchantTransCountRateInfo;
import com.gateway.countAnalysis.model.RiskPaddingRateInfo;
import com.gateway.countAnalysis.model.RiskPercent;
import com.gateway.countAnalysis.model.TotalCurrnecySuccessCount;
import com.gateway.countAnalysis.model.TransCountInfo;
import com.gateway.countAnalysis.model.TransHourCount;
import com.gateway.countAnalysis.model.TransOrDisPercent;
import com.gateway.countAnalysis.model.TransReRunCount;
import com.gateway.countAnalysis.model.TransRecord;
import com.gateway.transmgr.model.TransRecordInfo;

@Service
public class CountAnalysisServiceImpl implements CountAnalysisService{
	
	@Resource
	private CountAnalysisDao countAnalysisDao;
	
	@Override
	public PageInfo<CountAnalysis> queryCountAnalysisInfo(Criteria criteria) {
		PageInfo<CountAnalysis> page=new PageInfo<CountAnalysis>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(countAnalysisDao.countCountAnalysisInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <CountAnalysis> list=countAnalysisDao.queryCountAnalysisInfo(criteria,rb);
		page.setData(list);
		return page;
	}
	public List<CountAnalysis> queryCountAnalysisInfoAll(Criteria criteria) {
		List <CountAnalysis> list=countAnalysisDao.queryCountAnalysisInfo(criteria);
		return list;
	}
	
	@Override
	public PageInfo<CountAnalysis> queryCurrencyCountAnalysisInfo(Criteria criteria) {
		PageInfo<CountAnalysis> page=new PageInfo<CountAnalysis>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(countAnalysisDao.countCurrencyCountAnalysisInfo(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <CountAnalysis> list=countAnalysisDao.queryCurrencyCountAnalysisInfo(criteria,rb);
		page.setData(list);
		return page;
	}
	@Override
	public List<CountAnalysis> queryCurrencyCountAnalysisInfoAll(Criteria criteria) {
		List <CountAnalysis> list=countAnalysisDao.queryCurrencyCountAnalysisInfo(criteria);
		return list;
	}

	@Override
	public List<CountAnalysis> queryCountTransInfo(Criteria criteria) {
		String month = (String)criteria.getCondition().get("month");
		String year = (String)criteria.getCondition().get("year");
		List<CountAnalysis> list = countAnalysisDao.queryCountTransInfo(criteria);
		List<CountAnalysis> lists = new ArrayList<CountAnalysis>();
		if(StringUtils.isEmpty(month)){
			for(int i=1;i<13;i++){
				for(CountAnalysis li:list){
					if((i+"").equals(li.getMonth())){
						lists.add(li);
					}
				}
				if(i != lists.size()){
					lists.add(newCountAnalysis(year,i+""));
				}
			}
		}else{
			for(int i=1;i<(days(year,month)+1);i++){
				for(CountAnalysis li:list){
					if((i+"").equals(li.getDay())){
						lists.add(li);
					}
				}
				if(i != lists.size()){
					lists.add(newCountAnalysis(year,month,i+""));
				}
			}
		}
		
		return lists;
	}
	@Override
	public PageInfo<CountAnalysis> queryCountTransInfoForTable(Criteria criteria) {
		NumberFormat nf=NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(2);
		String month = (String)criteria.getCondition().get("month");
		String year = (String)criteria.getCondition().get("year");
		List<CountAnalysis> list = countAnalysisDao.queryCountTransInfo(criteria);
		PageInfo<CountAnalysis> page=new PageInfo<CountAnalysis>(criteria.getPageNum(),criteria.getPageSize());
		List<CountAnalysis> lists = new ArrayList<CountAnalysis>();
		if(StringUtils.isEmpty(month)){
			for(int i=1;i<13;i++){
				for(CountAnalysis li:list){
					if((i+"").equals(li.getMonth())){
						li.setMonth(nf.format(Integer.parseInt(li.getMonth())));
						lists.add(li);
					}
				}
				if(i != lists.size()){
					lists.add(newCountAnalysis(year,nf.format(i)));
				}
			}
		}else{
			for(int i=1;i<(days(year,month)+1);i++){
				for(CountAnalysis li:list){
					if((i+"").equals(li.getDay())){
						li.setDay(nf.format(Integer.parseInt(li.getDay())));
						li.setMonth(nf.format(Integer.parseInt(li.getMonth())));
						lists.add(li);
					}
				}
				if(i != lists.size()){
					lists.add(newCountAnalysis(year,nf.format(Integer.parseInt(month)),nf.format(i)));
				}
			}
		}
		page.setData(lists);
		return page;
	}
	/** 创建一个空对象 */
	private CountAnalysis newCountAnalysis(String year,String month){
		CountAnalysis info = new CountAnalysis();
		info.setYear(year);
		info.setMonth(month);
		
		return info;
	}
	/** 创建一个空对象 */
	private CountAnalysis newCountAnalysis(String year,String month,String day){
		CountAnalysis info = new CountAnalysis();
		info.setYear(year);
		info.setMonth(month);
		info.setDay(day);
		return info;
	}
	
	/** 根据年月获取月最大天数 */
	private int days(String yearS,String monthS){
		int year = Integer.parseInt(yearS);
		int month = Integer.parseInt(monthS);
		int days = 0;
		if (month != 2) {
			switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				days = 31;
				break;
			case 4:
			case 6:
			case 9:
			case 11:days = 30;
			}
		} else {
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
				days = 29;
			else
				days = 28;
		}
		return days;
	}

	@Override
	public PageInfo<CountAnalysis> queryWebSiteList(Criteria criteria) {
		PageInfo<CountAnalysis> page=new PageInfo<CountAnalysis>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(countAnalysisDao.countWebSiteList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <CountAnalysis> list=countAnalysisDao.queryWebSiteList(criteria,rb);
		int totalCount=0;
		DecimalFormat df = new DecimalFormat("0.00");
		for(CountAnalysis li:list){
			totalCount+=Integer.parseInt(li.getTransCount());
		}
		for(CountAnalysis li:list){
			BigDecimal rate = new BigDecimal(li.getTransCount()).divide(new BigDecimal(totalCount*1.0),4,BigDecimal.ROUND_HALF_UP);
			li.setTransRate(df.format(rate.multiply(new BigDecimal(100))) + "%");
		}
		page.setData(list);
		return page;
	}

	@Override
	public List<CountAnalysis> queryWebSiteListInfo(Criteria criteria) {
		List<CountAnalysis> list = countAnalysisDao.queryWebSiteList(criteria);
		int totalCount=0;
		DecimalFormat df = new DecimalFormat("0.00");
		for(CountAnalysis li:list){
			totalCount+=Integer.parseInt(li.getTransCount());
		}
		for(CountAnalysis li:list){
			BigDecimal rate = new BigDecimal(li.getTransCount()).divide(new BigDecimal(totalCount*1.0),4,BigDecimal.ROUND_HALF_UP);
			li.setTransRate(df.format(rate.multiply(new BigDecimal(100))) + "%");
		}
		return list;
	}

	@Override
	public PageInfo<DishonoeTotal> queryDishonoeTotalList(Criteria criteria) {
		PageInfo<DishonoeTotal> page=new PageInfo<DishonoeTotal>(criteria.getPageNum(),criteria.getPageSize());
		String month = (String)criteria.getCondition().get("month");
		List<DishonoeTotal> list = countAnalysisDao.queryDishonoeTotalList(criteria);
		List<DishonoeTotal> lists = new ArrayList<DishonoeTotal>();
		if(StringUtils.isEmpty(month)){
			page.setTotal(12);
			for(int i=1;i<13;i++){
				for(DishonoeTotal li:list){
					if((i+"").equals(li.getMonth())){
						lists.add(li);
					}
				}
				if(i != lists.size()){
					lists.add(newDishonoeTotal(i+""));
				}
			}
		}else{
			for(DishonoeTotal li:list){
				String getMonth = li.getMonth();
				if(1 == li.getMonth().length()){
					getMonth = "0" + getMonth;
				}
				if((month).equals(getMonth)){
					lists.add(li);
				}
			}
			if(0 == lists.size()){
				lists.add(newDishonoeTotal(month));
			}
			page.setTotal(1);
		}
		page.setData(lists);
		return page;
	}
	
	/** 创建一个空对象 */
	private DishonoeTotal newDishonoeTotal(String month){
		DishonoeTotal info = new DishonoeTotal();
		info.setMonth(month);
		info.setTotalNum("0");
		info.setDishonoeNum("0");
		info.setDishonoeColumn("0.00%");
		info.setTotalNumApril("0");
		info.setDishonoeNumApril("0");
		info.setDishonoeColumnApril("0.00%");
		info.setComplaintToDishonoeNum("0");
		info.setComplaintToDishonoeColumn("0.00%");
		info.setComplaintToDishonoeNumApril("0");
		info.setComplaintToDishonoeColumnApril("0.00%");
		return info;
	}

	@Override
	public PageInfo<CountAnalysis> queryCountryList(Criteria criteria) {
		PageInfo<CountAnalysis> page=new PageInfo<CountAnalysis>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(countAnalysisDao.countCountryList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <CountAnalysis> list=countAnalysisDao.queryCountryList(criteria,rb);
		int totalCount=0;
		DecimalFormat df = new DecimalFormat("0.00");
		for(CountAnalysis li:list){
			totalCount+=Integer.parseInt(li.getTransCount());
		}
		for(CountAnalysis li:list){
			BigDecimal rate = new BigDecimal(li.getTransCount()).divide(new BigDecimal(totalCount*1.0),4,BigDecimal.ROUND_HALF_UP);
			li.setTransRate(df.format(rate.multiply(new BigDecimal(100))) + "%");
		}
		page.setData(list);
		return page;
	}

	@Override
	public List<CountAnalysis> queryCountryListInfo(Criteria criteria) {
		List<CountAnalysis> list =countAnalysisDao.queryCountryList(criteria);
		int totalCount=0;
		DecimalFormat df = new DecimalFormat("0.00");
		for(CountAnalysis li:list){
			totalCount+=Integer.parseInt(li.getTransCount());
		}
		for(CountAnalysis li:list){
			BigDecimal rate = new BigDecimal(li.getTransCount()).divide(new BigDecimal(totalCount*1.0),4,BigDecimal.ROUND_HALF_UP);
			li.setTransRate(df.format(rate.multiply(new BigDecimal(100))) + "%");
		}
		return list;
	}

	@Override
	public PageInfo<CountAnalysis> queryFailureList(Criteria criteria) {
		PageInfo<CountAnalysis> page=new PageInfo<CountAnalysis>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(countAnalysisDao.countFailureList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <CountAnalysis> list=countAnalysisDao.queryFailureList(criteria,rb);
		page.setData(list);
		return page;
	}
	/** 失败订单分析 */
	public List<CountAnalysis> queryFailureListAll(Criteria criteria){
		List <CountAnalysis> list=countAnalysisDao.queryFailureList(criteria);
		return list;
	}

	@Override
	public PageInfo<CountAnalysis> queryRiskList(Criteria criteria) {
		PageInfo<CountAnalysis> page=new PageInfo<CountAnalysis>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(countAnalysisDao.countRiskList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <CountAnalysis> list=countAnalysisDao.queryRiskList(criteria,rb);
		List <CountAnalysis> all=countAnalysisDao.queryRiskList(criteria);
		int total=0;
		for(CountAnalysis info:all){
			total+=info.getTransRiskCount();
		}
		for(CountAnalysis info:list){
			info.setRate(info.getTransRiskCount()*1.0/total);
		}
		page.setData(list);
		return page;
	}
	@Override
	public PageInfo<CountAnalysis> queryRiskPendingList(Criteria criteria) {
		PageInfo<CountAnalysis> page=new PageInfo<CountAnalysis>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(countAnalysisDao.countRiskPendingList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <CountAnalysis> list=countAnalysisDao.queryRiskPendingList(criteria,rb);
		List <CountAnalysis> all=countAnalysisDao.queryRiskPendingList(criteria);
		int total=0;
		for(CountAnalysis info:all){
			total+=info.getTransRiskCount();
		}
		for(CountAnalysis info:list){
			info.setRate(info.getTransRiskCount()*1.0/total);
		}
		page.setData(list);
		return page;
	}

	@Override
	public PageInfo<RiskPaddingRateInfo> queryRiskTreat(Criteria criteria) {
		PageInfo<RiskPaddingRateInfo> page=new PageInfo<RiskPaddingRateInfo>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(countAnalysisDao.countRiskTreat(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<RiskPaddingRateInfo> list = countAnalysisDao.queryRiskTreat(criteria,rb);
		page.setData(list);
		return page;
	}
	
	
	@Override
	public PageInfo<DisCount> queryDishonoeCountList(Criteria criteria) {
		PageInfo<DisCount> page=new PageInfo<DisCount>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(countAnalysisDao.countDisCountList(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <DisCount> list=countAnalysisDao.queryDisCountList(criteria,rb);
		for(int i=0;i<list.size();i++){
			DisCount dis=list.get(i);
			try {
				dis.setTradeNos(new String(dis.getTradeNo()==null?new byte[]{}:dis.getTradeNo(),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			list.remove(i);
			list.add(i, dis);
		}
		page.setData(list);
		return page;
	}
	
	@Override
	public List<DisPercent> queryDisPer(Criteria criteria) {
		List<DisPercent> list=countAnalysisDao.queryDisPer(criteria);
		int total=0;
		for(int i=0;i<list.size();i++){
			DisPercent dis=list.get(i);
			total+=dis.getDisCount();
		}
		for(int i=0;i<list.size();i++){
			DisPercent dis=list.get(i);
			dis.setDisRate(dis.getDisCount()*1.0/total);
			list.remove(i);
			list.add(i, dis);
		}
		return list;
	}
	
	@Override
	public PageInfo<CompRateInfo> queryCompRateInfo(Criteria criteria) {
		PageInfo<CompRateInfo> page=new PageInfo<CompRateInfo>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(countAnalysisDao.countComRate(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <CompRateInfo> list=countAnalysisDao.queryCompRate(criteria,rb);
		for(int i=0;i<list.size();i++){
			CompRateInfo dis=list.get(i);
			try {
				dis.setTradeNos(new String(dis.getTradeNo()==null?new byte[]{}:dis.getTradeNo(),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			list.remove(i);
			list.add(i, dis);
		}
		page.setData(list);
		return page;
	}
	@Override
	public List<CompPercent> queryCompPercent(Criteria criteria) {
		List<CompPercent> list=countAnalysisDao.queryCompPer(criteria);
		int total=0;
		for(int i=0;i<list.size();i++){
			CompPercent dis=list.get(i);
			total+=dis.getComCount();
		}
		for(int i=0;i<list.size();i++){
			CompPercent dis=list.get(i);
			dis.setComRate(dis.getComCount()*1.0/total);
			list.remove(i);
			list.add(i, dis);
		}
		return list;
	}
	
	@Override
	public List<String> queryCompCounttradeNos(Criteria criteria){
		return countAnalysisDao.queryCompCounttradeNos(criteria);
	}
	@Override
	public List<RiskPercent> queryShowRiskPerInfo(Criteria criteria) {
		List<RiskPercent> list=countAnalysisDao.queryShowRiskPerInfo(criteria);
		int total=0;
		for(int i=0;i<list.size();i++){
			RiskPercent dis=list.get(i);
			total+=dis.getRiskCount();
		}
		for(int i=0;i<list.size();i++){
			RiskPercent dis=list.get(i);
			dis.setRiskRate(dis.getRiskCount()*1.0/total);
			list.remove(i);
			list.add(i, dis);
		}
		return list;
	}
	@Override
	public List<RiskPercent> queryShowRiskPendingPerInfo(Criteria criteria) {
		List<RiskPercent> list=countAnalysisDao.queryShowRiskPendingPerInfo(criteria);
		int total=0;
		for(int i=0;i<list.size();i++){
			RiskPercent dis=list.get(i);
			total+=dis.getRiskCount();
		}
		for(int i=0;i<list.size();i++){
			RiskPercent dis=list.get(i);
			dis.setRiskRate(dis.getRiskCount()*1.0/total);
			list.remove(i);
			list.add(i, dis);
		}
		return list;
	}
	@Override
	public List<String> queryDownRiskPendingInfo(Criteria criteria) {
		return countAnalysisDao.queryDownRiskPendingInfo(criteria);
	}
	@Override
	public List<String> queryDownRiskInfo(Criteria criteria) {
		return countAnalysisDao.queryDownRiskInfo(criteria);
	}
	
	@Override
	public PageInfo<TransRecord> queryTransRecordInfo(Criteria criteria) {
		PageInfo<TransRecord> page=new PageInfo<TransRecord>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(countAnalysisDao.countTransRecord(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List<TransRecord> list = countAnalysisDao.queryTransRecord(criteria,rb);
		page.setData(list);
		return page;
	}
	@Override
	public List<String> queryFailureInfoByRespMsg(Criteria criteria) {
		return countAnalysisDao.queryFailureInfoByRespMsg(criteria);
	}
	@Override
	public List<String> queryTransRecordInfoForExport(Criteria criteria) {
		return countAnalysisDao.queryTransRecordInfoForExport(criteria);
	}
	@Override
	public List<TotalCurrnecySuccessCount> queryCurrnecyTotalCount(Criteria criteria){
		return countAnalysisDao.queryCurrnecyTotalCount(criteria);
	}
	
	@Override
	public PageInfo<CurrencyDisCount> queryCurrencyDisCount(Criteria criteria) {
		PageInfo<CurrencyDisCount> page=new PageInfo<CurrencyDisCount>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(countAnalysisDao.countCurrencyDisRate(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <CurrencyDisCount> list=countAnalysisDao.currencyDisRate(criteria,rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public List<CurrencyDisCount> exportCurrencyDisCount(Criteria criteria) {
		return countAnalysisDao.currencyDisRate(criteria);
	}
	
	@Override
	public List<DisDesc> queryCurrencyDisDesc(Criteria criteria) {
		List<DisDesc> list=countAnalysisDao.queryCurrencyDisDesc(criteria);
		int total=0;
		for(DisDesc info:list){
			total+=info.getDisCount();
		}
		List<DisDesc> list1=new ArrayList<DisDesc>();
		for(DisDesc info:list){
			double rate=0;
			if(total!=0){
				rate=info.getDisCount()*1.0/total;
			}
			info.setDisRate(rate);
			list1.add(info);
		}
		return list1;
	}
	
	@Override
	public List<Complaint> queryListComplaintInfoList(Criteria criteria) {
		return countAnalysisDao.queryComplaintInfoList(criteria);
	}
	
	@Override
	public PageInfo<CurrencyDisCount> queryMerchantDisCount(Criteria criteria) {
		PageInfo<CurrencyDisCount> page=new PageInfo<CurrencyDisCount>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(countAnalysisDao.countmerchantDisRate(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <CurrencyDisCount> list=countAnalysisDao.merchantDisRate(criteria,rb);
		page.setData(list);
		return page;
	}
	@Override
	public List<CurrencyDisCount> queryMerchantDisCountAll(Criteria criteria) {
		return countAnalysisDao.merchantDisRate(criteria);
	}
	@Override
	public PageInfo<MerchantTransCountRateInfo> queryMerchantTransCountRate(
			Criteria criteria) {
		PageInfo<MerchantTransCountRateInfo> page=new PageInfo<MerchantTransCountRateInfo>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(countAnalysisDao.countMerchantTransCountRate(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <MerchantTransCountRateInfo> list=countAnalysisDao.queryMerchantTransCountRate(criteria,rb);
		page.setData(list);
		return page;
	}
	@Override
	public List<MerchantTransCountRateInfo> queryMerchantTransCountRateAll(
			Criteria criteria) {
		return countAnalysisDao.queryMerchantTransCountRate(criteria);
	}
	
	@Override
	public PageInfo<CountryInfo> queryCountryInfos(Criteria criteria) {
		PageInfo<CountryInfo> page=new PageInfo<CountryInfo>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(countAnalysisDao.countCountryInfos(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <CountryInfo> list=countAnalysisDao.queryCountryInfos(criteria,rb);
		page.setData(list);
		return page;
	}
	@Override
	public Collection<BrandCountInfo> queryBrandCountInfo(Criteria criteria) {
		List<BrandCountInfo> list=countAnalysisDao.queryBrandCountInfo(criteria);
		Map<String, BrandCountInfo> map=new HashMap<String, BrandCountInfo>();
		int totalCount=0;
		for(BrandCountInfo info:list){
				String[] brands=info.getBrands().split(",");
				for(String brand:brands){
					BrandCountInfo temp=map.get(brand);
					if(temp==null){
						totalCount+=info.getTransCount();
						map.put(brand, new BrandCountInfo().add(info));
					}else{
						totalCount+=info.getTransCount();
						map.put(brand, temp.add(info));
					}
				}
		}
		if(criteria.getCondition().get("brand") != null && !"".equals(criteria.getCondition().get("brand")) ){
			String brand=criteria.getCondition().get("brand").toString();
			BrandCountInfo info=map.get(criteria.getCondition().get("brand"));
			map.clear();
			if(info!=null){
				map.put(brand, info);
			}
		}
		for(String key:map.keySet()){
			BrandCountInfo info=map.get(key);
			info.setBrand(key);
			info.setTransRate(info.getTransCount()*1.0/totalCount);
		}
		List<BrandCountInfo> sortInfo=new ArrayList<BrandCountInfo>(map.values());
		return sortInfo;
	}
	@Override
	public List<DisDesc> queryTransRecordDesc(Criteria criteria) {
		List<DisDesc> list=countAnalysisDao.queryTransRecordDesc( criteria);
		int total=0;
		for(DisDesc info:list){
			total+=info.getDisCount();
		}
		List<DisDesc> list1=new ArrayList<DisDesc>();
		for(DisDesc info:list){
			double rate=0;
			if(total!=0){
				rate=info.getDisCount()*1.0/total;
			}
			info.setDisRate(rate);
			list1.add(info);
		}
		return list1;
	}
	
	@Override
	public PageInfo<TransReRunCount> queryTransRerunCountList(Criteria criteria) {
		PageInfo<TransReRunCount> page=new PageInfo<TransReRunCount>(criteria.getPageNum(),criteria.getPageSize());
		page.setTotal(countAnalysisDao.countTransRerunCount(criteria));
		RowBounds rb = new RowBounds(page.getOffset(), criteria.getPageSize());
		List <TransReRunCount> list=countAnalysisDao.queryTransRerunCount(criteria,rb);
		page.setData(list);
		return page;
	}
	
	@Override
	public List<TransReRunCount> queryTransRerunCountListAll(Criteria criteria) {
		return countAnalysisDao.queryTransRerunCount(criteria);
	}
	
	@Override
	public PageInfo<TransCountInfo> queryTransAnalysisInfoList(Criteria criteria) {
		PageInfo<TransCountInfo> pageInfo = new PageInfo<TransCountInfo>(criteria.getPageNum(), criteria.getPageSize());
		int count = countAnalysisDao.queryTransAnalysisCount(criteria);
		pageInfo.setTotal(count);
		RowBounds rb = new RowBounds(pageInfo.getOffset(), pageInfo.getPageSize());
		List<TransCountInfo> list = countAnalysisDao.queryTransAnalysisInfoList(rb, criteria);
		pageInfo.setData(list);
		return pageInfo;
	}
	
	@Override
	public List<ExportTransCountInfo> queryExportTransAnalysisInfoList(
			Criteria criteria) {
		List<ExportTransCountInfo> list = countAnalysisDao.queryExportTransAnalysisInfoList(criteria);
		if(list!=null && list.size()>0){
			for(ExportTransCountInfo info : list){
				criteria.getCondition().put("merNo", info.getMerNo());
				criteria.getCondition().put("terNo", info.getTerNo());
				criteria.getCondition().put("startDate", criteria.getCondition().get("startTransDate"));
				criteria.getCondition().put("endDate", criteria.getCondition().get("endTransDate"));
				List<ExportFaildTransAnalysisInfo> faildList= countAnalysisDao.queryExportFaildTransAnalysisInfoList(criteria);
				int total=0;
				for(ExportFaildTransAnalysisInfo info1 : faildList){
					total+=info1.getDisCount();
				}
				for(ExportFaildTransAnalysisInfo info1: faildList){
					double rate=0;
					if(total!=0){
						rate=info1.getDisCount()*1.0/total;
					}
					info1.setRate(rate);
				}
				info.setFaildList(faildList);
			}
		}
		return list;
	}
	
	@Override
	public List<FaildTransAnalysisInfo> queryFaildTransAnalysisInfoList(
			Criteria criteria) {
		List<FaildTransAnalysisInfo> list = countAnalysisDao.queryFaildTransAnalysisInfoList(criteria);
		int total=0;
		for(FaildTransAnalysisInfo info : list){
			total+=info.getDisCount();
		}
		for(FaildTransAnalysisInfo info: list){
			double rate=0;
			if(total!=0){
				rate=info.getDisCount()*1.0/total;
			}
			info.setRate(rate);
		}
		return list;
	}
	
	@Override
	public List<TransRecordInfo> queryTransInfoList(Criteria criteria) {
		return countAnalysisDao.queryTransInfoList(criteria);
	}
	
	@Override
	public PageInfo<EuropeTransInfo> queryEuropeTransInfoList(
			Criteria criteria) {
		PageInfo<EuropeTransInfo> pageInfo = new PageInfo<EuropeTransInfo>(criteria.getPageNum(), criteria.getPageSize());
		int count = countAnalysisDao.queryEuropeTransInfoListCount(criteria);
		pageInfo.setTotal(count);
		RowBounds rb = new RowBounds(pageInfo.getOffset(), pageInfo.getPageSize());
		List<EuropeTransInfo> list = countAnalysisDao.queryEuropeTransInfoList(rb, criteria);
		if(list!=null && list.size()>0){
			for(EuropeTransInfo europe : list){
				if(europe.getAgentNo()!=null && !("".equals(europe.getAgentNo()))){
					String[] code = europe.getAgentNo().split("&");
					if(code!=null && code.length>0){
						for(int i=0; i<code.length; i++){
							if(code[i]!=null && !("".equals(code[i]))){
								String[] result = code[i].split("=");
								if(result.length==2){
									if("z14".equals(result[0].toLowerCase())){
										Map<String, String> map = InitEuropeInfo.getZ14ResultMap();
										europe.setZ14Name(result[0]);
										europe.setZ14Code(result[1]);
										europe.setZ14Description(map.get(result[1]));
									}
									if("z2".equals(result[0].toLowerCase())){
										Map<String, String> map = InitEuropeInfo.getZ2ResultMap();
										europe.setZ2Name(result[0]);
										europe.setZ2Code(result[1]);
										String key = result[1]!=null?(result[1].length()>1?result[1].replaceAll("^0", ""):result[1]):"";
										europe.setZ2Description(map.get(key));
									}
									if("z6".equals(result[0].toLowerCase())){
										Map<String, String> map = InitEuropeInfo.getZ6ResultMap();
										europe.setZ6Name(result[0]);
										europe.setZ6Code(result[1]);
										europe.setZ6Description(map.get(result[1]));
									}
									if("z5".equals(result[0].toLowerCase())){
										europe.setZ5Name(result[0]);
										europe.setZ5Code(result[1]);
										europe.setZ5Description(result[1]);
									}
								}
							}
						}
					}
				}
//				try {
//					europe.setSixAndFour(((europe.getCheckNo()!=null && !("".equals(europe.getCheckNo())))?Funcs.decrypt(europe.getCheckNo()):"")
//							+"****"+((europe.getLast()!=null && !("".equals(europe.getLast()))?Funcs.decrypt(europe.getLast()):"")));
//				} catch (Exception e) {
//					continue;
//				}
			}
		}
		pageInfo.setData(list);
		return pageInfo;
	}
	
	@Override
	public List<ExportEuropeInfo> queryExportEuropeTransInfoList(
			Criteria criteria) {
		List<ExportEuropeInfo> list = countAnalysisDao.queryExportEuropeTransInfoList(criteria);
		return list;
	}
	
	@Override
	public List<EuropeChannelInfo> queryEuropeChannelInfoList(Criteria criteria) {
		List<EuropeChannelInfo> list = new ArrayList<EuropeChannelInfo>();
		String agentNo = countAnalysisDao.queryEuropeChannelInfo(criteria);
		if(agentNo!=null && !("".equals(agentNo))){
			String[] code = agentNo.split("&");
			if(code!=null && code.length>0){
				for(int i=0; i<code.length; i++){
					if(code[i]!=null && !("".equals(code[i]))){
						String[] result = code[i].split("=");
						if(result.length==2){
							if("z14".equals(result[0].toLowerCase())){
								EuropeChannelInfo europe = new EuropeChannelInfo();
								Map<String, String> map = InitEuropeInfo.getZ14ResultMap();
								europe.setName(result[0]);
								europe.setCode(result[1]);
								europe.setDescription(map.get(result[1]));
								list.add(europe);
							}
							if("z2".equals(result[0].toLowerCase())){
								Map<String, String> map = InitEuropeInfo.getZ2ResultMap();
								EuropeChannelInfo europe = new EuropeChannelInfo();
								europe.setName(result[0]);
								europe.setCode(result[1]);
								String key = result[1]!=null?(result[1].length()>1?result[1].replaceAll("^0", ""):result[1]):"";
								europe.setDescription(map.get(key));
								list.add(europe);
							}
							if("z6".equals(result[0].toLowerCase())){
								Map<String, String> map = InitEuropeInfo.getZ6ResultMap();
								EuropeChannelInfo europe = new EuropeChannelInfo();
								europe.setName(result[0]);
								europe.setCode(result[1]);
								europe.setDescription(map.get(result[1]));
								list.add(europe);
							}
							if("z5".equals(result[0].toLowerCase())){
								EuropeChannelInfo europe = new EuropeChannelInfo();
								europe.setName(result[0]);
								europe.setCode(result[1]);
								europe.setDescription(result[1]);
								list.add(europe);
							}
						}
					}
				}
			}
		}
		return list;
	}
	
	@Override
	public List<TransHourCount> queryTransHourCount(Criteria criteria) {
//		PageInfo<TransHourCount> pageInfo = new PageInfo<TransHourCount>(criteria.getPageNum(), criteria.getPageSize());
//		pageInfo.setTotal(48);
//		RowBounds rb = new RowBounds(pageInfo.getOffset(), pageInfo.getPageSize());
		List<TransHourCount> list = countAnalysisDao.queryTransHourCount( criteria);
//		pageInfo.setData(list);
		return list;
	}
	
	@Override
	public PageInfo<TransOrDisPercent> queryTransDisPercent(Criteria criteria) {
		PageInfo<TransOrDisPercent> page = new PageInfo<TransOrDisPercent>(criteria.getPageNum(), criteria.getPageSize());
		int count = countAnalysisDao.countTransOrDisPercent(criteria);
		page.setTotal(count);
		RowBounds rb = new RowBounds(page.getOffset(), page.getPageSize());
		List<TransOrDisPercent> list = countAnalysisDao.queryTransDisPercent(rb, criteria);
		page.setData(list);
		return page;
	}
	@Override
	public List<TransOrDisPercent> queryTransOrDisPercentList(Criteria criteria) {
		return countAnalysisDao.queryTransDisPercent(criteria);
	}
}
