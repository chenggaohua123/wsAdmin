package com.gateway.rocketmq.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gateway.common.utils.SHA256Utils;
import com.gateway.rocketmq.mapper.RocketMQMapper;
import com.gateway.rocketmq.model.ResultPayment;
import com.gateway.rocketmq.service.RocketMQProducerService;

@Service
public class RocketMQProducerServiceImpl implements RocketMQProducerService {
	private static Log logger = LogFactory.getLog(RocketMQProducerServiceImpl.class);
	@Autowired
	private RocketMQMapper rocketMQMapper;

	/**
	 * 生产者的组名
	 */
	@Value("${apache.rocketmq.producer.producerGroup}")
	private String producerGroup;

	/**
	 * NameServer 地址
	 */
	@Value("${apache.rocketmq.namesrvAddr}")
	private String namesrvAddr;

	@Override
	public boolean asyncProducer(ResultPayment resultPayment) {
		String merNotifyURL = resultPayment.getMerNotifyURL();
		ResultPayment newResultPayment = rocketMQMapper.getTransInfo(resultPayment);
		if (null != newResultPayment) {
			String merNo = newResultPayment.getMerNo();
			String terNo = newResultPayment.getTerNo();
			String tradeNo = newResultPayment.getTradeNo();
			String orderNo = newResultPayment.getOrderNo();
			String respCode = newResultPayment.getRespCode();
			String respMsg = newResultPayment.getRespMsg();
			String transType = newResultPayment.getTransType();
			String amountStr = newResultPayment.getAmount();
			BigDecimal amount = new BigDecimal(amountStr);
			amount = amount.setScale(2, RoundingMode.HALF_UP);
			String currencyCode = newResultPayment.getCurrencyCode();
			String shaKey = newResultPayment.getShaKey();
			StringBuffer sb = new StringBuffer();
			sb.append("amount=" + amount + "&");
			sb.append("currencyCode=" + currencyCode + "&");
			sb.append("merNo=" + merNo + "&");
			sb.append("orderNo=" + orderNo + "&");
			sb.append("respCode=" + respCode + "&");
			sb.append("respMsg=" + respMsg + "&");
			sb.append("terNo=" + terNo + "&");
			sb.append("tradeNo=" + tradeNo + "&");
			sb.append("transType=" + transType + "&");
			sb.append(shaKey);
			String hashcode = SHA256Utils.getSHA256Encryption(sb.toString());
			// 生产者的组名
			final DefaultMQProducer producer = new DefaultMQProducer(
					producerGroup);
			// 指定NameServer地址，多个地址以 ; 隔开
			producer.setNamesrvAddr(namesrvAddr);
			try {
				// 生产者开始
				producer.start();
				// 生产者发送失败重试次数
				producer.setRetryTimesWhenSendAsyncFailed(2);
				// 关键信息
				final String orderMsg = "{\"transType\":\""+transType+"\"," 
						+ "\"orderNo\":\""+ orderNo + "\"," 
						+ "\"merNo\":\""+ merNo + "\"," 
						+ "\"terNo\":\""+ terNo + "\"," 
						+ "\"tradeNo\":\"" + tradeNo + "\","
						+ "\"amount\":\"" + amount + "\","
						+ "\"currencyCode\":\"" + currencyCode + "\","
						+ "\"orderNo\":\"" + orderNo + "\","
						+ "\"respCode\":\""+ respCode + "\","
						+ "\"merNotifyURL\":\""+ merNotifyURL + "\","
						+ "\"respMsg\":\""+ respMsg + "\","
						+ "\"hashcode\":\""+ hashcode + "\"}";
				// 发送的信息
				Message msg = new Message("TOPIC_MERCHANT_ORDER", "NOTIFY",orderMsg.getBytes(RemotingHelper.DEFAULT_CHARSET));
				/**
				 * 目前RocketMQ免费版只支持固定精度级别的定时消息，服务器按照1-N定义了如下级别： “1s 5s 10s 30s
				 * 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h”；
				 * 若要发送定时消息，在应用层初始化Message消息对象之后， 调用setDelayTimeLevel(int
				 * level)方法来设置延迟级别，按照序列取相应的延迟级别，例如level=2，则延迟为5s:
				 */
				msg.setDelayTimeLevel(3); // 设置成为10S
				// 异步发送回调
				producer.send(msg, new SendCallback() {
					@Override
					public void onSuccess(SendResult sendResult) {
						logger.info("RocketMQProducerService发送异步onSuccess：MsgId:"
								+ sendResult.getMsgId()
								+ "，发送状态:"
								+ sendResult.getSendStatus()
								+ ",信息内容："
								+ orderMsg);
						producer.shutdown();// 关闭生产者
					}

					@Override
					public void onException(Throwable e) {
						e.printStackTrace();
						logger.info("RocketMQProducerService发送异常"+ e.getMessage() + "#消息内容：" + orderMsg);
						producer.shutdown();// 关闭生产者
					}
				});
				return true;
			} catch (UnsupportedEncodingException | MQClientException
					| RemotingException | InterruptedException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

}
