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
<style type="text/css">
</style>
<title>ThreatMetrix风险项详情</title>
</head>
<body>
	<div class="pageContent">
		<div id="w_list_print">
			<table class="list" width="100%" targetType="navTab"
				style="text-align: center;">
				<thead>
					<tr>
						<th>风险项</th>
					</tr>
				</thead>
				<tbody
					style="word-wrap: break-word; word-break: break-all; overflow-x: hidden;">
					<c:forEach items="${list}" var="info">
						<tr>
							<td>${info }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>