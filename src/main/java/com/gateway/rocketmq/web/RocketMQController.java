package com.gateway.rocketmq.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.rocketmq.model.ResultPayment;
import com.gateway.rocketmq.service.RocketMQProducerService;

@Controller
@RequestMapping(value = "/rocketmq")
public class RocketMQController extends BaseController {

	@Autowired
	private RocketMQProducerService rocketMQProducerService;

	/**
	 * 异步通知
	 * */
	@RequestMapping(value = "/asyncProducer")
	public ModelAndView asyncProducer(ResultPayment resultPayment) {
		boolean asyncStatus = rocketMQProducerService.asyncProducer(resultPayment);
		if (asyncStatus) {
			return ajaxDoneSuccess("异步通知已申请");
		}
		return ajaxDoneError("异步通知申请失败");
	}
}
