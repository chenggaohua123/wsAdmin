package com.gateway.rocketmq.mapper;

import org.apache.ibatis.annotations.Param;

import com.gateway.rocketmq.model.ResultPayment;

public interface RocketMQMapper {
	/**
	 * 通过商户号及终端号获取shaKey
	 * 
	 * */
	public ResultPayment getTransInfo(@Param("resultPayment")ResultPayment resultPayment);
}
