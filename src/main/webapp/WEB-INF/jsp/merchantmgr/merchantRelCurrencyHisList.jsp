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
<title>商户通道绑定</title>
</head>
<body>
<div class="pageContent">
<table class="list" width="100%" targetType="navTab" layoutH="0">
		<thead>
			<tr align="center">
				<th colspan="2">商户信息</th>
				<th colspan="2">绑定银行信息</th>
				<th colspan="4">其他</th>
			</tr>
			<tr>
				<th>商户号</th>
				<th>终端号</th>
				<th>绑定银行名称</th>
				<th>绑定通道名称</th>
				<th>绑定卡种</th>
				<th>状态</th>
				<th>绑定时间</th>
				<th>绑定人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="merchant">
				<tr target="sid_role" rel="${merchant.id }"    align="center">
					<td>${merchant.merNo }</td>
					<td>${merchant.terNo == '0'?'所有':merchant.terNo}</td>
					<td>${merchant.bankName}</td>
					<td>${merchant.currencyName}</td>
					<td>${funcs:getStringColumnKey('CARDTYPE',merchant.cardType,'未知状态')}</td>
					<td>${funcs:getStringColumnKey('AGENT_STATUS',merchant.enabled,'未知状态')}</td>
					<td>${funcs:formatDate(merchant.createDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${merchant.createBy }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</body>
</html>