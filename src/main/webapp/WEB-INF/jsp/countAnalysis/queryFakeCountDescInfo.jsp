<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="funcs" uri="funcs"%>    
<%
	String path = request.getContextPath();
%>     
<!DOCTYPE html PUBLIC "-//W3C//Dlabel HTML 4.01 pansitional//EN" "http://www.w3.org/p/html4/loose.dlabel">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看投诉单占比</title>
</head>
<body>
<div class="pageContent">
	<div id="w_list_print">
	<table class="list" width="98%" targetType="navTab" layoutH="20">
		<thead>
			<tr>
				<th>投诉原因</th>
				<th>投诉笔数</th>
				<th>占比</th>
				<th>投诉转换拒付笔数</th>
				<th>拒付转换率</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="config">
				<tr align="center">
					<td>${config.reason }</td>
					<td>${config.comCount }</td>
					<td>
					<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${config.comRate*100 }"/>%</td>
					<td>${config.comToDisCount }</td>
					<td>
					<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${config.comToDisRate*100 }"/>%</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
  </div>
</div>
</body>
</html>