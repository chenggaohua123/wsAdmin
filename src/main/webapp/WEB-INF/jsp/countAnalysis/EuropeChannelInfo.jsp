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
<title>关联订单信息</title>
</head>
<body>
    <div class="pageContent">
    <div class="pageHeader" id="pageHeaderx" style="display: block;">
		<div class="searchBar" >
			<ul class="searchContent" >
				<li>
				<span>欧洲通道信息总数: ${count}</span> 
				</li>
			</ul>	
		</div>
	</div>
	<div class="panelBar">
	</div>
	<div id="w_list_print">
	<table class="list" width="98%" targetType="navTab" layoutH="10" style="text-align: center;">
		<thead>
			<tr>
				<th>名称</th>
				<th>返回码</th>
				<th>描述</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="europe">
				<tr>
					<td>${europe.name }</td>
					<td>${europe.code }</td>
					<td>${europe.description}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</div>
</body>
</html>