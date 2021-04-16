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
<title>代理商历史列表</title>
</head>
<body>
<div class="pageContent">
	<div id="w_list_print">
	<table class="list" width="98%" targetType="navTab" layoutH="20">
		<thead>
			<tr>
				<th>商户号</th>
				<th>父级代理号</th>
				<th>商户名称</th>
				<th>手机号码</th>
				<th>状态</th>
				<th>注册时间</th>
				<th>开通时间</th>
				<th>失效时间</th>
				<th>账户名</th>
				<th>账户省份</th>
				<th>账户城市</th>
				<th>账户国家</th>
				<th>开户行</th>
				<th>创建人</th>
				<th>修改时间</th>
				<th>修改人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="merchant">
				<tr>
					<td>${merchant.agentNo }</td>
					<td>${merchant.parentAgentNo=='0'?'顶级代理':merchant.parentAgentNo  }</td>
					<td>${merchant.agentName }</td>
					<td>${merchant.phoneNo }</td>
					<td>${funcs:getStringColumnKey('AGENT_STATUS',merchant.enabled,'未知状态')}</td>
					<td>${funcs:formatDate(merchant.regDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${funcs:formatDate(merchant.activationDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${funcs:formatDate(merchant.expDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${merchant.accountName }</td>
					<td>${merchant.accountState }</td>
					<td>${merchant.accountCity }</td>
					<td>${merchant.accountContryCode }</td>
					<td>${merchant.accountAddress }</td>
					<td>${merchant.createBy }</td>
					<td>${merchant.uDate }</td>
					<td>${merchant.ucreateBy }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</div>
</body>
</html>