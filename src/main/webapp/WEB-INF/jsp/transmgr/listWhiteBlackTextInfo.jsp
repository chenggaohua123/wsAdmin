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
<title>白名单拒付规则信息列表</title>
</head>
<body>

	<table class="list" width="100%" style="text-align:center;" >
		<thead>
			<tr>
				<th>白名单规则类型</th>
				<th>白名单规则内容</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="info">
				<tr>
					<td>${info.blackType }</td>
					<td>${info.blackText }</td>
				</tr>
				
			</c:forEach>
		</tbody>
	</table>
</body>
</html>