<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%>    
<%
	String path = request.getContextPath();
%>     
<!DOCTYPE html PUBLIC "-//W3C//Dlabel HTML 4.01 pansitional//EN" "http://www.w3.org/p/html4/loose.dlabel">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看配置信息</title>
</head>
<body>
<div class="pageContent">
	<div id="w_list_print">
	<table class="list" width="98%" targetType="navTab" layoutH="20">
		<thead>
			<tr>
				<th>通道ID</th>
				<th>银行名称</th>
				<th>通道名称</th>
				<th>银行商户号</th>
				<th>银行终端号</th>
				<th>MCC</th>
				<th>是否关闭</th>
				<th>创建时间</th>
				<th>创建人</th>
				<th>修改人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="currency">
				<tr>
					<td>${currency.id }</td>
					<td>${currency.bankName }</td>
					<td>${currency.currencyName }</td>
					<td>${currency.merchantNo }</td>
					<td>${currency.terNo }</td>
					<td>${currency.mcc }</td>
					<td>${funcs:getStringColumnKey('MERCHANTCONFIG',currency.enabled,'未知状态')}</td>
					<td>${funcs:formatDate(currency.createDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${currency.createBy }</td>
					<td>${currency.ucreateBy }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
  </div>
</div>
</body>
</html>