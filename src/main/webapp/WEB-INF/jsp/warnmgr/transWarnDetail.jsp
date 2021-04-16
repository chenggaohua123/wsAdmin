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
<title>交易预警信息</title>
</head>
<body>
<div id="w_list_print">
	<table class="list" width="98%" targetType="navTab" layoutH="10" style="text-align: center;">
		<thead>
			<tr>
				<th>银行</th>
				<th>监控条件</th>
				<th>监控时效</th>
				<%--
				<th>预警发送邮箱</th>
				 --%>
				<th>预警邮件发送间隔时间</th>
				<th>出现笔数</th>
				<th>失败原因内容</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="warnInfo">
				<tr  target="id" rel="${warnInfo.id }"    align="center">
					<td>${warnInfo.bankName }</td>
					<td>
						<c:if test="${warnInfo.type==0 }">交易返回信息</c:if>
						<c:if test="${warnInfo.type==1 }">连续交易失败</c:if>
						<c:if test="${warnInfo.type==2 }">无新增交易</c:if>
					</td>
					<td>${warnInfo.activeTime } 分钟</td>
					<%--
					<td>${warnInfo.emails }</td>
					 --%>
					<td>${warnInfo.cycle } 分钟</td>
					<td>${warnInfo.time } 笔</td>
					<td>${warnInfo.respMsg }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</body>
</html>