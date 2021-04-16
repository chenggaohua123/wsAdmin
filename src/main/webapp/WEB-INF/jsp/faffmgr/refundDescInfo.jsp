<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>退款/拒付详情</title>
</head>
<body>
<div class="pageHeader">
</div>
    <div class="pageContent">
    <div class="panelBar">
	</div>
		<table class="list" width="100%" targetType="navTab" layoutH="142" style="text-align: center;">
			<thead>
				<tr>
					<th>商户号</th>
					<th>流水号</th>
					<th>银行交易金额</th>
					<th>退款/拒付金额</th>
					<th>交易时间</th>
					<th>退款/拒付时间</th>
					<th>操作人</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page}" var="info">
					<tr>
						<td>${info.merNo }</td>
						<td>${info.tradeNo }</td>
						<td>${info.bankTransCurrency } ${info.bankTransAmount }</td>
						<td>${info.refundCurrency } <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.refundAmount }"></fmt:formatNumber></td>
						<td>${funcs:formatDate(info.transDate,'yyyy-MM-dd HH:mm:ss')}</td>
						<td>${funcs:formatDate(info.refundDate,'yyyy-MM-dd HH:mm:ss')}</td>
						<td>${info.operateBy }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>