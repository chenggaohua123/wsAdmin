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
<title>汇率历史记录</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/exchangRate/queryExchangRateList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageContent">
	<div id="w_list_print">
	<table class="list" width="200%" targetType="navTab" layoutH="35" style="text-align: center;">
		<thead>
			<tr>
				<th>组名</th>
				<th>汇率类型</th>
				<th>原始币种</th>
				<th>目标币种</th>
				<th>汇率</th>
				<th>偏移值</th>
				<th>是否有效</th>
				<th>银行汇率类型</th>
				<th>发起人</th>
				<th>发起时间</th>
				<th>审核状态</th>
				<th>审核类型</th>
				<th>审核人</th>
				<th>审核时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="exchangRate">
				<tr target="rateId" rel="${exchangRate.id }"   >
					<td>${exchangRate.groupName }</td>
					<td>${exchangRate.type=='settle'?'结算汇率':'交易汇率'}</td>
					<td>${exchangRate.sourceCurrency}</td>
					<td>${exchangRate.targetCurrency}</td>
					<td>${exchangRate.rate}</td>
					<td>${exchangRate.offsetValue}</td>
					<td>${exchangRate.enabled==1?'有效':'无效'}</td>
					<td>${funcs:getStringColumnKey('bankRateType',exchangRate.rateType,'未知状态')}</td>
					<td>${exchangRate.createBy}</td>
					<td>${exchangRate.createDate}</td>
					<td>${funcs:getStringColumnKey('CHECK_STATUS',exchangRate.checkStatus,'未知状态')}</td>
					<td>${funcs:getStringColumnKey('OPERATION_TYPE',exchangRate.operationType,'未知状态')}</td>
					<td>${exchangRate.checkBy}</td>
					<td>${exchangRate.checkDate}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</div>
</body>
</html>