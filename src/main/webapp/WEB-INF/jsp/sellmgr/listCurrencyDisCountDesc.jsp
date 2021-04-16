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
<title>显示拒付原因占比</title>
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
					<th>拒付原因</th>
					<th>拒付笔数</th>
					<th>占比</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="info">
					<tr>
						<td>${info.disReason }</td>
						<td>${info.disCount }</td>
						<td>
						<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.disRate*100 }"></fmt:formatNumber>%
						
						</td>
						<td>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>