<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="funcs" uri="funcs"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>风控详细信息</title>
</head>
<body>
<div class="pageContent">
	<div class="tabs" currentIndex="0" eventType="click">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span>订单信息</span></a></li>
					<li><a href="javascript:;"><span>风控信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" style="height:100%;">
			<div class="pageFormContent" layoutH="60">
					<dl>
						<dt>流水号：</dt>
						<dd>${info.txId }</dd>
					</dl>
					<dl>
						<dt>订单号：</dt>
						<dd>${info.orderNo }</dd>
					</dl>
					<dl>
						<dt>风控账号：</dt>
						<dd>${info.accountNo }</dd>
					</dl>
					<dl>
						<dt>规则集合ID：</dt>
						<dd>${info.profileId }</dd>
					</dl>
					<dl>
						<dt>订单金额：</dt>
						<dd>${info.sourceCurrency } &nbsp; ${info.sourceAmount }</dd>
					</dl>
					<dl>
						<dt>来源网址：</dt>
						<dd>${info.website }</dd>
					</dl>
					<dl>
						<dt>支付卡种：</dt>
						<dd>${info.cardType }</dd>
					</dl>
					<dl>
						<dt>支付时间：</dt>
						<dd>${info.createDate }</dd>
					</dl>										
					<dl>
						<dt>Bin Number：</dt>
						<dd>${info.cardBin }</dd>
					</dl>
					<dl>
						<dt>加密卡号：</dt>
						<dd>${info.hashCardNo }</dd>
					</dl>
					<dl>
						<dt>收货人姓名：</dt>
						<dd>${info.shipFirstName } ${info.shipLastName }</dd>
					</dl>
					<dl>
						<dt>收货国家：</dt>
						<dd>${info.shipCountry }</dd>
					</dl>
					<dl>
						<dt>收货城市：</dt>
						<dd>${info.shipCity }</dd>
					</dl>
					<dl>
						<dt>收货州：</dt>
						<dd>${info.shipState }</dd>
					</dl>
					<dl>
						<dt>收货地址：</dt>
						<dd>${info.shipAddress }</dd>
					</dl>
					<dl>
						<dt>账单姓名：</dt>
						<dd>${info.billFirstName } ${info.billLastName }</dd>
					</dl>
					<dl>
						<dt>账单国家：</dt>
						<dd>${info.billCountry }</dd>
					</dl>
					<dl>
						<dt>账单城市：</dt>
						<dd>${info.billCity }</dd>
					</dl>
					<dl>
						<dt>账单州：</dt>
						<dd>${info.billState }</dd>
					</dl>
					<dl>
						<dt>持卡人邮箱：</dt>
						<dd>${info.email }</dd>
					</dl>
					<dl>
						<dt>持卡人电话：</dt>
						<dd>${info.phone }</dd>
					</dl>
					<dl>
						<dt>持卡人邮编：</dt>
						<dd>${info.zip }</dd>
					</dl>
					<dl>
						<dt>返回码：</dt>
						<dd>${info.ret }</dd>
					</dl>
					<dl>
						<dt>返回信息：</dt>
						<dd>${info.msg }</dd>
					</dl>
				</div>
			<div class="pageFormContent" layoutH="60">
					<dl>
						<dt>风控分数：</dt>
						<dd>${info.fraudScore }</dd>
					</dl>
					<dl>
						<dt>支付IP：</dt>
						<dd>${info.ipAddress }</dd>
					</dl>
					<dl>
						<dt>IP所在国家：</dt>
						<dd>${info.IPCountry } /${info.ipCountryName }/${info.ipCountryConf}%</dd>
					</dl>
					<dl>
						<dt>IP所在城市：</dt>
						<dd>${info.ipCity }/${info.ipCityConf }%</dd>
					</dl>
					<dl>
						<dt>IP所在州：</dt>
						<dd>${info.ipRegion } / ${info.ipRegionName }/${info.ipRegionConf }%</dd>
					</dl>
					<dl>
						<dt>IP美国区号：</dt>
						<dd>${info.ipAreaCode }</dd>
					</dl>
					
					<dl>
						<dt>支付IP所在地邮编：</dt>
						<dd>${info.ipPostalCode }/${info.ipPostalConf }%</dd>
					</dl>
					<dl>
						<dt>是否是高危国家：</dt>
						<dd>${info.highRiskCountry }</dd>
					</dl>
					<dl>
						<dt>IP所在地时区：</dt>
						<dd>${info.ipTimeZone }</dd>
					</dl>
					<dl>
						<dt>IP所在大洲：</dt>
						<dd>${info.ipContinentCode }</dd>
					</dl>
					<dl>
						<dt>ip自治系统号：</dt>
						<dd>${info.ipAsnum }</dd>
					</dl>

					<dl>
						<dt>IP用户类型：</dt>
						<dd>${info.ipUserType }</dd>
					</dl>
					<dl>
						<dt>网络连接类型：</dt>
						<dd>${info.ipNetSpeedCell }</dd>
					</dl>
					<dl>
						<dt>IP所在的二级域名：</dt>
						<dd>${info.ipDomain }</dd>
					</dl>
					<dl>
						<dt>IP所在ISP：</dt>
						<dd>${info.ipIsp }</dd>
					</dl>
					<dl>
						<dt>IP所在单位：</dt>
						<dd>${info.ipOrg }</dd>
					</dl>
					<dl>
						<dt>是否为匿名代理：</dt>
						<dd>${info.anonymousProxy }</dd>
					</dl>
					<dl>
						<dt>是否为透明代理：</dt>
						<dd>${info.isTransProxy }</dd>
					</dl>
					<dl>
						<dt>代理分数：</dt>
						<dd>${info.proxyScore }</dd>
					</dl>					
					
					<dl>
						<dt>是否是免费邮箱：</dt>
						<dd>${info.freeMail }</dd>
					</dl>
					<dl>
						<dt>发卡行：</dt>
						<dd>${info.binName }</dd>
					</dl>
					<dl>
						<dt>发卡国家：</dt>
						<dd>${info.binCountry }</dd>
					</dl>					
					
					<dl>
						<dt>发卡行电话：</dt>
						<dd>${info.binPhone }</dd>
					</dl>					
					<dl>
						<dt>是否是预付卡：</dt>
						<dd>${info.prepaid }</dd>
					</dl>	
					<dl>
						<dt>是否是高风险邮箱：</dt>
						<dd>${info.carderEmail }</dd>
					</dl>
					<dl>
						<dt>是否为高危地址：</dt>
						<dd>${info.shipForward }</dd>
					</dl>					
					<dl>
						<dt>IP和账单国家是否匹配：</dt>
						<dd>${info.countryMatch }</dd>
					</dl>	
					<dl>
						<dt width="100px">是否为知名公司代理（VPN之类）：</dt>
						<dd>${info.ipCorporateProxy }</dd>
					</dl>
					<dl>
						<dt width="100px">maxmind返回错误信息：</dt>
						<dd>${info.err }</dd>
					</dl>					
			</div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>
</body>
</html>