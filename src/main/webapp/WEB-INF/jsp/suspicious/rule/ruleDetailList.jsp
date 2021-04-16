<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>交易触犯规则详情</title>
</head>
<body>
    <div class="pageContent">
    <div class="pageHeader" id="pageHeaderx" style="display: block;">
		<div class="searchBar" >
			<ul class="searchContent" >
				<li>
				<span>触犯规则总数: ${rule.count}</span> 
				</li>
			</ul>	
		</div>
	</div>
	<div id="w_list_print">
	<table class="list" width="98%" targetType="navTab" layoutH="10" style="text-align: center;">
		<thead>
			<tr>
				<th>规则ID</th>
				<th>规则名称</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${rule.list}" var="rules">
				<tr>
					<td>${rules.ruleId }</td>
					<td>${fn:replace(rules.ruleName,",","<br>") }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</div>
</body>
</html>