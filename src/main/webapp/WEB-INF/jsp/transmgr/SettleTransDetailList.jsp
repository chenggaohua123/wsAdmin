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
<title>状态变更记录</title>
</head>
<body>
    <div class="pageContent">
	<div id="w_list_print">
	<table class="list" width="98%" targetType="navTab" layoutH="10" style="text-align: center;">
		<thead>
			<tr>
				<th>流水号</th>
				<th>参考号</th>
				<th>商户号</th>
				<th>终端号</th>
				<th>交易类型</th>
				<th>交易金额</th>
				<th>商户手续费</th>
				<th>代理商手续费</th>
				<th>父级代理商手续费</th>
				<th>返回码</th>
				<th>交易时间</th>
				<th>交易通道</th>
				<th>交易银行</th>
				<th>结算日期</th>
				<th>结算批次号</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="trans">
				<tr>
					<td>${trans.tradeNo }</td>
					<td>${trans.relNo }</td>
					<td>${trans.merNo }</td>
					<td>${trans.terNo }</td>
					<td>${funcs:getStringColumnKey('gw_transtype_info',trans.transType,'未知状态')}</td>
					<td>${trans.transAmount }</td>
					<td>${trans.merForAmount }</td>
					<td>${trans.agentForAmount }</td>
					<td>${trans.parentAgentForAmount }</td>
					<td>${funcs:getStringColumnKey('RESP_INFO',trans.respCode,trans.respCode)}</td>
					<td>${funcs:formatDate(trans.transDate,'yyyy-MM-dd HH:mm:ss')} </td>
					<td>${trans.currencyName }</td>
					<td>${trans.bankName }</td>
					<td>${trans.settleDate }</td>
					<td>${trans.settleBatchNo}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</div>
</body>
</html>