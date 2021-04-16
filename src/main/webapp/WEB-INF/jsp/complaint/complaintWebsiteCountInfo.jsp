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
<title>投诉类型列表</title>
</head>
<body>

<div class="pageContent">

	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="115" style="text-align:center;" >
		<thead>
			<tr>
				<th>网址</th>
				<th>总数</th>
				<th>投诉类型</th>
				<th>投诉描述</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="info">
				<tr>
					<td>${info.payWebSite }</td>
					<td>${info.complaintCount }</td>
					<td>
						<c:if test="${info.type=='0' }">调查单</c:if>
						<c:if test="${info.type=='1' }">拒付</c:if>
						<c:if test="${info.type=='2' }">持卡人投诉</c:if>
						<c:if test="${info.type=='3' }">伪冒单</c:if>
					</td>
					<td>${info.complaintDesc}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</div>
</body>
</html>