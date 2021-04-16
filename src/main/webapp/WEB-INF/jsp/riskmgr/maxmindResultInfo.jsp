<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>maxmind详细信息</title>
</head>
<body>
<div class="pageContent"  layoutH="0">
<div class="tabs" currentIndex="0" eventType="click" style="width:100%">
		<div class="tabsContent pageFormContent" >
			<div>
				 <dl>
		               <dt>流水号</dt>
					   <dd>${transInfo.tradeNo }</dd>
				</dl>
		         <dl>
		             <dt>分数</dt>
			         <dd>${transInfo.riskScore }</dd>
			     </dl>
			     <dl>
		             <dt>设备ID</dt>
			         <dd>${transInfo.uid }</dd>
			     </dl>
				<dl> 
				     <dt>国家匹配</dt>
				     <dd>${transInfo.countryMatch }</dd>
		         </dl>
		       
				<dl>	   
					   <dt>高危国家</dt>
					   <dd>${transInfo.highRiskCountry}</dd>
		        </dl>
		        <dl>	   
					   <dt>IP到账单地址距离</dt>
					   <dd>${transInfo.distance} km </dd>
		         </dl>
		       
				<dl>	   
					   <dt>ip精准范围</dt>
					   <dd>${transInfo.ip_accuracyRadius} </dd>
		         </dl>
		         
		        <dl>
		               <dt>ip城市</dt>
					   <dd>${transInfo.ip_city} </dd>
				</dl>
				<dl>	   
					   <dt>ip州/省</dt>
					   <dd>${transInfo.ip_region}</dd>
		         </dl>
		         <dl>	   
					   <dt>IP地区名字</dt>
					   <dd>${transInfo.ip_regionName}</dd>
		         </dl>
		         <dl>	   
					   <dt>IP邮编</dt>
					   <dd>${transInfo.ip_postalCode}</dd>
		         </dl>
		         <dl>	   
					   <dt>IP metrocode</dt>
					   <dd>${transInfo.ip_metroCode}</dd>
		         </dl>
		         <dl>	   
					   <dt>IP区号</dt>
					   <dd>${transInfo.ip_areaCode} </dd>
		         </dl>
		         <dl>
		              <dt>国家</dt>
					   <dd>${transInfo.countryCode }</dd>
				</dl>
				<dl>
		              <dt>IP国家名字</dt>
					   <dd>${transInfo.ip_countryName }</dd>
				</dl>
					 <dt>IP所在大洲</dt>
					   <dd>${transInfo.ip_continentCode }</dd>
				<dl>	   
		         </dl>
		         <dl>	   
					   <dt>IP纬度</dt>
					<dd> 
						<font color="red">${transInfo.ip_latitude }</font>
					</dd>
		         </dl>
		        <dl>	   
					   <dt>IP经度</dt>
					<dd> 
						<font color="red">${transInfo.ip_longitude }</font>
					</dd>
		         </dl>
		       <dl>
		              <dt> IP时区</dt>
					   <dd>${transInfo.ip_timeZone} </dd>
				</dl>
				<dl>	   
					   <dt>ip_asnum</dt>
					   <dd>
						<font color="red">${transInfo.ip_asnum}</font>
					</dd>
		         </dl>
				 <dl>
		               <dt> 用户类型</dt>
					   <dd>${transInfo.ip_userType}</dd>
				</dl>
				
		         <dl>
		               <dt> 网络连接类型</dt>
					   <dd>${transInfo.ip_netSpeedCell}</dd>
				</dl>
		        <dl>
		              <dt>IP所在的二级域名</dt>
					   <dd>${transInfo.ip_domain}</dd>
				</dl>
				<dl>	   
					   <dt>IP所在ISP</dt>
					   <dd>${transInfo.ip_isp }</dd>
		         </dl>
		        <dl>
		               <dt>IP所在单位</dt>
					   <dd>${transInfo.ip_org}</dd>
				</dl>
				<dl>	   
					   <dt>IP城市对的可能性</dt>
					   <dd>${transInfo.ip_cityConf}</dd>
		         </dl>
				<dl>	   
					   <dt>IP地域对的可能性</dt>
					   <dd>${transInfo.ip_regionConf}</dd>
		         </dl>
				<dl>	   
					   <dt>IP邮编对的可能性</dt>
					   <dd>${transInfo.ip_postalConf}</dd>
		         </dl>
				<dl>	   
					   <dt>IP国家对的可能性</dt>
					   <dd>${transInfo.ip_countryConf}</dd>
		         </dl>
				<dl>	   
					   <dt>是否为匿名代理</dt>
					   <dd>${transInfo.anonymousProxy }</dd>
		         </dl>
		         <dl>	   
					   <dt>proxyScore</dt>
					   <dd>${transInfo.proxyScore}</dd>
		         </dl>
		         <dl>	   
					   <dt>是否为透明代理</dt>
					   <dd>${transInfo.isTransProxy}</dd>
		         </dl>
		        
		         <dl>	   
					   <dt>是否为知名公司代理</dt>
					   <dd>${transInfo.ip_corporateProxy}</dd>
		         </dl>
		         <dl>	   
					   <dt>是否为免费邮件</dt>
					   <dd>${transInfo.freeMail}</dd>
		         </dl>
		         <dl>	   
					   <dt>高危邮箱</dt>
					   <dd>${transInfo.carderEmail} </dd>
		         </dl>
		         <dl>	   
					   <dt>银行BIN匹配</dt>
					   <dd>${transInfo.binMatch}</dd>
		         </dl>
		         
		         <dl>	   
					   <dt>BIN国家</dt>
					   <dd>${transInfo.binCountry}</dd>
		         </dl>
		          <dl>	   
					   <dt>用户提供的bin是否符合</dt>
					   <dd>${transInfo.binNameMatch}</dd>
		         </dl>
		          <dl>	   
					   <dt>BIN银行名字</dt>
					   <dd>${transInfo.binName}</dd>
		         </dl>
		         <dl>	   
					   <dt>电话号码BIN匹配</dt>
					   <dd>${transInfo.binPhoneMatch}</dd>
		         </dl>
		          <dl>	   
					   <dt>BIN电话号码</dt>
					   <dd>${transInfo.binPhone} </dd>
		         </dl>
		         <dl>	   
					   <dt>是否为预付卡</dt>
					   <dd>${transInfo.prepaid}</dd>
		         </dl>
		         <dl>	   
					   <dt>电话号码区号是否属于该邮编</dt>
					   <dd>${transInfo.custPhoneInBillingLoc}</dd>
		         </dl>
		          <dl>	   
					   <dt>高危收货地址</dt>
					   <dd>${transInfo.shipForward}</dd>
		         </dl>
		          <dl>	   
					   <dt>账单城市和邮编是否吻合</dt>
					   <dd>${transInfo.cityPostalMatch}</dd>
		         </dl>
		          <dl>	   
					   <dt>寄送城市是否和邮编吻合</dt>
					   <dd>
					 ${transInfo.shipCityPostalMatch}
					   </dd>
		         </dl>
		           <dl>	   
					   <dt>错误</dt>
					   <dd>${transInfo.err}</dd>
		         </dl>
		       </div>
         </div>
  </div>
</div>
</body>
</html>