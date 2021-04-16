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
<title>费率历史记录</title>
</head>
<body>
<div class="pageContent">
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="0">
		<thead>
				<tr>
					<th>商户号</th>
					<th>终端号</th>
					<th>银行名称</th>
					<th>商户费率</th>
					<th>保证金费率</th>
					<th>单笔手续费</th>
					<th>卡种</th>
					<th>创建时间</th>
					<th>创建人</th>
					<th>最后修改时间</th>
					<th>最后修改人</th>
				</tr>
			</thead>
		<tbody>
			<c:forEach items="${list}" var="rate">
					<tr>
						<td>${rate.merNo }</td>
						<td>${rate.terNo }</td>
						<td>${rate.bankId=='0'?'所有':rate.bankName}</td>
						<td>${rate.merRate }</td>
						<td>${rate.bondRate }</td>
						<td>${rate.singleFee }</td>
						<td>${rate.cardType }</td>
						<td>${funcs:formatDate(rate.createDate,'yyyy-MM-dd HH:mm')} </td>
						<td>${rate.createBy }</td>
						<td>${funcs:formatDate(rate.lastUpdateDate,'yyyy-MM-dd HH:mm')} </td>
						<td>${rate.lastUpdateBy }</td>
					</tr>
				</c:forEach>
		</tbody>
	</table>
	</div>
</div>
</body>
</html>