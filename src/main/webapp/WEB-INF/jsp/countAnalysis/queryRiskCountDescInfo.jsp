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
<title>查看风险单占比</title>
</head>
<body>
<div class="pageContent">
	<div id="w_list_print">
	<table class="list" width="98%" targetType="navTab" layoutH="20">
		<thead>
			<tr>
				<th>风险阻挡原因</th>
				<th>风险订单笔数</th>
				<th>占比</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="config">
				<tr align="center">
					<td>${config.reason }</td>
					<td>${config.riskCount }</td>
					<td>
					<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${config.riskRate*100 }"/>%</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
  </div>
</div>
</body>
</html>