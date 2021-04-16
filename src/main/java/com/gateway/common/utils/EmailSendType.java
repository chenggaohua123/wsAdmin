package com.gateway.common.utils;

public class EmailSendType {
	/** 支付成功发送邮件给持卡人  **/
	public static final String SENDTRANSSUCCESSTOCARDHOLDER="sendTransSuccessToCardHolder";
	/** 支付成功发送邮件给商户  **/
	public static final String SENDTRANSSUCCESSTOMERCHANT="sendTransSuccessToMerchant";
	/** 支付失败发送邮件给持卡人  **/
	public static final String SENDTRANSFAILTOCARDHOLDER="sendTransFailToCardHolder";
	/** 运单上传成功发送邮件给持卡人  **/
	public static final String SENDCREATESHIPMENTTOCARDHOLDER="sendCreateShipmentToCardHolder";
	/** 退款成功发送邮件给持卡人  **/
	public static final String SENDREFUNDSUCCESSTOCARDHOLDER="sendRefundSuccessToCardHolder";
	/** 添加拒付订单发送邮件给商户  **/
	public static final String SENDADDREFUSETOMERCHANT="sendAddRefuseToMerchant";
	/** 新建投诉订单发送邮件给商户  **/
	public static final String SENDADDCOMPLAINTOMERCHANT="sendAddComplainToMerchant";
	/** 投诉订单回复发送邮件给商户  **/
	public static final String SENDREPLYCOMPLAINTOMERCHANT="sendReplyComplainToMerchant";
	/** 投诉订单回复发送邮件给持卡人  **/
	public static final String SENDREPLYCOMPLAINTOCARDHOLDER="sendReplyComplainToCardHolder";
}
