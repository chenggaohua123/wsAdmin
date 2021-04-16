package com.gateway.rocketmq.service;

import com.gateway.rocketmq.model.ResultPayment;


/**
 * RocketMQProducerService
 */
public interface RocketMQProducerService {
	/**
	 * 处理时订单异步通知
	 * 
	 * @param resultPayment
	 */
	public boolean asyncProducer(ResultPayment resultPayment);

}
