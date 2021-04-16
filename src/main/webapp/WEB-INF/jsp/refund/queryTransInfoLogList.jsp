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
		<title>交易变更--退款</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	</head>
	<script type="text/javascript">
	</script>
	<body>
		<div id="w_list_print">
			<table class="list" width="100%" targetType="navTab" layoutH="1" style="text-align: center;">
		<thead>
			<tr>
				<th>流水号</th>
				<th>发起时间</th>
				<th>发起人</th>
				<th>操作类型</th>
				<th>操作金额</th>
				<th>审核状态</th>
				<th>审核人</th>
				<th>审核时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="li">
				<tr target="sid_role" rel="${merchant.id }"    align="center">
					<td>${li.tradeNo}</td>
					<td>${li.createDate}</td>
					<td>${li.createBy}</td>
					<td>
						<c:if test="${li.transType == 'dishonor'}">
							拒付
						</c:if>
						<c:if test="${li.transType == 'frozen'}">
							冻结
						</c:if>
						<c:if test="${li.transType == 'thaw'}">
							解冻
						</c:if>
						<c:if test="${li.transType == 'refund'}">
							退款
						</c:if>
					</td>
					<td>${li.transCurrency} ${li.transMoney}</td>
					<td>
						<c:if test="${li.status=='0'}">待审核</c:if>
						<c:if test="${li.status=='1'}">未通过</c:if>
						<c:if test="${li.status=='2'}">已通过</c:if>
					</td>
					<td>${li.checkBy}</td>
					<td>${li.checkDate}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	</body>
</html>	