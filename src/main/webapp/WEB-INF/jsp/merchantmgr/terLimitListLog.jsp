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
<title>商户终端限额历史记录</title>
</head>
<body>
<div class="pageContent">
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="9" style="text-align: center;">
		<thead>
			<tr>
				<th>商户号</th>
				<th>终端号</th>
				<th>单笔限额</th>
				<th>日限额</th>
				<th>月限额</th>
				<th>操作类型</th>
				<th>操作人</th>
				<th>操作时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="limit">
				<tr target="sid_role" rel="${limit.id }" align="center">
					<td>${limit.merNo }</td>
					<td>${limit.terNo }</td>
					<td>${limit.singleTransAmountLimit }</td>
					<td>${limit.dayTransAmountLimit }</td>
					<td>${limit.monthTransAmountLimit }</td>
					<td>
						<c:if test="${limit.type== 'add'}">添加</c:if>
						<c:if test="${limit.type== 'update'}">修改</c:if>
					</td>
					<td>${limit.upby }</td>
					<td>${funcs:formatDate(limit.updateDate,'yyyy-MM-dd HH:mm')} </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</div>
</body>
</html>