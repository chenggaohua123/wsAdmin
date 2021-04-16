package com.gateway.currency.mapper;

import org.apache.ibatis.annotations.Param;

public interface CurrencyMapper {
	/**
	 * 根据ids查询通道名称
	 * @param currencyIds
	 * @return 通道名称
	 */
	public String getCurrencyDayAmountNamesByIds(@Param("currencyDayAmountIds")String currencyDayAmountIds);
	
}
