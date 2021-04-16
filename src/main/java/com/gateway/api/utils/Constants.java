package com.gateway.api.utils;

public class Constants {
	
	//默认代理商户号
	public static String DEFAULE_AGENT_NO = "13570812759";
	//无效用户
	public static int USER_STATUS_0 = 0;
	//未开通收款，注册是初始化状态
	public static int USER_STATUS_1 = 1;
	//待审核
	public static int USER_STATUS_2 = 2;
	//已开通收款
	public static int USER_STATUS_3 = 3;
	//关闭用户
	public static int USER_STATUS_4 = 4;
	//商务版用户
	public static int USER_STATUS_5 = 5;
	//个人版用户
	public static int USER_STATUS_6 = 6;
	
	
	//手机用户
	public static String USER_SYSTEMID = "3";
	
	public static String API_ERROE_CODE_0000 = "0000";
	public static String API_ERROE_CODE_0000_DESC = "成功";
	
	public static String API_ERROE_CODE_0001 = "0001";
	public static String API_ERROE_CODE_0001_DESC = "用户没有登录权限。";
	
	public static String API_ERROE_CODE_0002 = "0002";
	public static String API_ERROE_CODE_0002_DESC = "用户名称不能为空。";
	
	public static String API_ERROE_CODE_0003 = "0003";
	public static String API_ERROE_CODE_0003_DESC = "手机号不能为空。";
	
	public static String API_ERROE_CODE_0004 = "0004";
	public static String API_ERROE_CODE_0004_DESC = "密码不能为空。";

	public static String API_ERROE_CODE_0005 = "0005";
	public static String API_ERROE_CODE_0005_DESC = "邮箱不能为空。";
	
	public static String API_ERROE_CODE_0006 = "0006";
	public static String API_ERROE_CODE_0006_DESC = "代理商户号不存在。";
	
	public static String API_ERROE_CODE_0007 = "0007";
	public static String API_ERROE_CODE_0007_DESC = "该手机号已经注册了。。";
	
	public static String API_ERROE_CODE_0008 = "0008";
	public static String API_ERROE_CODE_0008_DESC = "用户不存在。";
	
	public static String API_ERROE_CODE_0009 = "0009";
	public static String API_ERROE_CODE_0009_DESC = "没有可用通道分配。";
	
	public static String API_ERROE_CODE_0010 = "0010";
	public static String API_ERROE_CODE_0010_DESC = "该用户状态不允许开通收款功能";
	
	public static String API_ERROE_CODE_0011 = "0011";
	public static String API_ERROE_CODE_0011_DESC = "用户已经关闭。不允许登陆";

	public static String API_ERROE_CODE_0012 = "0012";
	public static String API_ERROE_CODE_0012_DESC = "商户号不存在。";
	
	public static String API_ERROE_CODE_0013 = "0013";
	public static String API_ERROE_CODE_0013_DESC = "该图片已经存在。";
	
	public static String API_ERROE_CODE_0014 = "0014";
	public static String API_ERROE_CODE_0014_DESC = "该图片不存在。";
	
	public static String API_ERROE_CODE_0015 = "0015";
	public static String API_ERROE_CODE_0015_DESC = "发送短信内容不能为空";
	
	public static String API_ERROE_CODE_0016 = "0016";
	public static String API_ERROE_CODE_0016_DESC = "终端SN号不能为空";
	
	public static String API_ERROE_CODE_0017 = "0017";
	public static String API_ERROE_CODE_0017_DESC = "该终端已经使用。";
	
	public static String API_ERROE_CODE_0018 = "0018";
	public static String API_ERROE_CODE_0018_DESC = "系统类型不能为空。";
	
	public static String API_ERROE_CODE_0019 = "0019";
	public static String API_ERROE_CODE_0019_DESC = "当前系统类型查询不到版本号。";
	
	public static String API_ERROE_CODE_0020 = "0020";
	public static String API_ERROE_CODE_0020_DESC = "商户号不能为空。";
	
	public static String API_ERROE_CODE_0021 = "0021";
	public static String API_ERROE_CODE_0021_DESC = "找回密码发送短信失败。";
	
	public static String API_ERROE_CODE_0022 = "0022";
	public static String API_ERROE_CODE_0022_DESC = "找回密码发送短信失败。";
	
	public static String API_ERROE_CODE_0023 = "0023";
	public static String API_ERROE_CODE_0023_DESC = "限额没有设置。";
	
	public static String API_ERROE_CODE_0024 = "0024";
	public static String API_ERROE_CODE_0024_DESC = "统计类型不能为空。";
	
	public static String API_ERROE_CODE_0025 = "0025";
	public static String API_ERROE_CODE_0025_DESC = "终端设备SN号不能为空。";
	
	public static String API_ERROE_CODE_0026 = "0026";
	public static String API_ERROE_CODE_0026_DESC = "终端设备SN号无效，或者未下发到代理。";
	
	public static String API_ERROE_CODE_0027 = "0027";
	public static String API_ERROE_CODE_0027_DESC = "终端设备SN号绑定的代理与该账号绑定的代理不一致。";
	
	public static String API_ERROE_CODE_0028 = "0028";
	public static String API_ERROE_CODE_0028_DESC = "该终端号已经被使用。";
	
	public static String API_ERROE_CODE_9999 = "9999";
	public static String API_ERROE_CODE_9999_DESC = "操作失败。";
	
	/** 交易复核类型 */
	public static String TRSAN_INFO_TYPE_CHECK = "check";
	/** 交易退款类型 */
	public static String TRSAN_INFO_TYPE_REFUND = "refund";
	/** 交易解冻类型 */
	public static String TRSAN_INFO_TYPE_THAW = "thaw";
	/** 交易冻结类型 */
	public static String TRSAN_INFO_TYPE_FROZEN = "frozen";
	/** 交易拒付类型 */
	public static String TRSAN_INFO_TYPE_DISHONOR = "dishonor";
	
	/** 汇率保存，修改类型-- 保存 */
	public static String EXCHANG_RATE_OPERATION_TYPE_INSERT = "insert";
	/** 汇率保存，修改类型-- 修改 */
	public static String EXCHANG_RATE_OPERATION_TYPE_UPDATE = "update";
	
	
}
