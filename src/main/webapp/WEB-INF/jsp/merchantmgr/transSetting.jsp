<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%
	String path = request.getContextPath();
%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>交易设置信息</title>
</head>
<body>
<div class="pageContent"  layoutH="0">

	<table class="list" width="100%">
				<thead>
					<tr>
						<th>终端号</th>
						<th>交易币种</th>
						<th>结算币种</th>
						<th>结算周期</th>
						<th>结算条件</th>
						<th>保证金结算周期</th>
						<th>交易费率</th>
						<th>保证金费率</th>
						<th>单笔手续费</th>
						<th>单笔限额</th>
						<th>交易日限额</th>
						<th>交易月限额</th>
						<th>交易卡种</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list }" var="setting">
						<tr>
							<td>${setting.terNo } </td>
							<td>${setting.transCurrencyCode }</td>
							<td>${setting.settleCurrencyCode }</td>
							<td>${setting.transSettleCycle }</td>
							<td>${funcs:getStringColumnKey('SETTLETYPE',setting.transSettleCondition,setting.transSettleCondition)}</td>
							<td>${setting.bailSettleCycle }</td>
							<td> ${setting.transRate }</td>
							<td>${setting.bailRate }</td>
							<td>${setting.sigleRate }</td>
							<td><fmt:formatNumber value="${setting.sigleLimitAmount }"  maxFractionDigits="2"/></td>
							<td><fmt:formatNumber value="${setting.dayLimitAmount }"  maxFractionDigits="2"/></td>
							<td><fmt:formatNumber value="${setting.monthLimitAmount }"  maxFractionDigits="2"/></td>
							<td>${setting.cardType }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
</div>
</body>
</html>