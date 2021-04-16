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
<title>ThreatMetrix返回信息详情</title>
</head>
<body>
<div class="pageContent"  layoutH="0">
<div class="tabs" currentIndex="0" eventType="click" style="width:100%">
		<div class="tabsContent pageFormContent" >
			<div>
				 <dl>
		               <dt>订单号</dt>
					   <dd>${info.tradeNo }</dd>
				</dl>
		         <dl>
		         	 <dt>帐户邮箱地址</dt>
			         <dd>${info.account_email }</dd>
			     </dl>
				<dl> 
				     <dt>帐户邮箱地址第一次看到时间</dt>
				     <dd>${info.account_email_first_seen }</dd>
		         </dl>
		       
				<dl>	   
					   <dt>帐户邮箱地址最后事件时间</dt>
					   <dd>${info.account_email_last_event}</dd>
		        </dl>
		        <dl>	   
					   <dt>帐户邮箱地址上次更新时间</dt>
					   <dd>${info.account_email_last_update}</dd>
		         </dl>
		       
				<dl>	   
					   <dt>帐户邮箱返回原因</dt>
					   <dd>${info.account_email_result}</dd>
		         </dl>
		         
		        <dl>
		               <dt>帐户邮箱地址评分</dt>
					   <dd>${info.account_email_score}</dd>
				</dl>
				<dl>	   
					   <dt>帐户邮箱地址最差评分</dt>
					   <dd>${info.account_email_worst_score }</dd>
		         </dl>
		         <dl>	   
					   <dt>帐户名称</dt>
					   <dd>${info.account_name }</dd>
		         </dl>
		         <dl>	   
					   <dt>帐户名称第一次看到时间</dt>
					   <dd>${info.account_name_first_seen }</dd>
		         </dl>
		         <dl>	   
					   <dt>帐户名称最后事件时间</dt>
					   <dd>${info.account_name_last_event}</dd>
		         </dl>
		         <dl>	   
					   <dt>帐户名称上次更新时间</dt>
					   <dd>${info.account_name_last_update} </dd>
		         </dl>
		           <dl>	   
					   <dt>帐户名称结果</dt>
					   <dd>${info.account_name_result}</dd>
		         </dl>
		          <dl>	   
					   <dt>帐户名称评分</dt>
					   <dd>${info.account_name_score}</dd>
		         </dl>
		       <dl>	   
					   <dt>帐户名称最差评分</dt>
					   <dd>${info.account_name_worst_score}</dd>
		         </dl>
		         <dl>	   
					   <dt>帐户电话</dt>
					 	<dd>${info.account_telephone }</dd>
		         </dl>
		         <dl>
		              <dt>帐户电话第一次看到时间</dt>
					   <dd>${info.account_telephone_first_seen }</dd>
				</dl>
				<dl>
		              <dt>帐户电话最后事件时间</dt>
					   <dd>${info.account_telephone_last_event }</dd>
				</dl>
				<dl>
		              <dt>帐户电话上次更新时间</dt>
					   <dd>
					   ${info.account_telephone_last_update }
					</dd>
				</dl>
				
				<dl>	   
					   <dt>帐户电话结果</dt>
						<dd>${info.account_telephone_result }</dd>
		         </dl>
		         <dl>	   
					   <dt>帐户电话评分</dt>
						<dd>${info.account_telephone_score }</dd>
		         </dl>
		        
		       <dl>
		              <dt>帐户电话最差评分</dt>
					   <dd>${transInfo.account_telephone_worst_score}</dd>
				</dl>
				<dl>	   
					   <dt>代理类型</dt>
						<dd>
							${info.agent_type }
						</dd>
		         </dl>
				 <dl>
		               <dt>事件时间</dt>
					   <dd>${info.api_call_datetime}</dd>
				</dl>
				
				 <dl>	   
					   <dt>API 版本</dt>
					<dd>
						${info.api_version }
					</dd>
		         </dl>
		         <dl>
		               <dt>浏览器</dt>
					   <dd>${info.browser }</dd>
				</dl>
		        <dl>
		              <dt>浏览器语言</dt>
					   <dd>${info.browser_language}</dd>
				</dl>
				<dl>	   
					   <dt>浏览器 String</dt>
					   <dd>${info.browser_string }</dd>
		         </dl>
		        <dl>
		               <dt>浏览器 String Hash</dt>
					   <dd>${info.browser_string_hash}</dd>
				</dl>
				<dl>	   
					   <dt>浏览器版本</dt>
					   <dd>${info.browser_version}</dd>
		         </dl>
		       
				<dl>	   
					   <dt>CC BIN</dt>
					   <dd>${info.cc_bin_number }</dd>
		         </dl>
		         <dl>	   
					   <dt>CC BIN 品牌</dt>
					   <dd>${info.cc_bin_number_brand}</dd>
		         </dl>
		         <dl>	   
					   <dt>CC BIN 类别</dt>
					   <dd>${info.cc_bin_number_category}</dd>
		         </dl>
		        
		         <dl>	   
					   <dt>CC BIN 国家</dt>
					   <dd>${info.cc_bin_number_geo}</dd>
		         </dl>
		         <dl>	   
					   <dt>发卡机构</dt>
					   <dd>${info.cc_bin_number_org}</dd>
		         </dl>
		         <dl>	   
					   <dt>CC BIN 类型</dt>
					   <dd>${info.cc_bin_number_type} </dd>
		         </dl>
		         <dl>	   
					   <dt>CSS Image Loaded</dt>
					   <dd>${info.css_image_loaded}</dd>
		         </dl>
		         
		         <dl>	   
					   <dt>Flash侦测</dt>
					   <dd>${info.detected_fl}</dd>
		         </dl>
		          <dl>	   
					   <dt>设备ID第一次看到时间</dt>
					   <dd>${info.device_first_seen}</dd>
		         </dl>
		          <dl>	   
					   <dt>设备ID</dt>
					   <dd>${info.device_id}</dd>
		         </dl>
		         <dl>	   
					   <dt>设备ID信心值</dt>
					   <dd>${info.device_id_confidence}</dd>
		         </dl>
		          <dl>	   
					   <dt>设备ID最后事件时间</dt>
					   <dd>${info.device_last_event} </dd>
		         </dl>
		         <dl>	   
					   <dt>设备ID上次更新时间</dt>
					   <dd>${info.device_last_update}</dd>
		         </dl>
		         <dl>	   
					   <dt>设备ID一致结果</dt>
					   <dd>${info.device_match_result}</dd>
		         </dl>
		          <dl>	   
					   <dt>设备ID结果</dt>
					   <dd>${info.device_result}</dd>
		         </dl>
		          <dl>	   
					   <dt>设备ID分数</dt>
					   <dd>
					   	${info.device_score }
					   </dd>
		         </dl>
		          <dl>	   
					   <dt>设备ID最差评分</dt>
					   <dd>
					   ${info.device_worst_score }
					   </dd>
		         </dl>
		           <dl>	   
					   <dt>域名解析</dt>
					   <dd>${info.dns_ip}</dd>
		         </dl>
		          <dl>	   
					   <dt>域名解析城市</dt>
					   <dd>${info.dns_ip_city}</dd>
		         </dl>
		         <dl>	   
					   <dt>域名解析国家</dt>
					   <dd>${info.dns_ip_geo}</dd>
		         </dl>
		         <dl>	   
					   <dt>域名解析网络服务提供商</dt>
					   <dd>${info.dns_ip_isp}</dd>
		         </dl>
		         <dl>	   
					   <dt>域名解析纬度</dt>
					   <dd>${info.dns_ip_latitude}</dd>
		         </dl>
		         <dl>	   
					   <dt>域名解析经度</dt>
					   <dd>${info.dns_ip_longitude}</dd>
		         </dl>
		         <dl>	   
					   <dt>域名解析组织</dt>
					   <dd>${info.dns_ip_organization}</dd>
		         </dl>
		         <dl>	   
					   <dt>域名解析地区</dt>
					   <dd>${info.dns_ip_region}</dd>
		         </dl>
		         <dl>	   
					   <dt>Cookies 启用</dt>
					   <dd>${info.enabled_ck}</dd>
		         </dl>
		         <dl>	   
					   <dt>Flash 启用</dt>
					   <dd>${info.enabled_fl}</dd>
		         </dl>
		         <dl>	   
					   <dt>Images 启用</dt>
					   <dd>${info.enabled_im}</dd>
		         </dl>
		         <dl>	   
					   <dt>Javascript 启用</dt>
					   <dd>${info.enabled_js}</dd>
		         </dl>
		         <dl>	   
					   <dt>事件类型</dt>
					   <dd>${info.event_type}</dd>
		         </dl>
		         <dl>	   
					   <dt>Flash 语言</dt>
					   <dd>${info.flash_lang}</dd>
		         </dl>
		         <dl>	   
					   <dt>Flash OS</dt>
					   <dd>${info.flash_os}</dd>
		         </dl>
		         <dl>	   
					   <dt>Flash 版本</dt>
					   <dd>${info.flash_version}</dd>
		         </dl>
		         <dl>	   
					   <dt>Smart ID 第一次看到時間</dt>
					   <dd>${info.fuzzy_device_first_seen}</dd>
		         </dl>
		         <dl>	   
					   <dt>Smart ID</dt>
					   <dd>${info.fuzzy_device_id}</dd>
		         </dl>
		         <dl>	   
					   <dt>Smart ID信心值</dt>
					   <dd>${info.fuzzy_device_id_confidence}</dd>
		         </dl>
		         <dl>	   
					   <dt>Smart ID最后事件時間</dt>
					   <dd>${info.fuzzy_device_last_event}</dd>
		         </dl>
		         <dl>	   
					   <dt>Smart ID上次更新時間</dt>
					   <dd>${info.fuzzy_device_last_update}</dd>
		         </dl>
		         <dl>	   
					   <dt>Smart ID一致结果</dt>
					   <dd>${info.fuzzy_device_match_result}</dd>
		         </dl>
		         <dl>	   
					   <dt>Smart ID结果</dt>
					   <dd>${info.fuzzy_device_result}</dd>
		         </dl>
		         <dl>	   
					   <dt>Smart ID评分</dt>
					   <dd>${info.fuzzy_device_score}</dd>
		         </dl>
		         <dl>	   
					   <dt>Smart ID最差评分</dt>
					   <dd>${info.fuzzy_device_worst_score}</dd>
		         </dl>
		         <dl>	   
					   <dt>Headers Name Hash</dt>
					   <dd>${info.headers_name_value_hash}</dd>
		         </dl>
		         <dl>	   
					   <dt>Headers Order Hash</dt>
					   <dd>${info.headers_order_string_hash}</dd>
		         </dl>
		         <dl>	   
					   <dt>诱捕指纹</dt>
					   <dd>${info.honeypot_fingerprint}</dd>
		         </dl>
		         <dl>	   
					   <dt>诱捕指纹检查</dt>
					   <dd>${info.honeypot_fingerprint_check}</dd>
		         </dl>
		         <dl>	   
					   <dt>诱捕指纹匹配</dt>
					   <dd>${info.honeypot_fingerprint_match}</dd>
		         </dl>
		         <dl>	   
					   <dt>HTTP Referer</dt>
					   <dd>${info.http_referer}</dd>
		         </dl>
		         <dl>	   
					   <dt>HTTP Referer 域名</dt>
					   <dd>${info.http_referer_domain}</dd>
		         </dl>
		         <dl>	   
					   <dt>HTTP Referer URL</dt>
					   <dd>${info.http_referer_url}</dd>
		         </dl>
		         <dl>	   
					   <dt>Image Loaded</dt>
					   <dd>${info.image_loaded}</dd>
		         </dl>
		         <dl>	   
					   <dt>输入IP</dt>
					   <dd>${info.input_ip_address}</dd>
		         </dl>
		         <dl>	   
					   <dt>输入IP城市</dt>
					   <dd>${info.input_ip_city}</dd>
		         </dl>
		         <dl>	   
					   <dt>输入IP第一次看到时间</dt>
					   <dd>${info.input_ip_first_seen}</dd>
		         </dl>
		         <dl>	   
					   <dt>输入IP国家</dt>
					   <dd>${info.input_ip_geo}</dd>
		         </dl>
		         <dl>	   
					   <dt>输入IP网络服务提供商</dt>
					   <dd>${info.input_ip_isp}</dd>
		         </dl>
		         <dl>	   
					   <dt>输入IP最后事件時間</dt>
					   <dd>${info.input_ip_last_event}</dd>
		         </dl>
		         <dl>	   
					   <dt>输入IP上次更新时间</dt>
					   <dd>${info.input_ip_last_update}</dd>
		         </dl>
		         <dl>	   
					   <dt>输入IP纬度</dt>
					   <dd>${info.input_ip_latitude}</dd>
		         </dl>
		         <dl>	   
					   <dt>输入IP经度</dt>
					   <dd>${info.input_ip_longitude}</dd>
		         </dl>
		         <dl>	   
					   <dt>输入IP组织</dt>
					   <dd>${info.input_ip_organization}</dd>
		         </dl>
		         <dl>	   
					   <dt>输入IP地区</dt>
					   <dd>${info.input_ip_region}</dd>
		         </dl>
		         <dl>	   
					   <dt>输入IP结果</dt>
					   <dd>${info.input_ip_result}</dd>
		         </dl>
		         <dl>	   
					   <dt>输入IP评分</dt>
					   <dd>${info.input_ip_score}</dd>
		         </dl>
		         <dl>	   
					   <dt>浏览器 (JavaScript侦测)</dt>
					   <dd>${info.js_browser}</dd>
		         </dl>
		         <dl>	   
					   <dt>浏览器 String (JavaScript侦测)</dt>
					   <dd>${info.js_browser_string}</dd>
		         </dl>
		         <dl>	   
					   <dt>浏览器 String Hash(JavaScript侦测)</dt>
					   <dd>${info.js_browser_string_hash}</dd>
		         </dl>
		         <dl>	   
					   <dt>字体 Hash(JavaScript侦测)</dt>
					   <dd>${info.js_fonts_hash}</dd>
		         </dl>
		         <dl>	   
					   <dt>字体数量(JavaScript侦测)</dt>
					   <dd>${info.js_fonts_number}</dd>
		         </dl>
		         <dl>	   
					   <dt>OS(JavaScript侦测)</dt>
					   <dd>${info.js_os}</dd>
		         </dl>
		         <dl>	   
					   <dt>MIME Type Hash</dt>
					   <dd>${info.mime_type_hash}</dd>
		         </dl>
		         <dl>	   
					   <dt>MIME Type Number</dt>
					   <dd>${info.mime_type_number}</dd>
		         </dl>
		         <dl>	   
					   <dt>Org ID</dt>
					   <dd>${info.org_id}</dd>
		         </dl>
		         <dl>	   
					   <dt>OS</dt>
					   <dd>${info.os}</dd>
		         </dl>
		         <dl>	   
					   <dt>字体 Hash</dt>
					   <dd>${info.os_fonts_hash}</dd>
		         </dl>
		         <dl>	   
					   <dt>字体 Count</dt>
					   <dd>${info.os_fonts_number}</dd>
		         </dl>
		         <dl>	   
					   <dt>OS 版本</dt>
					   <dd>${info.os_version}</dd>
		         </dl>
		         <dl>	   
					   <dt>页面上的时间</dt>
					   <dd>${info.page_time_on}</dd>
		         </dl>
		         <dl>	   
					   <dt>Plug-in Flash</dt>
					   <dd>${info.plugin_flash}</dd>
		         </dl>
		         <dl>	   
					   <dt>Plug-in Hash</dt>
					   <dd>${info.plugin_hash}</dd>
		         </dl>
		         <dl>	   
					   <dt>Plug-in Number</dt>
					   <dd>${info.plugin_number}</dd>
		         </dl>
		         <dl>	   
					   <dt>Plug-in Windows Media Player</dt>
					   <dd>${info.plugin_windows_media_player}</dd>
		         </dl>
		         <dl>	   
					   <dt>策略</dt>
					   <dd>${info.policy}</dd>
		         </dl>
		         <dl>	   
					   <dt>评分</dt>
					   <dd>${info.policy_score}</dd>
		         </dl>
		         <dl>	   
					   <dt>Profiled Domain</dt>
					   <dd>${info.profiled_domain}</dd>
		         </dl>
		         <dl>	   
					   <dt>Profiled URL</dt>
					   <dd>${info.profiled_url}</dd>
		         </dl>
		         <dl>	   
					   <dt>Profile 时间</dt>
					   <dd>${info.profiling_datetime}</dd>
		         </dl>
		         <dl>	   
					   <dt>原因</dt>
					   <dd>${info.reason_code}</dd>
		         </dl>
		         <dl>	   
					   <dt>Related Request ID</dt>
					   <dd>${info.request_id}</dd>
		         </dl>
		         <dl>	   
					   <dt>结果</dt>
					   <dd>${info.request_result}</dd>
		         </dl>
		         <dl>	   
					   <dt>审查状态</dt>
					   <dd>${info.review_status}</dd>
		         </dl>
		         <dl>	   
					   <dt>TMX 风险评级</dt>
					   <dd>${info.risk_rating}</dd>
		         </dl>
		         <dl>	   
					   <dt>屏幕颜色深度</dt>
					   <dd>${info.screen_color_depth}</dd>
		         </dl>
		         <dl>	   
					   <dt>屏幕分辨率</dt>
					   <dd>${info.screen_dpi}</dd>
		         </dl>
		         <dl>	   
					   <dt>屏幕分辨率</dt>
					   <dd>${info.screen_res}</dd>
		         </dl>
		         <dl>	   
					   <dt>API 服务类型</dt>
					   <dd>${info.service_type}</dd>
		         </dl>
		         <dl>	   
					   <dt>Session ID</dt>
					   <dd>${info.session_id}</dd>
		         </dl>
		         <dl>	   
					   <dt>Session ID 查询次数</dt>
					   <dd>${info.session_id_query_count}</dd>
		         </dl>
		         <dl>	   
					   <dt>总结风险评分</dt>
					   <dd>${info.summary_risk_score}</dd>
		         </dl>
		         <dl>	   
					   <dt>时区</dt>
					   <dd>${info.time_zone}</dd>
		         </dl>
		         <dl>	   
					   <dt>时区DST偏移</dt>
					   <dd>${info.time_zone_dst_offset}</dd>
		         </dl>
		         <dl>	   
					   <dt>TMX Variables</dt>
					   <dd>${info.tmx_variables}</dd>
		         </dl>
		         <dl>	   
					   <dt>真实IP</dt>
					   <dd>${info.true_ip}</dd>
		         </dl>
		         <dl>	   
					   <dt>真实IP城市</dt>
					   <dd>${info.true_ip_city}</dd>
		         </dl>
		         <dl>	   
					   <dt>真实IP第一次看到时间</dt>
					   <dd>${info.true_ip_first_seen}</dd>
		         </dl>
		         <dl>	   
					   <dt>真实IP国家</dt>
					   <dd>${info.true_ip_geo}</dd>
		         </dl>
		         <dl>	   
					   <dt>真实IP网络服务提供商</dt>
					   <dd>${info.true_ip_isp}</dd>
		         </dl>
		         <dl>	   
					   <dt>真实IP最后事件时间</dt>
					   <dd>${info.true_ip_last_event}</dd>
		         </dl>
		         <dl>	   
					   <dt>真实IP上次更新时间</dt>
					   <dd>${info.true_ip_last_update}</dd>
		         </dl>
		         <dl>	   
					   <dt>真实IP纬度</dt>
					   <dd>${info.true_ip_latitude}</dd>
		         </dl>
		         <dl>	   
					   <dt>真实IP经度</dt>
					   <dd>${info.true_ip_longitude}</dd>
		         </dl>
		         <dl>	   
					   <dt>真实IP组织</dt>
					   <dd>${info.true_ip_organization}</dd>
		         </dl>
		         <dl>	   
					   <dt>真实IP地区</dt>
					   <dd>${info.true_ip_region}</dd>
		         </dl>
		         <dl>	   
					   <dt>真实IP结果</dt>
					   <dd>${info.true_ip_result}</dd>
		         </dl>
		         <dl>	   
					   <dt>真实IP评分</dt>
					   <dd>${info.true_ip_score}</dd>
		         </dl>
		         <dl>	   
					   <dt>真实IP最差评分</dt>
					   <dd>${info.true_ip_worst_score}</dd>
		         </dl>
		         <dl>	   
					   <dt>UA 浏览器</dt>
					   <dd>${info.ua_browser}</dd>
		         </dl>
		         <dl>	   
					   <dt>UA OS</dt>
					   <dd>${info.ua_os}</dd>
		         </dl>
		         <dl>	   
					   <dt>UA 平台</dt>
					   <dd>${info.ua_platform}</dd>
		         </dl>
		         <dl>	   
					   <dt>时间</dt>
					   <dd>${funcs:formatDate(info.transDate,'yyyy-MM-dd HH:mm:ss')}</dd>
		         </dl>
		       </div>
         </div>
  </div>
</div>
</body>
</html>