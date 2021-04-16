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
<title>投诉人投诉历史记录</title>
</head>
<body>

<div class="pageContent">

	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="115" style="text-align:center;" >
		<thead>
			<tr>
				<th>投诉人</th>
				<th>投诉网址</th>
				<th>投诉类型</th>
				<th>投诉数量</th>
				<th>投诉描述 </th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="info">
				<tr>
					<td>${info.cardHolderName }</td>
					<td colspan="4">
						<table style="border: 0px;width: 100%;">
							<c:forEach items="${info.list }" var="des">
								<tr>
									<td style="width: 25%;">${des.payWebSite }</td>
									<td style="width: 25%;">
										<c:if test="${des.type=='0' }">调查单</c:if>
										<c:if test="${des.type=='1' }">拒付</c:if>
										<c:if test="${des.type=='2' }">持卡人投诉</c:if>
										<c:if test="${des.type=='3' }">伪冒单</c:if>
									</td>
									<td style="width: 25%;">${des.complaintCount }</td>
									<td style="width: 25%;">${des.complaintDesc }</td>
								</tr>
							</c:forEach>
							<tr></tr>
						</table>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</div>
</body>
</html>